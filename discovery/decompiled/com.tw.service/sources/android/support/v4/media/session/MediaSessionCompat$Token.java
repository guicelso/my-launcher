package android.support.v4.media.session;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.C0006g;

/* JADX INFO: loaded from: classes.dex */
public final class MediaSessionCompat$Token implements Parcelable {
    public static final Parcelable.Creator CREATOR = new o();
    private final Object se;
    private e te;

    MediaSessionCompat$Token(Object obj, e eVar, android.support.v4.media.p pVar) {
        this.se = obj;
        this.te = eVar;
    }

    public static MediaSessionCompat$Token fromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        e eVarAsInterface = d.asInterface(C0006g.a(bundle, "android.support.v4.media.session.EXTRA_BINDER"));
        android.support.v4.media.p pVarFromBundle = android.support.v4.media.p.fromBundle(bundle.getBundle("android.support.v4.media.session.SESSION_TOKEN2"));
        MediaSessionCompat$Token mediaSessionCompat$Token = (MediaSessionCompat$Token) bundle.getParcelable("android.support.v4.media.session.TOKEN");
        if (mediaSessionCompat$Token == null) {
            return null;
        }
        return new MediaSessionCompat$Token(mediaSessionCompat$Token.se, eVarAsInterface, pVarFromBundle);
    }

    public e I() {
        return this.te;
    }

    public void a(android.support.v4.media.p pVar) {
    }

    public void a(e eVar) {
        this.te = eVar;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaSessionCompat$Token)) {
            return false;
        }
        MediaSessionCompat$Token mediaSessionCompat$Token = (MediaSessionCompat$Token) obj;
        Object obj2 = this.se;
        if (obj2 == null) {
            return mediaSessionCompat$Token.se == null;
        }
        Object obj3 = mediaSessionCompat$Token.se;
        if (obj3 == null) {
            return false;
        }
        return obj2.equals(obj3);
    }

    public int hashCode() {
        Object obj = this.se;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2 = Build.VERSION.SDK_INT;
        parcel.writeParcelable((Parcelable) this.se, i);
    }
}
