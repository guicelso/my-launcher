package android.support.v4.app;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
class LoaderManagerImpl$LoaderViewModel extends android.arch.lifecycle.o {
    private static final android.arch.lifecycle.p na = new na();
    private a.b.c.b.p mLoaders = new a.b.c.b.p(10);

    LoaderManagerImpl$LoaderViewModel() {
    }

    static LoaderManagerImpl$LoaderViewModel a(android.arch.lifecycle.r rVar) {
        return (LoaderManagerImpl$LoaderViewModel) new android.arch.lifecycle.q(rVar, na).b(LoaderManagerImpl$LoaderViewModel.class);
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mLoaders.size() > 0) {
            printWriter.print(str);
            printWriter.println("Loaders:");
            String str2 = str + "    ";
            if (this.mLoaders.size() <= 0) {
                return;
            }
            ma maVar = (ma) this.mLoaders.valueAt(0);
            printWriter.print(str);
            printWriter.print("  #");
            printWriter.print(this.mLoaders.keyAt(0));
            printWriter.print(": ");
            printWriter.println(maVar.toString());
            maVar.dump(str2, fileDescriptor, printWriter, strArr);
            throw null;
        }
    }

    void l() {
        int size = this.mLoaders.size();
        for (int i = 0; i < size; i++) {
            ((ma) this.mLoaders.valueAt(i)).l();
        }
    }

    @Override // android.arch.lifecycle.o
    protected void m() {
        int size = this.mLoaders.size();
        a.b.c.b.p pVar = this.mLoaders;
        if (size <= 0) {
            pVar.clear();
        } else {
            ((ma) pVar.valueAt(0)).c(true);
            throw null;
        }
    }
}
