package android.support.v4.app;

import android.os.Build;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class y extends z {
    public static final InterfaceC0020u md;
    private static final Q me;
    private final boolean mf;
    private final CharSequence[] mg;
    private final Bundle mh;
    private final CharSequence mi;
    private final String mj;

    static {
        if (Build.VERSION.SDK_INT >= 20) {
            me = new A();
        } else if (Build.VERSION.SDK_INT >= 16) {
            me = new T();
        } else {
            me = new R();
        }
        md = new K();
    }

    @Override // android.support.v4.app.z
    public boolean qS() {
        return this.mf;
    }

    @Override // android.support.v4.app.z
    public CharSequence[] qT() {
        return this.mg;
    }

    @Override // android.support.v4.app.z
    public Bundle qU() {
        return this.mh;
    }

    @Override // android.support.v4.app.z
    public CharSequence qV() {
        return this.mi;
    }

    @Override // android.support.v4.app.z
    public String qW() {
        return this.mj;
    }
}
