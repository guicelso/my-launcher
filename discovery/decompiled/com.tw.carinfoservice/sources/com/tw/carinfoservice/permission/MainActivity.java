package com.tw.carinfoservice.permission;

import a.b.d.a.a;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import com.tw.carinfoservice.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        int i = Build.VERSION.SDK_INT;
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        if (i >= 23) {
            try {
                String[] strArr = {"android.permission.CAMERA", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE"};
                ArrayList arrayList = new ArrayList();
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    boolean z = true;
                    if (i2 >= length) {
                        if (arrayList.size() > 0) {
                            a.c(this, strArr, 1);
                        }
                        if (!Settings.System.canWrite(this)) {
                            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS", Uri.parse("package:" + getPackageName()));
                            intent.addFlags(268435456);
                            startActivity(intent);
                            break;
                        }
                        break;
                    }
                    String str = strArr[i2];
                    if (i >= 23) {
                        Object obj = a.b.d.b.a.f49a;
                        if (str == null) {
                            throw new IllegalArgumentException("permission is null");
                        }
                        if (checkPermission(str, Process.myPid(), Process.myUid()) != 0) {
                            z = false;
                        }
                    }
                    if (!z) {
                        arrayList.add(str);
                    }
                    i2++;
                }
            } catch (Exception e) {
                Log.e("mxy", e.toString());
                finish();
                return;
            }
        }
        finish();
    }
}
