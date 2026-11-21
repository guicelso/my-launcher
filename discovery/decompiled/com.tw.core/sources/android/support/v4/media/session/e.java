package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;

/* JADX INFO: loaded from: classes.dex */
final class e implements Parcelable.Creator {
    e() {
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper] */
    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat$ResultReceiverWrapper createFromParcel(final Parcel parcel) {
        return new Parcelable(parcel) { // from class: android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper
            public static final Parcelable.Creator CREATOR = new e();
            private ResultReceiver nh;

            {
                this.nh = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel2, int i) {
                this.nh.writeToParcel(parcel2, i);
            }
        };
    }

    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat$ResultReceiverWrapper[] newArray(int i) {
        return new MediaSessionCompat$ResultReceiverWrapper[i];
    }
}
