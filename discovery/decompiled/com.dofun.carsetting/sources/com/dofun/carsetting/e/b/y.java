package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateTS8.java */
/* JADX INFO: loaded from: classes.dex */
public class y extends a {
    public y() {
        this.j = "V10.5.5";
        this.m = "/data/tw/metadata/";
        this.c = "72bcb672-3eed-11e8-9400-2be03dc87d31";
        this.b = new String[]{"70c50004-3eed-11e8-b9de-6bee9dfc2ac8", "71cc15dc-3eed-11e8-ab81-df8b0cfc9c58", "73a96832-3eed-11e8-bc32-73cda6ec0a5a", "74a17496-3eed-11e8-bb73-5b4f82725d08", "75e51466-3eed-11e8-852d-8f773e6045a6", "ab6475a6-982d-11e4-887a-000c29ba27c0"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        Log.d("SystemUpdateTS8", "update:--------------start--------------");
        for (int i = 0; i < this.b.length; i++) {
            uVar.write(40730, 3, 0, "rm -f " + this.m + this.b[i]);
        }
        uVar.write(40730, 3, 0, "rm -f " + this.m + this.c + ".*");
        super.a(context, uVar, z);
        try {
            context.deleteDatabase("tecarinfo.db");
        } catch (Exception unused) {
        }
        uVar.write(40730, 1, 0, "newfs_msdos -F 32 -O android -c 8 -L metadata /dev/block/platform/sdio_emmc/by-name/metadata");
        uVar.write(40730, 1, 0, "mkdir " + this.m);
        uVar.write(40730, 1, 0, "mount -t vfat /dev/block/platform/sdio_emmc/by-name/metadata " + this.m);
        File[] fileArr = this.g;
        int length = fileArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            File file = fileArr[i2];
            if (file.getName().toUpperCase().startsWith(this.j) && file.getName().endsWith(".zip")) {
                Log.d("SystemUpdateTS8", "update:skip file::" + file.getName() + " reson1: This is a zip file type.");
            } else if (this.n && file.getName().startsWith(this.c)) {
                Log.d("SystemUpdateTS8", "update:skip file::" + file.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file.getName())) {
                Log.d("SystemUpdateTS8", "update:skip file::" + file.getName() + " reson3: use this file in Zip type update file.");
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
        uVar.write(65282, 0, 0, "xupdate");
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateTS8", "update:--------------end--------------");
    }
}
