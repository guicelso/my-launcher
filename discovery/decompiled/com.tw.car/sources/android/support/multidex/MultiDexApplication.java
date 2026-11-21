package android.support.multidex;

import a.b.c.a;
import android.app.Application;
import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class MultiDexApplication extends Application {
    @Override // android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        a.b(this);
    }
}
