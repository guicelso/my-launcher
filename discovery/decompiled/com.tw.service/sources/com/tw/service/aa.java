package com.tw.service;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
class aa {
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
    private int[] Gk = {R.id.door_b_41f, R.id.door_fl_41f, R.id.door_bl_41f, R.id.door_fr_41f, R.id.door_br_41f, R.id.door_tr_41f, R.id.door_cover_41f};
    private int[] Hk = {R.drawable.car_l, R.drawable.car_l_frontdoor, R.drawable.car_l_backdoor, R.drawable.car_l_r_frontdoor, R.drawable.car_l_r_backdoor, R.drawable.car_l_reartrunk, R.drawable.car_l_cover};
    private String[] Ik = {"car_l.png", "car_l_frontdoor.png", "car_l_backdoor.png", "car_l_r_frontdoor.png", "car_l_r_backdoor.png", "car_l_reartrunk.png", "car_l_cover.png"};
    private int[] Jk = {R.id.door_b_r_41f, R.id.door_fl_r_41f, R.id.door_bl_r_41f, R.id.door_fr_r_41f, R.id.door_br_r_41f, R.id.door_tr_r_41f, R.id.door_cover_r_41f};
    private int[] Kk = {R.drawable.car_r, R.drawable.car_r_r_frontdoor, R.drawable.car_r_r_backdoor, R.drawable.car_r_frontdoor, R.drawable.car_r_backdoor, R.drawable.car_r_reartrunk, R.drawable.car_r_cover};
    private String[] Lk = {"car_r.png", "car_r_r_frontdoor,png", "car_r_r_backdoor.png", "car_r_frontdoor.png", "car_r_backdoor.png", "car_r_reartrunk.png", "car_r_cover.png"};

