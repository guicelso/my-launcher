package com.tw.core.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.tw.core.c.C0031k;
import com.tw.core.utils.B;
import com.tw.core.utils.C0050d;
import com.tw.core.utils.C0054h;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.C0067u;
import com.tw.core.utils.E;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
class l extends BroadcastReceiver {
    final /* synthetic */ MainService hU;

    l(MainService mainService) {
        this.hU = mainService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws Throwable {
        String action = intent.getAction();
        B.ci("MainService", "receive broadcast：%s", action);
        if ("com.unisound.intent.action.DO_SHUTDOWN".equals(action)) {
            Iterator it = this.hU.hx.iterator();
            while (it.hasNext()) {
                ((com.tw.core.e.c) it.next()).jF();
            }
            return;
        }
        if ("com.unisound.intent.action.DO_SLEEP".equals(action)) {
            Iterator it2 = this.hU.hx.iterator();
            while (it2.hasNext()) {
                ((com.tw.core.e.c) it2.next()).jG();
            }
            return;
        }
        if ("com.unisound.intent.action.ACC_OFF".equals(action)) {
            Iterator it3 = this.hU.hx.iterator();
            while (it3.hasNext()) {
                ((com.tw.core.e.c) it3.next()).jB();
            }
            return;
        }
        if ("com.unisound.intent.action.ACC_ON".equals(action)) {
            Iterator it4 = this.hU.hx.iterator();
            while (it4.hasNext()) {
                ((com.tw.core.e.c) it4.next()).jC();
            }
            return;
        }
        if ("com.unisound.intent.action.DO_WAKEUP".equals(action)) {
            Iterator it5 = this.hU.hx.iterator();
            while (it5.hasNext()) {
                ((com.tw.core.e.c) it5.next()).jH();
            }
            return;
        }
        if ("android.intent.action.TIME_TICK".equals(action)) {
            C0054h.P().S(this.hU.hw);
            C0054h.P().T(this.hU.hw);
            return;
        }
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zFE = com.tw.core.b.a.fE(context);
            B.ci("MainService", "network change : %s", Boolean.valueOf(zFE));
            Iterator it6 = this.hU.hx.iterator();
            while (it6.hasNext()) {
                ((com.tw.core.e.c) it6.next()).jE(zFE);
            }
            if (!zFE) {
                com.tw.core.h.b.nN().disconnect();
                return;
            }
            com.tw.core.h.b.nN().nQ();
            try {
                boolean z = Math.abs(System.currentTimeMillis() - C0064r.bE(context, "LastTime", 0L)) > 3600000;
                B.ci("MainService", "network change 时间是否大于1小时 %s", Boolean.valueOf(z));
                if (z) {
                    C0064r.bH(context, "LastTime", System.currentTimeMillis());
                    if (this.hU.hz) {
                        this.hU.hz = false;
                        C0050d.w().y();
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                C0065s.bL("MainService", e);
                return;
            }
        }
        if (!"dofun.intent.action.EXTERNAL_APK_INSTALL".equals(intent.getAction())) {
            if ("com.dofun.intent.action.TRACK_SWITCH".equals(intent.getAction())) {
                boolean booleanExtra = intent.getBooleanExtra("trackSwitch", false);
                B.ci("MainService", "ACTION_TRACK_SWITCH %s", Boolean.valueOf(booleanExtra));
                C0031k.kt().kC(booleanExtra);
                return;
            }
            return;
        }
        String stringExtra = intent.getStringExtra("ApkFilePath");
        B.ci("MainService", "收到安装apk广播：%s", stringExtra);
        if (TextUtils.isEmpty(stringExtra) || !E.cF(stringExtra)) {
            return;
        }
        ArrayList arrayListBh = C0062p.bh(context);
        B.ci("MainService", "返回数据 %s", arrayListBh.toString());
        PackageInfo packageInfoGb = com.tw.core.b.d.gb(stringExtra);
        if (packageInfoGb == null || !arrayListBh.contains(packageInfoGb.packageName) || com.tw.core.b.d.gd(context, packageInfoGb.packageName) >= packageInfoGb.versionCode) {
            return;
        }
        B.ci("MainService", "返回数据 %s", packageInfoGb.packageName);
        C0067u.bR(this.hU.hw, stringExtra, new m(this));
    }
}
