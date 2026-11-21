package com.dofun.carsetting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.SeekBarBean;
import com.dofun.carsetting.g.q;
import java.util.List;

/* JADX INFO: compiled from: SeekbarDialogAdapter.java */
/* JADX INFO: loaded from: classes.dex */
public class d extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f126a;
    private List<SeekBarBean> b;
    private q c;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public d a(q qVar) {
        this.c = qVar;
        return this;
    }

    public d(Context context, List<SeekBarBean> list) {
        this.f126a = context;
        this.b = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.b.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.b.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = a(viewGroup, i);
        }
        a(view, i, viewGroup);
        return view;
    }

    /* JADX INFO: compiled from: SeekbarDialogAdapter.java */
    private class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        TextView f128a;
        SeekBar b;
        TextView c;

        private a() {
        }
    }

    private View a(ViewGroup viewGroup, final int i) {
        View viewInflate = LayoutInflater.from(this.f126a).inflate(R.layout.seekbar_dialog_item, viewGroup, false);
        final a aVar = new a();
        aVar.f128a = (TextView) viewInflate.findViewById(R.id.item_titile);
        aVar.c = (TextView) viewInflate.findViewById(R.id.item_propress);
        aVar.b = (SeekBar) viewInflate.findViewById(R.id.item_seekbar);
        aVar.b.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.adapter.d.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                if (z) {
                    aVar.c.setText(String.valueOf(aVar.b.getProgress() + ((SeekBarBean) d.this.b.get(i)).getmMin()));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                d.this.c.a(null, ((Integer) aVar.f128a.getTag()).intValue(), aVar.b.getProgress() + ((SeekBarBean) d.this.b.get(i)).getmMin());
            }
        });
        viewInflate.setTag(aVar);
        return viewInflate;
    }

    private void a(View view, int i, ViewGroup viewGroup) {
        a aVar = (a) view.getTag();
        aVar.f128a.setText(this.b.get(i).getmTitle());
        aVar.b.setProgress(this.b.get(i).getmProgress());
        aVar.b.setMax(this.b.get(i).getmMax() - this.b.get(i).getmMin());
        aVar.c.setText(String.valueOf(this.b.get(i).getmProgress() + this.b.get(i).getmMin()));
        aVar.f128a.setTag(Integer.valueOf(this.b.get(i).getIndex()));
    }
}
