package com.dofun.carsetting.tw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class NaviActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = new Intent();
        intent.setClassName("com.dofun.carsetting", "com.dofun.carsetting.ui.MainActivity");
        intent.setFlags(536870912);
        intent.putExtra("carsetting.fragment", "NaviChooseFragment");
        startActivity(intent);
        finish();
    }
}
