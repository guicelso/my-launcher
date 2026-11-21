package com.dofun.carsetting.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.tw.john.TWUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.f.u;
import com.dofun.carsetting.f.v;
import java.util.ArrayList;

/* JADX INFO: compiled from: ReverseTypeChooseDialog_TS10.java */
/* JADX INFO: loaded from: classes.dex */
public class g extends Dialog implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f516a;
    AlertDialog b;
    private Context c;
    private u d;
    private ExpandableListView e;
    private Button f;
    private ArrayList<String> g;
    private ArrayList<ArrayList<String>> h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private Handler n;

    private void c() {
    }

    public g(Context context) {
        super(context, R.style.custom_dialog);
        this.f516a = "ReverseTypeChooseDialog_TS10";
        this.g = new ArrayList<>();
        this.h = new ArrayList<>();
        this.m = -1;
        this.n = new Handler() { // from class: com.dofun.carsetting.widget.g.6
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                try {
                    if (message.what == 274 && g.this.m != message.arg1) {
                        g.this.m = message.arg1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.c = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.d = u.a();
        if (this.d != null) {
            this.d.addHandler(this.f516a, this.n);
            this.d.write(274, 255);
        }
        a();
        c();
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.c).inflate(R.layout.dialog_revertype, (ViewGroup) null);
        setContentView(viewInflate);
        ((ImageView) viewInflate.findViewById(R.id.iv_dialog_close)).setOnClickListener(this);
        this.i = SystemProperties.getInt("persist.camera.reverse", 0);
        if (this.i == 1) {
            this.j = Integer.parseInt(SystemProperties.get("persist.camera.ahd360", "3"));
            this.j--;
        } else {
            this.j = Integer.parseInt(SystemProperties.get("persist.sensor.type", "0"));
        }
        this.g = new ArrayList<>();
        for (CharSequence charSequence : this.c.getResources().getTextArray(R.array.reverse_ts10_type_entries)) {
            this.g.add((String) charSequence);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (CharSequence charSequence2 : this.c.getResources().getTextArray(R.array.reverse_camera_type_entries)) {
            arrayList.add((String) charSequence2);
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (CharSequence charSequence3 : this.c.getResources().getTextArray(R.array.ts9_ahd360_entries)) {
            arrayList2.add((String) charSequence3);
        }
        this.h = new ArrayList<>();
        this.h.add(arrayList);
        this.h.add(arrayList2);
        this.e = (ExpandableListView) viewInflate.findViewById(R.id.expandablelistview);
        this.f = (Button) viewInflate.findViewById(R.id.btn_reverse_type);
        this.f.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.g.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                g.this.b();
            }
        });
        final com.dofun.carsetting.adapter.a aVar = new com.dofun.carsetting.adapter.a(this.c, this.g, this.h, this.i, this.j);
        this.e.setAdapter(aVar);
        this.e.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.dofun.carsetting.widget.g.2
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                g.this.k = i;
                aVar.a(g.this.i, g.this.j);
                return false;
            }
        });
        this.e.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.dofun.carsetting.widget.g.3
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
                g.this.k = i;
                g.this.l = i2;
                aVar.a(i, i2);
                return false;
            }
        });
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = (int) this.c.getResources().getDimension(R.dimen.dialog_gesture_tip_width);
            attributes.height = (int) this.c.getResources().getDimension(R.dimen.dialog_gesture_tip_height);
            window.setAttributes(attributes);
        }
        setCanceledOnTouchOutside(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.c, R.style.dialogNoBg);
        String str = this.g.get(this.k) + "/" + this.h.get(this.k).get(this.l);
        View viewInflate = LayoutInflater.from(this.c).inflate(R.layout.dialog_revertype_ts_choose, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.title)).setText(str);
        builder.setView(viewInflate);
        this.b = builder.create();
        viewInflate.findViewById(R.id.canal).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.g.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                g.this.b.dismiss();
            }
        });
        viewInflate.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.g.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SystemProperties.set("persist.camera.reverse", String.valueOf(g.this.k));
                if (g.this.k != 1) {
                    SystemProperties.set("persist.sensor.type", String.valueOf(g.this.l));
                } else {
                    g.this.j = g.this.l + 1;
                    SystemProperties.set("persist.camera.ahd360", String.valueOf(g.this.j));
                    if (g.this.k > 0) {
                        SystemProperties.set("persist.camera.mode0", "" + g.this.l);
                    }
                    if (g.this.k >= 3) {
                        SystemProperties.set("persist.camera.id", "0");
                    } else {
                        SystemProperties.set("persist.camera.id", "1");
                    }
                }
                if (g.this.b != null) {
                    g.this.b.dismiss();
                }
                g.this.a(g.this.k);
                g.this.i = g.this.k;
                if (g.this.i == 0) {
                    v.a((TWUtil) g.this.d, false);
                }
                new Handler().postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.g.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        g.this.d.write(514, 1, 0);
                    }
                }, 200L);
            }
        });
        this.b.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        switch (i) {
            case 0:
                this.m |= 128;
                this.d.write(274, 1, this.m);
                break;
            case 1:
                this.m &= -129;
                this.d.write(274, 1, this.m);
                break;
        }
        this.d.write(40730, 1, 0, "setprop persist.camera.reverse " + i);
        this.d.write(40730, 1, 0, "mount -o remount,ro /");
        this.d.write(40730, 1, 0, "sync");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_dialog_close) {
            return;
        }
        dismiss();
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
    }

    @Override // android.app.Dialog
    public void hide() {
        super.hide();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        if (this.d != null) {
            this.d.removeHandler(this.f516a);
            this.d.close();
            this.d = null;
        }
    }
}
