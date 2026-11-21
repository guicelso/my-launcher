package com.dofun.carsetting.widget;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.dofun.carsetting.R;
import com.dofun.carsetting.f.u;
import java.util.ArrayList;

/* JADX INFO: compiled from: ReverseTypeChooseDialog_T919.java */
/* JADX INFO: loaded from: classes.dex */
public class f extends Dialog implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f510a;
    private Context b;
    private u c;
    private ExpandableListView d;
    private Button e;
    private ArrayList<com.dofun.carsetting.adapter.a.a> f;
    private int g;
    private int h;
    private Handler i;

    public f(Context context) {
        super(context, R.style.custom_dialog);
        this.f510a = "ReverseTypeChooseDialog_T919";
        this.f = new ArrayList<>();
        this.g = 0;
        this.h = 0;
        this.i = new Handler(Looper.getMainLooper()) { // from class: com.dofun.carsetting.widget.f.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    int i = message.what;
                } catch (Exception unused) {
                }
            }
        };
        this.b = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("xiang", "onCreate");
        this.c = u.a();
        a();
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.b).inflate(R.layout.dialog_revertype, (ViewGroup) null);
        setContentView(viewInflate);
        ((ImageView) viewInflate.findViewById(R.id.iv_dialog_close)).setOnClickListener(this);
        if (this.c != null) {
            this.c.addHandler(this.f510a, this.i);
            this.h = SystemProperties.getInt("persist.camera.reverse", -1);
        }
        this.f = new ArrayList<>();
        com.dofun.carsetting.adapter.a.a aVar = new com.dofun.carsetting.adapter.a.a("360", 0);
        aVar.a(SystemProperties.getInt("persist.camera.tp2815", 0));
        ArrayList<com.dofun.carsetting.adapter.a.a.C0012a> arrayList = new ArrayList<>();
        arrayList.add(new com.dofun.carsetting.adapter.a.a.C0012a("AHD 720P 25fps", 0));
        arrayList.add(new com.dofun.carsetting.adapter.a.a.C0012a("AHD 720P 30fps", 1));
        arrayList.add(new com.dofun.carsetting.adapter.a.a.C0012a("AHD 1080P 25fps", 4));
        arrayList.add(new com.dofun.carsetting.adapter.a.a.C0012a("AHD 1080P 30fps", 5));
        arrayList.add(new com.dofun.carsetting.adapter.a.a.C0012a("TVI 720P 25fps", 2));
        arrayList.add(new com.dofun.carsetting.adapter.a.a.C0012a("TVI 720P 30fps", 3));
        arrayList.add(new com.dofun.carsetting.adapter.a.a.C0012a("TVI 1080P 25fps", 6));
        arrayList.add(new com.dofun.carsetting.adapter.a.a.C0012a("TVI 1080P 30fps", 7));
        aVar.a(arrayList);
        this.f.add(aVar);
        com.dofun.carsetting.adapter.a.a aVar2 = new com.dofun.carsetting.adapter.a.a(this.b.getResources().getString(R.string.normal), 1);
        aVar2.a(SystemProperties.getInt("persist.camera.mode0", 0));
        ArrayList<com.dofun.carsetting.adapter.a.a.C0012a> arrayList2 = new ArrayList<>();
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a(this.b.getResources().getString(R.string.reverse_ahd_ntsc), 0));
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a(this.b.getResources().getString(R.string.reverse_ahd_pal), 1));
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a("AHD 720P 25fps", 2));
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a("AHD 720P 30fps", 3));
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a("AHD 1080P 25fps", 6));
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a("AHD 1080P 30fps", 7));
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a("TVI 720P 25fps", 4));
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a("TVI 720P 30fps", 5));
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a("TVI 1080P 25fps", 8));
        arrayList2.add(new com.dofun.carsetting.adapter.a.a.C0012a("TVI 1080P 30fps", 9));
        aVar2.a(arrayList2);
        this.f.add(aVar2);
        this.d = (ExpandableListView) viewInflate.findViewById(R.id.expandablelistview);
        this.e = (Button) viewInflate.findViewById(R.id.btn_reverse_type);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.f.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Log.d(f.this.f510a, "onClick: mReverseType=" + f.this.h + ",mCameraFomat=" + f.this.g);
                f.this.a(f.this.b, f.this.b.getString(R.string.reset_factory_dlg)).show();
                f.this.a(f.this.h);
                new Handler().postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.f.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        f.this.c.write(514, 1, 0);
                    }
                }, 2000L);
            }
        });
        final com.dofun.carsetting.adapter.c cVar = new com.dofun.carsetting.adapter.c(this.b, this.f, this.h);
        this.d.setAdapter(cVar);
        this.d.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.dofun.carsetting.widget.f.3
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                f.this.h = i;
                f.this.g = cVar.getGroup(i).a();
                Log.d(f.this.f510a, "onGroupClick: mReverseType=" + f.this.h);
                cVar.a(f.this.h);
                return false;
            }
        });
        this.d.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.dofun.carsetting.widget.f.4
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
                f.this.h = i;
                f.this.g = cVar.getChild(i, i2).b;
                Log.d(f.this.f510a, "onChildClick: mReverseType=" + f.this.h + ",mTvdresolution=" + f.this.g);
                cVar.b(i2);
                return false;
            }
        });
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = (int) this.b.getResources().getDimension(R.dimen.dialog_gesture_tip_width);
            attributes.height = (int) this.b.getResources().getDimension(R.dimen.dialog_gesture_tip_height);
            window.setAttributes(attributes);
        }
        setCanceledOnTouchOutside(true);
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
        if (this.c != null) {
            this.c.close();
            this.c = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        switch (i) {
            case 0:
                SystemProperties.set("persist.camera.tp2815", this.g + "");
                break;
            case 1:
                SystemProperties.set("persist.camera.mode0", this.g + "");
                break;
        }
        SystemProperties.set("persist.camera.reverse", i + "");
    }
}
