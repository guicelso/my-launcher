package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateTS10_2_2.java */
/* JADX INFO: loaded from: classes.dex */
public class r extends a {
    public r() {
        this.j = "TS10.2";
        this.m = "/data/";
        this.c = "a33325e2-8a14-11ec-b513-abde2c6d3df0";
        this.e = "a10e0c32-8a14-11ec-9a3b-23be67c402bc";
        this.b = new String[]{"a10e0c32-8a14-11ec-9a3b-23be67c402bc", "a2191928-8a14-11ec-abc8-13ee0a7bad29", "a53d6b9a-8a14-11ec-8c67-7bed6d3425de", "a71a8002-9cbf-11ea-b64e-578022b8a84a", "a861215a-9cbf-11ea-bb71-ebcf1fc5a18c", "a922eac4-9cbf-11ea-bb71-a72d60d5d2f6", "aa2e7e2e-9cbf-11ea-bb71-fbb02866c44e", "a446c646-8a14-11ec-9589-b38f543ee515", "ac2e7e2e-9cbf-11ea-bb71-fbb02866c44e", "6cdfbbd8-9a00-11e7-8c64-33bdecde7c40", "be2c8eca-9626-11e9-a185-43ddb010914b", "bfc404de-51df-11e8-9fb0-c7638fd53eeb", "a34a385a-9cbf-11ea-bb71-a36a7d5faaf4"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateTS10_2_2", "update:--------------start--------------");
        for (int i = 0; i < this.b.length; i++) {
            uVar.write(40730, 3, 0, "rm -f " + this.m + this.b[i]);
        }
        uVar.write(40730, 3, 0, "rm -f " + this.m + this.c + ".*");
        super.a(context, uVar, z);
        File[] fileArr = this.g;
        int length = fileArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            File file = fileArr[i2];
            if (file.getName().toUpperCase().startsWith(this.j) && file.getName().endsWith(".zip")) {
                Log.d("SystemUpdateTS10_2_2", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateTS10_2_2", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateTS10_2_2", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
            } else {
                uVar.write(40730, 1, 0, "cp " + file.getAbsolutePath() + " " + this.m);
            }
        }
        uVar.write(40730, 3, 0, "xcheck " + this.m + this.c);
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateTS10_2_2", "update:--------------end--------------");
    }
}
