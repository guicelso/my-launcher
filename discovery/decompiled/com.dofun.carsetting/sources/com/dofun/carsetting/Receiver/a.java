package com.dofun.carsetting.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: compiled from: ThemeBroadcastReceiver.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    private final Context d;
    private Handler c = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f29a = false;
    public boolean b = false;
    private BroadcastReceiver e = new BroadcastReceiver() { // from class: com.dofun.carsetting.Receiver.a.1
        /* JADX WARN: Code duplicated, block: B:13:0x004b  */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            byte b = 0;
            int intExtra = intent.getIntExtra("retCode", 0);
            Log.e("ThemeBroadcastReceiver", "retCode = " + intExtra + ",action = " + action);
            int iHashCode = action.hashCode();
            if (iHashCode != -1303693690) {
                if (iHashCode == 397751669 && action.equals("com.dofun.theme.THEME_IMPORT_RESULT")) {
                    b = 1;
                } else {
                    b = -1;
                }
            } else if (!action.equals("com.dofun.theme.THEME_EXPORT_RESULT")) {
                b = -1;
            }
            switch (b) {
                case 0:
                    a.this.b(intExtra);
                    break;
                case 1:
                    a.this.a(intExtra);
                    break;
            }
        }
    };
    private Runnable f = new Runnable() { // from class: com.dofun.carsetting.Receiver.a.2
        @Override // java.lang.Runnable
        public void run() {
            a.this.a("theme export timeout");
            a.this.f29a = true;
        }
    };
    private Runnable g = new Runnable() { // from class: com.dofun.carsetting.Receiver.a.3
        @Override // java.lang.Runnable
        public void run() {
            a.this.a("import timeout！");
            a.this.b = true;
        }
    };

    public a(Context context) {
        this.d = context;
    }

    public void a(boolean z) {
        if (z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.dofun.theme.THEME_EXPORT_RESULT");
            intentFilter.addAction("com.dofun.theme.THEME_IMPORT_RESULT");
            this.d.registerReceiver(this.e, intentFilter);
            return;
        }
        this.d.unregisterReceiver(this.e);
        this.c.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        this.b = true;
        switch (i) {
            case -1:
                this.b = false;
                a("THEME_IMPORT_LOADING");
                return;
            case 0:
                a("THEME_IMPORT_SUCCESS");
                break;
            case 1:
                a("THEME_IMPORT_FAILURE_NOT_PERMISSION");
                break;
            case 2:
                a("THEME_IMPORT_FAILURE_NOT_USB");
                break;
            case 3:
                a("THEME_IMPORT_FAILURE_NOT_FOUND_FILE");
                break;
            case 4:
                a("THEME_IMPORT_FAILURE_FILE_PARSE_ERROR");
                break;
            case 5:
                a("THEME_IMPORT_FAILURE_FILE_COPY_ERROR");
                break;
            case 6:
                a("THEME_IMPORT_FAILURE_EXISTS");
                break;
            case 7:
                a("THEME_IMPORT_FAILURE_INSTALL_ERROR");
                break;
            case 8:
                a("THEME_IMPORT_FAILURE_SWITCH_ERROR");
                break;
        }
        this.c.removeCallbacks(this.g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i) {
        switch (i) {
            case -1:
                a("THEME_EXPORT_LOADING！");
                return;
            case 0:
                this.f29a = true;
                a("THEME_EXPORT_SUCCESS！");
                break;
            case 1:
                this.f29a = true;
                a("THEME_EXPORT_FAILURE_NOT_PERMISSION");
                break;
            case 2:
                this.f29a = true;
                a("THEME_EXPORT_FAILURE_NOT_USB");
                break;
            case 3:
                this.f29a = true;
                a("THEME_EXPORT_FAILURE_NOT_CUSTOM_THEME");
                break;
            case 4:
                this.f29a = true;
                a("THEME_EXPORT_FAILURE_FILE_WIRTE_ERROR");
                break;
        }
        this.c.removeCallbacks(this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        Log.e("ThemeBroadcastReceiver", "------------------------" + str);
    }

    public void a() {
        if (!a(this.d, "com.dofun.overseasvariety") && !a(this.d, "com.dofun.variety")) {
            Log.e("ThemeBroadcastReceiver", "exportTheme Not find Theme apk");
            this.f29a = true;
        } else {
            this.d.sendBroadcast(new Intent("com.dofun.theme.THEME_EXPORT_REQUEST"));
            this.c.removeCallbacks(this.f);
            this.c.postDelayed(this.f, 180000L);
        }
    }

    public void b() {
        if (!a(this.d, "com.dofun.overseasvariety") && !a(this.d, "com.dofun.variety")) {
            Log.e("ThemeBroadcastReceiver", "importTheme Not find Theme apk");
            this.b = true;
        } else {
            this.d.sendStickyBroadcast(new Intent("com.dofun.theme.THEME_IMPORT_REQUEST"));
            this.c.removeCallbacks(this.g);
            this.c.postDelayed(this.g, 10000L);
        }
    }

    private boolean a(Context context, String str) {
        try {
            return (TextUtils.isEmpty(str) || context.getPackageManager().getPackageInfo(str, 0) == null) ? false : true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
