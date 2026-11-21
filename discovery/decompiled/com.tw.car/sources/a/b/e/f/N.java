package a.b.e.f;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class N extends HorizontalScrollView implements AdapterView.OnItemSelectedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Runnable f289a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public b f290b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public E f291c;
    public Spinner d;
    public boolean e;
    public int f;
    public int g;
    public int h;
    public int i;

    private class a extends BaseAdapter {
        public a() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return N.this.f291c.getChildCount();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return ((c) N.this.f291c.getChildAt(i)).f295b;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                N n = N.this;
                return n.a(((c) n.f291c.getChildAt(i)).f295b, true);
            }
            c cVar = (c) view;
            cVar.f295b = ((c) N.this.f291c.getChildAt(i)).f295b;
            cVar.a();
            return view;
        }
    }

    private class b implements View.OnClickListener {
        public b() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ((c) view).f295b.e();
            int childCount = N.this.f291c.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = N.this.f291c.getChildAt(i);
                childAt.setSelected(childAt == view);
            }
        }
    }

    private class c extends LinearLayout {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final int[] f294a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public a.b.e.a.b f295b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public TextView f296c;
        public ImageView d;
        public View e;

        public c(Context context, a.b.e.a.b bVar, boolean z) {
            super(context, null, a.b.e.b.a.actionBarTabStyle);
            this.f294a = new int[]{R.attr.background};
            this.f295b = bVar;
            ha haVarA = ha.a(context, null, this.f294a, a.b.e.b.a.actionBarTabStyle, 0);
            if (haVarA.e(0)) {
                setBackgroundDrawable(haVarA.b(0));
            }
            haVarA.f337b.recycle();
            if (z) {
                setGravity(8388627);
            }
            a();
        }

        public void a() {
            a.b.e.a.b bVar = this.f295b;
            View viewB = bVar.b();
            if (viewB != null) {
                ViewParent parent = viewB.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(viewB);
                    }
                    addView(viewB);
                }
                this.e = viewB;
                TextView textView = this.f296c;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                ImageView imageView = this.d;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.d.setImageDrawable(null);
                    return;
                }
                return;
            }
            View view = this.e;
            if (view != null) {
                removeView(view);
                this.e = null;
            }
            Drawable drawableC = bVar.c();
            CharSequence charSequenceD = bVar.d();
            if (drawableC != null) {
                if (this.d == null) {
                    C0023n c0023n = new C0023n(getContext(), null, 0);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    c0023n.setLayoutParams(layoutParams);
                    addView(c0023n, 0);
                    this.d = c0023n;
                }
                this.d.setImageDrawable(drawableC);
                this.d.setVisibility(0);
            } else {
                ImageView imageView2 = this.d;
                if (imageView2 != null) {
                    imageView2.setVisibility(8);
                    this.d.setImageDrawable(null);
                }
            }
            boolean z = !TextUtils.isEmpty(charSequenceD);
            if (z) {
                if (this.f296c == null) {
                    C0031w c0031w = new C0031w(getContext(), null, a.b.e.b.a.actionBarTabTextStyle);
                    c0031w.setEllipsize(TextUtils.TruncateAt.END);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams2.gravity = 16;
                    c0031w.setLayoutParams(layoutParams2);
                    addView(c0031w);
                    this.f296c = c0031w;
                }
                this.f296c.setText(charSequenceD);
                this.f296c.setVisibility(0);
            } else {
                TextView textView2 = this.f296c;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                    this.f296c.setText((CharSequence) null);
                }
            }
            ImageView imageView3 = this.d;
            if (imageView3 != null) {
                imageView3.setContentDescription(bVar.a());
            }
            a.b.b.a.e.a(this, z ? null : bVar.a());
        }

        @Override // android.view.View
        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(a.b.e.a.b.class.getName());
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(a.b.e.a.b.class.getName());
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (N.this.f > 0) {
                int measuredWidth = getMeasuredWidth();
                int i3 = N.this.f;
                if (measuredWidth > i3) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), i2);
                }
            }
        }

        @Override // android.view.View
        public void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (z2 && z) {
                sendAccessibilityEvent(4);
            }
        }
    }

    static {
        new DecelerateInterpolator();
    }

    public c a(a.b.e.a.b bVar, boolean z) {
        c cVar = new c(getContext(), bVar, z);
        if (z) {
            cVar.setBackgroundDrawable(null);
            cVar.setLayoutParams(new AbsListView.LayoutParams(-1, this.h));
        } else {
            cVar.setFocusable(true);
            if (this.f290b == null) {
                this.f290b = new b();
            }
            cVar.setOnClickListener(this.f290b);
        }
        return cVar;
    }

    public void a(int i) {
        View childAt = this.f291c.getChildAt(i);
        Runnable runnable = this.f289a;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        this.f289a = new M(this, childAt);
        post(this.f289a);
    }

    public final boolean a() {
        Spinner spinner = this.d;
        return spinner != null && spinner.getParent() == this;
    }

    public final boolean b() {
        Spinner spinner = this.d;
        if (!(spinner != null && spinner.getParent() == this)) {
            return false;
        }
        removeView(this.d);
        addView(this.f291c, new ViewGroup.LayoutParams(-2, -1));
        setTabSelected(this.d.getSelectedItemPosition());
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Runnable runnable = this.f289a;
        if (runnable != null) {
            post(runnable);
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Context context = getContext();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, a.b.e.b.j.ActionBar, a.b.e.b.a.actionBarStyle, 0);
        int layoutDimension = typedArrayObtainStyledAttributes.getLayoutDimension(a.b.e.b.j.ActionBar_height, 0);
        Resources resources = context.getResources();
        if (!context.getResources().getBoolean(a.b.e.b.b.abc_action_bar_embed_tabs)) {
            layoutDimension = Math.min(layoutDimension, resources.getDimensionPixelSize(a.b.e.b.d.abc_action_bar_stacked_max_height));
        }
        typedArrayObtainStyledAttributes.recycle();
        setContentHeight(layoutDimension);
        this.g = context.getResources().getDimensionPixelSize(a.b.e.b.d.abc_action_bar_stacked_tab_max_width);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.f289a;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        ((c) view).f295b.e();
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00bc  */
    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        boolean z = mode == 1073741824;
        setFillViewport(z);
        int childCount = this.f291c.getChildCount();
        if (childCount <= 1 || !(mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            this.f = -1;
        } else {
            if (childCount > 2) {
                this.f = (int) (View.MeasureSpec.getSize(i) * 0.4f);
            } else {
                this.f = View.MeasureSpec.getSize(i) / 2;
            }
            this.f = Math.min(this.f, this.g);
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.h, 1073741824);
        if (!z && this.e) {
            this.f291c.measure(0, iMakeMeasureSpec);
            if (this.f291c.getMeasuredWidth() <= View.MeasureSpec.getSize(i)) {
                b();
            } else if (!a()) {
                if (this.d == null) {
                    C0026q c0026q = new C0026q(getContext(), null, a.b.e.b.a.actionDropDownStyle);
                    c0026q.setLayoutParams(new E.a(-2, -1));
                    c0026q.setOnItemSelectedListener(this);
                    this.d = c0026q;
                }
                removeView(this.f291c);
                addView(this.d, new ViewGroup.LayoutParams(-2, -1));
                if (this.d.getAdapter() == null) {
                    this.d.setAdapter((SpinnerAdapter) new a());
                }
                Runnable runnable = this.f289a;
                if (runnable != null) {
                    removeCallbacks(runnable);
                    this.f289a = null;
                }
                this.d.setSelection(this.i);
            }
        } else {
            b();
        }
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i, iMakeMeasureSpec);
        int measuredWidth2 = getMeasuredWidth();
        if (!z || measuredWidth == measuredWidth2) {
            return;
        }
        setTabSelected(this.i);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void setAllowCollapse(boolean z) {
        this.e = z;
    }

    public void setContentHeight(int i) {
        this.h = i;
        requestLayout();
    }

    public void setTabSelected(int i) {
        this.i = i;
        int childCount = this.f291c.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = this.f291c.getChildAt(i2);
            boolean z = i2 == i;
            childAt.setSelected(z);
            if (z) {
                a(i);
            }
            i2++;
        }
        Spinner spinner = this.d;
        if (spinner == null || i < 0) {
            return;
        }
        spinner.setSelection(i);
    }
}
