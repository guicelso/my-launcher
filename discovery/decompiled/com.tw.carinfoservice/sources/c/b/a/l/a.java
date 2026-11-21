package c.b.a.l;

import android.os.Environment;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int f447a = 0;

    static {
        Environment.getExternalStorageDirectory().getPath();
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0025  */
    /* JADX WARN: Code duplicated, block: B:14:0x0028  */
    /* JADX WARN: Code duplicated, block: B:15:0x002b  */
    /* JADX WARN: Code duplicated, block: B:16:0x002e  */
    /* JADX WARN: Code duplicated, block: B:17:0x0031  */
    /* JADX WARN: Code duplicated, block: B:18:0x0034  */
    public static int a(String str) {
        int i;
        if (!str.startsWith("0x")) {
            throw new NumberFormatException();
        }
        int i2 = 0;
        for (int i3 = 2; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            switch (cCharAt) {
                case '0':
                    i = 0;
                    break;
                case '1':
                    i = 1;
                    break;
                case '2':
                    i = 2;
                    break;
                case '3':
                    i = 3;
                    break;
                case '4':
                    i = 4;
                    break;
                case '5':
                    i = 5;
                    break;
                case '6':
                    i = 6;
                    break;
                case '7':
                    i = 7;
                    break;
                case '8':
                    i = 8;
                    break;
                case '9':
                    i = 9;
                    break;
                default:
                    switch (cCharAt) {
                        case 'A':
                            i = 10;
                            break;
                        case 'B':
                            i = 11;
                            break;
                        case 'C':
                            i = 12;
                            break;
                        case 'D':
                            i = 13;
                            break;
                        case 'E':
                            i = 14;
                            break;
                        case 'F':
                            i = 15;
                            break;
                        default:
                            switch (cCharAt) {
                                case 'a':
                                    i = 10;
                                    break;
                                case 'b':
                                    i = 11;
                                    break;
                                case 'c':
                                    i = 12;
                                    break;
                                case 'd':
                                    i = 13;
                                    break;
                                case 'e':
                                    i = 14;
                                    break;
                                case 'f':
                                    i = 15;
                                    break;
                                default:
                                    throw new NumberFormatException();
                            }
                            break;
                    }
                    break;
            }
            i2 = (i2 * 16) + i;
        }
        return i2;
    }

    public static int[] b(float[] fArr) {
        int[] iArr = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            try {
                iArr[i] = (int) fArr[i];
            } catch (Exception unused) {
                return new int[0];
            }
        }
        return iArr;
    }

    public static byte[] c(c.b.a.e.a aVar, int i) {
        byte[] bArr = new byte[aVar.B.length];
        int i2 = 0;
        if (aVar.A == null) {
            while (true) {
                int[] iArr = aVar.C;
                if (i2 >= iArr.length) {
                    break;
                }
                if (i == 0) {
                    bArr[i2] = (byte) iArr[i2];
                } else {
                    bArr[i2] = (byte) aVar.D[i2];
                }
                i2++;
            }
        } else {
            while (true) {
                int[] iArr2 = aVar.A;
                if (i2 >= iArr2.length) {
                    break;
                }
                bArr[i2] = (byte) iArr2[i2];
                i2++;
            }
        }
        return bArr;
    }

    public static String d() {
        String str = SystemProperties.get("ro.board.platform");
        if ("sp9853i".equals(str)) {
            return "/dev/ttyS0";
        }
        if ("sc8830".equals(str)) {
            return "/dev/ttyS4";
        }
        if (!"t3".equals(str) && !"t8".equals(str)) {
            if ("t7".equals(str)) {
                return "/dev/ttyS5";
            }
            if ("mercury".equals(str)) {
                return "/dev/ttyS4";
            }
            if ("ums512".equals(str)) {
                if (b.e != 307) {
                    return "/dev/ttySE3";
                }
            } else {
                if ("t7".equals(str)) {
                    return "/dev/ttyS8";
                }
                if (!"sp9863a".equals(str)) {
                    return "ceres".equals(str) ? "/dev/ttyS7" : "/dev/ttyS2";
                }
                if (b.e != 307) {
                    return "/dev/ttyS3";
                }
            }
        }
        return "/dev/ttyS1";
    }

    public static int[] e(String str) {
        String strSubstring = str.substring(1, str.length() - 1);
        if (TextUtils.isEmpty(strSubstring) || strSubstring == null) {
            return new int[0];
        }
        if (!strSubstring.contains(";")) {
            Log.e("LS123", "getStringToArray: " + strSubstring);
            return new int[]{Integer.parseInt(strSubstring)};
        }
        String[] strArrSplit = strSubstring.split(";");
        int length = strArrSplit.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            if (strArrSplit[i].contains("0x")) {
                iArr[i] = a(strArrSplit[i]);
            } else {
                iArr[i] = Integer.parseInt(strArrSplit[i]);
            }
        }
        return iArr;
    }

    public static int[] f(String str) {
        String strSubstring = str.substring(1, str.length() - 1);
        if (TextUtils.isEmpty(strSubstring) || strSubstring == null) {
            return new int[0];
        }
        if (!strSubstring.contains(",")) {
            return strSubstring.contains("0x") ? new int[]{a(strSubstring)} : new int[]{Integer.parseInt(strSubstring)};
        }
        String[] strArrSplit = strSubstring.split(",");
        int length = strArrSplit.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            if (strArrSplit[i].contains("0x")) {
                iArr[i] = a(strArrSplit[i]);
            } else {
                iArr[i] = Integer.parseInt(strArrSplit[i]);
            }
        }
        return iArr;
    }

    public static int[] g(String[] strArr) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                iArr[i] = Integer.parseInt(strArr[i]);
            } catch (Exception unused) {
                return new int[0];
            }
        }
        return iArr;
    }

    public static int[] h(String[] strArr) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                iArr[i] = a(strArr[i]) & 255;
            } catch (Exception unused) {
                return new int[0];
            }
        }
        return iArr;
    }

    public static byte[] i(int i) {
        int i2 = 0;
        int i3 = i;
        do {
            i3 /= 256;
            i2++;
        } while (i3 > 0);
        byte[] bArr = new byte[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            if (i4 == 0) {
                bArr[0] = (byte) (i % 256);
            } else {
                int i5 = i;
                for (int i6 = 0; i6 < i4; i6++) {
                    i5 /= 256;
                }
                bArr[i4] = (byte) i5;
            }
        }
        return bArr;
    }

    public static byte[] j(String str) {
        String[] strArr = new String[str.length()];
        int i = 0;
        String str2 = "";
        for (int i2 = 0; i2 < str.length(); i2++) {
            strArr[i2] = Integer.toHexString(str.charAt(i2) & 65535);
            str2 = str2 + "\\u" + strArr[i2];
        }
        String[] strArrSplit = str2.split("\\\\u");
        int i3 = 0;
        for (int i4 = 1; i4 < strArrSplit.length; i4++) {
            Integer.parseInt(strArrSplit[i4], 16);
            i3 += 2;
        }
        byte[] bArr = new byte[i3];
        for (int i5 = 1; i5 < strArrSplit.length; i5++) {
            int i6 = Integer.parseInt(strArrSplit[i5], 16);
            bArr[i] = (byte) (i6 / 256);
            bArr[i + 1] = (byte) (i6 % 256);
            i += 2;
        }
        return bArr;
    }
}
