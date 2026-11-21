package android.support.v4.app;

import android.transition.Transition;
import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class da implements Transition.TransitionListener {
    final /* synthetic */ ArrayList Bd;
    final /* synthetic */ View Wd;

    da(ga gaVar, View view, ArrayList arrayList) {
        this.Wd = view;
        this.Bd = arrayList;
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionCancel(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionEnd(Transition transition) {
        transition.removeListener(this);
        this.Wd.setVisibility(8);
        int size = this.Bd.size();
        for (int i = 0; i < size; i++) {
            ((View) this.Bd.get(i)).setVisibility(0);
        }
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionPause(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionResume(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionStart(Transition transition) {
    }
}
