package android.support.v4.media.session;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class o implements Parcelable.Creator {
    o() {
    }

    @Override // android.os.Parcelable.Creator
    public Object createFromParcel(Parcel parcel) {
        int i = Build.VERSION.SDK_INT;
        return new MediaSessionCompat$Token(parcel.readParcelable(null), null, null);
    }

    @Override // android.os.Parcelable.Creator
    public Object[] newArray(int i) {
        return new MediaSessionCompat$Token[i];
    }
}
