package com.tw.service.xt.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface ITWCommandCallbackAidl extends IInterface {

    public class Default implements ITWCommandCallbackAidl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
        public void onBtCallStatus(int i, String str, String str2) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
        public void onBtConnectedStatus(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
        public void onBtPhoneStatus(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
        public void onExtendedInterface(Bundle bundle) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
        public void onReverseStatus(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
        public void onSleepStatus(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
        public void onSystemVolume(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
        public void onVolumeStatus(int i) {
        }
    }

    public abstract class Stub extends Binder implements ITWCommandCallbackAidl {
        private static final String DESCRIPTOR = "com.tw.service.xt.aidl.ITWCommandCallbackAidl";
        static final int TRANSACTION_onBtCallStatus = 4;
        static final int TRANSACTION_onBtConnectedStatus = 5;
        static final int TRANSACTION_onBtPhoneStatus = 3;
        static final int TRANSACTION_onExtendedInterface = 8;
        static final int TRANSACTION_onReverseStatus = 6;
        static final int TRANSACTION_onSleepStatus = 7;
        static final int TRANSACTION_onSystemVolume = 1;
        static final int TRANSACTION_onVolumeStatus = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITWCommandCallbackAidl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ITWCommandCallbackAidl)) ? new e(iBinder) : (ITWCommandCallbackAidl) iInterfaceQueryLocalInterface;
        }

        public static ITWCommandCallbackAidl getDefaultImpl() {
            return e.sDefaultImpl;
        }

        public static boolean setDefaultImpl(ITWCommandCallbackAidl iTWCommandCallbackAidl) {
            if (e.sDefaultImpl != null || iTWCommandCallbackAidl == null) {
                return false;
            }
            e.sDefaultImpl = iTWCommandCallbackAidl;
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
                    onSystemVolume(parcel.readInt());
                    break;
                case TRANSACTION_onVolumeStatus /* 2 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    onVolumeStatus(parcel.readInt());
                    break;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    onBtPhoneStatus(parcel.readInt());
                    break;
                case TRANSACTION_onBtCallStatus /* 4 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    onBtCallStatus(parcel.readInt(), parcel.readString(), parcel.readString());
                    break;
                case TRANSACTION_onBtConnectedStatus /* 5 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    onBtConnectedStatus(parcel.readInt());
                    break;
                case TRANSACTION_onReverseStatus /* 6 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    onReverseStatus(parcel.readInt());
                    break;
                case TRANSACTION_onSleepStatus /* 7 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    onSleepStatus(parcel.readInt());
                    break;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    onExtendedInterface(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeNoException();
            return true;
        }
    }

    void onBtCallStatus(int i, String str, String str2);

    void onBtConnectedStatus(int i);

    void onBtPhoneStatus(int i);

    void onExtendedInterface(Bundle bundle);

    void onReverseStatus(int i);

    void onSleepStatus(int i);

    void onSystemVolume(int i);

    void onVolumeStatus(int i);
}
