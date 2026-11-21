package a.b.e.e.a;

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

/* JADX INFO: renamed from: a.b.e.e.a.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0008a implements a.b.d.d.a.b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f205a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final int f206b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f207c;
    public CharSequence d;
    public CharSequence e;
    public Intent f;
    public char g;
    public char h;
    public Drawable i;
    public Context j;
    public ColorStateList k = null;
    public PorterDuff.Mode l = null;
    public boolean m = false;
    public boolean n = false;
    public int o = 16;

    public C0008a(Context context, int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.j = context;
        this.f205a = i2;
        this.f206b = i;
        this.f207c = i4;
        this.d = charSequence;
    }

    @Override // a.b.d.d.a.b
    public a.b.d.d.a.b a(a.b.d.h.e eVar) {
        throw new UnsupportedOperationException();
    }

    @Override // a.b.d.d.a.b
    public a.b.d.d.a.b a(CharSequence charSequence) {
        return this;
    }

    @Override // a.b.d.d.a.b
    public a.b.d.h.e a() {
        return null;
    }

    @Override // a.b.d.d.a.b
    public MenuItem a(char c2, int i) {
        this.h = Character.toLowerCase(c2);
        KeyEvent.normalizeMetaState(i);
        return this;
    }

    @Override // a.b.d.d.a.b
    public MenuItem a(ColorStateList colorStateList) {
        this.k = colorStateList;
        this.m = true;
        b();
        return this;
    }

    @Override // a.b.d.d.a.b
    public MenuItem a(PorterDuff.Mode mode) {
        this.l = mode;
        this.n = true;
        b();
        return this;
    }

    @Override // a.b.d.d.a.b
    public a.b.d.d.a.b b(CharSequence charSequence) {
        return this;
    }

    @Override // a.b.d.d.a.b
    public MenuItem b(char c2, int i) {
        this.g = c2;
        KeyEvent.normalizeMetaState(i);
        return this;
    }

    public final void b() {
        if (this.i != null) {
            if (this.m || this.n) {
                this.i = a.b.b.a.e.b(this.i);
                this.i = this.i.mutate();
                if (this.m) {
                    a.b.b.a.e.a(this.i, this.k);
                }
                if (this.n) {
                    a.b.b.a.e.a(this.i, this.l);
                }
            }
        }
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public boolean collapseActionView() {
        return false;
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public boolean expandActionView() {
        return false;
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
        return this.h;
    }

    @Override // android.view.MenuItem
    public int getGroupId() {
        return this.f206b;
    }

    @Override // android.view.MenuItem
    public Drawable getIcon() {
        return this.i;
    }

    @Override // android.view.MenuItem
    public Intent getIntent() {
        return this.f;
    }

    @Override // android.view.MenuItem
    public int getItemId() {
        return this.f205a;
    }

    @Override // android.view.MenuItem
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override // android.view.MenuItem
    public char getNumericShortcut() {
        return this.g;
    }

    @Override // android.view.MenuItem
    public int getOrder() {
        return this.f207c;
    }

    @Override // android.view.MenuItem
    public SubMenu getSubMenu() {
        return null;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitle() {
        return this.d;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.e;
        return charSequence != null ? charSequence : this.d;
    }

    @Override // android.view.MenuItem
    public boolean hasSubMenu() {
        return false;
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public boolean isActionViewExpanded() {
        return false;
    }

    @Override // android.view.MenuItem
    public boolean isCheckable() {
        return (this.o & 1) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isChecked() {
        return (this.o & 2) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isEnabled() {
        return (this.o & 16) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isVisible() {
        return (this.o & 8) == 0;
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
        this.h = Character.toLowerCase(c2);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setCheckable(boolean z) {
        this.o = (z ? 1 : 0) | (this.o & (-2));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setChecked(boolean z) {
        this.o = (z ? 2 : 0) | (this.o & (-3));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setEnabled(boolean z) {
        this.o = (z ? 16 : 0) | (this.o & (-17));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(int i) {
        this.i = a.b.d.b.a.b(this.j, i);
        b();
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(Drawable drawable) {
        this.i = drawable;
        b();
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIntent(Intent intent) {
        this.f = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setNumericShortcut(char c2) {
        this.g = c2;
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
        this.g = c2;
        this.h = Character.toLowerCase(c3);
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
        this.d = this.j.getResources().getString(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(CharSequence charSequence) {
        this.d = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.e = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setVisible(boolean z) {
        this.o = (this.o & 8) | (z ? 0 : 8);
        return this;
    }

    @Override // a.b.d.d.a.b, android.view.MenuItem
    public MenuItem setActionView(View view) {
        throw new UnsupportedOperationException();
    }
}
