package com.dofun.carsetting.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import java.io.File;
import java.util.ArrayList;

/* JADX INFO: compiled from: ReverseTypeChooseDialog_T100.java */
/* JADX INFO: loaded from: classes.dex */
public class c extends Dialog implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f489a;
    boolean b;
    AlertDialog c;
    private Context d;
    private u e;
    private ExpandableListView f;
    private Button g;
    private ArrayList<String> h;
    private ArrayList<ArrayList<String>> i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private PackageManager o;
    private ComponentName p;
    private ComponentName q;
    private ComponentName r;
    private Handler s;

    private void g() {
    }

    public c(Context context) {
        super(context, R.style.custom_dialog);
        this.f489a = "ReverseTypeChooseDialog_T100";
        this.h = new ArrayList<>();
        this.i = new ArrayList<>();
        this.j = -1;
        this.k = -1;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.s = new Handler(Looper.getMainLooper()) { // from class: com.dofun.carsetting.widget.c.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    int i = message.what;
                    if (i == 260) {
                        byte[] bArr = (byte[]) message.obj;
                        if (bArr.length > 8) {
                            c.this.b = String.valueOf(bArr[8] & 255).equals("0");
                        } else {
                            c.this.b = String.valueOf(bArr[5] & 255).equals("0");
                        }
                    } else if (i == 65527) {
                        c.this.d();
                        try {
                            Thread.sleep(12000L);
                        } catch (Exception unused) {
                        }
                        if (com.dofun.carsetting.f.b.ba()) {
                            if (c.this.m == 1) {
                                c.this.e.write(65282, 0, 0, String.format("run_command\nfdt set /soc@03000000/car_reverse tvd_type <0x%x>\nfdt set /soc@03000000/car_reverse csi_used <0x%x>\nfdt save", Integer.valueOf(c.this.m), Integer.valueOf(c.this.n)));
                            } else if (c.this.m != 9) {
                                if (c.this.m != 8) {
                                    if (c.this.m != 5) {
                                        c.this.e.write(65282, 0, 0, String.format("run_command\nfdt set /soc@03000000/car_reverse tvd_type <0x%x>\nfdt set /soc@03000000/car_reverse tvd_resolution <0x%x>\nfdt save", Integer.valueOf(c.this.m), Integer.valueOf(c.this.n)));
                                    } else {
                                        String str = ((((((("" + String.format("fdt set /soc/vind@0/vinc@0 vinc0_isp_sel <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_csi_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_mipi_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_isp_tx_ch <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_rear_sensor_sel <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_front_sensor_sel <0x%x>\n", 0)) + String.format("fdt set /soc@03000000/car_reverse tvd_type <0x%x>\n", Integer.valueOf(c.this.m))) + String.format("fdt set /soc@03000000/car_reverse tvd_resolution  <0x%x>\n", Integer.valueOf(c.this.n));
                                        c.this.e.write(65282, 0, 0, "run_command\n" + str + "\nfdt save");
                                    }
                                } else {
                                    String str2 = ((((((("" + String.format("fdt set /soc/vind@0/vinc@0 vinc0_isp_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_csi_sel <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_mipi_sel <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_isp_tx_ch <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_rear_sensor_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_front_sensor_sel <0x%x>\n", 1)) + String.format("fdt set /soc@03000000/car_reverse tvd_type <0x%x>\n", Integer.valueOf(c.this.m))) + String.format("fdt set /soc@03000000/car_reverse csi_used <0x%x>\n", Integer.valueOf(c.this.n + 3));
                                    c.this.e.write(65282, 0, 0, "run_command\n" + str2 + "\nfdt save");
                                }
                            } else {
                                String str3 = (((((("" + String.format("fdt set /soc/vind@0/vinc@0 vinc0_isp_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_csi_sel <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_mipi_sel <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_isp_tx_ch <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_rear_sensor_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_front_sensor_sel <0x%x>\n", 1)) + String.format("fdt set /soc@03000000/car_reverse tvd_type <0x%x>\n", Integer.valueOf(c.this.m));
                                c.this.e.write(65282, 0, 0, "run_command\n" + str3 + "\nfdt save");
                            }
                        } else if (c.this.m != 0 && c.this.m != 6) {
                            if (c.this.m != 1) {
                                if (c.this.m != 5) {
                                    c.this.e.write(65282, 0, 0, String.format("run_command\nfdt set /soc@03000000/car_reverse tvd_type <0x%x>\nfdt set /soc@03000000/car_reverse tvd_resolution <0x%x>\nfdt save", Integer.valueOf(c.this.m), Integer.valueOf(c.this.n)));
                                } else {
                                    String str4 = ((((("" + String.format("fdt set /soc/vind@0/vinc@0 vinc0_csi_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc0_mipi_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_csi_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_mipi_sel <0x%x>\n", 1)) + String.format("fdt set /soc@03000000/car_reverse tvd_type <0x%x>\n", Integer.valueOf(c.this.m))) + String.format("fdt set /soc@03000000/car_reverse tvd_resolution  <0x%x>\n", Integer.valueOf(c.this.n));
                                    c.this.e.write(65282, 0, 0, "run_command\n" + str4 + "\nfdt save");
                                }
                            } else {
                                String str5 = ((((("" + String.format("fdt set /soc/vind@0/vinc@0 vinc0_csi_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc0_mipi_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_csi_sel <0x%x>\n", 1)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_mipi_sel <0x%x>\n", 1)) + String.format("fdt set /soc@03000000/car_reverse tvd_type <0x%x>\n", Integer.valueOf(c.this.m))) + String.format("fdt set /soc@03000000/car_reverse csi_used  <0x%x>\n", Integer.valueOf(c.this.n));
                                c.this.e.write(65282, 0, 0, "run_command\n" + str5 + "\nfdt save");
                            }
                        } else {
                            String str6 = ((((("" + String.format("fdt set /soc/vind@0/vinc@0 vinc0_csi_sel <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc0_mipi_sel <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_csi_sel <0x%x>\n", 0)) + String.format("fdt set /soc/vind@0/vinc@1 vinc1_mipi_sel <0x%x>\n", 0)) + String.format("fdt set /soc@03000000/car_reverse tvd_type <0x%x>\n", Integer.valueOf(c.this.m))) + String.format("fdt set /soc@03000000/car_reverse tvd_resolution  <0x%x>\n", Integer.valueOf(c.this.n));
                            c.this.e.write(65282, 0, 0, "run_command\n" + str6 + "\nfdt save");
                        }
                    }
                } catch (Exception unused2) {
                }
            }
        };
        this.d = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("xiang", "onCreate");
        this.e = u.a();
        a();
        g();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        if (new File("/system/priv-app/Baony360/Baony360.apk").exists()) {
            this.r = new ComponentName("com.baony.tw360", "com.baony.ui.activity.TopwayBVActivity");
        } else if (new File("/system/priv-app/Zt360/Zt360.apk").exists()) {
            this.r = new ComponentName("cn.cardoor.zt360", "cn.cardoor.zt360.ui.activity.RecordActivity");
        } else if (new File("/system/priv-app/Ld360.apk").exists()) {
            this.r = new ComponentName("com.megaview.avm", "com.megaview.avm.ui.activity.PermissionsActivity");
        }
        if (new File("/system/priv-app/Truck360/Truck360.apk").exists()) {
            this.p = new ComponentName("com.softwinner.dvr", "com.softwinner.dvr.ui.activity.RecordActivity");
            this.q = new ComponentName("com.softwinner.dvr", "com.softwinner.dvr.ui.activity.RecordAliasActivity");
        }
        this.o = this.d.getApplicationContext().getPackageManager();
        e();
    }

    private void e() {
        int i = this.m;
        if (i == 0) {
            if (new File("/system/priv-app/Baony360/Baony360.apk").exists()) {
                a("com.baony.tw360", true);
                b(this.p);
                b(this.q);
                a(this.r);
                return;
            }
            if (new File("/system/priv-app/Zt360/Zt360.apk").exists()) {
                a("cn.cardoor.zt360", true);
                b(this.p);
                b(this.q);
                a(this.r);
                return;
            }
            if (new File("/system/priv-app/Ld360/Ld360.apk").exists()) {
                a("com.megaview.avm", true);
                b(this.p);
                b(this.q);
                a(this.r);
                return;
            }
            return;
        }
        switch (i) {
            case 5:
                break;
            case 6:
                a("com.softwinner.dvr", true);
                a(this.q);
                b(this.p);
                b(this.r);
                return;
            default:
                switch (i) {
                    case 8:
                    case 9:
                        break;
                    default:
                        a("com.baony.tw360", false);
                        a("cn.cardoor.zt360", false);
                        a("com.megaview.avm", false);
                        b(this.r);
                        a("com.softwinner.dvr", false);
                        b(this.p);
                        b(this.q);
                        break;
                }
                return;
        }
        a("com.softwinner.dvr", true);
        a(this.p);
        b(this.q);
        b(this.r);
    }

    public void a(String str, boolean z) {
        try {
            PackageManager packageManager = this.d.getPackageManager();
            if (z) {
                packageManager.setApplicationEnabledSetting(str, 1, 0);
            } else {
                packageManager.setApplicationEnabledSetting(str, 2, 0);
            }
        } catch (Exception unused) {
        }
    }

    private void a(ComponentName componentName) {
        try {
            this.o.setComponentEnabledSetting(componentName, 1, 1);
        } catch (Exception unused) {
        }
    }

    private void b(ComponentName componentName) {
        try {
            this.o.setComponentEnabledSetting(componentName, 2, 1);
        } catch (Exception unused) {
        }
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.d).inflate(R.layout.dialog_revertype, (ViewGroup) null);
        setContentView(viewInflate);
        ((ImageView) viewInflate.findViewById(R.id.iv_dialog_close)).setOnClickListener(this);
        if (this.e != null) {
            this.e.addHandler(this.f489a, this.s);
            this.m = this.e.write(65523, 0, 0, "car_reverse\ntvd_type");
            this.l = com.dofun.carsetting.f.b.b("sys/class/car_reverse/csiuse");
            this.n = this.e.write(65523, 0, 0, "car_reverse\ntvd_resolution");
            this.e.write(260, 255);
        }
        if (com.dofun.carsetting.f.b.ba()) {
            if (this.m == 1) {
                this.j = 0;
                this.n = this.l;
            } else if (this.m == 8) {
                this.j = 1;
                this.n = this.l - 3;
            } else if (this.m == 9) {
                this.j = 2;
                this.n = 0;
            } else if (this.m == 5) {
                this.j = 3;
                this.n = this.l;
            }
        } else if (c()) {
            if (this.m == 0) {
                this.j = 0;
            } else if (this.m == 1) {
                this.j = 1;
                this.n = this.l;
            } else if (this.m == 5) {
                this.j = 2;
            } else if (this.m == 6) {
                this.j = 3;
            }
        } else if (this.m == 1) {
            this.j = 0;
            this.n = this.l;
        } else if (this.m == 5) {
            this.j = 1;
        }
        this.k = this.n;
        this.h = new ArrayList<>();
        if (com.dofun.carsetting.f.b.ba()) {
            for (CharSequence charSequence : this.d.getResources().getTextArray(R.array.t100_sendfm_reverse_csi_less255_type_entries)) {
                this.h.add((String) charSequence);
            }
        } else if (c()) {
            for (CharSequence charSequence2 : this.d.getResources().getTextArray(R.array.t100_reverse_csi_less255_type_entries3)) {
                this.h.add((String) charSequence2);
            }
        } else {
            for (CharSequence charSequence3 : this.d.getResources().getTextArray(R.array.t100_reverse_csi_less255_type_entries)) {
                this.h.add((String) charSequence3);
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (CharSequence charSequence4 : this.d.getResources().getTextArray(R.array.new_reverse_cis1_less1080_entries)) {
            arrayList.add((String) charSequence4);
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (CharSequence charSequence5 : this.d.getResources().getTextArray(R.array.new_reverse_cis2_csiless255_entries)) {
            arrayList2.add((String) charSequence5);
        }
        ArrayList<String> arrayList3 = new ArrayList<>();
        for (CharSequence charSequence6 : this.d.getResources().getTextArray(R.array.new_reverse_cis1_ts18_entries)) {
            arrayList3.add((String) charSequence6);
        }
        this.i = new ArrayList<>();
        if (com.dofun.carsetting.f.b.ba()) {
            this.i.add(arrayList2);
            this.i.add(arrayList);
            this.i.add(arrayList3);
            this.i.add(arrayList);
        } else if (c()) {
            this.i.add(arrayList);
            this.i.add(arrayList2);
            this.i.add(arrayList);
            this.i.add(arrayList);
        } else {
            this.i.add(arrayList2);
            this.i.add(arrayList);
        }
        this.g = (Button) viewInflate.findViewById(R.id.btn_reverse_type);
        this.g.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.c.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.f();
            }
        });
        this.f = (ExpandableListView) viewInflate.findViewById(R.id.expandablelistview);
        final com.dofun.carsetting.adapter.a aVar = new com.dofun.carsetting.adapter.a(this.d, this.h, this.i, this.j, this.n);
        this.f.setAdapter(aVar);
        this.f.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.dofun.carsetting.widget.c.3
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                c.this.j = i;
                aVar.a(c.this.j, c.this.n);
                return false;
            }
        });
        this.f.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.dofun.carsetting.widget.c.4
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
                c.this.j = i;
                c.this.k = i2;
                aVar.a(c.this.j, c.this.k);
                return false;
            }
        });
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = (int) this.d.getResources().getDimension(R.dimen.dialog_gesture_tip_width);
            attributes.height = (int) this.d.getResources().getDimension(R.dimen.dialog_gesture_tip_height);
            window.setAttributes(attributes);
        }
        setCanceledOnTouchOutside(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        View viewInflate = LayoutInflater.from(this.d).inflate(R.layout.dialog_revertype_choose, (ViewGroup) null);
        String str = this.h.get(this.j) + "/" + this.i.get(this.j).get(this.k);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.d, R.style.dialogNoBg);
        ((TextView) viewInflate.findViewById(R.id.title)).setText(str);
        builder.setView(viewInflate);
        this.c = builder.create();
        viewInflate.findViewById(R.id.canal).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.c.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.c.dismiss();
            }
        });
        viewInflate.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.c.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.n = c.this.k;
                if (com.dofun.carsetting.f.b.ba()) {
                    if (c.this.j == 0) {
                        c.this.m = 1;
                    } else if (c.this.j == 1) {
                        c.this.m = 8;
                    } else if (c.this.j == 2) {
                        c.this.m = 9;
                        c.this.n = 0;
                    } else if (c.this.j == 3) {
                        c.this.m = 5;
                    }
                } else if (c.this.c()) {
                    if (c.this.j == 0) {
                        c.this.m = 0;
                    } else if (c.this.j == 1) {
                        c.this.m = 1;
                    } else if (c.this.j == 2) {
                        c.this.m = 5;
                    } else if (c.this.j == 3) {
                        c.this.m = 6;
                    }
                } else if (c.this.j == 0) {
                    c.this.m = 1;
                } else if (c.this.j == 1) {
                    c.this.m = 5;
                }
                if ((c.this.m == 1 || c.this.m == 2) && !c.this.b) {
                    c.this.e.write(260, 7, 0);
                }
                c.this.a(c.this.d, c.this.d.getString(R.string.reset_factory_dlg)).show();
                c.this.b();
                c.this.s.removeMessages(65527);
                c.this.s.sendEmptyMessageDelayed(65527, 200L);
            }
        });
        this.c.show();
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

    protected void b() {
        Intent intent = new Intent();
        intent.setAction("com.unisound.unicar.kill.allround");
        this.d.sendBroadcast(intent);
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
        if (this.e != null) {
            this.e.close();
            this.e = null;
        }
    }

    public boolean c() {
        return com.dofun.carsetting.f.b.aQ() >= 4;
    }
}
