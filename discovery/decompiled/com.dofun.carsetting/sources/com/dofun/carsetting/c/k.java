package com.dofun.carsetting.c;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.CFile;
import com.dofun.carsetting.f.u;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: compiled from: TlinkUpdateDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class k extends AlertDialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f174a;
    private u b;
    private TextView c;
    private ImageView d;
    private ListView e;
    private LinearLayout f;
    private TextView g;
    private TextView h;
    private LinearLayout i;
    private com.dofun.carsetting.adapter.f j;
    private ArrayList<CFile> k;
    private File[] l;
    private boolean m;
    private String n;
    private long o;
    private com.dofun.carsetting.b.a p;

    @SuppressLint({"HandlerLeak"})
    private Handler q;

    public k(Context context) {
        super(context, R.style.custom_dialog);
        this.l = new File[0];
        this.m = false;
        this.n = "/system/bin/sdCarplaySvc";
        this.p = com.dofun.carsetting.b.a.a();
        this.q = new Handler() { // from class: com.dofun.carsetting.c.k.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    if (message.what != 65521) {
                        return;
                    }
                    k.this.b.write(514, 1, 0);
                } catch (Exception unused) {
                }
            }
        };
        this.f174a = context;
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = u.a();
        if (this.b != null) {
            this.b.addHandler("TlinkUpdateDialog", this.q);
        }
        a();
        b();
    }

    private void b() {
        FilenameFilter filenameFilter = new FilenameFilter() { // from class: com.dofun.carsetting.c.k.2
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str) {
                return TextUtils.equals("sdcarplaysvc", str.toLowerCase(Locale.US));
            }
        };
        this.k = new ArrayList<>();
        for (int i = 0; i < this.p.A.length; i++) {
            File[] fileArrListFiles = new File("/" + this.p.A[i]).listFiles(filenameFilter);
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                this.k.add(new CFile(this.p.A[i], fileArrListFiles));
            }
        }
        for (int i2 = 0; i2 < this.p.B.length; i2++) {
            File[] fileArrListFiles2 = new File("/" + this.p.B[i2]).listFiles(filenameFilter);
            if (fileArrListFiles2 != null && fileArrListFiles2.length > 0) {
                this.k.add(new CFile(this.p.B[i2], fileArrListFiles2));
            }
        }
        File[] fileArrListFiles3 = new File("/sdcard").listFiles(filenameFilter);
        if (fileArrListFiles3 != null && fileArrListFiles3.length > 0) {
            this.k.add(new CFile("SDCARD", fileArrListFiles3));
        }
        if (this.k.size() <= 0) {
            a(this.f174a.getResources().getString(R.string.update_file_not_found2));
        }
        if (this.k.size() > 0) {
            CharSequence[] charSequenceArr = new CharSequence[this.k.size()];
            for (int i3 = 0; i3 < this.k.size(); i3++) {
                charSequenceArr[i3] = this.k.get(i3).cs;
            }
            this.j = new com.dofun.carsetting.adapter.f(this.f174a, charSequenceArr);
            this.e.setAdapter((ListAdapter) this.j);
        }
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.f174a).inflate(R.layout.dialog_tlink_update, (ViewGroup) null);
        setContentView(viewInflate);
        this.c = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        this.d = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        this.e = (ListView) viewInflate.findViewById(R.id.item_list);
        this.f = (LinearLayout) viewInflate.findViewById(R.id.ll_update_confirm);
        this.g = (TextView) viewInflate.findViewById(R.id.tv_update_cancel);
        this.h = (TextView) viewInflate.findViewById(R.id.tv_update_confirm);
        this.i = (LinearLayout) viewInflate.findViewById(R.id.ll_update_tv);
        this.c.setText(this.f174a.getResources().getString(R.string.tlink_update));
        setCanceledOnTouchOutside(true);
        this.e.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.c.k.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                k.this.l = ((CFile) k.this.k.get(i)).files;
                k.this.j.a(i);
                k.this.f.setVisibility(0);
            }
        });
        this.d.setOnClickListener(new a());
        this.g.setOnClickListener(new a());
        this.h.setOnClickListener(new a());
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) this.f174a.getResources().getDimension(R.dimen.dialog_check_update_info_width);
        attributes.height = (int) this.f174a.getResources().getDimension(R.dimen.dialog_check_update_info_height);
        Log.d("TlinkUpdateDialog", "init():lp.width:" + attributes.width + " lp.height:" + attributes.height);
        window.setAttributes(attributes);
    }

    /* JADX INFO: compiled from: TlinkUpdateDialog.java */
    private class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id != R.id.iv_dialog_close) {
                switch (id) {
                    case R.id.tv_update_cancel /* 2131231333 */:
                        if (!k.this.m) {
                            k.this.j.a(-1);
                            k.this.f.setVisibility(8);
                        }
                        break;
                    case R.id.tv_update_confirm /* 2131231334 */:
                        new Thread(new Runnable() { // from class: com.dofun.carsetting.c.k.a.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Looper.prepare();
                                k.this.m = true;
                                k.this.setCancelable(false);
                                k.this.setCanceledOnTouchOutside(false);
                                k.this.o = k.this.b(k.this.n);
                                k.this.c();
                                k.this.d();
                                Looper.loop();
                            }
                        }).start();
                        k.this.i.setVisibility(0);
                        break;
                }
            }
            if (k.this.m) {
                return;
            }
            k.this.onBackPressed();
        }
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        if (this.b != null) {
            this.b.removeHandler("TlinkUpdateDialog");
            this.b.close();
            this.b = null;
        }
    }

    private void a(String str) {
        Toast.makeText(this.f174a, str, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        for (File file : this.l) {
            if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                this.b.write(40730, 1, 0, "mount -o remount,rw /system_tw");
            } else {
                this.b.write(40730, 1, 0, "mount -o remount,rw /");
            }
            this.b.write(40730, 1, 0, "cp -rf " + file.getAbsolutePath() + " " + this.n);
            u uVar = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("chmod 755 -R ");
            sb.append(this.n);
            uVar.write(40730, 1, 0, sb.toString());
            if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                this.b.write(40730, 3, 0, "mount -o remount,ro /system_tw");
            } else {
                this.b.write(40730, 3, 0, "mount -o remount,ro /");
            }
            this.b.write(40730, 3, 0, "sync");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        if (b(this.n) - this.o > 0) {
            a(this.f174a.getResources().getString(R.string.up_s));
            this.q.removeMessages(65521);
            this.q.sendEmptyMessageDelayed(65521, 2000L);
        } else {
            a(this.f174a.getResources().getString(R.string.up_f));
            this.m = false;
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            this.i.setVisibility(4);
            this.f.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long b(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.lastModified();
        }
        return 0L;
    }
}
