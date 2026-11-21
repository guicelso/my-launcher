package com.tw.service.xt.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface IVideoCallBack extends IInterface {

    public class Default implements IVideoCallBack {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.tw.service.xt.aidl.IVideoCallBack
        public void extendedInterface(Bundle bundle) {
        }

        @Override // com.tw.service.xt.aidl.IVideoCallBack
        public void videoNext() {
        }

        @Override // com.tw.service.xt.aidl.IVideoCallBack
        public void videoPause() {
        }

        @Override // com.tw.service.xt.aidl.IVideoCallBack
        public void videoPlay() {
        }

        @Override // com.tw.service.xt.aidl.IVideoCallBack
        public void videoPre() {
        }
    }

    public abstract class Stub extends Binder implements IVideoCallBack {
        private static final String DESCRIPTOR = "com.tw.service.xt.aidl.IVideoCallBack";
        static final int TRANSACTION_extendedInterface = 5;
        static final int TRANSACTION_videoNext = 1;
        static final int TRANSACTION_videoPause = 4;
        static final int TRANSACTION_videoPlay = 3;
        static final int TRANSACTION_videoPre = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVideoCallBack asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IVideoCallBack)) ? new f(iBinder) : (IVideoCallBack) iInterfaceQueryLocalInterface;
        }

        public static IVideoCallBack getDefaultImpl() {
            return f.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IVideoCallBack iVideoCallBack) {
            if (f.sDefaultImpl != null || iVideoCallBack == null) {
                return false;
            }
            f.sDefaultImpl = iVideoCallBack;
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                videoNext();
            } else if (i == TRANSACTION_videoPre) {
                parcel.enforceInterface(DESCRIPTOR);
                videoPre();
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                videoPlay();
            } else if (i == TRANSACTION_videoPause) {
                parcel.enforceInterface(DESCRIPTOR);
                videoPause();
            } else {
                if (i != TRANSACTION_extendedInterface) {
                    if (i != 1598968902) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                extendedInterface(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
            }
            parcel2.writeNoException();
            return true;
        }
    }

    void extendedInterface(Bundle bundle);

    void videoNext();

    void videoPause();

    void videoPlay();

    void videoPre();
}
