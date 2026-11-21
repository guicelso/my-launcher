package com.android.ex.editstyledtext;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.Selection;

/* JADX INFO: loaded from: classes.dex */
class EditStyledText$SoftKeyReceiver extends ResultReceiver {
    d kR;
    int kS;
    int kT;

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, Bundle bundle) {
        if (i != 2) {
            Selection.setSelection(this.kR.getText(), this.kT, this.kS);
        }
    }
}
