package android.arch.lifecycle;

import android.support.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class CompositeGeneratedAdaptersObserver implements GenericLifecycleObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final b[] f8a;

    CompositeGeneratedAdaptersObserver(b[] bVarArr) {
        this.f8a = bVarArr;
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void a(d dVar, Lifecycle.Event event) {
        h hVar = new h();
        for (b bVar : this.f8a) {
            bVar.a(dVar, event, false, hVar);
        }
        for (b bVar2 : this.f8a) {
            bVar2.a(dVar, event, true, hVar);
        }
    }
}
