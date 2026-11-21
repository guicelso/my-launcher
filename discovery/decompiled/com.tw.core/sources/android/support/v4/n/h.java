package android.support.v4.n;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
abstract class h {
    f ot;
    b ou;
    d ov;

    h() {
    }

    public static boolean sm(Map map, Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!map.containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static boolean sn(Map map, Collection collection) {
        int size = map.size();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            map.remove(it.next());
        }
        return size != map.size();
    }

    public static boolean so(Map map, Collection collection) {
        int size = map.size();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    public static boolean ss(Set set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set2 = (Set) obj;
        try {
            if (set.size() == set2.size()) {
                return set.containsAll(set2);
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    protected abstract void rQ();

    protected abstract Object rR(int i, int i2);

    protected abstract Map rS();

    protected abstract int rT();

    protected abstract int rU(Object obj);

    protected abstract int rV(Object obj);

    protected abstract void rW(Object obj, Object obj2);

    protected abstract void rX(int i);

    protected abstract Object rY(int i, Object obj);

    public Set sp() {
        if (this.ot == null) {
            this.ot = new f(this);
        }
        return this.ot;
    }

    public Set sq() {
        if (this.ou == null) {
            this.ou = new b(this);
        }
        return this.ou;
    }

    public Collection sr() {
        if (this.ov == null) {
            this.ov = new d(this);
        }
        return this.ov;
    }

    public Object[] st(int i) {
        int iRT = rT();
        Object[] objArr = new Object[iRT];
        for (int i2 = 0; i2 < iRT; i2++) {
            objArr[i2] = rR(i2, i);
        }
        return objArr;
    }

    public Object[] su(Object[] objArr, int i) {
        int iRT = rT();
        Object[] objArr2 = objArr.length < iRT ? (Object[]) Array.newInstance(objArr.getClass().getComponentType(), iRT) : objArr;
        for (int i2 = 0; i2 < iRT; i2++) {
            objArr2[i2] = rR(i2, i);
        }
        if (objArr2.length > iRT) {
            objArr2[iRT] = null;
        }
        return objArr2;
    }
}
