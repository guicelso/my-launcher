package com.sprd.engineermode.debuglog;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.util.Log;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class TDBandData extends BandData {
    private static final String TAG = "TDBandData";
    private ITelephonyApi.IBand.TdBand band;
    private ITelephonyApi teleApi;

    public TDBandData(Context context, int phoneID, PreferenceCategory pref) {
        super(context, phoneID, pref);
        this.teleApi = CoreApi.getTelephonyApi();
        this.band = null;
        this.mBandTitle = new String[]{"TD_SCDMA A Band", "TD_SCDMA F Band"};
        this.mBandFrequency = new String[]{"td_a_frequency", "td_f_frequency"};
        this.mSelecteds = new int[]{0, 0};
        this.mPrefCheckBox = new CheckBoxPreference[this.mBandTitle.length];
    }

    public TDBandData(Context context, int phoneID) {
        super(context, phoneID);
        this.teleApi = CoreApi.getTelephonyApi();
        this.band = null;
        this.mBandTitle = new String[]{"TD_SCDMA A Band", "TD_SCDMA F Band"};
        this.mBandFrequency = new String[]{"td_a_frequency", "td_f_frequency"};
        this.mSelecteds = new int[]{0, 0};
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    protected boolean getSelectedBand() {
        ITelephonyApi.IBand.TdBand selectedBands = getBands();
        if (selectedBands == null) {
            return false;
        }
        if (selectedBands.band_a) {
            this.mSelecteds[0] = 1;
        }
        if (selectedBands.band_f) {
            this.mSelecteds[1] = 1;
        }
        this.band = selectedBands;
        return true;
    }

    private ITelephonyApi.IBand.TdBand getBands() {
        try {
            ITelephonyApi.IBand.TdBand band = this.teleApi.band().getTdBand(this.mPhoneID);
            return band;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ITelephonyApi.IBand.TdBand getSupportBands() {
        ITelephonyApi.IBand.TdBand tdBand = this.band;
        if (tdBand != null) {
            return tdBand;
        }
        if (getSelectedBand()) {
            return this.band;
        }
        return null;
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    public boolean setSelectedBandToModem() {
        ITelephonyApi.IBand.TdBand band = new ITelephonyApi.IBand.TdBand();
        if (this.mSelecteds[0] == 1) {
            band.band_a = true;
        }
        if (this.mSelecteds[1] == 1) {
            band.band_f = true;
        }
        Log.d(TAG, "setSelectedBandToModem");
        try {
            this.teleApi.band().setTdBand(this.mPhoneID, band);
            this.mSetSuccessful = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.mSetSuccessful = false;
            return false;
        }
    }
}
