package a.b.e.f;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;

/* JADX INFO: loaded from: classes.dex */
public class qa implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static qa f370a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static qa f371b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final View f372c;
    public final CharSequence d;
    public final int e;
    public final Runnable f = new oa(this);
    public final Runnable g = new pa(this);
    public int h;
    public int i;
    public ra j;
    public boolean k;

    public qa(View view, CharSequence charSequence) {
        this.f372c = view;
        this.d = charSequence;
        this.e = a.b.d.h.o.a(ViewConfiguration.get(this.f372c.getContext()));
        a();
        this.f372c.setOnLongClickListener(this);
        this.f372c.setOnHoverListener(this);
    }

    public static void a(qa qaVar) {
        qa qaVar2 = f370a;
        if (qaVar2 != null) {
            qaVar2.f372c.removeCallbacks(qaVar2.f);
        }
        f370a = qaVar;
        qa qaVar3 = f370a;
        if (qaVar3 != null) {
            qaVar3.f372c.postDelayed(qaVar3.f, ViewConfiguration.getLongPressTimeout());
        }
    }

    public final void a() {
        this.h = Integer.MAX_VALUE;
        this.i = Integer.MAX_VALUE;
    }

    public void b() {
        if (f371b == this) {
            f371b = null;
            ra raVar = this.j;
            if (raVar != null) {
                raVar.a();
                this.j = null;
                a();
                this.f372c.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        if (f370a == this) {
            a((qa) null);
        }
        this.f372c.removeCallbacks(this.g);
    }

    @Override // android.view.View.OnHoverListener
    public boolean onHover(View view, MotionEvent motionEvent) {
        boolean z;
        if (this.j != null && this.k) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.f372c.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            if (action == 10) {
                a();
                b();
            }
        } else if (this.f372c.isEnabled() && this.j == null) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (Math.abs(x - this.h) > this.e || Math.abs(y - this.i) > this.e) {
                this.h = x;
                this.i = y;
                z = true;
            } else {
                z = false;
            }
            if (z) {
                a(this);
            }
        }
        return false;
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        this.h = view.getWidth() / 2;
        this.i = view.getHeight() / 2;
        a(true);
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        b();
    }

    /* JADX WARN: Failed to analyze thrown exceptions
    java.util.ConcurrentModificationException
    	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
    	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:117)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
     */
    public void a(boolean z) {
        int height;
        int i;
        if (a.b.d.h.n.f(this.f372c)) {
            a((qa) null);
            qa qaVar = f371b;
            if (qaVar != null) {
                qaVar.b();
            }
            f371b = this;
            this.k = z;
            this.j = new ra(this.f372c.getContext());
            ra raVar = this.j;
            View view = this.f372c;
            int width = this.h;
            int i2 = this.i;
            boolean z2 = this.k;
            CharSequence charSequence = this.d;
            if (raVar.b()) {
                raVar.a();
            }
            raVar.f376c.setText(charSequence);
            WindowManager.LayoutParams layoutParams = raVar.d;
            layoutParams.token = view.getApplicationWindowToken();
            int dimensionPixelOffset = raVar.f374a.getResources().getDimensionPixelOffset(a.b.e.b.d.tooltip_precise_anchor_threshold);
            if (view.getWidth() < dimensionPixelOffset) {
                width = view.getWidth() / 2;
            }
            if (view.getHeight() >= dimensionPixelOffset) {
                int dimensionPixelOffset2 = raVar.f374a.getResources().getDimensionPixelOffset(a.b.e.b.d.tooltip_precise_anchor_extra_offset);
                height = i2 + dimensionPixelOffset2;
                i = i2 - dimensionPixelOffset2;
            } else {
                height = view.getHeight();
                i = 0;
            }
            layoutParams.gravity = 49;
            int dimensionPixelOffset3 = raVar.f374a.getResources().getDimensionPixelOffset(z2 ? a.b.e.b.d.tooltip_y_offset_touch : a.b.e.b.d.tooltip_y_offset_non_touch);
            View rootView = view.getRootView();
            ViewGroup.LayoutParams layoutParams2 = rootView.getLayoutParams();
            if (!(layoutParams2 instanceof WindowManager.LayoutParams) || ((WindowManager.LayoutParams) layoutParams2).type != 2) {
                for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
                    if (context instanceof Activity) {
                        rootView = ((Activity) context).getWindow().getDecorView();
                        break;
                    }
                }
            }
            if (rootView == null) {
                Log.e("TooltipPopup", "Cannot find app view");
            } else {
                rootView.getWindowVisibleDisplayFrame(raVar.e);
                Rect rect = raVar.e;
                if (rect.left < 0 && rect.top < 0) {
                    Resources resources = raVar.f374a.getResources();
                    int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
                    int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0;
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    raVar.e.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels);
                }
                rootView.getLocationOnScreen(raVar.g);
                view.getLocationOnScreen(raVar.f);
                int[] iArr = raVar.f;
                int i3 = iArr[0];
                int[] iArr2 = raVar.g;
                iArr[0] = i3 - iArr2[0];
                iArr[1] = iArr[1] - iArr2[1];
                layoutParams.x = (iArr[0] + width) - (rootView.getWidth() / 2);
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                raVar.f375b.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredHeight = raVar.f375b.getMeasuredHeight();
                int[] iArr3 = raVar.f;
                int i4 = ((iArr3[1] + i) - dimensionPixelOffset3) - measuredHeight;
                int i5 = iArr3[1] + height + dimensionPixelOffset3;
                if (!z2 ? measuredHeight + i5 > raVar.e.height() : i4 >= 0) {
                    layoutParams.y = i4;
                } else {
                    layoutParams.y = i5;
                }
            }
            ((WindowManager) raVar.f374a.getSystemService("window")).addView(raVar.f375b, raVar.d);
            this.f372c.addOnAttachStateChangeListener(this);
            long longPressTimeout = this.k ? 2500L : ((a.b.d.h.n.e(this.f372c) & 1) == 1 ? 3000L : 15000L) - ((long) ViewConfiguration.getLongPressTimeout());
            this.f372c.removeCallbacks(this.g);
            this.f372c.postDelayed(this.g, longPressTimeout);
        }
    }
}
