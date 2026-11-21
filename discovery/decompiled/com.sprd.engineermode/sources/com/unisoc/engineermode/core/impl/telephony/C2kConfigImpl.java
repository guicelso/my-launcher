package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class C2kConfigImpl implements ITelephonyApi.IC2kConfig {
    C2kConfigImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IC2kConfig
    public String getC2kConfig() throws Exception {
        String mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_GET_SPCAS_PARAM, 0);
        if (mStrTmp.contains("OK")) {
            return mStrTmp;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IC2kConfig
    public void setC2kConfig(String value) throws Exception {
        String mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_SET_SPCAS_PARAM + value, 0);
        if (!mStrTmp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
        }
    }
}
