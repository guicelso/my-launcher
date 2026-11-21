package c.b.a.k;

import android.util.Log;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final String f431a = "c.b.a.k.b";

    public static void a(String str, String str2, Object... objArr) {
        String string;
        String str3 = String.format(Locale.US, str2, objArr);
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        for (int i = 2; i < stackTrace.length; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            if (!stackTraceElement.getClassName().equals(f431a)) {
                StringBuilder sbB = c.a.a.a.a.b("(");
                sbB.append(stackTraceElement.getFileName());
                sbB.append(":");
                sbB.append(stackTraceElement.getLineNumber());
                sbB.append(")");
                string = sbB.toString();
                Log.e(str, String.format(Locale.US, "thread[%s, %d] %s: %s", Thread.currentThread().getName(), Long.valueOf(Thread.currentThread().getId()), string, str3));
            }
        }
        string = "<unknown>";
        Log.e(str, String.format(Locale.US, "thread[%s, %d] %s: %s", Thread.currentThread().getName(), Long.valueOf(Thread.currentThread().getId()), string, str3));
    }
}
