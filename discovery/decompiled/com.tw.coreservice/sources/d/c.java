package d;

import android.util.Log;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static boolean f36a = l.f55a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final String f37b = c.class.getName();

    public static String a(String str, Object... objArr) {
        String str2;
        if (objArr.length != 0) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        for (int i = 2; i < stackTrace.length; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            if (!stackTraceElement.getClassName().equals(f37b)) {
                str2 = "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
                return String.format(Locale.US, "thread[%s, %d] %s: %s", Thread.currentThread().getName(), Long.valueOf(Thread.currentThread().getId()), str2, str);
            }
        }
        str2 = "<unknown>";
        return String.format(Locale.US, "thread[%s, %d] %s: %s", Thread.currentThread().getName(), Long.valueOf(Thread.currentThread().getId()), str2, str);
    }

    public static void b(String str, String str2, Object... objArr) {
        if (f36a) {
            Log.d(str, a(str2, objArr));
        }
    }

    public static void c(String str, String str2, Object... objArr) {
        if (f36a) {
            Log.e(str, a(str2, objArr));
        }
    }

    public static void d(String str, Object... objArr) {
        if (f36a) {
            c("DFLog", str, objArr);
        }
    }
}
