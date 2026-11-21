package com.tw.core.messenger.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.tw.core.utils.B;

/* JADX INFO: loaded from: classes.dex */
public class d implements ServiceConnection {
    private String f;
    private f g;
    private Messenger h = new Messenger(new e(this));
    private Messenger i;
    private b j;
    private a k;

    public d(Context context, String str, b bVar, a aVar) {
        this.f = str;
        this.k = aVar;
        this.j = bVar;
    }

    public boolean e(Message message, f fVar) {
        this.g = fVar;
        if (this.i != null) {
            try {
                message.replyTo = this.h;
                this.i.send(message);
                B.ci("Connection", "data send %s", message);
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        B.ci("Connection", "onServiceConnected %s", componentName.getClassName());
        this.j.d.put(this.f, this);
        this.i = new Messenger(iBinder);
        if (this.k != null) {
            this.k.b(this);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        B.ci("Connection", "onServiceDisconnected %s", componentName.getClassName());
        this.j.d.remove(this.f);
        this.i = null;
        this.k = null;
    }
}
