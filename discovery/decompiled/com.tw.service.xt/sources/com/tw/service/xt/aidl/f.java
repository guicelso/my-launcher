package com.tw.service.xt.aidl;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
class f implements IVideoCallBack {
    public static IVideoCallBack sDefaultImpl;
    private IBinder mRemote;

    f(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override // com.tw.service.xt.aidl.IVideoCallBack
    public void extendedInterface(Bundle bundle) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IVideoCallBack");
            if (bundle != null) {
                parcelObtain.writeInt(1);
                bundle.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            if (this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) || IVideoCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.extendedInterface(bundle);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IVideoCallBack
    public void videoNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IVideoCallBack");
            if (this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) || IVideoCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.videoNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IVideoCallBack
    public void videoPause() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IVideoCallBack");
            if (this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) || IVideoCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.videoPause();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IVideoCallBack
    public void videoPlay() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IVideoCallBack");
            if (this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) || IVideoCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.videoPlay();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IVideoCallBack
    public void videoPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IVideoCallBack");
            if (this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) || IVideoCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.videoPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
