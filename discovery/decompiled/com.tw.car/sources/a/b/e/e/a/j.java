package a.b.e.e.a;

import android.support.v7.view.menu.ListMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class j extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public k f225a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f226b = -1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f227c;
    public final boolean d;
    public final LayoutInflater e;
    public final int f;

    public j(k kVar, LayoutInflater layoutInflater, boolean z, int i) {
        this.d = z;
        this.e = layoutInflater;
        this.f225a = kVar;
        this.f = i;
        a();
    }

    public void a() {
        k kVar = this.f225a;
        n nVar = kVar.w;
        if (nVar != null) {
            kVar.a();
            ArrayList<n> arrayList = kVar.k;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (arrayList.get(i) == nVar) {
                    this.f226b = i;
                    return;
                }
            }
        }
        this.f226b = -1;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList<n> arrayListC;
        if (this.d) {
            k kVar = this.f225a;
            kVar.a();
            arrayListC = kVar.k;
        } else {
            arrayListC = this.f225a.c();
        }
        return this.f226b < 0 ? arrayListC.size() : arrayListC.size() - 1;
    }

    @Override // android.widget.Adapter
    public n getItem(int i) {
        ArrayList<n> arrayListC;
        if (this.d) {
            k kVar = this.f225a;
            kVar.a();
            arrayListC = kVar.k;
        } else {
            arrayListC = this.f225a.c();
        }
        int i2 = this.f226b;
        if (i2 >= 0 && i >= i2) {
            i++;
        }
        return arrayListC.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.e.inflate(this.f, viewGroup, false);
        }
        int i2 = getItem(i).f233b;
        int i3 = i - 1;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        listMenuItemView.setGroupDividerEnabled(this.f225a.d() && i2 != (i3 >= 0 ? getItem(i3).f233b : i2));
        u.a aVar = (u.a) view;
        if (this.f227c) {
            listMenuItemView.setForceShowIcon(true);
        }
        aVar.a(getItem(i), 0);
        return view;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }
}
