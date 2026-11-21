package android.support.v4.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeProvider;

/* JADX INFO: renamed from: android.support.v4.view.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0027c {
    private static final View.AccessibilityDelegate Re = new View.AccessibilityDelegate();
    private final View.AccessibilityDelegate Qe = new C0026b(this);

    View.AccessibilityDelegate J() {
        return this.Qe;
    }

    public void a(View view, android.support.v4.view.a.b bVar) {
        Re.onInitializeAccessibilityNodeInfo(view, bVar.unwrap());
    }

    public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        return Re.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public android.support.v4.view.a.c getAccessibilityNodeProvider(View view) {
        int i = Build.VERSION.SDK_INT;
        AccessibilityNodeProvider accessibilityNodeProvider = Re.getAccessibilityNodeProvider(view);
        if (accessibilityNodeProvider != null) {
            return new android.support.v4.view.a.c(accessibilityNodeProvider);
        }
        return null;
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        Re.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        Re.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return Re.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        int i2 = Build.VERSION.SDK_INT;
        return Re.performAccessibilityAction(view, i, bundle);
    }

    public void sendAccessibilityEvent(View view, int i) {
        Re.sendAccessibilityEvent(view, i);
    }

    public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
        Re.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }
}
