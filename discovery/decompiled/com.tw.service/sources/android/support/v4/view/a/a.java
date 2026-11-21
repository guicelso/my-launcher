package android.support.v4.view.a;

import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

/* JADX INFO: loaded from: classes.dex */
public class a {
    final Object mAction;
    public static final a ACTION_FOCUS = new a(1, null);
    public static final a ACTION_CLEAR_FOCUS = new a(2, null);

    static {
        new a(4, null);
        new a(8, null);
        new a(16, null);
        new a(32, null);
        new a(64, null);
        new a(128, null);
        new a(256, null);
        new a(512, null);
        new a(1024, null);
        new a(2048, null);
        new a(4096, null);
        new a(8192, null);
        new a(16384, null);
        new a(32768, null);
        new a(65536, null);
        new a(131072, null);
        new a(262144, null);
        new a(524288, null);
        new a(1048576, null);
        new a(2097152, null);
        int i = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN);
        int i2 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION);
        int i3 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
        int i4 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT);
        int i5 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
        int i6 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT);
        int i7 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK);
        int i8 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        int i9 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW);
        int i10 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP);
        int i11 = Build.VERSION.SDK_INT;
        new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP);
    }

    public a(int i, CharSequence charSequence) {
        int i2 = Build.VERSION.SDK_INT;
        this.mAction = new AccessibilityNodeInfo.AccessibilityAction(i, charSequence);
    }

    a(Object obj) {
        this.mAction = obj;
    }
}
