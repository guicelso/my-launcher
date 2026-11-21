package com.tw.core.f;

import com.tw.core.utils.B;
import com.tw.core.utils.E;
import com.tw.core.utils.InterfaceC0059m;

/* JADX INFO: loaded from: classes.dex */
class l implements InterfaceC0059m {
    final /* synthetic */ g ix;

    l(g gVar) {
        this.ix = gVar;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        E.cJ(this.ix.io, this.ix.ip);
        B.ci("LogUploader", "rename file %s", this.ix.ip);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
        if (this.ix.io == null || !this.ix.io.exists()) {
            return;
        }
        B.ci("LogUploader", "rename file", this.ix.ip);
        E.cJ(this.ix.io, this.ix.ip);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        B.ci("LogUploader", "log upload result %s", str);
        boolean zDelete = this.ix.io.delete();
        boolean zDelete2 = this.ix.ir.delete();
        if (this.ix.in != null) {
            com.tw.core.a.b.en(this.ix.in);
        }
        B.ci("LogUploader", "%s delete result %s", this.ix.io.getName(), Boolean.valueOf(zDelete));
        B.ci("LogUploader", "%s delete result %s", this.ix.ir.getName(), Boolean.valueOf(zDelete2));
    }
}
