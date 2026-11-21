package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ControlEvdoImpl implements ITelephonyApi.IControlEvdo {
    private static final int SIM0 = 0;
    private static final int SIM1 = 1;

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IControlEvdo
    public String getEvdoStatus() {
        String mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_GET_SPC2K_RAT, 0);
        if (mStrTmp.contains("OK")) {
            return mStrTmp;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IControlEvdo
    public void openEvdo() {
        String atRSP = IATUtils.sendATCmd("at+spc2ksetrat=1,1", 0);
        if (atRSP.contains("OK")) {
            String atRSP2 = IATUtils.sendATCmd("at+spc2ksetrat=1,1", 1);
            if (!atRSP2.contains("OK")) {
                throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP2);
            }
            return;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IControlEvdo
    public void closeEvdo() {
        String atRSP = IATUtils.sendATCmd("at+spc2ksetrat=1,0", 0);
        if (atRSP.contains("OK")) {
            String atRSP2 = IATUtils.sendATCmd("at+spc2ksetrat=1,0", 1);
            if (!atRSP2.contains("OK")) {
                throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP2);
            }
            return;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
    }
}
