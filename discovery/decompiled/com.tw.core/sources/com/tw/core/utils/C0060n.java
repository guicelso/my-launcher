package com.tw.core.utils;

import android.text.TextUtils;
import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: renamed from: com.tw.core.utils.n, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0060n {
    public static String aL() {
        String strGr = com.tw.core.b.f.gr();
        String strSubstring = strGr != null ? strGr.substring(strGr.length() - 16) : System.currentTimeMillis() + "000";
        B.ci("rdc %s", strSubstring, new Object[0]);
        return strSubstring;
    }

    public static String aM(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = str.getBytes("utf-8");
            cipher.init(1, secretKeySpec, aO());
            return new String(Base64.encode(cipher.doFinal(bytes), 2));
        } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            C0065s.bK("AES加密报错:{}", e.getMessage());
            return null;
        }
    }

    public static String aN(String str, String str2) {
        byte[] bArrDecode = Base64.decode(str, 2);
        try {
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, secretKeySpec, aO());
            return new String(cipher.doFinal(bArrDecode), "utf-8");
        } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            C0065s.bK("AES解密报错:{}", e.getMessage());
            return null;
        }
    }

    private static IvParameterSpec aO() {
        return new IvParameterSpec("dofunsaascbciv16".getBytes());
    }
}
