package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
class FullLifecycleObserverAdapter implements GenericLifecycleObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final FullLifecycleObserver f9a;

    FullLifecycleObserverAdapter(FullLifecycleObserver fullLifecycleObserver) {
        this.f9a = fullLifecycleObserver;
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void a(d dVar, Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                this.f9a.a(dVar);
                return;
            case ON_START:
                this.f9a.b(dVar);
                return;
            case ON_RESUME:
                this.f9a.c(dVar);
                return;
            case ON_PAUSE:
                this.f9a.d(dVar);
                return;
            case ON_STOP:
                this.f9a.e(dVar);
                return;
            case ON_DESTROY:
                this.f9a.f(dVar);
                return;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
            default:
                return;
        }
    }
}
