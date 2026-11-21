package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
class v implements j {
    private final Rect mTempRect = new Rect();
    final /* synthetic */ ViewPager n;

    v(ViewPager viewPager) {
        this.n = viewPager;
    }

    @Override // android.support.v4.view.j
    public D a(View view, D d) {
        D dA = r.a(view, d);
        if (dA.isConsumed()) {
            return dA;
        }
        Rect rect = this.mTempRect;
        rect.left = dA.getSystemWindowInsetLeft();
        rect.top = dA.getSystemWindowInsetTop();
        rect.right = dA.getSystemWindowInsetRight();
        rect.bottom = dA.getSystemWindowInsetBottom();
        int childCount = this.n.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.n.getChildAt(i);
            int i2 = Build.VERSION.SDK_INT;
            WindowInsets windowInsets = (WindowInsets) D.a(dA);
            WindowInsets windowInsetsDispatchApplyWindowInsets = childAt.dispatchApplyWindowInsets(windowInsets);
            if (windowInsetsDispatchApplyWindowInsets != windowInsets) {
                windowInsets = new WindowInsets(windowInsetsDispatchApplyWindowInsets);
            }
            D dWrap = D.wrap(windowInsets);
            rect.left = Math.min(dWrap.getSystemWindowInsetLeft(), rect.left);
            rect.top = Math.min(dWrap.getSystemWindowInsetTop(), rect.top);
            rect.right = Math.min(dWrap.getSystemWindowInsetRight(), rect.right);
            rect.bottom = Math.min(dWrap.getSystemWindowInsetBottom(), rect.bottom);
        }
        return dA.replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
    }
}
