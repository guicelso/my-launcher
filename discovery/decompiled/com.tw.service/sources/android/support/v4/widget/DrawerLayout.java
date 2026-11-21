package android.support.v4.widget;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.view.AbsSavedState;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DrawerLayout extends ViewGroup {
    static final boolean Zh;
    private static final boolean _h;
    private float Af;
    private int Ah;
    private float Bf;
    private int Bh;
    private float Ch;
    private final G Dh;
    private final G Eh;
    private final o Fh;
    private final o Gh;
    private int Hh;
    private int Ih;
    private int Jh;
    private int Kh;
    private int Lh;
    private boolean Mh;
    private Drawable Nh;
    private Drawable Oh;
    private CharSequence Ph;
    private CharSequence Qh;
    private Drawable Rh;
    private Drawable Sh;
    private Drawable Th;
    private Drawable Uh;
    private final ArrayList Vh;
    private Rect Wh;
    private Matrix Xh;
    private Paint _f;
    private Object gg;
    private boolean hg;
    private Drawable ig;
    private boolean mInLayout;
    private List mListeners;
    private boolean mh;
    private final k yh;
    private float zh;
    private static final int[] Yh = {R.attr.colorPrimaryDark};
    static final int[] wh = {R.attr.layout_gravity};

    public class LayoutParams extends ViewGroup.MarginLayoutParams {
        float ak;
        boolean bk;
        int ck;
        public int gravity;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = 0;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = 0;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.wh);
            this.gravity = typedArrayObtainStyledAttributes.getInt(0, 0);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = 0;
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = 0;
        }
    }

    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new m();
        int Le;
        int Me;
        int Ne;
        int Oe;
        int Pe;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.Le = 0;
            this.Le = parcel.readInt();
            this.Me = parcel.readInt();
            this.Ne = parcel.readInt();
            this.Oe = parcel.readInt();
            this.Pe = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
            this.Le = 0;
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.Le);
            parcel.writeInt(this.Me);
            parcel.writeInt(this.Ne);
            parcel.writeInt(this.Oe);
            parcel.writeInt(this.Pe);
        }
    }

    static {
        int i = Build.VERSION.SDK_INT;
        Zh = true;
        _h = true;
    }

    public DrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DrawerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.yh = new k();
        this.Bh = -1728053248;
        this._f = new Paint();
        this.mh = true;
        this.Ih = 3;
        this.Jh = 3;
        this.Kh = 3;
        this.Lh = 3;
        this.Rh = null;
        this.Sh = null;
        this.Th = null;
        this.Uh = null;
        setDescendantFocusability(262144);
        float f = getResources().getDisplayMetrics().density;
        this.Ah = (int) ((64.0f * f) + 0.5f);
        float f2 = 400.0f * f;
        this.Fh = new o(this, 3);
        this.Gh = new o(this, 5);
        this.Dh = G.a(this, 1.0f, this.Fh);
        this.Dh.s(1);
        this.Dh.c(f2);
        this.Fh.a(this.Dh);
        this.Eh = G.a(this, 1.0f, this.Gh);
        this.Eh.s(2);
        this.Eh.c(f2);
        this.Gh.a(this.Eh);
        setFocusableInTouchMode(true);
        android.support.v4.view.r.d(this, 1);
        android.support.v4.view.r.a(this, new j(this));
        setMotionEventSplittingEnabled(false);
        if (android.support.v4.view.r.d(this)) {
            int i2 = Build.VERSION.SDK_INT;
            setOnApplyWindowInsetsListener(new i(this));
            setSystemUiVisibility(1280);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Yh);
            try {
                this.ig = typedArrayObtainStyledAttributes.getDrawable(0);
                typedArrayObtainStyledAttributes.recycle();
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        }
        this.zh = f * 10.0f;
        this.Vh = new ArrayList();
    }

    private boolean a(Drawable drawable, int i) {
        if (drawable == null) {
            return false;
        }
        int i2 = Build.VERSION.SDK_INT;
        if (!drawable.isAutoMirrored()) {
            return false;
        }
        int i3 = Build.VERSION.SDK_INT;
        drawable.setLayoutDirection(i);
        return true;
    }

    private void c(View view, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            android.support.v4.view.r.d(childAt, ((z || B(childAt)) && !(z && childAt == view)) ? 4 : 1);
        }
    }

    static boolean y(View view) {
        return (android.support.v4.view.r.e(view) == 4 || android.support.v4.view.r.e(view) == 2) ? false : true;
    }

    public boolean A(View view) {
        if (B(view)) {
            return (((LayoutParams) view.getLayoutParams()).ck & 1) == 1;
        }
        throw new IllegalArgumentException(b.a.a.a.a.a("View ", view, " is not a drawer"));
    }

    boolean B(View view) {
        int absoluteGravity = a.b.a.a.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, android.support.v4.view.r.f(view));
        return ((absoluteGravity & 3) == 0 && (absoluteGravity & 5) == 0) ? false : true;
    }

    public boolean C(View view) {
        if (B(view)) {
            return ((LayoutParams) view.getLayoutParams()).ak > 0.0f;
        }
        throw new IllegalArgumentException(b.a.a.a.a.a("View ", view, " is not a drawer"));
    }

    public void D(View view) {
        b(view, true);
    }

    void W() {
        if (this.Mh) {
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchTouchEvent(motionEventObtain);
        }
        motionEventObtain.recycle();
        this.Mh = true;
    }

    public void X() {
        j(false);
    }

    View Y() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((((LayoutParams) childAt.getLayoutParams()).ck & 1) == 1) {
                return childAt;
            }
        }
        return null;
    }

    View Z() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (B(childAt) && C(childAt)) {
                return childAt;
            }
        }
        return null;
    }

    void a(int i, int i2, View view) {
        int iQ = this.Dh.Q();
        int iQ2 = this.Eh.Q();
        int i3 = 2;
        if (iQ == 1 || iQ2 == 1) {
            i3 = 1;
        } else if (iQ != 2 && iQ2 != 2) {
            i3 = 0;
        }
        if (view != null && i2 == 0) {
            float f = ((LayoutParams) view.getLayoutParams()).ak;
            if (f == 0.0f) {
                t(view);
            } else if (f == 1.0f) {
                u(view);
            }
        }
        if (i3 != this.Hh) {
            this.Hh = i3;
            List list = this.mListeners;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    ((l) this.mListeners.get(size)).a(i3);
                }
            }
        }
    }

    public void a(View view, boolean z) {
        G g;
        int width;
        if (!B(view)) {
            throw new IllegalArgumentException(b.a.a.a.a.a("View ", view, " is not a sliding drawer"));
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (this.mh) {
            layoutParams.ak = 0.0f;
            layoutParams.ck = 0;
        } else if (z) {
            layoutParams.ck |= 4;
            if (k(view, 3)) {
                g = this.Dh;
                width = -view.getWidth();
            } else {
                g = this.Eh;
                width = getWidth();
            }
            g.d(view, width, view.getTop());
        } else {
            d(view, 0.0f);
            a(layoutParams.gravity, 0, view);
            view.setVisibility(4);
        }
        invalidate();
    }

    public void a(Object obj, boolean z) {
        this.gg = obj;
        this.hg = z;
        setWillNotDraw(!z && getBackground() == null);
        requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList arrayList, int i, int i2) {
        if (getDescendantFocusability() == 393216) {
            return;
        }
        int childCount = getChildCount();
        boolean z = false;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (!B(childAt)) {
                this.Vh.add(childAt);
            } else if (A(childAt)) {
                childAt.addFocusables(arrayList, i, i2);
                z = true;
            }
        }
        if (!z) {
            int size = this.Vh.size();
            for (int i4 = 0; i4 < size; i4++) {
                View view = (View) this.Vh.get(i4);
                if (view.getVisibility() == 0) {
                    view.addFocusables(arrayList, i, i2);
                }
            }
        }
        this.Vh.clear();
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        android.support.v4.view.r.d(view, (Y() != null || B(view)) ? 4 : 1);
        if (Zh) {
            return;
        }
        android.support.v4.view.r.a(view, this.yh);
    }

    public void b(View view, boolean z) {
        if (!B(view)) {
            throw new IllegalArgumentException(b.a.a.a.a.a("View ", view, " is not a sliding drawer"));
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (this.mh) {
            layoutParams.ak = 1.0f;
            layoutParams.ck = 1;
            c(view, true);
        } else if (z) {
            layoutParams.ck |= 2;
            if (k(view, 3)) {
                this.Dh.d(view, 0, view.getTop());
            } else {
                this.Eh.d(view, getWidth() - view.getWidth(), view.getTop());
            }
        } else {
            d(view, 1.0f);
            a(layoutParams.gravity, 0, view);
            view.setVisibility(0);
        }
        invalidate();
    }

    void c(View view, float f) {
        List list = this.mListeners;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                ((l) this.mListeners.get(size)).a(view, f);
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public void computeScroll() {
        int childCount = getChildCount();
        float fMax = 0.0f;
        for (int i = 0; i < childCount; i++) {
            fMax = Math.max(fMax, ((LayoutParams) getChildAt(i).getLayoutParams()).ak);
        }
        this.Ch = fMax;
        boolean zI = this.Dh.i(true);
        boolean zI2 = this.Eh.i(true);
        if (zI || zI2) {
            android.support.v4.view.r.n(this);
        }
    }

    void d(View view, float f) {
        float fX = x(view);
        float width = view.getWidth();
        int i = ((int) (width * f)) - ((int) (fX * width));
        if (!k(view, 3)) {
            i = -i;
        }
        view.offsetLeftAndRight(i);
        e(view, f);
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        boolean zDispatchGenericMotionEvent;
        if ((motionEvent.getSource() & 2) == 0 || motionEvent.getAction() == 10 || this.Ch <= 0.0f) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        for (int i = childCount - 1; i >= 0; i--) {
            View childAt = getChildAt(i);
            if (this.Wh == null) {
                this.Wh = new Rect();
            }
            childAt.getHitRect(this.Wh);
            if (this.Wh.contains((int) x, (int) y) && !z(childAt)) {
                if (childAt.getMatrix().isIdentity()) {
                    float scrollX = getScrollX() - childAt.getLeft();
                    float scrollY = getScrollY() - childAt.getTop();
                    motionEvent.offsetLocation(scrollX, scrollY);
                    zDispatchGenericMotionEvent = childAt.dispatchGenericMotionEvent(motionEvent);
                    motionEvent.offsetLocation(-scrollX, -scrollY);
                } else {
                    float scrollX2 = getScrollX() - childAt.getLeft();
                    float scrollY2 = getScrollY() - childAt.getTop();
                    MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                    motionEventObtain.offsetLocation(scrollX2, scrollY2);
                    Matrix matrix = childAt.getMatrix();
                    if (!matrix.isIdentity()) {
                        if (this.Xh == null) {
                            this.Xh = new Matrix();
                        }
                        matrix.invert(this.Xh);
                        motionEventObtain.transform(this.Xh);
                    }
                    zDispatchGenericMotionEvent = childAt.dispatchGenericMotionEvent(motionEventObtain);
                    motionEventObtain.recycle();
                }
                if (zDispatchGenericMotionEvent) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        int i;
        Drawable drawable;
        int height = getHeight();
        boolean z = z(view);
        int width = getWidth();
        int iSave = canvas.save();
        int i2 = 0;
        if (z) {
            int childCount = getChildCount();
            i = width;
            int i3 = 0;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (childAt != view && childAt.getVisibility() == 0) {
                    Drawable background = childAt.getBackground();
                    if ((background != null && background.getOpacity() == -1) && B(childAt) && childAt.getHeight() >= height) {
                        if (k(childAt, 3)) {
                            int right = childAt.getRight();
                            if (right > i3) {
                                i3 = right;
                            }
                        } else {
                            int left = childAt.getLeft();
                            if (left < i) {
                                i = left;
                            }
                        }
                    }
                }
            }
            canvas.clipRect(i3, 0, i, getHeight());
            i2 = i3;
        } else {
            i = width;
        }
        boolean zDrawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(iSave);
        float f = this.Ch;
        if (f <= 0.0f || !z) {
            if (this.Nh != null && k(view, 3)) {
                int intrinsicWidth = this.Nh.getIntrinsicWidth();
                int right2 = view.getRight();
                float fMax = Math.max(0.0f, Math.min(right2 / this.Dh.P(), 1.0f));
                this.Nh.setBounds(right2, view.getTop(), intrinsicWidth + right2, view.getBottom());
                this.Nh.setAlpha((int) (fMax * 255.0f));
                drawable = this.Nh;
            } else if (this.Oh != null && k(view, 5)) {
                int intrinsicWidth2 = this.Oh.getIntrinsicWidth();
                int left2 = view.getLeft();
                float fMax2 = Math.max(0.0f, Math.min((getWidth() - left2) / this.Eh.P(), 1.0f));
                this.Oh.setBounds(left2 - intrinsicWidth2, view.getTop(), left2, view.getBottom());
                this.Oh.setAlpha((int) (fMax2 * 255.0f));
                drawable = this.Oh;
            }
            drawable.draw(canvas);
        } else {
            int i5 = this.Bh;
            this._f.setColor((i5 & 16777215) | (((int) ((((-16777216) & i5) >>> 24) * f)) << 24));
            canvas.drawRect(i2, 0.0f, i, getHeight(), this._f);
        }
        return zDrawChild;
    }

    void e(View view, float f) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f == layoutParams.ak) {
            return;
        }
        layoutParams.ak = f;
        c(view, f);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public void h(int i, int i2) {
        View viewV;
        int absoluteGravity = a.b.a.a.getAbsoluteGravity(i2, android.support.v4.view.r.f(this));
        if (i2 == 3) {
            this.Ih = i;
        } else if (i2 == 5) {
            this.Jh = i;
        } else if (i2 == 8388611) {
            this.Kh = i;
        } else if (i2 == 8388613) {
            this.Lh = i;
        }
        if (i != 0) {
            (absoluteGravity == 3 ? this.Dh : this.Eh).cancel();
        }
        if (i != 1) {
            if (i == 2 && (viewV = v(absoluteGravity)) != null) {
                D(viewV);
                return;
            }
            return;
        }
        View viewV2 = v(absoluteGravity);
        if (viewV2 != null) {
            s(viewV2);
        }
    }

    void j(boolean z) {
        int childCount = getChildCount();
        boolean zD = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (B(childAt) && (!z || layoutParams.bk)) {
                zD |= k(childAt, 3) ? this.Dh.d(childAt, -childAt.getWidth(), childAt.getTop()) : this.Eh.d(childAt, getWidth(), childAt.getTop());
                layoutParams.bk = false;
            }
        }
        this.Fh.N();
        this.Gh.N();
        if (zD) {
            invalidate();
        }
    }

    boolean k(View view, int i) {
        return (w(view) & i) == i;
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
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.hg || this.ig == null) {
            return;
        }
        int i = Build.VERSION.SDK_INT;
        Object obj = this.gg;
        int systemWindowInsetTop = obj != null ? ((WindowInsets) obj).getSystemWindowInsetTop() : 0;
        if (systemWindowInsetTop > 0) {
            this.ig.setBounds(0, 0, getWidth(), systemWindowInsetTop);
            this.ig.draw(canvas);
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0031  */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View viewE;
        boolean z2;
        int actionMasked = motionEvent.getActionMasked();
        boolean zB = this.Dh.b(motionEvent) | this.Eh.b(motionEvent);
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                j(true);
                this.Mh = false;
            } else if (actionMasked != 2) {
                if (actionMasked == 3) {
                    j(true);
                    this.Mh = false;
                }
            } else if (this.Dh.p(3)) {
                this.Fh.N();
                this.Gh.N();
            }
            z = false;
        } else {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.Af = x;
            this.Bf = y;
            z = this.Ch > 0.0f && (viewE = this.Dh.e((int) x, (int) y)) != null && z(viewE);
            this.Mh = false;
        }
        if (!zB && !z) {
            int childCount = getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    z2 = false;
                    break;
                }
                if (((LayoutParams) getChildAt(i).getLayoutParams()).bk) {
                    z2 = true;
                    break;
                }
                i++;
            }
            if (!z2 && !this.Mh) {
                return false;
            }
        }
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (Z() != null) {
                keyEvent.startTracking();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyUp(i, keyEvent);
        }
        View viewZ = Z();
        if (viewZ != null && v(viewZ) == 0) {
            X();
        }
        return viewZ != null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        int i5;
        int measuredHeight;
        int i6;
        int i7;
        boolean z2 = true;
        this.mInLayout = true;
        int i8 = i3 - i;
        int childCount = getChildCount();
        int i9 = 0;
        while (i9 < childCount) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (z(childAt)) {
                    int i10 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    childAt.layout(i10, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, childAt.getMeasuredWidth() + i10, childAt.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin);
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight2 = childAt.getMeasuredHeight();
                    if (k(childAt, 3)) {
                        float f2 = measuredWidth;
                        i5 = (-measuredWidth) + ((int) (layoutParams.ak * f2));
                        f = (measuredWidth + i5) / f2;
                    } else {
                        float f3 = measuredWidth;
                        int i11 = i8 - ((int) (layoutParams.ak * f3));
                        f = (i8 - i11) / f3;
                        i5 = i11;
                    }
                    boolean z3 = f != layoutParams.ak ? z2 : false;
                    int i12 = layoutParams.gravity & 112;
                    if (i12 != 16) {
                        if (i12 != 80) {
                            measuredHeight = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                            i6 = measuredWidth + i5;
                            i7 = measuredHeight2 + measuredHeight;
                        } else {
                            int i13 = i4 - i2;
                            measuredHeight = (i13 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) - childAt.getMeasuredHeight();
                            i6 = measuredWidth + i5;
                            i7 = i13 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                        }
                        childAt.layout(i5, measuredHeight, i6, i7);
                    } else {
                        int i14 = i4 - i2;
                        int i15 = (i14 - measuredHeight2) / 2;
                        int i16 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                        if (i15 < i16) {
                            i15 = i16;
                        } else {
                            int i17 = i15 + measuredHeight2;
                            int i18 = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                            if (i17 > i14 - i18) {
                                i15 = (i14 - i18) - measuredHeight2;
                            }
                        }
                        childAt.layout(i5, i15, measuredWidth + i5, measuredHeight2 + i15);
                    }
                    if (z3) {
                        e(childAt, f);
                    }
                    int i19 = layoutParams.ak > 0.0f ? 0 : 4;
                    if (childAt.getVisibility() != i19) {
                        childAt.setVisibility(i19);
                    }
                }
            }
            i9++;
            z2 = true;
        }
        this.mInLayout = false;
        this.mh = false;
    }

    @Override // android.view.View
    @SuppressLint({"WrongConstant"})
    protected void onMeasure(int i, int i2) {
        String hexString;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824 || mode2 != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
            if (mode != Integer.MIN_VALUE && mode == 0) {
                size = 300;
            }
            if (mode2 != Integer.MIN_VALUE && mode2 == 0) {
                size2 = 300;
            }
        }
        setMeasuredDimension(size, size2);
        int i3 = 0;
        boolean z = this.gg != null && android.support.v4.view.r.d(this);
        int iF = android.support.v4.view.r.f(this);
        int childCount = getChildCount();
        int i4 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i3 < childCount) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (z) {
                    int absoluteGravity = a.b.a.a.getAbsoluteGravity(layoutParams.gravity, iF);
                    if (android.support.v4.view.r.d(childAt)) {
                        int i5 = Build.VERSION.SDK_INT;
                        WindowInsets windowInsetsReplaceSystemWindowInsets = (WindowInsets) this.gg;
                        if (absoluteGravity == 3) {
                            windowInsetsReplaceSystemWindowInsets = windowInsetsReplaceSystemWindowInsets.replaceSystemWindowInsets(windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetLeft(), windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetTop(), i4, windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetBottom());
                        } else if (absoluteGravity == 5) {
                            windowInsetsReplaceSystemWindowInsets = windowInsetsReplaceSystemWindowInsets.replaceSystemWindowInsets(i4, windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetTop(), windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetRight(), windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetBottom());
                        }
                        childAt.dispatchApplyWindowInsets(windowInsetsReplaceSystemWindowInsets);
                    } else {
                        int i6 = Build.VERSION.SDK_INT;
                        WindowInsets windowInsetsReplaceSystemWindowInsets2 = (WindowInsets) this.gg;
                        if (absoluteGravity == 3) {
                            windowInsetsReplaceSystemWindowInsets2 = windowInsetsReplaceSystemWindowInsets2.replaceSystemWindowInsets(windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetLeft(), windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetTop(), i4, windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetBottom());
                        } else if (absoluteGravity == 5) {
                            windowInsetsReplaceSystemWindowInsets2 = windowInsetsReplaceSystemWindowInsets2.replaceSystemWindowInsets(i4, windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetTop(), windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetRight(), windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetBottom());
                        }
                        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetLeft();
                        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetTop();
                        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetRight();
                        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetBottom();
                    }
                }
                if (z(childAt)) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec((size - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec((size2 - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, 1073741824));
                } else {
                    if (!B(childAt)) {
                        throw new IllegalStateException("Child " + childAt + " at index " + i3 + " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
                    }
                    if (_h) {
                        int i7 = Build.VERSION.SDK_INT;
                        float elevation = childAt.getElevation();
                        float f = this.zh;
                        if (elevation != f) {
                            android.support.v4.view.r.b(childAt, f);
                        }
                    }
                    int iW = w(childAt) & 7;
                    if (iW == 3) {
                        i4 = 1;
                    }
                    if ((i4 != 0 && z2) || (i4 == 0 && z3)) {
                        StringBuilder sbE = b.a.a.a.a.e("Child drawer has absolute gravity ");
                        if ((iW & 3) != 3) {
                            hexString = (iW & 5) == 5 ? "RIGHT" : Integer.toHexString(iW);
                        } else {
                            hexString = "LEFT";
                        }
                        sbE.append(hexString);
                        sbE.append(" but this ");
                        sbE.append("DrawerLayout");
                        sbE.append(" already has a ");
                        sbE.append("drawer view along that edge");
                        throw new IllegalStateException(sbE.toString());
                    }
                    if (i4 != 0) {
                        z2 = true;
                    } else {
                        z3 = true;
                    }
                    childAt.measure(ViewGroup.getChildMeasureSpec(i, this.Ah + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(i2, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, ((ViewGroup.MarginLayoutParams) layoutParams).height));
                }
            }
            i3++;
            i4 = 0;
        }
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        View viewV;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        int i = savedState.Le;
        if (i != 0 && (viewV = v(i)) != null) {
            D(viewV);
        }
        int i2 = savedState.Me;
        if (i2 != 3) {
            h(i2, 3);
        }
        int i3 = savedState.Ne;
        if (i3 != 3) {
            h(i3, 5);
        }
        int i4 = savedState.Oe;
        if (i4 != 3) {
            h(i4, 8388611);
        }
        int i5 = savedState.Pe;
        if (i5 != 3) {
            h(i5, 8388613);
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x001f  */
    /* JADX WARN: Code duplicated, block: B:22:0x003d  */
    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        Drawable drawable;
        Drawable drawable2;
        if (_h) {
            return;
        }
        int iF = android.support.v4.view.r.f(this);
        if (iF == 0) {
            Drawable drawable3 = this.Rh;
            if (drawable3 != null) {
                a(drawable3, iF);
                drawable = this.Rh;
            } else {
                drawable = this.Th;
            }
        } else {
            Drawable drawable4 = this.Sh;
            if (drawable4 != null) {
                a(drawable4, iF);
                drawable = this.Sh;
            } else {
                drawable = this.Th;
            }
        }
        this.Nh = drawable;
        int iF2 = android.support.v4.view.r.f(this);
        if (iF2 == 0) {
            Drawable drawable5 = this.Sh;
            if (drawable5 != null) {
                a(drawable5, iF2);
                drawable2 = this.Sh;
            } else {
                drawable2 = this.Uh;
            }
        } else {
            Drawable drawable6 = this.Rh;
            if (drawable6 != null) {
                a(drawable6, iF2);
                drawable2 = this.Rh;
            } else {
                drawable2 = this.Uh;
            }
        }
        this.Oh = drawable2;
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            boolean z = layoutParams.ck == 1;
            boolean z2 = layoutParams.ck == 2;
            if (z || z2) {
                savedState.Le = layoutParams.gravity;
                break;
            }
        }
        savedState.Me = this.Ih;
        savedState.Ne = this.Jh;
        savedState.Oe = this.Kh;
        savedState.Pe = this.Lh;
        return savedState;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0058  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View viewY;
        this.Dh.a(motionEvent);
        this.Eh.a(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action == 1) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                View viewE = this.Dh.e((int) x, (int) y);
                if (viewE != null && z(viewE)) {
                    float f = x - this.Af;
                    float f2 = y - this.Bf;
                    int touchSlop = this.Dh.getTouchSlop();
                    z = (f2 * f2) + (f * f) >= ((float) (touchSlop * touchSlop)) || (viewY = Y()) == null || v(viewY) == 2;
                }
                j(z);
            } else if (action == 3) {
                j(true);
            }
            return true;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        this.Af = x2;
        this.Bf = y2;
        this.Mh = false;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z) {
            j(true);
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mInLayout) {
            return;
        }
        super.requestLayout();
    }

    public void s(View view) {
        a(view, true);
    }

    void t(View view) {
        View rootView;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.ck & 1) == 1) {
            layoutParams.ck = 0;
            List list = this.mListeners;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    ((l) this.mListeners.get(size)).b(view);
                }
            }
            c(view, false);
            if (!hasWindowFocus() || (rootView = getRootView()) == null) {
                return;
            }
            rootView.sendAccessibilityEvent(32);
        }
    }

    void u(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.ck & 1) == 0) {
            layoutParams.ck = 1;
            List list = this.mListeners;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    ((l) this.mListeners.get(size)).a(view);
                }
            }
            c(view, true);
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32);
            }
        }
    }

    public int v(View view) {
        if (B(view)) {
            return w(((LayoutParams) view.getLayoutParams()).gravity);
        }
        throw new IllegalArgumentException(b.a.a.a.a.a("View ", view, " is not a drawer"));
    }

    View v(int i) {
        int absoluteGravity = a.b.a.a.getAbsoluteGravity(i, android.support.v4.view.r.f(this)) & 7;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((w(childAt) & 7) == absoluteGravity) {
                return childAt;
            }
        }
        return null;
    }

    public int w(int i) {
        int iF = android.support.v4.view.r.f(this);
        if (i == 3) {
            int i2 = this.Ih;
            if (i2 != 3) {
                return i2;
            }
            int i3 = iF == 0 ? this.Kh : this.Lh;
            if (i3 != 3) {
                return i3;
            }
            return 0;
        }
        if (i == 5) {
            int i4 = this.Jh;
            if (i4 != 3) {
                return i4;
            }
            int i5 = iF == 0 ? this.Lh : this.Kh;
            if (i5 != 3) {
                return i5;
            }
            return 0;
        }
        if (i == 8388611) {
            int i6 = this.Kh;
            if (i6 != 3) {
                return i6;
            }
            int i7 = iF == 0 ? this.Ih : this.Jh;
            if (i7 != 3) {
                return i7;
            }
            return 0;
        }
        if (i != 8388613) {
            return 0;
        }
        int i8 = this.Lh;
        if (i8 != 3) {
            return i8;
        }
        int i9 = iF == 0 ? this.Jh : this.Ih;
        if (i9 != 3) {
            return i9;
        }
        return 0;
    }

    int w(View view) {
        return a.b.a.a.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, android.support.v4.view.r.f(this));
    }

    float x(View view) {
        return ((LayoutParams) view.getLayoutParams()).ak;
    }

    public CharSequence x(int i) {
        int absoluteGravity = a.b.a.a.getAbsoluteGravity(i, android.support.v4.view.r.f(this));
        if (absoluteGravity == 3) {
            return this.Ph;
        }
        if (absoluteGravity == 5) {
            return this.Qh;
        }
        return null;
    }

    boolean z(View view) {
        return ((LayoutParams) view.getLayoutParams()).gravity == 0;
    }
}
