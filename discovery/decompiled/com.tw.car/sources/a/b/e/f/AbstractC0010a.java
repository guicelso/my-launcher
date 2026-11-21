package a.b.e.f;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v7.widget.ActionMenuView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: renamed from: a.b.e.f.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0010a extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0006a f307a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ActionMenuView f308b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public C0015f f309c;
    public int d;
    public boolean e;
    public boolean f;

    /* JADX INFO: renamed from: a.b.e.f.a$a, reason: collision with other inner class name */
    protected class C0006a implements a.b.d.h.p {
        public C0006a(AbstractC0010a abstractC0010a) {
        }
    }

    public AbstractC0010a(Context context) {
        this(context, null, 0);
    }

    public AbstractC0010a(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbstractC0010a(Context context, AttributeSet attributeSet, int i) {
        int i2;
        super(context, attributeSet, i);
        this.f307a = new C0006a(this);
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(a.b.e.b.a.actionBarPopupTheme, typedValue, true) || (i2 = typedValue.resourceId) == 0) {
            return;
        }
        new ContextThemeWrapper(context, i2);
    }

    public static int a(int i, int i2, boolean z) {
        return z ? i - i2 : i + i2;
    }

    public int a(View view, int i, int i2, int i3) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), i2);
        return Math.max(0, (i - view.getMeasuredWidth()) - i3);
    }

    public int a(View view, int i, int i2, int i3, boolean z) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = ((i3 - measuredHeight) / 2) + i2;
        if (z) {
            view.layout(i - measuredWidth, i4, i, measuredHeight + i4);
        } else {
            view.layout(i, i4, i + measuredWidth, measuredHeight + i4);
        }
        return z ? -measuredWidth : measuredWidth;
    }

    public abstract int getAnimatedVisibility();

    public abstract int getContentHeight();

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        int i;
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, a.b.e.b.j.ActionBar, a.b.e.b.a.actionBarStyle, 0);
        setContentHeight(typedArrayObtainStyledAttributes.getLayoutDimension(a.b.e.b.j.ActionBar_height, 0));
        typedArrayObtainStyledAttributes.recycle();
        C0015f c0015f = this.f309c;
        if (c0015f != null) {
            if (!c0015f.p) {
                Configuration configuration2 = c0015f.f209b.getResources().getConfiguration();
                int i2 = configuration2.screenWidthDp;
                int i3 = configuration2.screenHeightDp;
                if (configuration2.smallestScreenWidthDp > 600 || i2 > 600 || ((i2 > 960 && i3 > 720) || (i2 > 720 && i3 > 960))) {
                    i = 5;
                } else if (i2 >= 500 || ((i2 > 640 && i3 > 480) || (i2 > 480 && i3 > 640))) {
                    i = 4;
                } else {
                    i = i2 >= 360 ? 3 : 2;
                }
                c0015f.o = i;
            }
            a.b.e.e.a.k kVar = c0015f.f210c;
            if (kVar != null) {
                kVar.b(true);
            }
        }
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.f = false;
        }
        if (!this.f) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.f = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.f = false;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.e = false;
        }
        if (!this.e) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.e = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.e = false;
        }
        return true;
    }

    public abstract void setContentHeight(int i);

    @Override // android.view.View
    public void setVisibility(int i) {
        if (i != getVisibility()) {
            super.setVisibility(i);
        }
    }
}
