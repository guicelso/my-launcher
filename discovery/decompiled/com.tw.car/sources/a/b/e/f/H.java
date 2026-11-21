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
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class H implements a.b.e.e.a.w {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static Method f275a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static Method f276b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static Method f277c;
    public final Handler D;
    public Rect F;
    public boolean G;
    public PopupWindow H;
    public Context d;
    public ListAdapter e;
    public B f;
    public int i;
    public int j;
    public boolean l;
    public boolean m;
    public boolean n;
    public View s;
    public DataSetObserver u;
    public View v;
    public Drawable w;
    public AdapterView.OnItemClickListener x;
    public AdapterView.OnItemSelectedListener y;
    public int g = -2;
    public int h = -2;
    public int k = 1002;
    public int o = 0;
    public boolean p = false;
    public boolean q = false;
    public int r = Integer.MAX_VALUE;
    public int t = 0;
    public final e z = new e();
    public final d A = new d();
    public final c B = new c();
    public final a C = new a();
    public final Rect E = new Rect();

    private class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            B b2 = H.this.f;
            if (b2 != null) {
                b2.setListSelectionHidden(true);
                b2.requestLayout();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public class b extends DataSetObserver {
        public b() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            if (H.this.isShowing()) {
                H.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            H.this.dismiss();
        }
    }

    private class c implements AbsListView.OnScrollListener {
        public c() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i == 1) {
                if ((H.this.H.getInputMethodMode() == 2) || H.this.H.getContentView() == null) {
                    return;
                }
                H h = H.this;
                h.D.removeCallbacks(h.z);
                e eVar = H.this.z;
                B b2 = H.this.f;
                if (b2 == null || !a.b.d.h.n.f(b2) || H.this.f.getCount() <= H.this.f.getChildCount()) {
                    return;
                }
                int childCount = H.this.f.getChildCount();
                H h2 = H.this;
                if (childCount <= h2.r) {
                    h2.H.setInputMethodMode(2);
                    H.this.show();
                }
            }
        }
    }

    private class d implements View.OnTouchListener {
        public d() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            PopupWindow popupWindow;
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && (popupWindow = H.this.H) != null && popupWindow.isShowing() && x >= 0 && x < H.this.H.getWidth() && y >= 0 && y < H.this.H.getHeight()) {
                H h = H.this;
                h.D.postDelayed(h.z, 250L);
                return false;
            }
            if (action != 1) {
                return false;
            }
            H h2 = H.this;
            h2.D.removeCallbacks(h2.z);
            return false;
        }
    }

    private class e implements Runnable {
        public e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            B b2 = H.this.f;
            if (b2 == null || !a.b.d.h.n.f(b2) || H.this.f.getCount() <= H.this.f.getChildCount()) {
                return;
            }
            int childCount = H.this.f.getChildCount();
            H h = H.this;
            if (childCount <= h.r) {
                h.H.setInputMethodMode(2);
                H.this.show();
            }
        }
    }

    static {
        try {
            f275a = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
        } catch (NoSuchMethodException unused) {
            Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
            f276b = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
        } catch (NoSuchMethodException unused2) {
            Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        try {
            f277c = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
        } catch (NoSuchMethodException unused3) {
            Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
        }
    }

    public H(Context context, AttributeSet attributeSet, int i, int i2) {
        this.d = context;
        this.D = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.b.e.b.j.ListPopupWindow, i, i2);
        this.i = typedArrayObtainStyledAttributes.getDimensionPixelOffset(a.b.e.b.j.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.j = typedArrayObtainStyledAttributes.getDimensionPixelOffset(a.b.e.b.j.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.j != 0) {
            this.l = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        this.H = new C0024o(context, attributeSet, i, i2);
        this.H.setInputMethodMode(1);
    }

    public B a(Context context, boolean z) {
        return new B(context, z);
    }

    @Override // a.b.e.e.a.w
    public ListView a() {
        return this.f;
    }

    public void a(int i) {
        Drawable background = this.H.getBackground();
        if (background == null) {
            this.h = i;
            return;
        }
        background.getPadding(this.E);
        Rect rect = this.E;
        this.h = rect.left + rect.right + i;
    }

    public void a(Rect rect) {
        this.F = rect;
    }

    public void a(Drawable drawable) {
        this.H.setBackgroundDrawable(drawable);
    }

    public void a(boolean z) {
        this.G = z;
        this.H.setFocusable(z);
    }

    public Drawable b() {
        return this.H.getBackground();
    }

    public void b(int i) {
        this.j = i;
        this.l = true;
    }

    @Override // a.b.e.e.a.w
    public void dismiss() {
        this.H.dismiss();
        View view = this.s;
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.s);
            }
        }
        this.H.setContentView(null);
        this.f = null;
        this.D.removeCallbacks(this.z);
    }

    @Override // a.b.e.e.a.w
    public boolean isShowing() {
        return this.H.isShowing();
    }

    /* JADX WARN: Code duplicated, block: B:55:0x014d A[PHI: r2
      0x014d: PHI (r2v23 int) = (r2v0 int), (r2v27 int) binds: [B:51:0x0146, B:53:0x014a] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // a.b.e.e.a.w
    public void show() {
        int measuredHeight;
        int i;
        int iIntValue;
        int i2;
        B b2;
        View view;
        int i3;
        int i4 = Integer.MIN_VALUE;
        if (this.f == null) {
            Context context = this.d;
            new F(this);
            this.f = a(context, !this.G);
            Drawable drawable = this.w;
            if (drawable != null) {
                this.f.setSelector(drawable);
            }
            this.f.setAdapter(this.e);
            this.f.setOnItemClickListener(this.x);
            this.f.setFocusable(true);
            this.f.setFocusableInTouchMode(true);
            this.f.setOnItemSelectedListener(new G(this));
            this.f.setOnScrollListener(this.B);
            AdapterView.OnItemSelectedListener onItemSelectedListener = this.y;
            if (onItemSelectedListener != null) {
                this.f.setOnItemSelectedListener(onItemSelectedListener);
            }
            B b3 = this.f;
            View view2 = this.s;
            if (view2 != null) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                int i5 = this.t;
                if (i5 == 0) {
                    linearLayout.addView(view2);
                    linearLayout.addView(b3, layoutParams);
                } else if (i5 != 1) {
                    StringBuilder sbA = c.a.a.a.a.a("Invalid hint position ");
                    sbA.append(this.t);
                    Log.e("ListPopupWindow", sbA.toString());
                } else {
                    linearLayout.addView(b3, layoutParams);
                    linearLayout.addView(view2);
                }
                int i6 = this.h;
                if (i6 >= 0) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    i6 = 0;
                    i3 = 0;
                }
                view2.measure(View.MeasureSpec.makeMeasureSpec(i6, i3), 0);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view2.getLayoutParams();
                measuredHeight = view2.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
                view = linearLayout;
            } else {
                measuredHeight = 0;
                view = b3;
            }
            this.H.setContentView(view);
        } else {
            View view3 = this.s;
            if (view3 != null) {
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view3.getLayoutParams();
                measuredHeight = view3.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
            } else {
                measuredHeight = 0;
            }
        }
        Drawable background = this.H.getBackground();
        if (background != null) {
            background.getPadding(this.E);
            Rect rect = this.E;
            int i7 = rect.top;
            i = rect.bottom + i7;
            if (!this.l) {
                this.j = -i7;
            }
        } else {
            this.E.setEmpty();
            i = 0;
        }
        boolean z = this.H.getInputMethodMode() == 2;
        View view4 = this.v;
        int i8 = this.j;
        Method method = f276b;
        if (method != null) {
            try {
                iIntValue = ((Integer) method.invoke(this.H, view4, Integer.valueOf(i8), Boolean.valueOf(z))).intValue();
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
                iIntValue = this.H.getMaxAvailableHeight(view4, i8);
            }
        } else {
            iIntValue = this.H.getMaxAvailableHeight(view4, i8);
        }
        if (this.p || this.g == -1) {
            i2 = iIntValue + i;
        } else {
            int i9 = this.h;
            if (i9 != -2) {
                i4 = 1073741824;
                if (i9 == -1) {
                    int i10 = this.d.getResources().getDisplayMetrics().widthPixels;
                    Rect rect2 = this.E;
                    i9 = i10 - (rect2.left + rect2.right);
                }
            } else {
                int i11 = this.d.getResources().getDisplayMetrics().widthPixels;
                Rect rect3 = this.E;
                i9 = i11 - (rect3.left + rect3.right);
            }
            int iA = this.f.a(View.MeasureSpec.makeMeasureSpec(i9, i4), 0, -1, iIntValue - measuredHeight, -1);
            if (iA > 0) {
                measuredHeight += this.f.getPaddingBottom() + this.f.getPaddingTop() + i;
            }
            i2 = iA + measuredHeight;
        }
        boolean z2 = this.H.getInputMethodMode() == 2;
        PopupWindow popupWindow = this.H;
        int i12 = this.k;
        if (Build.VERSION.SDK_INT >= 23) {
            popupWindow.setWindowLayoutType(i12);
        } else {
            if (!a.b.b.a.e.d) {
                try {
                    a.b.b.a.e.f12c = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE);
                    a.b.b.a.e.f12c.setAccessible(true);
                } catch (Exception unused2) {
                }
                a.b.b.a.e.d = true;
            }
            Method method2 = a.b.b.a.e.f12c;
            if (method2 != null) {
                try {
                    method2.invoke(popupWindow, Integer.valueOf(i12));
                } catch (Exception unused3) {
                }
            }
        }
        if (this.H.isShowing()) {
            if (a.b.d.h.n.f(this.v)) {
                int width = this.h;
                if (width == -1) {
                    width = -1;
                } else if (width == -2) {
                    width = this.v.getWidth();
                }
                int i13 = this.g;
                if (i13 == -1) {
                    if (!z2) {
                        i2 = -1;
                    }
                    if (z2) {
                        this.H.setWidth(this.h == -1 ? -1 : 0);
                        this.H.setHeight(0);
                    } else {
                        this.H.setWidth(this.h == -1 ? -1 : 0);
                        this.H.setHeight(-1);
                    }
                } else if (i13 != -2) {
                    i2 = i13;
                }
                this.H.setOutsideTouchable((this.q || this.p) ? false : true);
                this.H.update(this.v, this.i, this.j, width < 0 ? -1 : width, i2 < 0 ? -1 : i2);
                return;
            }
            return;
        }
        int width2 = this.h;
        if (width2 == -1) {
            width2 = -1;
        } else if (width2 == -2) {
            width2 = this.v.getWidth();
        }
        int i14 = this.g;
        if (i14 == -1) {
            i2 = -1;
        } else if (i14 != -2) {
            i2 = i14;
        }
        this.H.setWidth(width2);
        this.H.setHeight(i2);
        Method method3 = f275a;
        if (method3 != null) {
            try {
                method3.invoke(this.H, true);
            } catch (Exception unused4) {
                Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
        this.H.setOutsideTouchable((this.q || this.p) ? false : true);
        this.H.setTouchInterceptor(this.A);
        if (this.n) {
            a.b.b.a.e.a(this.H, this.m);
        }
        Method method4 = f277c;
        if (method4 != null) {
            try {
                method4.invoke(this.H, this.F);
            } catch (Exception e2) {
                Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e2);
            }
        }
        PopupWindow popupWindow2 = this.H;
        View view5 = this.v;
        int i15 = this.i;
        int i16 = this.j;
        int i17 = this.o;
        int i18 = Build.VERSION.SDK_INT;
        popupWindow2.showAsDropDown(view5, i15, i16, i17);
        this.f.setSelection(-1);
        if ((!this.G || this.f.isInTouchMode()) && (b2 = this.f) != null) {
            b2.setListSelectionHidden(true);
            b2.requestLayout();
        }
        if (this.G) {
            return;
        }
        this.D.post(this.C);
    }
}
