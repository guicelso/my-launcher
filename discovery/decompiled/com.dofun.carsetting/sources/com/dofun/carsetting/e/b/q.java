package com.dofun.carsetting.e.b;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateTS10S_3_1.java */
/* JADX INFO: loaded from: classes.dex */
public class q extends a {
    public q() {
        this.j = "TS10S.3";
        this.k = "TS10S";
        this.m = "/data/";
        this.c = "aebc9002-1578-11ee-af19-93a0757cfabb";
        this.d = "a6410b3c-5e4a-11ed-a952-cb27d5d0623a";
        this.e = "ab09925e-1578-11ee-aff8-573641344079";
        this.b = new String[]{"ab09925e-1578-11ee-aff8-573641344079", "accd56f4-1578-11ee-afea-7f083581bc4d", "a1acf272-5f06-11ed-b232-0b3bb73f2dad", "a2c025e8-5f06-11ed-bf30-1bf24b59873", "a3a599f0-5f06-11ed-8a78-6bf149f66542", "ac2e7e2e-9cbf-11ea-bb71-fbb02866c44e", "a3cfff6c-5f06-11ed-b46b-6b38dfbdb4cb", "aff14fdc-5e4b-11ed-8ee3-c34ffa394fb9", "6cdfbbd8-9a00-11e7-8c64-33bdecde7c40", "be2c8eca-9626-11e9-a185-43ddb010914b", "bfc404de-51df-11e8-9fb0-c7638fd53eeb"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateTS10S_3_1", "update:--------------start--------------");
        for (int i = 0; i < this.b.length; i++) {
            uVar.write(40730, 3, 0, "rm -f " + this.m + this.b[i]);
        }
        uVar.write(40730, 3, 0, "rm -f " + this.m + this.c + ".*");
        uVar.write(40730, 3, 0, "rm -f " + this.m + this.d + ".*");
        super.a(context, uVar, z);
        File[] fileArr = this.g;
        int length = fileArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            File file = fileArr[i2];
            if (file.getName().toUpperCase().startsWith(this.j) && file.getName().endsWith(".zip")) {
                Log.d("SystemUpdateTS10S_3_1", "update:skip file::" + file.getName() + " reson1: This is a OS zip file type.");
            } else if (file.getName().toUpperCase().startsWith(this.k) && file.getName().endsWith(".zip")) {
                Log.d("SystemUpdateTS10S_3_1", "update:skip file::" + file.getName() + " reson1: This is a APP zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateTS10S_3_1", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateTS10S_3_1", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
            } else if (this.o && file.getName().startsWith(this.d)) {
                Log.d("SystemUpdateTS10S_3_1", "update:skip file::" + file.getName() + " reson4: use this file in Zip type update file.");
            } else {
                uVar.write(40730, 1, 0, "cp " + file.getAbsolutePath() + " " + this.m);
            }
        }
        uVar.write(40730, 3, 0, "xcheck " + this.m + this.c);
        uVar.write(40730, 3, 0, "xcheck " + this.m + this.d);
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        Log.d("SystemUpdateTS10S_3_1", "update:generalSystemFiles0 is: " + this.m + this.c + ".0");
        if (new File(this.m + this.c + ".0").exists()) {
            boolean zEquals = TextUtils.equals(SystemProperties.get("ro.boot.slot_suffix"), "_b");
            Log.d("SystemUpdateTS10S_3_1", "update:isSystemB: " + zEquals);
            if (zEquals) {
                uVar.write(40730, 3, 0, "bootctl set-active-boot-slot 0");
            }
        }
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateTS10S_3_1", "update:--------------end--------------");
    }
}
