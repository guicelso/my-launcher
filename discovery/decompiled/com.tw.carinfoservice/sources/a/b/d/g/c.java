package a.b.d.g;

/* JADX INFO: loaded from: classes.dex */
public class c<E> implements Cloneable {
    public static final Object e = new Object();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f117a = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public long[] f118b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object[] f119c;
    public int d;

    public c() {
        int iF = b.f(10);
        this.f118b = new long[iF];
        this.f119c = new Object[iF];
        this.d = 0;
    }

    public void a(long j, E e2) {
        int i = this.d;
        if (i != 0 && j <= this.f118b[i - 1]) {
            e(j, e2);
            return;
        }
        if (this.f117a && i >= this.f118b.length) {
            c();
        }
        int i2 = this.d;
        if (i2 >= this.f118b.length) {
            int iF = b.f(i2 + 1);
            long[] jArr = new long[iF];
            Object[] objArr = new Object[iF];
            long[] jArr2 = this.f118b;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr2 = this.f119c;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.f118b = jArr;
            this.f119c = objArr;
        }
        this.f118b[i2] = j;
        this.f119c[i2] = e2;
        this.d = i2 + 1;
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public c<E> clone() {
        try {
            c<E> cVar = (c) super.clone();
            cVar.f118b = (long[]) this.f118b.clone();
            cVar.f119c = (Object[]) this.f119c.clone();
            return cVar;
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    public final void c() {
        int i = this.d;
        long[] jArr = this.f118b;
        Object[] objArr = this.f119c;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != e) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.f117a = false;
        this.d = i2;
    }

    public E d(long j, E e2) {
        int iB = b.b(this.f118b, this.d, j);
        if (iB >= 0) {
            Object[] objArr = this.f119c;
            if (objArr[iB] != e) {
                return (E) objArr[iB];
            }
        }
        return e2;
    }

    public void e(long j, E e2) {
        int iB = b.b(this.f118b, this.d, j);
        if (iB >= 0) {
            this.f119c[iB] = e2;
            return;
        }
        int iB2 = iB ^ (-1);
        int i = this.d;
        if (iB2 < i) {
            Object[] objArr = this.f119c;
            if (objArr[iB2] == e) {
                this.f118b[iB2] = j;
                objArr[iB2] = e2;
                return;
            }
        }
        if (this.f117a && i >= this.f118b.length) {
            c();
            iB2 = b.b(this.f118b, this.d, j) ^ (-1);
        }
        int i2 = this.d;
        if (i2 >= this.f118b.length) {
            int iF = b.f(i2 + 1);
            long[] jArr = new long[iF];
            Object[] objArr2 = new Object[iF];
            long[] jArr2 = this.f118b;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr3 = this.f119c;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f118b = jArr;
            this.f119c = objArr2;
        }
        int i3 = this.d;
        if (i3 - iB2 != 0) {
            long[] jArr3 = this.f118b;
            int i4 = iB2 + 1;
            System.arraycopy(jArr3, iB2, jArr3, i4, i3 - iB2);
            Object[] objArr4 = this.f119c;
            System.arraycopy(objArr4, iB2, objArr4, i4, this.d - iB2);
        }
        this.f118b[iB2] = j;
        this.f119c[iB2] = e2;
        this.d++;
    }

    public String toString() {
        if (this.f117a) {
            c();
        }
        int i = this.d;
        if (i <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(i * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.d; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            if (this.f117a) {
                c();
            }
            sb.append(this.f118b[i2]);
            sb.append('=');
            if (this.f117a) {
                c();
            }
            Object obj = this.f119c[i2];
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
