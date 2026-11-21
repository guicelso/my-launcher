package a.a.a.a;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
final class a implements Executor {
    a() {
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        c.getInstance().b(runnable);
    }
}
