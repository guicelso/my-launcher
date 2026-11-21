package d;

import android.content.ComponentName;
import android.content.Intent;
import com.tw.tdservice.app.CoreApplication;

/* JADX INFO: loaded from: classes.dex */
public final class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int f35a = 0;

    public static ComponentName a() {
        Intent intent = new Intent();
        intent.setAction("com.tw.core.model.MainService");
        intent.setPackage("com.tw.core");
        return CoreApplication.f30a.startService(intent);
    }
}
