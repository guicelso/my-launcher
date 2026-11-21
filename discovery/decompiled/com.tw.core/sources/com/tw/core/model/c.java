package com.tw.core.model;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import com.tw.core.c.AbstractC0030j;
import com.tw.core.c.C0040t;
import com.tw.core.c.InterfaceC0041u;
import com.tw.core.track.model.ThirdPartnerGpsModel;
import com.tw.core.utils.B;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.E;
import com.tw.core.utils.Q;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class c implements InterfaceC0041u {
    private List hC = new ArrayList();
    private int hD;
    private Context hE;
    private String hF;
    private C0040t hG;
    private Handler hH;
    private AbstractC0030j hI;
    private int hJ;
    private String hK;

    public c(Context context, AbstractC0030j abstractC0030j, int i, int i2, String str) {
        this.hJ = 30;
        this.hE = context;
        this.hK = str;
        this.hD = i;
        this.hI = abstractC0030j;
        if (i2 > 0) {
            this.hJ = i2;
        }
        this.hF = E.cz() + File.separator + "." + Q.dL(str) + ".df";
        if (B.ao) {
            B.ci("ThirdPartnerGpsHandler", "上报地址 %s", str);
            B.ci("ThirdPartnerGpsHandler", "定位频率 %s 上传频率", Integer.valueOf(i), Integer.valueOf(i2));
            B.ci("ThirdPartnerGpsHandler", "存储地址 %s", this.hF);
        }
        mO();
    }

    private void mO() {
        if (this.hH == null) {
            HandlerThread handlerThread = new HandlerThread("Loop_upload_gps_data");
            handlerThread.start();
            this.hH = new q(this, handlerThread.getLooper());
            this.hH.sendEmptyMessageDelayed(9999, 10000L);
        }
    }

    private boolean mP() {
        boolean zFE = com.tw.core.b.a.fE(this.hE);
        B.ci("ThirdPartnerGpsHandler", "网络状态 %s", Boolean.valueOf(zFE));
        return zFE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mQ() {
        List<ThirdPartnerGpsModel> listKq;
        B.ci("ThirdPartnerGpsHandler", "checkOfflineGpsDataAndUpload %s", this.hK);
        if (!mP() || (listKq = this.hI.kq(100, this.hF)) == null) {
            return;
        }
        this.hI.km(listKq, this.hF);
        C0048b.p().execute(new d(this, listKq));
        if (B.ao) {
            for (ThirdPartnerGpsModel thirdPartnerGpsModel : listKq) {
                B.ci("ThirdPartnerGpsHandler", "正常上传的离线点 %s %s", Double.valueOf(thirdPartnerGpsModel.ht()), Double.valueOf(thirdPartnerGpsModel.hu()));
            }
        }
    }

    @Override // com.tw.core.c.InterfaceC0041u
    public void iB(Location location) {
        if (B.ao) {
            B.ci("ThirdPartnerGpsHandler", "定位器 " + this.hK + " onLocation getLatitude=" + location.getLatitude() + ",getLongitude" + location.getLongitude() + ",getAltitude" + location.getAltitude() + ",getAccuracy" + location.getAccuracy() + ",getBearing" + location.getBearing() + ",getSpeed" + location.getSpeed() + ",getTime" + location.getTime(), new Object[0]);
        }
        ThirdPartnerGpsModel thirdPartnerGpsModelHx = ThirdPartnerGpsModel.hx(location);
        if (thirdPartnerGpsModelHx != null) {
            this.hC.add(thirdPartnerGpsModelHx);
        }
        B.ci("ThirdPartnerGpsHandler", "size = %s mRealSendSize = %s", Integer.valueOf(this.hC.size()), Integer.valueOf(this.hJ));
        if (this.hC.size() >= this.hJ) {
            mM();
        }
    }

    public void mM() {
        if (this.hC.size() > 0) {
            ArrayList<ThirdPartnerGpsModel> arrayList = new ArrayList(this.hC);
            this.hC.clear();
            if (!mP()) {
                B.ci("ThirdPartnerGpsHandler", "不满足上传条件", new Object[0]);
                this.hI.ks(arrayList, this.hF);
                return;
            }
            if (B.ao) {
                for (ThirdPartnerGpsModel thirdPartnerGpsModel : arrayList) {
                    B.ci("ThirdPartnerGpsHandler", "正在上传的在线点 %s %s", Double.valueOf(thirdPartnerGpsModel.ht()), Double.valueOf(thirdPartnerGpsModel.hu()));
                }
            }
            C0048b.p().execute(new d(this, arrayList));
        }
    }

    public void mN() {
        if (this.hG == null) {
            this.hG = new C0040t(this.hE);
            this.hG.lc(this, this.hD * 1000);
        }
    }

    public void release() {
        if (this.hG != null) {
            this.hG.release();
            this.hG = null;
        }
        if (this.hH != null) {
            this.hH.removeCallbacksAndMessages(null);
            this.hH = null;
        }
    }
}
