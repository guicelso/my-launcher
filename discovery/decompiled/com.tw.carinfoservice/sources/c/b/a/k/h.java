package c.b.a.k;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class h implements Runnable {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ a f435b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ i f436c;

    public h(i iVar, a aVar) {
        this.f436c = iVar;
        this.f435b = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        b.a("ThemeManager", "theme switch start. size = %s", Integer.valueOf(this.f436c.f439c.size()));
        List<c> list = this.f436c.f439c;
        Collections.sort(list, new i.c(null));
        Iterator<c> it = list.iterator();
        while (it.hasNext()) {
            it.next().c(this.f435b);
        }
        Collections.sort(list, new i.d(null));
        i iVar = this.f436c;
        a aVar = this.f435b;
        Objects.requireNonNull(iVar);
        b.a("ThemeManager", "notifyThemeSwitching: start", new Object[0]);
        try {
            Iterator<c> it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                c next = it2.next();
                b.a("ThemeManager", "notifyThemeSwitching: switch! %s", next);
                if (!next.d(aVar)) {
                    b.a("ThemeManager", "notifyThemeSwitching: switch fail! %s", next);
                }
                z = true;
                break;
            }
        } catch (Exception e) {
            StringBuilder sbB = c.a.a.a.a.b("notifyThemeSwitching:");
            sbB.append(e.getMessage());
            b.a("ThemeManager", sbB.toString(), new Object[0]);
        }
        b.a("ThemeManager", "notifyThemeSwitching: end", new Object[0]);
        Collections.sort(list, new i.b(null));
        i iVar2 = this.f436c;
        a aVar2 = this.f435b;
        boolean z2 = !z;
        Objects.requireNonNull(iVar2);
        Iterator<c> it3 = list.iterator();
        while (it3.hasNext()) {
            it3.next().f(aVar2, z2);
        }
        b.a("ThemeManager", "theme switch finshed. size = %s", Integer.valueOf(this.f436c.f439c.size()));
    }
}
