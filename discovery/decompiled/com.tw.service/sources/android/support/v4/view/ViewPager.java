package android.support.v4.view;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ViewPager extends ViewGroup {
    private static final Interpolator Qf;
    static final int[] wh = {R.attr.layout_gravity};
    private static final C xh;
    private float Af;
    private float Bf;
    private float Cf;
    private float Df;
    private int Pg;
    private final x Qg;
    int Rg;
    private boolean Sg;
    private int Tg;
    private Drawable Ug;
    private int Vg;
    private int Wg;
    private float Xg;
    private float Yg;
    private int Zg;
    private boolean _g;
    private int ah;
    private boolean bh;
    private boolean ch;
    private int dh;
    private int eh;
    private int fh;
    private int gh;
    private int hh;
    private int ih;
    private boolean jh;
    private EdgeEffect kh;
    private EdgeEffect lh;
    private int mActivePointerId;
    k mAdapter;
    private boolean mInLayout;
    private final ArrayList mItems;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private final Rect mTempRect;
    private VelocityTracker mVelocityTracker;
    private boolean mh;
    private boolean nh;
    private int oh;
    private List ph;
    private A qh;
    private A rh;
    private List sh;
    private int th;
    private ArrayList uh;
    private final Runnable vh;
    private int zc;
    private int zf;

    public class LayoutParams extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor;

        public LayoutParams() {
            super(-1, -1);
            this.widthFactor = 0.0f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.widthFactor = 0.0f;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.wh);
            this.gravity = typedArrayObtainStyledAttributes.getInteger(0, 48);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new B();
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? SavedState.class.getClassLoader() : classLoader;
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder sbE = b.a.a.a.a.e("FragmentPager.SavedState{");
            sbE.append(Integer.toHexString(System.identityHashCode(this)));
            sbE.append(" position=");
            sbE.append(this.position);
            sbE.append("}");
            return sbE.toString();
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i);
        }
    }

    static {
        new s();
        Qf = new t();
        xh = new C();
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mItems = new ArrayList();
        this.Qg = new x();
        this.mTempRect = new Rect();
        this.Xg = -3.4028235E38f;
        this.Yg = Float.MAX_VALUE;
        this.ah = 1;
        this.mActivePointerId = -1;
        this.mh = true;
        this.vh = new u(this);
        this.zc = 0;
        V();
    }

    private void U(int i) {
        A a2 = this.qh;
        if (a2 != null) {
            ((n) a2).onPageSelected(i);
        }
        List list = this.ph;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                A a3 = (A) this.ph.get(i2);
                if (a3 != null) {
                    ((n) a3).onPageSelected(i);
                }
            }
        }
        A a4 = this.rh;
        if (a4 != null) {
            ((n) a4).onPageSelected(i);
        }
    }

    private boolean V(int i) {
        if (this.mItems.size() == 0) {
            if (this.mh) {
                return false;
            }
            this.nh = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.nh) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        x xVarJb = jb();
        int iIb = ib();
        int i2 = this.Tg;
        int i3 = iIb + i2;
        float f = iIb;
        int i4 = xVarJb.position;
        float f2 = ((i / f) - xVarJb.offset) / (xVarJb.widthFactor + (i2 / f));
        this.nh = false;
        onPageScrolled(i4, f2, (int) (i3 * f2));
        if (this.nh) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    private Rect a(Rect rect, View view) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        ViewParent parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent;
            rect.left = viewGroup.getLeft() + rect.left;
            rect.right = viewGroup.getRight() + rect.right;
            rect.top = viewGroup.getTop() + rect.top;
            rect.bottom = viewGroup.getBottom() + rect.bottom;
            parent = viewGroup.getParent();
        }
        return rect;
    }

    private void a(int i, boolean z, int i2, boolean z2) {
        int iMax;
        x xVarInfoForPosition = infoForPosition(i);
        if (xVarInfoForPosition != null) {
            iMax = (int) (Math.max(this.Xg, Math.min(xVarInfoForPosition.offset, this.Yg)) * ib());
        } else {
            iMax = 0;
        }
        if (z) {
            smoothScrollTo(iMax, 0, i2);
            if (z2) {
                U(i);
                return;
            }
            return;
        }
        if (z2) {
            U(i);
        }
        w(false);
        scrollTo(iMax, 0);
        V(iMax);
    }

    private void d(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.Cf = motionEvent.getX(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    private boolean h(float f) {
        boolean z;
        boolean z2;
        float f2 = this.Cf - f;
        this.Cf = f;
        float scrollX = getScrollX() + f2;
        float fIb = ib();
        float f3 = this.Xg * fIb;
        float f4 = this.Yg * fIb;
        boolean z3 = false;
        x xVar = (x) this.mItems.get(0);
        ArrayList arrayList = this.mItems;
        x xVar2 = (x) arrayList.get(arrayList.size() - 1);
        if (xVar.position != 0) {
            f3 = xVar.offset * fIb;
            z = false;
        } else {
            z = true;
        }
        if (xVar2.position != this.mAdapter.getCount() - 1) {
            f4 = xVar2.offset * fIb;
            z2 = false;
        } else {
            z2 = true;
        }
        if (scrollX < f3) {
            if (z) {
                this.kh.onPull(Math.abs(f3 - scrollX) / fIb);
                z3 = true;
            }
            scrollX = f3;
        } else if (scrollX > f4) {
            if (z2) {
                this.lh.onPull(Math.abs(scrollX - f4) / fIb);
                z3 = true;
            }
            scrollX = f4;
        }
        int i = (int) scrollX;
        this.Cf = (scrollX - i) + this.Cf;
        scrollTo(i, getScrollY());
        V(i);
        return z3;
    }

    private int ib() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private x jb() {
        int i;
        int iIb = ib();
        float scrollX = iIb > 0 ? getScrollX() / iIb : 0.0f;
        float f = iIb > 0 ? this.Tg / iIb : 0.0f;
        x xVar = null;
        float f2 = 0.0f;
        float f3 = 0.0f;
        int i2 = 0;
        int i3 = -1;
        boolean z = true;
        while (i2 < this.mItems.size()) {
            x xVar2 = (x) this.mItems.get(i2);
            if (!z && xVar2.position != (i = i3 + 1)) {
                xVar2 = this.Qg;
                xVar2.offset = f2 + f3 + f;
                xVar2.position = i;
                xVar2.widthFactor = this.mAdapter.getPageWidth(xVar2.position);
                i2--;
            }
            f2 = xVar2.offset;
            float f4 = xVar2.widthFactor + f2 + f;
            if (!z && scrollX < f2) {
                return xVar;
            }
            if (scrollX < f4 || i2 == this.mItems.size() - 1) {
                return xVar2;
            }
            i3 = xVar2.position;
            f3 = xVar2.widthFactor;
            i2++;
            z = false;
            xVar = xVar2;
        }
        return xVar;
    }

    private boolean kb() {
        this.mActivePointerId = -1;
        this.bh = false;
        this.ch = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        this.kh.onRelease();
        this.lh.onRelease();
        return this.kh.isFinished() || this.lh.isFinished();
    }

    private void lb() {
        if (this.th != 0) {
            ArrayList arrayList = this.uh;
            if (arrayList == null) {
                this.uh = new ArrayList();
            } else {
                arrayList.clear();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.uh.add(getChildAt(i));
            }
            Collections.sort(this.uh, xh);
        }
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    private void w(boolean z) {
        boolean z2 = this.zc == 2;
        if (z2) {
            setScrollingCacheEnabled(false);
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.mScroller.getCurrX();
                int currY = this.mScroller.getCurrY();
                if (scrollX != currX || scrollY != currY) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        V(currX);
                    }
                }
            }
        }
        this._g = false;
        boolean z3 = z2;
        for (int i = 0; i < this.mItems.size(); i++) {
            x xVar = (x) this.mItems.get(i);
            if (xVar.scrolling) {
                xVar.scrolling = false;
                z3 = true;
            }
        }
        if (z3) {
            if (z) {
                r.a(this, this.vh);
            } else {
                this.vh.run();
            }
        }
    }

    private void x(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    void V() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, Qf);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.zf = viewConfiguration.getScaledPagingTouchSlop();
        this.fh = (int) (400.0f * f);
        this.gh = viewConfiguration.getScaledMaximumFlingVelocity();
        this.kh = new EdgeEffect(context);
        this.lh = new EdgeEffect(context);
        this.hh = (int) (25.0f * f);
        this.ih = (int) (2.0f * f);
        this.dh = (int) (f * 16.0f);
        r.a(this, new y(this));
        if (r.e(this) == 0) {
            int i = Build.VERSION.SDK_INT;
            setImportantForAccessibility(1);
        }
        r.a(this, new v(this));
    }

    A a(A a2) {
        A a3 = this.rh;
        this.rh = a2;
        return a3;
    }

    public void a(z zVar) {
        if (this.sh == null) {
            this.sh = new ArrayList();
        }
        this.sh.add(zVar);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList arrayList, int i, int i2) {
        x xVarInfoForChild;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0 && (xVarInfoForChild = infoForChild(childAt)) != null && xVarInfoForChild.position == this.Rg) {
                    childAt.addFocusables(arrayList, i, i2);
                }
            }
        }
        if ((descendantFocusability != 262144 || size == arrayList.size()) && isFocusable()) {
            if ((i2 & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) {
                return;
            }
            arrayList.add(this);
        }
    }

    x addNewItem(int i, int i2) {
        x xVar = new x();
        xVar.position = i;
        xVar.object = this.mAdapter.instantiateItem((ViewGroup) this, i);
        xVar.widthFactor = this.mAdapter.getPageWidth(i);
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(xVar);
        } else {
            this.mItems.add(i2, xVar);
        }
        return xVar;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(ArrayList arrayList) {
        x xVarInfoForChild;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (xVarInfoForChild = infoForChild(childAt)) != null && xVarInfoForChild.position == this.Rg) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        layoutParams2.isDecor |= view.getClass().getAnnotation(w.class) != null;
        if (!this.mInLayout) {
            super.addView(view, i, layoutParams);
        } else {
            if (layoutParams2.isDecor) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams2.needsMeasure = true;
            addViewInLayout(view, i, layoutParams);
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00aa  */
    public boolean arrowScroll(int i) {
        boolean zPageRight;
        boolean z;
        View viewFindFocus = findFocus();
        boolean zPageLeft = false;
        if (viewFindFocus == this) {
            viewFindFocus = null;
        } else if (viewFindFocus != null) {
            ViewParent parent = viewFindFocus.getParent();
            while (true) {
                if (!(parent instanceof ViewGroup)) {
                    z = false;
                    break;
                }
                if (parent == this) {
                    z = true;
                    break;
                }
                parent = parent.getParent();
            }
            if (!z) {
                StringBuilder sb = new StringBuilder();
                sb.append(viewFindFocus.getClass().getSimpleName());
                for (ViewParent parent2 = viewFindFocus.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                    sb.append(" => ");
                    sb.append(parent2.getClass().getSimpleName());
                }
                StringBuilder sbE = b.a.a.a.a.e("arrowScroll tried to find focus based on non-child current focused view ");
                sbE.append(sb.toString());
                Log.e("ViewPager", sbE.toString());
                viewFindFocus = null;
            }
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i);
        if (viewFindNextFocus == null || viewFindNextFocus == viewFindFocus) {
            if (i == 17 || i == 1) {
                zPageLeft = pageLeft();
            } else if (i == 66 || i == 2) {
                zPageLeft = pageRight();
            }
        } else if (i == 17) {
            int i2 = a(this.mTempRect, viewFindNextFocus).left;
            int i3 = a(this.mTempRect, viewFindFocus).left;
            if (viewFindFocus == null || i2 < i3) {
                zPageRight = viewFindNextFocus.requestFocus();
            } else {
                zPageRight = pageLeft();
            }
            zPageLeft = zPageRight;
        } else if (i == 66) {
            int i4 = a(this.mTempRect, viewFindNextFocus).left;
            int i5 = a(this.mTempRect, viewFindFocus).left;
            if (viewFindFocus == null || i4 > i5) {
                zPageRight = viewFindNextFocus.requestFocus();
            } else {
                zPageRight = pageRight();
            }
            zPageLeft = zPageRight;
        }
        if (zPageLeft) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
        }
        return zPageLeft;
    }

    public void b(z zVar) {
        List list = this.sh;
        if (list != null) {
            list.remove(zVar);
        }
    }

    protected boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        int i4;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(childAt, true, i, i5 - childAt.getLeft(), i4 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return z && view.canScrollHorizontally(-i);
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        if (this.mAdapter == null) {
            return false;
        }
        int iIb = ib();
        int scrollX = getScrollX();
        if (i < 0) {
            return scrollX > ((int) (((float) iIb) * this.Xg));
        }
        return i > 0 && scrollX < ((int) (((float) iIb) * this.Yg));
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public void computeScroll() {
        this.Sg = true;
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            w(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (scrollX != currX || scrollY != currY) {
            scrollTo(currX, currY);
            if (!V(currX)) {
                this.mScroller.abortAnimation();
                scrollTo(0, currY);
            }
        }
        r.n(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        x xVarInfoForChild;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (xVarInfoForChild = infoForChild(childAt)) != null && xVarInfoForChild.position == this.Rg && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((f - 0.5f) * 0.47123894f);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        k kVar;
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean zDraw = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (kVar = this.mAdapter) != null && kVar.getCount() > 1)) {
            if (!this.kh.isFinished()) {
                int iSave = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate(getPaddingTop() + (-height), this.Xg * width);
                this.kh.setSize(height, width);
                zDraw = false | this.kh.draw(canvas);
                canvas.restoreToCount(iSave);
            }
            if (!this.lh.isFinished()) {
                int iSave2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate(-getPaddingTop(), (-(this.Yg + 1.0f)) * width2);
                this.lh.setSize(height2, width2);
                zDraw |= this.lh.draw(canvas);
                canvas.restoreToCount(iSave2);
            }
        } else {
            this.kh.finish();
            this.lh.finish();
        }
        if (zDraw) {
            r.n(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.Ug;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        int i;
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 21) {
                if (keyCode != 22) {
                    if (keyCode == 61) {
                        if (keyEvent.hasNoModifiers()) {
                            return arrowScroll(2);
                        }
                        if (keyEvent.hasModifiers(1)) {
                            return arrowScroll(1);
                        }
                    }
                } else {
                    if (keyEvent.hasModifiers(2)) {
                        return pageRight();
                    }
                    i = 66;
                }
            } else {
                if (keyEvent.hasModifiers(2)) {
                    return pageLeft();
                }
                i = 17;
            }
            return arrowScroll(i);
        }
        return false;
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
        return generateDefaultLayoutParams();
    }

    public k getAdapter() {
        return this.mAdapter;
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        if (this.th == 2) {
            i2 = (i - 1) - i2;
        }
        return ((LayoutParams) ((View) this.uh.get(i2)).getLayoutParams()).childIndex;
    }

    public int getCurrentItem() {
        return this.Rg;
    }

    x infoForAnyChild(View view) {
        while (true) {
            Object parent = view.getParent();
            if (parent == this) {
                return infoForChild(view);
            }
            if (parent == null || !(parent instanceof View)) {
                return null;
            }
            view = (View) parent;
        }
    }

    x infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); i++) {
            x xVar = (x) this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, xVar.object)) {
                return xVar;
            }
        }
        return null;
    }

    x infoForPosition(int i) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            x xVar = (x) this.mItems.get(i2);
            if (xVar.position == i) {
                return xVar;
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mh = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.vh);
        Scroller scroller = this.mScroller;
        if (scroller != null && !scroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float f;
        super.onDraw(canvas);
        if (this.Tg <= 0 || this.Ug == null || this.mItems.size() <= 0 || this.mAdapter == null) {
            return;
        }
        int scrollX = getScrollX();
        int width = getWidth();
        float f2 = width;
        float f3 = this.Tg / f2;
        int i = 0;
        x xVar = (x) this.mItems.get(0);
        float f4 = xVar.offset;
        int size = this.mItems.size();
        int i2 = xVar.position;
        int i3 = ((x) this.mItems.get(size - 1)).position;
        while (i2 < i3) {
            while (i2 > xVar.position && i < size) {
                i++;
                xVar = (x) this.mItems.get(i);
            }
            if (i2 == xVar.position) {
                float f5 = xVar.offset;
                float f6 = xVar.widthFactor;
                f = (f5 + f6) * f2;
                f4 = f5 + f6 + f3;
            } else {
                float pageWidth = this.mAdapter.getPageWidth(i2);
                f = (f4 + pageWidth) * f2;
                f4 = pageWidth + f3 + f4;
            }
            if (this.Tg + f > scrollX) {
                this.Ug.setBounds(Math.round(f), this.Vg, Math.round(this.Tg + f), this.Wg);
                this.Ug.draw(canvas);
            }
            if (f > scrollX + width) {
                return;
            }
            i2++;
            f3 = f3;
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            kb();
            return false;
        }
        if (action != 0) {
            if (this.bh) {
                return true;
            }
            if (this.ch) {
                return false;
            }
        }
        if (action == 0) {
            float x = motionEvent.getX();
            this.Af = x;
            this.Cf = x;
            float y = motionEvent.getY();
            this.Bf = y;
            this.Df = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.ch = false;
            this.Sg = true;
            this.mScroller.computeScrollOffset();
            if (this.zc != 2 || Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) <= this.ih) {
                w(false);
                this.bh = false;
            } else {
                this.mScroller.abortAnimation();
                this._g = false;
                populate();
                this.bh = true;
                x(true);
                setScrollState(1);
            }
        } else if (action == 2) {
            int i = this.mActivePointerId;
            if (i != -1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(i);
                float x2 = motionEvent.getX(iFindPointerIndex);
                float f = x2 - this.Cf;
                float fAbs = Math.abs(f);
                float y2 = motionEvent.getY(iFindPointerIndex);
                float fAbs2 = Math.abs(y2 - this.Bf);
                if (f != 0.0f) {
                    float f2 = this.Cf;
                    if (!((f2 < ((float) this.eh) && f > 0.0f) || (f2 > ((float) (getWidth() - this.eh)) && f < 0.0f)) && canScroll(this, false, (int) f, (int) x2, (int) y2)) {
                        this.Cf = x2;
                        this.Df = y2;
                        this.ch = true;
                        return false;
                    }
                }
                if (fAbs > this.zf && fAbs * 0.5f > fAbs2) {
                    this.bh = true;
                    x(true);
                    setScrollState(1);
                    float f3 = this.Af;
                    float f4 = this.zf;
                    this.Cf = f > 0.0f ? f3 + f4 : f3 - f4;
                    this.Df = y2;
                    setScrollingCacheEnabled(true);
                } else if (fAbs2 > this.zf) {
                    this.ch = true;
                }
                if (this.bh && h(x2)) {
                    r.n(this);
                }
            }
        } else if (action == 6) {
            d(motionEvent);
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.bh;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        x xVarInfoForChild;
        int iMax;
        int iMax2;
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int measuredHeight = paddingBottom;
        int i7 = 0;
        int measuredHeight2 = paddingTop;
        int measuredWidth = paddingLeft;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i9 = layoutParams.gravity;
                    int i10 = i9 & 7;
                    int i11 = i9 & 112;
                    if (i10 == 1) {
                        iMax = Math.max((i5 - childAt.getMeasuredWidth()) / 2, measuredWidth);
                    } else if (i10 == 3) {
                        iMax = measuredWidth;
                        measuredWidth = childAt.getMeasuredWidth() + measuredWidth;
                    } else if (i10 != 5) {
                        iMax = measuredWidth;
                    } else {
                        iMax = (i5 - paddingRight) - childAt.getMeasuredWidth();
                        paddingRight += childAt.getMeasuredWidth();
                    }
                    if (i11 == 16) {
                        iMax2 = Math.max((i6 - childAt.getMeasuredHeight()) / 2, measuredHeight2);
                    } else if (i11 == 48) {
                        iMax2 = measuredHeight2;
                        measuredHeight2 = childAt.getMeasuredHeight() + measuredHeight2;
                    } else if (i11 != 80) {
                        iMax2 = measuredHeight2;
                    } else {
                        iMax2 = (i6 - measuredHeight) - childAt.getMeasuredHeight();
                        measuredHeight += childAt.getMeasuredHeight();
                    }
                    int i12 = iMax + scrollX;
                    childAt.layout(i12, iMax2, childAt.getMeasuredWidth() + i12, childAt.getMeasuredHeight() + iMax2);
                    i7++;
                }
            }
        }
        int i13 = (i5 - measuredWidth) - paddingRight;
        for (int i14 = 0; i14 < childCount; i14++) {
            View childAt2 = getChildAt(i14);
            if (childAt2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams2.isDecor && (xVarInfoForChild = infoForChild(childAt2)) != null) {
                    float f = i13;
                    int i15 = ((int) (xVarInfoForChild.offset * f)) + measuredWidth;
                    if (layoutParams2.needsMeasure) {
                        layoutParams2.needsMeasure = false;
                        childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (f * layoutParams2.widthFactor), 1073741824), View.MeasureSpec.makeMeasureSpec((i6 - measuredHeight2) - measuredHeight, 1073741824));
                    }
                    childAt2.layout(i15, measuredHeight2, childAt2.getMeasuredWidth() + i15, childAt2.getMeasuredHeight() + measuredHeight2);
                }
            }
        }
        this.Vg = measuredHeight2;
        this.Wg = i6 - measuredHeight;
        this.oh = i7;
        if (this.mh) {
            z2 = false;
            a(this.Rg, false, 0, false);
        } else {
            z2 = false;
        }
        this.mh = z2;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        LayoutParams layoutParams;
        LayoutParams layoutParams2;
        int i3;
        setMeasuredDimension(ViewGroup.getDefaultSize(0, i), ViewGroup.getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        this.eh = Math.min(measuredWidth / 10, this.dh);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int measuredHeight2 = measuredHeight;
        int measuredWidth2 = paddingLeft;
        int i4 = 0;
        while (true) {
            boolean z = true;
            int i5 = 1073741824;
            if (i4 >= childCount) {
                break;
            }
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8 && (layoutParams2 = (LayoutParams) childAt.getLayoutParams()) != null && layoutParams2.isDecor) {
                int i6 = layoutParams2.gravity;
                int i7 = i6 & 7;
                int i8 = i6 & 112;
                boolean z2 = i8 == 48 || i8 == 80;
                if (i7 != 3 && i7 != 5) {
                    z = false;
                }
                int i9 = Integer.MIN_VALUE;
                if (z2) {
                    i3 = Integer.MIN_VALUE;
                    i9 = 1073741824;
                } else {
                    i3 = z ? 1073741824 : Integer.MIN_VALUE;
                }
                int i10 = ((ViewGroup.LayoutParams) layoutParams2).width;
                if (i10 != -2) {
                    if (i10 == -1) {
                        i10 = measuredWidth2;
                    }
                    i9 = 1073741824;
                } else {
                    i10 = measuredWidth2;
                }
                int i11 = ((ViewGroup.LayoutParams) layoutParams2).height;
                if (i11 == -2) {
                    i11 = measuredHeight2;
                    i5 = i3;
                } else if (i11 == -1) {
                    i11 = measuredHeight2;
                }
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i10, i9), View.MeasureSpec.makeMeasureSpec(i11, i5));
                if (z2) {
                    measuredHeight2 -= childAt.getMeasuredHeight();
                } else if (z) {
                    measuredWidth2 -= childAt.getMeasuredWidth();
                }
            }
            i4++;
        }
        View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
        this.Zg = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount2 = getChildCount();
        for (int i12 = 0; i12 < childCount2; i12++) {
            View childAt2 = getChildAt(i12);
            if (childAt2.getVisibility() != 8 && ((layoutParams = (LayoutParams) childAt2.getLayoutParams()) == null || !layoutParams.isDecor)) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (measuredWidth2 * layoutParams.widthFactor), 1073741824), this.Zg);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0066  */
    protected void onPageScrolled(int i, float f, int i2) {
        int iMax;
        int width;
        int left;
        if (this.oh > 0) {
            int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width2 = getWidth();
            int childCount = getChildCount();
            int measuredWidth = paddingRight;
            int i3 = paddingLeft;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i5 = layoutParams.gravity & 7;
                    if (i5 != 1) {
                        if (i5 == 3) {
                            width = childAt.getWidth() + i3;
                        } else if (i5 != 5) {
                            width = i3;
                        } else {
                            iMax = (width2 - measuredWidth) - childAt.getMeasuredWidth();
                            measuredWidth += childAt.getMeasuredWidth();
                        }
                        left = (i3 + scrollX) - childAt.getLeft();
                        if (left != 0) {
                            childAt.offsetLeftAndRight(left);
                        }
                        i3 = width;
                    } else {
                        iMax = Math.max((width2 - childAt.getMeasuredWidth()) / 2, i3);
                    }
                    int i6 = iMax;
                    width = i3;
                    i3 = i6;
                    left = (i3 + scrollX) - childAt.getLeft();
                    if (left != 0) {
                        childAt.offsetLeftAndRight(left);
                    }
                    i3 = width;
                }
            }
        }
        A a2 = this.qh;
        if (a2 != null) {
            ((n) a2).onPageScrolled(i, f, i2);
        }
        List list = this.ph;
        if (list != null) {
            int size = list.size();
            for (int i7 = 0; i7 < size; i7++) {
                A a3 = (A) this.ph.get(i7);
                if (a3 != null) {
                    ((n) a3).onPageScrolled(i, f, i2);
                }
            }
        }
        A a4 = this.rh;
        if (a4 != null) {
            ((n) a4).onPageScrolled(i, f, i2);
        }
        this.nh = true;
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3;
        x xVarInfoForChild;
        int childCount = getChildCount();
        int i4 = -1;
        if ((i & 2) != 0) {
            i4 = childCount;
            i2 = 0;
            i3 = 1;
        } else {
            i2 = childCount - 1;
            i3 = -1;
        }
        while (i2 != i4) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (xVarInfoForChild = infoForChild(childAt)) != null && xVarInfoForChild.position == this.Rg && childAt.requestFocus(i, rect)) {
                return true;
            }
            i2 += i3;
        }
        return false;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        k kVar = this.mAdapter;
        if (kVar != null) {
            kVar.restoreState(savedState.adapterState, savedState.loader);
            setCurrentItemInternal(savedState.position, false, true);
        } else {
            int i = savedState.position;
            Parcelable parcelable2 = savedState.adapterState;
            ClassLoader classLoader = savedState.loader;
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.Rg;
        k kVar = this.mAdapter;
        if (kVar != null) {
            savedState.adapterState = kVar.saveState();
        }
        return savedState;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        int iMin;
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            int i5 = this.Tg;
            if (i3 <= 0 || this.mItems.isEmpty()) {
                x xVarInfoForPosition = infoForPosition(this.Rg);
                iMin = (int) ((xVarInfoForPosition != null ? Math.min(xVarInfoForPosition.offset, this.Yg) : 0.0f) * ((i - getPaddingLeft()) - getPaddingRight()));
                if (iMin == getScrollX()) {
                    return;
                } else {
                    w(false);
                }
            } else if (!this.mScroller.isFinished()) {
                this.mScroller.setFinalX(getCurrentItem() * ib());
                return;
            } else {
                iMin = (int) ((getScrollX() / (((i3 - getPaddingLeft()) - getPaddingRight()) + i5)) * (((i - getPaddingLeft()) - getPaddingRight()) + i5));
            }
            scrollTo(iMin, getScrollY());
        }
    }

    /* JADX WARN: Code duplicated, block: B:77:0x019b  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        k kVar;
        int pointerId;
        if (this.jh) {
            return true;
        }
        boolean zKb = false;
        if ((motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) || (kVar = this.mAdapter) == null || kVar.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (!this.bh) {
                        int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                        if (iFindPointerIndex == -1) {
                            zKb = kb();
                        } else {
                            float x = motionEvent.getX(iFindPointerIndex);
                            float fAbs = Math.abs(x - this.Cf);
                            float y = motionEvent.getY(iFindPointerIndex);
                            float fAbs2 = Math.abs(y - this.Df);
                            if (fAbs > this.zf && fAbs > fAbs2) {
                                this.bh = true;
                                x(true);
                                float f = this.Af;
                                this.Cf = x - f > 0.0f ? f + this.zf : f - this.zf;
                                this.Df = y;
                                setScrollState(1);
                                setScrollingCacheEnabled(true);
                                ViewParent parent = getParent();
                                if (parent != null) {
                                    parent.requestDisallowInterceptTouchEvent(true);
                                }
                            }
                        }
                    }
                    if (this.bh) {
                        zKb = false | h(motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)));
                    }
                } else if (action != 3) {
                    if (action == 5) {
                        int actionIndex = motionEvent.getActionIndex();
                        this.Cf = motionEvent.getX(actionIndex);
                        pointerId = motionEvent.getPointerId(actionIndex);
                    } else if (action == 6) {
                        d(motionEvent);
                        this.Cf = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                    }
                } else if (this.bh) {
                    a(this.Rg, true, 0, false);
                    zKb = kb();
                }
            } else if (this.bh) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.gh);
                int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                this._g = true;
                int iIb = ib();
                int scrollX = getScrollX();
                x xVarJb = jb();
                float f2 = iIb;
                float f3 = this.Tg / f2;
                int iMax = xVarJb.position;
                float f4 = ((scrollX / f2) - xVarJb.offset) / (xVarJb.widthFactor + f3);
                if (Math.abs((int) (motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.Af)) <= this.hh || Math.abs(xVelocity) <= this.fh) {
                    iMax += (int) (f4 + (iMax >= this.Rg ? 0.4f : 0.6f));
                } else if (xVelocity <= 0) {
                    iMax++;
                }
                if (this.mItems.size() > 0) {
                    x xVar = (x) this.mItems.get(0);
                    ArrayList arrayList = this.mItems;
                    iMax = Math.max(xVar.position, Math.min(iMax, ((x) arrayList.get(arrayList.size() - 1)).position));
                }
                setCurrentItemInternal(iMax, true, true, xVelocity);
                zKb = kb();
            }
            if (zKb) {
                r.n(this);
            }
            return true;
        }
        this.mScroller.abortAnimation();
        this._g = false;
        populate();
        float x2 = motionEvent.getX();
        this.Af = x2;
        this.Cf = x2;
        float y2 = motionEvent.getY();
        this.Bf = y2;
        this.Df = y2;
        pointerId = motionEvent.getPointerId(0);
        this.mActivePointerId = pointerId;
        if (zKb) {
            r.n(this);
        }
        return true;
    }

    boolean pageLeft() {
        int i = this.Rg;
        if (i <= 0) {
            return false;
        }
        setCurrentItem(i - 1, true);
        return true;
    }

    boolean pageRight() {
        k kVar = this.mAdapter;
        if (kVar == null || this.Rg >= kVar.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.Rg + 1, true);
        return true;
    }

    void populate() {
        populate(this.Rg);
    }

    /* JADX WARN: Code duplicated, block: B:62:0x00db A[PHI: r2 r6 r12
      0x00db: PHI (r2v15 float) = (r2v13 float), (r2v14 float), (r2v9 float) binds: [B:61:0x00d9, B:58:0x00cb, B:52:0x00bd] A[DONT_GENERATE, DONT_INLINE]
      0x00db: PHI (r6v15 int) = (r6v14 int), (r6v2 int), (r6v18 int) binds: [B:61:0x00d9, B:58:0x00cb, B:52:0x00bd] A[DONT_GENERATE, DONT_INLINE]
      0x00db: PHI (r12v4 int) = (r12v2 int), (r12v3 int), (r12v7 int) binds: [B:61:0x00d9, B:58:0x00cb, B:52:0x00bd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:63:0x00e4 A[PHI: r2 r6 r12
      0x00e4: PHI (r2v17 float) = (r2v13 float), (r2v14 float), (r2v9 float) binds: [B:61:0x00d9, B:58:0x00cb, B:52:0x00bd] A[DONT_GENERATE, DONT_INLINE]
      0x00e4: PHI (r6v17 int) = (r6v14 int), (r6v2 int), (r6v18 int) binds: [B:61:0x00d9, B:58:0x00cb, B:52:0x00bd] A[DONT_GENERATE, DONT_INLINE]
      0x00e4: PHI (r12v6 int) = (r12v2 int), (r12v3 int), (r12v7 int) binds: [B:61:0x00d9, B:58:0x00cb, B:52:0x00bd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:96:0x0161 A[PHI: r1 r10
      0x0161: PHI (r1v21 float) = (r1v19 float), (r1v20 float), (r1v18 float) binds: [B:95:0x015f, B:92:0x014d, B:86:0x0139] A[DONT_GENERATE, DONT_INLINE]
      0x0161: PHI (r10v15 int) = (r10v13 int), (r10v14 int), (r10v12 int) binds: [B:95:0x015f, B:92:0x014d, B:86:0x0139] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:97:0x016a A[PHI: r1 r10
      0x016a: PHI (r1v23 float) = (r1v19 float), (r1v20 float), (r1v18 float) binds: [B:95:0x015f, B:92:0x014d, B:86:0x0139] A[DONT_GENERATE, DONT_INLINE]
      0x016a: PHI (r10v17 int) = (r10v13 int), (r10v14 int), (r10v12 int) binds: [B:95:0x015f, B:92:0x014d, B:86:0x0139] A[DONT_GENERATE, DONT_INLINE]] */
    void populate(int i) {
        x xVarInfoForPosition;
        String hexString;
        x xVarAddNewItem;
        x xVarInfoForChild;
        int i2;
        int i3;
        x xVar;
        x xVar2;
        int i4 = this.Rg;
        if (i4 != i) {
            xVarInfoForPosition = infoForPosition(i4);
            this.Rg = i;
        } else {
            xVarInfoForPosition = null;
        }
        if (this.mAdapter == null) {
            lb();
            return;
        }
        if (this._g) {
            lb();
            return;
        }
        if (getWindowToken() == null) {
            return;
        }
        this.mAdapter.startUpdate((ViewGroup) this);
        int i5 = this.ah;
        int i6 = 0;
        int iMax = Math.max(0, this.Rg - i5);
        int count = this.mAdapter.getCount();
        int iMin = Math.min(count - 1, this.Rg + i5);
        if (count != this.Pg) {
            try {
                hexString = getResources().getResourceName(getId());
            } catch (Resources.NotFoundException unused) {
                hexString = Integer.toHexString(getId());
            }
            StringBuilder sbE = b.a.a.a.a.e("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ");
            sbE.append(this.Pg);
            sbE.append(", found: ");
            sbE.append(count);
            sbE.append(" Pager id: ");
            sbE.append(hexString);
            sbE.append(" Pager class: ");
            sbE.append(ViewPager.class);
            sbE.append(" Problematic adapter: ");
            sbE.append(this.mAdapter.getClass());
            throw new IllegalStateException(sbE.toString());
        }
        while (true) {
            if (i6 < this.mItems.size()) {
                xVarAddNewItem = (x) this.mItems.get(i6);
                int i7 = xVarAddNewItem.position;
                int i8 = this.Rg;
                if (i7 >= i8) {
                    if (i7 != i8) {
                        break;
                    } else {
                        break;
                    }
                }
                i6++;
            }
            xVarAddNewItem = null;
            break;
        }
        if (xVarAddNewItem == null && count > 0) {
            xVarAddNewItem = addNewItem(this.Rg, i6);
        }
        if (xVarAddNewItem != null) {
            int i9 = i6 - 1;
            x xVar3 = i9 >= 0 ? (x) this.mItems.get(i9) : null;
            int iIb = ib();
            float paddingLeft = iIb <= 0 ? 0.0f : (2.0f - xVarAddNewItem.widthFactor) + (getPaddingLeft() / iIb);
            int i10 = i9;
            int i11 = i6;
            float f = 0.0f;
            for (int i12 = this.Rg - 1; i12 >= 0; i12--) {
                if (f >= paddingLeft && i12 < iMax) {
                    if (xVar3 == null) {
                        break;
                    }
                    if (i12 == xVar3.position && !xVar3.scrolling) {
                        this.mItems.remove(i10);
                        this.mAdapter.destroyItem((ViewGroup) this, i12, xVar3.object);
                        i10--;
                        i11--;
                        if (i10 >= 0) {
                            xVar3 = (x) this.mItems.get(i10);
                        } else {
                            xVar3 = null;
                        }
                    }
                } else if (xVar3 == null || i12 != xVar3.position) {
                    f += addNewItem(i12, i10 + 1).widthFactor;
                    i11++;
                    if (i10 >= 0) {
                        xVar3 = (x) this.mItems.get(i10);
                    } else {
                        xVar3 = null;
                    }
                } else {
                    f += xVar3.widthFactor;
                    i10--;
                    if (i10 >= 0) {
                        xVar3 = (x) this.mItems.get(i10);
                    } else {
                        xVar3 = null;
                    }
                }
            }
            float f2 = xVarAddNewItem.widthFactor;
            int i13 = i11 + 1;
            if (f2 < 2.0f) {
                x xVar4 = i13 < this.mItems.size() ? (x) this.mItems.get(i13) : null;
                float paddingRight = iIb <= 0 ? 0.0f : (getPaddingRight() / iIb) + 2.0f;
                int i14 = i13;
                for (int i15 = this.Rg + 1; i15 < count; i15++) {
                    if (f2 >= paddingRight && i15 > iMin) {
                        if (xVar4 == null) {
                            break;
                        }
                        if (i15 == xVar4.position && !xVar4.scrolling) {
                            this.mItems.remove(i14);
                            this.mAdapter.destroyItem((ViewGroup) this, i15, xVar4.object);
                            if (i14 < this.mItems.size()) {
                                xVar4 = (x) this.mItems.get(i14);
                            } else {
                                xVar4 = null;
                            }
                        }
                    } else if (xVar4 == null || i15 != xVar4.position) {
                        x xVarAddNewItem2 = addNewItem(i15, i14);
                        i14++;
                        f2 += xVarAddNewItem2.widthFactor;
                        if (i14 < this.mItems.size()) {
                            xVar4 = (x) this.mItems.get(i14);
                        } else {
                            xVar4 = null;
                        }
                    } else {
                        f2 += xVar4.widthFactor;
                        i14++;
                        if (i14 < this.mItems.size()) {
                            xVar4 = (x) this.mItems.get(i14);
                        } else {
                            xVar4 = null;
                        }
                    }
                }
            }
            int count2 = this.mAdapter.getCount();
            int iIb2 = ib();
            float f3 = iIb2 > 0 ? this.Tg / iIb2 : 0.0f;
            if (xVarInfoForPosition != null) {
                int i16 = xVarInfoForPosition.position;
                int i17 = xVarAddNewItem.position;
                if (i16 < i17) {
                    float pageWidth = xVarInfoForPosition.offset + xVarInfoForPosition.widthFactor + f3;
                    int i18 = 0;
                    while (true) {
                        i16++;
                        if (i16 > xVarAddNewItem.position || i18 >= this.mItems.size()) {
                            break;
                        }
                        while (true) {
                            xVar2 = (x) this.mItems.get(i18);
                            if (i16 <= xVar2.position || i18 >= this.mItems.size() - 1) {
                                break;
                            } else {
                                i18++;
                            }
                        }
                        while (i16 < xVar2.position) {
                            pageWidth += this.mAdapter.getPageWidth(i16) + f3;
                            i16++;
                        }
                        xVar2.offset = pageWidth;
                        pageWidth += xVar2.widthFactor + f3;
                    }
                } else if (i16 > i17) {
                    int size = this.mItems.size() - 1;
                    float pageWidth2 = xVarInfoForPosition.offset;
                    while (true) {
                        i16--;
                        if (i16 < xVarAddNewItem.position || size < 0) {
                            break;
                        }
                        while (true) {
                            xVar = (x) this.mItems.get(size);
                            if (i16 >= xVar.position || size <= 0) {
                                break;
                            } else {
                                size--;
                            }
                        }
                        while (i16 > xVar.position) {
                            pageWidth2 -= this.mAdapter.getPageWidth(i16) + f3;
                            i16--;
                        }
                        pageWidth2 -= xVar.widthFactor + f3;
                        xVar.offset = pageWidth2;
                    }
                }
            }
            int size2 = this.mItems.size();
            float pageWidth3 = xVarAddNewItem.offset;
            int i19 = xVarAddNewItem.position;
            int i20 = i19 - 1;
            this.Xg = i19 == 0 ? pageWidth3 : -3.4028235E38f;
            int i21 = count2 - 1;
            this.Yg = xVarAddNewItem.position == i21 ? (xVarAddNewItem.offset + xVarAddNewItem.widthFactor) - 1.0f : Float.MAX_VALUE;
            int i22 = i11 - 1;
            while (i22 >= 0) {
                x xVar5 = (x) this.mItems.get(i22);
                while (true) {
                    i3 = xVar5.position;
                    if (i20 <= i3) {
                        break;
                    }
                    pageWidth3 -= this.mAdapter.getPageWidth(i20) + f3;
                    i20--;
                }
                pageWidth3 -= xVar5.widthFactor + f3;
                xVar5.offset = pageWidth3;
                if (i3 == 0) {
                    this.Xg = pageWidth3;
                }
                i22--;
                i20--;
            }
            float pageWidth4 = xVarAddNewItem.offset + xVarAddNewItem.widthFactor + f3;
            int i23 = xVarAddNewItem.position;
            while (true) {
                i23++;
                if (i13 >= size2) {
                    break;
                }
                x xVar6 = (x) this.mItems.get(i13);
                while (true) {
                    i2 = xVar6.position;
                    if (i23 >= i2) {
                        break;
                    }
                    pageWidth4 += this.mAdapter.getPageWidth(i23) + f3;
                    i23++;
                }
                if (i2 == i21) {
                    this.Yg = (xVar6.widthFactor + pageWidth4) - 1.0f;
                }
                xVar6.offset = pageWidth4;
                pageWidth4 += xVar6.widthFactor + f3;
                i13++;
            }
            this.mAdapter.setPrimaryItem((ViewGroup) this, this.Rg, xVarAddNewItem.object);
        }
        this.mAdapter.finishUpdate((ViewGroup) this);
        int childCount = getChildCount();
        for (int i24 = 0; i24 < childCount; i24++) {
            View childAt = getChildAt(i24);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            layoutParams.childIndex = i24;
            if (!layoutParams.isDecor && layoutParams.widthFactor == 0.0f && (xVarInfoForChild = infoForChild(childAt)) != null) {
                layoutParams.widthFactor = xVarInfoForChild.widthFactor;
                layoutParams.position = xVarInfoForChild.position;
            }
        }
        lb();
        if (hasFocus()) {
            View viewFindFocus = findFocus();
            x xVarInfoForAnyChild = viewFindFocus != null ? infoForAnyChild(viewFindFocus) : null;
            if (xVarInfoForAnyChild == null || xVarInfoForAnyChild.position != this.Rg) {
                for (int i25 = 0; i25 < getChildCount(); i25++) {
                    View childAt2 = getChildAt(i25);
                    x xVarInfoForChild2 = infoForChild(childAt2);
                    if (xVarInfoForChild2 != null && xVarInfoForChild2.position == this.Rg && childAt2.requestFocus(2)) {
                        return;
                    }
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public void setCurrentItem(int i) {
        this._g = false;
        setCurrentItemInternal(i, !this.mh, false);
    }

    public void setCurrentItem(int i, boolean z) {
        this._g = false;
        setCurrentItemInternal(i, z, false);
    }

    void setCurrentItemInternal(int i, boolean z, boolean z2) {
        setCurrentItemInternal(i, z, z2, 0);
    }

    void setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        k kVar = this.mAdapter;
        if (kVar == null || kVar.getCount() <= 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (!z2 && this.Rg == i && this.mItems.size() != 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (i < 0) {
            i = 0;
        } else if (i >= this.mAdapter.getCount()) {
            i = this.mAdapter.getCount() - 1;
        }
        int i3 = this.ah;
        int i4 = this.Rg;
        if (i > i4 + i3 || i < i4 - i3) {
            for (int i5 = 0; i5 < this.mItems.size(); i5++) {
                ((x) this.mItems.get(i5)).scrolling = true;
            }
        }
        boolean z3 = this.Rg != i;
        if (!this.mh) {
            populate(i);
            a(i, z, i2, z3);
        } else {
            this.Rg = i;
            if (z3) {
                U(i);
            }
            requestLayout();
        }
    }

    void setScrollState(int i) {
        if (this.zc == i) {
            return;
        }
        this.zc = i;
        A a2 = this.qh;
        if (a2 != null) {
            ((n) a2).onPageScrollStateChanged(i);
        }
        List list = this.ph;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                A a3 = (A) this.ph.get(i2);
                if (a3 != null) {
                    ((n) a3).onPageScrollStateChanged(i);
                }
            }
        }
        A a4 = this.rh;
        if (a4 != null) {
            ((n) a4).onPageScrollStateChanged(i);
        }
    }

    void smoothScrollTo(int i, int i2, int i3) {
        int scrollX;
        int iAbs;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        Scroller scroller = this.mScroller;
        if ((scroller == null || scroller.isFinished()) ? false : true) {
            scrollX = this.Sg ? this.mScroller.getCurrX() : this.mScroller.getStartX();
            this.mScroller.abortAnimation();
            setScrollingCacheEnabled(false);
        } else {
            scrollX = getScrollX();
        }
        int i4 = scrollX;
        int scrollY = getScrollY();
        int i5 = i - i4;
        int i6 = i2 - scrollY;
        if (i5 == 0 && i6 == 0) {
            w(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int iIb = ib();
        int i7 = iIb / 2;
        float f = iIb;
        float f2 = i7;
        float fDistanceInfluenceForSnapDuration = (distanceInfluenceForSnapDuration(Math.min(1.0f, (Math.abs(i5) * 1.0f) / f)) * f2) + f2;
        int iAbs2 = Math.abs(i3);
        if (iAbs2 > 0) {
            iAbs = Math.round(Math.abs(fDistanceInfluenceForSnapDuration / iAbs2) * 1000.0f) * 4;
        } else {
            iAbs = (int) (((Math.abs(i5) / ((this.mAdapter.getPageWidth(this.Rg) * f) + this.Tg)) + 1.0f) * 100.0f);
        }
        int iMin = Math.min(iAbs, 600);
        this.Sg = false;
        this.mScroller.startScroll(i4, scrollY, i5, i6, iMin);
        r.n(this);
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.Ug;
    }
}
