package a.b.d.g;

/* JADX INFO: loaded from: classes.dex */
public class h<E> implements Cloneable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Object f146a = new Object();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f147b = false;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int[] f148c;
    public Object[] d;
    public int e;

    public h() {
        int iB = c.b(10);
        this.f148c = new int[iB];
        this.d = new Object[iB];
        this.e = 0;
    }

    public E a(int i) {
        return b(i, null);
    }

    public final void a() {
        int i = this.e;
        int[] iArr = this.f148c;
        Object[] objArr = this.d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != f146a) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.f147b = false;
        this.e = i2;
    }

    public void a(int i, E e) {
        int i2 = this.e;
        if (i2 != 0 && i <= this.f148c[i2 - 1]) {
            c(i, e);
            return;
        }
        if (this.f147b && this.e >= this.f148c.length) {
            a();
        }
        int i3 = this.e;
        if (i3 >= this.f148c.length) {
            int iB = c.b(i3 + 1);
            int[] iArr = new int[iB];
            Object[] objArr = new Object[iB];
            int[] iArr2 = this.f148c;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr2 = this.d;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.f148c = iArr;
            this.d = objArr;
        }
        this.f148c[i3] = i;
        this.d[i3] = e;
        this.e = i3 + 1;
    }

    public E b(int i, E e) {
        int iA = c.a(this.f148c, this.e, i);
        if (iA >= 0) {
            Object[] objArr = this.d;
            if (objArr[iA] != f146a) {
                return (E) objArr[iA];
            }
        }
        return e;
    }

    public void c(int i, E e) {
        int iA = c.a(this.f148c, this.e, i);
        if (iA >= 0) {
            this.d[iA] = e;
            return;
        }
        int iA2 = iA ^ (-1);
        if (iA2 < this.e) {
            Object[] objArr = this.d;
            if (objArr[iA2] == f146a) {
                this.f148c[iA2] = i;
                objArr[iA2] = e;
                return;
            }
        }
        if (this.f147b && this.e >= this.f148c.length) {
            a();
            iA2 = c.a(this.f148c, this.e, i) ^ (-1);
        }
        int i2 = this.e;
        if (i2 >= this.f148c.length) {
            int iB = c.b(i2 + 1);
            int[] iArr = new int[iB];
            Object[] objArr2 = new Object[iB];
            int[] iArr2 = this.f148c;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.d;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f148c = iArr;
            this.d = objArr2;
        }
        int i3 = this.e;
        if (i3 - iA2 != 0) {
            int[] iArr3 = this.f148c;
            int i4 = iA2 + 1;
            System.arraycopy(iArr3, iA2, iArr3, i4, i3 - iA2);
            Object[] objArr4 = this.d;
            System.arraycopy(objArr4, iA2, objArr4, i4, this.e - iA2);
        }
        this.f148c[iA2] = i;
        this.d[iA2] = e;
        this.e++;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public h<E> m1clone() {
        try {
            h<E> hVar = (h) super.clone();
            hVar.f148c = (int[]) this.f148c.clone();
            hVar.d = (Object[]) this.d.clone();
            return hVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public String toString() {
        if (this.f147b) {
            a();
        }
        int i = this.e;
        if (i <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(i * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.e; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            if (this.f147b) {
                a();
            }
            sb.append(this.f148c[i2]);
            sb.append('=');
            if (this.f147b) {
                a();
            }
            Object obj = this.d[i2];
            if (obj != this) {
                sb.append(obj);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
