package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.data.telephony.VamosCpcData;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class VamosCpcImpl implements ITelephonyApi.IVamosCpc {
    private static final String TAG = "VamosCpc";

    VamosCpcImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVamosCpc
    public VamosCpcData getVamosCpc() {
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, 0);
        Log.d(TAG, engconstents.ENG_AT_SPENGMD_007 + ": " + result);
        int vamosValue = -1;
        int cpcValue = -1;
        if (result.contains("OK")) {
            String[] str1 = result.replaceAll("--", "-+").split("\n");
            String[] str2 = str1[0].split("-");
            if (str2.length > 7) {
                if (str2[7].contains(",")) {
                    String[] str3 = str2[7].split(",");
                    vamosValue = Integer.valueOf(str3[0].replace("+", "-").trim()).intValue();
                } else {
                    vamosValue = Integer.valueOf(str2[7].replace("+", "-").trim()).intValue();
                }
            }
            if (str2.length > 14) {
                cpcValue = Integer.valueOf(str2[14].replace("+", "-").trim().substring(0, 1)).intValue();
            }
        }
        return new VamosCpcData(vamosValue, cpcValue);
    }
}
