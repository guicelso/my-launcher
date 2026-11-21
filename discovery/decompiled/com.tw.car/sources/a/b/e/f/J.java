package a.b.e.f;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.view.menu.ListMenuItemView;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class J extends H implements I {
    public static Method I;
    public I J;

    public static class a extends B {
        public final int f;
        public final int g;
        public I h;
        public MenuItem i;

        public a(Context context, boolean z) {
            super(context, z);
            Configuration configuration = context.getResources().getConfiguration();
            int i = Build.VERSION.SDK_INT;
            if (1 == configuration.getLayoutDirection()) {
                this.f = 21;
                this.g = 22;
            } else {
                this.f = 22;
                this.g = 21;
            }
        }

        @Override // a.b.e.f.B, android.view.View
        public boolean onHoverEvent(MotionEvent motionEvent) {
            int headersCount;
            int iPointToPosition;
            int i;
            if (this.h != null) {
                ListAdapter adapter = getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    adapter = headerViewListAdapter.getWrappedAdapter();
                } else {
                    headersCount = 0;
                }
                a.b.e.e.a.j jVar = (a.b.e.e.a.j) adapter;
                a.b.e.e.a.n item = null;
                if (motionEvent.getAction() != 10 && (iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY())) != -1 && (i = iPointToPosition - headersCount) >= 0 && i < jVar.getCount()) {
                    item = jVar.getItem(i);
                }
                MenuItem menuItem = this.i;
                if (menuItem != item) {
                    a.b.e.e.a.k kVar = jVar.f225a;
                    if (menuItem != null) {
                        this.h.a(kVar, menuItem);
                    }
                    this.i = item;
                    if (item != null) {
                        this.h.b(kVar, item);
                    }
                }
            }
            return super.onHoverEvent(motionEvent);
        }

        @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView();
            if (listMenuItemView != null && i == this.f) {
                if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                    performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId());
                }
                return true;
            }
            if (listMenuItemView == null || i != this.g) {
                return super.onKeyDown(i, keyEvent);
            }
            setSelection(-1);
            ((a.b.e.e.a.j) getAdapter()).f225a.a(false);
            return true;
        }

        public void setHoverListener(I i) {
            this.h = i;
        }

        @Override // a.b.e.f.B, android.widget.AbsListView
        public /* bridge */ /* synthetic */ void setSelector(Drawable drawable) {
            super.setSelector(drawable);
        }
    }

    static {
        try {
            I = PopupWindow.class.getDeclaredMethod("setTouchModal", Boolean.TYPE);
        } catch (NoSuchMethodException unused) {
            Log.i("MenuPopupWindow", "Could not find method setTouchModal() on PopupWindow. Oh well.");
        }
    }

    public J(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // a.b.e.f.H
    public B a(Context context, boolean z) {
        a aVar = new a(context, z);
        aVar.setHoverListener(this);
        return aVar;
    }

    @Override // a.b.e.f.I
    public void a(a.b.e.e.a.k kVar, MenuItem menuItem) {
        I i = this.J;
        if (i != null) {
            i.a(kVar, menuItem);
        }
    }

    public void a(Object obj) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.H.setExitTransition((Transition) obj);
        }
    }

    @Override // a.b.e.f.I
    public void b(a.b.e.e.a.k kVar, MenuItem menuItem) {
        I i = this.J;
        if (i != null) {
            i.b(kVar, menuItem);
        }
    }
}
