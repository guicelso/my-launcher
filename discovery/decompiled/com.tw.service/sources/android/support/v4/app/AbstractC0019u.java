package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

/* JADX INFO: renamed from: android.support.v4.app.u, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0019u extends AbstractC0017s {
    private final Activity mActivity;
    private final Context mContext;
    final O mFragmentManager;
    private final Handler mHandler;

    AbstractC0019u(r rVar) {
        Handler handler = rVar.mHandler;
        this.mFragmentManager = new O();
        this.mActivity = rVar;
        a.b.a.a.checkNotNull(rVar, "context == null");
        this.mContext = rVar;
        a.b.a.a.checkNotNull(handler, "handler == null");
        this.mHandler = handler;
    }

    Activity getActivity() {
        return this.mActivity;
    }

    Context getContext() {
        return this.mContext;
    }

    Handler getHandler() {
        return this.mHandler;
    }
}
