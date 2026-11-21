package a.b.d.b.a;

import android.graphics.Typeface;

/* JADX INFO: loaded from: classes.dex */
public class h implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Typeface f65a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ j f66b;

    public h(j jVar, Typeface typeface) {
        this.f66b = jVar;
        this.f65a = typeface;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f66b.a(this.f65a);
    }
}
