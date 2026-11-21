package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class BackStackState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new C0005f();
    final int mBreadCrumbShortTitleRes;
    final CharSequence mBreadCrumbShortTitleText;
    final int mBreadCrumbTitleRes;
    final CharSequence mBreadCrumbTitleText;
    final int mIndex;
    final String mName;
    final int[] mOps;
    final boolean mReorderingAllowed;
    final ArrayList mSharedElementSourceNames;
    final ArrayList mSharedElementTargetNames;
    final int mTransition;
    final int mTransitionStyle;

    public BackStackState(Parcel parcel) {
        this.mOps = parcel.createIntArray();
        this.mTransition = parcel.readInt();
        this.mTransitionStyle = parcel.readInt();
        this.mName = parcel.readString();
        this.mIndex = parcel.readInt();
        this.mBreadCrumbTitleRes = parcel.readInt();
        this.mBreadCrumbTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mBreadCrumbShortTitleRes = parcel.readInt();
        this.mBreadCrumbShortTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSharedElementSourceNames = parcel.createStringArrayList();
        this.mSharedElementTargetNames = parcel.createStringArrayList();
        this.mReorderingAllowed = parcel.readInt() != 0;
    }

    public BackStackState(C0004e c0004e) {
        int size = c0004e.mOps.size();
        this.mOps = new int[size * 6];
        if (!c0004e.mAddToBackStack) {
            throw new IllegalStateException("Not on back stack");
        }
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            C0003d c0003d = (C0003d) c0004e.mOps.get(i2);
            int[] iArr = this.mOps;
            int i3 = i + 1;
            iArr[i] = c0003d.cmd;
            int i4 = i3 + 1;
            ComponentCallbacksC0013n componentCallbacksC0013n = c0003d.fragment;
            iArr[i3] = componentCallbacksC0013n != null ? componentCallbacksC0013n.mIndex : -1;
            int[] iArr2 = this.mOps;
            int i5 = i4 + 1;
            iArr2[i4] = c0003d.enterAnim;
            int i6 = i5 + 1;
            iArr2[i5] = c0003d.exitAnim;
            int i7 = i6 + 1;
            iArr2[i6] = c0003d.popEnterAnim;
            i = i7 + 1;
            iArr2[i7] = c0003d.popExitAnim;
        }
        this.mTransition = c0004e.mTransition;
        this.mTransitionStyle = c0004e.mTransitionStyle;
        this.mName = c0004e.mName;
        this.mIndex = c0004e.mIndex;
        this.mBreadCrumbTitleRes = c0004e.mBreadCrumbTitleRes;
        this.mBreadCrumbTitleText = c0004e.mBreadCrumbTitleText;
        this.mBreadCrumbShortTitleRes = c0004e.mBreadCrumbShortTitleRes;
        this.mBreadCrumbShortTitleText = c0004e.mBreadCrumbShortTitleText;
        this.mSharedElementSourceNames = c0004e.mSharedElementSourceNames;
        this.mSharedElementTargetNames = c0004e.mSharedElementTargetNames;
        this.mReorderingAllowed = c0004e.mReorderingAllowed;
    }

    public C0004e a(O o) {
        C0004e c0004e = new C0004e(o);
        int i = 0;
        while (i < this.mOps.length) {
            C0003d c0003d = new C0003d();
            int[] iArr = this.mOps;
            int i2 = i + 1;
            c0003d.cmd = iArr[i];
            int i3 = i2 + 1;
            int i4 = iArr[i2];
            c0003d.fragment = i4 >= 0 ? (ComponentCallbacksC0013n) o.mActive.get(i4) : null;
            int[] iArr2 = this.mOps;
            int i5 = i3 + 1;
            c0003d.enterAnim = iArr2[i3];
            int i6 = i5 + 1;
            c0003d.exitAnim = iArr2[i5];
            int i7 = i6 + 1;
            c0003d.popEnterAnim = iArr2[i6];
            i = i7 + 1;
            c0003d.popExitAnim = iArr2[i7];
            c0004e.mEnterAnim = c0003d.enterAnim;
            c0004e.mExitAnim = c0003d.exitAnim;
            c0004e.mPopEnterAnim = c0003d.popEnterAnim;
            c0004e.mPopExitAnim = c0003d.popExitAnim;
            c0004e.a(c0003d);
        }
        c0004e.mTransition = this.mTransition;
        c0004e.mTransitionStyle = this.mTransitionStyle;
        c0004e.mName = this.mName;
        c0004e.mIndex = this.mIndex;
        c0004e.mAddToBackStack = true;
        c0004e.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
        c0004e.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
        c0004e.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
        c0004e.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
        c0004e.mSharedElementSourceNames = this.mSharedElementSourceNames;
        c0004e.mSharedElementTargetNames = this.mSharedElementTargetNames;
        c0004e.mReorderingAllowed = this.mReorderingAllowed;
        c0004e.bumpBackStackNesting(1);
        return c0004e;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.mOps);
        parcel.writeInt(this.mTransition);
        parcel.writeInt(this.mTransitionStyle);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mIndex);
        parcel.writeInt(this.mBreadCrumbTitleRes);
        TextUtils.writeToParcel(this.mBreadCrumbTitleText, parcel, 0);
        parcel.writeInt(this.mBreadCrumbShortTitleRes);
        TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, parcel, 0);
        parcel.writeStringList(this.mSharedElementSourceNames);
        parcel.writeStringList(this.mSharedElementTargetNames);
        parcel.writeInt(this.mReorderingAllowed ? 1 : 0);
    }
}
