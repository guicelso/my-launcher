package d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tw.tdservice.app.CoreApplication;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class h {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static volatile h f49d;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public List<b> f50a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f51b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public a f52c = new a();

    public class a extends BroadcastReceiver {
        public a() {
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [java.util.ArrayList, java.util.List<d.h$b>] */
        /* JADX WARN: Type inference failed for: r2v0, types: [java.util.ArrayList, java.util.List<d.h$b>] */
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                h hVar = h.this;
                boolean zA = i.a(CoreApplication.f30a);
                ?? r0 = hVar.f50a;
                if (r0 != 0) {
                    int size = r0.size();
                    for (int i = 0; i < size; i++) {
                        b bVar = (b) hVar.f50a.get(i);
                        if (bVar != null) {
                            bVar.a(zA);
                        }
                    }
                }
            }
        }
    }

    public interface b {
        void a(boolean z);
    }

    public static h b() {
        if (f49d == null) {
            synchronized (h.class) {
                if (f49d == null) {
                    f49d = new h();
                }
            }
        }
        return f49d;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.ArrayList, java.util.List<d.h$b>] */
    public final void a(b bVar) {
        if (bVar != null) {
            if (this.f50a == null) {
                this.f50a = new ArrayList();
            }
            this.f50a.add(bVar);
            if (this.f51b) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            CoreApplication.f30a.registerReceiver(this.f52c, intentFilter);
            this.f51b = true;
        }
    }
}
