package com.tw.core.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.tw.core.R;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0066t;
import com.tw.core.utils.C0067u;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends Activity {
    private static final String km = MainActivity.class.getSimpleName();

    private void oR() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 8388659;
        attributes.y = 100;
        attributes.width = 512;
        attributes.height = 300;
        getWindow().setAttributes(attributes);
    }

    public void installLauncher(View view) {
        File file = new File("sdcard/DoFun/launcher.apk");
        if (file.exists()) {
            C0067u.bR(this, file.getAbsolutePath(), new a(this));
        } else {
            Toast.makeText(this, "文件不存在 " + file.getAbsolutePath(), 0).show();
        }
    }

    public void installTWCore(View view) {
        File file = new File("sdcard/DoFun/TWCore.apk");
        if (file.exists()) {
            C0067u.bR(this, file.getAbsolutePath(), new b(this));
        } else {
            Toast.makeText(this, "文件不存在 " + file.getAbsolutePath(), 0).show();
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().setFlags(32, 32);
        setContentView(R.layout.layout_main_activity);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(km, "onNewIntent");
        oR();
    }

    public void showActiveTime(View view) {
        long jBE = C0064r.bE(this, "activateTime", 0L) * 1000;
        if (jBE > 0) {
            Toast.makeText(this, "激活时间 " + jBE + " " + C0066t.bN(jBE, C0066t.ac), 1).show();
        } else {
            Toast.makeText(this, "未激活", 0).show();
        }
    }
}
