package com.tw.core.main;

import android.widget.Toast;
import com.tw.core.utils.InterfaceC0068v;

/* JADX INFO: loaded from: classes.dex */
class a implements InterfaceC0068v {
    final /* synthetic */ MainActivity kn;

    a(MainActivity mainActivity) {
        this.kn = mainActivity;
    }

    @Override // com.tw.core.utils.InterfaceC0068v
    public void bX(int i, String str) {
        Toast.makeText(this.kn, "安装百变主题失败 " + i, 1).show();
    }

    @Override // com.tw.core.utils.InterfaceC0068v
    public void bY(String str) {
        Toast.makeText(this.kn, "安装百变主题成功", 1).show();
    }
}
