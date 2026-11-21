package android.support.v4.view;

import android.view.View;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
class C implements Comparator {
    C() {
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) ((View) obj).getLayoutParams();
        ViewPager.LayoutParams layoutParams2 = (ViewPager.LayoutParams) ((View) obj2).getLayoutParams();
        boolean z = layoutParams.isDecor;
        if (z != layoutParams2.isDecor) {
            return z ? 1 : -1;
        }
        return layoutParams.position - layoutParams2.position;
    }
}
