package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
public class SingleGeneratedAdapterObserver implements GenericLifecycleObserver {
    private final b ma;

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void a(e eVar, Lifecycle$Event lifecycle$Event) {
        this.ma.a(eVar, lifecycle$Event, false, null);
        this.ma.a(eVar, lifecycle$Event, true, null);
    }
}
