package android.support.design.widget;

import android.support.v4.view.r;
import android.view.View;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
class h implements Comparator {
    h() {
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        float fI = r.i((View) obj);
        float fI2 = r.i((View) obj2);
        if (fI > fI2) {
            return -1;
        }
        return fI < fI2 ? 1 : 0;
    }
}
