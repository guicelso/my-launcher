package kotlin.reflect.jvm.internal.pcollections;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes4.dex */
public final class HashPMap<K, V> {
    private static final HashPMap<Object, Object> EMPTY = new HashPMap<>(IntTreePMap.empty(), 0);
    private final IntTreePMap<ConsPStack<MapEntry<K, V>>> intMap;
    private final int size;

    @NotNull
    public static <K, V> HashPMap<K, V> empty() {
        return (HashPMap<K, V>) EMPTY;
    }

    private HashPMap(IntTreePMap<ConsPStack<MapEntry<K, V>>> intMap, int size) {
        this.intMap = intMap;
        this.size = size;
    }

    public int size() {
        return this.size;
    }

    public boolean containsKey(Object key) {
        return keyIndexIn(getEntries(key.hashCode()), key) != -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public V get(Object key) {
        for (ConsPStack entries = getEntries(key.hashCode()); entries != null && entries.size() > 0; entries = entries.rest) {
            MapEntry<K, V> entry = (MapEntry) entries.first;
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @NotNull
    public HashPMap<K, V> plus(K key, V value) {
        ConsPStack<MapEntry<K, V>> entries = getEntries(key.hashCode());
        int size0 = entries.size();
        int i = keyIndexIn(entries, key);
        if (i != -1) {
            entries = entries.minus(i);
        }
        ConsPStack<MapEntry<K, V>> entries2 = entries.plus(new MapEntry<>(key, value));
        return new HashPMap<>(this.intMap.plus(key.hashCode(), entries2), (this.size - size0) + entries2.size());
    }

    @NotNull
    public HashPMap<K, V> minus(Object key) {
        ConsPStack<MapEntry<K, V>> entries = getEntries(key.hashCode());
        int i = keyIndexIn(entries, key);
        if (i == -1) {
            return this;
        }
        ConsPStack<MapEntry<K, V>> entries2 = entries.minus(i);
        if (entries2.size() == 0) {
            return new HashPMap<>(this.intMap.minus(key.hashCode()), this.size - 1);
        }
        return new HashPMap<>(this.intMap.plus(key.hashCode(), entries2), this.size - 1);
    }

    private ConsPStack<MapEntry<K, V>> getEntries(int hash) {
        ConsPStack<MapEntry<K, V>> entries = this.intMap.get(hash);
        return entries == null ? ConsPStack.empty() : entries;
    }

    private static <K, V> int keyIndexIn(ConsPStack<MapEntry<K, V>> consPStack, Object obj) {
        int i = 0;
        while (consPStack != null && consPStack.size() > 0) {
            if (consPStack.first.key.equals(obj)) {
                return i;
            }
            consPStack = consPStack.rest;
            i++;
        }
        return -1;
    }
}
