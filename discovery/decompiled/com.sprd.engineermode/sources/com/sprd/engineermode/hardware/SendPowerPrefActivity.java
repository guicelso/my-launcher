package com.sprd.engineermode.hardware;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SendPowerPrefActivity extends Activity {
    private static final int CLEAR_MAX_POWER = 1;
    private static final String ORI_WCDMA_BAND = "ORI_WCDMA_BAND";
    private static final int SET_MAX_POWER = 0;
    private static final String TAG = "SendPowerPrefActivity";
    private static final int[] mWBandMap = {1, 2, 5, 8};
    private static final int[] mWBandET = {R.id.et_wband1, R.id.et_wband2, R.id.et_wband5, R.id.et_wband8};
    private static final int[] mWBandOK = {R.id.ok_wband1, R.id.ok_wband2, R.id.ok_wband5, R.id.ok_wband8};
    private static final int[] mWBandClear = {R.id.clear_wband1, R.id.clear_wband2, R.id.clear_wband5, R.id.clear_wband8};
    private static final int[] mWBandText = {R.id.text_wband1, R.id.text_wband2, R.id.text_wband5, R.id.text_wband8};
    private EditText mGsm850 = null;
    private EditText mEgsm900 = null;
    private EditText mDcs1800 = null;
    private EditText mPcs1900 = null;
    private EditText mTD19 = null;
    private EditText mTD21 = null;
    private EditText mWBand1 = null;
    private EditText mWBand2 = null;
    private EditText mWBand5 = null;
    private EditText mWBand8 = null;
    private Handler mSetMaxPowerHandler = null;
    private Handler mUiHandler = new Handler();
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private boolean isSupportTD = false;
    private boolean isSupportWCD = false;
    private List<ITelephonyApi.IBand.WcdmaBand> supportedWCDMABands = null;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws Exception {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_power_set);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mSetMaxPowerHandler = new SetMaxPowerHandler(ht.getLooper());
        this.mGsm850 = (EditText) findViewById(R.id.et_gsm850);
        this.mEgsm900 = (EditText) findViewById(R.id.et_egsm900);
        this.mDcs1800 = (EditText) findViewById(R.id.et_dcs1800);
        this.mPcs1900 = (EditText) findViewById(R.id.et_pcs1900);
        this.mTD19 = (EditText) findViewById(R.id.et_td19);
        this.mTD21 = (EditText) findViewById(R.id.et_td21);
        this.mWBand1 = (EditText) findViewById(R.id.et_wband1);
        this.mWBand2 = (EditText) findViewById(R.id.et_wband2);
        this.mWBand5 = (EditText) findViewById(R.id.et_wband5);
        this.mWBand8 = (EditText) findViewById(R.id.et_wband8);
        this.isSupportTD = this.teleApi.telephonyInfo().isSupportTdscdma();
        this.isSupportWCD = this.teleApi.telephonyInfo().isSupportWcdma();
        this.supportedWCDMABands = loadBands();
        Button buttonOk = (Button) findViewById(R.id.ok_gsm850);
        buttonOk.setText("Set");
        buttonOk.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mGsm850.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.GSM850.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mGsm850.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonOk2 = (Button) findViewById(R.id.ok_egsm900);
        buttonOk2.setText("Set");
        buttonOk2.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mEgsm900.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.GSM900.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mEgsm900.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonOk3 = (Button) findViewById(R.id.ok_dcs1800);
        buttonOk3.setText("Set");
        buttonOk3.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mDcs1800.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.DCS1800.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mDcs1800.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonOk4 = (Button) findViewById(R.id.ok_pcs1900);
        buttonOk4.setText("Set");
        buttonOk4.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mPcs1900.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.DCS1900.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mPcs1900.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonOk5 = (Button) findViewById(R.id.ok_td19);
        buttonOk5.setText("Set");
        buttonOk5.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mTD19.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.TD19.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mTD19.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonOk6 = (Button) findViewById(R.id.ok_td21);
        buttonOk6.setText("Set");
        buttonOk6.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mTD21.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.TD21.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mTD21.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonOk7 = (Button) findViewById(R.id.ok_wband1);
        buttonOk7.setText("Set");
        buttonOk7.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mWBand1.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.WBAND1.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mWBand1.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonOk8 = (Button) findViewById(R.id.ok_wband2);
        buttonOk8.setText("Set");
        buttonOk8.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mWBand2.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.WBAND2.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mWBand2.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonOk9 = (Button) findViewById(R.id.ok_wband5);
        buttonOk9.setText("Set");
        buttonOk9.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mWBand5.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.WBAND5.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mWBand5.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonOk10 = (Button) findViewById(R.id.ok_wband8);
        buttonOk10.setText("Set");
        buttonOk10.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SendPowerPrefActivity.this.mWBand8.getText().toString().equals("")) {
                    Toast.makeText(SendPowerPrefActivity.this, "please input data", 0).show();
                } else {
                    Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(0, ITelephonyApi.IRadioFunc.Band.WBAND8.getValue(), Integer.valueOf(SendPowerPrefActivity.this.mWBand8.getText().toString()).intValue());
                    SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
                }
            }
        });
        Button buttonClear = (Button) findViewById(R.id.clear_gsm850);
        buttonClear.setText("Clear");
        buttonClear.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.GSM850, R.id.et_gsm850));
        Button buttonClear2 = (Button) findViewById(R.id.clear_egsm900);
        buttonClear2.setText("Clear");
        buttonClear2.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.GSM900, R.id.et_egsm900));
        Button buttonClear3 = (Button) findViewById(R.id.clear_dcs1800);
        buttonClear3.setText("Clear");
        buttonClear3.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.DCS1800, R.id.et_dcs1800));
        Button buttonClear4 = (Button) findViewById(R.id.clear_pcs1900);
        buttonClear4.setText("Clear");
        buttonClear4.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.DCS1900, R.id.et_pcs1900));
        Button buttonClear5 = (Button) findViewById(R.id.clear_td19);
        buttonClear5.setText("Clear");
        buttonClear5.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.TD19, R.id.et_td19));
        Button buttonClear6 = (Button) findViewById(R.id.clear_td21);
        buttonClear6.setText("Clear");
        buttonClear6.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.TD21, R.id.et_td21));
        Button buttonClear7 = (Button) findViewById(R.id.clear_wband1);
        buttonClear7.setText("Clear");
        buttonClear7.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.WBAND1, R.id.et_wband1));
        Button buttonClear8 = (Button) findViewById(R.id.clear_wband2);
        buttonClear8.setText("Clear");
        buttonClear8.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.WBAND2, R.id.et_wband2));
        Button buttonClear9 = (Button) findViewById(R.id.clear_wband5);
        buttonClear9.setText("Clear");
        buttonClear9.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.WBAND5, R.id.et_wband5));
        Button buttonClear10 = (Button) findViewById(R.id.clear_wband8);
        buttonClear10.setText("Clear");
        buttonClear10.setOnClickListener(new ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band.WBAND8, R.id.et_wband8));
        if (!this.isSupportTD) {
            findViewById(R.id.et_td19).setVisibility(8);
            findViewById(R.id.et_td21).setVisibility(8);
            findViewById(R.id.ok_td19).setVisibility(8);
            findViewById(R.id.ok_td21).setVisibility(8);
            findViewById(R.id.clear_td19).setVisibility(8);
            findViewById(R.id.clear_td21).setVisibility(8);
            findViewById(R.id.text_td19).setVisibility(8);
            findViewById(R.id.text_td21).setVisibility(8);
        }
        if (!this.isSupportWCD) {
            findViewById(R.id.et_wband1).setVisibility(8);
            findViewById(R.id.et_wband2).setVisibility(8);
            findViewById(R.id.et_wband5).setVisibility(8);
            findViewById(R.id.et_wband8).setVisibility(8);
            findViewById(R.id.ok_wband1).setVisibility(8);
            findViewById(R.id.ok_wband2).setVisibility(8);
            findViewById(R.id.ok_wband5).setVisibility(8);
            findViewById(R.id.ok_wband8).setVisibility(8);
            findViewById(R.id.clear_wband1).setVisibility(8);
            findViewById(R.id.clear_wband2).setVisibility(8);
            findViewById(R.id.clear_wband5).setVisibility(8);
            findViewById(R.id.clear_wband8).setVisibility(8);
            findViewById(R.id.text_wband1).setVisibility(8);
            findViewById(R.id.text_wband2).setVisibility(8);
            findViewById(R.id.text_wband5).setVisibility(8);
            findViewById(R.id.text_wband8).setVisibility(8);
        }
        if (this.isSupportWCD) {
            List<ITelephonyApi.IBand.WcdmaBand> list = this.supportedWCDMABands;
            if (list == null || list.size() == 0) {
                for (int i = 0; i < mWBandMap.length; i++) {
                    boolean isOpened = true;
                    try {
                        isOpened = this.teleApi.band().isWcdmaBandOpen(0, ITelephonyApi.IBand.WcdmaBand.of(mWBandMap[i]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!isOpened) {
                        findViewById(mWBandET[i]).setVisibility(8);
                        findViewById(mWBandOK[i]).setVisibility(8);
                        findViewById(mWBandClear[i]).setVisibility(8);
                        findViewById(mWBandText[i]).setVisibility(8);
                    }
                }
                return;
            }
            int i2 = 0;
            while (true) {
                int[] iArr = mWBandMap;
                if (i2 < iArr.length) {
                    int band = iArr[i2];
                    boolean existedFlag = existSupportedWCDMABands(band);
                    if (!existedFlag) {
                        findViewById(mWBandET[i2]).setVisibility(8);
                        findViewById(mWBandOK[i2]).setVisibility(8);
                        findViewById(mWBandClear[i2]).setVisibility(8);
                        findViewById(mWBandText[i2]).setVisibility(8);
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    private boolean existSupportedWCDMABands(int band) {
        List<ITelephonyApi.IBand.WcdmaBand> list = this.supportedWCDMABands;
        if (list == null || list.size() == 0) {
            return false;
        }
        for (ITelephonyApi.IBand.WcdmaBand wBand : this.supportedWCDMABands) {
            if (wBand.getValue() == band) {
                return true;
            }
        }
        return false;
    }

    private List<ITelephonyApi.IBand.WcdmaBand> loadBands() {
        List<ITelephonyApi.IBand.WcdmaBand> bandList = new ArrayList<>();
        Log.d(TAG, "loadBands...");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String saveValue = sp.getString(ORI_WCDMA_BAND, "");
        Log.d(TAG, "saveValue=:" + saveValue);
        if (saveValue.equals("")) {
            return bandList;
        }
        String[] bands = saveValue.split(",");
        for (String band : bands) {
            Log.d(TAG, "load band: " + band);
            bandList.add(ITelephonyApi.IBand.WcdmaBand.valueOf(band));
        }
        return bandList;
    }

    private class ClearButtonOnClickListener implements View.OnClickListener {
        private ITelephonyApi.IRadioFunc.Band band;
        private int editorId;

        public ClearButtonOnClickListener(ITelephonyApi.IRadioFunc.Band band, int editorId) {
            this.band = band;
            this.editorId = editorId;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Message msg = SendPowerPrefActivity.this.mSetMaxPowerHandler.obtainMessage(1, this.band.getValue(), this.editorId);
            SendPowerPrefActivity.this.mSetMaxPowerHandler.sendMessage(msg);
        }
    }

    private void clickClearButton(ITelephonyApi.IRadioFunc.Band band, int editorId) {
        Message msg = this.mSetMaxPowerHandler.obtainMessage(1, band.getValue(), editorId);
        this.mSetMaxPowerHandler.sendMessage(msg);
    }

    private int parseWCDMAResp(String resp) {
        String[] str = resp.split(":|\n|,");
        Log.d(TAG, "parseWCDMAResp() str:" + Arrays.toString(str));
        if (str.length >= 3 && str[3].trim().equals("1")) {
            return 1;
        }
        return 0;
    }

    class SetMaxPowerHandler extends Handler {
        public SetMaxPowerHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(final Message msg) {
            ITelephonyApi.IRadioFunc.Band band = ITelephonyApi.IRadioFunc.Band.of(msg.arg1);
            boolean isSuccess = false;
            int i = msg.what;
            if (i == 0) {
                try {
                    SendPowerPrefActivity.this.teleApi.radioFunc().setMaxPower(band, msg.arg2);
                    isSuccess = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (i == 1) {
                try {
                    SendPowerPrefActivity.this.teleApi.radioFunc().clearMaxPower(band);
                    isSuccess = true;
                    SendPowerPrefActivity.this.mUiHandler.post(new Runnable() { // from class: com.sprd.engineermode.hardware.SendPowerPrefActivity.SetMaxPowerHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EditText editor = (EditText) SendPowerPrefActivity.this.findViewById(msg.arg2);
                            if (editor == null) {
                                Log.d(SendPowerPrefActivity.TAG, "editor is null...");
                            } else {
                                editor.setText("");
                            }
                        }
                    });
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                return;
            }
            if (isSuccess) {
                Toast.makeText(SendPowerPrefActivity.this, "Success", 0).show();
            } else {
                Toast.makeText(SendPowerPrefActivity.this, "Fail", 0).show();
            }
        }
    }
}
