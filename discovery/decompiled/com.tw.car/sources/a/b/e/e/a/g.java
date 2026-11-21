package a.b.e.e.a;

import android.view.MenuItem;

/* JADX INFO: loaded from: classes.dex */
public class g implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ i.a f216a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MenuItem f217b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ k f218c;
    public final /* synthetic */ h d;

    public g(h hVar, i.a aVar, MenuItem menuItem, k kVar) {
        this.d = hVar;
        this.f216a = aVar;
        this.f217b = menuItem;
        this.f218c = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        i.a aVar = this.f216a;
        if (aVar != null) {
            this.d.f219a.B = true;
            aVar.f223b.a(false);
            this.d.f219a.B = false;
        }
        if (this.f217b.isEnabled() && this.f217b.hasSubMenu()) {
            this.f218c.a(this.f217b, 4);
        }
    }
}
