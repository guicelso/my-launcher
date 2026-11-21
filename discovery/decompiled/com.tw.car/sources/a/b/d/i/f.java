package a.b.d.i;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.widget.NestedScrollView;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes.dex */
public class f implements Parcelable.Creator<NestedScrollView.c> {
    @Override // android.os.Parcelable.Creator
    public NestedScrollView.c createFromParcel(Parcel parcel) {
        return new NestedScrollView.c(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public NestedScrollView.c[] newArray(int i) {
        return new NestedScrollView.c[i];
    }
}
