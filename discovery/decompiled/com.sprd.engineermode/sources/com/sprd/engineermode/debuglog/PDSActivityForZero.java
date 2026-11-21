package com.sprd.engineermode.debuglog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.sprd.engineermode.utils.EMFileUtils;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.utils.ShellUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class PDSActivityForZero extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static final String CPU_CLUSTER_PATH = "/sys/devices/system/cpu/cpufreq/";
    private static final String CPU_POLICY = "cpu_policy";
    private static final String DVFS_STATUS_PATH = "/sys/devices/system/cpu/cpufreq/policy0/";
    private static final String KEY_CPU_FREQUENCE = "cpuFrequence";
    private static final String KEY_CPU_FRE_SWITCH = "cpuFrequenceSwitch";
    private static final String KEY_DVFS = "dvfs";
    private static final String KEY_MAX_CPU_FREQUENCE = "cpu_frq_max";
    private static final String KEY_SET_FREQ = "set_freq";
    private static final String TAG = "PDSActivityForZero";
    private static final String USER_SPACE_PATH = "/sys/devices/system/cpu/cpufreq/policy0/scaling_available_governors";
    private TwoStatePreference mCPUFreSwitch;
    private PreferenceScreen mCPUPre;
    private Preference[] mCpuFreSelect;
    private Preference mDVFSPre;
    private ArrayList<String> mOriginalGovernor = new ArrayList<>();
    private ArrayList<String> mDirs = new ArrayList<>();
    private ArrayList<String> mPolicyName = new ArrayList<>();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_power_dissipation1);
        this.mDVFSPre = findPreference(KEY_DVFS);
        this.mCPUFreSwitch = (TwoStatePreference) findPreference(KEY_CPU_FRE_SWITCH);
        this.mCPUFreSwitch.setOnPreferenceChangeListener(this);
        this.mCPUPre = (PreferenceScreen) findPreference(KEY_CPU_FREQUENCE);
        this.mCPUPre.setOnPreferenceClickListener(this);
        if (isDvfsEnabled()) {
            this.mDirs = EMFileUtils.getDirectoryName(CPU_CLUSTER_PATH);
            ArrayList<String> arrayList = this.mDirs;
            if (arrayList != null) {
                for (String dir : arrayList) {
                    if (dir.contains("policy")) {
                        this.mPolicyName.add(dir);
                    }
                }
                changeOriginalGovernor();
                initCpuPolicys();
            }
            this.mDVFSPre.setSummary(R.string.status_opened);
            this.mCPUPre.setEnabled(true);
            this.mCPUFreSwitch.setEnabled(true);
            return;
        }
        this.mDVFSPre.setSummary(R.string.status_closed);
        this.mCPUPre.setEnabled(false);
        this.mCPUFreSwitch.setEnabled(false);
    }

    @Override // android.app.Activity
    protected void onStart() {
        if (SystemPropertiesProxy.get("persist.vendor.power.hint", "1").equals("1")) {
            this.mCPUFreSwitch.setChecked(true);
        } else {
            this.mCPUFreSwitch.setChecked(false);
        }
        super.onStart();
    }

    private void initCpuPolicys() {
        this.mCpuFreSelect = new Preference[this.mPolicyName.size()];
        for (int i = 0; i < this.mPolicyName.size(); i++) {
            String curFreq = ShellUtils.execShellCmd("cat /sys/devices/system/cpu/cpufreq/" + this.mPolicyName.get(i) + "/scaling_cur_freq").trim();
            this.mCpuFreSelect[i] = new Preference(this);
            this.mCpuFreSelect[i].setTitle(this.mPolicyName.get(i));
            this.mCpuFreSelect[i].setKey(this.mPolicyName.get(i));
            this.mCpuFreSelect[i].setSummary((Integer.valueOf(curFreq).intValue() / 1000) + "mHz");
            this.mCPUPre.addPreference(this.mCpuFreSelect[i]);
            this.mCpuFreSelect[i].setOnPreferenceClickListener(this);
        }
    }

    private void changeOriginalGovernor() {
        for (int i = 0; i < this.mPolicyName.size(); i++) {
            String governor = ShellUtils.execShellCmd("cat /sys/devices/system/cpu/cpufreq/" + this.mPolicyName.get(i) + "/scaling_governor").replaceAll("\r|\n", "");
            this.mOriginalGovernor.add(governor);
            ShellUtils.writeToFile(CPU_CLUSTER_PATH + this.mPolicyName.get(i) + "/scaling_governor", "userspace");
        }
    }

    private void restoreOriginalGovernor() throws Exception {
        for (int i = 0; i < this.mOriginalGovernor.size(); i++) {
            ShellUtils.execShellCmd("echo " + this.mOriginalGovernor.get(i) + " > " + CPU_CLUSTER_PATH + this.mPolicyName.get(i) + "/scaling_governor");
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        TwoStatePreference twoStatePreference = this.mCPUFreSwitch;
        if (preference == twoStatePreference) {
            if (twoStatePreference.isChecked()) {
                Log.d(TAG, "mCPUFreSwitch is Checked");
                AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.cpu_fre_hint_title)).setMessage(getString(R.string.cpu_fre_reboot_hint)).setCancelable(false).setPositiveButton(R.string.alertdialog_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.PDSActivityForZero.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) throws IllegalAccessException, InvocationTargetException {
                        SystemPropertiesProxy.set("persist.vendor.power.hint", "0");
                        PDSActivityForZero.this.mCPUFreSwitch.setChecked(false);
                        Log.d(PDSActivityForZero.TAG, "set persist.vendor.power.hint to 0");
                        PowerManager pm = (PowerManager) PDSActivityForZero.this.getSystemService("power");
                        pm.reboot("switch_powerhint");
                    }
                }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.PDSActivityForZero.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
                alertDialog.show();
            } else {
                Log.d(TAG, "mCPUFreSwitch is not Checked");
                AlertDialog alertDialog2 = new AlertDialog.Builder(this).setTitle(getString(R.string.cpu_fre_hint_title)).setMessage(getString(R.string.cpu_fre_reboot_hint)).setCancelable(false).setPositiveButton(R.string.alertdialog_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.PDSActivityForZero.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) throws IllegalAccessException, InvocationTargetException {
                        SystemPropertiesProxy.set("persist.vendor.power.hint", "1");
                        PDSActivityForZero.this.mCPUFreSwitch.setChecked(true);
                        Log.d(PDSActivityForZero.TAG, "set persist.vendor.power.hint to 1");
                        PowerManager pm = (PowerManager) PDSActivityForZero.this.getSystemService("power");
                        pm.reboot("switch_powerhint");
                    }
                }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.PDSActivityForZero.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
                alertDialog2.show();
            }
        }
        return false;
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.cpu_fre_hint_title)).setMessage(getString(R.string.restore_cpu_frequency)).setPositiveButton(R.string.alertdialog_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.PDSActivityForZero.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PDSActivityForZero.this.finish();
            }
        }).create();
        alertDialog.show();
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        String key = pref.getKey();
        Log.d(TAG, "onPreferenceClick: " + key);
        for (int i = 0; i < this.mPolicyName.size(); i++) {
            if (key.equals(this.mCpuFreSelect[i].getKey())) {
                Intent intent = new Intent();
                intent.setClass(this, CpuFreqListActivity.class);
                intent.putExtra(CPU_POLICY, this.mPolicyName.get(i));
                startActivityForResult(intent, 0);
                break;
            }
        }
        if (pref == this.mCPUPre && SystemPropertiesProxy.get("persist.vendor.power.hint", "1").equals("1")) {
            Log.d(TAG, "persist.vendor.power.hint equals 1");
            AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.cpu_fre_hint_title)).setMessage(getString(R.string.cpu_fre_setting_hint)).setPositiveButton(R.string.alertdialog_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.PDSActivityForZero.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
            alertDialog.show();
        }
        return false;
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && requestCode == 0) {
            Log.d(TAG, "set Freq is: " + data.getExtras().getString(KEY_SET_FREQ));
            Log.d(TAG, "set policy is: " + data.getExtras().getString(CPU_POLICY));
            for (int i = 0; i < this.mPolicyName.size(); i++) {
                if (data.getExtras().getString(CPU_POLICY).equals(this.mPolicyName.get(i))) {
                    this.mCpuFreSelect[i].setSummary(data.getExtras().getString(KEY_SET_FREQ));
                }
            }
            Toast.makeText(getApplicationContext(), R.string.dvfs_cpufre_hint, 0).show();
        }
    }

    private boolean isDvfsEnabled() {
        return EMFileUtils.isFileDirExits(DVFS_STATUS_PATH) && ShellUtils.execShellCmd("cat /sys/devices/system/cpu/cpufreq/policy0/scaling_available_governors").contains("userspace");
    }
}
