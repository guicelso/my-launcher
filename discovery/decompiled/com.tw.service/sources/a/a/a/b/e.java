package a.a.a.b;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class e implements Map.Entry {
    e A;
    final Object mKey;
    e mNext;
    final Object mValue;

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return this.mKey.equals(eVar.mKey) && this.mValue.equals(eVar.mValue);
    }

    @Override // java.util.Map.Entry
    public Object getKey() {
        return this.mKey;
    }

    @Override // java.util.Map.Entry
    public Object getValue() {
        return this.mValue;
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        return this.mValue.hashCode() ^ this.mKey.hashCode();
    }

    @Override // java.util.Map.Entry
    public Object setValue(Object obj) {
        throw new UnsupportedOperationException("An entry modification is not supported");
    }

    public String toString() {
        return this.mKey + "=" + this.mValue;
    }
}
