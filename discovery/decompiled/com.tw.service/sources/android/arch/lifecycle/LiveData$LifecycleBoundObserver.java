package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
class LiveData$LifecycleBoundObserver extends j implements GenericLifecycleObserver {
    final e mOwner;
    final /* synthetic */ l n;

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void a(e eVar, Lifecycle$Event lifecycle$Event) {
        if (this.mOwner.d().getCurrentState() == Lifecycle$State.DESTROYED) {
            this.n.a((m) null);
        } else {
            b(i());
        }
    }

    @Override // android.arch.lifecycle.j
    void h() {
        this.mOwner.d().a(this);
    }

    @Override // android.arch.lifecycle.j
    boolean i() {
        return this.mOwner.d().getCurrentState().compareTo(Lifecycle$State.STARTED) >= 0;
    }
}
