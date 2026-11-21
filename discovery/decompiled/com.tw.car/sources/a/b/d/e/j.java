package a.b.d.e;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class j implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AtomicReference f110a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Callable f111b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ReentrantLock f112c;
    public final /* synthetic */ AtomicBoolean d;
    public final /* synthetic */ Condition e;

    public j(k kVar, AtomicReference atomicReference, Callable callable, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
        this.f110a = atomicReference;
        this.f111b = callable;
        this.f112c = reentrantLock;
        this.d = atomicBoolean;
        this.e = condition;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f110a.set(this.f111b.call());
        } catch (Exception unused) {
        }
        this.f112c.lock();
        try {
            this.d.set(false);
            this.e.signal();
        } finally {
            this.f112c.unlock();
        }
    }
}
