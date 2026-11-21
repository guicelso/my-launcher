package com.dofun.carsetting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dofun.carsetting.R;

/* JADX INFO: compiled from: UpdateAdapter.java */
/* JADX INFO: loaded from: classes.dex */
public class f extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f131a;
    private CharSequence[] b;
    private int c = -1;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public f(Context context, CharSequence[] charSequenceArr) {
        this.f131a = context;
        this.b = charSequenceArr;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.b.length;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.b[i];
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = a(viewGroup);
        }
        a(view, i, viewGroup);
        return view;
    }

    public void a(int i) {
        this.c = i;
        notifyDataSetChanged();
    }

    /* JADX INFO: compiled from: UpdateAdapter.java */
    private class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        TextView f132a;

        private a() {
        }
    }

    private View a(ViewGroup viewGroup) {
        View viewInflate = LayoutInflater.from(this.f131a).inflate(R.layout.multifunction_dialog_item, viewGroup, false);
        a aVar = new a();
        aVar.f132a = (TextView) viewInflate.findViewById(R.id.item_tv);
        viewInflate.setTag(aVar);
        return viewInflate;
    }

    @SuppressLint({"ResourceAsColor"})
    private void a(View view, int i, ViewGroup viewGroup) {
        a aVar = (a) view.getTag();
        aVar.f132a.setText(this.b[i]);
        aVar.f132a.getCompoundDrawables()[0].setLevel(i == this.c ? 1 : 0);
    }
}
