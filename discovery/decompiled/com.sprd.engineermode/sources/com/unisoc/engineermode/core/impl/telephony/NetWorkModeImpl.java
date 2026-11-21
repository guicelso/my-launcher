package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class NetWorkModeImpl implements ITelephonyApi.INetWorkMode {
    private static final String TAG = "EM-NetWorkMode";

    public static class NetWorkModeImplHolder {
        public static ITelephonyApi.INetWorkMode INSTANCE = new NetWorkModeImpl();
    }

    NetWorkModeImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetWorkMode
    public void setNetWorkMode(String atCmd, int mSimIndex) throws Exception {
        String atRsp = IATUtils.sendATCmd("AT+SFUN=5", mSimIndex);
        judgeAtResponse(atRsp);
        String atRsp2 = IATUtils.sendATCmd(atCmd, mSimIndex);
        judgeAtResponse(atRsp2);
        String atRsp3 = IATUtils.sendATCmd("AT+SFUN=4", mSimIndex);
        judgeAtResponse(atRsp3);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetWorkMode
    public String getNetWorkMode(int mSimIndex) {
        return IATUtils.sendATCmd(engconstents.ENG_AT_GET_WCDMA_MODEM, mSimIndex);
    }

    private void judgeAtResponse(String atRSP) throws Exception {
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }
}
