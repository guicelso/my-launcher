package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
class FullLifecycleObserverAdapter implements GenericLifecycleObserver {
    private final FullLifecycleObserver mObserver;

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void a(e eVar, Lifecycle$Event lifecycle$Event) {
        switch (lifecycle$Event) {
            case ON_CREATE:
                this.mObserver.b(eVar);
                return;
            case ON_START:
                this.mObserver.c(eVar);
                return;
            case ON_RESUME:
                this.mObserver.e(eVar);
                return;
            case ON_PAUSE:
                this.mObserver.d(eVar);
                return;
            case ON_STOP:
                this.mObserver.f(eVar);
                return;
            case ON_DESTROY:
                this.mObserver.a(eVar);
                return;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
            default:
                return;
        }
    }
}
