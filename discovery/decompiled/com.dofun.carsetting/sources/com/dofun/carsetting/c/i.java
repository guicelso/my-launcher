package com.dofun.carsetting.c;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.SeekBarBean;
import com.dofun.carsetting.g.q;
import com.dofun.carsetting.widget.MyListView;
import com.dofun.carsetting.widget.SeekBarPreference;
import java.util.List;

/* JADX INFO: compiled from: SeekBarDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class i extends Dialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f163a;
    private String b;
    private q c;
    private com.dofun.carsetting.adapter.d d;
    private List<SeekBarBean> e;
    private MyListView f;

    public i(Context context) {
        super(context, R.style.SeekBarDialogStyle);
        this.b = "";
        this.f163a = context;
    }

    public i a(String str) {
        this.b = str;
        return this;
    }

    public i a(List<SeekBarBean> list) {
        this.e = list;
        return this;
    }

    public i a(q qVar) {
        this.c = qVar;
        return this;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.f163a).inflate(R.layout.dialog_single_seekbar, (ViewGroup) null);
        setContentView(viewInflate);
        setCanceledOnTouchOutside(true);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        this.f = (MyListView) viewInflate.findViewById(R.id.item_list);
        ((ImageView) viewInflate.findViewById(R.id.iv_dialog_close)).setOnClickListener(new a());
        textView.setText(this.b);
        this.d = new com.dofun.carsetting.adapter.d(this.f163a, this.e);
        this.d.a(new q() { // from class: com.dofun.carsetting.c.i.1
            @Override // com.dofun.carsetting.g.q
            public void a(SeekBarPreference seekBarPreference, int i, int i2) {
                i.this.c.a(null, i, i2);
            }
        });
        this.f.setAdapter((ListAdapter) this.d);
        this.f.setFocusable(false);
        a(this.f);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (((double) this.f163a.getResources().getDisplayMetrics().widthPixels) * 0.55d);
        window.setAttributes(attributes);
    }

    /* JADX INFO: compiled from: SeekBarDialog.java */
    private class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getId() != R.id.iv_dialog_close) {
                return;
            }
            i.this.onBackPressed();
        }
    }

    public void a(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int measuredHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, listView);
            view.measure(1, 1);
            measuredHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = measuredHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
    }
}
