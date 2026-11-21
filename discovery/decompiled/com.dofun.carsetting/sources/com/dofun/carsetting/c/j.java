package com.dofun.carsetting.c;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.CFile;
import com.dofun.carsetting.f.u;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: compiled from: SystemUpdateDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class j extends Dialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    @SuppressLint({"HandlerLeak"})
    public Handler f166a;
    private Context b;
    private u c;
    private String d;
    private int e;
    private String f;
    private String g;
    private String h;
    private boolean i;
    private ListView j;
    private com.dofun.carsetting.adapter.f k;
    private ArrayList<CFile> l;
    private Switch m;
    private LinearLayout n;
    private boolean o;
    private boolean p;
    private com.dofun.carsetting.b.a q;
    private TextView r;
    private View s;

    public j(Context context) {
        super(context, R.style.SeekBarDialogStyle);
        this.d = null;
        this.e = 0;
        this.f = "0";
        this.g = null;
        this.h = null;
        this.o = false;
        this.p = false;
        this.q = com.dofun.carsetting.b.a.a();
        this.f166a = new Handler() { // from class: com.dofun.carsetting.c.j.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    int i = message.what;
                    if (i != 266) {
                        if (i == 1541) {
                            j.this.c.write(65281);
                            return;
                        }
                        if (i != 65281) {
                            return;
                        }
                        String str = (String) message.obj;
                        if (j.this.r != null) {
                            j.this.r.setText(str);
                        }
                        if (j.this.s != null) {
                            j.this.s.setVisibility(8);
                            return;
                        }
                        return;
                    }
                    if (j.this.d == null) {
                        j.this.d = (String) message.obj;
                        if (j.this.d == null || j.this.d.length() <= 0) {
                            return;
                        }
                        String str2 = j.this.d.split("-")[3];
                        j.this.e = Integer.parseInt(str2.substring(4, 6), 16);
                        j.this.f = str2.substring(1, 2);
                        j.this.g = j.this.q.r.a(j.this.e, j.this.f);
                        j.this.h = j.this.q.r.a(j.this.e);
                        j.this.c();
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
        this.c = u.a();
        if (this.c != null) {
            this.c.addHandler("SystemUpdateDialog", this.f166a);
            this.c.write(266, 255);
        }
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        FilenameFilter filenameFilter = new FilenameFilter() { // from class: com.dofun.carsetting.c.j.2
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str) {
                String lowerCase = str.toLowerCase(Locale.US);
                String[] strArr = j.this.q.s.b;
                if (com.dofun.carsetting.f.b.O()) {
                    if (lowerCase.startsWith(j.this.q.s.c + ".")) {
                        Log.d("SystemUpdateDialog", "file1 ." + com.dofun.carsetting.f.b.a(file, lowerCase));
                        if (com.dofun.carsetting.f.b.a(file, lowerCase)) {
                            return false;
                        }
                    }
                    for (int i = 0; i < strArr.length; i++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("file2.");
                        sb.append(TextUtils.equals(strArr[i], lowerCase) && com.dofun.carsetting.f.b.a(file, lowerCase));
                        Log.d("SystemUpdateDialog", sb.toString());
                        if (TextUtils.equals(strArr[i], lowerCase) && com.dofun.carsetting.f.b.a(file, lowerCase)) {
                            return false;
                        }
                    }
                }
                for (String str2 : strArr) {
                    if (TextUtils.equals(str2, lowerCase)) {
                        return true;
                    }
                }
                if (lowerCase.startsWith(j.this.q.s.c + ".")) {
                    return true;
                }
                if (!TextUtils.isEmpty(j.this.q.s.d)) {
                    if (lowerCase.startsWith(j.this.q.s.d + ".")) {
                        return true;
                    }
                }
                if (TextUtils.equals(j.this.h, lowerCase)) {
                    return true;
                }
                if (!TextUtils.isEmpty(j.this.q.s.j) && lowerCase.startsWith(j.this.q.s.j.toLowerCase(Locale.US)) && lowerCase.endsWith(".zip")) {
                    return true;
                }
                return !TextUtils.isEmpty(j.this.q.s.k) && lowerCase.startsWith(j.this.q.s.k.toLowerCase(Locale.US)) && lowerCase.endsWith(".zip");
            }
        };
        this.l = new ArrayList<>();
        for (int i = 0; i < this.q.A.length; i++) {
            File[] fileArrListFiles = new File("/" + this.q.A[i]).listFiles(filenameFilter);
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                this.l.add(new CFile(this.q.A[i], fileArrListFiles));
            }
        }
        for (int i2 = 0; i2 < this.q.B.length; i2++) {
            File[] fileArrListFiles2 = new File("/" + this.q.B[i2]).listFiles(filenameFilter);
            if (fileArrListFiles2 != null && fileArrListFiles2.length > 0) {
                this.l.add(new CFile(this.q.B[i2], fileArrListFiles2));
            }
        }
        File[] fileArrListFiles3 = new File("/sdcard").listFiles(filenameFilter);
        if (fileArrListFiles3 != null && fileArrListFiles3.length > 0) {
            this.l.add(new CFile("SDCARD", fileArrListFiles3));
        }
        if (this.l.size() > 0) {
            CharSequence[] charSequenceArr = new CharSequence[this.l.size()];
            for (int i3 = 0; i3 < this.l.size(); i3++) {
                charSequenceArr[i3] = this.l.get(i3).cs;
            }
            this.k = new com.dofun.carsetting.adapter.f(this.b, charSequenceArr);
            this.j.setAdapter((ListAdapter) this.k);
        }
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.b).inflate(R.layout.dialog_system_update, (ViewGroup) null);
        setContentView(viewInflate);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        this.r = (TextView) viewInflate.findViewById(R.id.update_tv);
        this.s = viewInflate.findViewById(R.id.update_progress);
        this.j = (ListView) viewInflate.findViewById(R.id.item_list);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        this.m = (Switch) viewInflate.findViewById(R.id.btn_clear_data);
        this.n = (LinearLayout) viewInflate.findViewById(R.id.ll_update_tv);
        if (!com.dofun.carsetting.f.b.w()) {
            ((LinearLayout) viewInflate.findViewById(R.id.ll_clear_data)).setVisibility(0);
        }
        this.m.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.dofun.carsetting.c.j.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                j.this.i = z;
                if (j.this.q.s != null) {
                    j.this.q.s.p = z;
                }
            }
        });
        textView.setText(this.b.getResources().getString(R.string.system_update_title));
        setCanceledOnTouchOutside(true);
        this.j.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.c.j.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                j.this.q.s.g = ((CFile) j.this.l.get(i)).files;
                j.this.k.a(i);
                if (com.dofun.carsetting.f.b.U()) {
                    new com.dofun.carsetting.widget.j(j.this.b, j.this.b.getResources().getString(R.string.cpbox_systemupdate_info)).a();
                }
                new Thread(new Runnable() { // from class: com.dofun.carsetting.c.j.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (com.dofun.carsetting.f.b.f()) {
                            j.this.d();
                            j.this.c.write(40730, 1, 0, "rwrite /sys/class/tw/gtp/dsppower 0");
                            if (j.this.q.s.p) {
                                j.this.e();
                            }
                        }
                        j.this.p = true;
                        j.this.setCancelable(false);
                        j.this.setCanceledOnTouchOutside(false);
                        if (j.this.q.s.r) {
                            j.this.q.s.a(j.this.b, j.this.c, j.this.i, j.this);
                        } else {
                            j.this.q.s.a(j.this.b, j.this.c, j.this.i);
                        }
                    }
                }).start();
                j.this.n.setVisibility(0);
            }
        });
        imageView.setOnClickListener(new a());
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        this.b.getResources().getDisplayMetrics();
        attributes.width = (int) this.b.getResources().getDimension(R.dimen.dialog_system_update_width);
        attributes.height = (int) this.b.getResources().getDimension(R.dimen.dialog_system_update_height);
        Log.d("SystemUpdateDialog", "init():lp.width:" + attributes.width + " lp.height:" + attributes.height);
        window.setAttributes(attributes);
    }

    /* JADX INFO: compiled from: SystemUpdateDialog.java */
    private class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getId() == R.id.iv_dialog_close && !j.this.p) {
                j.this.onBackPressed();
            }
        }
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        if (this.c != null) {
            this.c.removeHandler("SystemUpdateDialog");
            this.c.close();
            this.c = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        if (new File("/system/app/Roundadas/Roundadas.apk").exists()) {
            com.dofun.carsetting.f.a.a("com.percherry.roundadas");
            return;
        }
        if (new File("/system/priv-app/Baony360/Baony360.apk").exists()) {
            com.dofun.carsetting.f.a.a("com.baony.tw360");
            return;
        }
        if (new File("/system/priv-app/Truck360/Truck360.apk").exists()) {
            com.dofun.carsetting.f.a.a("com.softwinner.dvr");
            com.dofun.carsetting.f.a.a("com.xinzhihui.mydvr");
        } else if (new File("/system/priv-app/Zt360/Zt360.apk").exists()) {
            com.dofun.carsetting.f.a.a("cn.cardoor.zt360");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.c.write(40730, 2, 0, "rm -r /sdcard/.apk");
        File[] fileArrListFiles = new File("/data/app").listFiles(new FileFilter() { // from class: com.dofun.carsetting.c.j.5
            @Override // java.io.FileFilter
            public boolean accept(File file) {
                if (file.isHidden() || !file.isDirectory()) {
                    return false;
                }
                return file.getName().startsWith("com.softwinner.dvr") || file.getName().startsWith("com.baony.tw360") || file.getName().startsWith("cn.cardoor.zt360") || file.getName().startsWith("com.percherry.roundadas");
            }
        });
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                this.c.write(40730, 2, 0, "rm -r " + file.getAbsolutePath());
            }
        }
    }

    public void b() {
        Message messageObtain = Message.obtain();
        messageObtain.what = 65281;
        messageObtain.obj = this.b.getResources().getString(R.string.system_update_tv);
        this.f166a.sendMessage(messageObtain);
        this.p = false;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }
}
