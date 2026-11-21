package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class FragmentState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new S();
    final Bundle mArguments;
    final String mClassName;
    final int mContainerId;
    final boolean mDetached;
    final int mFragmentId;
    final boolean mFromLayout;
    final boolean mHidden;
    final int mIndex;
    ComponentCallbacksC0013n mInstance;
    final boolean mRetainInstance;
    Bundle mSavedFragmentState;
    final String mTag;

    FragmentState(Parcel parcel) {
        this.mClassName = parcel.readString();
        this.mIndex = parcel.readInt();
        this.mFromLayout = parcel.readInt() != 0;
        this.mFragmentId = parcel.readInt();
        this.mContainerId = parcel.readInt();
        this.mTag = parcel.readString();
        this.mRetainInstance = parcel.readInt() != 0;
        this.mDetached = parcel.readInt() != 0;
        this.mArguments = parcel.readBundle();
        this.mHidden = parcel.readInt() != 0;
        this.mSavedFragmentState = parcel.readBundle();
    }

    FragmentState(ComponentCallbacksC0013n componentCallbacksC0013n) {
        this.mClassName = componentCallbacksC0013n.getClass().getName();
        this.mIndex = componentCallbacksC0013n.mIndex;
        this.mFromLayout = componentCallbacksC0013n.mFromLayout;
        this.mFragmentId = componentCallbacksC0013n.mFragmentId;
        this.mContainerId = componentCallbacksC0013n.mContainerId;
        this.mTag = componentCallbacksC0013n.mTag;
        this.mRetainInstance = componentCallbacksC0013n.mRetainInstance;
        this.mDetached = componentCallbacksC0013n.mDetached;
        this.mArguments = componentCallbacksC0013n.mArguments;
        this.mHidden = componentCallbacksC0013n.mHidden;
    }

    public ComponentCallbacksC0013n a(AbstractC0019u abstractC0019u, AbstractC0017s abstractC0017s, ComponentCallbacksC0013n componentCallbacksC0013n, P p, android.arch.lifecycle.r rVar) {
        if (this.mInstance == null) {
            Context context = abstractC0019u.getContext();
            Bundle bundle = this.mArguments;
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            this.mInstance = abstractC0017s != null ? abstractC0017s.instantiate(context, this.mClassName, this.mArguments) : ComponentCallbacksC0013n.instantiate(context, this.mClassName, this.mArguments);
            Bundle bundle2 = this.mSavedFragmentState;
            if (bundle2 != null) {
                bundle2.setClassLoader(context.getClassLoader());
                this.mInstance.mSavedFragmentState = this.mSavedFragmentState;
            }
            this.mInstance.a(this.mIndex, componentCallbacksC0013n);
            ComponentCallbacksC0013n componentCallbacksC0013n2 = this.mInstance;
            componentCallbacksC0013n2.mFromLayout = this.mFromLayout;
            componentCallbacksC0013n2.mRestored = true;
            componentCallbacksC0013n2.mFragmentId = this.mFragmentId;
            componentCallbacksC0013n2.mContainerId = this.mContainerId;
            componentCallbacksC0013n2.mTag = this.mTag;
            componentCallbacksC0013n2.mRetainInstance = this.mRetainInstance;
            componentCallbacksC0013n2.mDetached = this.mDetached;
            componentCallbacksC0013n2.mHidden = this.mHidden;
            componentCallbacksC0013n2.mFragmentManager = abstractC0019u.mFragmentManager;
        }
        ComponentCallbacksC0013n componentCallbacksC0013n3 = this.mInstance;
        componentCallbacksC0013n3.mChildNonConfig = p;
        componentCallbacksC0013n3.oa = rVar;
        return componentCallbacksC0013n3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mClassName);
        parcel.writeInt(this.mIndex);
        parcel.writeInt(this.mFromLayout ? 1 : 0);
        parcel.writeInt(this.mFragmentId);
        parcel.writeInt(this.mContainerId);
        parcel.writeString(this.mTag);
        parcel.writeInt(this.mRetainInstance ? 1 : 0);
        parcel.writeInt(this.mDetached ? 1 : 0);
        parcel.writeBundle(this.mArguments);
        parcel.writeInt(this.mHidden ? 1 : 0);
        parcel.writeBundle(this.mSavedFragmentState);
    }
}
