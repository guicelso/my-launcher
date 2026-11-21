package android.support.v4.app;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
class oa extends la {
    private final LoaderManagerImpl$LoaderViewModel ae;
    private final android.arch.lifecycle.e ba;

    oa(android.arch.lifecycle.e eVar, android.arch.lifecycle.r rVar) {
        this.ba = eVar;
        this.ae = LoaderManagerImpl$LoaderViewModel.a(rVar);
    }

    @Override // android.support.v4.app.la
    @Deprecated
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.ae.dump(str, fileDescriptor, printWriter, strArr);
    }

    @Override // android.support.v4.app.la
    public void l() {
        this.ae.l();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        a.b.a.a.buildShortClassTag(this.ba, sb);
        sb.append("}}");
        return sb.toString();
    }
}
