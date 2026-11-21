package a.b.d.h.a;

import a.b.e.b.j;
import android.graphics.Rect;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final AccessibilityNodeInfo f149a;

    public a(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.f149a = accessibilityNodeInfo;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || a.class != obj.getClass()) {
            return false;
        }
        a aVar = (a) obj;
        AccessibilityNodeInfo accessibilityNodeInfo = this.f149a;
        if (accessibilityNodeInfo == null) {
            if (aVar.f149a != null) {
                return false;
            }
        } else if (!accessibilityNodeInfo.equals(aVar.f149a)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.f149a;
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        return accessibilityNodeInfo.hashCode();
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        Rect rect = new Rect();
        this.f149a.getBoundsInParent(rect);
        sb.append("; boundsInParent: " + rect);
        this.f149a.getBoundsInScreen(rect);
        sb.append("; boundsInScreen: " + rect);
        sb.append("; packageName: ");
        sb.append(this.f149a.getPackageName());
        sb.append("; className: ");
        sb.append(this.f149a.getClassName());
        sb.append("; text: ");
        sb.append(this.f149a.getText());
        sb.append("; contentDescription: ");
        sb.append(this.f149a.getContentDescription());
        sb.append("; viewId: ");
        int i = Build.VERSION.SDK_INT;
        sb.append(this.f149a.getViewIdResourceName());
        sb.append("; checkable: ");
        sb.append(this.f149a.isCheckable());
        sb.append("; checked: ");
        sb.append(this.f149a.isChecked());
        sb.append("; focusable: ");
        sb.append(this.f149a.isFocusable());
        sb.append("; focused: ");
        sb.append(this.f149a.isFocused());
        sb.append("; selected: ");
        sb.append(this.f149a.isSelected());
        sb.append("; clickable: ");
        sb.append(this.f149a.isClickable());
        sb.append("; longClickable: ");
        sb.append(this.f149a.isLongClickable());
        sb.append("; enabled: ");
        sb.append(this.f149a.isEnabled());
        sb.append("; password: ");
        sb.append(this.f149a.isPassword());
        sb.append("; scrollable: " + this.f149a.isScrollable());
        sb.append("; [");
        int actions = this.f149a.getActions();
        while (actions != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(actions);
            actions &= iNumberOfTrailingZeros ^ (-1);
            if (iNumberOfTrailingZeros == 1) {
                str = "ACTION_FOCUS";
            } else if (iNumberOfTrailingZeros != 2) {
                switch (iNumberOfTrailingZeros) {
                    case 4:
                        str = "ACTION_SELECT";
                        break;
                    case 8:
                        str = "ACTION_CLEAR_SELECTION";
                        break;
                    case 16:
                        str = "ACTION_CLICK";
                        break;
                    case j.AppCompatTheme_activityChooserViewStyle /* 32 */:
                        str = "ACTION_LONG_CLICK";
                        break;
                    case j.AppCompatTheme_dropDownListViewStyle /* 64 */:
                        str = "ACTION_ACCESSIBILITY_FOCUS";
                        break;
                    case 128:
                        str = "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
                        break;
                    case 256:
                        str = "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
                        break;
                    case 512:
                        str = "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
                        break;
                    case 1024:
                        str = "ACTION_NEXT_HTML_ELEMENT";
                        break;
                    case 2048:
                        str = "ACTION_PREVIOUS_HTML_ELEMENT";
                        break;
                    case 4096:
                        str = "ACTION_SCROLL_FORWARD";
                        break;
                    case 8192:
                        str = "ACTION_SCROLL_BACKWARD";
                        break;
                    case 16384:
                        str = "ACTION_COPY";
                        break;
                    case 32768:
                        str = "ACTION_PASTE";
                        break;
                    case 65536:
                        str = "ACTION_CUT";
                        break;
                    case 131072:
                        str = "ACTION_SET_SELECTION";
                        break;
                    default:
                        str = "ACTION_UNKNOWN";
                        break;
                }
            } else {
                str = "ACTION_CLEAR_FOCUS";
            }
            sb.append(str);
            if (actions != 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
