package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public final class l {
    private String ee;
    private CharSequence fe;
    private Uri ge;
    private Uri he;
    private CharSequence mDescription;
    private Bundle mExtras;
    private Bitmap mIcon;
    private CharSequence mTitle;

    public MediaDescriptionCompat build() {
        return new MediaDescriptionCompat(this.ee, this.mTitle, this.fe, this.mDescription, this.mIcon, this.ge, this.mExtras, this.he);
    }

    public l setDescription(CharSequence charSequence) {
        this.mDescription = charSequence;
        return this;
    }

    public l setExtras(Bundle bundle) {
        this.mExtras = bundle;
        return this;
    }

    public l setIconBitmap(Bitmap bitmap) {
        this.mIcon = bitmap;
        return this;
    }

    public l setIconUri(Uri uri) {
        this.ge = uri;
        return this;
    }

    public l setMediaId(String str) {
        this.ee = str;
        return this;
    }

    public l setMediaUri(Uri uri) {
        this.he = uri;
        return this;
    }

    public l setSubtitle(CharSequence charSequence) {
        this.fe = charSequence;
        return this;
    }

    public l setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        return this;
    }
}
