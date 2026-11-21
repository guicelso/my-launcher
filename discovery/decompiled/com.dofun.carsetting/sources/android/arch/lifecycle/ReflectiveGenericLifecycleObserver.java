package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final Object f14a;
    private final a.C0002a b;

    ReflectiveGenericLifecycleObserver(Object obj) {
        this.f14a = obj;
        this.b = a.f16a.b(this.f14a.getClass());
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void a(d dVar, Lifecycle.Event event) {
        this.b.a(dVar, event, this.f14a);
    }
}
