package android.support.v4.media;

import android.os.Bundle;
import android.support.v4.app.C0006g;
import android.support.v4.media.session.MediaSessionCompat$Token;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class p {
    private final o mImpl;

    p(o oVar) {
        this.mImpl = oVar;
    }

    public static p fromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (bundle.getInt("android.media.token.type", -1) == 100) {
            return new p(new r(MediaSessionCompat$Token.fromBundle(bundle.getBundle("android.media.token.LEGACY"))));
        }
        int i = bundle.getInt("android.media.token.uid");
        int i2 = bundle.getInt("android.media.token.type", -1);
        String string = bundle.getString("android.media.token.package_name");
        String string2 = bundle.getString("android.media.token.service_name");
        String string3 = bundle.getString("android.media.token.session_id");
        c cVarAsInterface = b.asInterface(C0006g.a(bundle, "android.media.token.session_binder"));
        if (i2 != 0) {
            if (i2 != 1 && i2 != 2) {
                throw new IllegalArgumentException("Invalid type");
            }
            if (TextUtils.isEmpty(string2)) {
                throw new IllegalArgumentException("Session service needs service name");
            }
        } else if (cVarAsInterface == null) {
            throw new IllegalArgumentException("Unexpected token for session, binder=" + cVarAsInterface);
        }
        if (TextUtils.isEmpty(string) || string3 == null) {
            throw new IllegalArgumentException("Package name nor ID cannot be null.");
        }
        return new p(new q(i, i2, string, string2, string3, cVarAsInterface));
    }

    public boolean equals(Object obj) {
        if (obj instanceof p) {
            return this.mImpl.equals(((p) obj).mImpl);
        }
        return false;
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    public String toString() {
        return this.mImpl.toString();
    }
}
