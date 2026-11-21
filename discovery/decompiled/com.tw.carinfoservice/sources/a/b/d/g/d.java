package a.b.d.g;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class d<K, V> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final LinkedHashMap<K, V> f120a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f121b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f122c;
    public int d;
    public int e;
    public int f;
    public int g;

    public d(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.f122c = i;
        this.f120a = new LinkedHashMap<>(0, 0.75f, true);
    }

    public final V a(K k) {
        Objects.requireNonNull(k, "key == null");
        synchronized (this) {
            V v = this.f120a.get(k);
            if (v != null) {
                this.f++;
                return v;
            }
            this.g++;
            return null;
        }
    }

    public final V b(K k, V v) {
        V vPut;
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.d++;
            this.f121b++;
            vPut = this.f120a.put(k, v);
            if (vPut != null) {
                this.f121b--;
            }
        }
        c(this.f122c);
        return vPut;
    }

    public void c(int i) {
        while (true) {
            synchronized (this) {
                if (this.f121b < 0 || (this.f120a.isEmpty() && this.f121b != 0)) {
                    break;
                }
                if (this.f121b > i && !this.f120a.isEmpty()) {
                    Map.Entry<K, V> next = this.f120a.entrySet().iterator().next();
                    K key = next.getKey();
                    next.getValue();
                    this.f120a.remove(key);
                    this.f121b--;
                    this.e++;
                }
                return;
            }
        }
        throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
    }

    public final synchronized String toString() {
        int i;
        int i2;
        i = this.f;
        i2 = this.g + i;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.f122c), Integer.valueOf(this.f), Integer.valueOf(this.g), Integer.valueOf(i2 != 0 ? (i * 100) / i2 : 0));
    }
}
