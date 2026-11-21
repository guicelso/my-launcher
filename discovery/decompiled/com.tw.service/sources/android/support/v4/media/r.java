package android.support.v4.media;

import android.support.v4.media.session.MediaSessionCompat$Token;

/* JADX INFO: loaded from: classes.dex */
final class r implements o {
    private final MediaSessionCompat$Token ne;

    r(MediaSessionCompat$Token mediaSessionCompat$Token) {
        this.ne = mediaSessionCompat$Token;
    }

    public boolean equals(Object obj) {
        if (obj instanceof r) {
            return this.ne.equals(((r) obj).ne);
        }
        return false;
    }

    public int hashCode() {
        return this.ne.hashCode();
    }

    public String toString() {
        StringBuilder sbE = b.a.a.a.a.e("SessionToken2 {legacyToken=");
        sbE.append(this.ne);
        sbE.append("}");
        return sbE.toString();
    }
}
