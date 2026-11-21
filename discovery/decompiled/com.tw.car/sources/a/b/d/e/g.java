package a.b.d.e;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
public class g implements Handler.Callback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ k f104a;

    public g(k kVar) {
        this.f104a = kVar;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            this.f104a.a();
            return true;
        }
        if (i != 1) {
            return true;
        }
        this.f104a.a((Runnable) message.obj);
        return true;
    }
}
