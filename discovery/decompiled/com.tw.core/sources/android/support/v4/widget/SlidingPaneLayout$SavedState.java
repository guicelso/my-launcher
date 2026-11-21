package android.support.v4.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class SlidingPaneLayout$SavedState extends View.BaseSavedState {
    public static final Parcelable.Creator CREATOR = new b();
    boolean ng;

    private SlidingPaneLayout$SavedState(Parcel parcel) {
        super(parcel);
        this.ng = parcel.readInt() != 0;
    }

    /* synthetic */ SlidingPaneLayout$SavedState(Parcel parcel, SlidingPaneLayout$SavedState slidingPaneLayout$SavedState) {
        this(parcel);
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.ng ? 1 : 0);
    }
}
