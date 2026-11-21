package com.tw.service;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
class da {
    private WindowManager Hc;
    private boolean Ic = false;
    private boolean Mk = false;
    private WindowManager.LayoutParams mLayoutParams;
    private RadarView mRV;
    private View mView;
    final /* synthetic */ TWService n;

    public da(TWService tWService) {
        this.n = tWService;
        this.mView = ((LayoutInflater) tWService.getSystemService("layout_inflater")).inflate(R.layout.radar, (ViewGroup) null);
        this.mRV = (RadarView) this.mView.findViewById(R.id.radar_rv);
        ((ImageView) this.mView.findViewById(R.id.radar_back)).setOnClickListener(new ba(this, tWService));
        ((ImageView) this.mView.findViewById(R.id.radar_voice)).setOnClickListener(new ca(this, tWService));
        this.Hc = (WindowManager) tWService.getSystemService("window");
        this.mLayoutParams = new WindowManager.LayoutParams(tWService.Db, -2, 0, 0, 2002, 32, 1);
    }

    public void F(int i) {
        ((ImageView) this.mView.findViewById(R.id.radar_voice)).getDrawable().setLevel(i);
    }

    public void G(int i) {
        this.mView.findViewById(R.id.radar_voice).setVisibility(i);
    }

    public void e(int i, int i2, int i3, int i4) {
        this.mRV.a(i, i2, i3, i4);
        this.mRV.invalidate();
    }

    public void f(int i, int i2, int i3, int i4) {
        this.mRV.b(i, i2, i3, i4);
        this.mRV.invalidate();
    }

    public void g(int i, int i2, int i3, int i4) {
        this.mRV.d(i, i2, i3, i4);
        this.mRV.invalidate();
    }

    public void h(int i, int i2, int i3, int i4) {
        this.mRV.c(i, i2, i3, i4);
        this.mRV.invalidate();
    }

    public void hide() {
        if (!this.n.kb && this.Ic) {
            this.Hc.removeView(this.mView);
            this.Ic = false;
        }
    }

    public boolean na() {
        return this.Ic;
    }

    public void oa() {
        this.Mk = false;
    }

    public void show() {
        if (TWService.rc == 0 || this.n.kb) {
            return;
        }
        if (((Settings.System.getInt(this.n.getContentResolver(), "SYSTEM_FRADAR", 0) == 0 && Settings.System.getInt(this.n.getContentResolver(), "SYSTEM_FRONT_FRADAR", 0) == 0) ? false : true) || this.Ic || this.Mk) {
            return;
        }
        this.Ic = true;
        this.Hc.addView(this.mView, this.mLayoutParams);
    }
}
