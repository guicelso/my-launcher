package com.dofun.carsetting.c;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.g.l;
import com.dofun.carsetting.widget.MyListView;

/* JADX INFO: compiled from: MultifunctionDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class g extends Dialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f158a;
    private Context b;
    private String c;
    private String d;
    private CharSequence[] e;
    private l f;
    private boolean[] g;

    public g(Context context) {
        super(context, R.style.SeekBarDialogStyle);
        this.c = "";
        this.d = "";
        this.f158a = 0;
        this.b = context;
    }

    public g a(String str) {
        this.c = str;
        return this;
    }

    public g b(String str) {
        this.d = str;
        return this;
    }

    public g a(CharSequence[] charSequenceArr) {
        this.e = charSequenceArr;
        return this;
    }

    public g a(l lVar) {
        this.f = lVar;
        return this;
    }

    public void a(int i) {
        this.f158a = i;
        this.g = new boolean[this.e.length];
        for (int i2 = 1; i2 <= this.e.length; i2++) {
            int i3 = i2 - 1;
            double d = i3;
            this.g[i3] = (this.f158a & ((int) Math.pow(2.0d, d))) == ((int) Math.pow(2.0d, d));
        }
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.b).inflate(R.layout.dialog_single_seekbar, (ViewGroup) null);
        setContentView(viewInflate);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        MyListView myListView = (MyListView) viewInflate.findViewById(R.id.item_list);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        textView.setText(this.c);
        setCanceledOnTouchOutside(true);
        final com.dofun.carsetting.adapter.b bVar = new com.dofun.carsetting.adapter.b(this.b, this.e, this.f158a);
        myListView.setAdapter((ListAdapter) bVar);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.c.g.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                int iPow = 0;
                if (g.this.d.equals("temp_mode_title")) {
                    g.this.g[i] = !g.this.g[i];
                    switch (i) {
                        case 0:
                            g.this.g[1] = !g.this.g[i];
                            break;
                        case 1:
                            g.this.g[0] = !g.this.g[i];
                            break;
                    }
                } else {
                    g.this.g[i] = !g.this.g[i];
                }
                for (int i2 = 1; i2 <= g.this.g.length; i2++) {
                    int i3 = i2 - 1;
                    if (g.this.g[i3]) {
                        iPow = ((int) Math.pow(2.0d, i3)) | iPow;
                    }
                }
                bVar.a(iPow);
                bVar.notifyDataSetChanged();
                g.this.f158a = iPow;
            }
        });
        imageView.setOnClickListener(new a());
        textView2.setOnClickListener(new a());
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        this.b.getResources().getDisplayMetrics();
        attributes.width = (int) this.b.getResources().getDimension(R.dimen.dialog_multi_choose_width);
        window.setAttributes(attributes);
    }

    /* JADX INFO: compiled from: MultifunctionDialog.java */
    private class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id != R.id.iv_dialog_close && id == R.id.tv_confirm) {
                g.this.f.a(null, g.this.f158a, g.this.g);
            }
            g.this.b();
        }
    }

    public void b() {
        onBackPressed();
    }
}
