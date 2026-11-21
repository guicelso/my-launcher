package c.b.a.e;

import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public int[] A;
    public int[] B;
    public int[] C;
    public int[] D;
    public String E;
    public String F;
    public int[] I;
    public String J;
    public String[] K;
    public int[] L;
    public String[] M;
    public int N;
    public int O;
    public int[] P;
    public int[] Q;
    public String[] R;
    public int S;
    public int[] T;
    public int U;
    public int V;
    public boolean W;
    public boolean X;
    public int[] d;
    public int[] f;
    public int[] g;
    public String[] h;
    public int[] i;
    public double[] n;
    public double[] o;
    public String[] p;
    public int[] q;
    public int r;
    public int[] s;
    public int u;
    public String[] w;
    public int y;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f396a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f397b = -1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f398c = 1281;
    public HashMap<Integer, int[]> e = new HashMap<>();
    public double j = 0.0d;
    public double k = 0.0d;
    public int l = 0;
    public String m = "";
    public int t = -1;
    public int v = 0;
    public int x = -1;
    public int z = 0;
    public int G = -1;
    public int H = 0;

    public a() {
        new HashMap();
        this.N = -1;
        this.O = -1;
        this.S = -1;
        this.U = 1;
        this.V = 1;
        this.W = false;
        this.X = true;
    }

    public int[] a() {
        return this.g;
    }

    public int b() {
        return this.f397b;
    }

    public int c() {
        return this.O;
    }

    public String d() {
        return this.m;
    }

    public int e() {
        return this.v;
    }

    public int f() {
        return this.N;
    }

    public int[] g() {
        return this.f;
    }

    public String toString() {
        StringBuilder sbB = c.a.a.a.a.b("CarConfigEntity{dataType_Receive=");
        sbB.append(this.f396a);
        sbB.append(", sendData_receive_position=");
        sbB.append(0);
        sbB.append(", sendDataType_receive_bit=");
        sbB.append(Arrays.toString((int[]) null));
        sbB.append(", features=");
        sbB.append(this.f397b);
        sbB.append(", dataType_What=");
        sbB.append(this.f398c);
        sbB.append(", values=");
        sbB.append(Arrays.toString(this.f));
        sbB.append('}');
        return sbB.toString();
    }
}
