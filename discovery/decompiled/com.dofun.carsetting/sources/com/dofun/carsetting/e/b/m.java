package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateT8P.java */
/* JADX INFO: loaded from: classes.dex */
public class m extends a {
    public m() {
        this.j = "V9.4.1";
        this.m = "/data/";
        this.c = "c23c0812-a146-11e9-948a-6f966dc3faf9";
        this.b = new String[]{"c02f24dc-a146-11e9-bb73-db85803ead9e", "c1434ea2-a146-11e9-8113-67e2f4bd95ae", "c37f80c8-a146-11e9-83b8-6f1eb50fd2c1", "c42a5e6c-a146-11e9-8c8d-43ea13a194ee", "c5759110-a146-11e9-a26c-c7232518a533", "ab6475a6-982d-11e4-887a-000c29ba27c0", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateT8P", "update:--------------start--------------");
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
                Log.d("SystemUpdateT8P", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateT8P", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateT8P", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
            } else {
                uVar.write(40730, 1, 0, "cp " + file.getAbsolutePath() + " " + this.m);
            }
        }
        uVar.write(40730, 3, 0, "xcheck /data/c23c0812-a146-11e9-948a-6f966dc3faf9 /data/c5759110-a146-11e9-a26c-c7232518a533");
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateT8P", "update:--------------end--------------");
    }
}
