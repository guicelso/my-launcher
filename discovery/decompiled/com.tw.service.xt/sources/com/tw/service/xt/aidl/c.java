package com.tw.service.xt.aidl;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
class c implements IRadioCallBack {
    public static IRadioCallBack sDefaultImpl;
    private IBinder mRemote;

    c(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override // com.tw.service.xt.aidl.IRadioCallBack
    public void extendedInterface(Bundle bundle) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IRadioCallBack");
            if (bundle != null) {
                parcelObtain.writeInt(1);
                bundle.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            if (this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) || IRadioCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.extendedInterface(bundle);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IRadioCallBack
    public void radioBand(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IRadioCallBack");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) || IRadioCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioBand(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IRadioCallBack
    public void radioBandChange() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IRadioCallBack");
            if (this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) || IRadioCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioBandChange();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IRadioCallBack
    public void radioNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IRadioCallBack");
            if (this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) || IRadioCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IRadioCallBack
    public void radioPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IRadioCallBack");
            if (this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) || IRadioCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IRadioCallBack
    public void radioSearchNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IRadioCallBack");
            if (this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) || IRadioCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioSearchNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IRadioCallBack
    public void radioSearchPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IRadioCallBack");
            if (this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) || IRadioCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioSearchPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.IRadioCallBack
    public void radioSetChannel(String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.IRadioCallBack");
            parcelObtain.writeString(str);
            if (this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) || IRadioCallBack.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioSetChannel(str);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
