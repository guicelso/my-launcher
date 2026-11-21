package com.tw.service.xt.aidl;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
class e implements ITWCommandCallbackAidl {
    public static ITWCommandCallbackAidl sDefaultImpl;
    private IBinder mRemote;

    e(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
    public void onBtCallStatus(int i, String str, String str2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandCallbackAidl");
            parcelObtain.writeInt(i);
            parcelObtain.writeString(str);
            parcelObtain.writeString(str2);
            if (this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) || ITWCommandCallbackAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.onBtCallStatus(i, str, str2);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
    public void onBtConnectedStatus(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandCallbackAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) || ITWCommandCallbackAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.onBtConnectedStatus(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
    public void onBtPhoneStatus(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandCallbackAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) || ITWCommandCallbackAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.onBtPhoneStatus(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
    public void onExtendedInterface(Bundle bundle) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandCallbackAidl");
            if (bundle != null) {
                parcelObtain.writeInt(1);
                bundle.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            if (this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) || ITWCommandCallbackAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.onExtendedInterface(bundle);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
    public void onReverseStatus(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandCallbackAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) || ITWCommandCallbackAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.onReverseStatus(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
    public void onSleepStatus(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandCallbackAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) || ITWCommandCallbackAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.onSleepStatus(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
    public void onSystemVolume(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandCallbackAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) || ITWCommandCallbackAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.onSystemVolume(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandCallbackAidl
    public void onVolumeStatus(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandCallbackAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) || ITWCommandCallbackAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.onVolumeStatus(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
