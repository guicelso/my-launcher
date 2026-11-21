package com.dofun.carsetting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.WallpaperInfo;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WallpaperAdapter extends RecyclerView.Adapter<b> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public WallpaperInfo f111a;
    private List<WallpaperInfo> b = new ArrayList();
    private a c;
    private Context d;

    public interface a {
        void a(WallpaperInfo wallpaperInfo, int i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public b onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.d = viewGroup.getContext();
        return new b(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wallpaper_list, viewGroup, false));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onBindViewHolder(b bVar, final int i) throws Exception {
        int iA = a(this.d, this.b.get(i).thumbnailResId, "drawable");
        if (iA != 0) {
            bVar.b.setImageResource(iA);
        }
        if (this.f111a != null) {
            if (TextUtils.equals(this.f111a.thumbnailResId, this.b.get(i).thumbnailResId)) {
                bVar.itemView.getBackground().setLevel(1);
            } else {
                bVar.itemView.getBackground().setLevel(0);
            }
        }
        bVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.adapter.WallpaperAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (WallpaperAdapter.this.c != null) {
                    WallpaperAdapter.this.f111a = (WallpaperInfo) WallpaperAdapter.this.b.get(i);
                    WallpaperAdapter.this.c.a((WallpaperInfo) WallpaperAdapter.this.b.get(i), i);
                    WallpaperAdapter.this.notifyDataSetChanged();
                }
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.b.size();
    }

    class b extends RecyclerView.ViewHolder {
        private ImageView b;

        public b(View view) {
            super(view);
            this.b = (ImageView) view.findViewById(R.id.iv_wallpaper_thumbnail);
        }
    }

    public void a(a aVar) {
        this.c = aVar;
    }

    public void a(List<WallpaperInfo> list) {
        this.b = list;
        notifyDataSetChanged();
    }

    public void a(WallpaperInfo wallpaperInfo) {
        this.f111a = wallpaperInfo;
        notifyDataSetChanged();
    }

    private int a(Context context, String str, String str2) throws Exception {
        try {
            return context.getResources().getIdentifier(str, str2, context.getPackageName());
        } catch (Exception e) {
            throw e;
        }
    }
}
