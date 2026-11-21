package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class MediaDescriptionCompat implements Parcelable {
    public static final Parcelable.Creator CREATOR = new d();
    private final CharSequence nE;
    private Object nF;
    private final Bundle nG;
    private final Bitmap nH;
    private final Uri nI;
    private final String nJ;
    private final Uri nK;
    private final CharSequence nL;
    private final CharSequence nM;

    private MediaDescriptionCompat(Parcel parcel) {
        this.nJ = parcel.readString();
        this.nM = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.nL = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.nE = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.nH = (Bitmap) parcel.readParcelable(null);
        this.nI = (Uri) parcel.readParcelable(null);
        this.nG = parcel.readBundle();
        this.nK = (Uri) parcel.readParcelable(null);
    }

    /* synthetic */ MediaDescriptionCompat(Parcel parcel, MediaDescriptionCompat mediaDescriptionCompat) {
        this(parcel);
    }

    private MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.nJ = str;
        this.nM = charSequence;
        this.nL = charSequence2;
        this.nE = charSequence3;
        this.nH = bitmap;
        this.nI = uri;
        this.nG = bundle;
        this.nK = uri2;
    }

    /* synthetic */ MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2, MediaDescriptionCompat mediaDescriptionCompat) {
        this(str, charSequence, charSequence2, charSequence3, bitmap, uri, bundle, uri2);
    }

    public static MediaDescriptionCompat rw(Object obj) {
        if (obj == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        g gVar = new g();
        gVar.rC(c.rr(obj));
        gVar.rF(c.rt(obj));
        gVar.rE(c.rs(obj));
        gVar.ry(c.rn(obj));
        gVar.rA(c.rp(obj));
        gVar.rB(c.rq(obj));
        gVar.rz(c.ro(obj));
        if (Build.VERSION.SDK_INT >= 23) {
            gVar.rD(i.rG(obj));
        }
        MediaDescriptionCompat mediaDescriptionCompatRx = gVar.rx();
        mediaDescriptionCompatRx.nF = obj;
        return mediaDescriptionCompatRx;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Object rv() {
        if (this.nF != null || Build.VERSION.SDK_INT < 21) {
            return this.nF;
        }
        Object objRI = j.rI();
        j.rN(objRI, this.nJ);
        j.rP(objRI, this.nM);
        j.rO(objRI, this.nL);
        j.rJ(objRI, this.nE);
        j.rL(objRI, this.nH);
        j.rM(objRI, this.nI);
        j.rK(objRI, this.nG);
        if (Build.VERSION.SDK_INT >= 23) {
            a.rl(objRI, this.nK);
        }
        this.nF = j.rH(objRI);
        return this.nF;
    }

    public String toString() {
        return this.nM + ", " + this.nL + ", " + this.nE;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            c.ru(rv(), parcel, i);
            return;
        }
        parcel.writeString(this.nJ);
        TextUtils.writeToParcel(this.nM, parcel, i);
        TextUtils.writeToParcel(this.nL, parcel, i);
        TextUtils.writeToParcel(this.nE, parcel, i);
        parcel.writeParcelable(this.nH, i);
        parcel.writeParcelable(this.nI, i);
        parcel.writeBundle(this.nG);
    }
}
