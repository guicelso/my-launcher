package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ControlMeidImpl implements ITelephonyApi.IControlMeid {
    private static final String mAtChannel = "atchannel1";

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IControlMeid
    public String getMeidStatus() throws Exception {
        String mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_GET_MEID_SWITCH, mAtChannel);
        if (mStrTmp.contains("OK")) {
            return mStrTmp;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IControlMeid
    public void openMeidSwitch() throws Exception {
        String atRSP = IATUtils.sendATCmd(engconstents.ENG_AT_OPEN_MEID_SWITCH, mAtChannel);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IControlMeid
    public void closeMeidSwitch() throws Exception {
        String atRSP = IATUtils.sendATCmd(engconstents.ENG_AT_CLOSE_MEID_SWITCH, mAtChannel);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }
}
