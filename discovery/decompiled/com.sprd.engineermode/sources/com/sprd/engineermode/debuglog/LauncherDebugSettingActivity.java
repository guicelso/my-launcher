package com.sprd.engineermode.debuglog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.widget.Toast;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.MiscProxyKt;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class LauncherDebugSettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String ACTION_PLATFORM_LAUNCHER = "com.android.launcher.action.UNISOC_LAUNCHER3";
    private static final String KEY_LAUNCHER_ALL_LOG = "launcher_all_log";
    private static final String KEY_LAUNCHER_CATEGORY = "category_launcher";
    private static final String KEY_LAUNCHER_DYNAMICION_LOG = "launcher_dynamicicon_log";
    private static final String KEY_LAUNCHER_EXTERNAL_LOG = "launcher_external_log";
    private static final String KEY_LAUNCHER_FW_LOG = "launcherframeworks_log";
    private static final String KEY_LAUNCHER_LOADER_LOG = "launcher_loader_log";
    private static final String KEY_LAUNCHER_PERFORMANCE_LOG = "launcher_perform_log";
    private static final String KEY_LAUNCHER_PREFERENCESCREEN = "launcher_debug_preferencescreen";
    private static final String KEY_LAUNCHER_UNRAD_LOG = "launcher_unread_log";
    private static final String KEY_LAUNCHER_WIDGET_LOG = "launcher_widget_log";
    private static final String KEY_WALLPAPERS_LOG = "wallpapers_log";
    private static final String PROP_DEBUG_ALL = "persist.sys.launcher.all";
    private static final String PROP_DEBUG_DYNAMIC_ICON = "persist.sys.launcher.dyicon";
    private static final String PROP_DEBUG_EXTERNAL_MSG = "persist.sys.launcher.external";
    private static final String PROP_DEBUG_LAUNCHER_FW = "persist.sys.launcherfw.debug";
    private static final String PROP_DEBUG_LOADER = "persist.sys.launcher.loader";
    private static final String PROP_DEBUG_PERFORMANCE = "persist.sys.launcher.perform";
    private static final String PROP_DEBUG_UNREAD = "persist.sys.launcher.unread";
    private static final String PROP_DEBUG_WALLPAPERS = "persist.sys.wallpaper.debug";
    private static final String PROP_DEBUG_WIDGET = "persist.sys.launcher.widget";
    private static final String SPRD_BADGE_SUPPORT = "ro.launcher.badge";
    private static final String SPRD_DYNAMIC_ICON_SUPPORT = "ro.launcher.dynamic";
    private static final String TAG = "LauncherDebugSettingActivity";
    private String mPlatformLauncherPkgName;
    private PreferenceCategory mPreGroup;
    private EMSwitchPreference mPrefAllLog;
    private EMSwitchPreference mPrefDynamicLog;
    private EMSwitchPreference mPrefLauncherFwLog;
    private EMSwitchPreference mPrefLoaderLog;
    private EMSwitchPreference mPrefPerformLog;
    private EMSwitchPreference mPrefReceiverLog;
    private EMSwitchPreference mPrefUnreadLog;
    private EMSwitchPreference mPrefWallpapersLog;
    private EMSwitchPreference mPrefWidgetLog;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_launcher_debug_settings);
        initLauncherAppPreferences();
        initOthersPreferences();
    }

    private boolean hasPlatformLauncher(String action, PackageManager pm) {
        if (TextUtils.isEmpty(action) || pm == null) {
            return false;
        }
        Intent intent = new Intent(action);
        for (ResolveInfo info : pm.queryBroadcastReceivers(intent, 0)) {
            if (info.activityInfo != null) {
                this.mPlatformLauncherPkgName = info.activityInfo.packageName;
                return true;
            }
        }
        return false;
    }

    private void initLauncherAppPreferences() {
        boolean hasPlatformLauncher = hasPlatformLauncher(ACTION_PLATFORM_LAUNCHER, getPackageManager());
        PreferenceScreen myScreen = (PreferenceScreen) findPreference(KEY_LAUNCHER_PREFERENCESCREEN);
        this.mPreGroup = (PreferenceCategory) findPreference(KEY_LAUNCHER_CATEGORY);
        if (!hasPlatformLauncher) {
            myScreen.removePreference(this.mPreGroup);
            return;
        }
        this.mPrefAllLog = (EMSwitchPreference) findPreference(KEY_LAUNCHER_ALL_LOG);
        this.mPrefAllLog.setChecked(SystemPropertiesProxy.getBoolean(PROP_DEBUG_ALL, false));
        this.mPrefAllLog.setOnPreferenceChangeListener(this);
        this.mPrefReceiverLog = (EMSwitchPreference) findPreference(KEY_LAUNCHER_EXTERNAL_LOG);
        this.mPrefReceiverLog.setChecked(SystemPropertiesProxy.getBoolean(PROP_DEBUG_EXTERNAL_MSG, true));
        this.mPrefReceiverLog.setOnPreferenceChangeListener(this);
        this.mPrefLoaderLog = (EMSwitchPreference) findPreference(KEY_LAUNCHER_LOADER_LOG);
        this.mPrefLoaderLog.setChecked(SystemPropertiesProxy.getBoolean(PROP_DEBUG_LOADER, true ^ Build.TYPE.equals("user")));
        this.mPrefLoaderLog.setOnPreferenceChangeListener(this);
        this.mPrefPerformLog = (EMSwitchPreference) findPreference(KEY_LAUNCHER_PERFORMANCE_LOG);
        this.mPrefPerformLog.setChecked(SystemPropertiesProxy.getBoolean(PROP_DEBUG_PERFORMANCE, false));
        this.mPrefPerformLog.setOnPreferenceChangeListener(this);
        this.mPrefWidgetLog = (EMSwitchPreference) findPreference(KEY_LAUNCHER_WIDGET_LOG);
        this.mPrefWidgetLog.setChecked(SystemPropertiesProxy.getBoolean(PROP_DEBUG_WIDGET, false));
        this.mPrefWidgetLog.setOnPreferenceChangeListener(this);
        this.mPrefUnreadLog = (EMSwitchPreference) findPreference(KEY_LAUNCHER_UNRAD_LOG);
        if (SystemPropertiesProxy.getBoolean(SPRD_BADGE_SUPPORT, false)) {
            this.mPrefUnreadLog.setChecked(SystemPropertiesProxy.getBoolean(PROP_DEBUG_UNREAD, false));
            this.mPrefUnreadLog.setOnPreferenceChangeListener(this);
        } else {
            this.mPreGroup.removePreference(this.mPrefUnreadLog);
        }
        this.mPrefDynamicLog = (EMSwitchPreference) findPreference(KEY_LAUNCHER_DYNAMICION_LOG);
        if (SystemPropertiesProxy.getBoolean(SPRD_DYNAMIC_ICON_SUPPORT, false)) {
            this.mPrefDynamicLog.setChecked(SystemPropertiesProxy.getBoolean(PROP_DEBUG_DYNAMIC_ICON, false));
            this.mPrefDynamicLog.setOnPreferenceChangeListener(this);
        } else {
            this.mPreGroup.removePreference(this.mPrefDynamicLog);
        }
    }

    private void initOthersPreferences() {
        this.mPrefWallpapersLog = (EMSwitchPreference) findPreference(KEY_WALLPAPERS_LOG);
        this.mPrefWallpapersLog.setChecked(SystemPropertiesProxy.getBoolean(PROP_DEBUG_WALLPAPERS, false));
        this.mPrefWallpapersLog.setOnPreferenceChangeListener(this);
        this.mPrefLauncherFwLog = (EMSwitchPreference) findPreference(KEY_LAUNCHER_FW_LOG);
        this.mPrefLauncherFwLog.setChecked(SystemPropertiesProxy.getBoolean(PROP_DEBUG_LAUNCHER_FW, false));
        this.mPrefLauncherFwLog.setOnPreferenceChangeListener(this);
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) throws IllegalAccessException, InvocationTargetException {
        if (pref == this.mPrefAllLog) {
            SystemPropertiesProxy.set(PROP_DEBUG_ALL, ((Boolean) newValue).booleanValue() ? "true" : "false");
        } else if (pref == this.mPrefReceiverLog) {
            SystemPropertiesProxy.set(PROP_DEBUG_EXTERNAL_MSG, ((Boolean) newValue).booleanValue() ? "true" : "false");
        } else if (pref == this.mPrefLoaderLog) {
            SystemPropertiesProxy.set(PROP_DEBUG_LOADER, ((Boolean) newValue).booleanValue() ? "true" : "false");
        } else if (pref == this.mPrefPerformLog) {
            SystemPropertiesProxy.set(PROP_DEBUG_PERFORMANCE, ((Boolean) newValue).booleanValue() ? "true" : "false");
        } else if (pref == this.mPrefWidgetLog) {
            SystemPropertiesProxy.set(PROP_DEBUG_WIDGET, ((Boolean) newValue).booleanValue() ? "true" : "false");
        } else if (pref == this.mPrefUnreadLog) {
            SystemPropertiesProxy.set(PROP_DEBUG_UNREAD, ((Boolean) newValue).booleanValue() ? "true" : "false");
        } else if (pref == this.mPrefDynamicLog) {
            SystemPropertiesProxy.set(PROP_DEBUG_DYNAMIC_ICON, ((Boolean) newValue).booleanValue() ? "true" : "false");
        } else if (pref == this.mPrefWallpapersLog) {
            SystemPropertiesProxy.set(PROP_DEBUG_WALLPAPERS, ((Boolean) newValue).booleanValue() ? "true" : "false");
        } else if (pref == this.mPrefLauncherFwLog) {
            SystemPropertiesProxy.set(PROP_DEBUG_LAUNCHER_FW, ((Boolean) newValue).booleanValue() ? "true" : "false");
        }
        if (((Boolean) newValue).booleanValue()) {
            if (pref == this.mPrefWallpapersLog || pref == this.mPrefLauncherFwLog) {
                new AlertDialog.Builder(pref.getContext()).setTitle(getResources().getString(R.string.launcher_fw_debug_enable_hint_title)).setMessage(getResources().getString(R.string.launcher_fw_debug_enable_hint_msg)).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.-$$Lambda$LauncherDebugSettingActivity$neT1E4db7PaQchU33LksvW9dLXA
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.lambda$onPreferenceChange$0$LauncherDebugSettingActivity(dialogInterface, i);
                    }
                }).create().show();
                return true;
            }
            if (!TextUtils.isEmpty(this.mPlatformLauncherPkgName)) {
                MiscProxyKt.ActivityManager_forceStopPackage(this.mPlatformLauncherPkgName);
                Toast.makeText(this, getResources().getString(R.string.launcher_debug_enable_hint), 0).show();
                return true;
            }
            return true;
        }
        return true;
    }

    public /* synthetic */ void lambda$onPreferenceChange$0$LauncherDebugSettingActivity(DialogInterface dialog, int which) {
        PowerManager pm = (PowerManager) getSystemService("power");
        if (pm != null) {
            pm.reboot(null);
        }
    }
}
