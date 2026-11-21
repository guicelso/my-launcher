package com.tw.car;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import c.b.a.c;
import c.b.a.d;
import c.b.a.e;
import c.b.a.h;

/* JADX INFO: loaded from: classes.dex */
public class DaciaFuelActivity extends Activity {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public float f464b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public float f465c;
    public float d;
    public float e;
    public AlertDialog f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public h f463a = null;
    public Handler mHandler = new c(this);
    public AlertDialog.Builder g = null;

    public void onClick(View view) {
        this.f = null;
        this.g = new AlertDialog.Builder(this);
        this.f = this.g.setTitle("Are you sure about the reset?").setNegativeButton("NO", new e(this)).setPositiveButton("YES", new d(this)).create();
        this.f.show();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dacia_fuel2);
        this.f463a = h.a();
        this.f463a.addHandler("DaciaFuelActivity", this.mHandler);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        this.f463a.close();
        this.f463a = null;
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.f463a.addHandler("DaciaFuelActivity", this.mHandler);
        this.f463a.a(144, 56, 0);
    }
}
