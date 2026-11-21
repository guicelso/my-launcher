package com.dofun.carsetting.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.util.Log;
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

/* JADX INFO: compiled from: ReverseTypeChooseDialog_TS10S.java */
/* JADX INFO: loaded from: classes.dex */
public class h extends Dialog implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f524a;
    int b;
    int c;
    int d;
    int e;
    AlertDialog f;
    private Context g;
    private u h;
    private ExpandableListView i;
    private Button j;
    private ArrayList<String> k;
    private ArrayList<ArrayList<String>> l;

    private void c() {
    }

    public h(Context context) {
        super(context, R.style.custom_dialog);
        this.f524a = "ReverseTypeChooseDialog_TS10S";
        this.k = new ArrayList<>();
        this.l = new ArrayList<>();
        this.g = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.h = u.a();
        a();
        c();
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.g).inflate(R.layout.dialog_revertype, (ViewGroup) null);
        setContentView(viewInflate);
        ((ImageView) viewInflate.findViewById(R.id.iv_dialog_close)).setOnClickListener(this);
        this.b = SystemProperties.getInt("persist.camera.reverse", 0);
        if (this.b == 1) {
            this.c = Integer.parseInt(SystemProperties.get("persist.camera.ahd360", "3"));
            this.c--;
        } else {
            this.c = Integer.parseInt(SystemProperties.get("persist.tw360.tvi", "0"));
        }
        Log.d(this.f524a, "reverse:" + this.b + ",camtype:" + this.c);
        this.k = new ArrayList<>();
        if (com.dofun.carsetting.f.b.a().contains("-HV") || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            for (CharSequence charSequence : this.g.getResources().getTextArray(R.array.reverse_ts10_type_entries)) {
                this.k.add((String) charSequence);
            }
        } else {
            for (CharSequence charSequence2 : this.g.getResources().getTextArray(R.array.reverse_ts10s_type_entries)) {
                this.k.add((String) charSequence2);
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (CharSequence charSequence3 : this.g.getResources().getTextArray(R.array.reverse_camera_type_entries)) {
            arrayList.add((String) charSequence3);
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (CharSequence charSequence4 : this.g.getResources().getTextArray(R.array.ts9_ahd360_entries)) {
            arrayList2.add((String) charSequence4);
        }
        this.l = new ArrayList<>();
        this.l.add(arrayList);
        this.l.add(arrayList2);
        if (!com.dofun.carsetting.f.b.a().contains("-HV")) {
            this.l.add(arrayList);
            this.l.add(arrayList);
        }
        this.i = (ExpandableListView) viewInflate.findViewById(R.id.expandablelistview);
        this.j = (Button) viewInflate.findViewById(R.id.btn_reverse_type);
        this.j.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.h.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                h.this.b();
            }
        });
        final com.dofun.carsetting.adapter.a aVar = new com.dofun.carsetting.adapter.a(this.g, this.k, this.l, this.b, this.c);
        this.i.setAdapter(aVar);
        this.i.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.dofun.carsetting.widget.h.2
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                h.this.d = i;
                aVar.a(h.this.b, h.this.c);
                return false;
            }
        });
        this.i.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.dofun.carsetting.widget.h.3
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
                h.this.d = i;
                h.this.e = i2;
                aVar.a(i, i2);
                return false;
            }
        });
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = (int) this.g.getResources().getDimension(R.dimen.dialog_gesture_tip_width);
            attributes.height = (int) this.g.getResources().getDimension(R.dimen.dialog_gesture_tip_height);
            window.setAttributes(attributes);
        }
        setCanceledOnTouchOutside(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.g, R.style.dialogNoBg);
        String str = this.k.get(this.d) + "/" + this.l.get(this.d).get(this.e);
        View viewInflate = LayoutInflater.from(this.g).inflate(R.layout.dialog_revertype_ts_choose, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.title)).setText(str);
        builder.setView(viewInflate);
        this.f = builder.create();
        viewInflate.findViewById(R.id.canal).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.h.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                h.this.f.dismiss();
            }
        });
        viewInflate.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.h.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SystemProperties.set("persist.camera.reverse", String.valueOf(h.this.d));
                if (h.this.d == 1) {
                    h.this.c = h.this.e + 1;
                    SystemProperties.set("persist.camera.ahd360", String.valueOf(h.this.c));
                    if (h.this.d > 0) {
                        SystemProperties.set("persist.camera.mode0", "" + h.this.e);
                    }
                    if (h.this.d >= 3) {
                        SystemProperties.set("persist.camera.id", "0");
                    } else {
                        SystemProperties.set("persist.camera.id", "1");
                    }
                } else {
                    SystemProperties.set("persist.tw360.tvi", String.valueOf(h.this.e));
                }
                if (h.this.f != null) {
                    h.this.f.dismiss();
                }
                if (!com.dofun.carsetting.f.b.I() && !com.dofun.carsetting.f.b.J()) {
                    v.a(h.this.h, h.this.d);
                }
                h.this.a(h.this.d);
                new Handler().postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.h.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        h.this.h.write(514, 1, 0);
                    }
                }, 200L);
            }
        });
        this.f.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        switch (i) {
            case 0:
                if (com.dofun.carsetting.f.b.au()) {
                    if (com.dofun.carsetting.f.b.a() != null && com.dofun.carsetting.f.b.a().contains("LDFY")) {
                        this.h.write(40730, 1, 0, "pm unhide com.baony.tw360");
                    } else {
                        this.h.write(40730, 1, 0, "pm unhide cn.cardoor.zt360");
                    }
                }
                this.h.write(40730, 1, 0, "pm hide com.tw.dvr");
                break;
            case 1:
                if (com.dofun.carsetting.f.b.a() != null && com.dofun.carsetting.f.b.a().contains("LDFY")) {
                    this.h.write(40730, 1, 0, "pm hide com.baony.tw360");
                } else {
                    this.h.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                }
                this.h.write(40730, 1, 0, "pm hide com.tw.dvr");
                break;
            case 2:
                if (com.dofun.carsetting.f.b.a() != null && com.dofun.carsetting.f.b.a().contains("LDFY")) {
                    this.h.write(40730, 1, 0, "pm hide com.baony.tw360");
                } else {
                    this.h.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                }
                this.h.write(40730, 1, 0, "pm unhide com.tw.dvr");
                break;
            case 3:
                if (com.dofun.carsetting.f.b.a() != null && com.dofun.carsetting.f.b.a().contains("LDFY")) {
                    this.h.write(40730, 1, 0, "pm hide com.baony.tw360");
                } else {
                    this.h.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                }
                if (com.dofun.carsetting.f.b.au()) {
                    this.h.write(40730, 1, 0, "pm unhide com.tw.dvr");
                }
                break;
        }
        this.h.write(40730, 1, 0, "setprop persist.camera.reverse " + i);
        this.h.write(40730, 1, 0, "mount -o remount,ro /system_tw");
        this.h.write(40730, 1, 0, "sync");
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
        if (this.h != null) {
            this.h.close();
            this.h = null;
        }
    }
}
