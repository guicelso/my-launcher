package com.dofun.carsetting.e.a;

import java.util.HashMap;

/* JADX INFO: compiled from: MCUUpdate.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    public HashMap<String, String> S;
    public HashMap<String, String> T;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected String f214a = "";
    protected String b = "";
    protected String c = "";
    protected String d = "";
    protected String e = "";
    protected String f = "";
    protected String g = "";
    protected String h = "";
    protected String i = "";
    protected String j = "";
    protected String k = "";
    protected String l = "";
    protected String m = "";
    protected String n = "";
    protected String o = "";
    protected String p = "";
    protected String q = "";
    protected String r = "";
    protected String s = "";
    protected String t = "";
    protected String u = "";
    protected String v = "";
    protected String w = "";
    protected String x = "";
    protected String y = "";
    protected String z = "";
    protected String A = "";
    protected String B = "";
    protected String C = "";
    protected String D = "";
    protected String E = "";
    protected String F = "";
    protected String G = "";
    protected String H = "";
    protected String I = "";
    protected String J = "";
    protected String K = "";
    protected String L = "";
    protected String M = "";
    protected String N = "";
    protected String O = "";
    protected String P = "";
    protected String Q = "";
    protected String R = "";

    public String a(int i, String str) {
        switch ((i & 28) >> 2) {
            case 1:
                return this.d;
            case 2:
                return this.e;
            case 3:
                return this.f;
            case 4:
                return this.g;
            case 5:
                return this.h;
            case 6:
                return this.i;
            case 7:
                return a().get(str);
            default:
                return this.c;
        }
    }

    public String a(int i) {
        if (i == 0) {
            return this.b;
        }
        return this.f214a;
    }

    public HashMap<String, String> a() {
        if (this.S == null) {
            this.S = new HashMap<>();
            this.S.put("0", this.j);
            this.S.put("1", this.k);
            this.S.put("2", this.l);
            this.S.put("3", this.m);
            this.S.put("5", this.n);
            this.S.put("6", this.o);
            this.S.put("7", this.p);
            this.S.put("8", this.q);
            this.S.put("9", this.r);
            this.S.put("A", this.s);
            this.S.put("B", this.t);
            this.S.put("C", this.u);
            this.S.put("D", this.v);
            this.S.put("E", this.w);
            this.S.put("F", this.x);
            this.S.put("G", this.y);
            this.S.put("H", this.z);
            this.S.put("I", this.A);
            this.S.put("J", this.B);
            this.S.put("K", this.C);
            this.S.put("L", this.D);
            this.S.put("M", this.E);
            this.S.put("N", this.F);
            this.S.put("O", this.G);
            this.S.put("P", this.H);
            this.S.put("Q", this.I);
            this.S.put("R", this.J);
            this.S.put("S", this.K);
            this.S.put("T", this.L);
            this.S.put("U", this.M);
            this.S.put("V", this.N);
            this.S.put("W", this.O);
            this.S.put("X", this.P);
            this.S.put("Y", this.Q);
            this.S.put("Z", this.R);
        }
        return this.S;
    }

    public HashMap<String, String> b() {
        if (this.T == null) {
            this.T = new HashMap<>();
            this.T.put("0", "AT32");
            this.T.put("1", "CN24");
            this.T.put("2", "FT030");
            this.T.put("3", "CMS32");
            this.T.put("5", "FT072");
            this.T.put("6", "6");
            this.T.put("7", "7");
            this.T.put("8", "8");
            this.T.put("9", "9");
            this.T.put("A", "A");
            this.T.put("B", "B");
            this.T.put("C", "C");
            this.T.put("D", "D");
            this.T.put("E", "E");
            this.T.put("F", "F");
            this.T.put("G", "G");
            this.T.put("H", "H");
            this.T.put("I", "I");
            this.T.put("J", "J");
            this.T.put("K", "K");
            this.T.put("L", "L");
            this.T.put("M", "M");
            this.T.put("N", "N");
            this.T.put("O", "O");
            this.T.put("P", "P");
            this.T.put("Q", "Q");
            this.T.put("R", "R");
            this.T.put("S", "S");
            this.T.put("T", "T");
            this.T.put("U", "U");
            this.T.put("V", "V");
            this.T.put("W", "W");
            this.T.put("X", "X");
            this.T.put("Y", "Y");
            this.T.put("Z", "Z");
        }
        return this.T;
    }
}
