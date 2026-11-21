package com.tw.service.xt;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.provider.Settings;
import android.util.Log;
import com.tw.service.xt.aidl.IBTCallBack;
import com.tw.service.xt.aidl.IMusicCallBack;
import com.tw.service.xt.aidl.IRadioCallBack;
import com.tw.service.xt.aidl.ITWCommandCallbackAidl;
import com.tw.service.xt.aidl.IVideoCallBack;
import com.tw.service.xt.binder.CommandServiceBinder;
import com.tw.service.xt.receiver.EasyConnBroadcastReceiver;
import com.tw.service.xt.utils.Constants;
import com.tw.service.xt.utils.DeviceUtils;
import com.tw.service.xt.utils.SystemUtils;

/* JADX INFO: loaded from: classes.dex */
public class CommandService extends Service implements BaseService {
    private static final String AIR_ACTION = "com.tw.ac.AirService.Bind";
    private static final String AIR_PACKAGE = "com.tw.ac";
    private static final String AIR_PACKAGE_SERVICE = "com.tw.ac.AirService";
    protected static final int SEND_EXTENDED_INTERFACE = 0;
    private static TWTW mTW;
    private int blmode;
    private EasyConnBroadcastReceiver easyConnBroadcastReceiver;
    private a.a.a.c mAircontroller;
    private boolean nightMode;
    private boolean requestData = false;
    private Binder binder = CommandServiceBinder.getSpeechCommandServiceBinder(this);
    private final String TAG = "CommandService";
    private Bundle mBundle = null;
    private boolean mBtPlayState = false;
    private ActivityManager mActivityManager = null;
    private int mSR = 0;
    private RemoteCallbackList iTWCommandCallbackAidlLis = new RemoteCallbackList();
    private RemoteCallbackList iTWRadioCallbackAidlLis = new RemoteCallbackList();
    private RemoteCallbackList iTWBTCallbackAidlLis = new RemoteCallbackList();
    private RemoteCallbackList iTWMusicCallbackAidlLis = new RemoteCallbackList();
    private RemoteCallbackList iTWVideoCallbackAidlLis = new RemoteCallbackList();
    private int currentLightValue = -1;
    private int mReverse = 0;
    private final int BT_MSG_WHAT = 1;
    private final int BT_MSG_CALLSTATE = 2;
    private final int BT_MSG_CONNECTED = 3;
    private int mSource = 0;
    private boolean setSystemVolumeUpOrDown = false;
    private int systemVolumeUpOrDown = -1;
    private boolean setSystemLightUpOrDown = false;
    private int systemLightUpOrDown = -1;
    private Handler mHandler = new Handler(new a(this));
    private ActivityInfo homeInfo = null;
    private BroadcastReceiver lightReceiver = new c(this);
    private BroadcastReceiver mSysetmReceiver = new d(this);
    private ServiceConnection mAirServiceConnection = new e(this);

    private void bindAirService() {
        Log.d("CommandService", "bindAirService");
        Intent intent = new Intent(AIR_ACTION);
        intent.setClassName(AIR_PACKAGE, AIR_PACKAGE_SERVICE);
        getApplicationContext().bindService(intent, this.mAirServiceConnection, 1);
    }

