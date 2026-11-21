package com.tw.service;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class ca implements View.OnClickListener {
    final /* synthetic */ da Rc;

    ca(da daVar, TWService tWService) {
        this.Rc = daVar;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x004c A[PHI: r0 r3 r5
      0x004c: PHI (r0v2 int) = (r0v0 int), (r0v4 int) binds: [B:20:0x0049, B:17:0x0034] A[DONT_GENERATE, DONT_INLINE]
      0x004c: PHI (r3v6 int) = (r3v4 int), (r3v8 int) binds: [B:20:0x0049, B:17:0x0034] A[DONT_GENERATE, DONT_INLINE]
      0x004c: PHI (r5v9 com.tw.service.la) = (r5v7 com.tw.service.la), (r5v12 com.tw.service.la) binds: [B:20:0x0049, B:17:0x0034] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        la laVar;
        int i;
        int i2 = la.mModel;
        int i3 = 198;
        int i4 = 0;
        if (i2 == 16 || i2 == 18 || i2 == 19) {
            this.Rc.n.pa.a(198, 0, this.Rc.n.qa == 1 ? 0 : 1);
            return;
        }
        if (i2 == 17 || i2 == 22) {
            laVar = this.Rc.n.pa;
            i = 171;
            if (this.Rc.n.qa != 1) {
                i4 = 1;
            }
        } else {
            if (i2 != 48) {
                return;
            }
            laVar = this.Rc.n.pa;
            i3 = 131;
            i = 26;
            if (this.Rc.n.qa != 1) {
                i4 = 1;
            }
        }
        laVar.a(i3, i, i4);
    }
}
