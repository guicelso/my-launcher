package com.dofun.carsetting.presenter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import java.util.List;

/* JADX INFO: compiled from: InternetPresenter.java */
/* JADX INFO: loaded from: classes.dex */
public class g extends b<com.dofun.carsetting.g.k, com.dofun.carsetting.d.g> implements com.dofun.carsetting.g.k {
    private Context d;

    public g(Context context) {
        super(context);
        this.d = context;
        ((com.dofun.carsetting.d.g) this.b).a(context);
    }

    @Override // com.dofun.carsetting.presenter.b
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.d.g i() {
        return com.dofun.carsetting.d.g.a();
    }

    public void a(String str) {
        com.dofun.carsetting.d.g.a().a(str, this);
    }

    public void b(String str) {
        ((com.dofun.carsetting.d.g) this.b).a(str);
    }

    public void d(boolean z) {
        if (this.b != 0) {
            ((com.dofun.carsetting.d.g) this.b).a(z);
        }
    }

    public int b() {
        if (this.b != 0) {
            return ((com.dofun.carsetting.d.g) this.b).b();
        }
        return -1;
    }

    public void e(boolean z) {
        if (this.b != 0) {
            ((com.dofun.carsetting.d.g) this.b).b(z);
        }
    }

    public boolean c() {
        if (this.b != 0) {
            return ((com.dofun.carsetting.d.g) this.b).c();
        }
        return false;
    }

    public void f(boolean z) {
        if (this.b != 0) {
            ((com.dofun.carsetting.d.g) this.b).c(z);
        }
    }

    public boolean d() {
        if (this.b != 0) {
            return ((com.dofun.carsetting.d.g) this.b).d();
        }
        return false;
    }

    @Override // com.dofun.carsetting.g.k
    public void a(boolean z) {
        if (j() != null) {
            j().a(z);
        }
    }

    @Override // com.dofun.carsetting.g.k
    public void a(List<ScanResult> list) {
        if (j() != null) {
            j().a(list);
        }
    }

    @Override // com.dofun.carsetting.g.k
    public void a(WifiInfo wifiInfo) {
        if (j() != null) {
            j().a(wifiInfo);
        }
    }

    @Override // com.dofun.carsetting.g.k
    public void a(String str, int i) {
        if (j() != null) {
            j().a(str, i);
        }
    }

    @Override // com.dofun.carsetting.g.k
    public void a(int i) {
        if (j() != null) {
            j().a(i);
        }
    }

    @Override // com.dofun.carsetting.g.k
    public void b(boolean z) {
        if (j() != null) {
            j().b(z);
        }
    }

    @Override // com.dofun.carsetting.g.k
    public void c(boolean z) {
        if (j() != null) {
            j().c(z);
        }
    }
}
