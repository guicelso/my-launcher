package android.support.v4.media.session;

import android.os.RemoteException;
import android.util.Log;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class j {
    private final List pe;
    private HashMap qe;
    private final MediaSessionCompat$Token re;

    /* JADX INFO: Access modifiers changed from: private */
    public void bb() {
        if (this.re.I() == null) {
            return;
        }
        synchronized (this.pe) {
            for (h hVar : this.pe) {
                i iVar = new i(hVar);
                this.qe.put(hVar, iVar);
                hVar.oe = iVar;
                try {
                    ((c) this.re.I()).a(iVar);
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                    this.pe.clear();
                }
            }
            this.pe.clear();
        }
    }
}
