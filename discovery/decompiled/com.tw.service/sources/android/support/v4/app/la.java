package android.support.v4.app;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public abstract class la {
    public static la g(android.arch.lifecycle.e eVar) {
        return new oa(eVar, ((android.arch.lifecycle.s) eVar).a());
    }

    @Deprecated
    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract void l();
}
