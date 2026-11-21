package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class Csfb2GmsDelayImpl implements ITelephonyApi.ICsfb2GmsDelay {
    private static final String TAG = "Csfb2GsmDelay";

    Csfb2GmsDelayImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICsfb2GmsDelay
    public boolean getStatus(int simIdx, int[] status) {
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_GET_CSFB2GSM, simIdx);
        Log.d(TAG, "<" + simIdx + ">get Csfb2Gsm_delay Result is " + result);
        String[] str = result.split("\n");
        if (str.length < 1) {
            return false;
        }
        String[] str1 = str[0].split("\\:");
        if (str1.length < 2) {
            return false;
        }
        String[] str2 = str1[1].split(",");
        if (!result.contains("OK")) {
            return false;
        }
        if (str2[0].trim().contains("1")) {
            status[0] = 1;
            status[1] = 0;
        } else if (str2[1].trim().contains("1")) {
            status[0] = 0;
            status[1] = 1;
        } else {
            status[0] = 0;
            status[1] = 0;
        }
        return true;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICsfb2GmsDelay
    public boolean openGrrcResident(int simIdx) {
        return IATUtils.sendATCmd("AT+SPSETGRRC=1,1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICsfb2GmsDelay
    public boolean closeGrrcResident(int simIdx) {
        return IATUtils.sendATCmd("AT+SPSETGRRC=0,1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICsfb2GmsDelay
    public boolean openGrrcRandomAccess(int simIdx) {
        return IATUtils.sendATCmd("AT+SPSETGRRC=1,2", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICsfb2GmsDelay
    public boolean closeGrrcRandomAccess(int simIdx) {
        return IATUtils.sendATCmd("AT+SPSETGRRC=0,2", simIdx).contains("OK");
    }
}
