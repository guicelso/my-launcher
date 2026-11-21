package a.a.a.b;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class i implements Iterable {
    private e mEnd;
    private e mStart;
    private WeakHashMap H = new WeakHashMap();
    private int mSize = 0;

    public Iterator descendingIterator() {
        d dVar = new d(this.mEnd, this.mStart);
        this.H.put(dVar, false);
        return dVar;
    }

    public Map.Entry eldest() {
        return this.mStart;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof i)) {
            return false;
        }
        i iVar = (i) obj;
        if (this.mSize != iVar.mSize) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = iVar.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object next = it2.next();
            if ((entry == null && next != null) || (entry != null && !entry.equals(next))) {
                return false;
            }
        }
        return (it.hasNext() || it2.hasNext()) ? false : true;
    }

    public f f() {
        f fVar = new f(this, null);
        this.H.put(fVar, false);
        return fVar;
    }

    public Map.Entry g() {
        return this.mEnd;
    }

    protected e get(Object obj) {
        e eVar = this.mStart;
        while (eVar != null && !eVar.mKey.equals(obj)) {
            eVar = eVar.mNext;
        }
        return eVar;
    }

    public int hashCode() {
        Iterator it = iterator();
        int iHashCode = 0;
        while (it.hasNext()) {
            iHashCode += ((Map.Entry) it.next()).hashCode();
        }
        return iHashCode;
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        c cVar = new c(this.mStart, this.mEnd);
        this.H.put(cVar, false);
        return cVar;
    }

    public Object remove(Object obj) {
        e eVar = get(obj);
        if (eVar == null) {
            return null;
        }
        this.mSize--;
        if (!this.H.isEmpty()) {
            Iterator it = this.H.keySet().iterator();
            while (it.hasNext()) {
                ((h) it.next()).a(eVar);
            }
        }
        e eVar2 = eVar.A;
        if (eVar2 != null) {
            eVar2.mNext = eVar.mNext;
        } else {
            this.mStart = eVar.mNext;
        }
        e eVar3 = eVar.mNext;
        if (eVar3 != null) {
            eVar3.A = eVar.A;
        } else {
            this.mEnd = eVar.A;
        }
        eVar.mNext = null;
        eVar.A = null;
        return eVar.mValue;
    }

    public int size() {
        return this.mSize;
    }

    public String toString() {
        StringBuilder sbE = b.a.a.a.a.e("[");
        Iterator it = iterator();
        while (it.hasNext()) {
            sbE.append(((Map.Entry) it.next()).toString());
            if (it.hasNext()) {
                sbE.append(", ");
            }
        }
        sbE.append("]");
        return sbE.toString();
    }
}
