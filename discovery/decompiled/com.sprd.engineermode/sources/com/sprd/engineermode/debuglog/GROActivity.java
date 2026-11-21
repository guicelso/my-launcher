package com.sprd.engineermode.debuglog;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class GROActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static final int GET_GRO_STATE = 14;
    private static final String KEY_GRO_SWITCH = "key_gro";
    private static final String TAG = "GROActivity";
    private DEGHandler mDEGHandler;
    private EMSwitchPreference mGro;
    private Handler mUiThread;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_apcp_gro);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mDEGHandler = new DEGHandler(ht.getLooper());
        this.mUiThread = new Handler();
        this.mGro = (EMSwitchPreference) findPreference(KEY_GRO_SWITCH);
        this.mGro.setOnPreferenceChangeListener(this);
        Message getGroState = this.mDEGHandler.obtainMessage(14);
        this.mDEGHandler.sendMessage(getGroState);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        this.mGro.setSummary(SystemPropertiesProxy.get("persist.vendor.cam.preview.fps", "0"));
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object objValue) throws IllegalAccessException, InvocationTargetException {
        Log.d(TAG, "pref =" + pref.getKey());
        EMSwitchPreference eMSwitchPreference = this.mGro;
        if (pref != eMSwitchPreference) {
            return true;
        }
        if (!eMSwitchPreference.isChecked()) {
            SystemPropertiesProxy.set("ctl.start", "vendor.gro_on");
            this.mGro.setSummary("1");
        } else {
            SystemPropertiesProxy.set("ctl.start", "vendor.gro_off");
            this.mGro.setSummary("0");
        }
        return true;
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        Log.d(TAG, "key=" + pref.getKey());
        return true;
    }

    public String readFile(String path) {
        File file = new File(path);
        String str = new String("");
        BufferedReader reader = null;
        try {
            try {
                try {
                    reader = new BufferedReader(new FileReader(file));
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        str = str + line;
                    }
                    reader.close();
                } catch (Exception e) {
                    Log.d(TAG, "Read file error!!!");
                    str = "readError";
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Log.d(TAG, "read " + path + " value is " + str.trim());
            return str.trim();
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    class DEGHandler extends Handler {
        public DEGHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 14) {
                String groStatus = GROActivity.this.readFile("/sys/module/seth/parameters/gro_enable");
                Log.d(GROActivity.TAG, "GRO status is " + groStatus);
                if ("readError".equals(groStatus) || groStatus == null) {
                    GROActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GROActivity.DEGHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            GROActivity.this.mGro.setEnabled(false);
                            GROActivity.this.mGro.setSummary(R.string.feature_not_support);
                        }
                    });
                    return;
                }
                if (groStatus.contains("1")) {
                    GROActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GROActivity.DEGHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            GROActivity.this.mGro.setChecked(true);
                            GROActivity.this.mGro.setSummary("1");
                        }
                    });
                } else if (groStatus.contains("0")) {
                    GROActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GROActivity.DEGHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            GROActivity.this.mGro.setChecked(false);
                            GROActivity.this.mGro.setSummary("0");
                        }
                    });
                } else {
                    GROActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GROActivity.DEGHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            GROActivity.this.mGro.setEnabled(false);
                            GROActivity.this.mGro.setSummary(R.string.feature_not_support);
                        }
                    });
                }
            }
        }
    }
}
