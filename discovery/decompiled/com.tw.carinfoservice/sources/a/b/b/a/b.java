package a.b.b.a;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class b extends f implements Animatable {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public C0000b f9c;
    public Context d;
    public ArgbEvaluator e;
    public final Drawable.Callback f;

    public class a implements Drawable.Callback {
        public a() {
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(Drawable drawable) {
            b.this.invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
            b.this.scheduleSelf(runnable, j);
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            b.this.unscheduleSelf(runnable);
        }
    }

    /* JADX INFO: renamed from: a.b.b.a.b$b, reason: collision with other inner class name */
    public static class C0000b extends Drawable.ConstantState {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f11a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public g f12b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public AnimatorSet f13c;
        public ArrayList<Animator> d;
        public a.b.d.g.a<Animator, String> e;

        public C0000b(C0000b c0000b, Drawable.Callback callback, Resources resources) {
            if (c0000b != null) {
                this.f11a = c0000b.f11a;
                g gVar = c0000b.f12b;
                if (gVar != null) {
                    Drawable.ConstantState constantState = gVar.getConstantState();
                    g gVar2 = (g) (resources != null ? constantState.newDrawable(resources) : constantState.newDrawable());
                    this.f12b = gVar2;
                    gVar2.mutate();
                    this.f12b = gVar2;
                    gVar2.setCallback(callback);
                    this.f12b.setBounds(c0000b.f12b.getBounds());
                    this.f12b.g = false;
                }
                ArrayList<Animator> arrayList = c0000b.d;
                if (arrayList != null) {
                    int size = arrayList.size();
                    this.d = new ArrayList<>(size);
                    this.e = new a.b.d.g.a<>(size);
                    for (int i = 0; i < size; i++) {
                        Animator animator = c0000b.d.get(i);
                        Animator animatorClone = animator.clone();
                        String str = c0000b.e.get(animator);
                        animatorClone.setTarget(this.f12b.f20c.f31b.p.get(str));
                        this.d.add(animatorClone);
                        this.e.put(animatorClone, str);
                    }
                    if (this.f13c == null) {
                        this.f13c = new AnimatorSet();
                    }
                    this.f13c.playTogether(this.d);
                }
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f11a;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }
    }

    public b() {
        this(null, null, null);
    }

    public b(Context context, C0000b c0000b, Resources resources) {
        this.e = null;
        a aVar = new a();
        this.f = aVar;
        this.d = context;
        this.f9c = new C0000b(null, aVar, null);
    }

    public final void a(Animator animator) {
        ArrayList<Animator> childAnimations;
        if ((animator instanceof AnimatorSet) && (childAnimations = ((AnimatorSet) animator).getChildAnimations()) != null) {
            for (int i = 0; i < childAnimations.size(); i++) {
                a(childAnimations.get(i));
            }
        }
        if (animator instanceof ObjectAnimator) {
            ObjectAnimator objectAnimator = (ObjectAnimator) animator;
            String propertyName = objectAnimator.getPropertyName();
            if ("fillColor".equals(propertyName) || "strokeColor".equals(propertyName)) {
                if (this.e == null) {
                    this.e = new ArgbEvaluator();
                }
                objectAnimator.setEvaluator(this.e);
            }
        }
    }

    @Override // a.b.b.a.f, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            a.b.a.a.a(drawable, theme);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        Drawable drawable = this.f19b;
        if (drawable == null || Build.VERSION.SDK_INT < 21) {
            return false;
        }
        return drawable.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        this.f9c.f12b.draw(canvas);
        if (this.f9c.f13c.isStarted()) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        Drawable drawable = this.f19b;
        return drawable != null ? a.b.a.a.m(drawable) : this.f9c.f12b.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.getChangingConfigurations() : super.getChangingConfigurations() | this.f9c.f11a;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        Drawable drawable = this.f19b;
        if (drawable == null || Build.VERSION.SDK_INT < 24) {
            return null;
        }
        return new c(drawable.getConstantState());
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.getIntrinsicHeight() : this.f9c.f12b.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.getIntrinsicWidth() : this.f9c.f12b.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.getOpacity() : this.f9c.f12b.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        inflate(resources, xmlPullParser, attributeSet, null);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        Animator animatorA;
        g gVarA;
        int next;
        int i = Build.VERSION.SDK_INT;
        Drawable drawable = this.f19b;
        if (drawable != null) {
            if (i >= 21) {
                drawable.inflate(resources, xmlPullParser, attributeSet, theme);
                return;
            } else {
                drawable.inflate(resources, xmlPullParser, attributeSet);
                return;
            }
        }
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                XmlResourceParser animation = null;
                if ("animated-vector".equals(name)) {
                    TypedArray typedArrayY = a.b.a.a.y(resources, theme, attributeSet, a.b.b.a.a.e);
                    int resourceId = typedArrayY.getResourceId(0, 0);
                    if (resourceId != 0) {
                        PorterDuff.Mode mode = g.k;
                        if (i >= 24) {
                            gVarA = new g();
                            Drawable drawable2 = i >= 21 ? resources.getDrawable(resourceId, theme) : resources.getDrawable(resourceId);
                            gVarA.f19b = drawable2;
                            new g.i(drawable2.getConstantState());
                        } else {
                            try {
                                XmlResourceParser xml = resources.getXml(resourceId);
                                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
                                do {
                                    next = xml.next();
                                    if (next == 2) {
                                        break;
                                    }
                                } while (next != 1);
                                if (next != 2) {
                                    throw new XmlPullParserException("No start tag found");
                                }
                                gVarA = g.a(resources, xml, attributeSetAsAttributeSet, theme);
                            } catch (IOException | XmlPullParserException e) {
                                Log.e("VectorDrawableCompat", "parser error", e);
                                gVarA = null;
                            }
                        }
                        gVarA.g = false;
                        gVarA.setCallback(this.f);
                        g gVar = this.f9c.f12b;
                        if (gVar != null) {
                            gVar.setCallback(null);
                        }
                        this.f9c.f12b = gVarA;
                    }
                    typedArrayY.recycle();
                } else if ("target".equals(name)) {
                    TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, a.b.b.a.a.f);
                    String string = typedArrayObtainAttributes.getString(0);
                    int resourceId2 = typedArrayObtainAttributes.getResourceId(1, 0);
                    if (resourceId2 != 0) {
                        Context context = this.d;
                        if (context == null) {
                            typedArrayObtainAttributes.recycle();
                            throw new IllegalStateException("Context can't be null when inflating animators");
                        }
                        if (i >= 24) {
                            animatorA = AnimatorInflater.loadAnimator(context, resourceId2);
                        } else {
                            Resources resources2 = context.getResources();
                            Resources.Theme theme2 = context.getTheme();
                            try {
                                try {
                                    try {
                                        animation = resources2.getAnimation(resourceId2);
                                        animatorA = a.b.b.a.c.a(context, resources2, theme2, animation, Xml.asAttributeSet(animation), null, 0, 1.0f);
                                        animation.close();
                                    } catch (IOException e2) {
                                        Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(resourceId2));
                                        notFoundException.initCause(e2);
                                        throw notFoundException;
                                    }
                                } catch (XmlPullParserException e3) {
                                    Resources.NotFoundException notFoundException2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(resourceId2));
                                    notFoundException2.initCause(e3);
                                    throw notFoundException2;
                                }
                            } catch (Throwable th) {
                                if (animation != null) {
                                    animation.close();
                                }
                                throw th;
                            }
                        }
                        animatorA.setTarget(this.f9c.f12b.f20c.f31b.p.get(string));
                        if (i < 21) {
                            a(animatorA);
                        }
                        C0000b c0000b = this.f9c;
                        if (c0000b.d == null) {
                            c0000b.d = new ArrayList<>();
                            this.f9c.e = new a.b.d.g.a<>();
                        }
                        this.f9c.d.add(animatorA);
                        this.f9c.e.put(animatorA, string);
                    }
                    typedArrayObtainAttributes.recycle();
                } else {
                    continue;
                }
            }
            eventType = xmlPullParser.next();
        }
        C0000b c0000b2 = this.f9c;
        if (c0000b2.f13c == null) {
            c0000b2.f13c = new AnimatorSet();
        }
        c0000b2.f13c.playTogether(c0000b2.d);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        Drawable drawable = this.f19b;
        return drawable != null ? a.b.a.a.u(drawable) : this.f9c.f12b.isAutoMirrored();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        Drawable drawable = this.f19b;
        return drawable != null ? ((AnimatedVectorDrawable) drawable).isRunning() : this.f9c.f13c.isRunning();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.isStateful() : this.f9c.f12b.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.mutate();
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.setBounds(rect);
        } else {
            this.f9c.f12b.setBounds(rect);
        }
    }

    @Override // a.b.b.a.f, android.graphics.drawable.Drawable
    public boolean onLevelChange(int i) {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.setLevel(i) : this.f9c.f12b.setLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.setState(iArr) : this.f9c.f12b.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.setAlpha(i);
        } else {
            this.f9c.f12b.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.setAutoMirrored(z);
            return;
        }
        g gVar = this.f9c.f12b;
        Drawable drawable2 = gVar.f19b;
        if (drawable2 != null) {
            a.b.a.a.F(drawable2, z);
        } else {
            gVar.f20c.e = z;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
            return;
        }
        g gVar = this.f9c.f12b;
        Drawable drawable2 = gVar.f19b;
        if (drawable2 != null) {
            drawable2.setColorFilter(colorFilter);
        } else {
            gVar.e = colorFilter;
            gVar.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.k.a
    public void setTint(int i) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            a.b.a.a.S(drawable, i);
        } else {
            this.f9c.f12b.setTint(i);
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.k.a
    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            a.b.a.a.T(drawable, colorStateList);
        } else {
            this.f9c.f12b.setTintList(colorStateList);
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.k.a
    public void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            a.b.a.a.U(drawable, mode);
        } else {
            this.f9c.f12b.setTintMode(mode);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            return drawable.setVisible(z, z2);
        }
        this.f9c.f12b.setVisible(z, z2);
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            ((AnimatedVectorDrawable) drawable).start();
        } else {
            if (this.f9c.f13c.isStarted()) {
                return;
            }
            this.f9c.f13c.start();
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            ((AnimatedVectorDrawable) drawable).stop();
        } else {
            this.f9c.f13c.end();
        }
    }

    public static class c extends Drawable.ConstantState {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Drawable.ConstantState f14a;

        public c(Drawable.ConstantState constantState) {
            this.f14a = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.f14a.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f14a.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            b bVar = new b(null, null, null);
            Drawable drawableNewDrawable = this.f14a.newDrawable();
            bVar.f19b = drawableNewDrawable;
            drawableNewDrawable.setCallback(bVar.f);
            return bVar;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            b bVar = new b(null, null, null);
            Drawable drawableNewDrawable = this.f14a.newDrawable(resources);
            bVar.f19b = drawableNewDrawable;
            drawableNewDrawable.setCallback(bVar.f);
            return bVar;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            b bVar = new b(null, null, null);
            Drawable drawableNewDrawable = this.f14a.newDrawable(resources, theme);
            bVar.f19b = drawableNewDrawable;
            drawableNewDrawable.setCallback(bVar.f);
            return bVar;
        }
    }
}
