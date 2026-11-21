package com.sprd.engineermode.connectivity.fm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.android.fmradio.FmNative;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.utils.StorageUtils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes4.dex */
public class FMNoiseScanActivity extends Activity implements View.OnClickListener {
    private static final int LOOP = 1;
    private static final String TAG = "FMNoiseScanActivity";
    static DecimalFormat decimalFormat;
    private Context mContext;
    String mCurFreq;
    private EditText mDelayEdit;
    String mEndFreq;
    private EditText mEndFreqEdit;
    private ArrayList<NoiseScanResult> mResult;
    private FMNoiseScanAdapter mResultAdapter;
    private ListView mResultList;
    private Button mStart;
    private EditText mStartFreqEdit;
    private Button mStop;
    private static int LOOP_TIME = 1000;
    private static String FILE_NAME = "FMNoiseTest_";
    private Handler UiHandler = new Handler();
    String LogPath = "";
    private Process process = null;
    private DataInputStream is = null;
    private DataOutputStream os = null;
    private Handler handler = new Handler() { // from class: com.sprd.engineermode.connectivity.fm.FMNoiseScanActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                int rssi = FmNative.getRssi();
                int snr = FmNative.getSnr();
                Log.d(FMNoiseScanActivity.TAG, "fm rssi is:" + rssi);
                Log.d(FMNoiseScanActivity.TAG, "fm snr is:" + snr);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                Date curDate = new Date(System.currentTimeMillis());
                String cmd = "echo \"" + formatter.format(curDate) + "   " + FMNoiseScanActivity.this.mCurFreq + "   " + String.valueOf(-rssi) + "   " + String.valueOf(snr) + "\" >> " + FMNoiseScanActivity.this.LogPath + "\n";
                Log.d(FMNoiseScanActivity.TAG, "cmd is:" + cmd);
                FMNoiseScanActivity.this.executeCmd(cmd);
                NoiseScanResult mSingleRes = new NoiseScanResult(FMNoiseScanActivity.this.mCurFreq, String.valueOf(-rssi), String.valueOf(snr));
                FMNoiseScanActivity.this.mResultAdapter.result.add(mSingleRes);
                FMNoiseScanActivity.this.mResultAdapter.notifyDataSetChanged();
                FMNoiseScanActivity.this.mResultList.setAdapter((ListAdapter) FMNoiseScanActivity.this.mResultAdapter);
                FMNoiseScanActivity.this.mCurFreq = FMNoiseScanActivity.decimalFormat.format(((double) Float.valueOf(FMNoiseScanActivity.this.mCurFreq).floatValue()) + 0.1d);
                Log.d(FMNoiseScanActivity.TAG, "current freq is:" + FMNoiseScanActivity.this.mCurFreq);
                if (Float.valueOf(FMNoiseScanActivity.this.mEndFreq).floatValue() >= Float.valueOf(FMNoiseScanActivity.this.mCurFreq).floatValue()) {
                    EarphoneFragment.setFMPlayerRoute(EarphoneFragment.FM_MODE, FMNoiseScanActivity.this.mCurFreq);
                    Message m = obtainMessage(1);
                    sendMessageDelayed(m, FMNoiseScanActivity.LOOP_TIME);
                }
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_noise_scan);
        this.mContext = this;
        this.mStartFreqEdit = (EditText) findViewById(R.id.start_freq_edit);
        this.mEndFreqEdit = (EditText) findViewById(R.id.end_freq_edit);
        this.mDelayEdit = (EditText) findViewById(R.id.fm_delay_edit);
        this.mStart = (Button) findViewById(R.id.fm_noise_start);
        this.mStart.setOnClickListener(this);
        this.mStop = (Button) findViewById(R.id.fm_noise_stop);
        this.mStop.setOnClickListener(this);
        this.mStart.setEnabled(true);
        this.mStop.setEnabled(false);
        this.mResultList = (ListView) findViewById(R.id.noise_result_text);
        decimalFormat = new DecimalFormat(".0");
        this.mResult = new ArrayList<>();
        this.mResultAdapter = new FMNoiseScanAdapter(this, this.mResult);
        this.mResultList.setAdapter((ListAdapter) this.mResultAdapter);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fm_noise_start /* 2131296674 */:
                String delay = this.mDelayEdit.getText().toString().trim();
                if (delay == null || "".equals(delay)) {
                    Toast.makeText(this.mContext, "empty delay time!", 0).show();
                } else {
                    LOOP_TIME = Integer.valueOf(delay).intValue();
                    this.mCurFreq = this.mStartFreqEdit.getText().toString().trim();
                    this.mEndFreq = this.mEndFreqEdit.getText().toString().trim();
                    String str = this.mCurFreq;
                    if (str == null || "".equals(str)) {
                        Toast.makeText(this.mContext, "empty start freqency!", 0).show();
                    } else {
                        Pattern pattern = Pattern.compile("[0-9]{1,3}.{0,1}[0-9]{0,1}");
                        Matcher matcher = pattern.matcher(this.mCurFreq);
                        Log.d(TAG, "the match start freqency result is : " + matcher.matches());
                        if (!matcher.matches()) {
                            Toast.makeText(this.mContext, "input start freqency error!", 0).show();
                        } else {
                            String str2 = this.mEndFreq;
                            if (str2 == null || "".equals(str2)) {
                                Toast.makeText(this.mContext, "empty end freqency!", 0).show();
                            } else {
                                Pattern pattern2 = Pattern.compile("[0-9]{1,3}.{0,1}[0-9]{0,1}");
                                Matcher matcher2 = pattern2.matcher(this.mEndFreq);
                                Log.d(TAG, "the match end freqency result is : " + matcher2.matches());
                                if (!matcher2.matches()) {
                                    Toast.makeText(this.mContext, "input end freqency error!", 0).show();
                                } else if (Float.valueOf(this.mEndFreq).floatValue() < Float.valueOf(this.mCurFreq).floatValue()) {
                                    Toast.makeText(this.mContext, "end freqency should be greater than start freqency!", 0).show();
                                } else {
                                    EarphoneFragment.setFMPlayerRoute(EarphoneFragment.FM_MODE, this.mCurFreq);
                                    getLogPath();
                                    Log.d(TAG, "the log path is:" + this.LogPath);
                                    initProcess();
                                    String cmd = "echo \"        TIME          FREQ  RSSI  SNR\" >> " + this.LogPath + "\n";
                                    executeCmd(cmd);
                                    this.mResultAdapter.result.clear();
                                    Message m = this.handler.obtainMessage(1);
                                    this.handler.sendMessage(m);
                                    this.mStart.setEnabled(false);
                                    this.mStop.setEnabled(true);
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.fm_noise_stop /* 2131296675 */:
                this.handler.removeMessages(1);
                this.mStart.setEnabled(true);
                this.mStop.setEnabled(false);
                break;
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy...");
        this.handler.removeMessages(1);
        EarphoneFragment.setFMPlayerRoute(EarphoneFragment.FM_MODE, EarphoneFragment.mChannelValue);
    }

    private void getLogPath() {
        String dir_path;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String fileName = FILE_NAME + formatter.format(curDate);
        if (checkSDCard()) {
            dir_path = StorageUtils.getExternalStorage() + "/FMNoiseTest";
        } else {
            dir_path = StorageUtils.getInternalStorage().toString() + "/FMNoiseTest";
        }
        try {
            File destDir = new File(dir_path);
            if (!destDir.exists()) {
                destDir.mkdirs();
                String command = "chmod 777 " + dir_path;
                Log.d(TAG, command);
                Log.d(TAG, dir_path + " create success");
            }
        } catch (Exception e) {
            Log.d(TAG, dir_path + " create fail");
            Log.d(TAG, e.getMessage());
        }
        this.LogPath = dir_path + "/" + fileName;
    }

    protected boolean checkSDCard() {
        if (StorageUtils.getExternalStorageState()) {
            return true;
        }
        return false;
    }

    private void initProcess() {
        try {
            suCmd("echo start");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private DataInputStream suCmd(String cmd) {
        try {
            this.process = Runtime.getRuntime().exec("/system/bin/sh");
            this.os = new DataOutputStream(this.process.getOutputStream());
            this.is = new DataInputStream(this.process.getInputStream());
            this.os.writeBytes(cmd + "\n");
            this.os.flush();
            return this.is;
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error -:" + e.getMessage());
            return this.is;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeCmd(String cmd) {
        Log.d(TAG, "cmd is:" + cmd);
        try {
            this.os.writeBytes(cmd);
            this.os.flush();
        } catch (IOException e) {
            Log.d(TAG, e.toString());
            e.printStackTrace();
        }
    }
}
