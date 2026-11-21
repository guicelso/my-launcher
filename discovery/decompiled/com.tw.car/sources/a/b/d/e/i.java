package a.b.d.e;

import android.os.Handler;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public class i implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Callable f107a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Handler f108b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ k.a f109c;

    public i(k kVar, Callable callable, Handler handler, k.a aVar) {
        this.f107a = callable;
        this.f108b = handler;
        this.f109c = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object objCall;
        try {
            objCall = this.f107a.call();
        } catch (Exception unused) {
            objCall = null;
        }
        this.f108b.post(new h(this, objCall));
    }
}
