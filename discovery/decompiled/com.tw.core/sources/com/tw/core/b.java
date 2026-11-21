package com.tw.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import org.eclipse.paho.client.mqttv3.logging.Logger;

/* JADX INFO: loaded from: classes.dex */
public abstract class b extends Binder implements a {
    public b() {
        attachInterface(this, "com.tw.core.a");
    }

    public static a asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.tw.core.a");
        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof a)) ? new c(iBinder) : (a) iInterfaceQueryLocalInterface;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case Logger.SEVERE /* 1 */:
                parcel.enforceInterface("com.tw.core.a");
                String strMX = mX();
                parcel2.writeNoException();
                parcel2.writeString(strMX);
                return true;
            case 1598968902:
                parcel2.writeString("com.tw.core.a");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
