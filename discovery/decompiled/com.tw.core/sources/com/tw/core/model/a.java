package com.tw.core.model;

import android.os.Handler;
import android.os.Looper;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.B;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private boolean hf;
    private boolean hg;
    private final Handler hh = new e(this, Looper.getMainLooper());

    private void md(boolean z, boolean z2) {
        B.ci("Remote360Handler", "保持唤醒 " + z + " 是否持续唤醒 " + z2 + " 当前休眠状态:" + this.hg, new Object[0]);
        this.hh.removeMessages(2);
        this.hh.removeMessages(1);
        DoFunApplication.nt().ae(z);
        if (z) {
            DoFunApplication.nt().af(true);
        } else if (this.hg) {
            DoFunApplication.nt().af(false);
        }
        if (z) {
            if (!z2) {
                this.hh.sendEmptyMessageDelayed(1, 15000L);
            } else {
                this.hh.sendEmptyMessageDelayed(2, 6000L);
                this.hh.sendEmptyMessageDelayed(1, 180000L);
            }
        }
    }

    public void me(String str, String str2) {
        com.tw.core.h.e eVarOc;
        if (!"cn.cardoor.zt360.action.MessengerService".equals(str2) || (eVarOc = com.tw.core.h.e.oc(str)) == null) {
            return;
        }
        B.ci("Remote360Handler", "data " + eVarOc, new Object[0]);
        if ("0800".equals(eVarOc.jm) || "0801".equals(eVarOc.jm)) {
            try {
                if (!"0801".equals(eVarOc.jm)) {
                    String strOptString = new JSONObject(eVarOc.jk).optString("status");
                    if ("1".equals(strOptString)) {
                        this.hf = true;
                        md(true, true);
                    } else if ("2".equals(strOptString)) {
                        this.hf = false;
                        md(false, false);
                    }
                } else if (this.hf) {
                    B.ci("Remote360Handler", "当前处于唤醒状态", new Object[0]);
                } else {
                    md(true, false);
                }
            } catch (Exception e) {
                B.ck(e.getMessage(), new Object[0]);
            }
        }
    }

    public void mf() {
        B.ci("Remote360Handler", "onSleep 当前是否在直播 " + this.hf, new Object[0]);
        this.hg = true;
        if (this.hf) {
            return;
        }
        this.hh.removeCallbacksAndMessages(null);
        DoFunApplication.nt().ae(false);
    }

    public void mg() {
        B.ci("Remote360Handler", "onWakeup", new Object[0]);
        this.hg = false;
        this.hh.removeCallbacksAndMessages(null);
        DoFunApplication.nt().ae(false);
    }
}
