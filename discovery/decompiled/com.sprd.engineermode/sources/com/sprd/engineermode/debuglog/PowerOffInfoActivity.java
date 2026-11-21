package com.sprd.engineermode.debuglog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.Toast;
import com.sprd.engineermode.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PowerOffInfoActivity extends SwitchBaseActivity {
    private static final String TAG = "PowerOffInfoActivity";
    private SwitchMachineAdapter mAdapter;
    private Handler mThirdHandler;
    private static List<String> mTimeArray = new ArrayList();
    private static List<String> mShutdownArray = new ArrayList();

    @Override // com.sprd.engineermode.debuglog.SwitchBaseActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mThirdHandler = new Handler();
        mPowerOffPref = getSharedPreferences("power_off", 0);
        mPowerOffCount = mPowerOffPref.getLong("info_count", 0L);
        mEditor = mPowerOffPref.edit();
        if (mTimeArray.size() > 0) {
            mTimeArray.clear();
            mShutdownArray.clear();
        }
        for (int i = 1; i <= mPowerOffCount; i++) {
            String key = "info_" + Integer.toString(i);
            String keyTime = key + "_time";
            String keyMode = key + "_mode";
            Log.d(TAG, "i = " + i + " keyTime = " + keyTime + " keyMode = " + keyMode);
            StringBuilder sb = new StringBuilder();
            sb.append("mPowerOffPref.getString(keyTime, null) = ");
            sb.append(mPowerOffPref.getString(keyTime, null));
            Log.d(TAG, sb.toString());
            Log.d(TAG, "mPowerOffPref.getString(keyMode, null) = " + mPowerOffPref.getString(keyMode, null));
            mTimeArray.add(mPowerOffPref.getString(keyTime, null));
            mShutdownArray.add(mPowerOffPref.getString(keyMode, null));
        }
    }

    @Override // com.sprd.engineermode.debuglog.SwitchBaseActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "mPowerOffCount = " + mPowerOffCount);
        if (this.mAdapter == null) {
            Log.d(TAG, "mAdapter == null");
            this.mAdapter = new SwitchMachineAdapter(this, mTimeArray, mShutdownArray, 2);
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
            this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.sprd.engineermode.debuglog.PowerOffInfoActivity.1
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
        Log.i(TAG, "onOptionsItemSelected");
        int itemId = item.getItemId();
        if (itemId == R.id.clear_info) {
            Log.i(TAG, "click clear_info");
            if (mPowerOffCount == 0) {
                Toast.makeText(this, R.string.no_data_toast, 0).show();
                return false;
            }
            doReset();
            Toast.makeText(this, R.string.clear_success_toast, 0).show();
            return true;
        }
        if (itemId != R.id.dump_info) {
            Log.i(TAG, "default");
            return super.onOptionsItemSelected(item);
        }
        Log.i(TAG, "dump_info");
        checkSDCard();
        if (mPowerOffCount == 0) {
            Toast.makeText(this, R.string.no_data_toast, 0).show();
        } else if (!this.mIsMounted) {
            Toast.makeText(this, R.string.no_sd_toast, 0).show();
        } else {
            this.mThirdHandler.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.PowerOffInfoActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    PowerOffInfoActivity.this.saveToSd(SwitchBaseActivity.POWER_OFF_PATH, SwitchBaseActivity.POWER_OFF_NAME);
                }
            });
        }
        return true;
    }

    private void doReset() {
        mPowerOffCount = 0L;
        mTimeArray.clear();
        mShutdownArray.clear();
        SwitchMachineAdapter switchMachineAdapter = this.mAdapter;
        if (switchMachineAdapter != null) {
            switchMachineAdapter.notifyDataSetChanged();
        }
        mEditor.clear();
        mEditor.apply();
        invalidateOptionsMenu();
    }

    public static class ShutDownReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            SwitchBaseActivity.mPowerOffPref = context.getSharedPreferences("power_off", 0);
            SwitchBaseActivity.mPowerOffCount = SwitchBaseActivity.mPowerOffPref.getLong("info_count", 0L);
            SwitchBaseActivity.mPowerOffCount++;
            SwitchBaseActivity.mEditor = SwitchBaseActivity.mPowerOffPref.edit();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
            String date = sDateFormat.format(new Date());
            String shutDownMode = intent.getStringExtra("shutdown_mode");
            Log.d(PowerOffInfoActivity.TAG, "date = " + date + " shutDownMode = " + shutDownMode + " mPowerOffCount = " + SwitchBaseActivity.mPowerOffCount);
            SwitchBaseActivity.mEditor.putLong("info_count", SwitchBaseActivity.mPowerOffCount);
            String key = "info_" + Long.toString(SwitchBaseActivity.mPowerOffCount);
            String keyTime = key + "_time";
            String keyMode = key + "_mode";
            SwitchBaseActivity.mEditor.putString(keyTime, date);
            SwitchBaseActivity.mEditor.putString(keyMode, shutDownMode);
            SwitchBaseActivity.mEditor.apply();
            if (shutDownMode != null && shutDownMode.contains("no_power")) {
                SwitchBaseActivity.mBatteryLifeTime = context.getSharedPreferences("battery_life_time", 0);
                boolean isFirst = SwitchBaseActivity.mBatteryLifeTime.getBoolean("first_shutdown", true);
                Log.d(PowerOffInfoActivity.TAG, "isFirst = " + isFirst);
                if (isFirst) {
                    SharedPreferences.Editor editor = SwitchBaseActivity.mBatteryLifeTime.edit();
                    editor.putString("shut_down_info", date);
                    editor.putBoolean("first_shutdown", false);
                    editor.apply();
                }
            }
        }
    }

    @Override // com.sprd.engineermode.debuglog.SwitchBaseActivity, android.app.Activity
    public void finish() {
        super.finish();
    }
}
