package com.dofun.carsetting.widget;

import android.app.ActivityManagerNative;
import android.app.AlarmManager;
import android.app.IActivityManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.FileUtils;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.graphics.drawable.PathInterpolatorCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.app.LocalePicker;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.f.u;
import com.dofun.carsetting.f.v;
import com.dofun.carsetting.f.w;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: loaded from: classes.dex */
public class ImportExportExtensionSettings {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static String f426a = "ImportExportExtensionSettings";
    public static com.dofun.carsetting.Receiver.a y;
    private u C;
    private int aA;
    private String aB;
    private boolean aC;
    private boolean aD;
    private com.dofun.carsetting.b.a aH;
    private ProgressDialog aI;
    private com.tw.a.a.a.a aL;
    private boolean aM;
    private BufferedWriter aN;
    private BufferedReader aO;
    private String aP;
    private int aQ;
    private String[] aU;
    private String[] aW;
    private Context az;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public String o;
    public String p;
    public String q;
    public String r;
    public String s;
    public String t;
    public String u;
    public String v;
    public String w;
    private static final boolean B = SystemProperties.getBoolean("persist.debug.carsetting", false);
    public static ProgressDialog x = null;
    public boolean b = false;
    public boolean c = false;
    private final String D = "system_usb11";
    private final String E = "system_usbhub";
    private final String F = "system_mirror";
    private final String G = "system_mirror_vertical";
    private final String H = "forward_looking_mirror";
    private final String I = "front_view_channel";
    private final String J = "system_line";
    private final String K = "system_line2";
    private final String L = "door";
    private final String M = "system_door2";
    private final String N = "system_fradar";
    private final String O = "ahd360";
    private final String P = "reversingIC";
    private final String Q = "system_iis";
    private final String R = "eq_settings";
    private final String S = "dsp_mode";
    private final String T = "system_floatvideo";
    private final String U = "system_ta";
    private final String V = "left_right_seat";
    private final String W = "left_right_seat_real";
    private final String X = "tvd0";
    private final String Y = "tvd1";
    private final String Z = "tvd2";
    private final String aa = "tvd3";
    private final String ab = "radio_region";
    private final String ac = "brake_mode";
    private final String ad = "tv_mode";
    private final String ae = "tv_norm";
    private final String af = "panel_light_color";
    private final String ag = "panel_light";
    private final String ah = "knobs1";
    private final String ai = "knobs2";
    private final String aj = "x_knobs";
    private final String ak = "ant_pwr";
    private final String al = "ampvol";
    private final String am = "swap_last_next";
    private final String an = "resolution";
    private final String ao = "tw_theme";
    private final String ap = "tw_cardoor";
    private final String aq = "tw_pip";
    private final String ar = "back_light_v";
    private final String as = "launcher_type";
    private final String at = "bba_carMode";
    private final String au = "system_colorTemp";
    private final String av = "spdif_out";
    private final String aw = "microphone_parameter";
    private final String ax = "lcd_enhance";
    private String ay = "";
    private boolean aE = false;
    private boolean aF = false;
    private boolean aG = false;
    private int aJ = 5;
    private int aK = 0;
    public int z = -1;
    private int aR = 20;
    private int aS = 80;
    String A = "/soc@03000000";
    private Handler aT = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                int i = message.what;
                boolean z = true;
                int i2 = 1;
                if (i == 260) {
                    byte[] bArr = (byte[]) message.obj;
                    ImportExportExtensionSettings.this.s = String.valueOf((int) bArr[0]);
                    Settings.System.putInt(ImportExportExtensionSettings.this.az.getContentResolver(), "sound_effects_enabled", bArr[0] == 0 ? 0 : 1);
                    ImportExportExtensionSettings.this.t = String.valueOf(bArr[5] & 255);
                    if (bArr.length > 8) {
                        ImportExportExtensionSettings.this.v = String.valueOf(bArr[8] & 255);
                    } else {
                        ImportExportExtensionSettings.this.v = String.valueOf(bArr[5] & 255);
                    }
                    if (bArr.length > 12) {
                        ImportExportExtensionSettings.this.w = String.valueOf(bArr[12] & 255);
                    } else {
                        ImportExportExtensionSettings.this.w = "5";
                    }
                } else if (i == 265) {
                    ImportExportExtensionSettings.this.e = String.valueOf((int) ((byte[]) message.obj)[0]);
                } else if (i == 267) {
                    byte[] bArr2 = (byte[]) message.obj;
                    ImportExportExtensionSettings.this.f = String.valueOf((int) bArr2[1]);
                    if ("3".equals(String.valueOf((int) bArr2[2]))) {
                        ImportExportExtensionSettings.this.g = "3";
                    } else {
                        ImportExportExtensionSettings.this.g = String.valueOf((int) bArr2[2]);
                    }
                    ImportExportExtensionSettings.this.h = String.valueOf((int) bArr2[3]);
                    ImportExportExtensionSettings.this.j = String.valueOf((int) bArr2[4]);
                    ImportExportExtensionSettings.this.k = String.valueOf((bArr2[7] & 1) == 1 ? 1 : 0);
                    ImportExportExtensionSettings.this.l = String.valueOf((bArr2[7] & 2) == 2 ? 1 : 0);
                    ImportExportExtensionSettings importExportExtensionSettings = ImportExportExtensionSettings.this;
                    if ((bArr2[7] & 4) != 4) {
                        i2 = 0;
                    }
                    importExportExtensionSettings.m = String.valueOf(i2);
                    ImportExportExtensionSettings.this.n = String.valueOf((int) bArr2[8]);
                    ImportExportExtensionSettings.this.p = String.valueOf((int) bArr2[15]);
                    ImportExportExtensionSettings.this.q = String.valueOf((int) bArr2[10]);
                    ImportExportExtensionSettings.this.u = String.valueOf((int) bArr2[19]);
                } else if (i == 272) {
                    byte[] bArr3 = (byte[]) message.obj;
                    ImportExportExtensionSettings.this.i = String.valueOf((bArr3[2] & 255) | ((bArr3[0] & 255) << 16) | ((bArr3[1] & 255) << 8));
                } else if (i != 276) {
                    switch (i) {
                        case 65521:
                            if (ImportExportExtensionSettings.B) {
                                Log.d(ImportExportExtensionSettings.f426a, "step:import fail. ->cmd:" + ImportExportExtensionSettings.this.aA);
                            }
                            ImportExportExtensionSettings.this.z = AUTOMATIC_IMPORT_STATE.AUTO_IMPORT_FAIL.ordinal();
                            Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.cfg_import_fail), 0).show();
                            if (ImportExportExtensionSettings.x != null) {
                                ImportExportExtensionSettings.x.dismiss();
                            }
                            if (ImportExportExtensionSettings.this.aM) {
                                Settings.System.putInt(MyApp.a().getContentResolver(), "RestoreFactoryImportState", 0);
                            }
                            break;
                        case 65522:
                            if (ImportExportExtensionSettings.B) {
                                Log.d(ImportExportExtensionSettings.f426a, "step:import success. ->cmd:" + ImportExportExtensionSettings.this.aA);
                            }
                            if (ImportExportExtensionSettings.x != null) {
                                ImportExportExtensionSettings.x.dismiss();
                            }
                            ImportExportExtensionSettings.this.z = AUTOMATIC_IMPORT_STATE.AUTO_IMPORT_SUCCESS.ordinal();
                            Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.cfg_import_success), 0).show();
                            ImportExportExtensionSettings.this.aG = true;
                            if (ImportExportExtensionSettings.this.aM) {
                                Settings.System.putInt(MyApp.a().getContentResolver(), "RestoreFactoryImportState", 1);
                                new j(ImportExportExtensionSettings.this.az).a();
                            }
                            ImportExportExtensionSettings.this.aT.sendEmptyMessageDelayed(65532, 1000L);
                            break;
                        case 65523:
                            Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.cfg_export_fail), 0).show();
                            if (ImportExportExtensionSettings.x != null) {
                                ImportExportExtensionSettings.x.dismiss();
                            }
                            break;
                        case 65524:
                            if (ImportExportExtensionSettings.x != null) {
                                ImportExportExtensionSettings.x.dismiss();
                            }
                            Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.cfg_export_success), 0).show();
                            break;
                        default:
                            switch (i) {
                                case 65528:
                                    int i3 = message.arg1;
                                    int i4 = message.arg2;
                                    Log.d(ImportExportExtensionSettings.f426a, "APK_INSTALL_STATE：currentApkPos:" + i3 + " apkCount:" + i4);
                                    MyApp myAppA = MyApp.a();
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(String.format(MyApp.a().getString(R.string.apk_install_state), Integer.valueOf(i3), Integer.valueOf(i4)));
                                    sb.append("");
                                    Toast.makeText(myAppA, sb.toString(), 0).show();
                                    break;
                                case 65529:
                                    if (ImportExportExtensionSettings.B) {
                                        Log.d(ImportExportExtensionSettings.f426a, "step:reboot system. ->cmd:" + ImportExportExtensionSettings.this.aA);
                                    }
                                    if (ImportExportExtensionSettings.this.aG) {
                                        if (ImportExportExtensionSettings.this.ay.length() != 0) {
                                            z = false;
                                        }
                                        if (ImportExportExtensionSettings.this.aM && !z) {
                                            Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.system_will_reboot), 0).show();
                                            ImportExportExtensionSettings.this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.1.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    ImportExportExtensionSettings.this.l();
                                                }
                                            }, 3000L);
                                        } else {
                                            com.dofun.carsetting.c.a aVar = new com.dofun.carsetting.c.a(ImportExportExtensionSettings.this.az, z);
                                            aVar.a(MyApp.a().getString(R.string.system_will_reboot));
                                            aVar.a(new com.dofun.carsetting.c.a.InterfaceC0013a() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.1.2
                                                @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                                                public void b() {
                                                }

                                                @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                                                public void a() {
                                                    ImportExportExtensionSettings.this.l();
                                                }

                                                @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                                                public void c() {
                                                    ImportExportExtensionSettings.this.l();
                                                }
                                            });
                                            aVar.show();
                                        }
                                    }
                                    break;
                                case 65530:
                                    if (ImportExportExtensionSettings.B) {
                                        Log.d(ImportExportExtensionSettings.f426a, "step:auto copy data success. ->cmd:" + ImportExportExtensionSettings.this.aA);
                                    }
                                    if (ImportExportExtensionSettings.this.aA == 1) {
                                        ImportExportExtensionSettings.this.z = AUTOMATIC_COPY_STATE.AUTO_COPY_COMPLETE.ordinal();
                                    } else {
                                        ImportExportExtensionSettings.this.z = AUTOMATIC_COPY_AND_UPDATE_STATE.AUTO_COPY_COMPLETE.ordinal();
                                    }
                                    if (ImportExportExtensionSettings.this.aI != null) {
                                        ImportExportExtensionSettings.this.aI.dismiss();
                                    }
                                    if (ImportExportExtensionSettings.this.aF) {
                                        ImportExportExtensionSettings.this.aT.sendEmptyMessage(65531);
                                    }
                                    break;
                                case 65531:
                                    ImportExportExtensionSettings.this.d(ImportExportExtensionSettings.this.aB + "/" + ImportExportExtensionSettings.this.aH.w);
                                    break;
                                case 65532:
                                    Log.d(ImportExportExtensionSettings.f426a, "CHECK_UPDATE_OR_REBOOT");
                                    File file = new File(ImportExportExtensionSettings.this.aB + "/" + ImportExportExtensionSettings.this.aH.y);
                                    if ((!file.exists() || !file.canRead()) && ImportExportExtensionSettings.this.aK != ImportExportExtensionSettings.this.aJ) {
                                        ImportExportExtensionSettings.this.aT.sendEmptyMessageDelayed(65532, 1000L);
                                        ImportExportExtensionSettings.n(ImportExportExtensionSettings.this);
                                    } else {
                                        ArrayList arrayListG = ImportExportExtensionSettings.this.g((String) null);
                                        if (arrayListG.size() == 0 || ImportExportExtensionSettings.this.aM) {
                                            ImportExportExtensionSettings.this.aT.sendEmptyMessage(65529);
                                        } else {
                                            ImportExportExtensionSettings.this.a((ArrayList<a>) arrayListG);
                                        }
                                    }
                                    break;
                                case 65533:
                                    Log.d(ImportExportExtensionSettings.f426a, "CHECK_DOFUN_EXPORT_STATE:export finish:" + ImportExportExtensionSettings.y.f29a);
                                    if (ImportExportExtensionSettings.y.f29a) {
                                        ImportExportExtensionSettings.this.aT.sendEmptyMessageDelayed(65524, 3000L);
                                    } else {
                                        ImportExportExtensionSettings.this.aT.sendEmptyMessageDelayed(65533, 3000L);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                    }
                } else {
                    ImportExportExtensionSettings.this.r = String.valueOf(message.arg1 | (message.arg2 << 8));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    });
    private boolean aV = false;

    enum AUTOMATIC_AUTO_REBOOT {
        INVALID,
        INIT,
        AUTO_REBOOT
    }

    enum AUTOMATIC_COPY_AND_UPDATE_STATE {
        INVALID,
        INIT,
        AUTO_COPY_START,
        AUTO_COPY_COMPLETE,
        AUTO_UPDATE_SYSTEM_SKIP,
        AUTO_UPDATE_SYSTEM_START,
        AUTO_UPDATE_SYSTEM_COMPLETE
    }

    enum AUTOMATIC_COPY_STATE {
        INVALID,
        INIT,
        AUTO_COPY_START,
        AUTO_COPY_COMPLETE
    }

    enum AUTOMATIC_IMPORT_STATE {
        INVALID,
        INIT,
        AUTO_IMPORT_START,
        AUTO_IMPORT_SUCCESS,
        AUTO_IMPORT_FAIL
    }

    enum AUTOMATIC_UPDATE_STATE {
        INVALID,
        INIT,
        AUTO_UPDATE_SYSTEM_SKIP,
        AUTO_UPDATE_SYSTEM_START,
        AUTO_UPDATE_SYSTEM_COMPLETE
    }

    static /* synthetic */ int n(ImportExportExtensionSettings importExportExtensionSettings) {
        int i = importExportExtensionSettings.aK;
        importExportExtensionSettings.aK = i + 1;
        return i;
    }

    public void a(Context context, int i, String str, boolean z, boolean z2) {
        this.az = context;
        this.aH = com.dofun.carsetting.b.a.a();
        this.aA = i;
        this.aB = str;
        this.aC = z;
        this.aD = z2;
        this.aK = 0;
        if (this.C != null) {
            this.C.write(265, 255);
            this.C.write(272, ViewCompat.MEASURED_STATE_MASK);
            this.C.write(267, 255);
            this.C.write(276, 255);
            this.C.write(260, 255);
        }
        if (com.dofun.carsetting.f.b.L()) {
            this.aL = com.tw.a.a.a.a.a();
        }
        if (com.dofun.carsetting.f.b.G()) {
            this.A = "/soc";
        }
        m();
    }

    public void a() {
        this.C = u.a();
        if (this.C != null) {
            this.C.addHandler(f426a, this.aT);
        }
        if (y == null) {
            y = new com.dofun.carsetting.Receiver.a(MyApp.a());
        }
        y.a(true);
        this.o = String.valueOf(Settings.System.getInt(MyApp.a().getContentResolver(), "AMPVOL", 0));
    }

    public void b() {
        if (y != null) {
            y.a(false);
        }
        if (this.C != null) {
            this.C.write(276, 3);
            this.C.close();
            this.C.removeHandler(f426a);
        }
        this.aK = 0;
    }

    public void a(final boolean[] zArr) {
        x = a(this.az, MyApp.a().getString(R.string.cfg_export_title));
        new Thread(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.11
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                boolean z;
                int i = 0;
                while (true) {
                    if (i >= ImportExportExtensionSettings.this.aH.A.length) {
                        z = false;
                        break;
                    }
                    String str = "/" + ImportExportExtensionSettings.this.aH.A[i];
                    String str2 = ImportExportExtensionSettings.this.aH.t;
                    if (new File(str).exists()) {
                        ImportExportExtensionSettings.this.a(str, str2, zArr);
                        z = true;
                        break;
                    }
                    i++;
                }
                if (!z) {
                    for (int i2 = 0; i2 < ImportExportExtensionSettings.this.aH.B.length; i2++) {
                        String str3 = "/" + ImportExportExtensionSettings.this.aH.B[i2];
                        String str4 = ImportExportExtensionSettings.this.aH.t;
                        if (new File(str3).exists()) {
                            ImportExportExtensionSettings.this.a(str3, str4, zArr);
                            z = true;
                            break;
                        }
                    }
                }
                if (z) {
                    return;
                }
                ImportExportExtensionSettings.this.aT.sendEmptyMessage(65523);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:191:0x192a A[Catch: Exception -> 0x1950, TryCatch #0 {Exception -> 0x1950, blocks: (B:4:0x0027, B:6:0x004f, B:7:0x0083, B:9:0x00a6, B:10:0x00da, B:12:0x00fd, B:13:0x0131, B:169:0x1892, B:171:0x18a4, B:172:0x18ae, B:191:0x192a, B:192:0x1938, B:194:0x193c, B:195:0x194f, B:184:0x1908, B:185:0x1916, B:187:0x191a), top: B:200:0x0027 }] */
    /* JADX WARN: Code duplicated, block: B:194:0x193c A[Catch: Exception -> 0x1950, TryCatch #0 {Exception -> 0x1950, blocks: (B:4:0x0027, B:6:0x004f, B:7:0x0083, B:9:0x00a6, B:10:0x00da, B:12:0x00fd, B:13:0x0131, B:169:0x1892, B:171:0x18a4, B:172:0x18ae, B:191:0x192a, B:192:0x1938, B:194:0x193c, B:195:0x194f, B:184:0x1908, B:185:0x1916, B:187:0x191a), top: B:200:0x0027 }] */
    /* JADX WARN: Code duplicated, block: B:217:? A[Catch: Exception -> 0x1950, SYNTHETIC, TRY_LEAVE, TryCatch #0 {Exception -> 0x1950, blocks: (B:4:0x0027, B:6:0x004f, B:7:0x0083, B:9:0x00a6, B:10:0x00da, B:12:0x00fd, B:13:0x0131, B:169:0x1892, B:171:0x18a4, B:172:0x18ae, B:191:0x192a, B:192:0x1938, B:194:0x193c, B:195:0x194f, B:184:0x1908, B:185:0x1916, B:187:0x191a), top: B:200:0x0027 }] */
    public void a(String str, String str2, boolean[] zArr) throws Throwable {
        Throwable th;
        BufferedWriter bufferedWriter;
        Exception exc;
        BufferedWriter bufferedWriter2;
        u uVar;
        Configuration configuration;
        String strFlattenToString;
        BluetoothAdapter defaultAdapter;
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tw.eq", "com.tw.eq.EQService"));
            intent.putExtra("ISExport", true);
            intent.putExtra("path", str);
            this.az.startService(intent);
        } catch (Exception unused) {
        }
        try {
            File file = new File(str + "/" + this.aH.v);
            if (!file.exists()) {
                this.C.write(40730, 3, 0, "mkdir " + file.getAbsolutePath());
                this.C.write(40730, 3, 0, "chmod 777 " + file.getAbsolutePath());
            }
            File file2 = new File(str + "/" + this.aH.w);
            if (!file2.exists()) {
                this.C.write(40730, 3, 0, "mkdir " + file2.getAbsolutePath());
                this.C.write(40730, 3, 0, "chmod 777 " + file2.getAbsolutePath());
            }
            File file3 = new File(str + "/" + this.aH.u);
            if (!file3.exists()) {
                this.C.write(40730, 3, 0, "mkdir " + file3.getAbsolutePath());
                this.C.write(40730, 3, 0, "chmod 777 " + file3.getAbsolutePath());
            }
            boolean z = zArr[0];
            boolean z2 = zArr[1];
            boolean z3 = zArr[2];
            boolean z4 = zArr[3];
            boolean z5 = zArr[4];
            try {
                y.a();
                bufferedWriter = new BufferedWriter(new FileWriter(str + "/" + str2));
                try {
                    try {
                        this.aN = new BufferedWriter(new FileWriter(str + "/backups"));
                        Log.d(f426a, "toCfg:" + str + "/" + str2 + " cRevers:" + z + " cKeymap:" + z2 + " cAPK:" + z3 + " cImage:" + z4 + " cOther:" + z5);
                        if (z) {
                            bufferedWriter.write("system_usb11=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_USB11", 0) + "\n");
                            bufferedWriter.write("system_usbhub=" + SystemProperties.get("persist.sys.usb.support_hub", "true") + "\n");
                            bufferedWriter.write("forward_looking_mirror=" + SystemProperties.get("persist.camera.mirror2", "0") + "\n");
                            if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                                bufferedWriter.write("front_view_channel=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_CHANNEL", 2) + "\n");
                            } else {
                                bufferedWriter.write("front_view_channel=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_CHANNEL", 0) + "\n");
                            }
                            bufferedWriter.write("system_line=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_LINE", 0) + "\n");
                            bufferedWriter.write("system_line2=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_LINE2", 0) + "\n");
                            bufferedWriter.write("door=" + Settings.System.getInt(MyApp.a().getContentResolver(), "DOOR", 0) + "\n");
                            bufferedWriter.write("system_door2=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_DOOR2", 0) + "\n");
                            bufferedWriter.write("system_fradar=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_FRADAR", 0) + "\n");
                            bufferedWriter.write("ahd360=" + SystemProperties.get("persist.camera.ahd360", "3") + "\n");
                            bufferedWriter.write("reversingIC=" + SystemProperties.get("persist.camera.ic", "0") + "\n");
                            bufferedWriter.write("system_iis=" + SystemProperties.get("persist.tw.iis", "1") + "\n");
                            bufferedWriter.write("spdif_out=" + SystemProperties.get("persist.spdif.out", "1") + "\n");
                            bufferedWriter.write("reverse_pattern=" + SystemProperties.get("persist.reverse.vr", "0") + "\n");
                            bufferedWriter.write("eq_settings=" + Settings.System.getInt(MyApp.a().getContentResolver(), "EQ_SETTINGS", 0) + "\n");
                            bufferedWriter.write("system_front_fradar=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_FRONT_FRADAR", 0) + "\n");
                            bufferedWriter.write("system_lrreverse=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_LRREVERSE", 0) + "\n");
                            if (com.dofun.carsetting.f.b.o()) {
                                bufferedWriter.write("system_colorTemp=" + SystemProperties.get("persist.vendor.pq.disable", "0xff") + "\n");
                                bufferedWriter.write("microphone_parameter=" + SystemProperties.get("persist.tw.microphone", "0") + "\n");
                            }
                            bufferedWriter.write("fowardview=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_SWITCH", 0) + "\n");
                            if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                                bufferedWriter.write("reverse_unused=" + this.C.write(65523, 0, 0, "car_reverse\nreverse_unused") + "\n");
                                bufferedWriter.write("reverse_type=" + this.C.write(65523, 0, 0, "car_reverse\ntvd_type") + "\n");
                                bufferedWriter.write("gamma=" + com.dofun.carsetting.f.b.b("/sys/class/disp/disp/attr/gamma") + "\n");
                                if (com.dofun.carsetting.f.b.b("sys/class/car_reverse/csiuse") < 255) {
                                    bufferedWriter.write("reverse_csi_used=" + this.C.write(65523, 0, 0, "car_reverse\ncsi_used") + "\n");
                                }
                                bufferedWriter.write("reverse_resolution=" + this.C.write(65523, 0, 0, "car_reverse\ntvd_resolution") + "\n");
                                if (com.dofun.carsetting.f.b.G()) {
                                    bufferedWriter.write("video_out=" + SystemProperties.get("persist.sys.video_out", "0") + "\n");
                                } else {
                                    bufferedWriter.write("video_out=" + this.C.write(65523, 0, 0, "disp\nvideo_out") + "\n");
                                }
                                bufferedWriter.write("twmemfake=" + this.C.write(65523, 0, 0, "car_reverse\ntwmemfake") + "\n");
                                if (com.dofun.carsetting.f.b.e() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                                    bufferedWriter.write("system_mirror=" + this.C.write(65523, 0, 0, "car_reverse\nmirror") + "\n");
                                } else {
                                    bufferedWriter.write("system_mirror=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_MIRROR", 0) + "\n");
                                }
                            } else {
                                if (com.dofun.carsetting.f.b.at()) {
                                    bufferedWriter.write("system_mirror=" + SystemProperties.get("persist.camerarear.mirror", "0") + "\n");
                                } else {
                                    bufferedWriter.write("system_mirror=" + SystemProperties.get("persist.camera.mirror", "0") + "\n");
                                }
                                bufferedWriter.write("system_mirror_vertical=" + SystemProperties.get("persist.camera.angle", "90") + "\n");
                            }
                            if (com.dofun.carsetting.f.b.L() && this.aL != null) {
                                boolean zA = this.aL.a(this.az, 0);
                                StringBuilder sb = new StringBuilder();
                                sb.append("sim_1_enable=");
                                sb.append(zA ? "1" : "0");
                                sb.append("\n");
                                bufferedWriter.write(sb.toString());
                            }
                            if (com.dofun.carsetting.f.b.L() && this.aL != null) {
                                boolean zA2 = this.aL.a(this.az, 1);
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("sim_2_enable=");
                                sb2.append(zA2 ? "1" : "0");
                                sb2.append("\n");
                                bufferedWriter.write(sb2.toString());
                            }
                            bufferedWriter.write("hicar_screen_size=" + Settings.System.getString(MyApp.a().getContentResolver(), "hicar_screen_size") + "\n");
                            bufferedWriter.write("zlink_hicar_hi=" + SystemProperties.get("persist.zlink.disable.features", "") + "\n");
                        }
                        if (z5) {
                            bufferedWriter.write("amplifier_control=" + this.u + "\n");
                            bufferedWriter.write("system_floatvideo=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_FLOATVIDEO", 0) + "\n");
                            bufferedWriter.write("system_ta=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_TA", 0) + "\n");
                            int i = Settings.System.getInt(MyApp.a().getContentResolver(), "LEFT_RIGHT_SEAT", 0);
                            bufferedWriter.write("left_right_seat=" + i + "\n");
                            bufferedWriter.write("left_right_seat_real=" + Settings.System.getInt(MyApp.a().getContentResolver(), "LEFT_RIGHT_SEAT_REAL", i) + "\n");
                            bufferedWriter.write("tvd0=" + SystemProperties.get("persist.camera.mode0", "2") + "\n");
                            bufferedWriter.write("tvd1=" + SystemProperties.get("persist.camera.mode1", "2") + "\n");
                            bufferedWriter.write("tvd2=" + SystemProperties.get("persist.camera.mode2", "2") + "\n");
                            bufferedWriter.write("tvd3=" + SystemProperties.get("persist.camera.mode3", "2") + "\n");
                            bufferedWriter.write("temp_mode=" + Settings.System.getInt(MyApp.a().getContentResolver(), "TEMP_MODE", 0) + "\n");
                            bufferedWriter.write("tw_no_streammedia=" + Settings.System.getInt(MyApp.a().getContentResolver(), "ISSHOW_STREAM_MEDIA_APK", 0) + "\n");
                            bufferedWriter.write("tw_tpms=" + Settings.System.getInt(MyApp.a().getContentResolver(), "ISSHOW_TPMS", 1) + "\n");
                            bufferedWriter.write("tw_wheel=" + Settings.System.getInt(MyApp.a().getContentResolver(), "ISSHOW_WHEEL", 1) + "\n");
                            bufferedWriter.write("tw_pip=" + SystemProperties.get("persist.tw.forcepip", "0") + "\n");
                            bufferedWriter.write("cid=" + SystemProperties.get("persist.tw.cid", "0") + "\n");
                            bufferedWriter.write("ap_5g_channel=" + SystemProperties.get("persist.5g.channel", "36") + "\n");
                            bufferedWriter.write("carsetting_wallpaper=" + Settings.System.getString(MyApp.a().getContentResolver(), "MAIN_BG_CHOOSE") + "\n");
                            bufferedWriter.write("air_popup_window=" + Settings.System.getInt(MyApp.a().getContentResolver(), "AIR_POPUP_WINDOW", 1) + "\n");
                            bufferedWriter.write("air_style=" + Settings.System.getInt(MyApp.a().getContentResolver(), "air.style.scene", -1) + "\n");
                            bufferedWriter.write("screen_casting_code_rate=" + SystemProperties.get("persist.suding.reverse.video.level", "8") + "\n");
                            bufferedWriter.write("trusted_module=" + SystemProperties.getBoolean("persist.tw.verify", true) + "\n");
                            bufferedWriter.write("boot_360=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_360", 1) + "\n");
                            bufferedWriter.write("float_360=" + Settings.System.getInt(MyApp.a().getContentResolver(), "FLOAT_360", 0) + "\n");
                            bufferedWriter.write("setting_floating=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SETTING_FLOATING", 0) + "\n");
                            bufferedWriter.write("verical_left_right=" + Settings.System.getInt(MyApp.a().getContentResolver(), "VERICAL_LEFT_RIGHT", 0) + "\n");
                            bufferedWriter.write("car_type=" + Settings.System.getInt(MyApp.a().getContentResolver(), "CAR_TYPE", 0) + "\n");
                            bufferedWriter.write("car_type2=" + SystemProperties.get("persist.sys.tw.car_type", "0") + "\n");
                            bufferedWriter.write("tw_install_apk=" + SystemProperties.getInt("persist.sys.tw.apkinstall_hide", 1) + "\n");
                            bufferedWriter.write("brightness=" + Settings.System.getInt(MyApp.a().getContentResolver(), "color_brightness", 50) + "\n");
                            bufferedWriter.write("contrast=" + Settings.System.getInt(MyApp.a().getContentResolver(), "color_contrast", 50) + "\n");
                            bufferedWriter.write("saturation=" + Settings.System.getInt(MyApp.a().getContentResolver(), "color_saturation", 50) + "\n");
                            bufferedWriter.write("colortemp=" + Settings.System.getInt(MyApp.a().getContentResolver(), "color_temp", 0) + "\n");
                            bufferedWriter.write("camera_reverse=" + SystemProperties.getInt("persist.camera.reverse", 1) + "\n");
                            bufferedWriter.write("USER_LOCK=" + Settings.System.getString(MyApp.a().getContentResolver(), "USER_LOCK") + "\n");
                            bufferedWriter.write("FACTORY_LOCK=" + Settings.System.getString(MyApp.a().getContentResolver(), "FACTORY_LOCK") + "\n");
                            bufferedWriter.write("RESET_FACTORY=" + Settings.System.getString(MyApp.a().getContentResolver(), "RESET_FACTORY") + "\n");
                            bufferedWriter.write("PLATFORMINFO=" + Settings.System.getString(MyApp.a().getContentResolver(), "PLATFORMINFO") + "\n");
                            bufferedWriter.write("CPU_LOCK=" + Settings.System.getString(MyApp.a().getContentResolver(), "CPU_LOCK") + "\n");
                            bufferedWriter.write("BOOT_LOCK=" + Settings.System.getString(MyApp.a().getContentResolver(), "BOOT_LOCK") + "\n");
                            bufferedWriter.write("SYSTEM_LOCK=" + Settings.System.getString(MyApp.a().getContentResolver(), "SYSTEM_LOCK") + "\n");
                            bufferedWriter.write("APP_VERSION_LOCK=" + Settings.System.getString(MyApp.a().getContentResolver(), "APP_VERSION_LOCK") + "\n");
                            bufferedWriter.write("MCU_LOCK=" + Settings.System.getString(MyApp.a().getContentResolver(), "MCU_LOCK") + "\n");
                            bufferedWriter.write("front_camera_index=" + Settings.System.getInt(MyApp.a().getContentResolver(), "FRONT_CAMERA_INDEX", 0) + "\n");
                            bufferedWriter.write("system_app2=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_APP2", 0) + "\n");
                            bufferedWriter.write("lcd_clk=" + SystemProperties.get("persist.panel.lcdclk", String.valueOf(t())) + "\n");
                            bufferedWriter.write("SHOW_VOLTAGE=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SHOW_VOLTAGE", 0) + "\n");
                            bufferedWriter.write("storage_info_switch=" + SystemProperties.getInt("persist.tw.storage.switch", 1) + "\n");
                            bufferedWriter.write("tw_file=" + Settings.System.getInt(MyApp.a().getContentResolver(), "ISSHOW_FILE", 1) + "\n");
                            bufferedWriter.write("tw_chrome=" + Settings.System.getInt(MyApp.a().getContentResolver(), "ISSHOW_CHROME", 1) + "\n");
                            bufferedWriter.write("tw_google=" + Settings.System.getInt(MyApp.a().getContentResolver(), "ISSHOW_GOOGLE", 1) + "\n");
                            bufferedWriter.write("remote_monitoring=" + SystemProperties.getInt("persist.tw.sleepmode", 1) + "\n");
                            if (com.dofun.carsetting.f.b.F()) {
                                bufferedWriter.write("performance_mode=" + com.dofun.carsetting.f.b.aJ() + "\n");
                            }
                            if (SystemProperties.getInt("persist.tw.bt.module", 2) == 0 && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
                                String name = defaultAdapter.getName();
                                if (!TextUtils.isEmpty(name)) {
                                    bufferedWriter.write("build_in_bt_name=" + name + "\n");
                                }
                            }
                            bufferedWriter.write("nav_bar_position=" + Settings.System.getInt(MyApp.a().getContentResolver(), "SETTING_NAVBAR", -1) + "\n");
                            if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                                bufferedWriter.write("screen_rotation=" + SystemProperties.get("persist.sf.forcerotation", "2") + "\n");
                            }
                            bufferedWriter.write("radio_region=" + this.e + "\n");
                            bufferedWriter.write("brake_mode=" + this.f + "\n");
                            bufferedWriter.write("tv_mode=" + this.g + "\n");
                            bufferedWriter.write("tv_norm=" + this.h + "\n");
                            bufferedWriter.write("panel_light_color=" + this.i + "\n");
                            bufferedWriter.write("panel_light=" + this.j + "\n");
                            bufferedWriter.write("knobs1=" + this.k + "\n");
                            bufferedWriter.write("knobs2=" + this.l + "\n");
                            bufferedWriter.write("x_knobs=" + this.m + "\n");
                            bufferedWriter.write("ant_pwr=" + this.n + "\n");
                            bufferedWriter.write("ampvol=" + this.o + "\n");
                            bufferedWriter.write("swap_last_next=" + this.p + "\n");
                            bufferedWriter.write("dsp_mode=" + this.q + "\n");
                            bufferedWriter.write("key_tone=" + this.s + "\n");
                            bufferedWriter.write("Sleep=" + this.t + "\n");
                            bufferedWriter.write("shutdown_monitor=" + this.v + "\n");
                            bufferedWriter.write("low_voltage_protection=" + this.w + "\n");
                            try {
                                if (Integer.valueOf(this.r).intValue() > 0) {
                                    bufferedWriter.write("back_light_v=" + this.r + "\n");
                                }
                            } catch (Exception e) {
                                Log.d(f426a, "toCfg:BACK_LIGHT_V:" + e.getMessage());
                            }
                            bufferedWriter.write("resolution=" + this.C.write(65283, 0, 0, "disp\ndisp_mode2") + "\n");
                            bufferedWriter.write("tw_cardoor=.ResourcePicture\n");
                            a("tw_cardoor", str + "/.ResourcePicture");
                            bufferedWriter.write("logo360=userlogo\n");
                            a("logo360", str + "/userlogo");
                            if (new File("sdcard/.brandlogo.png").exists()) {
                                bufferedWriter.write("brandlogo=.brandlogo.png\n");
                                a("brandlogo", str + "/.brandlogo.png");
                            }
                            this.C.write(40730, 1, 0, "chmod 777 twdataconfig/NaviBar-theme");
                            this.C.write(40730, 1, 0, "cp -rf /twdataconfig/NaviBar-theme" + str);
                            if (new File("data/tw/carlogo.png").exists()) {
                                bufferedWriter.write("carlogo=carlogo.png\n");
                                a("carlogo", str + "/carlogo.png");
                            }
                            if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                                bufferedWriter.write("screen_rotation_file_tag=/\n");
                                a("screen_rotation_file_tag", str + "/");
                            }
                            if (com.dofun.carsetting.f.b.a().contains("BBA")) {
                                bufferedWriter.write("launcher_type=" + SystemProperties.get("persist.tw.launcherType", "0") + "\n");
                                bufferedWriter.write("bba_carMode=" + SystemProperties.get("persist.tw.launcherType.carMode", "0") + "\n");
                            }
                            if (com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.s()) {
                                bufferedWriter.write("echo_delay_param=" + SystemProperties.get("persist.btaec.delay", "0") + "\n");
                            }
                            if (com.dofun.carsetting.f.b.a().contains("XHKJ1")) {
                                bufferedWriter.write("xhkj_launcher_type=" + SystemProperties.get("persist.tw.launcher.theme.type", "0") + "\n");
                            }
                            if (com.dofun.carsetting.f.b.aZ()) {
                                bufferedWriter.write("disable_hw_overlays=" + SystemProperties.get("persist.tw.disable.hwc", "0") + "\n");
                            }
                            if (com.dofun.carsetting.f.b.a().contains("BFD32") || com.dofun.carsetting.f.b.a().contains("KZS34")) {
                                bufferedWriter.write("screen_size=" + SystemProperties.get("persist.sys.screen.size", "0") + "\n");
                            }
                            if (com.dofun.carsetting.f.b.G()) {
                                bufferedWriter.write("persist_mic_front=" + SystemProperties.get("persist.mic.front", "0") + "\n");
                                bufferedWriter.write("i2stospdif=" + SystemProperties.getBoolean("persist.tw.i2stospdif", false) + "\n");
                            }
                            if (this.aH.ad) {
                                bufferedWriter.write("mic_select=" + SystemProperties.get("persist.tw.mic.select", "-1") + "\n");
                            }
                        }
                        if (z2) {
                            bufferedWriter.write("key_touch=touch\n");
                            a("key_touch", str + "/touch");
                            bufferedWriter.write("touch_import=touch.cfg\n");
                            bufferedWriter.write("key_panel=key_panel.0.tw\n");
                            a("key_panel", str + "/key_panel.0.tw");
                            bufferedWriter.write("key_swc=key_swc.0.tw\n");
                            a("key_swc", str + "/key_swc.0.tw");
                        }
                        if (z3) {
                            bufferedWriter.write("apk_update=apk\n");
                            a("apk_update", str + "/apk");
                            bufferedWriter.write("apk_install=apk_install\n");
                            a("apk_install", str + "/apk_install");
                        }
                        if (z4) {
                            bufferedWriter.write("logo_settings=bootlogo.bmp\n");
                            a("logo_settings", str);
                            if (new File("/data/local/bootanimation.zip").exists()) {
                                bufferedWriter.write("bootanimation=bootanimation.zip\n");
                                a("bootanimation", str + "/bootanimation.zip");
                            } else if (new File("/data/local/boot.mp4").exists()) {
                                bufferedWriter.write("bootanimation=boot.mp4\n");
                                a("bootanimation", str + "/boot.mp4");
                            } else {
                                bufferedWriter.write("bootanimation=android\n");
                            }
                            a("wallpaper", str + "/wallpaper.png");
                            if (new File(str + "/wallpaper.png").exists()) {
                                bufferedWriter.write("wallpaper=wallpaper.png\n");
                            } else {
                                try {
                                    strFlattenToString = WallpaperManager.getInstance(MyApp.a()).getWallpaperInfo().getComponent().flattenToString();
                                } catch (Exception unused2) {
                                    strFlattenToString = null;
                                }
                                if (strFlattenToString != null) {
                                    bufferedWriter.write("wallpaper=" + strFlattenToString + "\n");
                                }
                            }
                        }
                        if (z5) {
                            a("bt_ini", str + "/BtSettings.INI");
                            bufferedWriter.write("bt_ini=BtSettings.INI\n");
                            a("bt_ini", str + "/bt_conf.ini");
                            bufferedWriter.write("bt_ini=bt_conf.ini\n");
                            bufferedWriter.write("system_cfg=" + this.aH.t + "\n");
                            bufferedWriter.write("file_copy=" + this.aH.u + "\n");
                            bufferedWriter.write("sleepKillPackage_cfg=sleepKillPackage.cfg\n");
                            a("sleepKillPackage_cfg", str + "/sleepKillPackage.cfg");
                            bufferedWriter.write("config_resume_auto_play=config_resume_auto_play.xml\n");
                            a("config_resume_auto_play", str + "/config_resume_auto_play.xml");
                            bufferedWriter.write("navi_name=navi_name\n");
                            a("navi_name", str + "/navi_name");
                            bufferedWriter.write("speech_name=speech_name\n");
                            a("speech_name", str + "/speech_name");
                            bufferedWriter.write("app_autostart_name=app_autostart_name\n");
                            a("app_autostart_name", str + "/app_autostart_name");
                            bufferedWriter.write("config=config\n");
                            a("config", str + "/config");
                            try {
                                bufferedWriter.write("timezone=" + Calendar.getInstance().getTimeZone().getID() + "\n");
                            } catch (Exception unused3) {
                            }
                            try {
                                configuration = ActivityManagerNative.getDefault().getConfiguration();
                            } catch (Exception unused4) {
                                configuration = null;
                            }
                            if (configuration != null) {
                                bufferedWriter.write("fontScale=" + configuration.fontScale + "\n");
                                bufferedWriter.write("locale=" + configuration.locale.toLanguageTag() + "\n");
                            }
                            bufferedWriter.write("hardware_info=" + Settings.System.getInt(this.az.getContentResolver(), "hardware_info_visibility", 1) + "\n");
                            bufferedWriter.write("resolution_info_visibility=" + Settings.System.getInt(this.az.getContentResolver(), "resolution_info_visibility", 1) + "\n");
                            bufferedWriter.write("magic_2313=" + SystemProperties.get("persist.sys.eqtype.2313", "pt2313") + "\n");
                            bufferedWriter.write("magic_3702=" + SystemProperties.get("persist.sys.eqtype.3702", "asp3702") + "\n");
                            bufferedWriter.write("tw360_tvi=" + SystemProperties.get("persist.tw360.tvi", "0") + "\n");
                            bufferedWriter.write("tw_nfc=" + com.dofun.carsetting.f.b.b("/sys/class/car_reverse/twnfc") + "\n");
                        }
                        bufferedWriter.write("password=password_factory.cfg\n");
                        a("password", str + "/password_factory.cfg");
                        bufferedWriter.write("versionSettings=.VersionSettings\n");
                        a("versionSettings", str + "/.VersionSettings");
                        bufferedWriter.write("twdataconfig=tw_config.bin\n");
                        a("twdataconfig", str + "/tw_config.bin ");
                        bufferedWriter.write("lcd_enhance=lcd_enhance.bin\n");
                        a("lcd_enhance", str + "/lcd_enhance.bin ");
                        bufferedWriter.write("ph_theme_type=ph\n");
                        a("ph_theme_type", str + "/ph");
                        bufferedWriter.write("android_boot_version=" + this.C.write(65523, 0, 0, "mcu_gpio\nversion") + "\n");
                        bufferedWriter.write("backups=backups\n");
                        bufferedWriter.flush();
                        this.aN.flush();
                        this.aT.sendEmptyMessage(65533);
                        bufferedWriter.close();
                        this.C.write(40730, 1, 0, "sync");
                        if (this.aN != null) {
                            this.aN.close();
                            this.aN = null;
                            uVar = this.C;
                            uVar.write(40730, 1, 0, "sync");
                        }
                    } catch (Exception e2) {
                        exc = e2;
                        bufferedWriter2 = bufferedWriter;
                        try {
                            new File(str + "/" + str2).delete();
                            Log.d(f426a, "toFile:apk_backup:" + exc.getMessage());
                            this.aT.sendEmptyMessage(65523);
                            if (bufferedWriter2 != null) {
                                bufferedWriter2.close();
                                this.C.write(40730, 1, 0, "sync");
                            }
                            if (this.aN == null) {
                                return;
                            }
                            this.aN.close();
                            this.aN = null;
                            uVar = this.C;
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedWriter = bufferedWriter2;
                            if (bufferedWriter != null) {
                                bufferedWriter.close();
                                this.C.write(40730, 1, 0, "sync");
                            }
                            if (this.aN != null) {
                                throw th;
                            }
                            this.aN.close();
                            this.aN = null;
                            this.C.write(40730, 1, 0, "sync");
                            throw th;
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                        this.C.write(40730, 1, 0, "sync");
                    }
                    if (this.aN != null) {
                        throw th;
                    }
                    this.aN.close();
                    this.aN = null;
                    this.C.write(40730, 1, 0, "sync");
                    throw th;
                }
            } catch (Exception e3) {
                exc = e3;
                bufferedWriter2 = null;
            } catch (Throwable th4) {
                th = th4;
                bufferedWriter = null;
            }
        } catch (Exception unused5) {
        }
    }

    public void a(String str, final String str2) throws Throwable {
        byte b2;
        FileOutputStream fileOutputStream;
        if (this.aN != null) {
            try {
                String[] strArrSplit = str2.split("/");
                this.aN.write(str + "=" + strArrSplit[strArrSplit.length - 1] + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        switch (str) {
            case "config_resume_auto_play":
                b2 = 12;
                break;
            case "key_panel":
                b2 = 5;
                break;
            case "key_touch":
                b2 = 3;
                break;
            case "bt_ini":
                b2 = 9;
                break;
            case "config":
                b2 = 4;
                break;
            case "key_swc":
                b2 = 6;
                break;
            case "brandlogo":
                b2 = 1;
                break;
            case "ph_theme_type":
                b2 = 23;
                break;
            case "app_autostart_name":
                b2 = 26;
                break;
            case "logo360":
                b2 = 0;
                break;
            case "twdataconfig":
                b2 = 16;
                break;
            case "screen_rotation_file_tag":
                b2 = 25;
                break;
            case "carlogo":
                b2 = 24;
                break;
            case "logo_settings":
                b2 = 2;
                break;
            case "bootanimation":
                b2 = 7;
                break;
            case "navi_name":
                b2 = 21;
                break;
            case "versionSettings":
                b2 = 15;
                break;
            case "apk_install":
                b2 = 13;
                break;
            case "apk_update":
                b2 = 10;
                break;
            case "password":
                b2 = 14;
                break;
            case "tw_cardoor":
                b2 = 20;
                break;
            case "sleepKillPackage_cfg":
                b2 = 11;
                break;
            case "tw_theme":
                b2 = 19;
                break;
            case "wallpaper":
                b2 = 8;
                break;
            case "lcd_enhance":
                b2 = 17;
                break;
            case "speech_name":
                b2 = 22;
                break;
            case "android_boot_version":
                b2 = 18;
                break;
            default:
                b2 = -1;
                break;
        }
        try {
            switch (b2) {
                case 0:
                    this.C.write(40730, 3, 0, "cp -rf /mnt/sdcard/.Baios360/.cache " + str2);
                    return;
                case 1:
                    this.C.write(40730, 3, 0, "cp -rf /sdcard/.brandlogo.png " + str2);
                    return;
                case 2:
                    if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F()) {
                        this.C.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/bootloader /bootloader");
                        this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.12
                            @Override // java.lang.Runnable
                            public void run() {
                                ImportExportExtensionSettings.this.C.write(40730, 3, 0, "cp /bootloader/bootlogo.bmp " + str2);
                            }
                        }, 300L);
                        return;
                    }
                    if (com.dofun.carsetting.f.b.H()) {
                        this.C.write(40730, 3, 0, "cp /data/tw/bootlogo.bmp " + str2);
                        this.C.write(40730, 3, 0, "cp /data/tw/bootlogo.elf " + str2);
                        return;
                    }
                    if (com.dofun.carsetting.f.b.G()) {
                        this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.13
                            @Override // java.lang.Runnable
                            public void run() {
                                ImportExportExtensionSettings.this.C.write(40730, 3, 0, "cp /twdataconfig/bootlogo.bmp " + str2);
                            }
                        }, 300L);
                        return;
                    }
                    this.C.write(40730, 3, 0, "cp /data/tw/bootlogo.bmp " + str2);
                    return;
                case 3:
                    this.C.write(40730, 3, 0, "cp /data/tw/touch " + str2);
                    return;
                case 4:
                    this.C.write(40730, 3, 0, "cp /data/tw/config " + str2);
                    return;
                case 5:
                    this.C.write(264, 129);
                    return;
                case 6:
                    this.C.write(259, 129);
                    return;
                case 7:
                    if (str2.endsWith(".mp4")) {
                        this.C.write(40730, 3, 0, "cp /data/local/boot.mp4 " + str2);
                        return;
                    }
                    this.C.write(40730, 3, 0, "cp /data/local/bootanimation.zip " + str2);
                    return;
                case 8:
                    this.C.write(40730, 3, 0, "rm -r " + str2);
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(MyApp.a());
                    if (wallpaperManager.getWallpaperInfo() == null) {
                        try {
                            fileOutputStream = new FileOutputStream(str2);
                            try {
                                wallpaperManager.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                            } catch (Exception unused) {
                                if (fileOutputStream == null) {
                                    return;
                                }
                            } catch (Throwable th) {
                                th = th;
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                throw th;
                            }
                            break;
                        } catch (Exception unused2) {
                            fileOutputStream = null;
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream = null;
                        }
                        fileOutputStream.close();
                        return;
                    }
                    return;
                case 9:
                    if (str2.endsWith("tSettings.INI")) {
                        this.C.write(40730, 3, 0, "cp /data/bluesoleil/BtSettings.INI " + str2);
                    }
                    if (str2.endsWith("bt_conf.ini")) {
                        this.C.write(40730, 3, 0, "cp /data/goc/bt_conf.ini " + str2);
                        return;
                    }
                    return;
                case 10:
                    this.C.write(40730, 3, 0, "rm -r " + str2);
                    if (new File("/sdcard/.apk").exists()) {
                        this.C.write(40730, 3, 0, "cp -r /sdcard/.apk " + str2);
                        return;
                    }
                    return;
                case 11:
                    this.C.write(40730, 3, 0, "cp /sdcard/.Tcfg/sleepKillPackage.cfg " + str2);
                    this.C.write(40730, 3, 0, "cp /twdataconfig/cfg/sleepKillPackage.cfg " + str2);
                    return;
                case 12:
                    if (new File("/twdataconfig/cfg/config_resume_auto_play.xml").canRead()) {
                        this.C.write(40730, 3, 0, "cp /twdataconfig/cfg/config_resume_auto_play.xml " + str2);
                        return;
                    }
                    this.C.write(40730, 3, 0, "cp /data/tw/config_resume_auto_play.xml " + str2);
                    return;
                case 13:
                    try {
                        this.C.write(40730, 2, 0, "rm -r " + str2);
                        a(str2);
                        return;
                    } catch (Exception e2) {
                        Log.d(f426a, "toFile:apk_backup:" + e2.getMessage());
                        return;
                    }
                case 14:
                    this.C.write(40730, 3, 0, "cp /sdcard/.Tcfg/password.cfg " + str2);
                    this.C.write(40730, 3, 0, "cp /twdataconfig/cfg/password.cfg " + str2);
                    return;
                case 15:
                    if (new File("/twdataconfig/.VersionSettings").exists()) {
                        this.C.write(40730, 3, 0, "cp /twdataconfig/.VersionSettings " + str2);
                        return;
                    }
                    if (new File("/sdcard/.VersionSettings").exists()) {
                        this.C.write(40730, 3, 0, "cp /sdcard/.VersionSettings " + str2);
                        return;
                    }
                    return;
                case 16:
                    if (new File("/twdataconfig/tw_config.bin").exists()) {
                        this.C.write(40730, 3, 0, "cp /twdataconfig/tw_config.bin " + str2);
                        return;
                    }
                    return;
                case 17:
                    if (new File("/twdataconfig/lcd_enhance.bin").exists()) {
                        this.C.write(40730, 3, 0, "cp /twdataconfig/lcd_enhance.bin " + str2);
                        return;
                    }
                    return;
                case 18:
                    Integer.parseInt(str2);
                    return;
                case 19:
                    if (new File("/data/tw/theme").exists()) {
                        if (!new File(str2 + "/").exists()) {
                            this.C.write(40730, 3, 0, "mkdir " + str2);
                        }
                        this.C.write(40730, 3, 0, "cp -rf /data/tw/theme/* " + str2 + "/");
                        this.C.write(40730, 3, 0, "sync");
                        return;
                    }
                    return;
                case 20:
                    Log.d(f426a, "toFile:TW_CARDOOR:" + str2);
                    if (new File("/mnt/sdcard/.ResourcePicture").exists()) {
                        if (!new File(str2 + "/").exists()) {
                            this.C.write(40730, 3, 0, "mkdir " + str2);
                        }
                        this.C.write(40730, 3, 0, "cp -rf /mnt/sdcard/.ResourcePicture/* " + str2 + "/");
                        this.C.write(40730, 3, 0, "sync");
                        return;
                    }
                    return;
                case 21:
                    this.C.write(40730, 3, 0, "cp /data/tw/navi_name " + str2);
                    return;
                case 22:
                    this.C.write(40730, 3, 0, "cp /data/tw/speech_name " + str2);
                    return;
                case 23:
                    if (new File("/system/etc/ph").exists()) {
                        this.C.write(40730, 3, 0, "cp -rf /system/etc/ph " + str2);
                        this.C.write(40730, 3, 0, "sync");
                        return;
                    }
                    return;
                case 24:
                    this.C.write(40730, 3, 0, "cp -rf /data/tw/carlogo.png " + str2);
                    return;
                case 25:
                    this.C.write(40730, 3, 0, "cp -rf /twdataconfig/screen_rotaion_* " + str2);
                    return;
                case 26:
                    this.C.write(40730, 3, 0, "cp /data/tw/app_autostart_name " + str2);
                    return;
                default:
                    return;
            }
        } catch (Exception unused3) {
        }
    }

    public void a(String str) {
        Log.d(f426a, "backupApk:" + str);
        this.C.write(40730, 2, 0, "mkdir " + str);
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        a("/data/app", arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            f(arrayList.get(i).getAbsolutePath(), str);
        }
    }

    private void a(String str, List<File> list) {
        File[] fileArrListFiles = new File(str).listFiles(new FileFilter() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.14
            @Override // java.io.FileFilter
            public boolean accept(File file) {
                if (file.isHidden() || file.getName().startsWith("vmd")) {
                    return false;
                }
                return file.isDirectory() || file.getName().endsWith("base.apk");
            }
        });
        for (int i = 0; i < fileArrListFiles.length; i++) {
            if (fileArrListFiles[i].isDirectory()) {
                a(fileArrListFiles[i].getAbsolutePath(), list);
            } else {
                list.add(fileArrListFiles[i]);
            }
        }
    }

    private void f(String str, String str2) {
        File file = new File(new File(str).getParent());
        File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.15
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return !file2.isHidden() && file2.isFile() && file2.getName().endsWith(".apk");
            }
        });
        if (fileArrListFiles == null || fileArrListFiles.length != 1) {
            return;
        }
        Log.d(f426a, "checkAppNeedBackup:path:" + fileArrListFiles[0].getAbsolutePath());
        this.C.write(40730, 2, 0, "cp " + fileArrListFiles[0].getAbsolutePath() + " " + str2 + "/" + file.getName() + ".apk");
    }

    public void a(String str, boolean z) {
        this.aM = z;
        if (z) {
            this.aP = Settings.System.getString(MyApp.a().getContentResolver(), "RestoreFactoryImportKey");
            this.aQ = Settings.System.getInt(MyApp.a().getContentResolver(), "RestoreFactoryImportState", -1);
        }
        b(str);
    }

    public void b(final String str) {
        if (B) {
            Log.d(f426a, "step:import comfig: usbDisck path:" + str + " ->cmd:" + this.aA);
        }
        this.z = AUTOMATIC_IMPORT_STATE.AUTO_IMPORT_START.ordinal();
        if (this.aM) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "RestoreFactoryImportState", 0);
        }
        y.b();
        x = a(this.az, MyApp.a().getString(R.string.cfg_import_title));
        Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.cfg_import_title) + "......", 1).show();
        new Thread(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.16
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                boolean z;
                int i = 0;
                if (TextUtils.isEmpty(str)) {
                    String str2 = "";
                    String str3 = "";
                    int i2 = 0;
                    while (true) {
                        if (i2 >= ImportExportExtensionSettings.this.aH.A.length) {
                            z = false;
                            break;
                        }
                        str3 = "/" + ImportExportExtensionSettings.this.aH.A[i2];
                        str2 = ImportExportExtensionSettings.this.aH.t;
                        if (new File(str3 + "/" + str2).exists()) {
                            ImportExportExtensionSettings.this.b(str3, str2);
                            z = true;
                            break;
                        }
                        i2++;
                    }
                    if (!z) {
                        while (i < ImportExportExtensionSettings.this.aH.B.length) {
                            str3 = "/" + ImportExportExtensionSettings.this.aH.B[i];
                            str2 = ImportExportExtensionSettings.this.aH.t;
                            if (new File(str3 + "/" + str2).exists()) {
                                ImportExportExtensionSettings.this.b(str3, str2);
                                z = true;
                                break;
                            }
                            i++;
                        }
                    }
                    Log.d(ImportExportExtensionSettings.f426a, "cfg_import not special: path:" + str3 + " name:" + str2);
                    if (z) {
                        return;
                    }
                    ImportExportExtensionSettings.this.aT.sendEmptyMessage(65521);
                    return;
                }
                String str4 = str;
                String str5 = ImportExportExtensionSettings.this.aH.t;
                Log.d(ImportExportExtensionSettings.f426a, "cfg_import have special: path:" + str4 + " name:" + str5);
                if (new File(str4 + "/" + str5).exists()) {
                    ImportExportExtensionSettings.this.b(str4, str5);
                    i = 1;
                }
                if (i == 0) {
                    ImportExportExtensionSettings.this.aT.sendEmptyMessage(65521);
                }
            }
        }).start();
    }

    /* JADX WARN: Code duplicated, block: B:60:0x021e  */
    /* JADX WARN: Code duplicated, block: B:95:0x0118 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:99:0x018e A[EDGE_INSN: B:99:0x018e->B:54:0x018e BREAK  A[LOOP:1: B:84:0x0112->B:110:0x0112], SYNTHETIC] */
    public void b(String str, String str2) throws Throwable {
        BufferedReader bufferedReader;
        boolean z;
        String line;
        int i;
        String[] strArrSplit;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    if (!this.aM) {
                        this.C.write(40730, 3, 0, "touch " + this.aB + "/" + this.aH.y);
                        this.C.write(40730, 3, 0, "sync");
                        this.C.write(40730, 3, 0, "rm -r /sdcard/.Tcfg/");
                        this.C.write(40730, 3, 0, "mkdir /sdcard/.Tcfg/");
                        this.C.write(40730, 3, 0, "rm -r /twdataconfig/cfg/");
                        this.C.write(40730, 3, 0, "mkdir /twdataconfig/cfg/");
                        this.C.write(40730, 3, 0, "chmod 666 /twdataconfig/cfg/");
                        try {
                            try {
                                if (new File(str + "/backups").exists()) {
                                    this.aO = new BufferedReader(new FileReader(str + "/backups"));
                                    while (true) {
                                        String line2 = this.aO.readLine();
                                        if (line2 == null) {
                                            break;
                                        }
                                        String[] strArrSplit2 = line2.split("=");
                                        this.C.write(40730, 3, 0, "cp -r " + str + "/" + strArrSplit2[1] + " /sdcard/.Tcfg/");
                                    }
                                }
                                if (this.aO != null) {
                                    this.aO.close();
                                    this.aO = null;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (this.aO != null) {
                                    this.aO.close();
                                }
                                bufferedReader = new BufferedReader(new FileReader(str + "/" + str2));
                                z = false;
                                while (true) {
                                    try {
                                        try {
                                            line = bufferedReader.readLine();
                                            if (line != null) {
                                                break;
                                            }
                                            try {
                                                strArrSplit = line.split("=");
                                                Settings.System.putString(MyApp.a().getContentResolver(), "RestoreFactoryImportKey", strArrSplit[0]);
                                                if (z) {
                                                }
                                                if (c(strArrSplit[0], strArrSplit[1])) {
                                                }
                                            } catch (Exception unused) {
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            if (bufferedReader != null) {
                                                bufferedReader.close();
                                            }
                                            throw th;
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        bufferedReader2 = bufferedReader;
                                        Log.e(f426a, Log.getStackTraceString(e));
                                        if (bufferedReader2 != null) {
                                            bufferedReader2.close();
                                            return;
                                        }
                                        return;
                                    }
                                }
                                Settings.System.putString(MyApp.a().getContentResolver(), "RestoreFactoryImportKey", "");
                                this.C.write(40730, 3, 0, "chmod -R 666 /twdataconfig/cfg/");
                                g();
                                c();
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.tw.eq", "com.tw.eq.EQService"));
                                intent.putExtra("ISImport", true);
                                intent.putExtra("path", str);
                                this.az.startService(intent);
                                this.C.write(40730, 1, 0, "cp -rf " + str + "/NaviBar-theme  /twdataconfig/");
                                this.C.write(40730, 1, 0, "chmod 777 twdataconfig/NaviBar-theme");
                                this.C.write(40730, 1, 0, "chmod 777 /twdataconfig/NaviBar-theme/SystemUITheme.apk");
                                this.C.write(40730, 1, 0, "chmod 777 /twdataconfig/NaviBar-theme/SystemUINavibar.apk");
                                this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.17
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        ImportExportExtensionSettings.this.C.write(40730, 1, 0, "/system/bin/pm install -r /twdataconfig/NaviBar-theme/SystemUITheme.apk");
                                        ImportExportExtensionSettings.this.C.write(40730, 1, 0, "/system/bin/pm install -r /twdataconfig/NaviBar-theme/SystemUINavibar.apk");
                                    }
                                }, 1500L);
                                this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.18
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        ImportExportExtensionSettings.this.C.write(40730, 3, 0, "cmd overlay enable-exclusive com.tw.systemui.theme_yellow");
                                    }
                                }, 3000L);
                                i = PathInterpolatorCompat.MAX_NUM_POINTS;
                                if (y.b) {
                                    i = 10000;
                                } else {
                                    i = 10000;
                                }
                                long j = i;
                                Thread.sleep(j);
                                this.aT.sendEmptyMessageDelayed(65522, j);
                                bufferedReader.close();
                            }
                        } catch (Throwable th2) {
                            if (this.aO != null) {
                                this.aO.close();
                                this.aO = null;
                            }
                            throw th2;
                        }
                    }
                    bufferedReader = new BufferedReader(new FileReader(str + "/" + str2));
                    z = false;
                    while (true) {
                        line = bufferedReader.readLine();
                        if (line != null) {
                            break;
                            break;
                        }
                        strArrSplit = line.split("=");
                        Settings.System.putString(MyApp.a().getContentResolver(), "RestoreFactoryImportKey", strArrSplit[0]);
                        if (z && this.aM && this.aQ == 0 && !TextUtils.isEmpty(this.aP)) {
                            if (TextUtils.equals(this.aP, strArrSplit[0])) {
                                z = true;
                            }
                        }
                        if (c(strArrSplit[0], strArrSplit[1]) && !d(strArrSplit[0], strArrSplit[1]) && !e(strArrSplit[0], strArrSplit[1]) && !g(strArrSplit[0], strArrSplit[1])) {
                            a(strArrSplit[0], str + "/" + strArrSplit[1], str);
                        }
                    }
                    Settings.System.putString(MyApp.a().getContentResolver(), "RestoreFactoryImportKey", "");
                    this.C.write(40730, 3, 0, "chmod -R 666 /twdataconfig/cfg/");
                    g();
                    c();
                    try {
                        Intent intent2 = new Intent();
                        intent2.setComponent(new ComponentName("com.tw.eq", "com.tw.eq.EQService"));
                        intent2.putExtra("ISImport", true);
                        intent2.putExtra("path", str);
                        this.az.startService(intent2);
                    } catch (Exception unused2) {
                    }
                    this.C.write(40730, 1, 0, "cp -rf " + str + "/NaviBar-theme  /twdataconfig/");
                    this.C.write(40730, 1, 0, "chmod 777 twdataconfig/NaviBar-theme");
                    this.C.write(40730, 1, 0, "chmod 777 /twdataconfig/NaviBar-theme/SystemUITheme.apk");
                    this.C.write(40730, 1, 0, "chmod 777 /twdataconfig/NaviBar-theme/SystemUINavibar.apk");
                    this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.17
                        @Override // java.lang.Runnable
                        public void run() {
                            ImportExportExtensionSettings.this.C.write(40730, 1, 0, "/system/bin/pm install -r /twdataconfig/NaviBar-theme/SystemUITheme.apk");
                            ImportExportExtensionSettings.this.C.write(40730, 1, 0, "/system/bin/pm install -r /twdataconfig/NaviBar-theme/SystemUINavibar.apk");
                        }
                    }, 1500L);
                    this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.18
                        @Override // java.lang.Runnable
                        public void run() {
                            ImportExportExtensionSettings.this.C.write(40730, 3, 0, "cmd overlay enable-exclusive com.tw.systemui.theme_yellow");
                        }
                    }, 3000L);
                    i = PathInterpolatorCompat.MAX_NUM_POINTS;
                    if (y.b || this.b) {
                        i = 10000;
                    }
                    long j2 = i;
                    try {
                        Thread.sleep(j2);
                    } catch (Exception unused3) {
                    }
                    this.aT.sendEmptyMessageDelayed(65522, j2);
                    bufferedReader.close();
                } catch (Exception unused4) {
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
    }

    public boolean c(String str, String str2) throws Throwable {
        int i;
        if (str.equals("system_usb11")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_USB11", str2);
            return true;
        }
        if (str.equals("system_usbhub")) {
            SystemProperties.set("persist.sys.usb.support_hub", str2);
            return true;
        }
        if (str.equals("system_mirror")) {
            if (com.dofun.carsetting.f.b.e() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                int i2 = Integer.parseInt(str2);
                if (this.C.write(65523, 0, 0, "car_reverse\nmirror") != Integer.parseInt(str2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.ay);
                    sb.append(String.format("fdt set " + this.A + "/car_reverse mirror <0x%x>\n", Integer.valueOf(i2)));
                    this.ay = sb.toString();
                }
                return true;
            }
            if (com.dofun.carsetting.f.b.d()) {
                Settings.System.putInt(MyApp.a().getContentResolver(), "SYSTEM_MIRROR", Integer.parseInt(str2));
                return true;
            }
            SystemProperties.set("persist.camera.mirror", str2);
            SystemProperties.set("persist.camerarear.mirror", str2);
            return true;
        }
        if (str.equals("system_mirror_vertical")) {
            SystemProperties.set("persist.camera.angle", str2);
            return true;
        }
        if (str.equals("forward_looking_mirror")) {
            SystemProperties.set("persist.camera.mirror2", str2);
            return true;
        }
        if (str.equals("front_view_channel")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_CHANNEL", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("system_line")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_LINE", str2);
            return true;
        }
        if (str.equals("system_line2")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_LINE2", str2);
            return true;
        }
        if (str.equals("door")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "DOOR", str2);
            return true;
        }
        if (str.equals("system_floatvideo")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_FLOATVIDEO", str2);
            return true;
        }
        if (str.equals("system_door2")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_DOOR2", str2);
            return true;
        }
        if (str.equals("system_fradar")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_FRADAR", str2);
            return true;
        }
        if (str.equals("system_ta")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_TA", str2);
            return true;
        }
        if (str.equals("USER_LOCK")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "USER_LOCK", str2);
            return true;
        }
        if (str.equals("FACTORY_LOCK")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "FACTORY_LOCK", str2);
            return true;
        }
        if (str.equals("RESET_FACTORY")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "RESET_FACTORY", str2);
            return true;
        }
        if (str.equals("PLATFORMINFO")) {
            if (com.dofun.carsetting.f.b.F()) {
                u();
                if (!a(this.aW, str2) || com.dofun.carsetting.f.b.k(this.az).toLowerCase().equals(str2.toLowerCase())) {
                    Settings.System.putString(MyApp.a().getContentResolver(), "PLATFORMINFO", str2);
                }
            } else {
                Settings.System.putString(MyApp.a().getContentResolver(), "PLATFORMINFO", str2);
            }
            return true;
        }
        if (str.equals("CPU_LOCK")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "CPU_LOCK", str2);
            return true;
        }
        if (str.equals("BOOT_LOCK")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "BOOT_LOCK", str2);
            return true;
        }
        if (str.equals("SYSTEM_LOCK")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_LOCK", str2);
            return true;
        }
        if (str.equals("APP_VERSION_LOCK")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "APP_VERSION_LOCK", str2);
            return true;
        }
        if (str.equals("MCU_LOCK")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "MCU_LOCK", str2);
            return true;
        }
        if (str.equals("left_right_seat")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "LEFT_RIGHT_SEAT", str2);
            SystemProperties.set("persist.tw.navibar", String.valueOf(Integer.parseInt(str2) != 1 ? 1 : 2));
            return true;
        }
        if (str.equals("left_right_seat_real")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "LEFT_RIGHT_SEAT_REAL", str2);
            if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                com.dofun.carsetting.f.c.a(this.az, Integer.valueOf(str2).intValue() == 2);
            }
            return true;
        }
        if (str.equals("tvd0")) {
            SystemProperties.set("persist.camera.mode0", str2);
            return true;
        }
        if (str.equals("tvd1")) {
            SystemProperties.set("persist.camera.mode1", str2);
            return true;
        }
        if (str.equals("tvd2")) {
            SystemProperties.set("persist.camera.mode2", str2);
            return true;
        }
        if (str.equals("tvd3")) {
            SystemProperties.set("persist.camera.mode3", str2);
            return true;
        }
        if (str.equals("ahd360")) {
            SystemProperties.set("persist.camera.ahd360", str2);
            return true;
        }
        if (str.equals("reversingIC")) {
            SystemProperties.set("persist.camera.ic", str2);
            return true;
        }
        if (str.equals("system_iis")) {
            SystemProperties.set("persist.tw.iis", str2);
            this.C.write(515, 255);
            return true;
        }
        if (str.equals("temp_mode")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "TEMP_MODE", Integer.valueOf(str2).intValue());
            return true;
        }
        if (str.equals("reverse_pattern")) {
            SystemProperties.set("persist.reverse.vr", str2);
            return true;
        }
        if (str.equals("tw_no_streammedia")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "ISSHOW_STREAM_MEDIA_APK", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("tw_tpms")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "ISSHOW_TPMS", Integer.parseInt(str2));
            com.dofun.carsetting.f.a.a(this.az, "com.tw.tpms", Integer.parseInt(str2) == 1);
            return true;
        }
        if (str.equals("tw_wheel")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "ISSHOW_WHEEL", Integer.parseInt(str2));
            com.dofun.carsetting.f.a.a(this.az, "com.tw.keypad", Integer.parseInt(str2) == 1);
            return true;
        }
        if (str.equals("eq_settings")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "EQ_SETTINGS", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("tw_pip")) {
            SystemProperties.set("persist.tw.forcepip", str2);
            return true;
        }
        if (str.equals("amplifier_control")) {
            if (this.u != str2) {
                this.C.write(267, 19, Integer.parseInt(str2));
            }
            Settings.System.putString(this.az.getContentResolver(), "AMPLIFIER_CONTROL", str2);
            return true;
        }
        if (str.equals("system_front_fradar")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_FRONT_FRADAR", str2);
            return true;
        }
        if (str.equals("system_lrreverse")) {
            Settings.System.putString(MyApp.a().getContentResolver(), "SYSTEM_LRREVERSE", str2);
            return true;
        }
        if (str.equals("cid")) {
            SystemProperties.set("persist.tw.cid", str2);
            return true;
        }
        if (str.equals("system_colorTemp")) {
            SystemProperties.set("persist.vendor.pq.disable", str2);
            return true;
        }
        if (str.equals("ap_5g_channel")) {
            SystemProperties.set("persist.5g.channel", str2);
            return true;
        }
        if (str.equals("fowardview")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_SWITCH", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("spdif_out")) {
            SystemProperties.set("persist.spdif.out", str2);
            return true;
        }
        if (str.equals("sim_1_enable")) {
            if (this.aL != null) {
                this.aL.a(this.az, 0, "1".equals(str2));
            }
            return true;
        }
        if (str.equals("sim_2_enable")) {
            if (this.aL != null) {
                this.aL.a(this.az, 1, "1".equals(str2));
            }
            return true;
        }
        if (str.equals("carsetting_wallpaper")) {
            Settings.System.putString(this.az.getContentResolver(), "MAIN_BG_CHOOSE", str2);
            return true;
        }
        if (str.equals("air_popup_window")) {
            Settings.System.putString(this.az.getContentResolver(), "AIR_POPUP_WINDOW", str2);
            return true;
        }
        if (str.equals("air_style")) {
            Settings.System.putInt(this.az.getContentResolver(), "air.style.scene", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("screen_casting_code_rate")) {
            SystemProperties.set("persist.suding.reverse.video.level", str2);
            return true;
        }
        if (str.equals("trusted_module")) {
            SystemProperties.set("persist.tw.verify", str2);
            return true;
        }
        if (str.equals("boot_360")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "SYSTEM_360", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("float_360")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "FLOAT_360", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("setting_floating")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "SETTING_FLOATING", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("verical_left_right")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "VERICAL_LEFT_RIGHT", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("car_type")) {
            Settings.System.putInt(MyApp.a().getContentResolver(), "CAR_TYPE", Integer.parseInt(str2));
            return true;
        }
        if (str.equals("car_type2")) {
            SystemProperties.set("persist.sys.tw.car_type", str2);
            return true;
        }
        if (str.equals("camera_reverse")) {
            SystemProperties.set("persist.camera.reverse", str2);
            if (com.dofun.carsetting.f.b.s()) {
                if (Integer.parseInt(str2) == 3) {
                    SystemProperties.set("persist.camera.outtype", "1");
                    v.a((TWUtil) this.C, true);
                } else if (Integer.parseInt(str2) == 2 || Integer.parseInt(str2) == 0) {
                    v.a((TWUtil) this.C, false);
                }
            } else if (com.dofun.carsetting.f.b.p()) {
                v.a(this.C, Integer.parseInt(str2));
            }
            return true;
        }
        if (str.equals("build_in_bt_name")) {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && !TextUtils.isEmpty(str2)) {
                defaultAdapter.setName(str2);
            }
            return true;
        }
        if (str.equals("nav_bar_position")) {
            try {
                final int iIntValue = Integer.valueOf(str2).intValue();
                if (iIntValue != -1) {
                    Settings.System.putInt(MyApp.a().getContentResolver(), "SETTING_NAVBAR", iIntValue);
                    if (com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                        SystemProperties.set("persist.navibar.position", String.valueOf(iIntValue - 1));
                        switch (iIntValue) {
                            case 0:
                                Settings.System.putInt(this.az.getContentResolver(), "navi_bar_show_position", -1);
                                break;
                            case 1:
                                Settings.System.putInt(this.az.getContentResolver(), "navi_bar_show_position", 4);
                                break;
                            case 2:
                                Settings.System.putInt(this.az.getContentResolver(), "navi_bar_show_position", 1);
                                break;
                            case 3:
                                Settings.System.putInt(this.az.getContentResolver(), "navi_bar_show_position", 2);
                                break;
                            default:
                                break;
                        }
                    } else {
                        if (Settings.System.getInt(this.az.getContentResolver(), "navigationbar_config", 0) != 0) {
                            Settings.System.putInt(this.az.getContentResolver(), "navigationbar_config", 0);
                        }
                        if (Settings.System.getInt(this.az.getContentResolver(), "show_navigationbar", 0) != 0) {
                            Settings.System.putInt(this.az.getContentResolver(), "show_navigationbar", 0);
                        }
                        SystemProperties.set("persist.navibar.position", "0");
                        if (iIntValue != 0) {
                            this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    SystemProperties.set("persist.navibar.position", String.valueOf(iIntValue - 1));
                                    Settings.System.putInt(ImportExportExtensionSettings.this.az.getContentResolver(), "navigationbar_config", 16);
                                    Settings.System.putInt(ImportExportExtensionSettings.this.az.getContentResolver(), "show_navigationbar", 1);
                                }
                            }, 300L);
                        }
                    }
                }
            } catch (Exception e) {
                Log.w(f426a, "NAV_BAR_POSITION error:" + e.getMessage());
            }
            return true;
        }
        if (str.equals("screen_rotation")) {
            SystemProperties.set("persist.sf.forcerotation", str2);
            int i3 = Integer.parseInt(str2);
            if (!com.dofun.carsetting.f.b.I() && !com.dofun.carsetting.f.b.J()) {
                if (i3 == 1 || i3 == 3) {
                    b(1);
                } else {
                    b(0);
                }
            }
            return true;
        }
        if (str.equals("tw_install_apk")) {
            SystemProperties.set("persist.sys.tw.apkinstall_hide", str2);
            return true;
        }
        if (str.equals("microphone_parameter")) {
            SystemProperties.set("persist.tw.microphone", str2);
            return true;
        }
        if (str.equals("launcher_type")) {
            if (com.dofun.carsetting.f.b.a().contains("BBA")) {
                SystemProperties.set("persist.tw.launcherType", str2);
                if (Integer.parseInt(str2) == 0) {
                    this.C.write(40730, 1, 0, "pm unhide com.android.launcher");
                    this.C.write(40730, 1, 0, "pm hide com.dofun.variety");
                } else {
                    this.C.write(40730, 1, 0, "pm unhide com.dofun.variety");
                    this.C.write(40730, 1, 0, "pm hide com.android.launcher");
                }
            }
            return true;
        }
        if (str.equals("bba_carMode")) {
            SystemProperties.set("persist.tw.launcherType.carMode", str2);
            return true;
        }
        if (str.equals("hardware_info")) {
            Settings.System.putInt(this.az.getContentResolver(), "hardware_info_visibility", Integer.valueOf(str2).intValue());
        } else if (str.equals("resolution_info_visibility")) {
            Settings.System.putInt(this.az.getContentResolver(), "resolution_info_visibility", Integer.valueOf(str2).intValue());
        } else {
            if (str.equals("brightness")) {
                if (this.C != null) {
                    int i4 = ((Integer.parseInt(str2) * (this.aS - this.aR)) / 100) + this.aR;
                    Settings.System.putInt(MyApp.a().getContentResolver(), "color_brightness", Integer.parseInt(str2));
                    if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                        this.C.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_bright_tw " + i4);
                    } else {
                        this.C.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_bright " + i4);
                    }
                }
                return true;
            }
            if (str.equals("contrast")) {
                if (this.C != null) {
                    int i5 = ((Integer.parseInt(str2) * (this.aS - this.aR)) / 100) + this.aR;
                    Settings.System.putInt(MyApp.a().getContentResolver(), "color_contrast", Integer.parseInt(str2));
                    if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                        this.C.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_contrast_tw " + i5);
                    } else {
                        this.C.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_contrast " + i5);
                    }
                }
                return true;
            }
            if (str.equals("saturation")) {
                if (this.C != null) {
                    int i6 = ((Integer.parseInt(str2) * (this.aS - this.aR)) / 100) + this.aR;
                    Settings.System.putInt(MyApp.a().getContentResolver(), "color_saturation", Integer.parseInt(str2));
                    if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                        this.C.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_saturation_tw " + i6);
                    } else {
                        this.C.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_saturation " + i6);
                    }
                }
                return true;
            }
            if (str.equals("colortemp")) {
                if (this.C != null) {
                    Settings.System.putInt(MyApp.a().getContentResolver(), "color_temp", Integer.parseInt(str2));
                    if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                        this.C.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/color_temperature_tw " + Integer.parseInt(str2));
                    } else {
                        this.C.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/color_temperature " + Integer.parseInt(str2));
                    }
                }
                return true;
            }
            if (str.equals("echo_delay_param")) {
                SystemProperties.set("persist.btaec.delay", str2);
                return true;
            }
            if (str.equals("performance_mode")) {
                com.dofun.carsetting.f.b.a(Integer.parseInt(str2) == 0 ? 576 : 672);
                return true;
            }
            if (str.equals("xhkj_launcher_type")) {
                SystemProperties.set("persist.tw.launcher.theme.type", str2);
                return true;
            }
            if (str.equals("disable_hw_overlays")) {
                SystemProperties.set("persist.tw.disable.hwc", str2);
                com.dofun.carsetting.f.c.a(Integer.parseInt(str2) != 0);
                return true;
            }
            if (str.equals("magic_2313")) {
                SystemProperties.set("persist.sys.eqtype.2313", str2);
                return true;
            }
            if (str.equals("magic_3702")) {
                SystemProperties.set("persist.sys.eqtype.3702", str2);
                return true;
            }
            if (str.equals("tw360_tvi")) {
                SystemProperties.set("persist.tw360.tvi", str2);
                return true;
            }
            if (str.equals("screen_size")) {
                SystemProperties.set("persist.sys.screen.size", str2);
                return true;
            }
            if (str.equals("front_camera_index")) {
                Settings.System.putInt(MyApp.a().getContentResolver(), "FRONT_CAMERA_INDEX", Integer.parseInt(str2));
                return true;
            }
            if (str.equals("system_app2")) {
                Settings.System.putInt(MyApp.a().getContentResolver(), "SYSTEM_APP2", Integer.parseInt(str2));
                return true;
            }
            if (str.equals("hicar_screen_size")) {
                if (!TextUtils.isEmpty(str2)) {
                    Settings.System.putString(MyApp.a().getContentResolver(), "hicar_screen_size", str2);
                    return true;
                }
            } else {
                if (str.equals("zlink_hicar_hi")) {
                    SystemProperties.set("persist.zlink.disable.features", str2);
                    SystemProperties.set("rw.zlink.disable.features", str2);
                    return true;
                }
                if (str.equals("lcd_clk")) {
                    SystemProperties.set("persist.panel.lcdclk", str2);
                    if (com.dofun.carsetting.f.b.U() && this.C.write(65523, 0, 0, "lcd0\nlcd_dclk_freq") != (i = Integer.parseInt(str2))) {
                        this.ay += String.format("fdt set /soc@03000000/lcd0 lcd_dclk_freq <0x%x>\n", Integer.valueOf(i));
                    }
                    return true;
                }
                if (str.equals("SHOW_VOLTAGE")) {
                    Settings.System.putInt(MyApp.a().getContentResolver(), "SHOW_VOLTAGE", Integer.parseInt(str2));
                    return true;
                }
                if (str.equals("storage_info_switch")) {
                    SystemProperties.set("persist.tw.storage.switch", str2);
                    return true;
                }
                if (str.equals("persist_mic_front")) {
                    SystemProperties.set("persist.mic.front", str2);
                    return true;
                }
                if (str.equals("i2stospdif")) {
                    SystemProperties.set("persist.tw.i2stospdif", str2);
                    return true;
                }
                if (str.equals("mic_select")) {
                    SystemProperties.set("persist.tw.mic.select", str2);
                    com.dofun.carsetting.f.b.a(this.C, Integer.parseInt(str2));
                    w.a(this.C, "DeviceMic", Integer.parseInt(str2));
                    return true;
                }
                if (str.equals("tw_file")) {
                    Settings.System.putInt(MyApp.a().getContentResolver(), "ISSHOW_FILE", Integer.parseInt(str2));
                    com.dofun.carsetting.f.a.a(this.az, "com.tw.twfileexplore", Integer.parseInt(str2) == 1);
                    return true;
                }
                if (str.equals("tw_chrome")) {
                    Settings.System.putInt(MyApp.a().getContentResolver(), "ISSHOW_CHROME", Integer.parseInt(str2));
                    com.dofun.carsetting.f.a.a(this.az, "com.android.chrome", Integer.parseInt(str2) == 1);
                    return true;
                }
                if (str.equals("tw_google")) {
                    Settings.System.putInt(MyApp.a().getContentResolver(), "ISSHOW_GOOGLE", Integer.parseInt(str2));
                    com.dofun.carsetting.f.a.a(this.az, "com.google.android.googlequicksearchbox", Integer.parseInt(str2) == 1);
                    return true;
                }
                if (str.equals("remote_monitoring")) {
                    SystemProperties.set("persist.tw.sleepmode", str2);
                    return true;
                }
                if (str.equals("tw_nfc")) {
                    this.ay += String.format("fdt set /soc/car_reverse twnfc <0x%x>\n", Integer.valueOf(Integer.parseInt(str2)));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean d(String str, String str2) {
        try {
            if (str.equals("timezone")) {
                ((AlarmManager) MyApp.a().getSystemService(NotificationCompat.CATEGORY_ALARM)).setTimeZone(str2);
                return true;
            }
            if (str.equals("fontScale")) {
                try {
                    IActivityManager iActivityManager = ActivityManagerNative.getDefault();
                    Configuration configuration = iActivityManager.getConfiguration();
                    float f = Float.parseFloat(str2);
                    if (configuration.fontScale != f) {
                        configuration.fontScale = f;
                        iActivityManager.updatePersistentConfiguration(configuration);
                    }
                } catch (Exception unused) {
                }
                return true;
            }
            if (!str.equals("locale")) {
                return false;
            }
            this.c = true;
            this.d = str2;
            return true;
        } catch (Exception unused2) {
            return true;
        }
    }

    public boolean e(String str, String str2) {
        try {
            int i = 0;
            if (str.equals("radio_region")) {
                if (this.e != str2) {
                    this.C.write(265, 0, Integer.parseInt(str2));
                }
                return true;
            }
            if (str.equals("brake_mode")) {
                if (this.f != str2) {
                    this.C.write(267, 1, Integer.parseInt(str2));
                }
                return true;
            }
            int i2 = 2;
            if (str.equals("tv_mode")) {
                if (this.g != str2) {
                    this.C.write(267, 2, Integer.parseInt(str2));
                }
                return true;
            }
            if (str.equals("tv_norm")) {
                if (this.h != str2) {
                    this.C.write(267, 3, Integer.parseInt(str2));
                }
                return true;
            }
            if (str.equals("panel_light")) {
                if (this.j != str2) {
                    this.C.write(267, 4, Integer.parseInt(str2));
                }
                return true;
            }
            if (str.equals("panel_light_color")) {
                if (this.i != str2) {
                    this.C.write(272, Integer.parseInt(str2) & ViewCompat.MEASURED_SIZE_MASK);
                }
                return true;
            }
            if (str.equals("knobs1")) {
                if (this.k != str2) {
                    u uVar = this.C;
                    if (Integer.parseInt(str2) != 0) {
                        i = 1;
                    }
                    uVar.write(267, 7, i);
                }
                return true;
            }
            if (str.equals("knobs2")) {
                if (this.l != str2) {
                    u uVar2 = this.C;
                    if (Integer.parseInt(str2) != 0) {
                        i2 = 3;
                    }
                    uVar2.write(267, 7, i2);
                }
                return true;
            }
            if (str.equals("x_knobs")) {
                if (this.m != str2) {
                    this.C.write(267, 7, Integer.parseInt(str2) == 0 ? 4 : 5);
                }
                return true;
            }
            if (str.equals("ant_pwr")) {
                if (this.n != str2) {
                    this.C.write(267, 8, Integer.parseInt(str2));
                }
                return true;
            }
            if (str.equals("ampvol")) {
                if (this.o != str2) {
                    this.C.write(267, 13, Integer.parseInt(str2));
                }
                return true;
            }
            if (str.equals("swap_last_next")) {
                if (this.o != str2) {
                    this.C.write(267, 15, Integer.parseInt(str2));
                }
                return true;
            }
            if (str.equals("dsp_mode")) {
                if (this.q != str2) {
                    this.C.write(267, 10, Integer.valueOf(str2).intValue());
                    SystemProperties.set("persist.dsp.out", "0");
                }
                return true;
            }
            if (str.equals("back_light_v")) {
                if (this.r != str2) {
                    this.C.write(276, 2, Integer.valueOf(str2).intValue());
                }
                return true;
            }
            if (str.equals("key_tone")) {
                if (this.s != str2) {
                    this.C.write(260, 0, Integer.valueOf(str2).intValue());
                }
                return true;
            }
            if (str.equals("Sleep")) {
                if (this.t != str2) {
                    this.C.write(260, 5, Integer.valueOf(str2).intValue());
                }
                return true;
            }
            if (str.equals("shutdown_monitor")) {
                if (this.v != str2) {
                    this.C.write(260, 7, Integer.valueOf(str2).intValue());
                }
                return true;
            }
            if (!str.equals("low_voltage_protection")) {
                return false;
            }
            if (this.w != str2) {
                this.C.write(260, 12, Integer.valueOf(str2).intValue());
            }
            return true;
        } catch (Exception e) {
            Log.e(f426a, Log.getStackTraceString(e));
            return true;
        }
    }

    private boolean g(String str, String str2) {
        if (!com.dofun.carsetting.f.b.f() && !com.dofun.carsetting.f.b.F() && !com.dofun.carsetting.f.b.G()) {
            return false;
        }
        try {
            if (str.equals("f_sd0")) {
                int i = Integer.parseInt(str2);
                if (this.C.write(65523, 0, 0, "sdc0\nbus-width") != i) {
                    if (com.dofun.carsetting.f.b.G()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.ay);
                        sb.append(String.format("fdt set " + this.A + "/sdmmc@4020000/ bus-width <0x%x>\n", Integer.valueOf(i)));
                        this.ay = sb.toString();
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(this.ay);
                        sb2.append(String.format("fdt set " + this.A + "/sdmmc@04020000/ bus-width <0x%x>\n", Integer.valueOf(i)));
                        this.ay = sb2.toString();
                    }
                }
                return true;
            }
            if (str.equals("f_sd") && !com.dofun.carsetting.f.b.G()) {
                int i2 = Integer.parseInt(str2);
                if (this.C.write(65523, 0, 0, "sdc1\nbus-width") != i2) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.ay);
                    sb3.append(String.format("fdt set " + this.A + "/sdmmc@04021000/ bus-width <0x%x>\n", Integer.valueOf(i2)));
                    this.ay = sb3.toString();
                }
                return true;
            }
            if (str.equals("c_sd0")) {
                int i3 = Integer.parseInt(str2);
                if (this.C.write(65523, 0, 0, "sdc0\nmax-frequency") != Integer.parseInt(str2)) {
                    if (com.dofun.carsetting.f.b.G()) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(this.ay);
                        sb4.append(String.format("fdt set " + this.A + "/sdmmc@4020000/ max-frequency <0x%x>\n", Integer.valueOf(i3)));
                        this.ay = sb4.toString();
                    } else {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(this.ay);
                        sb5.append(String.format("fdt set " + this.A + "/sdmmc@04020000/ max-frequency <0x%x>\n", Integer.valueOf(i3)));
                        this.ay = sb5.toString();
                    }
                }
                return true;
            }
            if (str.equals("c_sd") && !com.dofun.carsetting.f.b.G()) {
                int i4 = Integer.parseInt(str2);
                if (this.C.write(65523, 0, 0, "sdc1\nmax-frequency") != Integer.parseInt(str2)) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(this.ay);
                    sb6.append(String.format("fdt set " + this.A + "/sdmmc@04021000/ max-frequency <0x%x>\n", Integer.valueOf(i4)));
                    this.ay = sb6.toString();
                }
                return true;
            }
            if (str.equals("resolution")) {
                int i5 = Integer.parseInt(str2);
                if (this.C.write(65523, 0, 0, "disp\ndisp_mode2") != Integer.parseInt(str2)) {
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(this.ay);
                    sb7.append(String.format("fdt set " + this.A + "/disp@01000000 disp_mode2 <0x%x>\n", Integer.valueOf(i5)));
                    this.ay = sb7.toString();
                    this.C.write(267, 11, Integer.parseInt(str2));
                }
                return true;
            }
            if (str.equals("reverse_unused")) {
                int i6 = Integer.parseInt(str2);
                this.C.write(267, 14, i6);
                if (this.C.write(65523, 0, 0, "car_reverse\nreverse_unused") != Integer.parseInt(str2)) {
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(this.ay);
                    sb8.append(String.format("fdt set " + this.A + "/car_reverse reverse_unused <0x%x>\n", Integer.valueOf(i6)));
                    this.ay = sb8.toString();
                }
                return true;
            }
            if (str.equals("reverse_type")) {
                int i7 = Integer.parseInt(str2);
                if (com.dofun.carsetting.f.b.b(i7)) {
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(this.ay);
                    sb9.append(String.format("fdt set " + this.A + "/car_reverse tvd_type <0x%x>\n", Integer.valueOf(i7)));
                    this.ay = sb9.toString();
                }
                return true;
            }
            if (str.equals("gamma") && !com.dofun.carsetting.f.b.G()) {
                int i8 = Integer.parseInt(str2);
                if (com.dofun.carsetting.f.b.b("/sys/class/disp/disp/attr/gamma") != Integer.parseInt(str2)) {
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append(this.ay);
                    sb10.append(String.format("fdt set " + this.A + "/disp@01000000 disp_gamma <0x%x>\n", Integer.valueOf(i8)));
                    this.ay = sb10.toString();
                }
                return true;
            }
            if (str.equals("reverse_csi_used")) {
                int i9 = Integer.parseInt(str2);
                if (com.dofun.carsetting.f.b.b("sys/class/car_reverse/csiuse") < 255) {
                    StringBuilder sb11 = new StringBuilder();
                    sb11.append(this.ay);
                    sb11.append(String.format("fdt set " + this.A + "/car_reverse csi_used <0x%x>\n", Integer.valueOf(i9)));
                    this.ay = sb11.toString();
                } else {
                    StringBuilder sb12 = new StringBuilder();
                    sb12.append(this.ay);
                    sb12.append(String.format("fdt set " + this.A + "/car_reverse tvd_resolution <0x%x>\n", Integer.valueOf(i9)));
                    this.ay = sb12.toString();
                }
                return true;
            }
            if (str.equals("reverse_resolution")) {
                int i10 = Integer.parseInt(str2);
                if (this.C.write(65523, 0, 0, "car_reverse\ntvd_resolution") != Integer.parseInt(str2)) {
                    StringBuilder sb13 = new StringBuilder();
                    sb13.append(this.ay);
                    sb13.append(String.format("fdt set " + this.A + "/car_reverse tvd_resolution <0x%x>\n", Integer.valueOf(i10)));
                    this.ay = sb13.toString();
                }
                return true;
            }
            if (str.equals("video_out")) {
                if (com.dofun.carsetting.f.b.G()) {
                    SystemProperties.set("persist.sys.video_out", str2);
                    switch (Integer.parseInt(str2)) {
                        case 0:
                            this.ay += "fdt set /soc/hdmi status disabled \n";
                            this.ay += "fdt set /soc/hdmi cvbs_used <0> \n";
                            break;
                        case 1:
                            this.ay += "fdt set /soc/hdmi status okay \n";
                            this.ay += "fdt set /soc/hdmi cvbs_used <0> \n";
                            break;
                        case 2:
                            this.ay += "fdt set /soc/hdmi status okay \n";
                            this.ay += "fdt set /soc/hdmi cvbs_used <1> \n";
                            break;
                        default:
                            break;
                    }
                } else {
                    int i11 = Integer.parseInt(str2);
                    if (this.C.write(65523, 0, 0, "disp\nvideo_out") != Integer.parseInt(str2)) {
                        StringBuilder sb14 = new StringBuilder();
                        sb14.append(this.ay);
                        sb14.append(String.format("fdt set " + this.A + "/disp@01000000 video_out <0x%x>\n", Integer.valueOf(i11)));
                        this.ay = sb14.toString();
                    }
                }
                return true;
            }
            if (!str.equals("twmemfake")) {
                return false;
            }
            int i12 = Integer.parseInt(str2);
            if (this.C.write(65523, 0, 0, "car_reverse\ntwmemfake") != Integer.parseInt(str2)) {
                this.ay += String.format("fdt set /soc/car_reverse twmemfake <0x%x>\n", Integer.valueOf(i12));
            }
            return true;
        } catch (Exception e) {
            Log.e(f426a, Log.getStackTraceString(e));
            return true;
        }
    }

    public boolean a(String str, final String str2, String str3) throws Throwable {
        int i;
        boolean z;
        String[] strArrSplit;
        try {
            if (str.equals("logo_settings")) {
                if (new File(str2).exists()) {
                    if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F()) {
                        this.C.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/bootloader /bootloader");
                        this.C.write(40730, 3, 0, "rm /bootloader/bootlogo.bmp");
                        this.C.write(40730, 3, 0, "rm /bootloader/bootlogo_720_1280.bmp");
                        this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.3
                            @Override // java.lang.Runnable
                            public void run() {
                                Log.d(ImportExportExtensionSettings.f426a, "xy:parseFile:logo_settings:" + str2);
                                ImportExportExtensionSettings.this.C.write(40730, 3, 0, "cp " + str2 + " /bootloader");
                                ImportExportExtensionSettings.this.C.write(40730, 3, 0, "sync");
                            }
                        }, 300L);
                    } else if (com.dofun.carsetting.f.b.H()) {
                        String str4 = SystemProperties.get("ro.tw.version");
                        if (TextUtils.isEmpty(str4) || (strArrSplit = str4.split("_")) == null || strArrSplit.length <= 1) {
                            z = true;
                        } else {
                            try {
                                z = Integer.parseInt(strArrSplit[1].split("\\.")[0]) >= 20240509;
                                try {
                                    Log.d(f426a, "onClick: systemOS date=" + strArrSplit[1]);
                                } catch (Exception e) {
                                    e = e;
                                    e.printStackTrace();
                                }
                            } catch (Exception e2) {
                                e = e2;
                                z = true;
                            }
                        }
                        if (z) {
                            this.C.write(40730, 3, 0, "mkfs.fat /dev/block/by-name/splash");
                            this.C.write(40730, 3, 0, "mkdir -p /data/tw/logo");
                            this.C.write(40730, 3, 0, "chmod 755 /data/tw/logo");
                            this.C.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/splash /data/tw/logo");
                            this.C.write(40730, 3, 0, "cp " + str2 + " /data/tw/bootlogo.bmp");
                            this.C.write(40730, 3, 0, "cp -rf /data/tw/bootlogo.bmp /data/tw/logo_custom.bmp");
                            this.C.write(40730, 3, 0, "cp -rf /data/tw/logo_custom.bmp /data/tw/logo/");
                            this.C.write(40730, 3, 0, "umount /data/tw/logo");
                            this.C.write(40730, 1, 0, "sync");
                        } else {
                            String strSubstring = str2.substring(0, str2.lastIndexOf("/"));
                            this.C.write(40730, 3, 0, "cp " + str2 + " /data/tw/bootlogo.bmp");
                            this.C.write(40730, 3, 0, "cp " + strSubstring + "/bootlogo.elf /data/tw/bootlogo.elf");
                            this.C.write(40730, 3, 0, "dd if=/data/tw/bootlogo.elf of=/dev/block/by-name/imagefv_a");
                            this.C.write(40730, 3, 0, "dd if=/data/tw/bootlogo.elf of=/dev/block/by-name/imagefv_b");
                        }
                    } else if (com.dofun.carsetting.f.b.G()) {
                        this.C.write(40730, 3, 0, "rm /twdataconfig/bootlogo.bmp");
                        this.C.write(40730, 3, 0, "rm /twdataconfig/bootlogo_720_1280.bmp");
                        this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.4
                            @Override // java.lang.Runnable
                            public void run() {
                                ImportExportExtensionSettings.this.C.write(40730, 3, 0, "cp " + str2 + " /twdataconfig");
                                ImportExportExtensionSettings.this.C.write(40730, 3, 0, "sync");
                            }
                        }, 300L);
                    } else {
                        this.C.write(40730, 3, 0, "cp " + str2 + " /data/tw/bootlogo.bmp");
                        this.C.write(40730, 3, 0, "dd if=/data/tw/bootlogo.bmp of=/dev/block/by-name/logo bs=512");
                        this.C.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                        if (new File(str3 + "/logo").exists()) {
                            this.C.write(40730, 3, 0, "cp -r " + str3 + "/logo /sdcard/.Tcfg/logo");
                        }
                    }
                }
                return true;
            }
            if (str.equals("key_touch")) {
                if (new File(str2).exists()) {
                    this.C.write(40730, 1, 0, "cp " + str2 + " /data/tw/touch");
                    this.C.write(40730, 1, 0, "chown 1000.1000 /data/tw/touch");
                    this.C.write(40730, 3, 0, "chmod 666 /data/tw/touch");
                }
                return true;
            }
            if (str.equals("touch_import")) {
                if (new File(str2).exists()) {
                    e();
                }
                return true;
            }
            if (str.equals("config")) {
                if (new File(str2).exists()) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(str2);
                        try {
                            byte[] bArr = new byte[4];
                            fileInputStream.read(bArr);
                            i = ((bArr[3] & 255) << 24) | ((bArr[2] & 255) << 16) | (bArr[0] & 255) | ((bArr[1] & 255) << 8);
                            try {
                                fileInputStream.close();
                            } catch (Exception unused) {
                            }
                        } catch (Exception unused2) {
                            fileInputStream.close();
                            i = -1;
                        } catch (Throwable th) {
                            fileInputStream.close();
                            throw th;
                        }
                    } catch (Exception unused3) {
                    }
                    if (i != -1) {
                        this.C.write(274, 1, i);
                    }
                }
                return true;
            }
            if (str.equals("key_panel")) {
                this.C.write(264, 128);
                return true;
            }
            if (str.equals("key_swc")) {
                this.C.write(259, 128);
                return true;
            }
            if (str.equals("system_cfg")) {
                if (!this.aM) {
                    this.C.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                    this.C.write(40730, 3, 0, "cp " + str2 + " /twdataconfig/cfg/");
                }
                return true;
            }
            if (str.equals("bootanimation")) {
                if (new File(str2).exists()) {
                    if (str2.endsWith(".mp4")) {
                        this.C.write(40730, 1, 0, "cp " + str2 + " /data/local/boot.mp4");
                        this.C.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                        this.C.write(40730, 1, 0, "chown 1000.1000 /data/local/boot.mp4");
                        this.C.write(40730, 3, 0, "chmod 755 /data/local/boot.mp4");
                    } else {
                        this.C.write(40730, 1, 0, "cp " + str2 + " /data/local/bootanimation.zip");
                        this.C.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                        this.C.write(40730, 1, 0, "chown 1000.1000 /data/local/bootanimation.zip");
                        this.C.write(40730, 3, 0, "chmod 755 /data/local/bootanimation.zip");
                    }
                } else if (str2 != null && str2.endsWith("android")) {
                    this.C.write(40730, 3, 0, "rm /data/local/bootanimation.zip");
                }
                return true;
            }
            if (str.equals("bt_ini")) {
                if (new File(str2).exists()) {
                    String str5 = null;
                    if (str2 != null && str2.endsWith("BtSettings.INI")) {
                        str5 = " /data/bluesoleil/BtSettings.INI";
                    } else if (str2 != null && str2.endsWith("bt_conf.ini")) {
                        str5 = " /data/goc/bt_conf.ini";
                    }
                    if (str5 != null) {
                        this.C.write(40730, 1, 0, "cp " + str2 + str5);
                        this.C.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                        this.C.write(40730, 3, 0, "cp " + str2 + " /twdataconfig/cfg/");
                        u uVar = this.C;
                        StringBuilder sb = new StringBuilder();
                        sb.append("chown 1000.1000 ");
                        sb.append(str5);
                        uVar.write(40730, 1, 0, sb.toString());
                        this.C.write(40730, 3, 0, "chmod 666 " + str5);
                    }
                }
                return true;
            }
            if (str.equals("wallpaper")) {
                if (new File(str2).exists()) {
                    this.C.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                    FileInputStream fileInputStream2 = new FileInputStream(str2);
                    try {
                        WallpaperManager.getInstance(MyApp.a()).setStream(fileInputStream2);
                    } catch (Exception unused4) {
                    } finally {
                        fileInputStream2.close();
                    }
                } else {
                    String[] strArrSplit2 = str2.split("/");
                    WallpaperManager.getInstance(MyApp.a()).getIWallpaperManager().setWallpaperComponent(new ComponentName(strArrSplit2[strArrSplit2.length - 2], strArrSplit2[strArrSplit2.length - 1]));
                }
                return true;
            }
            if (str.equals("sleepKillPackage_cfg")) {
                if (new File(str2).exists()) {
                    this.C.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                    this.C.write(40730, 3, 0, "cp " + str2 + " /twdataconfig/cfg/");
                }
                return true;
            }
            if (str.equals("config_resume_auto_play")) {
                if (new File(str2).exists()) {
                    if (new File("/twdataconfig/").canWrite()) {
                        this.C.write(40730, 3, 0, "cp " + str2 + " /twdataconfig/cfg/config_resume_auto_play.xml");
                        this.C.write(40730, 3, 0, "chmod 666 /twdataconfig/cfg/config_resume_auto_play.xml");
                    } else {
                        this.C.write(40730, 3, 0, "cp " + str2 + " /data/tw/config_resume_auto_play.xml");
                        this.C.write(40730, 3, 0, "chmod 666 /data/tw/config_resume_auto_play.xml");
                    }
                    this.C.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                }
                return true;
            }
            if (str.equals("file_copy")) {
                if (new File(str2).exists()) {
                    this.b = true;
                    this.C.write(40730, 3, 0, "cp -r " + str2 + "/. /sdcard/");
                }
                if (new File(str2 + "/CarSettingConfig.json").exists()) {
                    this.C.write(40730, 3, 0, "cp -r " + str2 + "/CarSettingConfig.json /sdcard/.Tcfg/");
                    this.C.write(40730, 3, 0, "cp -r " + str2 + "/CarSettingConfig.json /twdataconfig/cfg/");
                }
                return true;
            }
            if (str.equals("apk_install")) {
                e(str2);
                return true;
            }
            if (str.equals("password")) {
                if (new File(str2).exists()) {
                    if (!this.aM) {
                        this.C.write(40730, 3, 0, "cp " + str2 + " /sdcard/.Tcfg/");
                        this.C.write(40730, 3, 0, "cp " + str2 + " /twdataconfig/cfg/");
                    }
                    if (new File("/twdataconfig/cfg/password_factory.cfg").canRead()) {
                        h("/twdataconfig/cfg/password_factory.cfg");
                    } else {
                        h("/sdcard/.Tcfg/password_factory.cfg");
                    }
                }
                return true;
            }
            if (str.equals("versionSettings")) {
                if (new File(str2).exists()) {
                    if (com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.s()) {
                        this.C.write(40730, 1, 0, "cp -rf " + str2 + " /sdcard/.VersionSettings");
                        this.C.write(40730, 3, 0, "cp -rf " + str2 + " /twdataconfig/.VersionSettings");
                    } else {
                        this.C.write(40730, 1, 0, "cp -rf " + str2 + " /sdcard/.VersionSettings");
                        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            this.C.write(40730, 3, 0, "cp -rf " + str2 + " /bootloader/.VersionSettings");
                        }
                    }
                }
                return true;
            }
            if (str.equals("twdataconfig")) {
                if (new File(str2).exists()) {
                    this.C.write(40730, 3, 0, "cp " + str2 + " /twdataconfig/tw_config.bin ");
                }
                return true;
            }
            if (str.equals("lcd_enhance")) {
                if (new File(str2).exists()) {
                    this.C.write(40730, 3, 0, "cp " + str2 + " /twdataconfig/lcd_enhance.bin ");
                }
                return true;
            }
            if (str.equals("tw_cardoor")) {
                Log.d(f426a, "parseFile:TW_CARDOOR:" + str2);
                if (new File(str2).exists()) {
                    if (!new File("/mnt/sdcard/.ResourcePicture").exists()) {
                        this.C.write(40730, 3, 0, "mkdir /mnt/sdcard/.ResourcePicture");
                    }
                    this.C.write(40730, 3, 0, "cp -rf " + str2 + "/*  /mnt/sdcard/.ResourcePicture/");
                    this.C.write(40730, 3, 0, "sync");
                }
                return true;
            }
            if (str.equals("logo360")) {
                if (new File(str2).exists()) {
                    this.C.write(40730, 1, 0, "mount -t vfat /mnt/sdcard/.Baios360/.cache ");
                    this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.5
                        @Override // java.lang.Runnable
                        public void run() {
                            ImportExportExtensionSettings.this.C.write(40730, 3, 0, "cp -r " + str2 + "/ /mnt/sdcard/.Baios360/.cache");
                        }
                    }, 300L);
                }
                return true;
            }
            if (str.equals("brandlogo")) {
                if (new File(str2).exists()) {
                    this.C.write(40730, 1, 0, "cp " + str2 + " /sdcard/.brandlogo.png");
                }
            } else {
                if (str.equals("navi_name")) {
                    if (new File(str2).exists()) {
                        this.C.write(40730, 3, 0, "cp " + str2 + " /data/tw/");
                        this.C.write(40730, 3, 0, "chmod 666 /data/tw/navi_name");
                    }
                    return true;
                }
                if (str.equals("speech_name")) {
                    if (new File(str2).exists()) {
                        this.C.write(40730, 3, 0, "cp " + str2 + " /data/tw/");
                        this.C.write(40730, 3, 0, "chmod 666 /data/tw/speech_name");
                    }
                    return true;
                }
                if (str.equals("ph_theme_type")) {
                    if (new File(str2).exists()) {
                        if (!new File("/system/etc/ph").exists()) {
                            if (Build.VERSION.SDK_INT >= 28) {
                                this.C.write(40730, 1, 0, "mount -o remount,rw /");
                            } else {
                                this.C.write(40730, 1, 0, "mount -o remount,rw /system");
                            }
                            this.C.write(40730, 1, 0, "mkdir -p /system/etc/ph");
                        }
                        this.C.write(40730, 1, 0, "cp -rf " + str2 + " /system/etc/");
                        this.C.write(40730, 1, 0, "chown system.system /system/etc/ph");
                        this.C.write(40730, 1, 0, "chmod 755 -R /system/etc/ph");
                        this.C.write(40730, 1, 0, "chown system.system /system/etc/ph/*");
                        this.C.write(40730, 1, 0, "chmod 755 -R /system/etc/ph/*");
                        this.C.write(40730, 3, 0, "mount -o remount,ro /");
                        this.C.write(40730, 3, 0, "sync");
                    }
                    return true;
                }
                if (str.equals("carlogo")) {
                    if (new File(str2).exists()) {
                        this.C.write(40730, 3, 0, "cp " + str2 + " /data/tw/");
                        this.C.write(40730, 3, 0, "chmod 666 /data/tw/carlogo.png");
                    }
                    return true;
                }
                if (str.equals("screen_rotation_file_tag")) {
                    this.C.write(40730, 3, 0, "rm -rf /twdataconfig/screen_rotaion_*");
                    this.C.write(40730, 3, 0, "cp -rf " + str2 + "screen_rotaion_* /twdataconfig/");
                    this.C.write(40730, 3, 0, "chmod 666 /twdataconfig/screen_rotaion_*");
                    return true;
                }
                if (str.equals("app_autostart_name")) {
                    if (new File(str2).exists()) {
                        this.C.write(40730, 3, 0, "cp " + str2 + " /data/tw/");
                        this.C.write(40730, 3, 0, "chmod 666 /data/tw/app_autostart_name");
                    }
                    return true;
                }
            }
            return false;
        } catch (Exception e3) {
            Log.e(f426a, Log.getStackTraceString(e3));
            return true;
        }
    }

    public void c() {
        int i;
        if (this.c) {
            try {
                Configuration configuration = ActivityManagerNative.getDefault().getConfiguration();
                Locale localeForLanguageTag = Locale.forLanguageTag(this.d);
                if (!configuration.locale.equals(localeForLanguageTag)) {
                    LocaleList locales = LocalePicker.getLocales();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= locales.size()) {
                            i = 1;
                            break;
                        } else {
                            if (locales.get(i2).equals(localeForLanguageTag)) {
                                i = 0;
                                break;
                            }
                            i2++;
                        }
                    }
                    int size = locales.size();
                    Locale[] localeArr = new Locale[i + size];
                    localeArr[0] = localeForLanguageTag;
                    int i3 = 1;
                    for (int i4 = 0; i4 < size; i4++) {
                        if (!localeForLanguageTag.equals(locales.get(i4))) {
                            localeArr[i3] = locales.get(i4);
                            i3++;
                        }
                    }
                    LocaleList localeList = new LocaleList(localeArr);
                    LocaleList.setDefault(localeList);
                    LocalePicker.updateLocales(localeList);
                    new b(this.az).execute(new Void[0]);
                    Thread.sleep(2000L);
                }
            } catch (Exception unused) {
            }
            this.c = false;
        }
    }

    class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        CharSequence f452a;
        File[] b;

        public a(CharSequence charSequence, File[] fileArr) {
            this.f452a = charSequence;
            this.b = fileArr;
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x005d  */
    /* JADX WARN: Code duplicated, block: B:7:0x002e  */
    public void d() {
        boolean z;
        try {
            boolean z2 = true;
            if ((this.aA & 1) == 1) {
                if (new File(this.aB + "/" + this.aH.v).canRead()) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                z = false;
            }
            this.aE = z;
            if ((this.aA & 2) == 2) {
                if (!new File(this.aB + "/" + this.aH.w).canRead()) {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            this.aF = z2;
            Log.d(f426a, "autoCopyAndUpdateFromSpecificPath: needCopyDataFile:cmd:" + this.aA + " needCopyDataFile:" + this.aE + " needUpdateSystem:" + this.aF);
            if (this.aE) {
                c(this.aB + "/" + this.aH.v);
            }
        } catch (Exception e) {
            Log.d(f426a, Log.getStackTraceString(e));
        }
    }

    public void c(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        f(str);
    }

    private void f(final String str) {
        if (B) {
            Log.d(f426a, "step:copy data to root dir. sourceDir:" + str + " ->cmd:" + this.aA);
        }
        if (this.aA == 1) {
            this.z = AUTOMATIC_COPY_STATE.AUTO_COPY_START.ordinal();
        } else {
            this.z = AUTOMATIC_COPY_AND_UPDATE_STATE.AUTO_COPY_START.ordinal();
        }
        this.aI = a(this.az, MyApp.a().getString(R.string.copying_data_to_device));
        new Thread(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.6
            @Override // java.lang.Runnable
            public void run() {
                String[] list;
                int i = 0;
                ImportExportExtensionSettings.this.C.write(40730, 3, 0, "cp -rf " + str + "/. /sdcard/");
                ImportExportExtensionSettings.this.C.write(40730, 3, 0, "sync");
                File file = new File(str);
                if (!file.exists() || ((list = file.list()) != null && list.length != 0)) {
                    i = 300000;
                }
                ImportExportExtensionSettings.this.aT.sendEmptyMessageDelayed(65530, i);
            }
        }).start();
    }

    public void d(String str) {
        ArrayList<a> arrayListG = g(str);
        Log.d(f426a, "autoUpdateSystemFromSpecificDir: " + arrayListG.size());
        if (arrayListG.size() > 0) {
            a(arrayListG);
            return;
        }
        if (B) {
            Log.d(f426a, "step:auto update system from specific dir, but cannot find update file. specificDir:" + str + " ->cmd:" + this.aA);
        }
        if (this.aA == 2) {
            this.z = AUTOMATIC_UPDATE_STATE.AUTO_UPDATE_SYSTEM_SKIP.ordinal();
        } else {
            this.z = AUTOMATIC_COPY_AND_UPDATE_STATE.AUTO_UPDATE_SYSTEM_SKIP.ordinal();
        }
        Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.update_file_not_found), 1).show();
        if (new File(this.aB + "/" + this.aH.t).exists()) {
            Log.i(f426a, "Can not find system update file, so let's start auto import config.");
            b(this.aB);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<a> g(String str) {
        ArrayList<a> arrayList = new ArrayList<>();
        FilenameFilter filenameFilter = new FilenameFilter() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.7
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str2) {
                String lowerCase = str2.toLowerCase(Locale.US);
                String[] strArr = ImportExportExtensionSettings.this.aH.s.b;
                if (com.dofun.carsetting.f.b.O()) {
                    if (lowerCase.startsWith(ImportExportExtensionSettings.this.aH.s.c + ".")) {
                        Log.d(ImportExportExtensionSettings.f426a, "file1 ." + com.dofun.carsetting.f.b.a(file, lowerCase));
                        if (com.dofun.carsetting.f.b.a(file, lowerCase)) {
                            return false;
                        }
                    }
                    for (int i = 0; i < strArr.length; i++) {
                        String str3 = ImportExportExtensionSettings.f426a;
                        StringBuilder sb = new StringBuilder();
                        sb.append("file2.");
                        sb.append(TextUtils.equals(strArr[i], lowerCase) && com.dofun.carsetting.f.b.a(file, lowerCase));
                        Log.d(str3, sb.toString());
                        if (TextUtils.equals(strArr[i], lowerCase) && com.dofun.carsetting.f.b.a(file, lowerCase)) {
                            return false;
                        }
                    }
                }
                for (String str4 : strArr) {
                    if (TextUtils.equals(str4, lowerCase)) {
                        return true;
                    }
                }
                if (lowerCase.startsWith(ImportExportExtensionSettings.this.aH.s.c + ".")) {
                    return true;
                }
                if (!TextUtils.isEmpty(ImportExportExtensionSettings.this.aH.s.d)) {
                    if (lowerCase.startsWith(ImportExportExtensionSettings.this.aH.s.d + ".")) {
                        return true;
                    }
                }
                if (TextUtils.isEmpty(ImportExportExtensionSettings.this.aH.s.j) || !lowerCase.startsWith(ImportExportExtensionSettings.this.aH.s.j.toLowerCase(Locale.US)) || !lowerCase.endsWith(".zip")) {
                    return !TextUtils.isEmpty(ImportExportExtensionSettings.this.aH.s.k) && lowerCase.startsWith(ImportExportExtensionSettings.this.aH.s.k.toLowerCase(Locale.US)) && lowerCase.endsWith(".zip");
                }
                if (!ImportExportExtensionSettings.this.aH.s.r) {
                    return true;
                }
                long jA = com.dofun.carsetting.f.b.a(SystemProperties.get("ro.tw.version"));
                long jA2 = com.dofun.carsetting.f.b.a(lowerCase);
                Log.d(ImportExportExtensionSettings.f426a, "getUpdateFile accept: osVersionTime=" + jA + "," + jA2);
                return jA2 > jA;
            }
        };
        if (str != null) {
            File[] fileArrListFiles = new File(str).listFiles(filenameFilter);
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                arrayList.add(new a("AUTO_UPDATE_PATH", fileArrListFiles));
            }
        } else {
            for (int i = 0; i < this.aH.A.length; i++) {
                File[] fileArrListFiles2 = new File("/" + this.aH.A[i]).listFiles(filenameFilter);
                if (fileArrListFiles2 != null && fileArrListFiles2.length > 0) {
                    arrayList.add(new a(this.aH.A[i], fileArrListFiles2));
                }
            }
            for (int i2 = 0; i2 < this.aH.B.length; i2++) {
                File[] fileArrListFiles3 = new File("/" + this.aH.B[i2]).listFiles(filenameFilter);
                if (fileArrListFiles3 != null && fileArrListFiles3.length > 0) {
                    arrayList.add(new a(this.aH.B[i2], fileArrListFiles3));
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ArrayList<a> arrayList) {
        if (arrayList.size() > 0) {
            CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                charSequenceArr[i] = arrayList.get(i).f452a;
            }
            if (B) {
                Log.d(f426a, "step:start update system. ->cmd:" + this.aA);
            }
            if (this.aA == 2) {
                this.z = AUTOMATIC_UPDATE_STATE.AUTO_UPDATE_SYSTEM_START.ordinal();
            } else {
                this.z = AUTOMATIC_COPY_AND_UPDATE_STATE.AUTO_UPDATE_SYSTEM_START.ordinal();
            }
            this.aI = a(this.az, MyApp.a().getString(R.string.system_update_summary));
            final File[] fileArr = arrayList.get(0).b;
            new Thread(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.8
                @Override // java.lang.Runnable
                public void run() throws Throwable {
                    if (ImportExportExtensionSettings.this.aF) {
                        ImportExportExtensionSettings.this.C.write(40730, 3, 0, "touch " + ImportExportExtensionSettings.this.aB + "/" + ImportExportExtensionSettings.this.aH.x);
                    }
                    if (ImportExportExtensionSettings.this.a(fileArr)) {
                        ImportExportExtensionSettings.this.C.write(40730, 3, 0, "touch " + ImportExportExtensionSettings.this.aB + "/" + ImportExportExtensionSettings.this.aH.z);
                    }
                    ImportExportExtensionSettings.this.g();
                    ImportExportExtensionSettings.this.aH.s.g = fileArr;
                    ImportExportExtensionSettings.this.aH.s.a(ImportExportExtensionSettings.this.aC);
                    ImportExportExtensionSettings.this.aH.s.a(ImportExportExtensionSettings.this.az, ImportExportExtensionSettings.this.C, ImportExportExtensionSettings.this.aD);
                    if (ImportExportExtensionSettings.this.aI != null) {
                        ImportExportExtensionSettings.this.aI.dismiss();
                    }
                    if (ImportExportExtensionSettings.B) {
                        Log.d(ImportExportExtensionSettings.f426a, "step:auto update system complete. ->cmd:" + ImportExportExtensionSettings.this.aA);
                    }
                    if (ImportExportExtensionSettings.this.aA == 2) {
                        ImportExportExtensionSettings.this.z = AUTOMATIC_UPDATE_STATE.AUTO_UPDATE_SYSTEM_COMPLETE.ordinal();
                    } else {
                        ImportExportExtensionSettings.this.z = AUTOMATIC_COPY_AND_UPDATE_STATE.AUTO_UPDATE_SYSTEM_COMPLETE.ordinal();
                    }
                }
            }).start();
            return;
        }
        if (B) {
            Log.d(f426a, "step:auto update system skip, because no update file. ->cmd:" + this.aA);
        }
        if (this.aA == 2) {
            this.z = AUTOMATIC_UPDATE_STATE.AUTO_UPDATE_SYSTEM_SKIP.ordinal();
        } else {
            this.z = AUTOMATIC_COPY_AND_UPDATE_STATE.AUTO_UPDATE_SYSTEM_SKIP.ordinal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(File[] fileArr) {
        if (fileArr == null) {
            return false;
        }
        File fileA = null;
        File fileA2 = null;
        for (File file : fileArr) {
            if (TextUtils.equals(file.getName(), this.aH.s.e)) {
                return true;
            }
            if (file.getName().endsWith(".zip")) {
                String[] strArrSplit = file.getName().split("_");
                if (strArrSplit != null && strArrSplit.length >= 3) {
                    if (strArrSplit[2].toUpperCase().startsWith("OS")) {
                        fileA = a(fileA, file);
                    } else if (strArrSplit[2].toUpperCase().startsWith("APP")) {
                        fileA2 = a(fileA2, file);
                    } else {
                        fileA = a(fileA, file);
                    }
                } else {
                    fileA = a(fileA, file);
                }
            }
        }
        if (fileA != null) {
            try {
                ZipEntry entry = new ZipFile(fileA).getEntry(this.aH.s.e);
                if (entry != null && TextUtils.equals(entry.getName(), this.aH.s.e)) {
                    return true;
                }
            } catch (Exception e) {
                Log.e(f426a, "findBootUpdateFile:find boot update file in zip fail:" + e.getMessage());
            }
        }
        if (fileA2 != null) {
            try {
                ZipEntry entry2 = new ZipFile(fileA2).getEntry(this.aH.s.e);
                if (entry2 != null && TextUtils.equals(entry2.getName(), this.aH.s.e)) {
                    return true;
                }
            } catch (Exception e2) {
                Log.e(f426a, "findBootUpdateFile:find boot update file in zip fail:" + e2.getMessage());
            }
        }
        return false;
    }

    private File a(File file, File file2) {
        if (file == null) {
            return file2;
        }
        if (file2.lastModified() - file.lastModified() <= 0) {
            return file;
        }
        Log.d(f426a, "updateZipFileIfNeed:new file:" + file2.getName() + " " + file2.lastModified());
        return file2;
    }

    public void e() {
        byte[] bArr = new byte[242];
        if (b(bArr)) {
            byte b2 = 0;
            bArr[0] = 4;
            bArr[187] = 1;
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream("/usb/touch.cfg");
            } catch (Exception unused) {
            }
            if (fileInputStream == null) {
                try {
                    fileInputStream = new FileInputStream("/usb0/touch.cfg");
                } catch (Exception unused2) {
                }
            }
            if (fileInputStream == null) {
                try {
                    fileInputStream = new FileInputStream("/usb1/touch.cfg");
                } catch (Exception unused3) {
                }
            }
            if (fileInputStream == null) {
                try {
                    fileInputStream = new FileInputStream("/usb2/touch.cfg");
                } catch (Exception unused4) {
                }
            }
            if (fileInputStream == null) {
                try {
                    fileInputStream = new FileInputStream("/usb3/touch.cfg");
                } catch (Exception unused5) {
                }
            }
            if (fileInputStream == null) {
                try {
                    fileInputStream = new FileInputStream("/extsd/touch.cfg");
                } catch (Exception unused6) {
                }
            }
            if (fileInputStream == null) {
                try {
                    fileInputStream = new FileInputStream("/extsd1/touch.cfg");
                } catch (Exception unused7) {
                }
            }
            if (fileInputStream == null) {
                try {
                    fileInputStream = new FileInputStream("/sdcard/touch.cfg");
                } catch (Exception unused8) {
                }
            }
            try {
                if (fileInputStream != null) {
                    try {
                        byte[] bArr2 = new byte[242];
                        bArr2[0] = 4;
                        bArr2[1] = -70;
                        byte[] bArr3 = new byte[2048];
                        String[] strArrSplit = new String(bArr3, 0, fileInputStream.read(bArr3)).split(",");
                        for (int i = 0; i < strArrSplit.length; i++) {
                            int iIndexOf = strArrSplit[i].indexOf("0x");
                            bArr2[i + 2] = (byte) Integer.parseInt(strArrSplit[i].substring(iIndexOf + 2, iIndexOf + 4), 16);
                        }
                        if ((bArr2[8] & 3) != 0) {
                            int i2 = 2;
                            bArr2[2] = 0;
                            bArr2[8] = (byte) (bArr2[8] & (-4));
                            while (i2 < 186) {
                                b2 = (byte) (b2 + bArr2[i2]);
                                i2++;
                            }
                            bArr2[i2] = (byte) ((~b2) + 1);
                        }
                        a(bArr2);
                    } catch (Exception unused9) {
                    } finally {
                        fileInputStream.close();
                    }
                }
            } catch (Exception unused10) {
            }
        }
    }

    public void e(String str) {
        Log.d(f426a, "installApks:" + str);
        try {
            if (new File(str).exists()) {
                File[] fileArrListFiles = new File(str).listFiles(new FileFilter() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.9
                    @Override // java.io.FileFilter
                    public boolean accept(File file) {
                        if (file.isDirectory() || !file.getName().endsWith(".apk")) {
                            return false;
                        }
                        if (ImportExportExtensionSettings.this.aU == null) {
                            return true;
                        }
                        for (int i = 0; i < ImportExportExtensionSettings.this.aU.length; i++) {
                            if (file.getName().startsWith(ImportExportExtensionSettings.this.aU[i])) {
                                return false;
                            }
                        }
                        return true;
                    }
                });
                if (fileArrListFiles.length == 0) {
                    return;
                }
                int i = 0;
                while (i < fileArrListFiles.length) {
                    File file = fileArrListFiles[i];
                    Message message = new Message();
                    message.what = 65528;
                    i++;
                    message.arg1 = i;
                    message.arg2 = fileArrListFiles.length;
                    this.aT.sendMessage(message);
                    if (i(file.getAbsolutePath())) {
                        if (this.aV) {
                            s();
                        }
                        this.C.write(40730, 1, 0, "/system/bin/pm install -r -g " + file.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            Log.d(f426a, "installApks:" + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        if (this.ay.length() > 0) {
            this.C.write(65282, 0, 0, "run_command\n" + this.ay + "\nfdt save");
            return;
        }
        this.C.write(514, 1, 0);
    }

    public int a(int i) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/sys/class/tw/gtp/touch");
            try {
                fileOutputStream.write(new byte[]{(byte) i});
            } catch (Exception unused) {
            } finally {
                fileOutputStream.close();
            }
        } catch (Exception unused2) {
        }
        return 0;
    }

    public int a(byte[] bArr) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/sys/class/tw/gtp/touch");
            try {
                fileOutputStream.write(bArr);
            } catch (Exception unused) {
            } finally {
                fileOutputStream.close();
            }
            return 0;
        } catch (Exception unused2) {
            return 0;
        }
    }

    public boolean b(byte[] bArr) {
        a(3);
        c(bArr);
        return bArr[0] == 3;
    }

    public void c(byte[] bArr) {
        try {
            FileInputStream fileInputStream = new FileInputStream("/sys/class/tw/gtp/touch");
            try {
                fileInputStream.read(bArr);
            } catch (Exception unused) {
            } finally {
                fileInputStream.close();
            }
        } catch (Exception unused2) {
        }
    }

    public static ProgressDialog a(Context context, String str) {
        ProgressDialog progressDialog = new ProgressDialog(context, 3);
        try {
            progressDialog.setMessage(str);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setOnCancelListener(null);
            progressDialog.show();
        } catch (Exception e) {
            Log.e(f426a, "showProgressDialog:" + e.getMessage());
        }
        return progressDialog;
    }

    static Intent f() {
        return new Intent("android.intent.action.MAIN").addCategory("com.android.settings.SHORTCUT");
    }

    public static class b extends AsyncTask<Void, Void, Void> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final Context f453a;

        public b(Context context) {
            this.f453a = context;
        }

        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public Void doInBackground(Void... voidArr) {
            ShortcutManager shortcutManager = (ShortcutManager) this.f453a.getSystemService(ShortcutManager.class);
            PackageManager packageManager = this.f453a.getPackageManager();
            ArrayList arrayList = new ArrayList();
            for (ShortcutInfo shortcutInfo : shortcutManager.getPinnedShortcuts()) {
                if (shortcutInfo.getId().startsWith("component-shortcut-")) {
                    ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(ImportExportExtensionSettings.f().setComponent(ComponentName.unflattenFromString(shortcutInfo.getId().substring("component-shortcut-".length()))), 0);
                    if (resolveInfoResolveActivity != null) {
                        arrayList.add(new ShortcutInfo.Builder(this.f453a, shortcutInfo.getId()).setShortLabel(resolveInfoResolveActivity.loadLabel(packageManager)).build());
                    }
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            shortcutManager.updateShortcuts(arrayList);
            return null;
        }
    }

    private void h(String str) throws Throwable {
        BufferedReader bufferedReader;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(str));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        String[] strArrSplit = line.split("=");
                        if (strArrSplit.length >= 2) {
                            if (com.dofun.carsetting.f.b.F()) {
                                if (strArrSplit[0].equals("PLATFORMINFO")) {
                                    u();
                                    if (!a(this.aW, strArrSplit[1]) || com.dofun.carsetting.f.b.k(this.az).toLowerCase().equals(strArrSplit[1].toLowerCase())) {
                                        Settings.System.putString(MyApp.a().getContentResolver(), strArrSplit[0], strArrSplit[1]);
                                    }
                                }
                            } else {
                                Settings.System.putString(MyApp.a().getContentResolver(), strArrSplit[0], strArrSplit[1]);
                            }
                        }
                    } catch (Exception unused) {
                        if (bufferedReader == null) {
                            return;
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        throw th;
                    }
                }
            } catch (Exception unused2) {
                return;
            }
        } catch (Exception unused3) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
        }
        bufferedReader.close();
    }

    private boolean i(String str) {
        PackageInfo packageInfo;
        try {
            PackageInfo packageArchiveInfo = this.az.getPackageManager().getPackageArchiveInfo(str, 1);
            if (packageArchiveInfo == null) {
                return false;
            }
            String str2 = packageArchiveInfo.packageName;
            if (!TextUtils.isEmpty(str2)) {
                try {
                    packageInfo = this.az.getPackageManager().getPackageInfo(str2, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    packageInfo = null;
                }
                if (packageInfo == null) {
                    this.aV = "com.zjinnova.zlink".equals(str2);
                    return true;
                }
                Log.e(f426a, "checkIfNeedInstall:packageName: " + str2 + " new app versionCode:" + packageArchiveInfo.versionCode + " old app versionCode:" + packageInfo.versionCode);
                if (packageArchiveInfo.versionCode > packageInfo.versionCode) {
                    this.aV = "com.zjinnova.zlink".equals(str2);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(f426a, "checkIfNeedInstall:" + e.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r1v9 */
    public void g() throws Throwable {
        try {
            String str = this.aB + "/deviceid";
            Log.d(f426a, "writeCidToUsbDisk 0:cidFilePath:" + str);
            if (new File(str).exists()) {
                this.C.write(40730, 1, 0, "rm -rf " + str);
            }
            ?? r1 = 0;
            BufferedWriter bufferedWriter = null;
            try {
                try {
                    String strA = v.a();
                    Log.d(f426a, "writeCidToUsbDisk 1:cid:" + strA);
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(str));
                    try {
                        bufferedWriter2.write(strA);
                        bufferedWriter2.flush();
                        bufferedWriter2.close();
                    } catch (Exception e) {
                        e = e;
                        bufferedWriter = bufferedWriter2;
                        Log.d(f426a, "writeCidToUsbDisk:error:" + e.getMessage());
                        new File(str).delete();
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        r1 = bufferedWriter2;
                        if (r1 != 0) {
                            r1.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
            r1 = 438;
            FileUtils.setPermissions(str, 438, -1, -1);
            this.C.write(40730, 1, 0, "sync");
        } catch (Exception unused) {
        }
    }

    private void m() {
        this.aU = this.az.getResources().getStringArray(R.array.apks_black_list);
    }

    public void h() {
        Log.d(f426a, "startAutoReboot:cmd:--->" + this.aA);
        if (this.aA == 5) {
            this.z = AUTOMATIC_AUTO_REBOOT.AUTO_REBOOT.ordinal();
        }
    }

    public boolean i() {
        Log.d(f426a, "isAutomatic:automaticState:" + this.z + " cmd:" + this.aA);
        if (this.z == -1) {
            return false;
        }
        switch (this.aA) {
            case 1:
                return n();
            case 2:
                return o();
            case 3:
                return p();
            case 4:
                return q();
            case 5:
                return r();
            default:
                return false;
        }
    }

    private boolean n() {
        return (this.z == AUTOMATIC_COPY_STATE.INVALID.ordinal() || this.z == AUTOMATIC_COPY_STATE.AUTO_COPY_COMPLETE.ordinal()) ? false : true;
    }

    private boolean o() {
        return (this.z == AUTOMATIC_UPDATE_STATE.INVALID.ordinal() || this.z == AUTOMATIC_UPDATE_STATE.AUTO_UPDATE_SYSTEM_SKIP.ordinal() || this.z == AUTOMATIC_UPDATE_STATE.AUTO_UPDATE_SYSTEM_COMPLETE.ordinal()) ? false : true;
    }

    private boolean p() {
        return (this.z == AUTOMATIC_COPY_AND_UPDATE_STATE.INVALID.ordinal() || this.z == AUTOMATIC_COPY_AND_UPDATE_STATE.AUTO_UPDATE_SYSTEM_COMPLETE.ordinal()) ? false : true;
    }

    private boolean q() {
        return (this.z == AUTOMATIC_IMPORT_STATE.INVALID.ordinal() || this.z == AUTOMATIC_IMPORT_STATE.AUTO_IMPORT_SUCCESS.ordinal() || this.z == AUTOMATIC_IMPORT_STATE.AUTO_IMPORT_FAIL.ordinal()) ? false : true;
    }

    private boolean r() {
        return this.z != AUTOMATIC_AUTO_REBOOT.INVALID.ordinal();
    }

    public void j() {
        this.aA = -1;
        this.z = -1;
    }

    private void b(final int i) {
        if (com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            if (i == 0) {
                Settings.System.putInt(this.az.getContentResolver(), "navi_bar_show_position", -1);
                return;
            } else {
                Settings.System.putInt(this.az.getContentResolver(), "navi_bar_show_position", 4);
                return;
            }
        }
        if (Settings.System.getInt(this.az.getContentResolver(), "navigationbar_config", 0) != 0) {
            Settings.System.putInt(this.az.getContentResolver(), "navigationbar_config", 0);
        }
        if (Settings.System.getInt(this.az.getContentResolver(), "show_navigationbar", 0) != 0) {
            Settings.System.putInt(this.az.getContentResolver(), "show_navigationbar", 0);
        }
        SystemProperties.set("persist.navibar.position", "0");
        if (i != 0) {
            this.aT.postDelayed(new Runnable() { // from class: com.dofun.carsetting.widget.ImportExportExtensionSettings.10
                @Override // java.lang.Runnable
                public void run() {
                    SystemProperties.set("persist.navibar.position", String.valueOf(i - 1));
                    Settings.System.putInt(ImportExportExtensionSettings.this.az.getContentResolver(), "navigationbar_config", 16);
                    Settings.System.putInt(ImportExportExtensionSettings.this.az.getContentResolver(), "show_navigationbar", 1);
                }
            }, 300L);
        }
    }

    private void s() {
        Log.d(f426a, "clearZlinkCache()");
        if (this.C != null) {
            this.C.write(40730, 1, 0, "pm clear com.zjinnova.zlink");
        }
    }

    private int t() {
        return (((SystemProperties.getInt("persist.panel.width", 1024) + 30) * (SystemProperties.getInt("persist.panel.height", 600) + 30)) * 35) / 1000000;
    }

    private void u() {
        this.aW = this.az.getResources().getStringArray(R.array.platform_list);
    }

    private boolean a(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str.toLowerCase().contains(str2.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
