package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileFilter;

/* JADX INFO: compiled from: SystemUpdateT527.java */
/* JADX INFO: loaded from: classes.dex */
public class i extends a {
    public String s = "/twupdate/";

    public i() {
        this.j = "V15.1.1";
        this.k = "V15";
        this.m = "/data/";
        this.c = "527008ee-4479-49ef-8437-cc0d33f2b401";
        this.d = "527e6675-1d0e-420d-80de-fde900fa103f";
        this.e = "527e3831-16b2-4a99-b716-fe3c3deda9c1";
        this.b = new String[]{"527e3831-16b2-4a99-b716-fe3c3deda9c1", "527356ca-3ca5-422a-9f27-bc240733ede0", "527dfdf9-da3a-4998-a685-fb71ee75da78", "5275ab1e-c047-487e-9840-77aee149db9a", "5279ed56-93d4-4bc6-8f37-fdf80d595d5f", "5278ac81-f8b2-49f6-9e79-75cfb0e8cbaf", "527285d3-244f-42ea-a95f-82d76c56e986", "6b4312a2-f62e-11e9-93c6-6fe4b363521c", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0", "bf6e894a-529e-11e8-8ed8-3bbb4fd620dc", "1e84d07e-458c-11e5-951c-000c29ba27c0", "527aa86a-4502-4869-9699-588b76669cc5"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        super.a(context, uVar);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        File[] fileArrListFiles;
        Log.d("SystemUpdateT527", "update:--------------start--------------");
        uVar.write(40730, 1, 0, "mount -o remount,rw /twupdate");
        uVar.write(40730, 1, 0, "chmod 777 twupdate");
        uVar.write(40730, 1, 0, "rwrite /sys/class/tw/gtp/dsppower 0");
        for (int i = 0; i < this.b.length; i++) {
            uVar.write(40730, 3, 0, "rm -f " + this.s + this.b[i]);
        }
        uVar.write(40730, 3, 0, "rm -f " + this.s + this.c + ".*");
        uVar.write(40730, 3, 0, "rm -f " + this.s + this.d + ".*");
        if (this.p && (fileArrListFiles = new File("/data/app").listFiles(new FileFilter() { // from class: com.dofun.carsetting.e.b.i.1
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
                Log.d("SystemUpdateT527", "update:skip file::" + file2.getName() + " reson1: This is a OS zip file type.");
            } else if (file2.getName().toUpperCase().startsWith(this.k) && file2.getName().endsWith(".zip")) {
                Log.d("SystemUpdateT527", "update:skip file::" + file2.getName() + " reson1: This is a APP zip file type.");
            } else if (this.n && file2.getName().startsWith(this.c)) {
                Log.d("SystemUpdateT527", "update:skip file::" + file2.getName() + " reson2: use this file in Zip type update file.");
            } else if (this.l.contains(file2.getName())) {
                Log.d("SystemUpdateT527", "update:skip file::" + file2.getName() + " reson3: use this file in Zip type update file.");
            } else if (this.o && file2.getName().startsWith(this.d)) {
                Log.d("SystemUpdateT527", "update:skip file::" + file2.getName() + " reson4: use this file in Zip type update file.");
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
        uVar.write(40730, 3, 0, "xcheck2 " + this.s + this.d);
        uVar.write(40730, 1, 0, "chmod 755 twupdate");
        uVar.write(40730, 1, 0, "mount -o remount,ro /twupdate");
        uVar.write(40730, 1, 0, "sync");
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        if (this.p) {
            uVar.write(65282, 1, 0, "xupdate");
        } else {
            uVar.write(65282, 0, 0, "xupdate");
        }
        Log.d("SystemUpdateT527", "update:--------------end--------------");
    }

    public void a(String str) {
        Log.d("SystemUpdateT527", "mvDataToTwUpdate: " + this.m + str + " " + this.s);
        this.f215a.write(40730, 1, 0, "mv " + this.m + str + " " + this.s);
    }
}
