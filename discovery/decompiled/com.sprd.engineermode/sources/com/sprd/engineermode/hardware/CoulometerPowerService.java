package com.sprd.engineermode.hardware;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.sprd.engineermode.utils.EMFileUtils;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.IHardwareApi;

/* JADX INFO: loaded from: classes4.dex */
public class CoulometerPowerService extends Service {
    private static final String CC_TEST_CMD_PATH = "/sys/class/power_supply/sprdfgu/";
    public static final String TAG = "CoulometerPowerService";
    private static final int TEN_HOURS = 600;
    private static final int TEN_MINUTES = 10;
    private static final int TWINTY_MINUTES = 20;
    private AlarmManager alarmManager;
    private SharedPreferences.Editor editor;
    private IHardwareApi hwApi = CoreApi.getHardwareApi();
    private Intent mIntent;
    private PendingIntent pi1;
    private PendingIntent pi1_end;
    private PendingIntent pi2;
    private PendingIntent pi2_end;
    private PendingIntent pi3;
    private PendingIntent pi3_end;
    private SharedPreferences preferences;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "CoulometerPowerService onBind");
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.d(TAG, "CoulometerPowerService onCreate");
        this.alarmManager = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.mIntent = new Intent(this, (Class<?>) CoulometerPowerReceiver.class);
        this.preferences = getSharedPreferences("cc_status", 0);
        this.editor = this.preferences.edit();
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int startId) {
        long timeDuring;
        Log.d(TAG, "CoulometerPowerService onStart");
        try {
            if (EMFileUtils.isFileDirExits(CC_TEST_CMD_PATH)) {
                this.hwApi.coulometerPowerApi().writeOneToCCTestCmd();
            }
            String dur = intent.getStringExtra("duration");
            String time = intent.getStringExtra("time");
            this.mIntent.putExtra("duration", dur);
            this.mIntent.putExtra("time", time);
            int t = Integer.parseInt(dur);
            if (t > 20 && t <= TEN_HOURS) {
                timeDuring = SystemClock.elapsedRealtime() + 600000;
            } else if (t <= 20) {
                timeDuring = SystemClock.elapsedRealtime() + ((long) (t * 60 * 1000));
            } else {
                long timeDuring2 = SystemClock.elapsedRealtime();
                timeDuring = timeDuring2 + 3600000;
            }
            Log.d(TAG, "new >>> CoulometerPowerService dur: " + dur + " time: " + time + " timeDuring: " + timeDuring);
            if (time.equals("time1") && !CoulometerPowerTestActivity.timerStop[0]) {
                this.pi1 = PendingIntent.getBroadcast(this, 10, this.mIntent, 134217728);
                this.alarmManager.setExactAndAllowWhileIdle(2, timeDuring, this.pi1);
                return;
            }
            if (time.equals("time2") && !CoulometerPowerTestActivity.timerStop[1]) {
                this.pi2 = PendingIntent.getBroadcast(this, 11, this.mIntent, 134217728);
                this.alarmManager.setExactAndAllowWhileIdle(2, timeDuring, this.pi2);
                return;
            }
            if (time.equals("time3") && !CoulometerPowerTestActivity.timerStop[2]) {
                this.pi3 = PendingIntent.getBroadcast(this, 12, this.mIntent, 134217728);
                this.alarmManager.setExactAndAllowWhileIdle(2, timeDuring, this.pi3);
                return;
            }
            if (time.equals("time1_end")) {
                this.alarmManager.cancel(this.pi1);
                CoulometerPowerTestActivity.timerStop[0] = true;
                this.editor.putBoolean("time1_stop", true);
                this.editor.commit();
                return;
            }
            if (time.equals("time2_end")) {
                this.alarmManager.cancel(this.pi2);
                CoulometerPowerTestActivity.timerStop[1] = true;
                this.editor.putBoolean("time2_stop", true);
                this.editor.commit();
                return;
            }
            if (time.equals("time3_end")) {
                this.alarmManager.cancel(this.pi3);
                CoulometerPowerTestActivity.timerStop[2] = true;
                this.editor.putBoolean("time3_stop", true);
                this.editor.commit();
            }
        } catch (Exception ex) {
            Log.d(TAG, "onStart Exception: " + ex);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "CoulometerPowerService onDestroy");
    }
}
