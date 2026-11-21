package a.b.d.g;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class e<K, V> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final LinkedHashMap<K, V> f128a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f129b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f130c;
    public int d;
    public int e;
    public int f;
    public int g;

    public e(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.f130c = i;
        this.f128a = new LinkedHashMap<>(0, 0.75f, true);
    }

    public final V a(K k) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v = this.f128a.get(k);
            if (v != null) {
                this.f++;
                return v;
            }
            this.g++;
            return null;
        }
    }

    public final V a(K k, V v) {
        V vPut;
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.d++;
            this.f129b++;
            vPut = this.f128a.put(k, v);
            if (vPut != null) {
                this.f129b--;
            }
        }
        a(this.f130c);
        return vPut;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void a(int i) {
        while (true) {
            synchronized (this) {
                if (this.f129b < 0 || (this.f128a.isEmpty() && this.f129b != 0)) {
                    break;
                }
                if (this.f129b > i && !this.f128a.isEmpty()) {
                    Map.Entry<K, V> next = this.f128a.entrySet().iterator().next();
                    K key = next.getKey();
                    next.getValue();
                    this.f128a.remove(key);
                    this.f129b--;
                    this.e++;
                }
                return;
            }
        }
        throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
    }

    public final synchronized String toString() {
        int i;
        i = this.f + this.g;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.f130c), Integer.valueOf(this.f), Integer.valueOf(this.g), Integer.valueOf(i != 0 ? (this.f * 100) / i : 0));
    }
}
