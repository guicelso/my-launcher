package android.support.v4.widget;

import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.C0027c;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* JADX INFO: loaded from: classes.dex */
class r extends C0027c {
    private final Rect mTmpRect = new Rect();
    final /* synthetic */ SlidingPaneLayout n;

    r(SlidingPaneLayout slidingPaneLayout) {
        this.n = slidingPaneLayout;
    }

    @Override // android.support.v4.view.C0027c
    public void a(View view, android.support.v4.view.a.b bVar) {
        android.support.v4.view.a.b bVarA = android.support.v4.view.a.b.a(bVar);
        super.a(view, bVarA);
        Rect rect = this.mTmpRect;
        bVarA.getBoundsInParent(rect);
        bVar.setBoundsInParent(rect);
        bVarA.getBoundsInScreen(rect);
        bVar.setBoundsInScreen(rect);
        bVar.setVisibleToUser(bVarA.isVisibleToUser());
        bVar.setPackageName(bVarA.getPackageName());
        bVar.setClassName(bVarA.getClassName());
        bVar.setContentDescription(bVarA.getContentDescription());
        bVar.setEnabled(bVarA.isEnabled());
        bVar.setClickable(bVarA.isClickable());
        bVar.setFocusable(bVarA.isFocusable());
        bVar.setFocused(bVarA.isFocused());
        bVar.setAccessibilityFocused(bVarA.isAccessibilityFocused());
        bVar.setSelected(bVarA.isSelected());
        bVar.setLongClickable(bVarA.isLongClickable());
        bVar.addAction(bVarA.getActions());
        bVar.setMovementGranularities(bVarA.getMovementGranularities());
        bVarA.recycle();
        bVar.setClassName(SlidingPaneLayout.class.getName());
        bVar.setSource(view);
        Object objG = android.support.v4.view.r.g(view);
        if (objG instanceof View) {
            bVar.setParent((View) objG);
        }
        int childCount = this.n.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.n.getChildAt(i);
            if (!this.n.I(childAt) && childAt.getVisibility() == 0) {
                int i2 = Build.VERSION.SDK_INT;
                childAt.setImportantForAccessibility(1);
                bVar.addChild(childAt);
            }
        }
    }

    @Override // android.support.v4.view.C0027c
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        accessibilityEvent.setClassName(SlidingPaneLayout.class.getName());
    }

    @Override // android.support.v4.view.C0027c
    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        if (this.n.I(view)) {
            return false;
        }
        return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }
}
