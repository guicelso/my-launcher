package com.dofun.carsetting.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.AppInfo;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SleepAppSelectAdapter extends RecyclerView.Adapter<a> {
    private b b;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private List<AppInfo> f108a = new ArrayList();
    private boolean c = false;
    private boolean d = true;

    public interface b {
        void a(AppInfo appInfo, int i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public a onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_app_list, viewGroup, false));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onBindViewHolder(a aVar, final int i) {
        aVar.b.setText(this.f108a.get(i).appName);
        if (this.c) {
            aVar.d.setVisibility(0);
            if (this.f108a.get(i).appIcon != null) {
                aVar.d.setImageDrawable(this.f108a.get(i).appIcon);
            }
        } else {
            aVar.d.setVisibility(8);
        }
        if (this.d) {
            aVar.c.setVisibility(0);
        } else {
            aVar.c.setVisibility(8);
        }
        if (this.f108a.get(i).selected) {
            aVar.e.getDrawable().setLevel(1);
        } else {
            aVar.e.getDrawable().setLevel(0);
        }
        aVar.c.setVisibility(8);
        aVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.adapter.SleepAppSelectAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SleepAppSelectAdapter.this.b != null) {
                    SleepAppSelectAdapter.this.b.a((AppInfo) SleepAppSelectAdapter.this.f108a.get(i), i);
                }
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.f108a.size();
    }

    public void a(boolean z) {
        this.c = z;
    }

    class a extends RecyclerView.ViewHolder {
        private TextView b;
        private TextView c;
        private ImageView d;
        private ImageView e;

        public a(View view) {
            super(view);
            this.b = (TextView) view.findViewById(R.id.tv_text);
            this.c = (TextView) view.findViewById(R.id.tv_right_tip);
            this.d = (ImageView) view.findViewById(R.id.iv_left_icon);
            this.e = (ImageView) view.findViewById(R.id.iv_right_icon);
        }
    }

    public void a(b bVar) {
        this.b = bVar;
    }

    public void a(List<AppInfo> list) {
        this.f108a = list;
    }
}
