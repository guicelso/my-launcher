package c.b.b.f;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import android.util.Log;
import com.tw.qcarcamlib.CamParamConstant;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static int f478a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static String f479b = "";

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static boolean f480c = false;

    public static void A(int i) {
        SystemProperties.set("sys.camera.ch", i + "");
    }

    public static void B(int i) {
        SystemProperties.set(CamParamConstant.AUX.PERSIST_CAM_TYPE, String.format(Locale.US, "%d", Integer.valueOf(i)));
    }

    @SuppressLint({"SuspiciousIndentation"})
    public static void C(TWUtil tWUtil, int i) {
        if (tWUtil != null) {
            tWUtil.write(40730, 1, 0, "rwrite /sys/devices/tp2850/channel " + i);
        }
    }

    public static void D(String str, int i) {
        SystemProperties.set(str, String.format(Locale.US, "%d", Integer.valueOf(i)));
    }

    public static void E(String str, int i, Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(context.getPackageName(), 0).edit();
        editorEdit.putInt(str, i);
        editorEdit.commit();
    }

    public static void F(TWUtil tWUtil) {
        try {
            tWUtil.write(40730, 1, 0, "rwrite /sys/class/sensor_i2c/lrpos " + SystemProperties.getInt("persist.sys.tw.offset", 128));
        } catch (Exception e) {
            Log.e("DeviceInfoUtil", Log.getStackTraceString(e));
        }
    }

    public static void G(b bVar, Context context) {
        try {
            bVar.write(40730, 1, 0, Settings.System.getString(context.getContentResolver(), "TWCameraOffset") + SystemProperties.getInt(Settings.System.getString(context.getContentResolver(), "TWCameraOffsetSave"), 128));
        } catch (Exception e) {
            Log.e("DeviceInfoUtil", Log.getStackTraceString(e));
        }
    }

    @SuppressLint({"SuspiciousIndentation"})
    public static void H(TWUtil tWUtil, Context context) {
        if (Settings.System.getInt(context.getContentResolver(), "TWCameraShowLayoutCamera", 0) == 1) {
            return;
        }
        int i = Settings.System.getInt(context.getContentResolver(), "AUX_CAMERA_INDEX", 0);
        f478a = i;
        if (i == b() || tWUtil == null) {
            return;
        }
        StringBuilder sbB = c.a.a.a.a.b("rwrite /sys/devices/tp2850/type ");
        sbB.append(f478a);
        tWUtil.write(40730, 1, 0, sbB.toString());
    }

    public static int a(String str, int i, Context context) {
        return context.getSharedPreferences(context.getPackageName(), 0).getInt(str, i);
    }

    public static int b() {
        return c("/sys/devices/tp2850/type");
    }

    public static int c(String str) throws Throwable {
        int i = 0;
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
                try {
                    i = Integer.parseInt(bufferedReader2.readLine());
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
            }
        } catch (Exception unused3) {
        } catch (Throwable th2) {
            th = th2;
        }
        return i;
    }

    public static String d() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        if (!TextUtils.isEmpty(f479b)) {
            return f479b;
        }
        String str = "";
        try {
            str = SystemProperties.get("ro.systemtw.version");
            if (TextUtils.isEmpty(str)) {
                str = SystemProperties.get("ro.tw.version");
            }
            BufferedReader bufferedReader2 = null;
            try {
                bufferedReader = new BufferedReader(new FileReader("/system/etc/version"));
                try {
                    String line = bufferedReader.readLine();
                    if (line != null && line.length() > 0) {
                        str = line;
                    }
                    bufferedReader.close();
                } catch (Exception unused) {
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader == null) {
                        throw th;
                    }
                    bufferedReader.close();
                    throw th;
                }
            } catch (Exception unused2) {
            } catch (Throwable th3) {
                bufferedReader = null;
                th = th3;
            }
        } catch (Exception unused3) {
        }
        f479b = str;
        return str;
    }

    public static boolean e() {
        String str = Build.MODEL;
        return d().startsWith("V13") || "T100".equals(str) || "A100".equals(str) || "A133".equals(str) || "A133 pro".equals(str);
    }

    public static boolean f() {
        if (!d().startsWith("V6.4.3") && !d().startsWith("V8.3.2") && !d().startsWith("V8.2.3")) {
            if (!(d().startsWith("V8.1.0") || d().startsWith("V8.1.2"))) {
                if (!(d().startsWith("V8.1.8") || d().startsWith("V8.1.1"))) {
                    if (!(d().startsWith("V13.0.0") || d().startsWith("V13.0.0")) && !SystemProperties.get("ro.board.platform").equals("t3")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean g() {
        return d().startsWith("V15");
    }

    public static boolean h() {
        String str = Build.MODEL;
        return d().startsWith("V18.") || str.equals("earth") || str.equals("T537");
    }

    public static boolean i() {
        return d().startsWith("T5Q") || "QUAD-CORE T507".equals(Build.MODEL);
    }

    public static boolean j() {
        return d().startsWith("V19.1.1");
    }

    public static boolean k() {
        return d().startsWith("V7.1.1") || d().startsWith("V7.2.1") || d().startsWith("V7.3.1") || d().startsWith("V7.3.2") || d().startsWith("V7.5.1") || d().startsWith("V7.5.2") || "QUAD-CORE t7 p1".equals(Build.MODEL);
    }

    public static boolean l() {
        return d().startsWith("V9.3.1");
    }

    public static boolean m() {
        return d().startsWith("V9.4.1");
    }

    public static boolean n() {
        return "trinket for arm64".equals(Build.MODEL);
    }

    public static boolean o() {
        return d().startsWith("TS10.");
    }

    public static boolean p() {
        if (TextUtils.isEmpty(f479b)) {
            f479b = d();
        }
        return !TextUtils.isEmpty(f479b) && f479b.startsWith("V17");
    }

    public static boolean q() {
        return d().startsWith("TS10S");
    }

    public static boolean r() {
        String str = Build.MODEL;
        return d().startsWith("TS18.1.2") || d().startsWith("TS18.2.2") || d().startsWith("TS18.3.1") || d().startsWith("TS18A") || (!TextUtils.isEmpty(str) && str.contains("uis8581a2h10"));
    }

    public static boolean s() {
        return d().startsWith("V20.") || d().startsWith("V20_");
    }

    public static boolean t() {
        return d().startsWith("V12.1.1");
    }

    public static boolean u() {
        return d().startsWith("V10.4.5") || "SP9832A".equals(Build.MODEL);
    }

    public static boolean v() {
        return d().startsWith("V11.1.1") || d().startsWith("V11.1.2") || "sp9853i_1h10_vmm".equals(Build.MODEL);
    }

    @SuppressLint({"SuspiciousIndentation"})
    public static void w(TWUtil tWUtil, int i) {
        if (tWUtil != null) {
            tWUtil.write(40730, 1, 0, "rwrite /sys/devices/tp2815/channel " + i);
        }
    }

    @SuppressLint({"SuspiciousIndentation"})
    public static void x(TWUtil tWUtil, Context context) {
        int i = Settings.System.getInt(context.getContentResolver(), "AUX_CAMERA_INDEX", 0);
        f478a = i;
        if (i == b() || tWUtil == null) {
            return;
        }
        StringBuilder sbB = c.a.a.a.a.b("rwrite /sys/devices/tp2815/type ");
        sbB.append(f478a);
        tWUtil.write(40730, 1, 0, sbB.toString());
    }

    public static void y(b bVar, int i, int i2, int i3) {
        StringBuilder sb;
        String str;
        if (bVar != null) {
            if (s() || p()) {
                throw null;
            }
            if (!g() && !e() && !i() && !j() && !h()) {
                if (l() || m()) {
                    bVar.write(40721, i);
                    bVar.write(40722, i2);
                    bVar.write(40723, i3);
                    return;
                }
                return;
            }
            if (f480c) {
                bVar.write(40730, 1, 0, "rwrite /sys/devices/tp2815/brightness " + i);
                bVar.write(40730, 1, 0, "rwrite /sys/devices/tp2815/contrast " + i2);
                sb = new StringBuilder();
                str = "rwrite /sys/devices/tp2815/saturation ";
            } else {
                bVar.write(40730, 1, 0, "rwrite /sys/devices/tp2850/brightness " + i);
                bVar.write(40730, 1, 0, "rwrite /sys/devices/tp2850/contrast " + i2);
                sb = new StringBuilder();
                str = "rwrite /sys/devices/tp2850/saturation ";
            }
            sb.append(str);
            sb.append(i3);
            bVar.write(40730, 1, 0, sb.toString());
        }
    }

    public static void z(b bVar, int i, int i2, int i3, Context context) {
        bVar.write(40730, 1, 0, Settings.System.getString(context.getContentResolver(), "TWCameraBrightness") + i);
        bVar.write(40730, 1, 0, Settings.System.getString(context.getContentResolver(), "TWCameraContrast") + i2);
        bVar.write(40730, 1, 0, Settings.System.getString(context.getContentResolver(), "TWCameraSaturation") + i3);
    }
}
