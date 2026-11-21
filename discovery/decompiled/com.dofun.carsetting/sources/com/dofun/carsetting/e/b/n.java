package com.dofun.carsetting.e.b;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.util.Log;
import com.softwinner.shared.FileInfo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: SystemUpdateTQ919.java */
/* JADX INFO: loaded from: classes.dex */
public class n extends a {
    boolean s = false;

    public n() {
        this.r = true;
        this.j = "V16.1";
        this.k = "V16";
        this.m = "/data/tw/";
        this.c = "a83a180a-5e4a-11ed-b740-8be68328e96a-废弃";
        this.d = "612515a8-e0fe-11ee-baee-9bfd2ac310a9";
        this.b = new String[]{"6cdfbbd8-9a00-11e7-8c64-33bdecde7c40", "be2c8eca-9626-11e9-a185-43ddb010914b", "bfc404de-51df-11e8-9fb0-c7638fd53eeb"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) throws Throwable {
        this.s = true;
        a(context, uVar, false, null);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) throws Throwable {
        this.s = true;
        a(context, uVar, false, null);
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z, com.dofun.carsetting.c.j jVar) throws Throwable {
        boolean z2;
        String line;
        Log.d("SystemUpdateTQ919", "update:--------------start--------------");
        for (int i = 0; i < this.b.length; i++) {
            uVar.write(40730, 3, 0, "rm -f " + this.m + this.b[i]);
        }
        uVar.write(40730, 3, 0, "rm -f " + this.m + this.c + ".*");
        uVar.write(40730, 3, 0, "rm -f " + this.m + this.d + ".*");
        super.a(context, uVar, z, jVar);
        long jA = a(SystemProperties.get("ro.tw.version"));
        if (this.h != null) {
            String name = this.h.getName();
            String strSubstring = name.substring(0, name.length() - 4);
            Log.d("SystemUpdateTQ919", "update: fileVersion=" + strSubstring + "," + jA + "," + name);
            if (a(strSubstring) > jA) {
                uVar.write(40730, 3, 0, "rm /data/tw/config");
                uVar.write(40730, 3, 0, "rm -rf /data/tw/theme");
                Intent intent = new Intent();
                intent.setFlags(268435456);
                intent.setComponent(new ComponentName("com.softwinner.update", "com.softwinner.update.ui.AbUpdate"));
                intent.putExtra("update_path", new FileInfo.FileUnit(this.h.getAbsolutePath(), 1));
                intent.putExtra("needWipeData", this.p);
                intent.putExtra("isAutoUpdate", this.s);
                context.startActivity(intent);
                Log.d("SystemUpdateTQ919", "update:skip file::" + this.h.getName() + " reson1: This is a OS zip file type.");
                return;
            }
            z2 = true;
        } else {
            z2 = false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("appZipUpdateFile:");
        sb.append(this.i != null);
        Log.d("SystemUpdateTQ919", sb.toString());
        if (this.i != null) {
            try {
                ZipFile zipFile = new ZipFile(this.i);
                Log.d("SystemUpdateTQ919", "appZipUpdateFile:" + this.i.getName() + "," + this.i.getAbsolutePath());
                com.dofun.carsetting.f.v.a(zipFile, "OS_Config", this.m);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.m);
                sb2.append("/OS_Config");
                BufferedReader bufferedReader = new BufferedReader(new FileReader(sb2.toString()));
                line = bufferedReader.readLine();
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e = e2;
                line = "";
            }
            long jA2 = a(line);
            Log.d("SystemUpdateTQ919", "update: osVersionTime=" + jA + ",packageOSTime=" + jA2);
            uVar.write(40730, 3, 0, "rm /data/tw/config");
            uVar.write(40730, 3, 0, "rm -rf /data/tw/theme");
            if (jA >= jA2) {
                SystemProperties.set("sys.tw.needWipeData", this.p ? "1" : "0");
                SystemProperties.set("sys.tw.xupdate_para", this.i.getAbsolutePath());
                SystemProperties.set("sys.tw.xupdate", "1");
            } else {
                Intent intent2 = new Intent();
                intent2.setFlags(268435456);
                intent2.setComponent(new ComponentName("com.softwinner.update", "com.softwinner.update.ui.AbUpdate"));
                intent2.putExtra("update_path", new FileInfo.FileUnit(this.h.getAbsolutePath(), 1));
                intent2.putExtra("needWipeData", this.p);
                intent2.putExtra("isAutoUpdate", this.s);
                intent2.putExtra("needUpdateApp", true);
                context.startActivity(intent2);
            }
        } else if (this.q != null && z2) {
            this.q.b();
        }
        Log.d("SystemUpdateTQ919", "update:--------------end--------------");
    }

    private long a(String str) {
        String[] strArrSplit = str.split("_");
        if (strArrSplit == null || strArrSplit.length <= 1) {
            return 0L;
        }
        try {
            return new SimpleDateFormat("yyyyMMdd.HHmmss").parse(strArrSplit[1]).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
