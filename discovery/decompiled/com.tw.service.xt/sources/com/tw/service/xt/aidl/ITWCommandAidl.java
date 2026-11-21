package com.tw.service.xt.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface ITWCommandAidl extends IInterface {

    public class Default implements ITWCommandAidl {
        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void OpenProject(String str) {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void back() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btAccept() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btCall(String str) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btGetConnectedStatus() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btGetPhoneStatus() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btHangUP() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btNext() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btOpenMusic() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btPlayMusic() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btPre() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btSetCallStatus(int i, String str, String str2) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btSetConnectedStatus(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void btSetPhoneStatus(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void closeScreen() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void extendedInterface(Bundle bundle) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void getReverseStatus() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void getSleepStatus() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void getVolume() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void getVolumeStatus() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void goHome() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void killProject(String str) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void lightDown() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void lightMax() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void lightMin() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void lightUp() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void lightValue(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void mediaNext() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void mediaPause() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void mediaPlay() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void mediaPre() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void musicMode(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void musicNext() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void musicPause() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void musicPlay() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void musicPre() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void openScreen() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void radioBand(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void radioBandChange() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void radioNext() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void radioOpenChannel(String str) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void radioPre() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void radioSearchNext() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void radioSearchPre() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void radioSetChannel(int i) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void registerBTCallBack(IBTCallBack iBTCallBack) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void registerMusicCallBack(IMusicCallBack iMusicCallBack) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void registerRadioCallBack(IRadioCallBack iRadioCallBack) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void registerTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void registerVideoCallBack(IVideoCallBack iVideoCallBack) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void setVolumeMute(boolean z) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void setWifi(boolean z) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void unRegisterBTCallBack(IBTCallBack iBTCallBack) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void unRegisterMusicCallBack(IMusicCallBack iMusicCallBack) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void unRegisterRadioCallBack(IRadioCallBack iRadioCallBack) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void unRegisterTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void unRegisterVideoCallBack(IVideoCallBack iVideoCallBack) {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void videoNext() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void videoPause() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void videoPlay() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void videoPre() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void volumeDown() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void volumeMax() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void volumeMin() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void volumeMute() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void volumeUp() {
        }

        @Override // com.tw.service.xt.aidl.ITWCommandAidl
        public void volumeValue(int i) {
        }
    }

    public abstract class Stub extends Binder implements ITWCommandAidl {
        private static final String DESCRIPTOR = "com.tw.service.xt.aidl.ITWCommandAidl";
        static final int TRANSACTION_OpenProject = 51;
        static final int TRANSACTION_back = 49;
        static final int TRANSACTION_btAccept = 20;
        static final int TRANSACTION_btCall = 19;
        static final int TRANSACTION_btGetConnectedStatus = 28;
        static final int TRANSACTION_btGetPhoneStatus = 26;
        static final int TRANSACTION_btHangUP = 21;
        static final int TRANSACTION_btNext = 25;
        static final int TRANSACTION_btOpenMusic = 22;
        static final int TRANSACTION_btPlayMusic = 23;
        static final int TRANSACTION_btPre = 24;
        static final int TRANSACTION_btSetCallStatus = 30;
        static final int TRANSACTION_btSetConnectedStatus = 29;
        static final int TRANSACTION_btSetPhoneStatus = 27;
        static final int TRANSACTION_closeScreen = 65;
        static final int TRANSACTION_extendedInterface = 67;
        static final int TRANSACTION_getReverseStatus = 52;
        static final int TRANSACTION_getSleepStatus = 53;
        static final int TRANSACTION_getVolume = 46;
        static final int TRANSACTION_getVolumeStatus = 47;
        static final int TRANSACTION_goHome = 48;
        static final int TRANSACTION_killProject = 50;
        static final int TRANSACTION_lightDown = 60;
        static final int TRANSACTION_lightMax = 61;
        static final int TRANSACTION_lightMin = 62;
        static final int TRANSACTION_lightUp = 59;
        static final int TRANSACTION_lightValue = 63;
        static final int TRANSACTION_mediaNext = 55;
        static final int TRANSACTION_mediaPause = 58;
        static final int TRANSACTION_mediaPlay = 57;
        static final int TRANSACTION_mediaPre = 56;
        static final int TRANSACTION_musicMode = 35;
        static final int TRANSACTION_musicNext = 31;
        static final int TRANSACTION_musicPause = 34;
        static final int TRANSACTION_musicPlay = 33;
        static final int TRANSACTION_musicPre = 32;
        static final int TRANSACTION_openScreen = 66;
        static final int TRANSACTION_radioBand = 17;
        static final int TRANSACTION_radioBandChange = 18;
        static final int TRANSACTION_radioNext = 11;
        static final int TRANSACTION_radioOpenChannel = 16;
        static final int TRANSACTION_radioPre = 12;
        static final int TRANSACTION_radioSearchNext = 13;
        static final int TRANSACTION_radioSearchPre = 14;
        static final int TRANSACTION_radioSetChannel = 15;
        static final int TRANSACTION_registerBTCallBack = 9;
        static final int TRANSACTION_registerMusicCallBack = 5;
        static final int TRANSACTION_registerRadioCallBack = 3;
        static final int TRANSACTION_registerTWCommandCallback = 1;
        static final int TRANSACTION_registerVideoCallBack = 7;
        static final int TRANSACTION_setVolumeMute = 54;
        static final int TRANSACTION_setWifi = 64;
        static final int TRANSACTION_unRegisterBTCallBack = 10;
        static final int TRANSACTION_unRegisterMusicCallBack = 6;
        static final int TRANSACTION_unRegisterRadioCallBack = 4;
        static final int TRANSACTION_unRegisterTWCommandCallback = 2;
        static final int TRANSACTION_unRegisterVideoCallBack = 8;
        static final int TRANSACTION_videoNext = 36;
        static final int TRANSACTION_videoPause = 39;
        static final int TRANSACTION_videoPlay = 38;
        static final int TRANSACTION_videoPre = 37;
        static final int TRANSACTION_volumeDown = 41;
        static final int TRANSACTION_volumeMax = 43;
        static final int TRANSACTION_volumeMin = 44;
        static final int TRANSACTION_volumeMute = 42;
        static final int TRANSACTION_volumeUp = 40;
        static final int TRANSACTION_volumeValue = 45;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITWCommandAidl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ITWCommandAidl)) ? new d(iBinder) : (ITWCommandAidl) iInterfaceQueryLocalInterface;
        }

        public static ITWCommandAidl getDefaultImpl() {
            return d.sDefaultImpl;
        }

        public static boolean setDefaultImpl(ITWCommandAidl iTWCommandAidl) {
            if (d.sDefaultImpl != null || iTWCommandAidl == null) {
                return false;
            }
            d.sDefaultImpl = iTWCommandAidl;
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
                    registerTWCommandCallback(ITWCommandCallbackAidl.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case TRANSACTION_unRegisterTWCommandCallback /* 2 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    unRegisterTWCommandCallback(ITWCommandCallbackAidl.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerRadioCallBack(IRadioCallBack.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case TRANSACTION_unRegisterRadioCallBack /* 4 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    unRegisterRadioCallBack(IRadioCallBack.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case TRANSACTION_registerMusicCallBack /* 5 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerMusicCallBack(IMusicCallBack.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case TRANSACTION_unRegisterMusicCallBack /* 6 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    unRegisterMusicCallBack(IMusicCallBack.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case TRANSACTION_registerVideoCallBack /* 7 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerVideoCallBack(IVideoCallBack.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    unRegisterVideoCallBack(IVideoCallBack.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerBTCallBack(IBTCallBack.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case TRANSACTION_unRegisterBTCallBack /* 10 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    unRegisterBTCallBack(IBTCallBack.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case TRANSACTION_radioNext /* 11 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioNext();
                    break;
                case TRANSACTION_radioPre /* 12 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioPre();
                    break;
                case TRANSACTION_radioSearchNext /* 13 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioSearchNext();
                    break;
                case TRANSACTION_radioSearchPre /* 14 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioSearchPre();
                    break;
                case TRANSACTION_radioSetChannel /* 15 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioSetChannel(parcel.readInt());
                    break;
                case TRANSACTION_radioOpenChannel /* 16 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioOpenChannel(parcel.readString());
                    break;
                case TRANSACTION_radioBand /* 17 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioBand(parcel.readInt());
                    break;
                case TRANSACTION_radioBandChange /* 18 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    radioBandChange();
                    break;
                case TRANSACTION_btCall /* 19 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btCall(parcel.readString());
                    break;
                case TRANSACTION_btAccept /* 20 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btAccept();
                    break;
                case TRANSACTION_btHangUP /* 21 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btHangUP();
                    break;
                case TRANSACTION_btOpenMusic /* 22 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btOpenMusic();
                    break;
                case TRANSACTION_btPlayMusic /* 23 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btPlayMusic();
                    break;
                case TRANSACTION_btPre /* 24 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btPre();
                    break;
                case TRANSACTION_btNext /* 25 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btNext();
                    break;
                case TRANSACTION_btGetPhoneStatus /* 26 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btGetPhoneStatus();
                    break;
                case TRANSACTION_btSetPhoneStatus /* 27 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btSetPhoneStatus(parcel.readInt());
                    break;
                case TRANSACTION_btGetConnectedStatus /* 28 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btGetConnectedStatus();
                    break;
                case TRANSACTION_btSetConnectedStatus /* 29 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btSetConnectedStatus(parcel.readInt());
                    break;
                case TRANSACTION_btSetCallStatus /* 30 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    btSetCallStatus(parcel.readInt(), parcel.readString(), parcel.readString());
                    break;
                case TRANSACTION_musicNext /* 31 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicNext();
                    break;
                case TRANSACTION_musicPre /* 32 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicPre();
                    break;
                case TRANSACTION_musicPlay /* 33 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicPlay();
                    break;
                case TRANSACTION_musicPause /* 34 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicPause();
                    break;
                case TRANSACTION_musicMode /* 35 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    musicMode(parcel.readInt());
                    break;
                case TRANSACTION_videoNext /* 36 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    videoNext();
                    break;
                case TRANSACTION_videoPre /* 37 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    videoPre();
                    break;
                case TRANSACTION_videoPlay /* 38 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    videoPlay();
                    break;
                case TRANSACTION_videoPause /* 39 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    videoPause();
                    break;
                case TRANSACTION_volumeUp /* 40 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    volumeUp();
                    break;
                case TRANSACTION_volumeDown /* 41 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    volumeDown();
                    break;
                case TRANSACTION_volumeMute /* 42 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    volumeMute();
                    break;
                case TRANSACTION_volumeMax /* 43 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    volumeMax();
                    break;
                case TRANSACTION_volumeMin /* 44 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    volumeMin();
                    break;
                case TRANSACTION_volumeValue /* 45 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    volumeValue(parcel.readInt());
                    break;
                case TRANSACTION_getVolume /* 46 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    getVolume();
                    break;
                case TRANSACTION_getVolumeStatus /* 47 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    getVolumeStatus();
                    break;
                case TRANSACTION_goHome /* 48 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    goHome();
                    break;
                case TRANSACTION_back /* 49 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    back();
                    break;
                case TRANSACTION_killProject /* 50 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    killProject(parcel.readString());
                    break;
                case TRANSACTION_OpenProject /* 51 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    OpenProject(parcel.readString());
                    break;
                case TRANSACTION_getReverseStatus /* 52 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    getReverseStatus();
                    break;
                case TRANSACTION_getSleepStatus /* 53 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    getSleepStatus();
                    break;
                case TRANSACTION_setVolumeMute /* 54 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    setVolumeMute(parcel.readInt() != 0);
                    break;
                case TRANSACTION_mediaNext /* 55 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    mediaNext();
                    break;
                case TRANSACTION_mediaPre /* 56 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    mediaPre();
                    break;
                case TRANSACTION_mediaPlay /* 57 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    mediaPlay();
                    break;
                case TRANSACTION_mediaPause /* 58 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    mediaPause();
                    break;
                case TRANSACTION_lightUp /* 59 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    lightUp();
                    break;
                case TRANSACTION_lightDown /* 60 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    lightDown();
                    break;
                case TRANSACTION_lightMax /* 61 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    lightMax();
                    break;
                case TRANSACTION_lightMin /* 62 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    lightMin();
                    break;
                case TRANSACTION_lightValue /* 63 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    lightValue(parcel.readInt());
                    break;
                case TRANSACTION_setWifi /* 64 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    setWifi(parcel.readInt() != 0);
                    break;
                case TRANSACTION_closeScreen /* 65 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    closeScreen();
                    break;
                case TRANSACTION_openScreen /* 66 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    openScreen();
                    break;
                case TRANSACTION_extendedInterface /* 67 */:
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

    void OpenProject(String str);

    void back();

    void btAccept();

    void btCall(String str);

    void btGetConnectedStatus();

    void btGetPhoneStatus();

    void btHangUP();

    void btNext();

    void btOpenMusic();

    void btPlayMusic();

    void btPre();

    void btSetCallStatus(int i, String str, String str2);

    void btSetConnectedStatus(int i);

    void btSetPhoneStatus(int i);

    void closeScreen();

    void extendedInterface(Bundle bundle);

    void getReverseStatus();

    void getSleepStatus();

    void getVolume();

    void getVolumeStatus();

    void goHome();

    void killProject(String str);

    void lightDown();

    void lightMax();

    void lightMin();

    void lightUp();

    void lightValue(int i);

    void mediaNext();

    void mediaPause();

    void mediaPlay();

    void mediaPre();

    void musicMode(int i);

    void musicNext();

    void musicPause();

    void musicPlay();

    void musicPre();

    void openScreen();

    void radioBand(int i);

    void radioBandChange();

    void radioNext();

    void radioOpenChannel(String str);

    void radioPre();

    void radioSearchNext();

    void radioSearchPre();

    void radioSetChannel(int i);

    void registerBTCallBack(IBTCallBack iBTCallBack);

    void registerMusicCallBack(IMusicCallBack iMusicCallBack);

    void registerRadioCallBack(IRadioCallBack iRadioCallBack);

    void registerTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl);

    void registerVideoCallBack(IVideoCallBack iVideoCallBack);

    void setVolumeMute(boolean z);

    void setWifi(boolean z);

    void unRegisterBTCallBack(IBTCallBack iBTCallBack);

    void unRegisterMusicCallBack(IMusicCallBack iMusicCallBack);

    void unRegisterRadioCallBack(IRadioCallBack iRadioCallBack);

    void unRegisterTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl);

    void unRegisterVideoCallBack(IVideoCallBack iVideoCallBack);

    void videoNext();

    void videoPause();

    void videoPlay();

    void videoPre();

    void volumeDown();

    void volumeMax();

    void volumeMin();

    void volumeMute();

    void volumeUp();

    void volumeValue(int i);
}
