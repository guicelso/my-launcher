package com.dofun.carsetting.d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.f.o;
import com.dofun.carsetting.f.s;
import com.dofun.carsetting.f.t;
import com.dofun.carsetting.f.u;
import com.dofun.carsetting.f.v;
import com.dofun.carsetting.presenter.b;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: compiled from: DeviceinfoModel.java */
/* JADX INFO: loaded from: classes.dex */
public class c<P extends com.dofun.carsetting.presenter.b> extends com.dofun.carsetting.d.b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    com.dofun.carsetting.g.e f188a;
    private u e;
    private Context f;
    private TelephonyManager g;
    private c<P>.C0015c h;
    private c<P>.C0015c i;
    private SubscriptionInfo j;
    private SubscriptionInfo k;
    private String o;
    static final /* synthetic */ boolean d = !c.class.desiredAssertionStatus();
    static String b = "";
    static String c = "";
    private String l = "";
    private int m = 1;
    private int n = 1;
    private int p = -1;
    private Handler q = new Handler(Looper.getMainLooper()) { // from class: com.dofun.carsetting.d.c.2
        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            super.dispatchMessage(message);
            int i = message.what;
            if (i == 1) {
                com.dofun.carsetting.f.k.a(c.this.f, "isDoFunCard", 1);
                c.this.f188a.a(true);
                return;
            }
            if (i != 266) {
                if (i == 274 && c.this.p != message.arg1) {
                    c.this.p = message.arg1;
                    return;
                }
                return;
            }
            if (((String) message.obj) != null && ((String) message.obj).length() > 0) {
                try {
                    String str = ((String) message.obj).split("-")[3];
                    int i2 = Integer.parseInt(str.substring(4, 6), 16);
                    String strSubstring = str.substring(1, 2);
                    switch ((i2 & 28) >> 2) {
                        case 0:
                            c.this.o = c.this.f.getString(R.string.stm8);
                            break;
                        case 1:
                            c.this.o = c.this.f.getString(R.string.stm32);
                            break;
                        case 2:
                            c.this.o = c.this.f.getString(R.string.gd330);
                            break;
                        case 3:
                            c.this.o = c.this.f.getString(R.string.gd230);
                            break;
                        case 4:
                            c.this.o = c.this.f.getString(R.string.mm030);
                            break;
                        case 5:
                            c.this.o = c.this.f.getString(R.string.mm031);
                            break;
                        case 6:
                            c.this.o = c.this.f.getString(R.string.gd105);
                            break;
                        case 7:
                            c.this.o = com.dofun.carsetting.b.a.a().r.b().get(strSubstring);
                            if (TextUtils.isEmpty(c.this.o)) {
                                c.this.o = c.this.f.getString(R.string.unknown);
                            }
                            break;
                    }
                    c.this.f188a.s(c.this.o);
                } catch (Exception unused) {
                }
            }
            if (message.arg1 == 0) {
                if (message.obj != null) {
                    String string = message.obj.toString();
                    if (com.dofun.carsetting.f.b.aK()) {
                        string = "Ts8.4.5" + string.substring(string.indexOf("-"));
                    }
                    String string2 = Settings.System.getString(MyApp.a().getContentResolver(), "MCU_LOCK");
                    if (TextUtils.isEmpty(string2)) {
                        if (com.dofun.carsetting.f.b.a().contains("ZH55")) {
                            String strReplace = string.replace("Ts10.", "");
                            c.this.f188a.b(c.this.f.getResources().getString(R.string.about_mcu_info_t) + strReplace + "(" + c.this.o + ")");
                        } else {
                            c.this.f188a.b(c.this.f.getResources().getString(R.string.about_mcu_info_t) + string + "(" + c.this.o + ")");
                        }
                    } else {
                        int iIndexOf = string.indexOf(45);
                        if (iIndexOf != -1) {
                            string2 = string2 + string.substring(iIndexOf);
                        }
                        c.this.f188a.b(c.this.f.getResources().getString(R.string.about_mcu_info_t) + string2 + "(" + c.this.o + ")");
                    }
                    c.this.c(string);
                    c.this.b(string);
                    c.this.a(c.this.f, new a(string, string.split("-")), c.this.p);
                    return;
                }
                return;
            }
            if (message.arg1 == 1) {
                if (message.obj == null || message.obj.toString().equals("")) {
                    c.this.f188a.a(c.this.f.getString(R.string.unknown));
                    return;
                }
                String string3 = message.obj.toString();
                if (com.dofun.carsetting.f.b.aK()) {
                    string3 = "Ts8.4.5" + string3.substring(string3.indexOf("-"));
                }
                c.this.f188a.a(string3);
            }
        }
    };
    private String r = "";
    private BroadcastReceiver s = new BroadcastReceiver() { // from class: com.dofun.carsetting.d.c.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction() != null && intent.getAction().equals("dofun.intent.receive.topway.card")) {
                    if (intent.getIntExtra("DoFunCard", 0) == 1 && com.dofun.carsetting.f.b.g(MyApp.a()).intValue() == 1) {
                        c.this.f188a.a(true);
                    } else {
                        c.this.f188a.a(false);
                    }
                }
            } catch (Exception unused) {
                c.this.a(com.dofun.carsetting.f.b.d(c.this.f));
            }
        }
    };

    /* JADX INFO: compiled from: DeviceinfoModel.java */
    static class b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private static c f193a = new c();
    }

    public void a(com.dofun.carsetting.g.e eVar) {
        this.f188a = eVar;
    }

    public static c a() {
        return b.f193a;
    }

    public void a(String str) {
        HashMap map = new HashMap();
        map.put("iccid", str);
        String str2 = "http://plat.dofun.cc/tools/authIccid?iccid=" + str.substring(0, str.length() - 1) + "&appid=iwlxjoitwe1jwechatksdjiowjeklsdjhfi&sig=" + com.dofun.carsetting.f.i.a(s.a(map) + "wdf43sxdfgwechatsfdgwserg1fxhdff").toString();
        com.dofun.carsetting.f.g.a().a("DeviceinfoModel", "-------url =" + str2);
        com.dofun.carsetting.f.e.a();
        com.dofun.carsetting.f.e.a(map, str2, new com.dofun.carsetting.f.e.a() { // from class: com.dofun.carsetting.d.c.1
            @Override // com.dofun.carsetting.f.e.a
            public void a(String str3) {
                com.dofun.carsetting.f.g.a().a("DeviceinfoModel", "-------请求网络成功-----result =" + str3);
                try {
                    JSONObject jSONObject = new JSONObject(str3);
                    Handler handler = c.this.q;
                    int iOptInt = jSONObject.optInt(NotificationCompat.CATEGORY_STATUS);
                    int i = 1;
                    if (iOptInt != 1) {
                        i = 0;
                    }
                    handler.sendEmptyMessage(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.dofun.carsetting.f.e.a
            public void b(String str3) {
                com.dofun.carsetting.f.g.a().a("DeviceinfoModel", "-------请求网络失败-----request=" + str3);
            }
        });
    }

    public void b() {
        if (this.e != null) {
            this.e.removeHandler("DeviceinfoModel");
            this.e.close();
            this.e = null;
        }
        if (this.h != null) {
            this.g.listen(this.h, 0);
            this.h = null;
        }
        if (this.i != null) {
            this.g.listen(this.i, 0);
            this.i = null;
        }
    }

    public void a(Context context) throws Throwable {
        this.f = context;
        this.g = (TelephonyManager) context.getSystemService("phone");
        this.e = u.a();
        if (this.e != null) {
            this.e.addHandler("DeviceinfoModel", this.q);
            this.e.write(266, 255);
            this.e.write(266, 255, 1);
            this.e.write(274, 255);
        }
        d(context);
    }

    public void c() {
        try {
            this.f.unregisterReceiver(this.s);
        } catch (Exception e) {
            com.dofun.carsetting.f.g.a().a("DeviceinfoModel", e.getMessage());
        }
    }

    public void a(SubscriptionInfo... subscriptionInfoArr) {
        if (subscriptionInfoArr[0] != null && this.h == null) {
            com.dofun.carsetting.f.g.a().b("DeviceinfoModel", "添加mListenerSim1监听");
            this.h = new C0015c(subscriptionInfoArr[0].getSubscriptionId());
            this.g.listen(this.h, 321);
        }
        if (subscriptionInfoArr[1] == null || this.i != null) {
            return;
        }
        com.dofun.carsetting.f.g.a().b("DeviceinfoModel", "添加mListenerSim2监听");
        this.i = new C0015c(subscriptionInfoArr[1].getSubscriptionId());
        this.g.listen(this.i, 321);
    }

    private void d(Context context) throws Throwable {
        String string;
        this.r = com.dofun.carsetting.f.b.d(context);
        if (!TextUtils.isEmpty(this.r) && this.r.length() > 10) {
            this.f188a.f(this.r);
            context.sendBroadcast(new Intent("topway.intent.receive.dofun.card.info"));
        }
        context.registerReceiver(this.s, new IntentFilter("dofun.intent.receive.topway.card"));
        if (!com.dofun.carsetting.f.b.aK()) {
            this.f188a.c(context.getString(R.string.about_platform_info) + com.dofun.carsetting.f.b.j(context));
            if (com.dofun.carsetting.f.b.a().contains("ZYH53-V2")) {
                this.f188a.c("Clarion model: " + com.dofun.carsetting.f.b.j(context));
            }
        } else {
            this.f188a.c(context.getString(R.string.about_platform_info) + "TS8");
        }
        String strA = v.a();
        com.dofun.carsetting.g.e eVar = this.f188a;
        if ("0".endsWith(v.a())) {
            string = context.getString(R.string.about_device_num);
        } else {
            string = context.getString(R.string.about_device_num_t) + strA;
        }
        eVar.e(string);
        this.f188a.b(context.getString(R.string.about_total_t) + ((String) com.dofun.carsetting.f.b.a(context).first), context.getString(R.string.about_useful_t) + ((String) com.dofun.carsetting.f.b.a(context).second));
        if ((com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) && !o.g().equals("0")) {
            this.f188a.a(context.getString(R.string.about_total_t) + o.h(), context.getString(R.string.about_useful_t) + ((String) com.dofun.carsetting.f.b.c(context).second));
        } else {
            this.f188a.a(context.getString(R.string.about_total_t) + ((String) com.dofun.carsetting.f.b.c(context).first), context.getString(R.string.about_useful_t) + ((String) com.dofun.carsetting.f.b.c(context).second));
        }
        if ((com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.G()) && !o.a().equals("0")) {
            if (com.dofun.carsetting.f.b.s() && !o.i()) {
                this.f188a.d(context.getString(R.string.about_android_info) + Build.VERSION.RELEASE + ".0");
            } else {
                this.f188a.d(context.getString(R.string.about_android_info) + o.b());
            }
        } else {
            this.f188a.d(context.getString(R.string.about_android_info) + Build.VERSION.RELEASE + ".0");
            if (com.dofun.carsetting.f.b.a().contains("ZYH53-V2")) {
                this.f188a.d("Clarion OS");
            }
        }
        SubscriptionManager subscriptionManagerFrom = SubscriptionManager.from(context);
        if (ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") == 0) {
            this.j = subscriptionManagerFrom.getActiveSubscriptionInfoForSimSlotIndex(0);
            this.k = subscriptionManagerFrom.getActiveSubscriptionInfoForSimSlotIndex(1);
            if (this.j != null) {
                Log.d("DeviceinfoModel", "mSub1:" + this.j.getSubscriptionId());
                this.f188a.h(context.getString(R.string.about_operator_t) + com.dofun.carsetting.f.b.a(context, this.j));
            }
            if (this.k != null) {
                Log.d("DeviceinfoModel", "mSub2:" + this.k.getSubscriptionId());
                this.f188a.i(context.getString(R.string.about_operator_t) + com.dofun.carsetting.f.b.a(context, this.k));
            }
        }
        k();
        a(this.j, this.k);
        b(context);
        c(context);
        h();
        f();
        e();
        d();
    }

    public void d() {
        String string = Settings.System.getString(MyApp.a().getContentResolver(), "CPU_LOCK");
        if (TextUtils.isEmpty(string)) {
            this.f188a.u(this.f.getString(R.string.about_cpu_info_t) + com.dofun.carsetting.f.b.f(this.f));
            return;
        }
        this.f188a.u(this.f.getString(R.string.about_cpu_info_t) + string);
    }

    public void e() throws Throwable {
        String string = Settings.System.getString(MyApp.a().getContentResolver(), "BOOT_LOCK");
        String strC = com.dofun.carsetting.f.b.c("/sys/class/boot_version/version");
        String str = SystemProperties.get("ro.boot.lcd_describe");
        if (TextUtils.isEmpty(string)) {
            if (com.dofun.carsetting.f.b.f() && !strC.equals("")) {
                this.f188a.t(this.f.getResources().getString(R.string.about_boot_info_t) + strC);
                return;
            }
            if (str != null) {
                this.f188a.t(this.f.getResources().getString(R.string.about_boot_info_t) + str);
                return;
            }
            this.f188a.t(this.f.getResources().getString(R.string.about_boot_info));
            return;
        }
        if (com.dofun.carsetting.f.b.f() && !strC.equals("")) {
            int iIndexOf = strC.indexOf(95);
            if (iIndexOf != -1) {
                str = string + strC.substring(iIndexOf);
            }
            this.f188a.t(this.f.getResources().getString(R.string.about_boot_info_t) + str);
            return;
        }
        if (str != null) {
            int iIndexOf2 = str.indexOf(95);
            if (iIndexOf2 != -1) {
                str = string + str.substring(iIndexOf2);
            }
            this.f188a.t(this.f.getResources().getString(R.string.about_boot_info_t) + str);
            return;
        }
        this.f188a.t(this.f.getResources().getString(R.string.about_boot_info));
    }

    public void f() {
        String string = Settings.System.getString(MyApp.a().getContentResolver(), "SYSTEM_LOCK");
        String strReplace = SystemProperties.get("ro.tw.version");
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(strReplace)) {
                return;
            }
            try {
                String[] strArrSplit = strReplace.split("\\.");
                if (strArrSplit.length >= 3 && strArrSplit[2].startsWith("5")) {
                    this.f188a.w(this.f.getResources().getString(R.string.about_radio_module_4in1));
                }
            } catch (Exception unused) {
                this.f188a.w(this.f.getResources().getString(R.string.about_radio_module));
            }
            if (com.dofun.carsetting.f.b.aK()) {
                strReplace = "V10.5.5" + strReplace.substring(strReplace.indexOf("_"));
            } else if (com.dofun.carsetting.f.b.aL() && com.dofun.carsetting.f.b.aS()) {
                strReplace = "V8.32" + strReplace.substring(strReplace.indexOf("_"), strReplace.lastIndexOf("_"));
            } else if (com.dofun.carsetting.f.b.a().contains("ZH55")) {
                strReplace = strReplace.replace("TS10.", "");
            }
            this.f188a.r(this.f.getString(R.string.about_system_info_t) + strReplace);
            return;
        }
        int iIndexOf = strReplace.indexOf(95);
        if (iIndexOf != -1) {
            string = string + strReplace.substring(iIndexOf);
        }
        this.f188a.r(this.f.getString(R.string.about_system_info_t) + string);
    }

    public void g() {
        this.r = com.dofun.carsetting.f.b.d(this.f);
        if (TextUtils.isEmpty(this.r) || this.r.length() <= 10) {
            return;
        }
        this.f188a.f(this.r);
        this.f.sendBroadcast(new Intent("topway.intent.receive.dofun.card.info"));
    }

    /* JADX INFO: renamed from: com.dofun.carsetting.d.c$c, reason: collision with other inner class name */
    /* JADX INFO: compiled from: DeviceinfoModel.java */
    class C0015c extends PhoneStateListener {
        public C0015c(int i) {
            com.dofun.carsetting.f.l.a(this, "mSubId", Integer.valueOf(i));
        }

        /* JADX WARN: Code duplicated, block: B:18:0x007d  */
        @Override // android.telephony.PhoneStateListener
        public void onDataConnectionStateChanged(int i, int i2) {
            String string = EnvironmentCompat.MEDIA_UNKNOWN;
            switch (i) {
                case 0:
                    string = c.this.f.getString(R.string.radioInfo_data_disconnected);
                    break;
                case 1:
                    string = c.this.f.getString(R.string.radioInfo_data_connecting);
                    break;
                case 2:
                    string = c.this.f.getString(R.string.radioInfo_data_connected);
                    break;
                case 3:
                    string = c.this.f.getString(R.string.radioInfo_data_suspended);
                    break;
            }
            String str = EnvironmentCompat.MEDIA_UNKNOWN;
            try {
                String str2 = (String) c.this.g.getClass().getDeclaredMethod("getNetworkTypeName", Integer.TYPE).invoke(c.this.g, Integer.valueOf(i2));
                if (str2 != null) {
                    try {
                        if (str2.equals("LTE")) {
                            str = "4G";
                        } else {
                            str = str2;
                        }
                    } catch (Exception e) {
                        e = e;
                        str = str2;
                        e.printStackTrace();
                    }
                } else {
                    str = str2;
                }
            } catch (Exception e2) {
                e = e2;
            }
            c.this.a(this.mSubId.intValue(), string, str);
        }

        @Override // android.telephony.PhoneStateListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            c.this.a(this.mSubId.intValue(), signalStrength);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code duplicated, block: B:12:0x0048  */
        /* JADX WARN: Code duplicated, block: B:16:0x0050  */
        /* JADX WARN: Code duplicated, block: B:18:0x0053  */
        /* JADX WARN: Code duplicated, block: B:19:0x0074  */
        /* JADX WARN: Instruction removed from duplicated block: B:18:0x0053, please report this as an issue */
        /* JADX WARN: Instruction removed from duplicated block: B:19:0x0074, please report this as an issue */
        @Override // android.telephony.PhoneStateListener
        public void onServiceStateChanged(ServiceState serviceState) {
            int i;
            String string = c.this.f.getString(R.string.about_service_stat_t);
            switch (serviceState.getState()) {
                case 0:
                    string = string + c.this.f.getString(R.string.radioInfo_service_in);
                    i = 1;
                    break;
                case 1:
                    if (serviceState.getDataRegState() != 0) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    if (serviceState.getDataRegState() != 0) {
                        string = string + c.this.f.getResources().getString(R.string.radioInfo_service_out);
                    } else {
                        string = string + c.this.f.getResources().getString(R.string.radioInfo_service_in);
                    }
                    break;
                case 2:
                    i = 1;
                    if (serviceState.getDataRegState() != 0) {
                        string = string + c.this.f.getResources().getString(R.string.radioInfo_service_out);
                    } else {
                        string = string + c.this.f.getResources().getString(R.string.radioInfo_service_in);
                    }
                    break;
                case 3:
                    string = string + c.this.f.getString(R.string.radioInfo_service_off);
                    i = 0;
                    break;
                default:
                    i = 1;
                    break;
            }
            c.this.a(this.mSubId.intValue(), serviceState, string, i);
        }
    }

    public void a(int i, SignalStrength signalStrength) {
        try {
            Method declaredMethod = signalStrength.getClass().getDeclaredMethod("getDbm", new Class[0]);
            Method declaredMethod2 = signalStrength.getClass().getDeclaredMethod("getAsuLevel", new Class[0]);
            int iIntValue = ((Integer) declaredMethod.invoke(signalStrength, new Object[0])).intValue();
            int iIntValue2 = ((Integer) declaredMethod2.invoke(signalStrength, new Object[0])).intValue();
            if (this.m == 0) {
                this.f188a.j(this.f.getString(R.string.about_signal_strength_nosignal));
            } else if (this.j != null && i == this.j.getSubscriptionId()) {
                this.l = a(iIntValue);
                this.f188a.j(this.f.getString(R.string.about_signal_strength_t) + this.l + " (" + iIntValue + "dBm" + iIntValue2 + "asu)");
            }
            if (this.n == 0) {
                this.f188a.k(this.f.getString(R.string.about_signal_strength_nosignal));
            } else if (this.k != null && i == this.k.getSubscriptionId()) {
                this.l = a(iIntValue);
                this.f188a.k(this.f.getString(R.string.about_signal_strength_t) + this.l + " (" + iIntValue + "dBm" + iIntValue2 + "asu)");
            }
            k();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(int i, String str, String str2) {
        if (this.j != null && i == this.j.getSubscriptionId()) {
            this.f188a.p(this.f.getString(R.string.about_mobile_stat_t) + str);
            this.f188a.l(this.f.getString(R.string.about_mobile_type_t) + str2);
        }
        if (this.k == null || i != this.k.getSubscriptionId()) {
            return;
        }
        this.f188a.q(this.f.getString(R.string.about_mobile_stat_t) + str);
        this.f188a.m(this.f.getString(R.string.about_mobile_type_t) + str2);
    }

    public void a(int i, ServiceState serviceState, String str, int i2) {
        String string = (serviceState.getOperatorAlphaLong() == null || serviceState.getOperatorAlphaLong().equals("")) ? this.f.getString(R.string.unknown) : serviceState.getOperatorAlphaLong();
        Log.d("DeviceinfoModel", "onGetServiceStatChanged:subId:" + i);
        if (this.j != null) {
            Log.d("DeviceinfoModel", "onGetServiceStatChanged:mSub1:" + this.j.getSubscriptionId());
        }
        if (this.j != null && i == this.j.getSubscriptionId()) {
            Log.d("DeviceinfoModel", "onGetServiceStatChanged:" + this.f.getString(R.string.about_operator_t) + string);
            this.f188a.h(this.f.getString(R.string.about_operator_t) + com.dofun.carsetting.f.b.a(this.f, this.j));
            this.f188a.n(str);
            Log.d("DeviceinfoModel", "onGetServiceStatChanged:signalState1:" + this.m);
            this.m = i2;
            if (i2 == 0) {
                this.f188a.j(this.f.getString(R.string.about_signal_strength_nosignal));
            }
        }
        if (this.k == null || i != this.k.getSubscriptionId()) {
            return;
        }
        this.f188a.i(this.f.getString(R.string.about_operator_t) + com.dofun.carsetting.f.b.a(this.f, this.k));
        this.f188a.o(str);
        this.n = i2;
        if (i2 == 0) {
            this.f188a.k(this.f.getString(R.string.about_signal_strength_nosignal));
        }
    }

    private String a(int i) {
        if (i >= -70) {
            return this.f.getString(R.string.about_signal_strength_h);
        }
        if (i >= -95) {
            return this.f.getString(R.string.about_signal_strength_c);
        }
        return this.f.getString(R.string.about_signal_strength_l);
    }

    public void b(String str) {
        String[] strArr = {"NXP tef6624/21/", "SI47XX", "teff6657", "tef6686", "si4755", "TDA7706", "TDA7786", "tef685x", "TDA7703", "QN8035", "SI4754C", "NXP6831", "TDA7708", "TDA7707", "SI47925"};
        int i = 3;
        char cCharAt = str.split("-")[3].charAt(2);
        switch (cCharAt) {
            case '0':
                i = 0;
                break;
            case '1':
                i = 1;
                break;
            case '2':
                i = 2;
                break;
            case '3':
                break;
            case '4':
                i = 4;
                break;
            case '5':
                i = 5;
                break;
            case '6':
                i = 6;
                break;
            case '7':
                i = 7;
                break;
            case '8':
                i = 8;
                break;
            case '9':
                i = 9;
                break;
            default:
                switch (cCharAt) {
                    case 'A':
                        i = 10;
                        break;
                    case 'B':
                        i = 11;
                        break;
                    case 'C':
                        i = 12;
                        break;
                    case 'D':
                        i = 13;
                        break;
                    case 'E':
                        i = 14;
                        break;
                    default:
                        b = this.f.getResources().getString(R.string.about_radio_module);
                        i = -1;
                        break;
                }
                break;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (i2 == i) {
                b = this.f.getResources().getString(R.string.about_radio_module_type, strArr[i2]);
                this.f188a.w(b);
            }
        }
        this.f188a.w(b);
    }

    public void c(String str) {
        String[] strArr = {"TDA7415", "ROHM37534", "TDA7719", "PT2313", "NXP6831", "SI479X", "ROHM32107", "AK7604", "SI47925"};
        int i = 3;
        switch (str.split("-")[3].charAt(3)) {
            case '0':
                i = 0;
                break;
            case '1':
                i = 1;
                break;
            case '2':
                i = 2;
                break;
            case '3':
                break;
            case '4':
                i = 4;
                break;
            case '5':
                i = 5;
                break;
            case '6':
                i = 6;
                break;
            case '7':
            default:
                c = this.f.getResources().getString(R.string.about_music_core);
                i = -1;
                break;
            case '8':
                i = 7;
                break;
            case '9':
                i = 8;
                break;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (i2 == i) {
                c = this.f.getResources().getString(R.string.about_music_core_t, strArr[i2]);
                this.f188a.v(c);
            }
        }
        this.f188a.v(c);
    }

    public void b(Context context) {
        if (com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            this.f188a.x(String.format(context.getResources().getString(R.string.about_ble_bluetooth_info5), "5.0"));
            return;
        }
        if (com.dofun.carsetting.f.b.s()) {
            int i = SystemProperties.getInt("persist.tw.bt.module", -1);
            if (i == 0) {
                this.f188a.x(String.format(context.getResources().getString(R.string.about_ble_bluetooth_info5), "5.0") + "(UWE5623DU)");
                return;
            }
            if (i == 2) {
                this.f188a.x(String.format(context.getResources().getString(R.string.about_ble_bluetooth_info5), "5.0") + "(RTL8761B+UWE5623DU)");
                return;
            }
            this.f188a.x(String.format(context.getResources().getString(R.string.about_ble_bluetooth_info5), "5.0"));
            return;
        }
        if (com.dofun.carsetting.f.b.f()) {
            if (com.dofun.carsetting.f.b.h() || com.dofun.carsetting.f.b.i()) {
                this.f188a.x(context.getResources().getString(R.string.about_ble_bluetooth_info));
                return;
            } else {
                this.f188a.x(context.getResources().getString(R.string.about_bluetooth_info));
                return;
            }
        }
        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.f188a.x(String.format(context.getResources().getString(R.string.about_ble_bluetooth_info5), "5.4"));
            return;
        }
        if (context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            this.f188a.x(context.getResources().getString(R.string.about_ble_bluetooth_info));
        } else if (context.getPackageManager().hasSystemFeature("android.hardware.bluetooth")) {
            this.f188a.x(context.getResources().getString(R.string.about_ble_bluetooth_info));
        } else {
            this.f188a.x(context.getResources().getString(R.string.about_bluetooth_info_unknown));
        }
    }

    public void c(Context context) throws Throwable {
        String string = SystemProperties.get("gsm.version.baseband", "");
        Log.d("DeviceinfoModel", "getBaseBandInfo:baseband: " + string);
        if (TextUtils.isEmpty(string)) {
            string = "";
        }
        String strA = "";
        if (com.dofun.carsetting.f.b.L()) {
            try {
                strA = t.a().a(this.f, "gsm.version.baseband");
            } catch (Exception unused) {
                strA = "";
            }
        }
        Log.d("DeviceinfoModel", "getBaseBandInfo:cp2Version: " + strA);
        if (!TextUtils.isEmpty(strA)) {
            string = string + "|" + strA;
        }
        if (!TextUtils.isEmpty(i())) {
            string = string + "|" + i();
        }
        if (!TextUtils.isEmpty(j())) {
            string = string + "|" + j();
        }
        if (TextUtils.isEmpty(string)) {
            string = context.getResources().getString(R.string.device_info_default);
        }
        if (com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.p()) {
            int i = SystemProperties.getInt("ro.boot.rfboard.id", 0);
            if (i == 1) {
                string = string + "|-U";
            } else if (i == 2) {
                string = string + "|-D";
            }
        } else if (com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            int i2 = SystemProperties.getInt("ro.boot.rfboard.id", 0);
            if (i2 == 0) {
                string = string + "|BDV";
            } else if (i2 == 1) {
                string = string + "|US-BDV";
            } else if (i2 == 2) {
                string = string + "|BND";
            } else if (i2 == 2) {
                string = string + "|US-BND";
            }
        }
        if (com.dofun.carsetting.f.b.H()) {
            string = string + "-(" + com.dofun.carsetting.f.b.c("/sys/devices/platform/soc/soc:meig-hwversion/hwversion") + ")";
        }
        this.f188a.y(context.getResources().getString(R.string.about_band_info_t) + string);
    }

    public void h() {
        TelephonyManager telephonyManager = (TelephonyManager) this.f.getSystemService("phone");
        if (!d && telephonyManager == null) {
            throw new AssertionError();
        }
        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            String deviceId = telephonyManager.getDeviceId();
            this.f188a.z("IMEI1:" + deviceId);
            return;
        }
        if (TextUtils.isEmpty(telephonyManager.getImei(0))) {
            this.f188a.z("IMEI1：" + this.f.getResources().getString(R.string.device_info_default));
        } else {
            this.f188a.z("IMEI1：" + telephonyManager.getImei(0));
        }
        if (TextUtils.isEmpty(telephonyManager.getImei(1))) {
            this.f188a.A("IMEI2：" + this.f.getResources().getString(R.string.device_info_default));
            return;
        }
        this.f188a.A("IMEI2：" + telephonyManager.getImei(1));
    }

    /* JADX INFO: compiled from: DeviceinfoModel.java */
    public static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public String f192a;
        public String[] b;
        public String c;

        public a(String str, String[] strArr) {
            this.f192a = str;
            this.b = strArr;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:137:0x0523
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1478)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public void a(android.content.Context r17, com.dofun.carsetting.d.c.a r18, int r19) {
        /*
            Method dump skipped, instruction units count: 2230
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.dofun.carsetting.d.c.a(android.content.Context, com.dofun.carsetting.d.c$a, int):void");
    }

    private void k() {
        TelephonyManager telephonyManager = this.g;
        boolean z = !TextUtils.equals(TelephonyManager.getTelephonyProperty(0, "persist.radio.sim_enabled", "1"), "0");
        TelephonyManager telephonyManager2 = this.g;
        this.f188a.a(this.j != null, z, this.k != null, !TextUtils.equals(TelephonyManager.getTelephonyProperty(1, "persist.radio.sim_enabled", "1"), "0"));
    }

    public static String i() {
        return a("/data/vendor/gnss/config/config.xml", "PROPERTY", "GE2-VERSION");
    }

    public static String j() {
        return a("/data/vendor/gnss/supl/supl.xml", "PROPERTY", "AGPS-VERSION");
    }

    private static String a(String str, String str2, String str3) {
        String str4 = com.dofun.carsetting.f.b.H() ? "" : "UNKNOWN";
        Log.d("DeviceinfoModel", "filepath-> " + str + ", element name-> " + str2 + ", key-> " + str3);
        try {
            NodeList elementsByTagName = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(str)).getElementsByTagName(str2);
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                Element element = (Element) elementsByTagName.item(i);
                if (element.getAttribute("NAME").equals(str3)) {
                    return element.getAttribute("VALUE");
                }
            }
            return str4;
        } catch (Exception e) {
            Log.e("DeviceinfoModel", "Exception ->" + e);
            e.printStackTrace();
            return str4;
        }
    }
}
