package com.tw.core.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* JADX INFO: renamed from: com.tw.core.utils.u, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0067u {
    private static final String ak = C0067u.class.getName() + ".DONE.";

    public static void bP(Context context, String str, InterfaceC0053g interfaceC0053g) {
        if (TextUtils.isEmpty(str) || !com.tw.core.b.d.fY(str)) {
            B.cj("ApkController", "%s 未安装", str);
        } else {
            context.getPackageManager().deletePackage(str, new C0069w(interfaceC0053g), 0);
        }
    }

    public static void bQ(Context context, String str) {
        bR(context, str, null);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00b1 A[Catch: Exception -> 0x00a3, TRY_ENTER, TryCatch #7 {Exception -> 0x00a3, blocks: (B:6:0x001e, B:25:0x009a, B:22:0x0065, B:27:0x009f, B:19:0x005d, B:21:0x0062, B:34:0x00b1, B:36:0x00b6, B:37:0x00b9), top: B:57:0x001e }] */
    /* JADX WARN: Code duplicated, block: B:36:0x00b6 A[Catch: Exception -> 0x00a3, TryCatch #7 {Exception -> 0x00a3, blocks: (B:6:0x001e, B:25:0x009a, B:22:0x0065, B:27:0x009f, B:19:0x005d, B:21:0x0062, B:34:0x00b1, B:36:0x00b6, B:37:0x00b9), top: B:57:0x001e }] */
    public static void bR(Context context, String str, InterfaceC0068v interfaceC0068v) {
        OutputStream outputStreamOpenWrite;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        bS();
        PackageManager packageManager = context.getPackageManager();
        File file = new File(str);
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 1);
        if (packageArchiveInfo != null) {
            String str2 = packageArchiveInfo.applicationInfo.packageName;
            if (Build.VERSION.SDK_INT < 29) {
                try {
                    Method method = packageManager.getClass().getMethod("installPackage", Uri.class, IPackageInstallObserver.class, Integer.TYPE, String.class);
                    method.setAccessible(true);
                    method.invoke(packageManager, Uri.fromFile(file), new ah(interfaceC0068v), 2, "Settings");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    bT(null, str2, -3, interfaceC0068v);
                    return;
                }
            }
            try {
                PackageInstaller packageInstaller = packageManager.getPackageInstaller();
                int iCreateSession = packageInstaller.createSession(new PackageInstaller.SessionParams(1));
                PackageInstaller.Session sessionOpenSession = packageInstaller.openSession(iCreateSession);
                try {
                    fileInputStream = new FileInputStream(file);
                    try {
                        outputStreamOpenWrite = sessionOpenSession.openWrite(file.getName(), 0L, -1L);
                        try {
                            byte[] bArr = new byte[16384];
                            while (true) {
                                int i = fileInputStream.read(bArr);
                                if (i == -1) {
                                    break;
                                } else {
                                    outputStreamOpenWrite.write(bArr, 0, i);
                                }
                            }
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (outputStreamOpenWrite != null) {
                                outputStreamOpenWrite.close();
                            }
                        } catch (Exception e2) {
                            fileInputStream2 = fileInputStream;
                            try {
                                sessionOpenSession.abandon();
                                bT(null, str2, -4, interfaceC0068v);
                                if (fileInputStream2 != null) {
                                    fileInputStream2.close();
                                }
                                if (outputStreamOpenWrite != null) {
                                    outputStreamOpenWrite.close();
                                }
                            } catch (Throwable th) {
                                th = th;
                                fileInputStream = fileInputStream2;
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (outputStreamOpenWrite != null) {
                                    outputStreamOpenWrite.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (outputStreamOpenWrite != null) {
                                outputStreamOpenWrite.close();
                            }
                            throw th;
                        }
                    } catch (Exception e3) {
                        outputStreamOpenWrite = null;
                        fileInputStream2 = fileInputStream;
                    } catch (Throwable th3) {
                        th = th3;
                        outputStreamOpenWrite = null;
                    }
                } catch (Exception e4) {
                    outputStreamOpenWrite = null;
                    fileInputStream2 = null;
                } catch (Throwable th4) {
                    th = th4;
                    outputStreamOpenWrite = null;
                    fileInputStream = null;
                }
                String str3 = ak + iCreateSession;
                context.registerReceiver(new ag(str2, interfaceC0068v), new IntentFilter(str3));
                sessionOpenSession.commit(PendingIntent.getBroadcast(context, iCreateSession, new Intent(str3), 1207959552).getIntentSender());
            } catch (Exception e5) {
                e5.printStackTrace();
                bT(null, str2, -2, interfaceC0068v);
            }
        }
    }

    private static void bS() {
        if (com.tw.core.b.e.gm()) {
            SystemProperties.set("sys.tw.app", "1");
            C0065s.bK("ApkController", "sys.tw.app 1");
        }
        if (com.tw.core.b.e.gh() || Build.VERSION.SDK_INT < 17) {
            return;
        }
        int i = Settings.Global.getInt(DoFunApplication.nu().getContentResolver(), "package_verifier_user_consent", -1);
        C0065s.bK("ApkController", "package_verifier_user_consent " + (i == 1));
        if (i == 1) {
            Settings.Global.putInt(DoFunApplication.nu().getContentResolver(), "package_verifier_user_consent", -1);
        }
        C0065s.bK("ApkController", "package_verifier_user_consent result  " + Settings.Global.getInt(DoFunApplication.nu().getContentResolver(), "package_verifier_user_consent", -1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void bT(Intent intent, String str, int i, InterfaceC0068v interfaceC0068v) {
        C0065s.bK("ApkController", "packageName=" + str + "\nreturnCode=" + i);
        Intent intent2 = new Intent("dofun.intent.action.APK_INSTALL_RESULT");
        intent2.putExtra("packageName", str);
        intent2.putExtra("returnCode", i);
        DoFunApplication.nu().sendBroadcast(intent2);
        if (i == 1) {
            if (interfaceC0068v != null) {
                interfaceC0068v.bY(str);
            }
            if (DoFunApplication.nu().getPackageName().equals(str)) {
                System.exit(0);
                return;
            }
            return;
        }
        if (interfaceC0068v != null) {
            if (Build.VERSION.SDK_INT >= 21 && intent != null) {
                C0065s.bK("ApkController", "安装失败 " + intent.getStringExtra("android.content.pm.extra.STATUS_MESSAGE"));
            }
            interfaceC0068v.bX(i, str);
        }
        C0065s.bK("ApkController", "安装失败=============");
    }

    public static boolean bU(Context context, File file) {
        Uri uriFromFile;
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            if (Build.VERSION.SDK_INT >= 24) {
                uriFromFile = FileProvider.dz(context.getApplicationContext(), context.getPackageName() + ".fileProvider", file);
                intent.addFlags(1);
            } else {
                uriFromFile = Uri.fromFile(file);
            }
            intent.addFlags(ClientDefaults.MAX_MSG_SIZE);
            intent.setDataAndType(uriFromFile, "application/vnd.android.package-archive");
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
