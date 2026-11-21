package android.arch.a.b;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: FastSafeIterableMap.java */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class a<K, V> extends b<K, V> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private HashMap<K, b.c<K, V>> f3a = new HashMap<>();

    @Override // android.arch.a.b.b
    protected b.c<K, V> a(K k) {
        return this.f3a.get(k);
    }

    @Override // android.arch.a.b.b
    public V a(@NonNull K k, @NonNull V v) {
        b.c<K, V> cVarA = a(k);
        if (cVarA != null) {
            return cVarA.b;
        }
        this.f3a.put(k, b(k, v));
        return null;
    }

    @Override // android.arch.a.b.b
    public V b(@NonNull K k) {
        V v = (V) super.b(k);
        this.f3a.remove(k);
        return v;
    }

    public boolean c(K k) {
        return this.f3a.containsKey(k);
    }

    public Map.Entry<K, V> d(K k) {
        if (c(k)) {
            return this.f3a.get(k).d;
        }
        return null;
    }
}
