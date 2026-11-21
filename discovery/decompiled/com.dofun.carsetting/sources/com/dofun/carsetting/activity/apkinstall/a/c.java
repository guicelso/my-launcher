package com.dofun.carsetting.activity.apkinstall.a;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.SparseArray;
import com.dofun.carsetting.R;
import com.dofun.carsetting.f.u;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;

/* JADX INFO: compiled from: TWUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static String f101a;
    private static String b;
    private static SparseArray<Bitmap> c = new SparseArray<>();

    /* JADX WARN: Code duplicated, block: B:104:0x016a A[Catch: Exception -> 0x01da, TryCatch #0 {Exception -> 0x01da, blocks: (B:3:0x0001, B:5:0x0008, B:11:0x004c, B:13:0x0054, B:14:0x0057, B:17:0x005e, B:19:0x0066, B:22:0x0073, B:24:0x007f, B:27:0x0086, B:29:0x008e, B:32:0x009b, B:34:0x00a7, B:37:0x00ae, B:39:0x00b6, B:42:0x00c3, B:44:0x00cf, B:48:0x00d8, B:51:0x00df, B:55:0x00ea, B:56:0x00ed, B:59:0x00f4, B:62:0x00fd, B:66:0x0108, B:69:0x010f, B:74:0x011b, B:77:0x0122, B:81:0x012d, B:82:0x0130, B:85:0x0137, B:88:0x0140, B:92:0x014b, B:93:0x014e, B:96:0x0155, B:100:0x0160, B:101:0x0163, B:104:0x016a, B:108:0x0175, B:111:0x017c, B:115:0x0187, B:116:0x018a, B:119:0x0191, B:122:0x019a, B:125:0x01a3, B:129:0x01ae, B:132:0x01b5, B:136:0x01c0, B:139:0x01c7, B:144:0x01d3), top: B:150:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:106:0x0172 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:62:0x00fd A[Catch: Exception -> 0x01da, TryCatch #0 {Exception -> 0x01da, blocks: (B:3:0x0001, B:5:0x0008, B:11:0x004c, B:13:0x0054, B:14:0x0057, B:17:0x005e, B:19:0x0066, B:22:0x0073, B:24:0x007f, B:27:0x0086, B:29:0x008e, B:32:0x009b, B:34:0x00a7, B:37:0x00ae, B:39:0x00b6, B:42:0x00c3, B:44:0x00cf, B:48:0x00d8, B:51:0x00df, B:55:0x00ea, B:56:0x00ed, B:59:0x00f4, B:62:0x00fd, B:66:0x0108, B:69:0x010f, B:74:0x011b, B:77:0x0122, B:81:0x012d, B:82:0x0130, B:85:0x0137, B:88:0x0140, B:92:0x014b, B:93:0x014e, B:96:0x0155, B:100:0x0160, B:101:0x0163, B:104:0x016a, B:108:0x0175, B:111:0x017c, B:115:0x0187, B:116:0x018a, B:119:0x0191, B:122:0x019a, B:125:0x01a3, B:129:0x01ae, B:132:0x01b5, B:136:0x01c0, B:139:0x01c7, B:144:0x01d3), top: B:150:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:64:0x0105 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:96:0x0155 A[Catch: Exception -> 0x01da, TryCatch #0 {Exception -> 0x01da, blocks: (B:3:0x0001, B:5:0x0008, B:11:0x004c, B:13:0x0054, B:14:0x0057, B:17:0x005e, B:19:0x0066, B:22:0x0073, B:24:0x007f, B:27:0x0086, B:29:0x008e, B:32:0x009b, B:34:0x00a7, B:37:0x00ae, B:39:0x00b6, B:42:0x00c3, B:44:0x00cf, B:48:0x00d8, B:51:0x00df, B:55:0x00ea, B:56:0x00ed, B:59:0x00f4, B:62:0x00fd, B:66:0x0108, B:69:0x010f, B:74:0x011b, B:77:0x0122, B:81:0x012d, B:82:0x0130, B:85:0x0137, B:88:0x0140, B:92:0x014b, B:93:0x014e, B:96:0x0155, B:100:0x0160, B:101:0x0163, B:104:0x016a, B:108:0x0175, B:111:0x017c, B:115:0x0187, B:116:0x018a, B:119:0x0191, B:122:0x019a, B:125:0x01a3, B:129:0x01ae, B:132:0x01b5, B:136:0x01c0, B:139:0x01c7, B:144:0x01d3), top: B:150:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:98:0x015d A[RETURN] */
    public static boolean a(String str, int i, String str2) {
        try {
            u uVarA = u.a();
            if (uVarA != null) {
                int iWrite = uVarA.write(65521);
                uVarA.close();
                try {
                    if (String.format(Locale.US, "%05d", Integer.valueOf(Integer.parseInt(a.b(String.format(Locale.US, "%04x", Integer.valueOf(iWrite))).substring(0, 4), 16))).equals(str)) {
                        return true;
                    }
                } catch (Exception unused) {
                }
                switch (iWrite) {
                    case 6:
                        if (i == 5) {
                            if ("28012801".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case 17:
                        if (i == 5) {
                            if ("456789".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case 22:
                        if (i == 1) {
                            if ("668811".equals(str)) {
                                return true;
                            }
                        } else {
                            switch (i) {
                                case 3:
                                    if ("668822".equals(str)) {
                                        return true;
                                    }
                                    break;
                                case 4:
                                    if ("668833".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        }
                        break;
                    case 34:
                        if (i == 5) {
                            if ("87654321".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case 37:
                        if (i != 1) {
                            switch (i) {
                                case 3:
                                case 4:
                                    if ("333111".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        } else if ("333111".equals(str)) {
                            return true;
                        }
                        break;
                    case 38:
                    case 296:
                        if (i != 1) {
                            switch (i) {
                                case 3:
                                case 4:
                                    if ("8888".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        } else if ("8888".equals(str)) {
                            return true;
                        }
                        break;
                    case 48:
                        if (i == 1) {
                            if ("135790".equals(str)) {
                                return true;
                            }
                        } else {
                            switch (i) {
                                case 3:
                                case 4:
                                    if ("2468".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        }
                        break;
                    case 101:
                        if (i == 3) {
                            if ("110601".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case R.styleable.AppCompatTheme_windowFixedWidthMinor /* 117 */:
                        if (i == 5) {
                            if ("33603360".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case 121:
                        if (i == 1) {
                            if ("126".equals(str)) {
                                return true;
                            }
                        } else {
                            switch (i) {
                                case 3:
                                    if ("00000000".equals(str)) {
                                        return true;
                                    }
                                    break;
                                case 4:
                                    if ("126".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        }
                        break;
                    case 259:
                        if (i == 5) {
                            if ("12345678".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case 264:
                        if (i != 1) {
                            switch (i) {
                                case 3:
                                    if ("0120".equals(str) && a().startsWith("KBT11")) {
                                        return true;
                                    }
                                    if (!a().startsWith("KBT11") && str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                                case 4:
                                    if ("72052010".equals(str) && a().startsWith("KBT11")) {
                                        return true;
                                    }
                                    if (!a().startsWith("KBT11") && str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        } else {
                            if ("998877".equals(str) && a().startsWith("KBT11")) {
                                return true;
                            }
                            if (!a().startsWith("KBT11") && str2.equals(str)) {
                                return true;
                            }
                        }
                        break;
                    default:
                        if (str2.equals(str)) {
                            return true;
                        }
                        break;
                }
            } else if (str2.equals(str)) {
                return true;
            }
        } catch (Exception unused2) {
        }
        return false;
    }

    public static String a() throws Throwable {
        String str = "";
        try {
            String str2 = SystemProperties.get("ro.tw.version");
            if (str2 != null && str2.length() > 0) {
                str = str2;
            }
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/system/etc/version"));
                try {
                    String line = bufferedReader2.readLine();
                    if (line != null && line.length() > 0) {
                        str = line;
                    }
                    bufferedReader2.close();
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
        }
        return TextUtils.isEmpty(str) ? str : str.substring(str.lastIndexOf("_") + 1);
    }

    public static ProgressDialog a(Context context, String str) {
        ProgressDialog progressDialog = new ProgressDialog(context, 3);
        progressDialog.setMessage(str);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setOnCancelListener(null);
        progressDialog.show();
        return progressDialog;
    }
}
