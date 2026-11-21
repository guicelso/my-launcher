package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class DataServicePreferImpl implements ITelephonyApi.IDataServicePrefer {
    private static final String TAG = "DATASERVICEPREFER";

    DataServicePreferImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IDataServicePrefer
    public void open() throws Exception {
        Log.d(TAG, "OPEN_DATA_SERVICES_PRE");
        String result = IATUtils.sendATCmd("AT+SPBPM=1", "atchannel0");
        if (!result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IDataServicePrefer
    public void close() throws Exception {
        Log.d(TAG, "CLOSE_DATA_SERVICES_PRE");
        String result = IATUtils.sendATCmd("AT+SPBPM=0", "atchannel0");
        if (!result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }
}
