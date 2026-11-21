package com.tw.service;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class ba implements View.OnClickListener {
    final /* synthetic */ da Rc;

    ba(da daVar, TWService tWService) {
        this.Rc = daVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int i = la.mModel;
        if (i == 16 || i == 18 || i == 19) {
            this.Rc.n.pa.a(198, 1, 0);
        } else {
            this.Rc.n.pa.write(1287, 0);
        }
        this.Rc.Mk = true;
        this.Rc.hide();
    }
}
