package com.tw.core.h;

import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.B;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.C0066t;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static volatile a iT;
    private List iU;

    private a() {
    }

    public static a get() {
        if (iT == null) {
            synchronized (a.class) {
                if (iT == null) {
                    iT = new a();
                }
            }
        }
        return iT;
    }

    public void nK(d dVar) {
        if (this.iU == null) {
            this.iU = new ArrayList();
        }
        this.iU.add(dVar);
    }

    public void nL(d dVar) {
        if (this.iU == null || !this.iU.contains(dVar)) {
            return;
        }
        this.iU.remove(dVar);
    }

    protected void nM(String str) {
        if (this.iU == null || this.iU.isEmpty()) {
            return;
        }
        e eVarOc = e.oc(str);
        B.ci("MqttMsgManager", "notifyMsgChange = %s", eVarOc);
        if (eVarOc != null) {
            String str2 = eVarOc.jm + eVarOc.jl;
            long jBO = C0066t.bO();
            if (eVarOc.jn > 0) {
                long jBE = C0064r.bE(DoFunApplication.nu(), str2, 0L);
                if (jBO - jBE < eVarOc.jn) {
                    if (C0065s.aa) {
                        C0065s.bK("MqttMsgManager", "notifyMsgChange  curTime = " + jBO + ", preTime = " + jBE + ",intervalTime = " + (jBO - jBE) + ",interval = " + eVarOc.jn);
                        return;
                    }
                    return;
                }
            }
            C0064r.bH(DoFunApplication.nu(), str2, jBO);
            for (d dVar : this.iU) {
                if (dVar != null && TextUtils.equals(dVar.je(), eVarOc.jm)) {
                    dVar.jb(eVarOc);
                }
            }
        }
    }
}
