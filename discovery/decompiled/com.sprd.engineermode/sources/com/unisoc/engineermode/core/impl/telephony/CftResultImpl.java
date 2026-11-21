package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CftResultImpl implements ITelephonyApi.ICftResult {
    private static final int SIM0 = 0;

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICftResult
    public String getGsmTdScdmaCFTResult() {
        String mStrTmp = IATUtils.sendATCmd("AT+SGMR=0,0,3,0", 0);
        if (mStrTmp.contains("OK")) {
            return mStrTmp;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICftResult
    public String getWcdmaCFTResult() {
        String mStrTmp = IATUtils.sendATCmd("AT+SGMR=0,0,3,1", 0);
        if (mStrTmp.contains("OK")) {
            return mStrTmp;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICftResult
    public String getC2kCFTResult() {
        String mStrTmp = IATUtils.sendATCmd("AT+SGMR=0,0,3,2", 0);
        if (mStrTmp.contains("OK")) {
            return mStrTmp;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICftResult
    public String getLteCFTResult() {
        String mStrTmp = IATUtils.sendATCmd("AT+SGMR=1,0,3,3", 0);
        if (mStrTmp.contains("OK")) {
            return mStrTmp;
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }
}
