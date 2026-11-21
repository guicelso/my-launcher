package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: renamed from: android.support.v4.app.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0005f implements Parcelable.Creator {
    C0005f() {
    }

    @Override // android.os.Parcelable.Creator
    public Object createFromParcel(Parcel parcel) {
        return new BackStackState(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public Object[] newArray(int i) {
        return new BackStackState[i];
    }
}
