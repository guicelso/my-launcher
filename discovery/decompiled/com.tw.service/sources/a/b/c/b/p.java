package a.b.c.b;

/* JADX INFO: loaded from: classes.dex */
public class p implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean Ie;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    public p() {
        this(10);
    }

    public p(int i) {
        Object[] objArr;
        this.Ie = false;
        if (i == 0) {
            this.mKeys = e.Ee;
            objArr = e.Fe;
        } else {
            int i2 = e.i(i);
            this.mKeys = new int[i2];
            objArr = new Object[i2];
        }
        this.mValues = objArr;
        this.mSize = 0;
    }

    private void gc() {
        int i = this.mSize;
        int[] iArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.Ie = false;
        this.mSize = i2;
    }

    public void clear() {
        int i = this.mSize;
        Object[] objArr = this.mValues;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.Ie = false;
    }

    public Object clone() {
        try {
            p pVar = (p) super.clone();
            try {
                pVar.mKeys = (int[]) this.mKeys.clone();
                pVar.mValues = (Object[]) this.mValues.clone();
            } catch (CloneNotSupportedException unused) {
            }
            return pVar;
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public Object get(int i) {
        int iBinarySearch = e.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch >= 0) {
            Object[] objArr = this.mValues;
            if (objArr[iBinarySearch] != DELETED) {
                return objArr[iBinarySearch];
            }
        }
        return null;
    }

    public int keyAt(int i) {
        if (this.Ie) {
            gc();
        }
        return this.mKeys[i];
    }

    public void put(int i, Object obj) {
        int iBinarySearch = e.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = obj;
            return;
        }
        int i2 = ~iBinarySearch;
        if (i2 < this.mSize) {
            Object[] objArr = this.mValues;
            if (objArr[i2] == DELETED) {
                this.mKeys[i2] = i;
                objArr[i2] = obj;
                return;
            }
        }
        if (this.Ie && this.mSize >= this.mKeys.length) {
            gc();
            i2 = ~e.binarySearch(this.mKeys, this.mSize, i);
        }
        int i3 = this.mSize;
        if (i3 >= this.mKeys.length) {
            int i4 = e.i(i3 + 1);
            int[] iArr = new int[i4];
            Object[] objArr2 = new Object[i4];
            int[] iArr2 = this.mKeys;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.mValues;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.mKeys = iArr;
            this.mValues = objArr2;
        }
        int i5 = this.mSize;
        if (i5 - i2 != 0) {
            int[] iArr3 = this.mKeys;
            int i6 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i6, i5 - i2);
            Object[] objArr4 = this.mValues;
            System.arraycopy(objArr4, i2, objArr4, i6, this.mSize - i2);
        }
        this.mKeys[i2] = i;
        this.mValues[i2] = obj;
        this.mSize++;
    }

    public void remove(int i) {
        int iBinarySearch = e.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch >= 0) {
            Object[] objArr = this.mValues;
            Object obj = objArr[iBinarySearch];
            Object obj2 = DELETED;
            if (obj != obj2) {
                objArr[iBinarySearch] = obj2;
                this.Ie = true;
            }
        }
    }

    public int size() {
        if (this.Ie) {
            gc();
        }
        return this.mSize;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            Object objValueAt = valueAt(i);
            if (objValueAt != this) {
                sb.append(objValueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public Object valueAt(int i) {
        if (this.Ie) {
            gc();
        }
        return this.mValues[i];
    }
}
