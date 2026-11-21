package a.b.c;

import java.io.File;
import java.io.FileFilter;

/* JADX INFO: loaded from: classes.dex */
public final class b implements FileFilter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f36a;

    public b(String str) {
        this.f36a = str;
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        String name = file.getName();
        return (name.startsWith(this.f36a) || name.equals("MultiDex.lock")) ? false : true;
    }
}
