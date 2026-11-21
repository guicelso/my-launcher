package com.sprd.engineermode.debuglog;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SensorsIDActivity extends Activity {
    private static final String CAMERA_SENSOR = "/sys/devices/virtual/misc/sprd_sensor/camera_sensor_name";
    private static final int GET_CAMERA_ID = 0;
    private static final String GSENSOR = "sys/module/sprd_phinfo/parameters/SPRD_GsensorInfo";
    private static final String LCD_ID_NEW = "/sys/class/display/panel0/name";
    private static final String LCD_ID_OLD = "/sys/devices/platform/soc/soc:ap-ahb/20800000.dispc/lcd_name";
    private static final String LSENSOR = "sys/module/sprd_phinfo/parameters/SPRD_LsensorInfo";
    private static final String SENSOR_VERSION = "/sys/class/sprd_sensorhub/sensor_hub/sensor_info";
    private static final String TAG = "SensorsIDActivity";
    private static final String TP_CHIP_ID = "/sys/touchscreen/chip_id";
    private static final String TP_FIRMWARE_VERSION = "/sys/touchscreen/firmware_version";
    private TextView mCameraID;
    private boolean mIsCameraOpen;
    private TextView mLcdID;
    private TextView mSensorVersion;
    private TextView mTpVersion;
    private int mCameraId = 0;
    private Camera mCamera = null;
    private final int SIM0 = 0;
    private Handler mHandler = new Handler() { // from class: com.sprd.engineermode.debuglog.SensorsIDActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) throws IllegalAccessException, IOException, InvocationTargetException {
            if (msg.what == 0) {
                String cameraId = SensorsIDActivity.this.getCameraID();
                Log.e(SensorsIDActivity.TAG, "getCameraID:" + cameraId);
                if (cameraId != null) {
                    cameraId = cameraId.replaceAll("\n", "");
                }
                if (!TextUtils.isEmpty(cameraId)) {
                    SensorsIDActivity.this.mCameraID.setText(cameraId);
                    SystemPropertiesProxy.set("persist.vendor.cam.sensor.id", "");
                } else {
                    SystemPropertiesProxy.set("persist.vendor.cam.sensor.id", "trigger_srid");
                    if (!SensorsIDActivity.this.mIsCameraOpen) {
                        SensorsIDActivity.this.startCamera();
                    }
                    SensorsIDActivity.this.mHandler.sendEmptyMessageDelayed(0, 200L);
                }
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_id);
        this.mCameraID = (TextView) findViewById(R.id.camera_id);
        this.mLcdID = (TextView) findViewById(R.id.lcd_id);
        this.mTpVersion = (TextView) findViewById(R.id.tp_version);
        this.mHandler.sendEmptyMessage(0);
        this.mLcdID.setText(getLcdID());
        this.mTpVersion.setText(getTpID() + getTpVersion());
        this.mSensorVersion = (TextView) findViewById(R.id.sensor_version);
        new Thread(new Runnable() { // from class: com.sprd.engineermode.debuglog.-$$Lambda$SensorsIDActivity$wqDbFPwuWrWy3s1cU8tQn15J8jU
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onCreate$0$SensorsIDActivity();
            }
        }).start();
    }

    public /* synthetic */ void lambda$onCreate$0$SensorsIDActivity() {
        this.mSensorVersion.setText(getSensorID());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCamera() {
        Log.e(TAG, "startCamera");
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.release();
        }
        try {
            this.mCamera = Camera.open(this.mCameraId);
            this.mIsCameraOpen = true;
        } catch (RuntimeException e) {
            Log.e(TAG, "fail to open camera", e);
            e.printStackTrace();
            this.mCamera = null;
            this.mIsCameraOpen = false;
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        try {
            if (this.mCamera != null) {
                this.mCamera.release();
                this.mCamera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCameraID() throws IOException {
        String result = getFileValue(CAMERA_SENSOR);
        if (TextUtils.isEmpty(result)) {
            return SystemPropertiesProxy.get("vendor.cam.sensor.info");
        }
        return result;
    }

    private String getLsensorID() {
        return getFileValue(LSENSOR);
    }

    private String getGsensorID() {
        return getFileValue(GSENSOR);
    }

    private String getTpID() {
        return getFileValue(TP_CHIP_ID);
    }

    private String getTpVersion() {
        return getFileValue("/sys/touchscreen/firmware_version");
    }

    private String getLcdID() {
        String lcdId = LCD_ID_OLD;
        if (!isLCDIDExist(LCD_ID_OLD)) {
            lcdId = LCD_ID_NEW;
        }
        return getFileValue(lcdId);
    }

    private String getSensorID() throws IOException {
        if (new File(SENSOR_VERSION).isFile()) {
            String result = getFileValue(SENSOR_VERSION);
            return result;
        }
        HashMap<Integer, String> sensorTypeNameMap = new HashMap<Integer, String>() { // from class: com.sprd.engineermode.debuglog.SensorsIDActivity.2
            private static final long serialVersionUID = -4283877512932070968L;

            {
                put(1, "acc name: ");
                put(2, "mag name: ");
                put(4, "gyro name: ");
                put(5, "light name: ");
                put(6, "pressure name: ");
                put(7, "temperature name: ");
                put(8, "prox name: ");
                put(12, "hum name: ");
                put(13, "am-temperature name: ");
                put(21, "heart name: ");
            }
        };
        StringBuilder strBuilder = new StringBuilder();
        try {
            SensorManager sensorManager = (SensorManager) getSystemService("sensor");
            List<Sensor> sensorList = sensorManager.getSensorList(-1);
            for (Sensor sensor : sensorList) {
                String sensorName = sensorTypeNameMap.get(Integer.valueOf(sensor.getType()));
                if (sensorName != null) {
                    strBuilder.append(sensorName);
                    strBuilder.append(sensor.getName() != null ? sensor.getName().replaceAll(" sensor", "") : "");
                    strBuilder.append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }
        String result2 = strBuilder.toString();
        return result2;
    }

    private String getFileValue(String path) throws IOException {
        BufferedReader bReader = null;
        StringBuffer sBuffer = new StringBuffer();
        try {
            try {
                try {
                    FileInputStream fi = new FileInputStream(path);
                    bReader = new BufferedReader(new InputStreamReader(fi));
                    String str = bReader.readLine();
                    while (str != null) {
                        if (path.equals(SENSOR_VERSION)) {
                            if (str.startsWith("1")) {
                                str = str.replaceFirst("1", "");
                            }
                            if (!str.startsWith("0")) {
                                sBuffer.append(str + "\n");
                            }
                        } else {
                            sBuffer.append(str + "\n");
                        }
                        str = bReader.readLine();
                    }
                    bReader.close();
                } catch (Throwable th) {
                    if (bReader != null) {
                        try {
                            bReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                if (bReader != null) {
                    bReader.close();
                }
                Log.d(TAG, "getFileValue sBuffer.toString(): " + sBuffer.toString());
                return sBuffer.toString();
            } catch (IOException e3) {
                e3.printStackTrace();
                if (bReader != null) {
                    bReader.close();
                }
                Log.d(TAG, "getFileValue sBuffer.toString(): " + sBuffer.toString());
                return sBuffer.toString();
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        Log.d(TAG, "getFileValue sBuffer.toString(): " + sBuffer.toString());
        return sBuffer.toString();
    }

    public boolean isLCDIDExist(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }
}
