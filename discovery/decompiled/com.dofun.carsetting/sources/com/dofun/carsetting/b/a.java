package com.dofun.carsetting.b;

import android.os.SystemProperties;
import java.io.File;

/* JADX INFO: compiled from: PlatformConfig.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    private static a ae;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f134a;
    public boolean b;
    public boolean c;
    public boolean d;
    public boolean e;
    public boolean f;
    public boolean g;
    public boolean h;
    public boolean i;
    public int j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public int o;
    public boolean p;
    public String q;
    public com.dofun.carsetting.e.a.a r;
    public com.dofun.carsetting.e.b.a s;
    public String t;
    public String u;
    public String v;
    public String w;
    public String x;
    public String y;
    public String z;
    public String[] A = new String[0];
    public String[] B = new String[0];
    public boolean C = false;
    public boolean D = false;
    public boolean E = false;
    public boolean F = false;
    public boolean G = false;
    public boolean H = false;
    public boolean I = true;
    public boolean J = false;
    public boolean K = false;
    public boolean L = false;
    public boolean M = false;
    public boolean N = false;
    public boolean O = false;
    public boolean P = false;
    public boolean Q = false;
    public boolean R = false;
    public String S = "/system/etc/";
    public String T = "/system/priv-app/";
    public String U = "/";
    public String V = "/system";
    public String W = "/system_tw";
    public boolean X = true;
    public boolean Y = true;
    public boolean Z = false;
    public String aa = com.dofun.carsetting.f.b.aU();
    public boolean ab = false;
    public boolean ac = true;
    public boolean ad = false;

    private a() {
    }

    public static a a() {
        if (ae == null) {
            synchronized (a.class) {
                if (ae == null) {
                    ae = new a();
                }
            }
        }
        return ae;
    }

    public void b() {
        boolean z = true;
        this.f134a = true;
        this.b = com.dofun.carsetting.f.b.Q();
        this.c = false;
        this.d = !com.dofun.carsetting.f.b.T();
        int i = SystemProperties.getInt("persist.tw.bt.module", 2);
        if (com.dofun.carsetting.f.b.H()) {
            this.e = true;
        } else {
            this.e = com.dofun.carsetting.f.b.L() && i != 0;
        }
        this.f = true;
        this.g = true;
        this.h = !com.dofun.carsetting.f.b.ag();
        this.i = com.dofun.carsetting.f.b.L();
        this.j = com.dofun.carsetting.f.b.aq();
        this.k = com.dofun.carsetting.f.b.L();
        this.l = com.dofun.carsetting.f.b.I() || (com.dofun.carsetting.f.b.G() && new File("/dev/vipcore").exists());
        this.m = com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I();
        this.n = true;
        this.p = com.dofun.carsetting.f.b.A();
        this.q = com.dofun.carsetting.f.b.a();
        this.r = com.dofun.carsetting.f.b.V();
        this.s = com.dofun.carsetting.f.b.W();
        this.t = com.dofun.carsetting.f.b.X();
        this.u = com.dofun.carsetting.f.b.Y();
        this.v = com.dofun.carsetting.f.b.Z();
        this.w = com.dofun.carsetting.f.b.aa();
        this.x = com.dofun.carsetting.f.b.ab();
        this.y = com.dofun.carsetting.f.b.ac();
        this.z = com.dofun.carsetting.f.b.ad();
        this.A = com.dofun.carsetting.f.b.ae();
        this.B = com.dofun.carsetting.f.b.af();
        this.C = com.dofun.carsetting.f.b.ah();
        this.H = com.dofun.carsetting.f.b.ai();
        if (com.dofun.carsetting.f.b.a().contains("NO-MONITOR")) {
            this.E = false;
        } else {
            this.E = com.dofun.carsetting.f.b.ao();
        }
        this.F = com.dofun.carsetting.f.b.ap();
        this.J = !com.dofun.carsetting.f.b.aw();
        if (com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.l() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            this.o = 0;
        } else if (com.dofun.carsetting.f.b.n() || com.dofun.carsetting.f.b.m()) {
            this.o = 1;
        } else {
            this.o = 2;
        }
        this.G = com.dofun.carsetting.f.b.au() || com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J();
        this.I = com.dofun.carsetting.f.b.av();
        this.K = com.dofun.carsetting.f.b.az();
        this.L = com.dofun.carsetting.f.b.aC();
        this.M = com.dofun.carsetting.f.b.aA();
        this.N = com.dofun.carsetting.f.b.aB();
        this.O = com.dofun.carsetting.f.b.aG();
        this.P = com.dofun.carsetting.f.b.aK();
        this.Q = com.dofun.carsetting.f.b.aL();
        this.S = (com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.f()) ? "/system/etc/" : "/system_tw/etc/";
        this.T = (com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.f()) ? "/system_tw/priv-app/" : "/system/priv-app/";
        this.X = com.dofun.carsetting.f.b.aH();
        this.Y = com.dofun.carsetting.f.b.aI();
        this.ab = com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.J();
        this.Z = com.dofun.carsetting.f.b.aV();
        if (((com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.ba() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I()) && com.dofun.carsetting.f.b.au()) || com.dofun.carsetting.f.b.T() || com.dofun.carsetting.f.b.J()) {
            this.ac = false;
            int i2 = SystemProperties.getInt("persist.camera.reverse", 0);
            if (com.dofun.carsetting.f.b.p() && (i2 == 3 || i2 == 1 || i2 == 2)) {
                this.ac = true;
            }
        }
        if (!com.dofun.carsetting.f.b.I() && !com.dofun.carsetting.f.b.p() && !com.dofun.carsetting.f.b.o() && !com.dofun.carsetting.f.b.s() && !com.dofun.carsetting.f.b.G() && !com.dofun.carsetting.f.b.J()) {
            z = false;
        }
        this.ad = z;
    }
}
