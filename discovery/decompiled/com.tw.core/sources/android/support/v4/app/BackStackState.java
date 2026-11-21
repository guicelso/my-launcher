package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class BackStackState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new O();
    final int mG;
    final CharSequence mH;
    final int mI;
    final CharSequence mJ;
    final int mK;
    final int[] mL;
    final ArrayList mM;
    final ArrayList mN;
    final String mName;
    final int mO;
    final int mP;

    public BackStackState(Parcel parcel) {
        this.mL = parcel.createIntArray();
        this.mO = parcel.readInt();
        this.mP = parcel.readInt();
        this.mName = parcel.readString();
        this.mK = parcel.readInt();
        this.mI = parcel.readInt();
        this.mJ = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mG = parcel.readInt();
        this.mH = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mM = parcel.createStringArrayList();
        this.mN = parcel.createStringArrayList();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.mL);
        parcel.writeInt(this.mO);
        parcel.writeInt(this.mP);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mK);
        parcel.writeInt(this.mI);
        TextUtils.writeToParcel(this.mJ, parcel, 0);
        parcel.writeInt(this.mG);
        TextUtils.writeToParcel(this.mH, parcel, 0);
        parcel.writeStringList(this.mM);
        parcel.writeStringList(this.mN);
    }
}
