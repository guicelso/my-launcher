package android.support.v4.widget;

/* JADX INFO: renamed from: android.support.v4.widget.g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0035g implements Runnable {
    final /* synthetic */ ContentLoadingProgressBar n;

    RunnableC0035g(ContentLoadingProgressBar contentLoadingProgressBar) {
        this.n = contentLoadingProgressBar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.n.mDismissed) {
            return;
        }
        System.currentTimeMillis();
        this.n.setVisibility(0);
    }
}
