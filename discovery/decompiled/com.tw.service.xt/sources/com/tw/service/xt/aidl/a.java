package com.tw.service.xt.aidl;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
class a implements IBTCallBack {
    public static IBTCallBack sDefaultImpl;
    private IBinder mRemote;

    a(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void btAccept() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            if (this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btAccept();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void btCall(String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            parcelObtain.writeString(str);
            if (this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btCall(str);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void btGetConnectedStatus() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            if (this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btGetConnectedStatus();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void btGetPhoneStatus() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            if (this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btGetPhoneStatus();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void btHangUP() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            if (this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btHangUP();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void btNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            if (this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void btOpenMusic() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            if (this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btOpenMusic();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void btPlayMusic() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            if (this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btPlayMusic();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void btPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            if (this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IBTCallBack
    public void extendedInterface(Bundle bundle) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IBTCallBack");
            if (bundle != null) {
                parcelObtain.writeInt(1);
                bundle.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            if (this.mRemote.transact(10, parcelObtain, parcelObtain2, 0) || IBTCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.extendedInterface(bundle);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
