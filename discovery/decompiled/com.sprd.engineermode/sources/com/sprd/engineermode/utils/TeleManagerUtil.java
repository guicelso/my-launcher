package com.sprd.engineermode.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyPropertiesProxy;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class TeleManagerUtil {
    public static final int MODEM_TYPE_GSM = 0;
    public static final int MODEM_TYPE_LTE = 3;
    public static final int MODEM_TYPE_TDSCDMA = 1;
    public static final int MODEM_TYPE_WCDMA = 2;
    private static String PROP_TEST_MODE = "persist.radio.ssda.testmode";
    private static final String TAG = "TeleManagerUtil";
    private static TeleManagerUtil mRingtoneManagerUtil;
    private TelephonyManager mTelephonyManager;

    public enum MultiSimVariants {
        DSDS,
        DSDA,
        TSTS,
        UNKNOWN
    }

    public enum RadioFeatures {
        SVLET,
        TD_LTE,
        LTE_FDD,
        TD_LTE_AND_LTE_FDD,
        LTE_FDD_AND_W_AND_GSM_CSFB,
        TD_LTE_AND_W_AND_GSM_CSFB,
        TD_LTE_AND_LTE_FDD_AND_W_AND_GSM_CSFB,
        TD_LTE_AND_TD_AND_GSM_CSFB,
        TD_LTE_AND_LTE_FDD_AND_TD_AND_GSM_CSFB,
        TD_LTE_AND_LTE_FDD_AND_W_AND_TD_AND_GSM_CSFB,
        GSM_ONLY,
        WCDMA_ONLY,
        TD_ONLY,
        TD_AND_GSM,
        WCDMA_AND_GSM,
        NONE
    }

    private TeleManagerUtil(Context context) {
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public static TeleManagerUtil getInstance(Context context) {
        if (mRingtoneManagerUtil == null) {
            mRingtoneManagerUtil = new TeleManagerUtil(context);
        }
        return mRingtoneManagerUtil;
    }

    /* JADX INFO: renamed from: com.sprd.engineermode.utils.TeleManagerUtil$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$sprd$engineermode$utils$TeleManagerUtil$MultiSimVariants = new int[MultiSimVariants.values().length];

        static {
            try {
                $SwitchMap$com$sprd$engineermode$utils$TeleManagerUtil$MultiSimVariants[MultiSimVariants.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sprd$engineermode$utils$TeleManagerUtil$MultiSimVariants[MultiSimVariants.DSDS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sprd$engineermode$utils$TeleManagerUtil$MultiSimVariants[MultiSimVariants.DSDA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sprd$engineermode$utils$TeleManagerUtil$MultiSimVariants[MultiSimVariants.TSTS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public int getPhoneCount() {
        int i = AnonymousClass1.$SwitchMap$com$sprd$engineermode$utils$TeleManagerUtil$MultiSimVariants[getMultiSimConfiguration().ordinal()];
        if (i == 1) {
            return 1;
        }
        if (i == 2 || i == 3) {
            return 2;
        }
        if (i != 4) {
            return 1;
        }
        return 3;
    }

    public boolean hasIccCard(int phoneId) {
        return true;
    }

    public MultiSimVariants getMultiSimConfiguration() {
        String mSimConfig = SystemPropertiesProxy.get("persist.radio.multisim.config");
        if (mSimConfig.equals("dsds")) {
            return MultiSimVariants.DSDS;
        }
        if (mSimConfig.equals("dsda")) {
            return MultiSimVariants.DSDA;
        }
        if (mSimConfig.equals("tsts")) {
            return MultiSimVariants.TSTS;
        }
        return MultiSimVariants.UNKNOWN;
    }

    public static int getModemType() throws IllegalAccessException, InvocationTargetException {
        String baseBand = SystemPropertiesProxy.get(TelephonyPropertiesProxy.PROPERTY_BASEBAND_VERSION, "");
        if (baseBand != null && !baseBand.equals("")) {
            if (baseBand.contains("sc8810_modem") || baseBand.contains("sc8825_modem") || baseBand.contains("sc8830_modem") || baseBand.contains("sc8830g_CP0_modem") || baseBand.contains("sc8815_sc8815_modem")) {
                return 1;
            }
            if (baseBand.contains("sc7702_modem") || baseBand.contains("sc7710g2_modem") || baseBand.contains("sc8830_CP0_modem") || baseBand.contains("sc7715_sc7715_modem") || baseBand.contains("sc7730g_CP0_modem") || baseBand.contains("sc7731g_CP0_modem")) {
                return 2;
            }
            if (baseBand.contains("sc9620_parmv0") || baseBand.contains("sc9620") || baseBand.contains("sc9630") || baseBand.contains("sc9830")) {
                return 3;
            }
            return 0;
        }
        return 0;
    }

    public static RadioFeatures getRadioFeatures(int phoneId) throws IllegalAccessException, InvocationTargetException {
        Log.d(TAG, "getRadioFeatures: phoneId=" + phoneId);
        String radioTestMode = phoneId > 0 ? PROP_TEST_MODE + phoneId : PROP_TEST_MODE;
        int testMode = SystemPropertiesProxy.getInt(radioTestMode, -1);
        Log.d(TAG, "getRadioFeatures: testMode=" + testMode);
        if (testMode < 15) {
            for (RadioFeatures feature : RadioFeatures.values()) {
                if (feature.ordinal() == testMode) {
                    return feature;
                }
            }
        }
        return RadioFeatures.NONE;
    }
}
