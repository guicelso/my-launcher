package com.tw.core.model;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.tw.core.c.B;
import com.tw.core.c.C;
import com.tw.core.c.C0021a;
import com.tw.core.c.C0022b;
import com.tw.core.c.C0023c;
import com.tw.core.c.C0024d;
import com.tw.core.c.C0025e;
import com.tw.core.c.C0026f;
import com.tw.core.c.C0027g;
import com.tw.core.c.C0028h;
import com.tw.core.c.C0033m;
import com.tw.core.c.C0034n;
import com.tw.core.c.C0035o;
import com.tw.core.c.C0036p;
import com.tw.core.c.C0037q;
import com.tw.core.c.C0038r;
import com.tw.core.c.C0039s;
import com.tw.core.c.C0042v;
import com.tw.core.c.C0043w;
import com.tw.core.c.C0044x;
import com.tw.core.c.C0045y;
import com.tw.core.c.C0046z;
import com.tw.core.c.D;
import com.tw.core.utils.C0054h;
import com.tw.core.utils.C0065s;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MainService extends Service implements com.tw.core.h.f {
    private C0038r hv;
    private Context hw;
    private com.tw.core.a hy;
    private boolean hz = true;
    private List hx = new ArrayList();
    private com.tw.core.b hu = new k(this);
    private BroadcastReceiver hB = new l(this);
    private ServiceConnection hA = new n(this);

    /* JADX INFO: Access modifiers changed from: private */
    public void mA() {
        try {
            if (this.hA != null) {
                bindService(new Intent(this, (Class<?>) DameonService.class), this.hA, 1);
            }
        } catch (Exception e) {
            C0065s.bL("MainService", e);
        }
    }

    private void mB() {
        com.tw.core.h.b.nN().nX(this);
        com.tw.core.h.b.nN().disconnect();
    }

    private void mC() {
        this.hx.add(new C0036p(this));
        this.hx.add(new C0044x(this));
        this.hx.add(new com.tw.core.j.b());
        this.hx.add(new com.tw.core.j.a(this.hw));
        this.hx.add(new com.tw.core.i.c(this.hw, new com.tw.core.i.e()));
        this.hx.add(new B(this.hw));
        this.hx.add(new C0023c(this.hw));
        this.hx.add(new C0033m(this.hw));
        this.hx.add(new D(this.hw));
        this.hx.add(new C0028h(this.hw));
        this.hx.add(new C0046z(this.hw));
        this.hx.add(new C0022b(this.hw));
        this.hx.add(new C0042v(this.hw));
        this.hx.add(new C0024d(this.hw));
        this.hx.add(new C0021a(this.hw));
        List list = this.hx;
        C0038r c0038rKZ = C0038r.kZ(this.hw);
        this.hv = c0038rKZ;
        list.add(c0038rKZ);
        this.hx.add(new C0045y(this.hw));
        this.hx.add(new C0039s(this.hw));
        this.hx.add(new C0037q(this.hw));
        this.hx.add(new C0035o(this.hw));
        this.hx.add(new C0025e(this.hw));
        this.hx.add(new C0027g(this.hw));
        this.hx.add(new C0034n());
        if (com.tw.core.b.e.gh()) {
            this.hx.add(new C0026f(this.hw));
            this.hx.add(new C(this.hw));
        }
        if (com.tw.core.b.e.gm()) {
            this.hx.add(new C0043w(this.hw));
        }
        Iterator it = this.hx.iterator();
        while (it.hasNext()) {
            ((com.tw.core.e.c) it.next()).onCreate();
        }
    }

    private void mD() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("com.unisound.intent.action.DO_SHUTDOWN");
        intentFilter.addAction("com.unisound.intent.action.ACC_OFF");
        intentFilter.addAction("com.unisound.intent.action.DO_SLEEP");
        intentFilter.addAction("com.unisound.intent.action.ACC_ON");
        intentFilter.addAction("com.unisound.intent.action.DO_WAKEUP");
        intentFilter.addAction("dofun.intent.action.EXTERNAL_APK_INSTALL");
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("dofun.intent.action.ACTIVITY_STARTING");
        intentFilter.addAction("dofun.intent.action.ACTIVITY_RESUMING");
        if (!com.tw.core.b.e.gh()) {
            intentFilter.addAction("com.dofun.intent.action.TRACK_SWITCH");
        }
        registerReceiver(this.hB, intentFilter);
    }

    private void mz() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        registerReceiver(new o(this), intentFilter);
    }

    @Override // com.tw.core.h.f
    public void jc(boolean z, String str) {
        com.tw.core.utils.B.ci("MainService", "connected reconnect= %s ,serverURI= %s", Boolean.valueOf(z), str);
        String strGr = com.tw.core.b.f.gr();
        if (com.tw.core.b.e.gh()) {
            com.tw.core.utils.B.ci("MainService", "subscribeTopic", new Object[0]);
            com.tw.core.h.b.nN().nV(new String[]{com.tw.core.app.a.iF, com.tw.core.app.a.iG + strGr}, new int[]{com.tw.core.h.b.nN().jg, com.tw.core.h.b.nN().jg});
        } else {
            com.tw.core.utils.B.ci("MainService", "海外不做订阅", new Object[0]);
        }
        for (com.tw.core.e.c cVar : this.hx) {
            if (cVar instanceof com.tw.core.h.f) {
                ((com.tw.core.h.f) cVar).jc(z, str);
            }
        }
    }

    @Override // com.tw.core.h.f
    public void jy(Throwable th) {
        th.printStackTrace();
        com.tw.core.utils.B.cj("MainService", "disconnection reconnect= %s", th.getMessage());
        if (com.tw.core.b.e.gh()) {
            com.tw.core.h.b.nN().nW(new String[]{com.tw.core.app.a.iF, com.tw.core.app.a.iG + com.tw.core.b.f.gr()});
        }
        for (com.tw.core.e.c cVar : this.hx) {
            if (cVar instanceof com.tw.core.h.f) {
                ((com.tw.core.h.f) cVar).jy(th);
            }
        }
    }

    public void mE(int i, String str) {
        if (this.hv != null) {
            this.hv.la(i, str);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        C0065s.bK("MainService", "onBind()");
        return this.hu;
    }

    @Override // android.app.Service
    public void onCreate() {
        com.tw.core.utils.B.ci("MainService", "onCreate()", new Object[0]);
        super.onCreate();
        this.hw = this;
        mC();
        com.tw.core.h.b.nN().nT(this);
        mD();
        mz();
        mA();
        C0054h.P().W();
        com.tw.core.h.b.nN().nQ();
        if (com.tw.core.b.e.gh()) {
            return;
        }
        sendBroadcast(new Intent("com.dofun.intent.action.GET_TRACK_SWITCH"));
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        com.tw.core.utils.B.cj("MainService", "onDestroy()", new Object[0]);
        try {
            if (this.hB != null) {
                unregisterReceiver(this.hB);
            }
        } catch (Exception e) {
        }
        mB();
        b.ms(this.hw).onDestroy();
        Iterator it = this.hx.iterator();
        while (it.hasNext()) {
            ((com.tw.core.e.c) it.next()).jj();
        }
        this.hx.clear();
        sendBroadcast(new Intent("com.tw.core.DESTROY_MAINSERVICE"));
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        com.tw.core.utils.B.ci("MainService", "onStartCommand", new Object[0]);
        return super.onStartCommand(intent, 1, i2);
    }
}
