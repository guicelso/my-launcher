package com.android.ex.editstyledtext;

import android.util.Log;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

/* JADX INFO: loaded from: classes.dex */
public class i extends InputConnectionWrapper {
    d lf;

    public i(InputConnection inputConnection, d dVar) {
        super(inputConnection, true);
        this.lf = dVar;
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i) {
        Log.d("EditStyledText", "--- commitText:");
        this.lf.kQ.pu();
        return super.commitText(charSequence, i);
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        Log.d("EditStyledText", "--- finishcomposing:");
        if (!this.lf.pG() && !this.lf.pE() && !this.lf.pF()) {
            this.lf.pK();
        }
        return super.finishComposingText();
    }
}
