package c;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public final class f implements d.g.a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ File f20a;

    public f(File file) {
        this.f20a = file;
    }

    @Override // d.g.a
    public final void a(String str) {
        d.c.c("LogModel", "uploadSystemLog %s", str);
    }

    @Override // d.g.a
    public final void b(String str) {
        d.c.b("LogModel", "uploadSystemLog %s", str);
    }

    @Override // d.g.a
    public final void c() {
        this.f20a.delete();
    }
}
