package com.dofun.carsetting.presenter;

import android.content.Context;
import android.os.Handler;
import com.dofun.carsetting.d.b;
import com.dofun.carsetting.g.c;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: BasePresenter.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class b<V extends com.dofun.carsetting.g.c, M extends com.dofun.carsetting.d.b> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f389a;
    private WeakReference<V> d;
    public Handler c = new Handler();
    public M b = (M) i();

    public abstract M i();

    public void l() {
    }

    public b(Context context) {
        this.f389a = context;
    }

    public void a(V v) {
        this.d = new WeakReference<>(v);
    }

    public V j() {
        if (this.d == null) {
            return null;
        }
        return this.d.get();
    }

    public void k() {
        if (this.d != null) {
            this.d.clear();
            this.d = null;
        }
    }
}
