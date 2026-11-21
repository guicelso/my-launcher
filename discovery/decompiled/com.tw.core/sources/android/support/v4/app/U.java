package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class U implements Parcelable.Creator {
    U() {
    }

    @Override // android.os.Parcelable.Creator
    public FragmentTabHost$SavedState createFromParcel(Parcel parcel) {
        return new FragmentTabHost$SavedState(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public FragmentTabHost$SavedState[] newArray(int i) {
        return new FragmentTabHost$SavedState[i];
    }
}
