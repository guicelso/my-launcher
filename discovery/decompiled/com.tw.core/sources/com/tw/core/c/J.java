package com.tw.core.c;

import android.text.TextUtils;
import com.tw.core.utils.C0064r;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class J implements Runnable {
    final /* synthetic */ C0024d fX;
    final /* synthetic */ String fY;

    J(C0024d c0024d, String str) {
        this.fX = c0024d;
        this.fY = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tw.core.a.d dVar;
        List<com.tw.core.a.d> listDk = com.tw.core.utils.L.dk();
        if (listDk == null || listDk.size() != 2) {
            if (listDk == null || listDk.size() == 0) {
                this.fX.jr(this.fY, 106);
                return;
            } else {
                if (listDk.size() == 1) {
                    this.fX.jr(this.fY, 103);
                    return;
                }
                return;
            }
        }
        com.tw.core.a.d dVar2 = null;
        com.tw.core.a.d dVar3 = null;
        for (com.tw.core.a.d dVar4 : listDk) {
            String strEE = dVar4.eE();
            String strBz = C0064r.bz(this.fX.dS, strEE, null);
            if (TextUtils.isEmpty(strBz)) {
                strBz = com.tw.core.utils.L.dl(this.fX.dS, strEE);
                if (!TextUtils.isEmpty(strBz)) {
                    C0064r.bB(this.fX.dS, strEE, strBz);
                }
            }
            if (TextUtils.equals(strBz, "DoFunCard")) {
                com.tw.core.a.d dVar5 = dVar2;
                dVar = dVar4;
                dVar4 = dVar5;
            } else {
                dVar = dVar3;
            }
            dVar3 = dVar;
            dVar2 = dVar4;
        }
        if (dVar3 == null || dVar2 == null) {
            if (dVar3 == null) {
                this.fX.jr(this.fY, 107);
                return;
            } else {
                this.fX.jr(this.fY, 104);
                return;
            }
        }
        if (dVar3.eF()) {
            com.tw.core.utils.B.ci("SimCardManager", "兜风卡启动状态", new Object[0]);
        } else {
            com.tw.core.utils.B.ci("SimCardManager", "兜风卡未启动", new Object[0]);
            com.tw.core.utils.L.dm(this.fX.dS, dVar3.eG(), true);
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (dVar3.eH()) {
            com.tw.core.utils.B.ci("SimCardManager", "兜风卡使用流量状态", new Object[0]);
            this.fX.jr(this.fY, 105);
        } else {
            com.tw.core.utils.B.ci("SimCardManager", "兜风卡未设置为默认流量", new Object[0]);
            com.tw.core.utils.L.dn(this.fX.dS, dVar3.eI(), dVar3.eG());
            this.fX.jr(this.fY, 1);
        }
    }
}
