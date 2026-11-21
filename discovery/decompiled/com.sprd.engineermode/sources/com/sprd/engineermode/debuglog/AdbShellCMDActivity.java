package com.sprd.engineermode.debuglog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.sprd.engineermode.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes4.dex */
public class AdbShellCMDActivity extends Activity implements View.OnClickListener {
    private static final String IPERF_BIN = "/data/data/com.sprd.engineermode/bin/iperf";
    private static final String IPERF_PATH = "/data/data/com.sprd.engineermode";
    private static final String TAG = "AdbShellCMDActivity";
    private static final int UPDATE_CMD1 = 1;
    private static final int UPDATE_CMD2 = 2;
    private EditText mAdbShellEditText1;
    private EditText mAdbShellEditText2;
    private Button mClearBtn1;
    private Button mClearBtn2;
    private Button mOnOffBtn1;
    private Button mOnOffBtn2;
    private TextView mTextResult1;
    private TextView mTextResult2;
    private PowerManager.WakeLock mWakeLock;
    private boolean paused1 = true;
    private boolean paused2 = true;
    Message message1 = null;
    Message message2 = null;
    private String CMD = null;
    private String[] CMD1 = {"ls", "-l"};
    private String mAdbShellCMD1 = null;
    private String mAdbShellCMD2 = null;
    private Process proc1 = null;
    private Process proc2 = null;
    private String mResult1 = "";
    private String mResult2 = "";
    private Handler handler = new Handler() { // from class: com.sprd.engineermode.debuglog.AdbShellCMDActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i != 1) {
                if (i == 2 && !AdbShellCMDActivity.this.paused2) {
                    String result = (String) msg.obj;
                    if (result == null || result.equals("null")) {
                        AdbShellCMDActivity.this.mTextResult2.setText(AdbShellCMDActivity.this.getString(R.string.result_empty));
                        return;
                    } else {
                        AdbShellCMDActivity.this.mTextResult2.setText(result);
                        return;
                    }
                }
                return;
            }
            if (!AdbShellCMDActivity.this.paused1) {
                String result2 = (String) msg.obj;
                if (result2 == null || result2.equals("null")) {
                    AdbShellCMDActivity.this.mTextResult1.setText(AdbShellCMDActivity.this.getString(R.string.result_empty));
                } else {
                    AdbShellCMDActivity.this.mTextResult1.setText(result2);
                }
            }
        }
    };
    private Runnable runnable1 = new Runnable() { // from class: com.sprd.engineermode.debuglog.AdbShellCMDActivity.2
        @Override // java.lang.Runnable
        public void run() {
            if (!AdbShellCMDActivity.this.paused1) {
                AdbShellCMDActivity adbShellCMDActivity = AdbShellCMDActivity.this;
                adbShellCMDActivity.runCMD(adbShellCMDActivity.GenerateCMD(adbShellCMDActivity.mAdbShellCMD1), 1);
            }
        }
    };
    private Runnable runnable2 = new Runnable() { // from class: com.sprd.engineermode.debuglog.AdbShellCMDActivity.3
        @Override // java.lang.Runnable
        public void run() {
            if (!AdbShellCMDActivity.this.paused2) {
                AdbShellCMDActivity adbShellCMDActivity = AdbShellCMDActivity.this;
                adbShellCMDActivity.runCMD(adbShellCMDActivity.GenerateCMD(adbShellCMDActivity.mAdbShellCMD2), 2);
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adb_shell);
        this.mAdbShellEditText1 = (EditText) findViewById(R.id.adb_shell_cmd1);
        this.mAdbShellEditText2 = (EditText) findViewById(R.id.adb_shell_cmd2);
        this.mOnOffBtn1 = (Button) findViewById(R.id.start_btn1);
        this.mOnOffBtn1.setOnClickListener(this);
        this.mClearBtn1 = (Button) findViewById(R.id.clear_btn1);
        this.mClearBtn1.setOnClickListener(this);
        this.mOnOffBtn2 = (Button) findViewById(R.id.start_btn2);
        this.mOnOffBtn2.setOnClickListener(this);
        this.mClearBtn2 = (Button) findViewById(R.id.clear_btn2);
        this.mClearBtn2.setOnClickListener(this);
        this.mTextResult1 = (TextView) findViewById(R.id.result_text1);
        this.mTextResult2 = (TextView) findViewById(R.id.result_text2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runCMD(String[] cmd, int index) {
        InputStream is;
        int exitVal;
        int i;
        AdbShellCMDActivity adbShellCMDActivity = this;
        int i2 = index;
        String str = TAG;
        try {
            PowerManager pm = (PowerManager) adbShellCMDActivity.getSystemService("power");
            int i3 = 1;
            if (pm != null) {
                adbShellCMDActivity.mWakeLock = pm.newWakeLock(1, "WIFI:TEST");
                adbShellCMDActivity.mWakeLock.acquire(600000L);
            }
            Runtime runtime = Runtime.getRuntime();
            if (i2 == 1) {
                adbShellCMDActivity.proc1 = runtime.exec(cmd);
                is = adbShellCMDActivity.proc1.getInputStream();
            } else {
                adbShellCMDActivity.proc2 = runtime.exec(cmd);
                is = adbShellCMDActivity.proc2.getInputStream();
            }
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String line = buf.readLine();
                Log.d(str, "index: " + i2 + " run ShellUtiles line: " + line);
                if (line == null || line.equals("adbdec")) {
                    break;
                    break;
                }
                if (i2 == i3) {
                    adbShellCMDActivity.mResult1 += line + "\n\n";
                    i = 1;
                    adbShellCMDActivity.message1 = adbShellCMDActivity.handler.obtainMessage(1, adbShellCMDActivity.mResult1);
                    adbShellCMDActivity.handler.sendMessageDelayed(adbShellCMDActivity.message1, 200L);
                } else {
                    i = i3;
                    adbShellCMDActivity.mResult2 += line + "\n\n";
                    adbShellCMDActivity.message2 = adbShellCMDActivity.handler.obtainMessage(2, adbShellCMDActivity.mResult2);
                    adbShellCMDActivity.handler.sendMessageDelayed(adbShellCMDActivity.message2, 200L);
                }
                adbShellCMDActivity = this;
                i2 = index;
                str = str;
                i3 = i;
            }
            if (i2 == i3) {
                exitVal = adbShellCMDActivity.proc1.waitFor();
            } else {
                exitVal = adbShellCMDActivity.proc2.waitFor();
            }
            Log.d(str, "run ShellUtiles line END exitVal: " + exitVal);
            if (is != null) {
                buf.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e2) {
            e2.printStackTrace();
        } catch (InterruptedException e3) {
            e3.printStackTrace();
        } catch (NullPointerException e4) {
            e4.printStackTrace();
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override // android.app.Activity
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override // android.app.Activity
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] GenerateCMD(String strCMD) {
        String[] strings = strCMD.replaceAll("\\\\", "/").split("\\s+");
        for (String str : strings) {
            Log.d(TAG, "strCMD:" + str);
        }
        return strings;
    }

    private boolean isFileExist(String childPath) {
        File filePath = new File(childPath);
        if (filePath.exists()) {
            return true;
        }
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_btn1 /* 2131296532 */:
                Log.d(TAG, "onClick clear_btn1");
                this.mTextResult1.setText("");
                this.mResult1 = "";
                break;
            case R.id.clear_btn2 /* 2131296533 */:
                Log.d(TAG, "onClick clear_btn2");
                this.mTextResult2.setText("");
                this.mResult2 = "";
                break;
            case R.id.start_btn1 /* 2131297449 */:
                if (this.paused1) {
                    this.mAdbShellCMD1 = this.mAdbShellEditText1.getText().toString();
                    Log.d(TAG, "mAdbShellCMD1: " + this.mAdbShellCMD1);
                    String str = this.mAdbShellCMD1;
                    if (str == null || str.equals("")) {
                        this.mAdbShellCMD1 = "getprop";
                    }
                    this.paused1 = false;
                    new Thread(this.runnable1).start();
                    this.mOnOffBtn1.setText(R.string.coulometer_finish);
                    this.mOnOffBtn1.setBackground(getResources().getDrawable(R.drawable.btn_pause));
                } else {
                    this.paused1 = true;
                    this.mOnOffBtn1.setText(R.string.coulometer_start);
                    this.mOnOffBtn1.setBackground(getResources().getDrawable(R.drawable.btn_refresh));
                    Process process = this.proc1;
                    if (process != null) {
                        process.destroy();
                        this.proc1 = null;
                    }
                }
                break;
            case R.id.start_btn2 /* 2131297450 */:
                if (this.paused2) {
                    this.mAdbShellCMD2 = this.mAdbShellEditText2.getText().toString();
                    Log.d(TAG, "mAdbShellCMD2: " + this.mAdbShellCMD2);
                    String str2 = this.mAdbShellCMD2;
                    if (str2 == null || str2.equals("")) {
                        this.mAdbShellCMD2 = "getprop";
                    }
                    this.paused2 = false;
                    new Thread(this.runnable2).start();
                    this.mOnOffBtn2.setText(R.string.coulometer_finish);
                    this.mOnOffBtn2.setBackground(getResources().getDrawable(R.drawable.btn_pause));
                } else {
                    this.paused2 = true;
                    this.mOnOffBtn2.setText(R.string.coulometer_start);
                    this.mOnOffBtn2.setBackground(getResources().getDrawable(R.drawable.btn_refresh));
                    Process process2 = this.proc2;
                    if (process2 != null) {
                        process2.destroy();
                        this.proc2 = null;
                    }
                }
                break;
        }
    }
}
