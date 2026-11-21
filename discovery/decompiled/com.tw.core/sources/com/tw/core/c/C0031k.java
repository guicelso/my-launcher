package com.tw.core.c;

import android.content.Context;
import android.text.TextUtils;
import cn.cardoor.user.bean.Token;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0057k;
import com.tw.core.utils.C0064r;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0031k {
    private static volatile C0031k ew;
    private final Object ev = new Object();
    private final List ex = new ArrayList();
    private Context es = DoFunApplication.nu();
    private String eu = kA();
    private volatile boolean et = C0064r.by(this.es, "KeyOverseasTrackSwitch", false);

    private String kA() {
        String strCS = null;
        Token tokenKz = kz();
        if (tokenKz != null && !TextUtils.isEmpty(tokenKz.getToken())) {
            strCS = new com.tw.core.utils.G(tokenKz.getToken()).cS();
        }
        com.tw.core.utils.B.ci("UserManager", "getUidByCache %s", strCS);
        return strCS;
    }

    private void kD(String str) {
        com.tw.core.utils.B.ci("UserManager", "setUserId %s", str);
        this.eu = str;
        C0064r.bB(this.es, "key_app_bind_user_id", str);
        synchronized (this.ex) {
            if (TextUtils.isEmpty(str)) {
                Iterator it = this.ex.iterator();
                while (it.hasNext()) {
                    ((com.tw.core.e.a) it.next()).jl();
                }
            } else {
                Iterator it2 = this.ex.iterator();
                while (it2.hasNext()) {
                    ((com.tw.core.e.a) it2.next()).jk(str);
                }
            }
        }
    }

    public static C0031k kt() {
        if (ew == null) {
            synchronized (C0031k.class) {
                if (ew == null) {
                    ew = new C0031k();
                }
            }
        }
        return ew;
    }

    public void kB(Token token) {
        synchronized (this.ev) {
            com.tw.core.utils.B.ci("UserManager", "setToken %s", token);
            if (token == null || TextUtils.isEmpty(token.getToken())) {
                C0064r.bB(this.es, "KEY_TOKEN_DATA", null);
                kD(null);
            } else {
                C0064r.bB(this.es, "KEY_TOKEN_DATA", token.convert2Json().toString());
                kD(new com.tw.core.utils.G(token.getToken()).cS());
            }
        }
    }

    public void kC(boolean z) {
        C0064r.bA(this.es, "KeyOverseasTrackSwitch", z);
        boolean z2 = !com.tw.core.b.e.gh() ? z : true;
        if (kx() != z2) {
            this.et = z;
            com.tw.core.utils.B.ci("UserManager", "new track switch %s", Boolean.valueOf(z2));
            Iterator it = this.ex.iterator();
            while (it.hasNext()) {
                ((com.tw.core.e.a) it.next()).jm(z2);
            }
        }
    }

    public String ku() {
        return this.eu;
    }

    public void kv(com.tw.core.e.a aVar) {
        if (aVar != null) {
            synchronized (this.ex) {
                com.tw.core.utils.B.ci("UserManager", "addUserListener %s", aVar);
                this.ex.add(aVar);
            }
        }
    }

    public void kw(com.tw.core.e.a aVar) {
        if (aVar != null) {
            synchronized (this.ex) {
                com.tw.core.utils.B.ci("UserManager", "removeUserListener %s", aVar);
                this.ex.remove(aVar);
            }
        }
    }

    public boolean kx() {
        return this.et;
    }

    public void ky(InterfaceC0032l interfaceC0032l) {
        Token tokenKz = kz();
        if (tokenKz == null || C0057k.am(tokenKz.getToken()) || C0057k.am(tokenKz.getRefreshToken())) {
            return;
        }
        String str = com.tw.core.d.a.gO + "refreshToken=" + tokenKz.getRefreshToken();
        HashMap map = new HashMap();
        map.put(com.tw.core.b.e.gh() ? "Authorization" : "D-T-F", tokenKz.getToken());
        C0048b.p().execute(new R(this, str, map, interfaceC0032l));
    }

    public Token kz() {
        try {
            String strBz = C0064r.bz(this.es, "KEY_TOKEN_DATA", null);
            com.tw.core.utils.B.ci("UserManager", "cache token %s", strBz);
            if (!TextUtils.isEmpty(strBz)) {
                return Token.json2Bean(new JSONObject(strBz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
