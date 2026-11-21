package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new L();
    FragmentState[] mS;
    int[] mT;
    BackStackState[] mU;

    public FragmentManagerState() {
    }

    public FragmentManagerState(Parcel parcel) {
        this.mS = (FragmentState[]) parcel.createTypedArray(FragmentState.CREATOR);
        this.mT = parcel.createIntArray();
        this.mU = (BackStackState[]) parcel.createTypedArray(BackStackState.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.mS, i);
        parcel.writeIntArray(this.mT);
        parcel.writeTypedArray(this.mU, i);
    }
}
