package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class VolteDevelopSettingsImpl implements ITelephonyApi.IVolteDevelopSettings {
    private static final int SIM0 = 0;

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteDevelopSettings
    public int getDevFlag1() {
        return getDevFlag(1);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteDevelopSettings
    public int getDevFlag2() {
        return getDevFlag(2);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteDevelopSettings
    public int getDevFlag3() {
        return getDevFlag(3);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteDevelopSettings
    public void setDevFlag1(int value) {
        setDevFlag(1, value);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteDevelopSettings
    public void setDevFlag2(int value) {
        setDevFlag(2, value);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteDevelopSettings
    public void setDevFlag3(int value) {
        setDevFlag(3, value);
    }

    private void setDevFlag(int index, int value) {
        String at = String.format("%s100%d,1,\"%s\"", engconstents.ENG_AT_SPVOLTEENG, Integer.valueOf(index), Integer.valueOf(value));
        String atRsp = IATUtils.sendATCmd(at, 0);
        if (!atRsp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRsp);
        }
    }

    private int getDevFlag(int index) {
        String at = String.format("%s100%d,0", engconstents.ENG_AT_SPVOLTEENG, Integer.valueOf(index));
        String atRsp = IATUtils.sendATCmd(at, 0);
        if (atRsp.contains("OK")) {
            return anayResult(atRsp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRsp);
    }

    private int anayResult(String result) {
        try {
            String value = result.split("\\:")[1].split("\n")[0].split(",")[1].trim().replace("\"", "");
            return Integer.parseInt(value);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }
}
