package android.support.v4.n;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class a extends h {
    final /* synthetic */ e oc;

    a(e eVar) {
        this.oc = eVar;
    }

    @Override // android.support.v4.n.h
    protected void rQ() {
        this.oc.clear();
    }

    @Override // android.support.v4.n.h
    protected Object rR(int i, int i2) {
        return this.oc.of[(i << 1) + i2];
    }

    @Override // android.support.v4.n.h
    protected Map rS() {
        return this.oc;
    }

    @Override // android.support.v4.n.h
    protected int rT() {
        return this.oc.oe;
    }

    @Override // android.support.v4.n.h
    protected int rU(Object obj) {
        return this.oc.sc(obj);
    }

    @Override // android.support.v4.n.h
    protected int rV(Object obj) {
        return this.oc.sd(obj);
    }

    @Override // android.support.v4.n.h
    protected void rW(Object obj, Object obj2) {
        this.oc.put(obj, obj2);
    }

    @Override // android.support.v4.n.h
    protected void rX(int i) {
        this.oc.se(i);
    }

    @Override // android.support.v4.n.h
    protected Object rY(int i, Object obj) {
        return this.oc.sa(i, obj);
    }
}
