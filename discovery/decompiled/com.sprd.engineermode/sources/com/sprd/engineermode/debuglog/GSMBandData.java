package com.sprd.engineermode.debuglog;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.util.Log;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GSMBandData extends BandData {
    private static final String ORI_GSM_BAND = "ORI_GSM_BAND";
    private static final String TAG = "GSMBandData";
    private static final int[] mSBANDMap = {1, 2, 4, 8, 3, 9, 10, 12, 5, 11, 13, 6, 14, 7, 15};
    private List<ITelephonyApi.IBand.GsmBand> supportedBands;
    private ITelephonyApi teleApi;

    public GSMBandData(Context context, int phoneID) {
        super(context, phoneID);
        this.supportedBands = null;
        this.teleApi = CoreApi.getTelephonyApi();
    }

    public GSMBandData(Context context, int phoneID, PreferenceCategory pref) {
        super(context, phoneID, pref);
        this.supportedBands = null;
        this.teleApi = CoreApi.getTelephonyApi();
        this.supportedBands = loadBands();
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    protected boolean getSelectedBand() {
        try {
            List<ITelephonyApi.IBand.GsmBand> gsmBand = this.teleApi.band().getGsmBand(this.mPhoneID);
            if (this.supportedBands.isEmpty()) {
                this.supportedBands.addAll(gsmBand);
                saveBands(gsmBand);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (ITelephonyApi.IBand.GsmBand gsmBand2 : this.supportedBands) {
                arrayList.add(gsmBand2.name());
                arrayList2.add("g" + gsmBand2.name());
                arrayList3.add(Integer.valueOf(gsmBand.contains(gsmBand2) ? 1 : 0));
            }
            if (this.mBandTitle == null) {
                this.mBandTitle = (String[]) arrayList.toArray(new String[0]);
                this.mBandFrequency = (String[]) arrayList2.toArray(new String[0]);
                this.mPrefCheckBox = new CheckBoxPreference[this.mBandTitle.length];
                this.mSelecteds = new int[arrayList3.size()];
            }
            for (int i = 0; i < arrayList3.size(); i++) {
                this.mSelecteds[i] = ((Integer) arrayList3.get(i)).intValue();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected String[] getSelectATCmd() {
        return new String[0];
    }

    private void saveBands(List<ITelephonyApi.IBand.GsmBand> bandList) {
        if (bandList.isEmpty()) {
            return;
        }
        String saveValue = "";
        for (ITelephonyApi.IBand.GsmBand band : bandList) {
            if (!saveValue.equals("")) {
                saveValue = saveValue + ",";
            }
            saveValue = saveValue + band.name();
        }
        Log.d(TAG, "save gsm band: " + saveValue);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ORI_GSM_BAND, saveValue);
        editor.apply();
    }

    private List<ITelephonyApi.IBand.GsmBand> loadBands() {
        List<ITelephonyApi.IBand.GsmBand> bandList = new ArrayList<>();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        String saveValue = sp.getString(ORI_GSM_BAND, "");
        if (saveValue.equals("")) {
            return bandList;
        }
        String[] bands = saveValue.split(",");
        for (String band : bands) {
            Log.d(TAG, "load band: " + band);
            bandList.add(ITelephonyApi.IBand.GsmBand.valueOf(band));
        }
        return bandList;
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    public boolean setSelectedBandToModem() {
        List<ITelephonyApi.IBand.GsmBand> bandList = new ArrayList<>();
        for (int i = 0; i < this.supportedBands.size(); i++) {
            if (this.mSelecteds[i] == 1) {
                bandList.add(this.supportedBands.get(i));
            }
        }
        Log.d(TAG, "setSelectedBandToModem");
        try {
            this.teleApi.band().setGsmBand(this.mPhoneID, bandList);
            this.mSetSuccessful = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.mSetSuccessful = false;
            return false;
        }
    }
}
