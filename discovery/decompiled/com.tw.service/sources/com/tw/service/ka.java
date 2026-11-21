package com.tw.service;

import android.view.WindowManager;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
class ka {
    private WindowManager Hc;
    private boolean Ic = false;
    private boolean Pk = false;
    private WindowManager.LayoutParams mLayoutParams;
    private TextView mView;

    public ka(TWService tWService, String str, int i) {
        this.mView = new TextView(tWService);
        this.mView.setGravity(17);
        this.mView.setText(str);
        this.mView.setTextColor(-65536);
        this.mView.setTextSize(1, i);
        this.mView.setBackgroundResource(R.drawable.panel_background);
        this.Hc = (WindowManager) tWService.getSystemService("window");
        this.mLayoutParams = new WindowManager.LayoutParams(-2, -2, 0, 0, 2002, 56, 1);
        this.mLayoutParams.gravity = 48;
    }

    public ka(TWService tWService, boolean z) {
        if (this.Pk) {
            return;
        }
        this.Hc = (WindowManager) tWService.getSystemService("window");
        this.mLayoutParams = new WindowManager.LayoutParams(-2, -2, 0, 0, 2002, 32776, -3);
        this.mLayoutParams.gravity = 48;
        this.mView = new TextView(tWService);
        String string = null;
        try {
            string = z ? tWService.getString(R.string.ui_activate) : tWService.getString(R.string.ui);
        } catch (Exception unused) {
        }
        this.mView.setText((string == null || string.length() == 0) ? "UI unauthorized, please contact the supplier" : string);
        this.mView.setTextSize(30.0f);
        this.mView.setTextColor(-65536);
        this.mView.setClickable(true);
        this.mView.setEnabled(true);
        this.mView.setOnTouchListener(new ja(this, tWService));
        this.mView.setBackgroundResource(R.drawable.panel_background);
    }

    public void hide() {
        if (this.Ic) {
            this.Hc.removeView(this.mView);
            this.Ic = false;
        }
    }

    public void l(boolean z) {
        this.Pk = z;
    }

    public void show() {
        if (this.Ic) {
            return;
        }
        this.Ic = true;
        this.Hc.addView(this.mView, this.mLayoutParams);
    }
}
