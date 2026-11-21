package com.sprd.engineermode.debuglog;

import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.IPhoneInfo;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneInfoActivity extends PreferenceActivity {
    private static final String BASEID_NUMBER_KEY = "baseid_number";
    private static final String CDMA_IMSI_KEY = "cdma_imsi";
    private static final String CELLID_NUMBER_KEY = "cellid_sim";
    private static final String ESN_NUMBER_KEY = "esn_number";
    private static final String HARDWARE_VERSION_KEY = "hardware_number";
    private static final String ICC_ID_KEY = "icc_id";
    private static final String IMEI_NUMBER_KEY = "imei_number";
    private static final String IMSI_NUMBER_KEY = "imsi_sim";
    private static final String MCC_NUMBER_KEY = "mcc_sim";
    private static final String MEID_NUMBER_KEY = "meid_number";
    private static final String MEMORY_VOLUME_KEY = "memory_volume";
    private static final String MNC_NUMBER_KEY = "mnc_sim";
    private static final String NID_NUMBER_KEY = "nid_number";
    private static final String PRL_VERSION_KEY = "prl_version";
    private static final String SID_NUMBER_KEY = "sid_number";
    private static final String SOFTWARE_VERSION_KEY = "software_number";
    private static final String STORAGE_VOLUME_KEY = "storage_volume";
    private static final String SYSTEM_VERSION_KEY = "system_version";
    private static final String TAG = "PhoneInfoActivity";
    private static final String TERMINATOR_VERSION_KEY = "terminator_version";
    private IPhoneInfo phoneInfoApi = CoreApi.getDebugLogApi().phoneInfoApi();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        addPreferencesFromResource(R.xml.pref_phone_information_show);
    }

    private void setMccMnc() {
        int index = 1;
        for (IPhoneInfo.Operator mccMnc : this.phoneInfoApi.getAllOpers()) {
            Preference pref = findPreference(MCC_NUMBER_KEY + index);
            pref.setSummary(mccMnc.getMcc());
            Preference pref2 = findPreference(MNC_NUMBER_KEY + index);
            pref2.setSummary(mccMnc.getMnc());
            index++;
        }
    }

    private void setImsi() {
        int index = 1;
        for (String imsi : this.phoneInfoApi.getAllImsi()) {
            Preference pref = findPreference(IMSI_NUMBER_KEY + index);
            pref.setSummary(imsi);
            index++;
        }
    }

    private void setImei() {
        int index = 1;
        for (String imei : this.phoneInfoApi.getAllImei()) {
            Preference pref = findPreference(IMEI_NUMBER_KEY + index);
            pref.setSummary(imei);
            index++;
        }
    }

    private void setCdmaImsi() {
        int index = 1;
        for (String imei : this.phoneInfoApi.getCdmaImsi()) {
            Preference pref = findPreference(CDMA_IMSI_KEY + index);
            pref.setSummary(imei);
            index++;
        }
    }

    private void setIccId() {
        int index = 1;
        for (String imei : this.phoneInfoApi.getIccId()) {
            Preference pref = findPreference(ICC_ID_KEY + index);
            pref.setSummary(imei);
            index++;
        }
    }

    private void setCellId() {
        int index = 1;
        Iterator<Integer> it = this.phoneInfoApi.getCellId().iterator();
        while (it.hasNext()) {
            int id = it.next().intValue();
            if (id != -1) {
                Preference pref = findPreference(CELLID_NUMBER_KEY + index);
                pref.setSummary(String.valueOf(id));
            }
            index++;
        }
    }

    private void setCdmaInfo() {
        for (IPhoneInfo.CdmaInfo ci : this.phoneInfoApi.getCdmaInfo()) {
            if (ci.getSystemId() != -1) {
                Preference pref = findPreference(PRL_VERSION_KEY);
                pref.setSummary(ci.getPrl());
                Preference pref2 = findPreference(SID_NUMBER_KEY);
                pref2.setSummary(String.valueOf(ci.getSystemId()));
                Preference pref3 = findPreference(NID_NUMBER_KEY);
                pref3.setSummary(String.valueOf(ci.getNetworkId()));
                Preference pref4 = findPreference(BASEID_NUMBER_KEY);
                pref4.setSummary(String.valueOf(ci.getBaseStationId()));
            }
        }
    }

    private void setEsn() {
        Preference pref = findPreference(ESN_NUMBER_KEY);
        pref.setSummary(this.phoneInfoApi.getEsn());
    }

    private void setMeid() {
        Preference pref = findPreference(MEID_NUMBER_KEY);
        pref.setSummary(this.phoneInfoApi.getMeid());
    }

    private void setMemory() {
        Preference pref = findPreference(MEMORY_VOLUME_KEY);
        pref.setSummary(this.phoneInfoApi.getMemorySize());
    }

    private void setStorage() {
        Preference pref = findPreference(STORAGE_VOLUME_KEY);
        pref.setSummary(this.phoneInfoApi.getInternalStorageSize());
    }

    private void setVersion() {
        Preference pref = findPreference(SOFTWARE_VERSION_KEY);
        pref.setSummary(this.phoneInfoApi.getSoftwareVersion());
        Preference pref2 = findPreference(HARDWARE_VERSION_KEY);
        pref2.setSummary(this.phoneInfoApi.getHardwareVersion());
        Preference pref3 = findPreference(SYSTEM_VERSION_KEY);
        pref3.setSummary(this.phoneInfoApi.getOsVersion());
        Preference pref4 = findPreference(TERMINATOR_VERSION_KEY);
        pref4.setSummary(Build.MODEL);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override // android.app.Activity
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        setMccMnc();
        setImsi();
        setCellId();
        setCdmaInfo();
        setEsn();
        setMeid();
        setImei();
        setMemory();
        setStorage();
        setVersion();
        setCdmaImsi();
        setIccId();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
