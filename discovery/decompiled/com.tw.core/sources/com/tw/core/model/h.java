package com.tw.core.model;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.M;

/* JADX INFO: loaded from: classes.dex */
class h implements ServiceConnection {
    final /* synthetic */ DameonService hQ;

    h(DameonService dameonService) {
        this.hQ = dameonService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        C0065s.bK(DameonService.hi, "连接成功");
        this.hQ.hl = com.tw.core.b.asInterface(iBinder);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        if (this.hQ.hl != null) {
            try {
                String strMX = this.hQ.hl.mX();
                if (M.ds(strMX)) {
                    C0065s.bK(DameonService.hi, "正在运行的服务:" + strMX);
                } else {
                    C0065s.bK(DameonService.hi, "服务已死，启动服务:" + strMX);
                    this.hQ.startService(new Intent(DoFunApplication.nu(), (Class<?>) MainService.class));
                }
            } catch (RemoteException e) {
                C0065s.bL(DameonService.hi, e);
            }
        }
        this.hQ.mi();
    }
}
