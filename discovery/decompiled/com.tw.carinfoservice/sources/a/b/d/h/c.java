package a.b.d.h;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public a f146a;

    public interface a {
    }

    public c(Context context) {
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return true;
    }

    public abstract View c();

    public View d(MenuItem menuItem) {
        return c();
    }

    public boolean e() {
        return false;
    }

    public void f(SubMenu subMenu) {
    }

    public boolean g() {
        return false;
    }

    public void h(a aVar) {
        if (this.f146a != null) {
            StringBuilder sbB = c.a.a.a.a.b("setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this ");
            sbB.append(getClass().getSimpleName());
            sbB.append(" instance while it is still in use somewhere else?");
            Log.w("ActionProvider(support)", sbB.toString());
        }
        this.f146a = aVar;
    }
}
