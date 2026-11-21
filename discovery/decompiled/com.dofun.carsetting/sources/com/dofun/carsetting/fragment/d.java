package com.dofun.carsetting.fragment;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.view.View;
import java.util.List;

/* JADX INFO: compiled from: BaseInternetFragment.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class d extends ad<com.dofun.carsetting.presenter.g> implements com.dofun.carsetting.g.k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f262a = "";

    public abstract String a();

    @Override // com.dofun.carsetting.g.k
    public void a(int i) {
    }

    @Override // com.dofun.carsetting.g.k
    public void a(WifiInfo wifiInfo) {
    }

    @Override // com.dofun.carsetting.g.k
    public void a(String str, int i) {
    }

    @Override // com.dofun.carsetting.g.k
    public void a(List<ScanResult> list) {
    }

    @Override // com.dofun.carsetting.g.k
    public void a(boolean z) {
    }

    @Override // com.dofun.carsetting.g.k
    public void b(boolean z) {
    }

    @Override // com.dofun.carsetting.g.k
    public void c(boolean z) {
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onAttach(Context context) {
        this.i = context;
        super.onAttach(context);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.f262a = a();
        ((com.dofun.carsetting.presenter.g) this.h).a(this.f262a);
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.g d() {
        return new com.dofun.carsetting.presenter.g(this.i);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        if (this.h != 0) {
            ((com.dofun.carsetting.presenter.g) this.h).b(this.f262a);
        }
        super.onDestroy();
    }
}
