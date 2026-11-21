package com.sprd.engineermode.debuglog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.sprd.engineermode.utils.EMFileUtils;
import com.unisoc.engineermode.core.utils.StorageUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SwitchBaseActivity extends Activity {
    public static final String BATTERY_LIFE_TIME = "battery_life_time";
    public static final String INFO_COUNT = "info_count";
    public static final String MODEM_ASSERT_PATH = "/data/com.sprd.engineermode/shared_prefs/modem_assert.xml";
    public static final String MODEM_ASSERT_PREF_NAME = "modem_assert";
    public static final int MODE_MODEM_ASSERT = 3;
    public static final int MODE_POWER_OFF = 2;
    public static final int MODE_POWER_ON = 1;
    public static final String POWER_OFF_NAME = "power_off_info.xml";
    public static final String POWER_OFF_PATH = "/data/data/com.sprd.engineermode/shared_prefs/power_off.xml";
    public static final String POWER_OFF_PREF_NAME = "power_off";
    public static final String POWER_ON_NAME = "power_on_info.xml";
    public static final String POWER_ON_PATH = "/data/data/com.sprd.engineermode/shared_prefs/power_on.xml";
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
    private static final String TAG = "SwitchBaseActivity";
    public static SharedPreferences mBatteryLifeTime;
    private static Context mContext;
    public static SharedPreferences.Editor mEditor;
    public static SharedPreferences mModemAssertPref;
    public static SharedPreferences mPowerOffPref;
    public static SharedPreferences mPowerOnPref;
    public boolean mIsMounted = false;
    public ListView mListView;
    public static long mPowerOnCount = 0;
    public static long mPowerOffCount = 0;
    public static long mModemAssertCount = 0;
    public static final String INTERNAL_STORAGE_PATH = StorageUtils.getInternalStorage();
    public static final String MODEM_ASSERT_NAME = "modem_assert_info.xml";
    public static String MODEM_ASSERT_FILE = INTERNAL_STORAGE_PATH + "/EmInfo/" + MODEM_ASSERT_NAME;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_machine);
        mContext = this;
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mListView = (ListView) findViewById(R.id.list);
        checkSDCard();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.switch_machine_menu, menu);
        return true;
    }

    public void clearInfo() {
        EMFileUtils.write(MODEM_ASSERT_FILE, "", false);
    }

    public static void saveToInternal(String modemData) {
        if (getInternalFreeSize() <= 2) {
            Log.d(TAG, "Internal size is too small");
            Toast.makeText(mContext, R.string.no_space_toast, 0).show();
            return;
        }
        String fileDir = INTERNAL_STORAGE_PATH + "/EmInfo";
        Log.d(TAG, "the internal file dir path is " + fileDir);
        EMFileUtils.newFolder(fileDir);
        EMFileUtils.write(MODEM_ASSERT_FILE, modemData + "\n", true);
    }

    public void saveToSd(String preferenceName, String name) {
        if (!this.mIsMounted) {
            Log.d(TAG, "no SD");
            return;
        }
        if (getSDFreeSize() <= 2) {
            Log.d(TAG, "SD size is not too small");
            Toast.makeText(this, R.string.no_space_toast, 0).show();
            return;
        }
        File powerInfo = new File(preferenceName);
        File secondStorage = null;
        List<File> allStorages = new ArrayList<>();
        Collections.addAll(allStorages, getApplicationContext().getExternalFilesDirs(null));
        for (File file : allStorages) {
            Log.d(TAG, "file = " + file.getAbsolutePath());
            if (file.getAbsolutePath().startsWith("/storage/emulated/0")) {
                Log.d(TAG, "File is internal storage!");
            } else {
                secondStorage = file;
            }
        }
        if (secondStorage != null) {
            Log.d(TAG, "secondStorage = " + secondStorage.getAbsolutePath());
            File powerDir = new File(secondStorage.getAbsolutePath() + "/EmInfo");
            if (!powerDir.exists()) {
                Log.d(TAG, "!powerDir.exists()");
                powerDir.mkdirs();
            }
            File newFile = new File(powerDir, name);
            try {
                newFile.createNewFile();
                copyFile(powerInfo, newFile);
                Log.d(TAG, "sucess dump file to sd");
            } catch (IOException e) {
                Log.d(TAG, "dump fail because IOException");
            }
        }
    }

    public void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0L, inChannel.size(), outChannel);
            Toast.makeText(this, R.string.dump_file_toast, 0).show();
            inChannel.close();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    protected void checkSDCard() {
        if (StorageUtils.getExternalStorageState()) {
            this.mIsMounted = true;
        } else {
            this.mIsMounted = false;
        }
        Log.d(TAG, "bool mIsMounted= " + this.mIsMounted);
    }

    public long getSDFreeSize() {
        return StorageUtils.getStorageFreeSize(true);
    }

    public static long getInternalFreeSize() {
        return StorageUtils.getStorageFreeSize(false);
    }

    public void makeMenuItemVisible(Menu menu, int itemId, boolean visible) {
        MenuItem item = menu.findItem(itemId);
        if (item != null) {
            item.setVisible(visible);
        }
    }

    public void makeMenuItemEnabled(Menu menu, int itemId, boolean enable) {
        MenuItem item = menu.findItem(itemId);
        if (item != null) {
            item.setEnabled(enable);
        }
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
    }
}
