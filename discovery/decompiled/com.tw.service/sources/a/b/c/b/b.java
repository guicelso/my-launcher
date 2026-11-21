package a.b.c.b;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class b extends o implements Map {
    l De;

    private l getCollection() {
        if (this.De == null) {
            this.De = new a(this);
        }
        return this.De;
    }

    @Override // java.util.Map
    public Set entrySet() {
        l collection = getCollection();
        if (collection.mEntrySet == null) {
            collection.mEntrySet = new h(collection);
        }
        return collection.mEntrySet;
    }

    @Override // java.util.Map
    public Set keySet() {
        l collection = getCollection();
        if (collection.mKeySet == null) {
            collection.mKeySet = new i(collection);
        }
        return collection.mKeySet;
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        ensureCapacity(map.size() + this.mSize);
        for (Map.Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean retainAll(Collection collection) {
        return l.retainAllHelper(this, collection);
    }

    @Override // java.util.Map
    public Collection values() {
        l collection = getCollection();
        if (collection.mValues == null) {
            collection.mValues = new k(collection);
        }
        return collection.mValues;
    }
}
