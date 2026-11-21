package a.b.e.e.c;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class q extends g implements SubMenu {
    public g u;
    public h v;

    public q(Context context, g gVar, h hVar) {
        super(context);
        this.u = gVar;
        this.v = hVar;
    }

    @Override // a.b.e.e.c.g
    public boolean d(h hVar) {
        return this.u.d(hVar);
    }

    @Override // a.b.e.e.c.g
    public boolean e(g gVar, MenuItem menuItem) {
        return super.e(gVar, menuItem) || this.u.e(gVar, menuItem);
    }

    @Override // a.b.e.e.c.g
    public boolean f(h hVar) {
        return this.u.f(hVar);
    }

    @Override // android.view.SubMenu
    public MenuItem getItem() {
        return this.v;
    }

    @Override // a.b.e.e.c.g
    public g j() {
        return this.u.j();
    }

    @Override // a.b.e.e.c.g
    public boolean l() {
        return this.u.l();
    }

    @Override // a.b.e.e.c.g
    public boolean m() {
        return this.u.m();
    }

    @Override // a.b.e.e.c.g
    public boolean n() {
        return this.u.n();
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(int i) {
        t(0, null, i, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(int i) {
        t(i, null, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderView(View view) {
        t(0, null, 0, null, view);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(int i) {
        this.v.setIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(Drawable drawable) {
        this.v.setIcon(drawable);
        return this;
    }

    @Override // a.b.e.e.c.g, android.view.Menu
    public void setQwertyMode(boolean z) {
        this.u.setQwertyMode(z);
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(Drawable drawable) {
        t(0, null, 0, drawable, null);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(CharSequence charSequence) {
        t(0, charSequence, 0, null, null);
        return this;
    }
}
