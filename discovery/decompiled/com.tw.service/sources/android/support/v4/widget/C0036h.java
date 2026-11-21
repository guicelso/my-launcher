package android.support.v4.widget;

import java.util.ArrayList;
import java.util.HashSet;

/* JADX INFO: renamed from: android.support.v4.widget.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0036h {
    private final a.b.c.b.m rf = new a.b.c.b.m(10);
    private final a.b.c.b.o mGraph = new a.b.c.b.o();
    private final ArrayList sf = new ArrayList();
    private final HashSet tf = new HashSet();

    private void a(Object obj, ArrayList arrayList, HashSet hashSet) {
        if (arrayList.contains(obj)) {
            return;
        }
        if (hashSet.contains(obj)) {
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
        hashSet.add(obj);
        ArrayList arrayList2 = (ArrayList) this.mGraph.get(obj);
        if (arrayList2 != null) {
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                a(arrayList2.get(i), arrayList, hashSet);
            }
        }
        hashSet.remove(obj);
        arrayList.add(obj);
    }

    public ArrayList L() {
        this.sf.clear();
        this.tf.clear();
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            a(this.mGraph.keyAt(i), this.sf, this.tf);
        }
        return this.sf;
    }

    public void b(Object obj, Object obj2) {
        if (this.mGraph.indexOfKey(obj) >= 0) {
            if (this.mGraph.indexOfKey(obj2) >= 0) {
                ArrayList arrayList = (ArrayList) this.mGraph.get(obj);
                if (arrayList == null) {
                    arrayList = (ArrayList) this.rf.acquire();
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    this.mGraph.put(obj, arrayList);
                }
                arrayList.add(obj2);
                return;
            }
        }
        throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
    }

    public void clear() {
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.mGraph.valueAt(i);
            if (arrayList != null) {
                arrayList.clear();
                this.rf.release(arrayList);
            }
        }
        this.mGraph.clear();
    }

    public boolean contains(Object obj) {
        return this.mGraph.indexOfKey(obj) >= 0;
    }

    public void k(Object obj) {
        if (this.mGraph.indexOfKey(obj) >= 0) {
            return;
        }
        this.mGraph.put(obj, null);
    }

    public boolean l(Object obj) {
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.mGraph.valueAt(i);
            if (arrayList != null && arrayList.contains(obj)) {
                return true;
            }
        }
        return false;
    }
}
