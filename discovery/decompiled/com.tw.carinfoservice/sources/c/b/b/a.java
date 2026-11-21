package c.b.b;

import android.os.SystemProperties;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static int f451a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static int f452b = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static int f453c = -1;
    public static int d;
    public static int e;

    public static int a() {
        int i;
        c.b.b.f.b bVarA = c.b.b.f.b.a();
        if (!c.b.b.f.a.f()) {
            boolean z = false;
            if (c.b.b.f.a.u() || c.b.b.f.a.v() || c.b.b.f.a.o() || c.b.b.f.a.r() || c.b.b.f.a.t() || c.b.b.f.a.s() || c.b.b.f.a.q() || c.b.b.f.a.p()) {
                if (c.b.b.f.a.d().startsWith("V11.1.2") || c.b.b.f.a.o() || c.b.b.f.a.t() || c.b.b.f.a.q() || c.b.b.f.a.p()) {
                    f451a = 0;
                } else {
                    f451a = 1;
                }
            } else if (c.b.b.f.a.k()) {
                f451a = 6;
            } else if (c.b.b.f.a.i() || c.b.b.f.a.e() || c.b.b.f.a.g() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                if (c.b.b.f.a.g()) {
                    int iWrite = bVarA.write(65523, 0, 0, "car_reverse\ntvd_type");
                    if (new File("/sys/devices/tp2815").exists() && (iWrite == 0 || iWrite == 6)) {
                        z = true;
                    }
                    c.b.b.f.a.f480c = z;
                    if (z) {
                        f451a = 16;
                    } else {
                        f451a = 4;
                    }
                    if (c.b.b.f.a.j() || c.b.b.f.a.h()) {
                        c.b.b.f.a.f480c = !SystemProperties.get("persist.front.camera.chip_path", "tp2850").equals("tp2850");
                        i = SystemProperties.getInt("persist.aux.camera_id", 4);
                    }
                } else {
                    f451a = 4;
                }
            }
            return f451a;
        }
        i = 5;
        f451a = i;
        return f451a;
    }

    /* JADX WARN: Code duplicated, block: B:79:0x0106  */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0148, code lost:
    
        if (r1 == 0) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x014d, code lost:
    
        if (r1 == 0) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00bb, code lost:
    
        if (d() < 9) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x001f, code lost:
    
        if (r0 != 1) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0103, code lost:
    
        if (r1 == 0) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x013b, code lost:
    
        if (c.b.b.f.a.b() < 9) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void b() {
        /*
            Method dump skipped, instruction units count: 412
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.b.a.b():void");
    }

    /* JADX WARN: Code duplicated, block: B:52:0x0094  */
    /* JADX WARN: Code duplicated, block: B:54:0x009b  */
    /* JADX WARN: Code duplicated, block: B:55:0x00a2  */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0089, code lost:
    
        if (c.b.b.f.a.h() == false) goto L57;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int c(c.b.b.f.b r4, android.content.Context r5) throws java.lang.Throwable {
        /*
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r0 = "TWCameraSignal"
            r1 = 0
            int r5 = android.provider.Settings.System.getInt(r5, r0, r1)
            r0 = 4
            r2 = 40733(0x9f1d, float:5.7079E-41)
            if (r5 == 0) goto L2d
            r3 = 1
            if (r5 == r3) goto La2
            r3 = 2
            if (r5 == r3) goto L9b
            r3 = 3
            if (r5 == r3) goto L29
            if (r5 == r0) goto L8b
            r4 = 5
            if (r5 == r4) goto L21
            goto Lab
        L21:
            java.lang.String r4 = "/sys/devices/tp2815/signal"
            int r4 = c.b.b.f.a.c(r4)
            goto La9
        L29:
            if (r4 == 0) goto L98
            goto L94
        L2d:
            boolean r5 = c.b.b.f.a.f()
            if (r5 != 0) goto La2
            boolean r5 = c.b.b.f.a.k()
            if (r5 == 0) goto L3b
            goto La2
        L3b:
            boolean r5 = c.b.b.f.a.u()
            if (r5 != 0) goto L9b
            boolean r5 = c.b.b.f.a.v()
            if (r5 == 0) goto L48
            goto L9b
        L48:
            boolean r5 = c.b.b.f.a.o()
            if (r5 != 0) goto L92
            boolean r5 = c.b.b.f.a.r()
            if (r5 != 0) goto L92
            boolean r5 = c.b.b.f.a.t()
            if (r5 != 0) goto L92
            boolean r5 = c.b.b.f.a.n()
            if (r5 != 0) goto L92
            boolean r5 = c.b.b.f.a.q()
            if (r5 != 0) goto L92
            boolean r5 = c.b.b.f.a.s()
            if (r5 == 0) goto L6d
            goto L92
        L6d:
            boolean r4 = c.b.b.f.a.i()
            if (r4 != 0) goto L8b
            boolean r4 = c.b.b.f.a.e()
            if (r4 != 0) goto L8b
            boolean r4 = c.b.b.f.a.g()
            if (r4 != 0) goto L8b
            boolean r4 = c.b.b.f.a.j()
            if (r4 != 0) goto L8b
            boolean r4 = c.b.b.f.a.h()
            if (r4 == 0) goto Lab
        L8b:
            java.lang.String r4 = "/sys/devices/tp2850/signal"
            int r4 = c.b.b.f.a.c(r4)
            goto La9
        L92:
            if (r4 == 0) goto L98
        L94:
            int r1 = r4.write(r2)
        L98:
            c.b.b.a.f452b = r1
            goto Lab
        L9b:
            java.lang.String r4 = "sys.camera.signal"
            int r4 = android.os.SystemProperties.getInt(r4, r1)
            goto La9
        La2:
            int r5 = c.b.b.a.f451a
            int r5 = r5 - r0
            int r4 = r4.write(r2, r5)
        La9:
            c.b.b.a.f452b = r4
        Lab:
            int r4 = c.b.b.a.f452b
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.b.a.c(c.b.b.f.b, android.content.Context):int");
    }

    public static int d() {
        if (c.b.b.f.a.i() || c.b.b.f.a.e() || c.b.b.f.a.g()) {
            f453c = c.b.b.f.a.b();
        }
        return f453c;
    }
}
