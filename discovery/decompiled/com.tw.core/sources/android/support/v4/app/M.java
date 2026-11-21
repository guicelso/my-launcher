package android.support.v4.app;

import android.app.Notification;
import android.app.RemoteInput;

/* JADX INFO: loaded from: classes.dex */
class M {
    M() {
    }

    public static void rg(Notification.Builder builder, v vVar) {
        Notification.Action.Builder builder2 = new Notification.Action.Builder(vVar.qM(), vVar.qO(), vVar.qK());
        if (vVar.qN() != null) {
            for (RemoteInput remoteInput : I.rf(vVar.qN())) {
                builder2.addRemoteInput(remoteInput);
            }
        }
        if (vVar.qL() != null) {
            builder2.addExtras(vVar.qL());
        }
        builder.addAction(builder2.build());
    }
}
