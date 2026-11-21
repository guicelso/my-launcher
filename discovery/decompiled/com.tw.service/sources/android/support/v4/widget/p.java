package android.support.v4.widget;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.C0027c;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ScrollView;

/* JADX INFO: loaded from: classes.dex */
class p extends C0027c {
    p() {
    }

    @Override // android.support.v4.view.C0027c
    public void a(View view, android.support.v4.view.a.b bVar) {
        int iGa;
        super.a(view, bVar);
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        bVar.setClassName(ScrollView.class.getName());
        if (!nestedScrollView.isEnabled() || (iGa = nestedScrollView.ga()) <= 0) {
            return;
        }
        bVar.setScrollable(true);
        if (nestedScrollView.getScrollY() > 0) {
            bVar.addAction(8192);
        }
        if (nestedScrollView.getScrollY() < iGa) {
            bVar.addAction(4096);
        }
    }

    @Override // android.support.v4.view.C0027c
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        accessibilityEvent.setClassName(ScrollView.class.getName());
        accessibilityEvent.setScrollable(nestedScrollView.ga() > 0);
        accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
        accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
        int scrollX = nestedScrollView.getScrollX();
        int i = Build.VERSION.SDK_INT;
        accessibilityEvent.setMaxScrollX(scrollX);
        int iGa = nestedScrollView.ga();
        int i2 = Build.VERSION.SDK_INT;
        accessibilityEvent.setMaxScrollY(iGa);
    }

    @Override // android.support.v4.view.C0027c
    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (super.performAccessibilityAction(view, i, bundle)) {
            return true;
        }
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        if (!nestedScrollView.isEnabled()) {
            return false;
        }
        if (i == 4096) {
            int iMin = Math.min(nestedScrollView.getScrollY() + ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.ga());
            if (iMin == nestedScrollView.getScrollY()) {
                return false;
            }
            nestedScrollView.smoothScrollTo(0, iMin);
            return true;
        }
        if (i != 8192) {
            return false;
        }
        int iMax = Math.max(nestedScrollView.getScrollY() - ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
        if (iMax == nestedScrollView.getScrollY()) {
            return false;
        }
        nestedScrollView.smoothScrollTo(0, iMax);
        return true;
    }
}
