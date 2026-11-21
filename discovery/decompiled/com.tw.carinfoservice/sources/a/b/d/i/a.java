package a.b.d.i;

import a.b.d.h.j;
import android.content.res.Resources;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ListView;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class a implements View.OnTouchListener {
    public static final int r = ViewConfiguration.getTapTimeout();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0008a f158b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Interpolator f159c;
    public final View d;
    public Runnable e;
    public float[] f;
    public float[] g;
    public int h;
    public int i;
    public float[] j;
    public float[] k;
    public float[] l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;

    /* JADX INFO: renamed from: a.b.d.i.a$a, reason: collision with other inner class name */
    public static class C0008a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f160a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f161b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public float f162c;
        public float d;
        public float j;
        public int k;
        public long e = Long.MIN_VALUE;
        public long i = -1;
        public long f = 0;
        public int g = 0;
        public int h = 0;

        public final float a(long j) {
            long j2 = this.e;
            if (j < j2) {
                return 0.0f;
            }
            long j3 = this.i;
            if (j3 < 0 || j < j3) {
                return a.b((j - j2) / this.f160a, 0.0f, 1.0f) * 0.5f;
            }
            float f = this.j;
            return (a.b((j - j3) / this.k, 0.0f, 1.0f) * f) + (1.0f - f);
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a aVar = a.this;
            if (aVar.p) {
                if (aVar.n) {
                    aVar.n = false;
                    C0008a c0008a = aVar.f158b;
                    Objects.requireNonNull(c0008a);
                    long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                    c0008a.e = jCurrentAnimationTimeMillis;
                    c0008a.i = -1L;
                    c0008a.f = jCurrentAnimationTimeMillis;
                    c0008a.j = 0.5f;
                    c0008a.g = 0;
                    c0008a.h = 0;
                }
                C0008a c0008a2 = a.this.f158b;
                if ((c0008a2.i > 0 && AnimationUtils.currentAnimationTimeMillis() > c0008a2.i + ((long) c0008a2.k)) || !a.this.e()) {
                    a.this.p = false;
                    return;
                }
                a aVar2 = a.this;
                if (aVar2.o) {
                    aVar2.o = false;
                    Objects.requireNonNull(aVar2);
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                    aVar2.d.onTouchEvent(motionEventObtain);
                    motionEventObtain.recycle();
                }
                if (c0008a2.f == 0) {
                    throw new RuntimeException("Cannot compute scroll delta before calling start()");
                }
                long jCurrentAnimationTimeMillis2 = AnimationUtils.currentAnimationTimeMillis();
                float fA = c0008a2.a(jCurrentAnimationTimeMillis2);
                long j = jCurrentAnimationTimeMillis2 - c0008a2.f;
                c0008a2.f = jCurrentAnimationTimeMillis2;
                float f = j * ((fA * 4.0f) + ((-4.0f) * fA * fA));
                c0008a2.g = (int) (c0008a2.f162c * f);
                int i = (int) (f * c0008a2.d);
                c0008a2.h = i;
                ((e) a.this).s.scrollListBy(i);
                View view = a.this.d;
                int i2 = j.f151a;
                view.postOnAnimation(this);
            }
        }
    }

    public a(View view) {
        C0008a c0008a = new C0008a();
        this.f158b = c0008a;
        this.f159c = new AccelerateInterpolator();
        this.f = new float[]{0.0f, 0.0f};
        this.g = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
        this.j = new float[]{0.0f, 0.0f};
        this.k = new float[]{0.0f, 0.0f};
        this.l = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
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
        this.i = r;
        c0008a.f160a = 500;
        c0008a.f161b = 500;
    }

    public static float b(float f, float f2, float f3) {
        if (f > f3) {
            return f3;
        }
        return f < f2 ? f2 : f;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x003e A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:13:0x003f  */
    /* JADX WARN: Code duplicated, block: B:15:0x0051  */
    /* JADX WARN: Code duplicated, block: B:17:0x0058  */
    public final float a(int i, float f, float f2, float f3) {
        float fB;
        float interpolation;
        float fB2 = b(this.f[i] * f2, 0.0f, this.g[i]);
        float fC = c(f2 - f, fB2) - c(f, fB2);
        if (fC >= 0.0f) {
            if (fC > 0.0f) {
                interpolation = this.f159c.getInterpolation(fC);
            } else {
                fB = 0.0f;
            }
            if (fB == 0.0f) {
                return 0.0f;
            }
            float f4 = this.j[i];
            float f5 = this.k[i];
            float f6 = this.l[i];
            float f7 = f4 * f3;
            return fB > 0.0f ? b(fB * f7, f5, f6) : -b((-fB) * f7, f5, f6);
        }
        interpolation = -this.f159c.getInterpolation(-fC);
        fB = b(interpolation, -1.0f, 1.0f);
        if (fB == 0.0f) {
            return 0.0f;
        }
        float f8 = this.j[i];
        float f9 = this.k[i];
        float f10 = this.l[i];
        float f11 = f8 * f3;
        if (fB > 0.0f) {
        }
    }

    public final float c(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        int i = this.h;
        if (i == 0 || i == 1) {
            if (f < f2) {
                if (f >= 0.0f) {
                    return 1.0f - (f / f2);
                }
                if (this.p && i == 1) {
                    return 1.0f;
                }
            }
        } else if (i == 2 && f < 0.0f) {
            return f / (-f2);
        }
        return 0.0f;
    }

    public final void d() {
        int i = 0;
        if (this.n) {
            this.p = false;
            return;
        }
        C0008a c0008a = this.f158b;
        Objects.requireNonNull(c0008a);
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        int i2 = (int) (jCurrentAnimationTimeMillis - c0008a.e);
        int i3 = c0008a.f161b;
        if (i2 > i3) {
            i = i3;
        } else if (i2 >= 0) {
            i = i2;
        }
        c0008a.k = i;
        c0008a.j = c0008a.a(jCurrentAnimationTimeMillis);
        c0008a.i = jCurrentAnimationTimeMillis;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0021  */
    public boolean e() {
        boolean z;
        C0008a c0008a = this.f158b;
        float f = c0008a.d;
        int iAbs = (int) (f / Math.abs(f));
        float f2 = c0008a.f162c;
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
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0016  */
    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int i;
        if (!this.q) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                d();
            } else if (actionMasked != 2) {
                if (actionMasked == 3) {
                    d();
                }
            }
            return false;
        }
        this.o = true;
        this.m = false;
        float fA = a(0, motionEvent.getX(), view.getWidth(), this.d.getWidth());
        float fA2 = a(1, motionEvent.getY(), view.getHeight(), this.d.getHeight());
        C0008a c0008a = this.f158b;
        c0008a.f162c = fA;
        c0008a.d = fA2;
        if (!this.p && e()) {
            if (this.e == null) {
                this.e = new b();
            }
            this.p = true;
            this.n = true;
            if (this.m || (i = this.i) <= 0) {
                this.e.run();
            } else {
                View view2 = this.d;
                Runnable runnable = this.e;
                long j = i;
                int i2 = j.f151a;
                view2.postOnAnimationDelayed(runnable, j);
            }
            this.m = true;
        }
        return false;
    }
}
