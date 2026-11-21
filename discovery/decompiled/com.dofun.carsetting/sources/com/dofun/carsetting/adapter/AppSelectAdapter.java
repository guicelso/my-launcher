package com.dofun.carsetting.adapter;

import android.content.ComponentName;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
public class AppSelectAdapter extends RecyclerView.Adapter<a> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public ComponentName f102a;
    private b c;
    private List<AppInfo> b = new ArrayList();
    private boolean d = false;
    private boolean e = true;

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
        aVar.b.setText(this.b.get(i).appName);
        if (this.f102a != null) {
            if (TextUtils.equals(this.f102a.getClassName(), this.b.get(i).componentName.getClassName())) {
                aVar.e.getDrawable().setLevel(1);
            } else {
                aVar.e.getDrawable().setLevel(0);
            }
        }
        if (this.d) {
            aVar.d.setVisibility(0);
            if (this.b.get(i).appIcon != null) {
                aVar.d.setImageDrawable(this.b.get(i).appIcon);
            }
        } else {
            aVar.d.setVisibility(8);
        }
        if (this.e) {
            aVar.c.setVisibility(0);
        } else {
            aVar.c.setVisibility(8);
        }
        if (this.b.get(i).installed) {
            aVar.c.setText(R.string.has_install);
        } else {
            aVar.c.setText(R.string.not_install);
        }
        aVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.adapter.AppSelectAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AppSelectAdapter.this.c != null) {
                    AppSelectAdapter.this.f102a = ((AppInfo) AppSelectAdapter.this.b.get(i)).componentName;
                    AppSelectAdapter.this.c.a((AppInfo) AppSelectAdapter.this.b.get(i), i);
                }
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.b.size();
    }

    public void a(boolean z) {
        this.d = z;
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
        this.c = bVar;
    }

    public void a(List<AppInfo> list) {
        this.b = list;
    }

    public void b(boolean z) {
        this.e = z;
    }
}
