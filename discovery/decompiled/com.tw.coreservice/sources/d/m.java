package d;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.os.SystemProperties;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static String f56a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static String f57b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static String f58c;

    static {
        Objects.toString(Environment.getExternalStorageDirectory());
        String str = File.separator;
    }

    public static String a() throws Throwable {
        String str;
        String str2;
        Throwable th;
        BufferedReader bufferedReader;
        String line;
        Throwable th2;
        BufferedReader bufferedReader2;
        Throwable th3;
        BufferedReader bufferedReader3;
        Throwable th4;
        BufferedReader bufferedReader4;
        if (!TextUtils.isEmpty(f56a)) {
            return f56a;
        }
        String str3 = null;
        try {
            if (TextUtils.isEmpty(f57b)) {
                try {
                    bufferedReader4 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/3305"));
                    try {
                        String line2 = bufferedReader4.readLine();
                        f57b = line2;
                        bufferedReader4.close();
                        str = line2;
                    } catch (Exception unused) {
                        if (bufferedReader4 != null) {
                            bufferedReader4.close();
                        }
                        str = null;
                    } catch (Throwable th5) {
                        th4 = th5;
                        if (bufferedReader4 == null) {
                            throw th4;
                        }
                        bufferedReader4.close();
                        throw th4;
                    }
                } catch (Exception unused2) {
                    bufferedReader4 = null;
                } catch (Throwable th6) {
                    th4 = th6;
                    bufferedReader4 = null;
                }
            } else {
                str = f57b;
            }
        } catch (Exception unused3) {
        }
        try {
            if (TextUtils.isEmpty(f58c)) {
                try {
                    bufferedReader3 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/1d35"));
                    try {
                        String line3 = bufferedReader3.readLine();
                        f58c = line3;
                        bufferedReader3.close();
                        str2 = line3;
                    } catch (Exception unused4) {
                        if (bufferedReader3 != null) {
                            bufferedReader3.close();
                        }
                        str2 = null;
                    } catch (Throwable th7) {
                        th3 = th7;
                        if (bufferedReader3 == null) {
                            throw th3;
                        }
                        bufferedReader3.close();
                        throw th3;
                    }
                } catch (Exception unused5) {
                    bufferedReader3 = null;
                } catch (Throwable th8) {
                    th3 = th8;
                    bufferedReader3 = null;
                }
            } else {
                str2 = f58c;
            }
        } catch (Exception unused6) {
        }
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/cid"));
                try {
                    line = bufferedReader.readLine();
                    bufferedReader.close();
                } catch (Exception unused7) {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    line = "0";
                } catch (Throwable th9) {
                    th = th9;
                    if (bufferedReader == null) {
                        throw th;
                    }
                    bufferedReader.close();
                    throw th;
                }
            } catch (Exception unused8) {
                line = "0";
            }
        } catch (Exception unused9) {
            bufferedReader = null;
        } catch (Throwable th10) {
            th = th10;
            bufferedReader = null;
        }
        if (!TextUtils.equals(str, line) || "00000000000000000000000000000000".equals(str)) {
            if (!TextUtils.equals(str2, line) || "00000000000000000000000000000000".equals(str2)) {
                try {
                    try {
                        bufferedReader2 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/0394"));
                        try {
                            String line4 = bufferedReader2.readLine();
                            bufferedReader2.close();
                            str3 = line4;
                        } catch (Exception unused10) {
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                        } catch (Throwable th11) {
                            th2 = th11;
                            if (bufferedReader2 == null) {
                                throw th2;
                            }
                            bufferedReader2.close();
                            throw th2;
                        }
                    } catch (Exception unused11) {
                    }
                } catch (Exception unused12) {
                    bufferedReader2 = null;
                } catch (Throwable th12) {
                    th2 = th12;
                    bufferedReader2 = null;
                }
                str = (TextUtils.isEmpty(str3) || "00000000000000000000000000000000".equals(str3)) ? line : str3;
            } else {
                str = str2;
            }
        }
        f56a = str;
        return str;
    }

    public static String b(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT <= 23) {
            return context.getResources().getConfiguration().locale.toString();
        }
        try {
            Configuration configuration = context.getResources().getConfiguration();
            Method declaredMethod = Configuration.class.getDeclaredMethod("getLocales", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(configuration, new Object[0]);
            Method declaredMethod2 = objInvoke.getClass().getDeclaredMethod("get", Integer.TYPE);
            declaredMethod2.setAccessible(true);
            locale = (Locale) declaredMethod2.invoke(objInvoke, 0);
        } catch (Exception e) {
            e.printStackTrace();
            locale = null;
        }
        if (locale == null) {
            return "";
        }
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    public static String c() {
        return SystemProperties.get("ro.tw.version");
    }

    public static int d(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            c.d("Tools", e);
            return -1;
        }
    }

    public static boolean e(Closeable closeable) {
        if (closeable == null) {
            return true;
        }
        try {
            closeable.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int f(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }
}
