package com.tw.service.b;

import android.app.AlarmManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.LocaleList;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.tw.service.la;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private Context mContext;
    private la pa = la.open();

    public c(Context context) {
        this.mContext = context;
    }

    private boolean k(String str, String str2) {
        try {
            if (!str.equals("bt_ini")) {
                if (!str.equals("tw_theme")) {
                    return false;
                }
                if (new File(str2).exists()) {
                    this.pa.write(40730, 3, 0, "rm -rf /data/tw/theme/*");
                    this.pa.write(40730, 3, 0, "cp -rf " + str2 + "/* /data/tw/theme/");
                    this.pa.write(40730, 1, 0, "chown -R system.system /data/tw/theme");
                    this.pa.write(40730, 1, 0, "chmod 777 -R /data/tw/theme/");
                    this.pa.write(40730, 3, 0, "sync");
                }
                return true;
            }
            Log.d("ParseCfg", "parseFile key=" + str + "value=" + str2);
            if (new File(str2).exists()) {
                String str3 = null;
                if (str2 != null && str2.endsWith("BtSettings.INI")) {
                    str3 = " /data/bluesoleil/BtSettings.INI";
                } else if (str2 != null && str2.endsWith("bt_conf.ini")) {
                    str3 = " /data/goc/bt_conf.ini";
                }
                if (str3 != null) {
                    this.pa.write(40730, 1, 0, "cp " + str2 + str3);
                    this.pa.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                    this.pa.write(40730, 1, 0, "chown 1000.1000 " + str3);
                    this.pa.write(40730, 3, 0, "chmod 666 " + str3);
                }
            }
            return true;
        } catch (Exception e) {
            Log.e("ParseCfg", Log.getStackTraceString(e));
            return true;
        }
    }

    private boolean l(String str, String str2) {
        if (str.equals("left_right_seat")) {
            this.pa.write(267, 16, Integer.parseInt(str2));
            Settings.System.putString(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", str2);
            return true;
        }
        if (str.equals("tw_pip")) {
            SystemProperties.set("persist.tw.forcepip", str2);
            return true;
        }
        if (str.equals("ap_5g_channel")) {
            SystemProperties.set("persist.5g.channel", str2);
            return true;
        }
        if (str.equals("persist.tw.cid")) {
            SystemProperties.set("persist.tw.cid", str2);
            return true;
        }
        if (!str.equals("carsetting_wallpaper")) {
            return false;
        }
        Settings.System.putString(this.mContext.getContentResolver(), "MAIN_BG_CHOOSE", str2);
        return true;
    }

    private boolean m(String str, String str2) {
        try {
            if (str.equals("timezone")) {
                Log.d("ParseCfg", "parseSystem key=" + str + "value=" + str2);
                ((AlarmManager) this.mContext.getSystemService("alarm")).setTimeZone(str2);
                return true;
            }
            if (str.equals("fontScale")) {
                Log.d("ParseCfg", "parseSystem key=" + str + "value=" + str2);
                new Thread(new a(this, str2)).start();
                return true;
            }
            if (str.equals("locale")) {
                Log.d("ParseCfg", "parseSystem key=" + str + "value=" + str2);
                new Thread(new b(this, str2)).start();
                return true;
            }
            if (!str.equals("gms_setting")) {
                return false;
            }
            Log.d("ParseCfg", "parseSystem key=" + str + "value=" + str2);
            Settings.Global.putInt(this.mContext.getContentResolver(), "SYSTEM_TW_GMS", Integer.parseInt(str2));
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    protected void a(LocaleList localeList) {
        if (localeList != null) {
            try {
                Class<?> cls = Class.forName("android.app.ActivityManagerNative");
                Object objInvoke = cls.getDeclaredMethod("getDefault", new Class[0]).invoke(cls, new Object[0]);
                Class<?> cls2 = Class.forName("android.app.IActivityManager");
                Configuration configuration = (Configuration) cls2.getDeclaredMethod("getConfiguration", new Class[0]).invoke(objInvoke, new Object[0]);
                configuration.setLocales(localeList);
                cls2.getDeclaredMethod("updatePersistentConfiguration", Configuration.class).invoke(objInvoke, configuration);
            } catch (Exception e) {
                StringBuilder sbE = b.a.a.a.a.e("updateLanguage: ");
                sbE.append(e.getLocalizedMessage());
                Log.d("ParseCfg", sbE.toString());
            }
        }
    }

    public void b(String str, String str2) throws Throwable {
        BufferedReader bufferedReader;
        if (!b.a.a.a.a.f(str)) {
            return;
        }
        Log.d("ParseCfg", "parseCfg path=" + str + "name=" + str2);
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader(str + "/" + str2));
                    while (true) {
                        try {
                            try {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    bufferedReader.close();
                                    return;
                                }
                                try {
                                    String[] strArrSplit = line.split("=");
                                    if (!l(strArrSplit[0], strArrSplit[1]) && !m(strArrSplit[0], strArrSplit[1]) && !c(strArrSplit[0], strArrSplit[1])) {
                                        k(strArrSplit[0], str + "/" + strArrSplit[1]);
                                    }
                                } catch (Exception unused) {
                                }
                            } catch (Exception e) {
                                e = e;
                                bufferedReader2 = bufferedReader;
                                Log.e("ParseCfg", Log.getStackTraceString(e));
                                if (bufferedReader2 != null) {
                                    bufferedReader2.close();
                                    return;
                                }
                                return;
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public boolean c(String str, String str2) {
        try {
            if (!str.equals("Sleep")) {
                return false;
            }
            this.pa.write(260, 5, Integer.valueOf(str2).intValue());
            return false;
        } catch (Exception e) {
            Log.e("ParseCfg", Log.getStackTraceString(e));
            return true;
        }
    }
}
