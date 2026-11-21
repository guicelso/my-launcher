package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class FragmentTabHost$SavedState extends View.BaseSavedState {
    public static final Parcelable.Creator CREATOR = new U();
    String mQ;

    private FragmentTabHost$SavedState(Parcel parcel) {
        super(parcel);
        this.mQ = parcel.readString();
    }

    /* synthetic */ FragmentTabHost$SavedState(Parcel parcel, FragmentTabHost$SavedState fragmentTabHost$SavedState) {
        this(parcel);
    }

    public String toString() {
        return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.mQ + "}";
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mQ);
    }
}
