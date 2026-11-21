package a.b.e.e.a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class B extends v implements SubMenu {
    public B(Context context, a.b.d.d.a.c cVar) {
        super(context, cVar);
    }

    @Override // android.view.SubMenu
    public void clearHeader() {
        ((a.b.d.d.a.c) this.f213a).clearHeader();
    }

    @Override // android.view.SubMenu
    public MenuItem getItem() {
        return a(((a.b.d.d.a.c) this.f213a).getItem());
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(int i) {
        ((a.b.d.d.a.c) this.f213a).setHeaderIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(int i) {
        ((a.b.d.d.a.c) this.f213a).setHeaderTitle(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderView(View view) {
        ((a.b.d.d.a.c) this.f213a).setHeaderView(view);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(int i) {
        ((a.b.d.d.a.c) this.f213a).setIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(Drawable drawable) {
        ((a.b.d.d.a.c) this.f213a).setHeaderIcon(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(CharSequence charSequence) {
        ((a.b.d.d.a.c) this.f213a).setHeaderTitle(charSequence);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(Drawable drawable) {
        ((a.b.d.d.a.c) this.f213a).setIcon(drawable);
        return this;
    }
}
