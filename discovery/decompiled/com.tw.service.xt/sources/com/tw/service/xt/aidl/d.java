package com.tw.service.xt.aidl;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
class d implements ITWCommandAidl {
    public static ITWCommandAidl sDefaultImpl;
    private IBinder mRemote;

    d(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void OpenProject(String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeString(str);
            if (this.mRemote.transact(51, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.OpenProject(str);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void back() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(49, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.back();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btAccept() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(20, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btAccept();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btCall(String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeString(str);
            if (this.mRemote.transact(19, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btCall(str);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btGetConnectedStatus() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(28, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btGetConnectedStatus();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btGetPhoneStatus() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(26, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btGetPhoneStatus();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btHangUP() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(21, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btHangUP();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(25, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btOpenMusic() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(22, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btOpenMusic();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btPlayMusic() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(23, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btPlayMusic();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(24, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btSetCallStatus(int i, String str, String str2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(i);
            parcelObtain.writeString(str);
            parcelObtain.writeString(str2);
            if (this.mRemote.transact(30, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btSetCallStatus(i, str, str2);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btSetConnectedStatus(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(29, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btSetConnectedStatus(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btSetPhoneStatus(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(27, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.btSetPhoneStatus(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void closeScreen() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(65, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.closeScreen();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void extendedInterface(Bundle bundle) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (bundle != null) {
                parcelObtain.writeInt(1);
                bundle.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            if (this.mRemote.transact(67, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.extendedInterface(bundle);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void getReverseStatus() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(52, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.getReverseStatus();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void getSleepStatus() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(53, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.getSleepStatus();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void getVolume() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(46, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.getVolume();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void getVolumeStatus() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(47, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.getVolumeStatus();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void goHome() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(48, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.goHome();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void killProject(String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeString(str);
            if (this.mRemote.transact(50, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.killProject(str);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightDown() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(60, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.lightDown();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightMax() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(61, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.lightMax();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightMin() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(62, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.lightMin();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightUp() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(59, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.lightUp();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightValue(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(63, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.lightValue(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void mediaNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(55, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.mediaNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void mediaPause() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(58, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.mediaPause();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void mediaPlay() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(57, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.mediaPlay();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void mediaPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(56, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.mediaPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicMode(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(35, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicMode(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(31, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicPause() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(34, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicPause();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicPlay() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(33, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicPlay();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(32, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.musicPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void openScreen() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(66, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.openScreen();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioBand(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(17, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioBand(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioBandChange() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(18, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioBandChange();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(11, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioOpenChannel(String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeString(str);
            if (this.mRemote.transact(16, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioOpenChannel(str);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(12, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioSearchNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(13, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioSearchNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioSearchPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(14, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioSearchPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioSetChannel(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(15, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.radioSetChannel(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerBTCallBack(IBTCallBack iBTCallBack) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iBTCallBack != null ? iBTCallBack.asBinder() : null);
            if (this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.registerBTCallBack(iBTCallBack);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerMusicCallBack(IMusicCallBack iMusicCallBack) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iMusicCallBack != null ? iMusicCallBack.asBinder() : null);
            if (this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.registerMusicCallBack(iMusicCallBack);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerRadioCallBack(IRadioCallBack iRadioCallBack) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iRadioCallBack != null ? iRadioCallBack.asBinder() : null);
            if (this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.registerRadioCallBack(iRadioCallBack);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iTWCommandCallbackAidl != null ? iTWCommandCallbackAidl.asBinder() : null);
            if (this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.registerTWCommandCallback(iTWCommandCallbackAidl);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerVideoCallBack(IVideoCallBack iVideoCallBack) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iVideoCallBack != null ? iVideoCallBack.asBinder() : null);
            if (this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.registerVideoCallBack(iVideoCallBack);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void setVolumeMute(boolean z) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(z ? 1 : 0);
            if (this.mRemote.transact(54, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.setVolumeMute(z);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void setWifi(boolean z) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(z ? 1 : 0);
            if (this.mRemote.transact(64, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.setWifi(z);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterBTCallBack(IBTCallBack iBTCallBack) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iBTCallBack != null ? iBTCallBack.asBinder() : null);
            if (this.mRemote.transact(10, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.unRegisterBTCallBack(iBTCallBack);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterMusicCallBack(IMusicCallBack iMusicCallBack) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iMusicCallBack != null ? iMusicCallBack.asBinder() : null);
            if (this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.unRegisterMusicCallBack(iMusicCallBack);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterRadioCallBack(IRadioCallBack iRadioCallBack) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iRadioCallBack != null ? iRadioCallBack.asBinder() : null);
            if (this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.unRegisterRadioCallBack(iRadioCallBack);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iTWCommandCallbackAidl != null ? iTWCommandCallbackAidl.asBinder() : null);
            if (this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.unRegisterTWCommandCallback(iTWCommandCallbackAidl);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterVideoCallBack(IVideoCallBack iVideoCallBack) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeStrongBinder(iVideoCallBack != null ? iVideoCallBack.asBinder() : null);
            if (this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.unRegisterVideoCallBack(iVideoCallBack);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void videoNext() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(36, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.videoNext();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void videoPause() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(39, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.videoPause();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void videoPlay() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(38, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.videoPlay();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void videoPre() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(37, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.videoPre();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeDown() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(41, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.volumeDown();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeMax() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(43, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.volumeMax();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeMin() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(44, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.volumeMin();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeMute() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(42, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.volumeMute();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeUp() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            if (this.mRemote.transact(40, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.volumeUp();
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeValue(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.tw.service.xt.aidl.ITWCommandAidl");
            parcelObtain.writeInt(i);
            if (this.mRemote.transact(45, parcelObtain, parcelObtain2, 0) || ITWCommandAidl.Stub.getDefaultImpl() == null) {
                parcelObtain2.readException();
            } else {
                sDefaultImpl.volumeValue(i);
            }
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
