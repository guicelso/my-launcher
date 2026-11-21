package com.tw.car;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import c.b.a.f;
import c.b.a.h;

/* JADX INFO: loaded from: classes.dex */
public class EQActivity extends Activity implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public h f466a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f467b = 10;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f468c = 10;
    public int d = 10;
    public int e = 10;
    public int f = 10;
    public boolean g = false;
    public int h = 0;
    public Handler mHandler = new f(this);

    public static /* synthetic */ void a(EQActivity eQActivity, int i, int i2, int i3) {
        SeekBar seekBar = (SeekBar) eQActivity.findViewById(i);
        seekBar.setEnabled(false);
        seekBar.setMax(i3);
        seekBar.setProgress(i2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.balance_add /* 2131230752 */:
                this.f466a.a(132, 4, 1);
                break;
            case R.id.balance_less /* 2131230753 */:
                this.f466a.a(132, 4, 2);
                break;
            case R.id.bass_add /* 2131230756 */:
                this.f466a.a(132, 5, 1);
                break;
            case R.id.bass_less /* 2131230757 */:
                this.f466a.a(132, 5, 2);
                break;
            case R.id.fader_add /* 2131230794 */:
                this.f466a.a(132, 3, 1);
                break;
            case R.id.fader_less /* 2131230795 */:
                this.f466a.a(132, 3, 2);
                break;
            case R.id.sound_b_f_mode_0 /* 2131230862 */:
                this.f466a.a(132, 11, 0);
                break;
            case R.id.sound_b_f_mode_1 /* 2131230863 */:
                this.f466a.a(132, 11, 1);
                break;
            case R.id.sound_b_f_mode_2 /* 2131230864 */:
                this.f466a.a(132, 11, 2);
                break;
            case R.id.sound_b_f_mode_3 /* 2131230865 */:
                this.f466a.a(132, 11, 3);
                break;
            case R.id.treblr_add /* 2131230894 */:
                this.f466a.a(132, 6, 1);
                break;
            case R.id.treblr_less /* 2131230895 */:
                this.f466a.a(132, 6, 2);
                break;
            case R.id.v_add /* 2131230901 */:
                this.f466a.a(132, 2, 1);
                break;
            case R.id.v_less /* 2131230902 */:
                this.f466a.a(132, 2, 2);
                break;
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_eq);
        this.f466a = h.a();
        this.f466a.addHandler("EQActivity", this.mHandler);
        try {
            this.f466a.a(144, 147, 0);
        } catch (Exception unused) {
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        this.f466a.removeHandler("EQActivity");
        this.f466a.close();
        this.f466a = null;
        super.onDestroy();
    }
}
