package android.support.v4.media;

import android.os.Bundle;
import android.os.ResultReceiver;

/* JADX INFO: loaded from: classes.dex */
class MediaController2ImplLegacy$3 extends ResultReceiver {
    final /* synthetic */ j n;

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, Bundle bundle) {
        if (j.F().isAlive()) {
            if (i == -1) {
                j.G().execute(new g(this));
                throw null;
            }
            if (i == 0) {
                throw null;
            }
        }
    }
}
