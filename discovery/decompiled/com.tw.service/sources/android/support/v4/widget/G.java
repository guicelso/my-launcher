package android.support.v4.widget;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class G {
    private static final Interpolator Qf = new D();
    private float[] Af;
    private float[] Bf;
    private float[] Cf;
    private float[] Df;
    private int[] Ef;
    private int[] Ff;
    private int[] Gf;
    private int Hf;
    private float If;
    private float Jf;
    private int Kf;
    private int Lf;
    private View Mf;
    private boolean Nf;
    private final ViewGroup Of;
    private final F mCallback;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int yf;
    private int zf;
    private int mActivePointerId = -1;
    private final Runnable Pf = new E(this);

    private G(Context context, ViewGroup viewGroup, F f) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        }
        if (f == null) {
            throw new IllegalArgumentException("Callback may not be null");
        }
        this.Of = viewGroup;
        this.mCallback = f;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.Kf = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
        this.zf = viewConfiguration.getScaledTouchSlop();
        this.If = viewConfiguration.getScaledMaximumFlingVelocity();
        this.Jf = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(context, Qf);
    }

    private void P(int i) {
        if (this.Af == null || !q(i)) {
            return;
        }
        this.Af[i] = 0.0f;
        this.Bf[i] = 0.0f;
        this.Cf[i] = 0.0f;
        this.Df[i] = 0.0f;
        this.Ef[i] = 0;
        this.Ff[i] = 0;
        this.Gf[i] = 0;
        this.Hf = (~(1 << i)) & this.Hf;
    }

    private boolean Q(int i) {
        if (q(i)) {
            return true;
        }
        Log.e("ViewDragHelper", "Ignoring pointerId=" + i + " because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because  ViewDragHelper did not receive all the events in the event stream.");
        return false;
    }

    private float a(float f, float f2, float f3) {
        float fAbs = Math.abs(f);
        if (fAbs < f2) {
            return 0.0f;
        }
        if (fAbs > f3) {
            return f > 0.0f ? f3 : -f3;
        }
        return f;
    }

    public static G a(ViewGroup viewGroup, float f, F f2) {
        G g = new G(viewGroup.getContext(), viewGroup, f2);
        g.zf = (int) ((1.0f / f) * g.zf);
        return g;
    }

    private void a(float f, float f2, int i) {
        int i2 = b(f, f2, i, 1) ? 1 : 0;
        if (b(f2, f, i, 4)) {
            i2 |= 4;
        }
        if (b(f, f2, i, 2)) {
            i2 |= 2;
        }
        if (b(f2, f, i, 8)) {
            i2 |= 8;
        }
        if (i2 != 0) {
            int[] iArr = this.Ff;
            iArr[i] = iArr[i] | i2;
            this.mCallback.c(i2, i);
        }
    }

    private int b(int i, int i2, int i3) {
        int iAbs = Math.abs(i);
        if (iAbs < i2) {
            return 0;
        }
        if (iAbs > i3) {
            return i > 0 ? i3 : -i3;
        }
        return i;
    }

    private void b(float f, float f2) {
        this.Nf = true;
        this.mCallback.a(this.Mf, f, f2);
        this.Nf = false;
        if (this.yf == 1) {
            r(0);
        }
    }

    private void b(float f, float f2, int i) {
        float[] fArr = this.Af;
        if (fArr == null || fArr.length <= i) {
            int i2 = i + 1;
            float[] fArr2 = new float[i2];
            float[] fArr3 = new float[i2];
            float[] fArr4 = new float[i2];
            float[] fArr5 = new float[i2];
            int[] iArr = new int[i2];
            int[] iArr2 = new int[i2];
            int[] iArr3 = new int[i2];
            float[] fArr6 = this.Af;
            if (fArr6 != null) {
                System.arraycopy(fArr6, 0, fArr2, 0, fArr6.length);
                float[] fArr7 = this.Bf;
                System.arraycopy(fArr7, 0, fArr3, 0, fArr7.length);
                float[] fArr8 = this.Cf;
                System.arraycopy(fArr8, 0, fArr4, 0, fArr8.length);
                float[] fArr9 = this.Df;
                System.arraycopy(fArr9, 0, fArr5, 0, fArr9.length);
                int[] iArr4 = this.Ef;
                System.arraycopy(iArr4, 0, iArr, 0, iArr4.length);
                int[] iArr5 = this.Ff;
                System.arraycopy(iArr5, 0, iArr2, 0, iArr5.length);
                int[] iArr6 = this.Gf;
                System.arraycopy(iArr6, 0, iArr3, 0, iArr6.length);
            }
            this.Af = fArr2;
            this.Bf = fArr3;
            this.Cf = fArr4;
            this.Df = fArr5;
            this.Ef = iArr;
            this.Ff = iArr2;
            this.Gf = iArr3;
        }
        float[] fArr10 = this.Af;
        this.Cf[i] = f;
        fArr10[i] = f;
        float[] fArr11 = this.Bf;
        this.Df[i] = f2;
        fArr11[i] = f2;
        int[] iArr7 = this.Ef;
        int i3 = (int) f;
        int i4 = (int) f2;
        int i5 = i3 < this.Of.getLeft() + this.Kf ? 1 : 0;
        if (i4 < this.Of.getTop() + this.Kf) {
            i5 |= 4;
        }
        if (i3 > this.Of.getRight() - this.Kf) {
            i5 |= 2;
        }
        if (i4 > this.Of.getBottom() - this.Kf) {
            i5 |= 8;
        }
        iArr7[i] = i5;
        this.Hf |= 1 << i;
    }

    private boolean b(float f, float f2, int i, int i2) {
        float fAbs = Math.abs(f);
        float fAbs2 = Math.abs(f2);
        if ((this.Ef[i] & i2) != i2 || (this.Lf & i2) == 0 || (this.Gf[i] & i2) == i2 || (this.Ff[i] & i2) == i2) {
            return false;
        }
        int i3 = this.zf;
        if (fAbs <= i3 && fAbs2 <= i3) {
            return false;
        }
        if (fAbs >= fAbs2 * 0.5f || !this.mCallback.n(i2)) {
            return (this.Ff[i] & i2) == 0 && fAbs > ((float) this.zf);
        }
        int[] iArr = this.Gf;
        iArr[i] = iArr[i] | i2;
        return false;
    }

    private boolean b(View view, float f, float f2) {
        if (view == null) {
            return false;
        }
        boolean z = this.mCallback.p(view) > 0;
        this.mCallback.q(view);
        return z && Math.abs(f) > ((float) this.zf);
    }

    private int c(int i, int i2, int i3) {
        if (i == 0) {
            return 0;
        }
        int width = this.Of.getWidth();
        float f = width / 2;
        float fSin = (((float) Math.sin((Math.min(1.0f, Math.abs(i) / width) - 0.5f) * 0.47123894f)) * f) + f;
        int iAbs = Math.abs(i2);
        return Math.min(iAbs > 0 ? Math.round(Math.abs(fSin / iAbs) * 1000.0f) * 4 : (int) (((Math.abs(i) / i3) + 1.0f) * 256.0f), 600);
    }

    private void c(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = motionEvent.getPointerId(i);
            if (Q(pointerId)) {
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
                this.Cf[pointerId] = x;
                this.Df[pointerId] = y;
            }
        }
    }

    private void eb() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.If);
        b(a(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.Jf, this.If), a(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.Jf, this.If));
    }

    private boolean i(int i, int i2, int i3, int i4) {
        float f;
        float f2;
        float f3;
        float f4;
        int left = this.Mf.getLeft();
        int top = this.Mf.getTop();
        int i5 = i - left;
        int i6 = i2 - top;
        if (i5 == 0 && i6 == 0) {
            this.mScroller.abortAnimation();
            r(0);
            return false;
        }
        View view = this.Mf;
        int iB = b(i3, (int) this.Jf, (int) this.If);
        int iB2 = b(i4, (int) this.Jf, (int) this.If);
        int iAbs = Math.abs(i5);
        int iAbs2 = Math.abs(i6);
        int iAbs3 = Math.abs(iB);
        int iAbs4 = Math.abs(iB2);
        int i7 = iAbs3 + iAbs4;
        int i8 = iAbs + iAbs2;
        if (iB != 0) {
            f = iAbs3;
            f2 = i7;
        } else {
            f = iAbs;
            f2 = i8;
        }
        float f5 = f / f2;
        if (iB2 != 0) {
            f3 = iAbs4;
            f4 = i7;
        } else {
            f3 = iAbs2;
            f4 = i8;
        }
        int iC = c(i5, iB, this.mCallback.p(view));
        this.mCallback.q(view);
        this.mScroller.startScroll(left, top, i5, i6, (int) ((c(i6, iB2, 0) * (f3 / f4)) + (iC * f5)));
        r(2);
        return true;
    }

    public View O() {
        return this.Mf;
    }

    public int P() {
        return this.Kf;
    }

    public int Q() {
        return this.yf;
    }

    public void abort() {
        cancel();
        if (this.yf == 2) {
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int currX2 = this.mScroller.getCurrX();
            int currY2 = this.mScroller.getCurrY();
            this.mCallback.a(this.Mf, currX2, currY2, currX2 - currX, currY2 - currY);
        }
        r(0);
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:60:0x00f4  */
    public boolean b(MotionEvent motionEvent) {
        View viewE;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int pointerId = motionEvent.getPointerId(0);
            b(x, y, pointerId);
            View viewE2 = e((int) x, (int) y);
            if (viewE2 == this.Mf && this.yf == 2) {
                h(viewE2, pointerId);
            }
            int i = this.Ef[pointerId];
            int i2 = this.Lf;
            if ((i & i2) != 0) {
                this.mCallback.d(i & i2, pointerId);
            }
        } else if (actionMasked == 1) {
            cancel();
        } else if (actionMasked != 2) {
            if (actionMasked == 3) {
                cancel();
            } else if (actionMasked == 5) {
                int pointerId2 = motionEvent.getPointerId(actionIndex);
                float x2 = motionEvent.getX(actionIndex);
                float y2 = motionEvent.getY(actionIndex);
                b(x2, y2, pointerId2);
                int i3 = this.yf;
                if (i3 == 0) {
                    int i4 = this.Ef[pointerId2];
                    int i5 = this.Lf;
                    if ((i4 & i5) != 0) {
                        this.mCallback.d(i4 & i5, pointerId2);
                    }
                } else if (i3 == 2 && (viewE = e((int) x2, (int) y2)) == this.Mf) {
                    h(viewE, pointerId2);
                }
            } else if (actionMasked == 6) {
                P(motionEvent.getPointerId(actionIndex));
            }
        } else if (this.Af != null && this.Bf != null) {
            int pointerCount = motionEvent.getPointerCount();
            for (int i6 = 0; i6 < pointerCount; i6++) {
                int pointerId3 = motionEvent.getPointerId(i6);
                if (Q(pointerId3)) {
                    float x3 = motionEvent.getX(i6);
                    float y3 = motionEvent.getY(i6);
                    float f = x3 - this.Af[pointerId3];
                    float f2 = y3 - this.Bf[pointerId3];
                    View viewE3 = e((int) x3, (int) y3);
                    boolean z = viewE3 != null && b(viewE3, f, f2);
                    if (!z) {
                        a(f, f2, pointerId3);
                        if (this.yf != 1) {
                            break;
                        }
                    } else {
                        int left = viewE3.getLeft();
                        int i7 = (int) f;
                        int iA = this.mCallback.a(viewE3, left + i7, i7);
                        int i8 = (int) f2;
                        this.mCallback.b(viewE3, viewE3.getTop() + i8, i8);
                        int iP = this.mCallback.p(viewE3);
                        this.mCallback.q(viewE3);
                        if (iP == 0 || (iP > 0 && iA == left)) {
                            break;
                        }
                        a(f, f2, pointerId3);
                        if (this.yf != 1 || (z && h(viewE3, pointerId3))) {
                            break;
                        }
                    }
                }
            }
            c(motionEvent);
        }
        return this.yf == 1;
    }

    public void c(float f) {
        this.Jf = f;
    }

    public boolean c(View view, int i, int i2) {
        return view != null && i >= view.getLeft() && i < view.getRight() && i2 >= view.getTop() && i2 < view.getBottom();
    }

    public void cancel() {
        this.mActivePointerId = -1;
        float[] fArr = this.Af;
        if (fArr != null) {
            Arrays.fill(fArr, 0.0f);
            Arrays.fill(this.Bf, 0.0f);
            Arrays.fill(this.Cf, 0.0f);
            Arrays.fill(this.Df, 0.0f);
            Arrays.fill(this.Ef, 0);
            Arrays.fill(this.Ff, 0);
            Arrays.fill(this.Gf, 0);
            this.Hf = 0;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public boolean d(View view, int i, int i2) {
        this.Mf = view;
        this.mActivePointerId = -1;
        boolean zI = i(i, i2, 0, 0);
        if (!zI && this.yf == 0 && this.Mf != null) {
            this.Mf = null;
        }
        return zI;
    }

    public View e(int i, int i2) {
        for (int childCount = this.Of.getChildCount() - 1; childCount >= 0; childCount--) {
            ViewGroup viewGroup = this.Of;
            this.mCallback.m(childCount);
            View childAt = viewGroup.getChildAt(childCount);
            if (i >= childAt.getLeft() && i < childAt.getRight() && i2 >= childAt.getTop() && i2 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    public boolean f(int i, int i2) {
        if (this.Nf) {
            return i(i, i2, (int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    public void g(View view, int i) {
        if (view.getParent() != this.Of) {
            StringBuilder sbE = b.a.a.a.a.e("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
            sbE.append(this.Of);
            sbE.append(")");
            throw new IllegalArgumentException(sbE.toString());
        }
        this.Mf = view;
        this.mActivePointerId = i;
        this.mCallback.e(view, i);
        r(1);
    }

    public int getTouchSlop() {
        return this.zf;
    }

    boolean h(View view, int i) {
        if (view == this.Mf && this.mActivePointerId == i) {
            return true;
        }
        if (view == null || !this.mCallback.f(view, i)) {
            return false;
        }
        this.mActivePointerId = i;
        g(view, i);
        return true;
    }

    public boolean i(boolean z) {
        if (this.yf == 2) {
            boolean zComputeScrollOffset = this.mScroller.computeScrollOffset();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            int left = currX - this.Mf.getLeft();
            int top = currY - this.Mf.getTop();
            if (left != 0) {
                android.support.v4.view.r.b(this.Mf, left);
            }
            if (top != 0) {
                android.support.v4.view.r.c(this.Mf, top);
            }
            if (left != 0 || top != 0) {
                this.mCallback.a(this.Mf, currX, currY, left, top);
            }
            if (zComputeScrollOffset && currX == this.mScroller.getFinalX() && currY == this.mScroller.getFinalY()) {
                this.mScroller.abortAnimation();
                zComputeScrollOffset = false;
            }
            if (!zComputeScrollOffset) {
                if (z) {
                    this.Of.post(this.Pf);
                } else {
                    r(0);
                }
            }
        }
        return this.yf == 2;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x003f  */
    /* JADX WARN: Code duplicated, block: B:21:0x0041  */
    public boolean p(int i) {
        boolean z;
        int length = this.Af.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (q(i2)) {
                boolean z2 = (i & 1) == 1;
                boolean z3 = (i & 2) == 2;
                float f = this.Cf[i2] - this.Af[i2];
                float f2 = this.Df[i2] - this.Bf[i2];
                if (z2 && z3) {
                    float f3 = (f2 * f2) + (f * f);
                    int i3 = this.zf;
                    if (f3 > i3 * i3) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else if (!z2 ? !z3 || Math.abs(f2) <= this.zf : Math.abs(f) <= this.zf) {
                    z = false;
                } else {
                    z = true;
                }
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public boolean q(int i) {
        return (this.Hf & (1 << i)) != 0;
    }

    void r(int i) {
        this.Of.removeCallbacks(this.Pf);
        if (this.yf != i) {
            this.yf = i;
            this.mCallback.o(i);
            if (this.yf == 0) {
                this.Mf = null;
            }
        }
    }

    public void s(int i) {
        this.Lf = i;
    }

    public void a(MotionEvent motionEvent) {
        int i;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int i2 = 0;
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int pointerId = motionEvent.getPointerId(0);
            View viewE = e((int) x, (int) y);
            b(x, y, pointerId);
            h(viewE, pointerId);
            int i3 = this.Ef[pointerId];
            int i4 = this.Lf;
            if ((i3 & i4) != 0) {
                this.mCallback.d(i3 & i4, pointerId);
                return;
            }
            return;
        }
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (this.yf != 1) {
                    int pointerCount = motionEvent.getPointerCount();
                    while (i2 < pointerCount) {
                        int pointerId2 = motionEvent.getPointerId(i2);
                        if (Q(pointerId2)) {
                            float x2 = motionEvent.getX(i2);
                            float y2 = motionEvent.getY(i2);
                            float f = x2 - this.Af[pointerId2];
                            float f2 = y2 - this.Bf[pointerId2];
                            a(f, f2, pointerId2);
                            if (this.yf != 1) {
                                View viewE2 = e((int) x2, (int) y2);
                                if (b(viewE2, f, f2) && h(viewE2, pointerId2)) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        i2++;
                    }
                } else {
                    if (!Q(this.mActivePointerId)) {
                        return;
                    }
                    int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    float x3 = motionEvent.getX(iFindPointerIndex);
                    float y3 = motionEvent.getY(iFindPointerIndex);
                    float[] fArr = this.Cf;
                    int i5 = this.mActivePointerId;
                    int i6 = (int) (x3 - fArr[i5]);
                    int i7 = (int) (y3 - this.Df[i5]);
                    int left = this.Mf.getLeft() + i6;
                    int top = this.Mf.getTop() + i7;
                    int left2 = this.Mf.getLeft();
                    int top2 = this.Mf.getTop();
                    if (i6 != 0) {
                        left = this.mCallback.a(this.Mf, left, i6);
                        android.support.v4.view.r.b(this.Mf, left - left2);
                    }
                    int i8 = left;
                    if (i7 != 0) {
                        top = this.mCallback.b(this.Mf, top, i7);
                        android.support.v4.view.r.c(this.Mf, top - top2);
                    }
                    int i9 = top;
                    if (i6 != 0 || i7 != 0) {
                        this.mCallback.a(this.Mf, i8, i9, i8 - left2, i9 - top2);
                    }
                }
                c(motionEvent);
                return;
            }
            if (actionMasked != 3) {
                if (actionMasked == 5) {
                    int pointerId3 = motionEvent.getPointerId(actionIndex);
                    float x4 = motionEvent.getX(actionIndex);
                    float y4 = motionEvent.getY(actionIndex);
                    b(x4, y4, pointerId3);
                    if (this.yf != 0) {
                        if (c(this.Mf, (int) x4, (int) y4)) {
                            h(this.Mf, pointerId3);
                            return;
                        }
                        return;
                    }
                    h(e((int) x4, (int) y4), pointerId3);
                    int i10 = this.Ef[pointerId3];
                    int i11 = this.Lf;
                    if ((i10 & i11) != 0) {
                        this.mCallback.d(i10 & i11, pointerId3);
                        return;
                    }
                    return;
                }
                if (actionMasked != 6) {
                    return;
                }
                int pointerId4 = motionEvent.getPointerId(actionIndex);
                if (this.yf == 1 && pointerId4 == this.mActivePointerId) {
                    int pointerCount2 = motionEvent.getPointerCount();
                    while (true) {
                        if (i2 >= pointerCount2) {
                            i = -1;
                            break;
                        }
                        int pointerId5 = motionEvent.getPointerId(i2);
                        if (pointerId5 != this.mActivePointerId) {
                            View viewE3 = e((int) motionEvent.getX(i2), (int) motionEvent.getY(i2));
                            View view = this.Mf;
                            if (viewE3 == view && h(view, pointerId5)) {
                                i = this.mActivePointerId;
                                break;
                            }
                        }
                        i2++;
                    }
                    if (i == -1) {
                        eb();
                    }
                }
                P(pointerId4);
                return;
            }
            if (this.yf == 1) {
                b(0.0f, 0.0f);
            }
        } else if (this.yf == 1) {
            eb();
        }
        cancel();
    }
}
