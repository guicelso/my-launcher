package android.support.v4.app;

/* JADX INFO: loaded from: classes.dex */
class N {
    private final boolean vd;
    private final C0004e wd;
    private int xd;

    N(C0004e c0004e, boolean z) {
        this.vd = z;
        this.wd = c0004e;
    }

    public void cancelTransaction() {
        C0004e c0004e = this.wd;
        c0004e.mManager.a(c0004e, this.vd, false, false);
    }

    public void completeTransaction() {
        boolean z = this.xd > 0;
        O o = this.wd.mManager;
        int size = o.mAdded.size();
        for (int i = 0; i < size; i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) o.mAdded.get(i);
            componentCallbacksC0013n.a((N) null);
            if (z) {
                C0011l c0011l = componentCallbacksC0013n.mAnimationInfo;
                if (c0011l == null ? false : c0011l.mEnterTransitionPostponed) {
                    componentCallbacksC0013n.startPostponedEnterTransition();
                }
            }
        }
        C0004e c0004e = this.wd;
        c0004e.mManager.a(c0004e, this.vd, !z, true);
    }

    public boolean isReady() {
        return this.xd == 0;
    }

    public void onStartEnterTransition() {
        this.xd--;
        if (this.xd != 0) {
            return;
        }
        this.wd.mManager.ab();
    }

    public void startListening() {
        this.xd++;
    }
}
