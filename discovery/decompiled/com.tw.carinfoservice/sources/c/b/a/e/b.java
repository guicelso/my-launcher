package c.b.a.e;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f399a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public String f400b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f401c;
    public Bitmap d;
    public int e;

    public String a() {
        return this.f399a;
    }

    public void b(int i) {
        this.f401c = i;
    }

    public void c(boolean z) {
    }

    public void d(int i) {
        this.e = i;
    }

    public void e(String str) {
        this.f399a = str;
    }

    public void f(boolean z) {
    }

    public String toString() {
        StringBuilder sbB = c.a.a.a.a.b("MusicInfo{songName='");
        sbB.append(this.f399a);
        sbB.append('\'');
        sbB.append(", singerName='");
        sbB.append(this.f400b);
        sbB.append('\'');
        sbB.append(", playState=");
        sbB.append(this.f401c);
        sbB.append(", albumBitmap=");
        sbB.append(this.d);
        sbB.append(", progress=");
        sbB.append(this.e);
        sbB.append(", currentPosition=");
        sbB.append((String) null);
        sbB.append('\'');
        sbB.append(", totalDuration=");
        sbB.append((String) null);
        sbB.append('}');
        return sbB.toString();
    }
}
