package android.support.v4.app;

import android.graphics.Rect;
import android.transition.Transition;

/* JADX INFO: loaded from: classes.dex */
class fa extends Transition.EpicenterCallback {
    final /* synthetic */ Rect Nd;

    fa(ga gaVar, Rect rect) {
        this.Nd = rect;
    }

    @Override // android.transition.Transition.EpicenterCallback
    public Rect onGetEpicenter(Transition transition) {
        Rect rect = this.Nd;
        if (rect == null || rect.isEmpty()) {
            return null;
        }
        return this.Nd;
    }
}
