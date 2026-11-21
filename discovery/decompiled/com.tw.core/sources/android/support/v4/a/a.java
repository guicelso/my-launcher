package android.support.v4.a;

import android.content.Context;
import android.os.Process;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static int qs(Context context, String str) {
        if (str == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(str, Process.myPid(), Process.myUid());
    }
}
