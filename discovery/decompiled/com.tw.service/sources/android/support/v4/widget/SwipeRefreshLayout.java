package android.support.v4.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public class SwipeRefreshLayout extends ViewGroup implements android.support.v4.view.h, android.support.v4.view.e {
    static final int CIRCLE_DIAMETER = 40;
    static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final int[] wh = {R.attr.enabled};
    boolean Ai;
    private float Bf;
    private boolean Bi;
    C0029a Ci;
    private int Di;
    float Ei;
    protected int Fi;
    int Gi;
    int Hi;
    C0033e Ii;
    private Animation Ji;
    private Animation Ki;
    private Animation Li;
    private Animation Mi;
    boolean Ni;
    private int Oi;
    boolean Pi;
    private Animation.AnimationListener Qi;
    private final Animation Ri;
    private final Animation Si;
    private boolean bh;
    private final android.support.v4.view.i kg;
    private int mActivePointerId;
    private final DecelerateInterpolator mDecelerateInterpolator;
    protected int mFrom;
    C mListener;
    private View mTarget;
    boolean qi;
    private float ri;
    private float ti;
    private final android.support.v4.view.f ui;
    private final int[] vi;
    private final int[] wi;
    private boolean xi;
    int yi;
    private int zf;
    private float zi;

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.qi = false;
        this.ri = -1.0f;
        this.vi = new int[2];
        this.wi = new int[2];
        this.mActivePointerId = -1;
        this.Di = -1;
        this.Qi = new v(this);
        this.Ri = new z(this);
        this.Si = new A(this);
        this.zf = ViewConfiguration.get(context).getScaledTouchSlop();
        getResources().getInteger(R.integer.config_mediumAnimTime);
        setWillNotDraw(false);
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.Oi = (int) (displayMetrics.density * 40.0f);
        this.Ci = new C0029a(getContext(), -328966);
        this.Ii = new C0033e(getContext());
        this.Ii.setStyle(1);
        this.Ci.setImageDrawable(this.Ii);
        this.Ci.setVisibility(8);
        addView(this.Ci);
        setChildrenDrawingOrderEnabled(true);
        this.Gi = (int) (displayMetrics.density * 64.0f);
        this.ri = this.Gi;
        this.kg = new android.support.v4.view.i(this);
        this.ui = new android.support.v4.view.f(this);
        setNestedScrollingEnabled(true);
        int i = -this.Oi;
        this.yi = i;
        this.Fi = i;
        e(1.0f);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, wh);
        setEnabled(typedArrayObtainStyledAttributes.getBoolean(0, true));
        typedArrayObtainStyledAttributes.recycle();
    }

    private boolean b(Animation animation) {
        return (animation == null || !animation.hasStarted() || animation.hasEnded()) ? false : true;
    }

    private void d(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            this.mActivePointerId = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
        }
    }

    private void j(float f) {
        if (f > this.ri) {
            if (!this.qi) {
                this.Ni = true;
                mb();
                this.qi = true;
                if (!this.qi) {
                    a(this.Qi);
                    return;
                }
                int i = this.yi;
                Animation.AnimationListener animationListener = this.Qi;
                this.mFrom = i;
                this.Ri.reset();
                this.Ri.setDuration(200L);
                this.Ri.setInterpolator(this.mDecelerateInterpolator);
                if (animationListener != null) {
                    this.Ci.setAnimationListener(animationListener);
                }
                this.Ci.clearAnimation();
                this.Ci.startAnimation(this.Ri);
                return;
            }
            return;
        }
        this.qi = false;
        this.Ii.a(0.0f, 0.0f);
        y yVar = this.Ai ? null : new y(this);
        int i2 = this.yi;
        if (this.Ai) {
            this.mFrom = i2;
            this.Ei = this.Ci.getScaleX();
            this.Mi = new B(this);
            this.Mi.setDuration(150L);
            if (yVar != null) {
                this.Ci.setAnimationListener(yVar);
            }
            this.Ci.clearAnimation();
            this.Ci.startAnimation(this.Mi);
        } else {
            this.mFrom = i2;
            this.Si.reset();
            this.Si.setDuration(200L);
            this.Si.setInterpolator(this.mDecelerateInterpolator);
            if (yVar != null) {
                this.Ci.setAnimationListener(yVar);
            }
            this.Ci.clearAnimation();
            this.Ci.startAnimation(this.Si);
        }
        this.Ii.f(false);
    }

    private void k(float f) {
        this.Ii.f(true);
        float fMin = Math.min(1.0f, Math.abs(f / this.ri));
        float fMax = (((float) Math.max(((double) fMin) - 0.4d, 0.0d)) * 5.0f) / 3.0f;
        float fAbs = Math.abs(f) - this.ri;
        int i = this.Hi;
        if (i <= 0) {
            i = this.Pi ? this.Gi - this.Fi : this.Gi;
        }
        float f2 = i;
        double dMax = Math.max(0.0f, Math.min(fAbs, f2 * 2.0f) / f2) / 4.0f;
        float fPow = ((float) (dMax - Math.pow(dMax, 2.0d))) * 2.0f;
        int i2 = this.Fi + ((int) ((f2 * fMin) + (f2 * fPow * 2.0f)));
        if (this.Ci.getVisibility() != 0) {
            this.Ci.setVisibility(0);
        }
        if (!this.Ai) {
            this.Ci.setScaleX(1.0f);
            this.Ci.setScaleY(1.0f);
        }
        if (this.Ai) {
            f(Math.min(1.0f, f / this.ri));
        }
        if (f < this.ri) {
            if (this.Ii.getAlpha() > 76 && !b(this.Ki)) {
                this.Ki = p(this.Ii.getAlpha(), 76);
            }
        } else if (this.Ii.getAlpha() < 255 && !b(this.Li)) {
            this.Li = p(this.Ii.getAlpha(), 255);
        }
        this.Ii.a(0.0f, Math.min(0.8f, fMax * 0.8f));
        this.Ii.a(Math.min(1.0f, fMax));
        this.Ii.b(((fPow * 2.0f) + ((fMax * 0.4f) - 0.25f)) * 0.5f);
        z(i2 - this.yi);
    }

    private void l(float f) {
        float f2 = this.zi;
        float f3 = f - f2;
        int i = this.zf;
        if (f3 <= i || this.bh) {
            return;
        }
        this.Bf = f2 + i;
        this.bh = true;
        this.Ii.setAlpha(76);
    }

    private void mb() {
        if (this.mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (!childAt.equals(this.Ci)) {
                    this.mTarget = childAt;
                    return;
                }
            }
        }
    }

    private Animation p(int i, int i2) {
        x xVar = new x(this, i, i2);
        xVar.setDuration(300L);
        this.Ci.setAnimationListener(null);
        this.Ci.clearAnimation();
        this.Ci.startAnimation(xVar);
        return xVar;
    }

    void a(Animation.AnimationListener animationListener) {
        this.Ji = new w(this);
        this.Ji.setDuration(150L);
        this.Ci.setAnimationListener(animationListener);
        this.Ci.clearAnimation();
        this.Ci.startAnimation(this.Ji);
    }

    @Override // android.view.View
    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.ui.dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View
    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.ui.dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.View
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.ui.a(i, i2, iArr, iArr2, 0);
    }

    @Override // android.view.View
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.ui.a(i, i2, i3, i4, iArr, 0);
    }

    void e(float f) {
        int i = this.mFrom;
        z((i + ((int) ((this.Fi - i) * f))) - this.Ci.getTop());
    }

    void f(float f) {
        this.Ci.setScaleX(f);
        this.Ci.setScaleY(f);
    }

    public boolean fa() {
        View view = this.mTarget;
        if (!(view instanceof ListView)) {
            return view.canScrollVertically(-1);
        }
        int i = Build.VERSION.SDK_INT;
        return ((ListView) view).canScrollList(-1);
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        int i3 = this.Di;
        if (i3 < 0) {
            return i2;
        }
        if (i2 == i - 1) {
            return i3;
        }
        return i2 >= i3 ? i2 + 1 : i2;
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.kg.getNestedScrollAxes();
    }

    @Override // android.view.View
    public boolean hasNestedScrollingParent() {
        return this.ui.j(0);
    }

    @Override // android.view.View
    public boolean isNestedScrollingEnabled() {
        return this.ui.isNestedScrollingEnabled();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0058  */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        mb();
        int actionMasked = motionEvent.getActionMasked();
        if (this.Bi && actionMasked == 0) {
            this.Bi = false;
        }
        if (!isEnabled() || this.Bi || fa() || this.qi || this.xi) {
            return false;
        }
        if (actionMasked == 0) {
            z(this.Fi - this.Ci.getTop());
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.bh = false;
            int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            if (iFindPointerIndex < 0) {
                return false;
            }
            this.zi = motionEvent.getY(iFindPointerIndex);
        } else if (actionMasked == 1) {
            this.bh = false;
            this.mActivePointerId = -1;
        } else if (actionMasked == 2) {
            int i = this.mActivePointerId;
            if (i == -1) {
                Log.e("SwipeRefreshLayout", "Got ACTION_MOVE event but don't have an active pointer id.");
                return false;
            }
            int iFindPointerIndex2 = motionEvent.findPointerIndex(i);
            if (iFindPointerIndex2 < 0) {
                return false;
            }
            l(motionEvent.getY(iFindPointerIndex2));
        } else if (actionMasked == 3) {
            this.bh = false;
            this.mActivePointerId = -1;
        } else if (actionMasked == 6) {
            d(motionEvent);
        }
        return this.bh;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (this.mTarget == null) {
            mb();
        }
        View view = this.mTarget;
        if (view == null) {
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        view.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
        int measuredWidth2 = this.Ci.getMeasuredWidth();
        int measuredHeight2 = this.Ci.getMeasuredHeight();
        int i5 = measuredWidth / 2;
        int i6 = measuredWidth2 / 2;
        int i7 = this.yi;
        this.Ci.layout(i5 - i6, i7, i5 + i6, measuredHeight2 + i7);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mTarget == null) {
            mb();
        }
        View view = this.mTarget;
        if (view == null) {
            return;
        }
        view.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
        this.Ci.measure(View.MeasureSpec.makeMeasureSpec(this.Oi, 1073741824), View.MeasureSpec.makeMeasureSpec(this.Oi, 1073741824));
        this.Di = -1;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            if (getChildAt(i3) == this.Ci) {
                this.Di = i3;
                return;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        return dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        if (i2 > 0) {
            float f = this.ti;
            if (f > 0.0f) {
                float f2 = i2;
                if (f2 > f) {
                    iArr[1] = i2 - ((int) f);
                    this.ti = 0.0f;
                } else {
                    this.ti = f - f2;
                    iArr[1] = i2;
                }
                k(this.ti);
            }
        }
        if (this.Pi && i2 > 0 && this.ti == 0.0f && Math.abs(i2 - iArr[1]) > 0) {
            this.Ci.setVisibility(8);
        }
        int[] iArr2 = this.vi;
        if (dispatchNestedPreScroll(i - iArr[0], i2 - iArr[1], iArr2, null)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        dispatchNestedScroll(i, i2, i3, i4, this.wi);
        int i5 = i4 + this.wi[1];
        if (i5 >= 0 || fa()) {
            return;
        }
        this.ti += Math.abs(i5);
        k(this.ti);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.kg.onNestedScrollAccepted(view, view2, i);
        startNestedScroll(i & 2);
        this.ti = 0.0f;
        this.xi = true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return (!isEnabled() || this.Bi || this.qi || (i & 2) == 0) ? false : true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onStopNestedScroll(View view) {
        this.kg.onStopNestedScroll(view);
        this.xi = false;
        float f = this.ti;
        if (f > 0.0f) {
            j(f);
            this.ti = 0.0f;
        }
        stopNestedScroll();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.Bi && actionMasked == 0) {
            this.Bi = false;
        }
        if (!isEnabled() || this.Bi || fa() || this.qi || this.xi) {
            return false;
        }
        if (actionMasked == 0) {
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.bh = false;
        } else {
            if (actionMasked == 1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (iFindPointerIndex < 0) {
                    Log.e("SwipeRefreshLayout", "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }
                if (this.bh) {
                    float y = (motionEvent.getY(iFindPointerIndex) - this.Bf) * 0.5f;
                    this.bh = false;
                    j(y);
                }
                this.mActivePointerId = -1;
                return false;
            }
            if (actionMasked == 2) {
                int iFindPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (iFindPointerIndex2 < 0) {
                    Log.e("SwipeRefreshLayout", "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                float y2 = motionEvent.getY(iFindPointerIndex2);
                l(y2);
                if (this.bh) {
                    float f = (y2 - this.Bf) * 0.5f;
                    if (f <= 0.0f) {
                        return false;
                    }
                    k(f);
                }
            } else {
                if (actionMasked == 3) {
                    return false;
                }
                if (actionMasked == 5) {
                    int actionIndex = motionEvent.getActionIndex();
                    if (actionIndex < 0) {
                        Log.e("SwipeRefreshLayout", "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                        return false;
                    }
                    this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                } else if (actionMasked == 6) {
                    d(motionEvent);
                }
            }
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        int i = Build.VERSION.SDK_INT;
        View view = this.mTarget;
        if (view == null || android.support.v4.view.r.m(view)) {
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    void reset() {
        this.Ci.clearAnimation();
        this.Ii.stop();
        this.Ci.setVisibility(8);
        this.Ci.getBackground().setAlpha(255);
        this.Ii.setAlpha(255);
        if (this.Ai) {
            f(0.0f);
        } else {
            z(this.Fi - this.yi);
        }
        this.yi = this.Ci.getTop();
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (z) {
            return;
        }
        reset();
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z) {
        this.ui.setNestedScrollingEnabled(z);
    }

    @Override // android.view.View
    public boolean startNestedScroll(int i) {
        return this.ui.b(i, 0);
    }

    @Override // android.view.View
    public void stopNestedScroll() {
        this.ui.k(0);
    }

    void z(int i) {
        this.Ci.bringToFront();
        android.support.v4.view.r.c(this.Ci, i);
        this.yi = this.Ci.getTop();
    }
}
