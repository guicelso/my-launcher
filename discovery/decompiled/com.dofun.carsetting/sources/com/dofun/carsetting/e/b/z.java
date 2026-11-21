package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateTS9.java */
/* JADX INFO: loaded from: classes.dex */
public class z extends a {
    public z() {
        this.j = "V11.1.1";
        this.m = "/data/";
        this.c = "427c4ecc-c1fc-11e8-b558-cb289b80f7d2";
        this.b = new String[]{"408930e4-c1fc-11e8-8a06-2b5b8f8a7e48", "4192eb4c-c1fc-11e8-8f83-a7a51df1443b", "43864958-c1fc-11e8-b9ce-0701f06faf8a", "448bd188-c1fc-11e8-9eef-335485763bba", "4581ed5c-c1fc-11e8-911f-fb46c642287f", "4671e21c-c1fc-11e8-ac0e-7bb4334d40c5", "476bfd60-c1fc-11e8-be59-2358640d8473"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateTS9", "update:--------------start--------------");
        for (int i = 0; i < this.b.length; i++) {
            uVar.write(40730, 3, 0, "rm -f " + this.m + this.b[i]);
        }
        uVar.write(40730, 3, 0, "rm -f " + this.m + this.c + ".*");
        try {
            context.deleteDatabase("tecarinfo.db");
        } catch (Exception unused) {
        }
        super.a(context, uVar, z);
        for (File file : this.g) {
            if (file.getName().toUpperCase().startsWith(this.j) && file.getName().endsWith(".zip")) {
                Log.d("SystemUpdateTS9", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateTS9", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateTS9", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
            } else {
                uVar.write(40730, 1, 0, "cp " + file.getAbsolutePath() + " " + this.m);
            }
        }
        uVar.write(40730, 3, 0, "xcheck /data/427c4ecc-c1fc-11e8-b558-cb289b80f7d2 /data/43864958-c1fc-11e8-b9ce-0701f06faf8a");
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        try {
            Thread.sleep(2000L);
        } catch (Exception unused2) {
        }
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateTS9", "update:--------------end--------------");
    }
}
