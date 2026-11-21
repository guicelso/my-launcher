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
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.impl.connectivity.BTHelper;
import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
public class BTBLETXActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String MODE_CW = "1";
    private static final int MSG_BT_BLE_OFF = 2;
    private static final int MSG_BT_BLE_TX_START = 0;
    private static final int MSG_BT_BLE_TX_STOP = 1;
    private static final String TAG = "BTBLETXActivity";
    private IConnectivityApi.IBtEut.BTTX mBTBLETx;
    private BTTXHandler mBTBLETxHandler;
    private EditText mChannel;
    private EditText mDataLen;
    private TableRow mDataLenLayout;
    private Spinner mLePhy;
    private TableRow mLePhyLayout;
    private EditText mPacCnt;
    private TableRow mPacCntLayout;
    private Spinner mPattern;
    private TableRow mPatternLayout;
    private EditText mPowerValue;
    private Button mStart;
    private Button mStop;
    private Spinner mTxMode;
    private Handler mUiThread = new Handler();
    private IConnectivityApi.IBtEut btEut = CoreApi.getConnectivityApi().btEut();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_ble_tx);
        getWindow().setSoftInputMode(32);
        this.mBTBLETx = new IConnectivityApi.IBtEut.BTTX();
        this.mBTBLETx.powervalue = MODE_CW;
        initUI();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mBTBLETxHandler = new BTTXHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.mStop.isEnabled()) {
            Log.d(TAG, "Stop is Enabled when click back");
            Message doMessage = this.mBTBLETxHandler.obtainMessage(1);
            this.mBTBLETxHandler.sendMessage(doMessage);
        }
        if (BTHelper.isBTOn) {
            Log.d(TAG, "BT is On when click back");
            Message doMessage2 = this.mBTBLETxHandler.obtainMessage(2);
            this.mBTBLETxHandler.sendMessage(doMessage2);
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
            doMessage = this.mBTBLETxHandler.obtainMessage(0);
        } else if (v == this.mStop) {
            doMessage = this.mBTBLETxHandler.obtainMessage(1);
        }
        this.mBTBLETxHandler.sendMessage(doMessage);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        IConnectivityApi.IBtEut.BTTX bttx = this.mBTBLETx;
        if (bttx != null) {
            if (parent == this.mPattern) {
                bttx.pattern = getResources().getStringArray(R.array.bt_ble_tx_pattern_int)[position];
                return;
            }
            if (parent == this.mLePhy) {
                if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                    this.mBTBLETx.lephy = getResources().getStringArray(R.array.bt_ble_tx_le_phy_int)[position];
                    return;
                }
                return;
            }
            if (parent == this.mTxMode) {
                bttx.txMode = getResources().getStringArray(R.array.bt_ble_tx_mode_int)[position];
                setTxModeUI();
            }
        }
    }

    private void setTxModeUI() {
        if (this.mBTBLETx.txMode == null || this.mBTBLETx.txMode.equals(MODE_CW)) {
            this.mPatternLayout.setVisibility(8);
            this.mDataLenLayout.setVisibility(8);
            this.mLePhyLayout.setVisibility(8);
            this.mPacCntLayout.setVisibility(8);
            return;
        }
        this.mPatternLayout.setVisibility(0);
        this.mDataLenLayout.setVisibility(0);
        if (!Const.isMarlin2()) {
            this.mLePhyLayout.setVisibility(0);
        }
        this.mPacCntLayout.setVisibility(0);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void initUI() {
        this.mPatternLayout = (TableRow) findViewById(R.id.tablerow_pattern);
        this.mDataLenLayout = (TableRow) findViewById(R.id.tablerow_data_len);
        this.mPacCntLayout = (TableRow) findViewById(R.id.tablerow_pac_cnt);
        this.mPattern = (Spinner) findViewById(R.id.bt_ble_tx_pattern);
        ArrayAdapter<String> patternAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_ble_tx_pattern));
        patternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPattern.setAdapter((SpinnerAdapter) patternAdapter);
        this.mPattern.setOnItemSelectedListener(this);
        this.mLePhy = (Spinner) findViewById(R.id.bt_ble_tx_le_phy);
        ArrayAdapter<String> mLePhyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_ble_tx_le_phy));
        mLePhyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mLePhy.setAdapter((SpinnerAdapter) mLePhyAdapter);
        this.mLePhy.setOnItemSelectedListener(this);
        this.mLePhyLayout = (TableRow) findViewById(R.id.bt_ble_tx_phy_tablerow);
        if (Const.isMarlin2()) {
            this.mLePhyLayout.setVisibility(8);
        }
        setTxModeUI();
        this.mChannel = (EditText) findViewById(R.id.bt_ble_tx_channel);
        this.mChannel.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTBLETXActivity.1
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (BTBLETXActivity.this.mChannel.getText().length() != 0) {
                    if (Integer.parseInt(BTBLETXActivity.this.mChannel.getText().toString()) >= 0 && Integer.parseInt(BTBLETXActivity.this.mChannel.getText().toString()) <= 39) {
                        return;
                    }
                    Toast.makeText(BTBLETXActivity.this, "number between 0 and 39", 0).show();
                }
            }
        });
        this.mDataLen = (EditText) findViewById(R.id.bt_ble_tx_data_length);
        this.mDataLen.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTBLETXActivity.2
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (BTBLETXActivity.this.mDataLen.getText().length() != 0) {
                    if (Integer.parseInt(BTBLETXActivity.this.mDataLen.getText().toString()) >= 0 && Integer.parseInt(BTBLETXActivity.this.mDataLen.getText().toString()) <= 192) {
                        return;
                    }
                    Toast.makeText(BTBLETXActivity.this, "number between 0 and 192", 0).show();
                }
            }
        });
        this.mPacCnt = (EditText) findViewById(R.id.bt_ble_tx_pac_cnt);
        this.mPacCnt.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTBLETXActivity.3
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (BTBLETXActivity.this.mPacCnt.getText().length() != 0) {
                    if (Integer.parseInt(BTBLETXActivity.this.mPacCnt.getText().toString()) < 0 || Integer.parseInt(BTBLETXActivity.this.mPacCnt.getText().toString()) > 65535) {
                        Toast.makeText(BTBLETXActivity.this, "number between 0 and 65535", 0).show();
                    }
                }
            }
        });
        this.mTxMode = (Spinner) findViewById(R.id.bt_ble_tx_mode);
        ArrayAdapter<String> txModeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_ble_tx_mode));
        txModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mTxMode.setAdapter((SpinnerAdapter) txModeAdapter);
        this.mTxMode.setOnItemSelectedListener(this);
        this.mStart = (Button) findViewById(R.id.bt_ble_tx_start);
        this.mStart.setOnClickListener(this);
        this.mStop = (Button) findViewById(R.id.bt_ble_tx_stop);
        this.mStop.setOnClickListener(this);
        this.mStop.setEnabled(false);
        if (Const.isMarlin2()) {
            this.mLePhy.setEnabled(false);
        }
    }

    private boolean getSettingParam() {
        if (this.mChannel.getText().length() == 0) {
            Toast.makeText(this, "please input BLE TX Channel", 0).show();
            return false;
        }
        this.mBTBLETx.channel = this.mChannel.getText().toString();
        if (this.mBTBLETx.txMode != null && !this.mBTBLETx.txMode.equals(MODE_CW)) {
            if (this.mDataLen.getText().length() == 0) {
                Toast.makeText(this, "please input BLE TX Data Length", 0).show();
                return false;
            }
            this.mBTBLETx.datalength = this.mDataLen.getText().toString();
            if (this.mPacCnt.getText().length() == 0) {
                Toast.makeText(this, "please input BLE TX Pac Cnt", 0).show();
                return false;
            }
            this.mBTBLETx.paccnt = this.mPacCnt.getText().toString();
        }
        Log.d(TAG, "Now BT BLE TX Start\nBLE TX Pattern is " + this.mBTBLETx.pattern + "\nBLE TX Channel is " + this.mBTBLETx.channel + "\nBLE TX Pac Type is " + this.mBTBLETx.pactype + "\nBLE TX Pac Len is " + this.mBTBLETx.paclen + "\nBLE TX Pac Cnt is " + this.mBTBLETx.paccnt + "\nBLE TX DataLen is " + this.mBTBLETx.datalength + "\nBLE TX DataLen is " + this.mBTBLETx.lephy + "\n");
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
                if (BTBLETXActivity.this.btEut.BTBLETxStart(BTBLETXActivity.this.mBTBLETx)) {
                    BTBLETXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLETXActivity.BTTXHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTBLETXActivity.this, "BLE TX Start Success", 0).show();
                            BTBLETXActivity.this.mStart.setEnabled(false);
                            BTBLETXActivity.this.mStop.setEnabled(true);
                        }
                    });
                    return;
                } else {
                    BTBLETXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLETXActivity.BTTXHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTBLETXActivity.this, "BLE TX Start Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 1) {
                if (BTBLETXActivity.this.btEut.BTBLETxStop(BTBLETXActivity.this.mBTBLETx)) {
                    BTBLETXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLETXActivity.BTTXHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTBLETXActivity.this, "BLE TX Stop Success", 0).show();
                            BTBLETXActivity.this.mStart.setEnabled(true);
                            BTBLETXActivity.this.mStop.setEnabled(false);
                        }
                    });
                    return;
                } else {
                    BTBLETXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLETXActivity.BTTXHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTBLETXActivity.this, "BLE TX Stop Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 2) {
                if (BTBLETXActivity.this.btEut.BTOff()) {
                    BTBLETXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLETXActivity.BTTXHandler.5
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTBLETXActivity.this, "BT Off Success", 0).show();
                        }
                    });
                } else {
                    BTBLETXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLETXActivity.BTTXHandler.6
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTBLETXActivity.this, "BT Off Fail", 0).show();
                        }
                    });
                }
                BTBLETXActivity.this.finish();
            }
        }
    }
}
