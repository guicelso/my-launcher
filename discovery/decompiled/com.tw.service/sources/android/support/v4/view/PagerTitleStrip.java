package android.support.v4.view;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
@w
public class PagerTitleStrip extends ViewGroup {
    private static final int[] ATTRS = {R.attr.textAppearance, R.attr.textSize, R.attr.textColor, R.attr.gravity};
    private static final int[] Cg = {R.attr.textAllCaps};
    private WeakReference Ag;
    private int Bg;
    private int mGravity;
    int mTextColor;
    ViewPager qg;
    TextView rg;
    TextView sg;
    TextView tg;
    private int ug;
    float vg;
    private int wg;
    private boolean xg;
    private boolean yg;
    private final n zg;

    public PagerTitleStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ug = -1;
        this.vg = -1.0f;
        this.zg = new n(this);
        TextView textView = new TextView(context);
        this.rg = textView;
        addView(textView);
        TextView textView2 = new TextView(context);
        this.sg = textView2;
        addView(textView2);
        TextView textView3 = new TextView(context);
        this.tg = textView3;
        addView(textView3);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
        boolean z = false;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            TextView textView4 = this.rg;
            int i = Build.VERSION.SDK_INT;
            textView4.setTextAppearance(resourceId);
            TextView textView5 = this.sg;
            int i2 = Build.VERSION.SDK_INT;
            textView5.setTextAppearance(resourceId);
            TextView textView6 = this.tg;
            int i3 = Build.VERSION.SDK_INT;
            textView6.setTextAppearance(resourceId);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0);
        if (dimensionPixelSize != 0) {
            setTextSize(0, dimensionPixelSize);
        }
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            int color = typedArrayObtainStyledAttributes.getColor(2, 0);
            this.rg.setTextColor(color);
            this.sg.setTextColor(color);
            this.tg.setTextColor(color);
        }
        this.mGravity = typedArrayObtainStyledAttributes.getInteger(3, 80);
        typedArrayObtainStyledAttributes.recycle();
        this.mTextColor = this.sg.getTextColors().getDefaultColor();
        d(0.6f);
        this.rg.setEllipsize(TextUtils.TruncateAt.END);
        this.sg.setEllipsize(TextUtils.TruncateAt.END);
        this.tg.setEllipsize(TextUtils.TruncateAt.END);
        if (resourceId != 0) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, Cg);
            z = typedArrayObtainStyledAttributes2.getBoolean(0, false);
            typedArrayObtainStyledAttributes2.recycle();
        }
        TextView textView7 = this.rg;
        if (z) {
            a(textView7);
            a(this.sg);
            a(this.tg);
        } else {
            textView7.setSingleLine();
            this.sg.setSingleLine();
            this.tg.setSingleLine();
        }
        this.wg = (int) (context.getResources().getDisplayMetrics().density * 16.0f);
    }

    private static void a(TextView textView) {
        textView.setTransformationMethod(new o(textView.getContext()));
    }

    public int U() {
        return this.wg;
    }

    void a(int i, float f, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (i != this.ug) {
            a(i, this.qg.getAdapter());
        } else if (!z && f == this.vg) {
            return;
        }
        this.yg = true;
        int measuredWidth = this.rg.getMeasuredWidth();
        int measuredWidth2 = this.sg.getMeasuredWidth();
        int measuredWidth3 = this.tg.getMeasuredWidth();
        int i6 = measuredWidth2 / 2;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i7 = paddingRight + i6;
        int i8 = (width - (paddingLeft + i6)) - i7;
        float f2 = 0.5f + f;
        if (f2 > 1.0f) {
            f2 -= 1.0f;
        }
        int i9 = ((width - i7) - ((int) (i8 * f2))) - i6;
        int i10 = measuredWidth2 + i9;
        int baseline = this.rg.getBaseline();
        int baseline2 = this.sg.getBaseline();
        int baseline3 = this.tg.getBaseline();
        int iMax = Math.max(Math.max(baseline, baseline2), baseline3);
        int i11 = iMax - baseline;
        int i12 = iMax - baseline2;
        int i13 = iMax - baseline3;
        int iMax2 = Math.max(Math.max(this.rg.getMeasuredHeight() + i11, this.sg.getMeasuredHeight() + i12), this.tg.getMeasuredHeight() + i13);
        int i14 = this.mGravity & 112;
        if (i14 != 16) {
            if (i14 != 80) {
                i3 = i11 + paddingTop;
                i4 = i12 + paddingTop;
                i5 = paddingTop + i13;
            } else {
                i2 = (height - paddingBottom) - iMax2;
            }
            TextView textView = this.sg;
            textView.layout(i9, i4, i10, textView.getMeasuredHeight() + i4);
            int iMin = Math.min(paddingLeft, (i9 - this.wg) - measuredWidth);
            TextView textView2 = this.rg;
            textView2.layout(iMin, i3, measuredWidth + iMin, textView2.getMeasuredHeight() + i3);
            int iMax3 = Math.max((width - paddingRight) - measuredWidth3, i10 + this.wg);
            TextView textView3 = this.tg;
            textView3.layout(iMax3, i5, iMax3 + measuredWidth3, textView3.getMeasuredHeight() + i5);
            this.vg = f;
            this.yg = false;
        }
        i2 = (((height - paddingTop) - paddingBottom) - iMax2) / 2;
        i3 = i11 + i2;
        i4 = i12 + i2;
        i5 = i2 + i13;
        TextView textView4 = this.sg;
        textView4.layout(i9, i4, i10, textView4.getMeasuredHeight() + i4);
        int iMin2 = Math.min(paddingLeft, (i9 - this.wg) - measuredWidth);
        TextView textView5 = this.rg;
        textView5.layout(iMin2, i3, measuredWidth + iMin2, textView5.getMeasuredHeight() + i3);
        int iMax4 = Math.max((width - paddingRight) - measuredWidth3, i10 + this.wg);
        TextView textView6 = this.tg;
        textView6.layout(iMax4, i5, iMax4 + measuredWidth3, textView6.getMeasuredHeight() + i5);
        this.vg = f;
        this.yg = false;
    }

    void a(int i, k kVar) {
        int count = kVar != null ? kVar.getCount() : 0;
        this.xg = true;
        CharSequence pageTitle = null;
        this.rg.setText((i < 1 || kVar == null) ? null : kVar.getPageTitle(i - 1));
        this.sg.setText((kVar == null || i >= count) ? null : kVar.getPageTitle(i));
        int i2 = i + 1;
        if (i2 < count && kVar != null) {
            pageTitle = kVar.getPageTitle(i2);
        }
        this.tg.setText(pageTitle);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((getWidth() - getPaddingLeft()) - getPaddingRight()) * 0.8f)), Integer.MIN_VALUE);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE);
        this.rg.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.sg.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.tg.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.ug = i;
        if (!this.yg) {
            a(i, this.vg, false);
        }
        this.xg = false;
    }

    void a(k kVar, k kVar2) {
        if (kVar != null) {
            kVar.unregisterDataSetObserver(this.zg);
            this.Ag = null;
        }
        if (kVar2 != null) {
            kVar2.registerDataSetObserver(this.zg);
            this.Ag = new WeakReference(kVar2);
        }
        ViewPager viewPager = this.qg;
        if (viewPager != null) {
            this.ug = -1;
            this.vg = -1.0f;
            a(viewPager.getCurrentItem(), kVar2);
            requestLayout();
        }
    }

    public void d(float f) {
        this.Bg = ((int) (f * 255.0f)) & 255;
        int i = (this.Bg << 24) | (this.mTextColor & 16777215);
        this.rg.setTextColor(i);
        this.tg.setTextColor(i);
    }

    int getMinHeight() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicHeight();
        }
        return 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (!(parent instanceof ViewPager)) {
            throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
        }
        ViewPager viewPager = (ViewPager) parent;
        k adapter = viewPager.getAdapter();
        viewPager.a((A) this.zg);
        viewPager.a((z) this.zg);
        this.qg = viewPager;
        WeakReference weakReference = this.Ag;
        a(weakReference != null ? (k) weakReference.get() : null, adapter);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPager viewPager = this.qg;
        if (viewPager != null) {
            a(viewPager.getAdapter(), (k) null);
            this.qg.a((A) null);
            this.qg.b(this.zg);
            this.qg = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.qg != null) {
            float f = this.vg;
            if (f < 0.0f) {
                f = 0.0f;
            }
            a(this.ug, f, true);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int iMax;
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, paddingBottom, -2);
        int size = View.MeasureSpec.getSize(i);
        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i, (int) (size * 0.2f), -2);
        this.rg.measure(childMeasureSpec2, childMeasureSpec);
        this.sg.measure(childMeasureSpec2, childMeasureSpec);
        this.tg.measure(childMeasureSpec2, childMeasureSpec);
        if (View.MeasureSpec.getMode(i2) == 1073741824) {
            iMax = View.MeasureSpec.getSize(i2);
        } else {
            iMax = Math.max(getMinHeight(), this.sg.getMeasuredHeight() + paddingBottom);
        }
        setMeasuredDimension(size, View.resolveSizeAndState(iMax, i2, this.sg.getMeasuredState() << 16));
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.xg) {
            return;
        }
        super.requestLayout();
    }

    public void setTextSize(int i, float f) {
        this.rg.setTextSize(i, f);
        this.sg.setTextSize(i, f);
        this.tg.setTextSize(i, f);
    }

    public void u(int i) {
        this.wg = i;
        requestLayout();
    }
}
