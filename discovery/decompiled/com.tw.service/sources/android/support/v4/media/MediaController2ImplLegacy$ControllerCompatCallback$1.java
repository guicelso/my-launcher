package android.support.v4.media;

import android.os.Bundle;
import android.os.ResultReceiver;

/* JADX INFO: loaded from: classes.dex */
class MediaController2ImplLegacy$ControllerCompatCallback$1 extends ResultReceiver {
    final /* synthetic */ i Rc;

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, Bundle bundle) {
        i iVar = null;
        j jVar = iVar.n;
        if (j.F().isAlive()) {
            if (i != -1) {
                if (i != 0) {
                    return;
                }
                j jVar2 = iVar.n;
                throw null;
            }
            j jVar3 = iVar.n;
            j.G().execute(new h(this));
            j jVar4 = iVar.n;
            throw null;
        }
    }
}
