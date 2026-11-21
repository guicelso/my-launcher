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
import com.tw.carinfoservice.R;

/* JADX INFO: loaded from: classes.dex */
public class k0 implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {
    public static k0 k;
    public static k0 l;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final View f295b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final CharSequence f296c;
    public final int d;
    public final Runnable e = new a();
    public final Runnable f = new b();
    public int g;
    public int h;
    public l0 i;
    public boolean j;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            k0.this.d(false);
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            k0.this.b();
        }
    }

    public k0(View view, CharSequence charSequence) {
        this.f295b = view;
        this.f296c = charSequence;
        this.d = a.b.d.h.k.a(ViewConfiguration.get(view.getContext()));
        a();
        view.setOnLongClickListener(this);
        view.setOnHoverListener(this);
    }

    public static void c(k0 k0Var) {
        k0 k0Var2 = k;
        if (k0Var2 != null) {
            k0Var2.f295b.removeCallbacks(k0Var2.e);
        }
        k = k0Var;
        if (k0Var != null) {
            k0Var.f295b.postDelayed(k0Var.e, ViewConfiguration.getLongPressTimeout());
        }
    }

    public final void a() {
        this.g = Integer.MAX_VALUE;
        this.h = Integer.MAX_VALUE;
    }

    public void b() {
        if (l == this) {
            l = null;
            l0 l0Var = this.i;
            if (l0Var != null) {
                l0Var.a();
                this.i = null;
                a();
                this.f295b.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        if (k == this) {
            c(null);
        }
        this.f295b.removeCallbacks(this.f);
    }

    public void d(boolean z) {
        int height;
        int i;
        View view = this.f295b;
        int i2 = a.b.d.h.j.f151a;
        if (view.isAttachedToWindow()) {
            c(null);
            k0 k0Var = l;
            if (k0Var != null) {
                k0Var.b();
            }
            l = this;
            this.j = z;
            l0 l0Var = new l0(this.f295b.getContext());
            this.i = l0Var;
            View view2 = this.f295b;
            int width = this.g;
            int i3 = this.h;
            boolean z2 = this.j;
            CharSequence charSequence = this.f296c;
            if (l0Var.f305b.getParent() != null) {
                l0Var.a();
            }
            l0Var.f306c.setText(charSequence);
            WindowManager.LayoutParams layoutParams = l0Var.d;
            layoutParams.token = view2.getApplicationWindowToken();
            int dimensionPixelOffset = l0Var.f304a.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_threshold);
            if (view2.getWidth() < dimensionPixelOffset) {
                width = view2.getWidth() / 2;
            }
            if (view2.getHeight() >= dimensionPixelOffset) {
                int dimensionPixelOffset2 = l0Var.f304a.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_extra_offset);
                height = i3 + dimensionPixelOffset2;
                i = i3 - dimensionPixelOffset2;
            } else {
                height = view2.getHeight();
                i = 0;
            }
            layoutParams.gravity = 49;
            int dimensionPixelOffset3 = l0Var.f304a.getResources().getDimensionPixelOffset(z2 ? R.dimen.tooltip_y_offset_touch : R.dimen.tooltip_y_offset_non_touch);
            View rootView = view2.getRootView();
            ViewGroup.LayoutParams layoutParams2 = rootView.getLayoutParams();
            if (!(layoutParams2 instanceof WindowManager.LayoutParams) || ((WindowManager.LayoutParams) layoutParams2).type != 2) {
                for (Context context = view2.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
                    if (context instanceof Activity) {
                        rootView = ((Activity) context).getWindow().getDecorView();
                        break;
                    }
                }
            }
            if (rootView == null) {
                Log.e("TooltipPopup", "Cannot find app view");
            } else {
                rootView.getWindowVisibleDisplayFrame(l0Var.e);
                Rect rect = l0Var.e;
                if (rect.left < 0 && rect.top < 0) {
                    Resources resources = l0Var.f304a.getResources();
                    int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
                    int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0;
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    l0Var.e.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels);
                }
                rootView.getLocationOnScreen(l0Var.g);
                view2.getLocationOnScreen(l0Var.f);
                int[] iArr = l0Var.f;
                int i4 = iArr[0];
                int[] iArr2 = l0Var.g;
                iArr[0] = i4 - iArr2[0];
                iArr[1] = iArr[1] - iArr2[1];
                layoutParams.x = (iArr[0] + width) - (rootView.getWidth() / 2);
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                l0Var.f305b.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredHeight = l0Var.f305b.getMeasuredHeight();
                int[] iArr3 = l0Var.f;
                int i5 = ((iArr3[1] + i) - dimensionPixelOffset3) - measuredHeight;
                int i6 = iArr3[1] + height + dimensionPixelOffset3;
                if (!z2 ? measuredHeight + i6 > l0Var.e.height() : i5 >= 0) {
                    layoutParams.y = i5;
                } else {
                    layoutParams.y = i6;
                }
            }
            ((WindowManager) l0Var.f304a.getSystemService("window")).addView(l0Var.f305b, l0Var.d);
            this.f295b.addOnAttachStateChangeListener(this);
            long longPressTimeout = this.j ? 2500L : ((this.f295b.getWindowSystemUiVisibility() & 1) == 1 ? 3000L : 15000L) - ((long) ViewConfiguration.getLongPressTimeout());
            this.f295b.removeCallbacks(this.f);
            this.f295b.postDelayed(this.f, longPressTimeout);
        }
    }

    @Override // android.view.View.OnHoverListener
    public boolean onHover(View view, MotionEvent motionEvent) {
        boolean z;
        if (this.i != null && this.j) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.f295b.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            if (action == 10) {
                a();
                b();
            }
        } else if (this.f295b.isEnabled() && this.i == null) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (Math.abs(x - this.g) > this.d || Math.abs(y - this.h) > this.d) {
                this.g = x;
                this.h = y;
                z = true;
            } else {
                z = false;
            }
            if (z) {
                c(this);
            }
        }
        return false;
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        this.g = view.getWidth() / 2;
        this.h = view.getHeight() / 2;
        d(true);
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        b();
    }
}
