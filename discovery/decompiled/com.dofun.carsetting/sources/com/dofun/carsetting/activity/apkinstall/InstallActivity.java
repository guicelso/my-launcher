package com.dofun.carsetting.activity.apkinstall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.activity.apkinstall.a.c;
import com.dofun.carsetting.f.u;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class InstallActivity extends Activity implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private b f73a;
    private ListView b;
    private ArrayList<a> c = new ArrayList<>();
    private ArrayList<String> d = new ArrayList<>();
    private final String e = "/sdcard";
    private final String f = "/extsd";
    private final String g = "/extsd1";
    private final String h;
    private final String i;
    private final String j;
    private TextView k;
    private String l;
    private Button m;
    private Button n;
    private Button o;
    private Button p;
    private Button q;
    private Button r;
    private Button s;
    private Button t;
    private View u;
    private Handler v;
    private ServiceConnection w;
    private u x;

    public InstallActivity() {
        this.h = com.dofun.carsetting.f.b.G() ? "/usb" : (com.dofun.carsetting.f.b.O() || com.dofun.carsetting.f.b.F()) ? "/storage/usb0" : "/storage/usbdisk0";
        this.i = com.dofun.carsetting.f.b.G() ? "/usb1" : (com.dofun.carsetting.f.b.O() || com.dofun.carsetting.f.b.F()) ? "/storage/usb1" : "/storage/usbdisk1";
        this.j = com.dofun.carsetting.f.b.G() ? "/usb2" : (com.dofun.carsetting.f.b.O() || com.dofun.carsetting.f.b.F()) ? "/storage/usb2" : "/storage/usbdisk2";
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.v = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.3
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what == 274) {
                    if ((message.arg1 & 65536) == 65536) {
                        InstallActivity.this.getWindow().setBackgroundDrawableResource(R.color.key_normal);
                    }
                }
                return true;
            }
        });
        this.w = null;
        this.x = null;
    }

    private class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        String f88a;
        Drawable b;

        a(String str, Drawable drawable) {
            this.f88a = str;
            this.b = drawable;
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.install_app);
        this.k = (TextView) findViewById(R.id.current_path);
        this.u = findViewById(R.id.install_main);
        this.m = (Button) findViewById(R.id.defaultPathButton);
        this.n = (Button) findViewById(R.id.defaultPathButton2);
        this.o = (Button) findViewById(R.id.defaultPathButton3);
        this.p = (Button) findViewById(R.id.defaultPathButton4);
        this.q = (Button) findViewById(R.id.defaultPathButton5);
        this.r = (Button) findViewById(R.id.defaultPathButton6);
        this.s = (Button) findViewById(R.id.parentPathButton);
        this.t = (Button) findViewById(R.id.cpButton);
        this.f73a = new b(this);
        this.b = (ListView) findViewById(R.id.file_listview);
        this.b.setAdapter((ListAdapter) this.f73a);
        this.b.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                File file = new File((String) InstallActivity.this.d.get(i));
                if (file.canRead()) {
                    String absolutePath = file.getAbsolutePath();
                    if (absolutePath.startsWith("/sdcard") || absolutePath.startsWith("/extsd") || absolutePath.startsWith("/extsd1") || absolutePath.startsWith(InstallActivity.this.h) || absolutePath.startsWith(InstallActivity.this.i) || absolutePath.startsWith(InstallActivity.this.j)) {
                        if (file.isDirectory()) {
                            InstallActivity.this.a((String) InstallActivity.this.d.get(i));
                            return;
                        }
                        if (Build.VERSION.SDK_INT >= 24) {
                            com.dofun.carsetting.activity.apkinstall.a.b.a("==install==:N");
                            InstallActivity.this.a(InstallActivity.this, file);
                            return;
                        }
                        com.dofun.carsetting.activity.apkinstall.a.b.a("==install==:Not");
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setDataAndType(Uri.parse("file://" + absolutePath), "application/vnd.android.package-archive");
                        InstallActivity.this.startActivity(intent);
                    }
                }
            }
        });
        a("/sdcard");
        this.x = u.a();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.x != null) {
            this.x.start();
            this.x.addHandler("InstallActivity", this.v);
            this.x.write(274, 255);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, File file) {
        try {
            if (file.getName().contains("com.android.launcher")) {
                b(file.getAbsolutePath());
                return;
            }
            if (file.getName().contains("Bluetooth")) {
                c(file.getAbsolutePath());
                return;
            }
            Uri uriForFile = FileProvider.getUriForFile(context, "com.dofun.carsetting.files", file);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.addFlags(3);
            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
            Iterator<ResolveInfo> it = context.getPackageManager().queryIntentActivities(intent, 65536).iterator();
            while (it.hasNext()) {
                context.grantUriPermission(it.next().activityInfo.packageName, uriForFile, 3);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            com.dofun.carsetting.activity.apkinstall.a.b.a("==install==:" + e.toString());
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        Log.d("LZP", "filePath:" + str);
        File file = new File(str);
        String absolutePath = file.getAbsolutePath();
        if (absolutePath.startsWith("/sdcard") || absolutePath.startsWith("/extsd") || absolutePath.startsWith("/extsd1") || absolutePath.startsWith(this.h) || absolutePath.startsWith(this.i) || absolutePath.startsWith(this.j)) {
            this.k.setText(getString(R.string.current_path) + str);
            this.l = str;
            this.c.clear();
            this.d.clear();
            File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.4
                @Override // java.io.FileFilter
                public boolean accept(File file2) {
                    if (file2.isHidden()) {
                        return false;
                    }
                    return file2.isDirectory() || file2.getAbsolutePath().toLowerCase().endsWith(".apk");
                }
            });
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    this.c.add(new a(file2.getName(), null));
                    this.d.add(file2.getAbsolutePath());
                }
            }
            if ("/sdcard".equals(this.l) || "/extsd".equals(this.l) || "/extsd1".equals(this.l) || this.h.equals(this.l) || this.i.equals(this.l) || this.j.equals(this.l)) {
                this.s.setVisibility(8);
            } else {
                this.s.setVisibility(0);
            }
            this.f73a.notifyDataSetChanged();
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            File file = new File(this.l);
            String absolutePath = file.getAbsolutePath();
            if ("/sdcard".equals(absolutePath) || "/extsd".equals(absolutePath) || "/extsd1".equals(absolutePath) || this.h.equals(absolutePath) || this.i.equals(absolutePath) || this.j.equals(absolutePath)) {
                finish();
                return true;
            }
            a(file.getParent());
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    private class b extends BaseAdapter {
        private Context b;

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        public b(Context context) {
            this.b = null;
            this.b = context;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return InstallActivity.this.c.size();
        }

        public Drawable a(int i) {
            String str = (String) InstallActivity.this.d.get(i);
            if (new File(str).isDirectory()) {
                return InstallActivity.this.getResources().getDrawable(R.drawable.tw_ic_launcher_folder);
            }
            return b(str);
        }

        private class a {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            TextView f90a;
            ImageView b;

            private a() {
            }
        }

        private View a(ViewGroup viewGroup) {
            View viewInflate = LayoutInflater.from(this.b).inflate(R.layout.tw_file_item_raw, viewGroup, false);
            a aVar = new a();
            aVar.f90a = (TextView) viewInflate.findViewById(R.id.file_raw_layout);
            aVar.b = (ImageView) viewInflate.findViewById(R.id.imageView);
            viewInflate.setTag(aVar);
            return viewInflate;
        }

        private void a(View view, int i, ViewGroup viewGroup) {
            a aVar = (a) view.getTag();
            a aVar2 = (a) InstallActivity.this.c.get(i);
            if (aVar2.b == null) {
                aVar2.b = a(i);
            }
            aVar.f90a.setText(aVar2.f88a);
            aVar.f90a.setId(i);
            aVar.b.setBackgroundDrawable(aVar2.b);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = a(viewGroup);
            }
            a(view, i, viewGroup);
            return view;
        }

        public Resources a(String str) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException {
            Class<?> cls = Class.forName("android.content.res.AssetManager");
            Object objNewInstance = cls.getConstructor((Class[]) null).newInstance((Object[]) null);
            cls.getDeclaredMethod("addAssetPath", String.class).invoke(objNewInstance, str);
            Resources resources = InstallActivity.this.getResources();
            return (Resources) Resources.class.getConstructor(objNewInstance.getClass(), resources.getDisplayMetrics().getClass(), resources.getConfiguration().getClass()).newInstance(objNewInstance, resources.getDisplayMetrics(), resources.getConfiguration());
        }

        public Drawable b(String str) {
            try {
                return a(str).getDrawable(InstallActivity.this.getPackageManager().getPackageArchiveInfo(str, 1).applicationInfo.icon);
            } catch (Exception unused) {
                return InstallActivity.this.getResources().getDrawable(R.drawable.ic_launcher_settings);
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.w != null) {
            try {
                unbindService(this.w);
            } catch (Exception unused) {
            }
            this.w = null;
        }
        if (this.x != null) {
            this.x.removeHandler("InstallActivity");
        }
        super.onDestroy();
    }

    private void a(int i) {
        File file = new File(this.l);
        String absolutePath = file.getAbsolutePath();
        if (absolutePath.startsWith("/sdcard") || absolutePath.startsWith("/extsd") || absolutePath.startsWith("/extsd1") || absolutePath.startsWith(this.h) || absolutePath.startsWith(this.i) || absolutePath.startsWith(this.j)) {
            final File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.5
                @Override // java.io.FileFilter
                public boolean accept(File file2) {
                    return file2.getAbsolutePath().toLowerCase().endsWith(".apk");
                }
            });
            if (this.w != null) {
                try {
                    unbindService(this.w);
                } catch (Exception unused) {
                }
                this.w = null;
            }
            this.w = new ServiceConnection() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.6
                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName componentName) {
                }

                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    ((InstallService.a) iBinder).a().a(fileArrListFiles, new InstallService.c() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.6.1
                        @Override // com.dofun.carsetting.activity.apkinstall.InstallService.c
                        public void a(String str) {
                            Log.d("LZP", "packageName = " + str);
                        }
                    });
                }
            };
            startService(new Intent(this, (Class<?>) InstallService.class));
            bindService(new Intent(this, (Class<?>) InstallService.class), this.w, 1);
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, R.string.install_all);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (Settings.Secure.getInt(getContentResolver(), "install_non_market_apps", 0) > 0 || c.a("", 5, "")) {
            switch (menuItem.getItemId()) {
                case 1:
                    a(0);
                    break;
                case 2:
                    a(0);
                    break;
            }
            return true;
        }
        startActivity(new Intent("android.settings.SECURITY_SETTINGS"));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        u uVarA;
        if (this.l != null) {
            File file = new File(this.l);
            if (file.canRead() && file.isDirectory()) {
                String absolutePath = file.getAbsolutePath();
                if ("/sdcard".equals(absolutePath) || "/extsd".equals(absolutePath) || "/extsd1".equals(absolutePath) || "/usb".equals(absolutePath) || "/usb1".equals(absolutePath) || "/usb2".equals(absolutePath)) {
                    return;
                }
                if (absolutePath.equals("/sdcard/" + file.getName()) || (uVarA = u.a()) == null) {
                    return;
                }
                if (this.l.startsWith("/sdcard")) {
                    uVarA.write(40730, 2, 0, "mv " + this.l + " /sdcard/");
                } else {
                    uVarA.write(40730, 2, 0, "cp -r " + this.l + " /sdcard/");
                }
                uVarA.close();
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cpButton) {
            new AlertDialog.Builder(this, 4).setTitle(R.string.cp2inand).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.7
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    final ProgressDialog progressDialogA = c.a(InstallActivity.this, InstallActivity.this.getString(R.string.cp2inand));
                    new Thread(new Runnable() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            InstallActivity.this.a();
                            if (progressDialogA != null) {
                                progressDialogA.dismiss();
                            }
                        }
                    }).start();
                }
            }).setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null).show();
            return;
        }
        if (id != R.id.parentPathButton) {
            switch (id) {
                case R.id.defaultPathButton /* 2131230844 */:
                    a("/sdcard");
                    break;
                case R.id.defaultPathButton2 /* 2131230845 */:
                    a("/extsd");
                    break;
                case R.id.defaultPathButton3 /* 2131230846 */:
                    a("/extsd1");
                    break;
                case R.id.defaultPathButton4 /* 2131230847 */:
                    a(this.h);
                    break;
                case R.id.defaultPathButton5 /* 2131230848 */:
                    a(this.i);
                    break;
                case R.id.defaultPathButton6 /* 2131230849 */:
                    a(this.j);
                    break;
            }
            return;
        }
        a(new File(this.l).getParent());
    }

    private void b(final String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.install_apk).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                InstallActivity.this.x.write(40730, 1, 0, "mount -o rw,remount /system");
                InstallActivity.this.x.write(40730, 1, 0, "cp " + str + " /data/tw/Launcher2.apk");
                InstallActivity.this.x.write(40730, 1, 0, "xupdate u /data/tw/Launcher2.apk");
                new Handler().postDelayed(new Runnable() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        InstallActivity.this.x.write(40730, 1, 0, "reboot");
                    }
                }, 2000L);
            }
        }).setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                InstallActivity.this.finish();
            }
        });
        builder.create().setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.show();
    }

    private void c(final String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.install_apk).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                InstallActivity.this.x.write(40730, 1, 0, "mount -o rw,remount /system");
                InstallActivity.this.x.write(40730, 1, 0, "cp " + str + " /data/tw/com.tw.bt_305c.apk");
                InstallActivity.this.x.write(40730, 1, 0, "xupdate u /data/tw/com.tw.bt_305c.apk");
                new Handler().postDelayed(new Runnable() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        InstallActivity.this.x.write(40730, 1, 0, "reboot");
                    }
                }, 2000L);
            }
        }).setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.apkinstall.InstallActivity.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                InstallActivity.this.finish();
            }
        });
        builder.create().setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.show();
    }
}
