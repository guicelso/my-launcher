package com.sprd.engineermode.debuglog;

import android.content.Context;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.util.Log;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.impl.telephony.TelephonyManagerSprd;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class BandSelector {
    public static final int RADIO_MODE_CDMA2000 = 7;
    public static final int RADIO_MODE_GSM = 4;
    public static final int RADIO_MODE_LTE_FDD = 3;
    public static final int RADIO_MODE_MASK_CDMA2000 = 128;
    private static final int RADIO_MODE_MASK_GSM = 16;
    private static final int RADIO_MODE_MASK_LTE_FDD = 8;
    public static final int RADIO_MODE_MASK_NONE = 0;
    private static final int RADIO_MODE_MASK_TD_LTE = 4;
    private static final int RADIO_MODE_MASK_TD_SCDMA = 32;
    private static final int RADIO_MODE_MASK_WCDMA = 64;
    public static final int RADIO_MODE_NONE = 0;
    public static final int RADIO_MODE_TD_LTE = 2;
    public static final int RADIO_MODE_TD_SCDMA = 5;
    public static final int RADIO_MODE_WCDMA = 6;
    private static final String TAG = "BandSelector";
    private boolean PrimeCardFlag;
    private BandData[] mBandDatas;
    private Context mContext;
    private int mPhoneCount;
    private int mPhoneID;
    private int mPrimeryCard;
    private TelephonyManagerSprd.RadioCapbility mRadioCapbility;
    private int mRadioFeature;
    private int[] mSupportRadioModes;
    private Handler mUiThread;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();

    public BandSelector(int phoneID, Context context, Handler threadUi) {
        this.mUiThread = new Handler();
        TelephonyManagerSprd tmSprd = TelephonyManagerSprd.getInstance();
        this.mContext = context;
        this.mUiThread = threadUi;
        this.mPhoneID = phoneID;
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mPrimeryCard = tmSprd.getPrimaryCard();
        this.mRadioFeature = tmSprd.getPreferredNetworkType(this.mPhoneID);
        if (this.mPhoneID == this.mPrimeryCard) {
            this.PrimeCardFlag = true;
            this.mRadioCapbility = TelephonyManagerSprd.getRadioCapbility();
        } else {
            this.PrimeCardFlag = false;
            if (!this.teleApi.EngTestStatus().isEngTest()) {
                this.teleApi.EngTestStatus().set(1);
                this.mRadioFeature = tmSprd.getPreferredNetworkType(Math.abs(this.mPrimeryCard - 1));
                this.teleApi.EngTestStatus().set(0);
            } else {
                this.mRadioFeature = tmSprd.getPreferredNetworkType(Math.abs(this.mPrimeryCard - 1));
            }
        }
        getSupportRadioModes();
        Log.d(TAG, "Init OK:" + toString());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("mPhoneID:");
        sb.append(this.mPhoneID);
        sb.append(" mPhoneCount:");
        sb.append(this.mPhoneCount);
        sb.append(" mPrimeryCard:");
        sb.append(this.mPrimeryCard);
        sb.append(" mRadioCapbility:");
        sb.append(this.mRadioCapbility);
        sb.append(" mRadioFeature:");
        sb.append(this.mRadioFeature);
        sb.append(" support modes:");
        int[] iArr = this.mSupportRadioModes;
        if (iArr == null) {
            sb.append("null");
        } else {
            for (int n : iArr) {
                sb.append(getModeName(n) + " ");
            }
        }
        return sb.toString();
    }

    public String getSetInfo() {
        String info = "";
        if (this.mSupportRadioModes == null) {
            return "error.";
        }
        for (int i = 0; i < this.mSupportRadioModes.length; i++) {
            String info2 = info + getModeName(this.mSupportRadioModes[i]);
            BandData[] bandDataArr = this.mBandDatas;
            if (bandDataArr[i] != null && bandDataArr[i].isChanged()) {
                info = this.mBandDatas[i].successful() ? info2 + ":Success\n" : info2 + ":Fail\n";
            } else {
                info = info2 + ":Unchange\n";
            }
        }
        return info;
    }

    private String getModeName(int mode) {
        if (mode == 2) {
            return "LTE_TDD MODE";
        }
        if (mode == 3) {
            return "LTE_FDD MODE";
        }
        if (mode == 4) {
            return "GSM MODE";
        }
        if (mode == 5) {
            return "TD_SCDMA MODE";
        }
        if (mode == 6) {
            return "WCDMA MODE";
        }
        return "NONE";
    }

    private int getCapbility() {
        if (!this.PrimeCardFlag) {
            Log.d(TAG, "getCapbility mRadioFeature: " + this.mRadioFeature);
            switch (this.mRadioFeature) {
                case 56:
                    return 92;
                case 59:
                    return 124;
                case 60:
                    return 16;
                case 61:
                    return 64;
                case 64:
                    return 80;
                case 65:
                    return !this.teleApi.telephonyInfo().isSupportTdscdma() ? 80 : 112;
                case 67:
                    return 124;
                case 68:
                    return 108;
            }
        }
        Log.d(TAG, "getCapbility mRadioCapbility: " + this.mRadioCapbility);
        if (this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.NONE) {
            return 80;
        }
        if (this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.TDD_CSFB) {
            return 52;
        }
        if (this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.FDD_CSFB) {
            return 92;
        }
        if (this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.CSFB || this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.TDD_TDD) {
            return 124;
        }
        if (this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.TD_TD) {
            return 92;
        }
        if (this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.WG || this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.WW) {
            return 80;
        }
        return (this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.FLW || this.mRadioCapbility == TelephonyManagerSprd.RadioCapbility.TLTWCG_TLTWCG) ? 92 : 0;
    }

    private int getRadioFeature() {
        Log.d(TAG, "mRadioFeature: " + this.mRadioFeature);
        int i = this.mRadioFeature;
        if (i == 51) {
            return 4;
        }
        if (i == 52) {
            return 8;
        }
        if (i == 53) {
            return 12;
        }
        if (i == 54) {
            return 88;
        }
        if (i == 55) {
            return 84;
        }
        if (i == 56) {
            return 92;
        }
        if (i == 57) {
            return 52;
        }
        if (i == 58) {
            return 60;
        }
        if (i == 59) {
            return 124;
        }
        if (i == 60) {
            return 16;
        }
        if (i == 61) {
            return 64;
        }
        if (i == 62) {
            return 32;
        }
        if (i == 63) {
            return 48;
        }
        if (i == 64) {
            return 80;
        }
        if (i == -1) {
            return 16;
        }
        return 0;
    }

    private int[] getSupportRadioModes() {
        int modes1 = getCapbility();
        int n = 0;
        for (int i = 0; i < 8; i++) {
            if (((modes1 >> i) & 1) == 1) {
                n++;
            }
        }
        Log.d(TAG, "getSupportRadioModes modes1:" + modes1 + " modes:" + modes1 + " n:" + n);
        this.mSupportRadioModes = new int[n];
        int j = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = (1 << i2) & modes1;
            if (i3 == 4) {
                this.mSupportRadioModes[j] = 2;
            } else if (i3 == 8) {
                this.mSupportRadioModes[j] = 3;
            } else if (i3 == 16) {
                this.mSupportRadioModes[j] = 4;
            } else if (i3 != 32) {
                if (i3 == 64) {
                    this.mSupportRadioModes[j] = 6;
                }
            } else {
                this.mSupportRadioModes[j] = 5;
            }
            j++;
        }
        return this.mSupportRadioModes;
    }

    public void initModes(PreferenceGroup preferenceGroup) {
        if (this.mSupportRadioModes == null) {
            Log.d(TAG, "loadBands() mRadioModes is null");
            return;
        }
        if (this.mBandDatas != null) {
            return;
        }
        Log.d(TAG, "initModes mSupportRadioModes.length: " + this.mSupportRadioModes.length);
        this.mBandDatas = new BandData[this.mSupportRadioModes.length];
        for (int i = 0; i < this.mSupportRadioModes.length; i++) {
            BandData data = null;
            PreferenceCategory pref = new PreferenceCategory(this.mContext);
            pref.setTitle(getModeName(this.mSupportRadioModes[i]));
            preferenceGroup.addPreference(pref);
            int i2 = this.mSupportRadioModes[i];
            if (i2 == 2) {
                data = new TDDBandData(this.mContext, this.mPhoneID, pref);
            } else if (i2 == 3) {
                data = new FDDBandData(this.mContext, this.mPhoneID, pref);
            } else if (i2 == 4) {
                data = new GSMBandData(this.mContext, this.mPhoneID, pref);
            } else if (i2 == 5) {
                data = new TDBandData(this.mContext, this.mPhoneID, pref);
            } else if (i2 == 6) {
                data = new WCDMABandData(this.mContext, this.mPhoneID, pref);
            }
            if (data != null && data.init()) {
                BandData d = data;
                CheckBoxPreference[] cbPrefs = d.addAvailableBandsToPerference();
                for (CheckBoxPreference cb : cbPrefs) {
                    pref.addPreference(cb);
                }
                this.mBandDatas[i] = data;
            }
        }
    }

    public void loadBands() {
        Handler handler = this.mUiThread;
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.BandSelector.1
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < BandSelector.this.mBandDatas.length; i++) {
                    if (BandSelector.this.mBandDatas[i] != null) {
                        BandSelector.this.mBandDatas[i].init();
                    }
                }
            }
        });
    }

    public void saveBand() {
        for (BandData bands : this.mBandDatas) {
            if (bands != null && bands.isChanged()) {
                bands.setSelectedBandToModem();
            }
        }
    }

    public boolean isCheckOneOrMore() {
        int i = 0;
        while (true) {
            BandData[] bandDataArr = this.mBandDatas;
            if (i < bandDataArr.length) {
                if (bandDataArr[i] == null || bandDataArr[i].isCheckOneOrMore()) {
                    i++;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }
}
