package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class SimTraceImpl implements ITelephonyApi.ISimTrace {
    SimTraceImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ISimTrace
    public boolean get() throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPUSIMDRVLS?", "atchannel0");
        if (mStrTmp.contains("OK")) {
            return mStrTmp.contains("1");
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ISimTrace
    public void open() throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPUSIMDRVLS=1", "atchannel0");
        if (!mStrTmp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ISimTrace
    public void close() throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPUSIMDRVLS=0", "atchannel0");
        if (!mStrTmp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
        }
    }
}
