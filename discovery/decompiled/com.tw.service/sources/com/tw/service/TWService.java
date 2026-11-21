package com.tw.service;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TWService extends Service {
    private static int rc;
    protected boolean Eb;
    private ka Lb;
    private Toast Oa;
    private String Sb;
    private com.tw.service.c.b Ta;
    private int Ua;
    private WindowManager Ub;
    private WindowManager.LayoutParams Vb;
    private boolean Wb;
    private View Yb;
    protected int ab;
    private int bb;
    private LocationListener cc;
    private boolean fb;
    private int jb;
    private boolean kb;
    private oa lb;
    SharedPreferences lc;
    private HandlerC0037a mb;
    private fa mc;
    private da nb;
    protected boolean nightMode;
    private Y ob;
    private aa pb;
    private boolean qb;
    private ia sb;
    private volatile Looper xb;
    private volatile ga yb;
    private static final int[] pc = {R.string.radio, R.string.navi, R.string.music, R.string.video, R.string.bt, R.string.tv, R.string.aux, R.string.ipod, R.string.disc, R.string.tlink};
    private static final int[] qc = {R.string.music, R.string.video, 0};
    private static int sc = -1;
    private la pa = null;
    private int qa = -1;
    private int ra = -1;
    private int ta = 0;
    private boolean ua = false;
    private int mSource = -1;
    private int va = 0;
    private int wa = 0;
    private boolean xa = false;
    private int mActivity = 0;
    private int ya = 0;
    private int za = 0;
    private int Aa = 0;
    private int mConfig = -1;
    private int Ba = 0;
    private boolean Ca = false;
    private boolean Da = false;
    private boolean Ea = true;
    private boolean Fa = false;
    private boolean Ga = false;
    private boolean Ha = false;
    private int Ia = 0;
    private int Ja = 14;
    private int Ka = 11;
    private int La = 14;
    private boolean Ma = false;
    private ComponentName Na = null;
    private int mMode = 0;
    private int Pa = 0;
    private int Qa = -1;
    private boolean Ra = false;
    private boolean Sa = false;
    private int Va = -1;
    private int Wa = 0;
    private boolean Xa = false;
    private boolean Ya = false;
    private int Za = -1;
    private int _a = 0;
    private ea mThread = null;
    private boolean db = false;
    private boolean isScreenOn = true;
    private String eb = "";
    private int gb = -1;
    private int hb = 0;
    private boolean ib = false;
    private Handler mHandler = new HandlerC0060x(this);
    private boolean rb = false;
    private ActivityManager tb = null;
    private AudioManager mAudioManager = null;
    private AudioManager.OnAudioFocusChangeListener ub = null;
    private int vb = 0;
    private int wb = 0;
    private BroadcastReceiver zb = new F(this);
    private int Ab = 0;
    private BroadcastReceiver Bb = new G(this);
    private BroadcastReceiver Cb = new I(this);
    private int Db = 680;
    private String[] Fb = {"com.tw.ac", "com.tw.carsettings", "com.tw.fsync", "com.tw.m3cd", "com.tw.onstar", "com.tw.originalcar", "com.tw.rtpms", "com.tw.canuart_track", "com.tw.carapps", "com.tw.car", "com.tw.carinfoservice", "com.tw.touchpad", "com.tw.canuart"};
    private String[] Gb = {"com.tw.ac.AirService", "com.tw.carsettings.CarSettingService", "com.tw.fsync.FSyncService", "com.tw.m3cd.M3CDService", "com.tw.onstar.OnStarService", "com.tw.originalcar.service.OriginalCarService", "com.tw.rtpms.TPMSService", "com.tw.canuart_track.Canuat_TrackService", "com.tw.carapps.CarService", "com.tw.car.CarService", "com.tw.carinfoservice.CarService", "com.tw.touchpad.TouchService", "com.tw.canuart.MainService"};
    private ServiceConnection Hb = new W(this);
    final Object Ib = new Object();
    ServiceConnection Jb = null;
    final Runnable Kb = new RunnableC0046j(this);
    private boolean Mb = false;
    private boolean Nb = false;
    private ActivityInfo Ob = null;
    private boolean Pb = false;
    private boolean Qb = false;
    private boolean Rb = false;
    private BroadcastReceiver Tb = new C0058v(this);
    private boolean Xb = false;
    private boolean Zb = true;
    private int _b = 0;
    private int ac = 0;
    private float mSpeed = 0.0f;
    private int bc = 0;
    private List dc = new ArrayList();
    ServiceConnection ec = new ServiceConnectionC0061y(this);
    private BroadcastReceiver fc = new C0062z(this);
    private int gc = 0;
    private int hc = 0;
    private int ic = 0;
    private BroadcastReceiver jc = new C(this);
    private BroadcastReceiver kc = new D(this);
    private boolean nc = false;
    private ServiceConnection oc = new E(this);

    /* JADX INFO: Access modifiers changed from: private */
    public ComponentName Aa() {
        try {
            return this.tb.getRunningTasks(1).get(0).topActivity;
        } catch (Exception unused) {
            return null;
        }
    }

    static /* synthetic */ int Ba(TWService tWService) {
        int i = tWService.wb;
        tWService.wb = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Ba() {
        if (!Da()) {
            this.mAudioManager.setStreamVolume(3, this.Ja, 0);
        }
        this.mAudioManager.setStreamVolume(5, this.La, 0);
        this.mAudioManager.setStreamVolume(4, 15, 0);
        Log.d("TWService", "STREAM_MUSIC=" + this.mAudioManager.getStreamVolume(3));
        Log.d("TWService", "STREAM_NOTIFICATION=" + this.mAudioManager.getStreamVolume(5));
        Log.d("TWService", "STREAM_ALARM=" + this.mAudioManager.getStreamVolume(4));
        if (!Da()) {
            this.mAudioManager.setStreamMute(3, false);
        }
        this.mAudioManager.setStreamMute(5, false);
        this.mAudioManager.setStreamMute(4, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Ca() {
        String str = SystemProperties.get("persist.tw.overlay.systemUITheme", "");
        if (!TextUtils.isEmpty(str)) {
            this.pa.write(40730, 1, 0, b.a.a.a.a.a("cmd overlay enable-exclusive ", str));
        }
        String str2 = SystemProperties.get("persist.tw.overlay.btTheme", "");
        if (!TextUtils.isEmpty(str2)) {
            this.pa.write(40730, 1, 0, b.a.a.a.a.a("cmd overlay enable-exclusive ", str2));
        }
        String str3 = SystemProperties.get("persist.tw.overlay.radioTheme", "");
        if (!TextUtils.isEmpty(str3)) {
            this.pa.write(40730, 1, 0, b.a.a.a.a.a("cmd overlay enable-exclusive ", str3));
        }
        String str4 = SystemProperties.get("persist.tw.overlay.musicTheme", "");
        if (!TextUtils.isEmpty(str)) {
            this.pa.write(40730, 1, 0, b.a.a.a.a.a("cmd overlay enable-exclusive ", str4));
        }
        String str5 = SystemProperties.get("persist.tw.overlay.videoTheme", "");
        if (!TextUtils.isEmpty(str5)) {
            this.pa.write(40730, 1, 0, b.a.a.a.a.a("cmd overlay enable-exclusive ", str5));
        }
        String str6 = SystemProperties.get("persist.tw.overlay.eqTheme", "");
        if (!TextUtils.isEmpty(str6)) {
            this.pa.write(40730, 1, 0, b.a.a.a.a.a("cmd overlay enable-exclusive ", str6));
        }
        String str7 = SystemProperties.get("persist.tw.overlay.carsettingTheme", "");
        if (TextUtils.isEmpty(str7)) {
            return;
        }
        this.pa.write(40730, 1, 0, b.a.a.a.a.a("cmd overlay enable-exclusive ", str7));
    }

    static /* synthetic */ boolean Ca(TWService tWService) {
        return TextUtils.equals(tWService.s(), "cn.cardoor.zt360") || TextUtils.equals(tWService.s(), "com.baony.tw360");
    }

    static /* synthetic */ int D(TWService tWService, int i) {
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean Da() {
        return t().contains("BackRow") || SystemProperties.getBoolean("persist.tw.fm.send", false) || SystemProperties.getBoolean("persist.tw.bt.send", false);
    }

    static /* synthetic */ int E(TWService tWService) {
        int i = tWService.Ab;
        tWService.Ab = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean Ea() {
        this.gc = SystemProperties.getInt("persist.dialer.call.state", 0);
        this.hc = SystemProperties.getInt("persist.hicar.call.state", 0);
        return (this.va == 0 && !this.Qb && this.hc == 0 && this.gc == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean Fa() {
        boolean zF = b.a.a.a.a.f("/system/priv-app/com.tw.bt_305c/com.tw.bt_305c.apk");
        boolean zF2 = b.a.a.a.a.f("/system/priv-app/com.tw.bt_305a/com.tw.bt_305a.apk");
        Log.d("TWService", "isExistsTwoBtApk(),bt_305c:" + zF + ",bt_305a:" + zF2);
        return zF && zF2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean Ga() {
        File file = new File("/system/etc/apk/com.tw.bt_305a.apk");
        File file2 = new File("/system/priv-app/com.tw.bt_305a/com.tw.bt_305a.apk");
        boolean zExists = file.exists();
        boolean z = false;
        if (file2.exists() && zExists && file.length() == file2.length()) {
            z = true;
        }
        Log.d("TWService", "isSameBt305a(),isSame:" + z);
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Ha() {
        AudioManager audioManager;
        int i;
        if (this.Mb) {
            return;
        }
        if (!Da()) {
            if (this.Nb) {
                audioManager = this.mAudioManager;
                i = this.Ka;
            } else {
                audioManager = this.mAudioManager;
                i = this.Ja;
            }
            audioManager.setStreamVolume(3, i, 0);
        }
        StringBuilder sbE = b.a.a.a.a.e("muteNavi=");
        sbE.append(this.Nb);
        Log.d("TWService", sbE.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void I(int i) {
        Log.d("TWService", "dspCode:" + i);
        switch (i) {
            case 3:
                m(true);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            default:
                m(false);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Ia() {
        Intent intent = new Intent("com.android.settings.location.MODE_CHANGING");
        intent.putExtra("NEW_MODE", 3);
        sendBroadcast(intent, "android.permission.WRITE_SECURE_SETTINGS");
        Settings.Secure.putInt(getContentResolver(), "location_mode", 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int J(int i) {
        if (i == 0) {
            return 1;
        }
        if (i > 165) {
            return 0;
        }
        return ((i - 1) / 15) + 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Ja() throws Throwable {
        String str;
        la laVar;
        String str2;
        if (b.a.a.a.a.f("/data/preinstall")) {
            this.Ha = true;
            if (Settings.System.getInt(getContentResolver(), "RestoreFactoryImportState", -1) == 0) {
                La();
                return;
            }
            this.mHandler.sendEmptyMessageDelayed(65317, 3000L);
            this.mHandler.sendEmptyMessageDelayed(65318, 3000L);
            this.mHandler.sendEmptyMessageDelayed(65319, 3000L);
            this.mHandler.sendEmptyMessageDelayed(65329, 3000L);
            this.mHandler.sendEmptyMessageDelayed(65330, 3000L);
            this.mHandler.sendEmptyMessageDelayed(65332, 3000L);
            return;
        }
        Log.d("TWService", "/data/preinstall");
        this.pa.write(40730, 3, 0, "touch /data/preinstall");
        if (t().contains("BBA")) {
            if (SystemProperties.getInt("persist.tw.launcherType", 0) == 0) {
                this.pa.write(40730, 1, 0, "pm unhide com.android.launcher");
                laVar = this.pa;
                str2 = "pm hide com.dofun.variety";
            } else {
                this.pa.write(40730, 1, 0, "pm unhide com.dofun.variety");
                laVar = this.pa;
                str2 = "pm hide com.android.launcher";
            }
            laVar.write(40730, 1, 0, str2);
        }
        if (b.a.a.a.a.f("/data/media/0/0")) {
            Log.d("TWService", "/data/media");
            this.pa.write(40730, 3, 0, "mv /data/media/0 /data/media/1");
            this.pa.write(40730, 3, 0, "mv /data/media/1/0 /data/media/");
            this.pa.write(40730, 3, 0, "rm -r /data/media/1");
        }
        Context applicationContext = getApplicationContext();
        PackageInfo packageInfo = null;
        if (!TextUtils.isEmpty("com.dofun.carsetting")) {
            try {
                packageInfo = applicationContext.getPackageManager().getPackageInfo("com.dofun.carsetting", 0);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        int i = packageInfo == null ? 0 : packageInfo.versionCode;
        Log.d("AppUtils", "getAppVersion:=" + i + ",packageName=com.dofun.carsetting");
        if (i < 666) {
            new com.tw.service.b.c(getApplicationContext()).b("/sdcard/.Tcfg", "ts18.cfg");
        } else {
            La();
        }
        int i2 = this.jb;
        if (i2 == 90) {
            str = "bootlogo_90.bmp";
        } else if (i2 == 180) {
            str = "bootlogo_180.bmp";
        } else {
            str = i2 == 270 ? "bootlogo_270.bmp" : "bootlogo.bmp";
        }
        this.pa.write(40730, 3, 0, b.a.a.a.a.b("cp system/etc/logo/", str, " /data/tw/bootlogo.bmp"));
        this.pa.write(40730, 3, 0, "dd if=/data/tw/bootlogo.bmp of=/dev/block/by-name/logo bs=512");
        this.mHandler.sendEmptyMessage(65299);
        this.mHandler.sendEmptyMessageDelayed(65300, 600000L);
        new Thread(new RunnableC0053q(this)).start();
        if (SystemProperties.getBoolean("default.navibar.show", false)) {
            int iIntValue = Integer.valueOf(SystemProperties.get("persist.navibar.position")).intValue() + 1;
            Settings.System.putInt(getContentResolver(), "SETTING_NAVBAR", iIntValue);
            SystemProperties.set("persist.navibar.position", iIntValue == 0 ? "0" : String.valueOf(iIntValue - 1));
            Settings.System.putInt(getContentResolver(), "navigationbar_config", iIntValue == 0 ? 0 : 16);
            Settings.System.putInt(getContentResolver(), "show_navigationbar", iIntValue == 0 ? 0 : 1);
            l("com.android.launcher");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Ka() throws Throwable {
        if (this.ub == null) {
            this.ub = new C0049m(this);
        }
        if (this.vb == 1) {
            p(false);
        }
        if (this.mAudioManager.requestAudioFocus(this.ub, 3, 1) != 0 || this.va == 0) {
            return;
        }
        this.xa = true;
    }

    static /* synthetic */ void L(TWService tWService) throws Throwable {
        BufferedReader bufferedReader;
        tWService.dc.clear();
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader("/sdcard/.Tcfg/sleepKillPackage.cfg"));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                Log.d("TWService", "initDisKillPackageList:" + tWService.dc.size());
                                bufferedReader.close();
                                return;
                            }
                            tWService.dc.add(line);
                            if (SystemProperties.getInt("persist.tw.service.debug", 0) == 1) {
                                Log.d("TWService", "initDisKillPackageList:" + line);
                            }
                        } catch (Exception e) {
                            e = e;
                            bufferedReader2 = bufferedReader;
                            e.printStackTrace();
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    }
                } catch (Exception unused) {
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = bufferedReader2;
        }
    }

    private void La() {
        try {
            if (new File("/sdcard/.Tcfg").exists()) {
                this.mHandler.postDelayed(new RunnableC0054r(this), 1000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static /* synthetic */ void M(TWService tWService) {
        ActivityInfo activityInfo;
        StringBuilder sb;
        String str;
        if (tWService.Ob == null) {
            tWService.Ob = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").resolveActivityInfo(tWService.getPackageManager(), 0);
        }
        List<ActivityManager.RecentTaskInfo> recentTasks = tWService.tb.getRecentTasks(64, 2);
        if (recentTasks != null) {
            Iterator<ActivityManager.RecentTaskInfo> it = recentTasks.iterator();
            while (it.hasNext()) {
                try {
                    ComponentName component = it.next().baseIntent.getComponent();
                    String packageName = component.getPackageName();
                    if (!"com.android.systemui".equals(packageName) && !"com.tw.service".equals(packageName) && !"com.tw.dsphelper".equals(packageName) && !"com.ms.ms2160".equals(packageName) && !"com.zht.car.beidou".equals(packageName) && !"com.zht.car.htcanservice".equals(packageName) && ((activityInfo = tWService.Ob) == null || !activityInfo.packageName.equals(packageName) || !tWService.Ob.name.equals(component.getClassName()))) {
                        if (!tWService.dc.contains(packageName)) {
                            sb = new StringBuilder();
                            str = "killRecentTasks=";
                        } else if (SystemProperties.getInt("persist.tw.service.debug", 0) == 1) {
                            sb = new StringBuilder();
                            str = "killRecentTasks() mDiskillPackageList.contains(packageName)=";
                        }
                        sb.append(str);
                        sb.append(packageName);
                        Log.d("TWService", sb.toString());
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Ma() throws Throwable {
        BufferedReader bufferedReader;
        Intent intent;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader("/data/tw/speech_name"));
                    try {
                        String line = bufferedReader.readLine();
                        String line2 = bufferedReader.readLine();
                        if (!line.equals("com.dofun.aios.voice") && !line.equals("com.dofun.bridge")) {
                            if (line.equals("com.txznet.smartadapter")) {
                                intent = new Intent("txz.intent.action.smartwakeup.triggerRecordButton");
                                intent.setPackage("com.txznet.txz");
                            } else {
                                Intent intent2 = new Intent();
                                if ("com.google.android.googlequicksearchbox".equals(line)) {
                                    intent2.setAction("android.intent.action.VOICE_COMMAND");
                                } else {
                                    intent2.setClassName(line, line2);
                                }
                                intent2.setFlags(268435456);
                                startActivity(intent2);
                            }
                            bufferedReader.close();
                        }
                        intent = new Intent("com.unisound.intent.action.START_TALK");
                        sendBroadcast(intent);
                        bufferedReader.close();
                    } catch (Exception unused) {
                        bufferedReader2 = bufferedReader;
                        if (SystemProperties.get("ro.com.google.gmsversion") != null) {
                            startActivityAsUser(new Intent("android.speech.action.WEB_SEARCH"), UserHandle.CURRENT);
                        } else {
                            sendBroadcast(new Intent("com.unisound.intent.action.START_TALK"));
                        }
                        if (bufferedReader2 == null) {
                        } else {
                            bufferedReader2.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Exception unused2) {
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Na() throws Throwable {
        StringBuilder sb;
        PackageManager packageManager;
        String str;
        StringBuilder sbE = b.a.a.a.a.e("start360Activity:");
        sbE.append(rc);
        Log.d("TWService", sbE.toString());
        int i = rc;
        if (i == 0) {
            try {
                String strT = t();
                if (strT == null || !strT.contains("LDFY")) {
                    packageManager = getPackageManager();
                    str = "cn.cardoor.zt360";
                } else {
                    packageManager = getPackageManager();
                    str = "com.baony.tw360";
                }
                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(str);
                launchIntentForPackage.setFlags(268435456);
                startActivity(launchIntentForPackage);
                return;
            } catch (Exception e) {
                e = e;
                sb = new StringBuilder();
            }
        } else {
            if (i != 2 && i != 3) {
                return;
            }
            ComponentName componentNameAa = Aa();
            StringBuilder sbE2 = b.a.a.a.a.e("getTA():");
            sbE2.append(componentNameAa.getPackageName());
            Log.d("TWService", sbE2.toString());
            try {
                if (componentNameAa.getPackageName() == null || componentNameAa.getPackageName().equals("com.tw.dvr")) {
                    return;
                }
                Intent launchIntentForPackage2 = getPackageManager().getLaunchIntentForPackage("com.tw.dvr");
                launchIntentForPackage2.setFlags(268435456);
                startActivity(launchIntentForPackage2);
                return;
            } catch (Exception e2) {
                e = e2;
                sb = new StringBuilder();
            }
        }
        sb.append("start360Activity:");
        sb.append(e.getMessage());
        Log.d("TWService", sb.toString());
    }

    static /* synthetic */ void Na(TWService tWService) {
        if (!tWService.Zb && tWService._b != 3 && tWService.ac == 0) {
            if (tWService.cc != null) {
                ((LocationManager) tWService.getSystemService("location")).removeUpdates(tWService.cc);
                tWService.cc = null;
                return;
            }
            return;
        }
        if (tWService.cc == null) {
            tWService.cc = new C0059w(tWService);
            LocationManager locationManager = (LocationManager) tWService.getSystemService("location");
            if (locationManager.isProviderEnabled("gps")) {
                locationManager.requestLocationUpdates("gps", 0L, 0.0f, tWService.cc);
            }
        }
    }

    static /* synthetic */ boolean O(TWService tWService) {
        return Settings.Global.getInt(tWService.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Oa() {
        try {
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage("com.abc.dat");
            launchIntentForPackage.setFlags(268435456);
            startActivity(launchIntentForPackage);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Pa() {
        try {
            Display[] displays = ((DisplayManager) getSystemService("display")).getDisplays();
            if (displays == null || displays.length <= 1) {
                return;
            }
            Log.i("TWService", "lxw:startBindVideoOutApp:displays lenght:" + displays.length);
            ComponentName pipComponentName = Configuration.getPipComponentName();
            String topRunningAppOnDefaultDisplay = ActivityTaskManager.getService().getTopRunningAppOnDefaultDisplay();
            if (!TextUtils.isEmpty(topRunningAppOnDefaultDisplay) && ActivityTaskManager.getService().isInSecondScreenAppList(topRunningAppOnDefaultDisplay)) {
                if (pipComponentName == null || TextUtils.isEmpty(topRunningAppOnDefaultDisplay) || !TextUtils.equals(pipComponentName.getPackageName(), topRunningAppOnDefaultDisplay)) {
                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                    activityOptionsMakeBasic.setLaunchDisplayId(displays[1].getDisplayId());
                    Bundle bundle = activityOptionsMakeBasic.toBundle();
                    Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(topRunningAppOnDefaultDisplay);
                    Log.i("TWService", "lxw:startBindVideoOutApp:topRunningAppPkg:" + topRunningAppOnDefaultDisplay);
                    launchIntentForPackage.setFlags(268435456);
                    startActivity(launchIntentForPackage, bundle);
                    Log.i("TWService", "lxw:startBindVideoOutApp:start bind video output app end");
                }
            }
        } catch (Exception e) {
            StringBuilder sbE = b.a.a.a.a.e("lxw:startBindVideoOutApp:e:");
            sbE.append(e.getMessage());
            Log.i("TWService", sbE.toString());
        }
    }

    static /* synthetic */ void Q(TWService tWService) {
        tWService.va = 0;
        tWService.xa = false;
        tWService.mActivity = 0;
        tWService.Mb = false;
        tWService.Ba();
        tWService.pa.write(40464, 1);
        tWService.pa.write(514, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Qa() {
        synchronized (this) {
            if (this.mThread == null) {
                this.mThread = new ea(this, null);
                this.mThread.start();
                this._a = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Ra() {
        String str = SystemProperties.get("persist.phone_connect_app");
        Log.d("TWService", "connectAppPkg :" + str);
        if (!TextUtils.isEmpty(str)) {
            try {
                Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(str);
                launchIntentForPackage.setFlags(268435456);
                startActivity(launchIntentForPackage);
                return;
            } catch (Exception unused) {
            }
        }
        n("CONNECT");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Sa() {
        synchronized (this) {
            if (this.mThread != null) {
                this.pa.write(65532);
                this.mThread.interrupt();
                this.mThread = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Ta() {
    }

    static /* synthetic */ boolean Va(TWService tWService) {
        return tWService.ic != 0 || tWService.Rb;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Intent a(int i, String str) {
        Log.d("TWService", "buildFactorySettingIntent:cmd:" + i + " usbPath:" + str);
        Intent intent = new Intent();
        intent.setClassName("com.dofun.carsetting", "com.dofun.carsetting.ui.MainActivity");
        intent.putExtra("carsetting.fragment", "factoryFragment");
        intent.setFlags(268451840);
        if (i != -1) {
            intent.putExtra("cmd", i);
        }
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("path", str);
        }
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(int[] iArr, int[] iArr2, int i, int i2) {
        boolean z;
        boolean z2;
        if (iArr == null) {
            z = false;
            break;
        }
        int length = iArr.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                z = false;
                break;
            }
            if (iArr[i3] <= i) {
                z = true;
                break;
            }
            i3++;
        }
        if (iArr2 == null) {
            z2 = false;
            break;
        }
        int length2 = iArr2.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length2) {
                z2 = false;
                break;
            }
            if (iArr2[i4] <= i2) {
                z2 = true;
                break;
            }
            i4++;
        }
        return z | z2;
    }

    static /* synthetic */ int b(int i) {
        return i;
    }

    public static int b(String str) {
        try {
            return Integer.parseInt(new BufferedReader(new FileReader(str)).readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int c(Context context, String str) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.equals(str)) {
                int i = runningAppProcessInfo.pid;
                Log.d("TWService", "packname:" + str + " pid:" + i);
                return i;
            }
        }
        return -1;
    }

    static /* synthetic */ void d(TWService tWService) {
        String string = Settings.System.getString(tWService.getContentResolver(), "DEFAULT_PHONE_CONNECT_APP");
        Log.e("TWService", "TWService->speechLongPress:" + string);
        if (TextUtils.isEmpty(string) || "net.easyconn".equals(string)) {
            return;
        }
        if (!"com.zjinnova.zlink".equals(string)) {
            if ("cn.cardoor.hicar".equals(string)) {
                Intent intent = new Intent();
                intent.setAction("action.tw.open.hicarvoice");
                tWService.sendBroadcast(intent);
                return;
            }
            return;
        }
        Intent intent2 = new Intent("com.zjinnova.zlink");
        intent2.setPackage("com.zjinnova.zlink");
        Intent launchIntentForPackage = tWService.getPackageManager().getLaunchIntentForPackage("com.zjinnova.zlink");
        if (launchIntentForPackage == null || launchIntentForPackage.getComponent() == null) {
            return;
        }
        ComponentName component = launchIntentForPackage.getComponent();
        Log.d("TWService", "sendZlinkKeyCode: carPlayCn:" + component + " keyCode:1500");
        if (TextUtils.equals(component.getClassName(), "com.suding.speedplay.ui.MainActivity")) {
            intent2.setComponent(new ComponentName("com.zjinnova.zlink", "com.texustek.speedplay.broadcast.VendorBroadcastReceiver"));
        }
        StringBuilder sbE = b.a.a.a.a.e("sendZlinkKeyCode: targetCn:");
        sbE.append(intent2.getComponent());
        Log.d("TWService", sbE.toString());
        intent2.putExtra("command", "REQ_SPEC_FUNC_CMD");
        intent2.putExtra("specFuncCode", 1500);
        tWService.sendBroadcast(intent2);
    }

    static /* synthetic */ void e(TWService tWService, boolean z) {
        ia iaVar = tWService.sb;
        if (z) {
            iaVar.show();
        } else if (iaVar != null) {
            iaVar.hide();
        }
    }

    static /* synthetic */ void f(TWService tWService, boolean z) {
        boolean z2;
        tWService.mHandler.removeMessages(65297);
        tWService.pa.write(776, z ? 1 : 0);
        if (z) {
            z2 = true;
        } else if (tWService.va != 0) {
            return;
        } else {
            z2 = false;
        }
        tWService.n(z2);
    }

    private void f(String str, String str2) {
        Intent intent = new Intent(str);
        intent.setData(Uri.parse(str2));
        Message messageObtainMessage = this.yb.obtainMessage();
        messageObtainMessage.what = -1;
        messageObtainMessage.obj = intent;
        this.yb.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(String str, String str2) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(str, str2));
            startService(intent);
        } catch (Exception unused) {
        }
    }

    static /* synthetic */ void ga(TWService tWService) {
        String string = tWService.lc.getString("net_ip", "192.168.1.223");
        String string2 = tWService.lc.getString("netmask_ip", "255.255.255.0");
        String string3 = tWService.lc.getString("broadcast_ip", "192.168.1.255");
        String string4 = tWService.lc.getString("rule_ip", "192.168.1.0/24");
        String string5 = tWService.lc.getString("def_gateway", "192.168.1.1");
        Log.i("xiang", "net_ip: " + string);
        Log.i("xiang", "netmask_ip: " + string2);
        Log.i("xiang", "broadcast_ip: " + string3);
        Log.i("xiang", "rule_ip: " + string4);
        tWService.pa.write(40730, 1, 0, "ifconfig tw_eth0 " + string + " netmask " + string2 + " broadcast " + string3 + " up");
        tWService.pa.write(40730, 1, 0, b.a.a.a.a.b("ip rule add from ", string4, " lookup main pref 9000"));
        tWService.pa.write(40730, 1, 0, b.a.a.a.a.b("ip rule add to ", string4, " lookup main pref 9001"));
        tWService.pa.write(40730, 1, 0, "ip route del default");
        tWService.pa.write(40730, 1, 0, b.a.a.a.a.a("ip route add default via ", string5));
    }

    private void h(String str, String str2) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(str, str2));
            startForegroundService(intent);
        } catch (Exception unused) {
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 6621. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    static /* synthetic */ void ia(com.tw.service.TWService r19) {
        /*
            Method dump skipped, instruction units count: 662
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tw.service.TWService.ia(com.tw.service.TWService):void");
    }

    static /* synthetic */ int ib(TWService tWService) {
        int i = tWService.mMode + 1;
        tWService.mMode = i;
        return i;
    }

    private void j(int i, int i2) {
        new File("/data/tw/usbdisk" + i).delete();
        Message messageObtainMessage = this.yb.obtainMessage();
        messageObtainMessage.what = i;
        messageObtainMessage.obj = new Intent("android.intent.action.MEDIA_MOUNTED").setData(Uri.parse("/storage/usbdisk" + i));
        this.yb.sendMessageDelayed(messageObtainMessage, (long) i2);
    }

    static /* synthetic */ void j(TWService tWService, boolean z) {
        ConnectivityManager connectivityManager = (ConnectivityManager) tWService.getSystemService("connectivity");
        if (z) {
            connectivityManager.startTethering(0, false, new C0055s(tWService));
        } else {
            connectivityManager.stopTethering(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean j(String str) {
        PackageInfo packageInfo;
        try {
            PackageInfo packageArchiveInfo = getPackageManager().getPackageArchiveInfo(str, 1);
            if (packageArchiveInfo == null) {
                return false;
            }
            String str2 = packageArchiveInfo.packageName;
            if (!TextUtils.isEmpty(str2)) {
                try {
                    packageInfo = getPackageManager().getPackageInfo(str2, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    packageInfo = null;
                }
                if (packageInfo == null) {
                    return true;
                }
                Log.e("TWService", "checkIfNeedInstall:packageName: " + str2 + " new app versionCode:" + packageArchiveInfo.versionCode + " old app versionCode:" + packageInfo.versionCode);
                if (packageArchiveInfo.versionCode > packageInfo.versionCode) {
                    return true;
                }
            }
        } catch (Exception e) {
            StringBuilder sbE = b.a.a.a.a.e("checkIfNeedInstall:");
            sbE.append(e.getMessage());
            Log.e("TWService", sbE.toString());
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int k(int i, int i2) {
        if (i < i2) {
            return ((i * 11) / i2) + 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String k(String str) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader(str));
                    try {
                        String line = bufferedReader.readLine();
                        bufferedReader.close();
                        return line;
                    } catch (Exception e) {
                        e = e;
                        bufferedReader2 = bufferedReader;
                        Log.e("TWService", "getCidFromFilePath:1:" + e.getMessage());
                        if (bufferedReader2 != null) {
                            bufferedReader2.close();
                        }
                        return "";
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = bufferedReader2;
            }
        } catch (Exception e3) {
            StringBuilder sbE = b.a.a.a.a.e("getCidFromFilePath:2:");
            sbE.append(e3.getMessage());
            Log.e("TWService", sbE.toString());
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x001b  */
    static /* synthetic */ void k(TWService tWService, int i) {
        la laVar;
        int i2;
        if (i != 2) {
            if (i == 3) {
                laVar = tWService.pa;
                i2 = 40451;
            } else if (i != 12) {
                switch (i) {
                    case 8:
                        laVar = tWService.pa;
                        i2 = 40456;
                        break;
                    case 9:
                        laVar = tWService.pa;
                        i2 = 40457;
                        break;
                    case 10:
                        laVar = tWService.pa;
                        i2 = 40458;
                        break;
                }
            } else {
                laVar = tWService.pa;
                i2 = 40458;
            }
            laVar.write(i2, 2);
            tWService.pa.write(i2, 6);
        } else {
            if (tWService.Aa == 0) {
                tWService.pa.write(65282, 129);
            }
            tWService.pa.write(65282, 61);
        }
        tWService.pa.write(65296, 0);
        tWService.mActivity = 0;
        tWService.sendBroadcastAsUser(new Intent("com.unisound.intent.action.ACC_OFF"), UserHandle.CURRENT);
        SystemProperties.set("rw.zlink.hu.acc", "true");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:152:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:36:0x00b0 A[Catch: Exception -> 0x00b7, TRY_LEAVE, TryCatch #1 {Exception -> 0x00b7, blocks: (B:34:0x00aa, B:36:0x00b0, B:43:0x00c2, B:48:0x00cf, B:54:0x00dd, B:60:0x00ec, B:66:0x00fb, B:72:0x0109), top: B:103:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:83:0x0130 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:84:0x0132  */
    /* JADX WARN: Code duplicated, block: B:87:0x0165  */
    /* JADX WARN: Code duplicated, block: B:89:0x017f  */
    /* JADX WARN: Code duplicated, block: B:91:0x0185  */
    /* JADX WARN: Code duplicated, block: B:94:0x018f  */
    /* JADX WARN: Code duplicated, block: B:97:0x019f  */
    /* JADX WARN: Instruction removed from duplicated block: B:87:0x0165, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:97:0x019f, please report this as an issue */
    public void l(String str) {
        boolean z;
        int i;
        Iterator it;
        int iC;
        if (this.Ob == null) {
            this.Ob = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").resolveActivityInfo(getPackageManager(), 0);
        }
        List<ActivityManager.RecentTaskInfo> recentTasks = this.tb.getRecentTasks(64, 2);
        ArrayList arrayList = new ArrayList();
        if (recentTasks != null) {
            Iterator<ActivityManager.RecentTaskInfo> it2 = recentTasks.iterator();
            z = false;
            while (true) {
                if (it2.hasNext()) {
                    ActivityManager.RecentTaskInfo next = it2.next();
                    try {
                        ComponentName component = next.baseIntent.getComponent();
                        String packageName = component.getPackageName();
                        if (!"com.android.systemui".equals(packageName) && !"com.tw.service".equals(packageName) && !"com.tw.dsphelper".equals(packageName) && !"com.ms.ms2160".equals(packageName) && !"com.zht.car.beidou".equals(packageName) && !"com.zht.car.htcanservice".equals(packageName) && !"com.dofun.carsetting".equals(packageName) && (this.Ob == null || !this.Ob.packageName.equals(packageName) || !this.Ob.name.equals(component.getClassName()))) {
                            try {
                                if ("com.tw.bt".equals(str)) {
                                    if (this.mSource == 8) {
                                        i = this.mSource;
                                    } else {
                                        i = -1;
                                    }
                                } else if ("com.tw.radio".equals(str)) {
                                    if (this.mSource == 1) {
                                        i = this.mSource;
                                    } else {
                                        i = -1;
                                    }
                                } else if ("com.tw.music".equals(str)) {
                                    if (this.mSource == 3) {
                                        i = this.mSource;
                                    } else {
                                        i = -1;
                                    }
                                } else if ("com.tw.video".equals(str)) {
                                    if (this.mSource == 9) {
                                        i = this.mSource;
                                    } else {
                                        i = -1;
                                    }
                                } else if ("com.tw.dvd".equals(str)) {
                                    if (this.mSource == 12) {
                                        i = this.mSource;
                                    } else {
                                        i = -1;
                                    }
                                } else if ("com.tw.tv".equals(str)) {
                                    if (this.mSource == 5) {
                                        i = this.mSource;
                                    } else {
                                        i = -1;
                                    }
                                } else if ("com.tw.auxin".equals(str)) {
                                    if (this.mSource == 7) {
                                        i = this.mSource;
                                    } else {
                                        i = -1;
                                    }
                                } else if (packageName.equals(str)) {
                                    Log.d("TWService", "killRecentTasks=" + packageName);
                                    arrayList.add(next);
                                }
                                z = true;
                            } catch (Exception unused) {
                                z = true;
                            }
                        }
                    } catch (Exception unused2) {
                    }
                }
                if (z) {
                    if (arrayList.size() > 0) {
                        it = arrayList.iterator();
                        while (it.hasNext()) {
                            try {
                                ActivityManager.getService().removeTask(((ActivityManager.RecentTaskInfo) it.next()).persistentId);
                            } catch (Exception unused3) {
                            }
                        }
                        return;
                    }
                    Log.d("TWService", "killRecentTasks=" + str);
                    this.tb.forceStopPackage(str);
                    return;
                }
                if (i != -1) {
                    this.pa.write(769, 192, 0);
                }
                iC = c((Context) this, str);
                Log.d("TWService", "killRecentTasks _packageName:" + str + " appPid:" + iC + ",mSourceTemp:" + i);
                if (iC != -1) {
                    this.pa.write(40730, 1, 0, "kill " + iC);
                }
            }
        }
        z = false;
        i = -1;
        if (z) {
            if (arrayList.size() > 0) {
                it = arrayList.iterator();
                while (it.hasNext()) {
                    ActivityManager.getService().removeTask(((ActivityManager.RecentTaskInfo) it.next()).persistentId);
                }
                return;
            }
            Log.d("TWService", "killRecentTasks=" + str);
            this.tb.forceStopPackage(str);
            return;
        }
        if (i != -1) {
            this.pa.write(769, 192, 0);
        }
        iC = c((Context) this, str);
        Log.d("TWService", "killRecentTasks _packageName:" + str + " appPid:" + iC + ",mSourceTemp:" + i);
        if (iC != -1) {
            this.pa.write(40730, 1, 0, "kill " + iC);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m(String str) {
        if (rc != 1) {
            Log.d("TWService", "send360ViewMode :" + str);
            sendBroadcastAsUser(new Intent("com.percherry.roundadas.LOOK_AROUND_360_SPEECH").putExtra("audioControl", str), UserHandle.ALL);
        }
    }

    private void m(boolean z) {
        try {
            ComponentName componentName = new ComponentName("com.tw.eq", "com.tw.eq.EQChoiceActivity");
            ComponentName componentName2 = new ComponentName("com.tw.eq", "com.tw.eq.DSPActivity");
            if (z) {
                getPackageManager().setComponentEnabledSetting(componentName2, 2, 1);
                getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
            } else {
                getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
                getPackageManager().setComponentEnabledSetting(componentName2, 1, 1);
            }
        } catch (Exception e) {
            StringBuilder sbE = b.a.a.a.a.e("changeEQName:");
            sbE.append(e.getMessage());
            Log.d("TWService", sbE.toString());
        }
    }

    private void n(String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setClassName("com.dofun.carsetting", "com.dofun.carsetting.ui.MainActivity");
            intent.setFlags(268435456);
            intent.putExtra("carsetting.fragment", "DefaultAppSelectFragment");
            intent.putExtra("default_app_choose", str);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("TWService", Log.getStackTraceString(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n(boolean z) {
        if (this.Mb != z) {
            this.Mb = z;
            this.mAudioManager.setStreamMute(3, z);
            this.mAudioManager.setStreamMute(5, z);
            Ha();
            Log.d("TWService", "muteMusic=" + z);
        }
    }

    static /* synthetic */ int nb(TWService tWService) {
        int i = tWService.Pa + 1;
        tWService.Pa = i;
        return i;
    }

    static /* synthetic */ int o(TWService tWService, int i) {
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void o(boolean z) {
        StringBuilder sbE = b.a.a.a.a.e("muteNotification: isMute:");
        sbE.append(this.Mb);
        sbE.append(" mute:");
        sbE.append(z);
        Log.d("TWService", sbE.toString());
        if (this.Mb) {
            return;
        }
        this.mAudioManager.setStreamMute(5, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:50:0x00d8 A[Catch: Exception -> 0x00e1, TryCatch #8 {Exception -> 0x00e1, blocks: (B:48:0x00ce, B:50:0x00d8, B:51:0x00dd), top: B:70:0x00ce }] */
    /* JADX WARN: Code duplicated, block: B:55:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:57:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:59:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:66:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:68:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:70:0x00ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:? A[Catch: Exception -> 0x009a, SYNTHETIC, TRY_LEAVE, TryCatch #4 {Exception -> 0x009a, blocks: (B:41:0x0096, B:42:0x0099), top: B:66:0x0096 }] */
    /* JADX WARN: Code duplicated, block: B:80:? A[RETURN, SYNTHETIC] */
    public void p(boolean z) throws Throwable {
        Exception e;
        String str;
        String line;
        String str2;
        ComponentName componentName;
        String currentAudioFocusName = this.mAudioManager.getCurrentAudioFocusName();
        Log.d("TWService", "muteSpeech2 pkg= " + currentAudioFocusName + " is = " + z);
        if ("com.zjinnova.zlink".equals(currentAudioFocusName)) {
            if (z) {
                d(true);
                this.pa.write(776, 1);
                return;
            } else {
                d(false);
                this.pa.write(776, 0);
                return;
            }
        }
        if ("com.nng.igo.primong.igoworld".equals(currentAudioFocusName)) {
            return;
        }
        String line2 = "";
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/tw/speech_name"));
            try {
                line = bufferedReader2.readLine();
                try {
                    try {
                        line2 = bufferedReader2.readLine();
                        try {
                            bufferedReader2.close();
                            str2 = line2;
                            line2 = line;
                        } catch (Exception e2) {
                            e = e2;
                            str = line;
                            e = e;
                            StringBuilder sbE = b.a.a.a.a.e("getSpeechComponent:2:");
                            sbE.append(e.getMessage());
                            Log.e("TWService", sbE.toString());
                            str2 = line2;
                            line2 = str;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (Exception e4) {
                                e = e4;
                                str = "";
                                StringBuilder sbE2 = b.a.a.a.a.e("getSpeechComponent:2:");
                                sbE2.append(e.getMessage());
                                Log.e("TWService", sbE2.toString());
                                str2 = line2;
                                line2 = str;
                            }
                        }
                        str2 = "";
                    }
                } catch (Throwable th) {
                    str = line;
                    bufferedReader = bufferedReader2;
                    th = th;
                    if (bufferedReader != null) {
                        throw th;
                    }
                    try {
                        bufferedReader.close();
                        throw th;
                    } catch (Exception e5) {
                        e = e5;
                        e = e;
                        StringBuilder sbE3 = b.a.a.a.a.e("getSpeechComponent:2:");
                        sbE3.append(e.getMessage());
                        Log.e("TWService", sbE3.toString());
                        str2 = line2;
                        line2 = str;
                        componentName = new ComponentName(line2, str2);
                        Log.d("TWService", "muteSpeech2:speechCN:" + componentName);
                        if (!z) {
                            if (Ea()) {
                            }
                            if (TextUtils.equals(componentName.getPackageName(), currentAudioFocusName)) {
                                this.mAudioManager.setStreamMute(5, false);
                            }
                            d(false);
                        }
                        try {
                            if (TextUtils.equals(componentName.getPackageName(), currentAudioFocusName)) {
                                this.mAudioManager.setStreamMute(5, true);
                            }
                            d(true);
                        } catch (Exception e6) {
                            e6.printStackTrace();
                            return;
                        }
                    }
                }
            } catch (Exception e7) {
                e = e7;
                bufferedReader = bufferedReader2;
                bufferedReader2 = bufferedReader;
                line = "";
                e.printStackTrace();
                if (bufferedReader2 != null) {
                    bufferedReader2.close();
                }
                str2 = "";
                componentName = new ComponentName(line2, str2);
                Log.d("TWService", "muteSpeech2:speechCN:" + componentName);
                if (!z) {
                    if (TextUtils.equals(componentName.getPackageName(), currentAudioFocusName)) {
                        this.mAudioManager.setStreamMute(5, true);
                    }
                    d(true);
                } else if (Ea()) {
                    if (TextUtils.equals(componentName.getPackageName(), currentAudioFocusName)) {
                        this.mAudioManager.setStreamMute(5, false);
                    }
                    d(false);
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = bufferedReader2;
                str = "";
                if (bufferedReader != null) {
                    throw th;
                }
                bufferedReader.close();
                throw th;
            }
        } catch (Exception e8) {
            e = e8;
        } catch (Throwable th3) {
            th = th3;
        }
        componentName = new ComponentName(line2, str2);
        Log.d("TWService", "muteSpeech2:speechCN:" + componentName);
        if (!z) {
            if (TextUtils.equals(componentName.getPackageName(), currentAudioFocusName)) {
                this.mAudioManager.setStreamMute(5, true);
            }
            d(true);
        } else if (Ea()) {
            if (TextUtils.equals(componentName.getPackageName(), currentAudioFocusName)) {
                this.mAudioManager.setStreamMute(5, false);
            }
            d(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void q(boolean z) {
        Log.d("TWService", "setAirplaneModeOn=" + z);
        Settings.Global.putInt(getContentResolver(), "airplane_mode_on", z ? 1 : 0);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.putExtra("state", z);
        sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }

    public static String r() throws Throwable {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/sys/class/block/mmcblk0/device/cid"));
                try {
                    String line = bufferedReader2.readLine();
                    bufferedReader2.close();
                    return line;
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader == null) {
                        return "0";
                    }
                    bufferedReader.close();
                    return "0";
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                return "0";
            }
        } catch (Exception unused3) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void r(boolean z) {
        if (SystemProperties.getBoolean("persist.tw.air.show", true)) {
            this.pa.write(40730, 1, 0, z ? "pm unhide com.tw.ac" : "pm hide com.tw.ac");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void s(boolean z) {
        Log.d("TWService", "toBuildInBT");
        if (z) {
            this.pa.write(40730, 1, 0, "mount -o remount,rw /");
            this.pa.write(40730, 1, 0, "rm /system/bin/gocsdk");
            this.pa.write(40730, 3, 0, "mount -o remount,ro /");
        }
        a("com.tw.bt_305a", z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void t(boolean z) {
        Log.d("TWService", "toExternalBT");
        if (z) {
            this.pa.write(40730, 1, 0, "mount -o remount,rw /");
            this.pa.write(40730, 1, 0, "cp -rf /system/etc/goc/gocsdk6 /system/bin/gocsdk");
            this.pa.write(40730, 1, 0, "chmod 755 /system/bin/gocsdk");
            this.pa.write(40730, 3, 0, "mount -o remount,ro /");
        }
        a("com.tw.bt_305c", z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ta() {
        Log.d("TWService", "bindCarInfoService");
        try {
            Intent intent = new Intent("com.tw.carinfoservice.CarService.Bind");
            intent.setClassName("com.tw.carinfoservice", "com.tw.carinfoservice.CarService");
            int i = Build.VERSION.SDK_INT;
            if (Settings.System.getInt(getApplicationContext().getContentResolver(), "BIND_SERVICE_CONFIG", 0) != 0) {
                getApplication().startForegroundService(intent);
            } else {
                getApplication().startService(intent);
            }
            getApplication().bindService(intent, this.Hb, 1);
        } catch (Exception e) {
            Log.d("TWService", Log.getStackTraceString(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void takeScreenshot() {
        synchronized (this.Ib) {
            if (this.Jb != null) {
                return;
            }
            ComponentName componentName = new ComponentName("com.android.systemui", "com.android.systemui.screenshot.TakeScreenshotService");
            Intent intent = new Intent();
            intent.setComponent(componentName);
            ServiceConnectionC0048l serviceConnectionC0048l = new ServiceConnectionC0048l(this);
            if (bindService(intent, serviceConnectionC0048l, 1)) {
                this.Jb = serviceConnectionC0048l;
                this.mHandler.postDelayed(this.Kb, 10000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ua() {
        Log.d("TWService", "bindDofunWeatherService");
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.dofun.dofunweather.main", "com.dofun.dofunweather.main.WeatherService"));
            int i = Build.VERSION.SDK_INT;
            getApplication().startForegroundService(intent);
            getApplication().bindService(intent, this.oc, 1);
        } catch (Exception e) {
            Log.e("TWService", "Error starting WeatherService", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void va() {
        Intent intent = new Intent("com.android.settings.location.MODE_CHANGING");
        intent.putExtra("NEW_MODE", 0);
        sendBroadcast(intent, "android.permission.WRITE_SECURE_SETTINGS");
        Settings.Secure.putInt(getContentResolver(), "location_mode", 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String wa() throws Throwable {
        String line;
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/tw/navi_name"));
            try {
                line = bufferedReader2.readLine();
                try {
                    bufferedReader2.close();
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
                bufferedReader = bufferedReader2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception unused3) {
                    }
                }
                line = "";
            } catch (Throwable th) {
                th = th;
                bufferedReader = bufferedReader2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception unused4) {
                    }
                }
                throw th;
            }
        } catch (Exception unused5) {
        } catch (Throwable th2) {
            th = th2;
        }
        return TextUtils.isEmpty(line) ? "" : line;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean xa() {
        String str = SystemProperties.get("sys.df.variety.theme.window");
        if (!TextUtils.isEmpty(str) && !str.equals("1")) {
            Log.e("LZP", "TWService->getHomeShowType()2");
            return false;
        }
        Log.e("LZP", "TWService->getHomeShowType()1");
        String str2 = SystemProperties.get("sys.df.desktop");
        return TextUtils.isEmpty(str2) || str2.equals("navi");
    }

    static /* synthetic */ int y(TWService tWService, int i) {
        int i2 = i & tWService.mConfig;
        tWService.mConfig = i2;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String ya() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        try {
            bufferedReader = new BufferedReader(new FileReader("/data/tw/ktv_name"));
            try {
                String line = bufferedReader.readLine();
                Log.d("TWService", "packageName:" + line);
                try {
                    bufferedReader.close();
                } catch (Exception unused) {
                }
                return line;
            } catch (Exception unused2) {
                if (bufferedReader == null) {
                    return "";
                }
                try {
                    bufferedReader.close();
                    return "";
                } catch (Exception unused3) {
                    return "";
                }
            } catch (Throwable th2) {
                th = th2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception unused4) {
                    }
                }
                throw th;
            }
        } catch (Exception unused5) {
            bufferedReader = null;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String za() {
        String str = SystemProperties.get("persist.phone_connect_app");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            PackageManager packageManager = getPackageManager();
            return packageManager.getApplicationLabel(packageManager.getPackageInfo(str, 0).applicationInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TWService", Log.getStackTraceString(e));
            return null;
        }
    }

    public boolean a(Context context, String str) {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(40);
        if (runningServices.size() <= 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().toString().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void c(int i) {
        if (i == -1 || this.pa == null) {
            return;
        }
        Log.d("TWService", "killAppByPid: pid:" + i);
        this.pa.write(40730, 1, 0, "kill -9 " + i);
    }

    public void c(String str) {
        try {
            File file = new File(str);
            if (file.canRead() && file.isFile() && file.getName().endsWith(".apk")) {
                String absolutePath = file.getAbsolutePath();
                String str2 = getPackageManager().getPackageArchiveInfo(absolutePath, 1).packageName;
                if ("com.zht.car.beidou".equals(str2) || "com.zht.car.accontroller".equals(str2) || "com.zht.car.htcanservice".equals(str2)) {
                    this.pa.write(40730, 1, 0, "/system/bin/pm install -r " + absolutePath);
                }
            }
        } catch (Exception unused) {
        }
    }

    public void d(int i) {
        this.pa.write(40730, 1, 0, "mount -o remount,rw /vendor");
        this.pa.write(40730, 1, 0, "mount -o remount,rw /");
        File file = new File("/system/etc/virtual_cam_cfg2.json");
        File file2 = new File("/vendor/etc/virtual_cam_cfg.json");
        if (file.exists() && file2.exists() && file.canRead() && file2.canRead() && file.length() != file2.length()) {
            this.pa.write(40730, 1, 0, "cp -rf /system/etc/virtual_cam_cfg2.json /vendor/etc/virtual_cam_cfg.json");
            this.pa.write(40730, 3, 0, "chmod  -R 777 /vendor/etc/virtual_cam_cfg.json");
            this.pa.write(40730, 1, 0, "sync");
            ka kaVar = new ka(this, getString(R.string.warning_reset), 20);
            kaVar.l(true);
            kaVar.show();
        }
    }

    public void d(boolean z) {
        int i;
        if (z) {
            int i2 = this.mSource;
            if (i2 == 1) {
                this.pa.write(776, 1);
                return;
            }
            i = 11;
            if (i2 != 3) {
                if (i2 != 4) {
                    if (i2 != 8) {
                        if (i2 != 9) {
                            return;
                        }
                        this.pa.write(40457, i);
                        return;
                    }
                    this.pa.write(40456, i);
                    return;
                }
                this.pa.write(40452, i);
                return;
            }
            this.pa.write(40451, i);
        }
        int i3 = this.mSource;
        if (i3 == 1) {
            this.pa.write(776, 0);
            return;
        }
        i = 12;
        if (i3 != 3) {
            if (i3 != 4) {
                if (i3 != 8) {
                    if (i3 != 9) {
                        return;
                    }
                    this.pa.write(40457, i);
                    return;
                }
                this.pa.write(40456, i);
                return;
            }
            this.pa.write(40452, i);
            return;
        }
        this.pa.write(40451, i);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!TextUtils.equals(SystemProperties.get("persist.tw.forcepip", "0"), "1") || this.pa == null) {
            return;
        }
        SystemProperties.get("sys.df.desktop");
        ComponentName pipComponentName = Configuration.getPipComponentName();
        if (pipComponentName == null || !TextUtils.equals(pipComponentName.getPackageName(), "com.nng.igo.primong.igoworld")) {
            return;
        }
        la laVar = this.pa;
        StringBuilder sbE = b.a.a.a.a.e("kill ");
        sbE.append(c((Context) this, "com.nng.igo.primong.igoworld"));
        laVar.write(40730, 1, 0, sbE.toString());
    }

    /* JADX WARN: Code duplicated, block: B:246:0x0716  */
    /* JADX WARN: Code duplicated, block: B:255:0x0728  */
    /* JADX WARN: Code duplicated, block: B:44:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:47:0x0100  */
    @Override // android.app.Service
    public void onCreate() throws Throwable {
        int i;
        BufferedReader bufferedReader;
        String line;
        boolean z;
        boolean z2;
        int i2;
        int i3;
        la laVar;
        String str;
        la laVar2;
        int i4;
        int i5;
        byte b2;
        super.onCreate();
        new DisplayMetrics();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int i6 = displayMetrics.heightPixels;
        this.Db = displayMetrics.widthPixels;
        if (this.Db == 800 && (i6 == 480 || i6 == 600)) {
            i = 680;
        } else {
            int i7 = this.Db;
            i = ((double) (((float) i7) / ((float) i6))) > 2.0d ? (i7 / 7) * 4 : (i7 / 5) * 3;
        }
        this.Db = i;
        this.Oa = Toast.makeText(this, "", 0);
        ((TextView) this.Oa.getView().findViewById(android.R.id.message)).setTextSize(30.0f);
        this.Ta = new com.tw.service.c.b(this);
        if (!Da()) {
            this.lb = new oa(this);
        }
        this.mb = new HandlerC0037a(this);
        this.nb = new da(this);
        this.ob = new Y(this);
        this.pb = new aa(this);
        this.sb = new ia(this);
        this.tb = (ActivityManager) getSystemService("activity");
        this.mAudioManager = (AudioManager) getSystemService("audio");
        Ba();
        if (Settings.System.getInt(getContentResolver(), "SYSTEM_APP2", 0) != 0) {
            try {
                bufferedReader = new BufferedReader(new FileReader("/data/tw/app_autostart_name"));
                try {
                    line = bufferedReader.readLine();
                    try {
                        bufferedReader.close();
                    } catch (Exception unused) {
                    }
                } catch (Exception unused2) {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception unused3) {
                        }
                    }
                    line = "";
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception unused4) {
                        }
                    }
                    throw th;
                }
            } catch (Exception unused5) {
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
            }
            if (TextUtils.isEmpty(line)) {
                line = "";
            }
            if (TextUtils.isEmpty(line)) {
                z = false;
            } else {
                try {
                    if (getPackageManager().getPackageInfo(line, 0) != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (z) {
                z2 = true;
            } else {
                z2 = false;
            }
        } else {
            z2 = false;
        }
        this.Ca = z2;
        if (b.a.a.a.a.f("/sys/class/misc/mir3da")) {
            SystemProperties.set("persist.sf.forcerotation", "4");
        }
        this.qb = (SystemProperties.getInt("persist.sf.forcerotation", -1) != 1 ? Settings.System.getInt(getContentResolver(), "SYSTEM_USE_CAR_DOOR_TYPE", 0) : Settings.System.getInt(getContentResolver(), "SYSTEM_USE_CAR_DOOR_TYPE", 1)) != 0;
        HandlerThread handlerThread = new HandlerThread("MediaScan", 11);
        handlerThread.start();
        this.xb = handlerThread.getLooper();
        this.yb = new ga(this, this.xb);
        this.pa = la.open();
        if (t().contains("XPH17")) {
            SystemProperties.set("persist.usb.otg", "1");
        } else {
            SystemProperties.set("persist.usb.otg", "0");
        }
        this.jb = SystemProperties.getInt("ro.sf.swrotation", 0);
        la laVar3 = this.pa;
        if (laVar3 != null) {
            if (this.Ca) {
                laVar3.write(769, 192, 0);
            }
            if (!b.a.a.a.a.f("/sdcard/iNand/radio/")) {
                this.pa.write(40730, 1, 0, "mkdir -p /sdcard/iNand/radio");
                this.pa.write(40730, 1, 0, "chmod -R 777 /sdcard/iNand/radio");
            }
            if (!b.a.a.a.a.f("/sdcard/iNand/radio/logo/")) {
                this.pa.write(40730, 1, 0, "mkdir -p /sdcard/iNand/radio/logo");
                this.pa.write(40730, 1, 0, "chmod -R 777 /sdcard/iNand/radio/logo");
            }
            if (!b.a.a.a.a.f("/sdcard/iNand/radio/logo_dny/")) {
                this.pa.write(40730, 1, 0, "mkdir -p /sdcard/iNand/radio/logo_dny");
                this.pa.write(40730, 1, 0, "chmod -R 777 /sdcard/iNand/radio/logo_dny");
            }
            File file = new File("/system/etc/radio/radio_data.txt");
            File file2 = new File("/sdcard/iNand/radio/radio_data.txt");
            if ((file.exists() ? file.length() : 0L) > (file2.exists() ? file2.length() : 0L) && b.a.a.a.a.f("/system/etc/radio/radio_data.txt")) {
                this.pa.write(40730, 3, 0, "cp -rf system/etc/radio/radio_data.txt /sdcard/iNand/radio/");
            }
            File file3 = new File("/system/etc/radio/radio_data_dny.txt");
            File file4 = new File("/sdcard/iNand/radio/radio_data_dny.txt");
            if ((file3.exists() ? file3.length() : 0L) > (file4.exists() ? file4.length() : 0L) && b.a.a.a.a.f("/system/etc/radio/radio_data_dny.txt")) {
                this.pa.write(40730, 3, 0, "cp -rf system/etc/radio/radio_data_dny.txt /sdcard/iNand/radio/");
            }
            if (!b.a.a.a.a.f("/data/tw/theme/default/")) {
                this.pa.write(40730, 1, 0, "mkdir -p /data/tw/theme/default");
                this.pa.write(40730, 1, 0, "mkdir -p /data/tw/theme/day");
                this.pa.write(40730, 1, 0, "mkdir -p /data/tw/theme/night");
                this.pa.write(40730, 1, 0, "chmod -R 777 /data/tw/theme");
                new Thread(new RunnableC0056t(this)).start();
            }
            if (!b.a.a.a.a.f("/sdcard/theme/default/Launcher/LauncherTheme.apk") || !b.a.a.a.a.f("/data/preinstall")) {
                this.pa.write(40730, 1, 0, "rm -rf /sdcard/theme");
                this.pa.write(40730, 1, 0, "mkdir -p /sdcard/theme/default/");
                this.pa.write(40730, 1, 0, "chmod -R 777 /sdcard/theme");
                new Thread(new RunnableC0057u(this)).start();
            }
            if (new File("/sys/class/tw/gtp/touch").canWrite()) {
                byte[] bArr = new byte[412];
                try {
                    FileInputStream fileInputStream = new FileInputStream("/data/tw/touch");
                    try {
                        fileInputStream.read(bArr);
                    } catch (Exception unused6) {
                    } finally {
                        fileInputStream.close();
                    }
                } catch (Exception unused7) {
                }
                if (bArr[242] == 7) {
                    if ((bArr[243] & 128) != 128) {
                        i5 = 2;
                    } else {
                        int i8 = bArr[243] & 127;
                        if (i8 != 1) {
                            if (i8 != 3 && i8 != 5) {
                                switch (i8) {
                                    case 7:
                                        break;
                                    case 8:
                                        bArr[243] = 3;
                                        break;
                                    case 9:
                                        bArr[243] = 4;
                                        break;
                                    case 10:
                                        bArr[243] = 5;
                                        break;
                                    case 11:
                                        bArr[243] = 6;
                                        break;
                                    case 12:
                                        bArr[243] = 7;
                                        break;
                                    default:
                                        bArr[243] = 0;
                                        break;
                                }
                            } else {
                                bArr[243] = 1;
                            }
                            i5 = 2;
                        }
                        i5 = 2;
                        bArr[243] = 2;
                    }
                    a(bArr, 242, i5);
                } else {
                    i5 = 2;
                }
                if (bArr[248] == 6) {
                    b2 = 255;
                    a(bArr, 248, ((bArr[249] & 255) * 4) + i5);
                } else {
                    b2 = 255;
                }
                if (bArr[266] == 5) {
                    a(bArr, 266, ((bArr[267] & b2) * 12) + i5);
                }
            }
            this.pa.write(40730, 1, 0, "rwrite /sys/class/car_reverse/tvdtype 1");
            this.pa.write(40730, 1, 0, "chmod -R 666 /dev/uinput");
            sc = b("sys/class/car_reverse/tvdtype");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
            intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
            intentFilter.addDataScheme("file");
            registerReceiver(this.zb, intentFilter);
            if (new File("/mnt/sdcard").canRead()) {
                f("android.intent.action.MEDIA_UNMOUNTED", "file:///mnt/sdcard");
            }
            if (new File("/mnt/sdcard").canRead()) {
                f("android.intent.action.MEDIA_MOUNTED", "file:///mnt/sdcard");
            }
            if (new File("/storage/extsd").canRead()) {
                f("android.intent.action.MEDIA_MOUNTED", "file:///storage/extsd");
            }
            j(0, 7000);
            j(1, 7000);
            j(2, 7000);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.TIME_TICK");
            intentFilter2.addAction("android.intent.action.TIME_SET");
            intentFilter2.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter2.addAction("com.unisound.unicar.update.action");
            registerReceiver(this.Bb, intentFilter2);
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction("com.unisound.unicar.action.mute");
            intentFilter3.addAction("com.unisound.unicar.action.unmute");
            intentFilter3.addAction("com.unisound.unicar.app.action.close");
            intentFilter3.addAction("com.unisound.unicar.broadcast.action");
            intentFilter3.addAction("com.unisound.intent.action.GET_MUTE");
            intentFilter3.addAction("com.unisound.intent.action.GET_POWER");
            intentFilter3.addAction("com.unisound.intent.action.NET_MSG");
            registerReceiver(this.Cb, intentFilter3);
            IntentFilter intentFilter4 = new IntentFilter();
            intentFilter4.addAction("com.tw.openapk.action.Activity");
            registerReceiver(this.fc, intentFilter4);
            if (t().contains("DRZN27")) {
                this.lc = getSharedPreferences("TWService", 0);
                registerReceiver(this.kc, new IntentFilter("topway_ip_config"));
            }
            this.pa.addHandler("TWService", this.mHandler, new M(this));
            this.pa.write(514, 5);
            this.pa.write(266, 255);
            this.pa.write(40464, 1);
            this.pa.write(514, 3);
            this.pa.write(769, 255);
            this.pa.write(262, 255);
            this.pa.write(267, 255);
            this.pa.write(260, 255);
            this.pa.write(40727, 1);
            this.pa.write(1028, 255);
            if (Settings.System.getInt(getContentResolver(), "SYSTEM_USB11", 0) == 1) {
                this.pa.write(40730, 1, 0, "rwrite /sys/bus/platform/drivers/dwc_otg/udcspeed 1");
            }
            int i9 = Settings.System.getInt(getContentResolver(), "SYSTEM_FTEMP", 0);
            if (i9 > 0) {
                this.pa.write(40730, 1, 0, "rwrite /sys/class/tw/misc/ftemp " + i9);
            }
            this.mHandler.postDelayed(new O(this), 3000L);
            try {
                String str2 = SystemProperties.get("ro.fota.device").split("_")[1];
                this.kb = t().contains("Driving") || t().contains("CaCar3") || t().contains("TW1-V2");
                com.tw.service.a.f.getInstance().a(getApplicationContext(), this.pa, "");
                com.tw.service.a.f.getInstance().a(new U(this, str2));
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.d("TWService", "BUGBUG");
                new ka(this, false).show();
            }
            String str3 = SystemProperties.get("persist.lcd.size", "600x1024");
            if ("480x800".equals(str3)) {
                this.pa.write(267, 11, 0);
            } else if ("600x1024".equals(str3)) {
                this.pa.write(267, 11, 1);
            } else {
                if ("480x1280".equals(str3)) {
                    laVar2 = this.pa;
                    i4 = 2;
                } else if ("720x1280".equals(str3)) {
                    laVar2 = this.pa;
                    i4 = 3;
                } else if ("768x1024".equals(str3)) {
                    int i10 = this.jb;
                    if (i10 == 90) {
                        laVar2 = this.pa;
                        i4 = 4;
                    } else if (i10 == 270) {
                        laVar2 = this.pa;
                        i4 = 5;
                    } else {
                        laVar2 = this.pa;
                        i4 = 6;
                    }
                } else if ("800x1280".equals(str3)) {
                    int i11 = this.jb;
                    if (i11 == 90) {
                        laVar2 = this.pa;
                        i4 = 7;
                    } else if (i11 == 270) {
                        laVar2 = this.pa;
                        i4 = 8;
                    }
                } else {
                    laVar2 = this.pa;
                    i4 = 9;
                }
                laVar2.write(267, 11, i4);
            }
        } else {
            Log.e("TWService", "BUGBUG TW.open fail");
        }
        IntentFilter intentFilter5 = new IntentFilter();
        intentFilter5.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter5.addDataScheme("package");
        this.mc = new fa(this, null);
        registerReceiver(this.mc, intentFilter5);
        IntentFilter intentFilter6 = new IntentFilter();
        intentFilter6.addAction("com.zjinnova.zlink");
        intentFilter6.addAction("com.zlink.intent.action.softApBlockStation");
        registerReceiver(this.Tb, intentFilter6);
        IntentFilter intentFilter7 = new IntentFilter();
        intentFilter7.addAction("cn.cardoor.hicar.action.connect.status");
        intentFilter7.addAction("cn.cardoor.hicar.action.call.status");
        intentFilter7.addAction("cn.cardoor.hicar.action.voice.status");
        registerReceiver(this.jc, intentFilter7);
        this.mHandler.sendEmptyMessageDelayed(9999, 2000L);
        this.mHandler.sendEmptyMessageDelayed(65400, 6000L);
        this.pa.write(517, 255);
        this.Ub = (WindowManager) getSystemService("window");
        this.Vb = new WindowManager.LayoutParams(-1, -1, 1, 0, 2002, 56, 1);
        this.Vb.gravity = 80;
        this.Yb = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.window_warning, (ViewGroup) null);
        String str4 = SystemProperties.get("persist.tw.forcepip", "1");
        if (!t().contains("WINDOW") && !TextUtils.equals(str4, "0")) {
            SystemProperties.set("persist.tw.forcepip", "0");
        }
        String str5 = SystemProperties.get("persist.tw.forcepip", "1");
        File file5 = new File("/system/etc/theme/public/theme.apk");
        File file6 = new File("/system/etc/theme/sfp/theme.apk");
        File file7 = new File("/system/etc/theme/theme.apk");
        if (file5.exists() && file6.exists()) {
            if (file7.exists()) {
                if (!file5.canRead() || !file6.canRead()) {
                    i2 = 3;
                } else if (file7.canRead()) {
                    boolean z3 = !TextUtils.equals(str5, "1") ? file7.length() == file5.length() : file7.length() == file6.length();
                    Log.d("TWService", "checkAndUpdateThemeApk:needUpdate:" + z3);
                    if (z3) {
                        this.pa.write(40730, 1, 0, "mount -o remount,rw /");
                        if (TextUtils.equals(str5, "1")) {
                            this.pa.write(40730, 1, 0, "cp -rf /system/etc/theme/sfp/theme.apk /system/etc/theme/");
                            laVar = this.pa;
                            str = "cp -rf /system/etc/theme/sfp/theme.apk /data/tw/theme/";
                        } else {
                            this.pa.write(40730, 1, 0, "cp -rf /system/etc/theme/public/theme.apk /system/etc/theme/");
                            laVar = this.pa;
                            str = "cp -rf /system/etc/theme/public/theme.apk /data/tw/theme/";
                        }
                        laVar.write(40730, 1, 0, str);
                        this.pa.write(40730, 3, 0, "chmod 755 /system/etc/theme/theme.apk");
                        this.pa.write(40730, 3, 0, "chmod -R 777 /data/tw/theme");
                        this.pa.write(40730, 3, 0, "mount -o remount,ro /");
                        this.pa.write(40730, 3, 0, "sync");
                        i2 = 3;
                    } else {
                        i2 = 3;
                    }
                }
                i3 = -1;
            }
            i3 = -1;
            i2 = 3;
        } else {
            i2 = 3;
            i3 = -1;
        }
        int i12 = SystemProperties.getInt("persist.sf.forcerotation", i3);
        if (i12 == 1) {
            this.rb = false;
        } else if (i12 == 2) {
            this.rb = true;
        } else if (i12 == i2) {
            this.rb = false;
        }
        if (t().contains("XRC1") && t().contains("-DSP")) {
            this.rb = false;
        }
        if (rc == 1) {
            g("com.tw.reverse", "com.tw.reverse.RadarService");
        }
        if (t().contains("ACXMP5")) {
            this.pa.write(40730, 1, 0, "pm hide com.android.chrome");
        }
        if (Settings.System.getInt(getContentResolver(), "SETTING_NAVBAR", 0) == 0) {
            int i13 = SystemProperties.getInt("persist.sf.forcerotation", -1);
            if (i13 != 0 && i13 == 1) {
                Settings.System.putInt(getContentResolver(), "navigationbar_config", 1);
                Settings.System.putInt(getContentResolver(), "show_navigationbar", 1);
            } else {
                if (Settings.System.getInt(getContentResolver(), "navigationbar_config", 0) != 0) {
                    Settings.System.putInt(getContentResolver(), "navigationbar_config", 0);
                }
                if (Settings.System.getInt(getContentResolver(), "show_navigationbar", 0) != 0) {
                    Settings.System.putInt(getContentResolver(), "show_navigationbar", 0);
                }
            }
        }
        String str6 = this.eb;
        if (str6 != null && str6.contains("KTV")) {
            new Intent();
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage("com.lehu.mystyle.boardktv");
            if (launchIntentForPackage != null) {
                startActivity(launchIntentForPackage);
            }
        }
        int iWrite = this.pa.write(65521);
        try {
            File file8 = new File("/twdataconfig/tw_config_mcu.bin");
            Log.i("TWService", "checkTwDataConfigMcuBin():" + file8.exists() + ",mcuId:" + iWrite);
            if (!file8.exists()) {
                file8.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file8);
            fileOutputStream.write(new byte[]{-127, -123, 87, 84, 0, 0, (byte) ((iWrite >> 8) & 255), (byte) (iWrite & 255)});
            fileOutputStream.flush();
            fileOutputStream.close();
            FileUtils.setPermissions("/twdataconfig/tw_config_mcu.bin", 438, -1, -1);
            this.pa.write(40730, 1, 0, "sync");
            Log.i("TWService", "write mcu bin ok");
        } catch (Exception e3) {
            Log.d("TWService", Log.getStackTraceString(e3));
        }
        if (getSharedPreferences("DF_ASSISTANT_USER", 0).getBoolean("isFirst", true)) {
            Settings.Secure.putInt(getContentResolver(), "assisted_gps_enable_option", 1);
            SharedPreferences.Editor editorEdit = getSharedPreferences("DF_ASSISTANT_USER", 0).edit();
            editorEdit.putBoolean("isFirst", false);
            editorEdit.apply();
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = this.ub;
        if (onAudioFocusChangeListener != null) {
            this.mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
            this.ub = null;
        }
        unregisterReceiver(this.Cb);
        unregisterReceiver(this.Bb);
        unregisterReceiver(this.zb);
        unregisterReceiver(this.Tb);
        unregisterReceiver(this.fc);
        unregisterReceiver(this.jc);
        unregisterReceiver(this.kc);
        fa faVar = this.mc;
        if (faVar != null) {
            unregisterReceiver(faVar);
        }
        if (this.cc != null) {
            ((LocationManager) getSystemService("location")).removeUpdates(this.cc);
            this.cc = null;
        }
        this.xb.quit();
        la laVar = this.pa;
        if (laVar != null) {
            laVar.removeHandler("TWService");
            this.pa.close();
            this.pa = null;
        }
        if (this.nc) {
            unbindService(this.oc);
            this.nc = false;
        }
        super.onDestroy();
    }

    public String s() {
        return ((ActivityManager) getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getPackageName();
    }

    public String t() throws Throwable {
        if (!TextUtils.isEmpty(this.eb)) {
            return this.eb;
        }
        try {
            String str = SystemProperties.get("ro.tw.version");
            if (str != null && str.length() > 0) {
                this.eb = str;
            }
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/system/etc/version"));
                try {
                    String line = bufferedReader2.readLine();
                    if (line != null && line.length() > 0) {
                        this.eb = line;
                    }
                    bufferedReader2.close();
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
        }
        if (TextUtils.isEmpty(this.eb)) {
            return this.eb;
        }
        this.eb = this.eb.substring(this.eb.lastIndexOf("_") + 1);
        return this.eb;
    }

    public String u() {
        return ((ActivityManager) getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getClassName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:28:0x00a6 A[Catch: Exception -> 0x00aa, TryCatch #2 {Exception -> 0x00aa, blocks: (B:12:0x006d, B:26:0x009a, B:28:0x00a6, B:29:0x00a9), top: B:56:0x006d }] */
    /* JADX WARN: Code duplicated, block: B:34:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:36:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:40:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:41:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:43:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:45:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:47:0x0106  */
    /* JADX WARN: Code duplicated, block: B:48:0x010b  */
    /* JADX WARN: Code duplicated, block: B:52:0x0132  */
    /* JADX WARN: Code duplicated, block: B:60:? A[RETURN, SYNTHETIC] */
    public void H(int i) throws Throwable {
        BufferedWriter bufferedWriter;
        Exception e;
        String strT;
        la laVar;
        String str;
        File file;
        la laVar2 = this.pa;
        try {
            try {
                if (new File("/twdataconfig").exists()) {
                    file = new File("/twdataconfig/ReverseType");
                } else {
                    File file2 = new File("/sdcard/.Tcfg");
                    if (!file2.exists()) {
                        file2.mkdir();
                    }
                    file = new File("/sdcard/.Tcfg/ReverseType");
                }
                bufferedWriter = new BufferedWriter(new FileWriter(file));
                try {
                    Log.e("HYH", "ReverseFileUtils->saveReverseTypeToFile:" + i);
                    bufferedWriter.write(i + "");
                    bufferedWriter.flush();
                } catch (Exception e2) {
                    e = e2;
                    Log.e("HYH", "ReverseFileUtils->saveReverseTypeToFile:" + e.toString());
                    e.printStackTrace();
                    if (bufferedWriter != null) {
                    }
                    FileUtils.setPermissions("/twdataconfig/ReverseType", 438, -1, -1);
                    FileUtils.setPermissions("/sdcard/.Tcfg/ReverseType", 438, -1, -1);
                    laVar2.write(40730, 1, 0, "sync");
                    if (i != 0) {
                        if (i != 1) {
                            this.pa.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                            this.pa.write(40730, 1, 0, "pm hide com.baony.tw360");
                        } else if (i != 2) {
                            this.pa.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                            this.pa.write(40730, 1, 0, "pm hide com.baony.tw360");
                            this.pa.write(40730, 1, 0, "pm unhide com.tw.dvr");
                            g("com.tw.dvr", "com.tw.dvr.service.DVRService");
                        } else {
                            this.pa.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                            this.pa.write(40730, 1, 0, "pm hide com.baony.tw360");
                            this.pa.write(40730, 1, 0, "pm unhide com.tw.dvr");
                            g("com.tw.dvr", "com.tw.dvr.service.DVRService");
                        }
                        this.pa.write(40730, 1, 0, "setprop persist.camera.reverse " + i);
                        rc = i;
                        this.pa.write(40730, 1, 0, "sync");
                        if (i == 3) {
                            d(i);
                        }
                    }
                    strT = t();
                    if (strT != null) {
                        if (strT.contains("LDFY")) {
                            laVar = this.pa;
                            str = "pm unhide com.baony.tw360";
                        } else {
                            laVar = this.pa;
                            str = "pm unhide cn.cardoor.zt360";
                        }
                        laVar.write(40730, 1, 0, str);
                    }
                    this.pa.write(40730, 1, 0, "pm hide com.tw.dvr");
                    this.pa.write(40730, 1, 0, "setprop persist.camera.reverse " + i);
                    rc = i;
                    this.pa.write(40730, 1, 0, "sync");
                    if (i == 3) {
                        d(i);
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                throw th;
            }
        } catch (Exception e3) {
            bufferedWriter = null;
            e = e3;
        } catch (Throwable th2) {
            th = th2;
            bufferedWriter = null;
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            throw th;
        }
        try {
            bufferedWriter.close();
            FileUtils.setPermissions("/twdataconfig/ReverseType", 438, -1, -1);
            FileUtils.setPermissions("/sdcard/.Tcfg/ReverseType", 438, -1, -1);
            laVar2.write(40730, 1, 0, "sync");
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        if (i != 0) {
            if (i != 1) {
                this.pa.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                this.pa.write(40730, 1, 0, "pm hide com.baony.tw360");
            } else if (i != 2 || i == 3) {
                this.pa.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                this.pa.write(40730, 1, 0, "pm hide com.baony.tw360");
                this.pa.write(40730, 1, 0, "pm unhide com.tw.dvr");
                g("com.tw.dvr", "com.tw.dvr.service.DVRService");
            } else {
                this.pa.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                this.pa.write(40730, 1, 0, "pm hide com.baony.tw360");
                this.pa.write(40730, 1, 0, "pm hide com.tw.dvr");
                i = 1;
            }
            this.pa.write(40730, 1, 0, "setprop persist.camera.reverse " + i);
            rc = i;
            this.pa.write(40730, 1, 0, "sync");
            if (i == 3) {
                d(i);
            }
        }
        strT = t();
        if (strT != null) {
            if (strT.contains("LDFY")) {
                laVar = this.pa;
                str = "pm unhide com.baony.tw360";
            } else {
                laVar = this.pa;
                str = "pm unhide cn.cardoor.zt360";
            }
            laVar.write(40730, 1, 0, str);
        }
        this.pa.write(40730, 1, 0, "pm hide com.tw.dvr");
        this.pa.write(40730, 1, 0, "setprop persist.camera.reverse " + i);
        rc = i;
        this.pa.write(40730, 1, 0, "sync");
        if (i == 3) {
            d(i);
        }
    }

    static /* synthetic */ void a(TWService tWService, int i, int i2, int i3, byte[] bArr) {
        if (tWService.Ea) {
            return;
        }
        tWService.mb.a(i, i2, i3, bArr);
    }

    static /* synthetic */ void f(TWService tWService, String str) {
        PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) tWService.getSystemService("power")).newWakeLock(6, str);
        wakeLockNewWakeLock.acquire();
        wakeLockNewWakeLock.release();
    }

    static /* synthetic */ void I(TWService tWService, int i) throws Throwable {
        int i2;
        int i3;
        int i4;
        int i5;
        la laVar;
        String str;
        la laVar2;
        String str2;
        BufferedReader bufferedReader;
        Iterator<ActivityManager.RunningAppProcessInfo> it = ((ActivityManager) tWService.getSystemService("activity")).getRunningAppProcesses().iterator();
        while (true) {
            i2 = -1;
            if (!it.hasNext()) {
                i3 = -1;
                break;
            }
            ActivityManager.RunningAppProcessInfo next = it.next();
            if (next != null && TextUtils.equals(next.processName, "com.ms.ms2160")) {
                i3 = next.pid;
                Log.d("TWService", "getAppPid:com.ms.ms2160 pid:" + i3);
                break;
            }
        }
        tWService.c(i3);
        Intent intent = new Intent("com.zjinnova.zlink");
        intent.setClassName("com.zjinnova.zlink", "org.texustek.mirror.core.MirrorService");
        try {
            Log.i("TWService", "start zlink");
            tWService.getApplicationContext().bindService(intent, tWService.ec, 1);
            tWService.startService(intent);
        } catch (Exception unused) {
            Log.i("TWService", "error");
        }
        SystemProperties.set("rw.zlink.hu.acc", "false");
        Log.d("TWService", "Z-LINK ACTION_ENTER++");
        rc = SystemProperties.getInt("persist.camera.reverse", -1);
        if (rc == -1) {
            BufferedReader bufferedReader2 = null;
            try {
                try {
                    File file = new File("/twdataconfig").exists() ? new File("/twdataconfig/ReverseType") : new File("/sdcard/.Tcfg/ReverseType");
                    if (file.exists()) {
                        bufferedReader = new BufferedReader(new FileReader(file));
                        try {
                            String line = bufferedReader.readLine();
                            int i6 = Integer.parseInt(line);
                            Log.e("HYH", "ReverseFileUtils->getReverseTypeFromFile:" + file.canRead() + "," + line);
                            try {
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            i2 = i6;
                        } catch (Exception e2) {
                            bufferedReader2 = bufferedReader;
                            e = e2;
                            Log.e("HYH", "ReverseFileUtils->getReverseTypeFromFile:" + e.toString());
                            e.printStackTrace();
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            i2 = 1;
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = null;
                }
            } catch (Exception e5) {
                e = e5;
            }
            rc = i2;
        }
        sc = b("sys/class/car_reverse/tvdtype");
        StringBuilder sbE = b.a.a.a.a.e("tvdtype == ");
        sbE.append(sc);
        sbE.append("nostart 360 = ");
        sbE.append(rc);
        Log.d("TWService", sbE.toString());
        if (sc == 0) {
            tWService.H(rc);
        } else {
            tWService.H(1);
        }
        if (tWService.Aa != 0) {
            return;
        }
        if (SystemProperties.getInt("persist.tw.bt.module", 2) != 0) {
            tWService.pa.write(65296, 1);
        }
        tWService.pa.write(515, 255);
        tWService.sendBroadcastAsUser(new Intent("com.unisound.intent.action.ACC_ON"), UserHandle.CURRENT);
        Log.d("TWService", "startBootService--");
        try {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.tw.bt", "com.tw.bt.ATBluetoothService"));
            tWService.startService(intent2);
            if (tWService.Da()) {
                tWService.g("com.tw.bt.send", "com.tw.bt.send.ATBluetoothService");
            }
            tWService.g("com.dofun.carsetting", "com.dofun.carsetting.config.ConfigService");
            tWService.g("com.tw.fan", "com.tw.fan.FAN_Service");
            if (tWService.t() != null && tWService.t().contains("KED18-0395")) {
                tWService.g("syntax.org.il.suzukitalk", "syntax.org.il.suzukitalk.services.LocationService");
            }
            String str3 = tWService.eb;
            if (str3 != null && str3.contains("ZST")) {
                tWService.g("auto.dvd.system.dvdautosystemhelper", "auto.dvd.system.dvdautosystemhelper.service.BootService");
            }
            if (tWService.Eb) {
                com.tw.service.a.f.getInstance().pa();
            }
            String str4 = tWService.eb;
            if (str4 != null && str4.contains("ZYH32")) {
                try {
                    Intent intent3 = new Intent();
                    intent3.setPackage("com.example.clxycontrolservice");
                    intent3.setAction("com.example.clxycontrolservice.services.CheckSystemAppStatusService");
                    tWService.startForegroundService(intent3);
                } catch (Exception unused2) {
                }
                try {
                    Intent intent4 = new Intent();
                    intent4.setPackage("com.example.clxycontroldaemonservice");
                    intent4.setAction("com.example.clxycontroldaemonservice.daemon");
                    tWService.startForegroundService(intent4);
                } catch (Exception unused3) {
                }
                tWService.pa.write(40730, 1, 0, "pm disable com.dofun.carsetting/com.dofun.carsetting.activity.apkinstall.InstallActivity");
            }
            tWService.h("il.co.uzeb.targetitur", "il.co.uzeb.targetitur.TIService");
            tWService.h("com.ecar.assistantnew", "com.ecar.assistantnew.service.BootService");
            if (tWService.t().contains("LHQC9")) {
                tWService.g("com.lkj.blueoceancarmachine", "com.lkj.blueoceancarmachine.module.main.service.LocationService");
            }
            tWService.ua();
            Intent intent5 = new Intent("com.txznet.smartadapter.service.TXZService");
            intent5.setPackage("com.txznet.smartadapter");
            tWService.startService(intent5);
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        if (rc == 1) {
            tWService.g("com.tw.reverse", "com.tw.reverse.RadarService");
        }
        if (tWService.t().contains("ZRSJ")) {
            tWService.mHandler.sendEmptyMessageDelayed(65313, 3000L);
        }
        tWService.mHandler.removeMessages(65315);
        tWService.mHandler.sendEmptyMessageDelayed(65315, 3000L);
        tWService.mHandler.removeMessages(65331);
        tWService.mHandler.sendEmptyMessageDelayed(65331, 10000L);
        if (tWService.t().contains("ZHT1")) {
            tWService.mHandler.sendEmptyMessageDelayed(65303, 3000L);
        }
        if (tWService.t().contains("ZST11")) {
            tWService.g("com.ligo.acarsmartserver", "com.ligo.acarsmartserver.service.CarSmartService");
        }
        if (tWService.t().contains("ZST9")) {
            tWService.g("com.ankai.cardvr", "com.ankai.cardvr.service.MainService");
        }
        tWService.mHandler.postDelayed(new V(tWService), 500L);
        tWService.ta();
        if (tWService.ta != 0) {
            if (rc != 1) {
                tWService.Na();
                return;
            }
            return;
        }
        Log.d("TWService", "startBootActivity-source= " + i + " ,isStartTA= " + tWService.Ca);
        tWService.mHandler.removeMessages(65290);
        tWService.mHandler.removeMessages(65304);
        if (tWService.Ca) {
            tWService.Da = true;
            tWService.bb = 0;
            tWService.mHandler.sendEmptyMessageDelayed(65290, 5000L);
            i5 = 3000;
        } else {
            tWService.mHandler.removeMessages(65304);
            tWService.bb = i;
            switch (i) {
                case 0:
                case 6:
                default:
                    i4 = 0;
                    break;
                case 1:
                case 3:
                case 5:
                case 9:
                    if (tWService.wb > 0) {
                        tWService.mHandler.sendEmptyMessageDelayed(65304, 3000L);
                    }
                    i4 = 4000;
                    break;
                case 2:
                case 4:
                case 8:
                case 10:
                    tWService.mHandler.sendEmptyMessageDelayed(65304, 0L);
                    i4 = 1000;
                    break;
                case 7:
                case 11:
                    tWService.mHandler.sendEmptyMessageDelayed(65304, 1000L);
                    i4 = 2000;
                    break;
            }
            if (i != 0) {
                tWService.Ka();
            }
            i5 = i4;
        }
        if (tWService.pa.write(65521) == 35) {
            tWService.mHandler.sendEmptyMessageDelayed(65301, 1000L);
        }
        tWService.mHandler.removeMessages(65302);
        if (rc == 1 || Settings.System.getInt(tWService.getContentResolver(), "SYSTEM_360", 1) != 1) {
            String str5 = tWService.eb;
            if (str5 == null || !str5.contains("LDFY")) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("startBootActivity(): !isAutomatic");
                    sb.append(!tWService.ib);
                    Log.d("TWService", sb.toString());
                    if (!tWService.ib) {
                        String packageName = com.tw.service.b.d.getCustomPipAppComponentName().getPackageName();
                        int i7 = SystemProperties.getInt("sys.tw.forcepip", 0);
                        String str6 = SystemProperties.get("sys.df.desktop", "");
                        Log.d("TWService", "getCustomPipAppComponentName():" + packageName + ",pipState:" + i7 + ",pipAreaType:" + str6);
                        if (!"custom_app".equals(str6) || !"cn.cardoor.zt360".equals(packageName) || i7 != 1) {
                            Intent launchIntentForPackage = tWService.getPackageManager().getLaunchIntentForPackage("cn.cardoor.zt360");
                            launchIntentForPackage.putExtra("activityGone", true);
                            launchIntentForPackage.setFlags(268451840);
                            tWService.startActivity(launchIntentForPackage);
                        }
                    }
                } catch (Exception e7) {
                    StringBuilder sbE2 = b.a.a.a.a.e("start360Activity:");
                    sbE2.append(e7.getMessage());
                    Log.d("TWService", sbE2.toString());
                }
            } else {
                tWService.g("com.baony.tw360", "com.baony.ui.service.TWSingleCanBusService");
            }
        } else {
            tWService.mHandler.sendEmptyMessageDelayed(65302, i5);
        }
        if (tWService.Ca) {
            tWService.mHandler.removeMessages(65290);
            tWService.mHandler.sendEmptyMessageDelayed(65290, 5000L);
        }
        if (tWService.t().contains("Dat")) {
            tWService.mHandler.removeMessages(65312);
            tWService.mHandler.sendEmptyMessageDelayed(65312, 5000L);
        }
        if (tWService.t().contains("YFD")) {
            tWService.mHandler.sendEmptyMessageDelayed(65360, 15000L);
        }
        if (SystemProperties.getInt("persist.camera.tvtype", 0) == 0) {
            tWService.pa.write(40730, 1, 0, "pm enable com.tw.tv/com.tw.tv.TVActivity2");
            laVar = tWService.pa;
            str = "pm disable com.tw.tv/com.tw.tv.HDMIActivity";
        } else {
            tWService.pa.write(40730, 1, 0, "pm enable com.tw.tv/com.tw.tv.HDMIActivity");
            laVar = tWService.pa;
            str = "pm disable com.tw.tv/com.tw.tv.TVActivity2";
        }
        laVar.write(40730, 1, 0, str);
        if (Settings.System.getInt(tWService.getContentResolver(), "ISSHOW_STREAM_MEDIA_APK", 0) == 1) {
            laVar2 = tWService.pa;
            str2 = "pm enable com.tw.reverse/com.tw.reverse.StreamMediaActivity";
        } else {
            laVar2 = tWService.pa;
            str2 = "pm disable com.tw.reverse/com.tw.reverse.StreamMediaActivity";
        }
        laVar2.write(40730, 1, 0, str2);
        tWService.mHandler.sendEmptyMessageDelayed(65320, 30000L);
        if (tWService.t().contains("NaviBar")) {
            tWService.pa.write(40730, 1, 0, "chmod 777 twdataconfig");
            tWService.pa.write(40730, 1, 0, "chmod 777 twdataconfig/NaviBar-theme");
            tWService.pa.write(40730, 1, 0, "chmod 777 twdataconfig/NaviBar-theme/SystemUINavibar.apk");
            tWService.pa.write(40730, 1, 0, "/system/bin/pm install -r /twdataconfig/NaviBar-theme/SystemUINavibar.apk");
        }
        if (tWService.t().contains("YUY1")) {
            Settings.System.putInt(tWService.getContentResolver(), "ISSHOW_FILE", 0);
        }
    }

    private void a(byte[] bArr, int i, int i2) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/sys/class/tw/gtp/touch");
            try {
                fileOutputStream.write(bArr, i, i2);
            } catch (Exception unused) {
            } finally {
                fileOutputStream.close();
            }
        } catch (Exception unused2) {
        }
    }

    private void a(String str, boolean z) {
        la laVar;
        StringBuilder sb;
        if (z) {
            if (new File("/system/priv-app/" + str + "/" + str + ".apk").canRead() || !new File(b.a.a.a.a.b("/system/etc/apk/", str, ".apk")).canRead()) {
                return;
            }
            this.pa.write(40730, 1, 0, "mount -o remount,rw /");
            this.pa.write(40730, 1, 0, b.a.a.a.a.a("mkdir -p /system/priv-app/", str));
            this.pa.write(40730, 1, 0, b.a.a.a.a.a("chown root.root /system/priv-app/", str));
            this.pa.write(40730, 1, 0, b.a.a.a.a.a("chmod 755 /system/priv-app/", str));
            this.pa.write(40730, 1, 0, "ln /system/etc/apk/" + str + ".apk /system/priv-app/" + str + "/" + str + ".apk");
            la laVar2 = this.pa;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("chown root.root /system/priv-app/");
            sb2.append(str);
            sb2.append("/");
            sb2.append(str);
            sb2.append(".apk");
            laVar2.write(40730, 1, 0, sb2.toString());
            this.pa.write(40730, 1, 0, "chmod 644 /system/priv-app/" + str + "/" + str + ".apk");
            this.pa.write(40730, 3, 0, "mount -o remount,ro /");
            return;
        }
        if (new File("/system/priv-app/" + str + "/" + str + ".apk").canRead()) {
            this.pa.write(40730, 1, 0, "mount -o remount,rw /");
            if (new File(b.a.a.a.a.b("/system/etc/apk/", str, ".apk")).exists()) {
                laVar = this.pa;
                sb = new StringBuilder();
                sb.append("rm -r /system/priv-app/");
                sb.append(str);
            } else {
                this.pa.write(40730, 1, 0, "mv /system/priv-app/" + str + "/" + str + ".apk /system/etc/apk/" + str + ".apk");
                this.pa.write(40730, 1, 0, b.a.a.a.a.a("rm -r /system/priv-app/", str));
                this.pa.write(40730, 1, 0, b.a.a.a.a.b("chown root.root /system/etc/apk/", str, ".apk"));
                laVar = this.pa;
                sb = new StringBuilder();
                sb.append("chmod 644 /system/etc/apk/");
                sb.append(str);
                sb.append(".apk");
            }
            laVar.write(40730, 1, 0, sb.toString());
            this.pa.write(40730, 3, 0, "mount -o remount,ro /");
        }
    }
}
