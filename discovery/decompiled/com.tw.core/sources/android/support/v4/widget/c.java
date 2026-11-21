package android.support.v4.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class c implements Parcelable.Creator {
    c() {
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.support.v4.widget.DrawerLayout$SavedState] */
    @Override // android.os.Parcelable.Creator
    public DrawerLayout$SavedState createFromParcel(final Parcel parcel) {
        return new View.BaseSavedState(parcel) { // from class: android.support.v4.widget.DrawerLayout$SavedState
            public static final Parcelable.Creator CREATOR = new c();
            int nc;
            int nd;
            int ne;

            {
                super(parcel);
                this.ne = 0;
                this.nc = 0;
                this.nd = 0;
                this.ne = parcel.readInt();
            }

            @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(Parcel parcel2, int i) {
                super.writeToParcel(parcel2, i);
                parcel2.writeInt(this.ne);
            }
        };
    }

    @Override // android.os.Parcelable.Creator
    public DrawerLayout$SavedState[] newArray(int i) {
        return new DrawerLayout$SavedState[i];
    }
}
