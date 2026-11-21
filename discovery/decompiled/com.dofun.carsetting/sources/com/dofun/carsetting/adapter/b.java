package com.dofun.carsetting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dofun.carsetting.R;

/* JADX INFO: compiled from: MultiCheckDialogAdapter.java */
/* JADX INFO: loaded from: classes.dex */
public class b extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f122a;
    private CharSequence[] b;
    private int c;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public b(Context context, CharSequence[] charSequenceArr, int i) {
        this.f122a = context;
        this.b = charSequenceArr;
        this.c = i;
    }

    public void a(int i) {
        this.c = i;
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

    /* JADX INFO: compiled from: MultiCheckDialogAdapter.java */
    private class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        TextView f123a;

        private a() {
        }
    }

    private View a(ViewGroup viewGroup) {
        View viewInflate = LayoutInflater.from(this.f122a).inflate(R.layout.multifunction_dialog_item, viewGroup, false);
        a aVar = new a();
        aVar.f123a = (TextView) viewInflate.findViewById(R.id.item_tv);
        viewInflate.setTag(aVar);
        return viewInflate;
    }

    private void a(View view, int i, ViewGroup viewGroup) {
        a aVar = (a) view.getTag();
        aVar.f123a.setText(this.b[i]);
        double d = i;
        aVar.f123a.getCompoundDrawables()[0].setLevel((this.c & (((int) Math.pow(2.0d, d)) & 255)) == (((int) Math.pow(2.0d, d)) & 255) ? 1 : 0);
    }
}
