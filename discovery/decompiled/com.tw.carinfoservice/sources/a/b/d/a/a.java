package a.b.d.a;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class a extends a.b.d.b.a {

    /* JADX INFO: renamed from: a.b.d.a.a$a, reason: collision with other inner class name */
    public static class RunnableC0003a implements Runnable {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String[] f40b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final /* synthetic */ Activity f41c;
        public final /* synthetic */ int d;

        public RunnableC0003a(String[] strArr, Activity activity, int i) {
            this.f40b = strArr;
            this.f41c = activity;
            this.d = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            int[] iArr = new int[this.f40b.length];
            PackageManager packageManager = this.f41c.getPackageManager();
            String packageName = this.f41c.getPackageName();
            int length = this.f40b.length;
            for (int i = 0; i < length; i++) {
                iArr[i] = packageManager.checkPermission(this.f40b[i], packageName);
            }
            ((b) this.f41c).a(this.d, this.f40b, iArr);
        }
    }

    public interface b {
        void a(int i, String[] strArr, int[] iArr);
    }

    public interface c {
        void a(int i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void c(Activity activity, String[] strArr, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity instanceof c) {
                ((c) activity).a(i);
            }
            activity.requestPermissions(strArr, i);
        } else if (activity instanceof b) {
            new Handler(Looper.getMainLooper()).post(new RunnableC0003a(strArr, activity, i));
        }
    }
}
