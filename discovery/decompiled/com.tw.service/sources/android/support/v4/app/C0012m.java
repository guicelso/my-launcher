package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: renamed from: android.support.v4.app.m, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0012m implements Parcelable.Creator {
    C0012m() {
    }

    @Override // android.os.Parcelable.Creator
    public Object createFromParcel(final Parcel parcel) {
        final ClassLoader classLoader = null;
        return new Parcelable(parcel, classLoader) { // from class: android.support.v4.app.Fragment$SavedState
            public static final Parcelable.Creator CREATOR = new C0012m();
            final Bundle mState;

            {
                Bundle bundle;
                this.mState = parcel.readBundle();
                if (classLoader == null || (bundle = this.mState) == null) {
                    return;
                }
                bundle.setClassLoader(classLoader);
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel2, int i) {
                parcel2.writeBundle(this.mState);
            }
        };
    }

    @Override // android.os.Parcelable.Creator
    public Object[] newArray(int i) {
        return new Fragment$SavedState[i];
    }
}
