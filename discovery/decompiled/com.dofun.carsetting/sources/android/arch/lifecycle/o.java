package android.arch.lifecycle;

import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: compiled from: ViewModelStore.java */
/* JADX INFO: loaded from: classes.dex */
public class o {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final HashMap<String, m> f26a = new HashMap<>();

    final void a(String str, m mVar) {
        m mVarPut = this.f26a.put(str, mVar);
        if (mVarPut != null) {
            mVarPut.onCleared();
        }
    }

    final m a(String str) {
        return this.f26a.get(str);
    }

    public final void a() {
        Iterator<m> it = this.f26a.values().iterator();
        while (it.hasNext()) {
            it.next().onCleared();
        }
        this.f26a.clear();
    }
}
