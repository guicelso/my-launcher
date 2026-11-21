package com.dofun.carsetting.e.b;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateTS20.java */
/* JADX INFO: loaded from: classes.dex */
public class w extends a {
    boolean s = false;

    public w() {
        this.r = true;
        this.j = "V20";
        this.k = "V20";
        this.m = "/data/";
        this.c = "a83a180a-5e4a-11ed-b740-8be68328e96a-废弃";
        this.d = "7c9eecdf-b200-45dd-8971-c6666b928079-废弃";
        this.b = new String[]{"d9953a01-19f0-4d71-a384-4f1eabb5f05e", "d995cf1e-b216-bfc8-b2cb-c61be68b8733", "6cdfbbd8-9a00-11e7-8c64-33bdecde7c40", "be2c8eca-9626-11e9-a185-43ddb010914b", "bfc404de-51df-11e8-9fb0-c7638fd53eeb"};
        Log.d("SystemUpdateTS20", "SystemUpdateTS20:");
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        this.s = true;
        a(context, uVar, false, null);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        this.s = true;
        a(context, uVar, false, null);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0134 A[PHI: r8
      0x0134: PHI (r8v5 boolean) = (r8v4 boolean), (r8v4 boolean), (r8v4 boolean), (r8v4 boolean), (r8v4 boolean), (r8v4 boolean), (r8v9 boolean) binds: [B:10:0x00bc, B:12:0x00ce, B:14:0x00d6, B:15:0x00d8, B:17:0x00db, B:22:0x0116, B:23:0x0118] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:26:0x0138  */
    /* JADX WARN: Code duplicated, block: B:29:0x015d  */
    /* JADX WARN: Code duplicated, block: B:31:0x0165  */
    /* JADX WARN: Code duplicated, block: B:32:0x0181  */
    /* JADX WARN: Code duplicated, block: B:34:0x0185  */
    /* JADX WARN: Code duplicated, block: B:37:0x01a9  */
    /* JADX WARN: Instruction removed from duplicated block: B:31:0x0165, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:37:0x01a9, please report this as an issue */
    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z, com.dofun.carsetting.c.j jVar) {
        int i;
        Log.d("SystemUpdateTS20", "update:--------------start--------------");
        int i2 = 0;
        while (true) {
            i = 3;
            if (i2 >= this.b.length) {
                break;
            }
            uVar.write(40730, 3, 0, "rm -f " + this.m + this.b[i2]);
            i2++;
        }
        uVar.write(40730, 3, 0, "rm -f " + this.m + this.c + ".*");
        uVar.write(40730, 3, 0, "rm -f " + this.m + this.d + ".*");
        super.a(context, uVar, z, jVar);
        StringBuilder sb = new StringBuilder();
        sb.append("update: updateFiles=");
        sb.append(this.g.length);
        Log.d("SystemUpdateTS20", sb.toString());
        File[] fileArr = this.g;
        int length = fileArr.length;
        boolean z2 = false;
        File file = null;
        int i3 = 0;
        while (i3 < length) {
            File file2 = fileArr[i3];
            String name = file2.getName();
            Log.d("SystemUpdateTS20", "update:skip file::" + name);
            if (!name.isEmpty()) {
                String[] strArrSplit = name.split("_");
                if (name.toUpperCase().startsWith(this.k) && name.endsWith(".zip") && strArrSplit != null && strArrSplit.length >= i) {
                    if (strArrSplit[2].toUpperCase().startsWith("OS")) {
                        Log.d("SystemUpdateTS20", "update:skip file::" + name + " reson1: This is a OS zip file type.");
                        file = file2;
                    } else {
                        if (strArrSplit[2].toUpperCase().startsWith("APP")) {
                            Log.d("SystemUpdateTS20", "update:skip file::" + name + " reson1: This is a APP zip file type.");
                            z2 = true;
                        }
                        if (!this.n) {
                            if (this.l.contains(name)) {
                                Log.d("SystemUpdateTS20", "update:skip file::" + name + " reson3: use this file in Zip type update file.");
                            } else if (!this.o) {
                                Log.d("SystemUpdateTS20", "update0: cp " + file2.getAbsolutePath() + " " + this.m);
                                uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
                            } else {
                                Log.d("SystemUpdateTS20", "update0: cp " + file2.getAbsolutePath() + " " + this.m);
                                uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
                            }
                        } else if (this.l.contains(name)) {
                            Log.d("SystemUpdateTS20", "update:skip file::" + name + " reson3: use this file in Zip type update file.");
                        } else if (!this.o) {
                            Log.d("SystemUpdateTS20", "update0: cp " + file2.getAbsolutePath() + " " + this.m);
                            uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
                        } else {
                            Log.d("SystemUpdateTS20", "update0: cp " + file2.getAbsolutePath() + " " + this.m);
                            uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
                        }
                    }
                } else if (!this.n && name.startsWith(this.c)) {
                    Log.d("SystemUpdateTS20", "update:skip file::" + name + " reson2: use this file in Zip type update file.");
                } else if (this.l.contains(name)) {
                    Log.d("SystemUpdateTS20", "update:skip file::" + name + " reson3: use this file in Zip type update file.");
                } else if (!this.o && name.startsWith(this.d)) {
                    Log.d("SystemUpdateTS20", "update:skip file::" + name + " reson4: use this file in Zip type update file.");
                } else {
                    Log.d("SystemUpdateTS20", "update0: cp " + file2.getAbsolutePath() + " " + this.m);
                    uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
                }
            } else if (!this.n) {
                if (this.l.contains(name)) {
                    Log.d("SystemUpdateTS20", "update:skip file::" + name + " reson3: use this file in Zip type update file.");
                } else if (!this.o) {
                    Log.d("SystemUpdateTS20", "update0: cp " + file2.getAbsolutePath() + " " + this.m);
                    uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
                } else {
                    Log.d("SystemUpdateTS20", "update0: cp " + file2.getAbsolutePath() + " " + this.m);
                    uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
                }
            } else if (this.l.contains(name)) {
                Log.d("SystemUpdateTS20", "update:skip file::" + name + " reson3: use this file in Zip type update file.");
            } else if (!this.o) {
                Log.d("SystemUpdateTS20", "update0: cp " + file2.getAbsolutePath() + " " + this.m);
                uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
            } else {
                Log.d("SystemUpdateTS20", "update0: cp " + file2.getAbsolutePath() + " " + this.m);
                uVar.write(40730, 1, 0, "cp " + file2.getAbsolutePath() + " " + this.m);
            }
            i3++;
            i = 3;
        }
        if (!z2 && file != null) {
            Log.d("SystemUpdateTS20", "update1: cp " + file.getAbsolutePath() + " " + this.m);
            uVar.write(40730, 1, 0, "cp " + file + " " + this.m);
        }
        uVar.write(40730, 3, 0, "rm -rf /data/tw/theme");
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        uVar.write(40730, 1, 0, "sync");
        if (this.p) {
            uVar.write(65282, 1, 0);
        } else {
            uVar.write(65282, 0, 0);
        }
        Log.d("SystemUpdateTS20", "update:--------------end--------------");
    }
}
