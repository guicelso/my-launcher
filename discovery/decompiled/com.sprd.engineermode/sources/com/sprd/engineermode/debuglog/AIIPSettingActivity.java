package com.sprd.engineermode.debuglog;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.IDebugLogApi;

/* JADX INFO: loaded from: classes4.dex */
public class AIIPSettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String KEY_AI_IP = "ai_ip_key";
    private static final String KEY_COMPILATION_LOG = "compilation_log_key";
    private static final String KEY_CPUEXEC_LOG = "cpuexec_log_key";
    private static final String KEY_DRIVER_LOG = "driver_log_key";
    private static final String KEY_EXECUTION_LOG = "execution_log_key";
    private static final String KEY_MANAGER_LOG = "manager_log_key";
    private static final String KEY_MODEL_LOG = "model_log_key";
    private static final String NN_LOG_COMPILATION = "compilation";
    private static final String NN_LOG_CPUEXEC = "cpuexec";
    private static final String NN_LOG_DRIVER = "driver";
    private static final String NN_LOG_EXECUTION = "execution";
    private static final String NN_LOG_MANAGER = "manager";
    private static final String NN_LOG_MODEL = "model";
    private static final String TAG = "AIIPSettingActivity";
    private ListPreference mAIIPLog;
    private EMSwitchPreference mCompilationlLog;
    private EMSwitchPreference mCpuexecLog;
    private EMSwitchPreference mDriverLog;
    private EMSwitchPreference mExecutionlLog;
    private EMSwitchPreference mManagerLog;
    private EMSwitchPreference mModelLog;
    private IDebugLogApi debuglogApi = CoreApi.getDebugLogApi();
    private String NN_LOG = "model compilation execution cpuexec manager driver";

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_ai_ip);
        this.mModelLog = (EMSwitchPreference) findPreference(KEY_MODEL_LOG);
        this.mModelLog.setOnPreferenceChangeListener(this);
        this.mCompilationlLog = (EMSwitchPreference) findPreference(KEY_COMPILATION_LOG);
        this.mCompilationlLog.setOnPreferenceChangeListener(this);
        this.mExecutionlLog = (EMSwitchPreference) findPreference(KEY_EXECUTION_LOG);
        this.mExecutionlLog.setOnPreferenceChangeListener(this);
        this.mCpuexecLog = (EMSwitchPreference) findPreference(KEY_CPUEXEC_LOG);
        this.mCpuexecLog.setOnPreferenceChangeListener(this);
        this.mManagerLog = (EMSwitchPreference) findPreference(KEY_MANAGER_LOG);
        this.mManagerLog.setOnPreferenceChangeListener(this);
        this.mDriverLog = (EMSwitchPreference) findPreference(KEY_DRIVER_LOG);
        this.mDriverLog.setOnPreferenceChangeListener(this);
        this.mAIIPLog = (ListPreference) findPreference(KEY_AI_IP);
        this.mAIIPLog.setOnPreferenceChangeListener(this);
        initUI();
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        refreshUI();
    }

    private void initUI() {
        this.mModelLog.setChecked(false);
        this.mCompilationlLog.setChecked(false);
        this.mExecutionlLog.setChecked(false);
        this.mCpuexecLog.setChecked(false);
        this.mManagerLog.setChecked(false);
        this.mDriverLog.setChecked(false);
    }

    private void refreshUI() {
        String mAIStatus = this.debuglogApi.aiIpApi().getAiIpStatus();
        if (mAIStatus.equals("1")) {
            this.mAIIPLog.setValueIndex(1);
            ListPreference listPreference = this.mAIIPLog;
            listPreference.setSummary(listPreference.getEntry());
        } else if (mAIStatus.equals("2")) {
            this.mAIIPLog.setValueIndex(2);
            ListPreference listPreference2 = this.mAIIPLog;
            listPreference2.setSummary(listPreference2.getEntry());
        } else if (mAIStatus.equals("0")) {
            this.mAIIPLog.setValueIndex(0);
            ListPreference listPreference3 = this.mAIIPLog;
            listPreference3.setSummary(listPreference3.getEntry());
        } else {
            this.mAIIPLog.setValueIndex(0);
            this.mAIIPLog.setSummary(R.string.feature_not_support);
            this.mAIIPLog.setEnabled(false);
        }
        String mVlogStatus = this.debuglogApi.aiIpApi().getAiDebugVLog();
        if (mVlogStatus.trim().equals("1")) {
            this.mModelLog.setChecked(true);
            this.mCompilationlLog.setChecked(true);
            this.mExecutionlLog.setChecked(true);
            this.mCpuexecLog.setChecked(true);
            this.mManagerLog.setChecked(true);
            this.mDriverLog.setChecked(true);
            return;
        }
        if (mVlogStatus.contains(NN_LOG_MODEL)) {
            this.mModelLog.setChecked(true);
        }
        if (mVlogStatus.contains(NN_LOG_COMPILATION)) {
            this.mCompilationlLog.setChecked(true);
        }
        if (mVlogStatus.contains(NN_LOG_EXECUTION)) {
            this.mExecutionlLog.setChecked(true);
        }
        if (mVlogStatus.contains(NN_LOG_CPUEXEC)) {
            this.mCpuexecLog.setChecked(true);
        }
        if (mVlogStatus.contains(NN_LOG_MANAGER)) {
            this.mManagerLog.setChecked(true);
        }
        if (mVlogStatus.contains(NN_LOG_DRIVER)) {
            this.mDriverLog.setChecked(true);
        }
    }

    public String deleteSubString(String oriStr, String dleStr) {
        StringBuffer sb = new StringBuffer(oriStr);
        int delCount = 0;
        String mNewStr = null;
        while (true) {
            int index = sb.indexOf(dleStr);
            if (index == -1) {
                break;
            }
            sb.delete(index, dleStr.length() + index);
            delCount++;
        }
        if (delCount != 0) {
            mNewStr = sb.toString();
        }
        Log.d(TAG, "deleteSubString mNewStr: " + mNewStr + " oriStr: " + oriStr);
        return mNewStr;
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        Log.d(TAG, "onPreferenceChange key: " + key + " newValue: " + String.valueOf(newValue));
        if (KEY_AI_IP.equals(key)) {
            this.debuglogApi.aiIpApi().getAiIpStatus();
            if (String.valueOf(newValue).equals("1")) {
                this.debuglogApi.aiIpApi().setAIImaginationNNA();
            } else if (String.valueOf(newValue).equals("2")) {
                this.debuglogApi.aiIpApi().setAICambriconNPU();
            } else {
                this.debuglogApi.aiIpApi().setAINone();
            }
            this.mAIIPLog.setValueIndex(Integer.parseInt(String.valueOf(newValue)));
            ListPreference listPreference = this.mAIIPLog;
            listPreference.setSummary(listPreference.getEntry());
        } else if (KEY_CPUEXEC_LOG.equals(key)) {
            String mVlogStatus = this.debuglogApi.aiIpApi().getAiDebugVLog();
            if (mVlogStatus.contains(NN_LOG_CPUEXEC) || mVlogStatus.trim().equals("1")) {
                String delLogCpuExecStr = deleteSubString(mVlogStatus.equals("1") ? this.NN_LOG : mVlogStatus, NN_LOG_CPUEXEC);
                this.debuglogApi.aiIpApi().setAiDebugVLog(delLogCpuExecStr);
                this.mCpuexecLog.setChecked(false);
            } else {
                this.debuglogApi.aiIpApi().setAiDebugVLog(mVlogStatus + " " + NN_LOG_CPUEXEC);
                this.mCpuexecLog.setChecked(true);
            }
        } else if (KEY_MODEL_LOG.equals(key)) {
            String mVlogStatus2 = this.debuglogApi.aiIpApi().getAiDebugVLog();
            if (mVlogStatus2.contains(NN_LOG_MODEL) || mVlogStatus2.trim().equals("1")) {
                String delLogModelStr = deleteSubString(mVlogStatus2.equals("1") ? this.NN_LOG : mVlogStatus2, NN_LOG_MODEL);
                this.debuglogApi.aiIpApi().setAiDebugVLog(delLogModelStr);
                this.mModelLog.setChecked(false);
            } else {
                this.debuglogApi.aiIpApi().setAiDebugVLog(mVlogStatus2 + " " + NN_LOG_MODEL);
                this.mModelLog.setChecked(true);
            }
        } else if (KEY_COMPILATION_LOG.equals(key)) {
            String mVlogStatus3 = this.debuglogApi.aiIpApi().getAiDebugVLog();
            if (mVlogStatus3.contains(NN_LOG_COMPILATION) || mVlogStatus3.trim().equals("1")) {
                String delLogCompStr = deleteSubString(mVlogStatus3.equals("1") ? this.NN_LOG : mVlogStatus3, NN_LOG_COMPILATION);
                this.debuglogApi.aiIpApi().setAiDebugVLog(delLogCompStr);
                this.mCompilationlLog.setChecked(false);
            } else {
                this.debuglogApi.aiIpApi().setAiDebugVLog(mVlogStatus3 + " " + NN_LOG_COMPILATION);
                this.mCompilationlLog.setChecked(true);
            }
        } else if (KEY_EXECUTION_LOG.equals(key)) {
            String mVlogStatus4 = this.debuglogApi.aiIpApi().getAiDebugVLog();
            if (mVlogStatus4.contains(NN_LOG_EXECUTION) || mVlogStatus4.trim().equals("1")) {
                String delLogExecutionStr = deleteSubString(mVlogStatus4.equals("1") ? this.NN_LOG : mVlogStatus4, NN_LOG_EXECUTION);
                this.debuglogApi.aiIpApi().setAiDebugVLog(delLogExecutionStr);
                this.mExecutionlLog.setChecked(false);
            } else {
                this.debuglogApi.aiIpApi().setAiDebugVLog(mVlogStatus4 + " " + NN_LOG_EXECUTION);
                this.mExecutionlLog.setChecked(true);
            }
        } else if (KEY_MANAGER_LOG.equals(key)) {
            String mVlogStatus5 = this.debuglogApi.aiIpApi().getAiDebugVLog();
            if (mVlogStatus5.contains(NN_LOG_MANAGER) || mVlogStatus5.trim().equals("1")) {
                String delLogManagerStr = deleteSubString(mVlogStatus5.equals("1") ? this.NN_LOG : mVlogStatus5, NN_LOG_MANAGER);
                this.debuglogApi.aiIpApi().setAiDebugVLog(delLogManagerStr);
                this.mManagerLog.setChecked(false);
            } else {
                this.debuglogApi.aiIpApi().setAiDebugVLog(mVlogStatus5 + " " + NN_LOG_MANAGER);
                this.mManagerLog.setChecked(true);
            }
        } else if (KEY_DRIVER_LOG.equals(key)) {
            String mVlogStatus6 = this.debuglogApi.aiIpApi().getAiDebugVLog();
            if (mVlogStatus6.contains(NN_LOG_DRIVER) || mVlogStatus6.trim().equals("1")) {
                String delLogDriverStr = deleteSubString(mVlogStatus6.equals("1") ? this.NN_LOG : mVlogStatus6, NN_LOG_DRIVER);
                this.debuglogApi.aiIpApi().setAiDebugVLog(delLogDriverStr);
                this.mDriverLog.setChecked(false);
            } else {
                this.debuglogApi.aiIpApi().setAiDebugVLog(mVlogStatus6 + " " + NN_LOG_DRIVER);
                this.mDriverLog.setChecked(true);
            }
        }
        return true;
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }
}
