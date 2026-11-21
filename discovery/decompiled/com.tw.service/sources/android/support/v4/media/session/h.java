package android.support.v4.media.session;

import android.os.Build;
import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
public abstract class h implements IBinder.DeathRecipient {
    b oe;

    public h() {
        int i = Build.VERSION.SDK_INT;
        new l(new f(this));
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
    }
}
