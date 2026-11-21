package com.sprd.engineermode.debuglog;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.Toast;
import com.sprd.engineermode.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PowerOnInfoActivity extends SwitchBaseActivity {
    private static final String TAG = "PowerOnInfoActivity";
    private SwitchMachineAdapter mAdapter;
    private Handler mThirdHandler;
    private static List<String> mTimeArray = new ArrayList();
    private static List<String> mBootModeArray = new ArrayList();

    @Override // com.sprd.engineermode.debuglog.SwitchBaseActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mThirdHandler = new Handler();
        mPowerOnPref = getSharedPreferences("power_on", 0);
        mPowerOnCount = mPowerOnPref.getLong("info_count", 0L);
        mEditor = mPowerOnPref.edit();
        if (mTimeArray.size() > 0) {
            mTimeArray.clear();
            mBootModeArray.clear();
        }
        for (int i = 1; i <= mPowerOnCount; i++) {
            String key = "info_" + Integer.toString(i);
            String keyTime = key + "_time";
            String keyMode = key + "_mode";
            Log.d(TAG, "i = " + i + " keyTime = " + keyTime + " keyMode = " + keyMode);
            StringBuilder sb = new StringBuilder();
            sb.append("mPowerOnPref.getString(keyTime, null) = ");
            sb.append(mPowerOnPref.getString(keyTime, null));
            Log.d(TAG, sb.toString());
            Log.d(TAG, "mPowerOnPref.getString(keyMode, null) = " + mPowerOnPref.getString(keyMode, null));
            mTimeArray.add(mPowerOnPref.getString(keyTime, null));
            mBootModeArray.add(mPowerOnPref.getString(keyMode, null));
        }
    }

    @Override // com.sprd.engineermode.debuglog.SwitchBaseActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "mPowerOnCount = " + mPowerOnCount);
        if (this.mAdapter == null) {
            Log.d(TAG, "mAdapter == null");
            this.mAdapter = new SwitchMachineAdapter(this, mTimeArray, mBootModeArray, 1);
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
            this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.sprd.engineermode.debuglog.PowerOnInfoActivity.1
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
            if (mPowerOnCount == 0) {
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
        if (mPowerOnCount == 0) {
            Toast.makeText(this, R.string.no_data_toast, 0).show();
        } else if (!this.mIsMounted) {
            Toast.makeText(this, R.string.no_sd_toast, 0).show();
        } else {
            this.mThirdHandler.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.PowerOnInfoActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    PowerOnInfoActivity.this.saveToSd(SwitchBaseActivity.POWER_ON_PATH, SwitchBaseActivity.POWER_ON_NAME);
                }
            });
        }
        return true;
    }

    private void doReset() {
        mPowerOnCount = 0L;
        mTimeArray.clear();
        mBootModeArray.clear();
        SwitchMachineAdapter switchMachineAdapter = this.mAdapter;
        if (switchMachineAdapter != null) {
            switchMachineAdapter.notifyDataSetChanged();
        }
        mEditor.clear();
        mEditor.apply();
        invalidateOptionsMenu();
    }

    @Override // com.sprd.engineermode.debuglog.SwitchBaseActivity, android.app.Activity
    public void finish() {
        super.finish();
    }
}
