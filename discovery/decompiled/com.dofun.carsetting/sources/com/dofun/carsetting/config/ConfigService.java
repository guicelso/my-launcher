package com.dofun.carsetting.config;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.dofun.carsetting.a.b;
import com.dofun.carsetting.f.u;
import com.dofun.carsetting.f.w;
import com.dofun.carsetting.widget.ImportExportExtensionSettings;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ConfigService extends Service {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static DisplayMetrics f180a;
    public static Context b;
    private u c;
    private ImportExportExtensionSettings g;
    private com.dofun.carsetting.b.a h;
    private List<com.dofun.carsetting.config.a> d = new ArrayList();
    private RemoteCallbackList<b> e = new RemoteCallbackList<>();
    private a f = new a();
    private Handler i = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.config.ConfigService.3
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                int i = message.what;
                switch (i) {
                    case 43521:
                        ConfigService.this.b("/twdataconfig/tw_config.prop");
                        break;
                    case 43522:
                        ConfigService.this.a(ConfigService.this.c(message.obj != null ? (String) message.obj : null));
                        break;
                    default:
                        switch (i) {
                            case 65281:
                                for (int i2 = 0; i2 < ConfigService.this.d.size(); i2++) {
                                    com.dofun.carsetting.config.a aVar = (com.dofun.carsetting.config.a) ConfigService.this.d.get(i2);
                                    if (aVar.f186a.startsWith("#")) {
                                        Log.d("ConfigService", "MSG_LOAD_CONFIG_SUCCESS:config:key:" + aVar.f186a);
                                    } else {
                                        Log.d("ConfigService", "MSG_LOAD_CONFIG_SUCCESS:config:key:" + aVar.f186a + " value:" + aVar.b);
                                    }
                                }
                                break;
                            case 65282:
                            case 65283:
                            case 65284:
                                break;
                            default:
                                break;
                        }
                        break;
                }
            } catch (Exception e) {
                Log.d("ConfigService", "handleMessage:" + e.getMessage());
                return true;
            }
            return true;
        }
    });
    private BroadcastReceiver j = new BroadcastReceiver() { // from class: com.dofun.carsetting.config.ConfigService.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
                    Uri data = intent.getData();
                    Log.i("ConfigService", "packageUri: " + data);
                    if (data == null) {
                        return;
                    }
                    String schemeSpecificPart = data.getSchemeSpecificPart();
                    if (schemeSpecificPart == null) {
                        Log.e("ConfigService", "No package name");
                        return;
                    }
                    Intent launchIntentForPackage = ConfigService.this.getPackageManager().getLaunchIntentForPackage(schemeSpecificPart);
                    if (launchIntentForPackage == null || launchIntentForPackage.getComponent() == null) {
                        return;
                    }
                    ComponentName component = launchIntentForPackage.getComponent();
                    if (TextUtils.equals(schemeSpecificPart, "com.zjinnova.zlink")) {
                        Log.d("ConfigService", "ACTION_PACKAGE_ADDED:ComponentName:" + component);
                        com.dofun.carsetting.config.a aVar = new com.dofun.carsetting.config.a();
                        aVar.f186a = "tw.carplay.type";
                        if (component.toString().startsWith("ComponentInfo{com.zjinnova.zlink/com.suding")) {
                            SystemProperties.set("persist.sys.tw.link", "0");
                            aVar.b = "0";
                        } else if (component.toString().startsWith("ComponentInfo{com.zjinnova.zlink/com.zjinnova")) {
                            if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                                SystemProperties.set("persist.sys.tw.link", "3");
                            } else {
                                SystemProperties.set("persist.sys.tw.link", "2");
                            }
                            aVar.b = "1";
                        } else {
                            aVar.b = "0";
                        }
                        ConfigService.this.b(aVar);
                        ConfigService.this.i.removeMessages(43521);
                        ConfigService.this.i.sendEmptyMessageDelayed(43521, 100L);
                    }
                }
            } catch (Exception e) {
                Log.d("ConfigService", "mReceiver:" + e.getMessage());
            }
        }
    };

    public class a extends com.dofun.carsetting.a.a.AbstractBinderC0007a {
        public a() {
        }

        @Override // com.dofun.carsetting.a.a
        public void a(b bVar) {
            if (bVar != null) {
                ConfigService.this.e.register(bVar);
            }
        }

        @Override // com.dofun.carsetting.a.a
        public void a(String str, String str2) {
            Log.d("ConfigService", "setConfig1: key:" + str + " value:" + str2);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            ConfigService.this.b(new com.dofun.carsetting.config.a(str, str2));
            ConfigService.this.i.removeMessages(43521);
            ConfigService.this.i.sendEmptyMessageDelayed(43521, 100L);
        }

        @Override // com.dofun.carsetting.a.a
        public void a(String str) {
            Log.d("ConfigService", "getConfig: key:" + str);
            Message messageObtainMessage = ConfigService.this.i.obtainMessage();
            messageObtainMessage.what = 43522;
            messageObtainMessage.obj = str;
            ConfigService.this.i.sendMessage(messageObtainMessage);
        }

        @Override // com.dofun.carsetting.a.a
        public void b(b bVar) {
            ConfigService.this.e.unregister(bVar);
        }

        public void b(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            Log.d("ConfigService", "setConfig2: key:" + str + " value:" + str2);
            ConfigService.this.b(new com.dofun.carsetting.config.a(str, str2));
            ConfigService.this.i.removeMessages(43521);
            ConfigService.this.i.sendEmptyMessageDelayed(43521, 100L);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.f;
    }

    @Override // android.app.Service
    public void onCreate() throws Throwable {
        super.onCreate();
        this.c = u.a();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        registerReceiver(this.j, intentFilter);
        a("/twdataconfig/tw_config.prop");
        a();
    }

    private void a() throws Throwable {
        this.h = com.dofun.carsetting.b.a.a();
        this.h.b();
        f180a = getResources().getDisplayMetrics();
        b = getApplicationContext();
        if (!com.dofun.carsetting.f.b.at()) {
            Settings.System.putInt(b.getContentResolver(), "FLOAT_360", 0);
        }
        if (Settings.System.getInt(getContentResolver(), "FLOAT_360", 0) == 1) {
            com.dofun.carsetting.widget.a.a(b, f180a);
        } else {
            com.dofun.carsetting.widget.a.b(b, f180a);
        }
        Settings.System.putInt(b.getContentResolver(), "SHOW_VOLTAGE", Settings.System.getInt(b.getContentResolver(), "SHOW_VOLTAGE", 1));
        this.c.write(1292, 255);
        b();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            int intExtra = intent.getIntExtra("cmd", -1);
            String stringExtra = intent.getStringExtra("path");
            boolean booleanExtra = intent.getBooleanExtra("wipeData", false);
            boolean booleanExtra2 = intent.getBooleanExtra("isRestoreFactoryImport", false);
            Log.d("ConfigService", "onStartCommand: cmd=" + intExtra + ",pathString=" + stringExtra + ",isRestoreFactoryImport=" + booleanExtra2);
            if (booleanExtra2) {
                Log.d("ConfigService", "onStartCommand: " + stringExtra + "/" + this.h.t + "," + this.h.t);
                if (new File(stringExtra + "/" + this.h.t).exists()) {
                    this.g = new ImportExportExtensionSettings();
                    this.g.a();
                    this.g.a(getApplicationContext(), intExtra, stringExtra, false, booleanExtra);
                    this.g.a(stringExtra, booleanExtra2);
                }
            }
        }
        return super.onStartCommand(intent, i, i2);
    }

    private void a(final String str) {
        this.d.clear();
        new Thread(new Runnable() { // from class: com.dofun.carsetting.config.ConfigService.1
            /* JADX WARN: Code duplicated, block: B:10:0x002b  */
            /* JADX WARN: Code duplicated, block: B:52:0x0104 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:8:0x001e  */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v10, types: [java.io.BufferedReader] */
            /* JADX WARN: Type inference failed for: r1v19 */
            /* JADX WARN: Type inference failed for: r1v20 */
            /* JADX WARN: Type inference failed for: r1v5 */
            /* JADX WARN: Type inference failed for: r1v6 */
            /* JADX WARN: Type inference failed for: r1v7 */
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                ?? r1;
                BufferedReader bufferedReader;
                Exception e;
                String str2;
                StringBuilder sb;
                boolean zCanRead;
                File file = new File(str);
                if (file.exists() && (zCanRead = file.canRead())) {
                    r1 = zCanRead;
                    if (file.length() == 0) {
                        r1 = "/twdataconfig/tw_config.prop.backup";
                        if (new File("/twdataconfig/tw_config.prop.backup").canRead()) {
                            ConfigService.this.c.write(40730, 3, 0, "rm -rf /twdataconfig/tw_config.prop.backup");
                            ConfigService.this.c.write(40730, 3, 0, "mv /twdataconfig/tw_config.prop.backup/twdataconfig/tw_config.prop");
                            r1 = 438;
                            FileUtils.setPermissions(str, 438, -1, -1);
                        }
                    }
                } else {
                    r1 = "/twdataconfig/tw_config.prop.backup";
                    if (new File("/twdataconfig/tw_config.prop.backup").canRead()) {
                        ConfigService.this.c.write(40730, 3, 0, "rm -rf /twdataconfig/tw_config.prop.backup");
                        ConfigService.this.c.write(40730, 3, 0, "mv /twdataconfig/tw_config.prop.backup/twdataconfig/tw_config.prop");
                        r1 = 438;
                        FileUtils.setPermissions(str, 438, -1, -1);
                    }
                }
                try {
                    try {
                        bufferedReader = new BufferedReader(new FileReader(str));
                        while (true) {
                            try {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    ConfigService.this.i.sendEmptyMessage(65281);
                                    try {
                                        bufferedReader.close();
                                        return;
                                    } catch (Exception e2) {
                                        e = e2;
                                        str2 = "ConfigService";
                                        sb = new StringBuilder();
                                    }
                                } else if (!TextUtils.isEmpty(line)) {
                                    com.dofun.carsetting.config.a aVar = new com.dofun.carsetting.config.a();
                                    String strTrim = line.trim();
                                    if (strTrim.startsWith("#")) {
                                        aVar.f186a = strTrim;
                                    } else {
                                        String[] strArrSplit = strTrim.split("=");
                                        if (strArrSplit != null) {
                                            aVar.f186a = strArrSplit[0];
                                            aVar.b = strArrSplit[1];
                                        }
                                    }
                                    ConfigService.this.d.add(aVar);
                                }
                            } catch (Exception e3) {
                                e = e3;
                                Log.e("ConfigService", "loadConfig1:" + e.getMessage());
                                ConfigService.this.i.sendEmptyMessage(65282);
                                if (bufferedReader == null) {
                                    return;
                                }
                                try {
                                    bufferedReader.close();
                                    return;
                                } catch (Exception e4) {
                                    e = e4;
                                    str2 = "ConfigService";
                                    sb = new StringBuilder();
                                }
                            }
                            sb.append("loadConfig2:");
                            sb.append(e.getMessage());
                            Log.e(str2, sb.toString());
                            return;
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (r1 != 0) {
                            try {
                                r1.close();
                            } catch (Exception e5) {
                                Log.e("ConfigService", "loadConfig2:" + e5.getMessage());
                            }
                        }
                        throw th;
                    }
                } catch (Exception e6) {
                    bufferedReader = null;
                    e = e6;
                } catch (Throwable th2) {
                    th = th2;
                    r1 = 0;
                    if (r1 != 0) {
                        r1.close();
                    }
                    throw th;
                }
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final String str) {
        final File file = new File(str);
        new Thread(new Runnable() { // from class: com.dofun.carsetting.config.ConfigService.2
            /* JADX WARN: Code duplicated, block: B:35:0x011d A[Catch: Exception -> 0x0121, TryCatch #4 {Exception -> 0x0121, blocks: (B:20:0x00c4, B:21:0x00d4, B:31:0x0109, B:32:0x010c, B:35:0x011d, B:38:0x0123, B:39:0x0133), top: B:45:0x0044 }] */
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                BufferedWriter bufferedWriter;
                Throwable th;
                Exception e;
                String str2;
                if (file.exists()) {
                    ConfigService.this.c.write(40730, 3, 0, "cp -r " + file.getAbsolutePath() + " /twdataconfig/tw_config.prop.backup");
                    FileUtils.setPermissions("/twdataconfig/tw_config.prop.backup", 438, -1, -1);
                }
                try {
                    try {
                        bufferedWriter = new BufferedWriter(new FileWriter(str));
                        for (int i = 0; i < ConfigService.this.d.size(); i++) {
                            try {
                                try {
                                    com.dofun.carsetting.config.a aVar = (com.dofun.carsetting.config.a) ConfigService.this.d.get(i);
                                    if (!TextUtils.isEmpty(aVar.f186a)) {
                                        bufferedWriter.write(aVar.f186a.startsWith("#") ? aVar.f186a + "\n" : aVar.f186a + "=" + aVar.b + "\n");
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    Log.e("ConfigService", "saveConfig1:" + e.getMessage());
                                    ConfigService.this.i.sendEmptyMessage(65284);
                                    if (bufferedWriter != null) {
                                        bufferedWriter.close();
                                    }
                                    ConfigService.this.c.write(40730, 1, 0, "sync");
                                    str2 = "/twdataconfig/tw_config.prop";
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                if (bufferedWriter != null) {
                                    bufferedWriter.close();
                                }
                                ConfigService.this.c.write(40730, 1, 0, "sync");
                                FileUtils.setPermissions("/twdataconfig/tw_config.prop", 438, -1, -1);
                                throw th;
                            }
                        }
                        bufferedWriter.flush();
                        ConfigService.this.i.sendEmptyMessageDelayed(65283, 2000L);
                        bufferedWriter.close();
                        ConfigService.this.c.write(40730, 1, 0, "sync");
                        str2 = "/twdataconfig/tw_config.prop";
                    } catch (Exception e3) {
                        Log.e("ConfigService", "saveConfig2:" + e3.getMessage());
                        ConfigService.this.i.sendEmptyMessage(65284);
                        return;
                    }
                } catch (Exception e4) {
                    bufferedWriter = null;
                    e = e4;
                } catch (Throwable th3) {
                    bufferedWriter = null;
                    th = th3;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    ConfigService.this.c.write(40730, 1, 0, "sync");
                    FileUtils.setPermissions("/twdataconfig/tw_config.prop", 438, -1, -1);
                    throw th;
                }
                FileUtils.setPermissions(str2, 438, -1, -1);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(com.dofun.carsetting.config.a aVar) {
        if (aVar == null || TextUtils.isEmpty(aVar.f186a)) {
            return;
        }
        int iIndexOf = this.d.indexOf(aVar);
        int size = this.d.size();
        if (iIndexOf != -1) {
            this.d.get(iIndexOf).b = aVar.b;
        } else if (size > 0) {
            this.d.add(size - 1, aVar);
        } else {
            this.d.add(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.dofun.carsetting.config.a c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (int i = 0; i < this.d.size(); i++) {
            com.dofun.carsetting.config.a aVar = this.d.get(i);
            if (TextUtils.equals(aVar.f186a, str)) {
                return aVar;
            }
        }
        return null;
    }

    public void a(com.dofun.carsetting.config.a aVar) {
        if (aVar == null) {
            return;
        }
        try {
            int iBeginBroadcast = this.e.beginBroadcast();
            for (int i = 0; i < iBeginBroadcast; i++) {
                ((b) this.e.getBroadcastItem(i)).a(aVar.f186a, aVar.b);
            }
            this.e.finishBroadcast();
        } catch (RemoteException e) {
            Log.e("ConfigService", "onLeftTemp: " + e.getMessage());
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(this.j);
            if (this.c != null) {
                this.c.close();
                this.c = null;
            }
        } catch (Exception e) {
            Log.d("ConfigService", "onDestroy:" + e.getMessage());
        }
    }

    private void b() throws Throwable {
        int iA = w.a("DeviceMic");
        Log.d("ConfigService", "applyBtDeviceMic()-->" + iA);
        if (iA == -1) {
            iA = com.dofun.carsetting.f.b.bc();
            w.a(this.c, "DeviceMic", iA);
        } else if (iA == -2) {
            int iBc = SystemProperties.getInt("persist.tw.mic.select", -1);
            if (iBc == -1) {
                iBc = com.dofun.carsetting.f.b.bc();
            }
            iA = iBc;
        } else {
            com.dofun.carsetting.f.b.a(this.c, iA);
        }
        SystemProperties.set("persist.tw.mic.select", iA + "");
    }
}
