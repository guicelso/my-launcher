package a.b.e.d.a;

import a.b.b.a.k;
import a.b.d.g.h;
import a.b.e.b.j;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class b extends a.b.e.d.a.f {
    public C0005b o;
    public f p;
    public int q;
    public int r;
    public boolean s;

    private static class a extends f {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Animatable f189a;

        public a(Animatable animatable) {
            super(null);
            this.f189a = animatable;
        }

        @Override // a.b.e.d.a.b.f
        public void c() {
            this.f189a.start();
        }

        @Override // a.b.e.d.a.b.f
        public void d() {
            this.f189a.stop();
        }
    }

    /* JADX INFO: renamed from: a.b.e.d.a.b$b, reason: collision with other inner class name */
    static class C0005b extends a.b.e.d.a.f.a {
        public a.b.d.g.d<Long> K;
        public h<Integer> L;

        public C0005b(C0005b c0005b, b bVar, Resources resources) {
            h<Integer> hVar;
            super(c0005b, bVar, resources);
            if (c0005b != null) {
                this.K = c0005b.K;
                hVar = c0005b.L;
            } else {
                this.K = new a.b.d.g.d<>();
                hVar = new h<>();
            }
            this.L = hVar;
        }

        public static long a(int i, int i2) {
            return ((long) i2) | (((long) i) << 32);
        }

        public int a(int i, int i2, Drawable drawable, boolean z) {
            int iA = super.a(drawable);
            long jA = a(i, i2);
            long j = z ? 8589934592L : 0L;
            long j2 = iA;
            this.K.a(jA, Long.valueOf(j2 | j));
            if (z) {
                this.K.a(a(i2, i), Long.valueOf(4294967296L | j2 | j));
            }
            return iA;
        }

        public int b(int i) {
            if (i < 0) {
                return 0;
            }
            return this.L.b(i, 0).intValue();
        }

        public int b(int[] iArr) {
            int iA = super.a(iArr);
            return iA >= 0 ? iA : super.a(StateSet.WILD_CARD);
        }

        @Override // a.b.e.d.a.f.a, a.b.e.d.a.d.b
        public void d() {
            this.K = this.K.m0clone();
            this.L = this.L.m1clone();
        }

        @Override // a.b.e.d.a.f.a, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new b(this, null);
        }

        @Override // a.b.e.d.a.f.a, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new b(this, resources);
        }
    }

    private static class c extends f {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final a.b.b.a.d f190a;

        public c(a.b.b.a.d dVar) {
            super(null);
            this.f190a = dVar;
        }

        @Override // a.b.e.d.a.b.f
        public void c() {
            a.b.b.a.d dVar = this.f190a;
            Drawable drawable = dVar.f17a;
            if (drawable != null) {
                ((AnimatedVectorDrawable) drawable).start();
            } else {
                if (dVar.f4b.f8c.isStarted()) {
                    return;
                }
                dVar.f4b.f8c.start();
                dVar.invalidateSelf();
            }
        }

        @Override // a.b.e.d.a.b.f
        public void d() {
            a.b.b.a.d dVar = this.f190a;
            Drawable drawable = dVar.f17a;
            if (drawable != null) {
                ((AnimatedVectorDrawable) drawable).stop();
            } else {
                dVar.f4b.f8c.end();
            }
        }
    }

    private static class d extends f {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final ObjectAnimator f191a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final boolean f192b;

        public d(AnimationDrawable animationDrawable, boolean z, boolean z2) {
            super(null);
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            int i = z ? numberOfFrames - 1 : 0;
            int i2 = z ? 0 : numberOfFrames - 1;
            e eVar = new e(animationDrawable, z);
            ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(animationDrawable, "currentIndex", i, i2);
            int i3 = Build.VERSION.SDK_INT;
            objectAnimatorOfInt.setAutoCancel(true);
            objectAnimatorOfInt.setDuration(eVar.f195c);
            objectAnimatorOfInt.setInterpolator(eVar);
            this.f192b = z2;
            this.f191a = objectAnimatorOfInt;
        }

        @Override // a.b.e.d.a.b.f
        public boolean a() {
            return this.f192b;
        }

        @Override // a.b.e.d.a.b.f
        public void b() {
            this.f191a.reverse();
        }

        @Override // a.b.e.d.a.b.f
        public void c() {
            this.f191a.start();
        }

        @Override // a.b.e.d.a.b.f
        public void d() {
            this.f191a.cancel();
        }
    }

    private static class e implements TimeInterpolator {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int[] f193a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f194b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f195c;

        public e(AnimationDrawable animationDrawable, boolean z) {
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            this.f194b = numberOfFrames;
            int[] iArr = this.f193a;
            if (iArr == null || iArr.length < numberOfFrames) {
                this.f193a = new int[numberOfFrames];
            }
            int[] iArr2 = this.f193a;
            int i = 0;
            for (int i2 = 0; i2 < numberOfFrames; i2++) {
                int duration = animationDrawable.getDuration(z ? (numberOfFrames - i2) - 1 : i2);
                iArr2[i2] = duration;
                i += duration;
            }
            this.f195c = i;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            int i = (int) ((f * this.f195c) + 0.5f);
            int i2 = this.f194b;
            int[] iArr = this.f193a;
            int i3 = 0;
            while (i3 < i2 && i >= iArr[i3]) {
                i -= iArr[i3];
                i3++;
            }
            return (i3 / i2) + (i3 < i2 ? i / this.f195c : 0.0f);
        }
    }

    private static abstract class f {
        public /* synthetic */ f(a.b.e.d.a.a aVar) {
        }

        public boolean a() {
            return false;
        }

        public void b() {
        }

        public abstract void c();

        public abstract void d();
    }

    static {
        b.class.getSimpleName();
    }

    public b() {
        this(null, null);
    }

    public b(C0005b c0005b, Resources resources) {
        super(null);
        this.q = -1;
        this.r = -1;
        C0005b c0005b2 = new C0005b(c0005b, this, resources);
        this.f197a = c0005b2;
        int i = this.g;
        if (i >= 0) {
            this.f199c = c0005b2.a(i);
            Drawable drawable = this.f199c;
            if (drawable != null) {
                a(drawable);
            }
        }
        this.d = null;
        this.m = c0005b2;
        this.o = c0005b2;
        onStateChange(getState());
        jumpToCurrentState();
    }

    @Override // a.b.e.d.a.f, a.b.e.d.a.d
    public a.b.e.d.a.d.b a() {
        return new C0005b(this.o, this, null);
    }

    @Override // a.b.e.d.a.f
    public void clearMutated() {
        this.f197a.y = false;
        this.h = false;
        this.n = false;
        this.s = false;
    }

    @Override // a.b.e.d.a.f, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // a.b.e.d.a.d, android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        boolean z;
        Drawable drawable = this.d;
        if (drawable != null) {
            drawable.jumpToCurrentState();
            this.d = null;
            z = true;
        } else {
            z = false;
        }
        Drawable drawable2 = this.f199c;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
            if (this.f) {
                this.f199c.setAlpha(this.e);
            }
        }
        if (this.k != 0) {
            this.k = 0L;
            z = true;
        }
        if (this.j != 0) {
            this.j = 0L;
            z = true;
        }
        if (z) {
            invalidateSelf();
        }
        f fVar = this.p;
        if (fVar != null) {
            fVar.d();
            this.p = null;
            a(this.q);
            this.q = -1;
            this.r = -1;
        }
    }

    @Override // a.b.e.d.a.f, a.b.e.d.a.d, android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.s) {
            super.mutate();
            if (this == this) {
                C0005b c0005b = this.o;
                c0005b.K = c0005b.K.m0clone();
                c0005b.L = c0005b.L.m1clone();
                this.s = true;
            }
        }
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0067  */
    /* JADX WARN: Code duplicated, block: B:24:0x0088  */
    /* JADX WARN: Code duplicated, block: B:25:0x008a  */
    /* JADX WARN: Code duplicated, block: B:28:0x0094  */
    /* JADX WARN: Code duplicated, block: B:30:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:31:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:33:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:35:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:36:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:38:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:46:0x00e9  */
    @Override // a.b.e.d.a.f, android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        C0005b c0005b;
        int iB;
        int iB2;
        boolean z;
        int iLongValue;
        boolean z2;
        Object obj;
        f aVar;
        boolean z3;
        int iB3 = this.o.b(iArr);
        int i = this.g;
        boolean z4 = true;
        if (iB3 == i) {
            z4 = false;
        } else {
            f fVar = this.p;
            if (fVar == null) {
                this.p = null;
                this.r = -1;
                this.q = -1;
                c0005b = this.o;
                iB = c0005b.b(i);
                iB2 = c0005b.b(iB3);
                if (iB2 != 0 && iB != 0) {
                    iLongValue = (int) c0005b.K.b(C0005b.a(iB, iB2), -1L).longValue();
                    if (iLongValue >= 0) {
                        if ((c0005b.K.b(C0005b.a(iB, iB2), -1L).longValue() & 8589934592L) != 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        a(iLongValue);
                        obj = this.f199c;
                        if (obj instanceof AnimationDrawable) {
                            if ((c0005b.K.b(C0005b.a(iB, iB2), -1L).longValue() & 4294967296L) != 0) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            aVar = new d((AnimationDrawable) obj, z3, z2);
                        } else if (obj instanceof a.b.b.a.d) {
                            aVar = new c((a.b.b.a.d) obj);
                        } else if (obj instanceof Animatable) {
                            aVar = new a((Animatable) obj);
                        }
                        aVar.c();
                        this.p = aVar;
                        this.r = i;
                        this.q = iB3;
                        z = true;
                    }
                }
                z = false;
            } else {
                if (iB3 != this.q) {
                    if (iB3 == this.r && fVar.a()) {
                        fVar.b();
                        this.q = this.r;
                        this.r = iB3;
                    } else {
                        i = this.q;
                        fVar.d();
                        this.p = null;
                        this.r = -1;
                        this.q = -1;
                        c0005b = this.o;
                        iB = c0005b.b(i);
                        iB2 = c0005b.b(iB3);
                        if (iB2 != 0) {
                            iLongValue = (int) c0005b.K.b(C0005b.a(iB, iB2), -1L).longValue();
                            if (iLongValue >= 0) {
                                if ((c0005b.K.b(C0005b.a(iB, iB2), -1L).longValue() & 8589934592L) != 0) {
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                                a(iLongValue);
                                obj = this.f199c;
                                if (obj instanceof AnimationDrawable) {
                                    if ((c0005b.K.b(C0005b.a(iB, iB2), -1L).longValue() & 4294967296L) != 0) {
                                        z3 = true;
                                    } else {
                                        z3 = false;
                                    }
                                    aVar = new d((AnimationDrawable) obj, z3, z2);
                                } else if (obj instanceof a.b.b.a.d) {
                                    aVar = new c((a.b.b.a.d) obj);
                                } else if (obj instanceof Animatable) {
                                    aVar = new a((Animatable) obj);
                                }
                                aVar.c();
                                this.p = aVar;
                                this.r = i;
                                this.q = iB3;
                            }
                        }
                        z = false;
                    }
                }
                z = true;
            }
            if (!z && !a(iB3)) {
                z4 = false;
            }
        }
        Drawable drawable = this.f199c;
        return drawable != null ? z4 | drawable.setState(iArr) : z4;
    }

    @Override // a.b.e.d.a.d, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (this.p != null && (visible || z2)) {
            if (z) {
                this.p.c();
            } else {
                jumpToCurrentState();
            }
        }
        return visible;
    }

    @Override // a.b.e.d.a.f, a.b.e.d.a.d
    public a.b.e.d.a.f.a a() {
        return new C0005b(this.o, this, null);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static b a(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int next;
        int next2;
        String name = xmlPullParser.getName();
        if (name.equals("animated-selector")) {
            Drawable drawableCreateFromXmlInner = null;
            b bVar = new b(null, null);
            TypedArray typedArrayA = a.b.b.a.e.a(resources, theme, attributeSet, j.AnimatedStateListDrawableCompat);
            int i = 1;
            boolean z = typedArrayA.getBoolean(j.AnimatedStateListDrawableCompat_android_visible, true);
            super.setVisible(z, true);
            f fVar = bVar.p;
            if (fVar != null) {
                if (z) {
                    fVar.c();
                } else {
                    bVar.jumpToCurrentState();
                }
            }
            C0005b c0005b = bVar.o;
            if (Build.VERSION.SDK_INT >= 21) {
                c0005b.d |= typedArrayA.getChangingConfigurations();
            }
            c0005b.i = typedArrayA.getBoolean(j.AnimatedStateListDrawableCompat_android_variablePadding, c0005b.i);
            c0005b.l = typedArrayA.getBoolean(j.AnimatedStateListDrawableCompat_android_constantSize, c0005b.l);
            c0005b.A = typedArrayA.getInt(j.AnimatedStateListDrawableCompat_android_enterFadeDuration, c0005b.A);
            c0005b.B = typedArrayA.getInt(j.AnimatedStateListDrawableCompat_android_exitFadeDuration, c0005b.B);
            boolean z2 = typedArrayA.getBoolean(j.AnimatedStateListDrawableCompat_android_dither, c0005b.x);
            a.b.e.d.a.d.b bVar2 = bVar.f197a;
            if (bVar2.x != z2) {
                bVar2.x = z2;
                Drawable drawable = bVar.f199c;
                if (drawable != null) {
                    drawable.setDither(bVar2.x);
                }
            }
            bVar.f197a.a(resources);
            typedArrayA.recycle();
            int depth2 = xmlPullParser.getDepth() + 1;
            while (true) {
                int next3 = xmlPullParser.next();
                if (next3 == i || ((depth = xmlPullParser.getDepth()) < depth2 && next3 == 3)) {
                    break;
                }
                if (next3 == 2 && depth <= depth2) {
                    if (xmlPullParser.getName().equals("item")) {
                        TypedArray typedArrayA2 = a.b.b.a.e.a(resources, theme, attributeSet, j.AnimatedStateListDrawableItem);
                        int resourceId = typedArrayA2.getResourceId(j.AnimatedStateListDrawableItem_android_id, 0);
                        int resourceId2 = typedArrayA2.getResourceId(j.AnimatedStateListDrawableItem_android_drawable, -1);
                        if (resourceId2 > 0) {
                            drawableCreateFromXmlInner = a.b.e.c.a.a.c(context, resourceId2);
                        }
                        typedArrayA2.recycle();
                        int attributeCount = attributeSet.getAttributeCount();
                        int[] iArr = new int[attributeCount];
                        int i2 = 0;
                        for (int i3 = 0; i3 < attributeCount; i3++) {
                            int attributeNameResource = attributeSet.getAttributeNameResource(i3);
                            if (attributeNameResource != 0 && attributeNameResource != 16842960 && attributeNameResource != 16843161) {
                                int i4 = i2 + 1;
                                if (!attributeSet.getAttributeBooleanValue(i3, false)) {
                                    attributeNameResource = -attributeNameResource;
                                }
                                iArr[i2] = attributeNameResource;
                                i2 = i4;
                            }
                        }
                        int[] iArrTrimStateSet = StateSet.trimStateSet(iArr, i2);
                        if (drawableCreateFromXmlInner == null) {
                            do {
                                next2 = xmlPullParser.next();
                            } while (next2 == 4);
                            if (next2 != 2) {
                                throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <item> tag requires a 'drawable' attribute or child tag defining a drawable"));
                            }
                            drawableCreateFromXmlInner = xmlPullParser.getName().equals("vector") ? k.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
                        }
                        if (drawableCreateFromXmlInner != null) {
                            C0005b c0005b2 = bVar.o;
                            int iA = c0005b2.a(drawableCreateFromXmlInner);
                            c0005b2.J[iA] = iArrTrimStateSet;
                            c0005b2.L.c(iA, Integer.valueOf(resourceId));
                        } else {
                            throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <item> tag requires a 'drawable' attribute or child tag defining a drawable"));
                        }
                    } else if (xmlPullParser.getName().equals("transition")) {
                        TypedArray typedArrayA3 = a.b.b.a.e.a(resources, theme, attributeSet, j.AnimatedStateListDrawableTransition);
                        int resourceId3 = typedArrayA3.getResourceId(j.AnimatedStateListDrawableTransition_android_fromId, -1);
                        int resourceId4 = typedArrayA3.getResourceId(j.AnimatedStateListDrawableTransition_android_toId, -1);
                        int resourceId5 = typedArrayA3.getResourceId(j.AnimatedStateListDrawableTransition_android_drawable, -1);
                        Drawable drawableC = resourceId5 > 0 ? a.b.e.c.a.a.c(context, resourceId5) : null;
                        boolean z3 = typedArrayA3.getBoolean(j.AnimatedStateListDrawableTransition_android_reversible, false);
                        typedArrayA3.recycle();
                        if (drawableC == null) {
                            do {
                                next = xmlPullParser.next();
                            } while (next == 4);
                            if (next != 2) {
                                throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable"));
                            }
                            drawableC = xmlPullParser.getName().equals("animated-vector") ? a.b.b.a.d.a(context, resources, xmlPullParser, attributeSet, theme) : Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
                        }
                        if (drawableC == null) {
                            throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable"));
                        }
                        if (resourceId3 == -1 || resourceId4 == -1) {
                            throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <transition> tag requires 'fromId' & 'toId' attributes"));
                        }
                        bVar.o.a(resourceId3, resourceId4, drawableC, z3);
                    }
                    drawableCreateFromXmlInner = null;
                    i = 1;
                }
            }
            bVar.onStateChange(bVar.getState());
            return bVar;
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid animated-selector tag " + name);
    }

    @Override // a.b.e.d.a.f, a.b.e.d.a.d
    public void a(a.b.e.d.a.d.b bVar) {
        this.f197a = bVar;
        int i = this.g;
        if (i >= 0) {
            this.f199c = bVar.a(i);
            Drawable drawable = this.f199c;
            if (drawable != null) {
                a(drawable);
            }
        }
        this.d = null;
        if (bVar instanceof a.b.e.d.a.f.a) {
            this.m = (a.b.e.d.a.f.a) bVar;
        }
        if (bVar instanceof C0005b) {
            this.o = (C0005b) bVar;
        }
    }
}
