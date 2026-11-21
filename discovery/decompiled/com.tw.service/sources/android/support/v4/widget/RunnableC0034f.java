package android.support.v4.widget;

/* JADX INFO: renamed from: android.support.v4.widget.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0034f implements Runnable {
    final /* synthetic */ ContentLoadingProgressBar n;

    RunnableC0034f(ContentLoadingProgressBar contentLoadingProgressBar) {
        this.n = contentLoadingProgressBar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.n.setVisibility(8);
    }
}
