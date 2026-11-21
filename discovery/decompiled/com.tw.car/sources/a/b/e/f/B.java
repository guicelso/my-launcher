package a.b.e.f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public class B extends ListView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f258a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f259b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f260c;
    public a.b.d.i.e d;
    public b e;
    public Field mIsChildViewEnabled;
    public int mMotionPosition;
    public int mSelectionBottomPadding;
    public int mSelectionLeftPadding;
    public int mSelectionRightPadding;
    public int mSelectionTopPadding;
    public a mSelector;
    public final Rect mSelectorRect;

    private static class a extends a.b.e.d.a.e {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public boolean f261b;

        public a(Drawable drawable) {
            super(drawable);
            this.f261b = true;
        }

        @Override // a.b.e.d.a.e, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.f261b) {
                this.f204a.draw(canvas);
            }
        }

        @Override // a.b.e.d.a.e, android.graphics.drawable.Drawable
        public void setHotspot(float f, float f2) {
            if (this.f261b) {
                a.b.b.a.e.a(this.f204a, f, f2);
            }
        }

        @Override // a.b.e.d.a.e, android.graphics.drawable.Drawable
        public void setHotspotBounds(int i, int i2, int i3, int i4) {
            if (this.f261b) {
                a.b.b.a.e.a(this.f204a, i, i2, i3, i4);
            }
        }

        @Override // a.b.e.d.a.e, android.graphics.drawable.Drawable
        public boolean setState(int[] iArr) {
            if (this.f261b) {
                return this.f204a.setState(iArr);
            }
            return false;
        }

        @Override // a.b.e.d.a.e, android.graphics.drawable.Drawable
        public boolean setVisible(boolean z, boolean z2) {
            if (this.f261b) {
                return super.setVisible(z, z2);
            }
            return false;
        }
    }

    private class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            B b2 = B.this;
            b2.e = null;
            b2.drawableStateChanged();
        }
    }

    public B(Context context, boolean z) {
        super(context, null, a.b.e.b.a.dropDownListViewStyle);
        this.mSelectorRect = new Rect();
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.f259b = z;
        setCacheColorHint(0);
        try {
            this.mIsChildViewEnabled = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.mIsChildViewEnabled.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void setSelectorEnabled(boolean z) {
        a aVar = this.mSelector;
        if (aVar != null) {
            aVar.f261b = z;
        }
    }

    public int a(int i, int i2, int i3, int i4, int i5) {
        int listPaddingTop = getListPaddingTop();
        int listPaddingBottom = getListPaddingBottom();
        getListPaddingLeft();
        getListPaddingRight();
        int dividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return listPaddingTop + listPaddingBottom;
        }
        int i6 = listPaddingTop + listPaddingBottom;
        if (dividerHeight <= 0 || divider == null) {
            dividerHeight = 0;
        }
        int count = adapter.getCount();
        int measuredHeight = i6;
        View view = null;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i7 < count) {
            int itemViewType = adapter.getItemViewType(i7);
            if (itemViewType != i8) {
                view = null;
                i8 = itemViewType;
            }
            view = adapter.getView(i7, view, this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
                view.setLayoutParams(layoutParams);
            }
            int i10 = layoutParams.height;
            view.measure(i, i10 > 0 ? View.MeasureSpec.makeMeasureSpec(i10, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0));
            view.forceLayout();
            if (i7 > 0) {
                measuredHeight += dividerHeight;
            }
            measuredHeight += view.getMeasuredHeight();
            if (measuredHeight >= i4) {
                return (i5 < 0 || i7 <= i5 || i9 <= 0 || measuredHeight == i4) ? i4 : i9;
            }
            if (i5 >= 0 && i7 >= i5) {
                i9 = measuredHeight;
            }
            i7++;
        }
        return measuredHeight;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x01c2  */
    /* JADX WARN: Code duplicated, block: B:107:0x01df  */
    /* JADX WARN: Code duplicated, block: B:111:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:112:0x01ee  */
    /* JADX WARN: Code duplicated, block: B:114:0x01f2  */
    /* JADX WARN: Code duplicated, block: B:11:0x001d  */
    /* JADX WARN: Code duplicated, block: B:72:0x0131  */
    /* JADX WARN: Code duplicated, block: B:74:0x0146  */
    /* JADX WARN: Code duplicated, block: B:76:0x014b  */
    /* JADX WARN: Code duplicated, block: B:78:0x014f  */
    /* JADX WARN: Code duplicated, block: B:82:0x0164  */
    /* JADX WARN: Code duplicated, block: B:84:0x016a  */
    /* JADX WARN: Code duplicated, block: B:85:0x016c  */
    /* JADX WARN: Code duplicated, block: B:87:0x016f  */
    /* JADX WARN: Code duplicated, block: B:90:0x0174  */
    /* JADX WARN: Code duplicated, block: B:92:0x0178  */
    /* JADX WARN: Code duplicated, block: B:93:0x017b  */
    /* JADX WARN: Code duplicated, block: B:94:0x0181  */
    public boolean a(MotionEvent motionEvent, int i) {
        boolean z;
        View childAt;
        View childAt2;
        a.b.d.i.e eVar;
        a.b.d.i.e eVar2;
        int actionMasked;
        int i2;
        int actionMasked2 = motionEvent.getActionMasked();
        boolean z2 = true;
        if (actionMasked2 == 1) {
            z = false;
        } else {
            if (actionMasked2 != 2) {
                if (actionMasked2 != 3) {
                    z = true;
                } else {
                    z = false;
                }
                z2 = false;
                if (z || z2) {
                    this.f260c = false;
                    setPressed(false);
                    drawableStateChanged();
                    childAt2 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
                    if (childAt2 != null) {
                        childAt2.setPressed(false);
                    }
                }
                if (z) {
                    if (this.d == null) {
                        this.d = new a.b.d.i.e(this);
                    }
                    this.d.a(true);
                    eVar2 = this.d;
                    if (eVar2.q) {
                        actionMasked = motionEvent.getActionMasked();
                        if (actionMasked != 0) {
                            if (actionMasked == 1) {
                                if (eVar2.n) {
                                    eVar2.p = false;
                                } else {
                                    eVar2.f167b.a();
                                }
                            } else if (actionMasked != 2) {
                                if (actionMasked == 3) {
                                    if (eVar2.n) {
                                        eVar2.p = false;
                                    } else {
                                        eVar2.f167b.a();
                                    }
                                }
                            }
                            if (eVar2.r) {
                                boolean z3 = eVar2.p;
                            }
                        } else {
                            eVar2.o = true;
                            eVar2.m = false;
                        }
                        float fA = eVar2.a(0, motionEvent.getX(), getWidth(), eVar2.d.getWidth());
                        float fA2 = eVar2.a(1, motionEvent.getY(), getHeight(), eVar2.d.getHeight());
                        a.b.d.i.a.C0003a c0003a = eVar2.f167b;
                        c0003a.f171c = fA;
                        c0003a.d = fA2;
                        if (!eVar2.p && eVar2.b()) {
                            if (eVar2.e == null) {
                                eVar2.e = new a.b.d.i.a.b();
                            }
                            eVar2.p = true;
                            eVar2.n = true;
                            if (!eVar2.m || (i2 = eVar2.i) <= 0) {
                                eVar2.e.run();
                            } else {
                                a.b.d.h.n.a(eVar2.d, eVar2.e, i2);
                            }
                            eVar2.m = true;
                        }
                        if (eVar2.r) {
                            boolean z4 = eVar2.p;
                        }
                    }
                } else {
                    eVar = this.d;
                    if (eVar != null) {
                        eVar.a(false);
                    }
                }
                return z;
            }
            z = true;
        }
        int iFindPointerIndex = motionEvent.findPointerIndex(i);
        if (iFindPointerIndex < 0) {
            z = false;
            z2 = false;
        } else {
            int x = (int) motionEvent.getX(iFindPointerIndex);
            int y = (int) motionEvent.getY(iFindPointerIndex);
            int iPointToPosition = pointToPosition(x, y);
            if (iPointToPosition != -1) {
                View childAt3 = getChildAt(iPointToPosition - getFirstVisiblePosition());
                float f = x;
                float f2 = y;
                this.f260c = true;
                if (Build.VERSION.SDK_INT >= 21) {
                    drawableHotspotChanged(f, f2);
                }
                if (!isPressed()) {
                    setPressed(true);
                }
                layoutChildren();
                int i3 = this.mMotionPosition;
                if (i3 != -1 && (childAt = getChildAt(i3 - getFirstVisiblePosition())) != null && childAt != childAt3 && childAt.isPressed()) {
                    childAt.setPressed(false);
                }
                this.mMotionPosition = iPointToPosition;
                float left = f - childAt3.getLeft();
                float top = f2 - childAt3.getTop();
                if (Build.VERSION.SDK_INT >= 21) {
                    childAt3.drawableHotspotChanged(left, top);
                }
                if (!childAt3.isPressed()) {
                    childAt3.setPressed(true);
                }
                Drawable selector = getSelector();
                boolean z5 = (selector == null || iPointToPosition == -1) ? false : true;
                if (z5) {
                    selector.setVisible(false, false);
                }
                Rect rect = this.mSelectorRect;
                rect.set(childAt3.getLeft(), childAt3.getTop(), childAt3.getRight(), childAt3.getBottom());
                rect.left -= this.mSelectionLeftPadding;
                rect.top -= this.mSelectionTopPadding;
                rect.right += this.mSelectionRightPadding;
                rect.bottom += this.mSelectionBottomPadding;
                try {
                    boolean z6 = this.mIsChildViewEnabled.getBoolean(this);
                    if (childAt3.isEnabled() != z6) {
                        this.mIsChildViewEnabled.set(this, Boolean.valueOf(!z6));
                        if (iPointToPosition != -1) {
                            refreshDrawableState();
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (z5) {
                    Rect rect2 = this.mSelectorRect;
                    float fExactCenterX = rect2.exactCenterX();
                    float fExactCenterY = rect2.exactCenterY();
                    selector.setVisible(getVisibility() == 0, false);
                    a.b.b.a.e.a(selector, fExactCenterX, fExactCenterY);
                }
                Drawable selector2 = getSelector();
                if (selector2 != null && iPointToPosition != -1) {
                    a.b.b.a.e.a(selector2, f, f2);
                }
                setSelectorEnabled(false);
                refreshDrawableState();
                if (actionMasked2 == 1) {
                    performItemClick(childAt3, iPointToPosition, getItemIdAtPosition(iPointToPosition));
                }
                z = true;
                z2 = false;
            }
        }
        if (z) {
            this.f260c = false;
            setPressed(false);
            drawableStateChanged();
            childAt2 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
            if (childAt2 != null) {
                childAt2.setPressed(false);
            }
        } else {
            this.f260c = false;
            setPressed(false);
            drawableStateChanged();
            childAt2 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
            if (childAt2 != null) {
                childAt2.setPressed(false);
            }
        }
        if (z) {
            if (this.d == null) {
                this.d = new a.b.d.i.e(this);
            }
            this.d.a(true);
            eVar2 = this.d;
            if (eVar2.q) {
                actionMasked = motionEvent.getActionMasked();
                if (actionMasked != 0) {
                    if (actionMasked == 1) {
                        if (eVar2.n) {
                            eVar2.p = false;
                        } else {
                            eVar2.f167b.a();
                        }
                    } else if (actionMasked != 2) {
                        if (actionMasked == 3) {
                            if (eVar2.n) {
                                eVar2.p = false;
                            } else {
                                eVar2.f167b.a();
                            }
                        }
                    }
                    if (eVar2.r) {
                        boolean z7 = eVar2.p;
                    }
                } else {
                    eVar2.o = true;
                    eVar2.m = false;
                }
                float fA3 = eVar2.a(0, motionEvent.getX(), getWidth(), eVar2.d.getWidth());
                float fA4 = eVar2.a(1, motionEvent.getY(), getHeight(), eVar2.d.getHeight());
                a.b.d.i.a.C0003a c0003a2 = eVar2.f167b;
                c0003a2.f171c = fA3;
                c0003a2.d = fA4;
                if (!eVar2.p) {
                    if (eVar2.e == null) {
                        eVar2.e = new a.b.d.i.a.b();
                    }
                    eVar2.p = true;
                    eVar2.n = true;
                    if (eVar2.m) {
                        eVar2.e.run();
                    } else {
                        eVar2.e.run();
                    }
                    eVar2.m = true;
                }
                if (eVar2.r) {
                    boolean z8 = eVar2.p;
                }
            }
        } else {
            eVar = this.d;
            if (eVar != null) {
                eVar.a(false);
            }
        }
        return z;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        Drawable selector;
        if (!this.mSelectorRect.isEmpty() && (selector = getSelector()) != null) {
            selector.setBounds(this.mSelectorRect);
            selector.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        if (this.e != null) {
            return;
        }
        super.drawableStateChanged();
        setSelectorEnabled(true);
        a();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean hasFocus() {
        return this.f259b || super.hasFocus();
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return this.f259b || super.hasWindowFocus();
    }

    @Override // android.view.View
    public boolean isFocused() {
        return this.f259b || super.isFocused();
    }

    @Override // android.view.View
    public boolean isInTouchMode() {
        return (this.f259b && this.f258a) || super.isInTouchMode();
    }

    public int lookForSelectablePosition(int i, boolean z) {
        int iMin;
        ListAdapter adapter = getAdapter();
        if (adapter != null && !isInTouchMode()) {
            int count = adapter.getCount();
            if (!getAdapter().areAllItemsEnabled()) {
                if (z) {
                    iMin = Math.max(0, i);
                    while (iMin < count && !adapter.isEnabled(iMin)) {
                        iMin++;
                    }
                } else {
                    iMin = Math.min(i, count - 1);
                    while (iMin >= 0 && !adapter.isEnabled(iMin)) {
                        iMin--;
                    }
                }
                if (iMin < 0 || iMin >= count) {
                    return -1;
                }
                return iMin;
            }
            if (i >= 0 && i < count) {
                return i;
            }
        }
        return -1;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        this.e = null;
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (Build.VERSION.SDK_INT < 26) {
            return super.onHoverEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 10 && this.e == null) {
            this.e = new b();
            b bVar = this.e;
            B.this.post(bVar);
        }
        boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
        if (actionMasked == 9 || actionMasked == 7) {
            int iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            if (iPointToPosition != -1 && iPointToPosition != getSelectedItemPosition()) {
                View childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
                if (childAt.isEnabled()) {
                    setSelectionFromTop(iPointToPosition, childAt.getTop() - getTop());
                }
                a();
            }
        } else {
            setSelection(-1);
        }
        return zOnHoverEvent;
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mMotionPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        b bVar = this.e;
        if (bVar != null) {
            B b2 = B.this;
            b2.e = null;
            b2.removeCallbacks(bVar);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setListSelectionHidden(boolean z) {
        this.f258a = z;
    }

    @Override // android.widget.AbsListView
    public void setSelector(Drawable drawable) {
        this.mSelector = drawable != null ? new a(drawable) : null;
        super.setSelector(this.mSelector);
        Rect rect = new Rect();
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        this.mSelectionLeftPadding = rect.left;
        this.mSelectionTopPadding = rect.top;
        this.mSelectionRightPadding = rect.right;
        this.mSelectionBottomPadding = rect.bottom;
    }

    public final void a() {
        Drawable selector = getSelector();
        if (selector != null && this.f260c && isPressed()) {
            selector.setState(getDrawableState());
        }
    }
}
