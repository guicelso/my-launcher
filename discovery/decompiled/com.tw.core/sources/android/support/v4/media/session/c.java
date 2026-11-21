package android.support.v4.media.session;

import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class c implements Parcelable.Creator {
    c() {
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.support.v4.media.session.MediaSessionCompat$Token] */
    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat$Token createFromParcel(Parcel parcel) {
        final Object parcelable = Build.VERSION.SDK_INT >= 21 ? parcel.readParcelable(null) : parcel.readStrongBinder();
        return new Parcelable(parcelable) { // from class: android.support.v4.media.session.MediaSessionCompat$Token
            public static final Parcelable.Creator CREATOR = new c();
            private final Object ni;

            {
                this.ni = parcelable;
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel2, int i) {
                if (Build.VERSION.SDK_INT >= 21) {
                    parcel2.writeParcelable((Parcelable) this.ni, i);
                } else {
                    parcel2.writeStrongBinder((IBinder) this.ni);
                }
            }
        };
    }

    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat$Token[] newArray(int i) {
        return new MediaSessionCompat$Token[i];
    }
}
