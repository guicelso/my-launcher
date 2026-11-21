package com.tw.service.xt.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface IMusicCallBack extends IInterface {

    public class Default implements IMusicCallBack {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.tw.service.xt.aidl.IMusicCallBack
        public void extendedInterface(Bundle bundle) {
        }

        @Override // com.tw.service.xt.aidl.IMusicCallBack
        public void musicMode(int i) {
        }

        @Override // com.tw.service.xt.aidl.IMusicCallBack
        public void musicNext() {
        }

        @Override // com.tw.service.xt.aidl.IMusicCallBack
        public void musicPause() {
        }

        @Override // com.tw.service.xt.aidl.IMusicCallBack
        public void musicPlay() {
        }

        @Override // com.tw.service.xt.aidl.IMusicCallBack
        public void musicPre() {
        }
    }

    public abstract class Stub extends Binder implements IMusicCallBack {
        private static final String DESCRIPTOR = "com.tw.service.xt.aidl.IMusicCallBack";
        static final int TRANSACTION_extendedInterface = 6;
        static final int TRANSACTION_musicMode = 5;
        static final int TRANSACTION_musicNext = 1;
        static final int TRANSACTION_musicPause = 4;
        static final int TRANSACTION_musicPlay = 3;
        static final int TRANSACTION_musicPre = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMusicCallBack asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMusicCallBack)) ? new b(iBinder) : (IMusicCallBack) iInterfaceQueryLocalInterface;
        }

        public static IMusicCallBack getDefaultImpl() {
            return b.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IMusicCallBack iMusicCallBack) {
            if (b.sDefaultImpl != null || iMusicCallBack == null) {
                return false;
            }
            b.sDefaultImpl = iMusicCallBack;
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicNext();
                    break;
                case TRANSACTION_musicPre /* 2 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicPre();
                    break;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicPlay();
                    break;
                case TRANSACTION_musicPause /* 4 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicPause();
                    break;
                case TRANSACTION_musicMode /* 5 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicMode(parcel.readInt());
                    break;
                case TRANSACTION_extendedInterface /* 6 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    extendedInterface(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeNoException();
            return true;
        }
    }

    void extendedInterface(Bundle bundle);

    void musicMode(int i);

    void musicNext();

    void musicPause();

    void musicPlay();

    void musicPre();
}
