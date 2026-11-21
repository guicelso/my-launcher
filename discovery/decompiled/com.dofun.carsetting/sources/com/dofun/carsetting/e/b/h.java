package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateT3Q.java */
/* JADX INFO: loaded from: classes.dex */
public class h extends a {
    public h() {
        this.j = "V13.0.0";
        this.m = "/data/";
        this.c = "66383bd4-f62e-11e9-88b3-7311c5e41deb";
        this.b = new String[]{"64ae2dbe-f62e-11e9-b6cb-ab4560ace70a", "656c6784-f62e-11e9-9a28-1b522723d4f7", "675692f4-f62e-11e9-9c17-1f1a35371022", "682af1ac-f62e-11e9-a746-abe8d8051457", "690156a2-f62e-11e9-bbff-cf691b0ad72a", "6a21bf36-f62e-11e9-aba4-b397c9f71b96", "6b4312a2-f62e-11e9-93c6-6fe4b363521c", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateT3Q", "update:--------------start--------------");
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
                Log.d("SystemUpdateT3Q", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateT3Q", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateT3Q", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
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
        Log.d("SystemUpdateT3Q", "update:--------------end--------------");
    }
}
