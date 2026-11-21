package com.sprd.engineermode;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/* JADX INFO: loaded from: classes4.dex */
public class DeviceInfoActivity extends Activity implements View.OnClickListener {
    private static final String ADC_CALIBRATION = "adb_calibration";
    private static final int DIALOG_DEFAULT_TIMEOUT = 30000;
    private static final int MSG_ID_TIMEOUT = 1;
    public static final int OK_BUTTON = 2131296456;
    private static final String RF_CALIBRATION = "rf_calibration";
    String mDialogMessage;
    private boolean isValidAdcCalibrate = true;
    private boolean isValidRfCalibrate = true;
    Handler mTimeoutHandler = new Handler() { // from class: com.sprd.engineermode.DeviceInfoActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                DeviceInfoActivity.this.finish();
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Window window = getWindow();
        setContentView(R.layout.device_info);
        TextView mMessageView = (TextView) window.findViewById(R.id.dialog_message);
        Button okButton = (Button) findViewById(R.id.button_ok);
        okButton.setOnClickListener(this);
        if (getIntent() != null) {
            this.isValidAdcCalibrate = getIntent().getBooleanExtra(ADC_CALIBRATION, true);
            this.isValidRfCalibrate = getIntent().getBooleanExtra(RF_CALIBRATION, true);
            if (!this.isValidAdcCalibrate) {
                if (!this.isValidRfCalibrate) {
                    this.mDialogMessage = "ADC & RF Calibration Failed";
                } else {
                    this.mDialogMessage = "ADC Calibration Failed";
                }
            } else if (!this.isValidRfCalibrate) {
                this.mDialogMessage = "RF Calibration Failed";
            }
        }
        setTitle("Device Calibration Info");
        mMessageView.setText(this.mDialogMessage);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.getId() == R.id.button_ok) {
            finish();
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            finish();
            return false;
        }
        return false;
    }

    private void cancelTimeOut() {
        this.mTimeoutHandler.removeMessages(1);
    }

    private void startTimeOut() {
        cancelTimeOut();
        Handler handler = this.mTimeoutHandler;
        handler.sendMessageDelayed(handler.obtainMessage(1), 30000L);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        startTimeOut();
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        cancelTimeOut();
    }
}
