package android.support.v4.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.AbsSavedState;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class SlidingPaneLayout extends ViewGroup {
    private float Af;
    private float Bf;
    private int ai;
    private int bi;
    boolean ch;
    private Drawable di;
    private Drawable ei;
    private final int fi;
    private boolean gi;
    View hi;
    float ii;
    private float ji;
    int ki;
    private int li;
    private final Rect mTmpRect;
    private boolean mh;
    final G mi;
    boolean ni;
    final ArrayList oi;

    public class LayoutParams extends ViewGroup.MarginLayoutParams {
        private static final int[] ATTRS = {R.attr.layout_weight};
        boolean dk;
        boolean ek;
        Paint fk;
        public float weight;

        public LayoutParams() {
            super(-1, -1);
            this.weight = 0.0f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.weight = 0.0f;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
            this.weight = typedArrayObtainStyledAttributes.getFloat(0, 0.0f);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.weight = 0.0f;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.weight = 0.0f;
        }
    }

    class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new u();
        boolean isOpen;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.isOpen = parcel.readInt() != 0;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isOpen ? 1 : 0);
        }
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ai = -858993460;
        this.mh = true;
        this.mTmpRect = new Rect();
        this.oi = new ArrayList();
        float f = context.getResources().getDisplayMetrics().density;
        this.fi = (int) ((32.0f * f) + 0.5f);
        setWillNotDraw(false);
        android.support.v4.view.r.a(this, new r(this));
        int i2 = Build.VERSION.SDK_INT;
        setImportantForAccessibility(1);
        this.mi = G.a(this, 0.5f, new t(this));
        this.mi.c(f * 400.0f);
    }

    private boolean W(int i) {
        if (!this.mh && !a(0.0f, i)) {
            return false;
        }
        this.ni = false;
        return true;
    }

    private void a(View view, float f, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f > 0.0f && i != 0) {
            int i2 = (((int) ((((-16777216) & i) >>> 24) * f)) << 24) | (i & 16777215);
            if (layoutParams.fk == null) {
                layoutParams.fk = new Paint();
            }
            layoutParams.fk.setColorFilter(new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_OVER));
            if (view.getLayerType() != 2) {
                view.setLayerType(2, layoutParams.fk);
            }
            H(view);
            return;
        }
        if (view.getLayerType() != 0) {
            Paint paint = layoutParams.fk;
            if (paint != null) {
                paint.setColorFilter(null);
            }
            s sVar = new s(this, view);
            this.oi.add(sVar);
            android.support.v4.view.r.a(this, sVar);
        }
    }

    /* JADX WARN: Code duplicated, block: B:9:0x001c  */
    private void i(float f) {
        boolean z;
        boolean zBa = ba();
        LayoutParams layoutParams = (LayoutParams) this.hi.getLayoutParams();
        if (!layoutParams.ek) {
            z = false;
        } else if ((zBa ? ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) <= 0) {
            z = true;
        } else {
            z = false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != this.hi) {
                float f2 = 1.0f - this.ji;
                int i2 = this.li;
                this.ji = f;
                int i3 = ((int) (f2 * i2)) - ((int) ((1.0f - f) * i2));
                if (zBa) {
                    i3 = -i3;
                }
                childAt.offsetLeftAndRight(i3);
                if (z) {
                    float f3 = this.ji;
                    a(childAt, zBa ? f3 - 1.0f : 1.0f - f3, this.bi);
                }
            }
        }
    }

    void E(View view) {
        sendAccessibilityEvent(32);
    }

    void F(View view) {
        sendAccessibilityEvent(32);
    }

    void G(View view) {
    }

    void H(View view) {
        int i = Build.VERSION.SDK_INT;
        android.support.v4.view.r.a(view, ((LayoutParams) view.getLayoutParams()).fk);
    }

    boolean I(View view) {
        if (view == null) {
            return false;
        }
        return this.gi && ((LayoutParams) view.getLayoutParams()).ek && this.ii > 0.0f;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0053  */
    void J(View view) {
        int left;
        int right;
        int top;
        int bottom;
        boolean z;
        View view2 = view;
        boolean zBa = ba();
        int width = zBa ? getWidth() - getPaddingRight() : getPaddingLeft();
        int paddingLeft = zBa ? getPaddingLeft() : getWidth() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        if (view2 == null) {
            left = 0;
            right = 0;
            top = 0;
            bottom = 0;
        } else {
            if (view.isOpaque()) {
                z = true;
            } else {
                int i = Build.VERSION.SDK_INT;
                z = false;
            }
            if (z) {
                left = view.getLeft();
                right = view.getRight();
                top = view.getTop();
                bottom = view.getBottom();
            } else {
                left = 0;
                right = 0;
                top = 0;
                bottom = 0;
            }
        }
        int childCount = getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = getChildAt(i2);
            if (childAt == view2) {
                return;
            }
            if (childAt.getVisibility() != 8) {
                childAt.setVisibility((Math.max(zBa ? paddingLeft : width, childAt.getLeft()) < left || Math.max(paddingTop, childAt.getTop()) < top || Math.min(zBa ? width : paddingLeft, childAt.getRight()) > right || Math.min(height, childAt.getBottom()) > bottom) ? 0 : 4);
            }
            i2++;
            view2 = view;
            zBa = zBa;
        }
    }

    boolean a(float f, int i) {
        int paddingLeft;
        if (!this.gi) {
            return false;
        }
        boolean zBa = ba();
        LayoutParams layoutParams = (LayoutParams) this.hi.getLayoutParams();
        if (zBa) {
            int paddingRight = getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            paddingLeft = (int) (getWidth() - (((f * this.ki) + paddingRight) + this.hi.getWidth()));
        } else {
            paddingLeft = (int) ((f * this.ki) + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin);
        }
        G g = this.mi;
        View view = this.hi;
        if (!g.d(view, paddingLeft, view.getTop())) {
            return false;
        }
        ea();
        android.support.v4.view.r.n(this);
        return true;
    }

    public boolean aa() {
        return W(0);
    }

    boolean ba() {
        return android.support.v4.view.r.f(this) == 1;
    }

    public boolean ca() {
        return this.gi;
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mi.i(true)) {
            if (this.gi) {
                android.support.v4.view.r.n(this);
            } else {
                this.mi.abort();
            }
        }
    }

    public boolean da() {
        if (!this.mh && !a(1.0f, 0)) {
            return false;
        }
        this.ni = true;
        return true;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        int i;
        int right;
        super.draw(canvas);
        Drawable drawable = ba() ? this.ei : this.di;
        View childAt = getChildCount() > 1 ? getChildAt(1) : null;
        if (childAt == null || drawable == null) {
            return;
        }
        int top = childAt.getTop();
        int bottom = childAt.getBottom();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (ba()) {
            right = childAt.getRight();
            i = intrinsicWidth + right;
        } else {
            int left = childAt.getLeft();
            int i2 = left - intrinsicWidth;
            i = left;
            right = i2;
        }
        drawable.setBounds(right, top, i, bottom);
        drawable.draw(canvas);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int iSave = canvas.save();
        if (this.gi && !layoutParams.dk && this.hi != null) {
            canvas.getClipBounds(this.mTmpRect);
            if (ba()) {
                Rect rect = this.mTmpRect;
                rect.left = Math.max(rect.left, this.hi.getRight());
            } else {
                Rect rect2 = this.mTmpRect;
                rect2.right = Math.min(rect2.right, this.hi.getLeft());
            }
            canvas.clipRect(this.mTmpRect);
        }
        boolean zDrawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(iSave);
        return zDrawChild;
    }

    void ea() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public boolean isOpen() {
        return !this.gi || this.ii == 1.0f;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mh = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mh = true;
        int size = this.oi.size();
        for (int i = 0; i < size; i++) {
            s sVar = (s) this.oi.get(i);
            if (sVar.uf.getParent() == sVar.n) {
                sVar.uf.setLayerType(0, null);
                sVar.n.H(sVar.uf);
            }
            sVar.n.oi.remove(sVar);
        }
        this.oi.clear();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View childAt;
        int actionMasked = motionEvent.getActionMasked();
        if (!this.gi && actionMasked == 0 && getChildCount() > 1 && (childAt = getChildAt(1)) != null) {
            this.ni = !this.mi.c(childAt, (int) motionEvent.getX(), (int) motionEvent.getY());
        }
        if (!this.gi || (this.ch && actionMasked != 0)) {
            this.mi.cancel();
            return super.onInterceptTouchEvent(motionEvent);
        }
        if (actionMasked == 3 || actionMasked == 1) {
            this.mi.cancel();
            return false;
        }
        if (actionMasked == 0) {
            this.ch = false;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.Af = x;
            this.Bf = y;
            if (this.mi.c(this.hi, (int) x, (int) y) && I(this.hi)) {
                z = true;
            }
            return this.mi.b(motionEvent) || z;
        }
        if (actionMasked == 2) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            float fAbs = Math.abs(x2 - this.Af);
            float fAbs2 = Math.abs(y2 - this.Bf);
            if (fAbs > this.mi.getTouchSlop() && fAbs2 > fAbs) {
                this.mi.cancel();
                this.ch = true;
                return false;
            }
        }
        z = false;
        if (this.mi.b(motionEvent)) {
            return true;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean zBa = ba();
        G g = this.mi;
        if (zBa) {
            g.s(2);
        } else {
            g.s(1);
        }
        int i10 = i3 - i;
        int paddingRight = zBa ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = zBa ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.mh) {
            this.ii = (this.gi && this.ni) ? 1.0f : 0.0f;
        }
        int width = paddingRight;
        int i11 = width;
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt = getChildAt(i12);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                if (layoutParams.dk) {
                    int i13 = i10 - paddingLeft;
                    int iMin = (Math.min(width, i13 - this.fi) - i11) - (((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                    this.ki = iMin;
                    int i14 = zBa ? ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    layoutParams.ek = (measuredWidth / 2) + ((i11 + i14) + iMin) > i13;
                    int i15 = (int) (iMin * this.ii);
                    i5 = i14 + i15 + i11;
                    this.ii = i15 / this.ki;
                    i6 = 0;
                } else if (!this.gi || (i7 = this.li) == 0) {
                    i5 = width;
                    i6 = 0;
                } else {
                    i6 = (int) ((1.0f - this.ii) * i7);
                    i5 = width;
                }
                if (zBa) {
                    i9 = (i10 - i5) + i6;
                    i8 = i9 - measuredWidth;
                } else {
                    i8 = i5 - i6;
                    i9 = i8 + measuredWidth;
                }
                childAt.layout(i8, paddingTop, i9, childAt.getMeasuredHeight() + paddingTop);
                i11 = i5;
                width = childAt.getWidth() + width;
            }
        }
        if (this.mh) {
            if (this.gi) {
                if (this.li != 0) {
                    i(this.ii);
                }
                if (((LayoutParams) this.hi.getLayoutParams()).ek) {
                    a(this.hi, this.ii, this.ai);
                }
            } else {
                for (int i16 = 0; i16 < childCount; i16++) {
                    a(getChildAt(i16), 0.0f, this.ai);
                }
            }
            J(this.hi);
        }
        this.mh = false;
    }

    /* JADX WARN: Code duplicated, block: B:123:0x01e3  */
    /* JADX WARN: Code duplicated, block: B:125:0x01f4  */
    /* JADX WARN: Code duplicated, block: B:126:0x01f9  */
    /* JADX WARN: Code duplicated, block: B:40:0x00ae A[PHI: r12
      0x00ae: PHI (r12v2 float) = (r12v1 float), (r12v3 float) binds: [B:36:0x00a5, B:38:0x00aa] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:42:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:43:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:45:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:46:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:50:0x00da  */
    /* JADX WARN: Code duplicated, block: B:52:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:54:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:55:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:58:0x0100 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:62:0x0109  */
    /* JADX WARN: Code duplicated, block: B:63:0x010c  */
    /* JADX WARN: Code duplicated, block: B:66:0x0114  */
    /* JADX WARN: Code duplicated, block: B:76:0x0138  */
    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int paddingTop;
        int paddingTop2;
        int i3;
        int i4;
        int measuredHeight;
        int iMakeMeasureSpec;
        int i5;
        int i6;
        int iMakeMeasureSpec2;
        int i7;
        int measuredHeight2;
        int iMakeMeasureSpec3;
        int i8;
        int i9;
        int i10;
        int iMakeMeasureSpec4;
        int iMakeMeasureSpec5;
        int i11;
        int measuredHeight3;
        boolean z;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            }
            if (mode != Integer.MIN_VALUE && mode == 0) {
                size = 300;
            }
        } else if (mode2 == 0) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Height must not be UNSPECIFIED");
            }
            if (mode2 == 0) {
                size2 = 300;
                mode2 = Integer.MIN_VALUE;
            }
        }
        boolean z2 = false;
        if (mode2 != Integer.MIN_VALUE) {
            paddingTop2 = mode2 != 1073741824 ? 0 : (size2 - getPaddingTop()) - getPaddingBottom();
            paddingTop = paddingTop2;
        } else {
            paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
            paddingTop2 = 0;
        }
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int childCount = getChildCount();
        if (childCount > 2) {
            Log.e("SlidingPaneLayout", "onMeasure: More than two child views are not supported.");
        }
        this.hi = null;
        boolean z3 = false;
        int iMin = paddingTop2;
        int i12 = paddingLeft;
        float f = 0.0f;
        int i13 = 0;
        while (true) {
            i3 = 8;
            if (i13 >= childCount) {
                break;
            }
            View childAt = getChildAt(i13);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() == 8) {
                layoutParams.ek = z2;
            } else {
                float f2 = layoutParams.weight;
                if (f2 > 0.0f) {
                    f += f2;
                    if (((ViewGroup.MarginLayoutParams) layoutParams).width != 0) {
                        i8 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                        if (((ViewGroup.MarginLayoutParams) layoutParams).width == -2) {
                            iMakeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(paddingLeft - i8, Integer.MIN_VALUE);
                        } else {
                            if (((ViewGroup.MarginLayoutParams) layoutParams).width == -1) {
                                i10 = paddingLeft - i8;
                                i9 = 1073741824;
                            } else {
                                i9 = 1073741824;
                                i10 = ((ViewGroup.MarginLayoutParams) layoutParams).width;
                            }
                            iMakeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(i10, i9);
                        }
                        if (((ViewGroup.MarginLayoutParams) layoutParams).height == -2) {
                            i11 = Integer.MIN_VALUE;
                        } else {
                            if (((ViewGroup.MarginLayoutParams) layoutParams).height == -1) {
                                i11 = 1073741824;
                            } else {
                                iMakeMeasureSpec5 = View.MeasureSpec.makeMeasureSpec(((ViewGroup.MarginLayoutParams) layoutParams).height, 1073741824);
                            }
                            childAt.measure(iMakeMeasureSpec4, iMakeMeasureSpec5);
                            int measuredWidth = childAt.getMeasuredWidth();
                            measuredHeight3 = childAt.getMeasuredHeight();
                            if (mode2 == Integer.MIN_VALUE && measuredHeight3 > iMin) {
                                iMin = Math.min(measuredHeight3, paddingTop);
                            }
                            i12 -= measuredWidth;
                            if (i12 < 0) {
                                z = true;
                            } else {
                                z = false;
                            }
                            layoutParams.dk = z;
                            boolean z4 = z | z3;
                            if (layoutParams.dk) {
                                this.hi = childAt;
                            }
                            z3 = z4;
                        }
                        iMakeMeasureSpec5 = View.MeasureSpec.makeMeasureSpec(paddingTop, i11);
                        childAt.measure(iMakeMeasureSpec4, iMakeMeasureSpec5);
                        int measuredWidth2 = childAt.getMeasuredWidth();
                        measuredHeight3 = childAt.getMeasuredHeight();
                        if (mode2 == Integer.MIN_VALUE) {
                            iMin = Math.min(measuredHeight3, paddingTop);
                        }
                        i12 -= measuredWidth2;
                        if (i12 < 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        layoutParams.dk = z;
                        boolean z5 = z | z3;
                        if (layoutParams.dk) {
                            this.hi = childAt;
                        }
                        z3 = z5;
                    }
                } else {
                    i8 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                    if (((ViewGroup.MarginLayoutParams) layoutParams).width == -2) {
                        iMakeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(paddingLeft - i8, Integer.MIN_VALUE);
                    } else {
                        if (((ViewGroup.MarginLayoutParams) layoutParams).width == -1) {
                            i10 = paddingLeft - i8;
                            i9 = 1073741824;
                        } else {
                            i9 = 1073741824;
                            i10 = ((ViewGroup.MarginLayoutParams) layoutParams).width;
                        }
                        iMakeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(i10, i9);
                    }
                    if (((ViewGroup.MarginLayoutParams) layoutParams).height == -2) {
                        i11 = Integer.MIN_VALUE;
                    } else {
                        if (((ViewGroup.MarginLayoutParams) layoutParams).height == -1) {
                            i11 = 1073741824;
                        } else {
                            iMakeMeasureSpec5 = View.MeasureSpec.makeMeasureSpec(((ViewGroup.MarginLayoutParams) layoutParams).height, 1073741824);
                        }
                        childAt.measure(iMakeMeasureSpec4, iMakeMeasureSpec5);
                        int measuredWidth3 = childAt.getMeasuredWidth();
                        measuredHeight3 = childAt.getMeasuredHeight();
                        if (mode2 == Integer.MIN_VALUE) {
                            iMin = Math.min(measuredHeight3, paddingTop);
                        }
                        i12 -= measuredWidth3;
                        if (i12 < 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        layoutParams.dk = z;
                        boolean z6 = z | z3;
                        if (layoutParams.dk) {
                            this.hi = childAt;
                        }
                        z3 = z6;
                    }
                    iMakeMeasureSpec5 = View.MeasureSpec.makeMeasureSpec(paddingTop, i11);
                    childAt.measure(iMakeMeasureSpec4, iMakeMeasureSpec5);
                    int measuredWidth4 = childAt.getMeasuredWidth();
                    measuredHeight3 = childAt.getMeasuredHeight();
                    if (mode2 == Integer.MIN_VALUE) {
                        iMin = Math.min(measuredHeight3, paddingTop);
                    }
                    i12 -= measuredWidth4;
                    if (i12 < 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    layoutParams.dk = z;
                    boolean z7 = z | z3;
                    if (layoutParams.dk) {
                        this.hi = childAt;
                    }
                    z3 = z7;
                }
            }
            i13++;
            z2 = false;
        }
        if (z3 || f > 0.0f) {
            int i14 = paddingLeft - this.fi;
            int i15 = 0;
            while (i15 < childCount) {
                View childAt2 = getChildAt(i15);
                if (childAt2.getVisibility() == i3) {
                    i5 = i14;
                } else {
                    LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                    if (childAt2.getVisibility() != i3) {
                        boolean z8 = ((ViewGroup.MarginLayoutParams) layoutParams2).width == 0 && layoutParams2.weight > 0.0f;
                        int measuredWidth5 = z8 ? 0 : childAt2.getMeasuredWidth();
                        if (!z3 || childAt2 == this.hi) {
                            if (layoutParams2.weight > 0.0f) {
                                if (((ViewGroup.MarginLayoutParams) layoutParams2).width == 0) {
                                    if (((ViewGroup.MarginLayoutParams) layoutParams2).height == -2) {
                                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(paddingTop, Integer.MIN_VALUE);
                                    } else if (((ViewGroup.MarginLayoutParams) layoutParams2).height == -1) {
                                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(paddingTop, 1073741824);
                                    } else {
                                        i4 = 1073741824;
                                        measuredHeight = ((ViewGroup.MarginLayoutParams) layoutParams2).height;
                                    }
                                    if (z3) {
                                        i6 = paddingLeft - (((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin);
                                        i5 = i14;
                                        iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i6, 1073741824);
                                        if (measuredWidth5 != i6) {
                                            childAt2.measure(iMakeMeasureSpec2, iMakeMeasureSpec);
                                        }
                                    } else {
                                        i5 = i14;
                                        childAt2.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth5 + ((int) ((layoutParams2.weight * Math.max(0, i12)) / f)), 1073741824), iMakeMeasureSpec);
                                    }
                                } else {
                                    i4 = 1073741824;
                                    measuredHeight = childAt2.getMeasuredHeight();
                                }
                                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, i4);
                                if (z3) {
                                    i6 = paddingLeft - (((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin);
                                    i5 = i14;
                                    iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i6, 1073741824);
                                    if (measuredWidth5 != i6) {
                                        childAt2.measure(iMakeMeasureSpec2, iMakeMeasureSpec);
                                    }
                                } else {
                                    i5 = i14;
                                    childAt2.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth5 + ((int) ((layoutParams2.weight * Math.max(0, i12)) / f)), 1073741824), iMakeMeasureSpec);
                                }
                            }
                        } else if (((ViewGroup.MarginLayoutParams) layoutParams2).width < 0 && (measuredWidth5 > i14 || layoutParams2.weight > 0.0f)) {
                            if (z8) {
                                if (((ViewGroup.MarginLayoutParams) layoutParams2).height == -2) {
                                    iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(paddingTop, Integer.MIN_VALUE);
                                    i7 = 1073741824;
                                } else if (((ViewGroup.MarginLayoutParams) layoutParams2).height == -1) {
                                    i7 = 1073741824;
                                    iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(paddingTop, 1073741824);
                                } else {
                                    i7 = 1073741824;
                                    measuredHeight2 = ((ViewGroup.MarginLayoutParams) layoutParams2).height;
                                }
                                childAt2.measure(View.MeasureSpec.makeMeasureSpec(i14, i7), iMakeMeasureSpec3);
                            } else {
                                i7 = 1073741824;
                                measuredHeight2 = childAt2.getMeasuredHeight();
                            }
                            iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, i7);
                            childAt2.measure(View.MeasureSpec.makeMeasureSpec(i14, i7), iMakeMeasureSpec3);
                        }
                        i5 = i14;
                    } else {
                        i5 = i14;
                    }
                }
                i15++;
                i14 = i5;
                i3 = 8;
            }
        }
        setMeasuredDimension(size, getPaddingBottom() + getPaddingTop() + iMin);
        this.gi = z3;
        if (this.mi.Q() == 0 || z3) {
            return;
        }
        this.mi.abort();
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.isOpen) {
            da();
        } else {
            aa();
        }
        this.ni = savedState.isOpen;
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isOpen = ca() ? isOpen() : this.ni;
        return savedState;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            this.mh = true;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.gi) {
            return super.onTouchEvent(motionEvent);
        }
        this.mi.a(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.Af = x;
            this.Bf = y;
        } else if (actionMasked == 1 && I(this.hi)) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            float f = x2 - this.Af;
            float f2 = y2 - this.Bf;
            int touchSlop = this.mi.getTouchSlop();
            if ((f2 * f2) + (f * f) < touchSlop * touchSlop && this.mi.c(this.hi, (int) x2, (int) y2)) {
                W(0);
            }
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (isInTouchMode() || this.gi) {
            return;
        }
        this.ni = view == this.hi;
    }

    void y(int i) {
        if (this.hi == null) {
            this.ii = 0.0f;
            return;
        }
        boolean zBa = ba();
        LayoutParams layoutParams = (LayoutParams) this.hi.getLayoutParams();
        int width = this.hi.getWidth();
        if (zBa) {
            i = (getWidth() - i) - width;
        }
        this.ii = (i - ((zBa ? getPaddingRight() : getPaddingLeft()) + (zBa ? ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin))) / this.ki;
        if (this.li != 0) {
            i(this.ii);
        }
        if (layoutParams.ek) {
            a(this.hi, this.ii, this.ai);
        }
        G(this.hi);
    }
}
