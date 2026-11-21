package com.unisoc.engineermode.core.impl.telephony;

import android.telephony.TelephonyManager;
import android.util.Log;
import com.unisoc.engineermode.core.common.CommonKt;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class SimFuncImpl implements ITelephonyApi.ISimFunc {
    private static final String TAG = "SIMFUNC";

    SimFuncImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ISimFunc
    public String readPlmn(int simIdx) throws Exception {
        String cmd;
        if (isSimExist(simIdx)) {
            if (isUsim(simIdx)) {
                cmd = engconstents.ENG_RPLMN_USIM;
            } else {
                cmd = engconstents.ENG_RPLMN_SIM;
            }
            String rsp = IATUtils.sendATCmd(cmd, simIdx);
            Log.d(TAG, rsp);
            return paserFPLMN(rsp);
        }
        throw new UnsupportedOperationException("sim not exists");
    }

    private String paserFPLMN(String atRSP) {
        String infoPasered = "";
        if (atRSP.contains("OK")) {
            String[] str = atRSP.split(",");
            for (int i = 0; i < 4; i++) {
                int start = i * 6;
                if (str[2].substring(start + 1, start + 2).compareTo("F") != 0) {
                    String infoPasered2 = (((((infoPasered + "MCC:") + str[2].substring(start + 1, start + 2)) + str[2].substring(start + 0, start + 1)) + str[2].substring(start + 3, start + 4)) + " ") + "MNC:";
                    if (str[2].substring(start + 2, start + 3).compareTo("F") != 0) {
                        infoPasered2 = infoPasered2 + str[2].substring(start + 2, start + 3);
                    }
                    infoPasered = ((infoPasered2 + str[2].substring(start + 5, start + 6)) + str[2].substring(start + 4, start + 5)) + "\n";
                }
            }
            return infoPasered;
        }
        return "Error";
    }

    private boolean isSimExist(int simIndex) {
        TelephonyManager telephonyManager = (TelephonyManager) CommonKt.appCtx.getSystemService("phone");
        return telephonyManager != null && telephonyManager.getSimState(simIndex) == 5;
    }

    private boolean isUsim(int simIndex) {
        String rsp = IATUtils.sendATCmd("AT+CRSM=192,28539,0,0,15,0,\"3F007FFF\"", "atchannel" + simIndex);
        Log.d(TAG, rsp);
        if (rsp.contains("OK")) {
            String[] str = rsp.split(",");
            if (str[2].substring(0, 1).compareTo("0") == 0 && str[2].substring(1, 2).compareTo("0") == 0 && str[2].substring(2, 3).compareTo("0") == 0) {
                return true;
            }
        }
        return false;
    }
}
