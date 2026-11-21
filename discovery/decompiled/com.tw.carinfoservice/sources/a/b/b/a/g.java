package a.b.b.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class g extends a.b.b.a.f {
    public static final PorterDuff.Mode k = PorterDuff.Mode.SRC_IN;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public h f20c;
    public PorterDuffColorFilter d;
    public ColorFilter e;
    public boolean f;
    public boolean g;
    public final float[] h;
    public final Matrix i;
    public final Rect j;

    public static class b extends f {
        public b() {
        }

        public b(b bVar) {
            super(bVar);
        }

        @Override // a.b.b.a.g.f
        public boolean c() {
            return true;
        }
    }

    public static class c extends f {
        public int[] d;
        public a.b.d.b.b.a e;
        public float f;
        public a.b.d.b.b.a g;
        public float h;
        public int i;
        public float j;
        public float k;
        public float l;
        public float m;
        public Paint.Cap n;
        public Paint.Join o;
        public float p;

        public c() {
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

        public c(c cVar) {
            super(cVar);
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
            this.d = cVar.d;
            this.e = cVar.e;
            this.f = cVar.f;
            this.h = cVar.h;
            this.g = cVar.g;
            this.i = cVar.i;
            this.j = cVar.j;
            this.k = cVar.k;
            this.l = cVar.l;
            this.m = cVar.m;
            this.n = cVar.n;
            this.o = cVar.o;
            this.p = cVar.p;
        }

        @Override // a.b.b.a.g.e
        public boolean a() {
            return this.g.c() || this.e.c();
        }

        @Override // a.b.b.a.g.e
        public boolean b(int[] iArr) {
            return this.e.d(iArr) | this.g.d(iArr);
        }

        public float getFillAlpha() {
            return this.j;
        }

        public int getFillColor() {
            return this.g.f52c;
        }

        public float getStrokeAlpha() {
            return this.h;
        }

        public int getStrokeColor() {
            return this.e.f52c;
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
            this.g.f52c = i;
        }

        public void setStrokeAlpha(float f) {
            this.h = f;
        }

        public void setStrokeColor(int i) {
            this.e.f52c = i;
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

    public static class d extends e {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Matrix f21a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final ArrayList<e> f22b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public float f23c;
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

        public d() {
            super(null);
            this.f21a = new Matrix();
            this.f22b = new ArrayList<>();
            this.f23c = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 1.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = 0.0f;
            this.j = new Matrix();
            this.m = null;
        }

        public d(d dVar, a.b.d.g.a<String, Object> aVar) {
            f bVar;
            super(null);
            this.f21a = new Matrix();
            this.f22b = new ArrayList<>();
            this.f23c = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 1.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = 0.0f;
            Matrix matrix = new Matrix();
            this.j = matrix;
            this.m = null;
            this.f23c = dVar.f23c;
            this.d = dVar.d;
            this.e = dVar.e;
            this.f = dVar.f;
            this.g = dVar.g;
            this.h = dVar.h;
            this.i = dVar.i;
            this.l = dVar.l;
            String str = dVar.m;
            this.m = str;
            this.k = dVar.k;
            if (str != null) {
                aVar.put(str, this);
            }
            matrix.set(dVar.j);
            ArrayList<e> arrayList = dVar.f22b;
            for (int i = 0; i < arrayList.size(); i++) {
                e eVar = arrayList.get(i);
                if (eVar instanceof d) {
                    this.f22b.add(new d((d) eVar, aVar));
                } else {
                    if (eVar instanceof c) {
                        bVar = new c((c) eVar);
                    } else {
                        if (!(eVar instanceof b)) {
                            throw new IllegalStateException("Unknown object in the tree!");
                        }
                        bVar = new b((b) eVar);
                    }
                    this.f22b.add(bVar);
                    String str2 = bVar.f25b;
                    if (str2 != null) {
                        aVar.put(str2, bVar);
                    }
                }
            }
        }

        @Override // a.b.b.a.g.e
        public boolean a() {
            for (int i = 0; i < this.f22b.size(); i++) {
                if (this.f22b.get(i).a()) {
                    return true;
                }
            }
            return false;
        }

        @Override // a.b.b.a.g.e
        public boolean b(int[] iArr) {
            boolean zB = false;
            for (int i = 0; i < this.f22b.size(); i++) {
                zB |= this.f22b.get(i).b(iArr);
            }
            return zB;
        }

        public final void c() {
            this.j.reset();
            this.j.postTranslate(-this.d, -this.e);
            this.j.postScale(this.f, this.g);
            this.j.postRotate(this.f23c, 0.0f, 0.0f);
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
            return this.f23c;
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
                c();
            }
        }

        public void setPivotY(float f) {
            if (f != this.e) {
                this.e = f;
                c();
            }
        }

        public void setRotation(float f) {
            if (f != this.f23c) {
                this.f23c = f;
                c();
            }
        }

        public void setScaleX(float f) {
            if (f != this.f) {
                this.f = f;
                c();
            }
        }

        public void setScaleY(float f) {
            if (f != this.g) {
                this.g = f;
                c();
            }
        }

        public void setTranslateX(float f) {
            if (f != this.h) {
                this.h = f;
                c();
            }
        }

        public void setTranslateY(float f) {
            if (f != this.i) {
                this.i = f;
                c();
            }
        }
    }

    public static abstract class e {
        public e() {
        }

        public e(a aVar) {
        }

        public boolean a() {
            return false;
        }

        public boolean b(int[] iArr) {
            return false;
        }
    }

    public static abstract class f extends e {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public a.b.d.c.b[] f24a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public String f25b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f26c;

        public f() {
            super(null);
            this.f24a = null;
        }

        public f(f fVar) {
            super(null);
            this.f24a = null;
            this.f25b = fVar.f25b;
            this.f26c = fVar.f26c;
            this.f24a = a.b.a.a.l(fVar.f24a);
        }

        public boolean c() {
            return false;
        }

        public a.b.d.c.b[] getPathData() {
            return this.f24a;
        }

        public String getPathName() {
            return this.f25b;
        }

        public void setPathData(a.b.d.c.b[] bVarArr) {
            if (!a.b.a.a.c(this.f24a, bVarArr)) {
                this.f24a = a.b.a.a.l(bVarArr);
                return;
            }
            a.b.d.c.b[] bVarArr2 = this.f24a;
            for (int i = 0; i < bVarArr.length; i++) {
                bVarArr2[i].f67a = bVarArr[i].f67a;
                for (int i2 = 0; i2 < bVarArr[i].f68b.length; i2++) {
                    bVarArr2[i].f68b[i2] = bVarArr[i].f68b[i2];
                }
            }
        }
    }

    /* JADX INFO: renamed from: a.b.b.a.g$g, reason: collision with other inner class name */
    public static class C0001g {
        public static final Matrix q = new Matrix();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Path f27a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final Path f28b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final Matrix f29c;
        public Paint d;
        public Paint e;
        public PathMeasure f;
        public int g;
        public final d h;
        public float i;
        public float j;
        public float k;
        public float l;
        public int m;
        public String n;
        public Boolean o;
        public final a.b.d.g.a<String, Object> p;

        public C0001g() {
            this.f29c = new Matrix();
            this.i = 0.0f;
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 255;
            this.n = null;
            this.o = null;
            this.p = new a.b.d.g.a<>();
            this.h = new d();
            this.f27a = new Path();
            this.f28b = new Path();
        }

        public C0001g(C0001g c0001g) {
            this.f29c = new Matrix();
            this.i = 0.0f;
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 255;
            this.n = null;
            this.o = null;
            a.b.d.g.a<String, Object> aVar = new a.b.d.g.a<>();
            this.p = aVar;
            this.h = new d(c0001g.h, aVar);
            this.f27a = new Path(c0001g.f27a);
            this.f28b = new Path(c0001g.f28b);
            this.i = c0001g.i;
            this.j = c0001g.j;
            this.k = c0001g.k;
            this.l = c0001g.l;
            this.g = c0001g.g;
            this.m = c0001g.m;
            this.n = c0001g.n;
            String str = c0001g.n;
            if (str != null) {
                aVar.put(str, this);
            }
            this.o = c0001g.o;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v0 */
        /* JADX WARN: Type inference failed for: r11v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r11v11 */
        public final void a(d dVar, Matrix matrix, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            C0001g c0001g;
            C0001g c0001g2 = this;
            dVar.f21a.set(matrix);
            dVar.f21a.preConcat(dVar.j);
            canvas.save();
            ?? r11 = 0;
            int i3 = 0;
            while (i3 < dVar.f22b.size()) {
                e eVar = dVar.f22b.get(i3);
                if (eVar instanceof d) {
                    a((d) eVar, dVar.f21a, canvas, i, i2, colorFilter);
                } else {
                    if (eVar instanceof f) {
                        f fVar = (f) eVar;
                        float f = i / c0001g2.k;
                        float f2 = i2 / c0001g2.l;
                        float fMin = Math.min(f, f2);
                        Matrix matrix2 = dVar.f21a;
                        c0001g2.f29c.set(matrix2);
                        c0001g2.f29c.postScale(f, f2);
                        float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
                        matrix2.mapVectors(fArr);
                        float fHypot = (float) Math.hypot(fArr[r11], fArr[1]);
                        float fHypot2 = (float) Math.hypot(fArr[2], fArr[3]);
                        float f3 = (fArr[r11] * fArr[3]) - (fArr[1] * fArr[2]);
                        float fMax = Math.max(fHypot, fHypot2);
                        float fAbs = fMax > 0.0f ? Math.abs(f3) / fMax : 0.0f;
                        if (fAbs == 0.0f) {
                            c0001g = this;
                        } else {
                            c0001g = this;
                            Path path = c0001g.f27a;
                            Objects.requireNonNull(fVar);
                            path.reset();
                            a.b.d.c.b[] bVarArr = fVar.f24a;
                            if (bVarArr != null) {
                                a.b.d.c.b.b(bVarArr, path);
                            }
                            Path path2 = c0001g.f27a;
                            c0001g.f28b.reset();
                            if (fVar.c()) {
                                c0001g.f28b.addPath(path2, c0001g.f29c);
                                canvas.clipPath(c0001g.f28b);
                            } else {
                                c cVar = (c) fVar;
                                float f4 = cVar.k;
                                if (f4 != 0.0f || cVar.l != 1.0f) {
                                    float f5 = cVar.m;
                                    float f6 = (f4 + f5) % 1.0f;
                                    float f7 = (cVar.l + f5) % 1.0f;
                                    if (c0001g.f == null) {
                                        c0001g.f = new PathMeasure();
                                    }
                                    c0001g.f.setPath(c0001g.f27a, r11);
                                    float length = c0001g.f.getLength();
                                    float f8 = f6 * length;
                                    float f9 = f7 * length;
                                    path2.reset();
                                    if (f8 > f9) {
                                        c0001g.f.getSegment(f8, length, path2, true);
                                        c0001g.f.getSegment(0.0f, f9, path2, true);
                                    } else {
                                        c0001g.f.getSegment(f8, f9, path2, true);
                                    }
                                    path2.rLineTo(0.0f, 0.0f);
                                }
                                c0001g.f28b.addPath(path2, c0001g.f29c);
                                a.b.d.b.b.a aVar = cVar.g;
                                if (aVar.b() || aVar.f52c != 0) {
                                    a.b.d.b.b.a aVar2 = cVar.g;
                                    if (c0001g.e == null) {
                                        Paint paint = new Paint(1);
                                        c0001g.e = paint;
                                        paint.setStyle(Paint.Style.FILL);
                                    }
                                    Paint paint2 = c0001g.e;
                                    if (aVar2.b()) {
                                        Shader shader = aVar2.f50a;
                                        shader.setLocalMatrix(c0001g.f29c);
                                        paint2.setShader(shader);
                                        paint2.setAlpha(Math.round(cVar.j * 255.0f));
                                    } else {
                                        int i4 = aVar2.f52c;
                                        float f10 = cVar.j;
                                        PorterDuff.Mode mode = g.k;
                                        paint2.setColor((i4 & 16777215) | (((int) (Color.alpha(i4) * f10)) << 24));
                                    }
                                    paint2.setColorFilter(colorFilter);
                                    c0001g.f28b.setFillType(cVar.i == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                                    canvas.drawPath(c0001g.f28b, paint2);
                                }
                                a.b.d.b.b.a aVar3 = cVar.e;
                                if (aVar3.b() || aVar3.f52c != 0) {
                                    a.b.d.b.b.a aVar4 = cVar.e;
                                    if (c0001g.d == null) {
                                        Paint paint3 = new Paint(1);
                                        c0001g.d = paint3;
                                        paint3.setStyle(Paint.Style.STROKE);
                                    }
                                    Paint paint4 = c0001g.d;
                                    Paint.Join join = cVar.o;
                                    if (join != null) {
                                        paint4.setStrokeJoin(join);
                                    }
                                    Paint.Cap cap = cVar.n;
                                    if (cap != null) {
                                        paint4.setStrokeCap(cap);
                                    }
                                    paint4.setStrokeMiter(cVar.p);
                                    if (aVar4.b()) {
                                        Shader shader2 = aVar4.f50a;
                                        shader2.setLocalMatrix(c0001g.f29c);
                                        paint4.setShader(shader2);
                                        paint4.setAlpha(Math.round(cVar.h * 255.0f));
                                    } else {
                                        int i5 = aVar4.f52c;
                                        float f11 = cVar.h;
                                        PorterDuff.Mode mode2 = g.k;
                                        paint4.setColor((i5 & 16777215) | (((int) (Color.alpha(i5) * f11)) << 24));
                                    }
                                    paint4.setColorFilter(colorFilter);
                                    paint4.setStrokeWidth(cVar.f * fAbs * fMin);
                                    canvas.drawPath(c0001g.f28b, paint4);
                                }
                            }
                        }
                    }
                    i3++;
                    c0001g2 = c0001g;
                    r11 = 0;
                }
                c0001g = c0001g2;
                i3++;
                c0001g2 = c0001g;
                r11 = 0;
            }
            canvas.restore();
        }

        public float getAlpha() {
            return getRootAlpha() / 255.0f;
        }

        public int getRootAlpha() {
            return this.m;
        }

        public void setAlpha(float f) {
            setRootAlpha((int) (f * 255.0f));
        }

        public void setRootAlpha(int i) {
            this.m = i;
        }
    }

    public static class h extends Drawable.ConstantState {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f30a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public C0001g f31b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public ColorStateList f32c;
        public PorterDuff.Mode d;
        public boolean e;
        public Bitmap f;
        public ColorStateList g;
        public PorterDuff.Mode h;
        public int i;
        public boolean j;
        public boolean k;
        public Paint l;

        public h() {
            this.f32c = null;
            this.d = g.k;
            this.f31b = new C0001g();
        }

        public h(h hVar) {
            this.f32c = null;
            this.d = g.k;
            if (hVar != null) {
                this.f30a = hVar.f30a;
                C0001g c0001g = new C0001g(hVar.f31b);
                this.f31b = c0001g;
                Paint paint = hVar.f31b.e;
                if (paint != null) {
                    c0001g.e = new Paint(paint);
                }
                Paint paint2 = hVar.f31b.d;
                if (paint2 != null) {
                    this.f31b.d = new Paint(paint2);
                }
                this.f32c = hVar.f32c;
                this.d = hVar.d;
                this.e = hVar.e;
            }
        }

        public boolean a() {
            C0001g c0001g = this.f31b;
            if (c0001g.o == null) {
                c0001g.o = Boolean.valueOf(c0001g.h.a());
            }
            return c0001g.o.booleanValue();
        }

        public void b(int i, int i2) {
            this.f.eraseColor(0);
            Canvas canvas = new Canvas(this.f);
            C0001g c0001g = this.f31b;
            c0001g.a(c0001g.h, C0001g.q, canvas, i, i2, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f30a;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new g(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new g(this);
        }
    }

    public static class i extends Drawable.ConstantState {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Drawable.ConstantState f33a;

        public i(Drawable.ConstantState constantState) {
            this.f33a = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.f33a.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f33a.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            g gVar = new g();
            gVar.f19b = (VectorDrawable) this.f33a.newDrawable();
            return gVar;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            g gVar = new g();
            gVar.f19b = (VectorDrawable) this.f33a.newDrawable(resources);
            return gVar;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            g gVar = new g();
            gVar.f19b = (VectorDrawable) this.f33a.newDrawable(resources, theme);
            return gVar;
        }
    }

    public g() {
        this.g = true;
        this.h = new float[9];
        this.i = new Matrix();
        this.j = new Rect();
        this.f20c = new h();
    }

    public g(h hVar) {
        this.g = true;
        this.h = new float[9];
        this.i = new Matrix();
        this.j = new Rect();
        this.f20c = hVar;
        this.d = updateTintFilter(this.d, hVar.f32c, hVar.d);
    }

    public static g a(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        g gVar = new g();
        gVar.inflate(resources, xmlPullParser, attributeSet, theme);
        return gVar;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        Drawable drawable = this.f19b;
        if (drawable == null) {
            return false;
        }
        a.b.a.a.b(drawable);
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:56:0x0115  */
    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        Paint paint;
        int iIntValue;
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        copyBounds(this.j);
        if (this.j.width() <= 0 || this.j.height() <= 0) {
            return;
        }
        ColorFilter colorFilter = this.e;
        if (colorFilter == null) {
            colorFilter = this.d;
        }
        canvas.getMatrix(this.i);
        this.i.getValues(this.h);
        float fAbs = Math.abs(this.h[0]);
        float fAbs2 = Math.abs(this.h[4]);
        float fAbs3 = Math.abs(this.h[1]);
        float fAbs4 = Math.abs(this.h[3]);
        if (fAbs3 != 0.0f || fAbs4 != 0.0f) {
            fAbs = 1.0f;
            fAbs2 = 1.0f;
        }
        int iWidth = (int) (this.j.width() * fAbs);
        int iHeight = (int) (this.j.height() * fAbs2);
        int iMin = Math.min(2048, iWidth);
        int iMin2 = Math.min(2048, iHeight);
        if (iMin <= 0 || iMin2 <= 0) {
            return;
        }
        int iSave = canvas.save();
        Rect rect = this.j;
        canvas.translate(rect.left, rect.top);
        int i2 = Build.VERSION.SDK_INT;
        if (isAutoMirrored()) {
            if (i2 >= 23) {
                iIntValue = getLayoutDirection();
            } else {
                if (!a.b.a.a.f1b) {
                    try {
                        Method declaredMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
                        a.b.a.a.f0a = declaredMethod;
                        declaredMethod.setAccessible(true);
                    } catch (NoSuchMethodException e2) {
                        Log.i("DrawableCompat", "Failed to retrieve getLayoutDirection() method", e2);
                    }
                    a.b.a.a.f1b = true;
                }
                Method method = a.b.a.a.f0a;
                if (method != null) {
                    try {
                        iIntValue = ((Integer) method.invoke(this, new Object[0])).intValue();
                    } catch (Exception e3) {
                        Log.i("DrawableCompat", "Failed to invoke getLayoutDirection() via reflection", e3);
                        a.b.a.a.f0a = null;
                        iIntValue = 0;
                    }
                } else {
                    iIntValue = 0;
                }
            }
            if (iIntValue == 1) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        if (z) {
            canvas.translate(this.j.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        this.j.offsetTo(0, 0);
        h hVar = this.f20c;
        Bitmap bitmap = hVar.f;
        if (bitmap == null) {
            hVar.f = Bitmap.createBitmap(iMin, iMin2, Bitmap.Config.ARGB_8888);
            hVar.k = true;
        } else {
            if (!(iMin == bitmap.getWidth() && iMin2 == hVar.f.getHeight())) {
                hVar.f = Bitmap.createBitmap(iMin, iMin2, Bitmap.Config.ARGB_8888);
                hVar.k = true;
            }
        }
        if (this.g) {
            h hVar2 = this.f20c;
            if (!(!hVar2.k && hVar2.g == hVar2.f32c && hVar2.h == hVar2.d && hVar2.j == hVar2.e && hVar2.i == hVar2.f31b.getRootAlpha())) {
                this.f20c.b(iMin, iMin2);
                h hVar3 = this.f20c;
                hVar3.g = hVar3.f32c;
                hVar3.h = hVar3.d;
                hVar3.i = hVar3.f31b.getRootAlpha();
                hVar3.j = hVar3.e;
                hVar3.k = false;
            }
        } else {
            this.f20c.b(iMin, iMin2);
        }
        h hVar4 = this.f20c;
        Rect rect2 = this.j;
        if ((hVar4.f31b.getRootAlpha() < 255) || colorFilter != null) {
            if (hVar4.l == null) {
                Paint paint2 = new Paint();
                hVar4.l = paint2;
                paint2.setFilterBitmap(true);
            }
            hVar4.l.setAlpha(hVar4.f31b.getRootAlpha());
            hVar4.l.setColorFilter(colorFilter);
            paint = hVar4.l;
        } else {
            paint = null;
        }
        canvas.drawBitmap(hVar4.f, (Rect) null, rect2, paint);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        Drawable drawable = this.f19b;
        return drawable != null ? a.b.a.a.m(drawable) : this.f20c.f31b.getRootAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.getChangingConfigurations() : super.getChangingConfigurations() | this.f20c.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        Drawable drawable = this.f19b;
        if (drawable != null && Build.VERSION.SDK_INT >= 24) {
            return new i(drawable.getConstantState());
        }
        this.f20c.f30a = getChangingConfigurations();
        return this.f20c;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.getIntrinsicHeight() : (int) this.f20c.f31b.j;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.getIntrinsicWidth() : (int) this.f20c.f31b.i;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        ArrayDeque arrayDeque;
        boolean z;
        TypedArray typedArray;
        int i2;
        Drawable drawable = this.f19b;
        if (drawable != null) {
            a.b.a.a.t(drawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        h hVar = this.f20c;
        hVar.f31b = new C0001g();
        TypedArray typedArrayY = a.b.a.a.y(resources, theme, attributeSet, a.b.b.a.a.f6a);
        h hVar2 = this.f20c;
        C0001g c0001g = hVar2.f31b;
        int i3 = !a.b.a.a.s(xmlPullParser, "tintMode") ? -1 : typedArrayY.getInt(6, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        int i4 = 3;
        if (i3 == 3) {
            mode = PorterDuff.Mode.SRC_OVER;
        } else if (i3 != 5) {
            if (i3 != 9) {
                switch (i3) {
                    case 14:
                        mode = PorterDuff.Mode.MULTIPLY;
                        break;
                    case 15:
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
        hVar2.d = mode;
        ColorStateList colorStateList = typedArrayY.getColorStateList(1);
        if (colorStateList != null) {
            hVar2.f32c = colorStateList;
        }
        boolean z2 = hVar2.e;
        if (a.b.a.a.s(xmlPullParser, "autoMirrored")) {
            z2 = typedArrayY.getBoolean(5, z2);
        }
        hVar2.e = z2;
        float f2 = c0001g.k;
        if (a.b.a.a.s(xmlPullParser, "viewportWidth")) {
            f2 = typedArrayY.getFloat(7, f2);
        }
        c0001g.k = f2;
        float f3 = c0001g.l;
        if (a.b.a.a.s(xmlPullParser, "viewportHeight")) {
            f3 = typedArrayY.getFloat(8, f3);
        }
        c0001g.l = f3;
        if (c0001g.k <= 0.0f) {
            throw new XmlPullParserException(typedArrayY.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (f3 <= 0.0f) {
            throw new XmlPullParserException(typedArrayY.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
        c0001g.i = typedArrayY.getDimension(3, c0001g.i);
        int i5 = 2;
        float dimension = typedArrayY.getDimension(2, c0001g.j);
        c0001g.j = dimension;
        if (c0001g.i <= 0.0f) {
            throw new XmlPullParserException(typedArrayY.getPositionDescription() + "<vector> tag requires width > 0");
        }
        if (dimension <= 0.0f) {
            throw new XmlPullParserException(typedArrayY.getPositionDescription() + "<vector> tag requires height > 0");
        }
        float alpha = c0001g.getAlpha();
        if (a.b.a.a.s(xmlPullParser, "alpha")) {
            alpha = typedArrayY.getFloat(4, alpha);
        }
        c0001g.setAlpha(alpha);
        String string = typedArrayY.getString(0);
        if (string != null) {
            c0001g.n = string;
            c0001g.p.put(string, c0001g);
        }
        typedArrayY.recycle();
        hVar.f30a = getChangingConfigurations();
        hVar.k = true;
        h hVar3 = this.f20c;
        C0001g c0001g2 = hVar3.f31b;
        ArrayDeque arrayDeque2 = new ArrayDeque();
        arrayDeque2.push(c0001g2.h);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z3 = true;
        for (int i6 = 1; eventType != i6 && (xmlPullParser.getDepth() >= depth || eventType != i4); i6 = 1) {
            if (eventType == i5) {
                String name = xmlPullParser.getName();
                d dVar = (d) arrayDeque2.peek();
                if ("path".equals(name)) {
                    c cVar = new c();
                    TypedArray typedArrayY2 = a.b.a.a.y(resources, theme, attributeSet, a.b.b.a.a.f8c);
                    cVar.d = null;
                    if (a.b.a.a.s(xmlPullParser, "pathData")) {
                        String string2 = typedArrayY2.getString(0);
                        if (string2 != null) {
                            cVar.f25b = string2;
                        }
                        String string3 = typedArrayY2.getString(2);
                        if (string3 != null) {
                            cVar.f24a = a.b.a.a.j(string3);
                        }
                        typedArray = typedArrayY2;
                        cVar.g = a.b.a.a.o(typedArrayY2, xmlPullParser, theme, "fillColor", 1, 0);
                        float f4 = cVar.j;
                        if (a.b.a.a.s(xmlPullParser, "fillAlpha")) {
                            f4 = typedArray.getFloat(12, f4);
                        }
                        cVar.j = f4;
                        int i7 = !a.b.a.a.s(xmlPullParser, "strokeLineCap") ? -1 : typedArray.getInt(8, -1);
                        Paint.Cap cap = cVar.n;
                        if (i7 == 0) {
                            i2 = 2;
                            cap = Paint.Cap.BUTT;
                        } else if (i7 != 1) {
                            i2 = 2;
                            if (i7 == 2) {
                                cap = Paint.Cap.SQUARE;
                            }
                        } else {
                            i2 = 2;
                            cap = Paint.Cap.ROUND;
                        }
                        cVar.n = cap;
                        int i8 = !a.b.a.a.s(xmlPullParser, "strokeLineJoin") ? -1 : typedArray.getInt(9, -1);
                        Paint.Join join = cVar.o;
                        if (i8 == 0) {
                            join = Paint.Join.MITER;
                        } else if (i8 == 1) {
                            join = Paint.Join.ROUND;
                        } else if (i8 == i2) {
                            join = Paint.Join.BEVEL;
                        }
                        cVar.o = join;
                        float f5 = cVar.p;
                        if (a.b.a.a.s(xmlPullParser, "strokeMiterLimit")) {
                            f5 = typedArray.getFloat(10, f5);
                        }
                        cVar.p = f5;
                        cVar.e = a.b.a.a.o(typedArray, xmlPullParser, theme, "strokeColor", 3, 0);
                        float f6 = cVar.h;
                        if (a.b.a.a.s(xmlPullParser, "strokeAlpha")) {
                            f6 = typedArray.getFloat(11, f6);
                        }
                        cVar.h = f6;
                        float f7 = cVar.f;
                        if (a.b.a.a.s(xmlPullParser, "strokeWidth")) {
                            f7 = typedArray.getFloat(4, f7);
                        }
                        cVar.f = f7;
                        float f8 = cVar.l;
                        if (a.b.a.a.s(xmlPullParser, "trimPathEnd")) {
                            f8 = typedArray.getFloat(6, f8);
                        }
                        cVar.l = f8;
                        float f9 = cVar.m;
                        if (a.b.a.a.s(xmlPullParser, "trimPathOffset")) {
                            f9 = typedArray.getFloat(7, f9);
                        }
                        cVar.m = f9;
                        float f10 = cVar.k;
                        if (a.b.a.a.s(xmlPullParser, "trimPathStart")) {
                            f10 = typedArray.getFloat(5, f10);
                        }
                        cVar.k = f10;
                        int i9 = cVar.i;
                        if (a.b.a.a.s(xmlPullParser, "fillType")) {
                            i9 = typedArray.getInt(13, i9);
                        }
                        cVar.i = i9;
                    } else {
                        typedArray = typedArrayY2;
                    }
                    typedArray.recycle();
                    dVar.f22b.add(cVar);
                    c0001g2 = c0001g2;
                    if (cVar.getPathName() != null) {
                        c0001g2.p.put(cVar.getPathName(), cVar);
                    }
                    hVar3.f30a |= cVar.f26c;
                    arrayDeque = arrayDeque2;
                    z = false;
                } else {
                    ArrayDeque arrayDeque3 = arrayDeque2;
                    hVar = hVar;
                    depth = depth;
                    if ("clip-path".equals(name)) {
                        b bVar = new b();
                        if (a.b.a.a.s(xmlPullParser, "pathData")) {
                            TypedArray typedArrayY3 = a.b.a.a.y(resources, theme, attributeSet, a.b.b.a.a.d);
                            String string4 = typedArrayY3.getString(0);
                            if (string4 != null) {
                                bVar.f25b = string4;
                            }
                            String string5 = typedArrayY3.getString(1);
                            if (string5 != null) {
                                bVar.f24a = a.b.a.a.j(string5);
                            }
                            typedArrayY3.recycle();
                        }
                        dVar.f22b.add(bVar);
                        if (bVar.getPathName() != null) {
                            c0001g2.p.put(bVar.getPathName(), bVar);
                        }
                        hVar3.f30a = bVar.f26c | hVar3.f30a;
                    } else {
                        if ("group".equals(name)) {
                            d dVar2 = new d();
                            TypedArray typedArrayY4 = a.b.a.a.y(resources, theme, attributeSet, a.b.b.a.a.f7b);
                            dVar2.l = null;
                            float f11 = dVar2.f23c;
                            if (a.b.a.a.s(xmlPullParser, "rotation")) {
                                f11 = typedArrayY4.getFloat(5, f11);
                            }
                            dVar2.f23c = f11;
                            dVar2.d = typedArrayY4.getFloat(1, dVar2.d);
                            dVar2.e = typedArrayY4.getFloat(2, dVar2.e);
                            float f12 = dVar2.f;
                            if (a.b.a.a.s(xmlPullParser, "scaleX")) {
                                f12 = typedArrayY4.getFloat(3, f12);
                            }
                            dVar2.f = f12;
                            float f13 = dVar2.g;
                            if (a.b.a.a.s(xmlPullParser, "scaleY")) {
                                f13 = typedArrayY4.getFloat(4, f13);
                            }
                            dVar2.g = f13;
                            float f14 = dVar2.h;
                            if (a.b.a.a.s(xmlPullParser, "translateX")) {
                                f14 = typedArrayY4.getFloat(6, f14);
                            }
                            dVar2.h = f14;
                            float f15 = dVar2.i;
                            if (a.b.a.a.s(xmlPullParser, "translateY")) {
                                f15 = typedArrayY4.getFloat(7, f15);
                            }
                            dVar2.i = f15;
                            String string6 = typedArrayY4.getString(0);
                            if (string6 != null) {
                                dVar2.m = string6;
                            }
                            dVar2.c();
                            typedArrayY4.recycle();
                            dVar.f22b.add(dVar2);
                            arrayDeque = arrayDeque3;
                            arrayDeque.push(dVar2);
                            if (dVar2.getGroupName() != null) {
                                c0001g2.p.put(dVar2.getGroupName(), dVar2);
                            }
                            hVar3.f30a = dVar2.k | hVar3.f30a;
                        }
                        i4 = 3;
                    }
                    arrayDeque = arrayDeque3;
                    z = z3;
                }
                z3 = z;
                i4 = 3;
            } else {
                arrayDeque = arrayDeque2;
                hVar = hVar;
                depth = depth;
                if (eventType == i4 && "group".equals(xmlPullParser.getName())) {
                    arrayDeque.pop();
                }
            }
            eventType = xmlPullParser.next();
            arrayDeque2 = arrayDeque;
            hVar = hVar;
            depth = depth;
            i5 = 2;
        }
        h hVar4 = hVar;
        if (z3) {
            throw new XmlPullParserException("no path defined");
        }
        this.d = updateTintFilter(this.d, hVar4.f32c, hVar4.d);
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        Drawable drawable = this.f19b;
        return drawable != null ? a.b.a.a.u(drawable) : this.f20c.e;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        h hVar;
        ColorStateList colorStateList;
        Drawable drawable = this.f19b;
        if (drawable != null) {
            return drawable.isStateful();
        }
        return super.isStateful() || ((hVar = this.f20c) != null && (hVar.a() || ((colorStateList = this.f20c.f32c) != null && colorStateList.isStateful())));
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.f && super.mutate() == this) {
            this.f20c = new h(this.f20c);
            this.f = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        Drawable drawable = this.f19b;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        boolean z = false;
        h hVar = this.f20c;
        ColorStateList colorStateList = hVar.f32c;
        if (colorStateList != null && (mode = hVar.d) != null) {
            this.d = updateTintFilter(this.d, colorStateList, mode);
            invalidateSelf();
            z = true;
        }
        if (hVar.a()) {
            boolean zB = hVar.f31b.h.b(iArr);
            hVar.k |= zB;
            if (zB) {
                invalidateSelf();
                return true;
            }
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    public void scheduleSelf(Runnable runnable, long j) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, j);
        } else {
            super.scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.setAlpha(i2);
        } else if (this.f20c.f31b.getRootAlpha() != i2) {
            this.f20c.f31b.setRootAlpha(i2);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            a.b.a.a.F(drawable, z);
        } else {
            this.f20c.e = z;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        } else {
            this.e = colorFilter;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.k.a
    public void setTint(int i2) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            a.b.a.a.S(drawable, i2);
        } else {
            setTintList(ColorStateList.valueOf(i2));
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.k.a
    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            a.b.a.a.T(drawable, colorStateList);
            return;
        }
        h hVar = this.f20c;
        if (hVar.f32c != colorStateList) {
            hVar.f32c = colorStateList;
            this.d = updateTintFilter(this.d, colorStateList, hVar.d);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, a.b.d.c.k.a
    public void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.f19b;
        if (drawable != null) {
            a.b.a.a.U(drawable, mode);
            return;
        }
        h hVar = this.f20c;
        if (hVar.d != mode) {
            hVar.d = mode;
            this.d = updateTintFilter(this.d, hVar.f32c, mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.f19b;
        return drawable != null ? drawable.setVisible(z, z2) : super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.f19b;
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
