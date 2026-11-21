package android.support.v4.app;

import android.graphics.Rect;
import android.transition.Transition;

/* JADX INFO: loaded from: classes.dex */
class ca extends Transition.EpicenterCallback {
    final /* synthetic */ Rect Nd;

    ca(ga gaVar, Rect rect) {
        this.Nd = rect;
    }

    @Override // android.transition.Transition.EpicenterCallback
    public Rect onGetEpicenter(Transition transition) {
        return this.Nd;
    }
}
