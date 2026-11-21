package com.dofun.carsetting.c;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.CFile;
import com.dofun.carsetting.f.u;
import java.io.File;
import java.util.ArrayList;

/* JADX INFO: compiled from: McuUpdateDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class e extends Dialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    com.dofun.carsetting.adapter.f f147a;
    private Context b;
    private u c;
    private String d;
    private int e;
    private String f;
    private String g;
    private String h;
    private int i;
    private ListView j;
    private ArrayList<CFile> k;
    private SeekBar l;
    private TextView m;
    private com.dofun.carsetting.b.a n;

    @SuppressLint({"HandlerLeak"})
    private Handler o;

    public e(Context context) {
        super(context, R.style.SeekBarDialogStyle);
        this.d = null;
        this.e = 0;
        this.f = "0";
        this.g = null;
        this.h = null;
        this.i = 1023;
        this.n = com.dofun.carsetting.b.a.a();
        this.o = new Handler() { // from class: com.dofun.carsetting.c.e.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    int i = message.what;
                    if (i != 266) {
                        if (i != 1539) {
                            if (i == 1541) {
                                e.this.c.write(65281);
                            }
                        } else if (e.this.l != null) {
                            e.this.l.setProgress((message.arg1 * 100) / e.this.i);
                            e.this.m.setText(e.this.b.getResources().getString(R.string.mcu_update_summary) + ((message.arg1 * 100) / e.this.i) + "%");
                        }
                    } else if (e.this.d == null) {
                        e.this.d = (String) message.obj;
                        if (e.this.d != null && e.this.d.length() > 0) {
                            String str = e.this.d.split("-")[3];
                            e.this.e = Integer.parseInt(str.substring(4, 6), 16);
                            e.this.f = str.substring(1, 2);
                            e.this.g = e.this.n.r.a(e.this.e, e.this.f);
                            e.this.h = e.this.n.r.a(e.this.e);
                            e.this.b();
                        }
                    }
                } catch (Exception unused) {
                }
            }
        };
        this.b = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
        this.c = u.a();
        if (this.c != null) {
            this.c.addHandler("McuUpdateDialog", this.o);
            this.c.write(266, 255);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (this.g == null || "".equals(this.g)) {
            return;
        }
        this.k = new ArrayList<>();
        for (int i = 0; i < this.n.A.length; i++) {
            File file = new File("/" + this.n.A[i] + "/" + this.g);
            if (file.isFile() && file.canRead()) {
                this.k.add(new CFile(this.n.A[i], file));
            }
        }
        for (int i2 = 0; i2 < this.n.B.length; i2++) {
            File file2 = new File("/" + this.n.B[i2] + "/" + this.g);
            if (file2.isFile() && file2.canRead()) {
                this.k.add(new CFile(this.n.B[i2], file2));
            }
        }
        File file3 = new File("/sdcard/" + this.g);
        if (file3.isFile() && file3.canRead()) {
            this.k.add(new CFile("SDCARD", file3));
        }
        if (this.k.size() > 0) {
            CharSequence[] charSequenceArr = new CharSequence[this.k.size()];
            for (int i3 = 0; i3 < this.k.size(); i3++) {
                charSequenceArr[i3] = this.k.get(i3).cs;
            }
            this.f147a = new com.dofun.carsetting.adapter.f(this.b, charSequenceArr);
            this.j.setAdapter((ListAdapter) this.f147a);
        }
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.b).inflate(R.layout.dialog_mcu_update, (ViewGroup) null);
        setContentView(viewInflate);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        this.m = (TextView) viewInflate.findViewById(R.id.update_tv);
        this.j = (ListView) viewInflate.findViewById(R.id.item_list);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        this.l = (SeekBar) viewInflate.findViewById(R.id.update_progress);
        this.l.setVisibility(0);
        this.l.setOnTouchListener(new View.OnTouchListener() { // from class: com.dofun.carsetting.c.e.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        textView.setText(this.b.getResources().getString(R.string.mcu_update));
        setCanceledOnTouchOutside(true);
        this.j.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.c.e.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                int iWrite = (e.this.g == null || "".equals(e.this.g)) ? e.this.c.write(MotionEventCompat.ACTION_POINTER_INDEX_MASK) : e.this.c.write(MotionEventCompat.ACTION_POINTER_INDEX_MASK, 0, 0, ((CFile) e.this.k.get(i)).file.getAbsolutePath());
                if (iWrite >= 0) {
                    e.this.c.write(40730, 3, 0, "rm /data/tw/config");
                    if (iWrite >= 128) {
                        e.this.i = (iWrite / 64) - 1;
                    }
                }
                e.this.f147a.a(i);
                if (com.dofun.carsetting.f.b.f()) {
                    e.this.c.write(40730, 1, 0, "rwrite /sys/class/tw/gtp/dsppower 0");
                }
            }
        });
        imageView.setOnClickListener(new a());
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        this.b.getResources().getDisplayMetrics();
        attributes.width = (int) this.b.getResources().getDimension(R.dimen.dialog_mcu_update_width);
        attributes.height = (int) this.b.getResources().getDimension(R.dimen.dialog_mcu_update_height);
        window.setAttributes(attributes);
    }

    /* JADX INFO: compiled from: McuUpdateDialog.java */
    private class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getId() != R.id.iv_dialog_close) {
                return;
            }
            e.this.onBackPressed();
        }
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        if (this.c != null) {
            this.c.removeHandler("McuUpdateDialog");
            this.c.close();
            this.c = null;
        }
    }
}
