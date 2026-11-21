package com.tw.service.xt.binder;

import android.os.Bundle;
import android.util.Log;
import com.tw.service.xt.BaseService;
import com.tw.service.xt.aidl.IBTCallBack;
import com.tw.service.xt.aidl.IMusicCallBack;
import com.tw.service.xt.aidl.IRadioCallBack;
import com.tw.service.xt.aidl.ITWCommandAidl;
import com.tw.service.xt.aidl.ITWCommandCallbackAidl;
import com.tw.service.xt.aidl.IVideoCallBack;

/* JADX INFO: loaded from: classes.dex */
class a extends ITWCommandAidl.Stub {
    final /* synthetic */ BaseService k;

    a(BaseService baseService) {
        this.k = baseService;
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void OpenProject(String str) {
        this.k.sendSystemFunction(BaseService.OPENPROJECT, String.valueOf(str));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void back() {
        this.k.sendSystemFunction(BaseService.BACK, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btAccept() {
        this.k.sendBTFunction(BaseService.BTACCEPT, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btCall(String str) {
        this.k.sendBTFunction(BaseService.BTCALL, str);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btGetConnectedStatus() {
        this.k.sendBTFunction(BaseService.BTGETCONNECTEDSTATUS, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btGetPhoneStatus() {
        this.k.sendBTFunction(BaseService.BTGETPHONESTATUS, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btHangUP() {
        this.k.sendBTFunction(BaseService.BTHANDUP, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btNext() {
        this.k.sendBTFunction(BaseService.BTNEXT, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btOpenMusic() {
        this.k.sendBTFunction(BaseService.BTOPENMUSIC, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btPlayMusic() {
        this.k.sendBTFunction(BaseService.BTPLAYMUSIC, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btPre() {
        this.k.sendBTFunction(BaseService.BTPRE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btSetCallStatus(int i, String str, String str2) {
        this.k.sendBTFunction(BaseService.BTSETCALLSTATUS, i + "", str, str2);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btSetConnectedStatus(int i) {
        this.k.sendBTFunction(BaseService.BTSETCONNECTEDSTATUS, String.valueOf(i));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void btSetPhoneStatus(int i) {
        this.k.sendBTFunction(BaseService.BTSETPHONESTATUS, String.valueOf(i));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void closeScreen() {
        this.k.sendSystemFunction(BaseService.CLOSESCREEN, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void extendedInterface(Bundle bundle) {
        this.k.sendExtendedInterfaFunction(bundle);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void getReverseStatus() {
        this.k.sendSystemFunction(BaseService.GETREVERSESTATUS, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void getSleepStatus() {
        this.k.sendSystemFunction(BaseService.GETSLEEPSTATUS, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void getVolume() {
        this.k.sendSystemFunction(BaseService.GETVOLUME, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void getVolumeStatus() {
        this.k.sendSystemFunction(BaseService.GETVOLUMESTATUS, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void goHome() {
        this.k.sendSystemFunction(BaseService.GOHOME, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void killProject(String str) {
        this.k.sendSystemFunction(BaseService.KILLPROJECT, String.valueOf(str));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightDown() {
        this.k.sendSystemFunction(BaseService.LIGHTDOWN, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightMax() {
        this.k.sendSystemFunction(BaseService.LIGHTMAX, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightMin() {
        this.k.sendSystemFunction(BaseService.LIGHTMIN, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightUp() {
        this.k.sendSystemFunction(BaseService.LIGHTUP, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void lightValue(int i) {
        this.k.sendSystemFunction(BaseService.LIGHTVALUE, String.valueOf(i));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void mediaNext() {
        this.k.sendSystemFunction(BaseService.MEDIANEXT, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void mediaPause() {
        this.k.sendSystemFunction(BaseService.MEDIAPAUSE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void mediaPlay() {
        this.k.sendSystemFunction(BaseService.MEDIAPLAY, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void mediaPre() {
        this.k.sendSystemFunction(BaseService.MEDIAPRE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicMode(int i) {
        this.k.sendMusicFunction(BaseService.MUSICMODE, String.valueOf(i));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicNext() {
        this.k.sendMusicFunction(BaseService.MUSICNEXT, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicPause() {
        this.k.sendMusicFunction(BaseService.MUSICPAUSE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicPlay() {
        this.k.sendMusicFunction(BaseService.MUSICPLAY, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void musicPre() {
        this.k.sendMusicFunction(BaseService.MUSICPRE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void openScreen() {
        this.k.sendSystemFunction(BaseService.OPENSCREEN, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioBand(int i) {
        this.k.sendRadioFunction(BaseService.RADIOBAND, String.valueOf(i));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioBandChange() {
        this.k.sendRadioFunction(BaseService.RADIOBANDCHANGE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioNext() {
        this.k.sendRadioFunction(BaseService.RADIONEXT, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioOpenChannel(String str) {
        this.k.sendRadioFunction(BaseService.RADIOBANDINDEX, String.valueOf(str));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioPre() {
        this.k.sendRadioFunction(BaseService.RADIOPRE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioSearchNext() {
        this.k.sendRadioFunction(BaseService.RADIOSEARCHNEXT, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioSearchPre() {
        this.k.sendRadioFunction(BaseService.RADIOSEARCHPRE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void radioSetChannel(int i) {
        this.k.sendRadioFunction(BaseService.RADIOSETCHANNEL, String.valueOf(i));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerBTCallBack(IBTCallBack iBTCallBack) {
        this.k.registerBTCallBack(iBTCallBack);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerMusicCallBack(IMusicCallBack iMusicCallBack) {
        this.k.registerMusicCallBack(iMusicCallBack);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerRadioCallBack(IRadioCallBack iRadioCallBack) {
        this.k.registerRadioCallBack(iRadioCallBack);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl) {
        this.k.registerTWCommandCallback(iTWCommandCallbackAidl);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void registerVideoCallBack(IVideoCallBack iVideoCallBack) {
        this.k.registerVideoCallBack(iVideoCallBack);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void setVolumeMute(boolean z) {
        Log.d("LZP", "b:" + z);
        this.k.sendSystemFunction(BaseService.SETVOLUMEMUTE, String.valueOf(z));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void setWifi(boolean z) {
        this.k.sendSystemFunction(BaseService.SETWIFI, String.valueOf(z));
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterBTCallBack(IBTCallBack iBTCallBack) {
        this.k.unRegisterBTCallBack(iBTCallBack);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterMusicCallBack(IMusicCallBack iMusicCallBack) {
        this.k.unRegisterMusicCallBack(iMusicCallBack);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterRadioCallBack(IRadioCallBack iRadioCallBack) {
        this.k.unRegisterRadioCallBack(iRadioCallBack);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl) {
        this.k.unRegisterTWCommandCallback(iTWCommandCallbackAidl);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void unRegisterVideoCallBack(IVideoCallBack iVideoCallBack) {
        this.k.unRegisterVideoCallBack(iVideoCallBack);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void videoNext() {
        this.k.sendVideoFunction(BaseService.VIDEONEXT, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void videoPause() {
        this.k.sendVideoFunction(BaseService.VIDEOPAUSE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void videoPlay() {
        this.k.sendVideoFunction(BaseService.VIDEOPLAY, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void videoPre() {
        this.k.sendVideoFunction(BaseService.VIDEOPRE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeDown() {
        this.k.sendSystemFunction(BaseService.VOLUMEDOWN, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeMax() {
        this.k.sendSystemFunction(BaseService.VOLUMEMAX, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeMin() {
        this.k.sendSystemFunction(BaseService.VOLUMEMIN, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeMute() {
        this.k.sendSystemFunction(BaseService.VOLUMEMUTE, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeUp() {
        this.k.sendSystemFunction(BaseService.VOLUMEUP, new String[0]);
    }

    @Override // com.tw.service.xt.aidl.ITWCommandAidl
    public void volumeValue(int i) {
        this.k.sendSystemFunction(BaseService.VOLUMEVALUE, String.valueOf(i));
    }
}
