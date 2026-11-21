package com.sprd.engineermode;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.os.EnvironmentCompat;
import android.widget.TextView;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class yulongversioninfo extends Activity {
    private static final String TAG = "cgversioninfo";
    private TextView txtViewlabel01;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws IllegalAccessException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version);
        this.txtViewlabel01 = (TextView) findViewById(R.id.version_id);
        SystemPropertiesProxy systemPropertiesProxy = SystemPropertiesProxy.INSTANCE;
        String cgversion = SystemPropertiesProxy.get("ro.board.cg_version", EnvironmentCompat.MEDIA_UNKNOWN);
        this.txtViewlabel01.setText(cgversion);
    }
}
