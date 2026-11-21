package c;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.text.TextUtils;
import com.tw.tdservice.app.CoreApplication;
import d.j;
import d.m;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b extends a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f9a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public long f10b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f11c = 0;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public long f12d = 300000;
    public g e;

    public b(Context context) {
        this.f9a = context;
    }

    /* JADX WARN: Code duplicated, block: B:72:0x019e  */
    @Override // c.a
    public final void c() {
        boolean z;
        boolean zContains;
        int i;
        boolean z2;
        g gVar;
        String strD;
        d.c.b("CoreProcessChecker", "onWork", new Object[0]);
        while (this.f11c < 3) {
            try {
                Thread.sleep(this.f12d);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            d.c.b("CoreProcessChecker", "checkCoreProcessExist", new Object[0]);
            if (!((HashSet) j.a()).contains("com.tw.core")) {
                d.c.b("CoreProcessChecker", "core no running", new Object[0]);
                int i2 = d.b.f35a;
                try {
                    z = CoreApplication.f30a.getPackageManager().getPackageInfo("com.tw.core", 0) != null;
                } catch (PackageManager.NameNotFoundException unused) {
                    d.c.c("b", "com.tw.core 未安装", new Object[0]);
                }
                if (z) {
                    d.c.b("CoreProcessChecker", "core exist", new Object[0]);
                    long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                    this.f10b = jCurrentTimeMillis;
                    g gVar2 = new g(this.f9a, String.valueOf(jCurrentTimeMillis));
                    this.e = gVar2;
                    d.c.b("LogModel", "catchSystemLogcat", new Object[0]);
                    if (gVar2.f23c == null) {
                        Thread thread = new Thread(new e(gVar2));
                        gVar2.f23c = thread;
                        thread.start();
                    }
                    ComponentName componentNameA = d.b.a();
                    d.c.b("CoreProcessChecker", "startService result %s", componentNameA);
                    if (componentNameA == null) {
                        zContains = false;
                    } else {
                        try {
                            Thread.sleep(5000L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        zContains = ((HashSet) j.a()).contains("com.tw.core");
                    }
                    d.c.b("CoreProcessChecker", "launchCoreProcess result %s", Boolean.valueOf(zContains));
                    if (zContains) {
                        this.f11c = 0;
                        this.f12d = 300000L;
                    } else {
                        this.f11c++;
                        this.f12d *= 2;
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e3) {
                            e3.printStackTrace();
                        }
                    }
                    g gVar3 = this.e;
                    if (gVar3 != null) {
                        d.c.b("LogModel", "stopCatchSystemLogcat %s", gVar3.f23c);
                        Thread thread2 = gVar3.f23c;
                        if (thread2 != null) {
                            thread2.interrupt();
                            gVar3.f23c = null;
                        }
                    }
                    HashMap map = new HashMap();
                    map.put("cid", m.a());
                    map.put("language", m.b(this.f9a));
                    map.put("rescueStatus", zContains ? "0" : "1");
                    map.put("rescueTime", String.valueOf(this.f10b));
                    map.put("sysVersion", m.c());
                    Context context = this.f9a;
                    int i3 = d.b.f35a;
                    try {
                        i = context.getPackageManager().getPackageInfo("com.tw.core", 0).versionCode;
                    } catch (Exception unused2) {
                        i = -1;
                    }
                    map.put("twcoreVersion", String.valueOf(i));
                    new JSONObject(map);
                    if (!d.i.a(this.f9a) || (strD = d.g.d(map, a.a.C0000a.e, null)) == null) {
                        z2 = true;
                    } else {
                        try {
                            d.c.b("CoreProcessChecker", "reportLaunchInfo %s", strD);
                            JSONObject jSONObject = new JSONObject(strD);
                            if (!"0".equals(jSONObject.optString("code")) || zContains) {
                                z2 = true;
                            } else {
                                String strOptString = jSONObject.optString("data");
                                if (TextUtils.isEmpty(strOptString) || TextUtils.equals("null", strOptString)) {
                                    z2 = true;
                                } else {
                                    try {
                                        d(strOptString);
                                        z2 = false;
                                    } catch (JSONException e4) {
                                        e = e4;
                                        z2 = false;
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (JSONException e5) {
                            e = e5;
                            z2 = true;
                        }
                    }
                    if (z2 && (gVar = this.e) != null) {
                        d.c.b("LogModel", "deleteCatchLog %s", Boolean.valueOf(new File(gVar.f22b).delete()));
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0099  */
    /* JADX WARN: Code duplicated, block: B:33:0x0144  */
    /* JADX WARN: Code duplicated, block: B:34:0x0151  */
    /* JADX WARN: Code duplicated, block: B:45:? A[RETURN, SYNTHETIC] */
    public final void d(final String str) {
        File file;
        final HashMap map;
        String str2;
        final HashMap map2;
        final f fVar;
        g gVar = this.e;
        if (gVar == null || gVar.f22b == null) {
            return;
        }
        File file2 = new File(gVar.f22b);
        if (file2.exists() && file2.isFile()) {
            String str3 = file2.getAbsolutePath() + ".zip";
            String absolutePath = file2.getAbsolutePath();
            int i = d.d.f38a;
            File file3 = new File(absolutePath);
            String str4 = null;
            if (file3.exists()) {
                file = new File(str3);
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    d.c.b("d", "开始压缩", new Object[0]);
                    ZipOutputStream zipOutputStream = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(file), new CRC32()));
                    d.d.e(zipOutputStream, "DFLog", file3);
                    zipOutputStream.flush();
                    zipOutputStream.close();
                    d.c.b("d", "结束压缩 耗时 %s", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
                } catch (Exception e) {
                    e.printStackTrace();
                    file = null;
                }
                if (file != null) {
                    file2.delete();
                    map = new HashMap();
                    StringBuilder sb = new StringBuilder();
                    sb.append("tdlog/");
                    Context context = gVar.f21a;
                    int i2 = d.b.f35a;
                    try {
                        str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        str2 = "";
                    }
                    sb.append(str2);
                    sb.append("/");
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    String str5 = m.f56a;
                    try {
                        str4 = new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(jCurrentTimeMillis2));
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    sb.append(str4);
                    sb.append("/");
                    sb.append(m.a());
                    sb.append("/");
                    String string = sb.toString();
                    map.put("key", string + System.currentTimeMillis() + "-" + file.getName());
                    map2 = new HashMap();
                    map2.put("file", file);
                    d.c.b("LogModel", "uploadSystemLog %s", string);
                    fVar = new f(file);
                    d.g gVar2 = d.g.f48a;
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        Executors.newCachedThreadPool().execute(new Runnable() { // from class: d.e
                            @Override // java.lang.Runnable
                            public final void run() {
                                g.f(str, map, map2, fVar);
                            }
                        });
                    } else {
                        d.g.f(str, map, map2, fVar);
                    }
                }
            }
            d.c.c("d", "文件不存在", new Object[0]);
            file = null;
            if (file != null) {
                file2.delete();
                map = new HashMap();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("tdlog/");
                Context context2 = gVar.f21a;
                int i3 = d.b.f35a;
                str2 = context2.getPackageManager().getPackageInfo(context2.getPackageName(), 0).versionName;
                sb2.append(str2);
                sb2.append("/");
                long jCurrentTimeMillis3 = System.currentTimeMillis();
                String str6 = m.f56a;
                str4 = new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(jCurrentTimeMillis3));
                sb2.append(str4);
                sb2.append("/");
                sb2.append(m.a());
                sb2.append("/");
                String string2 = sb2.toString();
                map.put("key", string2 + System.currentTimeMillis() + "-" + file.getName());
                map2 = new HashMap();
                map2.put("file", file);
                d.c.b("LogModel", "uploadSystemLog %s", string2);
                fVar = new f(file);
                d.g gVar3 = d.g.f48a;
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    Executors.newCachedThreadPool().execute(new Runnable() { // from class: d.e
                        @Override // java.lang.Runnable
                        public final void run() {
                            g.f(str, map, map2, fVar);
                        }
                    });
                } else {
                    d.g.f(str, map, map2, fVar);
                }
            }
        }
    }
}
