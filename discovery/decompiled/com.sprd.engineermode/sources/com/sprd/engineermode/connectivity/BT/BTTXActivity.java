package com.sprd.engineermode.connectivity.BT;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.impl.connectivity.BTHelper;
import com.unisoc.engineermode.core.intf.IConnectivityApi;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class BTTXActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final int CHANNEL_MAX = 78;
    private static final int CHANNEL_MIN = 0;
    private static final int CHANNEL_VALUE = 255;
    private static final String MODE_CW = "1";
    private static final int MSG_BT_OFF = 2;
    private static final int MSG_BT_TX_START = 0;
    private static final int MSG_BT_TX_STOP = 1;
    private static final int PAC_CNT_MAX = 65535;
    private static final int PAC_CNT_MIN = 0;
    private static final int POWER_MAX_MARLIN2 = 4;
    private static final int POWER_MAX_MARLIN3 = 9;
    private static final int POWER_MIN = 0;
    private static final String TAG = "BTTXActivity";
    private static final int VALUE_NULL = 0;
    private BTTXHandler mBTTXHandler;
    private IConnectivityApi.IBtEut.BTTX mBTTx;
    private EditText mChannel;
    private EditText mPacCnt;
    private TableRow mPacCntLayout;
    private EditText mPacLen;
    private TableRow mPacLenLayout;
    private TextView mPacLenSummary;
    private Spinner mPacType;
    private TableRow mPacTypeLayout;
    private Spinner mPattern;
    private TableRow mPatternLayout;
    private EditText mPowerValue;
    private TableRow mPowerValueLayout;
    private Button mStart;
    private Button mStop;
    private Spinner mTxMode;
    private String mMaxPacLen = "0";
    private Handler mUiThread = new Handler();
    private IConnectivityApi.IBtEut btEut = CoreApi.getConnectivityApi().btEut();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_tx);
        getWindow().setSoftInputMode(32);
        this.mBTTx = new IConnectivityApi.IBtEut.BTTX();
        initUI();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mBTTXHandler = new BTTXHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.mStop.isEnabled()) {
            Log.d(TAG, "Stop is Enabled when click back");
            Message doMessage = this.mBTTXHandler.obtainMessage(1);
            this.mBTTXHandler.sendMessage(doMessage);
        }
        if (BTHelper.isBTOn) {
            Log.d(TAG, "BT is On when click back");
            Message doMessage2 = this.mBTTXHandler.obtainMessage(2);
            this.mBTTXHandler.sendMessage(doMessage2);
            return;
        }
        super.onBackPressed();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Message doMessage = null;
        if (!getSettingParam()) {
            return;
        }
        if (v == this.mStart) {
            doMessage = this.mBTTXHandler.obtainMessage(0);
        } else if (v == this.mStop) {
            doMessage = this.mBTTXHandler.obtainMessage(1);
        }
        this.mBTTXHandler.sendMessage(doMessage);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        IConnectivityApi.IBtEut.BTTX bttx = this.mBTTx;
        if (bttx != null) {
            if (parent == this.mPacType) {
                bttx.pactype = getResources().getStringArray(R.array.bt_pac_type_int)[position];
                this.mMaxPacLen = getResources().getStringArray(R.array.bt_pac_max_len)[position];
                this.mPacLenSummary.setText("MaxLen is " + this.mMaxPacLen);
                return;
            }
            if (parent == this.mPattern) {
                bttx.pattern = getResources().getStringArray(R.array.bt_tx_pattern_int)[position];
            } else if (parent == this.mTxMode) {
                bttx.txMode = getResources().getStringArray(R.array.bt_tx_mode_int)[position];
                setTxModeUI();
            }
        }
    }

    private void setTxModeUI() {
        if (this.mBTTx.txMode == null || this.mBTTx.txMode.equals(MODE_CW)) {
            this.mPatternLayout.setVisibility(8);
            this.mPacTypeLayout.setVisibility(8);
            this.mPacLenLayout.setVisibility(8);
            this.mPowerValueLayout.setVisibility(8);
            this.mPacCntLayout.setVisibility(8);
            return;
        }
        this.mPatternLayout.setVisibility(0);
        this.mPacTypeLayout.setVisibility(0);
        this.mPacLenLayout.setVisibility(0);
        this.mPowerValueLayout.setVisibility(0);
        this.mPacCntLayout.setVisibility(0);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void initUI() {
        this.mPatternLayout = (TableRow) findViewById(R.id.tablerow_pattern);
        this.mPacTypeLayout = (TableRow) findViewById(R.id.tablerow_pac_type);
        this.mPacLenLayout = (TableRow) findViewById(R.id.tablerow_pac_len);
        this.mPowerValueLayout = (TableRow) findViewById(R.id.tablerow_power_value);
        this.mPacCntLayout = (TableRow) findViewById(R.id.tablerow_pac_cnt);
        setTxModeUI();
        this.mPattern = (Spinner) findViewById(R.id.bt_tx_pattern);
        ArrayAdapter<String> patternAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_tx_pattern));
        patternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPattern.setAdapter((SpinnerAdapter) patternAdapter);
        this.mPattern.setOnItemSelectedListener(this);
        this.mChannel = (EditText) findViewById(R.id.bt_tx_channel);
        this.mChannel.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTTXActivity.1
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (BTTXActivity.this.mChannel.getText().length() != 0) {
                    if ((Integer.parseInt(BTTXActivity.this.mChannel.getText().toString()) >= 0 && Integer.parseInt(BTTXActivity.this.mChannel.getText().toString()) <= 78) || Integer.parseInt(BTTXActivity.this.mChannel.getText().toString()) == 255) {
                        return;
                    }
                    Toast.makeText(BTTXActivity.this, "number between 0 and 78 or 255", 0).show();
                }
            }
        });
        this.mPacType = (Spinner) findViewById(R.id.bt_tx_pac_type);
        ArrayAdapter<String> pactype = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_pac_type));
        pactype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPacType.setAdapter((SpinnerAdapter) pactype);
        this.mPacType.setOnItemSelectedListener(this);
        this.mPacLen = (EditText) findViewById(R.id.bt_tx_pac_len);
        this.mPacLenSummary = (TextView) findViewById(R.id.bt_tx_pac_len_summary);
        this.mPowerValue = (EditText) findViewById(R.id.bt_tx_power_value);
        if (Const.isMarlin2()) {
            this.mPowerValue.setHint(getString(R.string.power_value_marlin2));
        } else {
            this.mPowerValue.setHint(getString(R.string.power_value_marlin3));
        }
        this.mPowerValue.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTTXActivity.2
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                int max;
                if (BTTXActivity.this.mPowerValue.getText().length() != 0) {
                    if (Const.isMarlin2()) {
                        max = 4;
                    } else {
                        max = 9;
                    }
                    if (Integer.parseInt(BTTXActivity.this.mPowerValue.getText().toString()) < 0 || Integer.parseInt(BTTXActivity.this.mPowerValue.getText().toString()) > max) {
                        Toast.makeText(BTTXActivity.this, String.format(Locale.US, "number between %d and %d", 0, Integer.valueOf(max)), 0).show();
                    }
                }
            }
        });
        this.mPacCnt = (EditText) findViewById(R.id.bt_tx_pac_cnt);
        this.mPacCnt.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTTXActivity.3
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (BTTXActivity.this.mPacCnt.getText().length() != 0) {
                    if (Integer.parseInt(BTTXActivity.this.mPacCnt.getText().toString()) < 0 || Integer.parseInt(BTTXActivity.this.mPacCnt.getText().toString()) > 65535) {
                        Toast.makeText(BTTXActivity.this, "number between 0 and 65535", 0).show();
                    }
                }
            }
        });
        this.mTxMode = (Spinner) findViewById(R.id.bt_tx_mode);
        ArrayAdapter<String> txModeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_tx_mode));
        txModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mTxMode.setAdapter((SpinnerAdapter) txModeAdapter);
        this.mTxMode.setOnItemSelectedListener(this);
        this.mStart = (Button) findViewById(R.id.bt_tx_start);
        this.mStart.setOnClickListener(this);
        this.mStop = (Button) findViewById(R.id.bt_tx_stop);
        this.mStop.setOnClickListener(this);
        this.mStop.setEnabled(false);
    }

    private boolean getSettingParam() {
        if (this.mChannel.getText().length() == 0) {
            Toast.makeText(this, "please input TX Channel", 0).show();
            return false;
        }
        this.mBTTx.channel = this.mChannel.getText().toString();
        if (this.mBTTx.txMode != null && !this.mBTTx.txMode.equals(MODE_CW)) {
            if (this.mPacLen.getText().length() == 0) {
                Toast.makeText(this, "please input TX Pac Len", 0).show();
                return false;
            }
            if (Integer.parseInt(this.mPacLen.getText().toString()) > Integer.parseInt(this.mMaxPacLen)) {
                this.mBTTx.paclen = this.mMaxPacLen;
            } else {
                this.mBTTx.paclen = this.mPacLen.getText().toString();
            }
            if (this.mPowerValue.getText().length() == 0) {
                Toast.makeText(this, "please input TX Power Value", 0).show();
                return false;
            }
            this.mBTTx.powervalue = this.mPowerValue.getText().toString();
            if (this.mPacCnt.getText().length() == 0) {
                Toast.makeText(this, "please input TX Pac Cnt", 0).show();
                return false;
            }
            this.mBTTx.paccnt = this.mPacCnt.getText().toString();
        }
        Log.d(TAG, "Now BT TX Start\nTX Pattern is " + this.mBTTx.pattern + "\nTX Channel is " + this.mBTTx.channel + "\nTX Pac Type is " + this.mBTTx.pactype + "\nTX Pac Len is " + this.mBTTx.paclen + "\nTX Power Value is " + this.mBTTx.powervalue + "\nTX Pac Cnt is " + this.mBTTx.paccnt);
        return true;
    }

    class BTTXHandler extends Handler {
        public BTTXHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                if (BTTXActivity.this.btEut.BTTxStart(BTTXActivity.this.mBTTx)) {
                    BTTXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTTXActivity.BTTXHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTTXActivity.this, "TX Start Success", 0).show();
                            BTTXActivity.this.mStart.setEnabled(false);
                            BTTXActivity.this.mStop.setEnabled(true);
                        }
                    });
                    return;
                } else {
                    BTTXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTTXActivity.BTTXHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTTXActivity.this, "TX Start Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 1) {
                if (BTTXActivity.this.btEut.BTTxStop(BTTXActivity.this.mBTTx)) {
                    BTTXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTTXActivity.BTTXHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTTXActivity.this, "TX Stop Success", 0).show();
                            BTTXActivity.this.mStart.setEnabled(true);
                            BTTXActivity.this.mStop.setEnabled(false);
                        }
                    });
                    return;
                } else {
                    BTTXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTTXActivity.BTTXHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTTXActivity.this, "TX Stop Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 2) {
                if (BTTXActivity.this.btEut.BTOff()) {
                    Toast.makeText(BTTXActivity.this, "BT Off Success", 0).show();
                } else {
                    Toast.makeText(BTTXActivity.this, "BT Off Fail", 0).show();
                }
                BTTXActivity.this.finish();
            }
        }
    }
}
