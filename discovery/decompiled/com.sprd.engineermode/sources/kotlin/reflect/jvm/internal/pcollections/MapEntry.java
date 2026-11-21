package kotlin.reflect.jvm.internal.pcollections;

import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
final class MapEntry<K, V> implements Serializable {
    public final K key;
    public final V value;

    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public boolean equals(Object o) {
        if (!(o instanceof MapEntry)) {
            return false;
        }
        MapEntry<?, ?> e = (MapEntry) o;
        K k = this.key;
        if (k == null) {
            if (e.key != null) {
                return false;
            }
        } else if (!k.equals(e.key)) {
            return false;
        }
        V v = this.value;
        if (v == null) {
            if (e.value != null) {
                return false;
            }
        } else if (!v.equals(e.value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        K k = this.key;
        int iHashCode = k == null ? 0 : k.hashCode();
        V v = this.value;
        return iHashCode ^ (v != null ? v.hashCode() : 0);
    }

    public String toString() {
        return this.key + "=" + this.value;
    }
}
