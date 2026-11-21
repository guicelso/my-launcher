package a.b.d.a;

import android.app.Activity;
import android.content.pm.PackageManager;

/* JADX INFO: loaded from: classes.dex */
class a implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String[] f39a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Activity f40b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f41c;

    public a(String[] strArr, Activity activity, int i) {
        this.f39a = strArr;
        this.f40b = activity;
        this.f41c = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        int[] iArr = new int[this.f39a.length];
        PackageManager packageManager = this.f40b.getPackageManager();
        String packageName = this.f40b.getPackageName();
        int length = this.f39a.length;
        for (int i = 0; i < length; i++) {
            iArr[i] = packageManager.checkPermission(this.f39a[i], packageName);
        }
        ((b.a) this.f40b).a(this.f41c, this.f39a, iArr);
    }
}
