package android.support.v7.view.menu;

import a.b.e.e.a.k;
import a.b.e.e.a.n;
import a.b.e.e.a.t;
import a.b.e.e.a.u;
import a.b.e.f.ha;
import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandedMenuView extends ListView implements k.b, u, AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int[] f401a = {R.attr.background, R.attr.divider};

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public k f402b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f403c;

    public ExpandedMenuView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listViewStyle);
    }

    public ExpandedMenuView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        setOnItemClickListener(this);
        ha haVarA = ha.a(context, attributeSet, f401a, i, 0);
        if (haVarA.e(0)) {
            setBackgroundDrawable(haVarA.b(0));
        }
        if (haVarA.e(1)) {
            setDivider(haVarA.b(1));
        }
        haVarA.f337b.recycle();
    }

    @Override // a.b.e.e.a.k.b
    public boolean a(n nVar) {
        return this.f402b.a(nVar, (t) null, 0);
    }

    public int getWindowAnimations() {
        return this.f403c;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        a((n) getAdapter().getItem(i));
    }
}
