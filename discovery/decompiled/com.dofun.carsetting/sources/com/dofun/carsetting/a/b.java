package com.dofun.carsetting.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: compiled from: IConfigInfo.java */
/* JADX INFO: loaded from: classes.dex */
public interface b extends IInterface {
    void a(String str, String str2);

    /* JADX INFO: compiled from: IConfigInfo.java */
    public static abstract class a extends Binder implements b {
        public static b a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.dofun.carsetting.aidl.IConfigInfo");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof b)) {
                return (b) iInterfaceQueryLocalInterface;
            }
            return new C0008a(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1) {
                if (i == 1598968902) {
                    parcel2.writeString("com.dofun.carsetting.aidl.IConfigInfo");
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface("com.dofun.carsetting.aidl.IConfigInfo");
            a(parcel.readString(), parcel.readString());
            parcel2.writeNoException();
            return true;
        }

        /* JADX INFO: renamed from: com.dofun.carsetting.a.b$a$a, reason: collision with other inner class name */
        /* JADX INFO: compiled from: IConfigInfo.java */
        private static class C0008a implements b {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            private IBinder f33a;

            C0008a(IBinder iBinder) {
                this.f33a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f33a;
            }

            @Override // com.dofun.carsetting.a.b
            public void a(String str, String str2) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.dofun.carsetting.aidl.IConfigInfo");
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.f33a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