    private int getScreenBrightness() {
        try {
            return Settings.System.getInt(getContentResolver(), "screen_brightness");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void saveBrightness(int i) {
        Uri uriFor = Settings.System.getUriFor("screen_brightness");
        Settings.System.putInt(getContentResolver(), "screen_brightness", i);
        getContentResolver().notifyChange(uriFor, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCarSettingsType(String str) {
        Message messageObtain;
        try {
            if (Constants.CAR_NAVI_TYPE.equals(str)) {
                String str2 = SystemUtils.getBufferedReaderAppName("navi")[0];
                if ("com.autonavi.amapauto".equals(str2)) {
                    TWTW.mNaviType = 1;
                } else if ("com.tencent.wecarnavi".equals(str2)) {
                    TWTW.mNaviType = 2;
                } else {
                    TWTW.mNaviType = 0;
                }
                Bundle bundle = new Bundle();
                bundle.putString("action", Constants.ACTION_NAVI_INFO_REQUEST);
                bundle.putInt(Constants.CAR_NAVI_TYPE, TWTW.mNaviType);
                messageObtain = Message.obtain();
                messageObtain.what = 0;
                messageObtain.obj = bundle;
            } else {
                if (!Constants.CAR_VOICE_TYPE.equals(str)) {
                    return;
                }
                if ("com.txznet.txz".equals(SystemUtils.getBufferedReaderAppName("speech")[0])) {
                    TWTW.mVoiceType = 1;
                } else {
                    TWTW.mVoiceType = 0;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("action", Constants.ACTION_VOICE_INFO_REQUEST);
                bundle2.putInt(Constants.CAR_VOICE_TYPE, TWTW.mVoiceType);
                messageObtain = Message.obtain();
                messageObtain.what = 0;
                messageObtain.obj = bundle2;
            }
            this.mHandler.sendMessageDelayed(messageObtain, 50L);
        } catch (Exception unused) {
        }
    }

    private void sendKeyCode(int i) {
        new Thread(new b(this, i)).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLightValue() {
        int i;
        int i2 = 100;
        if (DeviceUtils.isLightFromMCU()) {
            i = this.currentLightValue;
            i2 = 10;
        } else {
            int screenBrightness = getScreenBrightness();
            if (DeviceUtils.isT7()) {
                int i3 = screenBrightness - 35;
                if (i3 < 0) {
                    i3 = 0;
                }
                i = (i3 * 100) / 220;
            } else {
                i = ((screenBrightness - 30) * 100) / 225;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("action", Constants.ACTION_LIGHT_RECIEVE);
        bundle.putInt(Constants.LIGHT_VALUE, i);
        bundle.putInt(Constants.LIGHT_MAX, i2);
        Message messageObtain = Message.obtain();
        messageObtain.what = 0;
        messageObtain.obj = bundle;
        this.mHandler.sendMessageDelayed(messageObtain, 50L);
    }

    private void sendRemainKONChange(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("action", Constants.ACTION_CAR_INFO_RECIEVE);
        bundle.putString(Constants.CAR_RemainKON, str);
        Message messageObtain = Message.obtain();
        messageObtain.what = 0;
        messageObtain.obj = bundle;
        this.mHandler.sendMessageDelayed(messageObtain, 50L);
    }

    private void sendReverseStatus() {
        try {
            int iBeginBroadcast = this.iTWCommandCallbackAidlLis.beginBroadcast();
            for (int i = 0; i < iBeginBroadcast; i++) {
                ((ITWCommandCallbackAidl) this.iTWCommandCallbackAidlLis.getBroadcastItem(i)).onReverseStatus(this.mReverse);
            }
            this.iTWCommandCallbackAidlLis.finishBroadcast();
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSourceChange(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("action", Constants.ACTION_SOURCE_RECIEVE);
        bundle.putInt(Constants.SOURCE_VALUE, this.mSource);
        Message messageObtain = Message.obtain();
        messageObtain.what = 0;
        messageObtain.obj = bundle;
        this.mHandler.sendMessageDelayed(messageObtain, 50L);
    }

    private void sendWaterTempChange(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("action", Constants.ACTION_CAR_INFO_RECIEVE);
        bundle.putString(Constants.CAR_WATER_TEMP, str);
        Message messageObtain = Message.obtain();
        messageObtain.what = 0;
        messageObtain.obj = bundle;
        this.mHandler.sendMessageDelayed(messageObtain, 50L);
    }

    private void setSystemBrightness(int i) {
        if (i < 30) {
            i = 30;
        }
        if (i > 255) {
            i = 255;
        }
        saveBrightness(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [int] */
    /* JADX WARN: Type inference failed for: r1v4 */
    private void setSystemBrightnessFromMcu(int i) {
        int i2 = this.blmode;
        ?? r1 = 1;
        if (i2 == 0) {
            r1 = this.nightMode;
        } else if (i2 == 1 || i2 != 2) {
            r1 = 0;
        }
        mTW.write(516, (int) r1, i);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        mTW = TWTW.open();
        TWTW twtw = mTW;
        if (twtw != null) {
            twtw.addHandler("CommandService", this.mHandler);
            mTW.write(516, 255);
            mTW.write(769, 255);
            mTW.write(2560, 255);
            mTW.write(2561, 255);
            mTW.write(2562, 255);
            mTW.write(2563, 255);
        }
        this.mActivityManager = (ActivityManager) getSystemService("activity");
        registerReceiver(this.lightReceiver, new IntentFilter("com.notification"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.YZS_ACC_OFF);
        intentFilter.addAction(Constants.YZS_ACC_ON);
        intentFilter.addAction(Constants.CARSETTINGS_CHANGE);
        registerReceiver(this.mSysetmReceiver, intentFilter);
        this.easyConnBroadcastReceiver = new EasyConnBroadcastReceiver(getApplicationContext());
        bindAirService();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        RemoteCallbackList remoteCallbackList = this.iTWCommandCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.kill();
        }
        BroadcastReceiver broadcastReceiver = this.lightReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        BroadcastReceiver broadcastReceiver2 = this.mSysetmReceiver;
        if (broadcastReceiver2 != null) {
            unregisterReceiver(broadcastReceiver2);
        }
        EasyConnBroadcastReceiver easyConnBroadcastReceiver = this.easyConnBroadcastReceiver;
        if (easyConnBroadcastReceiver != null) {
            easyConnBroadcastReceiver.unregisterReceiver();
        }
        if (this.mAirServiceConnection != null) {
            getApplicationContext().unbindService(this.mAirServiceConnection);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void registerBTCallBack(IBTCallBack iBTCallBack) {
        StringBuilder sb = new StringBuilder();
        sb.append("BT=registerBTCallBack=");
        sb.append(iBTCallBack != null);
        Log.d("CommandService", sb.toString());
        RemoteCallbackList remoteCallbackList = this.iTWBTCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.register(iBTCallBack);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void registerMusicCallBack(IMusicCallBack iMusicCallBack) {
        RemoteCallbackList remoteCallbackList = this.iTWMusicCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.register(iMusicCallBack);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void registerRadioCallBack(IRadioCallBack iRadioCallBack) {
        StringBuilder sb = new StringBuilder();
        sb.append("Radio=registerRadioCallBack=");
        sb.append(iRadioCallBack != null);
        Log.d("CommandService", sb.toString());
        RemoteCallbackList remoteCallbackList = this.iTWRadioCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.register(iRadioCallBack);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void registerTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl) {
        if (iTWCommandCallbackAidl != null) {
            this.iTWCommandCallbackAidlLis.register(iTWCommandCallbackAidl);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void registerVideoCallBack(IVideoCallBack iVideoCallBack) {
        RemoteCallbackList remoteCallbackList = this.iTWVideoCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.register(iVideoCallBack);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public synchronized void sendBTFunction(String str, String... strArr) {
        try {
            Log.e("CommandService", "SpeechCommandService->sendBTFunction:" + str);
            if (BaseService.BTSETPHONESTATUS.equals(str)) {
                Log.e("CommandService", "SpeechCommandService->sendBTFunction:message " + str);
                this.requestData = true;
                Message messageObtain = Message.obtain();
                messageObtain.what = 1;
                messageObtain.arg1 = Integer.parseInt(strArr[0]);
                this.mHandler.sendMessageDelayed(messageObtain, 100L);
                return;
            }
            if (BaseService.BTSETCALLSTATUS.equals(str)) {
                Message messageObtain2 = Message.obtain();
                messageObtain2.what = 2;
                messageObtain2.obj = new String[]{strArr[0], strArr[1], strArr[2]};
                this.mHandler.sendMessageDelayed(messageObtain2, 100L);
                return;
            }
            if (BaseService.BTSETCONNECTEDSTATUS.equals(str)) {
                Message messageObtain3 = Message.obtain();
                messageObtain3.what = 3;
                messageObtain3.arg1 = Integer.parseInt(strArr[0]);
                this.mHandler.sendMessageDelayed(messageObtain3, 100L);
                return;
            }
            if (this.iTWBTCallbackAidlLis != null) {
                try {
                    int iBeginBroadcast = this.iTWBTCallbackAidlLis.beginBroadcast();
                    for (int i = 0; i < iBeginBroadcast; i++) {
                        if (BaseService.BTCALL.equals(str)) {
                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).btCall(strArr[0]);
                        } else if (BaseService.BTACCEPT.equals(str)) {
                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).btAccept();
                        } else if (BaseService.BTHANDUP.equals(str)) {
                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).btHangUP();
                        } else if (BaseService.BTOPENMUSIC.equals(str)) {
                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).btOpenMusic();
                        } else if (BaseService.BTPLAYMUSIC.equals(str)) {
                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).btPlayMusic();
                        } else if (BaseService.BTPRE.equals(str)) {
                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).btPre();
                        } else if (BaseService.BTNEXT.equals(str)) {
                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).btNext();
                        } else if (BaseService.BTGETPHONESTATUS.equals(str)) {
                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).btGetPhoneStatus();
                        } else if (BaseService.BTGETCONNECTEDSTATUS.equals(str)) {
                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).btGetConnectedStatus();
                        }
                    }
                } catch (Exception e) {
                    Log.d("CommandService", "BT?" + e.toString());
                }
                this.iTWBTCallbackAidlLis.finishBroadcast();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.tw.service.xt.BaseService
    public synchronized void sendExtendedInterfaFunction(Bundle bundle) {
        String str;
        RemoteCallbackList remoteCallbackList;
        Log.e("CommandService", "SpeechCommandService->sendBundleFunction:");
        if (bundle != null) {
            try {
                String string = bundle.getString("project");
                String string2 = bundle.getString("action");
                String string3 = bundle.getString(BaseService.DATETYPE);
                int i = 0;
                if (BaseService.PROJECT_RADIO.equals(string)) {
                    int iBeginBroadcast = this.iTWRadioCallbackAidlLis.beginBroadcast();
                    while (i < iBeginBroadcast) {
                        ((IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i)).extendedInterface(bundle);
                        i++;
                    }
                    remoteCallbackList = this.iTWRadioCallbackAidlLis;
                } else if (BaseService.PROJECT_BT.equals(string)) {
                    int iBeginBroadcast2 = this.iTWBTCallbackAidlLis.beginBroadcast();
                    while (i < iBeginBroadcast2) {
                        ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i)).extendedInterface(bundle);
                        i++;
                    }
                    remoteCallbackList = this.iTWBTCallbackAidlLis;
                } else if (BaseService.PROJECT_MUSIC.equals(string)) {
                    int iBeginBroadcast3 = this.iTWMusicCallbackAidlLis.beginBroadcast();
                    while (i < iBeginBroadcast3) {
                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i)).extendedInterface(bundle);
                        i++;
                    }
                    remoteCallbackList = this.iTWMusicCallbackAidlLis;
                } else if (BaseService.PROJECT_VIDEO.equals(string)) {
                    int iBeginBroadcast4 = this.iTWVideoCallbackAidlLis.beginBroadcast();
                    while (i < iBeginBroadcast4) {
                        ((IVideoCallBack) this.iTWVideoCallbackAidlLis.getBroadcastItem(i)).extendedInterface(bundle);
                        i++;
                    }
                    remoteCallbackList = this.iTWVideoCallbackAidlLis;
                } else if (BaseService.DATETYPE_SEND.equals(string3)) {
                    if (string2.equals("com.tw.bt.av.play")) {
                        this.mBtPlayState = bundle.getBoolean("playState");
                    }
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 0;
                    messageObtain.obj = bundle;
                    this.mHandler.sendMessageDelayed(messageObtain, 50L);
                } else if (BaseService.PROJECT_AIR.equals(string)) {
                    byte b = (byte) bundle.getInt("data0", 0);
                    byte b2 = (byte) bundle.getInt("data1", 0);
                    Log.e("CommandService", "SpeechCommandService->sendBundleFunction:PROJECT_AIR-" + ((int) b) + "," + ((int) b2));
                    mTW.write(1297, b, 2, new byte[]{b2, 0});
                    int apkVersion = TWTW.getApkVersion(getApplicationContext(), AIR_PACKAGE);
                    Log.d("CommandService", "version:" + apkVersion + ",mAircontroller:" + this.mAircontroller);
                    if (apkVersion <= 2) {
                        mTW.write(34074, b, b2);
                    } else if (this.mAircontroller != null) {
                        this.mAircontroller.extendedInterface(bundle);
                    } else {
                        this.mBundle = bundle;
                        bindAirService();
                    }
                } else if (BaseService.PROJECT_SYSETM.equals(string)) {
                    if (Constants.ACTION_SOURCE_REQUEST.equals(string2)) {
                        sendSourceChange(this.mSource);
                    } else if (Constants.ACTION_LIGHT_REQUEST.equals(string2)) {
                        sendLightValue();
                    } else if (!Constants.ACTION_CAR_INFO_REQUEST.equals(string2)) {
                        if (Constants.ACTION_NAVI_INFO_REQUEST.equals(string2)) {
                            str = Constants.CAR_NAVI_TYPE;
                        } else if (Constants.ACTION_VOICE_INFO_REQUEST.equals(string2)) {
                            str = Constants.CAR_VOICE_TYPE;
                        }
                        sendCarSettingsType(str);
                    }
                } else if (BaseService.DATETYPE_TWDATA.equals(string)) {
                    byte b3 = bundle.getByte("type");
                    int i2 = bundle.getInt("what");
                    byte[] byteArray = bundle.getByteArray("data");
                    mTW.write(i2, b3, byteArray.length, byteArray);
                }
                remoteCallbackList.finishBroadcast();
            } catch (Exception e) {
                Log.d("CommandService", "sendBundleFunction?" + e.toString());
                e.printStackTrace();
            }
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void sendMusicFunction(String str, String... strArr) {
        Log.e("CommandService", "SpeechCommandService->sendMusicFunction:" + str);
        RemoteCallbackList remoteCallbackList = this.iTWMusicCallbackAidlLis;
        if (remoteCallbackList != null) {
            try {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < iBeginBroadcast; i++) {
                    if (BaseService.MUSICNEXT.equals(str)) {
                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i)).musicNext();
                    } else if (BaseService.MUSICPRE.equals(str)) {
                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i)).musicPre();
                    } else if (BaseService.MUSICPLAY.equals(str)) {
                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i)).musicPlay();
                    } else if (BaseService.MUSICPAUSE.equals(str)) {
                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i)).musicPause();
                    } else if (BaseService.MUSICMODE.equals(str)) {
                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i)).musicMode(Integer.parseInt(strArr[0]));
                    }
                }
            } catch (Exception e) {
                Log.d("CommandService", "Music?" + e.toString());
            }
            this.iTWMusicCallbackAidlLis.finishBroadcast();
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void sendRadioFunction(String str, String... strArr) {
        IRadioCallBack iRadioCallBack;
        int i;
        Log.e("CommandService", "SpeechCommandService->sendRadioFunction:" + str);
        RemoteCallbackList remoteCallbackList = this.iTWRadioCallbackAidlLis;
        if (remoteCallbackList != null) {
            try {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i2 = 0; i2 < iBeginBroadcast; i2++) {
                    if (BaseService.RADIONEXT.equals(str)) {
                        ((IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i2)).radioNext();
                    } else if (BaseService.RADIOPRE.equals(str)) {
                        ((IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i2)).radioPre();
                    } else if (BaseService.RADIOSEARCHNEXT.equals(str)) {
                        ((IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i2)).radioSearchNext();
                    } else if (BaseService.RADIOSEARCHPRE.equals(str)) {
                        ((IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i2)).radioSearchPre();
                    } else if (BaseService.RADIOSETCHANNEL.equals(str)) {
                        ((IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i2)).radioSetChannel(strArr[0]);
                    } else {
                        if (BaseService.RADIOBANDINDEX.equals(str)) {
                            iRadioCallBack = (IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i2);
                            i = Integer.parseInt(strArr[0]);
                        } else if (BaseService.RADIOBANDCHANGE.equals(str)) {
                            ((IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i2)).radioBandChange();
                        } else if (!BaseService.RADIOOPENCHANNEL.equals(str) && BaseService.RADIOBAND.equals(str)) {
                            iRadioCallBack = (IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i2);
                            i = Integer.parseInt(strArr[0]);
                        }
                        iRadioCallBack.radioBand(i);
                    }
                }
            } catch (Exception e) {
                Log.d("CommandService", "Radio?" + e.toString());
            }
            this.iTWRadioCallbackAidlLis.finishBroadcast();
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void sendSystemFunction(String str, String... strArr) {
        int i;
        RemoteCallbackList remoteCallbackList;
        TWTW twtw;
        int i2;
        TWTW twtw2;
        TWTW twtw3;
        int i3;
        TWTW twtw4;
        Log.d("CommandService", "sendSystemFunction:fun:" + str);
        try {
            int i4 = 0;
            int i5 = 1;
            if (BaseService.VOLUMEUP.equals(str)) {
                this.setSystemVolumeUpOrDown = true;
                this.systemVolumeUpOrDown = 1;
                this.requestData = true;
                twtw4 = mTW;
            } else {
                if (!BaseService.VOLUMEDOWN.equals(str)) {
                    if (BaseService.VOLUMEMUTE.equals(str)) {
                        return;
                    }
                    if (BaseService.VOLUMEMAX.equals(str)) {
                        mTW.write(515, 1, 30);
                        return;
                    }
                    if (BaseService.VOLUMEMIN.equals(str)) {
                        twtw3 = mTW;
                    } else {
                        if (BaseService.VOLUMEVALUE.equals(str)) {
                            mTW.write(515, 1, Integer.parseInt(strArr[0]));
                            return;
                        }
                        if (!BaseService.GETVOLUME.equals(str) && !BaseService.GETVOLUMESTATUS.equals(str)) {
                            if (BaseService.GOHOME.equals(str)) {
                                mTW.write(513, 1, 16);
                                return;
                            }
                            if (BaseService.BACK.equals(str)) {
                                mTW.write(513, 1, 18);
                                return;
                            }
                            if (BaseService.KILLPROJECT.equals(str)) {
                                if ((this.mSource == 1 && strArr[0].equals("com.tw.radio")) || ((this.mSource == 3 && strArr[0].equals("com.tw.music")) || strArr[0].equals("com.tw.video") || strArr[0].equals("com.tw.bt"))) {
                                    mTW.write(769, 192, 0);
                                }
                                sendBroadcast(new Intent(Constants.YZS_CLOSE).putExtra("packageName", strArr[0]));
                                return;
                            }
                            if (BaseService.OPENPROJECT.equals(str)) {
                                Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(strArr[0]);
                                launchIntentForPackage.setFlags(268435456);
                                startActivity(launchIntentForPackage);
                                return;
                            }
                            if (BaseService.LIGHTUP.equals(str)) {
                                if (!DeviceUtils.isLightFromMCU()) {
                                    setSystemBrightness(getScreenBrightness() + 25);
                                    return;
                                }
                                int i6 = this.currentLightValue + 1;
                                this.currentLightValue = i6;
                                this.currentLightValue = i6 > 10 ? 10 : this.currentLightValue;
                                i3 = this.currentLightValue;
                            } else if (BaseService.LIGHTDOWN.equals(str)) {
                                if (!DeviceUtils.isLightFromMCU()) {
                                    setSystemBrightness(getScreenBrightness() - 25);
                                    return;
                                }
                                int i7 = this.currentLightValue - 1;
                                this.currentLightValue = i7;
                                if (i7 >= 0) {
                                    i4 = this.currentLightValue;
                                }
                                this.currentLightValue = i4;
                                i3 = this.currentLightValue;
                            } else {
                                if (BaseService.LIGHTMAX.equals(str)) {
                                    if (DeviceUtils.isLightFromMCU()) {
                                        setSystemBrightnessFromMcu(10);
                                        return;
                                    } else {
                                        setSystemBrightness(255);
                                        return;
                                    }
                                }
                                if (BaseService.LIGHTMIN.equals(str)) {
                                    if (DeviceUtils.isLightFromMCU()) {
                                        setSystemBrightnessFromMcu(0);
                                        return;
                                    } else {
                                        setSystemBrightness(0);
                                        return;
                                    }
                                }
                                if (BaseService.LIGHTVALUE.equals(str)) {
                                    if (!DeviceUtils.isLightFromMCU()) {
                                        setSystemBrightness((Integer.parseInt(strArr[0]) * 255) / 100);
                                        return;
                                    }
                                    i3 = Integer.parseInt(strArr[0]);
                                } else {
                                    if (BaseService.SETWIFI.equals(str)) {
                                        ((WifiManager) getSystemService("wifi")).setWifiEnabled("true".equals(strArr[0]));
                                        return;
                                    }
                                    if (BaseService.GETREVERSESTATUS.equals(str)) {
                                        sendReverseStatus();
                                        return;
                                    }
                                    if (BaseService.GETSLEEPSTATUS.equals(str)) {
                                        return;
                                    }
                                    if (BaseService.CLOSESCREEN.equals(str)) {
                                        mTW.write(33281, 1, 110);
                                        return;
                                    }
                                    if (BaseService.OPENSCREEN.equals(str)) {
                                        mTW.write(33281, 1, 108);
                                        return;
                                    }
                                    if (!BaseService.SETVOLUMEMUTE.equals(str)) {
                                        if (BaseService.MEDIANEXT.equals(str)) {
                                            Log.d("CommandService", "CommandService-->sendSystemFunction-->MEDIANEXT-->mSource?" + this.mSource);
                                            int i8 = this.mSource;
                                            i2 = 19;
                                            if (i8 == 1) {
                                                if (this.iTWRadioCallbackAidlLis == null) {
                                                    twtw2 = mTW;
                                                    twtw2.write(513, 1, i2);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast = this.iTWRadioCallbackAidlLis.beginBroadcast();
                                                    for (int i9 = 0; i9 < iBeginBroadcast; i9++) {
                                                        ((IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i9)).radioNext();
                                                    }
                                                } catch (Exception e) {
                                                    Log.d("CommandService", "Radio?" + e.toString());
                                                }
                                                remoteCallbackList = this.iTWRadioCallbackAidlLis;
                                                remoteCallbackList.finishBroadcast();
                                                return;
                                            }
                                            if (i8 == 3) {
                                                if (this.iTWMusicCallbackAidlLis == null) {
                                                    twtw2 = mTW;
                                                    twtw2.write(513, 1, i2);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast2 = this.iTWMusicCallbackAidlLis.beginBroadcast();
                                                    for (int i10 = 0; i10 < iBeginBroadcast2; i10++) {
                                                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i10)).musicNext();
                                                    }
                                                } catch (Exception e2) {
                                                    Log.d("CommandService", "Music?" + e2.toString());
                                                }
                                                remoteCallbackList = this.iTWMusicCallbackAidlLis;
                                                remoteCallbackList.finishBroadcast();
                                                return;
                                            }
                                            if (i8 == 8) {
                                                if (this.iTWBTCallbackAidlLis == null) {
                                                    twtw2 = mTW;
                                                    twtw2.write(513, 1, i2);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast3 = this.iTWBTCallbackAidlLis.beginBroadcast();
                                                    for (int i11 = 0; i11 < iBeginBroadcast3; i11++) {
                                                        ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i11)).btNext();
                                                    }
                                                } catch (Exception e3) {
                                                    Log.d("CommandService", "BT?" + e3.toString());
                                                }
                                                remoteCallbackList = this.iTWBTCallbackAidlLis;
                                                remoteCallbackList.finishBroadcast();
                                                return;
                                            }
                                            if (i8 != 9) {
                                                sendKeyCode(87);
                                                return;
                                            }
                                            if (this.iTWVideoCallbackAidlLis == null) {
                                                twtw2 = mTW;
                                                twtw2.write(513, 1, i2);
                                                return;
                                            }
                                            try {
                                                int iBeginBroadcast4 = this.iTWVideoCallbackAidlLis.beginBroadcast();
                                                for (int i12 = 0; i12 < iBeginBroadcast4; i12++) {
                                                    ((IVideoCallBack) this.iTWVideoCallbackAidlLis.getBroadcastItem(i12)).videoNext();
                                                }
                                            } catch (Exception e4) {
                                                Log.d("CommandService", "Video?" + e4.toString());
                                            }
                                            remoteCallbackList = this.iTWVideoCallbackAidlLis;
                                            remoteCallbackList.finishBroadcast();
                                            return;
                                        }
                                        if (BaseService.MEDIAPRE.equals(str)) {
                                            int i13 = this.mSource;
                                            i2 = 21;
                                            if (i13 == 1) {
                                                if (this.iTWRadioCallbackAidlLis == null) {
                                                    twtw2 = mTW;
                                                    twtw2.write(513, 1, i2);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast5 = this.iTWRadioCallbackAidlLis.beginBroadcast();
                                                    for (int i14 = 0; i14 < iBeginBroadcast5; i14++) {
                                                        ((IRadioCallBack) this.iTWRadioCallbackAidlLis.getBroadcastItem(i14)).radioPre();
                                                    }
                                                } catch (Exception e5) {
                                                    Log.d("CommandService", "Radio?" + e5.toString());
                                                }
                                                remoteCallbackList = this.iTWRadioCallbackAidlLis;
                                            } else if (i13 == 3) {
                                                if (this.iTWMusicCallbackAidlLis == null) {
                                                    twtw2 = mTW;
                                                    twtw2.write(513, 1, i2);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast6 = this.iTWMusicCallbackAidlLis.beginBroadcast();
                                                    for (int i15 = 0; i15 < iBeginBroadcast6; i15++) {
                                                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i15)).musicPre();
                                                    }
                                                } catch (Exception e6) {
                                                    Log.d("CommandService", "Music?" + e6.toString());
                                                }
                                                remoteCallbackList = this.iTWMusicCallbackAidlLis;
                                            } else if (i13 != 8) {
                                                if (i13 != 9) {
                                                    sendKeyCode(88);
                                                    return;
                                                }
                                                if (this.iTWVideoCallbackAidlLis == null) {
                                                    twtw2 = mTW;
                                                    twtw2.write(513, 1, i2);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast7 = this.iTWVideoCallbackAidlLis.beginBroadcast();
                                                    for (int i16 = 0; i16 < iBeginBroadcast7; i16++) {
                                                        ((IVideoCallBack) this.iTWVideoCallbackAidlLis.getBroadcastItem(i16)).videoPre();
                                                    }
                                                } catch (Exception e7) {
                                                    Log.d("CommandService", "Video?" + e7.toString());
                                                }
                                                remoteCallbackList = this.iTWVideoCallbackAidlLis;
                                            } else {
                                                if (this.iTWBTCallbackAidlLis == null) {
                                                    twtw2 = mTW;
                                                    twtw2.write(513, 1, i2);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast8 = this.iTWBTCallbackAidlLis.beginBroadcast();
                                                    for (int i17 = 0; i17 < iBeginBroadcast8; i17++) {
                                                        ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i17)).btPre();
                                                    }
                                                } catch (Exception e8) {
                                                    Log.d("CommandService", "BT?" + e8.toString());
                                                }
                                                remoteCallbackList = this.iTWBTCallbackAidlLis;
                                            }
                                        } else if (BaseService.MEDIAPLAY.equals(str)) {
                                            int i18 = this.mSource;
                                            if (i18 == 1) {
                                                return;
                                            }
                                            if (i18 == 3) {
                                                if (this.iTWMusicCallbackAidlLis == null) {
                                                    twtw = mTW;
                                                    twtw.write(513, 1, 20);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast9 = this.iTWMusicCallbackAidlLis.beginBroadcast();
                                                    for (int i19 = 0; i19 < iBeginBroadcast9; i19++) {
                                                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i19)).musicPlay();
                                                    }
                                                } catch (Exception e9) {
                                                    Log.d("CommandService", "Music?" + e9.toString());
                                                }
                                                remoteCallbackList = this.iTWMusicCallbackAidlLis;
                                            } else if (i18 != 8) {
                                                if (i18 != 9) {
                                                    sendKeyCode(85);
                                                    return;
                                                }
                                                if (this.iTWVideoCallbackAidlLis == null) {
                                                    twtw = mTW;
                                                    twtw.write(513, 1, 20);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast10 = this.iTWVideoCallbackAidlLis.beginBroadcast();
                                                    for (int i20 = 0; i20 < iBeginBroadcast10; i20++) {
                                                        ((IVideoCallBack) this.iTWVideoCallbackAidlLis.getBroadcastItem(i20)).videoPlay();
                                                    }
                                                } catch (Exception e10) {
                                                    Log.d("CommandService", "Video?" + e10.toString());
                                                }
                                                remoteCallbackList = this.iTWVideoCallbackAidlLis;
                                            } else {
                                                if (this.iTWBTCallbackAidlLis == null) {
                                                    twtw = mTW;
                                                    twtw.write(513, 1, 20);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast11 = this.iTWBTCallbackAidlLis.beginBroadcast();
                                                    for (int i21 = 0; i21 < iBeginBroadcast11; i21++) {
                                                        if (!this.mBtPlayState) {
                                                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i21)).btPlayMusic();
                                                        }
                                                    }
                                                } catch (Exception e11) {
                                                    Log.d("CommandService", "BT?" + e11.toString());
                                                }
                                                remoteCallbackList = this.iTWBTCallbackAidlLis;
                                            }
                                        } else {
                                            if (!BaseService.MEDIAPAUSE.equals(str) || (i = this.mSource) == 1) {
                                                return;
                                            }
                                            if (i == 3) {
                                                if (this.iTWMusicCallbackAidlLis == null) {
                                                    twtw = mTW;
                                                    twtw.write(513, 1, 20);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast12 = this.iTWMusicCallbackAidlLis.beginBroadcast();
                                                    for (int i22 = 0; i22 < iBeginBroadcast12; i22++) {
                                                        ((IMusicCallBack) this.iTWMusicCallbackAidlLis.getBroadcastItem(i22)).musicPause();
                                                    }
                                                } catch (Exception e12) {
                                                    Log.d("CommandService", "Music?" + e12.toString());
                                                }
                                                remoteCallbackList = this.iTWMusicCallbackAidlLis;
                                            } else if (i != 8) {
                                                if (i != 9) {
                                                    sendKeyCode(127);
                                                    return;
                                                }
                                                if (this.iTWVideoCallbackAidlLis == null) {
                                                    twtw = mTW;
                                                    twtw.write(513, 1, 20);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast13 = this.iTWVideoCallbackAidlLis.beginBroadcast();
                                                    for (int i23 = 0; i23 < iBeginBroadcast13; i23++) {
                                                        ((IVideoCallBack) this.iTWVideoCallbackAidlLis.getBroadcastItem(i23)).videoPause();
                                                    }
                                                } catch (Exception e13) {
                                                    Log.d("CommandService", "Video?" + e13.toString());
                                                }
                                                remoteCallbackList = this.iTWVideoCallbackAidlLis;
                                            } else {
                                                if (this.iTWBTCallbackAidlLis == null) {
                                                    twtw = mTW;
                                                    twtw.write(513, 1, 20);
                                                    return;
                                                }
                                                try {
                                                    int iBeginBroadcast14 = this.iTWBTCallbackAidlLis.beginBroadcast();
                                                    for (int i24 = 0; i24 < iBeginBroadcast14; i24++) {
                                                        if (this.mBtPlayState) {
                                                            ((IBTCallBack) this.iTWBTCallbackAidlLis.getBroadcastItem(i24)).btPlayMusic();
                                                        }
                                                    }
                                                } catch (Exception e14) {
                                                    Log.d("CommandService", "BT?" + e14.toString());
                                                }
                                                remoteCallbackList = this.iTWBTCallbackAidlLis;
                                            }
                                        }
                                        remoteCallbackList.finishBroadcast();
                                        return;
                                    }
                                    twtw3 = mTW;
                                    if (!"true".equals(strArr[0])) {
                                        i5 = 0;
                                    }
                                }
                            }
                            setSystemBrightnessFromMcu(i3);
                            return;
                        }
                        this.requestData = true;
                        twtw4 = mTW;
                    }
                    twtw3.write(515, 0, i5);
                    return;
                }
                this.setSystemVolumeUpOrDown = true;
                this.systemVolumeUpOrDown = 0;
                this.requestData = true;
                twtw4 = mTW;
            }
            twtw4.write(515, 255, 0);
        } catch (Exception e15) {
            Log.d("CommandService", "system?" + e15.toString());
        }
    }

    public boolean sendTWCommandCallBack(Message message) {
        int iBeginBroadcast = this.iTWCommandCallbackAidlLis.beginBroadcast();
        try {
            int i = message.what;
            if (i == 0) {
                Object obj = message.obj;
                for (int i2 = 0; i2 < iBeginBroadcast; i2++) {
                    ITWCommandCallbackAidl iTWCommandCallbackAidl = (ITWCommandCallbackAidl) this.iTWCommandCallbackAidlLis.getBroadcastItem(i2);
                    if (obj instanceof Bundle) {
                        iTWCommandCallbackAidl.onExtendedInterface((Bundle) obj);
                    }
                }
            } else if (i == 1) {
                for (int i3 = 0; i3 < iBeginBroadcast; i3++) {
                    ((ITWCommandCallbackAidl) this.iTWCommandCallbackAidlLis.getBroadcastItem(i3)).onBtPhoneStatus(message.arg1);
                }
            } else if (i == 2) {
                for (int i4 = 0; i4 < iBeginBroadcast; i4++) {
                    ITWCommandCallbackAidl iTWCommandCallbackAidl2 = (ITWCommandCallbackAidl) this.iTWCommandCallbackAidlLis.getBroadcastItem(i4);
                    String[] strArr = (String[]) message.obj;
                    iTWCommandCallbackAidl2.onBtCallStatus(Integer.parseInt(strArr[0]), strArr[1], strArr[2]);
                }
            } else if (i == 3) {
                for (int i5 = 0; i5 < iBeginBroadcast; i5++) {
                    ((ITWCommandCallbackAidl) this.iTWCommandCallbackAidlLis.getBroadcastItem(i5)).onBtConnectedStatus(message.arg1);
                }
            } else if (i == 515) {
                int i6 = (message.arg1 & Integer.MIN_VALUE) == Integer.MIN_VALUE ? 0 : 1;
                int i7 = message.arg1 & Integer.MAX_VALUE;
                if (this.setSystemVolumeUpOrDown) {
                    if (this.systemVolumeUpOrDown == 1) {
                        mTW.write(513, 1, 1);
                    } else {
                        mTW.write(513, 1, 2);
                    }
                    this.setSystemVolumeUpOrDown = false;
                    this.systemVolumeUpOrDown = -1;
                } else {
                    if (iBeginBroadcast < 0) {
                        this.iTWCommandCallbackAidlLis.finishBroadcast();
                        return false;
                    }
                    for (int i8 = 0; i8 < iBeginBroadcast; i8++) {
                        ITWCommandCallbackAidl iTWCommandCallbackAidl3 = (ITWCommandCallbackAidl) this.iTWCommandCallbackAidlLis.getBroadcastItem(i8);
                        iTWCommandCallbackAidl3.onVolumeStatus(i6);
                        iTWCommandCallbackAidl3.onSystemVolume(i7);
                    }
                }
            } else if (i == 40732) {
                int i9 = this.mReverse;
                int i10 = message.arg1;
                if (i9 != i10) {
                    this.mReverse = i10;
                    for (int i11 = 0; i11 < iBeginBroadcast; i11++) {
                        ((ITWCommandCallbackAidl) this.iTWCommandCallbackAidlLis.getBroadcastItem(i11)).onReverseStatus(this.mReverse);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.iTWCommandCallbackAidlLis.finishBroadcast();
        return false;
    }

    @Override // com.tw.service.xt.BaseService
    public void sendVideoFunction(String str, String... strArr) {
        Log.e("CommandService", "SpeechCommandService->sendVideoFunction:" + str);
        RemoteCallbackList remoteCallbackList = this.iTWVideoCallbackAidlLis;
        if (remoteCallbackList != null) {
            try {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < iBeginBroadcast; i++) {
                    if (BaseService.VIDEONEXT.equals(str)) {
                        ((IVideoCallBack) this.iTWVideoCallbackAidlLis.getBroadcastItem(i)).videoNext();
                    } else if (BaseService.VIDEOPRE.equals(str)) {
                        ((IVideoCallBack) this.iTWVideoCallbackAidlLis.getBroadcastItem(i)).videoPre();
                    } else if (BaseService.VIDEOPLAY.equals(str)) {
                        ((IVideoCallBack) this.iTWVideoCallbackAidlLis.getBroadcastItem(i)).videoPlay();
                    } else if (BaseService.VIDEOPAUSE.equals(str)) {
                        ((IVideoCallBack) this.iTWVideoCallbackAidlLis.getBroadcastItem(i)).videoPause();
                    }
                }
            } catch (Exception e) {
                Log.d("CommandService", "Video?" + e.toString());
            }
            this.iTWVideoCallbackAidlLis.finishBroadcast();
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void unRegisterBTCallBack(IBTCallBack iBTCallBack) {
        RemoteCallbackList remoteCallbackList = this.iTWBTCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iBTCallBack);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void unRegisterMusicCallBack(IMusicCallBack iMusicCallBack) {
        RemoteCallbackList remoteCallbackList = this.iTWMusicCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iMusicCallBack);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void unRegisterRadioCallBack(IRadioCallBack iRadioCallBack) {
        RemoteCallbackList remoteCallbackList = this.iTWRadioCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iRadioCallBack);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void unRegisterTWCommandCallback(ITWCommandCallbackAidl iTWCommandCallbackAidl) {
        RemoteCallbackList remoteCallbackList = this.iTWCommandCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iTWCommandCallbackAidl);
        }
    }

    @Override // com.tw.service.xt.BaseService
    public void unRegisterVideoCallBack(IVideoCallBack iVideoCallBack) {
        RemoteCallbackList remoteCallbackList = this.iTWVideoCallbackAidlLis;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iVideoCallBack);
        }
    }
}
