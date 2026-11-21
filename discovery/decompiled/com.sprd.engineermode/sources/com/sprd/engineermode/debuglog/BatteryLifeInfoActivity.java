package com.sprd.engineermode.debuglog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.SwitchPreference;
import android.util.Log;
import com.sprd.engineermode.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class BatteryLifeInfoActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    public static final String BATTERY_LIFE_TIME = "battery_life_time";
    private static final String KEY_BATTERY_LIFE = "battery_life";
    private static final String KEY_BATTERY_TIME = "battery_time";
    private static final String KEY_LIFE_TIME = "life_time";
    private static final String KEY_LOWER_BATTERY = "low_power_items";
    private static final String KEY_LOWER_BATTERY_TIME = "low_power_time";
    private static final String KEY_OPEN_BATTERY = "open_battery_time";
    private static final String TAG = "BatteryLifeInfoActivity";
    private static final int UPDATE_BATTERY_LEVEL = 100;
    public static SharedPreferences mBatteryLifeTime;
    public static SharedPreferences.Editor mEditor;
    private SwitchPreference mBatteryLife;
    private PreferenceCategory mBatteryTime;
    private Context mContext;
    private Preference mLifeTime;
    private Preference mLowPowerTime;
    private Preference mOpenBatteryTime;
    private BatteryReceiver mReceiver;
    private static List<String> mTimeArray = new ArrayList();
    private static List<String> mBootModeArray = new ArrayList();
    private boolean mIsOpenLife = false;
    private boolean mIsInfoAlive = false;
    private CharSequence mOpenSummary = "unkown";
    private CharSequence mshutdownSummary = "unkown";
    private String mOpenTime = "unkown";
    private int mOpenLevel = 0;
    private String mShutdownTime = "unkown";
    private String mTotalTime = "unkown";
    Handler mMainThreadHandler = new Handler() { // from class: com.sprd.engineermode.debuglog.BatteryLifeInfoActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Log.i(BatteryLifeInfoActivity.TAG, "handleMessage  msg.what = " + msg.what);
            if (msg.what == 100) {
                BatteryLifeInfoActivity.this.updatePrefernce(true);
            }
        }
    };

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_battery);
        this.mBatteryLife = (SwitchPreference) findPreference(KEY_BATTERY_LIFE);
        this.mBatteryLife.setOnPreferenceChangeListener(this);
        this.mBatteryTime = (PreferenceCategory) findPreference(KEY_BATTERY_TIME);
        this.mOpenBatteryTime = findPreference(KEY_OPEN_BATTERY);
        this.mLowPowerTime = findPreference(KEY_LOWER_BATTERY_TIME);
        this.mLifeTime = findPreference(KEY_LIFE_TIME);
        mBatteryLifeTime = getSharedPreferences("battery_life_time", 0);
        mEditor = mBatteryLifeTime.edit();
        this.mContext = this;
        this.mOpenTime = mBatteryLifeTime.getString("open_time", "unkown");
        this.mOpenLevel = mBatteryLifeTime.getInt("open_battery", 100);
        this.mShutdownTime = mBatteryLifeTime.getString("shut_down_info", "unkown");
        this.mTotalTime = mBatteryLifeTime.getString("total_time", "unkown");
        Log.d(TAG, "onCreate mOpenTime = " + this.mOpenTime + " mShutdownTime = " + this.mShutdownTime + " mTotalTime = " + this.mTotalTime);
        if (this.mTotalTime.equals("unkown") && !this.mShutdownTime.equals("unkown") && !this.mOpenTime.equals("unkown")) {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
            Date startDate = null;
            Date shutDownTime = null;
            try {
                startDate = format.parse(this.mOpenTime);
                shutDownTime = format.parse(this.mShutdownTime);
                Log.d(TAG, "startDate = " + startDate + " shutDownTime = " + shutDownTime);
            } catch (ParseException e) {
                Log.d(TAG, "ParseException for startDate shutDownTime");
                e.printStackTrace();
            }
            if (shutDownTime != null && startDate != null) {
                long diff = shutDownTime.getTime() - startDate.getTime();
                long hours = diff / 3600000;
                long minutes = (diff - (hours * 3600000)) / 60000;
                long seconds = ((diff - (3600000 * hours)) - (60000 * minutes)) / 1000;
                Log.d(TAG, " diff = " + diff + " hours = " + hours + " minutes = " + minutes + " seconds = " + seconds);
                StringBuilder sb = new StringBuilder();
                sb.append(Long.toString(hours));
                sb.append(":");
                sb.append(Long.toString(minutes));
                sb.append(":");
                sb.append(Long.toString(seconds));
                this.mTotalTime = sb.toString();
                Log.d(TAG, " mTotalTime = " + this.mTotalTime);
            }
        }
        this.mIsOpenLife = this.mBatteryLife.isChecked();
        Log.d(TAG, "mIsOpenLife = " + this.mIsOpenLife);
        updatePrefernce(false);
        if (this.mIsOpenLife) {
            Log.d(TAG, "register BatteryReceiver");
            this.mReceiver = new BatteryReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.BATTERY_CHANGED");
            registerReceiver(this.mReceiver, filter);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePrefernce(boolean isChanged) {
        Log.d(TAG, "isChanged = " + isChanged + " mIsOpenLife = " + this.mIsOpenLife);
        if (this.mIsOpenLife) {
            if (isChanged) {
                mEditor.putString("open_time", this.mOpenTime);
                mEditor.putInt("open_battery", this.mOpenLevel);
                mEditor.apply();
            }
            Log.d(TAG, "mOpenTime = " + this.mOpenTime + " mShutdownTime = " + this.mShutdownTime + "mTotalTime = " + this.mTotalTime + "mIsOpenLife =" + this.mIsOpenLife);
            getPreferenceScreen().addPreference(this.mBatteryTime);
            StringBuilder sb = new StringBuilder();
            sb.append(this.mContext.getString(R.string.open_time, this.mOpenTime));
            sb.append("  ");
            sb.append(this.mContext.getString(R.string.battery_percent, Integer.valueOf(this.mOpenLevel)));
            this.mOpenSummary = sb.toString();
            this.mOpenBatteryTime.setSummary(this.mOpenSummary);
            if (!this.mShutdownTime.equals("unkown")) {
                this.mshutdownSummary = this.mContext.getString(R.string.open_time, this.mShutdownTime);
                this.mLowPowerTime.setSummary(this.mshutdownSummary);
            } else {
                this.mLowPowerTime.setSummary((CharSequence) null);
            }
            if (!this.mTotalTime.equals("unkown")) {
                this.mLifeTime.setSummary(this.mTotalTime);
                return;
            } else {
                this.mLifeTime.setSummary((CharSequence) null);
                return;
            }
        }
        getPreferenceScreen().removePreference(this.mBatteryTime);
        mEditor.clear();
        mEditor.apply();
        Log.d(TAG, "enter isChanged");
        this.mTotalTime = "unkown";
        this.mShutdownTime = "unkown";
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        SwitchPreference switchPreference = this.mBatteryLife;
        if (pref == switchPreference) {
            this.mIsOpenLife = !switchPreference.isChecked();
            Log.d(TAG, "onPreferenceChange mIsOpenLife = " + this.mIsOpenLife);
            if (this.mIsOpenLife) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
                this.mOpenTime = sDateFormat.format(new Date());
                this.mReceiver = new BatteryReceiver();
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.intent.action.BATTERY_CHANGED");
                registerReceiver(this.mReceiver, filter);
            } else {
                BatteryReceiver batteryReceiver = this.mReceiver;
                if (batteryReceiver != null) {
                    unregisterReceiver(batteryReceiver);
                    this.mIsInfoAlive = false;
                    this.mReceiver = null;
                }
            }
            updatePrefernce(true);
        }
        return true;
    }

    private class BatteryReceiver extends BroadcastReceiver {
        private BatteryReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(BatteryLifeInfoActivity.TAG, "onReceive  action = " + action);
            if (action.equals("android.intent.action.BATTERY_CHANGED") && !BatteryLifeInfoActivity.this.mIsInfoAlive) {
                int i = 0;
                int extra_level = intent.getIntExtra("level", 0);
                BatteryLifeInfoActivity batteryLifeInfoActivity = BatteryLifeInfoActivity.this;
                if (extra_level >= 0) {
                    i = extra_level > 100 ? 100 : extra_level;
                }
                batteryLifeInfoActivity.mOpenLevel = i;
                BatteryLifeInfoActivity.this.mIsInfoAlive = true;
                Log.i(BatteryLifeInfoActivity.TAG, "mOpenTime = " + BatteryLifeInfoActivity.this.mOpenTime + " battery level = " + BatteryLifeInfoActivity.this.mOpenLevel);
                Message msg = BatteryLifeInfoActivity.this.mMainThreadHandler.obtainMessage(100);
                BatteryLifeInfoActivity.this.mMainThreadHandler.sendMessage(msg);
            }
        }
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        BatteryReceiver batteryReceiver = this.mReceiver;
        if (batteryReceiver != null) {
            unregisterReceiver(batteryReceiver);
            this.mIsInfoAlive = false;
            this.mReceiver = null;
            Log.d(TAG, "onDestroy  unregisterReceiver mIsInfoAlive= " + this.mIsInfoAlive);
        }
    }
}
