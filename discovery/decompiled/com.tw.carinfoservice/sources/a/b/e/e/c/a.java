package a.b.e.e.c;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class a implements a.b.d.d.a.b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public CharSequence f206a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public CharSequence f207b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Intent f208c;
    public char d;
    public char e;
    public Drawable f;
    public Context g;
    public ColorStateList h = null;
    public PorterDuff.Mode i = null;
    public boolean j = false;
    public boolean k = false;
    public int l = 16;

    public a(Context context, int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.g = context;
        this.f206a = charSequence;
    }

    @Override // a.b.d.d.a.b
    public MenuItem a(ColorStateList colorStateList) {
        this.h = colorStateList;
        this.j = true;
        i();
        return this;
    }

    @Override // a.b.d.d.a.b
    public MenuItem b(char c2, int i) {
        this.e = Character.toLowerCase(c2);
        KeyEvent.normalizeMetaState(i);
        return this;
    }

    @Override // a.b.d.d.a.b
    public MenuItem c(char c2, int i) {
        this.d = c2;
        KeyEvent.normalizeMetaState(i);
        return this;
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public boolean collapseActionView() {
        return false;
    }

    @Override // a.b.d.d.a.b
    public a.b.d.h.c d() {
        return null;
    }

    @Override // a.b.d.d.a.b
    public a.b.d.d.a.b e(a.b.d.h.c cVar) {
        throw new UnsupportedOperationException();
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public boolean expandActionView() {
        return false;
    }

    @Override // a.b.d.d.a.b
    public a.b.d.d.a.b f(CharSequence charSequence) {
        return this;
    }

    @Override // a.b.d.d.a.b
    public MenuItem g(PorterDuff.Mode mode) {
        this.i = mode;
        this.k = true;
        i();
        return this;
    }

    @Override // android.view.MenuItem
    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public View getActionView() {
        return null;
    }

    @Override // android.view.MenuItem
    public char getAlphabeticShortcut() {
        return this.e;
    }

    @Override // android.view.MenuItem
    public int getGroupId() {
        return 0;
    }

    @Override // android.view.MenuItem
    public Drawable getIcon() {
        return this.f;
    }

    @Override // android.view.MenuItem
    public Intent getIntent() {
        return this.f208c;
    }

    @Override // android.view.MenuItem
    public int getItemId() {
        return R.id.home;
    }

    @Override // android.view.MenuItem
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override // android.view.MenuItem
    public char getNumericShortcut() {
        return this.d;
    }

    @Override // android.view.MenuItem
    public int getOrder() {
        return 0;
    }

    @Override // android.view.MenuItem
    public SubMenu getSubMenu() {
        return null;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitle() {
        return this.f206a;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.f207b;
        return charSequence != null ? charSequence : this.f206a;
    }

    @Override // a.b.d.d.a.b
    public a.b.d.d.a.b h(CharSequence charSequence) {
        return this;
    }

    @Override // android.view.MenuItem
    public boolean hasSubMenu() {
        return false;
    }

    public final void i() {
        Drawable drawable = this.f;
        if (drawable != null) {
            if (this.j || this.k) {
                Drawable drawableY = a.b.a.a.Y(drawable);
                this.f = drawableY;
                Drawable drawableMutate = drawableY.mutate();
                this.f = drawableMutate;
                if (this.j) {
                    a.b.a.a.T(drawableMutate, this.h);
                }
                if (this.k) {
                    a.b.a.a.U(this.f, this.i);
                }
            }
        }
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public boolean isActionViewExpanded() {
        return false;
    }

    @Override // android.view.MenuItem
    public boolean isCheckable() {
        return (this.l & 1) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isChecked() {
        return (this.l & 2) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isEnabled() {
        return (this.l & 16) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isVisible() {
        return (this.l & 8) == 0;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public MenuItem setActionView(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public MenuItem setAlphabeticShortcut(char c2) {
        this.e = Character.toLowerCase(c2);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setCheckable(boolean z) {
        this.l = (z ? 1 : 0) | (this.l & (-2));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setChecked(boolean z) {
        this.l = (z ? 2 : 0) | (this.l & (-3));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setEnabled(boolean z) {
        this.l = (z ? 16 : 0) | (this.l & (-17));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(int i) {
        this.f = a.b.d.b.a.b(this.g, i);
        i();
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(Drawable drawable) {
        this.f = drawable;
        i();
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIntent(Intent intent) {
        this.f208c = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setNumericShortcut(char c2) {
        this.d = c2;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setShortcut(char c2, char c3) {
        this.d = c2;
        this.e = Character.toLowerCase(c3);
        return this;
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public void setShowAsAction(int i) {
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public MenuItem setShowAsActionFlags(int i) {
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(int i) {
        this.f206a = this.g.getResources().getString(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(CharSequence charSequence) {
        this.f206a = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f207b = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setVisible(boolean z) {
        this.l = (this.l & 8) | (z ? 0 : 8);
        return this;
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public MenuItem setActionView(View view) {
        throw new UnsupportedOperationException();
    }
}