    public aa(TWService tWService) {
        this.n = tWService;
        int i = 0;
        this.mView = ((LayoutInflater) tWService.getSystemService("layout_inflater")).inflate(R.layout.car_door_v2, (ViewGroup) null);
        this.mView.setOnClickListener(new Z(this, tWService));
        boolean zA = true;
        int i2 = 0;
        while (true) {
            int[] iArr = this.Gk;
            if (i2 >= iArr.length) {
                break;
            }
            ImageView imageView = (ImageView) this.mView.findViewById(iArr[i2]);
            StringBuilder sbE = b.a.a.a.a.e("/mnt/sdcard/.ResourcePicture/CarDoor/");
            sbE.append(this.Ik[i2]);
            zA = C0038b.a(imageView, sbE.toString());
            i2++;
        }
        int i3 = 0;
        while (true) {
            int[] iArr2 = this.Jk;
            if (i3 >= iArr2.length) {
                break;
            }
            ImageView imageView2 = (ImageView) this.mView.findViewById(iArr2[i3]);
            StringBuilder sbE2 = b.a.a.a.a.e("/mnt/sdcard/.ResourcePicture/CarDoor/");
            sbE2.append(this.Lk[i3]);
            zA = C0038b.a(imageView2, sbE2.toString());
            i3++;
        }
        Log.d("TWService", "lxw:initDoorView:useExtraResource:" + zA);
        if (!zA) {
            int i4 = 0;
            while (true) {
                int[] iArr3 = this.Gk;
                if (i4 >= iArr3.length) {
                    break;
                }
                ((ImageView) this.mView.findViewById(iArr3[i4])).setImageResource(this.Hk[i4]);
                i4++;
            }
            while (true) {
                int[] iArr4 = this.Jk;
                if (i >= iArr4.length) {
                    break;
                }
                ((ImageView) this.mView.findViewById(iArr4[i])).setImageResource(this.Kk[i]);
                i++;
            }
        }
        this.Hc = (WindowManager) tWService.getSystemService("window");
        this.mLayoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2002, 1320, 1);
    }

    static /* synthetic */ void a(aa aaVar) {
        if (aaVar.Ic) {
            aaVar.Hc.removeView(aaVar.mView);
            aaVar.Ic = false;
        }
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

    /* JADX WARN: Code duplicated, block: B:96:0x0226  */
    /* JADX WARN: Code duplicated, block: B:97:0x0236  */
    private void a(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        View viewFindViewById;
        View viewFindViewById2;
        View viewFindViewById3;
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
            if (i2 == 0) {
                if (this.Ic) {
                    this.Hc.removeView(this.mView);
                    this.Ic = false;
                    return;
                }
                return;
            }
            if (Settings.System.getInt(this.n.getApplicationContext().getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                if ((this.Bk & 1) == 1) {
                    this.mView.findViewById(R.id.door_fl_41f).setVisibility(0);
                    this.mView.findViewById(R.id.door_fl_r_41f).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.door_fl_41f).setVisibility(8);
                    this.mView.findViewById(R.id.door_fl_r_41f).setVisibility(8);
                }
                if ((this.Bk & 2) == 2) {
                    this.mView.findViewById(R.id.door_fr_41f).setVisibility(0);
                    viewFindViewById2 = this.mView.findViewById(R.id.door_fr_r_41f);
                    viewFindViewById2.setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.door_fr_41f).setVisibility(8);
                    viewFindViewById = this.mView.findViewById(R.id.door_fr_r_41f);
                    viewFindViewById.setVisibility(8);
                }
            } else {
                if ((this.Bk & 1) == 1) {
                    this.mView.findViewById(R.id.door_fr_41f).setVisibility(0);
                    this.mView.findViewById(R.id.door_fr_r_41f).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.door_fr_41f).setVisibility(8);
                    this.mView.findViewById(R.id.door_fr_r_41f).setVisibility(8);
                }
                if ((this.Bk & 2) == 2) {
                    this.mView.findViewById(R.id.door_fl_41f).setVisibility(0);
                    viewFindViewById2 = this.mView.findViewById(R.id.door_fl_r_41f);
                    viewFindViewById2.setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.door_fl_41f).setVisibility(8);
                    viewFindViewById = this.mView.findViewById(R.id.door_fl_r_41f);
                    viewFindViewById.setVisibility(8);
                }
            }
            if ((this.Bk & 4) == 4) {
                this.mView.findViewById(R.id.door_bl_41f).setVisibility(0);
                this.mView.findViewById(R.id.door_bl_r_41f).setVisibility(0);
            } else {
                this.mView.findViewById(R.id.door_bl_41f).setVisibility(8);
                this.mView.findViewById(R.id.door_bl_r_41f).setVisibility(8);
            }
            if ((this.Bk & 8) == 8) {
                this.mView.findViewById(R.id.door_br_41f).setVisibility(0);
                this.mView.findViewById(R.id.door_br_r_41f).setVisibility(0);
            } else {
                this.mView.findViewById(R.id.door_br_41f).setVisibility(8);
                this.mView.findViewById(R.id.door_br_r_41f).setVisibility(8);
            }
            int i3 = (this.Bk >> 4) & 3;
            if (i3 == 1 || i3 == 3) {
                this.mView.findViewById(R.id.door_tr_41f).setVisibility(0);
                this.mView.findViewById(R.id.door_tr_r_41f).setVisibility(0);
            } else {
                this.mView.findViewById(R.id.door_tr_41f).setVisibility(8);
                this.mView.findViewById(R.id.door_tr_r_41f).setVisibility(8);
            }
            if ((this.Bk >> 5) == 1) {
                this.mView.findViewById(R.id.door_cover_41f).setVisibility(0);
                this.mView.findViewById(R.id.door_cover_r_41f).setVisibility(0);
            } else {
                this.mView.findViewById(R.id.door_cover_41f).setVisibility(8);
                this.mView.findViewById(R.id.door_cover_r_41f).setVisibility(8);
            }
            if (Settings.System.getInt(this.n.getApplicationContext().getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                int i4 = this.Bk;
                if ((i4 & 1) == 1 || (i4 & 4) == 4) {
                    this.mView.findViewById(R.id.door_left_41f).setVisibility(0);
                    viewFindViewById3 = this.mView.findViewById(R.id.door_right_41f);
                } else {
                    this.mView.findViewById(R.id.door_right_41f).setVisibility(0);
                    viewFindViewById3 = this.mView.findViewById(R.id.door_left_41f);
                }
            } else {
                int i5 = this.Bk;
                if ((i5 & 1) == 1 || (i5 & 4) == 4) {
                    this.mView.findViewById(R.id.door_right_41f).setVisibility(0);
                    viewFindViewById3 = this.mView.findViewById(R.id.door_left_41f);
                } else {
                    this.mView.findViewById(R.id.door_left_41f).setVisibility(0);
                    viewFindViewById3 = this.mView.findViewById(R.id.door_right_41f);
                }
            }
            viewFindViewById3.setVisibility(8);
            if (!(Settings.System.getInt(this.n.getContentResolver(), "SYSTEM_DOOR2", 1) == 1) || this.Ic) {
                return;
            }
            this.Ic = true;
            this.Hc.addView(this.mView, this.mLayoutParams);
        }
    }
}
