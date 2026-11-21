package a.b.d.h;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.widget.ScrollView;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final View.AccessibilityDelegate f143b = new View.AccessibilityDelegate();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final View.AccessibilityDelegate f144a = new a(this);

    public static final class a extends View.AccessibilityDelegate {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final b f145a;

        public a(b bVar) {
            this.f145a = bVar;
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            Objects.requireNonNull(this.f145a);
            return b.f143b.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
        }

        @Override // android.view.View.AccessibilityDelegate
        public AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
            Objects.requireNonNull(this.f145a);
            AccessibilityNodeProvider accessibilityNodeProvider = b.f143b.getAccessibilityNodeProvider(view);
            a.b.d.h.l.a aVar = accessibilityNodeProvider != null ? new a.b.d.h.l.a(accessibilityNodeProvider) : null;
            if (aVar != null) {
                return (AccessibilityNodeProvider) aVar.f152a;
            }
            return null;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            Objects.requireNonNull((NestedScrollView.a) this.f145a);
            b.f143b.onInitializeAccessibilityEvent(view, accessibilityEvent);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityEvent.setClassName(ScrollView.class.getName());
            accessibilityEvent.setScrollable(nestedScrollView.getScrollRange() > 0);
            accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
            accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
            accessibilityEvent.setMaxScrollX(nestedScrollView.getScrollX());
            accessibilityEvent.setMaxScrollY(nestedScrollView.getScrollRange());
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            int scrollRange;
            Objects.requireNonNull((NestedScrollView.a) this.f145a);
            b.f143b.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityNodeInfo.setClassName(ScrollView.class.getName());
            if (!nestedScrollView.isEnabled() || (scrollRange = nestedScrollView.getScrollRange()) <= 0) {
                return;
            }
            accessibilityNodeInfo.setScrollable(true);
            if (nestedScrollView.getScrollY() > 0) {
                accessibilityNodeInfo.addAction(8192);
            }
            if (nestedScrollView.getScrollY() < scrollRange) {
                accessibilityNodeInfo.addAction(4096);
            }
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            Objects.requireNonNull(this.f145a);
            b.f143b.onPopulateAccessibilityEvent(view, accessibilityEvent);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            Objects.requireNonNull(this.f145a);
            return b.f143b.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0040, code lost:
        
            if (r4 != r3.getScrollY()) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0062, code lost:
        
            if (r4 != r3.getScrollY()) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0064, code lost:
        
            r3.w(0 - r3.getScrollX(), r4 - r3.getScrollY());
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0071, code lost:
        
            return true;
         */
        @Override // android.view.View.AccessibilityDelegate
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean performAccessibilityAction(android.view.View r3, int r4, android.os.Bundle r5) {
            /*
                r2 = this;
                a.b.d.h.b r0 = r2.f145a
                android.support.v4.widget.NestedScrollView$a r0 = (android.support.v4.widget.NestedScrollView.a) r0
                java.util.Objects.requireNonNull(r0)
                android.view.View$AccessibilityDelegate r0 = a.b.d.h.b.f143b
                boolean r5 = r0.performAccessibilityAction(r3, r4, r5)
                r0 = 1
                if (r5 == 0) goto L11
                goto L71
            L11:
                android.support.v4.widget.NestedScrollView r3 = (android.support.v4.widget.NestedScrollView) r3
                boolean r5 = r3.isEnabled()
                r1 = 0
                if (r5 != 0) goto L1c
            L1a:
                r0 = 0
                goto L71
            L1c:
                r5 = 4096(0x1000, float:5.74E-42)
                if (r4 == r5) goto L43
                r5 = 8192(0x2000, float:1.14794E-41)
                if (r4 == r5) goto L25
                goto L1a
            L25:
                int r4 = r3.getHeight()
                int r5 = r3.getPaddingBottom()
                int r4 = r4 - r5
                int r5 = r3.getPaddingTop()
                int r4 = r4 - r5
                int r5 = r3.getScrollY()
                int r5 = r5 - r4
                int r4 = java.lang.Math.max(r5, r1)
                int r5 = r3.getScrollY()
                if (r4 == r5) goto L1a
                goto L64
            L43:
                int r4 = r3.getHeight()
                int r5 = r3.getPaddingBottom()
                int r4 = r4 - r5
                int r5 = r3.getPaddingTop()
                int r4 = r4 - r5
                int r5 = r3.getScrollY()
                int r5 = r5 + r4
                int r4 = r3.getScrollRange()
                int r4 = java.lang.Math.min(r5, r4)
                int r5 = r3.getScrollY()
                if (r4 == r5) goto L1a
            L64:
                int r5 = r3.getScrollX()
                int r1 = r1 - r5
                int r5 = r3.getScrollY()
                int r4 = r4 - r5
                r3.w(r1, r4)
            L71:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: a.b.d.h.b.a.performAccessibilityAction(android.view.View, int, android.os.Bundle):boolean");
        }

        @Override // android.view.View.AccessibilityDelegate
        public void sendAccessibilityEvent(View view, int i) {
            Objects.requireNonNull(this.f145a);
            b.f143b.sendAccessibilityEvent(view, i);
        }

        @Override // android.view.View.AccessibilityDelegate
        public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
            Objects.requireNonNull(this.f145a);
            b.f143b.sendAccessibilityEventUnchecked(view, accessibilityEvent);
        }
    }
}
