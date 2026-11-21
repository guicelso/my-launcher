package com.tw.service;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class X implements View.OnClickListener {
    final /* synthetic */ Y Rc;

    X(Y y, TWService tWService) {
        this.Rc = y;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.Rc.hide();
    }
}
