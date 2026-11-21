package a.b.e.e.a;

import a.b.e.f.ia;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class A extends k implements SubMenu {
    public n A;
    public k z;

    public A(Context context, k kVar, n nVar) {
        super(context);
        this.z = kVar;
        this.A = nVar;
    }

    @Override // a.b.e.e.a.k
    public void a(k.a aVar) {
        this.z.a(aVar);
    }

    @Override // a.b.e.e.a.k
    public boolean a(k kVar, MenuItem menuItem) {
        ActionMenuView.e eVar;
        k.a aVar = this.f;
        if (aVar != null && (eVar = ActionMenuView.this.z) != null) {
            Toolbar toolbar = ((ia) eVar).f342a;
        }
        return this.z.a(kVar, menuItem);
    }

    @Override // a.b.e.e.a.k
    public boolean a(n nVar) {
        return this.z.a(nVar);
    }

    @Override // a.b.e.e.a.k
    public k b() {
        return this.z.b();
    }

    @Override // a.b.e.e.a.k
    public boolean b(n nVar) {
        return this.z.b(nVar);
    }

    @Override // a.b.e.e.a.k
    public boolean d() {
        return this.z.d();
    }

    @Override // a.b.e.e.a.k
    public boolean e() {
        return this.z.e();
    }

    @Override // a.b.e.e.a.k
    public boolean f() {
        return this.z.f();
    }

    @Override // android.view.SubMenu
    public MenuItem getItem() {
        return this.A;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(int i) {
        a(0, null, i, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(int i) {
        a(i, null, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderView(View view) {
        a(0, null, 0, null, view);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(int i) {
        n nVar = this.A;
        nVar.l = null;
        nVar.m = i;
        nVar.x = true;
        nVar.n.b(false);
        return this;
    }

    @Override // a.b.e.e.a.k, android.view.Menu
    public void setQwertyMode(boolean z) {
        this.z.setQwertyMode(z);
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(Drawable drawable) {
        a(0, null, 0, drawable, null);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(CharSequence charSequence) {
        a(0, charSequence, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(Drawable drawable) {
        n nVar = this.A;
        nVar.m = 0;
        nVar.l = drawable;
        nVar.x = true;
        nVar.n.b(false);
        return this;
    }
}
