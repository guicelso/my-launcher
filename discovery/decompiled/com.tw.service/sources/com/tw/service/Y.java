package com.tw.service;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
class Y {
    private WindowManager Hc;
    private WindowManager.LayoutParams mLayoutParams;
    private View mView;
    final /* synthetic */ TWService n;
    private boolean Ic = false;
    private int Bk = 0;
    private boolean Ck = false;
    private boolean Dk = false;
    private boolean Ek = false;
    private boolean Fk = false;

    public Y(TWService tWService) {
        this.n = tWService;
        this.mView = ((LayoutInflater) tWService.getSystemService("layout_inflater")).inflate(R.layout.door, (ViewGroup) null);
        this.mView.setOnClickListener(new X(this, tWService));
        this.Hc = (WindowManager) tWService.getSystemService("window");
        this.mLayoutParams = new WindowManager.LayoutParams(316, 316, 0, 0, 2002, 40, 1);
    }

    private void a(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        int i = Settings.System.getInt(this.n.getContentResolver(), "SYSTEM_DOOR", 0);
        if ((i & 1) == 1) {
            this.Ck = true;
        }
        if ((i & 2) == 2) {
            this.Dk = true;
        }
        if ((i & 4) == 4) {
            this.Ek = true;
        }
        if ((i & 8) == 8) {
            this.Fk = true;
        }
        if (this.Ck) {
            z2 = z;
            z = z2;
        }
        if (this.Dk) {
            z4 = z3;
            z3 = z4;
        }
        if (this.Ek) {
            boolean z7 = z3;
            z3 = z;
            z = z7;
        }
        if (this.Fk) {
            boolean z8 = z4;
            z4 = z2;
            z2 = z8;
        }
        int i2 = z ? 1 : 0;
        if (z2) {
            i2 |= 2;
        }
        if (z3) {
            i2 |= 4;
        }
        if (z4) {
            i2 |= 8;
        }
        if (z5) {
            i2 |= 16;
        }
        if (z6) {
            i2 |= 32;
        }
        if (this.Bk != i2) {
            this.Bk = i2;
            int i3 = la.Tf;
            if (i2 != 0) {
                if (i3 != 1 && i3 == 2) {
                    this.n.mHandler.removeMessages(65286);
                }
                pb();
                return;
            }
            if (i3 == 1 || i3 != 2) {
                hide();
            } else {
                this.n.mHandler.removeMessages(65286);
                this.n.mHandler.sendEmptyMessageDelayed(65286, 1000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hide() {
        if (this.Ic) {
            this.Hc.removeView(this.mView);
            this.Ic = false;
        }
    }

    private void pb() {
        if ((this.Bk & 1) == 1) {
            this.mView.findViewById(R.id.door_fl).setVisibility(0);
        } else {
            this.mView.findViewById(R.id.door_fl).setVisibility(8);
        }
        if ((this.Bk & 2) == 2) {
            this.mView.findViewById(R.id.door_fr).setVisibility(0);
        } else {
            this.mView.findViewById(R.id.door_fr).setVisibility(8);
        }
        if ((this.Bk & 4) == 4) {
            this.mView.findViewById(R.id.door_bl).setVisibility(0);
        } else {
            this.mView.findViewById(R.id.door_bl).setVisibility(8);
        }
        if ((this.Bk & 8) == 8) {
            this.mView.findViewById(R.id.door_br).setVisibility(0);
        } else {
            this.mView.findViewById(R.id.door_br).setVisibility(8);
        }
        ((ImageView) this.mView.findViewById(R.id.door_b)).getDrawable().setLevel((this.Bk >> 4) & 3);
        if (!(Settings.System.getInt(this.n.getContentResolver(), "SYSTEM_DOOR2", 1) == 1) || this.Ic) {
            return;
        }
        this.Ic = true;
        this.Hc.addView(this.mView, this.mLayoutParams);
    }

    public void B(int i) {
        a((i & 128) == 128, (i & 64) == 64, (i & 32) == 32, (i & 16) == 16, (i & 8) == 8, (i & 4) == 4);
    }

    public void C(int i) {
        boolean z = (i & 128) == 128;
        a((i & 64) == 64, z, (i & 16) == 16, (i & 32) == 32, (i & 8) == 8, (i & 4) == 4);
    }

    public void D(int i) {
        a((i & 1) == 1, (i & 2) == 2, (i & 4) == 4, (i & 8) == 8, (i & 16) == 16, (i & 32) == 32);
    }

    public void E(int i) {
        boolean z = (i & 128) == 128;
        boolean z2 = (i & 64) == 64;
        boolean z3 = (i & 32) == 32;
        boolean z4 = (i & 16) == 16;
        a((z2 || (i & 1) != 1) ? z2 : true, (z || z3 || z4 || (i & 2) != 2) ? z : true, z4, z3, (i & 8) == 8, false);
    }
}
