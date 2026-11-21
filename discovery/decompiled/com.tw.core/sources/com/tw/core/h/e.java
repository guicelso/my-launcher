package com.tw.core.h;

import android.text.TextUtils;
import android.util.Base64;
import com.tw.core.utils.F;

/* JADX INFO: loaded from: classes.dex */
public class e {
    public final String jk;
    public final String jl;
    public final String jm;
    public final long jn;

    private e(String str, String str2, long j, String str3) {
        this.jm = str;
        this.jl = str2;
        this.jn = j;
        this.jk = str3;
    }

    public static e oc(String str) {
        try {
            if (TextUtils.isEmpty(str) || str.length() <= 22) {
                return null;
            }
            return new e(str.substring(0, 4), str.substring(4, 14), 1000 * ((long) F.cP(F.cQ(str.substring(14, 22)), 4)), new String(Base64.decode(str.substring(22), 8), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return "EmqMessage{msgId='" + this.jm + "', taskId='" + this.jl + "', msgTime=" + this.jn + ", content='" + this.jk + "'}";
    }
}
