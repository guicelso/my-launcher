package com.dofun.carsetting.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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

/* JADX INFO: compiled from: ReverseTypeChooseDialog_TS18.java */
/* JADX INFO: loaded from: classes.dex */
public class i extends Dialog implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f531a;
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

    public i(Context context) {
        super(context, R.style.custom_dialog);
        this.f531a = "ReverseTypeChooseDialog_TS18";
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

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_dialog_close) {
            return;
        }
        dismiss();
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
        this.k = new ArrayList<>();
        for (CharSequence charSequence : this.g.getResources().getTextArray(R.array.reverse_ts_type_entries)) {
            this.k.add((String) charSequence);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (CharSequence charSequence2 : this.g.getResources().getTextArray(R.array.reverse_camera_type_entries)) {
            arrayList.add((String) charSequence2);
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (CharSequence charSequence3 : this.g.getResources().getTextArray(R.array.reverse_camera_type_entries)) {
            arrayList2.add((String) charSequence3);
        }
        ArrayList<String> arrayList3 = new ArrayList<>();
        for (CharSequence charSequence4 : this.g.getResources().getTextArray(R.array.ts9_ahd360_entries)) {
            arrayList3.add((String) charSequence4);
        }
        this.l = new ArrayList<>();
        this.l.add(arrayList2);
        this.l.add(arrayList3);
        this.l.add(arrayList2);
        this.l.add(arrayList);
        this.i = (ExpandableListView) viewInflate.findViewById(R.id.expandablelistview);
        this.j = (Button) viewInflate.findViewById(R.id.btn_reverse_type);
        this.j.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.i.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                i.this.b();
            }
        });
        final com.dofun.carsetting.adapter.a aVar = new com.dofun.carsetting.adapter.a(this.g, this.k, this.l, this.b, this.c);
        this.i.setAdapter(aVar);
        this.i.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.dofun.carsetting.widget.i.2
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                i.this.d = i;
                aVar.a(i.this.b, i.this.c);
                return false;
            }
        });
        this.i.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.dofun.carsetting.widget.i.3
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
                i.this.d = i;
                i.this.e = i2;
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
        viewInflate.findViewById(R.id.canal).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.i.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                i.this.f.dismiss();
            }
        });
        viewInflate.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.i.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SystemProperties.set("persist.camera.reverse", String.valueOf(i.this.d));
                if (i.this.d == 1) {
                    i.this.c = i.this.e + 1;
                    SystemProperties.set("persist.camera.ahd360", String.valueOf(i.this.c));
                    if (i.this.d > 0) {
                        SystemProperties.set("persist.camera.mode0", "" + i.this.e);
                    }
                    if (i.this.d >= 3) {
                        SystemProperties.set("persist.camera.id", "0");
                    } else {
                        SystemProperties.set("persist.camera.id", "1");
                    }
                } else {
                    SystemProperties.set("persist.tw360.tvi", String.valueOf(i.this.e));
                }
                if (i.this.f != null) {
                    i.this.f.dismiss();
                }
                i.this.a(i.this.d);
                i.this.b = i.this.d;
                if (i.this.b == 3) {
                    SystemProperties.set("persist.camera.outtype", "1");
                    v.a((TWUtil) i.this.h, true);
                } else if (i.this.b == 2 || i.this.b == 0) {
                    v.a((TWUtil) i.this.h, false);
                }
                i.this.a(i.this.g, i.this.g.getString(R.string.reset_factory_dlg)).show();
                new Handler().postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.i.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        i.this.h.write(514, 1, 0);
                    }
                }, 2000L);
            }
        });
        this.f.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ProgressDialog a(Context context, String str) {
        ProgressDialog progressDialog = new ProgressDialog(context, 3);
        progressDialog.setMessage(str);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setOnCancelListener(null);
        return progressDialog;
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
                this.h.write(40730, 1, 0, "mount -o remount,rw /");
                this.h.write(40730, 1, 0, "chmod 666 /sys/block/mmcblk0boot0/force_ro");
                this.h.write(40730, 3, 0, "echo 0 >/sys/block/mmcblk0boot0/force_ro");
                this.h.write(40730, 3, 0, "dd if=/system/etc/topway/933/u-boot-spl-16k-sign.bin of=/dev/block/mmcblk0boot0");
                this.h.write(40730, 3, 0, "echo 1 >/sys/block/mmcblk0boot0/force_ro");
                this.h.write(40730, 1, 0, "pm hide com.tw.dvr");
                break;
            case 1:
                if (com.dofun.carsetting.f.b.a() != null && com.dofun.carsetting.f.b.a().contains("LDFY")) {
                    this.h.write(40730, 1, 0, "pm hide com.baony.tw360");
                } else {
                    this.h.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                }
                this.h.write(40730, 1, 0, "mount -o remount,rw /");
                this.h.write(40730, 1, 0, "chmod 666 /sys/block/mmcblk0boot0/force_ro");
                this.h.write(40730, 3, 0, "echo 0 >/sys/block/mmcblk0boot0/force_ro");
                this.h.write(40730, 3, 0, "dd if=/system/etc/topway/622/u-boot-spl-16k-sign.bin of=/dev/block/mmcblk0boot0");
                this.h.write(40730, 3, 0, "echo 1 >/sys/block/mmcblk0boot0/force_ro");
                this.h.write(40730, 1, 0, "pm hide com.tw.dvr");
                break;
            case 2:
            case 3:
                int iC = com.dofun.carsetting.f.a.a().c("com.tw.dvr");
                if (iC != -1) {
                    this.h.write(40730, 1, 0, "kill -9 " + iC);
                }
                if (com.dofun.carsetting.f.b.au()) {
                    this.h.write(40730, 1, 0, "pm unhide com.tw.dvr");
                }
                if (com.dofun.carsetting.f.b.a() != null && com.dofun.carsetting.f.b.a().contains("LDFY")) {
                    this.h.write(40730, 1, 0, "pm hide com.baony.tw360");
                } else {
                    this.h.write(40730, 1, 0, "pm hide cn.cardoor.zt360");
                }
                break;
        }
        this.h.write(40730, 1, 0, "sync");
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
