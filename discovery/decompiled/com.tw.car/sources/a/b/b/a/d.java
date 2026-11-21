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
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class d extends i implements a.b.b.a.b {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public a f4b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Context f5c;
    public ArgbEvaluator d;
    public final Drawable.Callback mCallback;

    /* JADX INFO: Access modifiers changed from: private */
    public static class a extends Drawable.ConstantState {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f6a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public k f7b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public AnimatorSet f8c;
        public ArrayList<Animator> d;
        public a.b.d.g.b<Animator, String> e;

        public a(Context context, a aVar, Drawable.Callback callback, Resources resources) {
            Drawable.ConstantState hVar;
            if (aVar != null) {
                this.f6a = aVar.f6a;
                k kVar = aVar.f7b;
                if (kVar != null) {
                    Drawable drawable = kVar.f17a;
                    if (drawable == null || Build.VERSION.SDK_INT < 24) {
                        kVar.f18b.f29a = kVar.getChangingConfigurations();
                        hVar = kVar.f18b;
                    } else {
                        hVar = new k.h(drawable.getConstantState());
                    }
                    this.f7b = (k) (resources != null ? hVar.newDrawable(resources) : hVar.newDrawable());
                    k kVar2 = this.f7b;
                    kVar2.mutate();
                    this.f7b = kVar2;
                    this.f7b.setCallback(callback);
                    this.f7b.setBounds(aVar.f7b.getBounds());
                    this.f7b.f = false;
                }
                ArrayList<Animator> arrayList = aVar.d;
                if (arrayList != null) {
                    int size = arrayList.size();
                    this.d = new ArrayList<>(size);
                    this.e = new a.b.d.g.b<>(size);
                    for (int i = 0; i < size; i++) {
                        Animator animator = aVar.d.get(i);
                        Animator animatorClone = animator.clone();
                        String str = aVar.e.get(animator);
                        animatorClone.setTarget(this.f7b.f18b.f30b.q.get(str));
                        this.d.add(animatorClone);
                        this.e.put(animatorClone, str);
                    }
                    if (this.f8c == null) {
                        this.f8c = new AnimatorSet();
                    }
                    this.f8c.playTogether(this.d);
                }
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f6a;
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

    public d() {
        this(null, null, null);
    }

    public d(Context context, a aVar, Resources resources) {
        this.d = null;
        this.mCallback = new c(this);
        this.f5c = context;
        if (aVar != null) {
            this.f4b = aVar;
        } else {
            this.f4b = new a(context, aVar, this.mCallback, resources);
        }
    }

    public static d a(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        d dVar = new d(context, null, null);
        dVar.inflate(resources, xmlPullParser, attributeSet, theme);
        return dVar;
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
                if (this.d == null) {
                    this.d = new ArgbEvaluator();
                }
                objectAnimator.setEvaluator(this.d);
            }
        }
    }

    @Override // a.b.b.a.i, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        Drawable drawable = this.f17a;
        if (drawable == null || Build.VERSION.SDK_INT < 21) {
            return;
        }
        drawable.applyTheme(theme);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        Drawable drawable = this.f17a;
        if (drawable == null || Build.VERSION.SDK_INT < 21) {
            return false;
        }
        return drawable.canApplyTheme();
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00f5  */
    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        int iIntValue;
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        } else {
            kVar.copyBounds(kVar.i);
            if (kVar.i.width() > 0 && kVar.i.height() > 0) {
                ColorFilter colorFilter = kVar.d;
                if (colorFilter == null) {
                    colorFilter = kVar.f19c;
                }
                canvas.getMatrix(kVar.h);
                kVar.h.getValues(kVar.g);
                float fAbs = Math.abs(kVar.g[0]);
                float fAbs2 = Math.abs(kVar.g[4]);
                float fAbs3 = Math.abs(kVar.g[1]);
                float fAbs4 = Math.abs(kVar.g[3]);
                if (fAbs3 != 0.0f || fAbs4 != 0.0f) {
                    fAbs = 1.0f;
                    fAbs2 = 1.0f;
                }
                int iWidth = (int) (kVar.i.width() * fAbs);
                int iHeight = (int) (kVar.i.height() * fAbs2);
                int iMin = Math.min(2048, iWidth);
                int iMin2 = Math.min(2048, iHeight);
                if (iMin > 0 && iMin2 > 0) {
                    int iSave = canvas.save();
                    Rect rect = kVar.i;
                    canvas.translate(rect.left, rect.top);
                    int i = Build.VERSION.SDK_INT;
                    if (kVar.isAutoMirrored()) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            iIntValue = kVar.getLayoutDirection();
                        } else {
                            if (!e.f11b) {
                                try {
                                    e.f10a = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
                                    e.f10a.setAccessible(true);
                                } catch (NoSuchMethodException e) {
                                    Log.i("DrawableCompat", "Failed to retrieve getLayoutDirection() method", e);
                                }
                                e.f11b = true;
                            }
                            Method method = e.f10a;
                            if (method != null) {
                                try {
                                    iIntValue = ((Integer) method.invoke(kVar, new Object[0])).intValue();
                                } catch (Exception e2) {
                                    Log.i("DrawableCompat", "Failed to invoke getLayoutDirection() via reflection", e2);
                                    e.f10a = null;
                                    iIntValue = 0;
                                }
                            } else {
                                iIntValue = 0;
                            }
                        }
                        z = iIntValue == 1;
                    }
                    if (z) {
                        canvas.translate(kVar.i.width(), 0.0f);
                        canvas.scale(-1.0f, 1.0f);
                    }
                    kVar.i.offsetTo(0, 0);
                    kVar.f18b.b(iMin, iMin2);
                    if (!kVar.f) {
                        kVar.f18b.c(iMin, iMin2);
                    } else if (!kVar.f18b.a()) {
                        kVar.f18b.c(iMin, iMin2);
                        kVar.f18b.d();
                    }
                    kVar.f18b.a(canvas, colorFilter, kVar.i);
                    canvas.restoreToCount(iSave);
                }
            }
        }
        if (this.f4b.f8c.isStarted()) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            int i = Build.VERSION.SDK_INT;
            return drawable.getAlpha();
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        if (drawable2 == null) {
            return kVar.f18b.f30b.getRootAlpha();
        }
        int i2 = Build.VERSION.SDK_INT;
        return drawable2.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        Drawable drawable = this.f17a;
        return drawable != null ? drawable.getChangingConfigurations() : super.getChangingConfigurations() | this.f4b.f6a;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        Drawable drawable = this.f17a;
        if (drawable == null || Build.VERSION.SDK_INT < 24) {
            return null;
        }
        return new b(drawable.getConstantState());
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        return drawable2 != null ? drawable2.getIntrinsicHeight() : (int) kVar.f18b.f30b.k;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        return drawable2 != null ? drawable2.getIntrinsicWidth() : (int) kVar.f18b.f30b.j;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        Drawable drawable2 = this.f4b.f7b.f17a;
        if (drawable2 != null) {
            return drawable2.getOpacity();
        }
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        inflate(resources, xmlPullParser, attributeSet, null);
    }

    /* JADX WARN: Undo finally extract visitor
    java.lang.NullPointerException: Cannot invoke "Object.hashCode()" because "this.second" is null
    	at jadx.core.utils.Pair.hashCode(Pair.java:35)
    	at java.base/java.util.HashMap.hash(HashMap.java:338)
    	at java.base/java.util.HashMap.getNode(HashMap.java:568)
    	at java.base/java.util.HashMap.containsKey(HashMap.java:594)
    	at jadx.core.dex.visitors.finaly.traverser.state.TraverserGlobalCommonState.hasBlocksBeenCached(TraverserGlobalCommonState.java:35)
    	at jadx.core.dex.visitors.finaly.traverser.handlers.MergePathActivePathTraverserHandler.handle(MergePathActivePathTraverserHandler.java:174)
    	at jadx.core.dex.visitors.finaly.traverser.handlers.AbstractActivePathTraverserHandler.process(AbstractActivePathTraverserHandler.java:19)
    	at jadx.core.dex.visitors.finaly.traverser.TraverserController.processHandlerImplementations(TraverserController.java:43)
    	at jadx.core.dex.visitors.finaly.traverser.TraverserController.advance(TraverserController.java:156)
    	at jadx.core.dex.visitors.finaly.traverser.TraverserController.process(TraverserController.java:79)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.findCommonInsns(MarkFinallyVisitor.java:404)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.extractFinally(MarkFinallyVisitor.java:284)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.processTryBlock(MarkFinallyVisitor.java:202)
    	at jadx.core.dex.visitors.finaly.MarkFinallyVisitor.visit(MarkFinallyVisitor.java:135)
     */
    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray typedArrayObtainAttributes;
        String str;
        Animator animatorA;
        Drawable drawable = this.f17a;
        if (drawable != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                drawable.inflate(resources, xmlPullParser, attributeSet, theme);
                return;
            } else {
                drawable.inflate(resources, xmlPullParser, attributeSet);
                return;
            }
        }
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        for (int i = 1; eventType != i && (xmlPullParser.getDepth() >= depth || eventType != 3); i = 1) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                XmlResourceParser xmlResourceParser = null;
                if ("animated-vector".equals(name)) {
                    typedArrayObtainAttributes = e.a(resources, theme, attributeSet, a.b.b.a.a.e);
                    int resourceId = typedArrayObtainAttributes.getResourceId(0, 0);
                    if (resourceId != 0) {
                        k kVarA = k.a(resources, resourceId, theme);
                        kVarA.f = false;
                        kVarA.setCallback(this.mCallback);
                        k kVar = this.f4b.f7b;
                        if (kVar != null) {
                            kVar.setCallback(null);
                        }
                        this.f4b.f7b = kVarA;
                    }
                } else if ("target".equals(name)) {
                    typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, a.b.b.a.a.f);
                    String string = typedArrayObtainAttributes.getString(0);
                    int resourceId2 = typedArrayObtainAttributes.getResourceId(i, 0);
                    if (resourceId2 != 0) {
                        Context context = this.f5c;
                        if (context == null) {
                            typedArrayObtainAttributes.recycle();
                            throw new IllegalStateException("Context can't be null when inflating animators");
                        }
                        if (Build.VERSION.SDK_INT >= 24) {
                            animatorA = AnimatorInflater.loadAnimator(context, resourceId2);
                        } else {
                            Resources resources2 = context.getResources();
                            Resources.Theme theme2 = context.getTheme();
                            try {
                                try {
                                    XmlResourceParser animation = resources2.getAnimation(resourceId2);
                                    str = "Can't load animation resource ID #0x";
                                    try {
                                        animatorA = e.a(context, resources2, theme2, animation, Xml.asAttributeSet(animation), null, 0, 1.0f);
                                        animation.close();
                                    } catch (IOException e) {
                                        e = e;
                                        Resources.NotFoundException notFoundException = new Resources.NotFoundException(str + Integer.toHexString(resourceId2));
                                        notFoundException.initCause(e);
                                        throw notFoundException;
                                    } catch (XmlPullParserException e2) {
                                        e = e2;
                                        Resources.NotFoundException notFoundException2 = new Resources.NotFoundException(str + Integer.toHexString(resourceId2));
                                        notFoundException2.initCause(e);
                                        throw notFoundException2;
                                    }
                                } catch (Throwable th) {
                                    if (0 != 0) {
                                        xmlResourceParser.close();
                                    }
                                    throw th;
                                }
                            } catch (IOException e3) {
                                e = e3;
                                str = "Can't load animation resource ID #0x";
                            } catch (XmlPullParserException e4) {
                                e = e4;
                                str = "Can't load animation resource ID #0x";
                            }
                        }
                        animatorA.setTarget(this.f4b.f7b.f18b.f30b.q.get(string));
                        if (Build.VERSION.SDK_INT < 21) {
                            a(animatorA);
                        }
                        a aVar = this.f4b;
                        if (aVar.d == null) {
                            aVar.d = new ArrayList<>();
                            this.f4b.e = new a.b.d.g.b<>();
                        }
                        this.f4b.d.add(animatorA);
                        this.f4b.e.put(animatorA, string);
                    }
                } else {
                    continue;
                }
                typedArrayObtainAttributes.recycle();
            }
            eventType = xmlPullParser.next();
        }
        a aVar2 = this.f4b;
        if (aVar2.f8c == null) {
            aVar2.f8c = new AnimatorSet();
        }
        aVar2.f8c.playTogether(aVar2.d);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            return e.a(drawable);
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        return drawable2 != null ? e.a(drawable2) : kVar.f18b.e;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        Drawable drawable = this.f17a;
        return drawable != null ? ((AnimatedVectorDrawable) drawable).isRunning() : this.f4b.f8c.isRunning();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        Drawable drawable = this.f17a;
        return drawable != null ? drawable.isStateful() : this.f4b.f7b.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.mutate();
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.setBounds(rect);
        } else {
            this.f4b.f7b.setBounds(rect);
        }
    }

    @Override // a.b.b.a.i, android.graphics.drawable.Drawable
    public boolean onLevelChange(int i) {
        Drawable drawable = this.f17a;
        return drawable != null ? drawable.setLevel(i) : this.f4b.f7b.setLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        Drawable drawable = this.f17a;
        return drawable != null ? drawable.setState(iArr) : this.f4b.f7b.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.setAlpha(i);
            return;
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        if (drawable2 != null) {
            drawable2.setAlpha(i);
        } else if (kVar.f18b.f30b.getRootAlpha() != i) {
            kVar.f18b.f30b.setRootAlpha(i);
            kVar.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            int i = Build.VERSION.SDK_INT;
            drawable.setAutoMirrored(z);
            return;
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        if (drawable2 == null) {
            kVar.f18b.e = z;
        } else {
            int i2 = Build.VERSION.SDK_INT;
            drawable2.setAutoMirrored(z);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
            return;
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        if (drawable2 != null) {
            drawable2.setColorFilter(colorFilter);
        } else {
            kVar.d = colorFilter;
            kVar.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTint(int i) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            e.a(drawable, i);
            return;
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        if (drawable2 != null) {
            e.a(drawable2, i);
        } else {
            kVar.setTintList(ColorStateList.valueOf(i));
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            e.a(drawable, colorStateList);
        } else {
            this.f4b.f7b.setTintList(colorStateList);
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            e.a(drawable, mode);
            return;
        }
        k kVar = this.f4b.f7b;
        Drawable drawable2 = kVar.f17a;
        if (drawable2 != null) {
            e.a(drawable2, mode);
            return;
        }
        k.g gVar = kVar.f18b;
        if (gVar.d != mode) {
            gVar.d = mode;
            kVar.f19c = kVar.updateTintFilter(kVar.f19c, gVar.f31c, mode);
            kVar.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            return drawable.setVisible(z, z2);
        }
        this.f4b.f7b.setVisible(z, z2);
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            ((AnimatedVectorDrawable) drawable).start();
        } else {
            if (this.f4b.f8c.isStarted()) {
                return;
            }
            this.f4b.f8c.start();
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            ((AnimatedVectorDrawable) drawable).stop();
        } else {
            this.f4b.f8c.end();
        }
    }

    private static class b extends Drawable.ConstantState {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Drawable.ConstantState f9a;

        public b(Drawable.ConstantState constantState) {
            this.f9a = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.f9a.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f9a.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            d dVar = new d(null, null, null);
            dVar.f17a = this.f9a.newDrawable();
            dVar.f17a.setCallback(dVar.mCallback);
            return dVar;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            d dVar = new d(null, null, null);
            dVar.f17a = this.f9a.newDrawable(resources);
            dVar.f17a.setCallback(dVar.mCallback);
            return dVar;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            d dVar = new d(null, null, null);
            dVar.f17a = this.f9a.newDrawable(resources, theme);
            dVar.f17a.setCallback(dVar.mCallback);
            return dVar;
        }
    }
}
