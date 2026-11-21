package com.tw.qcarcamlib;

import android.os.Process;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import c.a.a.a.a;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class QCarCamManager {
    private static final String TAG = "QCarCamManager";
    private static volatile QCarCamManager qCarCamManager;
    private CAMERA_CHANNEL cameraChannel;
    private Map<String, OnCameraStateListener> cameraStateListenerMap = new ConcurrentHashMap();
    private QcarcamJNI qcarcamJNI;

    public enum CAMERA_CHANNEL {
        CHANNEL_REVERSE,
        CHANNEL_TV,
        CHANNEL_AUX
    }

    public interface OnCameraStateListener {
        void onCameraState(int i, int i2);
    }

    public QCarCamManager() {
        initCameraJNI();
    }

    public static QCarCamManager getInstance() {
        if (qCarCamManager == null) {
            synchronized (QCarCamManager.class) {
                if (qCarCamManager == null) {
                    qCarCamManager = new QCarCamManager();
                }
            }
        }
        return qCarCamManager;
    }

    private void initCameraJNI() {
        QcarcamJNI qcarcamJNI = new QcarcamJNI();
        this.qcarcamJNI = qcarcamJNI;
        qcarcamJNI.setOnCallback(new QcarcamJNI.OnCallback() { // from class: com.tw.qcarcamlib.QCarCamManager.1
            @Override // com.tw.qcarcamlib.QcarcamJNI.OnCallback
            public void onState(int i, int i2) {
                Log.d(QCarCamManager.TAG, "OnCallback：onState: id：" + i + " state:" + i2);
                if (i2 == 4) {
                    QCarCamManager.this.setCamParam();
                }
                Iterator it = QCarCamManager.this.cameraStateListenerMap.entrySet().iterator();
                while (it.hasNext()) {
                    ((OnCameraStateListener) ((Map.Entry) it.next()).getValue()).onCameraState(i, i2);
                }
            }
        });
        this.qcarcamJNI.native_init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:15:0x0074  */
    /* JADX WARN: Code duplicated, block: B:17:0x0079  */
    /* JADX WARN: Code duplicated, block: B:19:0x007e  */
    public void setCamParam() {
        int i;
        int i2;
        int i3;
        String str;
        Log.d(TAG, "setCamParam: START");
        CAMERA_CHANNEL camera_channel = this.cameraChannel;
        if (camera_channel == CAMERA_CHANNEL.CHANNEL_REVERSE) {
            i = SystemProperties.getInt(CamParamConstant.REVERSE.PERSIST_CAM_BRIGHTNESS, 128);
            i2 = SystemProperties.getInt(CamParamConstant.REVERSE.PERSIST_CAM_CONTRAST, 128);
            str = CamParamConstant.REVERSE.PERSIST_CAM_SATURATION;
        } else {
            if (camera_channel != CAMERA_CHANNEL.CHANNEL_TV) {
                if (camera_channel == CAMERA_CHANNEL.CHANNEL_AUX) {
                    i = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_BRIGHTNESS, 128);
                    i2 = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_CONTRAST, 128);
                    str = CamParamConstant.AUX.PERSIST_CAM_SATURATION;
                } else {
                    i = 128;
                    i2 = 128;
                    i3 = 128;
                }
                Log.d(TAG, "setCamParam: brightness:" + i + " contrast:" + i2 + " saturation:" + i3);
                if (i != 128) {
                    setBrightness(i);
                }
                if (i2 != 128) {
                    setContrast(i2);
                }
                if (i3 != 128) {
                    setSaturation(i3);
                }
                Log.d(TAG, "setCamParam: END");
            }
            i = SystemProperties.getInt(CamParamConstant.TV.PERSIST_CAM_BRIGHTNESS, 128);
            i2 = SystemProperties.getInt(CamParamConstant.TV.PERSIST_CAM_CONTRAST, 128);
            str = CamParamConstant.TV.PERSIST_CAM_SATURATION;
        }
        i3 = SystemProperties.getInt(str, 128);
        Log.d(TAG, "setCamParam: brightness:" + i + " contrast:" + i2 + " saturation:" + i3);
        if (i != 128) {
            setBrightness(i);
        }
        if (i2 != 128) {
            setContrast(i2);
        }
        if (i3 != 128) {
            setSaturation(i3);
        }
        Log.d(TAG, "setCamParam: END");
    }

    public void closeCamera() {
        Log.d(TAG, "closeCamera: ");
        SystemProperties.set("sys.camera.ch", "0");
        this.cameraChannel = null;
        this.qcarcamJNI.native_surfaceDestroyed();
        this.qcarcamJNI.native_camera_dump(0);
        this.qcarcamJNI.native_camera_close();
    }

    public void dump25() {
        this.qcarcamJNI.native_camera_dump(25);
    }

    public void dumpOne() {
        this.qcarcamJNI.native_camera_dump(1);
    }

    public void dumpZero() {
        this.qcarcamJNI.native_camera_dump(0);
    }

    public void exitReverse() {
        Log.d(TAG, "exitReverse: ");
        SystemProperties.set("sys.camera.ch", "0");
        this.cameraChannel = null;
        this.qcarcamJNI.native_surfaceDestroyed();
        this.qcarcamJNI.native_camera_dump(0);
        this.qcarcamJNI.native_camera_close();
    }

    public CAMERA_CHANNEL getCameraChannel() {
        return this.cameraChannel;
    }

    public void openCamera(CAMERA_CHANNEL camera_channel, List<SurfaceHolder> list) {
        String str;
        StringBuilder sbB = a.b("openCamera: myPid:");
        sbB.append(Process.myPid());
        Log.d(TAG, sbB.toString());
        this.cameraChannel = camera_channel;
        Log.d(TAG, "QCarCamManager: create cameraChannel:" + camera_channel);
        if (camera_channel == CAMERA_CHANNEL.CHANNEL_REVERSE) {
            str = "0";
        } else {
            if (camera_channel != CAMERA_CHANNEL.CHANNEL_TV) {
                if (camera_channel == CAMERA_CHANNEL.CHANNEL_AUX) {
                    str = "2";
                }
                this.qcarcamJNI.setSurfaceList(list);
                this.qcarcamJNI.native_camera_open();
            }
            str = "1";
        }
        SystemProperties.set("sys.camera.ch", str);
        this.qcarcamJNI.setSurfaceList(list);
        this.qcarcamJNI.native_camera_open();
    }

    public void registerCameraStateCallback(String str, OnCameraStateListener onCameraStateListener) {
        if (TextUtils.isEmpty(str)) {
            Log.w(TAG, "registerCameraStateCallback: error: TAG cannot be null or empty");
        } else {
            this.cameraStateListenerMap.put(str, onCameraStateListener);
        }
    }

    public void setBrightness(int i) {
        this.qcarcamJNI.native_set_camera_brightness(i);
    }

    public void setContrast(int i) {
        this.qcarcamJNI.native_set_camera_contrast(i);
    }

    public void setSaturation(int i) {
        this.qcarcamJNI.native_set_camera_saturation(i);
    }

    public void unregisterCameraStateCallback(String str) {
        this.cameraStateListenerMap.remove(str);
    }
}
