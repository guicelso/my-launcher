package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle$Event;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
final class O extends AbstractC0023y implements LayoutInflater.Factory2 {
    static Field rd;
    static final Interpolator sd = new DecelerateInterpolator(2.5f);
    static final Interpolator td = new DecelerateInterpolator(1.5f);
    SparseArray mActive;
    ArrayList mAvailBackStackIndices;
    ArrayList mBackStack;
    ArrayList mBackStackChangeListeners;
    ArrayList mBackStackIndices;
    AbstractC0017s mContainer;
    ArrayList mCreatedMenus;
    boolean mDestroyed;
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    AbstractC0019u mHost;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    ComponentCallbacksC0013n mParent;
    ArrayList mPendingActions;
    ArrayList mPostponedTransactions;
    ComponentCallbacksC0013n mPrimaryNav;
    P mSavedNonConfig;
    boolean mStateSaved;
    boolean mStopped;
    ArrayList mTmpAddedFragments;
    ArrayList mTmpIsPop;
    ArrayList mTmpRecords;
    int mNextFragmentIndex = 0;
    final ArrayList mAdded = new ArrayList();
    private final CopyOnWriteArrayList mLifecycleCallbacks = new CopyOnWriteArrayList();
    int mCurState = 0;
    Bundle mStateBundle = null;
    SparseArray mStateArray = null;
    Runnable mExecCommit = new RunnableC0024z(this);

    static {
        new AccelerateInterpolator(2.5f);
        new AccelerateInterpolator(1.5f);
    }

    O() {
    }

