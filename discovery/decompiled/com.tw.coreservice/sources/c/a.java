package c;

import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public abstract class a implements Runnable {
    public final void b() {
        Executors.newCachedThreadPool().execute(this);
    }

    public abstract void c();

    @Override // java.lang.Runnable
    public final void run() {
        c();
    }
}
