package com.sprd.engineermode.debuglog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.utils.CmdUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class SystemSettingActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {
    private static final String GPU_FEEQ_AVAILABLE = "available_frequencies";
    private static final String GPU_FEEQ_CURRENT = "cur_freq";
    private static final String GPU_FEEQ_MAX = "max_freq";
    private static final String GPU_FEEQ_MIN = "min_freq";
    private static final String GPU_FEEQ_PATH = "/sys/class/devfreq/60000000.gpu/";
    private static final String KEY_GPU_FREQ = "gpu_freq";
    private static final String TAG = "SystemSettingActivity";
    private String mGPUCurrentFreq = null;
    private Preference mGPUFreq;
    private String[] mGpuFreq;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate...");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_system_settings);
        this.mGPUFreq = findPreference(KEY_GPU_FREQ);
        this.mGPUFreq.setOnPreferenceClickListener(this);
        this.mGPUFreq.setEnabled(isSharkL2Support());
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        if (pref == this.mGPUFreq) {
            popDialog();
            return true;
        }
        return true;
    }

    private void popDialog() {
        this.mGpuFreq = getmGpuFreq();
        this.mGPUCurrentFreq = getmGpuCurrentFreq();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        if (this.mGpuFreq != null && this.mGPUCurrentFreq != null) {
            alertBuilder.setTitle("gpu freq").setSingleChoiceItems(this.mGpuFreq, getCheckedItemPosition(this.mGPUCurrentFreq), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.SystemSettingActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    if (which >= 0) {
                        SystemSettingActivity systemSettingActivity = SystemSettingActivity.this;
                        systemSettingActivity.setGpuCurrent(systemSettingActivity.mGpuFreq[which]);
                        if (!SystemSettingActivity.this.readFile("/sys/class/devfreq/60000000.gpu/max_freq").contains(SystemSettingActivity.this.mGpuFreq[which])) {
                            SystemSettingActivity systemSettingActivity2 = SystemSettingActivity.this;
                            systemSettingActivity2.setGpuMaxFreq(systemSettingActivity2.mGpuFreq[which]);
                        }
                        if (!SystemSettingActivity.this.readFile("/sys/class/devfreq/60000000.gpu/min_freq").contains(SystemSettingActivity.this.mGpuFreq[which])) {
                            SystemSettingActivity systemSettingActivity3 = SystemSettingActivity.this;
                            systemSettingActivity3.setGpuMinFreq(systemSettingActivity3.mGpuFreq[which]);
                        }
                        SystemSettingActivity.this.mGPUFreq.setSummary(SystemSettingActivity.this.getmGpuCurrentFreq());
                    }
                }
            });
        } else {
            alertBuilder.setTitle("gpu freq").setMessage("read error");
        }
        alertBuilder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.SystemSettingActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                SystemSettingActivity.this.mGPUFreq.setSummary(SystemSettingActivity.this.getmGpuCurrentFreq());
            }
        });
        AlertDialog mAlertDialog = alertBuilder.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }

    private int getCheckedItemPosition(String currentFreq) {
        int index = 0;
        for (String str : this.mGpuFreq) {
            if (currentFreq.contains(str)) {
                break;
            }
            index++;
        }
        return index;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGpuCurrent(String current) {
        setGpuMaxFreq(current);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setGpuMinFreq(current);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String setGpuMaxFreq(String current) {
        return CmdUtils.run("echo " + current + ">" + GPU_FEEQ_PATH + GPU_FEEQ_MAX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String setGpuMinFreq(String current) {
        return CmdUtils.run("echo " + current + ">" + GPU_FEEQ_PATH + GPU_FEEQ_MIN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getmGpuCurrentFreq() {
        return readFile("/sys/class/devfreq/60000000.gpu/cur_freq");
    }

    private String[] getmGpuFreq() {
        String available = readFile("/sys/class/devfreq/60000000.gpu/available_frequencies");
        if (available != null) {
            String[] ret = available.split(" ");
            for (int i = 0; i < ret.length; i++) {
                ret[i] = ret[i].trim();
            }
            return ret;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String readFile(String path) {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        String data = null;
        try {
            try {
                try {
                    File file = new File(path);
                    fis = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    bos = new ByteArrayOutputStream();
                    while (true) {
                        int len = fis.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        bos.write(buffer, 0, len);
                    }
                    byte[] dataByte = bos.toByteArray();
                    data = new String(dataByte, 0, dataByte.length);
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fis.close();
                } catch (Throwable th) {
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (fis == null) {
                        throw th;
                    }
                    try {
                        fis.close();
                        throw th;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        throw th;
                    }
                }
            } catch (IOException e4) {
                e4.printStackTrace();
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (fis != null) {
                    fis.close();
                }
                return data;
            }
        } catch (IOException e6) {
            e6.printStackTrace();
        }
        return data;
    }

    public boolean isSharkL2Support() throws IllegalAccessException, InvocationTargetException {
        String hardware = SystemPropertiesProxy.get("ro.boot.hardware", EnvironmentCompat.MEDIA_UNKNOWN);
        if (hardware.contains("9850")) {
            return true;
        }
        return false;
    }
}
