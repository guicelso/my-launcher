package android.support.v4.view;

import android.database.DataSetObserver;

/* JADX INFO: loaded from: classes.dex */
class n extends DataSetObserver implements A, z {
    final /* synthetic */ PagerTitleStrip n;
    private int zc;

    n(PagerTitleStrip pagerTitleStrip) {
        this.n = pagerTitleStrip;
    }

    @Override // android.database.DataSetObserver
    public void onChanged() {
        PagerTitleStrip pagerTitleStrip = this.n;
        pagerTitleStrip.a(pagerTitleStrip.qg.getCurrentItem(), this.n.qg.getAdapter());
        float f = this.n.vg;
        if (f < 0.0f) {
            f = 0.0f;
        }
        PagerTitleStrip pagerTitleStrip2 = this.n;
        pagerTitleStrip2.a(pagerTitleStrip2.qg.getCurrentItem(), f, true);
    }

    public void onPageScrollStateChanged(int i) {
        this.zc = i;
    }

    public void onPageScrolled(int i, float f, int i2) {
        if (f > 0.5f) {
            i++;
        }
        this.n.a(i, f, false);
    }

    public void onPageSelected(int i) {
        if (this.zc == 0) {
            PagerTitleStrip pagerTitleStrip = this.n;
            pagerTitleStrip.a(pagerTitleStrip.qg.getCurrentItem(), this.n.qg.getAdapter());
            float f = this.n.vg;
            if (f < 0.0f) {
                f = 0.0f;
            }
            PagerTitleStrip pagerTitleStrip2 = this.n;
            pagerTitleStrip2.a(pagerTitleStrip2.qg.getCurrentItem(), f, true);
        }
    }
}
