package a.b.e.f;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/* JADX INFO: loaded from: classes.dex */
public class t extends ViewGroup {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f321b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f322c;
    public int d;
    public int e;
    public int f;
    public int g;
    public float h;
    public boolean i;
    public int[] j;
    public int[] k;
    public Drawable l;
    public int m;
    public int n;
    public int o;
    public int p;

    public static class a extends ViewGroup.MarginLayoutParams {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public float f323a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f324b;

        public a(int i, int i2) {
            super(i, i2);
            this.f324b = -1;
            this.f323a = 0.0f;
        }

        public a(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f324b = -1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.b.e.b.b.m);
            this.f323a = typedArrayObtainStyledAttributes.getFloat(3, 0.0f);
            this.f324b = typedArrayObtainStyledAttributes.getInt(0, -1);
            typedArrayObtainStyledAttributes.recycle();
        }

        public a(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f324b = -1;
        }
    }

    public t(Context context) {
        this(context, null);
    }

    public t(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public t(Context context, AttributeSet attributeSet, int i) {
        int resourceId;
        super(context, attributeSet, i);
        this.f321b = true;
        this.f322c = -1;
        this.d = 0;
        this.f = 8388659;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.b.e.b.b.l, i, 0);
        int i2 = typedArrayObtainStyledAttributes.getInt(1, -1);
        if (i2 >= 0) {
            setOrientation(i2);
        }
        int i3 = typedArrayObtainStyledAttributes.getInt(0, -1);
        if (i3 >= 0) {
            setGravity(i3);
        }
        boolean z = typedArrayObtainStyledAttributes.getBoolean(2, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.h = typedArrayObtainStyledAttributes.getFloat(4, -1.0f);
        this.f322c = typedArrayObtainStyledAttributes.getInt(3, -1);
        this.i = typedArrayObtainStyledAttributes.getBoolean(7, false);
        setDividerDrawable((!typedArrayObtainStyledAttributes.hasValue(5) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(5, 0)) == 0) ? typedArrayObtainStyledAttributes.getDrawable(5) : a.b.e.c.a.a.b(context, resourceId));
        this.o = typedArrayObtainStyledAttributes.getInt(8, 0);
        this.p = typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof a;
    }

    public void d(Canvas canvas, int i) {
        this.l.setBounds(getPaddingLeft() + this.p, i, (getWidth() - getPaddingRight()) - this.p, this.n + i);
        this.l.draw(canvas);
    }

    public void e(Canvas canvas, int i) {
        this.l.setBounds(i, getPaddingTop() + this.p, this.m + i, (getHeight() - getPaddingBottom()) - this.p);
        this.l.draw(canvas);
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public a generateDefaultLayoutParams() {
        int i = this.e;
        if (i == 0) {
            return new a(-2, -2);
        }
        if (i == 1) {
            return new a(-1, -2);
        }
        return null;
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public a generateLayoutParams(AttributeSet attributeSet) {
        return new a(getContext(), attributeSet);
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.f322c < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i2 = this.f322c;
        if (childCount <= i2) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i2);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.f322c == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int bottom = this.d;
        if (this.e == 1 && (i = this.f & 112) != 48) {
            if (i == 16) {
                bottom += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.g) / 2;
            } else if (i == 80) {
                bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.g;
            }
        }
        return bottom + ((ViewGroup.MarginLayoutParams) ((a) childAt.getLayoutParams())).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.f322c;
    }

    public Drawable getDividerDrawable() {
        return this.l;
    }

    public int getDividerPadding() {
        return this.p;
    }

    public int getDividerWidth() {
        return this.m;
    }

    public int getGravity() {
        return this.f;
    }

    public int getOrientation() {
        return this.e;
    }

    public int getShowDividers() {
        return this.o;
    }

    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.h;
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
    public a generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new a(layoutParams);
    }

    public int i() {
        return 0;
    }

    public int j() {
        return 0;
    }

    public int k() {
        return 0;
    }

    public boolean l(int i) {
        if (i == 0) {
            return (this.o & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.o & 4) != 0;
        }
        if ((this.o & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    public void m(View view, int i, int i2, int i3, int i4) {
        measureChildWithMargins(view, i, i2, i3, i4);
    }

    public int n() {
        return 0;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int right;
        int left;
        int paddingRight;
        int bottom;
        if (this.l == null) {
            return;
        }
        int i = 0;
        if (this.e == 1) {
            int virtualChildCount = getVirtualChildCount();
            while (i < virtualChildCount) {
                View childAt = getChildAt(i);
                if (childAt != null && childAt.getVisibility() != 8 && l(i)) {
                    d(canvas, (childAt.getTop() - ((ViewGroup.MarginLayoutParams) ((a) childAt.getLayoutParams())).topMargin) - this.n);
                }
                i++;
            }
            if (l(virtualChildCount)) {
                View childAt2 = getChildAt(virtualChildCount - 1);
                if (childAt2 == null) {
                    bottom = (getHeight() - getPaddingBottom()) - this.n;
                } else {
                    bottom = childAt2.getBottom() + ((ViewGroup.MarginLayoutParams) ((a) childAt2.getLayoutParams())).bottomMargin;
                }
                d(canvas, bottom);
                return;
            }
            return;
        }
        int virtualChildCount2 = getVirtualChildCount();
        boolean zA = m0.a(this);
        while (i < virtualChildCount2) {
            View childAt3 = getChildAt(i);
            if (childAt3 != null && childAt3.getVisibility() != 8 && l(i)) {
                a aVar = (a) childAt3.getLayoutParams();
                e(canvas, zA ? childAt3.getRight() + ((ViewGroup.MarginLayoutParams) aVar).rightMargin : (childAt3.getLeft() - ((ViewGroup.MarginLayoutParams) aVar).leftMargin) - this.m);
            }
            i++;
        }
        if (l(virtualChildCount2)) {
            View childAt4 = getChildAt(virtualChildCount2 - 1);
            if (childAt4 != null) {
                a aVar2 = (a) childAt4.getLayoutParams();
                if (zA) {
                    left = childAt4.getLeft();
                    paddingRight = ((ViewGroup.MarginLayoutParams) aVar2).leftMargin;
                    right = (left - paddingRight) - this.m;
                } else {
                    right = childAt4.getRight() + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin;
                }
            } else if (zA) {
                right = getPaddingLeft();
            } else {
                left = getWidth();
                paddingRight = getPaddingRight();
                right = (left - paddingRight) - this.m;
            }
            e(canvas, right);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(t.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(t.class.getName());
    }

    /* JADX WARN: Code duplicated, block: B:29:0x009d  */
    /* JADX WARN: Code duplicated, block: B:60:0x016c  */
    /* JADX WARN: Code duplicated, block: B:63:0x0176  */
    /* JADX WARN: Code duplicated, block: B:65:0x017a  */
    /* JADX WARN: Code duplicated, block: B:67:0x017e  */
    /* JADX WARN: Code duplicated, block: B:68:0x0181  */
    /* JADX WARN: Code duplicated, block: B:70:0x0189  */
    /* JADX WARN: Code duplicated, block: B:71:0x0197  */
    /* JADX WARN: Code duplicated, block: B:73:0x019d  */
    /* JADX WARN: Code duplicated, block: B:74:0x01a6  */
    /* JADX WARN: Code duplicated, block: B:77:0x01b8  */
    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int i6;
        int i7;
        int i8;
        int baseline;
        int i9;
        int i10;
        int measuredHeight;
        int paddingTop;
        int i11;
        int i12;
        int iK;
        int i13 = 8;
        int i14 = 5;
        if (this.e == 1) {
            int paddingLeft2 = getPaddingLeft();
            int i15 = i3 - i;
            int paddingRight = i15 - getPaddingRight();
            int paddingRight2 = (i15 - paddingLeft2) - getPaddingRight();
            int virtualChildCount = getVirtualChildCount();
            int i16 = this.f;
            int i17 = i16 & 112;
            int i18 = 8388615 & i16;
            if (i17 != 16) {
                paddingTop = i17 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.g;
            } else {
                paddingTop = getPaddingTop() + (((i4 - i2) - this.g) / 2);
            }
            int i19 = 0;
            while (i19 < virtualChildCount) {
                View childAt = getChildAt(i19);
                if (childAt == null) {
                    iK = n() + paddingTop;
                } else {
                    if (childAt.getVisibility() != i13) {
                        int measuredWidth = childAt.getMeasuredWidth();
                        int measuredHeight2 = childAt.getMeasuredHeight();
                        a aVar = (a) childAt.getLayoutParams();
                        int i20 = aVar.f324b;
                        if (i20 < 0) {
                            i20 = i18;
                        }
                        int i21 = a.b.d.h.j.f151a;
                        int absoluteGravity = Gravity.getAbsoluteGravity(i20, getLayoutDirection()) & 7;
                        if (absoluteGravity != 1) {
                            if (absoluteGravity != i14) {
                                i12 = ((ViewGroup.MarginLayoutParams) aVar).leftMargin + paddingLeft2;
                            } else {
                                i11 = paddingRight - measuredWidth;
                            }
                            if (l(i19)) {
                                paddingTop += this.n;
                            }
                            int i22 = paddingTop + ((ViewGroup.MarginLayoutParams) aVar).topMargin;
                            int iJ = j() + i22;
                            childAt.layout(i12, iJ, measuredWidth + i12, measuredHeight2 + iJ);
                            iK = k() + measuredHeight2 + ((ViewGroup.MarginLayoutParams) aVar).bottomMargin + i22;
                            i19 = i() + i19;
                        } else {
                            i11 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft2 + ((ViewGroup.MarginLayoutParams) aVar).leftMargin;
                        }
                        i12 = i11 - ((ViewGroup.MarginLayoutParams) aVar).rightMargin;
                        if (l(i19)) {
                            paddingTop += this.n;
                        }
                        int i23 = paddingTop + ((ViewGroup.MarginLayoutParams) aVar).topMargin;
                        int iJ2 = j() + i23;
                        childAt.layout(i12, iJ2, measuredWidth + i12, measuredHeight2 + iJ2);
                        iK = k() + measuredHeight2 + ((ViewGroup.MarginLayoutParams) aVar).bottomMargin + i23;
                        i19 = i() + i19;
                    }
                    i19++;
                    i13 = 8;
                    i14 = 5;
                }
                paddingTop = iK;
                i19++;
                i13 = 8;
                i14 = 5;
            }
            return;
        }
        boolean zA = m0.a(this);
        int paddingTop2 = getPaddingTop();
        int i24 = i4 - i2;
        int paddingBottom = i24 - getPaddingBottom();
        int paddingBottom2 = (i24 - paddingTop2) - getPaddingBottom();
        int virtualChildCount2 = getVirtualChildCount();
        int i25 = this.f;
        int i26 = 8388615 & i25;
        int i27 = i25 & 112;
        boolean z2 = this.f321b;
        int[] iArr = this.j;
        int[] iArr2 = this.k;
        int i28 = a.b.d.h.j.f151a;
        int absoluteGravity2 = Gravity.getAbsoluteGravity(i26, getLayoutDirection());
        if (absoluteGravity2 != 1) {
            paddingLeft = absoluteGravity2 != 5 ? getPaddingLeft() : ((getPaddingLeft() + i3) - i) - this.g;
        } else {
            paddingLeft = getPaddingLeft() + (((i3 - i) - this.g) / 2);
        }
        if (zA) {
            i7 = virtualChildCount2 - 1;
            i5 = 0;
            i6 = -1;
        } else {
            i5 = 0;
            i6 = 1;
            i7 = 0;
        }
        while (i5 < virtualChildCount2) {
            int i29 = (i6 * i5) + i7;
            View childAt2 = getChildAt(i29);
            if (childAt2 == null) {
                paddingLeft = n() + paddingLeft;
            } else {
                if (childAt2.getVisibility() != 8) {
                    int measuredWidth2 = childAt2.getMeasuredWidth();
                    int measuredHeight3 = childAt2.getMeasuredHeight();
                    a aVar2 = (a) childAt2.getLayoutParams();
                    if (z2) {
                        i8 = i27;
                        baseline = ((ViewGroup.MarginLayoutParams) aVar2).height != -1 ? childAt2.getBaseline() : -1;
                        i9 = aVar2.f324b;
                        if (i9 < 0) {
                            i9 = i8;
                        }
                        i10 = i9 & 112;
                        if (i10 != 16) {
                            measuredHeight = ((((paddingBottom2 - measuredHeight3) / 2) + paddingTop2) + ((ViewGroup.MarginLayoutParams) aVar2).topMargin) - ((ViewGroup.MarginLayoutParams) aVar2).bottomMargin;
                        } else if (i10 != 48) {
                            measuredHeight = ((ViewGroup.MarginLayoutParams) aVar2).topMargin + paddingTop2;
                            if (baseline != -1) {
                                measuredHeight = (iArr[1] - baseline) + measuredHeight;
                            }
                        } else if (i10 != 80) {
                            measuredHeight = paddingTop2;
                        } else {
                            measuredHeight = (paddingBottom - measuredHeight3) - ((ViewGroup.MarginLayoutParams) aVar2).bottomMargin;
                            if (baseline != -1) {
                                measuredHeight -= iArr2[2] - (childAt2.getMeasuredHeight() - baseline);
                            }
                        }
                        if (l(i29)) {
                            paddingLeft += this.m;
                        }
                        int i30 = paddingLeft + ((ViewGroup.MarginLayoutParams) aVar2).leftMargin;
                        int iJ3 = j() + i30;
                        childAt2.layout(iJ3, measuredHeight, measuredWidth2 + iJ3, measuredHeight3 + measuredHeight);
                        paddingLeft = i30 + k() + measuredWidth2 + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin;
                        i5 = i() + i5;
                    } else {
                        i8 = i27;
                    }
                    i9 = aVar2.f324b;
                    if (i9 < 0) {
                        i9 = i8;
                    }
                    i10 = i9 & 112;
                    if (i10 != 16) {
                        measuredHeight = ((((paddingBottom2 - measuredHeight3) / 2) + paddingTop2) + ((ViewGroup.MarginLayoutParams) aVar2).topMargin) - ((ViewGroup.MarginLayoutParams) aVar2).bottomMargin;
                    } else if (i10 != 48) {
                        measuredHeight = ((ViewGroup.MarginLayoutParams) aVar2).topMargin + paddingTop2;
                        if (baseline != -1) {
                            measuredHeight = (iArr[1] - baseline) + measuredHeight;
                        }
                    } else if (i10 != 80) {
                        measuredHeight = paddingTop2;
                    } else {
                        measuredHeight = (paddingBottom - measuredHeight3) - ((ViewGroup.MarginLayoutParams) aVar2).bottomMargin;
                        if (baseline != -1) {
                            measuredHeight -= iArr2[2] - (childAt2.getMeasuredHeight() - baseline);
                        }
                    }
                    if (l(i29)) {
                        paddingLeft += this.m;
                    }
                    int i31 = paddingLeft + ((ViewGroup.MarginLayoutParams) aVar2).leftMargin;
                    int iJ4 = j() + i31;
                    childAt2.layout(iJ4, measuredHeight, measuredWidth2 + iJ4, measuredHeight3 + measuredHeight);
                    paddingLeft = i31 + k() + measuredWidth2 + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin;
                    i5 = i() + i5;
                }
                i5++;
                virtualChildCount2 = virtualChildCount2;
                i27 = i8;
                z2 = z2;
            }
            i8 = i27;
            i5++;
            virtualChildCount2 = virtualChildCount2;
            i27 = i8;
            z2 = z2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:136:0x02ca  */
    /* JADX WARN: Code duplicated, block: B:147:0x0304  */
    /* JADX WARN: Code duplicated, block: B:153:0x0311  */
    /* JADX WARN: Code duplicated, block: B:214:0x04ad  */
    /* JADX WARN: Code duplicated, block: B:215:0x04b2  */
    /* JADX WARN: Code duplicated, block: B:218:0x04da  */
    /* JADX WARN: Code duplicated, block: B:219:0x04df  */
    /* JADX WARN: Code duplicated, block: B:222:0x04e9  */
    /* JADX WARN: Code duplicated, block: B:223:0x04f8  */
    /* JADX WARN: Code duplicated, block: B:225:0x050d  */
    /* JADX WARN: Code duplicated, block: B:231:0x0520  */
    /* JADX WARN: Code duplicated, block: B:240:0x0563  */
    /* JADX WARN: Code duplicated, block: B:246:0x0572  */
    /* JADX WARN: Code duplicated, block: B:249:0x057a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:250:0x057c  */
    /* JADX WARN: Code duplicated, block: B:252:0x0585  */
    /* JADX WARN: Code duplicated, block: B:254:0x0589  */
    /* JADX WARN: Code duplicated, block: B:281:0x0620  */
    /* JADX WARN: Code duplicated, block: B:283:0x0626  */
    /* JADX WARN: Code duplicated, block: B:284:0x0630  */
    /* JADX WARN: Code duplicated, block: B:286:0x0638  */
    /* JADX WARN: Code duplicated, block: B:287:0x063e  */
    /* JADX WARN: Code duplicated, block: B:289:0x0648  */
    /* JADX WARN: Code duplicated, block: B:290:0x0655  */
    /* JADX WARN: Code duplicated, block: B:316:0x06df  */
    /* JADX WARN: Code duplicated, block: B:318:0x06e6  */
    /* JADX WARN: Code duplicated, block: B:321:0x0703  */
    /* JADX WARN: Code duplicated, block: B:323:0x0709  */
    /* JADX WARN: Code duplicated, block: B:337:0x075c  */
    /* JADX WARN: Code duplicated, block: B:367:0x080c  */
    /* JADX WARN: Code duplicated, block: B:369:0x0817  */
    /* JADX WARN: Code duplicated, block: B:373:0x0842  */
    /* JADX WARN: Code duplicated, block: B:381:0x0854  */
    /* JADX WARN: Code duplicated, block: B:384:0x0886  */
    /* JADX WARN: Code duplicated, block: B:387:0x088b  */
    /* JADX WARN: Code duplicated, block: B:390:0x08ad  */
    /* JADX WARN: Code duplicated, block: B:392:0x08bc  */
    /* JADX WARN: Code duplicated, block: B:394:0x08c8  */
    /* JADX WARN: Code duplicated, block: B:396:0x08d4  */
    /* JADX WARN: Code duplicated, block: B:397:0x08e9  */
    /* JADX WARN: Code duplicated, block: B:434:0x08ea A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:439:? A[RETURN, SYNTHETIC] */
    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        char c2;
        int i3;
        float f;
        int i4;
        int iCombineMeasuredStates;
        int i5;
        int i6;
        int i7;
        char c3;
        View childAt;
        int i8;
        int i9;
        int iMax;
        int i10;
        int baseline;
        int measuredWidth;
        int iMakeMeasureSpec;
        int i11;
        int i12;
        View childAt2;
        a aVar;
        int i13;
        View childAt3;
        a aVar2;
        int i14;
        int iMax2;
        int i15;
        int i16;
        float f2;
        int i17;
        int i18;
        int i19;
        int iMax3;
        int i20;
        int i21;
        boolean z;
        boolean z2;
        a aVar3;
        int measuredWidth2;
        int i22;
        boolean z3;
        int i23;
        boolean z4;
        int i24;
        int measuredHeight;
        boolean z5;
        int baseline2;
        int i25;
        int i26;
        boolean z6;
        boolean z7;
        int measuredHeight2;
        int i27;
        a aVar4;
        boolean z8;
        int i28;
        boolean z9;
        int iMax4;
        int i29 = -2;
        int i30 = Integer.MIN_VALUE;
        int i31 = 8;
        float f3 = 0.0f;
        int i32 = 1073741824;
        if (this.e == 1) {
            this.g = 0;
            int virtualChildCount = getVirtualChildCount();
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int i33 = this.f322c;
            boolean z10 = this.i;
            int i34 = 0;
            int iMax5 = 0;
            int iMax6 = 0;
            int iMax7 = 0;
            int i35 = 0;
            float f4 = 0.0f;
            boolean z11 = false;
            int i36 = 0;
            boolean z12 = true;
            boolean z13 = false;
            while (i34 < virtualChildCount) {
                View childAt4 = getChildAt(i34);
                if (childAt4 == null) {
                    this.g = n() + this.g;
                } else {
                    if (childAt4.getVisibility() == i31) {
                        i34 += i();
                    } else {
                        if (l(i34)) {
                            this.g += this.n;
                        }
                        a aVar5 = (a) childAt4.getLayoutParams();
                        float f5 = aVar5.f323a;
                        f4 += f5;
                        if (mode2 == i32 && ((ViewGroup.MarginLayoutParams) aVar5).height == 0 && f5 > f3) {
                            int i37 = this.g;
                            this.g = Math.max(i37, ((ViewGroup.MarginLayoutParams) aVar5).topMargin + i37 + ((ViewGroup.MarginLayoutParams) aVar5).bottomMargin);
                            i33 = i33;
                            aVar4 = aVar5;
                            z8 = true;
                        } else {
                            if (((ViewGroup.MarginLayoutParams) aVar5).height != 0 || f5 <= f3) {
                                i27 = Integer.MIN_VALUE;
                            } else {
                                ((ViewGroup.MarginLayoutParams) aVar5).height = i29;
                                i27 = 0;
                            }
                            int i38 = f4 == f3 ? this.g : 0;
                            aVar4 = aVar5;
                            m(childAt4, i, 0, i2, i38);
                            if (i27 != i30) {
                                ((ViewGroup.MarginLayoutParams) aVar4).height = i27;
                            }
                            int measuredHeight3 = childAt4.getMeasuredHeight();
                            int i39 = this.g;
                            this.g = Math.max(i39, k() + i39 + measuredHeight3 + ((ViewGroup.MarginLayoutParams) aVar4).topMargin + ((ViewGroup.MarginLayoutParams) aVar4).bottomMargin);
                            int i40 = iMax6;
                            if (z10) {
                                iMax6 = Math.max(measuredHeight3, i40);
                            }
                            z8 = z11;
                        }
                        if (i33 >= 0 && i33 == i34 + 1) {
                            this.d = this.g;
                        }
                        if (i34 < i33 && aVar4.f323a > 0.0f) {
                            throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                        }
                        i28 = mode;
                        if (i28 == 1073741824 || ((ViewGroup.MarginLayoutParams) aVar4).width != -1) {
                            z9 = false;
                        } else {
                            z9 = true;
                            z13 = true;
                        }
                        int i41 = ((ViewGroup.MarginLayoutParams) aVar4).leftMargin + ((ViewGroup.MarginLayoutParams) aVar4).rightMargin;
                        int measuredWidth3 = childAt4.getMeasuredWidth() + i41;
                        iMax4 = Math.max(i35, measuredWidth3);
                        int iCombineMeasuredStates2 = View.combineMeasuredStates(i36, childAt4.getMeasuredState());
                        boolean z14 = z12 && ((ViewGroup.MarginLayoutParams) aVar4).width == -1;
                        if (aVar4.f323a > 0.0f) {
                            if (!z9) {
                                i41 = measuredWidth3;
                            }
                            iMax5 = Math.max(iMax5, i41);
                        } else {
                            int i42 = iMax5;
                            if (!z9) {
                                i41 = measuredWidth3;
                            }
                            iMax7 = Math.max(iMax7, i41);
                            iMax5 = i42;
                        }
                        i34 += i();
                        z11 = z8;
                        z12 = z14;
                        i36 = iCombineMeasuredStates2;
                    }
                    i34++;
                    mode = i28;
                    i35 = iMax4;
                    i33 = i33;
                    mode2 = mode2;
                    virtualChildCount = virtualChildCount;
                    i29 = -2;
                    i30 = Integer.MIN_VALUE;
                    i31 = 8;
                    f3 = 0.0f;
                    i32 = 1073741824;
                }
                i33 = i33;
                mode2 = mode2;
                i28 = mode;
                virtualChildCount = virtualChildCount;
                iMax4 = i35;
                i34++;
                mode = i28;
                i35 = iMax4;
                i33 = i33;
                mode2 = mode2;
                virtualChildCount = virtualChildCount;
                i29 = -2;
                i30 = Integer.MIN_VALUE;
                i31 = 8;
                f3 = 0.0f;
                i32 = 1073741824;
            }
            int i43 = mode2;
            int i44 = mode;
            int i45 = virtualChildCount;
            int i46 = iMax5;
            int i47 = iMax6;
            int iMax8 = iMax7;
            int iMax9 = i35;
            int iCombineMeasuredStates3 = i36;
            if (this.g > 0 && l(i45)) {
                this.g += this.n;
            }
            int i48 = i43;
            if (z10 && (i48 == Integer.MIN_VALUE || i48 == 0)) {
                this.g = 0;
                int i49 = 0;
                while (i49 < i45) {
                    View childAt5 = getChildAt(i49);
                    if (childAt5 == null) {
                        this.g = n() + this.g;
                    } else if (childAt5.getVisibility() == 8) {
                        i49 += i();
                    } else {
                        a aVar6 = (a) childAt5.getLayoutParams();
                        int i50 = this.g;
                        this.g = Math.max(i50, k() + i50 + i47 + ((ViewGroup.MarginLayoutParams) aVar6).topMargin + ((ViewGroup.MarginLayoutParams) aVar6).bottomMargin);
                    }
                    i49++;
                }
            }
            int paddingBottom = getPaddingBottom() + getPaddingTop() + this.g;
            this.g = paddingBottom;
            int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i2, 0);
            int i51 = (16777215 & iResolveSizeAndState) - this.g;
            if (z11 || (i51 != 0 && f4 > 0.0f)) {
                float f6 = this.h;
                if (f6 > 0.0f) {
                    f4 = f6;
                }
                this.g = 0;
                int i52 = 0;
                while (i52 < i45) {
                    View childAt6 = getChildAt(i52);
                    if (childAt6.getVisibility() != 8) {
                        a aVar7 = (a) childAt6.getLayoutParams();
                        float f7 = aVar7.f323a;
                        if (f7 > 0.0f) {
                            int i53 = (int) ((i51 * f7) / f4);
                            f4 -= f7;
                            int i54 = i51 - i53;
                            int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) aVar7).leftMargin + ((ViewGroup.MarginLayoutParams) aVar7).rightMargin, ((ViewGroup.MarginLayoutParams) aVar7).width);
                            if (((ViewGroup.MarginLayoutParams) aVar7).height != 0 || i48 != 1073741824) {
                                measuredHeight2 = childAt6.getMeasuredHeight() + i53;
                                if (measuredHeight2 < 0) {
                                    measuredHeight2 = 0;
                                }
                            } else if (i53 > 0) {
                                measuredHeight2 = i53;
                            } else {
                                measuredHeight2 = 0;
                            }
                            childAt6.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824));
                            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt6.getMeasuredState() & (-256));
                            i51 = i54;
                        }
                        int i55 = ((ViewGroup.MarginLayoutParams) aVar7).leftMargin + ((ViewGroup.MarginLayoutParams) aVar7).rightMargin;
                        int measuredWidth4 = childAt6.getMeasuredWidth() + i55;
                        iMax9 = Math.max(iMax9, measuredWidth4);
                        if (i44 != 1073741824) {
                            i25 = i51;
                            i26 = -1;
                            z6 = ((ViewGroup.MarginLayoutParams) aVar7).width == -1;
                            if (!z6) {
                                i55 = measuredWidth4;
                            }
                            iMax8 = Math.max(iMax8, i55);
                            if (z12 || ((ViewGroup.MarginLayoutParams) aVar7).width != i26) {
                                z7 = false;
                            } else {
                                z7 = true;
                            }
                            int i56 = this.g;
                            this.g = Math.max(i56, k() + childAt6.getMeasuredHeight() + i56 + ((ViewGroup.MarginLayoutParams) aVar7).topMargin + ((ViewGroup.MarginLayoutParams) aVar7).bottomMargin);
                            z12 = z7;
                            i51 = i25;
                        } else {
                            i25 = i51;
                            i26 = -1;
                        }
                        if (!z6) {
                            i55 = measuredWidth4;
                        }
                        iMax8 = Math.max(iMax8, i55);
                        if (z12) {
                            z7 = false;
                        } else {
                            z7 = false;
                        }
                        int i57 = this.g;
                        this.g = Math.max(i57, k() + childAt6.getMeasuredHeight() + i57 + ((ViewGroup.MarginLayoutParams) aVar7).topMargin + ((ViewGroup.MarginLayoutParams) aVar7).bottomMargin);
                        z12 = z7;
                        i51 = i25;
                    }
                    i52++;
                    i48 = i48;
                }
                this.g = getPaddingBottom() + getPaddingTop() + this.g;
            } else {
                iMax8 = Math.max(iMax8, i46);
                if (z10 && i48 != 1073741824) {
                    for (int i58 = 0; i58 < i45; i58++) {
                        View childAt7 = getChildAt(i58);
                        if (childAt7 != null && childAt7.getVisibility() != 8 && ((a) childAt7.getLayoutParams()).f323a > 0.0f) {
                            childAt7.measure(View.MeasureSpec.makeMeasureSpec(childAt7.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i47, 1073741824));
                        }
                    }
                }
            }
            int i59 = iMax9;
            if (z12 || i44 == 1073741824) {
                iMax8 = i59;
            }
            setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + iMax8, getSuggestedMinimumWidth()), i, iCombineMeasuredStates3), iResolveSizeAndState);
            if (z13) {
                int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
                for (int i60 = 0; i60 < i45; i60++) {
                    View childAt8 = getChildAt(i60);
                    if (childAt8.getVisibility() != 8) {
                        a aVar8 = (a) childAt8.getLayoutParams();
                        if (((ViewGroup.MarginLayoutParams) aVar8).width == -1) {
                            int i61 = ((ViewGroup.MarginLayoutParams) aVar8).height;
                            ((ViewGroup.MarginLayoutParams) aVar8).height = childAt8.getMeasuredHeight();
                            measureChildWithMargins(childAt8, iMakeMeasureSpec2, 0, i2, 0);
                            ((ViewGroup.MarginLayoutParams) aVar8).height = i61;
                        }
                    }
                }
                return;
            }
            return;
        }
        this.g = 0;
        int virtualChildCount2 = getVirtualChildCount();
        int mode3 = View.MeasureSpec.getMode(i);
        int mode4 = View.MeasureSpec.getMode(i2);
        if (this.j == null || this.k == null) {
            this.j = new int[4];
            this.k = new int[4];
        }
        int[] iArr = this.j;
        int[] iArr2 = this.k;
        iArr[3] = -1;
        iArr[2] = -1;
        iArr[1] = -1;
        iArr[0] = -1;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        boolean z15 = this.f321b;
        boolean z16 = this.i;
        boolean z17 = mode3 == 1073741824;
        int iMax10 = 0;
        float f8 = 0.0f;
        int i62 = 0;
        int i63 = 0;
        int iMax11 = 0;
        int i64 = 0;
        int iMax12 = 0;
        boolean z18 = false;
        boolean z19 = true;
        boolean z20 = false;
        while (i63 < virtualChildCount2) {
            View childAt9 = getChildAt(i63);
            if (childAt9 == null) {
                this.g = n() + this.g;
                i15 = iMax10;
                i16 = i62;
            } else {
                i15 = iMax10;
                i16 = i62;
                if (childAt9.getVisibility() == 8) {
                    i63 += i();
                } else {
                    if (l(i63)) {
                        this.g += this.m;
                    }
                    a aVar9 = (a) childAt9.getLayoutParams();
                    float f9 = aVar9.f323a;
                    float f10 = f8 + f9;
                    if (mode3 == 1073741824 && ((ViewGroup.MarginLayoutParams) aVar9).width == 0 && f9 > 0.0f) {
                        if (z17) {
                            this.g = ((ViewGroup.MarginLayoutParams) aVar9).leftMargin + ((ViewGroup.MarginLayoutParams) aVar9).rightMargin + this.g;
                        } else {
                            int i65 = this.g;
                            this.g = Math.max(i65, ((ViewGroup.MarginLayoutParams) aVar9).leftMargin + i65 + ((ViewGroup.MarginLayoutParams) aVar9).rightMargin);
                        }
                        if (z15) {
                            int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(0, 0);
                            childAt9.measure(iMakeMeasureSpec3, iMakeMeasureSpec3);
                            aVar3 = aVar9;
                            i19 = i15;
                            iMax3 = i16;
                            i21 = i63;
                            z = z16;
                            z2 = z15;
                        } else {
                            aVar3 = aVar9;
                            i19 = i15;
                            iMax3 = i16;
                            i21 = i63;
                            i23 = 1073741824;
                            z = z16;
                            z2 = z15;
                            z3 = true;
                        }
                        if (mode4 == i23 && ((ViewGroup.MarginLayoutParams) aVar3).height == -1) {
                            z4 = true;
                            z20 = true;
                        } else {
                            z4 = false;
                        }
                        i24 = ((ViewGroup.MarginLayoutParams) aVar3).topMargin + ((ViewGroup.MarginLayoutParams) aVar3).bottomMargin;
                        measuredHeight = childAt9.getMeasuredHeight() + i24;
                        int iCombineMeasuredStates4 = View.combineMeasuredStates(i64, childAt9.getMeasuredState());
                        if (!z2 && (baseline2 = childAt9.getBaseline()) != -1) {
                            int i66 = aVar3.f324b;
                            if (i66 < 0) {
                                i66 = this.f;
                            }
                            int i67 = (((i66 & 112) >> 4) & (-2)) >> 1;
                            iArr[i67] = Math.max(iArr[i67], baseline2);
                            iArr2[i67] = Math.max(iArr2[i67], measuredHeight - baseline2);
                        }
                        int iMax13 = Math.max(iMax12, measuredHeight);
                        if (z19 || ((ViewGroup.MarginLayoutParams) aVar3).height != -1) {
                            z5 = false;
                        } else {
                            z5 = true;
                        }
                        if (aVar3.f323a > 0.0f) {
                            if (z4) {
                                measuredHeight = i24;
                            }
                            iMax10 = Math.max(i19, measuredHeight);
                        } else {
                            int i68 = i19;
                            if (z4) {
                                measuredHeight = i24;
                            }
                            iMax3 = Math.max(iMax3, measuredHeight);
                            iMax10 = i68;
                        }
                        i63 = i() + i21;
                        iMax12 = iMax13;
                        i64 = iCombineMeasuredStates4;
                        z18 = z3;
                        z19 = z5;
                        f8 = f10;
                        i62 = iMax3;
                    } else {
                        int i69 = i63;
                        if (((ViewGroup.MarginLayoutParams) aVar9).width == 0) {
                            f2 = 0.0f;
                            if (f9 > 0.0f) {
                                ((ViewGroup.MarginLayoutParams) aVar9).width = -2;
                                i17 = 0;
                            }
                            if (f10 == f2) {
                                i18 = this.g;
                            } else {
                                i18 = 0;
                            }
                            i19 = i15;
                            iMax3 = i16;
                            i20 = i17;
                            i21 = i69;
                            z = z16;
                            z2 = z15;
                            m(childAt9, i, i18, i2, 0);
                            if (i20 != Integer.MIN_VALUE) {
                                aVar3 = aVar9;
                                ((ViewGroup.MarginLayoutParams) aVar3).width = i20;
                            } else {
                                aVar3 = aVar9;
                            }
                            measuredWidth2 = childAt9.getMeasuredWidth();
                            i22 = this.g;
                            if (z17) {
                                this.g = k() + ((ViewGroup.MarginLayoutParams) aVar3).leftMargin + measuredWidth2 + ((ViewGroup.MarginLayoutParams) aVar3).rightMargin + i22;
                            } else {
                                this.g = Math.max(i22, k() + i22 + measuredWidth2 + ((ViewGroup.MarginLayoutParams) aVar3).leftMargin + ((ViewGroup.MarginLayoutParams) aVar3).rightMargin);
                            }
                            if (z) {
                                iMax11 = Math.max(measuredWidth2, iMax11);
                            }
                        } else {
                            f2 = 0.0f;
                        }
                        i17 = Integer.MIN_VALUE;
                        if (f10 == f2) {
                            i18 = this.g;
                        } else {
                            i18 = 0;
                        }
                        i19 = i15;
                        iMax3 = i16;
                        i20 = i17;
                        i21 = i69;
                        z = z16;
                        z2 = z15;
                        m(childAt9, i, i18, i2, 0);
                        if (i20 != Integer.MIN_VALUE) {
                            aVar3 = aVar9;
                            ((ViewGroup.MarginLayoutParams) aVar3).width = i20;
                        } else {
                            aVar3 = aVar9;
                        }
                        measuredWidth2 = childAt9.getMeasuredWidth();
                        i22 = this.g;
                        if (z17) {
                            this.g = k() + ((ViewGroup.MarginLayoutParams) aVar3).leftMargin + measuredWidth2 + ((ViewGroup.MarginLayoutParams) aVar3).rightMargin + i22;
                        } else {
                            this.g = Math.max(i22, k() + i22 + measuredWidth2 + ((ViewGroup.MarginLayoutParams) aVar3).leftMargin + ((ViewGroup.MarginLayoutParams) aVar3).rightMargin);
                        }
                        if (z) {
                            iMax11 = Math.max(measuredWidth2, iMax11);
                        }
                    }
                    z3 = z18;
                    i23 = 1073741824;
                    if (mode4 == i23) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    i24 = ((ViewGroup.MarginLayoutParams) aVar3).topMargin + ((ViewGroup.MarginLayoutParams) aVar3).bottomMargin;
                    measuredHeight = childAt9.getMeasuredHeight() + i24;
                    int iCombineMeasuredStates5 = View.combineMeasuredStates(i64, childAt9.getMeasuredState());
                    if (!z2) {
                    }
                    int iMax14 = Math.max(iMax12, measuredHeight);
                    if (z19) {
                        z5 = false;
                    } else {
                        z5 = false;
                    }
                    if (aVar3.f323a > 0.0f) {
                        if (z4) {
                            measuredHeight = i24;
                        }
                        iMax10 = Math.max(i19, measuredHeight);
                    } else {
                        int i610 = i19;
                        if (z4) {
                            measuredHeight = i24;
                        }
                        iMax3 = Math.max(iMax3, measuredHeight);
                        iMax10 = i610;
                    }
                    i63 = i() + i21;
                    iMax12 = iMax14;
                    i64 = iCombineMeasuredStates5;
                    z18 = z3;
                    z19 = z5;
                    f8 = f10;
                    i62 = iMax3;
                }
                i63++;
                z16 = z;
                z15 = z2;
            }
            z2 = z15;
            iMax10 = i15;
            i62 = i16;
            z = z16;
            i63++;
            z16 = z;
            z15 = z2;
        }
        boolean z21 = z16;
        boolean z22 = z15;
        int i70 = i62;
        int i71 = iMax10;
        int iMax15 = i70;
        if (this.g > 0 && l(virtualChildCount2)) {
            this.g += this.m;
        }
        if (iArr[1] == -1 && iArr[0] == -1 && iArr[2] == -1) {
            c2 = 3;
            if (iArr[3] != -1) {
            }
            if (z21 && (mode3 == Integer.MIN_VALUE || mode3 == 0)) {
                this.g = 0;
                i13 = 0;
                while (i13 < virtualChildCount2) {
                    childAt3 = getChildAt(i13);
                    if (childAt3 == null) {
                        this.g = n() + this.g;
                    } else if (childAt3.getVisibility() == 8) {
                        i13 += i();
                    } else {
                        aVar2 = (a) childAt3.getLayoutParams();
                        i14 = this.g;
                        if (z17) {
                            iMax2 = k() + ((ViewGroup.MarginLayoutParams) aVar2).leftMargin + iMax11 + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin + i14;
                        } else {
                            iMax2 = Math.max(i14, k() + i14 + iMax11 + ((ViewGroup.MarginLayoutParams) aVar2).leftMargin + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin);
                        }
                        this.g = iMax2;
                    }
                    i13++;
                }
            }
            int paddingRight = getPaddingRight() + getPaddingLeft() + this.g;
            this.g = paddingRight;
            int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingRight, getSuggestedMinimumWidth()), i, 0);
            i3 = (16777215 & iResolveSizeAndState2) - this.g;
            if (!z18 || (i3 != 0 && f8 > 0.0f)) {
                f = this.h;
                if (f > 0.0f) {
                    f8 = f;
                }
                iArr[3] = -1;
                iArr[2] = -1;
                iArr[1] = -1;
                iArr[0] = -1;
                iArr2[3] = -1;
                iArr2[2] = -1;
                iArr2[1] = -1;
                iArr2[0] = -1;
                this.g = 0;
                i4 = i3;
                iCombineMeasuredStates = i64;
                int iMax16 = -1;
                i5 = 0;
                while (i5 < virtualChildCount2) {
                    childAt = getChildAt(i5);
                    if (childAt != null || childAt.getVisibility() == 8) {
                        i8 = i4;
                        virtualChildCount2 = virtualChildCount2;
                    } else {
                        a aVar10 = (a) childAt.getLayoutParams();
                        float f11 = aVar10.f323a;
                        if (f11 > 0.0f) {
                            int i72 = (int) ((i4 * f11) / f8);
                            float f12 = f8 - f11;
                            int i73 = i4 - i72;
                            int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i2, getPaddingBottom() + getPaddingTop() + ((ViewGroup.MarginLayoutParams) aVar10).topMargin + ((ViewGroup.MarginLayoutParams) aVar10).bottomMargin, ((ViewGroup.MarginLayoutParams) aVar10).height);
                            if (((ViewGroup.MarginLayoutParams) aVar10).width != 0 || mode3 != 1073741824) {
                                measuredWidth = childAt.getMeasuredWidth() + i72;
                                if (measuredWidth < 0) {
                                    measuredWidth = 0;
                                }
                            } else if (i72 > 0) {
                                measuredWidth = i72;
                            } else {
                                measuredWidth = 0;
                            }
                            childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), childMeasureSpec2);
                            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState() & (-16777216));
                            f8 = f12;
                            i9 = i73;
                        } else {
                            i9 = i4;
                        }
                        if (z17) {
                            iMax = k() + childAt.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) aVar10).leftMargin + ((ViewGroup.MarginLayoutParams) aVar10).rightMargin + this.g;
                        } else {
                            int i74 = this.g;
                            iMax = Math.max(i74, k() + childAt.getMeasuredWidth() + i74 + ((ViewGroup.MarginLayoutParams) aVar10).leftMargin + ((ViewGroup.MarginLayoutParams) aVar10).rightMargin);
                        }
                        this.g = iMax;
                        boolean z23 = mode4 != 1073741824 && ((ViewGroup.MarginLayoutParams) aVar10).height == -1;
                        int i75 = ((ViewGroup.MarginLayoutParams) aVar10).topMargin + ((ViewGroup.MarginLayoutParams) aVar10).bottomMargin;
                        int measuredHeight4 = childAt.getMeasuredHeight() + i75;
                        iMax16 = Math.max(iMax16, measuredHeight4);
                        if (!z23) {
                            i75 = measuredHeight4;
                        }
                        iMax15 = Math.max(iMax15, i75);
                        if (z19) {
                            i10 = -1;
                            boolean z24 = ((ViewGroup.MarginLayoutParams) aVar10).height == -1;
                            if (!z22 && (baseline = childAt.getBaseline()) != i10) {
                                int i76 = aVar10.f324b;
                                if (i76 < 0) {
                                    i76 = this.f;
                                }
                                int i77 = (((i76 & 112) >> 4) & (-2)) >> 1;
                                iArr[i77] = Math.max(iArr[i77], baseline);
                                iArr2[i77] = Math.max(iArr2[i77], measuredHeight4 - baseline);
                            }
                            z19 = z24;
                            i8 = i9;
                            f8 = f8;
                        } else {
                            i10 = -1;
                        }
                        if (!z22) {
                        }
                        z19 = z24;
                        i8 = i9;
                        f8 = f8;
                    }
                    i5++;
                    i4 = i8;
                    virtualChildCount2 = virtualChildCount2;
                }
                i6 = i2;
                i7 = virtualChildCount2;
                this.g = getPaddingRight() + getPaddingLeft() + this.g;
                if (iArr[1] != -1 && iArr[0] == -1 && iArr[2] == -1) {
                    c3 = 3;
                    if (iArr[3] == -1) {
                        iMax12 = iMax16;
                    }
                    if (z19 || mode4 == 1073741824) {
                        iMax15 = iMax12;
                    }
                    setMeasuredDimension(((-16777216) & iCombineMeasuredStates) | iResolveSizeAndState2, View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax15, getSuggestedMinimumHeight()), i6, iCombineMeasuredStates << 16));
                    if (z20) {
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
                        i11 = i7;
                        for (i12 = 0; i12 < i11; i12++) {
                            childAt2 = getChildAt(i12);
                            if (childAt2.getVisibility() != 8) {
                                aVar = (a) childAt2.getLayoutParams();
                                if (((ViewGroup.MarginLayoutParams) aVar).height == -1) {
                                    int i78 = ((ViewGroup.MarginLayoutParams) aVar).width;
                                    ((ViewGroup.MarginLayoutParams) aVar).width = childAt2.getMeasuredWidth();
                                    measureChildWithMargins(childAt2, i, 0, iMakeMeasureSpec, 0);
                                    ((ViewGroup.MarginLayoutParams) aVar).width = i78;
                                }
                            }
                        }
                    }
                }
                c3 = 3;
                iMax12 = Math.max(iMax16, Math.max(iArr2[c3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
                if (z19) {
                    iMax15 = iMax12;
                } else {
                    iMax15 = iMax12;
                }
                setMeasuredDimension(((-16777216) & iCombineMeasuredStates) | iResolveSizeAndState2, View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax15, getSuggestedMinimumHeight()), i6, iCombineMeasuredStates << 16));
                if (z20) {
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
                    i11 = i7;
                    while (i12 < i11) {
                        childAt2 = getChildAt(i12);
                        if (childAt2.getVisibility() != 8) {
                            aVar = (a) childAt2.getLayoutParams();
                            if (((ViewGroup.MarginLayoutParams) aVar).height == -1) {
                                int i79 = ((ViewGroup.MarginLayoutParams) aVar).width;
                                ((ViewGroup.MarginLayoutParams) aVar).width = childAt2.getMeasuredWidth();
                                measureChildWithMargins(childAt2, i, 0, iMakeMeasureSpec, 0);
                                ((ViewGroup.MarginLayoutParams) aVar).width = i79;
                            }
                        }
                    }
                }
            }
            iMax15 = Math.max(iMax15, i71);
            if (z21 && mode3 != 1073741824) {
                for (int i80 = 0; i80 < virtualChildCount2; i80++) {
                    View childAt10 = getChildAt(i80);
                    if (childAt10 != null && childAt10.getVisibility() != 8 && ((a) childAt10.getLayoutParams()).f323a > 0.0f) {
                        childAt10.measure(View.MeasureSpec.makeMeasureSpec(iMax11, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt10.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i6 = i2;
            i7 = virtualChildCount2;
            iCombineMeasuredStates = i64;
            if (z19) {
                iMax15 = iMax12;
            } else {
                iMax15 = iMax12;
            }
            setMeasuredDimension(((-16777216) & iCombineMeasuredStates) | iResolveSizeAndState2, View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax15, getSuggestedMinimumHeight()), i6, iCombineMeasuredStates << 16));
            if (z20) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
                i11 = i7;
                while (i12 < i11) {
                    childAt2 = getChildAt(i12);
                    if (childAt2.getVisibility() != 8) {
                        aVar = (a) childAt2.getLayoutParams();
                        if (((ViewGroup.MarginLayoutParams) aVar).height == -1) {
                            int i710 = ((ViewGroup.MarginLayoutParams) aVar).width;
                            ((ViewGroup.MarginLayoutParams) aVar).width = childAt2.getMeasuredWidth();
                            measureChildWithMargins(childAt2, i, 0, iMakeMeasureSpec, 0);
                            ((ViewGroup.MarginLayoutParams) aVar).width = i710;
                        }
                    }
                }
            }
        }
        c2 = 3;
        iMax12 = Math.max(iMax12, Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
        if (z21) {
            this.g = 0;
            i13 = 0;
            while (i13 < virtualChildCount2) {
                childAt3 = getChildAt(i13);
                if (childAt3 == null) {
                    this.g = n() + this.g;
                } else if (childAt3.getVisibility() == 8) {
                    i13 += i();
                } else {
                    aVar2 = (a) childAt3.getLayoutParams();
                    i14 = this.g;
                    if (z17) {
                        iMax2 = k() + ((ViewGroup.MarginLayoutParams) aVar2).leftMargin + iMax11 + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin + i14;
                    } else {
                        iMax2 = Math.max(i14, k() + i14 + iMax11 + ((ViewGroup.MarginLayoutParams) aVar2).leftMargin + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin);
                    }
                    this.g = iMax2;
                }
                i13++;
            }
        }
        int paddingRight2 = getPaddingRight() + getPaddingLeft() + this.g;
        this.g = paddingRight2;
        int iResolveSizeAndState3 = View.resolveSizeAndState(Math.max(paddingRight2, getSuggestedMinimumWidth()), i, 0);
        i3 = (16777215 & iResolveSizeAndState3) - this.g;
        if (z18) {
            f = this.h;
            if (f > 0.0f) {
                f8 = f;
            }
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            this.g = 0;
            i4 = i3;
            iCombineMeasuredStates = i64;
            int iMax17 = -1;
            i5 = 0;
            while (i5 < virtualChildCount2) {
                childAt = getChildAt(i5);
                if (childAt != null) {
                    i8 = i4;
                    virtualChildCount2 = virtualChildCount2;
                } else {
                    i8 = i4;
                    virtualChildCount2 = virtualChildCount2;
                }
                i5++;
                i4 = i8;
                virtualChildCount2 = virtualChildCount2;
            }
            i6 = i2;
            i7 = virtualChildCount2;
            this.g = getPaddingRight() + getPaddingLeft() + this.g;
            if (iArr[1] != -1) {
                c3 = 3;
            } else {
                c3 = 3;
            }
            iMax12 = Math.max(iMax17, Math.max(iArr2[c3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
        } else {
            f = this.h;
            if (f > 0.0f) {
                f8 = f;
            }
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            this.g = 0;
            i4 = i3;
            iCombineMeasuredStates = i64;
            int iMax18 = -1;
            i5 = 0;
            while (i5 < virtualChildCount2) {
                childAt = getChildAt(i5);
                if (childAt != null) {
                    i8 = i4;
                    virtualChildCount2 = virtualChildCount2;
                } else {
                    i8 = i4;
                    virtualChildCount2 = virtualChildCount2;
                }
                i5++;
                i4 = i8;
                virtualChildCount2 = virtualChildCount2;
            }
            i6 = i2;
            i7 = virtualChildCount2;
            this.g = getPaddingRight() + getPaddingLeft() + this.g;
            if (iArr[1] != -1) {
                c3 = 3;
            } else {
                c3 = 3;
            }
            iMax12 = Math.max(iMax18, Math.max(iArr2[c3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
        }
        if (z19) {
            iMax15 = iMax12;
        } else {
            iMax15 = iMax12;
        }
        setMeasuredDimension(((-16777216) & iCombineMeasuredStates) | iResolveSizeAndState3, View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax15, getSuggestedMinimumHeight()), i6, iCombineMeasuredStates << 16));
        if (z20) {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
            i11 = i7;
            while (i12 < i11) {
                childAt2 = getChildAt(i12);
                if (childAt2.getVisibility() != 8) {
                    aVar = (a) childAt2.getLayoutParams();
                    if (((ViewGroup.MarginLayoutParams) aVar).height == -1) {
                        int i711 = ((ViewGroup.MarginLayoutParams) aVar).width;
                        ((ViewGroup.MarginLayoutParams) aVar).width = childAt2.getMeasuredWidth();
                        measureChildWithMargins(childAt2, i, 0, iMakeMeasureSpec, 0);
                        ((ViewGroup.MarginLayoutParams) aVar).width = i711;
                    }
                }
            }
        }
    }

    public void setBaselineAligned(boolean z) {
        this.f321b = z;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i >= 0 && i < getChildCount()) {
            this.f322c = i;
            return;
        }
        StringBuilder sbB = c.a.a.a.a.b("base aligned child index out of range (0, ");
        sbB.append(getChildCount());
        sbB.append(")");
        throw new IllegalArgumentException(sbB.toString());
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.l) {
            return;
        }
        this.l = drawable;
        if (drawable != null) {
            this.m = drawable.getIntrinsicWidth();
            this.n = drawable.getIntrinsicHeight();
        } else {
            this.m = 0;
            this.n = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i) {
        this.p = i;
    }

    public void setGravity(int i) {
        if (this.f != i) {
            if ((8388615 & i) == 0) {
                i |= 8388611;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.f = i;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & 8388615;
        int i3 = this.f;
        if ((8388615 & i3) != i2) {
            this.f = i2 | ((-8388616) & i3);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.i = z;
    }

    public void setOrientation(int i) {
        if (this.e != i) {
            this.e = i;
            requestLayout();
        }
    }

    public void setShowDividers(int i) {
        if (i != this.o) {
            requestLayout();
        }
        this.o = i;
    }

    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        int i3 = this.f;
        if ((i3 & 112) != i2) {
            this.f = i2 | (i3 & (-113));
            requestLayout();
        }
    }

    public void setWeightSum(float f) {
        this.h = Math.max(0.0f, f);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
