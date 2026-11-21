package android.support.v4.widget;

import android.graphics.Rect;
import android.support.v4.view.C0027c;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class j extends C0027c {
    private final Rect mTmpRect = new Rect();
    final /* synthetic */ DrawerLayout n;

    j(DrawerLayout drawerLayout) {
        this.n = drawerLayout;
    }

    @Override // android.support.v4.view.C0027c
    public void a(View view, android.support.v4.view.a.b bVar) {
        if (DrawerLayout.Zh) {
            super.a(view, bVar);
        } else {
            android.support.v4.view.a.b bVarA = android.support.v4.view.a.b.a(bVar);
            super.a(view, bVarA);
            bVar.setSource(view);
            Object objG = android.support.v4.view.r.g(view);
            if (objG instanceof View) {
                bVar.setParent((View) objG);
            }
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
            bVarA.recycle();
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (DrawerLayout.y(childAt)) {
                    bVar.addChild(childAt);
                }
            }
        }
        bVar.setClassName(DrawerLayout.class.getName());
        bVar.setFocusable(false);
        bVar.setFocused(false);
        bVar.a(android.support.v4.view.a.a.ACTION_FOCUS);
        bVar.a(android.support.v4.view.a.a.ACTION_CLEAR_FOCUS);
    }

    @Override // android.support.v4.view.C0027c
    public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
        }
        List<CharSequence> text = accessibilityEvent.getText();
        View viewZ = this.n.Z();
        if (viewZ == null) {
            return true;
        }
        CharSequence charSequenceX = this.n.x(this.n.w(viewZ));
        if (charSequenceX == null) {
            return true;
        }
        text.add(charSequenceX);
        return true;
    }

    @Override // android.support.v4.view.C0027c
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        accessibilityEvent.setClassName(DrawerLayout.class.getName());
    }

    @Override // android.support.v4.view.C0027c
    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        if (DrawerLayout.Zh || DrawerLayout.y(view)) {
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
        return false;
    }
}
