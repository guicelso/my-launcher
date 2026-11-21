package com.sprd.engineermode.hardware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import com.sprd.engineermode.utils.EMFileUtils;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.IHardwareApi;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/* JADX INFO: loaded from: classes4.dex */
public class CoulometerPowerReceiver extends BroadcastReceiver {
    private static final String CC_TEST_CMD_PATH = "/sys/class/power_supply/sprdfgu/";
    private static final String CC_TEST_RESULT_PATH = "/sys/class/power_supply/sprdfgu/cc_test_result";
    private static final String TAG = "CoulometerPowerReceiver";
    private static final int UPDATE_UI_END = 1;
    private String dur;
    private SharedPreferences.Editor editor;
    private float mAveEnergy;
    private float mAveVoltage;
    private Context mContext;
    private int period;
    private SharedPreferences preferences;
    private String time;
    private Timer[] timer = new Timer[3];
    private Handler mHandler = new Handler();
    private PowerManager.WakeLock wakeLock = null;
    private IHardwareApi hwApi = CoreApi.getHardwareApi();
    private boolean isCCTestCmdExist = true;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        this.dur = intent.getStringExtra("duration");
        this.time = intent.getStringExtra("time");
        Log.d(TAG, "CoulometerPowerReceiver dur: " + this.dur + " time: " + this.time);
        this.isCCTestCmdExist = EMFileUtils.isFileDirExits(CC_TEST_CMD_PATH);
        StringBuilder sb = new StringBuilder();
        sb.append("isFileDirExits /sys/class/power_supply/sprdfgu: ");
        sb.append(this.isCCTestCmdExist);
        Log.d(TAG, sb.toString());
        CoulometerPowerTimer(this.time);
        Intent service = new Intent(context, (Class<?>) CoulometerPowerService.class);
        service.putExtra("duration", this.dur);
        service.putExtra("time", this.time);
        this.mContext.startService(service);
    }

    private void CoulometerPowerTimer(String timePeriod) {
        int index = 0;
        try {
            if (timePeriod.equals("time1")) {
                index = 0;
            } else if (timePeriod.equals("time2")) {
                index = 1;
            } else if (timePeriod.equals("time3")) {
                index = 2;
            }
            SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm");
            if (this.isCCTestCmdExist) {
                this.hwApi.coulometerPowerApi().writeZeroToCCTestCmd();
                CoulometerPowerTestActivity.mCCValue[index] = this.hwApi.coulometerPowerApi().getCcTestResult().split("\\s+")[0].replaceAll("AVG_CUR", "power");
                CoulometerPowerTestActivity.mCCValueVol[index] = "   Voltage:" + this.hwApi.coulometerPowerApi().getCcTestVoltages() + "mV";
            } else {
                String mCurEnergy = this.hwApi.coulometerPowerApi().getCcResultNewKernel();
                Log.d(TAG, "mCurEnergy: " + mCurEnergy + " startEnergy: " + CoulometerPowerTestActivity.mCCValue[index] + " durTime: " + this.dur);
                this.mAveEnergy = (((Float.valueOf(mCurEnergy).floatValue() - Float.valueOf(CoulometerPowerTestActivity.mCCValue[index]).floatValue()) * 72.0f) / 10.0f) / ((float) ((Integer.valueOf(this.dur).intValue() * 60) * 2));
                CoulometerPowerTestActivity.mCCValue[index] = "    Time: " + this.dur + "Min\n   Energy: " + String.valueOf(this.mAveEnergy).replaceAll("-", "") + "mA";
                String mVol = this.hwApi.coulometerPowerApi().getCcVoltNewKernel().replaceAll("\r|\n", "");
                this.mAveVoltage = Float.valueOf(mVol).floatValue() / 1000.0f;
                CoulometerPowerTestActivity.mCCValueVol[index] = "   Voltage: " + String.valueOf(this.mAveVoltage) + "mV";
            }
            Log.d(TAG, "hwApi CoulometerPowerTimer mCCValue: " + CoulometerPowerTestActivity.mCCValue[index] + " mCCValueVol: " + CoulometerPowerTestActivity.mCCValueVol[index]);
            if (timePeriod.equals("time1")) {
                if (this.isCCTestCmdExist) {
                    CoulometerPowerTestActivity.testResults1.add(getCCResult(CoulometerPowerTestActivity.mCCValue[index]));
                } else {
                    CoulometerPowerTestActivity.testResults1.add(String.valueOf(this.mAveEnergy));
                }
                CoulometerPowerTestActivity.testTimes1.add(sDateFormat.format(new Date()));
            } else if (timePeriod.equals("time2")) {
                if (this.isCCTestCmdExist) {
                    CoulometerPowerTestActivity.testResults2.add(getCCResult(CoulometerPowerTestActivity.mCCValue[index]));
                } else {
                    CoulometerPowerTestActivity.testResults2.add(String.valueOf(this.mAveEnergy));
                }
                CoulometerPowerTestActivity.testTimes2.add(sDateFormat.format(new Date()));
            } else if (timePeriod.equals("time3")) {
                if (this.isCCTestCmdExist) {
                    CoulometerPowerTestActivity.testResults3.add(getCCResult(CoulometerPowerTestActivity.mCCValue[index]));
                } else {
                    CoulometerPowerTestActivity.testResults3.add(String.valueOf(this.mAveEnergy));
                }
                CoulometerPowerTestActivity.testTimes3.add(sDateFormat.format(new Date()));
            } else {
                if (this.isCCTestCmdExist) {
                    CoulometerPowerTestActivity.testResults1.add(getCCResult(CoulometerPowerTestActivity.mCCValue[index]));
                } else {
                    CoulometerPowerTestActivity.testResults1.add(String.valueOf(this.mAveEnergy));
                }
                CoulometerPowerTestActivity.testTimes1.add(sDateFormat.format(new Date()));
            }
            if (this.isCCTestCmdExist) {
                this.hwApi.coulometerPowerApi().writeOneToCCTestCmd();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCCResult(String value) {
        String[] strings = value.split(":");
        if (strings.length > 1) {
            String str = strings[strings.length - 1].split("m")[0];
            Log.d(TAG, "getCCResult: " + str);
            return str;
        }
        return "";
    }
}
