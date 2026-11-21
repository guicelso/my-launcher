package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {
    private final a mInfo;
    private final Object mWrapped;

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void a(e eVar, Lifecycle$Event lifecycle$Event) {
        this.mInfo.a(eVar, lifecycle$Event, this.mWrapped);
    }
}
