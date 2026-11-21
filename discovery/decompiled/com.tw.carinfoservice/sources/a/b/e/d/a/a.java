package a.b.e.d.a;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class a extends a.b.e.d.a.d {
    public c p;
    public g q;
    public int r;
    public int s;
    public boolean t;

    public static class b extends g {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Animatable f182a;

        public b(Animatable animatable) {
            super(null);
            this.f182a = animatable;
        }

        @Override // a.b.e.d.a.a.g
        public void c() {
            this.f182a.start();
        }

        @Override // a.b.e.d.a.a.g
        public void d() {
            this.f182a.stop();
        }
    }

    public static class c extends a.b.e.d.a.d.a {
        public a.b.d.g.c<Long> K;
        public a.b.d.g.g<Integer> L;

        public c(c cVar, a aVar, Resources resources) {
            a.b.d.g.g<Integer> gVar;
            super(cVar, aVar, resources);
            if (cVar != null) {
                this.K = cVar.K;
                gVar = cVar.L;
            } else {
                this.K = new a.b.d.g.c<>();
                gVar = new a.b.d.g.g<>();
            }
            this.L = gVar;
        }

        public static long i(int i, int i2) {
            return ((long) i2) | (((long) i) << 32);
        }

        @Override // a.b.e.d.a.d.a, a.b.e.d.a.b.c
        public void e() {
            this.K = this.K.clone();
            this.L = this.L.clone();
        }

        public int j(int i) {
            if (i < 0) {
                return 0;
            }
            return this.L.c(i, 0).intValue();
        }

        @Override // a.b.e.d.a.d.a, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new a(this, null);
        }

        @Override // a.b.e.d.a.d.a, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new a(this, resources);
        }
    }

    public static class d extends g {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final a.b.b.a.b f183a;

        public d(a.b.b.a.b bVar) {
            super(null);
            this.f183a = bVar;
        }

        @Override // a.b.e.d.a.a.g
        public void c() {
            this.f183a.start();
        }

        @Override // a.b.e.d.a.a.g
        public void d() {
            this.f183a.stop();
        }
    }

    public static class e extends g {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final ObjectAnimator f184a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final boolean f185b;

        public e(AnimationDrawable animationDrawable, boolean z, boolean z2) {
            super(null);
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            int i = z ? numberOfFrames - 1 : 0;
            int i2 = z ? 0 : numberOfFrames - 1;
            f fVar = new f(animationDrawable, z);
            ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(animationDrawable, "currentIndex", i, i2);
            objectAnimatorOfInt.setAutoCancel(true);
            objectAnimatorOfInt.setDuration(fVar.f188c);
            objectAnimatorOfInt.setInterpolator(fVar);
            this.f185b = z2;
            this.f184a = objectAnimatorOfInt;
        }

        @Override // a.b.e.d.a.a.g
        public boolean a() {
            return this.f185b;
        }

        @Override // a.b.e.d.a.a.g
        public void b() {
            this.f184a.reverse();
        }

        @Override // a.b.e.d.a.a.g
        public void c() {
            this.f184a.start();
        }

        @Override // a.b.e.d.a.a.g
        public void d() {
            this.f184a.cancel();
        }
    }

    public static class f implements TimeInterpolator {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int[] f186a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f187b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f188c;

        public f(AnimationDrawable animationDrawable, boolean z) {
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            this.f187b = numberOfFrames;
            int[] iArr = this.f186a;
            if (iArr == null || iArr.length < numberOfFrames) {
                this.f186a = new int[numberOfFrames];
            }
            int[] iArr2 = this.f186a;
            int i = 0;
            for (int i2 = 0; i2 < numberOfFrames; i2++) {
                int duration = animationDrawable.getDuration(z ? (numberOfFrames - i2) - 1 : i2);
                iArr2[i2] = duration;
                i += duration;
            }
            this.f188c = i;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            int i = (int) ((f * this.f188c) + 0.5f);
            int i2 = this.f187b;
            int[] iArr = this.f186a;
            int i3 = 0;
            while (i3 < i2 && i >= iArr[i3]) {
                i -= iArr[i3];
                i3++;
            }
            return (i3 / i2) + (i3 < i2 ? i / this.f188c : 0.0f);
        }
    }

    public static abstract class g {
        public g(C0010a c0010a) {
        }

        public boolean a() {
            return false;
        }

        public void b() {
        }

        public abstract void c();

        public abstract void d();
    }

    public a() {
        this(null, null);
    }

    public a(c cVar, Resources resources) {
        super(null);
        this.r = -1;
        this.s = -1;
        c cVar2 = new c(cVar, this, resources);
        super.e(cVar2);
        this.p = cVar2;
        onStateChange(getState());
        jumpToCurrentState();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public static a g(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int i;
        int next;
        int next2;
        Context context2 = context;
        AttributeSet attributeSet2 = attributeSet;
        Resources.Theme theme2 = theme;
        String name = xmlPullParser.getName();
        if (!name.equals("animated-selector")) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid animated-selector tag " + name);
        }
        Drawable drawableCreateFromXmlInner = null;
        a aVar = new a(null, null);
        TypedArray typedArrayY = a.b.a.a.y(resources, theme2, attributeSet2, a.b.e.b.b.e);
        int i2 = 1;
        aVar.setVisible(typedArrayY.getBoolean(1, true), true);
        c cVar = aVar.p;
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 21) {
            cVar.d |= typedArrayY.getChangingConfigurations();
        }
        int i4 = 2;
        cVar.i = typedArrayY.getBoolean(2, cVar.i);
        int i5 = 3;
        cVar.l = typedArrayY.getBoolean(3, cVar.l);
        int i6 = 4;
        cVar.A = typedArrayY.getInt(4, cVar.A);
        cVar.B = typedArrayY.getInt(5, cVar.B);
        boolean z = false;
        aVar.setDither(typedArrayY.getBoolean(0, cVar.x));
        aVar.f189b.g(resources);
        typedArrayY.recycle();
        int depth2 = xmlPullParser.getDepth() + 1;
        while (true) {
            int next3 = xmlPullParser.next();
            if (next3 == i2 || ((depth = xmlPullParser.getDepth()) < depth2 && next3 == i5)) {
                break;
            }
            if (next3 == i4 && depth <= depth2) {
                if (xmlPullParser.getName().equals("item")) {
                    TypedArray typedArrayY2 = a.b.a.a.y(resources, theme2, attributeSet2, a.b.e.b.b.f);
                    int resourceId = typedArrayY2.getResourceId(z ? 1 : 0, z ? 1 : 0);
                    int resourceId2 = typedArrayY2.getResourceId(i2, -1);
                    if (resourceId2 > 0) {
                        drawableCreateFromXmlInner = a.b.e.c.a.a.b(context2, resourceId2);
                    }
                    typedArrayY2.recycle();
                    int attributeCount = attributeSet.getAttributeCount();
                    int[] iArr = new int[attributeCount];
                    int i7 = 0;
                    int i8 = 0;
                    while (true) {
                        i = depth2;
                        if (i8 >= attributeCount) {
                            break;
                        }
                        int attributeNameResource = attributeSet2.getAttributeNameResource(i8);
                        int i9 = attributeCount;
                        if (attributeNameResource != 0 && attributeNameResource != 16842960 && attributeNameResource != 16843161) {
                            int i10 = i7 + 1;
                            if (!attributeSet2.getAttributeBooleanValue(i8, z)) {
                                attributeNameResource = -attributeNameResource;
                            }
                            iArr[i7] = attributeNameResource;
                            i7 = i10;
                        }
                        i8++;
                        depth2 = i;
                        attributeCount = i9;
                    }
                    int[] iArrTrimStateSet = StateSet.trimStateSet(iArr, i7);
                    if (drawableCreateFromXmlInner == null) {
                        do {
                            next2 = xmlPullParser.next();
                        } while (next2 == i6);
                        if (next2 != 2) {
                            throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <item> tag requires a 'drawable' attribute or child tag defining a drawable"));
                        }
                        if (xmlPullParser.getName().equals("vector")) {
                            drawableCreateFromXmlInner = a.b.b.a.g.a(resources, xmlPullParser, attributeSet, theme);
                        } else {
                            drawableCreateFromXmlInner = i3 >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
                        }
                    }
                    if (drawableCreateFromXmlInner == null) {
                        throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <item> tag requires a 'drawable' attribute or child tag defining a drawable"));
                    }
                    c cVar2 = aVar.p;
                    int iA = cVar2.a(drawableCreateFromXmlInner);
                    cVar2.J[iA] = iArrTrimStateSet;
                    cVar2.L.d(iA, Integer.valueOf(resourceId));
                    drawableCreateFromXmlInner = null;
                    i2 = 1;
                    i4 = 2;
                    i5 = 3;
                } else {
                    i = depth2;
                    if (xmlPullParser.getName().equals("transition")) {
                        TypedArray typedArrayY3 = a.b.a.a.y(resources, theme2, attributeSet2, a.b.e.b.b.g);
                        int resourceId3 = typedArrayY3.getResourceId(2, -1);
                        int resourceId4 = typedArrayY3.getResourceId(1, -1);
                        int resourceId5 = typedArrayY3.getResourceId(z ? 1 : 0, -1);
                        Drawable drawableB = resourceId5 > 0 ? a.b.e.c.a.a.b(context2, resourceId5) : null;
                        boolean z2 = typedArrayY3.getBoolean(3, z);
                        typedArrayY3.recycle();
                        if (drawableB == null) {
                            do {
                                next = xmlPullParser.next();
                            } while (next == i6);
                            if (next != 2) {
                                throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable"));
                            }
                            if (xmlPullParser.getName().equals("animated-vector")) {
                                drawableB = new a.b.b.a.b(context2, null, null);
                                drawableB.inflate(resources, xmlPullParser, attributeSet2, theme2);
                            } else {
                                drawableB = i3 >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
                            }
                        }
                        if (drawableB == null) {
                            throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable"));
                        }
                        if (resourceId3 == -1 || resourceId4 == -1) {
                            throw new XmlPullParserException(c.a.a.a.a.a(xmlPullParser, new StringBuilder(), ": <transition> tag requires 'fromId' & 'toId' attributes"));
                        }
                        c cVar3 = aVar.p;
                        int iA2 = cVar3.a(drawableB);
                        long jI = c.i(resourceId3, resourceId4);
                        long j = z2 ? 8589934592L : 0L;
                        long j2 = iA2;
                        cVar3.K.a(jI, Long.valueOf(j2 | j));
                        if (z2) {
                            cVar3.K.a(c.i(resourceId4, resourceId3), Long.valueOf(j2 | 4294967296L | j));
                        }
                    }
                    drawableCreateFromXmlInner = null;
                    i2 = 1;
                    z = false;
                    i4 = 2;
                    i5 = 3;
                    i6 = 4;
                    context2 = context;
                    attributeSet2 = attributeSet;
                    theme2 = theme;
                }
                depth2 = i;
            }
        }
        aVar.onStateChange(aVar.getState());
        return aVar;
    }

    @Override // a.b.e.d.a.d, a.b.e.d.a.b
    public a.b.e.d.a.b.c b() {
        return new c(this.p, this, null);
    }

    @Override // a.b.e.d.a.d, a.b.e.d.a.b
    public void clearMutated() {
        super.clearMutated();
        this.t = false;
    }

    @Override // a.b.e.d.a.d, a.b.e.d.a.b
    public void e(a.b.e.d.a.b.c cVar) {
        super.e(cVar);
        if (cVar instanceof c) {
            this.p = (c) cVar;
        }
    }

    @Override // a.b.e.d.a.d
    /* JADX INFO: renamed from: f */
    public a.b.e.d.a.d.a b() {
        return new c(this.p, this, null);
    }

    @Override // a.b.e.d.a.d, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // a.b.e.d.a.b, android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        g gVar = this.q;
        if (gVar != null) {
            gVar.d();
            this.q = null;
            d(this.r);
            this.r = -1;
            this.s = -1;
        }
    }

    @Override // a.b.e.d.a.d, a.b.e.d.a.b, android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.t) {
            super.mutate();
            if (this == this) {
                this.p.e();
                this.t = true;
            }
        }
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0070  */
    /* JADX WARN: Code duplicated, block: B:28:0x0091  */
    /* JADX WARN: Code duplicated, block: B:29:0x0093  */
    /* JADX WARN: Code duplicated, block: B:32:0x009d  */
    /* JADX WARN: Code duplicated, block: B:34:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:35:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:37:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:39:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:40:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:42:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:50:0x00f2  */
    @Override // a.b.e.d.a.d, a.b.e.d.a.b, android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        c cVar;
        int iJ;
        int iJ2;
        boolean z;
        int iLongValue;
        boolean z2;
        Object obj;
        g bVar;
        boolean z3;
        c cVar2 = this.p;
        int iH = cVar2.h(iArr);
        if (iH < 0) {
            iH = cVar2.h(StateSet.WILD_CARD);
        }
        int i = this.h;
        boolean z4 = true;
        if (iH == i) {
            z4 = false;
        } else {
            g gVar = this.q;
            if (gVar == null) {
                this.q = null;
                this.s = -1;
                this.r = -1;
                cVar = this.p;
                iJ = cVar.j(i);
                iJ2 = cVar.j(iH);
                if (iJ2 != 0 && iJ != 0) {
                    iLongValue = (int) cVar.K.d(c.i(iJ, iJ2), -1L).longValue();
                    if (iLongValue >= 0) {
                        if ((cVar.K.d(c.i(iJ, iJ2), -1L).longValue() & 8589934592L) != 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        d(iLongValue);
                        obj = this.d;
                        if (obj instanceof AnimationDrawable) {
                            if ((cVar.K.d(c.i(iJ, iJ2), -1L).longValue() & 4294967296L) != 0) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            bVar = new e((AnimationDrawable) obj, z3, z2);
                        } else if (obj instanceof a.b.b.a.b) {
                            bVar = new d((a.b.b.a.b) obj);
                        } else if (obj instanceof Animatable) {
                            bVar = new b((Animatable) obj);
                        }
                        bVar.c();
                        this.q = bVar;
                        this.s = i;
                        this.r = iH;
                        z = true;
                    }
                }
                z = false;
            } else {
                if (iH != this.r) {
                    if (iH == this.s && gVar.a()) {
                        gVar.b();
                        this.r = this.s;
                        this.s = iH;
                    } else {
                        i = this.r;
                        gVar.d();
                        this.q = null;
                        this.s = -1;
                        this.r = -1;
                        cVar = this.p;
                        iJ = cVar.j(i);
                        iJ2 = cVar.j(iH);
                        if (iJ2 != 0) {
                            iLongValue = (int) cVar.K.d(c.i(iJ, iJ2), -1L).longValue();
                            if (iLongValue >= 0) {
                                if ((cVar.K.d(c.i(iJ, iJ2), -1L).longValue() & 8589934592L) != 0) {
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                                d(iLongValue);
                                obj = this.d;
                                if (obj instanceof AnimationDrawable) {
                                    if ((cVar.K.d(c.i(iJ, iJ2), -1L).longValue() & 4294967296L) != 0) {
                                        z3 = true;
                                    } else {
                                        z3 = false;
                                    }
                                    bVar = new e((AnimationDrawable) obj, z3, z2);
                                } else if (obj instanceof a.b.b.a.b) {
                                    bVar = new d((a.b.b.a.b) obj);
                                } else if (obj instanceof Animatable) {
                                    bVar = new b((Animatable) obj);
                                }
                                bVar.c();
                                this.q = bVar;
                                this.s = i;
                                this.r = iH;
                            }
                        }
                        z = false;
                    }
                }
                z = true;
            }
            if (!z && !d(iH)) {
                z4 = false;
            }
        }
        Drawable drawable = this.d;
        return drawable != null ? z4 | drawable.setState(iArr) : z4;
    }

    @Override // a.b.e.d.a.b, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        g gVar = this.q;
        if (gVar != null && (visible || z2)) {
            if (z) {
                gVar.c();
            } else {
                jumpToCurrentState();
            }
        }
        return visible;
    }
}
