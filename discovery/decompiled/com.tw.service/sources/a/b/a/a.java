package a.b.a;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static void a(Context context, String str, boolean z) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.getPackageInfo(str, 0) == null) {
                return;
            }
            if (z) {
                packageManager.setApplicationEnabledSetting(str, 1, 0);
            } else {
                packageManager.setApplicationEnabledSetting(str, 2, 0);
            }
        } catch (Exception e) {
            StringBuilder sbE = b.a.a.a.a.e("changeAppState:");
            sbE.append(e.getMessage());
            Log.e("AppUtils", sbE.toString());
        }
    }

    public static boolean a(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject(str);
        return TextUtils.equals(jSONObject.optString("softwareType"), str2) && TextUtils.equals(jSONObject.optString("cid"), str3);
    }

    public static void buildShortClassTag(Object obj, StringBuilder sb) {
        String hexString;
        int iLastIndexOf;
        if (obj == null) {
            hexString = "null";
        } else {
            String simpleName = obj.getClass().getSimpleName();
            if (simpleName.length() <= 0 && (iLastIndexOf = (simpleName = obj.getClass().getName()).lastIndexOf(46)) > 0) {
                simpleName = simpleName.substring(iLastIndexOf + 1);
            }
            sb.append(simpleName);
            sb.append('{');
            hexString = Integer.toHexString(System.identityHashCode(obj));
        }
        sb.append(hexString);
    }

    public static Object checkNotNull(Object obj, Object obj2) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(String.valueOf(obj2));
    }

    /* JADX WARN: Code duplicated, block: B:18:0x004c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:20:0x004f  */
    public static String decrypt(String str) {
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArrDoFinal;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmyWXcwD/iJ4mJyWgKTEoIhDCPnFRV4wTNssNnd1RYbJbJ9264Jua9GgqWe3hC2qz3K3xk6CrqMBiwphIBkOLzVAmFGpskKQgndqZviXNJ2tNEYH0MoO9jcQo1DsIQHoYFyYoBdvh4WqECdghLTBBoXU6SOnSMIlVk+xIw1uKpXQIDAQAB", 2))));
            byte[] bArrDecode = Base64.decode(str, 2);
            int length = bArrDecode.length;
            byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 0;
            while (true) {
                int i2 = length - i;
                if (i2 <= 0) {
                    break;
                }
                if (i2 >= 1024) {
                    try {
                        bArrDoFinal = cipher.doFinal(bArrDecode, i, 1024);
                        i += 1024;
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        if (byteArrayOutputStream == null) {
                            return "";
                        }
                        return byteArrayOutputStream.toString();
                    }
                } else {
                    bArrDoFinal = cipher.doFinal(bArrDecode, i, i2);
                    i = length;
                }
                byteArrayOutputStream.write(bArrDoFinal);
            }
        } catch (Exception e2) {
            e = e2;
            byteArrayOutputStream = null;
        }
        if (byteArrayOutputStream == null) {
            return "";
        }
        return byteArrayOutputStream.toString();
    }

    public static int getAbsoluteGravity(int i, int i2) {
        int i3 = Build.VERSION.SDK_INT;
        return Gravity.getAbsoluteGravity(i, i2);
    }

    public static boolean x() {
        try {
            return (new File("/twdataconfig").exists() ? new File("/twdataconfig/ReverseType") : new File("/sdcard/.Tcfg/ReverseType")).exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
