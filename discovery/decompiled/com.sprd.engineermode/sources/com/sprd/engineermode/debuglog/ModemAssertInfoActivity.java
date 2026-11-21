package com.sprd.engineermode.debuglog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ModemAssertInfoActivity extends SwitchBaseActivity {
    private static final String MODEM_ASSERT = "modem_assert";
    private static final String MODEM_INFO = "modem_info";
    private static final String MODEM_STAT = "modem_stat";
    private static final String TAG = "ModemAssertInfoActivity";
    private SwitchMachineAdapter mAdapter;
    private static List<String> mTimeArray = new ArrayList();
    private static List<String> mInfoArray = new ArrayList();

    @Override // com.sprd.engineermode.debuglog.SwitchBaseActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModemAssertPref = getSharedPreferences("modem_assert", 0);
        mModemAssertCount = mModemAssertPref.getLong("info_count", 0L);
        mEditor = mModemAssertPref.edit();
        if (mTimeArray.size() > 0) {
            mTimeArray.clear();
            mInfoArray.clear();
        }
        getModemAssertData();
    }

    private void getModemAssertData() {
        try {
            File file = new File(MODEM_ASSERT_FILE);
            BufferedReader input = new BufferedReader(new FileReader(file));
            while (true) {
                String data = input.readLine();
                if (data != null) {
                    if (!"".equals(data)) {
                        String[] str = data.split("   ");
                        mTimeArray.add(str[0]);
                        mInfoArray.add(str[1]);
                    }
                } else {
                    input.close();
                    return;
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override // com.sprd.engineermode.debuglog.SwitchBaseActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "mModemAssertCount = " + mModemAssertCount);
        if (this.mAdapter == null) {
            Log.d(TAG, "mAdapter == null");
            this.mAdapter = new SwitchMachineAdapter(this, mTimeArray, mInfoArray, 3);
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
            this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.sprd.engineermode.debuglog.ModemAssertInfoActivity.1
                @Override // android.widget.AbsListView.OnScrollListener
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override // android.widget.AbsListView.OnScrollListener
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                }
            });
        }
        this.mAdapter.notifyDataSetChanged();
        this.mListView.setSelection(0);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.clear_info) {
            Log.i(TAG, "click clear_info");
            if (mModemAssertCount == 0) {
                Toast.makeText(this, R.string.no_data_toast, 0).show();
                return false;
            }
            doReset();
            Toast.makeText(this, R.string.clear_success_toast, 0).show();
            return true;
        }
        if (itemId == R.id.dump_info) {
            Log.i(TAG, "dump_info");
            checkSDCard();
            if (mModemAssertCount == 0) {
                Toast.makeText(this, R.string.no_data_toast, 0).show();
            } else if (!this.mIsMounted) {
                Toast.makeText(this, R.string.no_sd_toast, 0).show();
            } else {
                saveToSd(MODEM_ASSERT_FILE, SwitchBaseActivity.MODEM_ASSERT_NAME);
            }
            return true;
        }
        Log.i(TAG, "default");
        return super.onOptionsItemSelected(item);
    }

    private void doReset() {
        mModemAssertCount = 0L;
        mTimeArray.clear();
        mInfoArray.clear();
        SwitchMachineAdapter switchMachineAdapter = this.mAdapter;
        if (switchMachineAdapter != null) {
            switchMachineAdapter.notifyDataSetChanged();
        }
        mEditor.clear();
        mEditor.apply();
        clearInfo();
        invalidateOptionsMenu();
    }

    public static class ModemAssertReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.d(ModemAssertInfoActivity.TAG, "modem assert happen");
            String modemAsserStat = intent.getStringExtra(ModemAssertInfoActivity.MODEM_STAT);
            if ("modem_assert".equals(modemAsserStat)) {
                SwitchBaseActivity.mModemAssertPref = context.getSharedPreferences("modem_assert", 0);
                SwitchBaseActivity.mModemAssertCount = SwitchBaseActivity.mModemAssertPref.getLong("info_count", 0L);
                SwitchBaseActivity.mModemAssertCount++;
                SwitchBaseActivity.mEditor = SwitchBaseActivity.mModemAssertPref.edit();
                SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
                String date = sDateFormat.format(new Date());
                String modemAsserInfo = intent.getStringExtra(ModemAssertInfoActivity.MODEM_INFO);
                Log.d(ModemAssertInfoActivity.TAG, "date = " + date + " modemAsserInfo = " + modemAsserInfo + " mModemAssertCount = " + SwitchBaseActivity.mModemAssertCount);
                SwitchBaseActivity.mEditor.putLong("info_count", SwitchBaseActivity.mModemAssertCount);
                SwitchBaseActivity.mEditor.commit();
                StringBuilder sb = new StringBuilder();
                sb.append(date);
                sb.append("   ");
                sb.append(modemAsserInfo);
                String modemData = sb.toString();
                SwitchBaseActivity.saveToInternal(modemData);
                if (SystemPropertiesProxy.getBoolean("persist.sys.modemassertdump", false)) {
                    context.sendBroadcast(new Intent("com.sprd.engineermode.MODEM_ASSERT_DUMP"));
                }
            }
        }
    }

    @Override // com.sprd.engineermode.debuglog.SwitchBaseActivity, android.app.Activity
    public void finish() {
        super.finish();
    }
}
