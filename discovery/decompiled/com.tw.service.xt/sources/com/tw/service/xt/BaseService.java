package com.tw.service.xt;

import android.os.Bundle;
import com.tw.service.xt.aidl.IBTCallBack;
import com.tw.service.xt.aidl.IMusicCallBack;
import com.tw.service.xt.aidl.IRadioCallBack;
import com.tw.service.xt.aidl.ITWCommandCallbackAidl;
import com.tw.service.xt.aidl.IVideoCallBack;

/* JADX INFO: loaded from: classes.dex */
public interface BaseService {
    public static final String BACK = "back";
    public static final String BTACCEPT = "btAccept";
    public static final String BTCALL = "btCall";
    public static final String BTGETCONNECTEDSTATUS = "btGetConnectedStatus";
    public static final String BTGETPHONESTATUS = "btGetPhoneStatus";
    public static final String BTHANDUP = "btHangUP";
    public static final String BTNEXT = "btNext";
    public static final String BTOPENMUSIC = "btOpenMusic";
    public static final String BTPLAYMUSIC = "btPlayMusic";
    public static final String BTPRE = "btPre";
    public static final String BTSETCALLSTATUS = "btSetCallStatus";
    public static final String BTSETCONNECTEDSTATUS = "btSetConnectedStatus";
    public static final String BTSETPHONESTATUS = "btSetPhoneStatus";
    public static final String CLOSESCREEN = "closeScreen";
    public static final String DATETYPE = "dateType";
    public static final String DATETYPE_RECEIVER = "receiver";
    public static final String DATETYPE_SEND = "send";
    public static final String DATETYPE_TWDATA = "twdata";
    public static final String EXTENDEDINTERFACE = "extendedInterface";
    public static final String GETREVERSESTATUS = "getReverseStatus";
    public static final String GETSLEEPSTATUS = "getSleepStatus";
    public static final String GETVOLUME = "getVolume";
    public static final String GETVOLUMESTATUS = "getVolumeStatus";
    public static final String GOHOME = "goHome";
    public static final String KILLPROJECT = "killProject";
    public static final String LIGHTDOWN = "lightDown";
    public static final String LIGHTMAX = "lightMax";
    public static final String LIGHTMIN = "lightMin";
    public static final String LIGHTUP = "lightUp";
    public static final String LIGHTVALUE = "lightValue";
    public static final String MEDIANEXT = "mediaNext";
    public static final String MEDIAPAUSE = "mediaPause";
    public static final String MEDIAPLAY = "mediaPlay";
    public static final String MEDIAPRE = "mediaPre";
    public static final String MUSICMODE = "musicMode";
    public static final String MUSICNEXT = "musicNext";
    public static final String MUSICPAUSE = "musicPause";
    public static final String MUSICPLAY = "musicPlay";
    public static final String MUSICPRE = "musicPre";
    public static final String OPENPROJECT = "OpenProject";
    public static final String OPENSCREEN = "openScreen";
    public static final String PROJECT_AIR = "air";
    public static final String PROJECT_BT = "bt";
    public static final String PROJECT_MUSIC = "music";
    public static final String PROJECT_RADIO = "radio";
    public static final String PROJECT_SYSETM = "system";
    public static final String PROJECT_VIDEO = "video";
    public static final String RADIOBAND = "radioband";
    public static final String RADIOBANDCHANGE = "radioBandChange";
    public static final String RADIOBANDINDEX = "radioBandIndex";
    public static final String RADIONEXT = "radioNext";
    public static final String RADIOOPENCHANNEL = "radioOpenChannel";
    public static final String RADIOPRE = "radioPre";
    public static final String RADIOSEARCHNEXT = "radioSearchNext";
    public static final String RADIOSEARCHPRE = "radioSearchPre";
    public static final String RADIOSETCHANNEL = "radioSetChannel";
    public static final String SETVOLUMEMUTE = "setVolumeMute";
    public static final String SETWIFI = "setWifi";
    public static final String VIDEONEXT = "videoNext";
    public static final String VIDEOPAUSE = "videoPause";
    public static final String VIDEOPLAY = "videoPlay";
    public static final String VIDEOPRE = "videoPre";
    public static final String VOLUMEDOWN = "volumeDown";
    public static final String VOLUMEMAX = "volumeMax";
    public static final String VOLUMEMIN = "volumeMin";
    public static final String VOLUMEMUTE = "volumeMute";
    public static final String VOLUMEUP = "volumeUp";
    public static final String VOLUMEVALUE = "volumeValue";

    void registerBTCallBack(IBTCallBack iBTCallBack);

    void registerMusicCallBack(IMusicCallBack iMusicCallBack);

    void registerRadioCallBack(IRadioCallBack iRadioCallBack);

    void registerTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl);

    void registerVideoCallBack(IVideoCallBack iVideoCallBack);

    void sendBTFunction(String str, String... strArr);

    void sendExtendedInterfaFunction(Bundle bundle);

    void sendMusicFunction(String str, String... strArr);

    void sendRadioFunction(String str, String... strArr);

    void sendSystemFunction(String str, String... strArr);

    void sendVideoFunction(String str, String... strArr);

    void unRegisterBTCallBack(IBTCallBack iBTCallBack);

    void unRegisterMusicCallBack(IMusicCallBack iMusicCallBack);

    void unRegisterRadioCallBack(IRadioCallBack iRadioCallBack);

    void unRegisterTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl);

    void unRegisterVideoCallBack(IVideoCallBack iVideoCallBack);
}
