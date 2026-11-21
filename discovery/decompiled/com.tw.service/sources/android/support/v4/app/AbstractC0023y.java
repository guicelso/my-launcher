package android.support.v4.app;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* JADX INFO: renamed from: android.support.v4.app.y, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0023y {
    public abstract V beginTransaction();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract boolean executePendingTransactions();

    public abstract ComponentCallbacksC0013n findFragmentByTag(String str);

    public abstract List getFragments();

    public abstract boolean isStateSaved();

    public abstract boolean popBackStackImmediate();
}
