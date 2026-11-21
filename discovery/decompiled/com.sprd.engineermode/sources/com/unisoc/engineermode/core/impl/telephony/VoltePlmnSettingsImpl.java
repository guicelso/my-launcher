package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class VoltePlmnSettingsImpl implements ITelephonyApi.IVoltePlmnSettings {
    private static final int SIM0 = 0;

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVoltePlmnSettings
    public String getPlmn1() {
        return getPlmn(1);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVoltePlmnSettings
    public void setPlmn1(String plmn) {
        setPlmn(1, plmn);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVoltePlmnSettings
    public String getPlmn2() {
        return getPlmn(2);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVoltePlmnSettings
    public void setPlmn2(String plmn) {
        setPlmn(2, plmn);
    }

    private String getPlmn(int index) {
        int id;
        if (index == 1) {
            id = 107;
        } else {
            id = 108;
        }
        String at = String.format(Locale.US, "%s%d,0", engconstents.ENG_AT_SPVOLTEENG, Integer.valueOf(id));
        String mStrTmp = IATUtils.sendATCmd(at, 0);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    private void setPlmn(int index, String plmn) {
        int id;
        if (index == 1) {
            id = 107;
        } else {
            id = 108;
        }
        String at = String.format(Locale.US, "%s%d,1,\"%s\"", engconstents.ENG_AT_SPVOLTEENG, Integer.valueOf(id), plmn);
        String atRsp = IATUtils.sendATCmd(at, 0);
        if (!atRsp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRsp);
        }
    }

    private String anayResult(String result) {
        try {
            return result.split("\\:")[1].split("\n")[0].split(",")[1].trim().replace("\"", "");
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }
}
