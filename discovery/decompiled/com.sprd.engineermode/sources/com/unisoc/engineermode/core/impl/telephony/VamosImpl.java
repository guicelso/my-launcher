package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class VamosImpl implements ITelephonyApi.IVamos {
    private static final String TAG = "ENGVAMOS";

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVamos
    public ITelephonyApi.IVamos.VamosState getState() {
        String atResponse = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, "atchannel0");
        if (!atResponse.contains("OK")) {
            throw new OperationFailedException("AT failed: " + atResponse);
        }
        ITelephonyApi.IVamos.VamosState state = new ITelephonyApi.IVamos.VamosState();
        String[] stateStr = atResponse.split("-");
        if (stateStr.length > 7 && stateStr[7].contains(",")) {
            String[] temp = stateStr[7].split(",");
            if (temp.length >= 2 && temp[0].equals("1")) {
                state.isSupported = true;
                if (temp[1].equals("1")) {
                    state.isWorking = true;
                }
            }
            return state;
        }
        throw new OperationFailedException("AT response parse error: " + atResponse);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVamos
    public void openCapability() {
        setCapability(true);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVamos
    public void closeCapability() {
        setCapability(false);
    }

    private void setCapability(boolean isOpened) {
        String value = isOpened ? "1" : "0";
        String atCmd = "AT+SPENGMD=1,0,7," + value;
        Log.d(TAG, "SET_STATUS atCmd = " + atCmd);
        String atResponse = IATUtils.sendATCmd(atCmd, "atchannel0");
        Log.d(TAG, "SET_STATUS responValue = " + atResponse);
        if (!atResponse.contains("OK")) {
            throw new OperationFailedException("AT Failed: " + atResponse);
        }
    }
}
