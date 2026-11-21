package com.tw.core.c;

import android.content.Context;
import android.text.TextUtils;
import com.tw.core.track.model.ThirdPartnerGpsModel;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.o, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0035o extends AbstractC0030j implements com.tw.core.h.f, com.tw.core.h.d, com.tw.core.e.c {
    private Context eC;
    private List eD = new ArrayList();
    private int eB = 3;

    public C0035o(Context context) {
        this.eC = context;
    }

    private void kH(List list) {
        try {
            String strBz = C0064r.bz(this.eC, "key_gps_deliver_config", null);
            com.tw.core.utils.B.ci("ThirdPartnerGpsDataManager", "上次配置的GPS链接 %s", strBz);
            if (!TextUtils.isEmpty(strBz)) {
                JSONArray jSONArray = new JSONArray(strBz);
                for (int i = 0; i < jSONArray.length(); i++) {
                    String strOptString = jSONArray.optString(i);
                    if (!list.contains(strOptString)) {
                        com.tw.core.utils.B.cj("ThirdPartnerGpsDataManager", "%s 不存在了", strOptString);
                        String str = com.tw.core.utils.E.cz() + File.separator + "." + com.tw.core.utils.Q.dL(strOptString) + ".df";
                        File file = new File(str);
                        if (file.exists()) {
                            com.tw.core.utils.B.ci("ThirdPartnerGpsDataManager", "文件 %s 删除结果 %s", str, Boolean.valueOf(file.delete()));
                        }
                    }
                }
            }
            C0064r.bB(this.eC, "key_gps_deliver_config", new JSONArray((Collection) list).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.e.c
    public void jB() {
    }

    @Override // com.tw.core.e.c
    public void jC() {
    }

    @Override // com.tw.core.e.c
    public void jE(boolean z) {
    }

    @Override // com.tw.core.e.c
    public void jF() {
        if (this.eD != null) {
            Iterator it = this.eD.iterator();
            while (it.hasNext()) {
                ((com.tw.core.model.c) it.next()).mM();
            }
        }
    }

    @Override // com.tw.core.e.c
    public void jG() {
    }

    @Override // com.tw.core.e.c
    public void jH() {
    }

    public void jI() {
        if (this.eD != null) {
            Iterator it = this.eD.iterator();
            while (it.hasNext()) {
                ((com.tw.core.model.c) it.next()).mN();
            }
        }
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            this.eB = jSONObject.optInt("keep_days", 3);
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("dispense");
            com.tw.core.utils.B.ci("ThirdPartnerGpsDataManager", "gps 分发 %s", jSONArrayOptJSONArray.toString());
            if (jSONArrayOptJSONArray.length() <= 0) {
                if (this.eD != null) {
                    Iterator it = this.eD.iterator();
                    while (it.hasNext()) {
                        ((com.tw.core.model.c) it.next()).release();
                    }
                    this.eD.clear();
                    this.eD = null;
                }
                kH(Collections.emptyList());
                com.tw.core.utils.B.ci("ThirdPartnerGpsDataManager", "无GPS数据 取消分发", new Object[0]);
                return;
            }
            if (this.eD == null) {
                this.eD = new ArrayList();
            }
            Iterator it2 = this.eD.iterator();
            while (it2.hasNext()) {
                ((com.tw.core.model.c) it2.next()).release();
            }
            this.eD.clear();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                int iOptInt = jSONObjectOptJSONObject.optInt("collect_frequency");
                int iOptInt2 = jSONObjectOptJSONObject.optInt("upload_frequency");
                String strOptString = jSONObjectOptJSONObject.optString("url");
                arrayList.add(strOptString);
                this.eD.add(new com.tw.core.model.c(this.eC, this, iOptInt, iOptInt2, strOptString));
            }
            kH(arrayList);
            jI();
        } catch (JSONException e) {
            e.printStackTrace();
            Iterator it3 = this.eD.iterator();
            while (it3.hasNext()) {
                ((com.tw.core.model.c) it3.next()).release();
            }
        }
    }

    @Override // com.tw.core.h.f
    public void jc(boolean z, String str) {
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0313";
    }

    @Override // com.tw.core.e.c
    public void jj() {
        if (this.eD != null) {
            Iterator it = this.eD.iterator();
            while (it.hasNext()) {
                ((com.tw.core.model.c) it.next()).release();
            }
            this.eD.clear();
            this.eD = null;
        }
        com.tw.core.h.a.get().nL(this);
    }

    @Override // com.tw.core.c.AbstractC0030j
    public ThirdPartnerGpsModel jw(String str) {
        return ThirdPartnerGpsModel.hp(str);
    }

    @Override // com.tw.core.h.f
    public void jy(Throwable th) {
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: kF, reason: merged with bridge method [inline-methods] */
    public boolean jM(ThirdPartnerGpsModel thirdPartnerGpsModel) {
        if (thirdPartnerGpsModel == null) {
            return false;
        }
        int iAY = C0062p.aY(System.currentTimeMillis(), thirdPartnerGpsModel.hq());
        com.tw.core.utils.B.ci("ThirdPartnerGpsDataManager", "cacheDay=" + this.eB + ",offectDay=" + iAY, new Object[0]);
        return iAY < this.eB;
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: kG, reason: merged with bridge method [inline-methods] */
    public String jL(ThirdPartnerGpsModel thirdPartnerGpsModel) {
        JSONObject jSONObjectHo;
        return (thirdPartnerGpsModel == null || (jSONObjectHo = thirdPartnerGpsModel.ho()) == null) ? "" : jSONObjectHo.toString();
    }

    @Override // com.tw.core.e.c
    public void onCreate() {
        com.tw.core.h.a.get().nK(this);
    }
}
