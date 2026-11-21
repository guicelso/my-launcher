package com.tw.service;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class Z implements View.OnClickListener {
    final /* synthetic */ aa Rc;

    Z(aa aaVar, TWService tWService) {
        this.Rc = aaVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        aa.a(this.Rc);
    }
}
