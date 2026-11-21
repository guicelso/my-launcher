package a.b.d.g;

/* JADX INFO: loaded from: classes.dex */
public class d<E> implements Cloneable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Object f125a = new Object();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f126b = false;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public long[] f127c;
    public Object[] d;
    public int e;

    public d() {
        int iC = c.c(10);
        this.f127c = new long[iC];
        this.d = new Object[iC];
        this.e = 0;
    }

    public final void a() {
        int i = this.e;
        long[] jArr = this.f127c;
        Object[] objArr = this.d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != f125a) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.f126b = false;
        this.e = i2;
    }

    public void a(long j, E e) {
        int i = this.e;
        if (i != 0 && j <= this.f127c[i - 1]) {
            c(j, e);
            return;
        }
        if (this.f126b && this.e >= this.f127c.length) {
            a();
        }
        int i2 = this.e;
        if (i2 >= this.f127c.length) {
            int iC = c.c(i2 + 1);
            long[] jArr = new long[iC];
            Object[] objArr = new Object[iC];
            long[] jArr2 = this.f127c;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr2 = this.d;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.f127c = jArr;
            this.d = objArr;
        }
        this.f127c[i2] = j;
        this.d[i2] = e;
        this.e = i2 + 1;
    }

    public E b(long j, E e) {
        int iA = c.a(this.f127c, this.e, j);
        if (iA >= 0) {
            Object[] objArr = this.d;
            if (objArr[iA] != f125a) {
                return (E) objArr[iA];
            }
        }
        return e;
    }

    public void c(long j, E e) {
        int iA = c.a(this.f127c, this.e, j);
        if (iA >= 0) {
            this.d[iA] = e;
            return;
        }
        int iA2 = iA ^ (-1);
        if (iA2 < this.e) {
            Object[] objArr = this.d;
            if (objArr[iA2] == f125a) {
                this.f127c[iA2] = j;
                objArr[iA2] = e;
                return;
            }
        }
        if (this.f126b && this.e >= this.f127c.length) {
            a();
            iA2 = c.a(this.f127c, this.e, j) ^ (-1);
        }
        int i = this.e;
        if (i >= this.f127c.length) {
            int iC = c.c(i + 1);
            long[] jArr = new long[iC];
            Object[] objArr2 = new Object[iC];
            long[] jArr2 = this.f127c;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr3 = this.d;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f127c = jArr;
            this.d = objArr2;
        }
        int i2 = this.e;
        if (i2 - iA2 != 0) {
            long[] jArr3 = this.f127c;
            int i3 = iA2 + 1;
            System.arraycopy(jArr3, iA2, jArr3, i3, i2 - iA2);
            Object[] objArr4 = this.d;
            System.arraycopy(objArr4, iA2, objArr4, i3, this.e - iA2);
        }
        this.f127c[iA2] = j;
        this.d[iA2] = e;
        this.e++;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public d<E> m0clone() {
        try {
            d<E> dVar = (d) super.clone();
            dVar.f127c = (long[]) this.f127c.clone();
            dVar.d = (Object[]) this.d.clone();
            return dVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public String toString() {
        if (this.f126b) {
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
            if (this.f126b) {
                a();
            }
            sb.append(this.f127c[i2]);
            sb.append('=');
            if (this.f126b) {
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
