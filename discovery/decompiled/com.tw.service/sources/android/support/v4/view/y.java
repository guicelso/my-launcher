package android.support.v4.view;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/* JADX INFO: loaded from: classes.dex */
class y extends C0027c {
    final /* synthetic */ ViewPager n;

    y(ViewPager viewPager) {
        this.n = viewPager;
    }

    @Override // android.support.v4.view.C0027c
    public void a(View view, android.support.v4.view.a.b bVar) {
        super.a(view, bVar);
        bVar.setClassName(ViewPager.class.getName());
        k kVar = this.n.mAdapter;
        bVar.setScrollable(kVar != null && kVar.getCount() > 1);
        if (this.n.canScrollHorizontally(1)) {
            bVar.addAction(4096);
        }
        if (this.n.canScrollHorizontally(-1)) {
            bVar.addAction(8192);
        }
    }

    @Override // android.support.v4.view.C0027c
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        k kVar;
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        accessibilityEvent.setClassName(ViewPager.class.getName());
        k kVar2 = this.n.mAdapter;
        accessibilityEvent.setScrollable(kVar2 != null && kVar2.getCount() > 1);
        if (accessibilityEvent.getEventType() != 4096 || (kVar = this.n.mAdapter) == null) {
            return;
        }
        accessibilityEvent.setItemCount(kVar.getCount());
        accessibilityEvent.setFromIndex(this.n.Rg);
        accessibilityEvent.setToIndex(this.n.Rg);
    }

    @Override // android.support.v4.view.C0027c
    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        ViewPager viewPager;
        int i2;
        if (super.performAccessibilityAction(view, i, bundle)) {
            return true;
        }
        if (i != 4096) {
            if (i != 8192 || !this.n.canScrollHorizontally(-1)) {
                return false;
            }
            viewPager = this.n;
            i2 = viewPager.Rg - 1;
        } else {
            if (!this.n.canScrollHorizontally(1)) {
                return false;
            }
            viewPager = this.n;
            i2 = viewPager.Rg + 1;
        }
        viewPager.setCurrentItem(i2);
        return true;
    }
}
