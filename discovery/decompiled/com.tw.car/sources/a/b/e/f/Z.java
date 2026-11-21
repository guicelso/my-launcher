package a.b.e.f;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.SearchView;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes.dex */
public class Z implements Parcelable.ClassLoaderCreator<SearchView.e> {
    @Override // android.os.Parcelable.Creator
    public Object createFromParcel(Parcel parcel) {
        return new SearchView.e(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public Object[] newArray(int i) {
        return new SearchView.e[i];
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public SearchView.e createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new SearchView.e(parcel, classLoader);
    }
}
