package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateT5Q.java */
/* JADX INFO: loaded from: classes.dex */
public class j extends a {
    com.dofun.carsetting.f.u s;

    public j() {
        this.j = "T5Q";
        this.m = "/data/";
        this.c = a();
        this.b = new String[]{"c40253b8-48bd-11ea-ab28-5b39b59898aa", "c550f72e-48bd-11ea-9c17-ff0442326b06", "c40253b8-37ac-11ea-ab28-5b39b59898aa", "c550f72e-37ac-11ea-9c17-ff0442326b06", "c40253b8-269b-11ea-ab28-5b39b59898aa", "c550f72e-269b-11ea-9c17-ff0442326b06", "c40253b8-48bd-12fc-ab28-5b39b59898aa", "c550f72e-48bd-12fc-9c17-ff0442326b06", "c40253b8-59ce-11ea-ab28-5b39b59898aa", "c550f72e-59ce-11ea-9c17-ff0442326b06", "690156a2-f62e-11e9-bbff-cf691b0ad72a", "6a21bf36-f62e-11e9-aba4-b397c9f71b96", "6b4312a2-f62e-11e9-93c6-6fe4b363521c", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0", "bf6e894a-529e-11e8-8ed8-3bbb4fd620dc", "bfc404de-51df-11e8-9fb0-c7638fd53eeb"};
    }

    public String a() {
        if (com.dofun.carsetting.f.b.g() || com.dofun.carsetting.f.b.k()) {
            this.c = "c640185e-48bd-11ea-b77d-f3aedc937344";
            if (new File("/data/c640185e-48bd-11ea-b77d-f3aedc937344.0").exists() && this.s != null) {
                this.s.write(40730, 3, 0, "rm -r /sdcard/360");
            }
        } else if (com.dofun.carsetting.f.b.h() || com.dofun.carsetting.f.b.k()) {
            this.c = "c640185e-37ac-11ea-b77d-f3aedc937344";
            if (new File("/data/c640185e-37ac-11ea-b77d-f3aedc937344.0").exists() && this.s != null) {
                this.s.write(40730, 3, 0, "rm -r /sdcard/360");
            }
        } else if (com.dofun.carsetting.f.b.i()) {
            this.c = "c640185e-269b-11ea-b77d-f3aedc937344";
            if (new File("/data/c640185e-269b-11ea-b77d-f3aedc937344.0").exists() && this.s != null) {
                this.s.write(40730, 3, 0, "rm -r /sdcard/360");
            }
        } else if (com.dofun.carsetting.f.b.j()) {
            this.c = "c640185e-48bd-12fc-b77d-f3aedc937344";
            if (new File("/data/c640185e-48bd-12fc-b77d-f3aedc937344.0").exists() && this.s != null) {
                this.s.write(40730, 3, 0, "rm -r /sdcard/360");
            }
        } else {
            this.c = "c640185e-59ce-11ea-b77d-f3aedc937344";
            if (new File("/data/c640185e-59ce-11ea-b77d-f3aedc937344.0").exists() && this.s != null) {
                this.s.write(40730, 3, 0, "rm -r /sdcard/360");
            }
        }
        return this.c;
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        this.s = uVar;
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        this.s = uVar;
        Log.d("SystemUpdateT5Q", "update:--------------start--------------");
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
                Log.d("SystemUpdateT5Q", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateT5Q", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateT5Q", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
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
        Log.d("SystemUpdateT5Q", "update:--------------end--------------");
    }
}
