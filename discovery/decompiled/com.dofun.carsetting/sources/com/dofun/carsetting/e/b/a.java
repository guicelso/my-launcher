package com.dofun.carsetting.e.b;

import android.content.Context;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: SystemUpdate.java */
/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected com.dofun.carsetting.f.u f215a;
    public File h;
    public File i;
    public boolean p;
    public com.dofun.carsetting.c.j q;
    public String[] b = new String[0];
    public String c = "";
    public String d = "";
    public String e = "";
    public int f = 10;
    public File[] g = new File[0];
    public String j = "";
    public String k = "";
    public List<String> l = new ArrayList();
    public String m = "";
    public boolean n = false;
    public boolean o = false;
    private boolean s = false;
    public boolean r = false;

    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        this.f215a = uVar;
        a(context, uVar, false);
    }

    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z) {
        if (uVar != null) {
            this.f215a = uVar;
        }
        this.p = z;
        a();
        b();
    }

    public void a(Context context, com.dofun.carsetting.f.u uVar, boolean z, com.dofun.carsetting.c.j jVar) {
        if (uVar != null) {
            this.f215a = uVar;
        }
        this.p = z;
        this.q = jVar;
        a();
        b();
    }

    private static void a() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/data/tw/update_time_config");
            try {
                fileOutputStream.write(new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date()).getBytes());
                fileOutputStream.flush();
            } catch (Exception unused) {
            } finally {
                fileOutputStream.close();
            }
            FileUtils.setPermissions("/data/tw/update_time_config", 438, -1, -1);
        } catch (Exception unused2) {
        }
    }

    private void b() {
        for (File file : this.g) {
            if (file.getName().endsWith(".zip")) {
                String[] strArrSplit = file.getName().split("_");
                if (strArrSplit != null && strArrSplit.length >= 3) {
                    if (strArrSplit[2].toUpperCase().startsWith("OS")) {
                        this.h = a(this.h, file);
                    } else if (strArrSplit[2].toUpperCase().startsWith("APP")) {
                        this.i = a(this.i, file);
                    } else {
                        this.h = a(this.h, file);
                    }
                } else {
                    this.h = a(this.h, file);
                }
            }
        }
        if (this.r) {
            return;
        }
        try {
            Log.d("SystemUpdate", "prepareZipUpdateFile:osZipUpdateFile:" + this.h);
            if (this.h != null) {
                a(this.h, false);
            }
            Log.d("SystemUpdate", "prepareZipUpdateFile:appZipUpdateFile:" + this.i);
            if (this.i != null) {
                a(this.i, true);
                if (this.s && this.f215a != null) {
                    this.f215a.write(40730, 3, 0, "rm -rf " + this.i.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            Log.d("SystemUpdate", "prepareZipUpdateFile:" + e.getMessage());
        }
        if (this.h == null && this.i == null) {
            return;
        }
        for (String str : this.l) {
            if (str.startsWith(this.c)) {
                this.n = true;
            }
            if (str.startsWith(this.d)) {
                this.o = true;
            }
        }
    }

    private File a(File file, File file2) {
        if (file == null) {
            return file2;
        }
        if (file2.lastModified() - file.lastModified() <= 0) {
            return file;
        }
        Log.d("SystemUpdate", "updateZipFileIfNeed:new file:" + file2.getName() + " " + file2.lastModified());
        return file2;
    }

    private void a(File file, boolean z) {
        ZipFile zipFile;
        Log.d("SystemUpdate", "unZipUpdateFile: zip file:" + file.getName() + " fileTime:" + file.lastModified());
        try {
            zipFile = new ZipFile(file);
            try {
                try {
                    boolean z2 = true;
                    if (com.dofun.carsetting.f.v.a(zipFile, "OS_Config", this.m) && z) {
                        try {
                            String line = new BufferedReader(new FileReader(this.m + "/OS_Config")).readLine();
                            String[] strArrSplit = line.split("_");
                            if (strArrSplit != null && strArrSplit.length >= 3) {
                                float f = Float.parseFloat(strArrSplit[1]);
                                String str = SystemProperties.get("ro.tw.version");
                                String[] strArrSplit2 = str.split("_");
                                if (strArrSplit2 != null && strArrSplit2.length >= 3) {
                                    float f2 = Float.parseFloat(strArrSplit2[1]);
                                    if (TextUtils.equals(strArrSplit2[2], strArrSplit[2]) && f <= f2) {
                                        z2 = false;
                                    }
                                }
                                Log.d("SystemUpdate", "unZipUpdateFile: packageOSVersion=" + line + "," + str + "," + z2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (z2) {
                        for (int i = 0; i < this.f; i++) {
                            boolean zA = com.dofun.carsetting.f.v.a(zipFile, this.c + "." + i, this.m);
                            if (zA) {
                                Log.d("SystemUpdate", "unZipUpdateFile:generalSystemFiles:" + this.c + "." + i + " success:" + zA);
                                List<String> list = this.l;
                                StringBuilder sb = new StringBuilder();
                                sb.append(this.c);
                                sb.append(".");
                                sb.append(i);
                                list.add(sb.toString());
                            }
                        }
                        for (int i2 = 0; i2 < this.b.length; i2++) {
                            boolean zA2 = com.dofun.carsetting.f.v.a(zipFile, this.b[i2], this.m);
                            if (zA2) {
                                Log.d("SystemUpdate", "unZipUpdateFile:specialSystemFiles:" + this.b[i2] + " success:" + zA2);
                                this.l.add(this.b[i2]);
                            }
                        }
                    }
                    for (int i3 = 0; i3 < this.f; i3++) {
                        boolean zA3 = com.dofun.carsetting.f.v.a(zipFile, this.d + "." + i3, this.m);
                        if (zA3) {
                            Log.d("SystemUpdate", "unZipUpdateFile:generalSystemTWFiles:" + this.d + "." + i3 + " success:" + zA3);
                            List<String> list2 = this.l;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(this.d);
                            sb2.append(".");
                            sb2.append(i3);
                            list2.add(sb2.toString());
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (zipFile != null) {
                        try {
                            zipFile.close();
                        } catch (Exception unused) {
                        }
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                if (zipFile == null) {
                    return;
                }
            }
        } catch (Exception unused3) {
            zipFile = null;
        } catch (Throwable th2) {
            th = th2;
            zipFile = null;
        }
        try {
            zipFile.close();
        } catch (Exception unused4) {
        }
    }

    public void a(boolean z) {
        this.s = z;
    }
}
