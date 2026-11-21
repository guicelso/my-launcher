package com.sprd.engineermode.debuglog;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class LTEBandData {
    public static final String KEY_FDD_LTE = "FDD_BAND";
    public static final String KEY_TDD_LTE = "TDD_BAND";
    private static final String ORI_LTE_BAND_FDD = "ORI_LTE_BAND_FDD";
    private static final String ORI_LTE_BAND_TDD = "ORI_LTE_BAND_TDD";
    private static final String TAG = "LTDBandData";
    private static LTEBandData mLTEBandData;
    private String mChannel;
    private Context mContext;
    private int mPhoneID;
    public ITelephonyApi.IBand.LteBand supportedBand;
    private int mLTESupportBand_TDD = -1;
    private int mLTESupportBand_FDD = -1;
    private int mLTECheckedBand_TDD = 0;
    private int mLTECheckedBand_FDD = 0;
    private ITelephonyApi.IBand.LteBand checkedBand = null;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();

    public static synchronized LTEBandData getInstance(int phoneID, Context context) {
        if (mLTEBandData == null) {
            mLTEBandData = new LTEBandData(phoneID, context);
        }
        return mLTEBandData;
    }

    public LTEBandData(int phoneID, Context context) {
        this.supportedBand = null;
        this.mContext = context;
        this.mPhoneID = phoneID;
        this.mChannel = "atchannel" + this.mPhoneID;
        this.supportedBand = loadBands();
    }

    public ITelephonyApi.IBand.LteBand getSupportBands() {
        ITelephonyApi.IBand.LteBand lteBand = this.supportedBand;
        if (lteBand != null) {
            return lteBand;
        }
        return getBands();
    }

    private ITelephonyApi.IBand.LteBand getBands() {
        try {
            ITelephonyApi.IBand.LteBand band = this.teleApi.band().getLteBand(this.mPhoneID);
            return band;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean loadSelectedBand() {
        this.checkedBand = getBands();
        ITelephonyApi.IBand.LteBand lteBand = this.checkedBand;
        if (lteBand == null) {
            return false;
        }
        if (this.supportedBand == null) {
            saveBands(lteBand);
            this.supportedBand = loadBands();
            return true;
        }
        return true;
    }

    public int[] getSupportBand_TDD() {
        return this.supportedBand.tddBands;
    }

    public int[] getSupportBand_FDD() {
        return this.supportedBand.fddBands;
    }

    public int[] getCheckedBand_TDD() {
        return this.checkedBand.tddBands;
    }

    public int[] getCheckedBand_FDD() {
        return this.checkedBand.fddBands;
    }

    private void saveBands(ITelephonyApi.IBand.LteBand lteBand) {
        String tddSaveValue = "";
        String fddSaveValue = "";
        for (int i = 0; i < 32; i++) {
            if (lteBand.tddBands[i] == 1) {
                if (!tddSaveValue.equals("")) {
                    tddSaveValue = tddSaveValue + ",";
                }
                tddSaveValue = tddSaveValue + i;
            }
            if (lteBand.fddBands[i] == 1) {
                if (!fddSaveValue.equals("")) {
                    fddSaveValue = fddSaveValue + ",";
                }
                fddSaveValue = fddSaveValue + i;
            }
        }
        Log.d(TAG, "save tdd band: " + tddSaveValue);
        Log.d(TAG, "save fdd band: " + fddSaveValue);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ORI_LTE_BAND_TDD, tddSaveValue);
        editor.putString(ORI_LTE_BAND_FDD, fddSaveValue);
        editor.apply();
    }

    private ITelephonyApi.IBand.LteBand loadBands() {
        ITelephonyApi.IBand.LteBand lteBand = new ITelephonyApi.IBand.LteBand();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        String tddSaveValue = sp.getString(ORI_LTE_BAND_TDD, "");
        String fddSaveValue = sp.getString(ORI_LTE_BAND_FDD, "");
        if (tddSaveValue.equals("") && fddSaveValue.equals("")) {
            return null;
        }
        if (!tddSaveValue.equals("")) {
            String[] bands = tddSaveValue.split(",");
            for (String band : bands) {
                Log.d(TAG, "load band: " + band);
                lteBand.tddBands[Integer.valueOf(band).intValue()] = 1;
            }
        }
        if (!fddSaveValue.equals("")) {
            String[] bands2 = fddSaveValue.split(",");
            for (String band2 : bands2) {
                Log.d(TAG, "load band: " + band2);
                lteBand.fddBands[Integer.valueOf(band2).intValue()] = 1;
            }
        }
        return lteBand;
    }
}
