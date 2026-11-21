package android.support.v4.widget;

import a.b.d.h.d;
import a.b.d.h.f;
import a.b.d.h.h;
import a.b.d.h.i;
import a.b.d.h.j;
import a.b.d.h.k;
import a.b.d.h.l;
import a.b.d.h.n;
import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class NestedScrollView extends FrameLayout implements i, f, l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final a f397a = new a();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final int[] f398b = {R.attr.fillViewport};

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public long f399c;
    public final Rect d;
    public OverScroller e;
    public EdgeEffect f;
    public EdgeEffect g;
    public int h;
    public boolean i;
    public boolean j;
    public View k;
    public boolean l;
    public VelocityTracker m;
    public int mTouchSlop;
    public float mVerticalScrollFactor;
    public boolean n;
    public boolean o;
    public int p;
    public int q;
    public int r;
    public final int[] s;
    public final int[] t;
    public int u;
    public int v;
    public c w;
    public final k x;
    public final h y;

    public interface b {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static class c extends View.BaseSavedState {
        public static final Parcelable.Creator<c> CREATOR = new a.b.d.i.f();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f400a;

        public c(Parcel parcel) {
            super(parcel);
            this.f400a = parcel.readInt();
        }

        public c(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder sbA = c.a.a.a.a.a("HorizontalScrollView.SavedState{");
            sbA.append(Integer.toHexString(System.identityHashCode(this)));
            sbA.append(" scrollPosition=");
            sbA.append(this.f400a);
            sbA.append("}");
            return sbA.toString();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f400a);
        }
    }

    public NestedScrollView(Context context) {
        this(context, null, 0);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public static int a(int i, int i2, int i3) {
        if (i2 >= i3 || i < 0) {
            return 0;
        }
        return i2 + i > i3 ? i3 - i2 : i;
    }

    public static boolean a(View view, View view2) {
        if (view == view2) {
            return true;
        }
        Object parent = view.getParent();
        return (parent instanceof ViewGroup) && a((View) parent, view2);
    }

    private float getVerticalScrollFactorCompat() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue typedValue = new TypedValue();
            Context context = getContext();
            if (!context.getTheme().resolveAttribute(R.attr.listPreferredItemHeight, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    public int a(Rect rect) {
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        int i2 = rect.bottom < (childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin ? i - verticalFadingEdgeLength : i;
        if (rect.bottom > i2 && rect.top > scrollY) {
            return Math.min((rect.height() > height ? rect.top - scrollY : rect.bottom - i2) + 0, (childAt.getBottom() + layoutParams.bottomMargin) - i);
        }
        if (rect.top >= scrollY || rect.bottom >= i2) {
            return 0;
        }
        return Math.max(rect.height() > height ? 0 - (i2 - rect.bottom) : 0 - (scrollY - rect.top), -getScrollY());
    }

    public final void a() {
        if (getOverScrollMode() == 2) {
            this.f = null;
            this.g = null;
        } else if (this.f == null) {
            Context context = getContext();
            this.f = new EdgeEffect(context);
            this.g = new EdgeEffect(context);
        }
    }

    public final void a(int i, int i2) {
        if (getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.f399c > 250) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int height = childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int scrollY = getScrollY();
            int iMax = Math.max(0, Math.min(i2 + scrollY, Math.max(0, height - height2))) - scrollY;
            this.v = getScrollY();
            this.e.startScroll(getScrollX(), scrollY, 0, iMax);
            n.g(this);
        } else {
            if (!this.e.isFinished()) {
                this.e.abortAnimation();
            }
            scrollBy(i, i2);
        }
        this.f399c = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.r) {
            int i = actionIndex == 0 ? 1 : 0;
            this.h = (int) motionEvent.getY(i);
            this.r = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.m;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    public final void a(View view) {
        view.getDrawingRect(this.d);
        offsetDescendantRectToMyCoords(view, this.d);
        int iA = a(this.d);
        if (iA != 0) {
            scrollBy(0, iA);
        }
    }

    @Override // a.b.d.h.i
    public void a(View view, int i, int i2, int i3, int i4, int i5) {
        int scrollY = getScrollY();
        scrollBy(0, i4);
        int scrollY2 = getScrollY() - scrollY;
        a(0, scrollY2, 0, i4 - scrollY2, (int[]) null, i5);
    }

    @Override // a.b.d.h.i
    public void a(View view, int i, int i2, int[] iArr, int i3) {
        a(i, i2, iArr, (int[]) null, i3);
    }

    public boolean a(int i) {
        View viewFindFocus = findFocus();
        if (viewFindFocus == this) {
            viewFindFocus = null;
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i);
        int maxScrollAmount = getMaxScrollAmount();
        if (viewFindNextFocus == null || !a(viewFindNextFocus, maxScrollAmount, getHeight())) {
            if (i == 33 && getScrollY() < maxScrollAmount) {
                maxScrollAmount = getScrollY();
            } else if (i == 130 && getChildCount() > 0) {
                View childAt = getChildAt(0);
                maxScrollAmount = Math.min((childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin) - ((getHeight() + getScrollY()) - getPaddingBottom()), maxScrollAmount);
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i != 130) {
                maxScrollAmount = -maxScrollAmount;
            }
            b(maxScrollAmount);
        } else {
            viewFindNextFocus.getDrawingRect(this.d);
            offsetDescendantRectToMyCoords(viewFindNextFocus, this.d);
            b(a(this.d));
            viewFindNextFocus.requestFocus(i);
        }
        if (viewFindFocus != null && viewFindFocus.isFocused() && (!a(viewFindFocus, 0, getHeight()))) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0057  */
    /* JADX WARN: Code duplicated, block: B:40:0x005a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x005c  */
    /* JADX WARN: Code duplicated, block: B:42:0x005e  */
    /* JADX WARN: Code duplicated, block: B:44:0x0061  */
    /* JADX WARN: Code duplicated, block: B:49:0x0083 A[ADDED_TO_REGION] */
    public boolean a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        boolean z2;
        boolean z3;
        int overScrollMode = getOverScrollMode();
        boolean z4 = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        boolean z5 = computeVerticalScrollRange() > computeVerticalScrollExtent();
        boolean z6 = overScrollMode == 0 || (overScrollMode == 1 && z4);
        boolean z7 = overScrollMode == 0 || (overScrollMode == 1 && z5);
        int i9 = i3 + i;
        int i10 = !z6 ? 0 : i7;
        int i11 = i4 + i2;
        int i12 = !z7 ? 0 : i8;
        int i13 = -i10;
        int i14 = i10 + i5;
        int i15 = -i12;
        int i16 = i12 + i6;
        if (i9 <= i14) {
            if (i9 >= i13) {
                i13 = i9;
                z2 = false;
            }
            if (i11 > i16) {
                if (i11 < i15) {
                    i11 = i15;
                } else {
                    z3 = false;
                }
                if (z3 && !f(1)) {
                    this.e.springBack(i13, i11, 0, 0, 0, getScrollRange());
                }
                onOverScrolled(i13, i11, z2, z3);
                return !z2 || z3;
            }
            i11 = i16;
            z3 = true;
            if (z3) {
                this.e.springBack(i13, i11, 0, 0, 0, getScrollRange());
            }
            onOverScrolled(i13, i11, z2, z3);
            if (z2) {
            }
        }
        i13 = i14;
        z2 = true;
        if (i11 > i16) {
            if (i11 < i15) {
                i11 = i15;
            } else {
                z3 = false;
            }
            if (z3) {
                this.e.springBack(i13, i11, 0, 0, 0, getScrollRange());
            }
            onOverScrolled(i13, i11, z2, z3);
            if (z2) {
            }
        }
        i11 = i16;
        z3 = true;
        if (z3) {
            this.e.springBack(i13, i11, 0, 0, 0, getScrollRange());
        }
        onOverScrolled(i13, i11, z2, z3);
        if (z2) {
        }
    }

    public final boolean a(View view, int i, int i2) {
        view.getDrawingRect(this.d);
        offsetDescendantRectToMyCoords(view, this.d);
        return this.d.bottom + i >= getScrollY() && this.d.top - i <= getScrollY() + i2;
    }

    @Override // android.view.ViewGroup
    public void addView(View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i, layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    public final void b() {
        VelocityTracker velocityTracker = this.m;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.m = null;
        }
    }

    public final void b(int i) {
        if (i != 0) {
            if (this.o) {
                a(0, i);
            } else {
                scrollBy(0, i);
            }
        }
    }

    public final void b(int i, int i2) {
        a(i - getScrollX(), i2 - getScrollY());
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0069  */
    public final boolean b(int i, int i2, int i3) {
        boolean z;
        int height = getHeight();
        int scrollY = getScrollY();
        int i4 = height + scrollY;
        boolean z2 = i == 33;
        ArrayList focusables = getFocusables(2);
        int size = focusables.size();
        View view = null;
        boolean z3 = false;
        for (int i5 = 0; i5 < size; i5++) {
            View view2 = (View) focusables.get(i5);
            int top = view2.getTop();
            int bottom = view2.getBottom();
            if (i2 < bottom && top < i3) {
                boolean z4 = i2 < top && bottom < i3;
                if (view == null) {
                    view = view2;
                    z3 = z4;
                } else {
                    boolean z5 = (z2 && top < view.getTop()) || (!z2 && bottom > view.getBottom());
                    if (z3) {
                        if (z4 && z5) {
                            view = view2;
                        }
                    } else if (z4) {
                        view = view2;
                        z3 = true;
                    } else if (z5) {
                        view = view2;
                    }
                }
            }
        }
        if (view == null) {
            view = this;
        }
        if (i2 < scrollY || i3 > i4) {
            b(z2 ? i2 - scrollY : i3 - i4);
            z = true;
        } else {
            z = false;
        }
        if (view != findFocus()) {
            view.requestFocus(i);
        }
        return z;
    }

    @Override // a.b.d.h.i
    public boolean b(View view, View view2, int i, int i2) {
        return (i & 2) != 0;
    }

    public void c(int i) {
        if (getChildCount() > 0) {
            c(2, 1);
            this.e.fling(getScrollX(), getScrollY(), 0, i, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            this.v = getScrollY();
            n.g(this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0051  */
    public boolean c(int i, int i2) {
        boolean zOnStartNestedScroll;
        h hVar = this.y;
        if (hVar.b(i2)) {
            return true;
        }
        if (hVar.d) {
            View view = hVar.f164c;
            for (ViewParent parent = hVar.f164c.getParent(); parent != null; parent = parent.getParent()) {
                View view2 = hVar.f164c;
                boolean z = parent instanceof i;
                if (z) {
                    zOnStartNestedScroll = ((i) parent).b(view, view2, i, i2);
                } else if (i2 != 0) {
                    zOnStartNestedScroll = false;
                } else if (Build.VERSION.SDK_INT >= 21) {
                    try {
                        zOnStartNestedScroll = parent.onStartNestedScroll(view, view2, i);
                    } catch (AbstractMethodError e) {
                        Log.e("ViewParentCompat", c.a.a.a.a.a("ViewParent ", parent, " does not implement interface ", "method onStartNestedScroll"), e);
                        zOnStartNestedScroll = false;
                    }
                } else if (parent instanceof j) {
                    zOnStartNestedScroll = ((j) parent).onStartNestedScroll(view, view2, i);
                } else {
                    zOnStartNestedScroll = false;
                }
                if (zOnStartNestedScroll) {
                    hVar.a(i2, parent);
                    View view3 = hVar.f164c;
                    if (z) {
                        ((i) parent).a(view, view3, i, i2);
                        return true;
                    }
                    if (i2 != 0) {
                        return true;
                    }
                    if (Build.VERSION.SDK_INT < 21) {
                        if (!(parent instanceof j)) {
                            return true;
                        }
                        ((j) parent).onNestedScrollAccepted(view, view3, i);
                        return true;
                    }
                    try {
                        parent.onNestedScrollAccepted(view, view3, i);
                        return true;
                    } catch (AbstractMethodError e2) {
                        Log.e("ViewParentCompat", c.a.a.a.a.a("ViewParent ", parent, " does not implement interface ", "method onNestedScrollAccepted"), e2);
                        return true;
                    }
                }
                if (parent instanceof View) {
                    view = (View) parent;
                }
            }
        }
        return false;
    }

    @Override // android.view.View
    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @Override // android.view.View
    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override // android.view.View
    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    @Override // android.view.View
    public void computeScroll() {
        EdgeEffect edgeEffect;
        if (!this.e.computeScrollOffset()) {
            if (f(1)) {
                h(1);
            }
            this.v = 0;
            return;
        }
        this.e.getCurrX();
        int currY = this.e.getCurrY();
        int i = currY - this.v;
        if (a(0, i, this.t, (int[]) null, 1)) {
            i -= this.t[1];
        }
        int i2 = i;
        if (i2 != 0) {
            int scrollRange = getScrollRange();
            int scrollY = getScrollY();
            a(0, i2, getScrollX(), scrollY, 0, scrollRange, 0, 0, false);
            int scrollY2 = getScrollY() - scrollY;
            if (!a(0, scrollY2, 0, i2 - scrollY2, (int[]) null, 1)) {
                int overScrollMode = getOverScrollMode();
                if (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0)) {
                    a();
                    if (currY <= 0 && scrollY > 0) {
                        edgeEffect = this.f;
                    } else if (currY >= scrollRange && scrollY < scrollRange) {
                        edgeEffect = this.g;
                    }
                    edgeEffect.onAbsorb((int) this.e.getCurrVelocity());
                }
            }
        }
        this.v = currY;
        n.g(this);
    }

    @Override // android.view.View
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override // android.view.View
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override // android.view.View
    public int computeVerticalScrollRange() {
        int childCount = getChildCount();
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (childCount == 0) {
            return height;
        }
        View childAt = getChildAt(0);
        int bottom = childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
        int scrollY = getScrollY();
        int iMax = Math.max(0, bottom - height);
        if (scrollY < 0) {
            return bottom - scrollY;
        }
        return scrollY > iMax ? bottom + (scrollY - iMax) : bottom;
    }

    public final void d(int i) {
        int scrollY = getScrollY();
        boolean z = (scrollY > 0 || i > 0) && (scrollY < getScrollRange() || i < 0);
        float f = i;
        if (dispatchNestedPreFling(0.0f, f)) {
            return;
        }
        dispatchNestedFling(0.0f, f, z);
        c(i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || a(keyEvent);
    }

    @Override // android.view.View
    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        ViewParent viewParentA;
        h hVar = this.y;
        if (!hVar.d || (viewParentA = hVar.a(0)) == null) {
            return false;
        }
        View view = hVar.f164c;
        if (Build.VERSION.SDK_INT < 21) {
            if (viewParentA instanceof j) {
                return ((j) viewParentA).onNestedFling(view, f, f2, z);
            }
            return false;
        }
        try {
            return viewParentA.onNestedFling(view, f, f2, z);
        } catch (AbstractMethodError e) {
            Log.e("ViewParentCompat", c.a.a.a.a.a("ViewParent ", viewParentA, " does not implement interface ", "method onNestedFling"), e);
            return false;
        }
    }

    @Override // android.view.View
    public boolean dispatchNestedPreFling(float f, float f2) {
        ViewParent viewParentA;
        h hVar = this.y;
        if (!hVar.d || (viewParentA = hVar.a(0)) == null) {
            return false;
        }
        View view = hVar.f164c;
        if (Build.VERSION.SDK_INT < 21) {
            if (viewParentA instanceof j) {
                return ((j) viewParentA).onNestedPreFling(view, f, f2);
            }
            return false;
        }
        try {
            return viewParentA.onNestedPreFling(view, f, f2);
        } catch (AbstractMethodError e) {
            Log.e("ViewParentCompat", c.a.a.a.a.a("ViewParent ", viewParentA, " does not implement interface ", "method onNestedPreFling"), e);
            return false;
        }
    }

    @Override // android.view.View
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return a(i, i2, iArr, iArr2, 0);
    }

    @Override // android.view.View
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return a(i, i2, i3, i4, iArr, 0);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        int paddingLeft;
        super.draw(canvas);
        if (this.f != null) {
            int scrollY = getScrollY();
            int paddingLeft2 = 0;
            if (!this.f.isFinished()) {
                int iSave = canvas.save();
                int width = getWidth();
                int height = getHeight();
                int iMin = Math.min(0, scrollY);
                if (Build.VERSION.SDK_INT < 21 || getClipToPadding()) {
                    width -= getPaddingRight() + getPaddingLeft();
                    paddingLeft = getPaddingLeft() + 0;
                } else {
                    paddingLeft = 0;
                }
                if (Build.VERSION.SDK_INT >= 21 && getClipToPadding()) {
                    height -= getPaddingBottom() + getPaddingTop();
                    iMin += getPaddingTop();
                }
                canvas.translate(paddingLeft, iMin);
                this.f.setSize(width, height);
                if (this.f.draw(canvas)) {
                    n.g(this);
                }
                canvas.restoreToCount(iSave);
            }
            if (this.g.isFinished()) {
                return;
            }
            int iSave2 = canvas.save();
            int width2 = getWidth();
            int height2 = getHeight();
            int iMax = Math.max(getScrollRange(), scrollY) + height2;
            if (Build.VERSION.SDK_INT < 21 || getClipToPadding()) {
                width2 -= getPaddingRight() + getPaddingLeft();
                paddingLeft2 = 0 + getPaddingLeft();
            }
            if (Build.VERSION.SDK_INT >= 21 && getClipToPadding()) {
                height2 -= getPaddingBottom() + getPaddingTop();
                iMax -= getPaddingBottom();
            }
            canvas.translate(paddingLeft2 - width2, iMax);
            canvas.rotate(180.0f, width2, 0.0f);
            this.g.setSize(width2, height2);
            if (this.g.draw(canvas)) {
                n.g(this);
            }
            canvas.restoreToCount(iSave2);
        }
    }

    public boolean e(int i) {
        int childCount;
        boolean z = i == 130;
        int height = getHeight();
        Rect rect = this.d;
        rect.top = 0;
        rect.bottom = height;
        if (z && (childCount = getChildCount()) > 0) {
            View childAt = getChildAt(childCount - 1);
            this.d.bottom = getPaddingBottom() + childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
            Rect rect2 = this.d;
            rect2.top = rect2.bottom - height;
        }
        Rect rect3 = this.d;
        return b(i, rect3.top, rect3.bottom);
    }

    public boolean f(int i) {
        return this.y.a(i) != null;
    }

    public boolean g(int i) {
        Rect rect;
        int i2 = 0;
        boolean z = i == 130;
        int height = getHeight();
        if (z) {
            this.d.top = getScrollY() + height;
            int childCount = getChildCount();
            if (childCount > 0) {
                View childAt = getChildAt(childCount - 1);
                int paddingBottom = getPaddingBottom() + childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
                rect = this.d;
                if (rect.top + height > paddingBottom) {
                    i2 = paddingBottom - height;
                    rect.top = i2;
                }
            }
        } else {
            this.d.top = getScrollY() - height;
            rect = this.d;
            if (rect.top < 0) {
                rect.top = i2;
            }
        }
        Rect rect2 = this.d;
        int i3 = rect2.top;
        rect2.bottom = height + i3;
        return b(i, i3, rect2.bottom);
    }

    @Override // android.view.View
    public float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = ((childAt.getBottom() + layoutParams.bottomMargin) - getScrollY()) - (getHeight() - getPaddingBottom());
        if (bottom < verticalFadingEdgeLength) {
            return bottom / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int) (getHeight() * 0.5f);
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.x.f165a;
    }

    public int getScrollRange() {
        if (getChildCount() <= 0) {
            return 0;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        return Math.max(0, ((childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin) - ((getHeight() - getPaddingTop()) - getPaddingBottom()));
    }

    @Override // android.view.View
    public float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int scrollY = getScrollY();
        if (scrollY < verticalFadingEdgeLength) {
            return scrollY / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    public void h(int i) {
        h hVar = this.y;
        ViewParent viewParentA = hVar.a(i);
        if (viewParentA != null) {
            View view = hVar.f164c;
            if (viewParentA instanceof i) {
                ((i) viewParentA).a(view, i);
            } else if (i == 0) {
                if (Build.VERSION.SDK_INT >= 21) {
                    try {
                        viewParentA.onStopNestedScroll(view);
                    } catch (AbstractMethodError e) {
                        Log.e("ViewParentCompat", c.a.a.a.a.a("ViewParent ", viewParentA, " does not implement interface ", "method onStopNestedScroll"), e);
                    }
                } else if (viewParentA instanceof j) {
                    ((j) viewParentA).onStopNestedScroll(view);
                }
            }
            hVar.a(i, null);
        }
    }

    @Override // android.view.View
    public boolean hasNestedScrollingParent() {
        return f(0);
    }

    @Override // android.view.View
    public boolean isNestedScrollingEnabled() {
        return this.y.d;
    }

    @Override // android.view.ViewGroup
    public void measureChild(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(FrameLayout.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft(), layoutParams.width), View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    @Override // android.view.ViewGroup
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(FrameLayout.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, 0));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.j = false;
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8 && !this.l) {
            float axisValue = motionEvent.getAxisValue(9);
            if (axisValue != 0.0f) {
                int verticalScrollFactorCompat = (int) (axisValue * getVerticalScrollFactorCompat());
                int scrollRange = getScrollRange();
                int scrollY = getScrollY();
                int i = scrollY - verticalScrollFactorCompat;
                if (i < 0) {
                    i = 0;
                } else if (i > scrollRange) {
                    i = scrollRange;
                }
                if (i != scrollY) {
                    super.scrollTo(getScrollX(), i);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0085  */
    /* JADX WARN: Code duplicated, block: B:36:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:49:0x00de  */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int action = motionEvent.getAction();
        if (action == 2 && this.l) {
            return true;
        }
        int i = action & 255;
        if (i == 0) {
            int y = (int) motionEvent.getY();
            int x = (int) motionEvent.getX();
            if (getChildCount() > 0) {
                int scrollY = getScrollY();
                View childAt = getChildAt(0);
                if (y < childAt.getTop() - scrollY || y >= childAt.getBottom() - scrollY || x < childAt.getLeft() || x >= childAt.getRight()) {
                    z = false;
                } else {
                    z = true;
                }
            } else {
                z = false;
            }
            if (z) {
                this.h = y;
                this.r = motionEvent.getPointerId(0);
                VelocityTracker velocityTracker = this.m;
                if (velocityTracker == null) {
                    this.m = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                this.m.addMovement(motionEvent);
                this.e.computeScrollOffset();
                this.l = !this.e.isFinished();
                c(2, 0);
            } else {
                this.l = false;
                b();
            }
        } else if (i == 1) {
            this.l = false;
            this.r = -1;
            b();
            if (this.e.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                n.g(this);
            }
            h(0);
        } else if (i == 2) {
            int i2 = this.r;
            if (i2 != -1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(i2);
                if (iFindPointerIndex == -1) {
                    Log.e("NestedScrollView", "Invalid pointerId=" + i2 + " in onInterceptTouchEvent");
                } else {
                    int y2 = (int) motionEvent.getY(iFindPointerIndex);
                    if (Math.abs(y2 - this.h) > this.mTouchSlop && (2 & getNestedScrollAxes()) == 0) {
                        this.l = true;
                        this.h = y2;
                        if (this.m == null) {
                            this.m = VelocityTracker.obtain();
                        }
                        this.m.addMovement(motionEvent);
                        this.u = 0;
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
            }
        } else if (i == 3) {
            this.l = false;
            this.r = -1;
            b();
            if (this.e.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                n.g(this);
            }
            h(0);
        } else if (i == 6) {
            a(motionEvent);
        }
        return this.l;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int measuredHeight = 0;
        this.i = false;
        View view = this.k;
        if (view != null && a(view, this)) {
            a(this.k);
        }
        this.k = null;
        if (!this.j) {
            if (this.w != null) {
                scrollTo(getScrollX(), this.w.f400a);
                this.w = null;
            }
            if (getChildCount() > 0) {
                View childAt = getChildAt(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                measuredHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
            int paddingTop = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
            int scrollY = getScrollY();
            int iA = a(scrollY, paddingTop, measuredHeight);
            if (iA != scrollY) {
                scrollTo(getScrollX(), iA);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.j = true;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.n && View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int measuredHeight2 = (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - layoutParams.topMargin) - layoutParams.bottomMargin;
            if (measuredHeight < measuredHeight2) {
                childAt.measure(FrameLayout.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width), View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824));
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (z) {
            return false;
        }
        d((int) f2);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        a(view, i, i2, iArr, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        a(view, i, i2, i3, i4, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public void onNestedScrollAccepted(View view, View view2, int i) {
        a(view, view2, i, 0);
    }

    @Override // android.view.View
    public void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        super.scrollTo(i, i2);
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        if (i == 2) {
            i = 130;
        } else if (i == 1) {
            i = 33;
        }
        View viewFindNextFocus = rect == null ? FocusFinder.getInstance().findNextFocus(this, null, i) : FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        if (viewFindNextFocus == null || (true ^ a(viewFindNextFocus, 0, getHeight()))) {
            return false;
        }
        return viewFindNextFocus.requestFocus(i, rect);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof c)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        c cVar = (c) parcelable;
        super.onRestoreInstanceState(cVar.getSuperState());
        this.w = cVar;
        requestLayout();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        c cVar = new c(super.onSaveInstanceState());
        cVar.f400a = getScrollY();
        return cVar;
    }

    @Override // android.view.View
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View viewFindFocus = findFocus();
        if (viewFindFocus == null || this == viewFindFocus || !a(viewFindFocus, 0, i4)) {
            return;
        }
        viewFindFocus.getDrawingRect(this.d);
        offsetDescendantRectToMyCoords(viewFindFocus, this.d);
        b(a(this.d));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return b(view, view2, i, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, a.b.d.h.j
    public void onStopNestedScroll(View view) {
        a(view, 0);
    }

    /* JADX WARN: Code duplicated, block: B:95:0x0220  */
    /* JADX WARN: Code duplicated, block: B:98:0x0231  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ViewParent parent;
        EdgeEffect edgeEffect;
        EdgeEffect edgeEffect2;
        if (this.m == null) {
            this.m = VelocityTracker.obtain();
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.u = 0;
        }
        motionEventObtain.offsetLocation(0.0f, this.u);
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                VelocityTracker velocityTracker = this.m;
                velocityTracker.computeCurrentVelocity(1000, this.q);
                int yVelocity = (int) velocityTracker.getYVelocity(this.r);
                if (Math.abs(yVelocity) > this.p) {
                    d(-yVelocity);
                } else if (this.e.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    n.g(this);
                }
                this.r = -1;
                this.l = false;
                b();
                h(0);
                edgeEffect = this.f;
                if (edgeEffect != null) {
                    edgeEffect.onRelease();
                    this.g.onRelease();
                }
            } else if (actionMasked == 2) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.r);
                if (iFindPointerIndex == -1) {
                    StringBuilder sbA = c.a.a.a.a.a("Invalid pointerId=");
                    sbA.append(this.r);
                    sbA.append(" in onTouchEvent");
                    Log.e("NestedScrollView", sbA.toString());
                } else {
                    int y = (int) motionEvent.getY(iFindPointerIndex);
                    int i = this.h - y;
                    if (a(0, i, this.t, this.s, 0)) {
                        i -= this.t[1];
                        motionEventObtain.offsetLocation(0.0f, this.s[1]);
                        this.u += this.s[1];
                    }
                    if (!this.l && Math.abs(i) > this.mTouchSlop) {
                        ViewParent parent2 = getParent();
                        if (parent2 != null) {
                            parent2.requestDisallowInterceptTouchEvent(true);
                        }
                        this.l = true;
                        i = i > 0 ? i - this.mTouchSlop : i + this.mTouchSlop;
                    }
                    int i2 = i;
                    if (this.l) {
                        this.h = y - this.s[1];
                        int scrollY = getScrollY();
                        int scrollRange = getScrollRange();
                        int overScrollMode = getOverScrollMode();
                        boolean z = overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0);
                        if (a(0, i2, 0, getScrollY(), 0, scrollRange, 0, 0, true) && !f(0)) {
                            this.m.clear();
                        }
                        int scrollY2 = getScrollY() - scrollY;
                        if (a(0, scrollY2, 0, i2 - scrollY2, this.s, 0)) {
                            int i3 = this.h;
                            int[] iArr = this.s;
                            this.h = i3 - iArr[1];
                            motionEventObtain.offsetLocation(0.0f, iArr[1]);
                            this.u += this.s[1];
                        } else if (z) {
                            a();
                            int i4 = scrollY + i2;
                            if (i4 < 0) {
                                EdgeEffect edgeEffect3 = this.f;
                                float height = i2 / getHeight();
                                float x = motionEvent.getX(iFindPointerIndex) / getWidth();
                                if (Build.VERSION.SDK_INT >= 21) {
                                    edgeEffect3.onPull(height, x);
                                } else {
                                    edgeEffect3.onPull(height);
                                }
                                if (!this.g.isFinished()) {
                                    edgeEffect2 = this.g;
                                    edgeEffect2.onRelease();
                                }
                            } else if (i4 > scrollRange) {
                                EdgeEffect edgeEffect4 = this.g;
                                float height2 = i2 / getHeight();
                                float x2 = 1.0f - (motionEvent.getX(iFindPointerIndex) / getWidth());
                                if (Build.VERSION.SDK_INT >= 21) {
                                    edgeEffect4.onPull(height2, x2);
                                } else {
                                    edgeEffect4.onPull(height2);
                                }
                                if (!this.f.isFinished()) {
                                    edgeEffect2 = this.f;
                                    edgeEffect2.onRelease();
                                }
                            }
                            EdgeEffect edgeEffect5 = this.f;
                            if (edgeEffect5 != null && (!edgeEffect5.isFinished() || !this.g.isFinished())) {
                                n.g(this);
                            }
                        }
                    }
                }
            } else if (actionMasked == 3) {
                if (this.l && getChildCount() > 0 && this.e.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    n.g(this);
                }
                this.r = -1;
                this.l = false;
                b();
                h(0);
                edgeEffect = this.f;
                if (edgeEffect != null) {
                    edgeEffect.onRelease();
                    this.g.onRelease();
                }
            } else if (actionMasked == 5) {
                int actionIndex = motionEvent.getActionIndex();
                this.h = (int) motionEvent.getY(actionIndex);
                this.r = motionEvent.getPointerId(actionIndex);
            } else if (actionMasked == 6) {
                a(motionEvent);
                this.h = (int) motionEvent.getY(motionEvent.findPointerIndex(this.r));
            }
        } else {
            if (getChildCount() == 0) {
                return false;
            }
            boolean z2 = !this.e.isFinished();
            this.l = z2;
            if (z2 && (parent = getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            if (!this.e.isFinished()) {
                this.e.abortAnimation();
            }
            this.h = (int) motionEvent.getY();
            this.r = motionEvent.getPointerId(0);
            c(2, 0);
        }
        VelocityTracker velocityTracker2 = this.m;
        if (velocityTracker2 != null) {
            velocityTracker2.addMovement(motionEventObtain);
        }
        motionEventObtain.recycle();
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (this.i) {
            this.k = view2;
        } else {
            a(view2);
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        int iA = a(rect);
        boolean z2 = iA != 0;
        if (z2) {
            if (z) {
                scrollBy(0, iA);
            } else {
                a(0, iA);
            }
        }
        return z2;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            b();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        this.i = true;
        super.requestLayout();
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
            int width2 = childAt.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int height2 = childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            int iA = a(i, width, width2);
            int iA2 = a(i2, height, height2);
            if (iA == getScrollX() && iA2 == getScrollY()) {
                return;
            }
            super.scrollTo(iA, iA2);
        }
    }

    public void setFillViewport(boolean z) {
        if (z != this.n) {
            this.n = z;
            requestLayout();
        }
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z) {
        h hVar = this.y;
        if (hVar.d) {
            n.i(hVar.f164c);
        }
        hVar.d = z;
    }

    public void setOnScrollChangeListener(b bVar) {
    }

    public void setSmoothScrollingEnabled(boolean z) {
        this.o = z;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override // android.view.View
    public boolean startNestedScroll(int i) {
        return c(i, 0);
    }

    @Override // android.view.View, a.b.d.h.g
    public void stopNestedScroll() {
        h(0);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = new Rect();
        this.i = true;
        this.j = false;
        this.k = null;
        this.l = false;
        this.o = true;
        this.r = -1;
        this.s = new int[2];
        this.t = new int[2];
        this.e = new OverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.p = viewConfiguration.getScaledMinimumFlingVelocity();
        this.q = viewConfiguration.getScaledMaximumFlingVelocity();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f398b, i, 0);
        setFillViewport(typedArrayObtainStyledAttributes.getBoolean(0, false));
        typedArrayObtainStyledAttributes.recycle();
        this.x = new k(this);
        this.y = new h(this);
        setNestedScrollingEnabled(true);
        n.a(this, f397a);
    }

    public boolean a(int i, int i2, int[] iArr, int[] iArr2, int i3) {
        ViewParent viewParentA;
        int i4;
        int i5;
        int[] iArr3;
        h hVar = this.y;
        if (!hVar.d || (viewParentA = hVar.a(i3)) == null) {
            return false;
        }
        if (i == 0 && i2 == 0) {
            if (iArr2 == null) {
                return false;
            }
            iArr2[0] = 0;
            iArr2[1] = 0;
            return false;
        }
        if (iArr2 != null) {
            hVar.f164c.getLocationInWindow(iArr2);
            i4 = iArr2[0];
            i5 = iArr2[1];
        } else {
            i4 = 0;
            i5 = 0;
        }
        if (iArr == null) {
            if (hVar.e == null) {
                hVar.e = new int[2];
            }
            iArr3 = hVar.e;
        } else {
            iArr3 = iArr;
        }
        iArr3[0] = 0;
        iArr3[1] = 0;
        View view = hVar.f164c;
        if (viewParentA instanceof i) {
            ((i) viewParentA).a(view, i, i2, iArr3, i3);
        } else if (i3 == 0) {
            if (Build.VERSION.SDK_INT >= 21) {
                try {
                    viewParentA.onNestedPreScroll(view, i, i2, iArr3);
                } catch (AbstractMethodError e) {
                    Log.e("ViewParentCompat", c.a.a.a.a.a("ViewParent ", viewParentA, " does not implement interface ", "method onNestedPreScroll"), e);
                }
            } else if (viewParentA instanceof j) {
                ((j) viewParentA).onNestedPreScroll(view, i, i2, iArr3);
            }
        }
        if (iArr2 != null) {
            hVar.f164c.getLocationInWindow(iArr2);
            iArr2[0] = iArr2[0] - i4;
            iArr2[1] = iArr2[1] - i5;
        }
        return (iArr3[0] == 0 && iArr3[1] == 0) ? false : true;
    }

    public boolean a(int i, int i2, int i3, int i4, int[] iArr, int i5) {
        ViewParent viewParentA;
        int i6;
        int i7;
        h hVar = this.y;
        if (!hVar.d || (viewParentA = hVar.a(i5)) == null) {
            return false;
        }
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            if (iArr == null) {
                return false;
            }
            iArr[0] = 0;
            iArr[1] = 0;
            return false;
        }
        if (iArr != null) {
            hVar.f164c.getLocationInWindow(iArr);
            i6 = iArr[0];
            i7 = iArr[1];
        } else {
            i6 = 0;
            i7 = 0;
        }
        View view = hVar.f164c;
        if (viewParentA instanceof i) {
            ((i) viewParentA).a(view, i, i2, i3, i4, i5);
        } else if (i5 == 0) {
            if (Build.VERSION.SDK_INT >= 21) {
                try {
                    viewParentA.onNestedScroll(view, i, i2, i3, i4);
                } catch (AbstractMethodError e) {
                    Log.e("ViewParentCompat", c.a.a.a.a.a("ViewParent ", viewParentA, " does not implement interface ", "method onNestedScroll"), e);
                }
            } else if (viewParentA instanceof j) {
                ((j) viewParentA).onNestedScroll(view, i, i2, i3, i4);
            }
        }
        if (iArr != null) {
            hVar.f164c.getLocationInWindow(iArr);
            iArr[0] = iArr[0] - i6;
            iArr[1] = iArr[1] - i7;
        }
        return true;
    }

    static class a extends d {
        @Override // a.b.d.h.d
        public void a(View view, a.b.d.h.a.a aVar) {
            int scrollRange;
            d.f158a.onInitializeAccessibilityNodeInfo(view, aVar.f149a);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            aVar.f149a.setClassName(ScrollView.class.getName());
            if (!nestedScrollView.isEnabled() || (scrollRange = nestedScrollView.getScrollRange()) <= 0) {
                return;
            }
            aVar.f149a.setScrollable(true);
            if (nestedScrollView.getScrollY() > 0) {
                aVar.f149a.addAction(8192);
            }
            if (nestedScrollView.getScrollY() < scrollRange) {
                aVar.f149a.addAction(4096);
            }
        }

        @Override // a.b.d.h.d
        public void b(View view, AccessibilityEvent accessibilityEvent) {
            d.f158a.onInitializeAccessibilityEvent(view, accessibilityEvent);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityEvent.setClassName(ScrollView.class.getName());
            accessibilityEvent.setScrollable(nestedScrollView.getScrollRange() > 0);
            accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
            accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
            int scrollX = nestedScrollView.getScrollX();
            int i = Build.VERSION.SDK_INT;
            accessibilityEvent.setMaxScrollX(scrollX);
            int scrollRange = nestedScrollView.getScrollRange();
            int i2 = Build.VERSION.SDK_INT;
            accessibilityEvent.setMaxScrollY(scrollRange);
        }

        @Override // a.b.d.h.d
        public boolean a(View view, int i, Bundle bundle) {
            int i2 = Build.VERSION.SDK_INT;
            if (d.f158a.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            if (!nestedScrollView.isEnabled()) {
                return false;
            }
            if (i == 4096) {
                int iMin = Math.min(nestedScrollView.getScrollY() + ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.getScrollRange());
                if (iMin == nestedScrollView.getScrollY()) {
                    return false;
                }
                nestedScrollView.b(0, iMin);
                return true;
            }
            if (i != 8192) {
                return false;
            }
            int iMax = Math.max(nestedScrollView.getScrollY() - ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
            if (iMax == nestedScrollView.getScrollY()) {
                return false;
            }
            nestedScrollView.b(0, iMax);
            return true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0033  */
    public boolean a(KeyEvent keyEvent) {
        boolean z;
        this.d.setEmpty();
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin > (getHeight() - getPaddingTop()) - getPaddingBottom()) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        if (!z) {
            if (!isFocused() || keyEvent.getKeyCode() == 4) {
                return false;
            }
            View viewFindFocus = findFocus();
            if (viewFindFocus == this) {
                viewFindFocus = null;
            }
            View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, 130);
            return (viewFindNextFocus == null || viewFindNextFocus == this || !viewFindNextFocus.requestFocus(130)) ? false : true;
        }
        if (keyEvent.getAction() != 0) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 19) {
            return !keyEvent.isAltPressed() ? a(33) : e(33);
        }
        if (keyCode == 20) {
            return !keyEvent.isAltPressed() ? a(130) : e(130);
        }
        if (keyCode != 62) {
            return false;
        }
        g(keyEvent.isShiftPressed() ? 33 : 130);
        return false;
    }

    @Override // a.b.d.h.i
    public void a(View view, View view2, int i, int i2) {
        this.x.f165a = i;
        c(2, i2);
    }

    @Override // a.b.d.h.i
    public void a(View view, int i) {
        this.x.f165a = 0;
        h(i);
    }
}
