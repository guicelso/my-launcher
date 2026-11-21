package com.dofun.carsetting.d;

import android.content.Context;
import com.dofun.carsetting.presenter.b;

/* JADX INFO: compiled from: RecorderChooseModel.java */
/* JADX INFO: loaded from: classes.dex */
public class j<P extends com.dofun.carsetting.presenter.b> extends b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f204a;

    private j() {
    }

    /* JADX INFO: compiled from: RecorderChooseModel.java */
    private static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private static j f205a = new j();
    }

    public static j a() {
        return a.f205a;
    }

    public void a(Context context) {
        this.f204a = context.getApplicationContext();
    }
}
