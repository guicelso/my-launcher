package android.support.v4.app;

/* JADX INFO: loaded from: classes.dex */
class E implements Runnable {
    final /* synthetic */ F n;

    E(F f) {
        this.n = f;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.n.mView.setLayerType(0, null);
    }
}
