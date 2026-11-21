package android.support.v4.app;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
class N {
    N() {
    }

    static Bundle[] rh(z[] zVarArr) {
        if (zVarArr == null) {
            return null;
        }
        Bundle[] bundleArr = new Bundle[zVarArr.length];
        for (int i = 0; i < zVarArr.length; i++) {
            bundleArr[i] = ri(zVarArr[i]);
        }
        return bundleArr;
    }

    static Bundle ri(z zVar) {
        Bundle bundle = new Bundle();
        bundle.putString("resultKey", zVar.qW());
        bundle.putCharSequence("label", zVar.qV());
        bundle.putCharSequenceArray("choices", zVar.qT());
        bundle.putBoolean("allowFreeFormInput", zVar.qS());
        bundle.putBundle("extras", zVar.qU());
        return bundle;
    }
}
