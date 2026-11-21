package com.tw.core.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tw.core.utils.C0063q;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;

/* JADX INFO: loaded from: classes.dex */
class g extends BroadcastReceiver {
    final /* synthetic */ DameonService hP;

    g(DameonService dameonService) {
        this.hP = dameonService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        C0065s.bK(DameonService.hi, "收到广播：" + action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            C0065s.bK(DameonService.hi, "检测到网络变化");
            if (com.tw.core.b.a.fE(context)) {
                C0065s.bK(DameonService.hi, "网络连接");
                long jBF = C0064r.bF(this.hP.hk, "uploadTime");
                Long lValueOf = Long.valueOf(System.currentTimeMillis());
                long jAbs = Math.abs(lValueOf.longValue() - jBF);
                C0065s.bK(DameonService.hi, "时间 " + (((jAbs / 1000) / 60) / 60.0d));
                if (jAbs >= 3600000) {
                    C0064r.bH(this.hP.hk, "uploadTime", lValueOf.longValue());
                    new C0063q(this.hP.hk).start();
                }
            }
        }
    }
}
