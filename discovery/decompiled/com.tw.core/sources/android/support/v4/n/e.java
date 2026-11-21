package android.support.v4.n;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class e extends c implements Map {
    h om;

    private h sk() {
        if (this.om == null) {
            this.om = new a(this);
        }
        return this.om;
    }

    @Override // java.util.Map
    public Set entrySet() {
        return sk().sp();
    }

    @Override // java.util.Map
    public Set keySet() {
        return sk().sq();
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        sf(this.oe + map.size());
        for (Map.Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public Collection values() {
        return sk().sr();
    }
}
