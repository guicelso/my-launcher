package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class WcdmaPreferImpl implements ITelephonyApi.IWcdmaPrefer {
    private static final int SIM0 = 0;

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IWcdmaPrefer
    public String getWcdmaPrefer() {
        String atRsp = IATUtils.sendATCmd(engconstents.ENG_GET_WPREFER_SWITCH, 0);
        if (atRsp.contains("OK")) {
            return atRsp;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRsp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IWcdmaPrefer
    public void openWcdmaPrefer() {
        String atRsp = IATUtils.sendATCmd("AT+SPWPREFERSWITCH=1", 0);
        if (!atRsp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRsp);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IWcdmaPrefer
    public void closeWcdmaPrefer() {
        String atRsp = IATUtils.sendATCmd("AT+SPWPREFERSWITCH=0", 0);
        if (!atRsp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRsp);
        }
    }
}
