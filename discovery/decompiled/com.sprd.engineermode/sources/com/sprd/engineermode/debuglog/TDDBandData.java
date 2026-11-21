package com.sprd.engineermode.debuglog;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.util.Log;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class TDDBandData extends BandData {
    private static final String TAG = "TDDBandData";
    LTEBandData mLTEBandData;
    ArrayList<String> mTitle;
    private ITelephonyApi teleApi;

    public TDDBandData(Context context, int phoneID) {
        super(context, phoneID);
        this.teleApi = CoreApi.getTelephonyApi();
        this.mLTEBandData = LTEBandData.getInstance(phoneID, context);
        this.mTitle = new ArrayList<>();
    }

    public TDDBandData(Context context, int phoneID, PreferenceCategory pref) {
        super(context, phoneID, pref);
        this.teleApi = CoreApi.getTelephonyApi();
        this.mLTEBandData = LTEBandData.getInstance(phoneID, context);
    }

    public int[] getSupportBands() {
        if (this.mLTEBandData.loadSelectedBand()) {
            return this.mLTEBandData.getSupportBand_TDD();
        }
        return null;
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    protected boolean getSelectedBand() {
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> key = new ArrayList<>();
        ArrayList<Integer> checked = new ArrayList<>();
        this.mLTEBandData.loadSelectedBand();
        int[] supportband = this.mLTEBandData.getSupportBand_TDD();
        int[] checkedband = this.mLTEBandData.getCheckedBand_TDD();
        for (int i = 0; i < 32; i++) {
            if (supportband[i] == 1) {
                title.add(LTEBandData.KEY_TDD_LTE + (i + 33));
                key.add(LTEBandData.KEY_TDD_LTE + (i + 33));
                checked.add(Integer.valueOf(checkedband[i]));
            }
        }
        if (this.mBandTitle == null) {
            this.mBandTitle = (String[]) title.toArray(new String[0]);
            this.mBandFrequency = (String[]) key.toArray(new String[0]);
            this.mPrefCheckBox = new CheckBoxPreference[this.mBandTitle.length];
            this.mSelecteds = new int[checked.size()];
        }
        for (int i2 = 0; i2 < checked.size(); i2++) {
            this.mSelecteds[i2] = checked.get(i2).intValue();
        }
        return true;
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    public boolean setSelectedBandToModem() {
        this.mLTEBandData.loadSelectedBand();
        ITelephonyApi.IBand.LteBand newBand = new ITelephonyApi.IBand.LteBand();
        newBand.tddBands = Arrays.copyOf(this.mLTEBandData.getCheckedBand_TDD(), 32);
        newBand.fddBands = Arrays.copyOf(this.mLTEBandData.getCheckedBand_FDD(), 32);
        int n = 0;
        for (int i = 0; i < 32; i++) {
            if (this.mLTEBandData.supportedBand.tddBands[i] == 1) {
                if (this.mSelecteds[n] == 1) {
                    newBand.tddBands[i] = 1;
                } else {
                    newBand.tddBands[i] = 0;
                }
                n++;
            }
        }
        Log.d(TAG, "setSelectedBandToModem");
        try {
            this.teleApi.band().setLteBand(this.mPhoneID, newBand);
            this.mSetSuccessful = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.mSetSuccessful = false;
            return false;
        }
    }
}
