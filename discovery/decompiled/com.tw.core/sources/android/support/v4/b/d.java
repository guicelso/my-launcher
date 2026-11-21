package android.support.v4.b;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
class d implements Parcelable.Creator {
    final e mY;

    public d(e eVar) {
        this.mY = eVar;
    }

    @Override // android.os.Parcelable.Creator
    public Object createFromParcel(Parcel parcel) {
        return this.mY.createFromParcel(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public Object[] newArray(int i) {
        return this.mY.newArray(i);
    }
}
