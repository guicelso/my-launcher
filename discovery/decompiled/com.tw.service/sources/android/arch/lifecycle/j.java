package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
abstract class j {
    int ha;
    boolean mActive;
    final m mObserver;
    final /* synthetic */ l n;

    void b(boolean z) {
        if (z == this.mActive) {
            return;
        }
        this.mActive = z;
        boolean z2 = this.n.mActiveCount == 0;
        this.n.mActiveCount += this.mActive ? 1 : -1;
        if (z2 && this.mActive) {
            this.n.onActive();
        }
        if (this.n.mActiveCount == 0 && !this.mActive) {
            this.n.k();
        }
        if (this.mActive) {
            this.n.b(this);
        }
    }

    void h() {
    }

    abstract boolean i();
}
