package a.b.d.d.a;

import a.b.d.h.e;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.MenuItem;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface b extends MenuItem {
    b a(e eVar);

    b a(CharSequence charSequence);

    e a();

    MenuItem a(char c2, int i);

    MenuItem a(ColorStateList colorStateList);

    MenuItem a(PorterDuff.Mode mode);

    b b(CharSequence charSequence);

    MenuItem b(char c2, int i);

    @Override // android.view.MenuItem
    boolean collapseActionView();

    @Override // android.view.MenuItem
    boolean expandActionView();

    @Override // android.view.MenuItem
    View getActionView();

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
