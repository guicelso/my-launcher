package android.support.v4.os;

import android.os.Bundle;
import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
class e extends b {
    final /* synthetic */ ResultReceiver n;

    e(ResultReceiver resultReceiver) {
        this.n = resultReceiver;
    }

    public void send(int i, Bundle bundle) {
        ResultReceiver resultReceiver = this.n;
        Handler handler = resultReceiver.mHandler;
        if (handler != null) {
            handler.post(new f(resultReceiver, i, bundle));
        } else {
            resultReceiver.onReceiveResult(i, bundle);
        }
    }
}
