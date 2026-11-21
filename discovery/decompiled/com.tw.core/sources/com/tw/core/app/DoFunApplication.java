package com.tw.core.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Process;
import com.tw.core.e.b;
import com.tw.core.model.DameonService;
import com.tw.core.model.MainService;
import com.tw.core.utils.B;
import com.tw.core.utils.C0050d;
import com.tw.core.utils.C0055i;
import com.tw.core.utils.C0058l;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.I;
import com.tw.core.utils.J;
import com.tw.core.utils.K;
import com.tw.core.utils.M;
import com.tw.core.utils.T;

/* JADX INFO: loaded from: classes.dex */
public class DoFunApplication extends Application {
    private static DoFunApplication iA;
    private static final String iy = DoFunApplication.class.getSimpleName();
    private static b iz;
    private T iB;

    public static b nt() {
        return iz;
    }

    public static Context nu() {
        return iA;
    }

    private void nv() {
        boolean zBy = C0064r.by(this, "key_log_status", false);
        if (J.aJ) {
            C0065s.aa = true;
            B.ao = true;
        } else {
            C0065s.aa = zBy;
            B.ao = zBy;
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        C0062p.ba();
        this.iB.dQ(C0062p.aV(this));
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        B.ci(iy, "onCreate", new Object[0]);
        iA = this;
        C0058l.aq();
        if (M.dt(Process.myPid())) {
            nv();
            if (C0062p.aU()) {
                iz = new I();
            } else {
                iz = new C0055i();
                String strX = nt().X();
                B.ci(iy, "channel name " + strX + " sn " + Build.SERIAL + " md5 salt " + K.df(Build.SERIAL, strX), new Object[0]);
            }
            this.iB = new T(this);
            this.iB.dP();
            startService(new Intent(this, (Class<?>) MainService.class));
            startService(new Intent(this, (Class<?>) DameonService.class));
            C0050d.w().E(this);
            C0062p.aZ(this, null);
        }
    }
}
