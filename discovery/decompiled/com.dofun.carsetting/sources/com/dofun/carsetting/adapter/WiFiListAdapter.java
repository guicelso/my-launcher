package com.dofun.carsetting.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.WiFiItem;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WiFiListAdapter extends RecyclerView.Adapter<c> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private List<WiFiItem> f114a = new ArrayList();
    private a b;
    private b c;

    public interface a {
        void a(WiFiItem wiFiItem, int i);
    }

    public interface b {
        void a(WiFiItem wiFiItem, int i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public c onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new c(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wifi_list, viewGroup, false));
        }
        if (i == 2) {
            return new c(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wifi_list, viewGroup, false));
        }
        if (i == 3) {
            return new c(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wifi_list, viewGroup, false));
        }
        return null;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onBindViewHolder(c cVar, final int i) {
        cVar.b.setImageResource(this.f114a.get(i).leftIconRes);
        cVar.c.setImageResource(this.f114a.get(i).rightIconRes);
        cVar.e.setText(this.f114a.get(i).contentText);
        if (this.f114a.get(i).leftIconRes == 0) {
            cVar.b.setVisibility(8);
        } else {
            cVar.b.setVisibility(0);
            if (cVar.b.getDrawable() != null) {
                cVar.b.getDrawable().setLevel(this.f114a.get(i).signalLevel);
            }
        }
        if (TextUtils.isEmpty(this.f114a.get(i).tipText)) {
            cVar.c.setVisibility(0);
        } else {
            cVar.c.setVisibility(8);
        }
        cVar.d.setText(this.f114a.get(i).tipText);
        cVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.adapter.WiFiListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (WiFiListAdapter.this.b != null) {
                    WiFiListAdapter.this.b.a((WiFiItem) WiFiListAdapter.this.f114a.get(i), i);
                }
            }
        });
        cVar.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.dofun.carsetting.adapter.WiFiListAdapter.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (WiFiListAdapter.this.c == null) {
                    return true;
                }
                WiFiListAdapter.this.c.a((WiFiItem) WiFiListAdapter.this.f114a.get(i), i);
                return true;
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.f114a.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.f114a.get(i).itemType;
    }

    class c extends RecyclerView.ViewHolder {
        private ImageView b;
        private ImageView c;
        private TextView d;
        private TextView e;

        public c(View view) {
            super(view);
            this.b = (ImageView) view.findViewById(R.id.iv_left_icon);
            this.c = (ImageView) view.findViewById(R.id.iv_right_icon);
            this.d = (TextView) view.findViewById(R.id.tv_tip);
            this.e = (TextView) view.findViewById(R.id.tv_text);
        }
    }
}
