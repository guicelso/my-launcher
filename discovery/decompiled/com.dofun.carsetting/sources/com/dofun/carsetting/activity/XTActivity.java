package com.dofun.carsetting.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import com.dofun.carsetting.presenter.b;

/* JADX INFO: loaded from: classes.dex */
public abstract class XTActivity<P extends b> extends BaseActivity {
    public P b;
    public boolean c = false;

    @Override // com.dofun.carsetting.activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        b();
        super.onCreate(bundle);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // com.dofun.carsetting.activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.b.k();
        this.b = null;
    }

    @TargetApi(24)
    private void b() {
        if (Build.VERSION.SDK_INT >= 24) {
            this.c = isInMultiWindowMode();
        }
    }
}
