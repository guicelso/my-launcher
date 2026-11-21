package android.support.v4.os;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
class f implements Runnable {
    final int mResultCode;
    final Bundle mResultData;
    final /* synthetic */ ResultReceiver n;

    f(ResultReceiver resultReceiver, int i, Bundle bundle) {
        this.n = resultReceiver;
        this.mResultCode = i;
        this.mResultData = bundle;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.n.onReceiveResult(this.mResultCode, this.mResultData);
    }
}
