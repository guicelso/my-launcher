package android.support.v4.app;

import android.animation.Animator;
import android.arch.lifecycle.Lifecycle$Event;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: renamed from: android.support.v4.app.n, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class ComponentCallbacksC0013n implements ComponentCallbacks, View.OnCreateContextMenuListener, android.arch.lifecycle.e, android.arch.lifecycle.s {
    private static final a.b.c.b.o od = new a.b.c.b.o();
    static final Object pd = new Object();
    Boolean hd;
    View jd;
    float kd;
    private android.arch.lifecycle.g ld;
    boolean mAdded;
    C0011l mAnimationInfo;
    Bundle mArguments;
    int mBackStackNesting;
    boolean mCalled;
    O mChildFragmentManager;
    P mChildNonConfig;
    ViewGroup mContainer;
    int mContainerId;
    boolean mDeferStart;
    boolean mDetached;
    int mFragmentId;
    O mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    boolean mHiddenChanged;
    AbstractC0019u mHost;
    boolean mInLayout;
    boolean mIsCreated;
    boolean mIsNewlyAdded;
    LayoutInflater mLayoutInflater;
    ComponentCallbacksC0013n mParentFragment;
    boolean mPerformedCreateView;
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetaining;
    Bundle mSavedFragmentState;
    SparseArray mSavedViewState;
    String mTag;
    ComponentCallbacksC0013n mTarget;
    int mTargetRequestCode;
    View mView;
    String mWho;
    android.arch.lifecycle.e md;
    android.arch.lifecycle.r oa;
    int mState = 0;
    int mIndex = -1;
    int mTargetIndex = -1;
    boolean mMenuVisible = true;
    boolean mUserVisibleHint = true;
    android.arch.lifecycle.g tc = new android.arch.lifecycle.g(this);
    android.arch.lifecycle.l nd = new android.arch.lifecycle.l();

    /* JADX INFO: Access modifiers changed from: private */
    public void Va() {
        C0011l c0011l = this.mAnimationInfo;
        N n = null;
        if (c0011l != null) {
            c0011l.mEnterTransitionPostponed = false;
            N n2 = c0011l.mStartEnterTransitionListener;
            c0011l.mStartEnterTransitionListener = null;
            n = n2;
        }
        if (n != null) {
            n.onStartEnterTransition();
        }
    }

    private C0011l Wa() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = new C0011l();
        }
        return this.mAnimationInfo;
    }

    static boolean b(Context context, String str) {
        try {
            Class<?> clsLoadClass = (Class) od.get(str);
            if (clsLoadClass == null) {
                clsLoadClass = context.getClassLoader().loadClass(str);
                od.put(str, clsLoadClass);
            }
            return ComponentCallbacksC0013n.class.isAssignableFrom(clsLoadClass);
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static ComponentCallbacksC0013n instantiate(Context context, String str, Bundle bundle) {
        try {
            Class<?> clsLoadClass = (Class) od.get(str);
            if (clsLoadClass == null) {
                clsLoadClass = context.getClassLoader().loadClass(str);
                od.put(str, clsLoadClass);
            }
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) clsLoadClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            if (bundle != null) {
                bundle.setClassLoader(componentCallbacksC0013n.getClass().getClassLoader());
                componentCallbacksC0013n.setArguments(bundle);
            }
            return componentCallbacksC0013n;
        } catch (ClassNotFoundException e) {
            final String str2 = "Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public";
            throw new RuntimeException(str2, e) { // from class: android.support.v4.app.Fragment$InstantiationException
            };
        } catch (IllegalAccessException e2) {
            final String str3 = "Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public";
            throw new RuntimeException(str3, e2) { // from class: android.support.v4.app.Fragment$InstantiationException
            };
        } catch (InstantiationException e3) {
            final String str4 = "Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public";
            throw new RuntimeException(str4, e3) { // from class: android.support.v4.app.Fragment$InstantiationException
            };
        } catch (NoSuchMethodException e4) {
            final String strB = b.a.a.a.a.b("Unable to instantiate fragment ", str, ": could not find Fragment constructor");
            throw new RuntimeException(strB, e4) { // from class: android.support.v4.app.Fragment$InstantiationException
            };
        } catch (InvocationTargetException e5) {
            final String strB2 = b.a.a.a.a.b("Unable to instantiate fragment ", str, ": calling Fragment constructor caused an exception");
            throw new RuntimeException(strB2, e5) { // from class: android.support.v4.app.Fragment$InstantiationException
            };
        }
    }

    @Override // android.arch.lifecycle.s
    public android.arch.lifecycle.r a() {
        AbstractC0019u abstractC0019u = this.mHost;
        if ((abstractC0019u == null ? null : abstractC0019u.getContext()) == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (this.oa == null) {
            this.oa = new android.arch.lifecycle.r();
        }
        return this.oa;
    }

    final void a(int i, ComponentCallbacksC0013n componentCallbacksC0013n) {
        String str;
        this.mIndex = i;
        StringBuilder sb = new StringBuilder();
        if (componentCallbacksC0013n != null) {
            sb.append(componentCallbacksC0013n.mWho);
            str = ":";
        } else {
            str = "android:fragment:";
        }
        sb.append(str);
        sb.append(this.mIndex);
        this.mWho = sb.toString();
    }

    void a(Animator animator) {
        Wa().mAnimator = animator;
    }

    void a(Bundle bundle) {
        Parcelable parcelable;
        if (bundle == null || (parcelable = bundle.getParcelable("android:support:fragments")) == null) {
            return;
        }
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
        }
        this.mChildFragmentManager.a(parcelable, this.mChildNonConfig);
        this.mChildNonConfig = null;
        this.mChildFragmentManager.dispatchCreate();
    }

    void a(N n) {
        Wa();
        N n2 = this.mAnimationInfo.mStartEnterTransitionListener;
        if (n == n2) {
            return;
        }
        if (n != null && n2 != null) {
            throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
        }
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l.mEnterTransitionPostponed) {
            c0011l.mStartEnterTransitionListener = n;
        }
        if (n != null) {
            n.startListening();
        }
    }

    void c(View view) {
        Wa().mAnimatingAway = view;
    }

    @Override // android.arch.lifecycle.e
    public android.arch.lifecycle.c d() {
        return this.tc;
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    ComponentCallbacksC0013n findFragmentByWho(String str) {
        if (str.equals(this.mWho)) {
            return this;
        }
        O o = this.mChildFragmentManager;
        if (o != null) {
            return o.findFragmentByWho(str);
        }
        return null;
    }

    View getAnimatingAway() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return null;
        }
        return c0011l.mAnimatingAway;
    }

    Animator getAnimator() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return null;
        }
        return c0011l.mAnimator;
    }

    public Object getEnterTransition() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return null;
        }
        return c0011l.Zc;
    }

    void getEnterTransitionCallback() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return;
        }
        qa qaVar = c0011l.mEnterTransitionCallback;
    }

    public Object getExitTransition() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return null;
        }
        return c0011l.ad;
    }

    int getNextAnim() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return 0;
        }
        return c0011l.mNextAnim;
    }

    int getNextTransition() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return 0;
        }
        return c0011l.mNextTransition;
    }

    int getNextTransitionStyle() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return 0;
        }
        return c0011l.mNextTransitionStyle;
    }

    public Object getReenterTransition() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return null;
        }
        return c0011l.bd == pd ? getExitTransition() : this.mAnimationInfo.bd;
    }

    public Object getReturnTransition() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return null;
        }
        return c0011l._c == pd ? getEnterTransition() : this.mAnimationInfo._c;
    }

    public Object getSharedElementEnterTransition() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return null;
        }
        return c0011l.cd;
    }

    public Object getSharedElementReturnTransition() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return null;
        }
        return c0011l.dd == pd ? getSharedElementEnterTransition() : this.mAnimationInfo.dd;
    }

    int getStateAfterAnimating() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return 0;
        }
        return c0011l.mStateAfterAnimating;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    void instantiateChildFragmentManager() {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment has not been attached yet.");
        }
        this.mChildFragmentManager = new O();
        O o = this.mChildFragmentManager;
        AbstractC0019u abstractC0019u = this.mHost;
        C0009j c0009j = new C0009j(this);
        if (o.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        o.mHost = abstractC0019u;
        o.mContainer = c0009j;
        o.mParent = this;
    }

    boolean isHideReplaced() {
        C0011l c0011l = this.mAnimationInfo;
        if (c0011l == null) {
            return false;
        }
        return c0011l.mIsHideReplaced;
    }

    final boolean isInBackStack() {
        return this.mBackStackNesting > 0;
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        AbstractC0019u abstractC0019u = this.mHost;
        (abstractC0019u == null ? null : (r) abstractC0019u.getActivity()).onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void onDestroyOptionsMenu() {
    }

    public void onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
        AbstractC0019u abstractC0019u = this.mHost;
        if ((abstractC0019u == null ? null : abstractC0019u.getActivity()) != null) {
            this.mCalled = false;
            this.mCalled = true;
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCalled = true;
    }

    void performConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.dispatchConfigurationChanged(configuration);
        }
    }

    boolean performContextItemSelected(MenuItem menuItem) {
        O o;
        return (this.mHidden || (o = this.mChildFragmentManager) == null || !o.dispatchContextItemSelected(menuItem)) ? false : true;
    }

    boolean performCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        boolean z = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            z = true;
        }
        O o = this.mChildFragmentManager;
        return o != null ? z | o.dispatchCreateOptionsMenu(menu, menuInflater) : z;
    }

    void performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.noteStateNotSaved();
        }
        this.mPerformedCreateView = true;
        this.md = new C0010k(this);
        this.ld = null;
        this.mView = null;
        if (this.mView != null) {
            this.md.d();
            this.nd.setValue(this.md);
        } else {
            if (this.ld != null) {
                throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
            }
            this.md = null;
        }
    }

    void performDestroyView() {
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.dispatchDestroyView();
        }
        this.mState = 1;
        this.mCalled = false;
        this.mCalled = true;
        if (this.mView != null) {
            this.ld.b(Lifecycle$Event.ON_DESTROY);
        }
        if (!this.mCalled) {
            throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", this, " did not call through to super.onDestroyView()"));
        }
        la.g(this).l();
        this.mPerformedCreateView = false;
    }

    LayoutInflater performGetLayoutInflater(Bundle bundle) {
        AbstractC0019u abstractC0019u = this.mHost;
        if (abstractC0019u == null) {
            throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        }
        C0015p c0015p = (C0015p) abstractC0019u;
        LayoutInflater layoutInflaterCloneInContext = c0015p.n.getLayoutInflater().cloneInContext(c0015p.n);
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
            int i = this.mState;
            if (i >= 5) {
                this.mChildFragmentManager.dispatchResume();
            } else if (i >= 4) {
                this.mChildFragmentManager.dispatchStart();
            } else if (i >= 2) {
                this.mChildFragmentManager.dispatchActivityCreated();
            } else if (i >= 1) {
                this.mChildFragmentManager.dispatchCreate();
            }
        }
        O o = this.mChildFragmentManager;
        o.getLayoutInflaterFactory();
        layoutInflaterCloneInContext.setFactory2(o);
        int i2 = Build.VERSION.SDK_INT;
        this.mLayoutInflater = layoutInflaterCloneInContext;
        return this.mLayoutInflater;
    }

    void performLowMemory() {
        this.mCalled = true;
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.dispatchLowMemory();
        }
    }

    void performMultiWindowModeChanged(boolean z) {
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.dispatchMultiWindowModeChanged(z);
        }
    }

    boolean performOptionsItemSelected(MenuItem menuItem) {
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu) {
            boolean z = this.mMenuVisible;
        }
        O o = this.mChildFragmentManager;
        return o != null && o.dispatchOptionsItemSelected(menuItem);
    }

    void performOptionsMenuClosed(Menu menu) {
        if (this.mHidden) {
            return;
        }
        if (this.mHasMenu) {
            boolean z = this.mMenuVisible;
        }
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.dispatchOptionsMenuClosed(menu);
        }
    }

    void performPause() {
        if (this.mView != null) {
            this.ld.b(Lifecycle$Event.ON_PAUSE);
        }
        this.tc.b(Lifecycle$Event.ON_PAUSE);
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.dispatchPause();
        }
        this.mState = 4;
        this.mCalled = false;
        this.mCalled = true;
        if (!this.mCalled) {
            throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", this, " did not call through to super.onPause()"));
        }
    }

    void performPictureInPictureModeChanged(boolean z) {
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.dispatchPictureInPictureModeChanged(z);
        }
    }

    boolean performPrepareOptionsMenu(Menu menu) {
        boolean z = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            z = true;
        }
        O o = this.mChildFragmentManager;
        return o != null ? z | o.dispatchPrepareOptionsMenu(menu) : z;
    }

    void performResume() {
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 5;
        this.mCalled = false;
        this.mCalled = true;
        if (!this.mCalled) {
            throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", this, " did not call through to super.onResume()"));
        }
        O o2 = this.mChildFragmentManager;
        if (o2 != null) {
            o2.dispatchResume();
            this.mChildFragmentManager.execPendingActions();
        }
        this.tc.b(Lifecycle$Event.ON_RESUME);
        if (this.mView != null) {
            this.ld.b(Lifecycle$Event.ON_RESUME);
        }
    }

    void performSaveInstanceState(Bundle bundle) {
        Parcelable parcelableSaveAllState;
        O o = this.mChildFragmentManager;
        if (o == null || (parcelableSaveAllState = o.saveAllState()) == null) {
            return;
        }
        bundle.putParcelable("android:support:fragments", parcelableSaveAllState);
    }

    void performStart() {
        O o = this.mChildFragmentManager;
        if (o != null) {
            o.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 4;
        this.mCalled = false;
        this.mCalled = true;
        if (!this.mCalled) {
            throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", this, " did not call through to super.onStart()"));
        }
        O o2 = this.mChildFragmentManager;
        if (o2 != null) {
            o2.dispatchStart();
        }
        this.tc.b(Lifecycle$Event.ON_START);
        if (this.mView != null) {
            this.ld.b(Lifecycle$Event.ON_START);
        }
    }

    final void restoreViewState(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = this.mSavedViewState;
        if (sparseArray != null) {
            this.jd.restoreHierarchyState(sparseArray);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        this.mCalled = true;
        if (this.mView != null) {
            this.ld.b(Lifecycle$Event.ON_CREATE);
        }
        if (!this.mCalled) {
            throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", this, " did not call through to super.onViewStateRestored()"));
        }
    }

    public void setArguments(Bundle bundle) {
        if (this.mIndex >= 0) {
            O o = this.mFragmentManager;
            if (o == null ? false : o.isStateSaved()) {
                throw new IllegalStateException("Fragment already active and state has been saved");
            }
        }
        this.mArguments = bundle;
    }

    void setHideReplaced(boolean z) {
        Wa().mIsHideReplaced = z;
    }

    void setNextAnim(int i) {
        if (this.mAnimationInfo == null && i == 0) {
            return;
        }
        Wa().mNextAnim = i;
    }

    void setNextTransition(int i, int i2) {
        if (this.mAnimationInfo == null && i == 0 && i2 == 0) {
            return;
        }
        Wa();
        C0011l c0011l = this.mAnimationInfo;
        c0011l.mNextTransition = i;
        c0011l.mNextTransitionStyle = i2;
    }

    void setStateAfterAnimating(int i) {
        Wa().mStateAfterAnimating = i;
    }

    public void startPostponedEnterTransition() {
        O o = this.mFragmentManager;
        if (o == null || o.mHost == null) {
            Wa().mEnterTransitionPostponed = false;
        } else if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
            this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new RunnableC0008i(this));
        } else {
            Va();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        a.b.a.a.buildShortClassTag(this, sb);
        if (this.mIndex >= 0) {
            sb.append(" #");
            sb.append(this.mIndex);
        }
        if (this.mFragmentId != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            sb.append(" ");
            sb.append(this.mTag);
        }
        sb.append('}');
        return sb.toString();
    }

    AbstractC0023y z() {
        return this.mChildFragmentManager;
    }
}
