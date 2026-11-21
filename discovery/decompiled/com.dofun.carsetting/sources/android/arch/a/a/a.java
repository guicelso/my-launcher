package android.arch.a.a;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: ArchTaskExecutor.java */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class a extends c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static volatile a f1a;

    @NonNull
    private static final Executor d = new Executor() { // from class: android.arch.a.a.a.1
        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            a.a().b(runnable);
        }
    };

    @NonNull
    private static final Executor e = new Executor() { // from class: android.arch.a.a.a.2
        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            a.a().a(runnable);
        }
    };

    @NonNull
    private c c = new b();

    @NonNull
    private c b = this.c;

    private a() {
    }

    @NonNull
    public static a a() {
        if (f1a != null) {
            return f1a;
        }
        synchronized (a.class) {
            if (f1a == null) {
                f1a = new a();
            }
        }
        return f1a;
    }

    @Override // android.arch.a.a.c
    public void a(Runnable runnable) {
        this.b.a(runnable);
    }

    @Override // android.arch.a.a.c
    public void b(Runnable runnable) {
        this.b.b(runnable);
    }

    @Override // android.arch.a.a.c
    public boolean b() {
        return this.b.b();
    }
}
