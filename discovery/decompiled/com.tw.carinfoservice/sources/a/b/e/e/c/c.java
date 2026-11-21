package a.b.e.e.c;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class c<T> extends d<T> {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Context f211b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Map<a.b.d.d.a.b, MenuItem> f212c;
    public Map<a.b.d.d.a.c, SubMenu> d;

    public c(Context context, T t) {
        super(t);
        this.f211b = context;
    }

    public final MenuItem i(MenuItem menuItem) {
        if (!(menuItem instanceof a.b.d.d.a.b)) {
            return menuItem;
        }
        a.b.d.d.a.b bVar = (a.b.d.d.a.b) menuItem;
        if (this.f212c == null) {
            this.f212c = new a.b.d.g.a();
        }
        MenuItem menuItem2 = this.f212c.get(menuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        j jVar = new j(this.f211b, bVar);
        this.f212c.put(bVar, jVar);
        return jVar;
    }

    public final SubMenu j(SubMenu subMenu) {
        if (!(subMenu instanceof a.b.d.d.a.c)) {
            return subMenu;
        }
        a.b.d.d.a.c cVar = (a.b.d.d.a.c) subMenu;
        if (this.d == null) {
            this.d = new a.b.d.g.a();
        }
        SubMenu subMenu2 = this.d.get(cVar);
        if (subMenu2 != null) {
            return subMenu2;
        }
        r rVar = new r(this.f211b, cVar);
        this.d.put(cVar, rVar);
        return rVar;
    }
}
