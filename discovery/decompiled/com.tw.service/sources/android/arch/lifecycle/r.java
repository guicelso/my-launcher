package android.arch.lifecycle;

import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class r {
    private final HashMap mMap = new HashMap();

    final void a(String str, o oVar) {
        o oVar2 = (o) this.mMap.put(str, oVar);
        if (oVar2 != null) {
            oVar2.m();
        }
    }

    public final void clear() {
        Iterator it = this.mMap.values().iterator();
        while (it.hasNext()) {
            ((o) it.next()).m();
        }
        this.mMap.clear();
    }

    final o get(String str) {
        return (o) this.mMap.get(str);
    }
}
