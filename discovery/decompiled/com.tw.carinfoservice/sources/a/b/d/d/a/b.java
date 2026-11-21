package a.b.d.d.a;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.MenuItem;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface b extends MenuItem {
    MenuItem a(ColorStateList colorStateList);

    MenuItem b(char c2, int i);

    MenuItem c(char c2, int i);

    @Override // android.view.MenuItem
    boolean collapseActionView();

    a.b.d.h.c d();

    b e(a.b.d.h.c cVar);

    @Override // android.view.MenuItem
    boolean expandActionView();

    b f(CharSequence charSequence);

    MenuItem g(PorterDuff.Mode mode);

    @Override // android.view.MenuItem
    View getActionView();

    b h(CharSequence charSequence);

    @Override // android.view.MenuItem
    boolean isActionViewExpanded();

    @Override // android.view.MenuItem
    MenuItem setActionView(int i);

    @Override // android.view.MenuItem
    MenuItem setActionView(View view);

    @Override // android.view.MenuItem
    void setShowAsAction(int i);

    @Override // android.view.MenuItem
    MenuItem setShowAsActionFlags(int i);
}
