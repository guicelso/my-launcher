package android.support.v4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* JADX INFO: loaded from: classes.dex */
public class PagerTabStrip extends PagerTitleStrip {
    private float Af;
    private float Bf;
    private int Dg;
    private int Eg;
    private int Fg;
    private int Gg;
    private int Hg;
    private int Ig;
    private final Paint Jg;
    private int Kg;
    private boolean Lg;
    private boolean Mg;
    private int Ng;
    private boolean Og;
    private final Rect mTempRect;
    private int zf;

    public PagerTabStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.Jg = new Paint();
        this.mTempRect = new Rect();
        this.Kg = 255;
        this.Lg = false;
        this.Mg = false;
        this.Dg = this.mTextColor;
        this.Jg.setColor(this.Dg);
        float f = context.getResources().getDisplayMetrics().density;
        this.Eg = (int) ((3.0f * f) + 0.5f);
        this.Fg = (int) ((6.0f * f) + 0.5f);
        this.Gg = (int) (64.0f * f);
        this.Ig = (int) ((16.0f * f) + 0.5f);
        this.Ng = (int) ((1.0f * f) + 0.5f);
        this.Hg = (int) ((f * 32.0f) + 0.5f);
        this.zf = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        u(U());
        setWillNotDraw(false);
        this.rg.setFocusable(true);
        this.rg.setOnClickListener(new l(this));
        this.tg.setFocusable(true);
        this.tg.setOnClickListener(new m(this));
        if (getBackground() == null) {
            this.Lg = true;
        }
    }

    @Override // android.support.v4.view.PagerTitleStrip
    void a(int i, float f, boolean z) {
        Rect rect = this.mTempRect;
        int height = getHeight();
        int left = this.sg.getLeft() - this.Ig;
        int right = this.sg.getRight() + this.Ig;
        int i2 = height - this.Eg;
        rect.set(left, i2, right, height);
        super.a(i, f, z);
        this.Kg = (int) (Math.abs(f - 0.5f) * 2.0f * 255.0f);
        rect.union(this.sg.getLeft() - this.Ig, i2, this.sg.getRight() + this.Ig, height);
        invalidate(rect);
    }

    @Override // android.support.v4.view.PagerTitleStrip
    int getMinHeight() {
        Drawable background = getBackground();
        return Math.max(background != null ? background.getIntrinsicHeight() : 0, this.Hg);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int left = this.sg.getLeft() - this.Ig;
        int right = this.sg.getRight() + this.Ig;
        int i = height - this.Eg;
        this.Jg.setColor((this.Kg << 24) | (this.Dg & 16777215));
        float f = height;
        canvas.drawRect(left, i, right, f, this.Jg);
        if (this.Lg) {
            this.Jg.setColor((-16777216) | (this.Dg & 16777215));
            canvas.drawRect(getPaddingLeft(), height - this.Ng, getWidth() - getPaddingRight(), f, this.Jg);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ViewPager viewPager;
        int currentItem;
        int action = motionEvent.getAction();
        if (action != 0 && this.Og) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (action == 0) {
            this.Af = x;
            this.Bf = y;
            this.Og = false;
        } else if (action == 1) {
            if (x < this.sg.getLeft() - this.Ig) {
                viewPager = this.qg;
                currentItem = viewPager.getCurrentItem() - 1;
            } else if (x > this.sg.getRight() + this.Ig) {
                viewPager = this.qg;
                currentItem = viewPager.getCurrentItem() + 1;
            }
            viewPager.setCurrentItem(currentItem);
        } else if (action == 2 && (Math.abs(x - this.Af) > this.zf || Math.abs(y - this.Bf) > this.zf)) {
            this.Og = true;
        }
        return true;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        if (this.Mg) {
            return;
        }
        this.Lg = (i & (-16777216)) == 0;
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.Mg) {
            return;
        }
        this.Lg = drawable == null;
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.Mg) {
            return;
        }
        this.Lg = i == 0;
    }

    @Override // android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        int i5 = this.Fg;
        if (i4 < i5) {
            i4 = i5;
        }
        super.setPadding(i, i2, i3, i4);
    }

    @Override // android.support.v4.view.PagerTitleStrip
    public void u(int i) {
        int i2 = this.Gg;
        if (i < i2) {
            i = i2;
        }
        super.u(i);
    }
}
