package a.b.e.f;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.view.menu.ListMenuItemView;
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
public class x extends v implements w {
    public static Method F;
    public w E;

    public static class a extends q {
        public final int o;
        public final int p;
        public w q;
        public MenuItem r;

        public a(Context context, boolean z) {
            super(context, z);
            if (1 == context.getResources().getConfiguration().getLayoutDirection()) {
                this.o = 21;
                this.p = 22;
            } else {
                this.o = 22;
                this.p = 21;
            }
        }

        @Override // a.b.e.f.q, android.view.View
        public boolean onHoverEvent(MotionEvent motionEvent) {
            int headersCount;
            int iPointToPosition;
            int i;
            if (this.q != null) {
                ListAdapter adapter = getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    adapter = headerViewListAdapter.getWrappedAdapter();
                } else {
                    headersCount = 0;
                }
                a.b.e.e.c.f fVar = (a.b.e.e.c.f) adapter;
                a.b.e.e.c.h item = null;
                if (motionEvent.getAction() != 10 && (iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY())) != -1 && (i = iPointToPosition - headersCount) >= 0 && i < fVar.getCount()) {
                    item = fVar.getItem(i);
                }
                MenuItem menuItem = this.r;
                if (menuItem != item) {
                    a.b.e.e.c.g gVar = fVar.f223b;
                    if (menuItem != null) {
                        this.q.b(gVar, menuItem);
                    }
                    this.r = item;
                    if (item != null) {
                        this.q.a(gVar, item);
                    }
                }
            }
            return super.onHoverEvent(motionEvent);
        }

        @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView();
            if (listMenuItemView != null && i == this.o) {
                if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                    performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId());
                }
                return true;
            }
            if (listMenuItemView == null || i != this.p) {
                return super.onKeyDown(i, keyEvent);
            }
            setSelection(-1);
            ((a.b.e.e.c.f) getAdapter()).f223b.c(false);
            return true;
        }

        public void setHoverListener(w wVar) {
            this.q = wVar;
        }

        @Override // a.b.e.f.q, android.widget.AbsListView
        public /* bridge */ /* synthetic */ void setSelector(Drawable drawable) {
            super.setSelector(drawable);
        }
    }

    static {
        try {
            F = PopupWindow.class.getDeclaredMethod("setTouchModal", Boolean.TYPE);
        } catch (NoSuchMethodException unused) {
            Log.i("MenuPopupWindow", "Could not find method setTouchModal() on PopupWindow. Oh well.");
        }
    }

    public x(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, null, i, i2);
    }

    @Override // a.b.e.f.w
    public void a(a.b.e.e.c.g gVar, MenuItem menuItem) {
        w wVar = this.E;
        if (wVar != null) {
            wVar.a(gVar, menuItem);
        }
    }

    @Override // a.b.e.f.w
    public void b(a.b.e.e.c.g gVar, MenuItem menuItem) {
        w wVar = this.E;
        if (wVar != null) {
            wVar.b(gVar, menuItem);
        }
    }

    @Override // a.b.e.f.v
    public q e(Context context, boolean z) {
        a aVar = new a(context, z);
        aVar.setHoverListener(this);
        return aVar;
    }

    public void l(Object obj) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.A.setEnterTransition(null);
        }
    }

    public void m(Object obj) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.A.setExitTransition(null);
        }
    }
}
