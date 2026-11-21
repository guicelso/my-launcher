package android.arch.lifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentCallbacks2;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class n extends Fragment {
    public static void a(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.findFragmentByTag("android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
            fragmentManager.beginTransaction().add(new n(), "android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
            fragmentManager.executePendingTransactions();
        }
    }

    private void c(Lifecycle$Event lifecycle$Event) {
        ComponentCallbacks2 activity = getActivity();
        if (activity instanceof h) {
            ((h) activity).d().b(lifecycle$Event);
        } else if (activity instanceof e) {
            c cVarD = ((e) activity).d();
            if (cVarD instanceof g) {
                ((g) cVarD).b(lifecycle$Event);
            }
        }
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        c(Lifecycle$Event.ON_CREATE);
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        c(Lifecycle$Event.ON_DESTROY);
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        c(Lifecycle$Event.ON_PAUSE);
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        c(Lifecycle$Event.ON_RESUME);
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        c(Lifecycle$Event.ON_START);
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        c(Lifecycle$Event.ON_STOP);
    }
}
