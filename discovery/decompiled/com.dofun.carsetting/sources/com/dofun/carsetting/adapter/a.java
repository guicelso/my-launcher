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

/* JADX INFO: compiled from: ExpandableAdapter.java */
/* JADX INFO: loaded from: classes.dex */
public class a extends BaseExpandableListAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    ArrayList<String> f118a;
    ArrayList<ArrayList<String>> b;
    int c;
    int d;
    Context e;

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

    public a(Context context, ArrayList<String> arrayList, ArrayList<ArrayList<String>> arrayList2, int i, int i2) {
        this.f118a = arrayList;
        this.b = arrayList2;
        this.c = i;
        this.d = i2;
        this.e = context;
    }

    public void a(int i, int i2) {
        this.c = i;
        this.d = i2;
        notifyDataSetChanged();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.f118a.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int i) {
        return this.b.get(i).size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int i) {
        return this.f118a.get(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int i, int i2) {
        return this.b.get(i).get(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reverse_type, viewGroup, false);
        }
        C0011a c0011a = new C0011a();
        c0011a.b = (ImageView) view.findViewById(R.id.reverse_type_im);
        c0011a.f119a = (TextView) view.findViewById(R.id.reverse_type_tv);
        c0011a.f119a.setText((String) getGroup(i));
        if (i == this.c) {
            c0011a.f119a.setTextColor(this.e.getResources().getColor(R.color.blue));
            c0011a.b.setImageDrawable(this.e.getResources().getDrawable(R.drawable.select_btn_offon));
        } else {
            c0011a.f119a.setTextColor(this.e.getResources().getColor(R.color.text_color_normal));
            c0011a.b.setImageDrawable(this.e.getResources().getDrawable(R.drawable.select_btn_onoff));
        }
        return view;
    }

    /* JADX INFO: renamed from: com.dofun.carsetting.adapter.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: ExpandableAdapter.java */
    private class C0011a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        TextView f119a;
        ImageView b;

        private C0011a() {
        }
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reverse_cis_used, viewGroup, false);
        }
        C0011a c0011a = new C0011a();
        ((TextView) view).setText((String) getChild(i, i2));
        c0011a.f119a = (TextView) view.findViewById(R.id.reverse_cis_used);
        c0011a.f119a.setText((String) getChild(i, i2));
        if (i2 == this.d && i == this.c) {
            c0011a.f119a.setTextColor(this.e.getResources().getColor(R.color.blue));
        } else {
            c0011a.f119a.setTextColor(this.e.getResources().getColor(R.color.text_color_normal));
        }
        return view;
    }
}
