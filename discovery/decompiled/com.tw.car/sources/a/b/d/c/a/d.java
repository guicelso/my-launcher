package a.b.d.c.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class d extends c {
    public static Method g;

    private static class a extends c.a {
        public a(c.a aVar, Resources resources) {
            super(aVar, resources);
        }

        @Override // a.b.d.c.a.c.a, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new d(this, resources);
        }
    }

    public d(c.a aVar, Resources resources) {
        super(aVar, resources);
        c();
    }

    public d(Drawable drawable) {
        super(drawable);
        c();
    }

    @Override // a.b.d.c.a.c
    public boolean a() {
        if (Build.VERSION.SDK_INT != 21) {
            return false;
        }
        Drawable drawable = this.f;
        return (drawable instanceof GradientDrawable) || (drawable instanceof DrawableContainer) || (drawable instanceof InsetDrawable) || (drawable instanceof RippleDrawable);
    }

    @Override // a.b.d.c.a.c
    public c.a b() {
        return new a(this.d, null);
    }

    public final void c() {
        if (g == null) {
            try {
                g = Drawable.class.getDeclaredMethod("isProjected", new Class[0]);
            } catch (Exception e) {
                Log.w("WrappedDrawableApi21", "Failed to retrieve Drawable#isProjected() method", e);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Rect getDirtyBounds() {
        return this.f.getDirtyBounds();
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        this.f.getOutline(outline);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isProjected() {
        Method method;
        Drawable drawable = this.f;
        if (drawable != null && (method = g) != null) {
            try {
                return ((Boolean) method.invoke(drawable, new Object[0])).booleanValue();
            } catch (Exception e) {
                Log.w("WrappedDrawableApi21", "Error calling Drawable#isProjected() method", e);
            }
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        this.f.setHotspot(f, f2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        this.f.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // a.b.d.c.a.c, android.graphics.drawable.Drawable
    public boolean setState(int[] iArr) {
        if (!(a(iArr) || this.f.setState(iArr))) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    @Override // a.b.d.c.a.c, android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTint(int i) {
        if (a()) {
            setTintList(ColorStateList.valueOf(i));
        } else {
            this.f.setTint(i);
        }
    }

    @Override // a.b.d.c.a.c, android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTintList(ColorStateList colorStateList) {
        if (!a()) {
            this.f.setTintList(colorStateList);
        } else {
            this.d.f74c = colorStateList;
            a(getState());
        }
    }

    @Override // a.b.d.c.a.c, android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTintMode(PorterDuff.Mode mode) {
        if (!a()) {
            this.f.setTintMode(mode);
        } else {
            this.d.d = mode;
            a(getState());
        }
    }
}
