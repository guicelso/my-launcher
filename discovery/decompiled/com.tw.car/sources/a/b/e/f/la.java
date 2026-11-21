package a.b.e.f;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes.dex */
public class la implements Parcelable.ClassLoaderCreator<Toolbar.d> {
    @Override // android.os.Parcelable.Creator
    public Object createFromParcel(Parcel parcel) {
        return new Toolbar.d(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public Object[] newArray(int i) {
        return new Toolbar.d[i];
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public Toolbar.d createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new Toolbar.d(parcel, classLoader);
    }
}
