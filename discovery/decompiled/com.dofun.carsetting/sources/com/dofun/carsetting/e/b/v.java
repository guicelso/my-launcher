package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateTS18_US.java */
/* JADX INFO: loaded from: classes.dex */
public class v extends a {
    public v() {
        this.j = "TS18.1.2";
        this.m = "/data/";
        this.c = "18a3c26e-9cbf-11ea-bb71-d76aba25355c";
        this.e = "189e7e2e-9cbf-11ea-bb71-fbb02866c44e";
        this.b = new String[]{"18a3c26e-9cbf-11ea-bb71-476391092b23", "18a3c26e-9cbf-11ea-bb71-fbb02866c44e", "18a3c26e-9cbf-11ea-bb71-ebcf1fc5a18c", "184a8002-9cbf-11ea-b64e-578022b8a84a", "1862eac4-9cbf-11ea-bb71-a72d60d5d2f6", "184a8002-9cbf-22ea-b64e-578022b8a84a", "187e7e2e-9cbf-11ea-bb71-fbb02866c44e", "189e7e2e-9cbf-11ea-bb71-fbb02866c44e", "18ae7e2e-9cbf-11ea-bb71-fbb02866c44e", "6cdfbbd8-9a00-11e7-8c64-33bdecde7c40", "bfc404de-51df-11e8-9fb0-c7638fd53eeb", "be2c8eca-9626-11e9-a185-43ddb010914b"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateTS18_US", "update:--------------start--------------");
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
                Log.d("SystemUpdateTS18_US", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateTS18_US", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateTS18_US", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
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
        Log.d("SystemUpdateTS18_US", "update:--------------end--------------");
    }
}
