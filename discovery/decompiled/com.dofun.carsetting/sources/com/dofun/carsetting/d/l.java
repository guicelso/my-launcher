package com.dofun.carsetting.d;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.dofun.carsetting.f.s;
import com.dofun.carsetting.g.r;
import com.dofun.carsetting.presenter.b;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* JADX INFO: compiled from: SimInfoModel.java */
/* JADX INFO: loaded from: classes.dex */
public class l<P extends com.dofun.carsetting.presenter.b> extends com.dofun.carsetting.d.b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f207a;
    private l<P>.a c;
    private l<P>.a d;
    private TelephonyManager e;
    private SubscriptionInfo f;
    private SubscriptionInfo g;
    private Map<String, r> b = new ConcurrentHashMap();
    private Handler h = new Handler(Looper.getMainLooper()) { // from class: com.dofun.carsetting.d.l.2
        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            super.dispatchMessage(message);
            if (message.what != 1) {
                return;
            }
            com.dofun.carsetting.f.k.a(l.this.f207a, "isDoFunCard", 1);
            Iterator it = l.this.b.entrySet().iterator();
            while (it.hasNext()) {
                ((r) ((Map.Entry) it.next()).getValue()).a(true);
            }
        }
    };

    /* JADX INFO: compiled from: SimInfoModel.java */
    static class b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private static l f211a = new l();
    }

    public static l a() {
        return b.f211a;
    }

    public void a(Context context) {
        this.f207a = context.getApplicationContext();
        b();
    }

    private void b() {
        this.e = (TelephonyManager) this.f207a.getSystemService("phone");
        SubscriptionManager subscriptionManagerFrom = SubscriptionManager.from(this.f207a);
        this.f = subscriptionManagerFrom.getActiveSubscriptionInfoForSimSlotIndex(0);
        this.g = subscriptionManagerFrom.getActiveSubscriptionInfoForSimSlotIndex(1);
        if (this.f != null && this.c == null) {
            com.dofun.carsetting.f.g.a().b("SimInfoModel", "添加mListenerSim1监听");
            this.c = new a(this.f.getSubscriptionId());
            this.e.listen(this.c, 321);
        }
        if (this.g == null || this.d != null) {
            return;
        }
        com.dofun.carsetting.f.g.a().b("SimInfoModel", "添加mListenerSim2监听");
        this.d = new a(this.g.getSubscriptionId());
        this.e.listen(this.d, 321);
    }

    public void a(String str, r rVar) {
        this.b.put(str, rVar);
        rVar.a(this.f, this.g);
    }

    public void a(String str) {
        this.b.remove(str);
    }

    public void b(String str) {
        HashMap map = new HashMap();
        map.put("iccid", str);
        String str2 = "http://plat.dofun.cc/tools/authIccid?iccid=" + str.substring(0, str.length() - 1) + "&appid=iwlxjoitwe1jwechatksdjiowjeklsdjhfi&sig=" + com.dofun.carsetting.f.i.a(s.a(map) + "wdf43sxdfgwechatsfdgwserg1fxhdff").toString();
        com.dofun.carsetting.f.g.a().a("SimInfoModel", "-------url =" + str2);
        com.dofun.carsetting.f.e.a();
        com.dofun.carsetting.f.e.a(map, str2, new com.dofun.carsetting.f.e.a() { // from class: com.dofun.carsetting.d.l.1
            @Override // com.dofun.carsetting.f.e.a
            public void a(String str3) {
                com.dofun.carsetting.f.g.a().a("SimInfoModel", "-------请求网络成功-----result =" + str3);
                try {
                    JSONObject jSONObject = new JSONObject(str3);
                    Handler handler = l.this.h;
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
                com.dofun.carsetting.f.g.a().a("SimInfoModel", "-------请求网络失败-----request=" + str3);
            }
        });
    }

    /* JADX INFO: compiled from: SimInfoModel.java */
    class a extends PhoneStateListener {
        public a(int i) {
            super(Integer.valueOf(i));
            com.dofun.carsetting.f.l.a(this, "mSubId", Integer.valueOf(i));
        }

        @Override // android.telephony.PhoneStateListener
        public void onDataConnectionStateChanged(int i, int i2) {
            String str;
            String str2 = EnvironmentCompat.MEDIA_UNKNOWN;
            try {
                str = (String) l.this.e.getClass().getDeclaredMethod("getNetworkTypeName", Integer.TYPE).invoke(l.this.e, Integer.valueOf(i2));
                if (str != null) {
                    try {
                        if (str.equals("LTE")) {
                            str2 = "4G";
                            str = str2;
                        }
                    } catch (Exception e) {
                        str2 = str;
                        e = e;
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
            Iterator it = l.this.b.entrySet().iterator();
            while (it.hasNext()) {
                ((r) ((Map.Entry) it.next()).getValue()).a(this.mSubId.intValue(), i, str);
            }
        }

        @Override // android.telephony.PhoneStateListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            Iterator it = l.this.b.entrySet().iterator();
            while (it.hasNext()) {
                ((r) ((Map.Entry) it.next()).getValue()).a(this.mSubId.intValue(), signalStrength);
            }
        }

        @Override // android.telephony.PhoneStateListener
        public void onServiceStateChanged(ServiceState serviceState) {
            Iterator it = l.this.b.entrySet().iterator();
            while (it.hasNext()) {
                ((r) ((Map.Entry) it.next()).getValue()).a(this.mSubId.intValue(), serviceState);
            }
        }
    }
}
