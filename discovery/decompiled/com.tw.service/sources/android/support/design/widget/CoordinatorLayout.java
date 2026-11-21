package android.support.design.widget;

import a.b.c.b.m;
import a.b.c.b.n;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.D;
import android.support.v4.view.i;
import android.support.v4.view.j;
import android.support.v4.view.r;
import android.support.v4.widget.C0036h;
import android.support.v4.widget.H;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.tw.service.R;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class CoordinatorLayout extends ViewGroup implements android.support.v4.view.g {
    static final String lg;
    static final Class[] mg;
    static final ThreadLocal ng;
    static final Comparator og;
    private static final m pg;
    private final List Wf;
    private final C0036h Xf;
    private final List Yf;
    private final int[] Zf;
    private Paint _f;
    private boolean ag;
    private int[] bg;
    private View cg;
    private View dg;
    private f eg;
    private boolean fg;
    private D gg;
    private boolean hg;
    private Drawable ig;
    private j jg;
    private final i kg;
    private boolean mIsAttachedToWindow;
    ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

    public abstract class Behavior {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public D a(CoordinatorLayout coordinatorLayout, View view, D d) {
            return d;
        }

        public void a(CoordinatorLayout coordinatorLayout, View view, Parcelable parcelable) {
        }

        public void a(CoordinatorLayout coordinatorLayout, View view, View view2, int i) {
            if (i == 0) {
                d(coordinatorLayout, view, view2);
            }
        }

        @Deprecated
        public void a(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4) {
        }

        public void a(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5) {
            if (i5 == 0) {
                a(coordinatorLayout, view, view2, i, i2, i3, i4);
            }
        }

        @Deprecated
        public void a(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr) {
        }

        public void a(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr, int i3) {
            if (i3 == 0) {
                a(coordinatorLayout, view, view2, i, i2, iArr);
            }
        }

        @Deprecated
        public void a(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i) {
        }

        public void a(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
            if (i2 == 0) {
                a(coordinatorLayout, view, view2, view3, i);
            }
        }

        public void a(e eVar) {
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view) {
            return c(coordinatorLayout, view) > 0.0f;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, int i) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, Rect rect) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, View view2, float f, float f2) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, View view2, float f, float f2, boolean z) {
            return false;
        }

        public int b(CoordinatorLayout coordinatorLayout, View view) {
            return -16777216;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return false;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return false;
        }

        @Deprecated
        public boolean b(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i) {
            return false;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
            if (i2 == 0) {
                return b(coordinatorLayout, view, view2, view3, i);
            }
            return false;
        }

        public float c(CoordinatorLayout coordinatorLayout, View view) {
            return 0.0f;
        }

        public void c(CoordinatorLayout coordinatorLayout, View view, View view2) {
        }

        public Parcelable d(CoordinatorLayout coordinatorLayout, View view) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        @Deprecated
        public void d(CoordinatorLayout coordinatorLayout, View view, View view2) {
        }

        public void y() {
        }
    }

    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new g();
        SparseArray Ke;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            int i = parcel.readInt();
            int[] iArr = new int[i];
            parcel.readIntArray(iArr);
            Parcelable[] parcelableArray = parcel.readParcelableArray(classLoader);
            this.Ke = new SparseArray(i);
            for (int i2 = 0; i2 < i; i2++) {
                this.Ke.append(iArr[i2], parcelableArray[i2]);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            SparseArray sparseArray = this.Ke;
            int size = sparseArray != null ? sparseArray.size() : 0;
            parcel.writeInt(size);
            int[] iArr = new int[size];
            Parcelable[] parcelableArr = new Parcelable[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = this.Ke.keyAt(i2);
                parcelableArr[i2] = (Parcelable) this.Ke.valueAt(i2);
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(parcelableArr, i);
        }
    }

    static {
        Package r0 = CoordinatorLayout.class.getPackage();
        lg = r0 != null ? r0.getName() : null;
        int i = Build.VERSION.SDK_INT;
        og = new h();
        mg = new Class[]{Context.class, AttributeSet.class};
        ng = new ThreadLocal();
        pg = new n(12);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.coordinatorLayoutStyle);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.Wf = new ArrayList();
        this.Xf = new C0036h();
        this.Yf = new ArrayList();
        new ArrayList();
        this.Zf = new int[2];
        this.kg = new i(this);
        TypedArray typedArrayObtainStyledAttributes = i == 0 ? context.obtainStyledAttributes(attributeSet, a.b.b.a.Vc, 0, R.style.Widget_Support_CoordinatorLayout) : context.obtainStyledAttributes(attributeSet, a.b.b.a.Vc, i, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(a.b.b.a.Wc, 0);
        if (resourceId != 0) {
            Resources resources = context.getResources();
            this.bg = resources.getIntArray(resourceId);
            float f = resources.getDisplayMetrics().density;
            int length = this.bg.length;
            for (int i2 = 0; i2 < length; i2++) {
                int[] iArr = this.bg;
                iArr[i2] = (int) (iArr[i2] * f);
            }
        }
        this.ig = typedArrayObtainStyledAttributes.getDrawable(1);
        typedArrayObtainStyledAttributes.recycle();
        hb();
        super.setOnHierarchyChangeListener(new d(this));
    }

    private int R(int i) {
        StringBuilder sb;
        int[] iArr = this.bg;
        if (iArr == null) {
            sb = new StringBuilder();
            sb.append("No keylines defined for ");
            sb.append(this);
            sb.append(" - attempted index lookup ");
            sb.append(i);
        } else {
            if (i >= 0 && i < iArr.length) {
                return iArr[i];
            }
            sb = new StringBuilder();
            sb.append("Keyline index ");
            sb.append(i);
            sb.append(" out of range for ");
            sb.append(this);
        }
        Log.e("CoordinatorLayout", sb.toString());
        return 0;
    }

    private static int S(int i) {
        if ((i & 7) == 0) {
            i |= 8388611;
        }
        return (i & 112) == 0 ? i | 48 : i;
    }

    private static int T(int i) {
        if (i == 0) {
            return 8388661;
        }
        return i;
    }

    static Behavior a(Context context, AttributeSet attributeSet, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith(".")) {
            str = context.getPackageName() + str;
        } else if (str.indexOf(46) < 0 && !TextUtils.isEmpty(lg)) {
            str = lg + '.' + str;
        }
        try {
            Map map = (Map) ng.get();
            if (map == null) {
                map = new HashMap();
                ng.set(map);
            }
            Constructor<?> constructor = (Constructor) map.get(str);
            if (constructor == null) {
                constructor = context.getClassLoader().loadClass(str).getConstructor(mg);
                constructor.setAccessible(true);
                map.put(str, constructor);
            }
            return (Behavior) constructor.newInstance(context, attributeSet);
        } catch (Exception e) {
            throw new RuntimeException(b.a.a.a.a.a("Could not inflate Behavior subclass ", str), e);
        }
    }

    private void a(int i, Rect rect, Rect rect2, e eVar, int i2, int i3) {
        int iWidth;
        int iHeight;
        int i4 = eVar.gravity;
        if (i4 == 0) {
            i4 = 17;
        }
        int absoluteGravity = a.b.a.a.getAbsoluteGravity(i4, i);
        int i5 = eVar.Nj;
        if ((i5 & 7) == 0) {
            i5 |= 8388611;
        }
        if ((i5 & 112) == 0) {
            i5 |= 48;
        }
        int absoluteGravity2 = a.b.a.a.getAbsoluteGravity(i5, i);
        int i6 = absoluteGravity & 7;
        int i7 = absoluteGravity & 112;
        int i8 = absoluteGravity2 & 7;
        int i9 = absoluteGravity2 & 112;
        if (i8 != 1) {
            iWidth = i8 != 5 ? rect.left : rect.right;
        } else {
            iWidth = rect.left + (rect.width() / 2);
        }
        if (i9 != 16) {
            iHeight = i9 != 80 ? rect.top : rect.bottom;
        } else {
            iHeight = rect.top + (rect.height() / 2);
        }
        if (i6 == 1) {
            iWidth -= i2 / 2;
        } else if (i6 != 5) {
            iWidth -= i2;
        }
        if (i7 == 16) {
            iHeight -= i3 / 2;
        } else if (i7 != 80) {
            iHeight -= i3;
        }
        rect2.set(iWidth, iHeight, i2 + iWidth, i3 + iHeight);
    }

    private static void a(Rect rect) {
        rect.setEmpty();
        pg.release(rect);
    }

    private void a(e eVar, Rect rect, int i, int i2) {
        int width = getWidth();
        int height = getHeight();
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) eVar).leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i) - ((ViewGroup.MarginLayoutParams) eVar).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) eVar).topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i2) - ((ViewGroup.MarginLayoutParams) eVar).bottomMargin));
        rect.set(iMax, iMax2, i + iMax, i2 + iMax2);
    }

    private void c(List list) {
        list.clear();
        boolean zIsChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            list.add(getChildAt(zIsChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i) : i));
        }
        Comparator comparator = og;
        if (comparator != null) {
            Collections.sort(list, comparator);
        }
    }

    private static Rect fb() {
        Rect rect = (Rect) pg.acquire();
        return rect == null ? new Rect() : rect;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0058  */
    /* JADX WARN: Code duplicated, block: B:27:0x0064 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:28:0x0066  */
    /* JADX WARN: Code duplicated, block: B:33:0x0075  */
    /* JADX WARN: Code duplicated, block: B:83:0x006d A[SYNTHETIC] */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:34:0x0079
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:162)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private void gb() {
        /*
            Method dump skipped, instruction units count: 315
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.gb():void");
    }

    private void hb() {
        int i = Build.VERSION.SDK_INT;
        if (!r.d(this)) {
            r.a(this, (j) null);
            return;
        }
        if (this.jg == null) {
            this.jg = new a(this);
        }
        r.a(this, this.jg);
        setSystemUiVisibility(1280);
    }

    private void l(View view, int i) {
        e eVar = (e) view.getLayoutParams();
        int i2 = eVar.Sj;
        if (i2 != i) {
            r.b(view, i - i2);
            eVar.Sj = i;
        }
    }

    private void m(View view, int i) {
        e eVar = (e) view.getLayoutParams();
        int i2 = eVar.Tj;
        if (i2 != i) {
            r.c(view, i - i2);
            eVar.Tj = i;
        }
    }

    private void v(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            Behavior behavior = ((e) childAt.getLayoutParams()).Lj;
            if (behavior != null) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                if (z) {
                    behavior.a(this, childAt, motionEventObtain);
                } else {
                    behavior.b(this, childAt, motionEventObtain);
                }
                motionEventObtain.recycle();
            }
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            ((e) getChildAt(i2).getLayoutParams()).ka();
        }
        this.cg = null;
        this.ag = false;
    }

    void R() {
        if (this.mIsAttachedToWindow) {
            if (this.eg == null) {
                this.eg = new f(this);
            }
            getViewTreeObserver().addOnPreDrawListener(this.eg);
        }
        this.fg = true;
    }

    void S() {
        int childCount = getChildCount();
        boolean z = false;
        for (int i = 0; i < childCount; i++) {
            if (this.Xf.l(getChildAt(i))) {
                z = true;
                break;
            }
        }
        if (z != this.fg) {
            if (z) {
                R();
            } else {
                T();
            }
        }
    }

    void T() {
        if (this.mIsAttachedToWindow && this.eg != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.eg);
        }
        this.fg = false;
    }

    @Override // android.support.v4.view.g
    public void a(View view, int i, int i2, int[] iArr, int i3) {
        Behavior behavior;
        int childCount = getChildCount();
        boolean z = false;
        int i4 = 0;
        int iMax = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                e eVar = (e) childAt.getLayoutParams();
                if (eVar.A(i3) && (behavior = eVar.Lj) != null) {
                    int[] iArr2 = this.Zf;
                    iArr2[1] = 0;
                    iArr2[0] = 0;
                    behavior.a(this, childAt, view, i, i2, iArr2, i3);
                    int[] iArr3 = this.Zf;
                    int iMax2 = i > 0 ? Math.max(i4, iArr3[0]) : Math.min(i4, iArr3[0]);
                    int[] iArr4 = this.Zf;
                    i4 = iMax2;
                    iMax = i2 > 0 ? Math.max(iMax, iArr4[1]) : Math.min(iMax, iArr4[1]);
                    z = true;
                }
            }
        }
        iArr[0] = i4;
        iArr[1] = iMax;
        if (z) {
            t(1);
        }
    }

    void a(View view, int i, Rect rect, Rect rect2) {
        e eVar = (e) view.getLayoutParams();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        a(i, rect, rect2, eVar, measuredWidth, measuredHeight);
        a(eVar, rect2, measuredWidth, measuredHeight);
    }

    void a(View view, boolean z, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
        } else if (z) {
            b(view, rect);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    public void b(View view, int i, int i2, int i3, int i4) {
        measureChildWithMargins(view, i, i2, i3, i4);
    }

    void b(View view, Rect rect) {
        H.a(this, view, rect);
    }

    @Override // android.support.v4.view.g
    public boolean b(View view, View view2, int i, int i2) {
        int childCount = getChildCount();
        boolean z = false;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                e eVar = (e) childAt.getLayoutParams();
                Behavior behavior = eVar.Lj;
                if (behavior != null) {
                    boolean zB = behavior.b(this, childAt, view, view2, i, i2);
                    eVar.a(i2, zB);
                    z |= zB;
                } else {
                    eVar.a(i2, false);
                }
            }
        }
        return z;
    }

    void c(View view, Rect rect) {
        rect.set(((e) view.getLayoutParams())._j);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof e) && super.checkLayoutParams(layoutParams);
    }

    void d(View view, Rect rect) {
        ((e) view.getLayoutParams())._j.set(rect);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        e eVar = (e) view.getLayoutParams();
        Behavior behavior = eVar.Lj;
        if (behavior != null) {
            float fC = behavior.c(this, view);
            if (fC > 0.0f) {
                if (this._f == null) {
                    this._f = new Paint();
                }
                this._f.setColor(eVar.Lj.b(this, view));
                Paint paint = this._f;
                int iRound = Math.round(fC * 255.0f);
                if (iRound < 0) {
                    iRound = 0;
                } else if (iRound > 255) {
                    iRound = 255;
                }
                paint.setAlpha(iRound);
                int iSave = canvas.save();
                if (view.isOpaque()) {
                    canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), Region.Op.DIFFERENCE);
                }
                canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), this._f);
                canvas.restoreToCount(iSave);
            }
        }
        return super.drawChild(canvas, view, j);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.ig;
        boolean state = false;
        if (drawable != null && drawable.isStateful()) {
            state = false | drawable.setState(drawableState);
        }
        if (state) {
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public e generateDefaultLayoutParams() {
        return new e(-2, -2);
    }

    @Override // android.view.ViewGroup
    public e generateLayoutParams(AttributeSet attributeSet) {
        return new e(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public e generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof e) {
            return new e((e) layoutParams);
        }
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new e((ViewGroup.MarginLayoutParams) layoutParams) : new e(layoutParams);
    }

    final List getDependencySortedChildren() {
        gb();
        return Collections.unmodifiableList(this.Wf);
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.kg.getNestedScrollAxes();
    }

    @Override // android.view.View
    protected int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingBottom() + getPaddingTop());
    }

    @Override // android.view.View
    protected int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingRight() + getPaddingLeft());
    }

    void i(View view, int i) {
        Behavior behavior;
        e eVar = (e) view.getLayoutParams();
        if (eVar.Uj != null) {
            Rect rectFb = fb();
            Rect rectFb2 = fb();
            Rect rectFb3 = fb();
            b(eVar.Uj, rectFb);
            a(view, false, rectFb2);
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            a(i, rectFb, rectFb3, eVar, measuredWidth, measuredHeight);
            boolean z = (rectFb3.left == rectFb2.left && rectFb3.top == rectFb2.top) ? false : true;
            a(eVar, rectFb3, measuredWidth, measuredHeight);
            int i2 = rectFb3.left - rectFb2.left;
            int i3 = rectFb3.top - rectFb2.top;
            if (i2 != 0) {
                r.b(view, i2);
            }
            if (i3 != 0) {
                r.c(view, i3);
            }
            if (z && (behavior = eVar.Lj) != null) {
                behavior.b(this, view, eVar.Uj);
            }
            rectFb.setEmpty();
            pg.release(rectFb);
            rectFb2.setEmpty();
            pg.release(rectFb2);
            rectFb3.setEmpty();
            pg.release(rectFb3);
        }
    }

    public void j(View view, int i) {
        e eVar = (e) view.getLayoutParams();
        int i2 = 0;
        if (eVar.Uj == null && eVar.Pj != -1) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        }
        View view2 = eVar.Uj;
        if (view2 != null) {
            Rect rectFb = fb();
            Rect rectFb2 = fb();
            try {
                b(view2, rectFb);
                a(view, i, rectFb, rectFb2);
                view.layout(rectFb2.left, rectFb2.top, rectFb2.right, rectFb2.bottom);
                a(rectFb);
                rectFb2.setEmpty();
                pg.release(rectFb2);
                return;
            } catch (Throwable th) {
                a(rectFb);
                a(rectFb2);
                throw th;
            }
        }
        int i3 = eVar.Oj;
        if (i3 < 0) {
            e eVar2 = (e) view.getLayoutParams();
            Rect rectFb3 = fb();
            rectFb3.set(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) eVar2).leftMargin, getPaddingTop() + ((ViewGroup.MarginLayoutParams) eVar2).topMargin, (getWidth() - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) eVar2).rightMargin, (getHeight() - getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) eVar2).bottomMargin);
            if (this.gg != null && r.d(this) && !r.d(view)) {
                rectFb3.left = this.gg.getSystemWindowInsetLeft() + rectFb3.left;
                rectFb3.top = this.gg.getSystemWindowInsetTop() + rectFb3.top;
                rectFb3.right -= this.gg.getSystemWindowInsetRight();
                rectFb3.bottom -= this.gg.getSystemWindowInsetBottom();
            }
            Rect rectFb4 = fb();
            int iS = S(eVar2.gravity);
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            int i4 = Build.VERSION.SDK_INT;
            Gravity.apply(iS, measuredWidth, measuredHeight, rectFb3, rectFb4, i);
            view.layout(rectFb4.left, rectFb4.top, rectFb4.right, rectFb4.bottom);
            rectFb3.setEmpty();
            pg.release(rectFb3);
            rectFb4.setEmpty();
            pg.release(rectFb4);
            return;
        }
        e eVar3 = (e) view.getLayoutParams();
        int absoluteGravity = a.b.a.a.getAbsoluteGravity(T(eVar3.gravity), i);
        int i5 = absoluteGravity & 7;
        int i6 = absoluteGravity & 112;
        int width = getWidth();
        int height = getHeight();
        int measuredWidth2 = view.getMeasuredWidth();
        int measuredHeight2 = view.getMeasuredHeight();
        if (i == 1) {
            i3 = width - i3;
        }
        int iR = R(i3) - measuredWidth2;
        if (i5 == 1) {
            iR += measuredWidth2 / 2;
        } else if (i5 == 5) {
            iR += measuredWidth2;
        }
        if (i6 == 16) {
            i2 = 0 + (measuredHeight2 / 2);
        } else if (i6 == 80) {
            i2 = measuredHeight2 + 0;
        }
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) eVar3).leftMargin, Math.min(iR, ((width - getPaddingRight()) - measuredWidth2) - ((ViewGroup.MarginLayoutParams) eVar3).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) eVar3).topMargin, Math.min(i2, ((height - getPaddingBottom()) - measuredHeight2) - ((ViewGroup.MarginLayoutParams) eVar3).bottomMargin));
        view.layout(iMax, iMax2, measuredWidth2 + iMax, measuredHeight2 + iMax2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        v(false);
        if (this.fg) {
            if (this.eg == null) {
                this.eg = new f(this);
            }
            getViewTreeObserver().addOnPreDrawListener(this.eg);
        }
        if (this.gg == null && r.d(this)) {
            int i = Build.VERSION.SDK_INT;
            requestApplyInsets();
        }
        this.mIsAttachedToWindow = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        v(false);
        if (this.fg && this.eg != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.eg);
        }
        View view = this.dg;
        if (view != null) {
            onStopNestedScroll(view);
        }
        this.mIsAttachedToWindow = false;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.hg || this.ig == null) {
            return;
        }
        D d = this.gg;
        int systemWindowInsetTop = d != null ? d.getSystemWindowInsetTop() : 0;
        if (systemWindowInsetTop > 0) {
            this.ig.setBounds(0, 0, getWidth(), systemWindowInsetTop);
            this.ig.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            v(true);
        }
        boolean zA = a(motionEvent, 0);
        if (actionMasked == 1 || actionMasked == 3) {
            v(true);
        }
        return zA;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Behavior behavior;
        int iF = r.f(this);
        int size = this.Wf.size();
        for (int i5 = 0; i5 < size; i5++) {
            View view = (View) this.Wf.get(i5);
            if (view.getVisibility() != 8 && ((behavior = ((e) view.getLayoutParams()).Lj) == null || !behavior.a(this, view, iF))) {
                j(view, iF);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:43:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:46:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:49:0x011b  */
    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int iMax;
        int i4;
        int i5;
        Behavior behavior;
        e eVar;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        gb();
        S();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int iF = r.f(this);
        boolean z = iF == 1;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        int i11 = paddingLeft + paddingRight;
        int i12 = paddingTop + paddingBottom;
        int suggestedMinimumWidth = getSuggestedMinimumWidth();
        int suggestedMinimumHeight = getSuggestedMinimumHeight();
        boolean z2 = this.gg != null && r.d(this);
        int size3 = this.Wf.size();
        int i13 = suggestedMinimumWidth;
        int iMax2 = suggestedMinimumHeight;
        int iCombineMeasuredStates = 0;
        int i14 = 0;
        while (i14 < size3) {
            View view = (View) this.Wf.get(i14);
            if (view.getVisibility() == 8) {
                i10 = i14;
                i8 = size3;
            } else {
                e eVar2 = (e) view.getLayoutParams();
                int i15 = eVar2.Oj;
                if (i15 < 0 || mode == 0) {
                    i3 = i13;
                } else {
                    int iR = R(i15);
                    int i16 = eVar2.gravity;
                    if (i16 == 0) {
                        i16 = 8388661;
                    }
                    int absoluteGravity = a.b.a.a.getAbsoluteGravity(i16, iF) & 7;
                    i3 = i13;
                    if ((absoluteGravity == 3 && !z) || (absoluteGravity == 5 && z)) {
                        iMax = Math.max(0, (size - paddingRight) - iR);
                    } else if ((absoluteGravity == 5 && !z) || (absoluteGravity == 3 && z)) {
                        iMax = Math.max(0, iR - paddingLeft);
                    }
                    if (z2 || r.d(view)) {
                        i4 = i;
                        i5 = i2;
                    } else {
                        int systemWindowInsetRight = this.gg.getSystemWindowInsetRight() + this.gg.getSystemWindowInsetLeft();
                        int systemWindowInsetBottom = this.gg.getSystemWindowInsetBottom() + this.gg.getSystemWindowInsetTop();
                        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size - systemWindowInsetRight, mode);
                        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2 - systemWindowInsetBottom, mode2);
                        i4 = iMakeMeasureSpec;
                        i5 = iMakeMeasureSpec2;
                    }
                    behavior = eVar2.Lj;
                    if (behavior != null) {
                        eVar = eVar2;
                        i9 = i3;
                        i6 = iCombineMeasuredStates;
                        i7 = iMax2;
                        i10 = i14;
                        i8 = size3;
                        if (!behavior.a(this, view, i4, iMax, i5, 0)) {
                        }
                        e eVar3 = eVar;
                        int iMax3 = Math.max(i9, view.getMeasuredWidth() + i11 + ((ViewGroup.MarginLayoutParams) eVar3).leftMargin + ((ViewGroup.MarginLayoutParams) eVar3).rightMargin);
                        iMax2 = Math.max(i7, view.getMeasuredHeight() + i12 + ((ViewGroup.MarginLayoutParams) eVar3).topMargin + ((ViewGroup.MarginLayoutParams) eVar3).bottomMargin);
                        iCombineMeasuredStates = View.combineMeasuredStates(i6, view.getMeasuredState());
                        i13 = iMax3;
                    } else {
                        eVar = eVar2;
                        i6 = iCombineMeasuredStates;
                        i7 = iMax2;
                        i8 = size3;
                        i9 = i3;
                        i10 = i14;
                    }
                    b(view, i4, iMax, i5, 0);
                    e eVar4 = eVar;
                    int iMax4 = Math.max(i9, view.getMeasuredWidth() + i11 + ((ViewGroup.MarginLayoutParams) eVar4).leftMargin + ((ViewGroup.MarginLayoutParams) eVar4).rightMargin);
                    iMax2 = Math.max(i7, view.getMeasuredHeight() + i12 + ((ViewGroup.MarginLayoutParams) eVar4).topMargin + ((ViewGroup.MarginLayoutParams) eVar4).bottomMargin);
                    iCombineMeasuredStates = View.combineMeasuredStates(i6, view.getMeasuredState());
                    i13 = iMax4;
                }
                iMax = 0;
                if (z2) {
                    i4 = i;
                    i5 = i2;
                } else {
                    i4 = i;
                    i5 = i2;
                }
                behavior = eVar2.Lj;
                if (behavior != null) {
                    eVar = eVar2;
                    i9 = i3;
                    i6 = iCombineMeasuredStates;
                    i7 = iMax2;
                    i10 = i14;
                    i8 = size3;
                    if (!behavior.a(this, view, i4, iMax, i5, 0)) {
                    }
                    e eVar5 = eVar;
                    int iMax5 = Math.max(i9, view.getMeasuredWidth() + i11 + ((ViewGroup.MarginLayoutParams) eVar5).leftMargin + ((ViewGroup.MarginLayoutParams) eVar5).rightMargin);
                    iMax2 = Math.max(i7, view.getMeasuredHeight() + i12 + ((ViewGroup.MarginLayoutParams) eVar5).topMargin + ((ViewGroup.MarginLayoutParams) eVar5).bottomMargin);
                    iCombineMeasuredStates = View.combineMeasuredStates(i6, view.getMeasuredState());
                    i13 = iMax5;
                } else {
                    eVar = eVar2;
                    i6 = iCombineMeasuredStates;
                    i7 = iMax2;
                    i8 = size3;
                    i9 = i3;
                    i10 = i14;
                }
                b(view, i4, iMax, i5, 0);
                e eVar6 = eVar;
                int iMax6 = Math.max(i9, view.getMeasuredWidth() + i11 + ((ViewGroup.MarginLayoutParams) eVar6).leftMargin + ((ViewGroup.MarginLayoutParams) eVar6).rightMargin);
                iMax2 = Math.max(i7, view.getMeasuredHeight() + i12 + ((ViewGroup.MarginLayoutParams) eVar6).topMargin + ((ViewGroup.MarginLayoutParams) eVar6).bottomMargin);
                iCombineMeasuredStates = View.combineMeasuredStates(i6, view.getMeasuredState());
                i13 = iMax6;
            }
            i14 = i10 + 1;
            size3 = i8;
        }
        int i17 = iCombineMeasuredStates;
        setMeasuredDimension(View.resolveSizeAndState(i13, i, (-16777216) & i17), View.resolveSizeAndState(iMax2, i2, i17 << 16));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        Behavior behavior;
        int childCount = getChildCount();
        boolean zA = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                e eVar = (e) childAt.getLayoutParams();
                if (eVar.A(0) && (behavior = eVar.Lj) != null) {
                    zA |= behavior.a(this, childAt, view, f, f2, z);
                }
            }
        }
        if (zA) {
            t(1);
        }
        return zA;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        Behavior behavior;
        int childCount = getChildCount();
        boolean zA = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                e eVar = (e) childAt.getLayoutParams();
                if (eVar.A(0) && (behavior = eVar.Lj) != null) {
                    zA |= behavior.a(this, childAt, view, f, f2);
                }
            }
        }
        return zA;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        a(view, i, i2, iArr, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        a(view, i, i2, i3, i4, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        a(view, view2, i, 0);
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        SparseArray sparseArray = savedState.Ke;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int id = childAt.getId();
            Behavior behavior = r(childAt).Lj;
            if (id != -1 && behavior != null && (parcelable2 = (Parcelable) sparseArray.get(id)) != null) {
                behavior.a(this, childAt, parcelable2);
            }
        }
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelableD;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int id = childAt.getId();
            Behavior behavior = ((e) childAt.getLayoutParams()).Lj;
            if (id != -1 && behavior != null && (parcelableD = behavior.d(this, childAt)) != null) {
                sparseArray.append(id, parcelableD);
            }
        }
        savedState.Ke = sparseArray;
        return savedState;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return b(view, view2, i, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onStopNestedScroll(View view) {
        a(view, 0);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0029 A[PHI: r3
      0x0029: PHI (r3v4 boolean) = (r3v2 boolean), (r3v5 boolean) binds: [B:9:0x0020, B:5:0x0012] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:14:0x002f  */
    /* JADX WARN: Code duplicated, block: B:15:0x0035 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:16:0x0037  */
    /* JADX WARN: Code duplicated, block: B:18:0x004a  */
    /* JADX WARN: Code duplicated, block: B:22:0x0052  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zA;
        boolean zB;
        MotionEvent motionEventObtain;
        int actionMasked = motionEvent.getActionMasked();
        if (this.cg == null) {
            zA = a(motionEvent, 1);
            if (!zA) {
                zB = false;
            }
            motionEventObtain = null;
            if (this.cg == null) {
                zB |= super.onTouchEvent(motionEvent);
            } else if (zA) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                super.onTouchEvent(motionEventObtain);
            }
            if (motionEventObtain != null) {
                motionEventObtain.recycle();
            }
            if (actionMasked != 1 || actionMasked == 3) {
                v(false);
            }
            return zB;
        }
        zA = false;
        Behavior behavior = ((e) this.cg.getLayoutParams()).Lj;
        if (behavior != null) {
            zB = behavior.b(this, this.cg, motionEvent);
        } else {
            zB = false;
        }
        motionEventObtain = null;
        if (this.cg == null) {
            zB |= super.onTouchEvent(motionEvent);
        } else if (zA) {
            long jUptimeMillis2 = SystemClock.uptimeMillis();
            motionEventObtain = MotionEvent.obtain(jUptimeMillis2, jUptimeMillis2, 3, 0.0f, 0.0f, 0);
            super.onTouchEvent(motionEventObtain);
        }
        if (motionEventObtain != null) {
            motionEventObtain.recycle();
        }
        if (actionMasked != 1) {
            v(false);
        } else {
            v(false);
        }
        return zB;
    }

    /* JADX WARN: Multi-variable type inference failed */
    e r(View view) {
        e eVar = (e) view.getLayoutParams();
        if (!eVar.Mj) {
            if (view instanceof b) {
                Behavior behaviorB = ((b) view).b();
                if (behaviorB == null) {
                    Log.e("CoordinatorLayout", "Attached behavior class is null");
                }
                Behavior behavior = eVar.Lj;
                if (behavior != behaviorB) {
                    if (behavior != null) {
                        behavior.y();
                    }
                    eVar.Lj = behaviorB;
                    eVar.Mj = true;
                    if (behaviorB != null) {
                        behaviorB.a(eVar);
                    }
                }
            } else {
                c cVar = null;
                for (Class<?> superclass = view.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                    cVar = (c) superclass.getAnnotation(c.class);
                    if (cVar != null) {
                        break;
                    }
                }
                if (cVar != null) {
                    try {
                        Behavior behavior2 = (Behavior) cVar.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                        Behavior behavior3 = eVar.Lj;
                        if (behavior3 != behavior2) {
                            if (behavior3 != null) {
                                behavior3.y();
                            }
                            eVar.Lj = behavior2;
                            eVar.Mj = true;
                            if (behavior2 != null) {
                                behavior2.a(eVar);
                            }
                        }
                    } catch (Exception e) {
                        StringBuilder sbE = b.a.a.a.a.e("Default behavior class ");
                        sbE.append(cVar.value().getName());
                        sbE.append(" could not be instantiated. Did you forget");
                        sbE.append(" a default constructor?");
                        Log.e("CoordinatorLayout", sbE.toString(), e);
                    }
                }
            }
            eVar.Mj = true;
        }
        return eVar;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        Behavior behavior = ((e) view.getLayoutParams()).Lj;
        if (behavior == null || !behavior.a(this, view, rect, z)) {
            return super.requestChildRectangleOnScreen(view, rect, z);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (!z || this.ag) {
            return;
        }
        v(false);
        this.ag = true;
    }

    @Override // android.view.View
    public void setFitsSystemWindows(boolean z) {
        super.setFitsSystemWindows(z);
        hb();
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.ig;
        if (drawable == null || drawable.isVisible() == z) {
            return;
        }
        this.ig.setVisible(z, false);
    }

    /* JADX WARN: Code duplicated, block: B:93:0x01d1  */
    final void t(int i) {
        int i2;
        int i3;
        Behavior behavior;
        boolean zB;
        boolean z;
        boolean z2;
        int width;
        int i4;
        int i5;
        int i6;
        int height;
        int i7;
        int i8;
        int i9;
        int iF = r.f(this);
        int size = this.Wf.size();
        Rect rectFb = fb();
        Rect rectFb2 = fb();
        Rect rectFb3 = fb();
        int i10 = 0;
        while (i10 < size) {
            View view = (View) this.Wf.get(i10);
            e eVar = (e) view.getLayoutParams();
            if (i == 0 && view.getVisibility() == 8) {
                i3 = size;
            } else {
                for (int i11 = 0; i11 < i10; i11++) {
                    if (eVar.Vj == ((View) this.Wf.get(i11))) {
                        i(view, iF);
                    }
                }
                a(view, true, rectFb2);
                if (eVar.Qj != 0 && !rectFb2.isEmpty()) {
                    int absoluteGravity = a.b.a.a.getAbsoluteGravity(eVar.Qj, iF);
                    int i12 = absoluteGravity & 112;
                    if (i12 == 48) {
                        rectFb.top = Math.max(rectFb.top, rectFb2.bottom);
                    } else if (i12 == 80) {
                        rectFb.bottom = Math.max(rectFb.bottom, getHeight() - rectFb2.top);
                    }
                    int i13 = absoluteGravity & 7;
                    if (i13 == 3) {
                        rectFb.left = Math.max(rectFb.left, rectFb2.right);
                    } else if (i13 == 5) {
                        rectFb.right = Math.max(rectFb.right, getWidth() - rectFb2.left);
                    }
                }
                if (eVar.Rj != 0 && view.getVisibility() == 0 && r.l(view) && view.getWidth() > 0 && view.getHeight() > 0) {
                    e eVar2 = (e) view.getLayoutParams();
                    Behavior behavior2 = eVar2.Lj;
                    Rect rectFb4 = fb();
                    Rect rectFb5 = fb();
                    rectFb5.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                    if (behavior2 == null || !behavior2.a(this, view, rectFb4)) {
                        rectFb4.set(rectFb5);
                    } else if (!rectFb5.contains(rectFb4)) {
                        StringBuilder sbE = b.a.a.a.a.e("Rect should be within the child's bounds. Rect:");
                        sbE.append(rectFb4.toShortString());
                        sbE.append(" | Bounds:");
                        sbE.append(rectFb5.toShortString());
                        throw new IllegalArgumentException(sbE.toString());
                    }
                    rectFb5.setEmpty();
                    pg.release(rectFb5);
                    if (!rectFb4.isEmpty()) {
                        int absoluteGravity2 = a.b.a.a.getAbsoluteGravity(eVar2.Rj, iF);
                        if ((absoluteGravity2 & 48) != 48 || (i8 = (rectFb4.top - ((ViewGroup.MarginLayoutParams) eVar2).topMargin) - eVar2.Tj) >= (i9 = rectFb.top)) {
                            z = false;
                        } else {
                            m(view, i9 - i8);
                            z = true;
                        }
                        if ((absoluteGravity2 & 80) == 80 && (height = ((getHeight() - rectFb4.bottom) - ((ViewGroup.MarginLayoutParams) eVar2).bottomMargin) + eVar2.Tj) < (i7 = rectFb.bottom)) {
                            m(view, height - i7);
                            z = true;
                        }
                        if (!z) {
                            m(view, 0);
                        }
                        if ((absoluteGravity2 & 3) != 3 || (i5 = (rectFb4.left - ((ViewGroup.MarginLayoutParams) eVar2).leftMargin) - eVar2.Sj) >= (i6 = rectFb.left)) {
                            z2 = false;
                        } else {
                            l(view, i6 - i5);
                            z2 = true;
                        }
                        if ((absoluteGravity2 & 5) == 5 && (width = ((getWidth() - rectFb4.right) - ((ViewGroup.MarginLayoutParams) eVar2).rightMargin) + eVar2.Sj) < (i4 = rectFb.right)) {
                            l(view, width - i4);
                            z2 = true;
                        }
                        if (!z2) {
                            l(view, 0);
                        }
                    }
                    rectFb4.setEmpty();
                    pg.release(rectFb4);
                }
                if (i != 2) {
                    c(view, rectFb3);
                    if (rectFb3.equals(rectFb2)) {
                        i3 = size;
                    } else {
                        d(view, rectFb2);
                        i3 = size;
                        for (i2 = i10 + 1; i2 < i3; i2++) {
                            View view2 = (View) this.Wf.get(i2);
                            e eVar3 = (e) view2.getLayoutParams();
                            behavior = eVar3.Lj;
                            if (behavior == null && behavior.a(this, view2, view)) {
                                if (i == 0 && eVar3.ia()) {
                                    eVar3.ja();
                                } else {
                                    if (i != 2) {
                                        zB = behavior.b(this, view2, view);
                                    } else {
                                        behavior.c(this, view2, view);
                                        zB = true;
                                    }
                                    if (i == 1) {
                                        eVar3.k(zB);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    i3 = size;
                    while (i2 < i3) {
                        View view3 = (View) this.Wf.get(i2);
                        e eVar4 = (e) view3.getLayoutParams();
                        behavior = eVar4.Lj;
                        if (behavior == null) {
                        }
                    }
                }
            }
            i10++;
            size = i3;
        }
        a(rectFb);
        a(rectFb2);
        a(rectFb3);
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.ig;
    }

    @Override // android.support.v4.view.g
    public void a(View view, int i, int i2, int i3, int i4, int i5) {
        Behavior behavior;
        int childCount = getChildCount();
        boolean z = false;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                e eVar = (e) childAt.getLayoutParams();
                if (eVar.A(i5) && (behavior = eVar.Lj) != null) {
                    behavior.a(this, childAt, view, i, i2, i3, i4, i5);
                    z = true;
                }
            }
        }
        if (z) {
            t(1);
        }
    }

    final D b(D d) {
        Behavior behavior;
        D d2 = this.gg;
        int i = Build.VERSION.SDK_INT;
        if (!Objects.equals(d2, d)) {
            this.gg = d;
            this.hg = d != null && d.getSystemWindowInsetTop() > 0;
            setWillNotDraw(!this.hg && getBackground() == null);
            if (!d.isConsumed()) {
                int childCount = getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = getChildAt(i2);
                    if (r.d(childAt) && (behavior = ((e) childAt.getLayoutParams()).Lj) != null) {
                        d = behavior.a(this, childAt, d);
                        if (d.isConsumed()) {
                            break;
                        }
                    }
                }
            }
            requestLayout();
        }
        return d;
    }

    @Override // android.support.v4.view.g
    public void a(View view, View view2, int i, int i2) {
        Behavior behavior;
        this.kg.a(view, view2, i, i2);
        this.dg = view2;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            e eVar = (e) childAt.getLayoutParams();
            if (eVar.A(i2) && (behavior = eVar.Lj) != null) {
                behavior.a(this, childAt, view, view2, i, i2);
            }
        }
    }

    @Override // android.support.v4.view.g
    public void a(View view, int i) {
        this.kg.a(view, i);
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            e eVar = (e) childAt.getLayoutParams();
            if (eVar.A(i)) {
                Behavior behavior = eVar.Lj;
                if (behavior != null) {
                    behavior.a(this, childAt, view, i);
                }
                eVar.a(i, false);
                eVar.ja();
            }
        }
        this.dg = null;
    }

    private boolean a(MotionEvent motionEvent, int i) {
        int actionMasked = motionEvent.getActionMasked();
        List list = this.Yf;
        c(list);
        int size = list.size();
        boolean zA = false;
        boolean z = false;
        MotionEvent motionEventObtain = null;
        for (int i2 = 0; i2 < size; i2++) {
            View view = (View) list.get(i2);
            e eVar = (e) view.getLayoutParams();
            Behavior behavior = eVar.Lj;
            if (!(zA || z) || actionMasked == 0) {
                if (!zA && behavior != null) {
                    if (i == 0) {
                        zA = behavior.a(this, view, motionEvent);
                    } else if (i == 1) {
                        zA = behavior.b(this, view, motionEvent);
                    }
                    if (zA) {
                        this.cg = view;
                    }
                }
                boolean zHa = eVar.ha();
                boolean zE = eVar.e(this, view);
                boolean z2 = zE && !zHa;
                if (zE && !z2) {
                    break;
                }
                z = z2;
            } else if (behavior != null) {
                if (motionEventObtain == null) {
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                }
                if (i == 0) {
                    behavior.a(this, view, motionEventObtain);
                } else if (i == 1) {
                    behavior.b(this, view, motionEventObtain);
                }
            }
        }
        list.clear();
        return zA;
    }
}
