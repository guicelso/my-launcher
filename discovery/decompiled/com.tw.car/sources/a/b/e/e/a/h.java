package a.b.e.e.a;

import a.b.e.f.I;
import android.os.SystemClock;
import android.view.MenuItem;

/* JADX INFO: loaded from: classes.dex */
public class h implements I {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ i f219a;

    public h(i iVar) {
        this.f219a = iVar;
    }

    @Override // a.b.e.f.I
    public void a(k kVar, MenuItem menuItem) {
        this.f219a.h.removeCallbacksAndMessages(kVar);
    }

    @Override // a.b.e.f.I
    public void b(k kVar, MenuItem menuItem) {
        this.f219a.h.removeCallbacksAndMessages(null);
        int size = this.f219a.j.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (kVar == this.f219a.j.get(i).f223b) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1) {
            return;
        }
        int i2 = i + 1;
        this.f219a.h.postAtTime(new g(this, i2 < this.f219a.j.size() ? this.f219a.j.get(i2) : null, menuItem, kVar), kVar, SystemClock.uptimeMillis() + 200);
    }
}
