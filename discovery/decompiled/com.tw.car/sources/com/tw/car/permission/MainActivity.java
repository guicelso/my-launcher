package com.tw.car.permission;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import c.b.a.a.a;
import c.b.a.a.b;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a aVar = new a(this);
        String[] strArr = {"android.permission.CAMERA", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE"};
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("   ");
            sb.append(!b.a(this, str));
            Log.e("ls", sb.toString());
            if (b.a(this, str)) {
                aVar.a(1);
            } else {
                arrayList.add(str);
            }
        }
        if (arrayList.size() > 0) {
            a.b.d.a.b.a(this, strArr, 1);
        }
        finish();
    }
}
