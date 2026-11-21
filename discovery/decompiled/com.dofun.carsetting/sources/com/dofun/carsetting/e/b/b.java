package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileFilter;

/* JADX INFO: compiled from: SystemUpdateT100.java */
/* JADX INFO: loaded from: classes.dex */
public class b extends a {
    public String s = "/twupdate/";

    public b() {
        this.j = "V13.1.1";
        this.k = "V13";
        this.m = "/data/";
        this.c = "10081c48-2534-11ee-ba05-bf92df42c74e";
        this.d = "100427d4-2534-11ee-85ff-d7e3f0627949";
        this.e = "1007a536-2534-11ee-b24b-33e8a8ca6466";
        this.b = new String[]{"10053e7e-2534-11ee-9b70-8bf029d6f83b", "1004d600-2534-11ee-a577-3f3ffaaf236e", "100d5f04-2534-11ee-9d6b-3727573204c9", "100c5ed4-2534-11ee-b93e-cf39b201bdf2", "100d6288-2534-11ee-8daf-3740c077b037", "100d4ade-2534-11ee-bb2a-937df0a170ed", "1007a536-2534-11ee-b24b-33e8a8ca6466", "1003bbe0-2534-11ee-ba48-67f09ee95b03", "6b4312a2-f62e-11e9-93c6-6fe4b363521c", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0", "bf6e894a-529e-11e8-8ed8-3bbb4fd620dc", "1e84d07e-458c-11e5-951c-000c29ba27c0"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        File[] fileArrListFiles;
        Log.d("SystemUpdateT100", "update:--------------start--------------");
        uVar.write(40730, 1, 0, "mount -o remount,rw /twupdate");
        uVar.write(40730, 1, 0, "chmod 777 twupdate");
        uVar.write(40730, 1, 0, "rwrite /sys/class/tw/gtp/dsppower 0");
        for (int i = 0; i < this.b.length; i++) {
            uVar.write(40730, 3, 0, "rm -f " + this.s + this.b[i]);
        }
        uVar.write(40730, 3, 0, "rm -f " + this.s + this.c + ".*");
        uVar.write(40730, 3, 0, "rm -f " + this.s + this.d + ".*");
        if (this.p && (fileArrListFiles = new File("/data/app").listFiles(new FileFilter() { // from class: com.dofun.carsetting.e.b.b.1
            @Override // java.io.FileFilter
            public boolean accept(File file) {
                if (file.isHidden() || !file.isDirectory()) {
                    return false;
                }
                return file.getName().startsWith("com.softwinner.dvr") || file.getName().startsWith("com.baony.tw360") || file.getName().startsWith("cn.cardoor.zt360") || file.getName().startsWith("com.megaview.avm") || file.getName().startsWith("com.percherry.roundadas");
            }
        })) != null) {
            for (File file : fileArrListFiles) {
                uVar.write(40730, 2, 0, "rm -r " + file.getAbsolutePath());
            }
        }
        super.a(context, uVar, z);
        for (File file2 : this.g) {
            if (file2.getName().toUpperCase().startsWith(this.j) && file2.getName().endsWith(".zip")) {
                Log.d("SystemUpdateT100", "update:skip file::" + file2.getName() + " reson1: This is a OS zip file type.");
            } else if (file2.getName().toUpperCase().startsWith(this.k) && file2.getName().endsWith(".zip")) {
                Log.d("SystemUpdateT100", "update:skip file::" + file2.getName() + " reson1: This is a APP zip file type.");
            } else if (this.n && file2.getName().startsWith(this.c)) {
                Log.d("SystemUpdateT100", "update:skip file::" + file2.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file2.getName())) {
                Log.d("SystemUpdateT100", "update:skip file::" + file2.getName() + " reson3: use this file in Zip type update file.");
            } else if (this.o && file2.getName().startsWith(this.d)) {
                Log.d("SystemUpdateT100", "update:skip file::" + file2.getName() + " reson4: use this file in Zip type update file.");
            } else {
                uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
            }
        }
        a(this.c + ".*");
        a(this.d + ".*");
        if (this.b != null) {
            for (int i2 = 0; i2 < this.b.length; i2++) {
                a(this.b[i2]);
            }
        }
        uVar.write(40730, 3, 0, "xcheck " + this.s + this.c);
        uVar.write(40730, 3, 0, "xcheck " + this.s + this.d);
        uVar.write(40730, 1, 0, "chmod 755 twupdate");
        uVar.write(40730, 1, 0, "mount -o remount,ro /twupdate");
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateT100", "update:--------------end--------------");
    }

    public void a(String str) {
        Log.d("SystemUpdateT100", "mvDataToTwUpdate: " + this.m + str + " " + this.s);
        this.f215a.write(40730, 1, 0, "mv " + this.m + str + " " + this.s);
    }
}
