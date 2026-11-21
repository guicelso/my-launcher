package com.unisoc.engineermode.core.impl.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.unisoc.engineermode.core.impl.nonpublic.RadioInteractorProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SubscriptionManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class TelephonyManagerSprd {
    static final String MODEM_TYPE = "ro.vendor.radio.modemtype";
    public static final int MODEM_TYPE_C2K = 4;
    public static final int MODEM_TYPE_GSM = 0;
    public static final int MODEM_TYPE_LTE = 3;
    public static final int MODEM_TYPE_TDSCDMA = 1;
    public static final int MODEM_TYPE_WCDMA = 2;
    public static final int NETWORK_MODE_BASE = 50;
    public static final int NETWORK_MODE_CDMA = 4;
    public static final int NETWORK_MODE_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 22;
    public static final int NETWORK_MODE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 21;
    public static final int NT_CDMA = 73;
    public static final int NT_EVDO = 74;
    public static final int NT_EVDO_CDMA = 72;
    public static final int NT_GSM = 60;
    public static final int NT_LTE_FDD = 52;
    public static final int NT_LTE_FDD_TD_LTE = 53;
    public static final int NT_LTE_FDD_TD_LTE_TDSCDMA_GSM = 58;
    public static final int NT_LTE_FDD_TD_LTE_WCDMA = 68;
    public static final int NT_LTE_FDD_TD_LTE_WCDMA_GSM = 56;
    public static final int NT_LTE_FDD_TD_LTE_WCDMA_TDSCDMA_GSM = 59;
    public static final int NT_LTE_FDD_WCDMA_GSM = 54;
    public static final int NT_LTE_WCDMA_TDSCDMA_EVDO_CDMA_GSM = 67;
    public static final int NT_TDSCDMA = 62;
    public static final int NT_TDSCDMA_GSM = 63;
    public static final int NT_TD_LTE = 51;
    public static final int NT_TD_LTE_TDSCDMA_GSM = 57;
    public static final int NT_TD_LTE_WCDMA_GSM = 55;
    public static final int NT_UNKNOWN = -1;
    public static final int NT_WCDMA = 61;
    public static final int NT_WCDMA_GSM = 64;
    public static final int NT_WCDMA_TDSCDMA_EVDO_CDMA_GSM = 65;
    public static final String SIM_STANDBY = "sim_standby";
    static final String TAG = "TelephonyManagerSprd";
    private static String PROP_SSDA_MODE = "persist.vendor.radio.modem.config";
    private static String MODE_SVLTE = "svlte";
    private static String MODE_TDD_CSFB = "TL_TD_G,G";
    private static String MODE_FDD_CSFB = "TL_LF_W_G,G";
    private static String MODE_CSFB = "TL_LF_TD_W_G,G";
    private static String MODE_LW = "TL_LF_TD_W_G,W_G";
    private static String MODE_FLW = "TL_LF_W_G,W_G";
    private static String MODE_WG = "W_G,G";
    private static String MODE_WG_WG = "W_G,W_G";
    private static String MODE_TD_TD = "TL_LF_W_G,TL_LF_W_G";
    private static String MODE_TDD_TDD = "TL_LF_TD_W_G,TL_LF_TD_W_G";
    private static String MODE_TLTWCG_TLTWCG = "TL_LF_TD_W_C_G,TL_LF_TD_W_C_G";

    public enum RadioCapbility {
        NONE,
        TDD_SVLTE,
        FDD_CSFB,
        TDD_CSFB,
        CSFB,
        LW,
        FLW,
        WG,
        WW,
        TD_TD,
        TDD_TDD,
        TLTWCG_TLTWCG
    }

    private static class TelephonyManagerSprdHolder {
        static final TelephonyManagerSprd INSTANCE = new TelephonyManagerSprd();

        private TelephonyManagerSprdHolder() {
        }
    }

    public static TelephonyManagerSprd getInstance() {
        return TelephonyManagerSprdHolder.INSTANCE;
    }

    private TelephonyManagerSprd() {
    }

    public static int getModemType() throws IllegalAccessException, InvocationTargetException {
        String modeType = SystemPropertiesProxy.get(MODEM_TYPE, "");
        Log.d(TAG, "getModemType: modemType=" + modeType);
        if ("t".equals(modeType)) {
            return 1;
        }
        if ("w".equals(modeType)) {
            return 2;
        }
        if ("tl".equals(modeType) || "lf".equals(modeType) || "l".equals(modeType)) {
            String ssdaMode = SystemPropertiesProxy.get(PROP_SSDA_MODE);
            Log.d(TAG, "getModemType: ssdaMode=" + ssdaMode);
            if (ssdaMode.equals(MODE_WG_WG) || ssdaMode.equals(MODE_WG)) {
                return 2;
            }
            if (ssdaMode.equals(MODE_TLTWCG_TLTWCG)) {
                return 4;
            }
            return 3;
        }
        return 0;
    }

    public int getPrimaryCard() {
        int phoneId = SubscriptionManagerProxy.getDefaultDataPhoneId();
        Log.d(TAG, "getPrimaryCard: " + phoneId);
        return phoneId;
    }

    public int getPreferredNetworkType(int slotIdx) {
        int networkType = RadioInteractorProxy.getPreferredNetworkType(slotIdx);
        Log.d(TAG, "the NetworkType of the slot[" + slotIdx + "]: " + networkType);
        return networkType;
    }

    public int getPreferredNetworkType() {
        return getPreferredNetworkType(getPrimaryCard());
    }

    public int setPreferredNetworkType(int slotIdx, int networkType) {
        if (!SubscriptionManagerProxy.isValidPhoneId(slotIdx)) {
            Log.d(TAG, "the slotIdx is not Valid");
            return -1;
        }
        return RadioInteractorProxy.setPreferredNetworkType(slotIdx, networkType);
    }

    public int setPreferredNetworkType(int networkType) {
        return setPreferredNetworkType(getPrimaryCard(), networkType);
    }

    public static RadioCapbility getRadioCapbility() {
        String ssdaMode = SystemPropertiesProxy.get(PROP_SSDA_MODE);
        Log.d(TAG, "getRadioCapbility: ssdaMode=" + ssdaMode);
        if (ssdaMode.equals(MODE_TDD_CSFB)) {
            return RadioCapbility.TDD_CSFB;
        }
        if (ssdaMode.equals(MODE_FDD_CSFB)) {
            return RadioCapbility.FDD_CSFB;
        }
        if (ssdaMode.equals(MODE_CSFB)) {
            return RadioCapbility.CSFB;
        }
        if (ssdaMode.equals(MODE_LW)) {
            return RadioCapbility.CSFB;
        }
        if (ssdaMode.equals(MODE_FLW)) {
            return RadioCapbility.FLW;
        }
        if (ssdaMode.equals(MODE_WG)) {
            return RadioCapbility.WG;
        }
        if (ssdaMode.equals(MODE_WG_WG)) {
            return RadioCapbility.WW;
        }
        if (ssdaMode.equals(MODE_TDD_TDD)) {
            return RadioCapbility.TDD_TDD;
        }
        if (ssdaMode.equals(MODE_TD_TD)) {
            return RadioCapbility.TD_TD;
        }
        if (ssdaMode.equals(MODE_TLTWCG_TLTWCG)) {
            return RadioCapbility.TLTWCG_TLTWCG;
        }
        return RadioCapbility.NONE;
    }

    public static boolean isSimStandby(int phoneId, Context context) {
        if (context == null) {
            return true;
        }
        ContentResolver contentResolver = context.getContentResolver();
        StringBuilder sb = new StringBuilder();
        sb.append(SIM_STANDBY);
        sb.append(phoneId);
        return Settings.Global.getInt(contentResolver, sb.toString(), 1) == 1;
    }
}
