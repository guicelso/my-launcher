package android.support.v4.app;

import android.os.Bundle;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class ma extends android.arch.lifecycle.l implements a.b.c.a.b {
    private android.arch.lifecycle.e ba;
    private final Bundle mArgs;
    private final int mId;

    @Override // android.arch.lifecycle.l
    public void a(android.arch.lifecycle.m mVar) {
        super.a(mVar);
        this.ba = null;
    }

    void c(boolean z) {
        throw null;
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mId=");
        printWriter.print(this.mId);
        printWriter.print(" mArgs=");
        printWriter.println(this.mArgs);
        printWriter.print(str);
        printWriter.print("mLoader=");
        printWriter.println((Object) null);
        String str2 = str + "  ";
        throw null;
    }

    @Override // android.arch.lifecycle.l
    protected void k() {
        throw null;
    }

    void l() {
        android.arch.lifecycle.e eVar = this.ba;
    }

    @Override // android.arch.lifecycle.l
    protected void onActive() {
        throw null;
    }

    @Override // android.arch.lifecycle.l
    public void setValue(Object obj) {
        super.setValue(obj);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("LoaderInfo{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" #");
        sb.append(this.mId);
        sb.append(" : ");
        a.b.a.a.buildShortClassTag(null, sb);
        sb.append("}}");
        return sb.toString();
    }
}
