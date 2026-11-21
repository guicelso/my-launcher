package a.b.d.i;

import a.b.d.h.n;
import android.content.res.Resources;
import android.os.Build;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public abstract class a implements View.OnTouchListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int f166a = ViewConfiguration.getTapTimeout();
    public final View d;
    public Runnable e;
    public int h;
    public int i;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;
    public boolean r;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0003a f167b = new C0003a();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Interpolator f168c = new AccelerateInterpolator();
    public float[] f = {0.0f, 0.0f};
    public float[] g = {Float.MAX_VALUE, Float.MAX_VALUE};
    public float[] j = {0.0f, 0.0f};
    public float[] k = {0.0f, 0.0f};
    public float[] l = {Float.MAX_VALUE, Float.MAX_VALUE};

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a.b.d.i.a$a, reason: collision with other inner class name */
    public static class C0003a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f169a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f170b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public float f171c;
        public float d;
        public float j;
        public int k;
        public long e = Long.MIN_VALUE;
        public long i = -1;
        public long f = 0;
        public int g = 0;
        public int h = 0;

        public final float a(long j) {
            if (j < this.e) {
                return 0.0f;
            }
            long j2 = this.i;
            if (j2 < 0 || j < j2) {
                return a.a((j - this.e) / this.f169a, 0.0f, 1.0f) * 0.5f;
            }
            long j3 = j - j2;
            float f = this.j;
            return (a.a(j3 / this.k, 0.0f, 1.0f) * f) + (1.0f - f);
        }

        public void a() {
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.k = a.a((int) (jCurrentAnimationTimeMillis - this.e), 0, this.f170b);
            this.j = a(jCurrentAnimationTimeMillis);
            this.i = jCurrentAnimationTimeMillis;
        }

        public void b() {
            this.e = AnimationUtils.currentAnimationTimeMillis();
            this.i = -1L;
            this.f = this.e;
            this.j = 0.5f;
            this.g = 0;
            this.h = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a aVar = a.this;
            if (aVar.p) {
                if (aVar.n) {
                    aVar.n = false;
                    aVar.f167b.b();
                }
                C0003a c0003a = a.this.f167b;
                if ((c0003a.i > 0 && AnimationUtils.currentAnimationTimeMillis() > c0003a.i + ((long) c0003a.k)) || !a.this.b()) {
                    a.this.p = false;
                    return;
                }
                a aVar2 = a.this;
                if (aVar2.o) {
                    aVar2.o = false;
                    aVar2.a();
                }
                if (c0003a.f == 0) {
                    throw new RuntimeException("Cannot compute scroll delta before calling start()");
                }
                long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                float fA = c0003a.a(jCurrentAnimationTimeMillis);
                long j = jCurrentAnimationTimeMillis - c0003a.f;
                c0003a.f = jCurrentAnimationTimeMillis;
                float f = j * ((fA * 4.0f) + ((-4.0f) * fA * fA));
                c0003a.g = (int) (c0003a.f171c * f);
                c0003a.h = (int) (f * c0003a.d);
                int i = c0003a.g;
                int i2 = c0003a.h;
                ListView listView = ((e) a.this).s;
                int i3 = Build.VERSION.SDK_INT;
                listView.scrollListBy(i2);
                n.a(a.this.d, this);
            }
        }
    }

    public a(View view) {
        this.d = view;
        float f = Resources.getSystem().getDisplayMetrics().density;
        float[] fArr = this.l;
        float f2 = ((int) ((1575.0f * f) + 0.5f)) / 1000.0f;
        fArr[0] = f2;
        fArr[1] = f2;
        float[] fArr2 = this.k;
        float f3 = ((int) ((f * 315.0f) + 0.5f)) / 1000.0f;
        fArr2[0] = f3;
        fArr2[1] = f3;
        this.h = 1;
        float[] fArr3 = this.g;
        fArr3[0] = Float.MAX_VALUE;
        fArr3[1] = Float.MAX_VALUE;
        float[] fArr4 = this.f;
        fArr4[0] = 0.2f;
        fArr4[1] = 0.2f;
        float[] fArr5 = this.j;
        fArr5[0] = 0.001f;
        fArr5[1] = 0.001f;
        this.i = f166a;
        C0003a c0003a = this.f167b;
        c0003a.f169a = 500;
        c0003a.f170b = 500;
    }

    public static float a(float f, float f2, float f3) {
        if (f > f3) {
            return f3;
        }
        return f < f2 ? f2 : f;
    }

    public static int a(int i, int i2, int i3) {
        if (i > i3) {
            return i3;
        }
        return i < i2 ? i2 : i;
    }

    public final float a(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        int i = this.h;
        if (i == 0 || i == 1) {
            if (f < f2) {
                if (f >= 0.0f) {
                    return 1.0f - (f / f2);
                }
                if (this.p && this.h == 1) {
                    return 1.0f;
                }
            }
        } else if (i == 2 && f < 0.0f) {
            return f / (-f2);
        }
        return 0.0f;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x003e A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:13:0x003f  */
    /* JADX WARN: Code duplicated, block: B:15:0x0051  */
    /* JADX WARN: Code duplicated, block: B:17:0x0058  */
    public final float a(int i, float f, float f2, float f3) {
        float fA;
        float interpolation;
        float fA2 = a(this.f[i] * f2, 0.0f, this.g[i]);
        float fA3 = a(f2 - f, fA2) - a(f, fA2);
        if (fA3 >= 0.0f) {
            if (fA3 > 0.0f) {
                interpolation = this.f168c.getInterpolation(fA3);
            } else {
                fA = 0.0f;
            }
            if (fA == 0.0f) {
                return 0.0f;
            }
            float f4 = this.j[i];
            float f5 = this.k[i];
            float f6 = this.l[i];
            float f7 = f4 * f3;
            return fA > 0.0f ? a(fA * f7, f5, f6) : -a((-fA) * f7, f5, f6);
        }
        interpolation = -this.f168c.getInterpolation(-fA3);
        fA = a(interpolation, -1.0f, 1.0f);
        if (fA == 0.0f) {
            return 0.0f;
        }
        float f8 = this.j[i];
        float f9 = this.k[i];
        float f10 = this.l[i];
        float f11 = f8 * f3;
        if (fA > 0.0f) {
        }
    }

    public void a() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
        this.d.onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0021  */
    public boolean b() {
        boolean z;
        C0003a c0003a = this.f167b;
        float f = c0003a.d;
        int iAbs = (int) (f / Math.abs(f));
        float f2 = c0003a.f171c;
        int iAbs2 = (int) (f2 / Math.abs(f2));
        if (iAbs != 0) {
            ListView listView = ((e) this).s;
            int count = listView.getCount();
            if (count == 0) {
                z = false;
            } else {
                int childCount = listView.getChildCount();
                int firstVisiblePosition = listView.getFirstVisiblePosition();
                int i = firstVisiblePosition + childCount;
                if (iAbs <= 0 ? iAbs >= 0 || (firstVisiblePosition <= 0 && listView.getChildAt(0).getTop() >= 0) : i >= count && listView.getChildAt(childCount - 1).getBottom() <= listView.getHeight()) {
                    z = false;
                } else {
                    z = true;
                }
            }
            if (z) {
                return true;
            }
        }
        if (iAbs2 != 0) {
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0016  */
    /* JADX WARN: Code duplicated, block: B:15:0x001a  */
    /* JADX WARN: Code duplicated, block: B:16:0x001d  */
    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int i;
        if (!this.q) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                if (this.n) {
                    this.p = false;
                } else {
                    this.f167b.a();
                }
            } else if (actionMasked != 2) {
                if (actionMasked == 3) {
                    if (this.n) {
                        this.p = false;
                    } else {
                        this.f167b.a();
                    }
                }
            }
            return this.r && this.p;
        }
        this.o = true;
        this.m = false;
        float fA = a(0, motionEvent.getX(), view.getWidth(), this.d.getWidth());
        float fA2 = a(1, motionEvent.getY(), view.getHeight(), this.d.getHeight());
        C0003a c0003a = this.f167b;
        c0003a.f171c = fA;
        c0003a.d = fA2;
        if (!this.p && b()) {
            if (this.e == null) {
                this.e = new b();
            }
            this.p = true;
            this.n = true;
            if (this.m || (i = this.i) <= 0) {
                this.e.run();
            } else {
                n.a(this.d, this.e, i);
            }
            this.m = true;
        }
        if (this.r) {
            return false;
        }
    }

    public a a(boolean z) {
        if (this.q && !z) {
            if (this.n) {
                this.p = false;
            } else {
                this.f167b.a();
            }
        }
        this.q = z;
        return this;
    }
}
