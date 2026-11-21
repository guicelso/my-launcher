package c;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import d.m;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class h extends c.a implements d.h.b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f25a = "TDService-TW";

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public long f26b = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Context f27c;

    public class a implements d.g.a {
        public a() {
        }

        @Override // d.g.a
        public final void a(String str) {
            d.c.c("UpgradeWorker", "upgrade failure %s", str);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x0054 A[Catch: Exception -> 0x0093, TryCatch #0 {Exception -> 0x0093, blocks: (B:3:0x0016, B:9:0x003c, B:11:0x0054, B:13:0x005c, B:8:0x0033, B:5:0x0020), top: B:51:0x0016, inners: #1 }] */
        /* JADX WARN: Code duplicated, block: B:13:0x005c A[Catch: Exception -> 0x0093, TRY_LEAVE, TryCatch #0 {Exception -> 0x0093, blocks: (B:3:0x0016, B:9:0x003c, B:11:0x0054, B:13:0x005c, B:8:0x0033, B:5:0x0020), top: B:51:0x0016, inners: #1 }] */
        /* JADX WARN: Code duplicated, block: B:17:0x009d  */
        @Override // d.g.a
        public final void b(String str) {
            b.a aVar;
            JSONObject jSONObject;
            String string;
            JSONObject jSONObjectOptJSONObject;
            boolean z = true;
            d.c.b("UpgradeWorker", "self upgrade response %s", str);
            h hVar = h.this;
            Objects.requireNonNull(hVar);
            int i = d.d.f38a;
            try {
                String str2 = m.f56a;
                if (TextUtils.isEmpty(str)) {
                    jSONObject = new JSONObject(str);
                    string = jSONObject.getString("code");
                    aVar = new b.a();
                    aVar.f5a = string;
                    if ("0".equals(string)) {
                        jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject != null) {
                            String strOptString = jSONObjectOptJSONObject.optString("title");
                            String strOptString2 = jSONObjectOptJSONObject.optString("name");
                            String strOptString3 = jSONObjectOptJSONObject.optString("path");
                            String strOptString4 = jSONObjectOptJSONObject.optString("flag");
                            String strOptString5 = jSONObjectOptJSONObject.optString("version");
                            String strOptString6 = jSONObjectOptJSONObject.optString("describe");
                            String strOptString7 = jSONObjectOptJSONObject.optString("appId");
                            aVar.f6b = strOptString2;
                            aVar.f7c = strOptString;
                            aVar.f8d = strOptString6;
                            aVar.e = strOptString4;
                            aVar.f = strOptString5;
                            aVar.g = strOptString3;
                            aVar.h = strOptString7;
                        }
                    } else {
                        aVar = null;
                    }
                } else {
                    try {
                        str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
                    } catch (Exception e) {
                        d.c.d("Tools", e);
                    }
                    jSONObject = new JSONObject(str);
                    string = jSONObject.getString("code");
                    aVar = new b.a();
                    aVar.f5a = string;
                    if ("0".equals(string)) {
                        jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject != null) {
                            String strOptString8 = jSONObjectOptJSONObject.optString("title");
                            String strOptString9 = jSONObjectOptJSONObject.optString("name");
                            String strOptString10 = jSONObjectOptJSONObject.optString("path");
                            String strOptString11 = jSONObjectOptJSONObject.optString("flag");
                            String strOptString12 = jSONObjectOptJSONObject.optString("version");
                            String strOptString13 = jSONObjectOptJSONObject.optString("describe");
                            String strOptString14 = jSONObjectOptJSONObject.optString("appId");
                            aVar.f6b = strOptString9;
                            aVar.f7c = strOptString8;
                            aVar.f8d = strOptString13;
                            aVar.e = strOptString11;
                            aVar.f = strOptString12;
                            aVar.g = strOptString10;
                            aVar.h = strOptString14;
                        }
                    } else {
                        aVar = null;
                    }
                }
            } catch (Exception e2) {
                d.c.d("d", e2);
            }
            if (aVar == null) {
                d.c.c("UpgradeWorker", "versionUpdateBean null", new Object[0]);
                return;
            }
            int iD = m.d(hVar.f27c);
            String str3 = aVar.e;
            d.c.b("UpgradeWorker", "当前版本:" + iD + "服务器版本:" + str3, new Object[0]);
            if (m.f(str3) <= iD) {
                d.c.b("UpgradeWorker", "当前程序为最新版本", new Object[0]);
                return;
            }
            d.c.b("UpgradeWorker", "为旧版本", new Object[0]);
            if (TextUtils.isEmpty(str3)) {
                z = false;
            } else {
                String str4 = d.d.b() + "/download/tdservice.apk";
                File file = new File(str4);
                if (file.exists() && file.isFile()) {
                    d.c.b("UpgradeWorker", "存在", new Object[0]);
                    PackageInfo packageArchiveInfo = hVar.f27c.getPackageManager().getPackageArchiveInfo(str4, 1);
                    if (packageArchiveInfo != null) {
                        String str5 = packageArchiveInfo.packageName;
                        String str6 = packageArchiveInfo.versionName;
                        int i2 = packageArchiveInfo.versionCode;
                        d.c.b("UpgradeWorker", "packageName=" + str5 + "  apkVersionName=" + str6, new Object[0]);
                        if (str5.equals(hVar.f27c.getPackageName()) && m.f(str3) == i2) {
                            d.c.b("UpgradeWorker", "本地有服务器版本", new Object[0]);
                        } else {
                            d.c.b("UpgradeWorker", "本地有版本，但是是旧版本！", new Object[0]);
                        }
                    }
                    file.delete();
                } else {
                    d.c.c("UpgradeWorker", "不存在", new Object[0]);
                }
                z = false;
            }
            if (z) {
                d.c.b("UpgradeWorker", "存在已经下载的apk", new Object[0]);
                hVar.d();
                return;
            }
            if (TextUtils.isEmpty(aVar.g)) {
                return;
            }
            d.c.b("UpgradeWorker", "开始下载", new Object[0]);
            boolean zA = d.g.a(aVar.g, "tdservice.apk");
            d.c.b("UpgradeWorker", "downloadResult:" + zA, new Object[0]);
            if (zA) {
                String str7 = aVar.h;
                if (TextUtils.isEmpty(str7)) {
                    return;
                }
                HashMap map = new HashMap();
                map.put("appId", str7);
                map.put("cid", m.a());
                map.put("strategyId", hVar.f25a);
                d.g.d(map, a.a.C0000a.f4d, new i(hVar));
            }
        }

        @Override // d.g.a
        public final void c() {
        }
    }

    public h(Context context) {
        this.f27c = context;
        d.h.b().a(this);
    }

    @Override // d.h.b
    public final void a(boolean z) {
        if (z) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            d.c.b("UpgradeWorker", "current %s last %s", Long.valueOf(jCurrentTimeMillis / 1000), Long.valueOf(this.f26b / 1000));
            if (jCurrentTimeMillis - this.f26b >= 300000) {
                b();
                this.f26b = jCurrentTimeMillis;
            }
        }
    }

    @Override // c.a
    public final void c() {
        String strSubstring;
        String str;
        Context context = this.f27c;
        try {
            strSubstring = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toCharsString().substring(0, 32);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            strSubstring = "";
        }
        if (!"3082039130820279a003020102020900".equals(strSubstring)) {
            str = "308203953082027da003020102020900".equals(strSubstring) ? "TDService-TW-T3" : "TDService-TW-TS8";
            d.g.b(a.a.C0000a.f3c + "cid=" + m.a() + "&strategyId=" + this.f25a + "&flag=" + m.d(this.f27c) + "&plat=" + Build.MODEL + "&androidVersion=" + Build.VERSION.RELEASE + "&systemVersion=" + m.c() + "&language=" + m.b(this.f27c), new a());
        }
        this.f25a = str;
        d.g.b(a.a.C0000a.f3c + "cid=" + m.a() + "&strategyId=" + this.f25a + "&flag=" + m.d(this.f27c) + "&plat=" + Build.MODEL + "&androidVersion=" + Build.VERSION.RELEASE + "&systemVersion=" + m.c() + "&language=" + m.b(this.f27c), new a());
    }

    public final void d() {
        String str = d.d.b() + "/download/tdservice.apk";
        d.c.b("UpgradeWorker", "安装路径：" + str, new Object[0]);
        if (new File(str).exists()) {
            d.a.a(this.f27c, str, null);
        }
    }
}
