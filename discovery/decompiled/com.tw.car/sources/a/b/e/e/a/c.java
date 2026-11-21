package a.b.e.e.a;

import android.content.Context;
import android.os.Build;
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

    public final MenuItem a(MenuItem menuItem) {
        if (!(menuItem instanceof a.b.d.d.a.b)) {
            return menuItem;
        }
        a.b.d.d.a.b bVar = (a.b.d.d.a.b) menuItem;
        if (this.f212c == null) {
            this.f212c = new a.b.d.g.b();
        }
        MenuItem menuItem2 = this.f212c.get(menuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        Context context = this.f211b;
        int i = Build.VERSION.SDK_INT;
        p pVar = new p(context, bVar);
        this.f212c.put(bVar, pVar);
        return pVar;
    }

    public final SubMenu a(SubMenu subMenu) {
        if (!(subMenu instanceof a.b.d.d.a.c)) {
            return subMenu;
        }
        a.b.d.d.a.c cVar = (a.b.d.d.a.c) subMenu;
        if (this.d == null) {
            this.d = new a.b.d.g.b();
        }
        SubMenu subMenu2 = this.d.get(cVar);
        if (subMenu2 != null) {
            return subMenu2;
        }
        B b2 = new B(this.f211b, cVar);
        this.d.put(cVar, b2);
        return b2;
    }
}
