package android.support.v4.media.session;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.C0006g;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver extends ResultReceiver {
    private WeakReference Uc;

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, Bundle bundle) {
        j jVar = (j) this.Uc.get();
        if (jVar == null || bundle == null) {
            return;
        }
        jVar.re.a(d.asInterface(C0006g.a(bundle, "android.support.v4.media.session.EXTRA_BINDER")));
        jVar.re.a(android.support.v4.media.p.fromBundle(bundle.getBundle("android.support.v4.media.session.SESSION_TOKEN2")));
        jVar.bb();
    }
}
