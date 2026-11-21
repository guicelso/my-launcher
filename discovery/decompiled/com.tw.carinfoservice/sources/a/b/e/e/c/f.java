package a.b.e.e.c;

import android.support.v7.view.menu.ListMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class f extends BaseAdapter {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public g f223b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f224c = -1;
    public boolean d;
    public final boolean e;
    public final LayoutInflater f;
    public final int g;

    public f(g gVar, LayoutInflater layoutInflater, boolean z, int i) {
        this.e = z;
        this.f = layoutInflater;
        this.f223b = gVar;
        this.g = i;
        a();
    }

    public void a() {
        g gVar = this.f223b;
        h hVar = gVar.s;
        if (hVar != null) {
            gVar.i();
            ArrayList<h> arrayList = gVar.j;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (arrayList.get(i) == hVar) {
                    this.f224c = i;
                    return;
                }
            }
        }
        this.f224c = -1;
    }

    @Override // android.widget.Adapter
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public h getItem(int i) {
        ArrayList<h> arrayListK;
        if (this.e) {
            g gVar = this.f223b;
            gVar.i();
            arrayListK = gVar.j;
        } else {
            arrayListK = this.f223b.k();
        }
        int i2 = this.f224c;
        if (i2 >= 0 && i >= i2) {
            i++;
        }
        return arrayListK.get(i);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList<h> arrayListK;
        if (this.e) {
            g gVar = this.f223b;
            gVar.i();
            arrayListK = gVar.j;
        } else {
            arrayListK = this.f223b.k();
        }
        int i = this.f224c;
        int size = arrayListK.size();
        return i < 0 ? size : size - 1;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.f.inflate(this.g, viewGroup, false);
        }
        int i2 = getItem(i).f229b;
        int i3 = i - 1;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        listMenuItemView.setGroupDividerEnabled(this.f223b.l() && i2 != (i3 >= 0 ? getItem(i3).f229b : i2));
        n.a aVar = (n.a) view;
        if (this.d) {
            listMenuItemView.setForceShowIcon(true);
        }
        aVar.c(getItem(i), 0);
        return view;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }
}
