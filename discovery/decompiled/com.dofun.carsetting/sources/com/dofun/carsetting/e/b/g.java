package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateT3P.java */
/* JADX INFO: loaded from: classes.dex */
public class g extends a {
    public g() {
        this.j = "V8.1.1";
        this.m = "/data/tw/metadata/";
        this.c = "5a2f8d64-bf39-11e9-abb7-3733648aa093";
        this.e = "58599ee4-bf39-11e9-973f-6f536fb4350f";
        this.b = new String[]{"58599ee4-bf39-11e9-973f-6f536fb4350f", "597b623a-bf39-11e9-817d-dbcd8bba2407", "5b4d120c-bf39-11e9-a11f-efa32bb27489", "5c6420c2-bf39-11e9-a064-e39a4408aa27", "5d1809fc-bf39-11e9-bfdf-cf15c72d75d6", "ab6475a6-982d-11e4-887a-000c29ba27c0", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0", "3072f462-8747-11e5-bf6c-000c29ba27c0", "3155779c-8747-11e5-9db5-000c29ba27c0"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateT3P", "update:--------------start--------------");
        uVar.write(40730, 1, 0, "newfs_msdos -F 32 -O android -c 8 -L metadata /dev/block/by-name/metadata");
        uVar.write(40730, 1, 0, "mkdir " + this.m);
        uVar.write(40730, 1, 0, "mount -t vfat -o umask=000 /dev/block/by-name/metadata /data/tw/metadata");
        super.a(context, uVar, z);
        for (File file : this.g) {
            if (file.getName().toUpperCase().startsWith(this.j) && file.getName().endsWith(".zip")) {
                Log.d("SystemUpdateT3P", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateT3P", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateT3P", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
            } else {
                uVar.write(40730, 1, 0, "cp " + file.getAbsolutePath() + " " + this.m);
            }
        }
        uVar.write(40730, 3, 0, "xcheck " + this.m + this.c);
        StringBuilder sb = new StringBuilder();
        sb.append("umount ");
        sb.append(this.m);
        uVar.write(40730, 1, 0, sb.toString());
        uVar.write(40730, 1, 0, "rm -r " + this.m);
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateT3P", "update:--------------end--------------");
    }
}
