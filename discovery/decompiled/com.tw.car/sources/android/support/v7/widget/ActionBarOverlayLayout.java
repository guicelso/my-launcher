package android.support.v7.widget;

import a.b.d.h.j;
import a.b.d.h.k;
import a.b.d.h.n;
import a.b.e.b.f;
import a.b.e.f.C0012c;
import a.b.e.f.InterfaceC0033y;
import a.b.e.f.InterfaceC0034z;
import a.b.e.f.RunnableC0013d;
import a.b.e.f.RunnableC0014e;
import a.b.e.f.na;
import a.b.e.f.ta;
import android.R;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.OverScroller;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class ActionBarOverlayLayout extends ViewGroup implements InterfaceC0033y, j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int[] f410a = {a.b.e.b.a.actionBarSize, R.attr.windowContentOverlay};
    public final k A;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f411b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f412c;
    public ContentFrameLayout d;
    public ActionBarContainer e;
    public InterfaceC0034z f;
    public Drawable g;
    public boolean h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public int m;
    public int n;
    public final Rect o;
    public final Rect p;
    public final Rect q;
    public final Rect r;
    public final Rect s;
    public final Rect t;
    public final Rect u;
    public OverScroller v;
    public ViewPropertyAnimator w;
    public final AnimatorListenerAdapter x;
    public final Runnable y;
    public final Runnable z;

    public interface a {
    }

    public static class b extends ViewGroup.MarginLayoutParams {
        public b(int i, int i2) {
            super(i, i2);
        }

        public b(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public b(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public ActionBarOverlayLayout(Context context) {
        this(context, null);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f412c = 0;
        this.o = new Rect();
        this.p = new Rect();
        this.q = new Rect();
        this.r = new Rect();
        this.s = new Rect();
        this.t = new Rect();
        this.u = new Rect();
        this.x = new C0012c(this);
        this.y = new RunnableC0013d(this);
        this.z = new RunnableC0014e(this);
        a(context);
        this.A = new k(this);
    }

    public void a() {
        removeCallbacks(this.y);
        removeCallbacks(this.z);
        ViewPropertyAnimator viewPropertyAnimator = this.w;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    public final void a(Context context) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(f410a);
        this.f411b = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.g = typedArrayObtainStyledAttributes.getDrawable(1);
        setWillNotDraw(this.g == null);
        typedArrayObtainStyledAttributes.recycle();
        this.h = context.getApplicationInfo().targetSdkVersion < 19;
        this.v = new OverScroller(context);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final boolean a(View view, Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        b bVar = (b) view.getLayoutParams();
        if (z) {
            int i = ((ViewGroup.MarginLayoutParams) bVar).leftMargin;
            int i2 = rect.left;
            if (i != i2) {
                ((ViewGroup.MarginLayoutParams) bVar).leftMargin = i2;
                z5 = true;
            } else {
                z5 = false;
            }
        } else {
            z5 = false;
        }
        if (z2) {
            int i3 = ((ViewGroup.MarginLayoutParams) bVar).topMargin;
            int i4 = rect.top;
            if (i3 != i4) {
                ((ViewGroup.MarginLayoutParams) bVar).topMargin = i4;
                z5 = true;
            }
        }
        if (z4) {
            int i5 = ((ViewGroup.MarginLayoutParams) bVar).rightMargin;
            int i6 = rect.right;
            if (i5 != i6) {
                ((ViewGroup.MarginLayoutParams) bVar).rightMargin = i6;
                z5 = true;
            }
        }
        if (!z3) {
            return z5;
        }
        int i7 = ((ViewGroup.MarginLayoutParams) bVar).bottomMargin;
        int i8 = rect.bottom;
        if (i7 == i8) {
            return z5;
        }
        ((ViewGroup.MarginLayoutParams) bVar).bottomMargin = i8;
        return true;
    }

    public void b() {
        InterfaceC0034z wrapper;
        if (this.d == null) {
            this.d = (ContentFrameLayout) findViewById(f.action_bar_activity_content);
            this.e = (ActionBarContainer) findViewById(f.action_bar_container);
            KeyEvent.Callback callbackFindViewById = findViewById(f.action_bar);
            if (callbackFindViewById instanceof InterfaceC0034z) {
                wrapper = (InterfaceC0034z) callbackFindViewById;
            } else {
                if (!(callbackFindViewById instanceof Toolbar)) {
                    StringBuilder sbA = c.a.a.a.a.a("Can't make a decor toolbar out of ");
                    sbA.append(callbackFindViewById.getClass().getSimpleName());
                    throw new IllegalStateException(sbA.toString());
                }
                wrapper = ((Toolbar) callbackFindViewById).getWrapper();
            }
            this.f = wrapper;
        }
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof b;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        int translationY;
        super.draw(canvas);
        if (this.g == null || this.h) {
            return;
        }
        if (this.e.getVisibility() == 0) {
            translationY = (int) (this.e.getTranslationY() + this.e.getBottom() + 0.5f);
        } else {
            translationY = 0;
        }
        this.g.setBounds(0, translationY, getWidth(), this.g.getIntrinsicHeight() + translationY);
        this.g.draw(canvas);
    }

    @Override // android.view.View
    public boolean fitSystemWindows(Rect rect) {
        b();
        int iE = n.e(this) & 256;
        boolean zA = a(this.e, rect, true, true, false, true);
        this.r.set(rect);
        Rect rect2 = this.r;
        Rect rect3 = this.o;
        Method method = ta.f382a;
        if (method != null) {
            try {
                method.invoke(this, rect2, rect3);
            } catch (Exception e) {
                Log.d("ViewUtils", "Could not invoke computeFitSystemWindows", e);
            }
        }
        if (!this.s.equals(this.r)) {
            this.s.set(this.r);
            zA = true;
        }
        if (!this.p.equals(this.o)) {
            this.p.set(this.o);
            zA = true;
        }
        if (zA) {
            requestLayout();
        }
        return true;
    }

    @Override // android.view.ViewGroup
    public b generateDefaultLayoutParams() {
        return new b(-1, -1);
    }

    @Override // android.view.ViewGroup
    public b generateLayoutParams(AttributeSet attributeSet) {
        return new b(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new b(layoutParams);
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.e;
        if (actionBarContainer != null) {
            return -((int) actionBarContainer.getTranslationY());
        }
        return 0;
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.A.f165a;
    }

    public CharSequence getTitle() {
        b();
        return ((na) this.f).f359a.getTitle();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        a(getContext());
        n.h(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        getPaddingRight();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                b bVar = (b) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i6 = ((ViewGroup.MarginLayoutParams) bVar).leftMargin + paddingLeft;
                int i7 = ((ViewGroup.MarginLayoutParams) bVar).topMargin + paddingTop;
                childAt.layout(i6, i7, measuredWidth + i6, measuredHeight + i7);
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int measuredHeight;
        b();
        measureChildWithMargins(this.e, i, 0, i2, 0);
        b bVar = (b) this.e.getLayoutParams();
        int iMax = Math.max(0, this.e.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) bVar).leftMargin + ((ViewGroup.MarginLayoutParams) bVar).rightMargin);
        int iMax2 = Math.max(0, this.e.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) bVar).topMargin + ((ViewGroup.MarginLayoutParams) bVar).bottomMargin);
        int iCombineMeasuredStates = View.combineMeasuredStates(0, this.e.getMeasuredState());
        boolean z = (n.e(this) & 256) != 0;
        if (z) {
            measuredHeight = this.f411b;
            if (this.j && this.e.getTabContainer() != null) {
                measuredHeight += this.f411b;
            }
        } else {
            measuredHeight = this.e.getVisibility() != 8 ? this.e.getMeasuredHeight() : 0;
        }
        this.q.set(this.o);
        this.t.set(this.r);
        Rect rect = (this.i || z) ? this.t : this.q;
        rect.top += measuredHeight;
        rect.bottom += 0;
        a(this.d, this.q, true, true, true, true);
        if (!this.u.equals(this.t)) {
            this.u.set(this.t);
            this.d.a(this.t);
        }
        measureChildWithMargins(this.d, i, 0, i2, 0);
        b bVar2 = (b) this.d.getLayoutParams();
        int iMax3 = Math.max(iMax, this.d.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) bVar2).leftMargin + ((ViewGroup.MarginLayoutParams) bVar2).rightMargin);
        int iMax4 = Math.max(iMax2, this.d.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) bVar2).topMargin + ((ViewGroup.MarginLayoutParams) bVar2).bottomMargin);
        int iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.d.getMeasuredState());
        setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + iMax3, getSuggestedMinimumWidth()), i, iCombineMeasuredStates2), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax4, getSuggestedMinimumHeight()), i2, iCombineMeasuredStates2 << 16));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (!this.k || !z) {
            return false;
        }
        this.v.fling(0, 0, 0, (int) f2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (this.v.getFinalY() > this.e.getHeight()) {
            a();
            this.z.run();
        } else {
            a();
            this.y.run();
        }
        this.l = true;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        this.m += i2;
        setActionBarHideOffset(this.m);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.A.a(view, view2, i, 0);
        this.m = getActionBarHideOffset();
        a();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public boolean onStartNestedScroll(View view, View view2, int i) {
        if ((i & 2) == 0 || this.e.getVisibility() != 0) {
            return false;
        }
        return this.k;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public void onStopNestedScroll(View view) {
        if (!this.k || this.l) {
            return;
        }
        if (this.m <= this.e.getHeight()) {
            a();
            postDelayed(this.y, 600L);
        } else {
            a();
            postDelayed(this.z, 600L);
        }
    }

    @Override // android.view.View
    public void onWindowSystemUiVisibilityChanged(int i) {
        int i2 = Build.VERSION.SDK_INT;
        super.onWindowSystemUiVisibilityChanged(i);
        b();
        int i3 = this.n ^ i;
        this.n = i;
        int i4 = i & 4;
        int i5 = i & 256;
        int i6 = i3 & 256;
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.f412c = i;
    }

    public void setActionBarHideOffset(int i) {
        a();
        this.e.setTranslationY(-Math.max(0, Math.min(i, this.e.getHeight())));
    }

    public void setActionBarVisibilityCallback(a aVar) {
        if (getWindowToken() == null) {
            return;
        }
        int i = this.f412c;
        throw null;
    }

    public void setHasNonEmbeddedTabs(boolean z) {
        this.j = z;
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        if (z != this.k) {
            this.k = z;
            if (z) {
                return;
            }
            a();
            setActionBarHideOffset(0);
        }
    }

    public void setIcon(int i) {
        b();
        na naVar = (na) this.f;
        naVar.d = i != 0 ? a.b.e.c.a.a.c(naVar.a(), i) : null;
        naVar.d();
    }

    public void setLogo(int i) {
        b();
        na naVar = (na) this.f;
        naVar.a(i != 0 ? a.b.e.c.a.a.c(naVar.a(), i) : null);
    }

    public void setOverlayMode(boolean z) {
        this.i = z;
        this.h = z && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public void setShowingForActionMode(boolean z) {
    }

    public void setUiOptions(int i) {
    }

    public void setWindowCallback(Window.Callback callback) {
        b();
        ((na) this.f).k = callback;
    }

    public void setWindowTitle(CharSequence charSequence) {
        b();
        na naVar = (na) this.f;
        if (naVar.g) {
            return;
        }
        naVar.a(charSequence);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void setIcon(Drawable drawable) {
        b();
        na naVar = (na) this.f;
        naVar.d = drawable;
        naVar.d();
    }
}
