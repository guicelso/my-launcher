package com.tw.core.model;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.B;
import com.tw.core.utils.M;

/* JADX INFO: loaded from: classes.dex */
class n implements ServiceConnection {
    final /* synthetic */ MainService hW;

    n(MainService mainService) {
        this.hW = mainService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        B.ci("MainService", "连接远程服务成功", new Object[0]);
        this.hW.hy = com.tw.core.b.asInterface(iBinder);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        B.cj("MainService", "onServiceDisconnected", new Object[0]);
        if (this.hW.hy != null) {
            try {
                String strMX = this.hW.hy.mX();
                if (M.ds(strMX)) {
                    B.ci("MainService", "正在运行的服务:" + strMX, new Object[0]);
                } else {
                    B.cj("MainService", "服务已死，启动服务:" + strMX, new Object[0]);
                    this.hW.startService(new Intent(DoFunApplication.nu(), (Class<?>) DameonService.class));
                }
            } catch (RemoteException e) {
                B.ck("MainService", e);
            }
        }
        this.hW.mA();
    }
}
