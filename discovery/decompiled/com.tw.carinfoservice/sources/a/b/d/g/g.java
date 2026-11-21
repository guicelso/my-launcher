package a.b.d.g;

/* JADX INFO: loaded from: classes.dex */
public class g<E> implements Cloneable {
    public static final Object d = new Object();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int[] f138a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object[] f139b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f140c;

    public g() {
        int iE = b.e(10);
        this.f138a = new int[iE];
        this.f139b = new Object[iE];
        this.f140c = 0;
    }

    public void a(int i, E e) {
        int i2 = this.f140c;
        if (i2 != 0 && i <= this.f138a[i2 - 1]) {
            d(i, e);
            return;
        }
        if (i2 >= this.f138a.length) {
            int iE = b.e(i2 + 1);
            int[] iArr = new int[iE];
            Object[] objArr = new Object[iE];
            int[] iArr2 = this.f138a;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr2 = this.f139b;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.f138a = iArr;
            this.f139b = objArr;
        }
        this.f138a[i2] = i;
        this.f139b[i2] = e;
        this.f140c = i2 + 1;
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public g<E> clone() {
        try {
            g<E> gVar = (g) super.clone();
            gVar.f138a = (int[]) this.f138a.clone();
            gVar.f139b = (Object[]) this.f139b.clone();
            return gVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public E c(int i, E e) {
        int iA = b.a(this.f138a, this.f140c, i);
        if (iA >= 0) {
            Object[] objArr = this.f139b;
            if (objArr[iA] != d) {
                return (E) objArr[iA];
            }
        }
        return e;
    }

    public void d(int i, E e) {
        int iA = b.a(this.f138a, this.f140c, i);
        if (iA >= 0) {
            this.f139b[iA] = e;
            return;
        }
        int i2 = iA ^ (-1);
        int i3 = this.f140c;
        if (i2 < i3) {
            Object[] objArr = this.f139b;
            if (objArr[i2] == d) {
                this.f138a[i2] = i;
                objArr[i2] = e;
                return;
            }
        }
        if (i3 >= this.f138a.length) {
            int iE = b.e(i3 + 1);
            int[] iArr = new int[iE];
            Object[] objArr2 = new Object[iE];
            int[] iArr2 = this.f138a;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.f139b;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f138a = iArr;
            this.f139b = objArr2;
        }
        int i4 = this.f140c - i2;
        if (i4 != 0) {
            int[] iArr3 = this.f138a;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4);
            Object[] objArr4 = this.f139b;
            System.arraycopy(objArr4, i2, objArr4, i5, this.f140c - i2);
        }
        this.f138a[i2] = i;
        this.f139b[i2] = e;
        this.f140c++;
    }

    public String toString() {
        int i = this.f140c;
        if (i <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(i * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.f140c; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            sb.append(this.f138a[i2]);
            sb.append('=');
            Object obj = this.f139b[i2];
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
