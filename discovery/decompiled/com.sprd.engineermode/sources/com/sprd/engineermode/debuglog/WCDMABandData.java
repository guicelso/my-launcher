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
public class WCDMABandData extends BandData {
    private static final String ORI_WCDMA_BAND = "ORI_WCDMA_BAND";
    private static final String TAG = "WCDMABandData";
    private List<ITelephonyApi.IBand.WcdmaBand> supportedBands;
    private ITelephonyApi teleApi;

    public WCDMABandData(Context context, int phoneID) {
        super(context, phoneID);
        this.supportedBands = null;
        this.teleApi = CoreApi.getTelephonyApi();
    }

    public WCDMABandData(Context context, int phoneID, PreferenceCategory pref) {
        super(context, phoneID, pref);
        this.supportedBands = null;
        this.teleApi = CoreApi.getTelephonyApi();
        this.supportedBands = loadBands();
    }

    public List<ITelephonyApi.IBand.WcdmaBand> getSupportBands() {
        List<ITelephonyApi.IBand.WcdmaBand> list = this.supportedBands;
        if (list != null) {
            return list;
        }
        return getBands();
    }

    private List<ITelephonyApi.IBand.WcdmaBand> getBands() {
        try {
            List<ITelephonyApi.IBand.WcdmaBand> selectedBands = this.teleApi.band().getWcdmaBand(this.mPhoneID);
            return selectedBands;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    protected boolean getSelectedBand() {
        List<ITelephonyApi.IBand.WcdmaBand> bands = getBands();
        if (bands == null) {
            return false;
        }
        List<ITelephonyApi.IBand.WcdmaBand> list = this.supportedBands;
        if (list != null && list.isEmpty()) {
            this.supportedBands.addAll(bands);
            saveBands(bands);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (ITelephonyApi.IBand.WcdmaBand wcdmaBand : this.supportedBands) {
            arrayList.add(wcdmaBand.name());
            arrayList2.add("w" + wcdmaBand.name());
            arrayList3.add(Integer.valueOf(bands.contains(wcdmaBand) ? 1 : 0));
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
    }

    private void saveBands(List<ITelephonyApi.IBand.WcdmaBand> bandList) {
        if (bandList.isEmpty()) {
            return;
        }
        StringBuffer saveValue = new StringBuffer();
        for (ITelephonyApi.IBand.WcdmaBand band : bandList) {
            if (!saveValue.toString().equals("")) {
                saveValue.append(",");
            }
            saveValue.append(band.name());
        }
        Log.d(TAG, "save wcdma band: " + saveValue.toString());
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ORI_WCDMA_BAND, saveValue.toString());
        editor.apply();
    }

    private List<ITelephonyApi.IBand.WcdmaBand> loadBands() {
        List<ITelephonyApi.IBand.WcdmaBand> bandList = new ArrayList<>();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        String saveValue = sp.getString(ORI_WCDMA_BAND, "");
        if (saveValue.equals("")) {
            return bandList;
        }
        String[] bands = saveValue.split(",");
        for (String band : bands) {
            Log.d(TAG, "load band: " + band);
            bandList.add(ITelephonyApi.IBand.WcdmaBand.valueOf(band));
        }
        return bandList;
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    public boolean setSelectedBandToModem() {
        List<ITelephonyApi.IBand.WcdmaBand> enabledBandList = new ArrayList<>();
        List<ITelephonyApi.IBand.WcdmaBand> disabledBandList = new ArrayList<>();
        for (int i = 0; i < this.supportedBands.size(); i++) {
            if (this.mSelecteds[i] == 1) {
                enabledBandList.add(this.supportedBands.get(i));
            } else {
                disabledBandList.add(this.supportedBands.get(i));
            }
        }
        Log.d(TAG, "setSelectedBandToModem");
        try {
            this.teleApi.band().setWcdmaBand(this.mPhoneID, enabledBandList, disabledBandList);
            this.mSetSuccessful = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.mSetSuccessful = false;
            return false;
        }
    }
}
