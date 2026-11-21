package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public final class g {
    private CharSequence nS;
    private Bundle nT;
    private Bitmap nU;
    private Uri nV;
    private String nW;
    private Uri nX;
    private CharSequence nY;
    private CharSequence nZ;

    public g rA(Bitmap bitmap) {
        this.nU = bitmap;
        return this;
    }

    public g rB(Uri uri) {
        this.nV = uri;
        return this;
    }

    public g rC(String str) {
        this.nW = str;
        return this;
    }

    public g rD(Uri uri) {
        this.nX = uri;
        return this;
    }

    public g rE(CharSequence charSequence) {
        this.nY = charSequence;
        return this;
    }

    public g rF(CharSequence charSequence) {
        this.nZ = charSequence;
        return this;
    }

    public MediaDescriptionCompat rx() {
        return new MediaDescriptionCompat(this.nW, this.nZ, this.nY, this.nS, this.nU, this.nV, this.nT, this.nX, null);
    }

    public g ry(CharSequence charSequence) {
        this.nS = charSequence;
        return this;
    }

    public g rz(Bundle bundle) {
        this.nT = bundle;
        return this;
    }
}
