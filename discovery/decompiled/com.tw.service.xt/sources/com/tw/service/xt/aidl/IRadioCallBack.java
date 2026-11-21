package com.tw.service.xt.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface IRadioCallBack extends IInterface {

    public class Default implements IRadioCallBack {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.tw.service.xt.aidl.IRadioCallBack
        public void extendedInterface(Bundle bundle) {
        }

        @Override // com.tw.service.xt.aidl.IRadioCallBack
        public void radioBand(int i) {
        }

        @Override // com.tw.service.xt.aidl.IRadioCallBack
        public void radioBandChange() {
        }

        @Override // com.tw.service.xt.aidl.IRadioCallBack
        public void radioNext() {
        }

        @Override // com.tw.service.xt.aidl.IRadioCallBack
        public void radioPre() {
        }

        @Override // com.tw.service.xt.aidl.IRadioCallBack
        public void radioSearchNext() {
        }

        @Override // com.tw.service.xt.aidl.IRadioCallBack
        public void radioSearchPre() {
        }

        @Override // com.tw.service.xt.aidl.IRadioCallBack
        public void radioSetChannel(String str) {
        }
    }

    public abstract class Stub extends Binder implements IRadioCallBack {
        private static final String DESCRIPTOR = "com.tw.service.xt.aidl.IRadioCallBack";
        static final int TRANSACTION_extendedInterface = 8;
        static final int TRANSACTION_radioBand = 6;
        static final int TRANSACTION_radioBandChange = 7;
        static final int TRANSACTION_radioNext = 1;
        static final int TRANSACTION_radioPre = 2;
        static final int TRANSACTION_radioSearchNext = 3;
        static final int TRANSACTION_radioSearchPre = 4;
        static final int TRANSACTION_radioSetChannel = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioCallBack asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRadioCallBack)) ? new c(iBinder) : (IRadioCallBack) iInterfaceQueryLocalInterface;
        }

        public static IRadioCallBack getDefaultImpl() {
            return c.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IRadioCallBack iRadioCallBack) {
            if (c.sDefaultImpl != null || iRadioCallBack == null) {
                return false;
            }
            c.sDefaultImpl = iRadioCallBack;
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
                    radioNext();
                    break;
                case TRANSACTION_radioPre /* 2 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioPre();
                    break;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioSearchNext();
                    break;
                case TRANSACTION_radioSearchPre /* 4 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioSearchPre();
                    break;
                case TRANSACTION_radioSetChannel /* 5 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioSetChannel(parcel.readString());
                    break;
                case TRANSACTION_radioBand /* 6 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioBand(parcel.readInt());
                    break;
                case TRANSACTION_radioBandChange /* 7 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioBandChange();
                    break;
                case 8:
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

    void radioBand(int i);

    void radioBandChange();

    void radioNext();

    void radioPre();

    void radioSearchNext();

    void radioSearchPre();

    void radioSetChannel(String str);
}
