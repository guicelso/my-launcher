package a.b.e.f;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class v implements a.b.e.e.c.o {
    public static Method B;
    public static Method C;
    public static Method D;
    public PopupWindow A;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Context f326b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ListAdapter f327c;
    public q d;
    public int g;
    public int h;
    public boolean j;
    public boolean k;
    public boolean l;
    public DataSetObserver p;
    public View q;
    public AdapterView.OnItemClickListener r;
    public final Handler w;
    public Rect y;
    public boolean z;
    public int e = -2;
    public int f = -2;
    public int i = 1002;
    public int m = 0;
    public int n = Integer.MAX_VALUE;
    public int o = 0;
    public final e s = new e();
    public final d t = new d();
    public final c u = new c();
    public final a v = new a();
    public final Rect x = new Rect();

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            q qVar = v.this.d;
            if (qVar != null) {
                qVar.setListSelectionHidden(true);
                qVar.requestLayout();
            }
        }
    }

    public class b extends DataSetObserver {
        public b() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            if (v.this.c()) {
                v.this.f();
            }
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            v.this.i();
        }
    }

    public class c implements AbsListView.OnScrollListener {
        public c() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i == 1) {
                if ((v.this.A.getInputMethodMode() == 2) || v.this.A.getContentView() == null) {
                    return;
                }
                v vVar = v.this;
                vVar.w.removeCallbacks(vVar.s);
                v.this.s.run();
            }
        }
    }

    public class d implements View.OnTouchListener {
        public d() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            PopupWindow popupWindow;
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && (popupWindow = v.this.A) != null && popupWindow.isShowing() && x >= 0 && x < v.this.A.getWidth() && y >= 0 && y < v.this.A.getHeight()) {
                v vVar = v.this;
                vVar.w.postDelayed(vVar.s, 250L);
                return false;
            }
            if (action != 1) {
                return false;
            }
            v vVar2 = v.this;
            vVar2.w.removeCallbacks(vVar2.s);
            return false;
        }
    }

    public class e implements Runnable {
        public e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            q qVar = v.this.d;
            if (qVar != null) {
                int i = a.b.d.h.j.f151a;
                if (!qVar.isAttachedToWindow() || v.this.d.getCount() <= v.this.d.getChildCount()) {
                    return;
                }
                int childCount = v.this.d.getChildCount();
                v vVar = v.this;
                if (childCount <= vVar.n) {
                    vVar.A.setInputMethodMode(2);
                    v.this.f();
                }
            }
        }
    }

    static {
        Class cls = Boolean.TYPE;
        try {
            B = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", cls);
        } catch (NoSuchMethodException unused) {
            Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
            C = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, cls);
        } catch (NoSuchMethodException unused2) {
            Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        try {
            D = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
        } catch (NoSuchMethodException unused3) {
            Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
        }
    }

    public v(Context context, AttributeSet attributeSet, int i, int i2) {
        this.f326b = context;
        this.w = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.b.e.b.b.n, i, i2);
        this.g = typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, 0);
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, 0);
        this.h = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.j = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        k kVar = new k(context, attributeSet, i, i2);
        this.A = kVar;
        kVar.setInputMethodMode(1);
    }

    @Override // a.b.e.e.c.o
    public boolean c() {
        return this.A.isShowing();
    }

    @Override // a.b.e.e.c.o
    public ListView d() {
        return this.d;
    }

    public q e(Context context, boolean z) {
        throw null;
    }

    @Override // a.b.e.e.c.o
    public void f() {
        int i;
        int iIntValue;
        int i2;
        int paddingBottom;
        q qVar;
        if (this.d == null) {
            q qVarE = e(this.f326b, !this.z);
            this.d = qVarE;
            qVarE.setAdapter(this.f327c);
            this.d.setOnItemClickListener(this.r);
            this.d.setFocusable(true);
            this.d.setFocusableInTouchMode(true);
            this.d.setOnItemSelectedListener(new u(this));
            this.d.setOnScrollListener(this.u);
            this.A.setContentView(this.d);
        }
        Drawable background = this.A.getBackground();
        if (background != null) {
            background.getPadding(this.x);
            Rect rect = this.x;
            int i3 = rect.top;
            i = rect.bottom + i3;
            if (!this.j) {
                this.h = -i3;
            }
        } else {
            this.x.setEmpty();
            i = 0;
        }
        boolean z = this.A.getInputMethodMode() == 2;
        View view = this.q;
        int i4 = this.h;
        Method method = C;
        if (method != null) {
            try {
                iIntValue = ((Integer) method.invoke(this.A, view, Integer.valueOf(i4), Boolean.valueOf(z))).intValue();
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
                iIntValue = this.A.getMaxAvailableHeight(view, i4);
            }
        } else {
            iIntValue = this.A.getMaxAvailableHeight(view, i4);
        }
        if (this.e == -1) {
            paddingBottom = iIntValue + i;
        } else {
            int i5 = this.f;
            if (i5 != -2) {
                i2 = 1073741824;
                if (i5 == -1) {
                    int i6 = this.f326b.getResources().getDisplayMetrics().widthPixels;
                    Rect rect2 = this.x;
                    i5 = i6 - (rect2.left + rect2.right);
                }
            } else {
                int i7 = this.f326b.getResources().getDisplayMetrics().widthPixels;
                Rect rect3 = this.x;
                i5 = i7 - (rect3.left + rect3.right);
                i2 = Integer.MIN_VALUE;
            }
            int iA = this.d.a(View.MeasureSpec.makeMeasureSpec(i5, i2), iIntValue - 0, -1);
            paddingBottom = iA + (iA > 0 ? this.d.getPaddingBottom() + this.d.getPaddingTop() + i + 0 : 0);
        }
        boolean z2 = this.A.getInputMethodMode() == 2;
        PopupWindow popupWindow = this.A;
        int i8 = this.i;
        if (Build.VERSION.SDK_INT >= 23) {
            popupWindow.setWindowLayoutType(i8);
        } else {
            if (!a.b.a.a.d) {
                try {
                    Method declaredMethod = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE);
                    a.b.a.a.f2c = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (Exception unused2) {
                }
                a.b.a.a.d = true;
            }
            Method method2 = a.b.a.a.f2c;
            if (method2 != null) {
                try {
                    method2.invoke(popupWindow, Integer.valueOf(i8));
                } catch (Exception unused3) {
                }
            }
        }
        if (this.A.isShowing()) {
            View view2 = this.q;
            int i9 = a.b.d.h.j.f151a;
            if (view2.isAttachedToWindow()) {
                int width = this.f;
                if (width == -1) {
                    width = -1;
                } else if (width == -2) {
                    width = this.q.getWidth();
                }
                int i10 = this.e;
                if (i10 == -1) {
                    if (!z2) {
                        paddingBottom = -1;
                    }
                    if (z2) {
                        this.A.setWidth(this.f == -1 ? -1 : 0);
                        this.A.setHeight(0);
                    } else {
                        this.A.setWidth(this.f == -1 ? -1 : 0);
                        this.A.setHeight(-1);
                    }
                } else if (i10 != -2) {
                    paddingBottom = i10;
                }
                this.A.setOutsideTouchable(true);
                this.A.update(this.q, this.g, this.h, width < 0 ? -1 : width, paddingBottom < 0 ? -1 : paddingBottom);
                return;
            }
            return;
        }
        int width2 = this.f;
        if (width2 == -1) {
            width2 = -1;
        } else if (width2 == -2) {
            width2 = this.q.getWidth();
        }
        int i11 = this.e;
        if (i11 == -1) {
            paddingBottom = -1;
        } else if (i11 != -2) {
            paddingBottom = i11;
        }
        this.A.setWidth(width2);
        this.A.setHeight(paddingBottom);
        Method method3 = B;
        if (method3 != null) {
            try {
                method3.invoke(this.A, Boolean.TRUE);
            } catch (Exception unused4) {
                Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
        this.A.setOutsideTouchable(true);
        this.A.setTouchInterceptor(this.t);
        if (this.l) {
            a.b.a.a.P(this.A, this.k);
        }
        Method method4 = D;
        if (method4 != null) {
            try {
                method4.invoke(this.A, this.y);
            } catch (Exception e2) {
                Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e2);
            }
        }
        this.A.showAsDropDown(this.q, this.g, this.h, this.m);
        this.d.setSelection(-1);
        if ((!this.z || this.d.isInTouchMode()) && (qVar = this.d) != null) {
            qVar.setListSelectionHidden(true);
            qVar.requestLayout();
        }
        if (this.z) {
            return;
        }
        this.w.post(this.v);
    }

    public void g(ListAdapter listAdapter) {
        DataSetObserver dataSetObserver = this.p;
        if (dataSetObserver == null) {
            this.p = new b();
        } else {
            ListAdapter listAdapter2 = this.f327c;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.f327c = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.p);
        }
        q qVar = this.d;
        if (qVar != null) {
            qVar.setAdapter(this.f327c);
        }
    }

    public void h(int i) {
        Drawable background = this.A.getBackground();
        if (background == null) {
            this.f = i;
            return;
        }
        background.getPadding(this.x);
        Rect rect = this.x;
        this.f = rect.left + rect.right + i;
    }

    @Override // a.b.e.e.c.o
    public void i() {
        this.A.dismiss();
        this.A.setContentView(null);
        this.d = null;
        this.w.removeCallbacks(this.s);
    }

    public void j(boolean z) {
        this.z = z;
        this.A.setFocusable(z);
    }

    public void k(int i) {
        this.h = i;
        this.j = true;
    }
}
