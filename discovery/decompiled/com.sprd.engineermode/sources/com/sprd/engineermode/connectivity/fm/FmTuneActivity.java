package com.sprd.engineermode.connectivity.fm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.fmradio.FmNative;
import com.sprd.engineermode.R;

/* JADX INFO: loaded from: classes4.dex */
public class FmTuneActivity extends Activity implements View.OnClickListener {
    private static final String FREQ_OFFSET_HINT = "0000~FFFF";
    private static final int GET_TUNE_PARA_BTN = 2131296690;
    private static final String POWER_HINT = "0000~1FFF";
    private static final String RSSIT_HINT = "0~150";
    private static final int SET_TUNE_PARA_BTN = 2131297334;
    private static final String SNR_HINT = "0~50";
    private static final String TAG = "FmTuneActivity";
    private static final int TUNE_FREQ_OFFSET_TH_EDIT = 2131297572;
    private static final int TUNE_PILOT_NOISE_TH_EDIT = 2131297575;
    private static final int TUNE_PILOT_POWER_TH_EDIT = 2131297577;
    private static final int TUNE_RSSI_TH_EDIT = 2131297579;
    private static final int TUNE_SNR_TH_EDIT = 2131297581;
    private Context mContext;
    private Button mGetTuneBtn;
    private Button mSetTuneBtn;
    private EditText mTuneFreqOffsetTh;
    private EditText mTuneNoisePowerTh;
    private EditText mTunePilotPowerTh;
    private EditText mTuneRssiTh;
    private EditText mTuneSnrTh;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_tune_mode);
        this.mContext = this;
        this.mTuneRssiTh = (EditText) findViewById(R.id.tune_rssi_th_edit);
        this.mTuneRssiTh.setHint(RSSIT_HINT);
        this.mTuneSnrTh = (EditText) findViewById(R.id.tune_snr_th_edit);
        this.mTuneSnrTh.setHint(SNR_HINT);
        this.mTuneFreqOffsetTh = (EditText) findViewById(R.id.tune_freq_offset_th_edit);
        this.mTuneFreqOffsetTh.setHint(FREQ_OFFSET_HINT);
        this.mTunePilotPowerTh = (EditText) findViewById(R.id.tune_pilot_power_th_edit);
        this.mTunePilotPowerTh.setHint(POWER_HINT);
        this.mTuneNoisePowerTh = (EditText) findViewById(R.id.tune_noise_power_th_edit);
        this.mTuneNoisePowerTh.setHint(POWER_HINT);
        this.mGetTuneBtn = (Button) findViewById(R.id.get_tune_btn);
        this.mGetTuneBtn.setOnClickListener(this);
        this.mSetTuneBtn = (Button) findViewById(R.id.set_tune_btn);
        this.mSetTuneBtn.setOnClickListener(this);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.get_tune_btn) {
            FmNative.FmSeekCriteriaParms read_para = new FmNative.FmSeekCriteriaParms();
            if (-1 == FmNative.getTuneParm(read_para)) {
                Toast.makeText(this.mContext, "get tune fail!", 0).show();
                Log.d(TAG, "get tune fail!");
                return;
            }
            Log.d(TAG, "read parameter:rssi=" + read_para.rssi_th + ";snr=" + ((int) read_para.snr_th) + ";freq_offset=" + read_para.freq_offset_th + ";pilot_power=" + read_para.pilot_power_th + ";noise_power=" + read_para.noise_power_th);
            this.mTuneRssiTh.setText(String.valueOf(read_para.rssi_th));
            this.mTuneSnrTh.setText(String.valueOf((int) read_para.snr_th));
            this.mTuneFreqOffsetTh.setText(Integer.toHexString(read_para.freq_offset_th));
            this.mTunePilotPowerTh.setText(Integer.toHexString(read_para.pilot_power_th));
            this.mTuneNoisePowerTh.setText(Integer.toHexString(read_para.noise_power_th));
            return;
        }
        if (id == R.id.set_tune_btn) {
            EditText editText = this.mTuneRssiTh;
            if (editText == null || "".equals(editText.getText().toString())) {
                Toast.makeText(this.mContext, "empty rssi threshold!", 0).show();
                return;
            }
            int rssi = Integer.valueOf(this.mTuneRssiTh.getText().toString()).intValue();
            if (rssi < 0 || rssi > 150) {
                Toast.makeText(this.mContext, "please input rssi 0~150 !", 0).show();
                return;
            }
            EditText editText2 = this.mTuneSnrTh;
            if (editText2 == null || "".equals(editText2.getText().toString())) {
                Toast.makeText(this.mContext, "empty snr threshold!", 0).show();
                return;
            }
            int snr = Integer.valueOf(this.mTuneSnrTh.getText().toString()).intValue();
            if (snr < 0 || snr > 50) {
                Toast.makeText(this.mContext, "please input snr 0~50 !", 0).show();
                return;
            }
            EditText editText3 = this.mTuneFreqOffsetTh;
            if (editText3 == null || "".equals(editText3.getText().toString())) {
                Toast.makeText(this.mContext, "empty freq offset threshold!", 0).show();
                return;
            }
            int freqOffset = Integer.parseInt(this.mTuneFreqOffsetTh.getText().toString(), 16);
            if (freqOffset < 0 || freqOffset > Integer.parseInt("FFFF", 16)) {
                Toast.makeText(this.mContext, "please input freq  offset 0000~FFFF !", 0).show();
                return;
            }
            EditText editText4 = this.mTunePilotPowerTh;
            if (editText4 == null || "".equals(editText4.getText().toString())) {
                Toast.makeText(this.mContext, "empty pilot power threshold!", 0).show();
                return;
            }
            int pilotPower = Integer.parseInt(this.mTunePilotPowerTh.getText().toString(), 16);
            if (pilotPower < 0 || pilotPower > Integer.parseInt("1FFF", 16)) {
                Toast.makeText(this.mContext, "please input pilot power 0000~1FFF !", 0).show();
                return;
            }
            EditText editText5 = this.mTuneNoisePowerTh;
            if (editText5 == null || "".equals(editText5.getText().toString())) {
                Toast.makeText(this.mContext, "empty noise power threshold!", 0).show();
                return;
            }
            int noisePower = Integer.parseInt(this.mTuneNoisePowerTh.getText().toString(), 16);
            if (noisePower < 0 || noisePower > Integer.parseInt("1FFF", 16)) {
                Toast.makeText(this.mContext, "please input noise power 0000~1FFF !", 0).show();
                return;
            }
            FmNative.FmSeekCriteriaParms write_para = new FmNative.FmSeekCriteriaParms();
            write_para.rssi_th = rssi;
            write_para.snr_th = (byte) snr;
            write_para.freq_offset_th = freqOffset;
            write_para.pilot_power_th = pilotPower;
            write_para.noise_power_th = noisePower;
            Log.d(TAG, "write parameter:rssi=" + write_para.rssi_th + ";snr=" + ((int) write_para.snr_th) + ";freq_offset=" + write_para.freq_offset_th + ";pilot_power=" + write_para.pilot_power_th + ";noise_power=" + write_para.noise_power_th);
            FmNative.setTuneParm(write_para);
        }
    }
}
