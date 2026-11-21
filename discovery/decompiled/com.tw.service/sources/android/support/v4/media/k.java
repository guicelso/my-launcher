package android.support.v4.media;

import android.media.MediaDescription;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class k implements Parcelable.Creator {
    k() {
    }

    @Override // android.os.Parcelable.Creator
    public Object createFromParcel(Parcel parcel) {
        int i = Build.VERSION.SDK_INT;
        return MediaDescriptionCompat.e(MediaDescription.CREATOR.createFromParcel(parcel));
    }

    @Override // android.os.Parcelable.Creator
    public Object[] newArray(int i) {
        return new MediaDescriptionCompat[i];
    }
}
