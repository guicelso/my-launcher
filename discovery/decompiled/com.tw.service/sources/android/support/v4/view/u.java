package android.support.v4.view;

/* JADX INFO: loaded from: classes.dex */
class u implements Runnable {
    final /* synthetic */ ViewPager n;

    u(ViewPager viewPager) {
        this.n = viewPager;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.n.setScrollState(0);
        this.n.populate();
    }
}
