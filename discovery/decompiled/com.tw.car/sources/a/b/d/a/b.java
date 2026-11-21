package a.b.d.a;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class b extends a.b.d.b.a {

    public interface a {
        void a(int i, String[] strArr, int[] iArr);
    }

    /* JADX INFO: renamed from: a.b.d.a.b$b, reason: collision with other inner class name */
    public interface InterfaceC0001b {
        void a(int i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void a(Activity activity, String[] strArr, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity instanceof InterfaceC0001b) {
                ((InterfaceC0001b) activity).a(i);
            }
            activity.requestPermissions(strArr, i);
        } else if (activity instanceof a) {
            new Handler(Looper.getMainLooper()).post(new a.b.d.a.a(strArr, activity, i));
        }
    }
}
