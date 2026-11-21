package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateT3O.java */
/* JADX INFO: loaded from: classes.dex */
public class f extends a {
    public f() {
        this.j = "V8.1.0";
        this.m = "/data/tw/metadata/";
        this.c = "52c7b3fa-cad7-11e8-8188-57adc8d05072";
        this.b = new String[]{"50924514-cad7-11e8-bad6-4b580bf5a3e3", "51eb6df0-cad7-11e8-bf31-afdca4ac0826", "53adbfc6-cad7-11e8-8aa7-7bc0c0ab3341", "54e87a2a-cad7-11e8-94ca-3f47923cd20f", "55bbbee4-cad7-11e8-b6fc-0b42902a4160", "ab6475a6-982d-11e4-887a-000c29ba27c0", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateT3O", "update:--------------start--------------");
        uVar.write(40730, 1, 0, "newfs_msdos -F 32 -O android -c 8 -L metadata /dev/block/by-name/metadata");
        uVar.write(40730, 1, 0, "mkdir " + this.m);
        uVar.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/metadata /data/tw/metadata");
        super.a(context, uVar, z);
        for (File file : this.g) {
            if (file.getName().toUpperCase().startsWith(this.j) && file.getName().endsWith(".zip")) {
                Log.d("SystemUpdateT3O", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateT3O", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateT3O", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
            } else {
                uVar.write(40730, 1, 0, "cp " + file.getAbsolutePath() + " " + this.m);
            }
        }
        uVar.write(40730, 3, 0, "xcheck " + this.m + this.c);
        uVar.write(40730, 1, 0, "umount /data/tw/metadata");
        uVar.write(40730, 1, 0, "rm -r /data/tw/metadata");
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateT3O", "update:--------------end--------------");
    }
}
