package a.b.d.h;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public b f161a;

    public interface a {
    }

    public interface b {
    }

    public e(Context context) {
    }

    public View a(MenuItem menuItem) {
        return ((a.b.e.e.a.o.a) this).f235b.onCreateActionView();
    }

    public void a(b bVar) {
        if (this.f161a != null && bVar != null) {
            StringBuilder sbA = c.a.a.a.a.a("setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this ");
            sbA.append(getClass().getSimpleName());
            sbA.append(" instance while it is still in use somewhere else?");
            Log.w("ActionProvider(support)", sbA.toString());
        }
        this.f161a = bVar;
    }

    public boolean a() {
        return true;
    }

    public boolean b() {
        return false;
    }
}
