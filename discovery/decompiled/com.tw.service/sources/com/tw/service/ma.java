package com.tw.service;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class ma implements View.OnClickListener {
    final /* synthetic */ oa n;

    ma(oa oaVar) {
        this.n = oaVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.n.pa.write(515, 0, !this.n.Tc ? 1 : 0);
        oa oaVar = this.n;
        oaVar.Tc = !oaVar.Tc;
    }
}
