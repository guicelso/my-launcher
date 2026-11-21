package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class NetInfoWcdma {
    private static final String TAG = "NetInfoWcdma";

    public static ITelephonyApi.INetInfoWcdmaUeCap.UeCap getUeCap(int simIdx) {
        String[] caps = getCapability(simIdx);
        String[] nv = getNvInfo(simIdx);
        ITelephonyApi.INetInfoWcdmaUeCap.UeCap uecap = new ITelephonyApi.INetInfoWcdmaUeCap.UeCap();
        uecap.ul16Qam = caps[26].startsWith("1");
        uecap.snow3g = nv[20].endsWith("1");
        uecap.wDiversity = nv[9].equals("1");
        uecap.dbHsdpa = nv[18].endsWith("1");
        if (caps[5].length() > 2) {
            uecap.hsdpa = caps[5].substring(2, 3).equals("1");
        } else {
            uecap.hsdpa = false;
        }
        Log.d(TAG, String.format("ueCap = %b, %b, %b, %b, %b", Boolean.valueOf(uecap.dbHsdpa), Boolean.valueOf(uecap.hsdpa), Boolean.valueOf(uecap.snow3g), Boolean.valueOf(uecap.ul16Qam), Boolean.valueOf(uecap.wDiversity)));
        return uecap;
    }

    public static List<String> getNwCap(int simIdx) {
        String[] caps = getCapability(simIdx);
        String[] nv = getNvInfo(simIdx);
        ArrayList<String> nwValueList = new ArrayList<>();
        nwValueList.add(caps[14].substring(caps[14].length() - 1));
        nwValueList.add(caps[26].substring(0, 1));
        nwValueList.add(nv[18].substring(0, 1));
        nwValueList.add(nv[13].substring(0, 1));
        nwValueList.add(nv[3].substring(nv[3].length() - 1));
        nwValueList.add(nv[2].substring(nv[2].length() - 1));
        nwValueList.add(nv[20].substring(0, 1));
        nwValueList.add(nv[8].substring(nv[8].length() - 1));
        return nwValueList;
    }

    private static String[] getCapability(int simIdx) {
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, simIdx);
        if (!result.contains("OK")) {
            throw new OperationFailedException("get capability failed. " + result);
        }
        String result2 = result.replace("--", "-+");
        String[] params = result2.split("\n")[0].split("-");
        if (params.length < 27) {
            Log.d(TAG, "wrong data");
            throw new OperationFailedException("get capability failed. " + result2);
        }
        return params;
    }

    private static String[] getNvInfo(int simIdx) {
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_008, simIdx);
        if (!result.contains("OK")) {
            throw new OperationFailedException("get capability failed. " + result);
        }
        String result2 = result.replace("--", "-+");
        String[] params = result2.split("\n")[0].split("-");
        if (params.length < 21) {
            Log.d(TAG, "wrong data");
            throw new OperationFailedException("get capability failed. " + result2);
        }
        return params;
    }
}
