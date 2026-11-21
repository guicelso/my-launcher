package com.dofun.carsetting.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.AppInfo;
import com.dofun.carsetting.ui.MainActivity;
import java.util.ArrayList;

/* JADX INFO: compiled from: SoftChooseAdapter.java */
/* JADX INFO: loaded from: classes.dex */
public class e extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private LayoutInflater f129a;
    private ArrayList<AppInfo> b;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public e(LayoutInflater layoutInflater) {
        this.f129a = layoutInflater;
    }

    public void a(ArrayList<AppInfo> arrayList) {
        this.b = arrayList;
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        if (this.b == null) {
            return 0;
        }
        return this.b.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        if (this.b == null) {
            return null;
        }
        return this.b.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = this.f129a.inflate(R.layout.adapter_soft_choose, (ViewGroup) null);
            aVar = new a(view);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        aVar.b.setVisibility(0);
        aVar.b.setBackground(this.b.get(i).getAppIcon());
        aVar.f130a.setText(this.b.get(i).getAppName());
        if (this.b.get(i).isChoose()) {
            if (MainActivity.b) {
                aVar.c.setBackgroundResource(R.drawable.btn_shutter_pressed);
            } else {
                aVar.c.setBackgroundResource(R.drawable.btn_shutter_pressed_port);
            }
        } else if (MainActivity.b) {
            aVar.c.setBackgroundResource(R.drawable.btn_shutter_pressed_disabled);
        } else {
            aVar.c.setBackgroundResource(R.drawable.btn_shutter_pressed_disabled_port);
        }
        return view;
    }

    /* JADX INFO: compiled from: SoftChooseAdapter.java */
    class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        TextView f130a;
        TextView b;
        TextView c;

        public a(View view) {
            this.f130a = (TextView) view.findViewById(R.id.tv_app_name);
            this.b = (TextView) view.findViewById(R.id.tv_app_icon);
            this.c = (TextView) view.findViewById(R.id.tv_app_choose);
        }
    }
}
