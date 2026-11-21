package com.tw.core.model;

import com.tw.core.track.model.ThirdPartnerGpsModel;
import com.tw.core.utils.C0058l;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d implements Runnable {
    private List hL;
    final /* synthetic */ c hM;

    public d(c cVar, List list) {
        this.hM = cVar;
        this.hL = list;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            JSONArray jSONArray = new JSONArray();
            Iterator it = this.hL.iterator();
            while (it.hasNext()) {
                JSONObject jSONObjectHo = ((ThirdPartnerGpsModel) it.next()).ho();
                if (jSONObjectHo != null) {
                    jSONObjectHo.put("cid", com.tw.core.b.f.gr());
                    jSONArray.put(jSONObjectHo);
                }
            }
            if (jSONArray.length() > 0) {
                C0058l.az(jSONArray, this.hM.hK, new p(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
