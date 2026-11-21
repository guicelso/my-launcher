package com.dofun.carsetting.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.dofun.carsetting.R;
import com.dofun.carsetting.g.j;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;

/* JADX INFO: loaded from: classes.dex */
public class TestActivity extends Activity implements View.OnClickListener, j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private InfoPreference f416a;
    private InfoPreference b;
    private InfoPreference c;
    private SwitchPreference d;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.tw_freference_test);
        this.f416a = (InfoPreference) findViewById(R.id.ip_mobil_network);
        this.b = (InfoPreference) findViewById(R.id.ip_voice_icon);
        this.c = (InfoPreference) findViewById(R.id.ip_data_use);
        this.d = (SwitchPreference) findViewById(R.id.sp_rove);
        this.f416a.setOnPreferenceChangeListener(this);
        this.b.setOnPreferenceChangeListener(this);
        this.c.setOnPreferenceChangeListener(this);
        this.d.setOnPreferenceChangeListener(this);
        this.f416a.setValue("2");
        this.d.setValue(true);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        view.getTag();
    }

    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        Log.d("TestActivity", "onPreferenceChange: " + obj);
        int id = twPreference.getId();
        return (id == R.id.ip_data_use || id != R.id.ip_mobil_network) ? true : true;
    }
}
