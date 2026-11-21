package com.sprd.engineermode.debuglog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.sprd.engineermode.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes4.dex */
public class PingActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "PingActivity";
    private static final int UPDATE = 1;
    private EditText mAddressEditText;
    private Button mClearBtn;
    private EditText mCountEditText;
    private Button mOnOffBtn;
    private RadioGroup mPingAddress;
    private RadioButton mPingIPV4;
    private RadioButton mPingIPV6;
    private EditText mSizeEditText;
    private TextView mText;
    private boolean paused = true;
    Message message = null;
    private String CMD = null;
    private String mStrAddress = null;
    private String mStrCount = null;
    private String mStrSize = null;
    private boolean mIPV4 = true;
    private Process proc = null;
    private String mResult = "";
    private Handler handler = new Handler() { // from class: com.sprd.engineermode.debuglog.PingActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1 && !PingActivity.this.paused) {
                String result = (String) msg.obj;
                if (result == null || result.equals("null")) {
                    PingActivity.this.mText.setText(PingActivity.this.getString(R.string.result_empty));
                } else {
                    PingActivity.this.mText.setText(result);
                }
            }
        }
    };
    private Runnable runnable = new Runnable() { // from class: com.sprd.engineermode.debuglog.PingActivity.2
        @Override // java.lang.Runnable
        public void run() {
            if (!PingActivity.this.paused) {
                PingActivity pingActivity = PingActivity.this;
                pingActivity.runCMD(pingActivity.GenerateCMD(pingActivity.CMD));
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ping_package);
        this.mPingAddress = (RadioGroup) findViewById(R.id.radio_group);
        this.mPingIPV4 = (RadioButton) this.mPingAddress.findViewById(R.id.radio_btn_ipv4);
        this.mPingIPV4.setOnClickListener(this);
        this.mPingIPV6 = (RadioButton) this.mPingAddress.findViewById(R.id.radio_btn_ipv6);
        this.mPingIPV6.setOnClickListener(this);
        this.mAddressEditText = (EditText) findViewById(R.id.ping_addr);
        this.mSizeEditText = (EditText) findViewById(R.id.ping_size);
        this.mCountEditText = (EditText) findViewById(R.id.ping_count);
        this.mOnOffBtn = (Button) findViewById(R.id.start_btn);
        this.mOnOffBtn.setOnClickListener(this);
        this.mClearBtn = (Button) findViewById(R.id.clear_btn);
        this.mClearBtn.setOnClickListener(this);
        this.mText = (TextView) findViewById(R.id.result_text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String runCMD(String[] cmd) {
        try {
            this.proc = Runtime.getRuntime().exec(cmd);
            InputStream is = this.proc.getInputStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String line = buf.readLine();
                Log.d(TAG, "run ShellUtiles line: " + line);
                if (line == null) {
                    break;
                }
                this.mResult += line + "\n\n";
                this.message = this.handler.obtainMessage(1, this.mResult);
                this.handler.sendMessageDelayed(this.message, 200L);
            }
            Log.d(TAG, "run ShellUtiles line END");
            if (is != null) {
                buf.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
        return this.mResult;
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

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed");
        this.paused = true;
        Process process = this.proc;
        if (process != null) {
            process.destroy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] GenerateCMD(String strCMD) {
        String[] strings = strCMD.replaceAll("\\\\", "/").split("\\s+");
        for (String str : strings) {
            Log.d(TAG, "strCMD:" + str);
        }
        return strings;
    }

    private String GetPingCMD() {
        this.mStrAddress = this.mAddressEditText.getText().toString();
        this.mStrCount = this.mCountEditText.getText().toString();
        if (this.mIPV4) {
            this.CMD = "ping ";
        } else {
            this.CMD = "ping6 ";
        }
        String str = this.mStrCount;
        if (str == null || str.equals("")) {
            this.CMD += " -c 4 ";
        } else {
            this.CMD += " -c " + this.mStrCount + " ";
        }
        this.mStrSize = this.mSizeEditText.getText().toString();
        String str2 = this.mStrSize;
        if (str2 != null && !str2.equals("")) {
            this.CMD += " -s " + this.mStrSize + " ";
        }
        String str3 = this.mStrAddress;
        if (str3 == null || str3.equals("")) {
            this.mStrAddress = " www.baidu.com";
        }
        this.CMD += this.mStrAddress;
        Log.d(TAG, "onClick start_btn CMD: " + this.CMD);
        return this.CMD;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_btn /* 2131296531 */:
                Log.d(TAG, "onClick clear_btn");
                this.mText.setText("");
                this.mResult = "";
                break;
            case R.id.radio_btn_ipv4 /* 2131297171 */:
                Log.d(TAG, "onClick radio_btn_ipv4");
                this.mIPV4 = true;
                break;
            case R.id.radio_btn_ipv6 /* 2131297172 */:
                Log.d(TAG, "onClick radio_btn_ipv6");
                this.mIPV4 = false;
                break;
            case R.id.start_btn /* 2131297448 */:
                if (this.paused) {
                    GetPingCMD();
                    this.paused = false;
                    new Thread(this.runnable).start();
                    this.mOnOffBtn.setText(R.string.coulometer_finish);
                    this.mOnOffBtn.setBackground(getResources().getDrawable(R.drawable.btn_pause));
                } else {
                    this.paused = true;
                    this.mOnOffBtn.setText(R.string.coulometer_start);
                    this.mOnOffBtn.setBackground(getResources().getDrawable(R.drawable.btn_refresh));
                    Process process = this.proc;
                    if (process != null) {
                        process.destroy();
                    }
                }
                break;
        }
    }
}
