package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateT8O.java */
/* JADX INFO: loaded from: classes.dex */
public class l extends a {
    public l() {
        this.j = "V9.3.1";
        this.m = "/data/tw/metadata/";
        this.c = "22e42020-0a56-11e8-abef-631acfb3beab";
        this.b = new String[]{"2048977e-0a56-11e8-ad86-73d7e28f000b", "21a4f59a-0a56-11e8-91c9-971a30d57911", "237c9c6a-0a56-11e8-a7af-1b099ebd49aa", "24966194-0a56-11e8-8c57-9fb4c038a428", "2516e792-0a56-11e8-8f93-f3371fae028e", "ab6475a6-982d-11e4-887a-000c29ba27c0", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateT8O", "update:--------------start--------------");
        uVar.write(40730, 1, 0, "newfs_msdos -F 32 -O android -c 8 -L metadata /dev/block/by-name/metadata");
        uVar.write(40730, 1, 0, "mkdir " + this.m);
        uVar.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/metadata " + this.m);
        super.a(context, uVar, z);
        for (File file : this.g) {
            if (file.getName().toUpperCase().startsWith(this.j) && file.getName().endsWith(".zip")) {
                Log.d("SystemUpdateT8O", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateT8O", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateT8O", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
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
        Log.d("SystemUpdateT8O", "update:--------------end--------------");
    }
}
