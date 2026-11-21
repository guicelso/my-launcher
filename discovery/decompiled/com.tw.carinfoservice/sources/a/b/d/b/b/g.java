package a.b.d.b.b;

import a.b.e.f.l;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class g {

    public class a implements Runnable {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Typeface f62b;

        public a(Typeface typeface) {
            this.f62b = typeface;
        }

        @Override // java.lang.Runnable
        public void run() {
            g.this.c(this.f62b);
        }
    }

    public class b implements Runnable {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f64b;

        public b(int i) {
            this.f64b = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            Objects.requireNonNull((l.a) g.this);
        }
    }

    public final void a(int i, Handler handler) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(new b(i));
    }

    public final void b(Typeface typeface, Handler handler) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(new a(typeface));
    }

    public abstract void c(Typeface typeface);
}
