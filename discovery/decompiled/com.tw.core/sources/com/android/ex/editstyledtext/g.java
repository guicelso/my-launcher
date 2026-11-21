package com.android.ex.editstyledtext;

import android.view.MenuItem;

/* JADX INFO: loaded from: classes.dex */
class g implements MenuItem.OnMenuItemClickListener {
    final /* synthetic */ d lb;

    private g(d dVar) {
        this.lb = dVar;
    }

    /* synthetic */ g(d dVar, g gVar) {
        this(dVar);
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem menuItem) {
        return this.lb.onTextContextMenuItem(menuItem.getItemId());
    }
}
