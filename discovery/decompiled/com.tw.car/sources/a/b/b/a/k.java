package a.b.b.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class k extends i {
    public static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public g f18b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public PorterDuffColorFilter f19c;
    public ColorFilter d;
    public boolean e;
    public boolean f;
    public final float[] g;
    public final Matrix h;
    public final Rect i;

    private static class a extends e {
        public a() {
        }

        public a(a aVar) {
            super(aVar);
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (a.b.b.a.e.a(xmlPullParser, "pathData")) {
                TypedArray typedArrayA = a.b.b.a.e.a(resources, theme, attributeSet, a.b.b.a.a.d);
                String string = typedArrayA.getString(0);
                if (string != null) {
                    this.f24b = string;
                }
                String string2 = typedArrayA.getString(1);
                if (string2 != null) {
                    this.f23a = a.b.b.a.e.a(string2);
                }
                typedArrayA.recycle();
            }
        }

        @Override // a.b.b.a.k.e
        public boolean b() {
            return true;
        }
    }

    private static class b extends e {
        public int[] d;
        public a.b.d.b.a.a e;
        public float f;
        public a.b.d.b.a.a g;
        public float h;
        public int i;
        public float j;
        public float k;
        public float l;
        public float m;
        public Paint.Cap n;
        public Paint.Join o;
        public float p;

        public b() {
            this.f = 0.0f;
            this.h = 1.0f;
            this.i = 0;
            this.j = 1.0f;
            this.k = 0.0f;
            this.l = 1.0f;
            this.m = 0.0f;
            this.n = Paint.Cap.BUTT;
            this.o = Paint.Join.MITER;
            this.p = 4.0f;
        }

        public b(b bVar) {
            super(bVar);
            this.f = 0.0f;
            this.h = 1.0f;
            this.i = 0;
            this.j = 1.0f;
            this.k = 0.0f;
            this.l = 1.0f;
            this.m = 0.0f;
            this.n = Paint.Cap.BUTT;
            this.o = Paint.Join.MITER;
            this.p = 4.0f;
            this.d = bVar.d;
            this.e = bVar.e;
            this.f = bVar.f;
            this.h = bVar.h;
            this.g = bVar.g;
            this.i = bVar.i;
            this.j = bVar.j;
            this.k = bVar.k;
            this.l = bVar.l;
            this.m = bVar.m;
            this.n = bVar.n;
            this.o = bVar.o;
            this.p = bVar.p;
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray typedArrayA = a.b.b.a.e.a(resources, theme, attributeSet, a.b.b.a.a.f2c);
            this.d = null;
            if (a.b.b.a.e.a(xmlPullParser, "pathData")) {
                String string = typedArrayA.getString(0);
                if (string != null) {
                    this.f24b = string;
                }
                String string2 = typedArrayA.getString(2);
                if (string2 != null) {
                    this.f23a = a.b.b.a.e.a(string2);
                }
                this.g = a.b.b.a.e.a(typedArrayA, xmlPullParser, theme, "fillColor", 1, 0);
                this.j = a.b.b.a.e.a(typedArrayA, xmlPullParser, "fillAlpha", 12, this.j);
                int iB = a.b.b.a.e.b(typedArrayA, xmlPullParser, "strokeLineCap", 8, -1);
                Paint.Cap cap = this.n;
                if (iB == 0) {
                    cap = Paint.Cap.BUTT;
                } else if (iB == 1) {
                    cap = Paint.Cap.ROUND;
                } else if (iB == 2) {
                    cap = Paint.Cap.SQUARE;
                }
                this.n = cap;
                int iB2 = a.b.b.a.e.b(typedArrayA, xmlPullParser, "strokeLineJoin", 9, -1);
                Paint.Join join = this.o;
                if (iB2 == 0) {
                    join = Paint.Join.MITER;
                } else if (iB2 == 1) {
                    join = Paint.Join.ROUND;
                } else if (iB2 == 2) {
                    join = Paint.Join.BEVEL;
                }
                this.o = join;
                this.p = a.b.b.a.e.a(typedArrayA, xmlPullParser, "strokeMiterLimit", 10, this.p);
                this.e = a.b.b.a.e.a(typedArrayA, xmlPullParser, theme, "strokeColor", 3, 0);
                this.h = a.b.b.a.e.a(typedArrayA, xmlPullParser, "strokeAlpha", 11, this.h);
                this.f = a.b.b.a.e.a(typedArrayA, xmlPullParser, "strokeWidth", 4, this.f);
                this.l = a.b.b.a.e.a(typedArrayA, xmlPullParser, "trimPathEnd", 6, this.l);
                this.m = a.b.b.a.e.a(typedArrayA, xmlPullParser, "trimPathOffset", 7, this.m);
                this.k = a.b.b.a.e.a(typedArrayA, xmlPullParser, "trimPathStart", 5, this.k);
                this.i = a.b.b.a.e.b(typedArrayA, xmlPullParser, "fillType", 13, this.i);
            }
            typedArrayA.recycle();
        }

        @Override // a.b.b.a.k.d
        public boolean a() {
            return this.g.c() || this.e.c();
        }

        @Override // a.b.b.a.k.d
        public boolean a(int[] iArr) {
            return this.e.a(iArr) | this.g.a(iArr);
        }

        public float getFillAlpha() {
            return this.j;
        }

        public int getFillColor() {
            return this.g.f55c;
        }

        public float getStrokeAlpha() {
            return this.h;
        }

        public int getStrokeColor() {
            return this.e.f55c;
        }

        public float getStrokeWidth() {
            return this.f;
        }

        public float getTrimPathEnd() {
            return this.l;
        }

        public float getTrimPathOffset() {
            return this.m;
        }

        public float getTrimPathStart() {
            return this.k;
        }

        public void setFillAlpha(float f) {
            this.j = f;
        }

        public void setFillColor(int i) {
            this.g.f55c = i;
        }

        public void setStrokeAlpha(float f) {
            this.h = f;
        }

        public void setStrokeColor(int i) {
            this.e.f55c = i;
        }

        public void setStrokeWidth(float f) {
            this.f = f;
        }

        public void setTrimPathEnd(float f) {
            this.l = f;
        }

        public void setTrimPathOffset(float f) {
            this.m = f;
        }

        public void setTrimPathStart(float f) {
            this.k = f;
        }
    }

    private static class c extends d {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Matrix f20a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final ArrayList<d> f21b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public float f22c;
        public float d;
        public float e;
        public float f;
        public float g;
        public float h;
        public float i;
        public final Matrix j;
        public int k;
        public int[] l;
        public String m;

        public c() {
            super(null);
            this.f20a = new Matrix();
            this.f21b = new ArrayList<>();
            this.f22c = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 1.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = 0.0f;
            this.j = new Matrix();
            this.m = null;
        }

        public c(c cVar, a.b.d.g.b<String, Object> bVar) {
            e aVar;
            super(null);
            this.f20a = new Matrix();
            this.f21b = new ArrayList<>();
            this.f22c = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 1.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = 0.0f;
            this.j = new Matrix();
            this.m = null;
            this.f22c = cVar.f22c;
            this.d = cVar.d;
            this.e = cVar.e;
            this.f = cVar.f;
            this.g = cVar.g;
            this.h = cVar.h;
            this.i = cVar.i;
            this.l = cVar.l;
            this.m = cVar.m;
            this.k = cVar.k;
            String str = this.m;
            if (str != null) {
                bVar.put(str, this);
            }
            this.j.set(cVar.j);
            ArrayList<d> arrayList = cVar.f21b;
            for (int i = 0; i < arrayList.size(); i++) {
                d dVar = arrayList.get(i);
                if (dVar instanceof c) {
                    this.f21b.add(new c((c) dVar, bVar));
                } else {
                    if (dVar instanceof b) {
                        aVar = new b((b) dVar);
                    } else {
                        if (!(dVar instanceof a)) {
                            throw new IllegalStateException("Unknown object in the tree!");
                        }
                        aVar = new a((a) dVar);
                    }
                    this.f21b.add(aVar);
                    String str2 = aVar.f24b;
                    if (str2 != null) {
                        bVar.put(str2, aVar);
                    }
                }
            }
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray typedArrayA = a.b.b.a.e.a(resources, theme, attributeSet, a.b.b.a.a.f1b);
            this.l = null;
            this.f22c = a.b.b.a.e.a(typedArrayA, xmlPullParser, "rotation", 5, this.f22c);
            this.d = typedArrayA.getFloat(1, this.d);
            this.e = typedArrayA.getFloat(2, this.e);
            this.f = a.b.b.a.e.a(typedArrayA, xmlPullParser, "scaleX", 3, this.f);
            this.g = a.b.b.a.e.a(typedArrayA, xmlPullParser, "scaleY", 4, this.g);
            this.h = a.b.b.a.e.a(typedArrayA, xmlPullParser, "translateX", 6, this.h);
            this.i = a.b.b.a.e.a(typedArrayA, xmlPullParser, "translateY", 7, this.i);
            String string = typedArrayA.getString(0);
            if (string != null) {
                this.m = string;
            }
            b();
            typedArrayA.recycle();
        }

        @Override // a.b.b.a.k.d
        public boolean a() {
            for (int i = 0; i < this.f21b.size(); i++) {
                if (this.f21b.get(i).a()) {
                    return true;
                }
            }
            return false;
        }

        @Override // a.b.b.a.k.d
        public boolean a(int[] iArr) {
            boolean zA = false;
            for (int i = 0; i < this.f21b.size(); i++) {
                zA |= this.f21b.get(i).a(iArr);
            }
            return zA;
        }

        public final void b() {
            this.j.reset();
            this.j.postTranslate(-this.d, -this.e);
            this.j.postScale(this.f, this.g);
            this.j.postRotate(this.f22c, 0.0f, 0.0f);
            this.j.postTranslate(this.h + this.d, this.i + this.e);
        }

        public String getGroupName() {
            return this.m;
        }

        public Matrix getLocalMatrix() {
            return this.j;
        }

        public float getPivotX() {
            return this.d;
        }

        public float getPivotY() {
            return this.e;
        }

        public float getRotation() {
            return this.f22c;
        }

        public float getScaleX() {
            return this.f;
        }

        public float getScaleY() {
            return this.g;
        }

        public float getTranslateX() {
            return this.h;
        }

        public float getTranslateY() {
            return this.i;
        }

        public void setPivotX(float f) {
            if (f != this.d) {
                this.d = f;
                b();
            }
        }

        public void setPivotY(float f) {
            if (f != this.e) {
                this.e = f;
                b();
            }
        }

        public void setRotation(float f) {
            if (f != this.f22c) {
                this.f22c = f;
                b();
            }
        }

        public void setScaleX(float f) {
            if (f != this.f) {
                this.f = f;
                b();
            }
        }

        public void setScaleY(float f) {
            if (f != this.g) {
                this.g = f;
                b();
            }
        }

        public void setTranslateX(float f) {
            if (f != this.h) {
                this.h = f;
                b();
            }
        }

        public void setTranslateY(float f) {
            if (f != this.i) {
                this.i = f;
                b();
            }
        }
    }

    private static abstract class d {
        public d() {
        }

        public /* synthetic */ d(j jVar) {
        }

        public boolean a() {
            return false;
        }

        public boolean a(int[] iArr) {
            return false;
        }
    }

    private static abstract class e extends d {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public a.b.d.c.b[] f23a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public String f24b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f25c;

        public e() {
            super(null);
            this.f23a = null;
        }

        public e(e eVar) {
            super(null);
            this.f23a = null;
            this.f24b = eVar.f24b;
            this.f25c = eVar.f25c;
            this.f23a = a.b.b.a.e.a(eVar.f23a);
        }

        public void a(Path path) {
            path.reset();
            a.b.d.c.b[] bVarArr = this.f23a;
            if (bVarArr != null) {
                a.b.d.c.b.a(bVarArr, path);
            }
        }

        public boolean b() {
            return false;
        }

        public a.b.d.c.b[] getPathData() {
            return this.f23a;
        }

        public String getPathName() {
            return this.f24b;
        }

        public void setPathData(a.b.d.c.b[] bVarArr) {
            if (!a.b.b.a.e.a(this.f23a, bVarArr)) {
                this.f23a = a.b.b.a.e.a(bVarArr);
                return;
            }
            a.b.d.c.b[] bVarArr2 = this.f23a;
            for (int i = 0; i < bVarArr.length; i++) {
                bVarArr2[i].f75a = bVarArr[i].f75a;
                for (int i2 = 0; i2 < bVarArr[i].f76b.length; i2++) {
                    bVarArr2[i].f76b[i2] = bVarArr[i].f76b[i2];
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class f {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public static final Matrix f26a = new Matrix();

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final Path f27b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final Path f28c;
        public final Matrix d;
        public Paint e;
        public Paint f;
        public PathMeasure g;
        public int h;
        public final c i;
        public float j;
        public float k;
        public float l;
        public float m;
        public int n;
        public String o;
        public Boolean p;
        public final a.b.d.g.b<String, Object> q;

        public f() {
            this.d = new Matrix();
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            this.n = 255;
            this.o = null;
            this.p = null;
            this.q = new a.b.d.g.b<>();
            this.i = new c();
            this.f27b = new Path();
            this.f28c = new Path();
        }

        public f(f fVar) {
            this.d = new Matrix();
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            this.n = 255;
            this.o = null;
            this.p = null;
            this.q = new a.b.d.g.b<>();
            this.i = new c(fVar.i, this.q);
            this.f27b = new Path(fVar.f27b);
            this.f28c = new Path(fVar.f28c);
            this.j = fVar.j;
            this.k = fVar.k;
            this.l = fVar.l;
            this.m = fVar.m;
            this.h = fVar.h;
            this.n = fVar.n;
            this.o = fVar.o;
            String str = fVar.o;
            if (str != null) {
                this.q.put(str, this);
            }
            this.p = fVar.p;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v0 */
        /* JADX WARN: Type inference failed for: r11v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r11v20 */
        public final void a(c cVar, Matrix matrix, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            f fVar;
            f fVar2 = this;
            cVar.f20a.set(matrix);
            cVar.f20a.preConcat(cVar.j);
            canvas.save();
            ?? r11 = 0;
            int i3 = 0;
            while (i3 < cVar.f21b.size()) {
                d dVar = cVar.f21b.get(i3);
                if (dVar instanceof c) {
                    a((c) dVar, cVar.f20a, canvas, i, i2, colorFilter);
                } else {
                    if (dVar instanceof e) {
                        e eVar = (e) dVar;
                        float f = i / fVar2.l;
                        float f2 = i2 / fVar2.m;
                        float fMin = Math.min(f, f2);
                        Matrix matrix2 = cVar.f20a;
                        fVar2.d.set(matrix2);
                        fVar2.d.postScale(f, f2);
                        float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
                        matrix2.mapVectors(fArr);
                        float fHypot = (float) Math.hypot(fArr[r11], fArr[1]);
                        float fHypot2 = (float) Math.hypot(fArr[2], fArr[3]);
                        float f3 = (fArr[r11] * fArr[3]) - (fArr[1] * fArr[2]);
                        float fMax = Math.max(fHypot, fHypot2);
                        float fAbs = fMax > 0.0f ? Math.abs(f3) / fMax : 0.0f;
                        if (fAbs == 0.0f) {
                            fVar = this;
                        } else {
                            fVar = this;
                            eVar.a(fVar.f27b);
                            Path path = fVar.f27b;
                            fVar.f28c.reset();
                            if (eVar.b()) {
                                fVar.f28c.addPath(path, fVar.d);
                                canvas.clipPath(fVar.f28c);
                            } else {
                                b bVar = (b) eVar;
                                if (bVar.k != 0.0f || bVar.l != 1.0f) {
                                    float f4 = bVar.k;
                                    float f5 = bVar.m;
                                    float f6 = (f4 + f5) % 1.0f;
                                    float f7 = (bVar.l + f5) % 1.0f;
                                    if (fVar.g == null) {
                                        fVar.g = new PathMeasure();
                                    }
                                    fVar.g.setPath(fVar.f27b, r11);
                                    float length = fVar.g.getLength();
                                    float f8 = f6 * length;
                                    float f9 = f7 * length;
                                    path.reset();
                                    if (f8 > f9) {
                                        fVar.g.getSegment(f8, length, path, true);
                                        fVar.g.getSegment(0.0f, f9, path, true);
                                    } else {
                                        fVar.g.getSegment(f8, f9, path, true);
                                    }
                                    path.rLineTo(0.0f, 0.0f);
                                }
                                fVar.f28c.addPath(path, fVar.d);
                                if (bVar.g.d()) {
                                    a.b.d.b.a.a aVar = bVar.g;
                                    if (fVar.f == null) {
                                        fVar.f = new Paint(1);
                                        fVar.f.setStyle(Paint.Style.FILL);
                                    }
                                    Paint paint = fVar.f;
                                    if (aVar.b()) {
                                        Shader shaderA = aVar.a();
                                        shaderA.setLocalMatrix(fVar.d);
                                        paint.setShader(shaderA);
                                        paint.setAlpha(Math.round(bVar.j * 255.0f));
                                    } else {
                                        paint.setColor(k.a(aVar.f55c, bVar.j));
                                    }
                                    paint.setColorFilter(colorFilter);
                                    fVar.f28c.setFillType(bVar.i == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                                    canvas.drawPath(fVar.f28c, paint);
                                }
                                if (bVar.e.d()) {
                                    a.b.d.b.a.a aVar2 = bVar.e;
                                    if (fVar.e == null) {
                                        fVar.e = new Paint(1);
                                        fVar.e.setStyle(Paint.Style.STROKE);
                                    }
                                    Paint paint2 = fVar.e;
                                    Paint.Join join = bVar.o;
                                    if (join != null) {
                                        paint2.setStrokeJoin(join);
                                    }
                                    Paint.Cap cap = bVar.n;
                                    if (cap != null) {
                                        paint2.setStrokeCap(cap);
                                    }
                                    paint2.setStrokeMiter(bVar.p);
                                    if (aVar2.b()) {
                                        Shader shaderA2 = aVar2.a();
                                        shaderA2.setLocalMatrix(fVar.d);
                                        paint2.setShader(shaderA2);
                                        paint2.setAlpha(Math.round(bVar.h * 255.0f));
                                    } else {
                                        paint2.setColor(k.a(aVar2.f55c, bVar.h));
                                    }
                                    paint2.setColorFilter(colorFilter);
                                    paint2.setStrokeWidth(bVar.f * fAbs * fMin);
                                    canvas.drawPath(fVar.f28c, paint2);
                                }
                            }
                        }
                    }
                    i3++;
                    fVar2 = fVar;
                    r11 = 0;
                }
                fVar = fVar2;
                i3++;
                fVar2 = fVar;
                r11 = 0;
            }
            canvas.restore();
        }

        public void a(Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            a(this.i, f26a, canvas, i, i2, colorFilter);
        }

        public boolean a() {
            if (this.p == null) {
                this.p = Boolean.valueOf(this.i.a());
            }
            return this.p.booleanValue();
        }

        public boolean a(int[] iArr) {
            return this.i.a(iArr);
        }

        public float getAlpha() {
            return getRootAlpha() / 255.0f;
        }

        public int getRootAlpha() {
            return this.n;
        }

        public void setAlpha(float f) {
            setRootAlpha((int) (f * 255.0f));
        }

        public void setRootAlpha(int i) {
            this.n = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class g extends Drawable.ConstantState {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f29a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public f f30b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public ColorStateList f31c;
        public PorterDuff.Mode d;
        public boolean e;
        public Bitmap f;
        public ColorStateList g;
        public PorterDuff.Mode h;
        public int i;
        public boolean j;
        public boolean k;
        public Paint l;

        public g() {
            this.f31c = null;
            this.d = k.DEFAULT_TINT_MODE;
            this.f30b = new f();
        }

        public g(g gVar) {
            this.f31c = null;
            this.d = k.DEFAULT_TINT_MODE;
            if (gVar != null) {
                this.f29a = gVar.f29a;
                this.f30b = new f(gVar.f30b);
                Paint paint = gVar.f30b.f;
                if (paint != null) {
                    this.f30b.f = new Paint(paint);
                }
                Paint paint2 = gVar.f30b.e;
                if (paint2 != null) {
                    this.f30b.e = new Paint(paint2);
                }
                this.f31c = gVar.f31c;
                this.d = gVar.d;
                this.e = gVar.e;
            }
        }

        public Paint a(ColorFilter colorFilter) {
            if (!b() && colorFilter == null) {
                return null;
            }
            if (this.l == null) {
                this.l = new Paint();
                this.l.setFilterBitmap(true);
            }
            this.l.setAlpha(this.f30b.getRootAlpha());
            this.l.setColorFilter(colorFilter);
            return this.l;
        }

        public void a(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            canvas.drawBitmap(this.f, (Rect) null, rect, a(colorFilter));
        }

        public boolean a() {
            return !this.k && this.g == this.f31c && this.h == this.d && this.j == this.e && this.i == this.f30b.getRootAlpha();
        }

        public boolean a(int i, int i2) {
            return i == this.f.getWidth() && i2 == this.f.getHeight();
        }

        public boolean a(int[] iArr) {
            boolean zA = this.f30b.a(iArr);
            this.k |= zA;
            return zA;
        }

        public void b(int i, int i2) {
            if (this.f == null || !a(i, i2)) {
                this.f = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                this.k = true;
            }
        }

        public boolean b() {
            return this.f30b.getRootAlpha() < 255;
        }

        public void c(int i, int i2) {
            this.f.eraseColor(0);
            this.f30b.a(new Canvas(this.f), i, i2, null);
        }

        public boolean c() {
            return this.f30b.a();
        }

        public void d() {
            this.g = this.f31c;
            this.h = this.d;
            this.i = this.f30b.getRootAlpha();
            this.j = this.e;
            this.k = false;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f29a;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new k(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new k(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class h extends Drawable.ConstantState {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Drawable.ConstantState f32a;

        public h(Drawable.ConstantState constantState) {
            this.f32a = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.f32a.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f32a.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            k kVar = new k();
            kVar.f17a = (VectorDrawable) this.f32a.newDrawable();
            return kVar;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            k kVar = new k();
            kVar.f17a = (VectorDrawable) this.f32a.newDrawable(resources);
            return kVar;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            k kVar = new k();
            kVar.f17a = (VectorDrawable) this.f32a.newDrawable(resources, theme);
            return kVar;
        }
    }

    public k() {
        this.f = true;
        this.g = new float[9];
        this.h = new Matrix();
        this.i = new Rect();
        this.f18b = new g();
    }

    public k(g gVar) {
        this.f = true;
        this.g = new float[9];
        this.h = new Matrix();
        this.i = new Rect();
        this.f18b = gVar;
        this.f19c = updateTintFilter(this.f19c, gVar.f31c, gVar.d);
    }

    public static int a(int i, float f2) {
        return (i & 16777215) | (((int) (Color.alpha(i) * f2)) << 24);
    }

    public static k a(Resources resources, int i, Resources.Theme theme) {
        int next;
        if (Build.VERSION.SDK_INT >= 24) {
            k kVar = new k();
            kVar.f17a = Build.VERSION.SDK_INT >= 21 ? resources.getDrawable(i, theme) : resources.getDrawable(i);
            new h(kVar.f17a.getConstantState());
            return kVar;
        }
        try {
            XmlResourceParser xml = resources.getXml(i);
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next == 2) {
                return createFromXmlInner(resources, (XmlPullParser) xml, attributeSetAsAttributeSet, theme);
            }
            throw new XmlPullParserException("No start tag found");
        } catch (IOException | XmlPullParserException e2) {
            Log.e("VectorDrawableCompat", "parser error", e2);
            return null;
        }
    }

    public static k createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        k kVar = new k();
        kVar.inflate(resources, xmlPullParser, attributeSet, theme);
        return kVar;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        Drawable drawable = this.f17a;
        if (drawable == null || Build.VERSION.SDK_INT < 21) {
            return false;
        }
        drawable.canApplyTheme();
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00e8  */
    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        int iIntValue;
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        copyBounds(this.i);
        if (this.i.width() <= 0 || this.i.height() <= 0) {
            return;
        }
        ColorFilter colorFilter = this.d;
        if (colorFilter == null) {
            colorFilter = this.f19c;
        }
        canvas.getMatrix(this.h);
        this.h.getValues(this.g);
        float fAbs = Math.abs(this.g[0]);
        float fAbs2 = Math.abs(this.g[4]);
        float fAbs3 = Math.abs(this.g[1]);
        float fAbs4 = Math.abs(this.g[3]);
        if (fAbs3 != 0.0f || fAbs4 != 0.0f) {
            fAbs = 1.0f;
            fAbs2 = 1.0f;
        }
        int iWidth = (int) (this.i.width() * fAbs);
        int iHeight = (int) (this.i.height() * fAbs2);
        int iMin = Math.min(2048, iWidth);
        int iMin2 = Math.min(2048, iHeight);
        if (iMin <= 0 || iMin2 <= 0) {
            return;
        }
        int iSave = canvas.save();
        Rect rect = this.i;
        canvas.translate(rect.left, rect.top);
        int i = Build.VERSION.SDK_INT;
        if (isAutoMirrored()) {
            if (Build.VERSION.SDK_INT >= 23) {
                iIntValue = getLayoutDirection();
            } else {
                if (!a.b.b.a.e.f11b) {
                    try {
                        a.b.b.a.e.f10a = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
                        a.b.b.a.e.f10a.setAccessible(true);
                    } catch (NoSuchMethodException e2) {
                        Log.i("DrawableCompat", "Failed to retrieve getLayoutDirection() method", e2);
                    }
                    a.b.b.a.e.f11b = true;
                }
                Method method = a.b.b.a.e.f10a;
                if (method != null) {
                    try {
                        iIntValue = ((Integer) method.invoke(this, new Object[0])).intValue();
                    } catch (Exception e3) {
                        Log.i("DrawableCompat", "Failed to invoke getLayoutDirection() via reflection", e3);
                        a.b.b.a.e.f10a = null;
                        iIntValue = 0;
                    }
                } else {
                    iIntValue = 0;
                }
            }
            z = iIntValue == 1;
        }
        if (z) {
            canvas.translate(this.i.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        this.i.offsetTo(0, 0);
        this.f18b.b(iMin, iMin2);
        if (!this.f) {
            this.f18b.c(iMin, iMin2);
        } else if (!this.f18b.a()) {
            this.f18b.c(iMin, iMin2);
            this.f18b.d();
        }
        this.f18b.a(canvas, colorFilter, this.i);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        Drawable drawable = this.f17a;
        if (drawable == null) {
            return this.f18b.f30b.getRootAlpha();
        }
        int i = Build.VERSION.SDK_INT;
        return drawable.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        Drawable drawable = this.f17a;
        return drawable != null ? drawable.getChangingConfigurations() : super.getChangingConfigurations() | this.f18b.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        Drawable drawable = this.f17a;
        if (drawable != null && Build.VERSION.SDK_INT >= 24) {
            return new h(drawable.getConstantState());
        }
        this.f18b.f29a = getChangingConfigurations();
        return this.f18b;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Drawable drawable = this.f17a;
        return drawable != null ? drawable.getIntrinsicHeight() : (int) this.f18b.f30b.k;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Drawable drawable = this.f17a;
        return drawable != null ? drawable.getIntrinsicWidth() : (int) this.f18b.f30b.j;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
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
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int i;
        int i2;
        e eVar;
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
        g gVar = this.f18b;
        gVar.f30b = new f();
        TypedArray typedArrayA = a.b.b.a.e.a(resources, theme, attributeSet, a.b.b.a.a.f0a);
        g gVar2 = this.f18b;
        f fVar = gVar2.f30b;
        int iB = a.b.b.a.e.b(typedArrayA, xmlPullParser, "tintMode", 6, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        if (iB == 3) {
            mode = PorterDuff.Mode.SRC_OVER;
        } else if (iB != 5) {
            if (iB != 9) {
                switch (iB) {
                    case 14:
                        mode = PorterDuff.Mode.MULTIPLY;
                        break;
                    case a.b.e.b.j.AppCompatTheme_actionMenuTextAppearance /* 15 */:
                        mode = PorterDuff.Mode.SCREEN;
                        break;
                    case 16:
                        mode = PorterDuff.Mode.ADD;
                        break;
                }
            } else {
                mode = PorterDuff.Mode.SRC_ATOP;
            }
        }
        gVar2.d = mode;
        int i3 = 1;
        ColorStateList colorStateList = typedArrayA.getColorStateList(1);
        if (colorStateList != null) {
            gVar2.f31c = colorStateList;
        }
        boolean z = gVar2.e;
        if (a.b.b.a.e.a(xmlPullParser, "autoMirrored")) {
            z = typedArrayA.getBoolean(5, z);
        }
        gVar2.e = z;
        fVar.l = a.b.b.a.e.a(typedArrayA, xmlPullParser, "viewportWidth", 7, fVar.l);
        fVar.m = a.b.b.a.e.a(typedArrayA, xmlPullParser, "viewportHeight", 8, fVar.m);
        if (fVar.l <= 0.0f) {
            throw new XmlPullParserException(typedArrayA.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (fVar.m <= 0.0f) {
            throw new XmlPullParserException(typedArrayA.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
        fVar.j = typedArrayA.getDimension(3, fVar.j);
        int i4 = 2;
        fVar.k = typedArrayA.getDimension(2, fVar.k);
        if (fVar.j <= 0.0f) {
            throw new XmlPullParserException(typedArrayA.getPositionDescription() + "<vector> tag requires width > 0");
        }
        if (fVar.k <= 0.0f) {
            throw new XmlPullParserException(typedArrayA.getPositionDescription() + "<vector> tag requires height > 0");
        }
        fVar.setAlpha(a.b.b.a.e.a(typedArrayA, xmlPullParser, "alpha", 4, fVar.getAlpha()));
        String string = typedArrayA.getString(0);
        if (string != null) {
            fVar.o = string;
            fVar.q.put(string, fVar);
        }
        typedArrayA.recycle();
        gVar.f29a = getChangingConfigurations();
        gVar.k = true;
        g gVar3 = this.f18b;
        f fVar2 = gVar3.f30b;
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(fVar2.i);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z2 = true;
        while (eventType != i3 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == i4) {
                String name = xmlPullParser.getName();
                c cVar = (c) arrayDeque.peek();
                if ("path".equals(name)) {
                    b bVar = new b();
                    bVar.a(resources, attributeSet, theme, xmlPullParser);
                    cVar.f21b.add(bVar);
                    if (bVar.getPathName() != null) {
                        fVar2.q.put(bVar.getPathName(), bVar);
                    }
                    z2 = false;
                    eVar = bVar;
                } else if ("clip-path".equals(name)) {
                    a aVar = new a();
                    aVar.a(resources, attributeSet, theme, xmlPullParser);
                    cVar.f21b.add(aVar);
                    String pathName = aVar.getPathName();
                    eVar = aVar;
                    if (pathName != null) {
                        fVar2.q.put(aVar.getPathName(), aVar);
                        eVar = aVar;
                    }
                } else if ("group".equals(name)) {
                    c cVar2 = new c();
                    cVar2.a(resources, attributeSet, theme, xmlPullParser);
                    cVar.f21b.add(cVar2);
                    arrayDeque.push(cVar2);
                    if (cVar2.getGroupName() != null) {
                        fVar2.q.put(cVar2.getGroupName(), cVar2);
                    }
                    i = gVar3.f29a;
                    i2 = cVar2.k;
                    gVar3.f29a = i2 | i;
                }
                i = gVar3.f29a;
                i2 = eVar.f25c;
                gVar3.f29a = i2 | i;
            } else if (eventType == 3 && "group".equals(xmlPullParser.getName())) {
                arrayDeque.pop();
            }
            eventType = xmlPullParser.next();
            i3 = 1;
            i4 = 2;
        }
        if (z2) {
            throw new XmlPullParserException("no path defined");
        }
        this.f19c = updateTintFilter(this.f19c, gVar.f31c, gVar.d);
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        Drawable drawable = this.f17a;
        return drawable != null ? a.b.b.a.e.a(drawable) : this.f18b.e;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        g gVar;
        ColorStateList colorStateList;
        Drawable drawable = this.f17a;
        if (drawable != null) {
            return drawable.isStateful();
        }
        return super.isStateful() || ((gVar = this.f18b) != null && (gVar.c() || ((colorStateList = this.f18b.f31c) != null && colorStateList.isStateful())));
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.e && super.mutate() == this) {
            this.f18b = new g(this.f18b);
            this.e = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        Drawable drawable = this.f17a;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        boolean z = false;
        g gVar = this.f18b;
        ColorStateList colorStateList = gVar.f31c;
        if (colorStateList != null && (mode = gVar.d) != null) {
            this.f19c = updateTintFilter(this.f19c, colorStateList, mode);
            invalidateSelf();
            z = true;
        }
        if (!gVar.c() || !gVar.a(iArr)) {
            return z;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void scheduleSelf(Runnable runnable, long j) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, j);
        } else {
            super.scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.setAlpha(i);
        } else if (this.f18b.f30b.getRootAlpha() != i) {
            this.f18b.f30b.setRootAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        Drawable drawable = this.f17a;
        if (drawable == null) {
            this.f18b.e = z;
        } else {
            int i = Build.VERSION.SDK_INT;
            drawable.setAutoMirrored(z);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        } else {
            this.d = colorFilter;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTint(int i) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            a.b.b.a.e.a(drawable, i);
        } else {
            setTintList(ColorStateList.valueOf(i));
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            a.b.b.a.e.a(drawable, colorStateList);
            return;
        }
        g gVar = this.f18b;
        if (gVar.f31c != colorStateList) {
            gVar.f31c = colorStateList;
            this.f19c = updateTintFilter(this.f19c, colorStateList, gVar.d);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.a.a
    public void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            a.b.b.a.e.a(drawable, mode);
            return;
        }
        g gVar = this.f18b;
        if (gVar.d != mode) {
            gVar.d = mode;
            this.f19c = updateTintFilter(this.f19c, gVar.f31c, mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.f17a;
        return drawable != null ? drawable.setVisible(z, z2) : super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.f17a;
        if (drawable != null) {
            drawable.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }

    public PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }
}
