package a.b.d.g;

import java.util.ConcurrentModificationException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class f<K, V> {
    public static Object[] d;
    public static int e;
    public static Object[] f;
    public static int g;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int[] f135a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object[] f136b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f137c;

    public f() {
        this.f135a = b.f114a;
        this.f136b = b.f116c;
        this.f137c = 0;
    }

    public f(int i) {
        if (i == 0) {
            this.f135a = b.f114a;
            this.f136b = b.f116c;
        } else {
            a(i);
        }
        this.f137c = 0;
    }

    public static void b(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (a.class) {
                if (g < 10) {
                    objArr[0] = f;
                    objArr[1] = iArr;
                    for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    f = objArr;
                    g++;
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (a.class) {
                if (e < 10) {
                    objArr[0] = d;
                    objArr[1] = iArr;
                    for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    d = objArr;
                    e++;
                }
            }
        }
    }

    public final void a(int i) {
        if (i == 8) {
            synchronized (a.class) {
                Object[] objArr = f;
                if (objArr != null) {
                    this.f136b = objArr;
                    f = (Object[]) objArr[0];
                    this.f135a = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    g--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (a.class) {
                Object[] objArr2 = d;
                if (objArr2 != null) {
                    this.f136b = objArr2;
                    d = (Object[]) objArr2[0];
                    this.f135a = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    e--;
                    return;
                }
            }
        }
        this.f135a = new int[i];
        this.f136b = new Object[i << 1];
    }

    public int c(Object obj, int i) {
        int i2 = this.f137c;
        if (i2 == 0) {
            return -1;
        }
        try {
            int iA = b.a(this.f135a, i2, i);
            if (iA < 0 || obj.equals(this.f136b[iA << 1])) {
                return iA;
            }
            int i3 = iA + 1;
            while (i3 < i2 && this.f135a[i3] == i) {
                if (obj.equals(this.f136b[i3 << 1])) {
                    return i3;
                }
                i3++;
            }
            for (int i4 = iA - 1; i4 >= 0 && this.f135a[i4] == i; i4--) {
                if (obj.equals(this.f136b[i4 << 1])) {
                    return i4;
                }
            }
            return i3 ^ (-1);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    public void clear() {
        int i = this.f137c;
        if (i > 0) {
            int[] iArr = this.f135a;
            Object[] objArr = this.f136b;
            this.f135a = b.f114a;
            this.f136b = b.f116c;
            this.f137c = 0;
            b(iArr, objArr, i);
        }
        if (this.f137c > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(Object obj) {
        return d(obj) >= 0;
    }

    public boolean containsValue(Object obj) {
        return f(obj) >= 0;
    }

    public int d(Object obj) {
        return obj == null ? e() : c(obj, obj.hashCode());
    }

    public int e() {
        int i = this.f137c;
        if (i == 0) {
            return -1;
        }
        try {
            int iA = b.a(this.f135a, i, 0);
            if (iA < 0 || this.f136b[iA << 1] == null) {
                return iA;
            }
            int i2 = iA + 1;
            while (i2 < i && this.f135a[i2] == 0) {
                if (this.f136b[i2 << 1] == null) {
                    return i2;
                }
                i2++;
            }
            for (int i3 = iA - 1; i3 >= 0 && this.f135a[i3] == 0; i3--) {
                if (this.f136b[i3 << 1] == null) {
                    return i3;
                }
            }
            return i2 ^ (-1);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof f) {
            f fVar = (f) obj;
            if (this.f137c != fVar.f137c) {
                return false;
            }
            for (int i = 0; i < this.f137c; i++) {
                try {
                    K kG = g(i);
                    V vI = i(i);
                    Object obj2 = fVar.get(kG);
                    if (vI == null) {
                        if (obj2 != null || !fVar.containsKey(kG)) {
                            return false;
                        }
                    } else if (!vI.equals(obj2)) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                    return false;
                }
            }
            return true;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (this.f137c != map.size()) {
                return false;
            }
            for (int i2 = 0; i2 < this.f137c; i2++) {
                try {
                    K kG2 = g(i2);
                    V vI2 = i(i2);
                    Object obj3 = map.get(kG2);
                    if (vI2 == null) {
                        if (obj3 != null || !map.containsKey(kG2)) {
                            return false;
                        }
                    } else if (!vI2.equals(obj3)) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused2) {
                }
            }
            return true;
        }
        return false;
    }

    public int f(Object obj) {
        int i = this.f137c * 2;
        Object[] objArr = this.f136b;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
            return -1;
        }
        for (int i3 = 1; i3 < i; i3 += 2) {
            if (obj.equals(objArr[i3])) {
                return i3 >> 1;
            }
        }
        return -1;
    }

    public K g(int i) {
        return (K) this.f136b[i << 1];
    }

    public V get(Object obj) {
        int iD = d(obj);
        if (iD >= 0) {
            return (V) this.f136b[(iD << 1) + 1];
        }
        return null;
    }

    public V h(int i) {
        Object[] objArr = this.f136b;
        int i2 = i << 1;
        V v = (V) objArr[i2 + 1];
        int i3 = this.f137c;
        int i4 = 0;
        if (i3 <= 1) {
            b(this.f135a, objArr, i3);
            this.f135a = b.f114a;
            this.f136b = b.f116c;
        } else {
            int i5 = i3 - 1;
            int[] iArr = this.f135a;
            if (iArr.length <= 8 || i3 >= iArr.length / 3) {
                if (i < i5) {
                    int i6 = i + 1;
                    int i7 = i5 - i;
                    System.arraycopy(iArr, i6, iArr, i, i7);
                    Object[] objArr2 = this.f136b;
                    System.arraycopy(objArr2, i6 << 1, objArr2, i2, i7 << 1);
                }
                Object[] objArr3 = this.f136b;
                int i8 = i5 << 1;
                objArr3[i8] = null;
                objArr3[i8 + 1] = null;
            } else {
                a(i3 > 8 ? i3 + (i3 >> 1) : 8);
                if (i3 != this.f137c) {
                    throw new ConcurrentModificationException();
                }
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.f135a, 0, i);
                    System.arraycopy(objArr, 0, this.f136b, 0, i2);
                }
                if (i < i5) {
                    int i9 = i + 1;
                    int i10 = i5 - i;
                    System.arraycopy(iArr, i9, this.f135a, i, i10);
                    System.arraycopy(objArr, i9 << 1, this.f136b, i2, i10 << 1);
                }
            }
            i4 = i5;
        }
        if (i3 != this.f137c) {
            throw new ConcurrentModificationException();
        }
        this.f137c = i4;
        return v;
    }

    public int hashCode() {
        int[] iArr = this.f135a;
        Object[] objArr = this.f136b;
        int i = this.f137c;
        int i2 = 1;
        int i3 = 0;
        int iHashCode = 0;
        while (i3 < i) {
            Object obj = objArr[i2];
            iHashCode += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return iHashCode;
    }

    public V i(int i) {
        return (V) this.f136b[(i << 1) + 1];
    }

    public boolean isEmpty() {
        return this.f137c <= 0;
    }

    public V put(K k, V v) {
        int i;
        int iC;
        int i2 = this.f137c;
        if (k == null) {
            iC = e();
            i = 0;
        } else {
            int iHashCode = k.hashCode();
            i = iHashCode;
            iC = c(k, iHashCode);
        }
        if (iC >= 0) {
            int i3 = (iC << 1) + 1;
            Object[] objArr = this.f136b;
            V v2 = (V) objArr[i3];
            objArr[i3] = v;
            return v2;
        }
        int i4 = iC ^ (-1);
        int[] iArr = this.f135a;
        if (i2 >= iArr.length) {
            int i5 = 4;
            if (i2 >= 8) {
                i5 = (i2 >> 1) + i2;
            } else if (i2 >= 4) {
                i5 = 8;
            }
            Object[] objArr2 = this.f136b;
            a(i5);
            if (i2 != this.f137c) {
                throw new ConcurrentModificationException();
            }
            int[] iArr2 = this.f135a;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr2, 0, this.f136b, 0, objArr2.length);
            }
            b(iArr, objArr2, i2);
        }
        if (i4 < i2) {
            int[] iArr3 = this.f135a;
            int i6 = i4 + 1;
            System.arraycopy(iArr3, i4, iArr3, i6, i2 - i4);
            Object[] objArr3 = this.f136b;
            System.arraycopy(objArr3, i4 << 1, objArr3, i6 << 1, (this.f137c - i4) << 1);
        }
        int i7 = this.f137c;
        if (i2 == i7) {
            int[] iArr4 = this.f135a;
            if (i4 < iArr4.length) {
                iArr4[i4] = i;
                Object[] objArr4 = this.f136b;
                int i8 = i4 << 1;
                objArr4[i8] = k;
                objArr4[i8 + 1] = v;
                this.f137c = i7 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    public V remove(Object obj) {
        int iD = d(obj);
        if (iD >= 0) {
            return h(iD);
        }
        return null;
    }

    public int size() {
        return this.f137c;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f137c * 28);
        sb.append('{');
        for (int i = 0; i < this.f137c; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            K kG = g(i);
            if (kG != this) {
                sb.append(kG);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            V vI = i(i);
            if (vI != this) {
                sb.append(vI);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
