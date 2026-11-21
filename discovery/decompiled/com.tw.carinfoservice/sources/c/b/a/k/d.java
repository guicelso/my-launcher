package c.b.a.k;

import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Process;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class d extends ContextWrapper {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final j f432a;

    public d(j jVar) {
        super(jVar.f441a);
        this.f432a = jVar;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public ApplicationInfo getApplicationInfo() {
        return this.f432a.d.applicationInfo;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public AssetManager getAssets() {
        return this.f432a.f443c.getAssets();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public String getPackageCodePath() {
        return this.f432a.d.applicationInfo.sourceDir;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public String getPackageName() {
        return this.f432a.d.packageName;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public String getPackageResourcePath() {
        j jVar = this.f432a;
        Objects.requireNonNull(jVar);
        int iMyUid = Process.myUid();
        ApplicationInfo applicationInfo = jVar.d.applicationInfo;
        return applicationInfo.uid == iMyUid ? applicationInfo.sourceDir : applicationInfo.publicSourceDir;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        return this.f432a.f443c;
    }
}
