package com.tw.carinfoservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.Xml;
import c.b.a.k.h;
import c.b.a.k.i;
import c.b.a.k.j;
import com.tw.carinfoservice.CarService;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: classes.dex */
public class CarService extends Service implements c.b.a.m.a {
    public static boolean o = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public c.b.a.j.b f484b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public c.b.a.b f485c;
    public c.b.a.i.b d;
    public c.b.a.i.a e;
    public int f = 1;
    public HashMap<String, Boolean> g = new HashMap<>();
    public Handler h = new Handler(new a());
    public int i = 0;
    public long j = 0;
    public String k = "";
    public ServiceConnection l = new d();
    public boolean m = false;
    public BroadcastReceiver n = new e();

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 0) {
                int i = 0;
                while (true) {
                    String[] strArr = c.b.a.f.a.f402a;
                    if (i >= strArr.length) {
                        break;
                    }
                    if (CarService.this.g.get(strArr[i]) != null && !CarService.this.g.get(strArr[i]).booleanValue()) {
                        CarService.this.d(strArr[i]);
                    }
                    i++;
                }
            }
            return false;
        }
    }

    public class b implements c.b.a.i.b.a {
        public b() {
        }
    }

    public class c implements c.b.a.i.a.InterfaceC0017a {
        public c() {
        }
    }

    public class d implements ServiceConnection {
        public d() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            StringBuilder sbB = c.a.a.a.a.b("onServiceConnected:");
            sbB.append(componentName.getPackageName());
            Log.d("CarInfoService", sbB.toString());
            CarService.this.h.removeMessages(0);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            StringBuilder sbB = c.a.a.a.a.b("onServiceDisconnected:");
            sbB.append(componentName.getPackageName());
            sbB.append(",isSystemSleep:");
            sbB.append(CarService.this.m);
            Log.d("CarInfoService", sbB.toString());
            CarService carService = CarService.this;
            if (carService.m) {
                return;
            }
            String packageName = componentName.getPackageName();
            if (carService.k.equals(packageName)) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                long j = jCurrentTimeMillis - carService.j;
                carService.j = jCurrentTimeMillis;
                if (j < 2000) {
                    int i = carService.i + 1;
                    carService.i = i;
                    if (i >= 5) {
                        Log.e("CarInfoService", packageName + "，此服务持续异常，取消重复绑定");
                        return;
                    }
                } else {
                    carService.i = 0;
                    carService.k = "";
                }
            } else {
                carService.i = 0;
                carService.k = packageName;
            }
            carService.d(packageName);
        }
    }

    public class e extends BroadcastReceiver {
        public e() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("com.unisound.intent.action.DO_SLEEP".equals(intent.getAction()) || "com.unisound.intent.action.ACC_OFF".equals(intent.getAction())) {
                CarService.this.m = true;
            } else if ("com.unisound.intent.action.ACC_ON".equals(intent.getAction())) {
                CarService.this.m = false;
            }
        }
    }

    public class f extends JobService {
        @Override // android.app.job.JobService
        public boolean onStartJob(JobParameters jobParameters) {
            Intent intent = new Intent(this, (Class<?>) CarService.class);
            intent.setAction("RESTART_AFTER_KILL");
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(intent);
                return false;
            }
            startService(intent);
            return false;
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }
    }

    @Override // c.b.a.m.a
    public void a() {
        int i = Build.VERSION.SDK_INT;
        if (i >= 21) {
            new Handler().postDelayed(new Runnable() { // from class: c.b.a.a
                @Override // java.lang.Runnable
                public final void run() {
                    CarService carService = this.f394b;
                    carService.stopForeground(true);
                    carService.stopSelf();
                    Process.killProcess(Process.myPid());
                }
            }, 100L);
            ((JobScheduler) getSystemService("jobscheduler")).schedule(new JobInfo.Builder(1, new ComponentName(this, (Class<?>) f.class)).setOverrideDeadline(0L).setPersisted(true).build());
        } else if (i >= 26) {
            stopForeground(true);
            stopSelf();
            Process.killProcess(Process.myPid());
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0024  */
    public final void b(String str, String str2, String str3, ServiceConnection serviceConnection) {
        boolean z;
        try {
            if ("com.tw.car".equals(str)) {
                String strE = c.b.a.l.b.e(this, str);
                if (TextUtils.isEmpty(strE) || "1.0".equals(strE)) {
                    z = false;
                    Log.d("CarInfoService", "旧版CarApp,不绑定服务");
                } else {
                    z = true;
                }
            } else {
                z = true;
            }
            if (z) {
                Intent intent = new Intent(str3);
                intent.setClassName(str, str2);
                if (Build.VERSION.SDK_INT >= 26) {
                    getApplication().startForegroundService(intent);
                } else {
                    getApplication().startService(intent);
                }
                getApplication().bindService(intent, serviceConnection, 1);
            }
        } catch (Exception e2) {
            Log.d("CarInfoService", Log.getStackTraceString(e2));
        }
    }

    public final Notification c() {
        SparseArray<Bundle> sparseArrayA;
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("notification_channel_id_01", "Foreground Service Notification", 2);
            notificationChannel.setDescription("Channel description");
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        a.b.d.a.c cVar = new a.b.d.a.c(this, "notification_channel_id_01");
        cVar.h.icon = R.drawable.ic_app_launcher;
        cVar.h.when = System.currentTimeMillis();
        cVar.d = PendingIntent.getActivity(this, 1, new Intent(this, (Class<?>) CarService.class), 134217728);
        a.b.d.a.d dVar = new a.b.d.a.d(cVar);
        Objects.requireNonNull(dVar.f46b);
        if (i < 26 && i < 24) {
            if (i < 21 && i < 20 && (sparseArrayA = a.b.d.a.e.a(dVar.f47c)) != null) {
                dVar.d.putSparseParcelableArray("android.support.actionExtras", sparseArrayA);
            }
            dVar.f45a.setExtras(dVar.d);
        }
        Notification notificationBuild = dVar.f45a.build();
        Objects.requireNonNull(dVar.f46b);
        return notificationBuild;
    }

    public final void d(String str) {
        Log.d("CarInfoService", "rebindService:" + str);
        int i = 0;
        while (true) {
            String[] strArr = c.b.a.f.a.f402a;
            if (i >= strArr.length) {
                return;
            }
            if (str.equals(strArr[i])) {
                this.g.put(str, Boolean.FALSE);
                this.h.sendEmptyMessageDelayed(0, 2000L);
                b(strArr[i], c.b.a.f.a.f403b[i], c.b.a.f.a.f404c[i], this.l);
                return;
            }
            i++;
        }
    }

    public final void e() {
        int i = 0;
        if (Build.VERSION.SDK_INT >= 26) {
            Settings.System.putInt(getApplicationContext().getContentResolver(), "BIND_SERVICE_CONFIG", 1);
            startForeground(1, c());
            if (!this.m && !o) {
                while (true) {
                    String[] strArr = c.b.a.f.a.f402a;
                    if (i >= strArr.length) {
                        break;
                    }
                    StringBuilder sbB = c.a.a.a.a.b("bindServiceList():");
                    String[] strArr2 = c.b.a.f.a.f403b;
                    sbB.append(strArr2[i]);
                    Log.d("CarInfoService", sbB.toString());
                    b(strArr[i], strArr2[i], c.b.a.f.a.f404c[i], this.l);
                    i++;
                }
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.unisound.intent.action.DO_SLEEP");
                intentFilter.addAction("com.unisound.intent.action.ACC_OFF");
                intentFilter.addAction("com.unisound.intent.action.ACC_ON");
                registerReceiver(this.n, intentFilter);
            }
        } else {
            Settings.System.putInt(getApplicationContext().getContentResolver(), "BIND_SERVICE_CONFIG", 0);
        }
        o = true;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        StringBuilder sbB = c.a.a.a.a.b("onBind:");
        sbB.append(intent.getClass());
        Log.e("CarInfoService", sbB.toString());
        return this.f485c;
    }

    @Override // android.app.Service
    public void onCreate() {
        String str;
        c.b.a.k.f fVar;
        String str2;
        j jVar;
        j jVar2;
        Context context;
        ArrayList<c.b.a.e.a> arrayList;
        Handler handler;
        super.onCreate();
        e();
        c.b.a.j.b bVar = new c.b.a.j.b(this);
        this.f484b = bVar;
        bVar.f426a = this;
        c.b.a.h.b bVar2 = (c.b.a.h.b) bVar.f427b;
        int i = 2;
        if (bVar2.K == null) {
            bVar2.K = new c.b.a.k.f();
            if (new File("sdcard/.ResourcePicture/Apk/CarServiceTheme.apk").exists()) {
                fVar = bVar2.K;
                str2 = "sdcard/.ResourcePicture/Apk/CarServiceTheme.apk";
            } else {
                fVar = bVar2.K;
                str2 = "system/etc/CarServiceTheme.apk";
            }
            fVar.f434a = str2;
            i iVar = i.a.f440a;
            c.b.a.k.c cVar = bVar2.Q;
            if (!iVar.f439c.contains(cVar)) {
                iVar.f439c.add(cVar);
                c.b.a.k.b.a("ThemeManager", "registerThemeSwitchStatus : %s, size : %s", cVar, Integer.valueOf(iVar.f439c.size()));
            }
            if (new File(bVar2.K.f434a).exists()) {
                c.b.a.k.a aVar = new c.b.a.k.a();
                String str3 = bVar2.K.f434a;
                i iVar2 = i.a.f440a;
                File file = new File(str3);
                if (iVar2.f437a == null) {
                    throw new IllegalStateException("must be call init(Context ctx).");
                }
                c.b.a.k.b.a("ThemeManager", "loadThemePlugin : %s", file);
                if (file.exists()) {
                    iVar2.f438b.get(file.getAbsolutePath());
                    try {
                        jVar = new j(iVar2, iVar2.f437a, file);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        jVar = null;
                    }
                    if (jVar != null) {
                        iVar2.f438b.put(file.getAbsolutePath(), jVar);
                    }
                    c.b.a.k.b.a("ThemeManager", "load theme plugin : %s", file.getAbsoluteFile());
                    jVar2 = jVar;
                } else {
                    c.b.a.k.b.a("ThemeManager", "plugin is not exists. path = %s", file.getAbsoluteFile());
                    jVar2 = null;
                }
                i iVar3 = i.a.f440a;
                synchronized (iVar3) {
                    iVar3.d = jVar2;
                    Log.e("ThemeManager", "updateCurThemePlugin: " + jVar2);
                }
                aVar.f429a = jVar2;
                Context context2 = jVar2.f442b;
                int i2 = c.b.a.k.k.a.f444a;
                i iVar4 = i.a.f440a;
                synchronized (iVar4) {
                    context = iVar4.d != null ? iVar4.d.f442b : null;
                }
                try {
                    XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                    xmlPullParserNewPullParser.setInput(context2.getAssets().open("CarServiceConfig.xml"), "UTF-8");
                    int eventType = xmlPullParserNewPullParser.getEventType();
                    arrayList = null;
                    while (eventType != 1) {
                        if (eventType == 0) {
                            arrayList = new ArrayList<>();
                        } else if (eventType == i) {
                            if ("factorys".equals(xmlPullParserNewPullParser.getName())) {
                                int attributeCount = xmlPullParserNewPullParser.getAttributeCount();
                                for (int i3 = 0; i3 < attributeCount; i3++) {
                                    String attributeName = xmlPullParserNewPullParser.getAttributeName(i3);
                                    String attributeValue = xmlPullParserNewPullParser.getAttributeValue(i3);
                                    Log.e("LS123", "name: " + attributeName);
                                    Log.e("LS123", "value: " + attributeValue);
                                    if ("dataType-request".equals(attributeName)) {
                                        c.b.a.k.k.a.f445b = c.b.a.l.a.a(attributeValue);
                                    } else if (attributeName.contains("dataType-requestData")) {
                                        Log.e("LS123", "getXMLListFromAssets: " + attributeValue);
                                        for (String str4 : attributeValue.split(";")) {
                                            HashMap<Integer, int[]> map = c.b.a.k.k.a.f446c;
                                            map.put(Integer.valueOf(map.size()), c.b.a.l.a.f(str4));
                                        }
                                    } else if (attributeName.contains("dataType-send")) {
                                        c.b.a.k.k.a.f444a = c.b.a.l.a.a(attributeValue);
                                    } else if (attributeName.contains("aidl_sending_speed")) {
                                        Integer.parseInt(attributeValue);
                                    } else if (attributeName.contains("Front_Radar_Send_ID")) {
                                        c.b.a.k.k.a.e = 1;
                                        c.b.a.k.k.a.f = c.b.a.l.a.a(attributeValue);
                                    } else if (attributeName.contains("Front_Radar_Send_Data_Down")) {
                                        c.b.a.k.k.a.g = attributeValue;
                                    } else if (attributeName.contains("Front_Radar_Send_Data_Up")) {
                                        c.b.a.k.k.a.h = attributeValue;
                                    } else if (attributeName.contains("Front_Radar_Receive_ID")) {
                                        c.b.a.k.k.a.i = c.b.a.l.a.a(attributeValue);
                                    } else if (attributeName.contains("Front_Radar_Receive_Data_ID")) {
                                        c.b.a.k.k.a.j = Integer.parseInt(attributeValue);
                                    } else if (attributeName.contains("Front_Radar_Receive_Data")) {
                                        c.b.a.k.k.a.k = Integer.parseInt(attributeValue);
                                    } else if (attributeName.contains("Front_Radar_Receive_Bit")) {
                                        c.b.a.k.k.a.l = Integer.parseInt(attributeValue);
                                    } else if (attributeName.contains("Front_Radar_Up")) {
                                        c.b.a.k.k.a.m = Integer.parseInt(attributeValue);
                                    }
                                }
                            }
                            if ("array_item".equals(xmlPullParserNewPullParser.getName())) {
                                arrayList.add(null);
                            }
                            if ("item".equals(xmlPullParserNewPullParser.getName())) {
                                arrayList.add(c.b.a.k.k.a.a(xmlPullParserNewPullParser, context));
                            }
                        } else if (eventType == 3) {
                            Log.d("LS123", "END_TAG:" + xmlPullParserNewPullParser.getName() + "," + arrayList.toString());
                        }
                        eventType = xmlPullParserNewPullParser.next();
                        i = 2;
                    }
                } catch (Exception unused) {
                    Log.d("LS123", "Exception when getListFromXML file=CarServiceConfig.xml");
                    arrayList = null;
                }
                aVar.f430b = arrayList;
                StringBuilder sbB = c.a.a.a.a.b("carSettingList:  ");
                sbB.append(aVar.f430b);
                Log.i("xiang", sbB.toString());
                i iVar5 = i.a.f440a;
                Objects.requireNonNull(iVar5);
                h hVar = new h(iVar5, aVar);
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    hVar.run();
                } else {
                    Pair pair = new Pair(hVar, null);
                    synchronized (c.b.a.k.e.class) {
                        if (c.b.a.k.e.f433a == null) {
                            c.b.a.k.e.f433a = new c.b.a.k.e.a();
                        }
                        handler = c.b.a.k.e.f433a;
                    }
                    handler.obtainMessage(1, pair).sendToTarget();
                }
            }
            try {
                bVar2.f412b.write(1292, 255);
                Thread.sleep(30L);
                bVar2.f412b.write(267, 255);
                Thread.sleep(30L);
                bVar2.f412b.write(266, 255, 0);
                Thread.sleep(30L);
                bVar2.u0(true);
                bVar2.O.sendEmptyMessageDelayed(65296, 200L);
                bVar2.O.sendEmptyMessageDelayed(65297, 1000L);
            } catch (Exception unused2) {
            }
        }
        c.b.a.h.b bVar3 = (c.b.a.h.b) this.f484b.f427b;
        Objects.requireNonNull(bVar3);
        this.f485c = new c.b.a.g.a(bVar3, c.b.a.h.b.g0);
        Uri uriFor = Settings.System.getUriFor("AUX_CAMERA_INDEX");
        this.d = new c.b.a.i.b(this, "AUX_CAMERA_INDEX", new Handler(), new b());
        getContentResolver().registerContentObserver(uriFor, false, this.d);
        if (c.b.b.f.a.e() || c.b.b.f.a.i()) {
            c.b.a.i.a aVar2 = new c.b.a.i.a("/sys/devices/tp2850/type", new c());
            this.e = aVar2;
            aVar2.startWatching();
        }
        ContentResolver contentResolver = getContentResolver();
        if (!c.b.b.f.a.f() && !c.b.b.f.a.k()) {
            if (!(c.b.b.f.a.m() || c.b.b.f.a.l() || c.b.b.f.a.d().startsWith("V9.2.2")) && !c.b.b.f.a.t() && !c.b.b.f.a.u() && !c.b.b.f.a.v()) {
                if (!c.b.b.f.a.i()) {
                    if (c.b.b.f.a.n()) {
                        Settings.System.putInt(contentResolver, "Protocol_RightViewType", 1);
                        Settings.System.putString(contentResolver, "Protocol_RightViewPackage", "com.tw.rightview");
                        str = "com.tw.rightview.RightViewActivity";
                    } else {
                        if (c.b.b.f.a.r() || c.b.b.f.a.o()) {
                            Settings.System.putInt(contentResolver, "Protocol_RightViewType", 2);
                            Settings.System.putString(contentResolver, "Protocol_RightViewAction", "com.tw.openapk.action.Activity");
                            Settings.System.putString(contentResolver, "Protocol_RightViewExtraName", "open_action");
                            Settings.System.putString(contentResolver, "Protocol_RightViewExtraValue", "com.tw.rightview_open");
                            return;
                        }
                        Settings.System.putInt(contentResolver, "Protocol_RightViewType", 1);
                        Settings.System.putString(contentResolver, "Protocol_RightViewPackage", "com.tw.rightview");
                        str = "com.tw.rightview.LRReverseActivity";
                    }
                    Settings.System.putString(contentResolver, "Protocol_RightViewClass", str);
                    return;
                }
                Settings.System.putInt(contentResolver, "Protocol_RightViewDoubleFlash", 1);
            }
        }
        Settings.System.putInt(contentResolver, "Protocol_RightViewType", 0);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.d("CarInfoService", "onDestroy()");
        c.b.a.j.b bVar = this.f484b;
        if (bVar != null) {
            Objects.requireNonNull((c.b.a.h.b) bVar.f427b);
            Objects.requireNonNull((c.b.a.h.b) this.f484b.f427b);
        }
        getContentResolver().unregisterContentObserver(this.d);
        c.b.a.i.a aVar = this.e;
        if (aVar != null) {
            aVar.stopWatching();
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        e();
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        this.f485c = null;
        return super.onUnbind(intent);
    }
}
