package a.b.d.c.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public class c extends Drawable implements Drawable.Callback, a.b.d.c.a.b, a.b.d.c.a.a {
    public static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f69a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public PorterDuff.Mode f70b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f71c;
    public a d;
    public boolean e;
    public Drawable f;

    protected static abstract class a extends Drawable.ConstantState {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f72a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public Drawable.ConstantState f73b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public ColorStateList f74c;
        public PorterDuff.Mode d;

        public a(a aVar, Resources resources) {
            this.f74c = null;
            this.d = c.DEFAULT_TINT_MODE;
            if (aVar != null) {
                this.f72a = aVar.f72a;
                this.f73b = aVar.f73b;
                this.f74c = aVar.f74c;
                this.d = aVar.d;
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.f72a;
            Drawable.ConstantState constantState = this.f73b;
            return i | (constantState != null ? constantState.getChangingConfigurations() : 0);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return newDrawable(null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public abstract Drawable newDrawable(Resources resources);
    }

    private static class b extends a {
        public b(a aVar, Resources resources) {
            super(aVar, resources);
        }

        @Override // a.b.d.c.a.c.a, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new c(this, resources);
        }
    }

    public c(a aVar, Resources resources) {
        Drawable.ConstantState constantState;
        this.d = aVar;
        a aVar2 = this.d;
        if (aVar2 == null || (constantState = aVar2.f73b) == null) {
            return;
        }
        a(constantState.newDrawable(resources));
    }

    public c(Drawable drawable) {
        this.d = b();
        a(drawable);
    }

    public final void a(Drawable drawable) {
        Drawable drawable2 = this.f;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.f = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            boolean zIsVisible = drawable.isVisible();
            if (!super.setVisible(zIsVisible, true)) {
                this.f.setVisible(zIsVisible, true);
            }
            setState(drawable.getState());
            setLevel(drawable.getLevel());
            setBounds(drawable.getBounds());
            a aVar = this.d;
            if (aVar != null) {
                aVar.f73b = drawable.getConstantState();
            }
        }
        invalidateSelf();
    }

    public boolean a() {
        return true;
    }

    public final boolean a(int[] iArr) {
        if (!a()) {
            return false;
        }
        a aVar = this.d;
        ColorStateList colorStateList = aVar.f74c;
        PorterDuff.Mode mode = aVar.d;
        if (colorStateList == null || mode == null) {
            this.f71c = false;
            clearColorFilter();
        } else {
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (!this.f71c || colorForState != this.f69a || mode != this.f70b) {
                setColorFilter(colorForState, mode);
                this.f69a = colorForState;
                this.f70b = mode;
                this.f71c = true;
                return true;
            }
        }
        return false;
    }

    public a b() {
        return new b(this.d, null);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.f.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        int changingConfigurations = super.getChangingConfigurations();
        a aVar = this.d;
        int changingConfigurations2 = 0;
        if (aVar != null) {
            int i = aVar.f72a;
            Drawable.ConstantState constantState = aVar.f73b;
            changingConfigurations2 = (constantState != null ? constantState.getChangingConfigurations() : 0) | i;
        }
        return changingConfigurations | changingConfigurations2 | this.f.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        a aVar = this.d;
        if (aVar == null) {
            return null;
        }
        int changingConfigurations = 0;
        if (!(aVar.f73b != null)) {
            return null;
        }
        a aVar2 = this.d;
        int changingConfigurations2 = super.getChangingConfigurations();
        a aVar3 = this.d;
        if (aVar3 != null) {
            int i = aVar3.f72a;
            Drawable.ConstantState constantState = aVar3.f73b;
            changingConfigurations = (constantState != null ? constantState.getChangingConfigurations() : 0) | i;
        }
        aVar2.f72a = changingConfigurations | changingConfigurations2 | this.f.getChangingConfigurations();
        return this.d;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable getCurrent() {
        return this.f.getCurrent();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.f.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.f.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return this.f.getMinimumHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return this.f.getMinimumWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.f.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        return this.f.getPadding(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public int[] getState() {
        return this.f.getState();
    }

    @Override // android.graphics.drawable.Drawable
    public Region getTransparentRegion() {
        return this.f.getTransparentRegion();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.f.isAutoMirrored();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        a aVar;
        ColorStateList colorStateList = (!a() || (aVar = this.d) == null) ? null : aVar.f74c;
        return (colorStateList != null && colorStateList.isStateful()) || this.f.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        this.f.jumpToCurrentState();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.e && super.mutate() == this) {
            this.d = b();
            Drawable drawable = this.f;
            if (drawable != null) {
                drawable.mutate();
            }
            a aVar = this.d;
            if (aVar != null) {
                Drawable drawable2 = this.f;
                aVar.f73b = drawable2 != null ? drawable2.getConstantState() : null;
            }
            this.e = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.f;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLevelChange(int i) {
        return this.f.setLevel(i);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.f.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        this.f.setAutoMirrored(z);
    }

    @Override // android.graphics.drawable.Drawable
    public void setChangingConfigurations(int i) {
        this.f.setChangingConfigurations(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.f.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.f.setDither(z);
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.f.setFilterBitmap(z);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setState(int[] iArr) {
        return a(iArr) || this.f.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTintList(ColorStateList colorStateList) {
        this.d.f74c = colorStateList;
        a(this.f.getState());
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTintMode(PorterDuff.Mode mode) {
        this.d.d = mode;
        a(this.f.getState());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2) || this.f.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}