    private void M(int i) {
        try {
            this.mExecutingActions = true;
            moveToState(i, false);
            this.mExecutingActions = false;
            execPendingActions();
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    private void Xa() {
        SparseArray sparseArray = this.mActive;
        if (sparseArray != null) {
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                if (this.mActive.valueAt(size) == null) {
                    SparseArray sparseArray2 = this.mActive;
                    sparseArray2.delete(sparseArray2.keyAt(size));
                }
            }
        }
    }

    private void Ya() {
        if (isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mNoTransactionsBecause == null) {
            return;
        }
        StringBuilder sbE = b.a.a.a.a.e("Can not perform this action inside of ");
        sbE.append(this.mNoTransactionsBecause);
        throw new IllegalStateException(sbE.toString());
    }

    private void Za() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    private void _a() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                ((N) this.mPostponedTransactions.remove(0)).completeTransaction();
            }
        }
    }

    static H a(float f, float f2, float f3, float f4) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(sd);
        scaleAnimation.setDuration(220L);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f3, f4);
        alphaAnimation.setInterpolator(td);
        alphaAnimation.setDuration(220L);
        animationSet.addAnimation(alphaAnimation);
        return new H(animationSet, (RunnableC0024z) null);
    }

    private static Animation.AnimationListener a(Animation animation) {
        String str;
        try {
            if (rd == null) {
                rd = Animation.class.getDeclaredField("mListener");
                rd.setAccessible(true);
            }
            return (Animation.AnimationListener) rd.get(animation);
        } catch (IllegalAccessException e) {
            e = e;
            str = "Cannot access Animation's mListener field";
            Log.e("FragmentManager", str, e);
            return null;
        } catch (NoSuchFieldException e2) {
            e = e2;
            str = "No field with the name mListener is found in Animation class";
            Log.e("FragmentManager", str, e);
            return null;
        }
    }

    private static void a(P p) {
        if (p == null) {
            return;
        }
        List fragments = p.getFragments();
        if (fragments != null) {
            Iterator it = fragments.iterator();
            while (it.hasNext()) {
                ((ComponentCallbacksC0013n) it.next()).mRetaining = true;
            }
        }
        List childNonConfigs = p.getChildNonConfigs();
        if (childNonConfigs != null) {
            Iterator it2 = childNonConfigs.iterator();
            while (it2.hasNext()) {
                a((P) it2.next());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0004e c0004e, boolean z, boolean z2, boolean z3) {
        if (z) {
            c0004e.executePopOps(z3);
        } else {
            c0004e.executeOps();
        }
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(c0004e);
        arrayList2.add(Boolean.valueOf(z));
        if (z2) {
            ba.a(this, arrayList, arrayList2, 0, 1, true);
        }
        if (z3) {
            moveToState(this.mCurState, true);
        }
        SparseArray sparseArray = this.mActive;
        if (sparseArray != null) {
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mActive.valueAt(i);
                if (componentCallbacksC0013n != null && componentCallbacksC0013n.mView != null && componentCallbacksC0013n.mIsNewlyAdded && c0004e.interactsWith(componentCallbacksC0013n.mContainerId)) {
                    float f = componentCallbacksC0013n.kd;
                    if (f > 0.0f) {
                        componentCallbacksC0013n.mView.setAlpha(f);
                    }
                    if (z3) {
                        componentCallbacksC0013n.kd = 0.0f;
                    } else {
                        componentCallbacksC0013n.kd = -1.0f;
                        componentCallbacksC0013n.mIsNewlyAdded = false;
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0036 A[PHI: r0 r2
      0x0036: PHI (r0v8 int) = (r0v5 int), (r0v4 int) binds: [B:29:0x0075, B:15:0x0034] A[DONT_GENERATE, DONT_INLINE]
      0x0036: PHI (r2v4 int) = (r2v2 int), (r2v1 int) binds: [B:29:0x0075, B:15:0x0034] A[DONT_GENERATE, DONT_INLINE]] */
    private void a(ArrayList arrayList, ArrayList arrayList2) {
        int iIndexOf;
        int iIndexOf2;
        ArrayList arrayList3 = this.mPostponedTransactions;
        int size = arrayList3 == null ? 0 : arrayList3.size();
        int i = 0;
        while (i < size) {
            N n = (N) this.mPostponedTransactions.get(i);
            if (arrayList != null && !n.vd && (iIndexOf2 = arrayList.indexOf(n.wd)) != -1 && ((Boolean) arrayList2.get(iIndexOf2)).booleanValue()) {
                n.cancelTransaction();
            } else if (n.isReady() || (arrayList != null && n.wd.interactsWith(arrayList, 0, arrayList.size()))) {
                this.mPostponedTransactions.remove(i);
                i--;
                size--;
                if (arrayList == null || n.vd || (iIndexOf = arrayList.indexOf(n.wd)) == -1 || !((Boolean) arrayList2.get(iIndexOf)).booleanValue()) {
                    n.completeTransaction();
                } else {
                    n.cancelTransaction();
                }
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ab() {
        synchronized (this) {
            boolean z = false;
            boolean z2 = (this.mPostponedTransactions == null || this.mPostponedTransactions.isEmpty()) ? false : true;
            if (this.mPendingActions != null && this.mPendingActions.size() == 1) {
                z = true;
            }
            if (z2 || z) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
        }
    }

    private boolean b(ArrayList arrayList, ArrayList arrayList2) {
        synchronized (this) {
            if (this.mPendingActions != null && this.mPendingActions.size() != 0) {
                int size = this.mPendingActions.size();
                boolean z = false;
                for (int i = 0; i < size; i++) {
                    ((C0004e) ((M) this.mPendingActions.get(i))).generateOps(arrayList, arrayList2);
                    z |= true;
                }
                this.mPendingActions.clear();
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                return z;
            }
            return false;
        }
    }

    private void c(a.b.c.b.d dVar) {
        int i = this.mCurState;
        if (i < 1) {
            return;
        }
        int iMin = Math.min(i, 4);
        int size = this.mAdded.size();
        for (int i2 = 0; i2 < size; i2++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(i2);
            if (componentCallbacksC0013n.mState < iMin) {
                a(componentCallbacksC0013n, iMin, componentCallbacksC0013n.getNextAnim(), componentCallbacksC0013n.getNextTransition(), false);
                if (componentCallbacksC0013n.mView != null && !componentCallbacksC0013n.mHidden && componentCallbacksC0013n.mIsNewlyAdded) {
                    dVar.add(componentCallbacksC0013n);
                }
            }
        }
    }

    private void c(ArrayList arrayList, ArrayList arrayList2) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        a(arrayList, arrayList2);
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            if (!((C0004e) arrayList.get(i)).mReorderingAllowed) {
                if (i2 != i) {
                    a(arrayList, arrayList2, i2, i);
                }
                i2 = i + 1;
                if (((Boolean) arrayList2.get(i)).booleanValue()) {
                    while (i2 < size && ((Boolean) arrayList2.get(i2)).booleanValue() && !((C0004e) arrayList.get(i2)).mReorderingAllowed) {
                        i2++;
                    }
                }
                a(arrayList, arrayList2, i, i2);
                i = i2 - 1;
            }
            i++;
        }
        if (i2 != size) {
            a(arrayList, arrayList2, i2, size);
        }
    }

    static boolean modifiesAlpha(Animator animator) {
        if (animator == null) {
            return false;
        }
        if (animator instanceof ValueAnimator) {
            for (PropertyValuesHolder propertyValuesHolder : ((ValueAnimator) animator).getValues()) {
                if ("alpha".equals(propertyValuesHolder.getPropertyName())) {
                    return true;
                }
            }
        } else if (animator instanceof AnimatorSet) {
            ArrayList<Animator> childAnimations = ((AnimatorSet) animator).getChildAnimations();
            for (int i = 0; i < childAnimations.size(); i++) {
                if (modifiesAlpha(childAnimations.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int reverseTransit(int i) {
        if (i == 4097) {
            return 8194;
        }
        if (i != 4099) {
            return i != 8194 ? 0 : 4097;
        }
        return 4099;
    }

    private void u(boolean z) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment host has been destroyed");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z) {
            Ya();
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList();
            this.mTmpIsPop = new ArrayList();
        }
        this.mExecutingActions = true;
        try {
            a((ArrayList) null, (ArrayList) null);
        } finally {
            this.mExecutingActions = false;
        }
    }

    public void A() {
        M(2);
    }

    public int a(C0004e c0004e) {
        synchronized (this) {
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                int iIntValue = ((Integer) this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1)).intValue();
                this.mBackStackIndices.set(iIntValue, c0004e);
                return iIntValue;
            }
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList();
            }
            int size = this.mBackStackIndices.size();
            this.mBackStackIndices.add(c0004e);
            return size;
        }
    }

    public void a(int i, C0004e c0004e) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList();
            }
            int size = this.mBackStackIndices.size();
            if (i < size) {
                this.mBackStackIndices.set(i, c0004e);
            } else {
                while (size < i) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList();
                    }
                    this.mAvailBackStackIndices.add(Integer.valueOf(size));
                    size++;
                }
                this.mBackStackIndices.add(c0004e);
            }
        }
    }

    void a(Parcelable parcelable, P p) {
        List childNonConfigs;
        List listC;
        if (parcelable == null) {
            return;
        }
        FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
        if (fragmentManagerState.mActive == null) {
            return;
        }
        int i = 0;
        if (p != null) {
            List fragments = p.getFragments();
            childNonConfigs = p.getChildNonConfigs();
            listC = p.C();
            int size = fragments != null ? fragments.size() : 0;
            for (int i2 = 0; i2 < size; i2++) {
                ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) fragments.get(i2);
                int i3 = 0;
                while (true) {
                    FragmentState[] fragmentStateArr = fragmentManagerState.mActive;
                    if (i3 >= fragmentStateArr.length || fragmentStateArr[i3].mIndex == componentCallbacksC0013n.mIndex) {
                        break;
                    } else {
                        i3++;
                    }
                }
                FragmentState[] fragmentStateArr2 = fragmentManagerState.mActive;
                if (i3 == fragmentStateArr2.length) {
                    StringBuilder sbE = b.a.a.a.a.e("Could not find active fragment with index ");
                    sbE.append(componentCallbacksC0013n.mIndex);
                    a(new IllegalStateException(sbE.toString()));
                    throw null;
                }
                FragmentState fragmentState = fragmentStateArr2[i3];
                fragmentState.mInstance = componentCallbacksC0013n;
                componentCallbacksC0013n.mSavedViewState = null;
                componentCallbacksC0013n.mBackStackNesting = 0;
                componentCallbacksC0013n.mInLayout = false;
                componentCallbacksC0013n.mAdded = false;
                componentCallbacksC0013n.mTarget = null;
                Bundle bundle = fragmentState.mSavedFragmentState;
                if (bundle != null) {
                    bundle.setClassLoader(this.mHost.getContext().getClassLoader());
                    componentCallbacksC0013n.mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray("android:view_state");
                    componentCallbacksC0013n.mSavedFragmentState = fragmentState.mSavedFragmentState;
                }
            }
        } else {
            childNonConfigs = null;
            listC = null;
        }
        this.mActive = new SparseArray(fragmentManagerState.mActive.length);
        int i4 = 0;
        while (true) {
            FragmentState[] fragmentStateArr3 = fragmentManagerState.mActive;
            if (i4 >= fragmentStateArr3.length) {
                break;
            }
            FragmentState fragmentState2 = fragmentStateArr3[i4];
            if (fragmentState2 != null) {
                ComponentCallbacksC0013n componentCallbacksC0013nA = fragmentState2.a(this.mHost, this.mContainer, this.mParent, (childNonConfigs == null || i4 >= childNonConfigs.size()) ? null : (P) childNonConfigs.get(i4), (listC == null || i4 >= listC.size()) ? null : (android.arch.lifecycle.r) listC.get(i4));
                this.mActive.put(componentCallbacksC0013nA.mIndex, componentCallbacksC0013nA);
                fragmentState2.mInstance = null;
            }
            i4++;
        }
        if (p != null) {
            List fragments2 = p.getFragments();
            int size2 = fragments2 != null ? fragments2.size() : 0;
            for (int i5 = 0; i5 < size2; i5++) {
                ComponentCallbacksC0013n componentCallbacksC0013n2 = (ComponentCallbacksC0013n) fragments2.get(i5);
                int i6 = componentCallbacksC0013n2.mTargetIndex;
                if (i6 >= 0) {
                    componentCallbacksC0013n2.mTarget = (ComponentCallbacksC0013n) this.mActive.get(i6);
                    if (componentCallbacksC0013n2.mTarget == null) {
                        Log.w("FragmentManager", "Re-attaching retained fragment " + componentCallbacksC0013n2 + " target no longer exists: " + componentCallbacksC0013n2.mTargetIndex);
                    }
                }
            }
        }
        this.mAdded.clear();
        if (fragmentManagerState.mAdded != null) {
            int i7 = 0;
            while (true) {
                int[] iArr = fragmentManagerState.mAdded;
                if (i7 >= iArr.length) {
                    break;
                }
                ComponentCallbacksC0013n componentCallbacksC0013n3 = (ComponentCallbacksC0013n) this.mActive.get(iArr[i7]);
                if (componentCallbacksC0013n3 == null) {
                    StringBuilder sbE2 = b.a.a.a.a.e("No instantiated fragment for index #");
                    sbE2.append(fragmentManagerState.mAdded[i7]);
                    a(new IllegalStateException(sbE2.toString()));
                    throw null;
                }
                componentCallbacksC0013n3.mAdded = true;
                if (this.mAdded.contains(componentCallbacksC0013n3)) {
                    throw new IllegalStateException("Already added!");
                }
                synchronized (this.mAdded) {
                    this.mAdded.add(componentCallbacksC0013n3);
                }
                i7++;
            }
        }
        BackStackState[] backStackStateArr = fragmentManagerState.mBackStack;
        if (backStackStateArr != null) {
            this.mBackStack = new ArrayList(backStackStateArr.length);
            while (true) {
                BackStackState[] backStackStateArr2 = fragmentManagerState.mBackStack;
                if (i >= backStackStateArr2.length) {
                    break;
                }
                C0004e c0004eA = backStackStateArr2[i].a(this);
                this.mBackStack.add(c0004eA);
                int i8 = c0004eA.mIndex;
                if (i8 >= 0) {
                    a(i8, c0004eA);
                }
                i++;
            }
        } else {
            this.mBackStack = null;
        }
        int i9 = fragmentManagerState.mPrimaryNavActiveIndex;
        if (i9 >= 0) {
            this.mPrimaryNav = (ComponentCallbacksC0013n) this.mActive.get(i9);
        }
        this.mNextFragmentIndex = fragmentManagerState.mNextFragmentIndex;
    }

    public void a(M m, boolean z) {
        if (!z) {
            Ya();
        }
        synchronized (this) {
            if (!this.mDestroyed && this.mHost != null) {
                if (this.mPendingActions == null) {
                    this.mPendingActions = new ArrayList();
                }
                this.mPendingActions.add(m);
                ab();
                return;
            }
            if (!z) {
                throw new IllegalStateException("Activity has been destroyed");
            }
        }
    }

    public void a(ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        h(componentCallbacksC0013n);
        if (componentCallbacksC0013n.mDetached) {
            return;
        }
        if (this.mAdded.contains(componentCallbacksC0013n)) {
            throw new IllegalStateException("Fragment already added: " + componentCallbacksC0013n);
        }
        synchronized (this.mAdded) {
            this.mAdded.add(componentCallbacksC0013n);
        }
        componentCallbacksC0013n.mAdded = true;
        componentCallbacksC0013n.mRemoving = false;
        if (componentCallbacksC0013n.mView == null) {
            componentCallbacksC0013n.mHiddenChanged = false;
        }
        if (componentCallbacksC0013n.mHasMenu && componentCallbacksC0013n.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        if (z) {
            a(componentCallbacksC0013n, this.mCurState, 0, 0, false);
        }
    }

    void b(ComponentCallbacksC0013n componentCallbacksC0013n, Bundle bundle, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.b(componentCallbacksC0013n, bundle, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    @Override // android.support.v4.app.AbstractC0023y
    public V beginTransaction() {
        return new C0004e(this);
    }

    void c(ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.c(componentCallbacksC0013n, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    void d(ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.d(componentCallbacksC0013n, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.mStopped = false;
        M(2);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        for (int i = 0; i < this.mAdded.size(); i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(i);
            if (componentCallbacksC0013n != null) {
                componentCallbacksC0013n.performConfigurationChanged(configuration);
            }
        }
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(i);
            if (componentCallbacksC0013n != null && componentCallbacksC0013n.performContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        M(1);
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.mCurState < 1) {
            return false;
        }
        ArrayList arrayList = null;
        boolean z = false;
        for (int i = 0; i < this.mAdded.size(); i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(i);
            if (componentCallbacksC0013n != null && componentCallbacksC0013n.performCreateOptionsMenu(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(componentCallbacksC0013n);
                z = true;
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                ComponentCallbacksC0013n componentCallbacksC0013n2 = (ComponentCallbacksC0013n) this.mCreatedMenus.get(i2);
                if (arrayList == null || !arrayList.contains(componentCallbacksC0013n2)) {
                    componentCallbacksC0013n2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z;
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions();
        M(0);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchDestroyView() {
        M(1);
    }

    public void dispatchLowMemory() {
        for (int i = 0; i < this.mAdded.size(); i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(i);
            if (componentCallbacksC0013n != null) {
                componentCallbacksC0013n.performLowMemory();
            }
        }
    }

    public void dispatchMultiWindowModeChanged(boolean z) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(size);
            if (componentCallbacksC0013n != null) {
                componentCallbacksC0013n.performMultiWindowModeChanged(z);
            }
        }
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(i);
            if (componentCallbacksC0013n != null && componentCallbacksC0013n.performOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mCurState < 1) {
            return;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(i);
            if (componentCallbacksC0013n != null) {
                componentCallbacksC0013n.performOptionsMenuClosed(menu);
            }
        }
    }

    public void dispatchPause() {
        M(4);
    }

    public void dispatchPictureInPictureModeChanged(boolean z) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(size);
            if (componentCallbacksC0013n != null) {
                componentCallbacksC0013n.performPictureInPictureModeChanged(z);
            }
        }
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        if (this.mCurState < 1) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < this.mAdded.size(); i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(i);
            if (componentCallbacksC0013n != null && componentCallbacksC0013n.performPrepareOptionsMenu(menu)) {
                z = true;
            }
        }
        return z;
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        this.mStopped = false;
        M(5);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        this.mStopped = false;
        M(4);
    }

    public void dispatchStop() {
        this.mStopped = true;
        M(3);
    }

    void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    @Override // android.support.v4.app.AbstractC0023y
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        String strA = b.a.a.a.a.a(str, "    ");
        SparseArray sparseArray = this.mActive;
        if (sparseArray != null && (size5 = sparseArray.size()) > 0) {
            printWriter.print(str);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (int i = 0; i < size5; i++) {
                ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mActive.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(componentCallbacksC0013n);
                if (componentCallbacksC0013n != null) {
                    printWriter.print(strA);
                    printWriter.print("mFragmentId=#");
                    printWriter.print(Integer.toHexString(componentCallbacksC0013n.mFragmentId));
                    printWriter.print(" mContainerId=#");
                    printWriter.print(Integer.toHexString(componentCallbacksC0013n.mContainerId));
                    printWriter.print(" mTag=");
                    printWriter.println(componentCallbacksC0013n.mTag);
                    printWriter.print(strA);
                    printWriter.print("mState=");
                    printWriter.print(componentCallbacksC0013n.mState);
                    printWriter.print(" mIndex=");
                    printWriter.print(componentCallbacksC0013n.mIndex);
                    printWriter.print(" mWho=");
                    printWriter.print(componentCallbacksC0013n.mWho);
                    printWriter.print(" mBackStackNesting=");
                    printWriter.println(componentCallbacksC0013n.mBackStackNesting);
                    printWriter.print(strA);
                    printWriter.print("mAdded=");
                    printWriter.print(componentCallbacksC0013n.mAdded);
                    printWriter.print(" mRemoving=");
                    printWriter.print(componentCallbacksC0013n.mRemoving);
                    printWriter.print(" mFromLayout=");
                    printWriter.print(componentCallbacksC0013n.mFromLayout);
                    printWriter.print(" mInLayout=");
                    printWriter.println(componentCallbacksC0013n.mInLayout);
                    printWriter.print(strA);
                    printWriter.print("mHidden=");
                    printWriter.print(componentCallbacksC0013n.mHidden);
                    printWriter.print(" mDetached=");
                    printWriter.print(componentCallbacksC0013n.mDetached);
                    printWriter.print(" mMenuVisible=");
                    printWriter.print(componentCallbacksC0013n.mMenuVisible);
                    printWriter.print(" mHasMenu=");
                    printWriter.println(componentCallbacksC0013n.mHasMenu);
                    printWriter.print(strA);
                    printWriter.print("mRetainInstance=");
                    printWriter.print(componentCallbacksC0013n.mRetainInstance);
                    printWriter.print(" mRetaining=");
                    printWriter.print(componentCallbacksC0013n.mRetaining);
                    printWriter.print(" mUserVisibleHint=");
                    printWriter.println(componentCallbacksC0013n.mUserVisibleHint);
                    if (componentCallbacksC0013n.mFragmentManager != null) {
                        printWriter.print(strA);
                        printWriter.print("mFragmentManager=");
                        printWriter.println(componentCallbacksC0013n.mFragmentManager);
                    }
                    if (componentCallbacksC0013n.mHost != null) {
                        printWriter.print(strA);
                        printWriter.print("mHost=");
                        printWriter.println(componentCallbacksC0013n.mHost);
                    }
                    if (componentCallbacksC0013n.mParentFragment != null) {
                        printWriter.print(strA);
                        printWriter.print("mParentFragment=");
                        printWriter.println(componentCallbacksC0013n.mParentFragment);
                    }
                    if (componentCallbacksC0013n.mArguments != null) {
                        printWriter.print(strA);
                        printWriter.print("mArguments=");
                        printWriter.println(componentCallbacksC0013n.mArguments);
                    }
                    if (componentCallbacksC0013n.mSavedFragmentState != null) {
                        printWriter.print(strA);
                        printWriter.print("mSavedFragmentState=");
                        printWriter.println(componentCallbacksC0013n.mSavedFragmentState);
                    }
                    if (componentCallbacksC0013n.mSavedViewState != null) {
                        printWriter.print(strA);
                        printWriter.print("mSavedViewState=");
                        printWriter.println(componentCallbacksC0013n.mSavedViewState);
                    }
                    if (componentCallbacksC0013n.mTarget != null) {
                        printWriter.print(strA);
                        printWriter.print("mTarget=");
                        printWriter.print(componentCallbacksC0013n.mTarget);
                        printWriter.print(" mTargetRequestCode=");
                        printWriter.println(componentCallbacksC0013n.mTargetRequestCode);
                    }
                    if (componentCallbacksC0013n.getNextAnim() != 0) {
                        printWriter.print(strA);
                        printWriter.print("mNextAnim=");
                        printWriter.println(componentCallbacksC0013n.getNextAnim());
                    }
                    if (componentCallbacksC0013n.mContainer != null) {
                        printWriter.print(strA);
                        printWriter.print("mContainer=");
                        printWriter.println(componentCallbacksC0013n.mContainer);
                    }
                    if (componentCallbacksC0013n.mView != null) {
                        printWriter.print(strA);
                        printWriter.print("mView=");
                        printWriter.println(componentCallbacksC0013n.mView);
                    }
                    if (componentCallbacksC0013n.jd != null) {
                        printWriter.print(strA);
                        printWriter.print("mInnerView=");
                        printWriter.println(componentCallbacksC0013n.mView);
                    }
                    if (componentCallbacksC0013n.getAnimatingAway() != null) {
                        printWriter.print(strA);
                        printWriter.print("mAnimatingAway=");
                        printWriter.println(componentCallbacksC0013n.getAnimatingAway());
                        printWriter.print(strA);
                        printWriter.print("mStateAfterAnimating=");
                        printWriter.println(componentCallbacksC0013n.getStateAfterAnimating());
                    }
                    la.g(componentCallbacksC0013n).dump(strA, fileDescriptor, printWriter, strArr);
                    if (componentCallbacksC0013n.mChildFragmentManager != null) {
                        printWriter.print(strA);
                        printWriter.println("Child " + componentCallbacksC0013n.mChildFragmentManager + ":");
                        componentCallbacksC0013n.mChildFragmentManager.dump(b.a.a.a.a.a(strA, "  "), fileDescriptor, printWriter, strArr);
                    }
                }
            }
        }
        int size6 = this.mAdded.size();
        if (size6 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i2 = 0; i2 < size6; i2++) {
                ComponentCallbacksC0013n componentCallbacksC0013n2 = (ComponentCallbacksC0013n) this.mAdded.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(componentCallbacksC0013n2.toString());
            }
        }
        ArrayList arrayList = this.mCreatedMenus;
        if (arrayList != null && (size4 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i3 = 0; i3 < size4; i3++) {
                ComponentCallbacksC0013n componentCallbacksC0013n3 = (ComponentCallbacksC0013n) this.mCreatedMenus.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(componentCallbacksC0013n3.toString());
            }
        }
        ArrayList arrayList2 = this.mBackStack;
        if (arrayList2 != null && (size3 = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i4 = 0; i4 < size3; i4++) {
                C0004e c0004e = (C0004e) this.mBackStack.get(i4);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i4);
                printWriter.print(": ");
                printWriter.println(c0004e.toString());
                c0004e.dump(strA, printWriter, true);
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null && (size2 = this.mBackStackIndices.size()) > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack Indices:");
                for (int i5 = 0; i5 < size2; i5++) {
                    Object obj = (C0004e) this.mBackStackIndices.get(i5);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i5);
                    printWriter.print(": ");
                    printWriter.println(obj);
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        ArrayList arrayList3 = this.mPendingActions;
        if (arrayList3 != null && (size = arrayList3.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Pending Actions:");
            for (int i6 = 0; i6 < size; i6++) {
                Object obj2 = (M) this.mPendingActions.get(i6);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i6);
                printWriter.print(": ");
                printWriter.println(obj2);
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.mNoTransactionsBecause);
        }
    }

    public void e(ComponentCallbacksC0013n componentCallbacksC0013n) {
        if (componentCallbacksC0013n.mDetached) {
            componentCallbacksC0013n.mDetached = false;
            if (componentCallbacksC0013n.mAdded) {
                return;
            }
            if (this.mAdded.contains(componentCallbacksC0013n)) {
                throw new IllegalStateException("Fragment already added: " + componentCallbacksC0013n);
            }
            synchronized (this.mAdded) {
                this.mAdded.add(componentCallbacksC0013n);
            }
            componentCallbacksC0013n.mAdded = true;
            if (componentCallbacksC0013n.mHasMenu && componentCallbacksC0013n.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
        }
    }

    void e(ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.e(componentCallbacksC0013n, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    public boolean execPendingActions() {
        u(true);
        boolean z = false;
        while (b(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                c(this.mTmpRecords, this.mTmpIsPop);
                Za();
                z = true;
            } catch (Throwable th) {
                Za();
                throw th;
            }
        }
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
        Xa();
        return z;
    }

    @Override // android.support.v4.app.AbstractC0023y
    public boolean executePendingTransactions() {
        boolean zExecPendingActions = execPendingActions();
        _a();
        return zExecPendingActions;
    }

    public void f(ComponentCallbacksC0013n componentCallbacksC0013n) {
        if (componentCallbacksC0013n.mDetached) {
            return;
        }
        componentCallbacksC0013n.mDetached = true;
        if (componentCallbacksC0013n.mAdded) {
            synchronized (this.mAdded) {
                this.mAdded.remove(componentCallbacksC0013n);
            }
            if (componentCallbacksC0013n.mHasMenu && componentCallbacksC0013n.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            componentCallbacksC0013n.mAdded = false;
        }
    }

    void f(ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.f(componentCallbacksC0013n, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    public ComponentCallbacksC0013n findFragmentById(int i) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(size);
            if (componentCallbacksC0013n != null && componentCallbacksC0013n.mFragmentId == i) {
                return componentCallbacksC0013n;
            }
        }
        SparseArray sparseArray = this.mActive;
        if (sparseArray == null) {
            return null;
        }
        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
            ComponentCallbacksC0013n componentCallbacksC0013n2 = (ComponentCallbacksC0013n) this.mActive.valueAt(size2);
            if (componentCallbacksC0013n2 != null && componentCallbacksC0013n2.mFragmentId == i) {
                return componentCallbacksC0013n2;
            }
        }
        return null;
    }

    @Override // android.support.v4.app.AbstractC0023y
    public ComponentCallbacksC0013n findFragmentByTag(String str) {
        if (str != null) {
            for (int size = this.mAdded.size() - 1; size >= 0; size--) {
                ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(size);
                if (componentCallbacksC0013n != null && str.equals(componentCallbacksC0013n.mTag)) {
                    return componentCallbacksC0013n;
                }
            }
        }
        SparseArray sparseArray = this.mActive;
        if (sparseArray == null || str == null) {
            return null;
        }
        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
            ComponentCallbacksC0013n componentCallbacksC0013n2 = (ComponentCallbacksC0013n) this.mActive.valueAt(size2);
            if (componentCallbacksC0013n2 != null && str.equals(componentCallbacksC0013n2.mTag)) {
                return componentCallbacksC0013n2;
            }
        }
        return null;
    }

    public ComponentCallbacksC0013n findFragmentByWho(String str) {
        ComponentCallbacksC0013n componentCallbacksC0013nFindFragmentByWho;
        SparseArray sparseArray = this.mActive;
        if (sparseArray == null || str == null) {
            return null;
        }
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mActive.valueAt(size);
            if (componentCallbacksC0013n != null && (componentCallbacksC0013nFindFragmentByWho = componentCallbacksC0013n.findFragmentByWho(str)) != null) {
                return componentCallbacksC0013nFindFragmentByWho;
            }
        }
        return null;
    }

    public void freeBackStackIndex(int i) {
        synchronized (this) {
            this.mBackStackIndices.set(i, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList();
            }
            this.mAvailBackStackIndices.add(Integer.valueOf(i));
        }
    }

    public void g(ComponentCallbacksC0013n componentCallbacksC0013n) {
        if (componentCallbacksC0013n.mHidden) {
            return;
        }
        componentCallbacksC0013n.mHidden = true;
        componentCallbacksC0013n.mHiddenChanged = true ^ componentCallbacksC0013n.mHiddenChanged;
    }

    void g(ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.g(componentCallbacksC0013n, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    @Override // android.support.v4.app.AbstractC0023y
    public List getFragments() {
        List list;
        if (this.mAdded.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        synchronized (this.mAdded) {
            list = (List) this.mAdded.clone();
        }
        return list;
    }

    LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this;
    }

    void h(ComponentCallbacksC0013n componentCallbacksC0013n) {
        if (componentCallbacksC0013n.mIndex >= 0) {
            return;
        }
        int i = this.mNextFragmentIndex;
        this.mNextFragmentIndex = i + 1;
        componentCallbacksC0013n.a(i, this.mParent);
        if (this.mActive == null) {
            this.mActive = new SparseArray();
        }
        this.mActive.put(componentCallbacksC0013n.mIndex, componentCallbacksC0013n);
    }

    void h(ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.h(componentCallbacksC0013n, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    void i(ComponentCallbacksC0013n componentCallbacksC0013n) {
        Animator animator;
        if (componentCallbacksC0013n == null) {
            return;
        }
        int iMin = this.mCurState;
        if (componentCallbacksC0013n.mRemoving) {
            iMin = componentCallbacksC0013n.isInBackStack() ? Math.min(iMin, 1) : Math.min(iMin, 0);
        }
        a(componentCallbacksC0013n, iMin, componentCallbacksC0013n.getNextTransition(), componentCallbacksC0013n.getNextTransitionStyle(), false);
        View view = componentCallbacksC0013n.mView;
        if (view != null) {
            ViewGroup viewGroup = componentCallbacksC0013n.mContainer;
            ComponentCallbacksC0013n componentCallbacksC0013n2 = null;
            if (viewGroup != null && view != null) {
                int iIndexOf = this.mAdded.indexOf(componentCallbacksC0013n);
                while (true) {
                    iIndexOf--;
                    if (iIndexOf < 0) {
                        break;
                    }
                    ComponentCallbacksC0013n componentCallbacksC0013n3 = (ComponentCallbacksC0013n) this.mAdded.get(iIndexOf);
                    if (componentCallbacksC0013n3.mContainer == viewGroup && componentCallbacksC0013n3.mView != null) {
                        componentCallbacksC0013n2 = componentCallbacksC0013n3;
                        break;
                    }
                }
            }
            if (componentCallbacksC0013n2 != null) {
                View view2 = componentCallbacksC0013n2.mView;
                ViewGroup viewGroup2 = componentCallbacksC0013n.mContainer;
                int iIndexOfChild = viewGroup2.indexOfChild(view2);
                int iIndexOfChild2 = viewGroup2.indexOfChild(componentCallbacksC0013n.mView);
                if (iIndexOfChild2 < iIndexOfChild) {
                    viewGroup2.removeViewAt(iIndexOfChild2);
                    viewGroup2.addView(componentCallbacksC0013n.mView, iIndexOfChild);
                }
            }
            if (componentCallbacksC0013n.mIsNewlyAdded && componentCallbacksC0013n.mContainer != null) {
                float f = componentCallbacksC0013n.kd;
                if (f > 0.0f) {
                    componentCallbacksC0013n.mView.setAlpha(f);
                }
                componentCallbacksC0013n.kd = 0.0f;
                componentCallbacksC0013n.mIsNewlyAdded = false;
                H hA = a(componentCallbacksC0013n, componentCallbacksC0013n.getNextTransition(), true, componentCallbacksC0013n.getNextTransitionStyle());
                if (hA != null) {
                    a(componentCallbacksC0013n.mView, hA);
                    Animation animation = hA.animation;
                    if (animation != null) {
                        componentCallbacksC0013n.mView.startAnimation(animation);
                    } else {
                        hA.animator.setTarget(componentCallbacksC0013n.mView);
                        hA.animator.start();
                    }
                }
            }
        }
        if (componentCallbacksC0013n.mHiddenChanged) {
            if (componentCallbacksC0013n.mView != null) {
                H hA2 = a(componentCallbacksC0013n, componentCallbacksC0013n.getNextTransition(), !componentCallbacksC0013n.mHidden, componentCallbacksC0013n.getNextTransitionStyle());
                if (hA2 == null || (animator = hA2.animator) == null) {
                    if (hA2 != null) {
                        a(componentCallbacksC0013n.mView, hA2);
                        componentCallbacksC0013n.mView.startAnimation(hA2.animation);
                        hA2.animation.start();
                    }
                    componentCallbacksC0013n.mView.setVisibility((!componentCallbacksC0013n.mHidden || componentCallbacksC0013n.isHideReplaced()) ? 0 : 8);
                    if (componentCallbacksC0013n.isHideReplaced()) {
                        componentCallbacksC0013n.setHideReplaced(false);
                    }
                } else {
                    animator.setTarget(componentCallbacksC0013n.mView);
                    if (!componentCallbacksC0013n.mHidden) {
                        componentCallbacksC0013n.mView.setVisibility(0);
                    } else if (componentCallbacksC0013n.isHideReplaced()) {
                        componentCallbacksC0013n.setHideReplaced(false);
                    } else {
                        ViewGroup viewGroup3 = componentCallbacksC0013n.mContainer;
                        View view3 = componentCallbacksC0013n.mView;
                        viewGroup3.startViewTransition(view3);
                        hA2.animator.addListener(new D(this, viewGroup3, view3, componentCallbacksC0013n));
                    }
                    a(componentCallbacksC0013n.mView, hA2);
                    hA2.animator.start();
                }
            }
            if (componentCallbacksC0013n.mAdded && componentCallbacksC0013n.mHasMenu && componentCallbacksC0013n.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            componentCallbacksC0013n.mHiddenChanged = false;
            boolean z = componentCallbacksC0013n.mHidden;
        }
    }

    @Override // android.support.v4.app.AbstractC0023y
    public boolean isStateSaved() {
        return this.mStateSaved || this.mStopped;
    }

    public void j(ComponentCallbacksC0013n componentCallbacksC0013n) {
        boolean z = !componentCallbacksC0013n.isInBackStack();
        if (!componentCallbacksC0013n.mDetached || z) {
            synchronized (this.mAdded) {
                this.mAdded.remove(componentCallbacksC0013n);
            }
            if (componentCallbacksC0013n.mHasMenu && componentCallbacksC0013n.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            componentCallbacksC0013n.mAdded = false;
            componentCallbacksC0013n.mRemoving = true;
        }
    }

    void k(ComponentCallbacksC0013n componentCallbacksC0013n) {
        if (componentCallbacksC0013n.jd == null) {
            return;
        }
        SparseArray sparseArray = this.mStateArray;
        if (sparseArray == null) {
            this.mStateArray = new SparseArray();
        } else {
            sparseArray.clear();
        }
        componentCallbacksC0013n.jd.saveHierarchyState(this.mStateArray);
        if (this.mStateArray.size() > 0) {
            componentCallbacksC0013n.mSavedViewState = this.mStateArray;
            this.mStateArray = null;
        }
    }

    public void l(ComponentCallbacksC0013n componentCallbacksC0013n) {
        if (componentCallbacksC0013n == null || (this.mActive.get(componentCallbacksC0013n.mIndex) == componentCallbacksC0013n && (componentCallbacksC0013n.mHost == null || componentCallbacksC0013n.mFragmentManager == this))) {
            this.mPrimaryNav = componentCallbacksC0013n;
            return;
        }
        throw new IllegalArgumentException("Fragment " + componentCallbacksC0013n + " is not an active fragment of FragmentManager " + this);
    }

    public void m(ComponentCallbacksC0013n componentCallbacksC0013n) {
        if (componentCallbacksC0013n.mHidden) {
            componentCallbacksC0013n.mHidden = false;
            componentCallbacksC0013n.mHiddenChanged = !componentCallbacksC0013n.mHiddenChanged;
        }
    }

    void moveToState(int i, boolean z) {
        AbstractC0019u abstractC0019u;
        if (this.mHost == null && i != 0) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.mCurState) {
            this.mCurState = i;
            if (this.mActive != null) {
                int size = this.mAdded.size();
                for (int i2 = 0; i2 < size; i2++) {
                    i((ComponentCallbacksC0013n) this.mAdded.get(i2));
                }
                int size2 = this.mActive.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mActive.valueAt(i3);
                    if (componentCallbacksC0013n != null && ((componentCallbacksC0013n.mRemoving || componentCallbacksC0013n.mDetached) && !componentCallbacksC0013n.mIsNewlyAdded)) {
                        i(componentCallbacksC0013n);
                    }
                }
                startPendingDeferredFragments();
                if (this.mNeedMenuInvalidate && (abstractC0019u = this.mHost) != null && this.mCurState == 5) {
                    ((C0015p) abstractC0019u).n.invalidateOptionsMenu();
                    this.mNeedMenuInvalidate = false;
                }
            }
        }
    }

    public void noteStateNotSaved() {
        O o;
        this.mSavedNonConfig = null;
        this.mStateSaved = false;
        this.mStopped = false;
        int size = this.mAdded.size();
        for (int i = 0; i < size; i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mAdded.get(i);
            if (componentCallbacksC0013n != null && (o = componentCallbacksC0013n.mChildFragmentManager) != null) {
                o.noteStateNotSaved();
            }
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        ComponentCallbacksC0013n componentCallbacksC0013n;
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, L.Fragment);
        if (attributeValue == null) {
            attributeValue = typedArrayObtainStyledAttributes.getString(0);
        }
        String str2 = attributeValue;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(1, -1);
        String string = typedArrayObtainStyledAttributes.getString(2);
        typedArrayObtainStyledAttributes.recycle();
        if (!ComponentCallbacksC0013n.b(this.mHost.getContext(), str2)) {
            return null;
        }
        int id = view != null ? view.getId() : 0;
        if (id == -1 && resourceId == -1 && string == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + str2);
        }
        ComponentCallbacksC0013n componentCallbacksC0013nFindFragmentById = resourceId != -1 ? findFragmentById(resourceId) : null;
        if (componentCallbacksC0013nFindFragmentById == null && string != null) {
            componentCallbacksC0013nFindFragmentById = findFragmentByTag(string);
        }
        if (componentCallbacksC0013nFindFragmentById == null && id != -1) {
            componentCallbacksC0013nFindFragmentById = findFragmentById(id);
        }
        if (componentCallbacksC0013nFindFragmentById == null) {
            ComponentCallbacksC0013n componentCallbacksC0013nInstantiate = this.mContainer.instantiate(context, str2, null);
            componentCallbacksC0013nInstantiate.mFromLayout = true;
            componentCallbacksC0013nInstantiate.mFragmentId = resourceId != 0 ? resourceId : id;
            componentCallbacksC0013nInstantiate.mContainerId = id;
            componentCallbacksC0013nInstantiate.mTag = string;
            componentCallbacksC0013nInstantiate.mInLayout = true;
            componentCallbacksC0013nInstantiate.mFragmentManager = this;
            AbstractC0019u abstractC0019u = this.mHost;
            componentCallbacksC0013nInstantiate.mHost = abstractC0019u;
            componentCallbacksC0013nInstantiate.onInflate(abstractC0019u.getContext(), attributeSet, componentCallbacksC0013nInstantiate.mSavedFragmentState);
            a(componentCallbacksC0013nInstantiate, true);
            componentCallbacksC0013n = componentCallbacksC0013nInstantiate;
        } else {
            if (componentCallbacksC0013nFindFragmentById.mInLayout) {
                throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + str2);
            }
            componentCallbacksC0013nFindFragmentById.mInLayout = true;
            AbstractC0019u abstractC0019u2 = this.mHost;
            componentCallbacksC0013nFindFragmentById.mHost = abstractC0019u2;
            if (!componentCallbacksC0013nFindFragmentById.mRetaining) {
                componentCallbacksC0013nFindFragmentById.onInflate(abstractC0019u2.getContext(), attributeSet, componentCallbacksC0013nFindFragmentById.mSavedFragmentState);
            }
            componentCallbacksC0013n = componentCallbacksC0013nFindFragmentById;
        }
        if (this.mCurState >= 1 || !componentCallbacksC0013n.mFromLayout) {
            a(componentCallbacksC0013n, this.mCurState, 0, 0, false);
        } else {
            a(componentCallbacksC0013n, 1, 0, 0, false);
        }
        View view2 = componentCallbacksC0013n.mView;
        if (view2 == null) {
            throw new IllegalStateException(b.a.a.a.a.b("Fragment ", str2, " did not create a view."));
        }
        if (resourceId != 0) {
            view2.setId(resourceId);
        }
        if (componentCallbacksC0013n.mView.getTag() == null) {
            componentCallbacksC0013n.mView.setTag(string);
        }
        return componentCallbacksC0013n.mView;
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Override // android.support.v4.app.AbstractC0023y
    public boolean popBackStackImmediate() {
        int size;
        AbstractC0023y abstractC0023yZ;
        Ya();
        execPendingActions();
        u(true);
        ComponentCallbacksC0013n componentCallbacksC0013n = this.mPrimaryNav;
        if (componentCallbacksC0013n != null && (abstractC0023yZ = componentCallbacksC0013n.z()) != null && abstractC0023yZ.popBackStackImmediate()) {
            return true;
        }
        ArrayList arrayList = this.mTmpRecords;
        ArrayList arrayList2 = this.mTmpIsPop;
        ArrayList arrayList3 = this.mBackStack;
        boolean z = false;
        if (arrayList3 != null && (size = arrayList3.size() - 1) >= 0) {
            arrayList.add(this.mBackStack.remove(size));
            arrayList2.add(true);
            z = true;
        }
        if (z) {
            this.mExecutingActions = true;
            try {
                c(this.mTmpRecords, this.mTmpIsPop);
                Za();
            } catch (Throwable th) {
                Za();
                throw th;
            }
        }
        doPendingDeferredStart();
        Xa();
        return z;
    }

    P retainNonConfig() {
        a(this.mSavedNonConfig);
        return this.mSavedNonConfig;
    }

    Parcelable saveAllState() {
        int size;
        int i;
        BackStackState[] backStackStateArr;
        int[] iArr;
        int size2;
        Bundle bundle;
        _a();
        SparseArray sparseArray = this.mActive;
        if (sparseArray == null) {
            i = 0;
            size = 0;
        } else {
            size = sparseArray.size();
            i = 0;
        }
        while (true) {
            backStackStateArr = null;
            if (i >= size) {
                break;
            }
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mActive.valueAt(i);
            if (componentCallbacksC0013n != null) {
                if (componentCallbacksC0013n.getAnimatingAway() != null) {
                    int stateAfterAnimating = componentCallbacksC0013n.getStateAfterAnimating();
                    View animatingAway = componentCallbacksC0013n.getAnimatingAway();
                    Animation animation = animatingAway.getAnimation();
                    if (animation != null) {
                        animation.cancel();
                        animatingAway.clearAnimation();
                    }
                    componentCallbacksC0013n.c((View) null);
                    a(componentCallbacksC0013n, stateAfterAnimating, 0, 0, false);
                } else if (componentCallbacksC0013n.getAnimator() != null) {
                    componentCallbacksC0013n.getAnimator().end();
                }
            }
            i++;
        }
        execPendingActions();
        this.mStateSaved = true;
        this.mSavedNonConfig = null;
        SparseArray sparseArray2 = this.mActive;
        if (sparseArray2 == null || sparseArray2.size() <= 0) {
            return null;
        }
        int size3 = this.mActive.size();
        FragmentState[] fragmentStateArr = new FragmentState[size3];
        boolean z = false;
        for (int i2 = 0; i2 < size3; i2++) {
            ComponentCallbacksC0013n componentCallbacksC0013n2 = (ComponentCallbacksC0013n) this.mActive.valueAt(i2);
            if (componentCallbacksC0013n2 != null) {
                if (componentCallbacksC0013n2.mIndex < 0) {
                    a(new IllegalStateException("Failure saving state: active " + componentCallbacksC0013n2 + " has cleared index: " + componentCallbacksC0013n2.mIndex));
                    throw null;
                }
                FragmentState fragmentState = new FragmentState(componentCallbacksC0013n2);
                fragmentStateArr[i2] = fragmentState;
                if (componentCallbacksC0013n2.mState <= 0 || fragmentState.mSavedFragmentState != null) {
                    fragmentState.mSavedFragmentState = componentCallbacksC0013n2.mSavedFragmentState;
                } else {
                    if (this.mStateBundle == null) {
                        this.mStateBundle = new Bundle();
                    }
                    componentCallbacksC0013n2.performSaveInstanceState(this.mStateBundle);
                    d(componentCallbacksC0013n2, this.mStateBundle, false);
                    if (this.mStateBundle.isEmpty()) {
                        bundle = null;
                    } else {
                        bundle = this.mStateBundle;
                        this.mStateBundle = null;
                    }
                    if (componentCallbacksC0013n2.mView != null) {
                        k(componentCallbacksC0013n2);
                    }
                    if (componentCallbacksC0013n2.mSavedViewState != null) {
                        if (bundle == null) {
                            bundle = new Bundle();
                        }
                        bundle.putSparseParcelableArray("android:view_state", componentCallbacksC0013n2.mSavedViewState);
                    }
                    if (!componentCallbacksC0013n2.mUserVisibleHint) {
                        if (bundle == null) {
                            bundle = new Bundle();
                        }
                        bundle.putBoolean("android:user_visible_hint", componentCallbacksC0013n2.mUserVisibleHint);
                    }
                    fragmentState.mSavedFragmentState = bundle;
                    ComponentCallbacksC0013n componentCallbacksC0013n3 = componentCallbacksC0013n2.mTarget;
                    if (componentCallbacksC0013n3 != null) {
                        if (componentCallbacksC0013n3.mIndex < 0) {
                            a(new IllegalStateException("Failure saving state: " + componentCallbacksC0013n2 + " has target not in fragment manager: " + componentCallbacksC0013n2.mTarget));
                            throw null;
                        }
                        if (fragmentState.mSavedFragmentState == null) {
                            fragmentState.mSavedFragmentState = new Bundle();
                        }
                        Bundle bundle2 = fragmentState.mSavedFragmentState;
                        ComponentCallbacksC0013n componentCallbacksC0013n4 = componentCallbacksC0013n2.mTarget;
                        int i3 = componentCallbacksC0013n4.mIndex;
                        if (i3 < 0) {
                            a(new IllegalStateException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n4, " is not currently in the FragmentManager")));
                            throw null;
                        }
                        bundle2.putInt("android:target_state", i3);
                        int i4 = componentCallbacksC0013n2.mTargetRequestCode;
                        if (i4 != 0) {
                            fragmentState.mSavedFragmentState.putInt("android:target_req_state", i4);
                        }
                    }
                }
                z = true;
            }
        }
        if (!z) {
            return null;
        }
        int size4 = this.mAdded.size();
        if (size4 > 0) {
            iArr = new int[size4];
            for (int i5 = 0; i5 < size4; i5++) {
                iArr[i5] = ((ComponentCallbacksC0013n) this.mAdded.get(i5)).mIndex;
                if (iArr[i5] < 0) {
                    StringBuilder sbE = b.a.a.a.a.e("Failure saving state: active ");
                    sbE.append(this.mAdded.get(i5));
                    sbE.append(" has cleared index: ");
                    sbE.append(iArr[i5]);
                    a(new IllegalStateException(sbE.toString()));
                    throw null;
                }
            }
        } else {
            iArr = null;
        }
        ArrayList arrayList = this.mBackStack;
        if (arrayList != null && (size2 = arrayList.size()) > 0) {
            backStackStateArr = new BackStackState[size2];
            for (int i6 = 0; i6 < size2; i6++) {
                backStackStateArr[i6] = new BackStackState((C0004e) this.mBackStack.get(i6));
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.mActive = fragmentStateArr;
        fragmentManagerState.mAdded = iArr;
        fragmentManagerState.mBackStack = backStackStateArr;
        ComponentCallbacksC0013n componentCallbacksC0013n5 = this.mPrimaryNav;
        if (componentCallbacksC0013n5 != null) {
            fragmentManagerState.mPrimaryNavActiveIndex = componentCallbacksC0013n5.mIndex;
        }
        fragmentManagerState.mNextFragmentIndex = this.mNextFragmentIndex;
        saveNonConfig();
        return fragmentManagerState;
    }

    void saveNonConfig() {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        P p;
        if (this.mActive != null) {
            arrayList = null;
            arrayList2 = null;
            arrayList3 = null;
            for (int i = 0; i < this.mActive.size(); i++) {
                ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mActive.valueAt(i);
                if (componentCallbacksC0013n != null) {
                    if (componentCallbacksC0013n.mRetainInstance) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(componentCallbacksC0013n);
                        ComponentCallbacksC0013n componentCallbacksC0013n2 = componentCallbacksC0013n.mTarget;
                        componentCallbacksC0013n.mTargetIndex = componentCallbacksC0013n2 != null ? componentCallbacksC0013n2.mIndex : -1;
                    }
                    O o = componentCallbacksC0013n.mChildFragmentManager;
                    if (o != null) {
                        o.saveNonConfig();
                        p = componentCallbacksC0013n.mChildFragmentManager.mSavedNonConfig;
                    } else {
                        p = componentCallbacksC0013n.mChildNonConfig;
                    }
                    if (arrayList2 == null && p != null) {
                        arrayList2 = new ArrayList(this.mActive.size());
                        for (int i2 = 0; i2 < i; i2++) {
                            arrayList2.add(null);
                        }
                    }
                    if (arrayList2 != null) {
                        arrayList2.add(p);
                    }
                    if (arrayList3 == null && componentCallbacksC0013n.oa != null) {
                        arrayList3 = new ArrayList(this.mActive.size());
                        for (int i3 = 0; i3 < i; i3++) {
                            arrayList3.add(null);
                        }
                    }
                    if (arrayList3 != null) {
                        arrayList3.add(componentCallbacksC0013n.oa);
                    }
                }
            }
        } else {
            arrayList = null;
            arrayList2 = null;
            arrayList3 = null;
        }
        if (arrayList == null && arrayList2 == null && arrayList3 == null) {
            this.mSavedNonConfig = null;
        } else {
            this.mSavedNonConfig = new P(arrayList, arrayList2, arrayList3);
        }
    }

    void startPendingDeferredFragments() {
        if (this.mActive == null) {
            return;
        }
        for (int i = 0; i < this.mActive.size(); i++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = (ComponentCallbacksC0013n) this.mActive.valueAt(i);
            if (componentCallbacksC0013n != null && componentCallbacksC0013n.mDeferStart) {
                if (this.mExecutingActions) {
                    this.mHavePendingDeferredStart = true;
                } else {
                    componentCallbacksC0013n.mDeferStart = false;
                    a(componentCallbacksC0013n, this.mCurState, 0, 0, false);
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        ComponentCallbacksC0013n componentCallbacksC0013n = this.mParent;
        if (componentCallbacksC0013n != null) {
            a.b.a.a.buildShortClassTag(componentCallbacksC0013n, sb);
        } else {
            a.b.a.a.buildShortClassTag(this.mHost, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    void a(ComponentCallbacksC0013n componentCallbacksC0013n, Bundle bundle, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.a(componentCallbacksC0013n, bundle, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    void b(ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.b(componentCallbacksC0013n, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    void c(ComponentCallbacksC0013n componentCallbacksC0013n, Bundle bundle, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.c(componentCallbacksC0013n, bundle, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    void d(ComponentCallbacksC0013n componentCallbacksC0013n, Bundle bundle, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.d(componentCallbacksC0013n, bundle, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    void a(ComponentCallbacksC0013n componentCallbacksC0013n, Context context, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.a(componentCallbacksC0013n, context, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    void b(ComponentCallbacksC0013n componentCallbacksC0013n, Context context, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.b(componentCallbacksC0013n, context, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    void a(ComponentCallbacksC0013n componentCallbacksC0013n, View view, Bundle bundle, boolean z) {
        ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mParent;
        if (componentCallbacksC0013n2 != null) {
            O o = componentCallbacksC0013n2.mFragmentManager;
            if (o instanceof O) {
                o.a(componentCallbacksC0013n, view, bundle, true);
            }
        }
        for (K k : this.mLifecycleCallbacks) {
            if (!z || k.ud) {
                AbstractC0021w abstractC0021w = k.mCallback;
                throw null;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:63:0x014e  */
    private void a(ArrayList arrayList, ArrayList arrayList2, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        ArrayList arrayList3 = arrayList;
        boolean z = ((C0004e) arrayList3.get(i)).mReorderingAllowed;
        ArrayList arrayList4 = this.mTmpAddedFragments;
        if (arrayList4 == null) {
            this.mTmpAddedFragments = new ArrayList();
        } else {
            arrayList4.clear();
        }
        this.mTmpAddedFragments.addAll(this.mAdded);
        ComponentCallbacksC0013n componentCallbacksC0013n = this.mPrimaryNav;
        int i8 = i;
        boolean z2 = false;
        while (true) {
            int i9 = 1;
            if (i8 < i2) {
                C0004e c0004e = (C0004e) arrayList3.get(i8);
                int i10 = 3;
                if (!((Boolean) arrayList2.get(i8)).booleanValue()) {
                    ArrayList arrayList5 = this.mTmpAddedFragments;
                    ComponentCallbacksC0013n componentCallbacksC0013n2 = componentCallbacksC0013n;
                    int i11 = 0;
                    while (i11 < c0004e.mOps.size()) {
                        C0003d c0003d = (C0003d) c0004e.mOps.get(i11);
                        int i12 = c0003d.cmd;
                        if (i12 != i9) {
                            if (i12 != 2) {
                                if (i12 == i10 || i12 == 6) {
                                    arrayList5.remove(c0003d.fragment);
                                    ComponentCallbacksC0013n componentCallbacksC0013n3 = c0003d.fragment;
                                    if (componentCallbacksC0013n3 == componentCallbacksC0013n2) {
                                        c0004e.mOps.add(i11, new C0003d(9, componentCallbacksC0013n3));
                                        i11++;
                                        componentCallbacksC0013n2 = null;
                                    }
                                } else if (i12 == 7) {
                                    i7 = 1;
                                } else if (i12 == 8) {
                                    c0004e.mOps.add(i11, new C0003d(9, componentCallbacksC0013n2));
                                    i11++;
                                    componentCallbacksC0013n2 = c0003d.fragment;
                                }
                                i7 = 1;
                            } else {
                                ComponentCallbacksC0013n componentCallbacksC0013n4 = c0003d.fragment;
                                int i13 = componentCallbacksC0013n4.mContainerId;
                                int i14 = i11;
                                ComponentCallbacksC0013n componentCallbacksC0013n5 = componentCallbacksC0013n2;
                                int size = arrayList5.size() - 1;
                                boolean z3 = false;
                                while (size >= 0) {
                                    ComponentCallbacksC0013n componentCallbacksC0013n6 = (ComponentCallbacksC0013n) arrayList5.get(size);
                                    if (componentCallbacksC0013n6.mContainerId != i13) {
                                        i13 = i13;
                                    } else if (componentCallbacksC0013n6 == componentCallbacksC0013n4) {
                                        i13 = i13;
                                        z3 = true;
                                    } else {
                                        if (componentCallbacksC0013n6 == componentCallbacksC0013n5) {
                                            c0004e.mOps.add(i14, new C0003d(9, componentCallbacksC0013n6));
                                            i14++;
                                            componentCallbacksC0013n5 = null;
                                        }
                                        C0003d c0003d2 = new C0003d(3, componentCallbacksC0013n6);
                                        c0003d2.enterAnim = c0003d.enterAnim;
                                        c0003d2.popEnterAnim = c0003d.popEnterAnim;
                                        c0003d2.exitAnim = c0003d.exitAnim;
                                        c0003d2.popExitAnim = c0003d.popExitAnim;
                                        c0004e.mOps.add(i14, c0003d2);
                                        arrayList5.remove(componentCallbacksC0013n6);
                                        i14++;
                                    }
                                    size--;
                                    i13 = i13;
                                }
                                if (z3) {
                                    c0004e.mOps.remove(i14);
                                    i11 = i14 - 1;
                                    i7 = 1;
                                } else {
                                    i7 = 1;
                                    c0003d.cmd = 1;
                                    arrayList5.add(componentCallbacksC0013n4);
                                    i11 = i14;
                                }
                                componentCallbacksC0013n2 = componentCallbacksC0013n5;
                            }
                            i11 += i7;
                            i9 = i7;
                            i10 = 3;
                        } else {
                            i7 = i9;
                        }
                        arrayList5.add(c0003d.fragment);
                        i11 += i7;
                        i9 = i7;
                        i10 = 3;
                    }
                    componentCallbacksC0013n = componentCallbacksC0013n2;
                } else {
                    ArrayList arrayList6 = this.mTmpAddedFragments;
                    ComponentCallbacksC0013n componentCallbacksC0013n7 = componentCallbacksC0013n;
                    for (int i15 = 0; i15 < c0004e.mOps.size(); i15++) {
                        C0003d c0003d3 = (C0003d) c0004e.mOps.get(i15);
                        int i16 = c0003d3.cmd;
                        if (i16 != 1) {
                            if (i16 != 3) {
                                switch (i16) {
                                    case 6:
                                        arrayList6.add(c0003d3.fragment);
                                        break;
                                    case 8:
                                        componentCallbacksC0013n7 = null;
                                        break;
                                    case 9:
                                        componentCallbacksC0013n7 = c0003d3.fragment;
                                        break;
                                }
                            } else {
                                arrayList6.add(c0003d3.fragment);
                            }
                        }
                        arrayList6.remove(c0003d3.fragment);
                    }
                    componentCallbacksC0013n = componentCallbacksC0013n7;
                }
                z2 = z2 || c0004e.mAddToBackStack;
                i8++;
                arrayList3 = arrayList;
            } else {
                this.mTmpAddedFragments.clear();
                if (!z) {
                    ba.a(this, arrayList, arrayList2, i, i2, false);
                }
                int i17 = i;
                while (i17 < i2) {
                    C0004e c0004e2 = (C0004e) arrayList.get(i17);
                    if (((Boolean) arrayList2.get(i17)).booleanValue()) {
                        c0004e2.bumpBackStackNesting(-1);
                        c0004e2.executePopOps(i17 == i2 + (-1));
                    } else {
                        c0004e2.bumpBackStackNesting(1);
                        c0004e2.executeOps();
                    }
                    i17++;
                }
                if (z) {
                    a.b.c.b.d dVar = new a.b.c.b.d();
                    c(dVar);
                    i3 = i;
                    int i18 = i2;
                    for (int i19 = i2 - 1; i19 >= i3; i19--) {
                        C0004e c0004e3 = (C0004e) arrayList.get(i19);
                        boolean zBooleanValue = ((Boolean) arrayList2.get(i19)).booleanValue();
                        if (c0004e3.isPostponed() && !c0004e3.interactsWith(arrayList, i19 + 1, i2)) {
                            if (this.mPostponedTransactions == null) {
                                this.mPostponedTransactions = new ArrayList();
                            }
                            N n = new N(c0004e3, zBooleanValue);
                            this.mPostponedTransactions.add(n);
                            c0004e3.d(n);
                            if (zBooleanValue) {
                                c0004e3.executeOps();
                            } else {
                                c0004e3.executePopOps(false);
                            }
                            i18--;
                            if (i19 != i18) {
                                arrayList.remove(i19);
                                arrayList.add(i18, c0004e3);
                            }
                            c(dVar);
                        }
                    }
                    i4 = 0;
                    int size2 = dVar.size();
                    for (int i20 = 0; i20 < size2; i20++) {
                        ComponentCallbacksC0013n componentCallbacksC0013n8 = (ComponentCallbacksC0013n) dVar.valueAt(i20);
                        if (!componentCallbacksC0013n8.mAdded) {
                            View view = componentCallbacksC0013n8.mView;
                            componentCallbacksC0013n8.kd = view.getAlpha();
                            view.setAlpha(0.0f);
                        }
                    }
                    i5 = i18;
                } else {
                    i3 = i;
                    i4 = 0;
                    i5 = i2;
                }
                if (i5 != i3 && z) {
                    ba.a(this, arrayList, arrayList2, i, i5, true);
                    moveToState(this.mCurState, true);
                }
                while (i3 < i2) {
                    C0004e c0004e4 = (C0004e) arrayList.get(i3);
                    if (((Boolean) arrayList2.get(i3)).booleanValue() && (i6 = c0004e4.mIndex) >= 0) {
                        freeBackStackIndex(i6);
                        c0004e4.mIndex = -1;
                    }
                    ArrayList arrayList7 = c0004e4.mCommitRunnables;
                    if (arrayList7 != null) {
                        int size3 = arrayList7.size();
                        for (int i21 = i4; i21 < size3; i21++) {
                            ((Runnable) c0004e4.mCommitRunnables.get(i21)).run();
                        }
                        c0004e4.mCommitRunnables = null;
                    }
                    i3++;
                }
                if (!z2 || this.mBackStackChangeListeners == null) {
                    return;
                }
                while (i4 < this.mBackStackChangeListeners.size()) {
                    ((InterfaceC0022x) this.mBackStackChangeListeners.get(i4)).onBackStackChanged();
                    i4++;
                }
                return;
            }
        }
    }

    H a(ComponentCallbacksC0013n componentCallbacksC0013n, int i, boolean z, int i2) {
        byte b2;
        Window window;
        boolean z2;
        int nextAnim = componentCallbacksC0013n.getNextAnim();
        if (nextAnim != 0) {
            boolean zEquals = "anim".equals(this.mHost.getContext().getResources().getResourceTypeName(nextAnim));
            if (zEquals) {
                try {
                    Animation animationLoadAnimation = AnimationUtils.loadAnimation(this.mHost.getContext(), nextAnim);
                    if (animationLoadAnimation != null) {
                        return new H(animationLoadAnimation, (RunnableC0024z) null);
                    }
                    z2 = true;
                } catch (Resources.NotFoundException e) {
                    throw e;
                } catch (RuntimeException unused) {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            if (!z2) {
                try {
                    Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(this.mHost.getContext(), nextAnim);
                    if (animatorLoadAnimator != null) {
                        return new H(animatorLoadAnimator, (RunnableC0024z) null);
                    }
                } catch (RuntimeException e2) {
                    if (zEquals) {
                        throw e2;
                    }
                    Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(this.mHost.getContext(), nextAnim);
                    if (animationLoadAnimation2 != null) {
                        return new H(animationLoadAnimation2, (RunnableC0024z) null);
                    }
                }
            }
        }
        if (i == 0) {
            return null;
        }
        if (i == 4097) {
            b2 = z ? (byte) 1 : (byte) 2;
        } else if (i == 4099) {
            b2 = z ? (byte) 5 : (byte) 6;
        } else if (i != 8194) {
            b2 = -1;
        } else {
            b2 = z ? (byte) 3 : (byte) 4;
        }
        if (b2 < 0) {
            return null;
        }
        switch (b2) {
            case 1:
                this.mHost.getContext();
                return a(1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                this.mHost.getContext();
                return a(1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                this.mHost.getContext();
                return a(0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                this.mHost.getContext();
                return a(1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                this.mHost.getContext();
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation.setInterpolator(td);
                alphaAnimation.setDuration(220L);
                return new H(alphaAnimation, (RunnableC0024z) null);
            case 6:
                this.mHost.getContext();
                AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation2.setInterpolator(td);
                alphaAnimation2.setDuration(220L);
                return new H(alphaAnimation2, (RunnableC0024z) null);
            default:
                if (i2 == 0) {
                    if ((((C0015p) this.mHost).n.getWindow() != null) && (window = ((C0015p) this.mHost).n.getWindow()) != null) {
                        int i3 = window.getAttributes().windowAnimations;
                    }
                }
                return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x01a3  */
    /* JADX WARN: Code duplicated, block: B:102:0x01ae  */
    /* JADX WARN: Code duplicated, block: B:105:0x01be  */
    /* JADX WARN: Code duplicated, block: B:107:0x01c2  */
    /* JADX WARN: Code duplicated, block: B:108:0x01c4  */
    /* JADX WARN: Code duplicated, block: B:110:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:113:0x01d2  */
    /* JADX WARN: Code duplicated, block: B:114:0x01df  */
    /* JADX WARN: Code duplicated, block: B:116:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:118:0x01f6  */
    /* JADX WARN: Code duplicated, block: B:127:0x021c  */
    /* JADX WARN: Code duplicated, block: B:129:0x0225  */
    /* JADX WARN: Code duplicated, block: B:131:0x0232  */
    /* JADX WARN: Code duplicated, block: B:133:0x0236  */
    /* JADX WARN: Code duplicated, block: B:135:0x023a  */
    /* JADX WARN: Code duplicated, block: B:137:0x023e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:138:0x0240  */
    /* JADX WARN: Code duplicated, block: B:140:0x024a  */
    /* JADX WARN: Code duplicated, block: B:144:0x0252  */
    /* JADX WARN: Code duplicated, block: B:145:0x0254 A[Catch: NotFoundException -> 0x027f, TryCatch #0 {NotFoundException -> 0x027f, blocks: (B:142:0x024e, B:147:0x025a, B:148:0x0265, B:149:0x027e, B:145:0x0254), top: B:304:0x024e }] */
    /* JADX WARN: Code duplicated, block: B:147:0x025a A[Catch: NotFoundException -> 0x027f, TryCatch #0 {NotFoundException -> 0x027f, blocks: (B:142:0x024e, B:147:0x025a, B:148:0x0265, B:149:0x027e, B:145:0x0254), top: B:304:0x024e }] */
    /* JADX WARN: Code duplicated, block: B:148:0x0265 A[Catch: NotFoundException -> 0x027f, TryCatch #0 {NotFoundException -> 0x027f, blocks: (B:142:0x024e, B:147:0x025a, B:148:0x0265, B:149:0x027e, B:145:0x0254), top: B:304:0x024e }] */
    /* JADX WARN: Code duplicated, block: B:153:0x02ad  */
    /* JADX WARN: Code duplicated, block: B:155:0x02be  */
    /* JADX WARN: Code duplicated, block: B:158:0x02d0  */
    /* JADX WARN: Code duplicated, block: B:160:0x02d7  */
    /* JADX WARN: Code duplicated, block: B:163:0x02e0  */
    /* JADX WARN: Code duplicated, block: B:169:0x02fa  */
    /* JADX WARN: Code duplicated, block: B:171:0x02fe  */
    /* JADX WARN: Code duplicated, block: B:174:0x0306  */
    /* JADX WARN: Code duplicated, block: B:177:0x0313  */
    /* JADX WARN: Code duplicated, block: B:179:0x0317  */
    /* JADX WARN: Code duplicated, block: B:182:0x0323  */
    /* JADX WARN: Code duplicated, block: B:184:0x032b  */
    /* JADX WARN: Code duplicated, block: B:187:0x0339  */
    /* JADX WARN: Code duplicated, block: B:189:0x033d  */
    /* JADX WARN: Code duplicated, block: B:192:0x0346  */
    /* JADX WARN: Code duplicated, block: B:249:0x0453  */
    /* JADX WARN: Code duplicated, block: B:251:0x0457  */
    /* JADX WARN: Code duplicated, block: B:253:0x045d  */
    /* JADX WARN: Code duplicated, block: B:254:0x0468  */
    /* JADX WARN: Code duplicated, block: B:256:0x046e  */
    /* JADX WARN: Code duplicated, block: B:302:0x0552  */
    /* JADX WARN: Code duplicated, block: B:304:0x024e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:305:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x011a  */
    /* JADX WARN: Code duplicated, block: B:77:0x011d  */
    /* JADX WARN: Code duplicated, block: B:80:0x0125  */
    /* JADX WARN: Code duplicated, block: B:82:0x0131  */
    /* JADX WARN: Code duplicated, block: B:84:0x0135  */
    /* JADX WARN: Code duplicated, block: B:85:0x013f  */
    /* JADX WARN: Code duplicated, block: B:89:0x0179  */
    /* JADX WARN: Code duplicated, block: B:90:0x017b  */
    /* JADX WARN: Code duplicated, block: B:92:0x0181  */
    /* JADX WARN: Code duplicated, block: B:95:0x0189  */
    /* JADX WARN: Code duplicated, block: B:97:0x018d  */
    /* JADX WARN: Instruction removed from duplicated block: B:148:0x0265, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:302:0x0552, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:85:0x013f, please report this as an issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v118, types: [android.os.Bundle, android.view.View, android.view.ViewGroup, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v120 */
    /* JADX WARN: Type inference failed for: r0v121 */
    /* JADX WARN: Type inference failed for: r0v122 */
    /* JADX WARN: Type inference failed for: r0v123, types: [android.os.Bundle, android.util.SparseArray] */
    /* JADX WARN: Type inference failed for: r0v124 */
    /* JADX WARN: Type inference failed for: r0v125 */
    /* JADX WARN: Type inference failed for: r0v126 */
    /* JADX WARN: Type inference failed for: r0v134 */
    /* JADX WARN: Type inference failed for: r0v79 */
    /* JADX WARN: Type inference failed for: r17v0, types: [android.support.v4.app.n, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v51 */
    /* JADX WARN: Type inference failed for: r1v52, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r1v71, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r1v75 */
    /* JADX WARN: Type inference failed for: r1v85 */
    /* JADX WARN: Type inference failed for: r1v86 */
    /* JADX WARN: Type inference failed for: r1v87 */
    void a(ComponentCallbacksC0013n componentCallbacksC0013n, int i, int i2, int i3, boolean z) {
        int i4;
        ViewGroup viewGroup;
        Activity activity;
        Activity activity2;
        AbstractC0019u abstractC0019u;
        ComponentCallbacksC0013n componentCallbacksC0013n2;
        O o;
        ComponentCallbacksC0013n componentCallbacksC0013n3;
        AbstractC0019u abstractC0019u2;
        Activity activity3;
        ?? r0;
        O o2;
        O o3;
        boolean z2;
        Object obj;
        ComponentCallbacksC0013n componentCallbacksC0013n4;
        ComponentCallbacksC0013n componentCallbacksC0013n5;
        O o4;
        O o5;
        int i5;
        ?? r1;
        View view;
        boolean z3;
        ViewGroup viewGroup2;
        String resourceName;
        AbstractC0019u abstractC0019u3;
        ?? context;
        View view2;
        ?? r2;
        int i6;
        int i7 = 1;
        if (!componentCallbacksC0013n.mAdded || componentCallbacksC0013n.mDetached) {
            i4 = i;
            if (i4 > 1) {
                i4 = 1;
            }
        } else {
            i4 = i;
        }
        if (componentCallbacksC0013n.mRemoving && i4 > (i6 = componentCallbacksC0013n.mState)) {
            i4 = (i6 == 0 && componentCallbacksC0013n.isInBackStack()) ? 1 : componentCallbacksC0013n.mState;
        }
        int i8 = 4;
        int i9 = (!componentCallbacksC0013n.mDeferStart || componentCallbacksC0013n.mState >= 4 || i4 <= 3) ? i4 : 3;
        int i10 = componentCallbacksC0013n.mState;
        if (i10 > i9) {
            if (i10 > i9) {
                if (i10 != 1) {
                    if (i10 != 2) {
                        if (i10 != 3) {
                            if (i10 != 4) {
                                if (i10 == 5) {
                                    if (i9 < 5) {
                                        componentCallbacksC0013n.performPause();
                                        d(componentCallbacksC0013n, false);
                                    }
                                }
                            }
                            if (i9 < 4) {
                                componentCallbacksC0013n.tc.b(Lifecycle$Event.ON_STOP);
                                O o6 = componentCallbacksC0013n.mChildFragmentManager;
                                if (o6 != null) {
                                    o6.dispatchStop();
                                }
                                componentCallbacksC0013n.mState = 3;
                                componentCallbacksC0013n.mCalled = false;
                                componentCallbacksC0013n.mCalled = true;
                                if (componentCallbacksC0013n.mCalled) {
                                    g(componentCallbacksC0013n, false);
                                } else {
                                    throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n, " did not call through to super.onStop()"));
                                }
                            }
                        }
                        if (i9 < 3) {
                            O o7 = componentCallbacksC0013n.mChildFragmentManager;
                            if (o7 != null) {
                                o7.M(2);
                            }
                            componentCallbacksC0013n.mState = 2;
                        }
                    }
                    if (i9 < 2) {
                        if (componentCallbacksC0013n.mView != null && (!((C0015p) this.mHost).n.isFinishing()) && componentCallbacksC0013n.mSavedViewState == null) {
                            k(componentCallbacksC0013n);
                        }
                        componentCallbacksC0013n.performDestroyView();
                        h(componentCallbacksC0013n, false);
                        View view3 = componentCallbacksC0013n.mView;
                        if (view3 != null && (viewGroup = componentCallbacksC0013n.mContainer) != null) {
                            viewGroup.endViewTransition(view3);
                            componentCallbacksC0013n.mView.clearAnimation();
                            H hA = (this.mCurState <= 0 || this.mDestroyed || componentCallbacksC0013n.mView.getVisibility() != 0 || componentCallbacksC0013n.kd < 0.0f) ? null : a((ComponentCallbacksC0013n) componentCallbacksC0013n, i2, false, i3);
                            componentCallbacksC0013n.kd = 0.0f;
                            if (hA != null) {
                                View view4 = componentCallbacksC0013n.mView;
                                ViewGroup viewGroup3 = componentCallbacksC0013n.mContainer;
                                viewGroup3.startViewTransition(view4);
                                componentCallbacksC0013n.setStateAfterAnimating(i9);
                                Animation animation = hA.animation;
                                if (animation != null) {
                                    J j = new J(animation, viewGroup3, view4);
                                    componentCallbacksC0013n.c(componentCallbacksC0013n.mView);
                                    j.setAnimationListener(new B(this, a(j), viewGroup3, componentCallbacksC0013n));
                                    a(view4, hA);
                                    componentCallbacksC0013n.mView.startAnimation(j);
                                } else {
                                    Animator animator = hA.animator;
                                    componentCallbacksC0013n.a(animator);
                                    animator.addListener(new C(this, viewGroup3, view4, componentCallbacksC0013n));
                                    animator.setTarget(componentCallbacksC0013n.mView);
                                    a(componentCallbacksC0013n.mView, hA);
                                    animator.start();
                                }
                            }
                            componentCallbacksC0013n.mContainer.removeView(componentCallbacksC0013n.mView);
                        }
                        componentCallbacksC0013n.mContainer = null;
                        componentCallbacksC0013n.mView = null;
                        componentCallbacksC0013n.md = null;
                        componentCallbacksC0013n.nd.setValue(null);
                        componentCallbacksC0013n.jd = null;
                        componentCallbacksC0013n.mInLayout = false;
                    }
                    if (i9 >= 1) {
                        if (this.mDestroyed) {
                            if (componentCallbacksC0013n.getAnimatingAway() != null) {
                                View animatingAway = componentCallbacksC0013n.getAnimatingAway();
                                componentCallbacksC0013n.c(null);
                                animatingAway.clearAnimation();
                            } else if (componentCallbacksC0013n.getAnimator() != null) {
                                Animator animator2 = componentCallbacksC0013n.getAnimator();
                                componentCallbacksC0013n.a(null);
                                animator2.cancel();
                            }
                        }
                        if (componentCallbacksC0013n.getAnimatingAway() != null) {
                        }
                        componentCallbacksC0013n.setStateAfterAnimating(i9);
                    }
                } else if (i9 >= 1) {
                    if (this.mDestroyed) {
                        if (componentCallbacksC0013n.getAnimatingAway() != null) {
                            View animatingAway2 = componentCallbacksC0013n.getAnimatingAway();
                            componentCallbacksC0013n.c(null);
                            animatingAway2.clearAnimation();
                        } else if (componentCallbacksC0013n.getAnimator() != null) {
                            Animator animator3 = componentCallbacksC0013n.getAnimator();
                            componentCallbacksC0013n.a(null);
                            animator3.cancel();
                        }
                    }
                    if (componentCallbacksC0013n.getAnimatingAway() != null && componentCallbacksC0013n.getAnimator() == null) {
                        if (!componentCallbacksC0013n.mRetaining) {
                            componentCallbacksC0013n.tc.b(Lifecycle$Event.ON_DESTROY);
                            O o8 = componentCallbacksC0013n.mChildFragmentManager;
                            if (o8 != null) {
                                o8.dispatchDestroy();
                            }
                            componentCallbacksC0013n.mState = 0;
                            componentCallbacksC0013n.mCalled = false;
                            componentCallbacksC0013n.mIsCreated = false;
                            componentCallbacksC0013n.mCalled = true;
                            android.arch.lifecycle.r rVar = componentCallbacksC0013n.oa;
                            if (rVar != null && !componentCallbacksC0013n.mHost.mFragmentManager.mStateSaved) {
                                rVar.clear();
                            }
                            if (componentCallbacksC0013n.mCalled) {
                                componentCallbacksC0013n.mChildFragmentManager = null;
                                b((ComponentCallbacksC0013n) componentCallbacksC0013n, false);
                            } else {
                                throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n, " did not call through to super.onDestroy()"));
                            }
                        } else {
                            componentCallbacksC0013n.mState = 0;
                        }
                        componentCallbacksC0013n.mCalled = false;
                        componentCallbacksC0013n.mCalled = true;
                        componentCallbacksC0013n.mLayoutInflater = null;
                        if (componentCallbacksC0013n.mCalled) {
                            O o9 = componentCallbacksC0013n.mChildFragmentManager;
                            if (o9 != null) {
                                if (!componentCallbacksC0013n.mRetaining) {
                                    throw new IllegalStateException("Child FragmentManager of " + ((Object) componentCallbacksC0013n) + " was not  destroyed and this fragment is not retaining instance");
                                }
                                o9.dispatchDestroy();
                                componentCallbacksC0013n.mChildFragmentManager = null;
                            }
                            c((ComponentCallbacksC0013n) componentCallbacksC0013n, false);
                            if (!z) {
                                if (!componentCallbacksC0013n.mRetaining) {
                                    int i11 = componentCallbacksC0013n.mIndex;
                                    if (i11 >= 0) {
                                        this.mActive.put(i11, null);
                                        componentCallbacksC0013n.mIndex = -1;
                                        componentCallbacksC0013n.mWho = null;
                                        componentCallbacksC0013n.mAdded = false;
                                        componentCallbacksC0013n.mRemoving = false;
                                        componentCallbacksC0013n.mFromLayout = false;
                                        componentCallbacksC0013n.mInLayout = false;
                                        componentCallbacksC0013n.mRestored = false;
                                        componentCallbacksC0013n.mBackStackNesting = 0;
                                        componentCallbacksC0013n.mFragmentManager = null;
                                        componentCallbacksC0013n.mChildFragmentManager = null;
                                        componentCallbacksC0013n.mHost = null;
                                        componentCallbacksC0013n.mFragmentId = 0;
                                        componentCallbacksC0013n.mContainerId = 0;
                                        componentCallbacksC0013n.mTag = null;
                                        componentCallbacksC0013n.mHidden = false;
                                        componentCallbacksC0013n.mDetached = false;
                                        componentCallbacksC0013n.mRetaining = false;
                                    }
                                } else {
                                    componentCallbacksC0013n.mHost = null;
                                    componentCallbacksC0013n.mParentFragment = null;
                                    componentCallbacksC0013n.mFragmentManager = null;
                                }
                            }
                        } else {
                            throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n, " did not call through to super.onDetach()"));
                        }
                    } else {
                        componentCallbacksC0013n.setStateAfterAnimating(i9);
                    }
                }
            }
            if (componentCallbacksC0013n.mState != i7) {
                Log.w("FragmentManager", "moveToState: Fragment state for " + ((Object) componentCallbacksC0013n) + " not updated inline; expected state " + i7 + " found " + componentCallbacksC0013n.mState);
                componentCallbacksC0013n.mState = i7;
            }
        }
        if (componentCallbacksC0013n.mFromLayout && !componentCallbacksC0013n.mInLayout) {
            return;
        }
        if (componentCallbacksC0013n.getAnimatingAway() != null || componentCallbacksC0013n.getAnimator() != null) {
            componentCallbacksC0013n.c(null);
            componentCallbacksC0013n.a(null);
            a((ComponentCallbacksC0013n) componentCallbacksC0013n, componentCallbacksC0013n.getStateAfterAnimating(), 0, 0, true);
        }
        int i12 = componentCallbacksC0013n.mState;
        if (i12 == 0) {
            if (i9 > 0) {
                Bundle bundle = componentCallbacksC0013n.mSavedFragmentState;
                if (bundle != null) {
                    bundle.setClassLoader(this.mHost.getContext().getClassLoader());
                    componentCallbacksC0013n.mSavedViewState = componentCallbacksC0013n.mSavedFragmentState.getSparseParcelableArray("android:view_state");
                    int i13 = componentCallbacksC0013n.mSavedFragmentState.getInt("android:target_state", -1);
                    if (i13 == -1) {
                        componentCallbacksC0013n5 = null;
                    } else {
                        ComponentCallbacksC0013n componentCallbacksC0013n6 = (ComponentCallbacksC0013n) this.mActive.get(i13);
                        if (componentCallbacksC0013n6 == null) {
                            a(new IllegalStateException("Fragment no longer exists for key android:target_state: index " + i13));
                            throw null;
                        }
                        componentCallbacksC0013n5 = componentCallbacksC0013n6;
                    }
                    componentCallbacksC0013n.mTarget = componentCallbacksC0013n5;
                    if (componentCallbacksC0013n.mTarget != null) {
                        componentCallbacksC0013n.mTargetRequestCode = componentCallbacksC0013n.mSavedFragmentState.getInt("android:target_req_state", 0);
                    }
                    Boolean bool = componentCallbacksC0013n.hd;
                    if (bool != null) {
                        componentCallbacksC0013n.mUserVisibleHint = bool.booleanValue();
                        activity = null;
                        componentCallbacksC0013n.hd = null;
                    } else {
                        activity = null;
                        componentCallbacksC0013n.mUserVisibleHint = componentCallbacksC0013n.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
                    }
                    if (!componentCallbacksC0013n.mUserVisibleHint) {
                        componentCallbacksC0013n.mDeferStart = true;
                        if (i9 > 3) {
                            activity2 = activity;
                            i9 = 3;
                        }
                    }
                    abstractC0019u = this.mHost;
                    componentCallbacksC0013n.mHost = abstractC0019u;
                    componentCallbacksC0013n2 = this.mParent;
                    componentCallbacksC0013n.mParentFragment = componentCallbacksC0013n2;
                    if (componentCallbacksC0013n2 != null) {
                        o = componentCallbacksC0013n2.mChildFragmentManager;
                    } else {
                        o = abstractC0019u.mFragmentManager;
                    }
                    componentCallbacksC0013n.mFragmentManager = o;
                    componentCallbacksC0013n3 = componentCallbacksC0013n.mTarget;
                    if (componentCallbacksC0013n3 != null) {
                        obj = this.mActive.get(componentCallbacksC0013n3.mIndex);
                        componentCallbacksC0013n4 = componentCallbacksC0013n.mTarget;
                        if (obj == componentCallbacksC0013n4) {
                            throw new IllegalStateException("Fragment " + ((Object) componentCallbacksC0013n) + " declared target fragment " + componentCallbacksC0013n.mTarget + " that does not belong to this FragmentManager!");
                        }
                        if (componentCallbacksC0013n4.mState < 1) {
                            a(componentCallbacksC0013n4, 1, 0, 0, true);
                        }
                    }
                    b((ComponentCallbacksC0013n) componentCallbacksC0013n, this.mHost.getContext(), false);
                    componentCallbacksC0013n.mCalled = false;
                    this.mHost.getContext();
                    componentCallbacksC0013n.mCalled = true;
                    abstractC0019u2 = componentCallbacksC0013n.mHost;
                    if (abstractC0019u2 == null) {
                        activity3 = activity2;
                    } else {
                        activity3 = abstractC0019u2.getActivity();
                    }
                    if (activity3 != null) {
                        componentCallbacksC0013n.mCalled = false;
                        componentCallbacksC0013n.mCalled = true;
                    }
                    if (componentCallbacksC0013n.mCalled) {
                        throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n, " did not call through to super.onAttach()"));
                    }
                    if (componentCallbacksC0013n.mParentFragment == null) {
                        ((C0015p) this.mHost).n.a(componentCallbacksC0013n);
                    }
                    a((ComponentCallbacksC0013n) componentCallbacksC0013n, this.mHost.getContext(), false);
                    if (!componentCallbacksC0013n.mIsCreated) {
                        c(componentCallbacksC0013n, componentCallbacksC0013n.mSavedFragmentState, false);
                        Bundle bundle2 = componentCallbacksC0013n.mSavedFragmentState;
                        o2 = componentCallbacksC0013n.mChildFragmentManager;
                        if (o2 != null) {
                            o2.noteStateNotSaved();
                        }
                        componentCallbacksC0013n.mState = 1;
                        componentCallbacksC0013n.mCalled = false;
                        componentCallbacksC0013n.mCalled = true;
                        componentCallbacksC0013n.a(bundle2);
                        o3 = componentCallbacksC0013n.mChildFragmentManager;
                        if (o3 != null) {
                            if (o3.mCurState >= 1) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (!z2) {
                                componentCallbacksC0013n.mChildFragmentManager.dispatchCreate();
                            }
                        }
                        componentCallbacksC0013n.mIsCreated = true;
                        if (componentCallbacksC0013n.mCalled) {
                            componentCallbacksC0013n.tc.b(Lifecycle$Event.ON_CREATE);
                            b((ComponentCallbacksC0013n) componentCallbacksC0013n, componentCallbacksC0013n.mSavedFragmentState, false);
                        } else {
                            throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n, " did not call through to super.onCreate()"));
                        }
                    } else {
                        componentCallbacksC0013n.a(componentCallbacksC0013n.mSavedFragmentState);
                        componentCallbacksC0013n.mState = 1;
                    }
                    componentCallbacksC0013n.mRetaining = false;
                    r0 = activity2;
                } else {
                    activity = null;
                }
                activity2 = activity;
                abstractC0019u = this.mHost;
                componentCallbacksC0013n.mHost = abstractC0019u;
                componentCallbacksC0013n2 = this.mParent;
                componentCallbacksC0013n.mParentFragment = componentCallbacksC0013n2;
                if (componentCallbacksC0013n2 != null) {
                    o = componentCallbacksC0013n2.mChildFragmentManager;
                } else {
                    o = abstractC0019u.mFragmentManager;
                }
                componentCallbacksC0013n.mFragmentManager = o;
                componentCallbacksC0013n3 = componentCallbacksC0013n.mTarget;
                if (componentCallbacksC0013n3 != null) {
                    obj = this.mActive.get(componentCallbacksC0013n3.mIndex);
                    componentCallbacksC0013n4 = componentCallbacksC0013n.mTarget;
                    if (obj == componentCallbacksC0013n4) {
                        throw new IllegalStateException("Fragment " + ((Object) componentCallbacksC0013n) + " declared target fragment " + componentCallbacksC0013n.mTarget + " that does not belong to this FragmentManager!");
                    }
                    if (componentCallbacksC0013n4.mState < 1) {
                        a(componentCallbacksC0013n4, 1, 0, 0, true);
                    }
                }
                b((ComponentCallbacksC0013n) componentCallbacksC0013n, this.mHost.getContext(), false);
                componentCallbacksC0013n.mCalled = false;
                this.mHost.getContext();
                componentCallbacksC0013n.mCalled = true;
                abstractC0019u2 = componentCallbacksC0013n.mHost;
                if (abstractC0019u2 == null) {
                    activity3 = activity2;
                } else {
                    activity3 = abstractC0019u2.getActivity();
                }
                if (activity3 != null) {
                    componentCallbacksC0013n.mCalled = false;
                    componentCallbacksC0013n.mCalled = true;
                }
                if (componentCallbacksC0013n.mCalled) {
                    throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n, " did not call through to super.onAttach()"));
                }
                if (componentCallbacksC0013n.mParentFragment == null) {
                    ((C0015p) this.mHost).n.a(componentCallbacksC0013n);
                }
                a((ComponentCallbacksC0013n) componentCallbacksC0013n, this.mHost.getContext(), false);
                if (!componentCallbacksC0013n.mIsCreated) {
                    c(componentCallbacksC0013n, componentCallbacksC0013n.mSavedFragmentState, false);
                    Bundle bundle3 = componentCallbacksC0013n.mSavedFragmentState;
                    o2 = componentCallbacksC0013n.mChildFragmentManager;
                    if (o2 != null) {
                        o2.noteStateNotSaved();
                    }
                    componentCallbacksC0013n.mState = 1;
                    componentCallbacksC0013n.mCalled = false;
                    componentCallbacksC0013n.mCalled = true;
                    componentCallbacksC0013n.a(bundle3);
                    o3 = componentCallbacksC0013n.mChildFragmentManager;
                    if (o3 != null) {
                        if (o3.mCurState >= 1) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (!z2) {
                            componentCallbacksC0013n.mChildFragmentManager.dispatchCreate();
                        }
                    }
                    componentCallbacksC0013n.mIsCreated = true;
                    if (componentCallbacksC0013n.mCalled) {
                        componentCallbacksC0013n.tc.b(Lifecycle$Event.ON_CREATE);
                        b((ComponentCallbacksC0013n) componentCallbacksC0013n, componentCallbacksC0013n.mSavedFragmentState, false);
                    } else {
                        throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n, " did not call through to super.onCreate()"));
                    }
                } else {
                    componentCallbacksC0013n.a(componentCallbacksC0013n.mSavedFragmentState);
                    componentCallbacksC0013n.mState = 1;
                }
                componentCallbacksC0013n.mRetaining = false;
                r0 = activity2;
            }
            if (componentCallbacksC0013n.mFromLayout && !componentCallbacksC0013n.mPerformedCreateView) {
                componentCallbacksC0013n.performCreateView(componentCallbacksC0013n.performGetLayoutInflater(componentCallbacksC0013n.mSavedFragmentState), r0, componentCallbacksC0013n.mSavedFragmentState);
                view2 = componentCallbacksC0013n.mView;
                if (view2 != null) {
                    componentCallbacksC0013n.jd = view2;
                    view2.setSaveFromParentEnabled(false);
                    if (componentCallbacksC0013n.mHidden) {
                        componentCallbacksC0013n.mView.setVisibility(8);
                    }
                    a((ComponentCallbacksC0013n) componentCallbacksC0013n, componentCallbacksC0013n.mView, componentCallbacksC0013n.mSavedFragmentState, false);
                } else {
                    componentCallbacksC0013n.jd = r0;
                }
            }
            if (i9 > 1) {
                if (!componentCallbacksC0013n.mFromLayout) {
                    i5 = componentCallbacksC0013n.mContainerId;
                    if (i5 != 0) {
                        r1 = r0;
                    } else {
                        if (i5 != -1) {
                            a(new IllegalArgumentException(b.a.a.a.a.a("Cannot create fragment ", componentCallbacksC0013n, " for a container view with no id")));
                            throw r0;
                        }
                        viewGroup2 = (ViewGroup) this.mContainer.onFindViewById(i5);
                        r1 = viewGroup2;
                        if (viewGroup2 == null) {
                            r1 = viewGroup2;
                            if (!componentCallbacksC0013n.mRestored) {
                                try {
                                    abstractC0019u3 = componentCallbacksC0013n.mHost;
                                    if (abstractC0019u3 == null) {
                                        context = r0;
                                    } else {
                                        context = abstractC0019u3.getContext();
                                    }
                                    if (context != 0) {
                                        resourceName = context.getResources().getResourceName(componentCallbacksC0013n.mContainerId);
                                        StringBuilder sbE = b.a.a.a.a.e("No view found for id 0x");
                                        sbE.append(Integer.toHexString(componentCallbacksC0013n.mContainerId));
                                        sbE.append(" (");
                                        sbE.append(resourceName);
                                        sbE.append(") for fragment ");
                                        sbE.append((Object) componentCallbacksC0013n);
                                        a(new IllegalArgumentException(sbE.toString()));
                                        throw r0;
                                    }
                                    throw new IllegalStateException("Fragment " + ((Object) componentCallbacksC0013n) + " not attached to a context.");
                                } catch (Resources.NotFoundException unused) {
                                    resourceName = "unknown";
                                }
                            }
                        }
                    }
                    componentCallbacksC0013n.mContainer = r1;
                    componentCallbacksC0013n.performCreateView(componentCallbacksC0013n.performGetLayoutInflater(componentCallbacksC0013n.mSavedFragmentState), r1, componentCallbacksC0013n.mSavedFragmentState);
                    view = componentCallbacksC0013n.mView;
                    if (view != null) {
                        componentCallbacksC0013n.jd = view;
                        view.setSaveFromParentEnabled(false);
                        if (r1 != 0) {
                            r1.addView(componentCallbacksC0013n.mView);
                        }
                        if (componentCallbacksC0013n.mHidden) {
                            componentCallbacksC0013n.mView.setVisibility(8);
                        }
                        a((ComponentCallbacksC0013n) componentCallbacksC0013n, componentCallbacksC0013n.mView, componentCallbacksC0013n.mSavedFragmentState, false);
                        if (componentCallbacksC0013n.mView.getVisibility() == 0 || componentCallbacksC0013n.mContainer == null) {
                            z3 = false;
                        } else {
                            z3 = true;
                        }
                        componentCallbacksC0013n.mIsNewlyAdded = z3;
                    } else {
                        componentCallbacksC0013n.jd = r0;
                    }
                }
                Bundle bundle4 = componentCallbacksC0013n.mSavedFragmentState;
                o4 = componentCallbacksC0013n.mChildFragmentManager;
                if (o4 != null) {
                    o4.noteStateNotSaved();
                }
                componentCallbacksC0013n.mState = 2;
                componentCallbacksC0013n.mCalled = false;
                componentCallbacksC0013n.mCalled = true;
                if (componentCallbacksC0013n.mCalled) {
                    o5 = componentCallbacksC0013n.mChildFragmentManager;
                    if (o5 != null) {
                        o5.dispatchActivityCreated();
                    }
                    a((ComponentCallbacksC0013n) componentCallbacksC0013n, componentCallbacksC0013n.mSavedFragmentState, false);
                    if (componentCallbacksC0013n.mView != null) {
                        componentCallbacksC0013n.restoreViewState(componentCallbacksC0013n.mSavedFragmentState);
                    }
                    componentCallbacksC0013n.mSavedFragmentState = r0;
                } else {
                    throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n, " did not call through to super.onActivityCreated()"));
                }
            }
            if (i9 > 2) {
                componentCallbacksC0013n.mState = 3;
            }
            if (i9 > 3) {
                componentCallbacksC0013n.performStart();
                f(componentCallbacksC0013n, false);
            }
            i8 = 4;
            r2 = r0;
            if (i9 > i8) {
                componentCallbacksC0013n.performResume();
                e(componentCallbacksC0013n, false);
                componentCallbacksC0013n.mSavedFragmentState = r2;
                componentCallbacksC0013n.mSavedViewState = r2;
            }
        } else if (i12 != 1) {
            if (i12 == 2) {
                r0 = 0;
                if (i9 > 2) {
                    componentCallbacksC0013n.mState = 3;
                }
                if (i9 > 3) {
                    componentCallbacksC0013n.performStart();
                    f(componentCallbacksC0013n, false);
                }
                i8 = 4;
                r2 = r0;
                if (i9 > i8) {
                    componentCallbacksC0013n.performResume();
                    e(componentCallbacksC0013n, false);
                    componentCallbacksC0013n.mSavedFragmentState = r2;
                    componentCallbacksC0013n.mSavedViewState = r2;
                }
            } else if (i12 == 3) {
                r0 = 0;
                if (i9 > 3) {
                    componentCallbacksC0013n.performStart();
                    f(componentCallbacksC0013n, false);
                }
                i8 = 4;
                r2 = r0;
                if (i9 > i8) {
                    componentCallbacksC0013n.performResume();
                    e(componentCallbacksC0013n, false);
                    componentCallbacksC0013n.mSavedFragmentState = r2;
                    componentCallbacksC0013n.mSavedViewState = r2;
                }
            } else if (i12 == 4) {
                r2 = 0;
                if (i9 > i8) {
                    componentCallbacksC0013n.performResume();
                    e(componentCallbacksC0013n, false);
                    componentCallbacksC0013n.mSavedFragmentState = r2;
                    componentCallbacksC0013n.mSavedViewState = r2;
                }
            }
        }
        r0 = 0;
        if (componentCallbacksC0013n.mFromLayout) {
            componentCallbacksC0013n.performCreateView(componentCallbacksC0013n.performGetLayoutInflater(componentCallbacksC0013n.mSavedFragmentState), r0, componentCallbacksC0013n.mSavedFragmentState);
            view2 = componentCallbacksC0013n.mView;
            if (view2 != null) {
                componentCallbacksC0013n.jd = view2;
                view2.setSaveFromParentEnabled(false);
                if (componentCallbacksC0013n.mHidden) {
                    componentCallbacksC0013n.mView.setVisibility(8);
                }
                a((ComponentCallbacksC0013n) componentCallbacksC0013n, componentCallbacksC0013n.mView, componentCallbacksC0013n.mSavedFragmentState, false);
            } else {
                componentCallbacksC0013n.jd = r0;
            }
        }
        if (i9 > 1) {
            if (!componentCallbacksC0013n.mFromLayout) {
                i5 = componentCallbacksC0013n.mContainerId;
                if (i5 != 0) {
                    r1 = r0;
                } else {
                    if (i5 != -1) {
                        a(new IllegalArgumentException(b.a.a.a.a.a("Cannot create fragment ", componentCallbacksC0013n, " for a container view with no id")));
                        throw r0;
                    }
                    viewGroup2 = (ViewGroup) this.mContainer.onFindViewById(i5);
                    r1 = viewGroup2;
                    if (viewGroup2 == null) {
                        r1 = viewGroup2;
                        if (!componentCallbacksC0013n.mRestored) {
                            abstractC0019u3 = componentCallbacksC0013n.mHost;
                            if (abstractC0019u3 == null) {
                                context = r0;
                            } else {
                                context = abstractC0019u3.getContext();
                            }
                            if (context != 0) {
                                resourceName = context.getResources().getResourceName(componentCallbacksC0013n.mContainerId);
                                StringBuilder sbE2 = b.a.a.a.a.e("No view found for id 0x");
                                sbE2.append(Integer.toHexString(componentCallbacksC0013n.mContainerId));
                                sbE2.append(" (");
                                sbE2.append(resourceName);
                                sbE2.append(") for fragment ");
                                sbE2.append((Object) componentCallbacksC0013n);
                                a(new IllegalArgumentException(sbE2.toString()));
                                throw r0;
                            }
                            throw new IllegalStateException("Fragment " + ((Object) componentCallbacksC0013n) + " not attached to a context.");
                        }
                    }
                }
                componentCallbacksC0013n.mContainer = r1;
                componentCallbacksC0013n.performCreateView(componentCallbacksC0013n.performGetLayoutInflater(componentCallbacksC0013n.mSavedFragmentState), r1, componentCallbacksC0013n.mSavedFragmentState);
                view = componentCallbacksC0013n.mView;
                if (view != null) {
                    componentCallbacksC0013n.jd = view;
                    view.setSaveFromParentEnabled(false);
                    if (r1 != 0) {
                        r1.addView(componentCallbacksC0013n.mView);
                    }
                    if (componentCallbacksC0013n.mHidden) {
                        componentCallbacksC0013n.mView.setVisibility(8);
                    }
                    a((ComponentCallbacksC0013n) componentCallbacksC0013n, componentCallbacksC0013n.mView, componentCallbacksC0013n.mSavedFragmentState, false);
                    if (componentCallbacksC0013n.mView.getVisibility() == 0) {
                        z3 = false;
                    } else {
                        z3 = false;
                    }
                    componentCallbacksC0013n.mIsNewlyAdded = z3;
                } else {
                    componentCallbacksC0013n.jd = r0;
                }
            }
            Bundle bundle5 = componentCallbacksC0013n.mSavedFragmentState;
            o4 = componentCallbacksC0013n.mChildFragmentManager;
            if (o4 != null) {
                o4.noteStateNotSaved();
            }
            componentCallbacksC0013n.mState = 2;
            componentCallbacksC0013n.mCalled = false;
            componentCallbacksC0013n.mCalled = true;
            if (componentCallbacksC0013n.mCalled) {
                o5 = componentCallbacksC0013n.mChildFragmentManager;
                if (o5 != null) {
                    o5.dispatchActivityCreated();
                }
                a((ComponentCallbacksC0013n) componentCallbacksC0013n, componentCallbacksC0013n.mSavedFragmentState, false);
                if (componentCallbacksC0013n.mView != null) {
                    componentCallbacksC0013n.restoreViewState(componentCallbacksC0013n.mSavedFragmentState);
                }
                componentCallbacksC0013n.mSavedFragmentState = r0;
            } else {
                throw new SuperNotCalledException(b.a.a.a.a.a("Fragment ", componentCallbacksC0013n, " did not call through to super.onActivityCreated()"));
            }
        }
        if (i9 > 2) {
            componentCallbacksC0013n.mState = 3;
        }
        if (i9 > 3) {
            componentCallbacksC0013n.performStart();
            f(componentCallbacksC0013n, false);
        }
        i8 = 4;
        r2 = r0;
        if (i9 > i8) {
            componentCallbacksC0013n.performResume();
            e(componentCallbacksC0013n, false);
            componentCallbacksC0013n.mSavedFragmentState = r2;
            componentCallbacksC0013n.mSavedViewState = r2;
        }
        i7 = i9;
        if (componentCallbacksC0013n.mState != i7) {
            Log.w("FragmentManager", "moveToState: Fragment state for " + ((Object) componentCallbacksC0013n) + " not updated inline; expected state " + i7 + " found " + componentCallbacksC0013n.mState);
            componentCallbacksC0013n.mState = i7;
        }
    }

    private static void a(View view, H h) {
        boolean zModifiesAlpha;
        if (view == null || h == null) {
            return;
        }
        boolean z = false;
        int i = Build.VERSION.SDK_INT;
        if (view.getLayerType() == 0 && android.support.v4.view.r.j(view)) {
            Animation animation = h.animation;
            if (animation instanceof AlphaAnimation) {
                zModifiesAlpha = true;
                break;
            }
            if (animation instanceof AnimationSet) {
                List<Animation> animations = ((AnimationSet) animation).getAnimations();
                int i2 = 0;
                while (true) {
                    if (i2 >= animations.size()) {
                        zModifiesAlpha = false;
                        break;
                    } else {
                        if (animations.get(i2) instanceof AlphaAnimation) {
                            zModifiesAlpha = true;
                            break;
                        }
                        i2++;
                    }
                }
            } else {
                zModifiesAlpha = modifiesAlpha(h.animator);
            }
            if (zModifiesAlpha) {
                z = true;
            }
        }
        if (z) {
            Animator animator = h.animator;
            if (animator != null) {
                animator.addListener(new I(view));
                return;
            }
            Animation.AnimationListener animationListenerA = a(h.animation);
            view.setLayerType(2, null);
            h.animation.setAnimationListener(new F(view, animationListenerA));
        }
    }

    private void a(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new a.b.c.b.f("FragmentManager"));
        AbstractC0019u abstractC0019u = this.mHost;
        try {
            if (abstractC0019u != null) {
                ((C0015p) abstractC0019u).n.dump("  ", null, printWriter, new String[0]);
            } else {
                dump("  ", null, printWriter, new String[0]);
            }
            throw runtimeException;
        } catch (Exception e) {
            Log.e("FragmentManager", "Failed dumping state", e);
            throw runtimeException;
        }
    }
}
