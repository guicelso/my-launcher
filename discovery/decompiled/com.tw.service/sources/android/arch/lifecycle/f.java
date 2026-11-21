package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
class f {
    GenericLifecycleObserver ga;
    Lifecycle$State mState;

    void b(e eVar, Lifecycle$Event lifecycle$Event) {
        Lifecycle$State lifecycle$StateA = g.a(lifecycle$Event);
        this.mState = g.a(this.mState, lifecycle$StateA);
        this.ga.a(eVar, lifecycle$Event);
        this.mState = lifecycle$StateA;
    }
}
