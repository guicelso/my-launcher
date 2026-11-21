package com.tw.service.xt.aidl;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
class b implements IMusicCallBack {
    public static IMusicCallBack sDefaultImpl;
    private IBinder mRemote;

    b(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override // com.tw.service.xt.aidl.IMusicCallBack
    public void extendedInterface(Bundle bundle) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IMusicCallBack");
            if (bundle != null) {
                parcelObtain.writeInt(1);
                bundle.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            if (this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) || IMusicCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.extendedInterface(bundle);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IMusicCallBack
    public void musicMode(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IMusicCallBack");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) || IMusicCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicMode(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IMusicCallBack
    public void musicNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IMusicCallBack");
            if (this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) || IMusicCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IMusicCallBack
    public void musicPause() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IMusicCallBack");
            if (this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) || IMusicCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicPause();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IMusicCallBack
    public void musicPlay() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IMusicCallBack");
            if (this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) || IMusicCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicPlay();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IMusicCallBack
    public void musicPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IMusicCallBack");
            if (this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) || IMusicCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
