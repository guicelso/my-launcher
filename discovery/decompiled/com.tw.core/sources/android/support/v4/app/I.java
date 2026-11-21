package android.support.v4.app;

import android.app.RemoteInput;

/* JADX INFO: loaded from: classes.dex */
class I {
    I() {
    }

    static RemoteInput[] rf(z[] zVarArr) {
        if (zVarArr == null) {
            return null;
        }
        RemoteInput[] remoteInputArr = new RemoteInput[zVarArr.length];
        for (int i = 0; i < zVarArr.length; i++) {
            z zVar = zVarArr[i];
            remoteInputArr[i] = new RemoteInput.Builder(zVar.qW()).setLabel(zVar.qV()).setChoices(zVar.qT()).setAllowFreeFormInput(zVar.qS()).addExtras(zVar.qU()).build();
        }
        return remoteInputArr;
    }
}
