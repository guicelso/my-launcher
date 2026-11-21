package com.dofun.carsetting.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dofun.carsetting.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NaviBarAdapter extends RecyclerView.Adapter<a> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f412a;
    private List<com.dofun.carsetting.ui.a> b;
    private b c;
    private c d;
    private int e = -1;

    public interface b {
        void c(int i);
    }

    public interface c {
        void d(int i);
    }

    public NaviBarAdapter(Context context, List<com.dofun.carsetting.ui.a> list) {
        this.b = new ArrayList();
        this.f412a = context;
        this.b = list;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public a onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new a(a(viewGroup));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onBindViewHolder(final a aVar, final int i) {
        aVar.c.setText(this.b.get(i).c);
        aVar.b.setImageResource(this.b.get(i).b);
        if (!this.b.get(i).d) {
            aVar.itemView.setVisibility(8);
        }
        if (this.e == i) {
            aVar.itemView.setSelected(true);
        } else {
            aVar.itemView.setSelected(false);
        }
        aVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.ui.NaviBarAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                aVar.itemView.setSelected(true);
                NaviBarAdapter.this.e = i;
                if (NaviBarAdapter.this.c != null) {
                    NaviBarAdapter.this.c.c(i);
                }
                NaviBarAdapter.this.notifyDataSetChanged();
            }
        });
        aVar.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.dofun.carsetting.ui.NaviBarAdapter.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                aVar.itemView.setSelected(true);
                NaviBarAdapter.this.e = i;
                if (NaviBarAdapter.this.d != null) {
                    NaviBarAdapter.this.d.d(i);
                }
                NaviBarAdapter.this.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.b.size();
    }

    private View a(ViewGroup viewGroup) {
        LinearLayout linearLayout = new LinearLayout(this.f412a);
        linearLayout.setBackgroundResource(R.drawable.rb_item_bg);
        linearLayout.setGravity(17);
        linearLayout.setOrientation(0);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(-1, (int) this.f412a.getResources().getDimension(R.dimen.navi_bar_item_height));
        LinearLayout linearLayout2 = new LinearLayout(this.f412a);
        linearLayout2.addView(linearLayout, layoutParams);
        ImageView imageView = new ImageView(this.f412a);
        imageView.setId(R.id.id_navi_bar_icon);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.width = (int) this.f412a.getResources().getDimension(R.dimen.main_radio_btn_drawable_size);
        layoutParams2.height = (int) this.f412a.getResources().getDimension(R.dimen.main_radio_btn_drawable_size);
        linearLayout.addView(imageView, layoutParams2);
        TextView textView = new TextView(this.f412a);
        textView.setId(R.id.id_navi_bar_text);
        textView.setGravity(19);
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setMarqueeRepeatLimit(-1);
        textView.setTextColor(this.f412a.getColor(R.color.text_color_normal));
        textView.setTextSize(0, this.f412a.getResources().getDimension(R.dimen.title_text_size));
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -1);
        layoutParams3.setMargins((int) this.f412a.getResources().getDimension(R.dimen.tw_dp_w1), 0, 0, 0);
        linearLayout.addView(textView, layoutParams3);
        return linearLayout2;
    }

    class a extends RecyclerView.ViewHolder {
        private ImageView b;
        private TextView c;

        public a(View view) {
            super(view);
            this.b = (ImageView) view.findViewById(R.id.id_navi_bar_icon);
            this.c = (TextView) view.findViewById(R.id.id_navi_bar_text);
        }
    }

    public void a(b bVar) {
        this.c = bVar;
    }

    public void a(c cVar) {
        this.d = cVar;
    }

    public void a(int i) {
        this.e = i;
        notifyDataSetChanged();
    }

    public void a(String str) {
        int i = 0;
        while (true) {
            if (i >= this.b.size()) {
                i = -1;
                break;
            } else if (TextUtils.equals(this.b.get(i).e, str)) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1) {
            return;
        }
        a(i);
    }

    public void b(int i) {
        int i2 = 0;
        while (true) {
            if (i2 >= this.b.size()) {
                i2 = -1;
                break;
            } else if (i == this.b.get(i2).f417a) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 == -1) {
            return;
        }
        a(i2);
    }

    public int c(int i) {
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            if (i == this.b.get(i2).f417a) {
                return i2;
            }
        }
        return -1;
    }
}
