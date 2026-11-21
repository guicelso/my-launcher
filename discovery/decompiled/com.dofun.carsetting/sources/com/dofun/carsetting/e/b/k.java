package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateT8N.java */
/* JADX INFO: loaded from: classes.dex */
public class k extends a {
    public k() {
        this.j = "V9.2.1";
        this.m = "/data/tw/metadata";
        this.c = "b278023e-7757-11e7-bb3a-07933d230b22";
        this.b = new String[]{"b0392f52-7757-11e7-aa02-e7ef54e1e194", "b1604ca8-7757-11e7-906d-3390eed0c0c2", "ab6475a6-982d-11e4-887a-000c29ba27c0", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateT8N", "update:--------------start--------------");
        uVar.write(40730, 1, 0, "newfs_msdos -F 32 -O android -c 8 -L metadata /dev/block/by-name/metadata");
        uVar.write(40730, 1, 0, "mkdir " + this.m);
        uVar.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/metadata " + this.m);
        super.a(context, uVar, z);
        for (File file : this.g) {
            if (file.getName().toUpperCase().startsWith(this.j) && file.getName().endsWith(".zip")) {
                Log.d("SystemUpdateT8N", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateT8N", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateT8N", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
            } else {
                uVar.write(40730, 1, 0, "cp " + file.getAbsolutePath() + " " + this.m);
            }
        }
        uVar.write(40730, 3, 0, "xcheck  " + this.m + this.c);
        uVar.write(40730, 1, 0, "umount /data/tw/metadata");
        uVar.write(40730, 1, 0, "rm -r /data/tw/metadata");
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateT8N", "update:--------------end--------------");
    }
}
