package com.tw.core.utils;

import android.content.Context;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class V implements InterfaceC0059m {
    final /* synthetic */ U aX;
    final /* synthetic */ Context aY;
    final /* synthetic */ String aZ;
    final /* synthetic */ Map ba;

    V(U u, Context context, String str, Map map) {
        this.aX = u;
        this.aY = context;
        this.aZ = str;
        this.ba = map;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        B.ci(C0047a.o, "初始化车机处理结果 %s", str);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
        boolean unused = C0047a.p = false;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        try {
            B.ci(C0047a.o, "初始化车机处理结果: %s", str);
            if ("0".equals(new JSONObject(C0062p.aT(str)).getString("code"))) {
                B.ci(C0047a.o, "初始化车机信息成功", new Object[0]);
                C0064r.bA(this.aY, "car_init_state", true);
                C0064r.bB(this.aY, "key_last_device_cid", com.tw.core.b.f.gr());
                C0064r.bA(this.aY, "key_cid_change", false);
                C0064r.bB(this.aY, "key_record_mcu_version", this.aZ);
                C0064r.bB(this.aY, "key_record_system_version", (String) this.ba.get("sysVersion"));
                C0064r.bB(this.aY, "key_record_resolution", (String) this.ba.get("screenResolution"));
            }
        } catch (Exception e) {
            C0065s.bL(C0047a.o, e);
        }
    }
}
