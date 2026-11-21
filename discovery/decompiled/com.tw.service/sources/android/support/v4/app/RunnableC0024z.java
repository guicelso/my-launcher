package android.support.v4.app;

/* JADX INFO: renamed from: android.support.v4.app.z, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0024z implements Runnable {
    final /* synthetic */ O n;

    RunnableC0024z(O o) {
        this.n = o;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.n.execPendingActions();
    }
}
