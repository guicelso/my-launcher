package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
public class CompositeGeneratedAdaptersObserver implements GenericLifecycleObserver {
    private final b[] W;

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void a(e eVar, Lifecycle$Event lifecycle$Event) {
        k kVar = new k();
        for (b bVar : this.W) {
            bVar.a(eVar, lifecycle$Event, false, kVar);
        }
        for (b bVar2 : this.W) {
            bVar2.a(eVar, lifecycle$Event, true, kVar);
        }
    }
}
