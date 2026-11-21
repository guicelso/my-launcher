package a.b.e.e.c;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.CollapsibleActionView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class i extends a.b.e.e.c.c<a.b.d.d.a.b> implements MenuItem {
    public Method e;

    public class a extends a.b.d.h.c {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final ActionProvider f232b;

        public a(Context context, ActionProvider actionProvider) {
            super(context);
            this.f232b = actionProvider;
        }

        @Override // a.b.d.h.c
        public boolean a() {
            return this.f232b.hasSubMenu();
        }

        @Override // a.b.d.h.c
        public View c() {
            return this.f232b.onCreateActionView();
        }

        @Override // a.b.d.h.c
        public boolean e() {
            return this.f232b.onPerformDefaultAction();
        }

        @Override // a.b.d.h.c
        public void f(SubMenu subMenu) {
            this.f232b.onPrepareSubMenu(i.this.j(subMenu));
        }
    }

    public static class b extends FrameLayout implements a.b.e.e.a {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final CollapsibleActionView f234b;

        /* JADX WARN: Multi-variable type inference failed */
        public b(View view) {
            super(view.getContext());
            this.f234b = (CollapsibleActionView) view;
            addView(view);
        }

        @Override // a.b.e.e.a
        public void a() {
            this.f234b.onActionViewExpanded();
        }

        @Override // a.b.e.e.a
        public void c() {
            this.f234b.onActionViewCollapsed();
        }
    }

    public class c extends a.b.e.e.c.d<MenuItem.OnActionExpandListener> implements MenuItem.OnActionExpandListener {
        public c(MenuItem.OnActionExpandListener onActionExpandListener) {
            super(onActionExpandListener);
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return ((MenuItem.OnActionExpandListener) this.f213a).onMenuItemActionCollapse(i.this.i(menuItem));
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return ((MenuItem.OnActionExpandListener) this.f213a).onMenuItemActionExpand(i.this.i(menuItem));
        }
    }

    public class d extends a.b.e.e.c.d<MenuItem.OnMenuItemClickListener> implements MenuItem.OnMenuItemClickListener {
        public d(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            super(onMenuItemClickListener);
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            return ((MenuItem.OnMenuItemClickListener) this.f213a).onMenuItemClick(i.this.i(menuItem));
        }
    }

    public i(Context context, a.b.d.d.a.b bVar) {
        super(context, bVar);
    }

    @Override // android.view.MenuItem
    public boolean collapseActionView() {
        return ((a.b.d.d.a.b) this.f213a).collapseActionView();
    }

    @Override // android.view.MenuItem
    public boolean expandActionView() {
        return ((a.b.d.d.a.b) this.f213a).expandActionView();
    }

    @Override // android.view.MenuItem
    public ActionProvider getActionProvider() {
        a.b.d.h.c cVarD = ((a.b.d.d.a.b) this.f213a).d();
        if (cVarD instanceof a) {
            return ((a) cVarD).f232b;
        }
        return null;
    }

    @Override // android.view.MenuItem
    public View getActionView() {
        View actionView = ((a.b.d.d.a.b) this.f213a).getActionView();
        return actionView instanceof b ? (View) ((b) actionView).f234b : actionView;
    }

    @Override // android.view.MenuItem
    public char getAlphabeticShortcut() {
        return ((a.b.d.d.a.b) this.f213a).getAlphabeticShortcut();
    }

    @Override // android.view.MenuItem
    public int getGroupId() {
        return ((a.b.d.d.a.b) this.f213a).getGroupId();
    }

    @Override // android.view.MenuItem
    public Drawable getIcon() {
        return ((a.b.d.d.a.b) this.f213a).getIcon();
    }

    @Override // android.view.MenuItem
    public Intent getIntent() {
        return ((a.b.d.d.a.b) this.f213a).getIntent();
    }

    @Override // android.view.MenuItem
    public int getItemId() {
        return ((a.b.d.d.a.b) this.f213a).getItemId();
    }

    @Override // android.view.MenuItem
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return ((a.b.d.d.a.b) this.f213a).getMenuInfo();
    }

    @Override // android.view.MenuItem
    public char getNumericShortcut() {
        return ((a.b.d.d.a.b) this.f213a).getNumericShortcut();
    }

    @Override // android.view.MenuItem
    public int getOrder() {
        return ((a.b.d.d.a.b) this.f213a).getOrder();
    }

    @Override // android.view.MenuItem
    public SubMenu getSubMenu() {
        return j(((a.b.d.d.a.b) this.f213a).getSubMenu());
    }

    @Override // android.view.MenuItem
    public CharSequence getTitle() {
        return ((a.b.d.d.a.b) this.f213a).getTitle();
    }

    @Override // android.view.MenuItem
    public CharSequence getTitleCondensed() {
        return ((a.b.d.d.a.b) this.f213a).getTitleCondensed();
    }

    @Override // android.view.MenuItem
    public boolean hasSubMenu() {
        return ((a.b.d.d.a.b) this.f213a).hasSubMenu();
    }

    @Override // android.view.MenuItem
    public boolean isActionViewExpanded() {
        return ((a.b.d.d.a.b) this.f213a).isActionViewExpanded();
    }

    @Override // android.view.MenuItem
    public boolean isCheckable() {
        return ((a.b.d.d.a.b) this.f213a).isCheckable();
    }

    @Override // android.view.MenuItem
    public boolean isChecked() {
        return ((a.b.d.d.a.b) this.f213a).isChecked();
    }

    @Override // android.view.MenuItem
    public boolean isEnabled() {
        return ((a.b.d.d.a.b) this.f213a).isEnabled();
    }

    @Override // android.view.MenuItem
    public boolean isVisible() {
        return ((a.b.d.d.a.b) this.f213a).isVisible();
    }

    public a k(ActionProvider actionProvider) {
        return new a(this.f211b, actionProvider);
    }

    @Override // android.view.MenuItem
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        ((a.b.d.d.a.b) this.f213a).e(actionProvider != null ? k(actionProvider) : null);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionView(int i) {
        ((a.b.d.d.a.b) this.f213a).setActionView(i);
        View actionView = ((a.b.d.d.a.b) this.f213a).getActionView();
        if (actionView instanceof CollapsibleActionView) {
            ((a.b.d.d.a.b) this.f213a).setActionView(new b(actionView));
        }
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionView(View view) {
        if (view instanceof CollapsibleActionView) {
            view = new b(view);
        }
        ((a.b.d.d.a.b) this.f213a).setActionView(view);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setAlphabeticShortcut(char c2) {
        ((a.b.d.d.a.b) this.f213a).setAlphabeticShortcut(c2);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setCheckable(boolean z) {
        ((a.b.d.d.a.b) this.f213a).setCheckable(z);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setChecked(boolean z) {
        ((a.b.d.d.a.b) this.f213a).setChecked(z);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setEnabled(boolean z) {
        ((a.b.d.d.a.b) this.f213a).setEnabled(z);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(int i) {
        ((a.b.d.d.a.b) this.f213a).setIcon(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(Drawable drawable) {
        ((a.b.d.d.a.b) this.f213a).setIcon(drawable);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIntent(Intent intent) {
        ((a.b.d.d.a.b) this.f213a).setIntent(intent);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setNumericShortcut(char c2) {
        ((a.b.d.d.a.b) this.f213a).setNumericShortcut(c2);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        ((a.b.d.d.a.b) this.f213a).setOnActionExpandListener(onActionExpandListener != null ? new c(onActionExpandListener) : null);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        ((a.b.d.d.a.b) this.f213a).setOnMenuItemClickListener(onMenuItemClickListener != null ? new d(onMenuItemClickListener) : null);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setShortcut(char c2, char c3) {
        ((a.b.d.d.a.b) this.f213a).setShortcut(c2, c3);
        return this;
    }

    @Override // android.view.MenuItem
    public void setShowAsAction(int i) {
        ((a.b.d.d.a.b) this.f213a).setShowAsAction(i);
    }

    @Override // android.view.MenuItem
    public MenuItem setShowAsActionFlags(int i) {
        ((a.b.d.d.a.b) this.f213a).setShowAsActionFlags(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(int i) {
        ((a.b.d.d.a.b) this.f213a).setTitle(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(CharSequence charSequence) {
        ((a.b.d.d.a.b) this.f213a).setTitle(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitleCondensed(CharSequence charSequence) {
        ((a.b.d.d.a.b) this.f213a).setTitleCondensed(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setVisible(boolean z) {
        return ((a.b.d.d.a.b) this.f213a).setVisible(z);
    }
}
