package com.dofun.carsetting.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: compiled from: IConfigController.java */
/* JADX INFO: loaded from: classes.dex */
public interface a extends IInterface {
    void a(b bVar);

    void a(String str);

    void a(String str, String str2);

    void b(b bVar);

    /* JADX INFO: renamed from: com.dofun.carsetting.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: IConfigController.java */
    public static abstract class AbstractBinderC0007a extends Binder implements a {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public AbstractBinderC0007a() {
            attachInterface(this, "com.dofun.carsetting.aidl.IConfigController");
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1598968902) {
                parcel2.writeString("com.dofun.carsetting.aidl.IConfigController");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.dofun.carsetting.aidl.IConfigController");
                    a(b.a.a(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface("com.dofun.carsetting.aidl.IConfigController");
                    a(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface("com.dofun.carsetting.aidl.IConfigController");
                    a(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.dofun.carsetting.aidl.IConfigController");
                    b(b.a.a(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }
}
