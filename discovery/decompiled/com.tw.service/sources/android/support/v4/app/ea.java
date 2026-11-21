package android.support.v4.app;

import android.transition.Transition;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class ea implements Transition.TransitionListener {
    final /* synthetic */ ArrayList Bd;
    final /* synthetic */ Object Cd;
    final /* synthetic */ ArrayList Gd;
    final /* synthetic */ ArrayList Hd;
    final /* synthetic */ Object Id;
    final /* synthetic */ Object Xd;
    final /* synthetic */ ga n;

    ea(ga gaVar, Object obj, ArrayList arrayList, Object obj2, ArrayList arrayList2, Object obj3, ArrayList arrayList3) {
        this.n = gaVar;
        this.Cd = obj;
        this.Hd = arrayList;
        this.Id = obj2;
        this.Bd = arrayList2;
        this.Xd = obj3;
        this.Gd = arrayList3;
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionCancel(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionEnd(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionPause(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionResume(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionStart(Transition transition) {
        Object obj = this.Cd;
        if (obj != null) {
            this.n.a(obj, this.Hd, (ArrayList) null);
        }
        Object obj2 = this.Id;
        if (obj2 != null) {
            this.n.a(obj2, this.Bd, (ArrayList) null);
        }
        Object obj3 = this.Xd;
        if (obj3 != null) {
            this.n.a(obj3, this.Gd, (ArrayList) null);
        }
    }
}
