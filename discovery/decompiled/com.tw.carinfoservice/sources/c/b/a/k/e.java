package c.b.a.k;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static Handler f433a;

    public static class a extends Handler {
        public a() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1) {
                Pair pair = (Pair) message.obj;
                ((Runnable) pair.first).run();
                Object obj = pair.second;
                if (obj != null) {
                    ((CountDownLatch) obj).countDown();
                }
            }
        }
    }
}
