package android.support.v4.app;

import java.io.PrintWriter;
import java.util.ArrayList;

/* JADX INFO: renamed from: android.support.v4.app.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0004e extends V implements InterfaceC0020v, M {
    boolean mAddToBackStack;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    ArrayList mCommitRunnables;
    boolean mCommitted;
    int mEnterAnim;
    int mExitAnim;
    final O mManager;
    String mName;
    int mPopEnterAnim;
    int mPopExitAnim;
    ArrayList mSharedElementSourceNames;
    ArrayList mSharedElementTargetNames;
    int mTransition;
    int mTransitionStyle;
    ArrayList mOps = new ArrayList();
    int mIndex = -1;
    boolean mReorderingAllowed = false;

    public C0004e(O o) {
        this.mManager = o;
    }

    private static boolean b(C0003d c0003d) {
        ComponentCallbacksC0013n componentCallbacksC0013n = c0003d.fragment;
        if (componentCallbacksC0013n == null || !componentCallbacksC0013n.mAdded || componentCallbacksC0013n.mView == null || componentCallbacksC0013n.mDetached || componentCallbacksC0013n.mHidden) {
            return false;
        }
        C0011l c0011l = componentCallbacksC0013n.mAnimationInfo;
        return c0011l == null ? false : c0011l.mEnterTransitionPostponed;
    }

    void a(C0003d c0003d) {
        this.mOps.add(c0003d);
        c0003d.enterAnim = this.mEnterAnim;
        c0003d.exitAnim = this.mExitAnim;
        c0003d.popEnterAnim = this.mPopEnterAnim;
        c0003d.popExitAnim = this.mPopExitAnim;
    }

    void bumpBackStackNesting(int i) {
        if (this.mAddToBackStack) {
            int size = this.mOps.size();
            for (int i2 = 0; i2 < size; i2++) {
                ComponentCallbacksC0013n componentCallbacksC0013n = ((C0003d) this.mOps.get(i2)).fragment;
                if (componentCallbacksC0013n != null) {
                    componentCallbacksC0013n.mBackStackNesting += i;
                }
            }
        }
    }

    @Override // android.support.v4.app.V
    public int commit() {
        if (this.mCommitted) {
            throw new IllegalStateException("commit already called");
        }
        this.mCommitted = true;
        this.mIndex = this.mAddToBackStack ? this.mManager.a(this) : -1;
        this.mManager.a((M) this, false);
        return this.mIndex;
    }

    void d(N n) {
        for (int i = 0; i < this.mOps.size(); i++) {
            C0003d c0003d = (C0003d) this.mOps.get(i);
            if (b(c0003d)) {
                c0003d.fragment.a(n);
            }
        }
    }

    public void dump(String str, PrintWriter printWriter, boolean z) {
        String string;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.mName);
            printWriter.print(" mIndex=");
            printWriter.print(this.mIndex);
            printWriter.print(" mCommitted=");
            printWriter.println(this.mCommitted);
            if (this.mTransition != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.mTransition));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.mTransitionStyle));
            }
            if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mEnterAnim));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.mExitAnim));
            }
            if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mPopEnterAnim));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.mPopExitAnim));
            }
            if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.mBreadCrumbTitleText);
            }
            if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.mBreadCrumbShortTitleText);
            }
        }
        if (this.mOps.isEmpty()) {
            return;
        }
        printWriter.print(str);
        printWriter.println("Operations:");
        String str2 = str + "    ";
        int size = this.mOps.size();
        for (int i = 0; i < size; i++) {
            C0003d c0003d = (C0003d) this.mOps.get(i);
            switch (c0003d.cmd) {
                case 0:
                    string = "NULL";
                    break;
                case 1:
                    string = "ADD";
                    break;
                case 2:
                    string = "REPLACE";
                    break;
                case 3:
                    string = "REMOVE";
                    break;
                case 4:
                    string = "HIDE";
                    break;
                case 5:
                    string = "SHOW";
                    break;
                case 6:
                    string = "DETACH";
                    break;
                case 7:
                    string = "ATTACH";
                    break;
                case 8:
                    string = "SET_PRIMARY_NAV";
                    break;
                case 9:
                    string = "UNSET_PRIMARY_NAV";
                    break;
                default:
                    StringBuilder sbE = b.a.a.a.a.e("cmd=");
                    sbE.append(c0003d.cmd);
                    string = sbE.toString();
                    break;
            }
            printWriter.print(str);
            printWriter.print("  Op #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.print(string);
            printWriter.print(" ");
            printWriter.println(c0003d.fragment);
            if (z) {
                if (c0003d.enterAnim != 0 || c0003d.exitAnim != 0) {
                    printWriter.print(str);
                    printWriter.print("enterAnim=#");
                    printWriter.print(Integer.toHexString(c0003d.enterAnim));
                    printWriter.print(" exitAnim=#");
                    printWriter.println(Integer.toHexString(c0003d.exitAnim));
                }
                if (c0003d.popEnterAnim != 0 || c0003d.popExitAnim != 0) {
                    printWriter.print(str);
                    printWriter.print("popEnterAnim=#");
                    printWriter.print(Integer.toHexString(c0003d.popEnterAnim));
                    printWriter.print(" popExitAnim=#");
                    printWriter.println(Integer.toHexString(c0003d.popExitAnim));
                }
            }
        }
    }

    void executeOps() {
        int size = this.mOps.size();
        for (int i = 0; i < size; i++) {
            C0003d c0003d = (C0003d) this.mOps.get(i);
            ComponentCallbacksC0013n componentCallbacksC0013n = c0003d.fragment;
            if (componentCallbacksC0013n != null) {
                componentCallbacksC0013n.setNextTransition(this.mTransition, this.mTransitionStyle);
            }
            switch (c0003d.cmd) {
                case 1:
                    componentCallbacksC0013n.setNextAnim(c0003d.enterAnim);
                    this.mManager.a(componentCallbacksC0013n, false);
                    break;
                case 2:
                default:
                    StringBuilder sbE = b.a.a.a.a.e("Unknown cmd: ");
                    sbE.append(c0003d.cmd);
                    throw new IllegalArgumentException(sbE.toString());
                case 3:
                    componentCallbacksC0013n.setNextAnim(c0003d.exitAnim);
                    this.mManager.j(componentCallbacksC0013n);
                    break;
                case 4:
                    componentCallbacksC0013n.setNextAnim(c0003d.exitAnim);
                    this.mManager.g(componentCallbacksC0013n);
                    break;
                case 5:
                    componentCallbacksC0013n.setNextAnim(c0003d.enterAnim);
                    this.mManager.m(componentCallbacksC0013n);
                    break;
                case 6:
                    componentCallbacksC0013n.setNextAnim(c0003d.exitAnim);
                    this.mManager.f(componentCallbacksC0013n);
                    break;
                case 7:
                    componentCallbacksC0013n.setNextAnim(c0003d.enterAnim);
                    this.mManager.e(componentCallbacksC0013n);
                    break;
                case 8:
                    this.mManager.l(componentCallbacksC0013n);
                    break;
                case 9:
                    this.mManager.l(null);
                    break;
            }
            if (!this.mReorderingAllowed && c0003d.cmd != 1 && componentCallbacksC0013n != null) {
                this.mManager.i(componentCallbacksC0013n);
            }
        }
        if (this.mReorderingAllowed) {
            return;
        }
        O o = this.mManager;
        o.moveToState(o.mCurState, true);
    }

    void executePopOps(boolean z) {
        for (int size = this.mOps.size() - 1; size >= 0; size--) {
            C0003d c0003d = (C0003d) this.mOps.get(size);
            ComponentCallbacksC0013n componentCallbacksC0013n = c0003d.fragment;
            if (componentCallbacksC0013n != null) {
                componentCallbacksC0013n.setNextTransition(O.reverseTransit(this.mTransition), this.mTransitionStyle);
            }
            switch (c0003d.cmd) {
                case 1:
                    componentCallbacksC0013n.setNextAnim(c0003d.popExitAnim);
                    this.mManager.j(componentCallbacksC0013n);
                    break;
                case 2:
                default:
                    StringBuilder sbE = b.a.a.a.a.e("Unknown cmd: ");
                    sbE.append(c0003d.cmd);
                    throw new IllegalArgumentException(sbE.toString());
                case 3:
                    componentCallbacksC0013n.setNextAnim(c0003d.popEnterAnim);
                    this.mManager.a(componentCallbacksC0013n, false);
                    break;
                case 4:
                    componentCallbacksC0013n.setNextAnim(c0003d.popEnterAnim);
                    this.mManager.m(componentCallbacksC0013n);
                    break;
                case 5:
                    componentCallbacksC0013n.setNextAnim(c0003d.popExitAnim);
                    this.mManager.g(componentCallbacksC0013n);
                    break;
                case 6:
                    componentCallbacksC0013n.setNextAnim(c0003d.popEnterAnim);
                    this.mManager.e(componentCallbacksC0013n);
                    break;
                case 7:
                    componentCallbacksC0013n.setNextAnim(c0003d.popExitAnim);
                    this.mManager.f(componentCallbacksC0013n);
                    break;
                case 8:
                    this.mManager.l(null);
                    break;
                case 9:
                    this.mManager.l(componentCallbacksC0013n);
                    break;
            }
            if (!this.mReorderingAllowed && c0003d.cmd != 3 && componentCallbacksC0013n != null) {
                this.mManager.i(componentCallbacksC0013n);
            }
        }
        if (this.mReorderingAllowed || !z) {
            return;
        }
        O o = this.mManager;
        o.moveToState(o.mCurState, true);
    }

    public boolean generateOps(ArrayList arrayList, ArrayList arrayList2) {
        arrayList.add(this);
        arrayList2.add(false);
        if (!this.mAddToBackStack) {
            return true;
        }
        O o = this.mManager;
        if (o.mBackStack == null) {
            o.mBackStack = new ArrayList();
        }
        o.mBackStack.add(this);
        return true;
    }

    boolean interactsWith(int i) {
        int size = this.mOps.size();
        for (int i2 = 0; i2 < size; i2++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = ((C0003d) this.mOps.get(i2)).fragment;
            int i3 = componentCallbacksC0013n != null ? componentCallbacksC0013n.mContainerId : 0;
            if (i3 != 0 && i3 == i) {
                return true;
            }
        }
        return false;
    }

    boolean interactsWith(ArrayList arrayList, int i, int i2) {
        if (i2 == i) {
            return false;
        }
        int size = this.mOps.size();
        int i3 = -1;
        for (int i4 = 0; i4 < size; i4++) {
            ComponentCallbacksC0013n componentCallbacksC0013n = ((C0003d) this.mOps.get(i4)).fragment;
            int i5 = componentCallbacksC0013n != null ? componentCallbacksC0013n.mContainerId : 0;
            if (i5 != 0 && i5 != i3) {
                for (int i6 = i; i6 < i2; i6++) {
                    C0004e c0004e = (C0004e) arrayList.get(i6);
                    int size2 = c0004e.mOps.size();
                    for (int i7 = 0; i7 < size2; i7++) {
                        ComponentCallbacksC0013n componentCallbacksC0013n2 = ((C0003d) c0004e.mOps.get(i7)).fragment;
                        if ((componentCallbacksC0013n2 != null ? componentCallbacksC0013n2.mContainerId : 0) == i5) {
                            return true;
                        }
                    }
                }
                i3 = i5;
            }
        }
        return false;
    }

    boolean isPostponed() {
        for (int i = 0; i < this.mOps.size(); i++) {
            if (b((C0003d) this.mOps.get(i))) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            sb.append(" #");
            sb.append(this.mIndex);
        }
        if (this.mName != null) {
            sb.append(" ");
            sb.append(this.mName);
        }
        sb.append("}");
        return sb.toString();
    }
}
