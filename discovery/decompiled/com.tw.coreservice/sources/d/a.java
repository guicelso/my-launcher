package d;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.tw.tdservice.app.CoreApplication;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final String f31a = a.class.getName() + ".DONE.";

    /* JADX INFO: renamed from: d.a$a, reason: collision with other inner class name */
    public class C0001a extends BroadcastReceiver {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f32a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ c f33b;

        public C0001a(String str, c cVar) {
            this.f32a = str;
            this.f33b = cVar;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().startsWith(a.f31a) && TextUtils.equals(intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME"), this.f32a)) {
                context.unregisterReceiver(this);
                int intExtra = intent.getIntExtra("android.content.pm.extra.STATUS", 1);
                a.b(this.f32a, intExtra != 0 ? intExtra : 1, this.f33b);
            }
        }
    }

    public class b extends IPackageInstallObserver.Stub {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final /* synthetic */ c f34a;

        public b(c cVar) {
            this.f34a = cVar;
        }

        public final void packageInstalled(String str, int i) {
            d.c.b("ApkController", "installPackage packageName=" + str + "\nreturnCode=" + i, new Object[0]);
            a.b(str, i, this.f34a);
        }
    }

    public interface c {
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00a5 A[Catch: Exception -> 0x00ae, TryCatch #2 {Exception -> 0x00ae, blocks: (B:4:0x0019, B:12:0x004c, B:14:0x0051, B:27:0x0071, B:30:0x00a5, B:32:0x00aa, B:33:0x00ad, B:24:0x0069, B:26:0x006e), top: B:45:0x0019 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x00aa A[Catch: Exception -> 0x00ae, TryCatch #2 {Exception -> 0x00ae, blocks: (B:4:0x0019, B:12:0x004c, B:14:0x0051, B:27:0x0071, B:30:0x00a5, B:32:0x00aa, B:33:0x00ad, B:24:0x0069, B:26:0x006e), top: B:45:0x0019 }] */
    public static void a(Context context, String str, c cVar) throws Throwable {
        int i;
        OutputStream outputStream;
        PackageManager packageManager = context.getPackageManager();
        File file = new File(str);
        String str2 = packageManager.getPackageArchiveInfo(str, 1).applicationInfo.packageName;
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                PackageInstaller packageInstaller = packageManager.getPackageInstaller();
                int iCreateSession = packageInstaller.createSession(new PackageInstaller.SessionParams(1));
                PackageInstaller.Session sessionOpenSession = packageInstaller.openSession(iCreateSession);
                FileInputStream fileInputStream = null;
                outputStreamOpenWrite = null;
                OutputStream outputStreamOpenWrite = null;
                fileInputStream = null;
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        outputStreamOpenWrite = sessionOpenSession.openWrite(file.getName(), 0L, -1L);
                        byte[] bArr = new byte[16384];
                        while (true) {
                            int i2 = fileInputStream2.read(bArr);
                            if (i2 == -1) {
                                break;
                            } else {
                                outputStreamOpenWrite.write(bArr, 0, i2);
                            }
                        }
                        fileInputStream2.close();
                        if (outputStreamOpenWrite != null) {
                            outputStreamOpenWrite.close();
                        }
                    } catch (Exception unused) {
                        outputStream = outputStreamOpenWrite;
                        fileInputStream = fileInputStream2;
                        try {
                            sessionOpenSession.abandon();
                            b(str2, -4, cVar);
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        outputStream = outputStreamOpenWrite;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                    outputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    outputStream = null;
                }
                String str3 = f31a + iCreateSession;
                context.registerReceiver(new C0001a(str2, cVar), new IntentFilter(str3));
                sessionOpenSession.commit(PendingIntent.getBroadcast(context, iCreateSession, new Intent(str3), 1207959552).getIntentSender());
                return;
            } catch (Exception e) {
                e.printStackTrace();
                i = -2;
            }
        } else {
            try {
                Method method = packageManager.getClass().getMethod("installPackage", Uri.class, IPackageInstallObserver.class, Integer.TYPE, String.class);
                method.setAccessible(true);
                method.invoke(packageManager, Uri.fromFile(file), new b(cVar), 2, "Settings");
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                i = -3;
            }
        }
        b(str2, i, cVar);
    }

    public static void b(String str, int i, c cVar) {
        d.c.b("ApkController", "packageName=" + str + "\nreturnCode=" + i, new Object[0]);
        Intent intent = new Intent("dofun.intent.action.APK_INSTALL_RESULT");
        intent.putExtra("packageName", str);
        intent.putExtra("returnCode", i);
        CoreApplication.f30a.sendBroadcast(intent);
        if (i != 1) {
            if (cVar != null) {
                c.d.a aVar = (c.d.a) cVar;
                c.d.this.g(aVar.f16a, aVar.f17b, String.valueOf(i));
            }
            d.c.c("ApkController", "安装失败=============", new Object[0]);
            return;
        }
        if (cVar != null) {
            c.d.a aVar2 = (c.d.a) cVar;
            c.d.this.g(aVar2.f16a, aVar2.f17b, "0");
        }
        if (CoreApplication.f30a.getPackageName().equals(str)) {
            System.exit(0);
        }
    }
}
