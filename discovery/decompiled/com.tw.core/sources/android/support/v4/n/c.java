package android.support.v4.n;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class c {
    static Object[] og;
    static int oh;
    static Object[] oj;
    static int ok;
    int[] oi = j.oA;
    Object[] of = j.oC;
    int oe = 0;

    private void sg(int i) {
        Class<e> cls;
        if (i == 8) {
            cls = e.class;
            synchronized (cls) {
                if (oj != null) {
                    Object[] objArr = oj;
                    this.of = objArr;
                    oj = (Object[]) objArr[0];
                    this.oi = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    ok--;
                }
            }
            return;
        }
        if (i == 4) {
            cls = e.class;
            synchronized (cls) {
                if (og != null) {
                    Object[] objArr2 = og;
                    this.of = objArr2;
                    og = (Object[]) objArr2[0];
                    this.oi = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    oh--;
                }
            }
            return;
        }
        this.oi = new int[i];
        this.of = new Object[i << 1];
        this.oi = new int[i];
        this.of = new Object[i << 1];
    }

    private static void sh(int[] iArr, Object[] objArr, int i) {
        Class<e> cls;
        if (iArr.length == 8) {
            cls = e.class;
            synchronized (cls) {
                if (ok < 10) {
                    objArr[0] = oj;
                    objArr[1] = iArr;
                    for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    oj = objArr;
                    ok++;
                }
            }
        } else {
            if (iArr.length != 4) {
                return;
            }
            cls = e.class;
            synchronized (cls) {
                if (oh < 10) {
                    objArr[0] = og;
                    objArr[1] = iArr;
                    for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    og = objArr;
                    oh++;
                }
            }
        }
    }

    public void clear() {
        if (this.oe != 0) {
            sh(this.oi, this.of, this.oe);
            this.oi = j.oA;
            this.of = j.oC;
            this.oe = 0;
        }
    }

    public boolean containsKey(Object obj) {
        return sc(obj) >= 0;
    }

    public boolean containsValue(Object obj) {
        return sd(obj) >= 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (size() != map.size()) {
            return false;
        }
        for (int i = 0; i < this.oe; i++) {
            try {
                Object objSb = sb(i);
                Object objRZ = rZ(i);
                Object obj2 = map.get(objSb);
                if (objRZ == null) {
                    if (obj2 != null || !map.containsKey(objSb)) {
                        return false;
                    }
                } else if (!objRZ.equals(obj2)) {
                    return false;
                }
            } catch (ClassCastException e) {
                return false;
            } catch (NullPointerException e2) {
                return false;
            }
        }
        return true;
    }

    public Object get(Object obj) {
        int iSc = sc(obj);
        if (iSc >= 0) {
            return this.of[(iSc << 1) + 1];
        }
        return null;
    }

    public int hashCode() {
        int[] iArr = this.oi;
        Object[] objArr = this.of;
        int i = this.oe;
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

    public boolean isEmpty() {
        return this.oe <= 0;
    }

    public Object put(Object obj, Object obj2) {
        int iHashCode;
        int iSi;
        int i = 8;
        if (obj == null) {
            iSi = sj();
            iHashCode = 0;
        } else {
            iHashCode = obj.hashCode();
            iSi = si(obj, iHashCode);
        }
        if (iSi >= 0) {
            int i2 = (iSi << 1) + 1;
            Object obj3 = this.of[i2];
            this.of[i2] = obj2;
            return obj3;
        }
        int i3 = ~iSi;
        if (this.oe >= this.oi.length) {
            if (this.oe >= 8) {
                i = this.oe + (this.oe >> 1);
            } else if (this.oe < 4) {
                i = 4;
            }
            int[] iArr = this.oi;
            Object[] objArr = this.of;
            sg(i);
            if (this.oi.length > 0) {
                System.arraycopy(iArr, 0, this.oi, 0, iArr.length);
                System.arraycopy(objArr, 0, this.of, 0, objArr.length);
            }
            sh(iArr, objArr, this.oe);
        }
        if (i3 < this.oe) {
            System.arraycopy(this.oi, i3, this.oi, i3 + 1, this.oe - i3);
            System.arraycopy(this.of, i3 << 1, this.of, (i3 + 1) << 1, (this.oe - i3) << 1);
        }
        this.oi[i3] = iHashCode;
        this.of[i3 << 1] = obj;
        this.of[(i3 << 1) + 1] = obj2;
        this.oe++;
        return null;
    }

    public Object rZ(int i) {
        return this.of[(i << 1) + 1];
    }

    public Object remove(Object obj) {
        int iSc = sc(obj);
        if (iSc >= 0) {
            return se(iSc);
        }
        return null;
    }

    public Object sa(int i, Object obj) {
        int i2 = (i << 1) + 1;
        Object obj2 = this.of[i2];
        this.of[i2] = obj;
        return obj2;
    }

    public Object sb(int i) {
        return this.of[i << 1];
    }

    public int sc(Object obj) {
        return obj == null ? sj() : si(obj, obj.hashCode());
    }

    int sd(Object obj) {
        int i = 1;
        int i2 = this.oe * 2;
        Object[] objArr = this.of;
        if (obj == null) {
            while (i < i2) {
                if (objArr[i] == null) {
                    return i >> 1;
                }
                i += 2;
            }
            return -1;
        }
        while (i < i2) {
            if (obj.equals(objArr[i])) {
                return i >> 1;
            }
            i += 2;
        }
        return -1;
    }

    public Object se(int i) {
        Object obj = this.of[(i << 1) + 1];
        if (this.oe <= 1) {
            sh(this.oi, this.of, this.oe);
            this.oi = j.oA;
            this.of = j.oC;
            this.oe = 0;
        } else if (this.oi.length <= 8 || this.oe >= this.oi.length / 3) {
            this.oe--;
            if (i < this.oe) {
                System.arraycopy(this.oi, i + 1, this.oi, i, this.oe - i);
                System.arraycopy(this.of, (i + 1) << 1, this.of, i << 1, (this.oe - i) << 1);
            }
            this.of[this.oe << 1] = null;
            this.of[(this.oe << 1) + 1] = null;
        } else {
            int i2 = this.oe > 8 ? this.oe + (this.oe >> 1) : 8;
            int[] iArr = this.oi;
            Object[] objArr = this.of;
            sg(i2);
            this.oe--;
            if (i > 0) {
                System.arraycopy(iArr, 0, this.oi, 0, i);
                System.arraycopy(objArr, 0, this.of, 0, i << 1);
            }
            if (i < this.oe) {
                System.arraycopy(iArr, i + 1, this.oi, i, this.oe - i);
                System.arraycopy(objArr, (i + 1) << 1, this.of, i << 1, (this.oe - i) << 1);
            }
        }
        return obj;
    }

    public void sf(int i) {
        if (this.oi.length < i) {
            int[] iArr = this.oi;
            Object[] objArr = this.of;
            sg(i);
            if (this.oe > 0) {
                System.arraycopy(iArr, 0, this.oi, 0, this.oe);
                System.arraycopy(objArr, 0, this.of, 0, this.oe << 1);
            }
            sh(iArr, objArr, this.oe);
        }
    }

    int si(Object obj, int i) {
        int i2 = this.oe;
        if (i2 == 0) {
            return -1;
        }
        int iSv = j.sv(this.oi, i2, i);
        if (iSv < 0 || obj.equals(this.of[iSv << 1])) {
            return iSv;
        }
        int i3 = iSv + 1;
        while (i3 < i2 && this.oi[i3] == i) {
            if (obj.equals(this.of[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = iSv - 1; i4 >= 0 && this.oi[i4] == i; i4--) {
            if (obj.equals(this.of[i4 << 1])) {
                return i4;
            }
        }
        return ~i3;
    }

    public int size() {
        return this.oe;
    }

    int sj() {
        int i = this.oe;
        if (i == 0) {
            return -1;
        }
        int iSv = j.sv(this.oi, i, 0);
        if (iSv < 0 || this.of[iSv << 1] == null) {
            return iSv;
        }
        int i2 = iSv + 1;
        while (i2 < i && this.oi[i2] == 0) {
            if (this.of[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = iSv - 1; i3 >= 0 && this.oi[i3] == 0; i3--) {
            if (this.of[i3 << 1] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.oe * 28);
        sb.append('{');
        for (int i = 0; i < this.oe; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            Object objSb = sb(i);
            if (objSb != this) {
                sb.append(objSb);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            Object objRZ = rZ(i);
            if (objRZ != this) {
                sb.append(objRZ);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
