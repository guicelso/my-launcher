package android.support.v4.app;

/* JADX INFO: renamed from: android.support.v4.app.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0008i implements Runnable {
    final /* synthetic */ ComponentCallbacksC0013n n;

    RunnableC0008i(ComponentCallbacksC0013n componentCallbacksC0013n) {
        this.n = componentCallbacksC0013n;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.n.Va();
    }
}
