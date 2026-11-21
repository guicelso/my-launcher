package android.arch.lifecycle;

import android.support.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SingleGeneratedAdapterObserver implements GenericLifecycleObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final b f15a;

    SingleGeneratedAdapterObserver(b bVar) {
        this.f15a = bVar;
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void a(d dVar, Lifecycle.Event event) {
        this.f15a.a(dVar, event, false, null);
        this.f15a.a(dVar, event, true, null);
    }
}
