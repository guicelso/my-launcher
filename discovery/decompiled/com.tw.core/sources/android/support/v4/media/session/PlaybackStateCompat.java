package android.support.v4.media.session;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class PlaybackStateCompat implements Parcelable {
    public static final Parcelable.Creator CREATOR = new a();
    private final long nl;
    private final long nm;
    private final long nn;
    private List no;
    private final CharSequence np;
    private final Bundle nq;
    private final long nr;
    private final float ns;
    private final int nt;
    private final long nu;

    public final class CustomAction implements Parcelable {
        public static final Parcelable.Creator CREATOR = new f();
        private final String nA;
        private final Bundle nB;
        private final int nC;
        private final CharSequence nD;

        private CustomAction(Parcel parcel) {
            this.nA = parcel.readString();
            this.nD = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.nC = parcel.readInt();
            this.nB = parcel.readBundle();
        }

        /* synthetic */ CustomAction(Parcel parcel, CustomAction customAction) {
            this(parcel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "Action:mName='" + this.nD + ", mIcon=" + this.nC + ", mExtras=" + this.nB;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.nA);
            TextUtils.writeToParcel(this.nD, parcel, i);
            parcel.writeInt(this.nC);
            parcel.writeBundle(this.nB);
        }
    }

    private PlaybackStateCompat(Parcel parcel) {
        this.nt = parcel.readInt();
        this.nr = parcel.readLong();
        this.ns = parcel.readFloat();
        this.nu = parcel.readLong();
        this.nn = parcel.readLong();
        this.nl = parcel.readLong();
        this.np = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.no = parcel.createTypedArrayList(CustomAction.CREATOR);
        this.nm = parcel.readLong();
        this.nq = parcel.readBundle();
    }

    /* synthetic */ PlaybackStateCompat(Parcel parcel, PlaybackStateCompat playbackStateCompat) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PlaybackState {");
        sb.append("state=").append(this.nt);
        sb.append(", position=").append(this.nr);
        sb.append(", buffered position=").append(this.nn);
        sb.append(", speed=").append(this.ns);
        sb.append(", updated=").append(this.nu);
        sb.append(", actions=").append(this.nl);
        sb.append(", error=").append(this.np);
        sb.append(", custom actions=").append(this.no);
        sb.append(", active item id=").append(this.nm);
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.nt);
        parcel.writeLong(this.nr);
        parcel.writeFloat(this.ns);
        parcel.writeLong(this.nu);
        parcel.writeLong(this.nn);
        parcel.writeLong(this.nl);
        TextUtils.writeToParcel(this.np, parcel, i);
        parcel.writeTypedList(this.no);
        parcel.writeLong(this.nm);
        parcel.writeBundle(this.nq);
    }
}
