package android.support.v4.media;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
@TargetApi(16)
class j implements f {
    static final Bundle de;
    private final HandlerThread ce;
    private final Executor mCallbackExecutor;

    static {
        Log.isLoggable("MC2ImplLegacy", 3);
        de = new Bundle();
        de.putBoolean("android.support.v4.media.root_default_root", true);
    }

    static /* synthetic */ void E() {
    }

    static /* synthetic */ HandlerThread F() {
        j jVar = null;
        return jVar.ce;
    }

    static /* synthetic */ Executor G() {
        j jVar = null;
        return jVar.mCallbackExecutor;
    }
}
