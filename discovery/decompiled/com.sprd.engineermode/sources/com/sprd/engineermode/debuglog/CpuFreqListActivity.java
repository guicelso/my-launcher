package com.sprd.engineermode.debuglog;

import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.utils.ShellUtils;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class CpuFreqListActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private static final String CPU_CLUSTER_PATH = "/sys/devices/system/cpu/cpufreq/";
    private static final String CPU_POLICY = "cpu_policy";
    private static final String KEY_SET_FREQ = "set_freq";
    private static final String TAG = "CpuFreqListActivity";
    private String mAvaliableFreq;
    private String[] mCpuFreq;
    private ArrayAdapter<String> mCpuFreqAdapter;
    private ListView mCpuFreqList;
    private String[] mCpuFreqSet;
    private String mPolicy;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws Exception {
        super.onCreate(savedInstanceState);
        this.mPolicy = getIntent().getStringExtra(CPU_POLICY);
        Log.d(TAG, "mPolicy: " + this.mPolicy);
        if (this.mPolicy != null) {
            this.mAvaliableFreq = getAvaliableFreq();
            this.mCpuFreqSet = this.mAvaliableFreq.trim().split(" ");
            this.mCpuFreq = changeUnit();
            this.mCpuFreqAdapter = new ArrayAdapter<>(this, R.layout.simple_list_item_single_choice, this.mCpuFreq);
            this.mCpuFreqList = getListView();
            this.mCpuFreqList.setAdapter((ListAdapter) this.mCpuFreqAdapter);
            this.mCpuFreqList.setOnItemClickListener(this);
            this.mCpuFreqList.setChoiceMode(1);
            initCpuFreq();
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) throws IllegalAccessException, InvocationTargetException {
        String curFreq = this.mCpuFreqSet[position];
        SystemPropertiesProxy.set("persist.sys.thermal.ipa", "0");
        setFreq(curFreq);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SET_FREQ, (Integer.valueOf(curFreq).intValue() / 1000) + "mHz");
        bundle.putString(CPU_POLICY, this.mPolicy);
        intent.putExtras(bundle);
        setResult(-1, intent);
        finish();
    }

    private String getAvaliableFreq() {
        return ShellUtils.execShellCmd("cat /sys/devices/system/cpu/cpufreq/" + this.mPolicy + "/scaling_available_frequencies");
    }

    private String[] changeUnit() {
        this.mCpuFreq = new String[this.mCpuFreqSet.length];
        for (int i = 0; i < this.mCpuFreqSet.length; i++) {
            Log.d(TAG, "mCpuFreqSet[i]: " + this.mCpuFreqSet[i]);
            this.mCpuFreq[i] = (Integer.valueOf(this.mCpuFreqSet[i]).intValue() / 1000) + "mHz";
        }
        return this.mCpuFreq;
    }

    private void initCpuFreq() throws Exception {
        String curFreq = ShellUtils.execShellCmd("cat /sys/devices/system/cpu/cpufreq/" + this.mPolicy + "/scaling_cur_freq");
        StringBuilder sb = new StringBuilder();
        sb.append("initCpuFreq000: ");
        sb.append(curFreq);
        Log.d(TAG, sb.toString());
        if (!"".equals(curFreq)) {
            for (int i = 0; i < this.mCpuFreqSet.length; i++) {
                if (curFreq.trim().equals(this.mCpuFreqSet[i])) {
                    this.mCpuFreqList.setItemChecked(i, true);
                }
            }
        }
    }

    private void setFreq(String freq) {
        Log.d(TAG, "setFreq=" + freq);
        ShellUtils.writeToFile(CPU_CLUSTER_PATH + this.mPolicy + "/scaling_setspeed", freq);
    }
}
