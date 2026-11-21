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

/* JADX INFO: compiled from: ReverseTypeChooseDialog_T527.java */
/* JADX INFO: loaded from: classes.dex */
public class e extends Dialog implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f503a;
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

    private void f() {
    }

    public e(Context context) {
        super(context, R.style.custom_dialog);
        this.f503a = "ReverseTypeChooseDialog_T527";
        this.h = new ArrayList<>();
        this.i = new ArrayList<>();
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.s = new Handler(Looper.getMainLooper()) { // from class: com.dofun.carsetting.widget.e.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    int i = message.what;
                    if (i == 260) {
                        byte[] bArr = (byte[]) message.obj;
                        if (bArr.length > 8) {
                            e.this.b = String.valueOf(bArr[8] & 255).equals("0");
                        } else {
                            e.this.b = String.valueOf(bArr[5] & 255).equals("0");
                        }
                    } else if (i == 65527) {
                        e.this.c();
                        try {
                            Thread.sleep(12000L);
                        } catch (Exception unused) {
                        }
                        if (e.this.l == 255) {
                            if (e.this.m == 2) {
                                e.this.e.write(65282, 0, 0, String.format("run_command\nfdt set /soc/car_reverse tvd_type <0x%x>\nfdt save", Integer.valueOf(e.this.m)));
                            } else {
                                e.this.e.write(65282, 0, 0, String.format("run_command\nfdt set /soc/car_reverse tvd_type <0x%x>\nfdt set /soc/car_reverse tvd_resolution <0x%x>\nfdt save", Integer.valueOf(e.this.m), Integer.valueOf(e.this.n)));
                            }
                        } else if (e.this.l < 255) {
                            if (e.this.m == 1) {
                                e.this.e.write(65282, 0, 0, String.format("run_command\nfdt set /soc/car_reverse tvd_type <0x%x>\nfdt set /soc/car_reverse csi_used <0x%x>\nfdt save", Integer.valueOf(e.this.m), Integer.valueOf(e.this.n)));
                            } else {
                                e.this.e.write(65282, 0, 0, String.format("run_command\nfdt set /soc/car_reverse tvd_type <0x%x>\nfdt set /soc/car_reverse tvd_resolution <0x%x>\nfdt save", Integer.valueOf(e.this.m), Integer.valueOf(e.this.n)));
                            }
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
        this.e = u.a();
        a();
        f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
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
        d();
    }

    private void d() {
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
            }
            return;
        }
        switch (i) {
            case 5:
                a("com.softwinner.dvr", true);
                a(this.p);
                b(this.q);
                b(this.r);
                break;
            case 6:
            case 7:
                a("com.softwinner.dvr", true);
                a(this.q);
                b(this.p);
                b(this.r);
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
    }

    public void a(String str, boolean z) {
        try {
            PackageManager packageManager = this.d.getApplicationContext().getPackageManager();
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
        int i = 0;
        if (this.e != null) {
            this.e.addHandler(this.f503a, this.s);
            this.m = this.e.write(65523, 0, 0, "car_reverse\ntvd_type");
            this.l = com.dofun.carsetting.f.b.b("sys/class/car_reverse/csiuse");
            this.n = this.e.write(65523, 0, 0, "car_reverse\ntvd_resolution");
            this.e.write(260, 255);
        }
        if (this.l < 255) {
            if (this.m != 0) {
                if (this.m == 1) {
                    this.n = this.l;
                } else if (this.m == 5 || this.m == 6 || this.m == 7) {
                    this.m -= 3;
                }
            }
        } else if (this.m != 0) {
            int i2 = this.m;
        }
        this.h = new ArrayList<>();
        if (this.l < 255) {
            for (CharSequence charSequence : this.d.getResources().getTextArray(R.array.t527_reverse_csi_less255_type_entries)) {
                this.h.add((String) charSequence);
            }
        } else {
            for (CharSequence charSequence2 : this.d.getResources().getTextArray(R.array.reverse_csi_more_255_type_entries)) {
                this.h.add((String) charSequence2);
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (CharSequence charSequence3 : this.d.getResources().getTextArray(R.array.new_reverse_cis1_less1080_entries)) {
            arrayList.add((String) charSequence3);
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        if (this.l < 255) {
            CharSequence[] textArray = this.d.getResources().getTextArray(R.array.new_reverse_cis2_csiless255_entries);
            int length = textArray.length;
            while (i < length) {
                arrayList2.add((String) textArray[i]);
                i++;
            }
        } else {
            CharSequence[] textArray2 = this.d.getResources().getTextArray(R.array.new_reverse_cis2_csimore255_entries);
            int length2 = textArray2.length;
            while (i < length2) {
                arrayList2.add((String) textArray2[i]);
                i++;
            }
        }
        this.i = new ArrayList<>();
        this.i.add(arrayList);
        this.i.add(arrayList2);
        this.i.add(arrayList);
        this.i.add(arrayList);
        this.i.add(arrayList);
        this.f = (ExpandableListView) viewInflate.findViewById(R.id.expandablelistview);
        this.g = (Button) viewInflate.findViewById(R.id.btn_reverse_type);
        this.g.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.e.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                e.this.e();
            }
        });
        final com.dofun.carsetting.adapter.a aVar = new com.dofun.carsetting.adapter.a(this.d, this.h, this.i, this.m, this.n);
        this.f.setAdapter(aVar);
        this.f.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.dofun.carsetting.widget.e.3
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i3, long j) {
                e.this.j = i3;
                aVar.a(e.this.m, e.this.n);
                return false;
            }
        });
        this.f.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.dofun.carsetting.widget.e.4
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i3, int i4, long j) {
                e.this.j = i3;
                e.this.k = i4;
                aVar.a(e.this.j, e.this.k);
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
    public void e() {
        String str;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.d, R.style.dialogNoBg);
        if (this.j == 2 || this.j == 3) {
            str = this.h.get(this.j);
        } else {
            str = this.h.get(this.j) + "/" + this.i.get(this.j).get(this.k);
        }
        View viewInflate = LayoutInflater.from(this.d).inflate(R.layout.dialog_revertype_choose, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.title)).setText(str);
        if (this.l < 255) {
            ((TextView) viewInflate.findViewById(R.id.message)).setText(this.j != 1 ? this.d.getString(R.string.camera12_prompt) : this.d.getString(R.string.camera10_prompt));
            ((ImageView) viewInflate.findViewById(R.id.icon)).setImageDrawable(this.j != 1 ? this.d.getDrawable(R.drawable.pin_icon12) : this.d.getDrawable(R.drawable.pin_icon10));
        } else {
            ((TextView) viewInflate.findViewById(R.id.message)).setText(this.j == 2 ? this.d.getString(R.string.camera10_prompt) : this.d.getString(R.string.camera12_prompt));
            if (this.j == 0) {
                ((ImageView) viewInflate.findViewById(R.id.icon)).setImageDrawable(this.d.getDrawable(R.drawable.pin_icon12));
            } else if (this.j == 1) {
                ((ImageView) viewInflate.findViewById(R.id.icon)).setImageDrawable(this.d.getDrawable(R.drawable.pin_icon121));
            } else {
                ((ImageView) viewInflate.findViewById(R.id.icon)).setImageDrawable(this.d.getDrawable(R.drawable.pin_icon10));
            }
        }
        builder.setView(viewInflate);
        this.c = builder.create();
        viewInflate.findViewById(R.id.canal).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.e.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                e.this.c.dismiss();
            }
        });
        viewInflate.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.e.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                e.this.m = e.this.j;
                e.this.n = e.this.k;
                if (e.this.l < 255) {
                    if (e.this.m == 2) {
                        e.this.m = 5;
                    } else if (e.this.m == 3) {
                        e.this.m = 6;
                    } else if (e.this.m == 4) {
                        e.this.m = 7;
                    }
                }
                if ((e.this.m == 1 || e.this.m == 2) && !e.this.b) {
                    e.this.e.write(260, 7, 0);
                }
                e.this.a(e.this.d, e.this.d.getString(R.string.reset_factory_dlg)).show();
                e.this.b();
                e.this.s.removeMessages(65527);
                e.this.s.sendEmptyMessageDelayed(65527, 2000L);
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
}
