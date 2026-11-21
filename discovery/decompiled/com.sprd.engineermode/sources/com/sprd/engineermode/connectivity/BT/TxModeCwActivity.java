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
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.connectivity.BTHelper;
import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
public class TxModeCwActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final int CHANNEL_MAX_VALUE = 78;
    private static final int CHANNEL_MIN_VALUE = 0;
    private static final int MSG_BT_OFF = 3;
    private static final int MSG_TX_MODE_START = 0;
    private static final int MSG_TX_MODE_STOP = 1;
    private static final String TAG = "TxModeCwActivity";
    private IConnectivityApi.IBtEut.TxMode mBtTxMode;
    private TxModeHandler mBtTxModeHandler;
    private EditText mChannel;
    private Button mStart;
    private Button mStop;
    private Spinner mTxMode;
    private Handler mUiThread = new Handler();
    private IConnectivityApi.IBtEut btEut = CoreApi.getConnectivityApi().btEut();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_tx_mode);
        getWindow().setSoftInputMode(32);
        this.mBtTxMode = new IConnectivityApi.IBtEut.TxMode();
        this.mBtTxMode.mode = "1";
        initUI();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mBtTxModeHandler = new TxModeHandler(ht.getLooper());
    }

    private void initUI() {
        this.mChannel = (EditText) findViewById(R.id.tx_mode_channel);
        this.mChannel.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.TxModeCwActivity.1
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (TxModeCwActivity.this.mChannel.getText().length() != 0) {
                    if (Integer.parseInt(TxModeCwActivity.this.mChannel.getText().toString()) < 0 || Integer.parseInt(TxModeCwActivity.this.mChannel.getText().toString()) > 78) {
                        Toast.makeText(TxModeCwActivity.this, "number between 0 and 78", 0).show();
                    }
                }
            }
        });
        this.mTxMode = (Spinner) findViewById(R.id.bt_tx_mode_cw);
        ArrayAdapter<String> txModeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_tx_mode_cw));
        txModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mTxMode.setAdapter((SpinnerAdapter) txModeAdapter);
        this.mTxMode.setOnItemSelectedListener(this);
        this.mStart = (Button) findViewById(R.id.tx_mode_start);
        this.mStart.setOnClickListener(this);
        this.mStop = (Button) findViewById(R.id.tx_mode_stop);
        this.mStop.setOnClickListener(this);
        this.mStop.setEnabled(false);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.mStop.isEnabled()) {
            Log.d(TAG, "Stop is Enabled when click back");
            Message doMessage = this.mBtTxModeHandler.obtainMessage(1);
            this.mBtTxModeHandler.sendMessage(doMessage);
        }
        if (BTHelper.isBTOn) {
            Log.d(TAG, "BT is On when click back");
            Message doMessage2 = this.mBtTxModeHandler.obtainMessage(3);
            this.mBtTxModeHandler.sendMessage(doMessage2);
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
            doMessage = this.mBtTxModeHandler.obtainMessage(0);
        } else if (v == this.mStop) {
            doMessage = this.mBtTxModeHandler.obtainMessage(1);
        }
        this.mBtTxModeHandler.sendMessage(doMessage);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == this.mTxMode) {
            this.mBtTxMode.mode = getResources().getStringArray(R.array.bt_tx_mode_cw_int)[position];
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private boolean getSettingParam() {
        if (this.mChannel.getText().length() == 0) {
            Toast.makeText(this, "please input Channel", 0).show();
            return false;
        }
        this.mBtTxMode.channel = this.mChannel.getText().toString();
        return true;
    }

    class TxModeHandler extends Handler {
        public TxModeHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                if (TxModeCwActivity.this.btEut.btTxModeStart(TxModeCwActivity.this.mBtTxMode)) {
                    TxModeCwActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.-$$Lambda$TxModeCwActivity$TxModeHandler$S4DcXHn3FfgzhyMZ8DDBtT9eNok
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$0$TxModeCwActivity$TxModeHandler();
                        }
                    });
                    return;
                } else {
                    TxModeCwActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.-$$Lambda$TxModeCwActivity$TxModeHandler$9NQF5vZbbJgaw5TLkM7Ih1f6fVg
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$1$TxModeCwActivity$TxModeHandler();
                        }
                    });
                    return;
                }
            }
            if (i == 1) {
                if (TxModeCwActivity.this.btEut.btTxModeStop(TxModeCwActivity.this.mBtTxMode)) {
                    TxModeCwActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.-$$Lambda$TxModeCwActivity$TxModeHandler$RglYP4c6f8wOevGjjgpUFg7C8-I
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$2$TxModeCwActivity$TxModeHandler();
                        }
                    });
                    return;
                } else {
                    TxModeCwActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.-$$Lambda$TxModeCwActivity$TxModeHandler$fI2yJhKoIR8Y3dv8BMMtrZnzd5A
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$3$TxModeCwActivity$TxModeHandler();
                        }
                    });
                    return;
                }
            }
            if (i == 3) {
                if (TxModeCwActivity.this.btEut.BTOff()) {
                    TxModeCwActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.-$$Lambda$TxModeCwActivity$TxModeHandler$9IYitehBXhP1XhpLpHID59MhmJk
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$4$TxModeCwActivity$TxModeHandler();
                        }
                    });
                } else {
                    TxModeCwActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.-$$Lambda$TxModeCwActivity$TxModeHandler$1Ajg7O84kNXDNcHruL3iBeIn6uU
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$5$TxModeCwActivity$TxModeHandler();
                        }
                    });
                }
                TxModeCwActivity.this.finish();
            }
        }

        public /* synthetic */ void lambda$handleMessage$0$TxModeCwActivity$TxModeHandler() {
            Toast.makeText(TxModeCwActivity.this, "TX Mode Start Success", 0).show();
            TxModeCwActivity.this.mStart.setEnabled(false);
            TxModeCwActivity.this.mStop.setEnabled(true);
        }

        public /* synthetic */ void lambda$handleMessage$1$TxModeCwActivity$TxModeHandler() {
            Toast.makeText(TxModeCwActivity.this, "TX Mode Start Fail", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$2$TxModeCwActivity$TxModeHandler() {
            Toast.makeText(TxModeCwActivity.this, "TX Mode Stop Success", 0).show();
            TxModeCwActivity.this.mStart.setEnabled(true);
            TxModeCwActivity.this.mStop.setEnabled(false);
        }

        public /* synthetic */ void lambda$handleMessage$3$TxModeCwActivity$TxModeHandler() {
            Toast.makeText(TxModeCwActivity.this, "TX Mode Stop Fail", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$4$TxModeCwActivity$TxModeHandler() {
            Toast.makeText(TxModeCwActivity.this, "BT Off Success", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$5$TxModeCwActivity$TxModeHandler() {
            Toast.makeText(TxModeCwActivity.this, "BT Off Fail", 0).show();
        }
    }
}
