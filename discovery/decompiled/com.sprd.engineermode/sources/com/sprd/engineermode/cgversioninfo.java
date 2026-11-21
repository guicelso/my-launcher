package com.sprd.engineermode;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/* JADX INFO: loaded from: classes4.dex */
public class cgversioninfo extends Activity {
    private static final String TAG = "cgversioninfo";
    private TextView txtViewlabel01;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version);
        this.txtViewlabel01 = (TextView) findViewById(R.id.version_id);
        this.txtViewlabel01.setText("T8861A_140519_22_user");
    }
}
