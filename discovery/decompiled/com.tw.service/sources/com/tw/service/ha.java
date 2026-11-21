package com.tw.service;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class ha implements View.OnClickListener {
    final /* synthetic */ ia Rc;

    ha(ia iaVar, TWService tWService) {
        this.Rc = iaVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.Rc.n.pa.write(1028, 1, 0);
        this.Rc.hide();
    }
}
