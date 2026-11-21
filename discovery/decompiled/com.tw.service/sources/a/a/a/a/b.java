package a.a.a.a;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
final class b implements Executor {
    b() {
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        c.getInstance().a(runnable);
    }
}
