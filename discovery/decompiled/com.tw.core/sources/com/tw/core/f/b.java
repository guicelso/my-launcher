package com.tw.core.f;

import android.text.TextUtils;
import com.tw.core.utils.B;
import com.tw.core.utils.E;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
abstract class b implements Runnable {
    protected com.tw.core.a.b ic;

    b() {
    }

    protected void nb(long j) {
        if (ne() != null) {
            File file = new File(ne());
            if (E.cC(file)) {
                B.ci("BaseLogCollector", "handle clear dir %s", ne());
                ArrayList<File> arrayList = new ArrayList();
                List listCH = E.cH(file, new i(this, arrayList));
                for (File file2 : arrayList) {
                    B.ci("BaseLogCollector", "file %s delete %s", file2.getName(), Boolean.valueOf(file2.delete()));
                }
                if (listCH.size() >= j) {
                    B.cj("BaseLogCollector", "clear all file", new Object[0]);
                    E.cI(file);
                }
            }
        }
    }

    protected boolean nc() {
        return (this.ic == null || TextUtils.isEmpty(this.ic.el()) || TextUtils.isEmpty(this.ic.ek()) || TextUtils.isEmpty(this.ic.em())) ? false : true;
    }

    public void nd() {
        if (this.ic != null) {
            h.nr(this.ic.el());
        }
    }

    abstract String ne();

    public void nf() {
    }
}
