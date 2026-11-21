package d;

import android.app.ActivityManager;
import com.tw.tdservice.app.CoreApplication;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class j {
    public static Set<String> a() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) CoreApplication.f30a.getSystemService("activity")).getRunningAppProcesses();
        HashSet hashSet = new HashSet();
        if (runningAppProcesses != null) {
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (it.hasNext()) {
                Collections.addAll(hashSet, it.next().pkgList);
            }
        }
        return hashSet;
    }
}
