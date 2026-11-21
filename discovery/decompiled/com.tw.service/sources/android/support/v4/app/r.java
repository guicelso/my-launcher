package android.support.v4.app;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle$State;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class r extends Activity implements android.arch.lifecycle.s, InterfaceC0000a, InterfaceC0001b, android.arch.lifecycle.e {
    boolean mCreated;
    final C0018t mFragments;
    final Handler mHandler;
    boolean mResumed;
    boolean mRetaining;
    boolean mStopped;
    private android.arch.lifecycle.r oa;
    private android.arch.lifecycle.g tc;
    boolean uc;
    boolean vc;
    boolean wc;
    int xc;
    a.b.c.b.p yc;

    public r() {
        new a.b.c.b.o();
        this.tc = new android.arch.lifecycle.g(this);
        this.mHandler = new HandlerC0014o(this);
        this.mFragments = C0018t.a(new C0015p(this));
        this.mStopped = true;
        this.uc = true;
    }

    private static boolean a(AbstractC0023y abstractC0023y, Lifecycle$State lifecycle$State) {
        boolean zA = false;
        for (ComponentCallbacksC0013n componentCallbacksC0013n : abstractC0023y.getFragments()) {
            if (componentCallbacksC0013n != null) {
                if (componentCallbacksC0013n.tc.getCurrentState().compareTo(Lifecycle$State.STARTED) >= 0) {
                    componentCallbacksC0013n.tc.a(lifecycle$State);
                    zA = true;
                }
                O o = componentCallbacksC0013n.mChildFragmentManager;
                if (o != null) {
                    zA |= a(o, lifecycle$State);
                }
            }
        }
        return zA;
    }

    static void e(int i) {
        if ((i & (-65536)) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
    }

    @Override // android.arch.lifecycle.s
    public android.arch.lifecycle.r a() {
        if (getApplication() == null) {
            throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        }
        if (this.oa == null) {
            this.oa = new android.arch.lifecycle.r();
        }
        return this.oa;
    }

    public void a(ComponentCallbacksC0013n componentCallbacksC0013n) {
    }

    @Override // android.arch.lifecycle.e
    public android.arch.lifecycle.c d() {
        return this.tc;
    }

    @Override // android.app.Activity
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mCreated=");
        printWriter.print(this.mCreated);
        printWriter.print(" mResumed=");
        printWriter.print(this.mResumed);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mReallyStopped=");
        printWriter.println(this.uc);
        la.g(this).dump(str2, fileDescriptor, printWriter, strArr);
        this.mFragments.B().dump(str, fileDescriptor, printWriter, strArr);
    }

    void e(boolean z) {
        if (this.uc) {
            return;
        }
        this.uc = true;
        this.mRetaining = z;
        this.mHandler.removeMessages(1);
        this.mFragments.A();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        this.mFragments.noteStateNotSaved();
        int i3 = i >> 16;
        if (i3 == 0) {
            C0002c.D();
            super.onActivityResult(i, i2, intent);
            return;
        }
        int i4 = i3 - 1;
        String str = (String) this.yc.get(i4);
        this.yc.remove(i4);
        if (str == null) {
            Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
        } else if (this.mFragments.findFragmentByWho(str) == null) {
            Log.w("FragmentActivity", "Activity result no fragment exists for who: " + str);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        AbstractC0023y abstractC0023yB = this.mFragments.B();
        boolean zIsStateSaved = abstractC0023yB.isStateSaved();
        if (zIsStateSaved) {
            int i = Build.VERSION.SDK_INT;
        }
        if (zIsStateSaved || !abstractC0023yB.popBackStackImmediate()) {
            super.onBackPressed();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mFragments.noteStateNotSaved();
        this.mFragments.dispatchConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        this.mFragments.d(null);
        super.onCreate(bundle);
        android.arch.lifecycle.n.a(this);
        C0016q c0016q = (C0016q) getLastNonConfigurationInstance();
        if (c0016q != null) {
            this.oa = c0016q.qd;
        }
        if (bundle != null) {
            this.mFragments.a(bundle.getParcelable("android:support:fragments"), c0016q != null ? c0016q.fragments : null);
            if (bundle.containsKey("android:support:next_request_index")) {
                this.xc = bundle.getInt("android:support:next_request_index");
                int[] intArray = bundle.getIntArray("android:support:request_indicies");
                String[] stringArray = bundle.getStringArray("android:support:request_fragment_who");
                if (intArray == null || stringArray == null || intArray.length != stringArray.length) {
                    Log.w("FragmentActivity", "Invalid requestCode mapping in savedInstanceState.");
                } else {
                    this.yc = new a.b.c.b.p(intArray.length);
                    for (int i = 0; i < intArray.length; i++) {
                        this.yc.put(intArray[i], stringArray[i]);
                    }
                }
            }
        }
        if (this.yc == null) {
            this.yc = new a.b.c.b.p(10);
            this.xc = 0;
        }
        this.mFragments.dispatchCreate();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0) {
            return super.onCreatePanelMenu(i, menu);
        }
        return this.mFragments.dispatchCreateOptionsMenu(menu, getMenuInflater()) | super.onCreatePanelMenu(i, menu);
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View viewOnCreateView = this.mFragments.onCreateView(view, str, context, attributeSet);
        return viewOnCreateView == null ? super.onCreateView(view, str, context, attributeSet) : viewOnCreateView;
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        e(false);
        android.arch.lifecycle.r rVar = this.oa;
        if (rVar != null && !this.mRetaining) {
            rVar.clear();
        }
        this.mFragments.dispatchDestroy();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        this.mFragments.dispatchLowMemory();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        if (i == 0) {
            return this.mFragments.dispatchOptionsItemSelected(menuItem);
        }
        if (i != 6) {
            return false;
        }
        return this.mFragments.dispatchContextItemSelected(menuItem);
    }

    @Override // android.app.Activity
    public void onMultiWindowModeChanged(boolean z) {
        this.mFragments.dispatchMultiWindowModeChanged(z);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.mFragments.noteStateNotSaved();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
        if (i == 0) {
            this.mFragments.dispatchOptionsMenuClosed(menu);
        }
        super.onPanelClosed(i, menu);
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mResumed = false;
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
            this.mFragments.dispatchResume();
        }
        this.mFragments.dispatchPause();
    }

    @Override // android.app.Activity
    public void onPictureInPictureModeChanged(boolean z) {
        this.mFragments.dispatchPictureInPictureModeChanged(z);
    }

    @Override // android.app.Activity
    protected void onPostResume() {
        super.onPostResume();
        this.mHandler.removeMessages(2);
        this.mFragments.dispatchResume();
        this.mFragments.execPendingActions();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onPreparePanel(int i, View view, Menu menu) {
        if (i != 0 || menu == null) {
            return super.onPreparePanel(i, view, menu);
        }
        return this.mFragments.dispatchPrepareOptionsMenu(menu) | super.onPreparePanel(0, view, menu);
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        this.mFragments.noteStateNotSaved();
        int i2 = (i >> 16) & 65535;
        if (i2 != 0) {
            int i3 = i2 - 1;
            String str = (String) this.yc.get(i3);
            this.yc.remove(i3);
            if (str == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
            } else if (this.mFragments.findFragmentByWho(str) == null) {
                Log.w("FragmentActivity", "Activity result no fragment exists for who: " + str);
            }
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mHandler.sendEmptyMessage(2);
        this.mResumed = true;
        this.mFragments.execPendingActions();
    }

    @Override // android.app.Activity
    public final Object onRetainNonConfigurationInstance() {
        if (this.mStopped) {
            e(true);
        }
        P pRetainNestedNonConfig = this.mFragments.retainNestedNonConfig();
        if (pRetainNestedNonConfig == null && this.oa == null) {
            return null;
        }
        C0016q c0016q = new C0016q();
        c0016q.qd = this.oa;
        c0016q.fragments = pRetainNestedNonConfig;
        return c0016q;
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        this.tc.a(Lifecycle$State.CREATED);
        super.onSaveInstanceState(bundle);
        while (a(this.mFragments.B(), Lifecycle$State.CREATED)) {
        }
        Parcelable parcelableSaveAllState = this.mFragments.saveAllState();
        if (parcelableSaveAllState != null) {
            bundle.putParcelable("android:support:fragments", parcelableSaveAllState);
        }
        if (this.yc.size() > 0) {
            bundle.putInt("android:support:next_request_index", this.xc);
            int[] iArr = new int[this.yc.size()];
            String[] strArr = new String[this.yc.size()];
            for (int i = 0; i < this.yc.size(); i++) {
                iArr[i] = this.yc.keyAt(i);
                strArr[i] = (String) this.yc.valueAt(i);
            }
            bundle.putIntArray("android:support:request_indicies", iArr);
            bundle.putStringArray("android:support:request_fragment_who", strArr);
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        this.mStopped = false;
        this.uc = false;
        this.mHandler.removeMessages(1);
        if (!this.mCreated) {
            this.mCreated = true;
            this.mFragments.dispatchActivityCreated();
        }
        this.mFragments.noteStateNotSaved();
        this.mFragments.execPendingActions();
        this.mFragments.dispatchStart();
    }

    @Override // android.app.Activity
    public void onStateNotSaved() {
        this.mFragments.noteStateNotSaved();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        this.mStopped = true;
        while (a(this.mFragments.B(), Lifecycle$State.CREATED)) {
        }
        this.mHandler.sendEmptyMessage(1);
        this.mFragments.dispatchStop();
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int i) {
        if (!this.wc && i != -1) {
            e(i);
        }
        super.startActivityForResult(intent, i);
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        if (!this.wc && i != -1) {
            e(i);
        }
        super.startActivityForResult(intent, i, bundle);
    }

    @Override // android.app.Activity
    public void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4) throws IntentSender.SendIntentException {
        if (!this.vc && i != -1) {
            e(i);
        }
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4);
    }

    @Override // android.app.Activity
    public void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
        if (!this.vc && i != -1) {
            e(i);
        }
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View viewOnCreateView = this.mFragments.onCreateView(null, str, context, attributeSet);
        return viewOnCreateView == null ? super.onCreateView(str, context, attributeSet) : viewOnCreateView;
    }
}
