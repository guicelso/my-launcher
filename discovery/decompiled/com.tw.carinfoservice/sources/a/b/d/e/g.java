package a.b.d.e;

import android.os.Handler;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public class g implements Runnable {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Callable f107b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Handler f108c;
    public final /* synthetic */ f.c d;

    public class a implements Runnable {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Object f109b;

        public a(Object obj) {
            this.f109b = obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            g.this.d.a(this.f109b);
        }
    }

    public g(f fVar, Callable callable, Handler handler, f.c cVar) {
        this.f107b = callable;
        this.f108c = handler;
        this.d = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object objCall;
        try {
            objCall = this.f107b.call();
        } catch (Exception unused) {
            objCall = null;
        }
        this.f108c.post(new a(objCall));
    }
}
