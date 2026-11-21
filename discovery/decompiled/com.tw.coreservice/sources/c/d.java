package c;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import com.tw.tdservice.app.CoreApplication;
import d.m;
import java.io.File;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class d extends c.a implements d.h.b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public long f14a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Context f15b;

    public class a implements d.a.c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f16a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f17b;

        public a(String str, String str2) {
            this.f16a = str;
            this.f17b = str2;
        }
    }

    public class b implements d.g.a {
        @Override // d.g.a
        public final void a(String str) {
            d.c.c("CoreUpgradeWorker", "reportCoreUpgradeStatus onFailure %s", str);
        }

        @Override // d.g.a
        public final void b(String str) {
            d.c.b("CoreUpgradeWorker", "reportCoreUpgradeStatus onResponse %s", str);
        }

        @Override // d.g.a
        public final void c() {
        }
    }

    public d(Context context) {
        this.f15b = context;
        d.h.b().a(this);
    }

    @Override // d.h.b
    public final void a(boolean z) {
        if (z) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            d.c.b("CoreUpgradeWorker", "current %s last %s", Long.valueOf(jCurrentTimeMillis / 1000), Long.valueOf(this.f14a / 1000));
            if (jCurrentTimeMillis - this.f14a >= 300000) {
                b();
                this.f14a = jCurrentTimeMillis;
            }
        }
    }

    @Override // c.a
    public final void c() {
        PackageInfo packageInfo;
        d.c.b("CoreUpgradeWorker", "onWork", new Object[0]);
        Context context = this.f15b;
        int i = d.b.f35a;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.tw.core", 1);
        } catch (Exception unused) {
            packageInfo = null;
        }
        d.g.b(a.a.C0000a.f1a + "cid=" + m.a() + "&flag=" + (packageInfo != null ? packageInfo.versionCode : 0) + "&plat=" + Build.MODEL + "&androidVersion=" + Build.VERSION.RELEASE + "&sysVersion=" + m.c() + "&language=" + m.b(this.f15b), new c(this));
    }

    public final void d(String str, String str2) {
        PackageInfo packageInfo;
        Context context = this.f15b;
        int i = d.b.f35a;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.tw.core", 1);
        } catch (Exception unused) {
            packageInfo = null;
        }
        String strValueOf = packageInfo != null ? String.valueOf(packageInfo.versionCode) : "0";
        String str3 = d.d.b() + "/download/twcore.apk";
        File file = new File(str3);
        if (file.exists() && file.isFile()) {
            PackageInfo packageArchiveInfo = CoreApplication.f30a.getPackageManager().getPackageArchiveInfo(str3, 1);
            if (packageArchiveInfo == null) {
                d.c.b("CoreUpgradeWorker", "文件不完整 删除结果%s", Boolean.valueOf(file.delete()));
            } else {
                if (m.f(str2) <= packageArchiveInfo.versionCode) {
                    if (m.f(str2) != packageArchiveInfo.versionCode) {
                        d.c.b("CoreUpgradeWorker", "删除本地的新版本%s", Boolean.valueOf(file.delete()));
                        return;
                    } else {
                        d.c.b("CoreUpgradeWorker", "本地有服务器版本 直接安装", new Object[0]);
                        f(strValueOf, str2);
                        return;
                    }
                }
                d.c.b("CoreUpgradeWorker", "删除本地版本%s 下载新版本 %s", Boolean.valueOf(file.delete()), Integer.valueOf(packageArchiveInfo.versionCode));
            }
        } else {
            d.c.b("CoreUpgradeWorker", "本地无下载文件", new Object[0]);
        }
        e(str, strValueOf, str2);
    }

    public final void e(String str, String str2, String str3) throws Throwable {
        boolean zA = d.g.a(str, "twcore.apk");
        d.c.b("CoreUpgradeWorker", "downloadResult:" + zA, new Object[0]);
        g(str2, str3, zA ? "100" : "99");
        if (zA) {
            f(str2, str3);
        }
    }

    public final void f(String str, String str2) throws Throwable {
        String str3 = d.d.b() + "/download/twcore.apk";
        d.c.b("CoreUpgradeWorker", "安装路径：" + str3, new Object[0]);
        if (new File(str3).exists()) {
            d.a.a(this.f15b, str3, new a(str, str2));
        }
    }

    public final void g(String str, String str2, String str3) {
        HashMap map = new HashMap();
        map.put("cid", m.a());
        map.put("upgradeNew", str2);
        map.put("upgradeOld", str);
        map.put("upgradeStatus", str3);
        d.g.d(map, a.a.C0000a.f2b, new b());
    }
}
