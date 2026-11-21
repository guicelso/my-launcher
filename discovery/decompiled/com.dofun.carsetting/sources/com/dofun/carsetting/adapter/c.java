package com.dofun.carsetting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dofun.carsetting.R;
import java.util.ArrayList;

/* JADX INFO: compiled from: ReverseTypeExpandableAdapter.java */
/* JADX INFO: loaded from: classes.dex */
public class c extends BaseExpandableListAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    ArrayList<com.dofun.carsetting.adapter.a.a> f124a;
    Context b;
    private int c;
    private int d;

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int i, int i2) {
        return i + i2;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int i) {
        return i;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public c(Context context, ArrayList<com.dofun.carsetting.adapter.a.a> arrayList, int i) {
        this.f124a = arrayList;
        this.c = i;
        this.b = context;
        d(i);
    }

    private void d(int i) {
        if (this.f124a != null) {
            for (int i2 = 0; i2 < this.f124a.size(); i2++) {
                int iA = this.f124a.get(i2).a();
                if (i == i2) {
                    ArrayList<com.dofun.carsetting.adapter.a.a.C0012a> arrayListB = this.f124a.get(i2).b();
                    if (arrayListB == null) {
                        return;
                    }
                    for (int i3 = 0; i3 < arrayListB.size(); i3++) {
                        if (iA == arrayListB.get(i3).b) {
                            this.d = i3;
                        }
                    }
                }
            }
        }
    }

    public void a(int i) {
        this.c = i;
        d(i);
        notifyDataSetChanged();
    }

    public void b(int i) {
        this.d = i;
        notifyDataSetChanged();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.f124a.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int i) {
        ArrayList<com.dofun.carsetting.adapter.a.a.C0012a> arrayListB = this.f124a.get(i).b();
        if (arrayListB != null) {
            return arrayListB.size();
        }
        return 0;
    }

    @Override // android.widget.ExpandableListAdapter
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.adapter.a.a getGroup(int i) {
        return this.f124a.get(i);
    }

    @Override // android.widget.ExpandableListAdapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.adapter.a.a.C0012a getChild(int i, int i2) {
        ArrayList<com.dofun.carsetting.adapter.a.a.C0012a> arrayListB = this.f124a.get(i).b();
        if (arrayListB != null) {
            return arrayListB.get(i2);
        }
        return null;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reverse_type, viewGroup, false);
        }
        a aVar = new a();
        aVar.b = (ImageView) view.findViewById(R.id.reverse_type_im);
        aVar.f125a = (TextView) view.findViewById(R.id.reverse_type_tv);
        aVar.f125a.setText(getGroup(i).c());
        if (i == this.c) {
            aVar.f125a.setTextColor(this.b.getResources().getColor(R.color.blue));
            aVar.b.setImageDrawable(this.b.getResources().getDrawable(R.drawable.select_btn_offon));
        } else {
            aVar.f125a.setTextColor(this.b.getResources().getColor(R.color.white));
            aVar.b.setImageDrawable(this.b.getResources().getDrawable(R.drawable.select_btn_onoff));
        }
        return view;
    }

    /* JADX INFO: compiled from: ReverseTypeExpandableAdapter.java */
    private class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        TextView f125a;
        ImageView b;

        private a() {
        }
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reverse_cis_used, viewGroup, false);
        }
        a aVar = new a();
        com.dofun.carsetting.adapter.a.a.C0012a child = getChild(i, i2);
        getGroup(i);
        ((TextView) view).setText(child.f121a);
        aVar.f125a = (TextView) view.findViewById(R.id.reverse_cis_used);
        aVar.f125a.setText(getChild(i, i2).f121a);
        if (this.c == i && this.d == i2) {
            aVar.f125a.setTextColor(this.b.getResources().getColor(R.color.blue));
        } else {
            aVar.f125a.setTextColor(this.b.getResources().getColor(R.color.white));
        }
        return view;
    }
}
