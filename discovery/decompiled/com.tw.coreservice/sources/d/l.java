package d;

import android.os.Environment;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static boolean f55a = false;

    static {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            try {
                f55a = new JSONObject(d.d(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DoFun/core")).optBoolean("logEnable");
            } catch (JSONException unused) {
            }
        }
    }
}
