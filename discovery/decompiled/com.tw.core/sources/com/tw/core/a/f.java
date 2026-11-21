package com.tw.core.a;

import android.text.TextUtils;
import com.tw.core.utils.C0070x;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class f {
    private int cj;
    private int ck;
    private boolean cl;
    private boolean cm;

    /* JADX INFO: renamed from: cn, reason: collision with root package name */
    private boolean f2cn;
    private List co;
    private int cp;
    private String cq;

    public static f fi(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            boolean z = jSONObject.getBoolean("collect_on_off");
            int i = jSONObject.getInt("collect_frequency") * 1000;
            boolean z2 = jSONObject.getBoolean("upload_on_off");
            int i2 = jSONObject.getInt("upload_frequency");
            boolean zOptBoolean = jSONObject.optBoolean("binding_on_off");
            int i3 = jSONObject.getInt("keep_days");
            f fVar = new f();
            if (jSONObject.has("url") && jSONObject.optString("url") != null) {
                String strBZ = C0070x.bZ(jSONObject.optString("url"));
                if (!TextUtils.isEmpty(strBZ)) {
                    fVar.fy(strBZ);
                }
            }
            String strOptString = jSONObject.optString("support_packages");
            if (!TextUtils.isEmpty(strOptString)) {
                if (strOptString.contains(",")) {
                    fVar.fv(new ArrayList(Arrays.asList(strOptString.split(","))));
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(strOptString);
                    fVar.fv(arrayList);
                }
            }
            fVar.fu(z);
            fVar.ft(i);
            fVar.fx(z2);
            fVar.fw(i2);
            fVar.fr(zOptBoolean);
            fVar.fs(i3);
            return fVar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        if (fm() == fVar.fm() && fj() == fVar.fj() && fk() == fVar.fk() && fo() == fVar.fo() && fn() == fVar.fn() && fl() == fVar.fl() && fq() == fVar.fq()) {
            return TextUtils.equals(fp(), fVar.fp());
        }
        return false;
    }

    public boolean fj() {
        return this.cm;
    }

    public boolean fk() {
        return this.f2cn;
    }

    public int fl() {
        return this.cj;
    }

    public int fm() {
        return this.ck;
    }

    public boolean fn() {
        return this.cl;
    }

    public int fo() {
        return this.cp;
    }

    public String fp() {
        return this.cq;
    }

    public List fq() {
        return this.co;
    }

    public void fr(boolean z) {
        this.cl = z;
    }

    public void fs(int i) {
        this.cj = i;
    }

    public void ft(int i) {
        this.ck = i;
    }

    public void fu(boolean z) {
        this.cm = z;
    }

    public void fv(List list) {
        this.co = list;
    }

    public void fw(int i) {
        this.cp = i;
    }

    public void fx(boolean z) {
        this.f2cn = z;
    }

    public void fy(String str) {
        this.cq = str;
    }

    public String toString() {
        return "DataCollectConfig{isColOn=" + this.cm + ", colFreq=" + this.ck + ", isUploadOn=" + this.f2cn + ", uploadFreq=" + this.cp + ", isBindOn=" + this.cl + ", cacheDay=" + this.cj + ", url=" + this.cq + ", recordList=" + this.co + '}';
    }
}
