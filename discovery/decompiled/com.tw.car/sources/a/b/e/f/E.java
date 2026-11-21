package a.b.e.f;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/* JADX INFO: loaded from: classes.dex */
public class E extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f268a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f269b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f270c;
    public int d;
    public int e;
    public int f;
    public float g;
    public boolean h;
    public int[] i;
    public int[] j;
    public Drawable k;
    public int l;
    public int m;
    public int n;
    public int o;

    public static class a extends ViewGroup.MarginLayoutParams {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public float f271a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f272b;

        public a(int i, int i2) {
            super(i, i2);
            this.f272b = -1;
            this.f271a = 0.0f;
        }

        public a(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f272b = -1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.b.e.b.j.LinearLayoutCompat_Layout);
            this.f271a = typedArrayObtainStyledAttributes.getFloat(a.b.e.b.j.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.f272b = typedArrayObtainStyledAttributes.getInt(a.b.e.b.j.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            typedArrayObtainStyledAttributes.recycle();
        }

        public a(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f272b = -1;
        }
    }

    public E(Context context) {
        this(context, null, 0);
    }

    public E(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public int a(View view) {
        return 0;
    }

    public int a(View view, int i) {
        return 0;
    }

    public View a(int i) {
        return getChildAt(i);
    }

    public final void a(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View viewA = a(i3);
            if (viewA.getVisibility() != 8) {
                a aVar = (a) viewA.getLayoutParams();
                if (((ViewGroup.MarginLayoutParams) aVar).height == -1) {
                    int i4 = ((ViewGroup.MarginLayoutParams) aVar).width;
                    ((ViewGroup.MarginLayoutParams) aVar).width = viewA.getMeasuredWidth();
                    measureChildWithMargins(viewA, i2, 0, iMakeMeasureSpec, 0);
                    ((ViewGroup.MarginLayoutParams) aVar).width = i4;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:32:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:34:0x00be  */
    /* JADX WARN: Code duplicated, block: B:36:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:37:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:39:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:41:0x00da  */
    /* JADX WARN: Code duplicated, block: B:43:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:44:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:47:0x00fc  */
    public void a(int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        int baseline;
        int i9;
        int i10;
        int measuredHeight;
        boolean zA = ta.a(this);
        int paddingTop = getPaddingTop();
        int i11 = i4 - i2;
        int paddingBottom = i11 - getPaddingBottom();
        int paddingBottom2 = (i11 - paddingTop) - getPaddingBottom();
        int virtualChildCount = getVirtualChildCount();
        int i12 = this.e;
        int i13 = 8388615 & i12;
        int i14 = i12 & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay;
        boolean z2 = this.f268a;
        int[] iArr = this.i;
        int[] iArr2 = this.j;
        int iA = a.b.b.a.e.a(i13, a.b.d.h.n.c(this));
        if (iA != 1) {
            paddingLeft = iA != 5 ? getPaddingLeft() : ((getPaddingLeft() + i3) - i) - this.f;
        } else {
            paddingLeft = getPaddingLeft() + (((i3 - i) - this.f) / 2);
        }
        int iA2 = 0;
        if (zA) {
            i5 = virtualChildCount - 1;
            i6 = -1;
        } else {
            i5 = 0;
            i6 = 1;
        }
        while (iA2 < virtualChildCount) {
            int i15 = (i6 * iA2) + i5;
            View viewA = a(i15);
            if (viewA == null) {
                paddingLeft += c(i15);
                i7 = i5;
            } else {
                i7 = i5;
                if (viewA.getVisibility() != 8) {
                    int measuredWidth = viewA.getMeasuredWidth();
                    int measuredHeight2 = viewA.getMeasuredHeight();
                    a aVar = (a) viewA.getLayoutParams();
                    if (z2) {
                        i8 = i14;
                        z = z2;
                        baseline = ((ViewGroup.MarginLayoutParams) aVar).height != -1 ? viewA.getBaseline() : -1;
                        i9 = aVar.f272b;
                        if (i9 < 0) {
                            i9 = i8;
                        }
                        i10 = i9 & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay;
                        if (i10 != 16) {
                            measuredHeight = ((((paddingBottom2 - measuredHeight2) / 2) + paddingTop) + ((ViewGroup.MarginLayoutParams) aVar).topMargin) - ((ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                        } else if (i10 != 48) {
                            measuredHeight = ((ViewGroup.MarginLayoutParams) aVar).topMargin + paddingTop;
                            if (baseline != -1) {
                                measuredHeight = (iArr[1] - baseline) + measuredHeight;
                            }
                        } else if (i10 != 80) {
                            measuredHeight = paddingTop;
                        } else {
                            measuredHeight = (paddingBottom - measuredHeight2) - ((ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                            if (baseline != -1) {
                                measuredHeight -= iArr2[2] - (viewA.getMeasuredHeight() - baseline);
                            }
                        }
                        if (b(i15)) {
                            paddingLeft += this.l;
                        }
                        int i16 = ((ViewGroup.MarginLayoutParams) aVar).leftMargin + paddingLeft;
                        int iA3 = a(viewA) + i16;
                        viewA.layout(iA3, measuredHeight, measuredWidth + iA3, measuredHeight2 + measuredHeight);
                        int iB = b(viewA) + measuredWidth + ((ViewGroup.MarginLayoutParams) aVar).rightMargin + i16;
                        iA2 += a(viewA, i15);
                        paddingLeft = iB;
                    } else {
                        i8 = i14;
                        z = z2;
                    }
                    i9 = aVar.f272b;
                    if (i9 < 0) {
                        i9 = i8;
                    }
                    i10 = i9 & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay;
                    if (i10 != 16) {
                        measuredHeight = ((((paddingBottom2 - measuredHeight2) / 2) + paddingTop) + ((ViewGroup.MarginLayoutParams) aVar).topMargin) - ((ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                    } else if (i10 != 48) {
                        measuredHeight = ((ViewGroup.MarginLayoutParams) aVar).topMargin + paddingTop;
                        if (baseline != -1) {
                            measuredHeight = (iArr[1] - baseline) + measuredHeight;
                        }
                    } else if (i10 != 80) {
                        measuredHeight = paddingTop;
                    } else {
                        measuredHeight = (paddingBottom - measuredHeight2) - ((ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                        if (baseline != -1) {
                            measuredHeight -= iArr2[2] - (viewA.getMeasuredHeight() - baseline);
                        }
                    }
                    if (b(i15)) {
                        paddingLeft += this.l;
                    }
                    int i17 = ((ViewGroup.MarginLayoutParams) aVar).leftMargin + paddingLeft;
                    int iA4 = a(viewA) + i17;
                    viewA.layout(iA4, measuredHeight, measuredWidth + iA4, measuredHeight2 + measuredHeight);
                    int iB2 = b(viewA) + measuredWidth + ((ViewGroup.MarginLayoutParams) aVar).rightMargin + i17;
                    iA2 += a(viewA, i15);
                    paddingLeft = iB2;
                }
                iA2++;
                i5 = i7;
                i14 = i8;
                virtualChildCount = virtualChildCount;
                z2 = z;
                i6 = i6;
            }
            i8 = i14;
            z = z2;
            iA2++;
            i5 = i7;
            i14 = i8;
            virtualChildCount = virtualChildCount;
            z2 = z;
            i6 = i6;
        }
    }

    public void a(Canvas canvas) {
        int right;
        int left;
        int paddingRight;
        int virtualChildCount = getVirtualChildCount();
        boolean zA = ta.a(this);
        for (int i = 0; i < virtualChildCount; i++) {
            View viewA = a(i);
            if (viewA != null && viewA.getVisibility() != 8 && b(i)) {
                a aVar = (a) viewA.getLayoutParams();
                b(canvas, zA ? viewA.getRight() + ((ViewGroup.MarginLayoutParams) aVar).rightMargin : (viewA.getLeft() - ((ViewGroup.MarginLayoutParams) aVar).leftMargin) - this.l);
            }
        }
        if (b(virtualChildCount)) {
            View viewA2 = a(virtualChildCount - 1);
            if (viewA2 != null) {
                a aVar2 = (a) viewA2.getLayoutParams();
                if (zA) {
                    left = viewA2.getLeft();
                    paddingRight = ((ViewGroup.MarginLayoutParams) aVar2).leftMargin;
                    right = (left - paddingRight) - this.l;
                } else {
                    right = viewA2.getRight() + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin;
                }
            } else if (zA) {
                right = getPaddingLeft();
            } else {
                left = getWidth();
                paddingRight = getPaddingRight();
                right = (left - paddingRight) - this.l;
            }
            b(canvas, right);
        }
    }

    public void a(Canvas canvas, int i) {
        this.k.setBounds(getPaddingLeft() + this.o, i, (getWidth() - getPaddingRight()) - this.o, this.m + i);
        this.k.draw(canvas);
    }

    public void a(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    public int b(View view) {
        return 0;
    }

    public final void b(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View viewA = a(i3);
            if (viewA.getVisibility() != 8) {
                a aVar = (a) viewA.getLayoutParams();
                if (((ViewGroup.MarginLayoutParams) aVar).width == -1) {
                    int i4 = ((ViewGroup.MarginLayoutParams) aVar).height;
                    ((ViewGroup.MarginLayoutParams) aVar).height = viewA.getMeasuredHeight();
                    measureChildWithMargins(viewA, iMakeMeasureSpec, 0, i2, 0);
                    ((ViewGroup.MarginLayoutParams) aVar).height = i4;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0094  */
    public void b(int i, int i2, int i3, int i4) {
        int paddingTop;
        int i5;
        int i6;
        int paddingLeft = getPaddingLeft();
        int i7 = i3 - i;
        int paddingRight = i7 - getPaddingRight();
        int paddingRight2 = (i7 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i8 = this.e;
        int i9 = i8 & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay;
        int i10 = i8 & 8388615;
        if (i9 != 16) {
            paddingTop = i9 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.f;
        } else {
            paddingTop = getPaddingTop() + (((i4 - i2) - this.f) / 2);
        }
        int iA = 0;
        while (iA < virtualChildCount) {
            View viewA = a(iA);
            if (viewA == null) {
                paddingTop = c(iA) + paddingTop;
            } else if (viewA.getVisibility() != 8) {
                int measuredWidth = viewA.getMeasuredWidth();
                int measuredHeight = viewA.getMeasuredHeight();
                a aVar = (a) viewA.getLayoutParams();
                int i11 = aVar.f272b;
                if (i11 < 0) {
                    i11 = i10;
                }
                int iA2 = a.b.b.a.e.a(i11, a.b.d.h.n.c(this)) & 7;
                if (iA2 != 1) {
                    if (iA2 != 5) {
                        i6 = ((ViewGroup.MarginLayoutParams) aVar).leftMargin + paddingLeft;
                    } else {
                        i5 = paddingRight - measuredWidth;
                    }
                    if (b(iA)) {
                        paddingTop += this.m;
                    }
                    int i12 = paddingTop + ((ViewGroup.MarginLayoutParams) aVar).topMargin;
                    int iA3 = a(viewA) + i12;
                    viewA.layout(i6, iA3, measuredWidth + i6, measuredHeight + iA3);
                    int iB = b(viewA) + measuredHeight + ((ViewGroup.MarginLayoutParams) aVar).bottomMargin + i12;
                    iA += a(viewA, iA);
                    paddingTop = iB;
                } else {
                    i5 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft + ((ViewGroup.MarginLayoutParams) aVar).leftMargin;
                }
                i6 = i5 - ((ViewGroup.MarginLayoutParams) aVar).rightMargin;
                if (b(iA)) {
                    paddingTop += this.m;
                }
                int i13 = paddingTop + ((ViewGroup.MarginLayoutParams) aVar).topMargin;
                int iA4 = a(viewA) + i13;
                viewA.layout(i6, iA4, measuredWidth + i6, measuredHeight + iA4);
                int iB2 = b(viewA) + measuredHeight + ((ViewGroup.MarginLayoutParams) aVar).bottomMargin + i13;
                iA += a(viewA, iA);
                paddingTop = iB2;
            }
            iA++;
        }
    }

    public void b(Canvas canvas) {
        int virtualChildCount = getVirtualChildCount();
        for (int i = 0; i < virtualChildCount; i++) {
            View viewA = a(i);
            if (viewA != null && viewA.getVisibility() != 8 && b(i)) {
                a(canvas, (viewA.getTop() - ((ViewGroup.MarginLayoutParams) ((a) viewA.getLayoutParams())).topMargin) - this.m);
            }
        }
        if (b(virtualChildCount)) {
            View viewA2 = a(virtualChildCount - 1);
            a(canvas, viewA2 == null ? (getHeight() - getPaddingBottom()) - this.m : viewA2.getBottom() + ((ViewGroup.MarginLayoutParams) ((a) viewA2.getLayoutParams())).bottomMargin);
        }
    }

    public void b(Canvas canvas, int i) {
        this.k.setBounds(i, getPaddingTop() + this.o, this.l + i, (getHeight() - getPaddingBottom()) - this.o);
        this.k.draw(canvas);
    }

    public boolean b(int i) {
        if (i == 0) {
            return (this.n & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.n & 4) != 0;
        }
        if ((this.n & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    public int c(int i) {
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:165:0x03bd A[PHI: r4
      0x03bd: PHI (r4v37 int) = (r4v33 int), (r4v38 int) binds: [B:164:0x03bb, B:160:0x03b0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:195:0x0468  */
    /* JADX WARN: Code duplicated, block: B:60:0x0174  */
    /* JADX WARN: Code duplicated, block: B:67:0x0196  */
    /* JADX WARN: Code duplicated, block: B:74:0x01c3  */
    /* JADX WARN: Code duplicated, block: B:77:0x01ca A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:79:0x01cd  */
    /* JADX WARN: Code duplicated, block: B:81:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:83:0x01d9  */
    public void c(int i, int i2) {
        int[] iArr;
        int i3;
        int iMax;
        int iMax2;
        int i4;
        int i5;
        int baseline;
        int i6;
        int i7;
        int i8;
        boolean z;
        boolean z2;
        View view;
        int i9;
        boolean z3;
        int i10;
        int measuredHeight;
        boolean z4;
        int baseline2;
        int i11;
        this.f = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (this.i == null || this.j == null) {
            this.i = new int[4];
            this.j = new int[4];
        }
        int[] iArr2 = this.i;
        int[] iArr3 = this.j;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        iArr3[3] = -1;
        iArr3[2] = -1;
        iArr3[1] = -1;
        iArr3[0] = -1;
        boolean z5 = this.f268a;
        boolean z6 = this.h;
        int i12 = 1073741824;
        boolean z7 = mode == 1073741824;
        float f = 0.0f;
        int iA = 0;
        int iMax3 = 0;
        int i13 = 0;
        int iMax4 = 0;
        int iMax5 = 0;
        boolean z8 = false;
        int i14 = 0;
        boolean z9 = true;
        boolean z10 = false;
        while (true) {
            iArr = iArr3;
            if (iA >= virtualChildCount) {
                break;
            }
            View viewA = a(iA);
            if (viewA == null) {
                this.f = c(iA) + this.f;
            } else {
                if (viewA.getVisibility() == 8) {
                    iA += a(viewA, iA);
                } else {
                    if (b(iA)) {
                        this.f += this.l;
                    }
                    a aVar = (a) viewA.getLayoutParams();
                    float f2 = f + aVar.f271a;
                    if (mode == i12 && ((ViewGroup.MarginLayoutParams) aVar).width == 0 && aVar.f271a > 0.0f) {
                        if (z7) {
                            this.f = ((ViewGroup.MarginLayoutParams) aVar).leftMargin + ((ViewGroup.MarginLayoutParams) aVar).rightMargin + this.f;
                        } else {
                            int i15 = this.f;
                            this.f = Math.max(i15, ((ViewGroup.MarginLayoutParams) aVar).leftMargin + i15 + ((ViewGroup.MarginLayoutParams) aVar).rightMargin);
                        }
                        if (z5) {
                            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            viewA.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                            i8 = iA;
                            z = z6;
                            z2 = z5;
                            view = viewA;
                        } else {
                            i8 = iA;
                            z = z6;
                            z2 = z5;
                            view = viewA;
                            i9 = 1073741824;
                            z8 = true;
                        }
                        if (mode2 == i9 && ((ViewGroup.MarginLayoutParams) aVar).height == -1) {
                            z3 = true;
                            z10 = true;
                        } else {
                            z3 = false;
                        }
                        i10 = ((ViewGroup.MarginLayoutParams) aVar).topMargin + ((ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                        measuredHeight = view.getMeasuredHeight() + i10;
                        int iCombineMeasuredStates = View.combineMeasuredStates(i14, view.getMeasuredState());
                        if (z2 && (baseline2 = view.getBaseline()) != -1) {
                            i11 = aVar.f272b;
                            if (i11 < 0) {
                                i11 = this.e;
                            }
                            int i16 = (((i11 & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay) >> 4) & (-2)) >> 1;
                            iArr2[i16] = Math.max(iArr2[i16], baseline2);
                            iArr[i16] = Math.max(iArr[i16], measuredHeight - baseline2);
                        }
                        int iMax6 = Math.max(i13, measuredHeight);
                        if (z9 || ((ViewGroup.MarginLayoutParams) aVar).height != -1) {
                            z4 = false;
                        } else {
                            z4 = true;
                        }
                        if (aVar.f271a > 0.0f) {
                            if (!z3) {
                                i10 = measuredHeight;
                            }
                            iMax5 = Math.max(iMax5, i10);
                        } else {
                            int i17 = iMax5;
                            if (z3) {
                                measuredHeight = i10;
                            }
                            iMax4 = Math.max(iMax4, measuredHeight);
                            iMax5 = i17;
                        }
                        int i18 = i8;
                        i13 = iMax6;
                        i14 = iCombineMeasuredStates;
                        z9 = z4;
                        iA = a(view, i18) + i18;
                        f = f2;
                    } else {
                        if (((ViewGroup.MarginLayoutParams) aVar).width != 0 || aVar.f271a <= 0.0f) {
                            i7 = Integer.MIN_VALUE;
                        } else {
                            ((ViewGroup.MarginLayoutParams) aVar).width = -2;
                            i7 = 0;
                        }
                        i8 = iA;
                        int i19 = i7;
                        z = z6;
                        z2 = z5;
                        a(viewA, i8, i, f2 == 0.0f ? this.f : 0, i2, 0);
                        if (i19 != Integer.MIN_VALUE) {
                            ((ViewGroup.MarginLayoutParams) aVar).width = i19;
                        }
                        int measuredWidth = viewA.getMeasuredWidth();
                        if (z7) {
                            view = viewA;
                            this.f = b(view) + ((ViewGroup.MarginLayoutParams) aVar).leftMargin + measuredWidth + ((ViewGroup.MarginLayoutParams) aVar).rightMargin + this.f;
                        } else {
                            view = viewA;
                            int i20 = this.f;
                            this.f = Math.max(i20, b(view) + i20 + measuredWidth + ((ViewGroup.MarginLayoutParams) aVar).leftMargin + ((ViewGroup.MarginLayoutParams) aVar).rightMargin);
                        }
                        if (z) {
                            iMax3 = Math.max(measuredWidth, iMax3);
                        }
                    }
                    i9 = 1073741824;
                    if (mode2 == i9) {
                        z3 = false;
                    } else {
                        z3 = false;
                    }
                    i10 = ((ViewGroup.MarginLayoutParams) aVar).topMargin + ((ViewGroup.MarginLayoutParams) aVar).bottomMargin;
                    measuredHeight = view.getMeasuredHeight() + i10;
                    int iCombineMeasuredStates2 = View.combineMeasuredStates(i14, view.getMeasuredState());
                    if (z2) {
                        i11 = aVar.f272b;
                        if (i11 < 0) {
                            i11 = this.e;
                        }
                        int i110 = (((i11 & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay) >> 4) & (-2)) >> 1;
                        iArr2[i110] = Math.max(iArr2[i110], baseline2);
                        iArr[i110] = Math.max(iArr[i110], measuredHeight - baseline2);
                    }
                    int iMax7 = Math.max(i13, measuredHeight);
                    if (z9) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    if (aVar.f271a > 0.0f) {
                        if (!z3) {
                            i10 = measuredHeight;
                        }
                        iMax5 = Math.max(iMax5, i10);
                    } else {
                        int i111 = iMax5;
                        if (z3) {
                            measuredHeight = i10;
                        }
                        iMax4 = Math.max(iMax4, measuredHeight);
                        iMax5 = i111;
                    }
                    int i112 = i8;
                    i13 = iMax7;
                    i14 = iCombineMeasuredStates2;
                    z9 = z4;
                    iA = a(view, i112) + i112;
                    f = f2;
                }
                iA++;
                iArr3 = iArr;
                z6 = z;
                z5 = z2;
                i12 = 1073741824;
            }
            z = z6;
            z2 = z5;
            iA++;
            iArr3 = iArr;
            z6 = z;
            z5 = z2;
            i12 = 1073741824;
        }
        boolean z11 = z6;
        boolean z12 = z5;
        int iMax8 = i13;
        int i21 = iMax4;
        int i22 = iMax5;
        int i23 = i14;
        if (this.f > 0 && b(virtualChildCount)) {
            this.f += this.l;
        }
        if (iArr2[1] != -1 || iArr2[0] != -1 || iArr2[2] != -1 || iArr2[3] != -1) {
            iMax8 = Math.max(iMax8, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))));
        }
        if (z11 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.f = 0;
            int iA2 = 0;
            while (iA2 < virtualChildCount) {
                View viewA2 = a(iA2);
                if (viewA2 == null) {
                    this.f = c(iA2) + this.f;
                } else if (viewA2.getVisibility() == 8) {
                    iA2 += a(viewA2, iA2);
                } else {
                    a aVar2 = (a) viewA2.getLayoutParams();
                    if (z7) {
                        this.f = b(viewA2) + ((ViewGroup.MarginLayoutParams) aVar2).leftMargin + iMax3 + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin + this.f;
                    } else {
                        int i24 = this.f;
                        this.f = Math.max(i24, b(viewA2) + i24 + iMax3 + ((ViewGroup.MarginLayoutParams) aVar2).leftMargin + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin);
                    }
                    iA2++;
                    iMax8 = iMax8;
                }
                iA2++;
                iMax8 = iMax8;
            }
        }
        int i25 = iMax8;
        this.f = getPaddingRight() + getPaddingLeft() + this.f;
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.f, getSuggestedMinimumWidth()), i, 0);
        int i26 = (16777215 & iResolveSizeAndState) - this.f;
        if (z8 || (i26 != 0 && f > 0.0f)) {
            float f3 = this.g;
            if (f3 > 0.0f) {
                f = f3;
            }
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            this.f = 0;
            int i27 = i21;
            int iCombineMeasuredStates3 = i23;
            int iMax9 = -1;
            float f4 = f;
            int i28 = 0;
            while (i28 < virtualChildCount) {
                View viewA3 = a(i28);
                if (viewA3 == null || viewA3.getVisibility() == 8) {
                    i4 = i26;
                    virtualChildCount = virtualChildCount;
                } else {
                    a aVar3 = (a) viewA3.getLayoutParams();
                    float f5 = aVar3.f271a;
                    if (f5 > 0.0f) {
                        int measuredWidth2 = (int) ((i26 * f5) / f4);
                        float f6 = f4 - f5;
                        int i29 = i26 - measuredWidth2;
                        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, getPaddingBottom() + getPaddingTop() + ((ViewGroup.MarginLayoutParams) aVar3).topMargin + ((ViewGroup.MarginLayoutParams) aVar3).bottomMargin, ((ViewGroup.MarginLayoutParams) aVar3).height);
                        if (((ViewGroup.MarginLayoutParams) aVar3).width == 0) {
                            i6 = 1073741824;
                            if (mode == 1073741824) {
                                if (measuredWidth2 <= 0) {
                                    measuredWidth2 = 0;
                                }
                            }
                            viewA3.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, i6), childMeasureSpec);
                            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, viewA3.getMeasuredState() & (-16777216));
                            f4 = f6;
                            i4 = i29;
                        } else {
                            i6 = 1073741824;
                        }
                        measuredWidth2 = viewA3.getMeasuredWidth() + measuredWidth2;
                        if (measuredWidth2 < 0) {
                            measuredWidth2 = 0;
                        }
                        viewA3.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, i6), childMeasureSpec);
                        iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, viewA3.getMeasuredState() & (-16777216));
                        f4 = f6;
                        i4 = i29;
                    } else {
                        i4 = i26;
                    }
                    if (z7) {
                        this.f = b(viewA3) + viewA3.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) aVar3).leftMargin + ((ViewGroup.MarginLayoutParams) aVar3).rightMargin + this.f;
                    } else {
                        int i30 = this.f;
                        this.f = Math.max(i30, b(viewA3) + viewA3.getMeasuredWidth() + i30 + ((ViewGroup.MarginLayoutParams) aVar3).leftMargin + ((ViewGroup.MarginLayoutParams) aVar3).rightMargin);
                    }
                    boolean z13 = mode2 != 1073741824 && ((ViewGroup.MarginLayoutParams) aVar3).height == -1;
                    int i31 = ((ViewGroup.MarginLayoutParams) aVar3).topMargin + ((ViewGroup.MarginLayoutParams) aVar3).bottomMargin;
                    int measuredHeight2 = viewA3.getMeasuredHeight() + i31;
                    iMax9 = Math.max(iMax9, measuredHeight2);
                    if (!z13) {
                        i31 = measuredHeight2;
                    }
                    int iMax10 = Math.max(i27, i31);
                    if (z9) {
                        i5 = -1;
                        boolean z14 = ((ViewGroup.MarginLayoutParams) aVar3).height == -1;
                        if (!z12 && (baseline = viewA3.getBaseline()) != i5) {
                            int i32 = aVar3.f272b;
                            if (i32 < 0) {
                                i32 = this.e;
                            }
                            int i33 = (((i32 & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay) >> 4) & (-2)) >> 1;
                            iArr2[i33] = Math.max(iArr2[i33], baseline);
                            iArr[i33] = Math.max(iArr[i33], measuredHeight2 - baseline);
                        }
                        i27 = iMax10;
                        z9 = z14;
                        f4 = f4;
                    } else {
                        i5 = -1;
                    }
                    if (!z12) {
                    }
                    i27 = iMax10;
                    z9 = z14;
                    f4 = f4;
                }
                i28++;
                i26 = i4;
                virtualChildCount = virtualChildCount;
            }
            i3 = virtualChildCount;
            this.f = getPaddingRight() + getPaddingLeft() + this.f;
            iMax = (iArr2[1] == -1 && iArr2[0] == -1 && iArr2[2] == -1 && iArr2[3] == -1) ? iMax9 : Math.max(iMax9, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))));
            i23 = iCombineMeasuredStates3;
            iMax2 = i27;
        } else {
            iMax2 = Math.max(i21, i22);
            if (z11 && mode != 1073741824) {
                for (int i34 = 0; i34 < virtualChildCount; i34++) {
                    View viewA4 = a(i34);
                    if (viewA4 != null && viewA4.getVisibility() != 8 && ((a) viewA4.getLayoutParams()).f271a > 0.0f) {
                        viewA4.measure(View.MeasureSpec.makeMeasureSpec(iMax3, 1073741824), View.MeasureSpec.makeMeasureSpec(viewA4.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i3 = virtualChildCount;
            iMax = i25;
        }
        if (z9 || mode2 == 1073741824) {
            iMax2 = iMax;
        }
        setMeasuredDimension(iResolveSizeAndState | (i23 & (-16777216)), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax2, getSuggestedMinimumHeight()), i2, i23 << 16));
        if (z10) {
            a(i3, i);
        }
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof a;
    }

    /* JADX WARN: Code duplicated, block: B:136:0x02ed A[PHI: r10
      0x02ed: PHI (r10v22 int) = (r10v20 int), (r10v23 int) binds: [B:135:0x02eb, B:131:0x02e0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:148:0x0329  */
    /* JADX WARN: Code duplicated, block: B:154:0x0336  */
    public void d(int i, int i2) {
        int i3;
        int i4;
        int iCombineMeasuredStates;
        int iMax;
        int i5;
        float f;
        int i6;
        int i7;
        boolean z;
        boolean z2;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int iMax2;
        int i13;
        int i14;
        View view;
        int iMax3;
        boolean z3;
        int iMax4;
        this.f = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int i15 = this.f269b;
        boolean z4 = this.h;
        float f2 = 0.0f;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int iA = 0;
        boolean z5 = false;
        boolean z6 = true;
        boolean z7 = false;
        while (true) {
            int i21 = 8;
            i19 = i19;
            if (iA >= virtualChildCount) {
                int i22 = i16;
                int i23 = i18;
                int i24 = i20;
                int i25 = virtualChildCount;
                int iMax5 = i17;
                int i26 = mode2;
                if (this.f > 0) {
                    i3 = i25;
                    if (b(i3)) {
                        this.f += this.m;
                    }
                } else {
                    i3 = i25;
                }
                if (z4) {
                    i4 = i26;
                    if (i4 == Integer.MIN_VALUE || i4 == 0) {
                        this.f = 0;
                        int iA2 = 0;
                        while (iA2 < i3) {
                            View viewA = a(iA2);
                            if (viewA == null) {
                                this.f = c(iA2) + this.f;
                            } else if (viewA.getVisibility() == i21) {
                                iA2 += a(viewA, iA2);
                            } else {
                                a aVar = (a) viewA.getLayoutParams();
                                int i27 = this.f;
                                this.f = Math.max(i27, b(viewA) + i27 + i23 + ((ViewGroup.MarginLayoutParams) aVar).topMargin + ((ViewGroup.MarginLayoutParams) aVar).bottomMargin);
                            }
                            iA2++;
                            i21 = 8;
                        }
                    }
                } else {
                    i4 = i26;
                }
                this.f = getPaddingBottom() + getPaddingTop() + this.f;
                int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.f, getSuggestedMinimumHeight()), i2, 0);
                int i28 = (16777215 & iResolveSizeAndState) - this.f;
                if (z5 || (i28 != 0 && f2 > 0.0f)) {
                    float f3 = this.g;
                    if (f3 > 0.0f) {
                        f2 = f3;
                    }
                    this.f = 0;
                    float f4 = f2;
                    int i29 = 0;
                    int iMax6 = i24;
                    iCombineMeasuredStates = i22;
                    while (i29 < i3) {
                        View viewA2 = a(i29);
                        if (viewA2.getVisibility() == 8) {
                            f = f4;
                        } else {
                            a aVar2 = (a) viewA2.getLayoutParams();
                            float f5 = aVar2.f271a;
                            if (f5 > 0.0f) {
                                int measuredHeight = (int) ((i28 * f5) / f4);
                                i5 = i28 - measuredHeight;
                                f = f4 - f5;
                                int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) aVar2).leftMargin + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin, ((ViewGroup.MarginLayoutParams) aVar2).width);
                                if (((ViewGroup.MarginLayoutParams) aVar2).height == 0) {
                                    i8 = 1073741824;
                                    if (i4 == 1073741824) {
                                        if (measuredHeight <= 0) {
                                            measuredHeight = 0;
                                        }
                                    }
                                    viewA2.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight, i8));
                                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, viewA2.getMeasuredState() & (-256));
                                } else {
                                    i8 = 1073741824;
                                }
                                measuredHeight = viewA2.getMeasuredHeight() + measuredHeight;
                                if (measuredHeight < 0) {
                                    measuredHeight = 0;
                                }
                                viewA2.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight, i8));
                                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, viewA2.getMeasuredState() & (-256));
                            } else {
                                i5 = i28;
                                f = f4;
                            }
                            int i30 = ((ViewGroup.MarginLayoutParams) aVar2).leftMargin + ((ViewGroup.MarginLayoutParams) aVar2).rightMargin;
                            int measuredWidth = viewA2.getMeasuredWidth() + i30;
                            iMax5 = Math.max(iMax5, measuredWidth);
                            if (mode != 1073741824) {
                                i6 = iCombineMeasuredStates;
                                i7 = -1;
                                z = ((ViewGroup.MarginLayoutParams) aVar2).width == -1;
                                if (!z) {
                                    i30 = measuredWidth;
                                }
                                iMax6 = Math.max(iMax6, i30);
                                if (z6 || ((ViewGroup.MarginLayoutParams) aVar2).width != i7) {
                                    z2 = false;
                                } else {
                                    z2 = true;
                                }
                                int i31 = this.f;
                                this.f = Math.max(i31, b(viewA2) + viewA2.getMeasuredHeight() + i31 + ((ViewGroup.MarginLayoutParams) aVar2).topMargin + ((ViewGroup.MarginLayoutParams) aVar2).bottomMargin);
                                z6 = z2;
                                i28 = i5;
                                iCombineMeasuredStates = i6;
                            } else {
                                i6 = iCombineMeasuredStates;
                                i7 = -1;
                            }
                            if (!z) {
                                i30 = measuredWidth;
                            }
                            iMax6 = Math.max(iMax6, i30);
                            if (z6) {
                                z2 = false;
                            } else {
                                z2 = false;
                            }
                            int i32 = this.f;
                            this.f = Math.max(i32, b(viewA2) + viewA2.getMeasuredHeight() + i32 + ((ViewGroup.MarginLayoutParams) aVar2).topMargin + ((ViewGroup.MarginLayoutParams) aVar2).bottomMargin);
                            z6 = z2;
                            i28 = i5;
                            iCombineMeasuredStates = i6;
                        }
                        i29++;
                        f4 = f;
                    }
                    this.f = getPaddingBottom() + getPaddingTop() + this.f;
                    iMax = iMax6;
                } else {
                    iMax = Math.max(i24, i19);
                    if (z4 && i4 != 1073741824) {
                        for (int i33 = 0; i33 < i3; i33++) {
                            View viewA3 = a(i33);
                            if (viewA3 != null && viewA3.getVisibility() != 8 && ((a) viewA3.getLayoutParams()).f271a > 0.0f) {
                                viewA3.measure(View.MeasureSpec.makeMeasureSpec(viewA3.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i23, 1073741824));
                            }
                        }
                    }
                    iCombineMeasuredStates = i22;
                }
                if (z6 || mode == 1073741824) {
                    iMax = iMax5;
                }
                setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + iMax, getSuggestedMinimumWidth()), i, iCombineMeasuredStates), iResolveSizeAndState);
                if (z7) {
                    b(i3, i2);
                    return;
                }
                return;
            }
            View viewA4 = a(iA);
            if (viewA4 == null) {
                this.f = c(iA) + this.f;
            } else {
                int i34 = i16;
                if (viewA4.getVisibility() == 8) {
                    iA += a(viewA4, iA);
                    i16 = i34;
                } else {
                    if (b(iA)) {
                        this.f += this.m;
                    }
                    a aVar3 = (a) viewA4.getLayoutParams();
                    float f6 = f2 + aVar3.f271a;
                    if (mode2 == 1073741824 && ((ViewGroup.MarginLayoutParams) aVar3).height == 0 && aVar3.f271a > 0.0f) {
                        int i35 = this.f;
                        this.f = Math.max(i35, ((ViewGroup.MarginLayoutParams) aVar3).topMargin + i35 + ((ViewGroup.MarginLayoutParams) aVar3).bottomMargin);
                        iMax3 = i18;
                        view = viewA4;
                        i13 = i20;
                        virtualChildCount = virtualChildCount;
                        i10 = i34;
                        i11 = i17;
                        z5 = true;
                        i14 = iA;
                        i12 = mode2;
                        iMax2 = i19;
                    } else {
                        int i36 = i17;
                        if (((ViewGroup.MarginLayoutParams) aVar3).height != 0 || aVar3.f271a <= 0.0f) {
                            i9 = Integer.MIN_VALUE;
                        } else {
                            ((ViewGroup.MarginLayoutParams) aVar3).height = -2;
                            i9 = 0;
                        }
                        i10 = i34;
                        int i37 = i9;
                        i11 = i36;
                        int i38 = i18;
                        virtualChildCount = virtualChildCount;
                        i12 = mode2;
                        iMax2 = i19;
                        i13 = i20;
                        i14 = iA;
                        a(viewA4, iA, i, 0, i2, f6 == 0.0f ? this.f : 0);
                        if (i37 != Integer.MIN_VALUE) {
                            ((ViewGroup.MarginLayoutParams) aVar3).height = i37;
                        }
                        int measuredHeight2 = viewA4.getMeasuredHeight();
                        int i39 = this.f;
                        view = viewA4;
                        this.f = Math.max(i39, b(view) + i39 + measuredHeight2 + ((ViewGroup.MarginLayoutParams) aVar3).topMargin + ((ViewGroup.MarginLayoutParams) aVar3).bottomMargin);
                        iMax3 = z4 ? Math.max(measuredHeight2, i38) : i38;
                    }
                    if (i15 >= 0 && i15 == i14 + 1) {
                        this.f270c = this.f;
                    }
                    if (i14 < i15 && aVar3.f271a > 0.0f) {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    if (mode == 1073741824 || ((ViewGroup.MarginLayoutParams) aVar3).width != -1) {
                        z3 = false;
                    } else {
                        z3 = true;
                        z7 = true;
                    }
                    int i40 = ((ViewGroup.MarginLayoutParams) aVar3).leftMargin + ((ViewGroup.MarginLayoutParams) aVar3).rightMargin;
                    int measuredWidth2 = view.getMeasuredWidth() + i40;
                    int iMax7 = Math.max(i11, measuredWidth2);
                    int iCombineMeasuredStates2 = View.combineMeasuredStates(i10, view.getMeasuredState());
                    boolean z8 = z6 && ((ViewGroup.MarginLayoutParams) aVar3).width == -1;
                    if (aVar3.f271a > 0.0f) {
                        if (!z3) {
                            i40 = measuredWidth2;
                        }
                        iMax2 = Math.max(iMax2, i40);
                        iMax4 = i13;
                    } else {
                        if (!z3) {
                            i40 = measuredWidth2;
                        }
                        iMax4 = Math.max(i13, i40);
                    }
                    int iA3 = a(view, i14) + i14;
                    i18 = iMax3;
                    z6 = z8;
                    i19 = iMax2;
                    f2 = f6;
                    i20 = iMax4;
                    i16 = iCombineMeasuredStates2;
                    iA = iA3;
                    i17 = iMax7;
                }
                iA++;
                mode2 = i12;
                virtualChildCount = virtualChildCount;
            }
            i12 = mode2;
            iA++;
            mode2 = i12;
            virtualChildCount = virtualChildCount;
        }
    }

    @Override // android.view.ViewGroup
    public a generateDefaultLayoutParams() {
        int i = this.d;
        if (i == 0) {
            return new a(-2, -2);
        }
        if (i == 1) {
            return new a(-1, -2);
        }
        return null;
    }

    @Override // android.view.ViewGroup
    public a generateLayoutParams(AttributeSet attributeSet) {
        return new a(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public a generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new a(layoutParams);
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.f269b < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i2 = this.f269b;
        if (childCount <= i2) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i2);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.f269b == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int bottom = this.f270c;
        if (this.d == 1 && (i = this.e & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay) != 48) {
            if (i == 16) {
                bottom += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.f) / 2;
            } else if (i == 80) {
                bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.f;
            }
        }
        return bottom + ((ViewGroup.MarginLayoutParams) ((a) childAt.getLayoutParams())).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.f269b;
    }

    public Drawable getDividerDrawable() {
        return this.k;
    }

    public int getDividerPadding() {
        return this.o;
    }

    public int getDividerWidth() {
        return this.l;
    }

    public int getGravity() {
        return this.e;
    }

    public int getOrientation() {
        return this.d;
    }

    public int getShowDividers() {
        return this.n;
    }

    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.g;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.k == null) {
            return;
        }
        if (this.d == 1) {
            b(canvas);
        } else {
            a(canvas);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(E.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(E.class.getName());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.d == 1) {
            b(i, i2, i3, i4);
        } else {
            a(i, i2, i3, i4);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        if (this.d == 1) {
            d(i, i2);
        } else {
            c(i, i2);
        }
    }

    public void setBaselineAligned(boolean z) {
        this.f268a = z;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i >= 0 && i < getChildCount()) {
            this.f269b = i;
            return;
        }
        StringBuilder sbA = c.a.a.a.a.a("base aligned child index out of range (0, ");
        sbA.append(getChildCount());
        sbA.append(")");
        throw new IllegalArgumentException(sbA.toString());
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.k) {
            return;
        }
        this.k = drawable;
        if (drawable != null) {
            this.l = drawable.getIntrinsicWidth();
            this.m = drawable.getIntrinsicHeight();
        } else {
            this.l = 0;
            this.m = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i) {
        this.o = i;
    }

    public void setGravity(int i) {
        if (this.e != i) {
            if ((8388615 & i) == 0) {
                i |= 8388611;
            }
            if ((i & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay) == 0) {
                i |= 48;
            }
            this.e = i;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & 8388615;
        int i3 = this.e;
        if ((8388615 & i3) != i2) {
            this.e = i2 | ((-8388616) & i3);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.h = z;
    }

    public void setOrientation(int i) {
        if (this.d != i) {
            this.d = i;
            requestLayout();
        }
    }

    public void setShowDividers(int i) {
        if (i != this.n) {
            requestLayout();
        }
        this.n = i;
    }

    public void setVerticalGravity(int i) {
        int i2 = i & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay;
        int i3 = this.e;
        if ((i3 & a.b.e.b.j.AppCompatTheme_windowActionBarOverlay) != i2) {
            this.e = i2 | (i3 & (-113));
            requestLayout();
        }
    }

    public void setWeightSum(float f) {
        this.g = Math.max(0.0f, f);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public E(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f268a = true;
        this.f269b = -1;
        this.f270c = 0;
        this.e = 8388659;
        ha haVarA = ha.a(context, attributeSet, a.b.e.b.j.LinearLayoutCompat, i, 0);
        int iD = haVarA.d(a.b.e.b.j.LinearLayoutCompat_android_orientation, -1);
        if (iD >= 0) {
            setOrientation(iD);
        }
        int iD2 = haVarA.d(a.b.e.b.j.LinearLayoutCompat_android_gravity, -1);
        if (iD2 >= 0) {
            setGravity(iD2);
        }
        boolean zA = haVarA.a(a.b.e.b.j.LinearLayoutCompat_android_baselineAligned, true);
        if (!zA) {
            setBaselineAligned(zA);
        }
        this.g = haVarA.f337b.getFloat(a.b.e.b.j.LinearLayoutCompat_android_weightSum, -1.0f);
        this.f269b = haVarA.d(a.b.e.b.j.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.h = haVarA.a(a.b.e.b.j.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(haVarA.b(a.b.e.b.j.LinearLayoutCompat_divider));
        this.n = haVarA.d(a.b.e.b.j.LinearLayoutCompat_showDividers, 0);
        this.o = haVarA.c(a.b.e.b.j.LinearLayoutCompat_dividerPadding, 0);
        haVarA.f337b.recycle();
    }
}
