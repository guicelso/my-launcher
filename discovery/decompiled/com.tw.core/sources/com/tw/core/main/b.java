package com.tw.core.main;

import android.widget.Toast;
import com.tw.core.utils.InterfaceC0068v;

/* JADX INFO: loaded from: classes.dex */
class b implements InterfaceC0068v {
    final /* synthetic */ MainActivity ko;

    b(MainActivity mainActivity) {
        this.ko = mainActivity;
    }

    @Override // com.tw.core.utils.InterfaceC0068v
    public void bX(int i, String str) {
        Toast.makeText(this.ko, "安装TWcore失败 " + i, 1).show();
    }

    @Override // com.tw.core.utils.InterfaceC0068v
    public void bY(String str) {
        Toast.makeText(this.ko, "安装TWcore成功", 1).show();
    }
}
