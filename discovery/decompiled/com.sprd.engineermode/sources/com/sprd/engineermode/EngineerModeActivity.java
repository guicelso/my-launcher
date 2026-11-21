package com.sprd.engineermode;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.sprd.engineermode.connectivity.ConnectivityFragment;
import com.sprd.engineermode.debuglog.DebugLogFragment;
import com.sprd.engineermode.hardware.HardWareFragment;
import com.sprd.engineermode.location.LocationFragment;
import com.sprd.engineermode.telephony.TelephonyFragment;
import com.unisoc.engineermode.core.common.Const;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class EngineerModeActivity extends AppCompatActivity {
    private static final String AT_CLOSE_CP2 = "poweroff";
    private static final String AT_OPEN_CP2 = "poweron";
    public static final String BATTERY_LIFE_TIME = "battery_life_time";
    private static final int CLOSE_CP2 = 0;
    public static final String INFO_COUNT = "info_count";
    public static final String MODEM_ASSERT_PREF_NAME = "modem_assert";
    private static final int OPEN_CP2 = 1;
    public static final String POWER_OFF_PREF_NAME = "power_off";
    public static final String POWER_ON_PREF_NAME = "power_on";
    public static final String PREF_INFO_MODE = "_mode";
    public static final String PREF_INFO_NUM = "info_";
    public static final String PREF_INFO_TIME = "_time";
    public static final String PREF_IS_FIRST_SHUTDOWN = "first_shutdown";
    public static final String PREF_MODEM_INFO = "_info";
    public static final String PREF_OPEN_BATTERY = "open_battery";
    public static final String PREF_OPEN_TIME = "open_time";
    public static final String PREF_SHUT_DOWN_INFO = "shut_down_info";
    public static final String PREF_TOTAL_TIME = "total_time";
    private static final String SOCKET_NAME = "wcnd";
    private static final String TAG = "EngineerModeActivity";
    private SharedPreferences.Editor editor;
    private SharedPreferences mBatteryLifeTime;
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private ArrayList<Fragment> mFragmentsList;
    private SharedPreferences mPowerOffPref;
    private SharedPreferences mPrefs;
    private ViewPager mViewPager;
    private SharedPreferences preferences;
    public static String CURRENT_TAB = "android.app.engmode.currenttab";
    public static boolean mIsFirst = false;
    public static long mPowerOffCount = 0;
    private List<String> mTitleList = new ArrayList();
    private Handler mUiThread = new Handler();
    private int[] mTabTitle = {R.string.tab_telephony, R.string.tab_debug, R.string.tab_connectivity, R.string.tab_hardwaretest, R.string.tab_location};
    private int mCurrentTab = 0;
    private ShutDownReceiver mReceiver = null;

    public interface TabState {
        public static final int TAB_CONNECT_INDEX = 2;
        public static final int TAB_DEBUG_INDEX = 1;
        public static final int TAB_HARDWARE_INDEX = 3;
        public static final int TAB_LOCATION_INDEX = 4;
        public static final int TAB_TELE_INDEX = 0;
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        if (Const.isGSIVersion()) {
            finish();
            return;
        }
        setToolbarTabLayout();
        initCoordinatorAndTabLayout();
        this.preferences = getSharedPreferences("cc_status", 0);
        Log.d(TAG, "onCreate");
        this.editor = this.preferences.edit();
        this.editor.putBoolean("time1_stop", true);
        this.editor.putBoolean("time2_stop", true);
        this.editor.putBoolean("time3_stop", true);
        this.editor.commit();
        if (this.mReceiver == null) {
            this.mReceiver = new ShutDownReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.ACTION_SHUTDOWN");
            registerReceiver(this.mReceiver, filter);
        }
    }

    private void setToolbarTabLayout() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    private class ShutDownReceiver extends BroadcastReceiver {
        private ShutDownReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            EngineerModeActivity.this.mPowerOffPref = context.getSharedPreferences("power_off", 0);
            EngineerModeActivity.mPowerOffCount = EngineerModeActivity.this.mPowerOffPref.getLong("info_count", 0L);
            EngineerModeActivity.mPowerOffCount++;
            EngineerModeActivity engineerModeActivity = EngineerModeActivity.this;
            engineerModeActivity.mEditor = engineerModeActivity.mPowerOffPref.edit();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
            String date = sDateFormat.format(new Date());
            String shutDownMode = intent.getStringExtra("shutdown_mode");
            Log.d(EngineerModeActivity.TAG, "date = " + date + " shutDownMode = " + shutDownMode + " mPowerOffCount = " + EngineerModeActivity.mPowerOffCount);
            EngineerModeActivity.this.mEditor.putLong("info_count", EngineerModeActivity.mPowerOffCount);
            String key = "info_" + Long.toString(EngineerModeActivity.mPowerOffCount);
            String keyTime = key + "_time";
            String keyMode = key + "_mode";
            EngineerModeActivity.this.mEditor.putString(keyTime, date);
            EngineerModeActivity.this.mEditor.putString(keyMode, shutDownMode);
            EngineerModeActivity.this.mEditor.apply();
            if (shutDownMode != null && shutDownMode.contains("no_power")) {
                EngineerModeActivity.this.mBatteryLifeTime = context.getSharedPreferences("battery_life_time", 0);
                boolean isFirst = EngineerModeActivity.this.mBatteryLifeTime.getBoolean("first_shutdown", true);
                Log.d(EngineerModeActivity.TAG, "isFirst = " + isFirst);
                if (isFirst) {
                    SharedPreferences.Editor editor = EngineerModeActivity.this.mBatteryLifeTime.edit();
                    editor.putString("shut_down_info", date);
                    editor.putBoolean("first_shutdown", false);
                    editor.apply();
                }
            }
        }
    }

    private void initCoordinatorAndTabLayout() {
        setContentView(R.layout.activity_main);
        this.mFragmentsList = new ArrayList<>();
        this.mViewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment telephonyFragment = new TelephonyFragment();
        Fragment debugFragment = new DebugLogFragment();
        Fragment connectivityFragment = new ConnectivityFragment();
        Fragment hardwareFragment = new HardWareFragment();
        Fragment locationFragment = new LocationFragment();
        this.mFragmentsList.add(telephonyFragment);
        this.mFragmentsList.add(debugFragment);
        this.mFragmentsList.add(connectivityFragment);
        this.mFragmentsList.add(hardwareFragment);
        this.mFragmentsList.add(locationFragment);
        this.mViewPager.setAdapter(new TabFragmentPagerAdapter(fragmentManager, this.mFragmentsList, this.mTabTitle, this.mContext));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_telephony)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_debug)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_connectivity)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_hardwaretest)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_location)));
        tabLayout.setupWithViewPager(this.mViewPager);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.version_info, menu);
        MenuItem item = menu.findItem(R.id.action_version_info);
        if (item != null) {
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_version_info) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.version_info)).setMessage(getString(R.string.version_info_detail)).setPositiveButton(R.string.alertdialog_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.EngineerModeActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
            alertDialog.show();
            return true;
        }
        Log.i(TAG, "default");
        return super.onOptionsItemSelected(item);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        mIsFirst = false;
        ShutDownReceiver shutDownReceiver = this.mReceiver;
        if (shutDownReceiver != null) {
            unregisterReceiver(shutDownReceiver);
            this.mReceiver = null;
        }
        super.onDestroy();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }
}
