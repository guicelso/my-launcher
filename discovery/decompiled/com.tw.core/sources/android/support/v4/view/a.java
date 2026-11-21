package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.b.e;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class a implements e {
    a() {
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.support.v4.view.ViewPager$SavedState] */
    @Override // android.support.v4.b.e
    public ViewPager$SavedState createFromParcel(final Parcel parcel, final ClassLoader classLoader) {
        return new View.BaseSavedState(parcel, classLoader) { // from class: android.support.v4.view.ViewPager$SavedState
            public static final Parcelable.Creator CREATOR = android.support.v4.b.a.rj(new a());
            Parcelable mZ;
            ClassLoader na;
            int nb;

            {
                super(parcel);
                classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
                this.nb = parcel.readInt();
                this.mZ = parcel.readParcelable(classLoader);
                this.na = classLoader;
            }

            public String toString() {
                return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.nb + "}";
            }

            @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(Parcel parcel2, int i) {
                super.writeToParcel(parcel2, i);
                parcel2.writeInt(this.nb);
                parcel2.writeParcelable(this.mZ, i);
            }
        };
    }

    @Override // android.support.v4.b.e
    public ViewPager$SavedState[] newArray(int i) {
        return new ViewPager$SavedState[i];
    }
}
