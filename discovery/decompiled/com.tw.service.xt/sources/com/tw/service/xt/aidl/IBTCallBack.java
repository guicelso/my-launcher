package com.tw.service.xt.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface IBTCallBack extends IInterface {

    public class Default implements IBTCallBack {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void btAccept() {
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void btCall(String str) {
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void btGetConnectedStatus() {
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void btGetPhoneStatus() {
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void btHangUP() {
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void btNext() {
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void btOpenMusic() {
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void btPlayMusic() {
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void btPre() {
        }

        @Override // com.tw.service.xt.aidl.IBTCallBack
        public void extendedInterface(Bundle bundle) {
        }
    }

    public abstract class Stub extends Binder implements IBTCallBack {
        private static final String DESCRIPTOR = "com.tw.service.xt.aidl.IBTCallBack";
        static final int TRANSACTION_btAccept = 2;
        static final int TRANSACTION_btCall = 1;
        static final int TRANSACTION_btGetConnectedStatus = 9;
        static final int TRANSACTION_btGetPhoneStatus = 8;
        static final int TRANSACTION_btHangUP = 3;
        static final int TRANSACTION_btNext = 7;
        static final int TRANSACTION_btOpenMusic = 4;
        static final int TRANSACTION_btPlayMusic = 5;
        static final int TRANSACTION_btPre = 6;
        static final int TRANSACTION_extendedInterface = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBTCallBack asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IBTCallBack)) ? new a(iBinder) : (IBTCallBack) iInterfaceQueryLocalInterface;
        }

        public static IBTCallBack getDefaultImpl() {
            return a.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IBTCallBack iBTCallBack) {
            if (a.sDefaultImpl != null || iBTCallBack == null) {
                return false;
            }
            a.sDefaultImpl = iBTCallBack;
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
                    btCall(parcel.readString());
                    break;
                case TRANSACTION_btAccept /* 2 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btAccept();
                    break;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    btHangUP();
                    break;
                case TRANSACTION_btOpenMusic /* 4 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btOpenMusic();
                    break;
                case TRANSACTION_btPlayMusic /* 5 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btPlayMusic();
                    break;
                case TRANSACTION_btPre /* 6 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btPre();
                    break;
                case TRANSACTION_btNext /* 7 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btNext();
                    break;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    btGetPhoneStatus();
                    break;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    btGetConnectedStatus();
                    break;
                case TRANSACTION_extendedInterface /* 10 */:
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

    void btAccept();

    void btCall(String str);

    void btGetConnectedStatus();

    void btGetPhoneStatus();

    void btHangUP();

    void btNext();

    void btOpenMusic();

    void btPlayMusic();

    void btPre();

    void extendedInterface(Bundle bundle);
}
