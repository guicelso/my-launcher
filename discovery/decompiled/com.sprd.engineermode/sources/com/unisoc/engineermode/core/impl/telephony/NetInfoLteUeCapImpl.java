package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class NetInfoLteUeCapImpl implements ITelephonyApi.INetInfoLteUeCap {
    private static final String CLOSE = "0";
    private static final String OPEN = "1";
    private static final String TAG = "NetInfoUeCap";

    public static class NetInfoLteUeCapImplHolder {
        static final ITelephonyApi.INetInfoLteUeCap INSTANCE = new NetInfoLteUeCapImpl();
    }

    NetInfoLteUeCapImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLteUeCap
    public boolean getCap(int simIdx, boolean[] isOn) {
        int num = 0;
        String channel = "atchannel" + simIdx;
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, channel);
        if (!result.contains("OK")) {
            return false;
        }
        String[] str1 = result.replaceAll("--", "-+").split("\n");
        String[] str2 = str1[0].split("-");
        if (str2.length < 27) {
            Log.d(TAG, "wrong data");
            return false;
        }
        for (int i = 15; i < str2.length; i++) {
            if (i == 17) {
                String[] ca = str2[i].split(",");
                if (ca.length < 2) {
                    Log.d(TAG, "data wrong");
                } else {
                    int num2 = num + 1;
                    isOn[num] = ca[0].equals(OPEN);
                    num = num2 + 1;
                    isOn[num2] = ca[1].equals(OPEN);
                }
            } else if (i == 25) {
                isOn[num] = str2[i].equals(OPEN);
                num++;
            }
        }
        return true;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLteUeCap
    public boolean openDlca(int simIdx) {
        return IATUtils.sendATCmd("AT1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLteUeCap
    public boolean closeDlca(int simIdx) {
        return IATUtils.sendATCmd("AT0", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLteUeCap
    public boolean openUlca(int simIdx) {
        return IATUtils.sendATCmd("AT+SPLTECFG=1,10", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLteUeCap
    public boolean closeUlca(int simIdx) {
        return IATUtils.sendATCmd("AT+SPLTECFG=0,10", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLteUeCap
    public boolean openUl64Qam(int simIdx) {
        return IATUtils.sendATCmd("AT+spengmd=1,0,7,3,1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLteUeCap
    public boolean closeUl64Qam(int simIdx) {
        return IATUtils.sendATCmd("AT+spengmd=1,0,7,3,0", simIdx).contains("OK");
    }
}
