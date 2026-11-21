package android.support.v4.view;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class m implements View.OnClickListener {
    final /* synthetic */ PagerTabStrip n;

    m(PagerTabStrip pagerTabStrip) {
        this.n = pagerTabStrip;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        ViewPager viewPager = this.n.qg;
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
}
