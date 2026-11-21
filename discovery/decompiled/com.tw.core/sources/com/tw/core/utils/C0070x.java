package com.tw.core.utils;

import android.util.Base64;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* JADX INFO: renamed from: com.tw.core.utils.x, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0070x {
    private static PublicKey am;

    public static String bZ(String str) {
        try {
            if (am == null) {
                am = cd();
            }
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
            cipher.init(2, am);
            cipher.update(cb(str));
            String str2 = new String(cipher.doFinal(), "UTF-8");
            B.ci("RsaUtil", "公钥解密后的数据：%s", str2);
            return str2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String ca(String str) {
        try {
            if (am == null) {
                am = cd();
            }
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
            cipher.init(1, am);
            cipher.update(str.getBytes("UTF-8"));
            String strCc = cc(cipher.doFinal());
            B.ci("RsaUtil", "源数据：%s", str);
            B.ci("RsaUtil", "公钥加密后的数据：%s", strCc);
            return strCc;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private static byte[] cb(String str) {
        return Base64.decode(str, 2);
    }

    private static String cc(byte[] bArr) {
        return new String(Base64.encode(bArr, 2));
    }

    private static PublicKey cd() {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(cb("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2geKn1rer7H4Xjyl8lAGACer+w4NrHBhzNbH2wVLh9Ghmb6Z3wK2CVS7jLvMkCzwLTzpI08foJtG2UObvPwL3w+a5zGUFJayaf7BV+ZYnuUswyrxfMwy753q+da+AnVdkW9ldjeUTomuGUA2+x8wBxlfS8ZRzqlzFgCTwqu4K9wIDAQAB")));
    }
}
