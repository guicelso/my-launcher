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
public class FMAudioActivity extends Activity implements View.OnClickListener {
    private static final int AUDIO_HBOUND_EDIT = 2131296350;
    private static final int AUDIO_LBOUND_EDIT = 2131296352;
    private static final int AUDIO_PHYT_EDIT = 2131296355;
    private static final int AUDIO_POWER_TH_EDIT = 2131296357;
    private static final int AUDIO_SNR_TH_EDIT = 2131296360;
    private static final int GET_AUDIO_PARA_BTN = 2131296689;
    private static final String HINT = "0~512";
    private static final String PHYT_HINT = "0~31";
    private static final int SET_AUDIO_PARA_BTN = 2131297331;
    private static final String SNR_HINT = "0~50";
    private static final String TAG = "FMAudioActivity";
    private EditText mAudioHbound;
    private EditText mAudioLbound;
    private EditText mAudioPhyt;
    private EditText mAudioPowerTh;
    private EditText mAudioSnrTh;
    private Context mContext;
    private Button mGetAudioBtn;
    private Button mSetAudioBtn;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_audio_mode);
        this.mContext = this;
        this.mAudioHbound = (EditText) findViewById(R.id.audio_hbound_edit);
        this.mAudioHbound.setHint(HINT);
        this.mAudioLbound = (EditText) findViewById(R.id.audio_lbound_edit);
        this.mAudioLbound.setHint(HINT);
        this.mAudioPowerTh = (EditText) findViewById(R.id.audio_power_th_edit);
        this.mAudioPowerTh.setHint(HINT);
        this.mAudioPhyt = (EditText) findViewById(R.id.audio_phyt_edit);
        this.mAudioPhyt.setHint(PHYT_HINT);
        this.mAudioSnrTh = (EditText) findViewById(R.id.audio_snr_th_edit);
        this.mAudioSnrTh.setHint(SNR_HINT);
        this.mGetAudioBtn = (Button) findViewById(R.id.get_audio_btn);
        this.mGetAudioBtn.setOnClickListener(this);
        this.mSetAudioBtn = (Button) findViewById(R.id.set_audio_btn);
        this.mSetAudioBtn.setOnClickListener(this);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.get_audio_btn) {
            FmNative.FmAudioThresholdParms read_para = new FmNative.FmAudioThresholdParms();
            if (-1 == FmNative.getAudioParm(read_para)) {
                Toast.makeText(this.mContext, "get audio fail!", 0).show();
                Log.d(TAG, "get audio fail!");
                return;
            }
            Log.d(TAG, "read parameter:hbound=" + read_para.hbound + ";lhound=" + read_para.lbound + ";power=" + read_para.power_th + ";phyt=" + ((int) read_para.phyt) + ";snr=" + ((int) read_para.snr_th));
            this.mAudioHbound.setText(String.valueOf(read_para.hbound));
            this.mAudioLbound.setText(String.valueOf(read_para.lbound));
            this.mAudioPowerTh.setText(String.valueOf(read_para.power_th));
            this.mAudioPhyt.setText(String.valueOf((int) read_para.phyt));
            this.mAudioSnrTh.setText(String.valueOf((int) read_para.snr_th));
            return;
        }
        if (id == R.id.set_audio_btn) {
            EditText editText = this.mAudioHbound;
            if (editText == null || "".equals(String.valueOf(editText.getText()))) {
                Toast.makeText(this.mContext, "empty softmute hbound threshold!", 0).show();
                return;
            }
            int hbound = Integer.valueOf(this.mAudioHbound.getText().toString()).intValue();
            if (hbound < 0 || hbound > 512) {
                Toast.makeText(this.mContext, "please input softmute hbound 0~512 !", 0).show();
                return;
            }
            EditText editText2 = this.mAudioLbound;
            if (editText2 == null || "".equals(String.valueOf(editText2.getText()))) {
                Toast.makeText(this.mContext, "empty softmute lbound threshold!", 0).show();
                return;
            }
            int lbound = Integer.valueOf(this.mAudioLbound.getText().toString()).intValue();
            if (lbound < 0 || lbound > 512) {
                Toast.makeText(this.mContext, "please input softmute lbound 0~512 !", 0).show();
                return;
            }
            EditText editText3 = this.mAudioPowerTh;
            if (editText3 == null || "".equals(String.valueOf(editText3.getText()))) {
                Toast.makeText(this.mContext, "empty power threshold!", 0).show();
                return;
            }
            int power = Integer.valueOf(this.mAudioPowerTh.getText().toString()).intValue();
            if (power < 0 || power > 512) {
                Toast.makeText(this.mContext, "please input power 0~512 !", 0).show();
                return;
            }
            EditText editText4 = this.mAudioPhyt;
            if (editText4 == null || "".equals(String.valueOf(editText4.getText()))) {
                Toast.makeText(this.mContext, "empty Retardation coefficient threshold!", 0).show();
                return;
            }
            int phyt = Integer.valueOf(this.mAudioPhyt.getText().toString()).intValue();
            if (phyt < 0 || phyt > 31) {
                Toast.makeText(this.mContext, "please input Retardation coefficient 0~31 !", 0).show();
                return;
            }
            EditText editText5 = this.mAudioSnrTh;
            if (editText5 == null || "".equals(String.valueOf(editText5.getText()))) {
                Toast.makeText(this.mContext, "empty snr threshold!", 0).show();
                return;
            }
            int snr = Integer.valueOf(this.mAudioSnrTh.getText().toString()).intValue();
            if (snr < 0 || snr > 50) {
                Toast.makeText(this.mContext, "please input snr 0~50 !", 0).show();
                return;
            }
            FmNative.FmAudioThresholdParms write_para = new FmNative.FmAudioThresholdParms();
            write_para.hbound = hbound;
            write_para.lbound = lbound;
            write_para.power_th = power;
            write_para.phyt = (byte) phyt;
            write_para.snr_th = (byte) snr;
            Log.d(TAG, "write parameter:hbound=" + write_para.hbound + ";lhound=" + write_para.lbound + ";power=" + write_para.power_th + ";phyt=" + ((int) write_para.phyt) + ";snr=" + ((int) write_para.snr_th));
            FmNative.setAudioParm(write_para);
        }
    }
}
