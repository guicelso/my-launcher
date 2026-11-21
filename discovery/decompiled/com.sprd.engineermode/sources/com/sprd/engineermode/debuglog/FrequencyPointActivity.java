package com.sprd.engineermode.debuglog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class FrequencyPointActivity extends Activity implements View.OnClickListener {
    private static final String PHONE_CHANNEL = "phonae_channel";
    private static final String TAG = "FrequencyPointActivity";
    private static final int UPDATE = 1;
    private String frequencyNumber;
    private String intentChannel;
    private EditText mFrequencyEditText;
    private RadioButton mFrequencyLte;
    private RadioButton mFrequencyWcdma;
    private Button mOnOffBtn;
    private RadioGroup mPingAddress;
    private boolean paused = true;
    private boolean mLte = true;
    private Runnable runnable = new Runnable() { // from class: com.sprd.engineermode.debuglog.FrequencyPointActivity.1
        @Override // java.lang.Runnable
        public void run() {
            boolean unused = FrequencyPointActivity.this.paused;
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_point);
        Intent intent = getIntent();
        this.intentChannel = intent.getStringExtra(PHONE_CHANNEL);
        this.mPingAddress = (RadioGroup) findViewById(R.id.radio_group);
        this.mFrequencyLte = (RadioButton) this.mPingAddress.findViewById(R.id.radio_btn_lte);
        this.mFrequencyLte.setOnClickListener(this);
        this.mFrequencyWcdma = (RadioButton) this.mPingAddress.findViewById(R.id.radio_btn_wcdma);
        this.mFrequencyWcdma.setOnClickListener(this);
        this.mFrequencyEditText = (EditText) findViewById(R.id.frequency_point_edit);
        this.mOnOffBtn = (Button) findViewById(R.id.start_btn);
        this.mOnOffBtn.setOnClickListener(this);
    }

    private String sendAt(String cmd, String servername) {
        String res = IATUtils.sendATCmd(cmd, servername);
        Log.d(TAG, "ATCmd is " + cmd + ", result is " + res);
        if (res != null) {
            return res;
        }
        return "FAILED";
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

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_btn_lte /* 2131297173 */:
                Log.d(TAG, "onClick radio_btn_lte");
                this.mLte = true;
                break;
            case R.id.radio_btn_wcdma /* 2131297174 */:
                Log.d(TAG, "onClick radio_btn_wcdma");
                this.mLte = false;
                break;
            case R.id.start_btn /* 2131297448 */:
                this.frequencyNumber = this.mFrequencyEditText.getText().toString();
                String str = this.frequencyNumber;
                if (str == null || str.equals("")) {
                    Toast.makeText(this, "Frequency point is empty", 0).show();
                } else if (this.mLte) {
                    sendAt("AT+SFUN=5", this.intentChannel);
                    sendAt("AT+SPFRQ=1", this.intentChannel);
                    sendAt("AT+SFUN=4", this.intentChannel);
                    sendAt("AT+SFUN=5", this.intentChannel);
                    sendAt("AT+SPCLEANINFO=45", this.intentChannel);
                    sendAt("AT+SPFRQ=0,4," + this.frequencyNumber, this.intentChannel);
                    sendAt("AT+SFUN=4", this.intentChannel);
                } else {
                    sendAt("AT+SPFRQ=0,0," + this.frequencyNumber, this.intentChannel);
                }
                break;
        }
    }
}
