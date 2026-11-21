package a.b.d.a;

import android.app.Notification;
import android.os.Bundle;
import android.util.SparseArray;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Object f51a = new Object();

    static {
        new Object();
    }

    public static Bundle a(d dVar) {
        Bundle bundle = new Bundle();
        bundle.putInt("icon", dVar.g);
        bundle.putCharSequence("title", dVar.h);
        bundle.putParcelable("actionIntent", dVar.i);
        Bundle bundle2 = dVar.f42a;
        Bundle bundle3 = bundle2 != null ? new Bundle(bundle2) : new Bundle();
        bundle3.putBoolean("android.support.allowGeneratedReplies", dVar.d);
        bundle.putBundle("extras", bundle3);
        bundle.putParcelableArray("remoteInputs", a(dVar.f43b));
        bundle.putBoolean("showsUserInterface", dVar.e);
        bundle.putInt("semanticAction", dVar.f);
        return bundle;
    }

    public static SparseArray<Bundle> a(List<Bundle> list) {
        int size = list.size();
        SparseArray<Bundle> sparseArray = null;
        for (int i = 0; i < size; i++) {
            Bundle bundle = list.get(i);
            if (bundle != null) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                sparseArray.put(i, bundle);
            }
        }
        return sparseArray;
    }

    public static Bundle[] a(h[] hVarArr) {
        if (hVarArr == null) {
            return null;
        }
        Bundle[] bundleArr = new Bundle[hVarArr.length];
        if (hVarArr.length <= 0) {
            return bundleArr;
        }
        h hVar = hVarArr[0];
        new Bundle();
        throw null;
    }

    public static Bundle a(Notification.Builder builder, d dVar) {
        builder.addAction(dVar.g, dVar.h, dVar.i);
        Bundle bundle = new Bundle(dVar.f42a);
        h[] hVarArr = dVar.f43b;
        if (hVarArr != null) {
            bundle.putParcelableArray("android.support.remoteInputs", a(hVarArr));
        }
        h[] hVarArr2 = dVar.f44c;
        if (hVarArr2 != null) {
            bundle.putParcelableArray("android.support.dataRemoteInputs", a(hVarArr2));
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", dVar.d);
        return bundle;
    }
}
