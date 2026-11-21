package a;

import android.text.TextUtils;
import com.tw.tdservice.app.CoreApplication;
import d.c;
import d.k;
import d.m;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static String f0a = "http://tdcore.car.cardoor.cn/tdcore/api";

    /* JADX INFO: renamed from: a.a$a, reason: collision with other inner class name */
    public static class C0000a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public static final String f1a = a.f0a + "/upgrade/info?";

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public static final String f2b = a.f0a + "/collect/upgradeinfo?";

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public static final String f3c = a.f0a + "/upgradeauto/upinfo?";

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public static final String f4d = a.f0a + "/upgradeauto/upresult?";
        public static final String e = a.f0a + "/collect/rescueinfo";
    }

    static {
        String str = k.f54a;
        String strB = m.b(CoreApplication.f30a);
        String str2 = k.f54a;
        boolean z = false;
        c.b("SystemInfoTools", "gmsVersion %s %s", str2, strB);
        if (TextUtils.isEmpty(str2) && "zh_CN".equals(strB)) {
            z = true;
        }
        f0a = z ? "http://tdcore.car.cardoor.cn/tdcore/api" : "http://tdcore.car.dofuncar.com/tdcore/api";
    }
}
