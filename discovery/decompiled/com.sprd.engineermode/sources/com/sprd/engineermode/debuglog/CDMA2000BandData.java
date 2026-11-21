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
public class CDMA2000BandData extends BandData {
    private static final String ORI_CDMA_BAND = "ORI_CDMA_BAND";
    private static final String TAG = "CDMA2000BandData";
    private List<ITelephonyApi.IBand.CDMA2000Band> supportedBands;
    private ITelephonyApi teleApi;

    public CDMA2000BandData(Context context, int phoneID) {
        super(context, phoneID);
        this.supportedBands = null;
        this.teleApi = CoreApi.getTelephonyApi();
    }

    public CDMA2000BandData(Context context, int phoneID, PreferenceCategory pref) {
        super(context, phoneID, pref);
        this.supportedBands = null;
        this.teleApi = CoreApi.getTelephonyApi();
        this.supportedBands = loadBands();
    }

    public List<ITelephonyApi.IBand.CDMA2000Band> getSupportBands() {
        List<ITelephonyApi.IBand.CDMA2000Band> list = this.supportedBands;
        if (list != null) {
            return list;
        }
        return getBands();
    }

    private List<ITelephonyApi.IBand.CDMA2000Band> getBands() {
        try {
            List<ITelephonyApi.IBand.CDMA2000Band> selectedBands = this.teleApi.band().getCDMA2000Band(this.mPhoneID);
            return selectedBands;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    protected boolean getSelectedBand() {
        List<ITelephonyApi.IBand.CDMA2000Band> bands = getBands();
        if (bands == null) {
            return false;
        }
        List<ITelephonyApi.IBand.CDMA2000Band> list = this.supportedBands;
        if (list != null) {
            if (list.isEmpty()) {
                this.supportedBands.addAll(bands);
                saveBands(bands);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (ITelephonyApi.IBand.CDMA2000Band cDMA2000Band : this.supportedBands) {
                arrayList.add(cDMA2000Band.name());
                arrayList2.add("w" + cDMA2000Band.name());
                arrayList3.add(Integer.valueOf(bands.contains(cDMA2000Band) ? 1 : 0));
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
        return true;
    }

    private void saveBands(List<ITelephonyApi.IBand.CDMA2000Band> bandList) {
        if (bandList.isEmpty()) {
            return;
        }
        String saveValue = "";
        for (ITelephonyApi.IBand.CDMA2000Band band : bandList) {
            if (!saveValue.equals("")) {
                saveValue = saveValue + ",";
            }
            saveValue = saveValue + band.name();
        }
        Log.d(TAG, "save wcdma band: " + saveValue);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ORI_CDMA_BAND, saveValue);
        editor.apply();
    }

    private List<ITelephonyApi.IBand.CDMA2000Band> loadBands() {
        List<ITelephonyApi.IBand.CDMA2000Band> bandList = new ArrayList<>();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        String saveValue = sp.getString(ORI_CDMA_BAND, "");
        if (saveValue.equals("")) {
            return bandList;
        }
        String[] bands = saveValue.split(",");
        for (String band : bands) {
            Log.d(TAG, "load band: " + band);
            bandList.add(ITelephonyApi.IBand.CDMA2000Band.valueOf(band));
        }
        return bandList;
    }

    @Override // com.sprd.engineermode.debuglog.BandData
    public boolean setSelectedBandToModem() {
        List<ITelephonyApi.IBand.CDMA2000Band> enabledBandList = new ArrayList<>();
        List<ITelephonyApi.IBand.CDMA2000Band> disabledBandList = new ArrayList<>();
        for (int i = 0; i < this.supportedBands.size(); i++) {
            if (this.mSelecteds[i] == 1) {
                enabledBandList.add(this.supportedBands.get(i));
            } else {
                disabledBandList.add(this.supportedBands.get(i));
            }
        }
        Log.d(TAG, "setSelectedBandToModem");
        try {
            this.teleApi.band().setCDMA2000Band(this.mPhoneID, enabledBandList, disabledBandList);
            this.mSetSuccessful = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.mSetSuccessful = false;
            return false;
        }
    }
}
