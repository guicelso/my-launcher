package com.tw.core.utils;

import android.text.TextUtils;
import android.util.Base64;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class R {
    public static String dN(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(2, new SecretKeySpec(str.getBytes("UTF-8"), "RC4"));
            byte[] bArrDecode = Base64.decode(str2, 2);
            return new String(cipher.update(Arrays.copyOfRange(bArrDecode, 16, bArrDecode.length)), "UTF-8");
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
