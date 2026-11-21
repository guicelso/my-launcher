package com.tw.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes.dex */
class ia {
    private WindowManager Hc;
    private boolean Ic = false;
    private WindowManager.LayoutParams mLayoutParams;
    private View mView;
    final /* synthetic */ TWService n;

    public ia(TWService tWService) {
        this.n = tWService;
        this.mView = ((LayoutInflater) tWService.getSystemService("layout_inflater")).inflate(R.layout.ta_layout, (ViewGroup) null);
        this.mView.findViewById(R.id.img_ta).setOnClickListener(new ha(this, tWService));
        this.Hc = (WindowManager) tWService.getSystemService("window");
        this.mLayoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2002, 40, 1);
    }

    public void hide() {
        if (this.Ic) {
            this.Hc.removeView(this.mView);
            this.Ic = false;
        }
    }

    public void show() {
        if (this.Ic) {
            return;
        }
        this.Ic = true;
        this.Hc.addView(this.mView, this.mLayoutParams);
    }
}
