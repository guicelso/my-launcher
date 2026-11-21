package com.sprd.engineermode.debuglog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/* JADX INFO: loaded from: classes4.dex */
public class CFTResultActivity extends Activity {
    private static final int ADCBYTES = 56;
    private static final String ADC_PATH = "/productinfo/adc.bin";
    private static final String TAG = "CFTResultActivity";
    private TextView txtViewlabel01;
    private String str = null;
    private Handler mUiHandler = new Handler();
    private DataInputStream mInputStream = null;
    byte[] buffer = new byte[56];
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();

    static /* synthetic */ String access$184(CFTResultActivity x0, Object x1) {
        String str = x0.str + x1;
        x0.str = str;
        return str;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctf_result);
        this.txtViewlabel01 = (TextView) findViewById(R.id.ctf_result);
        new Thread(new Runnable() { // from class: com.sprd.engineermode.debuglog.CFTResultActivity.1
            @Override // java.lang.Runnable
            public void run() {
                if (CFTResultActivity.this.teleApi.telephonyInfo().isSupportGsm() || CFTResultActivity.this.teleApi.telephonyInfo().isSupportTdscdma()) {
                    CFTResultActivity.this.str = "GSM/TD ";
                }
                try {
                    if (SystemPropertiesProxy.get("ro.product.board").contains("9620")) {
                        Log.d(CFTResultActivity.TAG, "This product is 9620");
                        String str1 = CFTResultActivity.this.teleApi.cftResult().getGsmTdScdmaCFTResult();
                        boolean isAdc = CFTResultActivity.this.readFile();
                        String[] str3 = str1.split("\n");
                        for (int i = 0; i < str3.length; i++) {
                            if (str3[i].contains("ADC")) {
                                Log.d(CFTResultActivity.TAG, "9620 has calibration bit");
                                String[] str4 = str3[i].split("\\:");
                                if (isAdc) {
                                    str3[i] = str4[0] + ":ADC calibrated Pass";
                                }
                            }
                            CFTResultActivity.access$184(CFTResultActivity.this, str3[i] + "\n");
                        }
                    } else {
                        Log.d(CFTResultActivity.TAG, "This product is not 9620");
                        CFTResultActivity.access$184(CFTResultActivity.this, CFTResultActivity.this.teleApi.cftResult().getGsmTdScdmaCFTResult());
                    }
                    if (CFTResultActivity.this.teleApi.telephonyInfo().isSupportWcdma()) {
                        CFTResultActivity.access$184(CFTResultActivity.this, "\nWCDMA ");
                        CFTResultActivity.access$184(CFTResultActivity.this, CFTResultActivity.this.teleApi.cftResult().getWcdmaCFTResult());
                    }
                    if (CFTResultActivity.this.teleApi.telephonyInfo().isSupportC2k()) {
                        CFTResultActivity.access$184(CFTResultActivity.this, "\nC2K ");
                        CFTResultActivity.access$184(CFTResultActivity.this, CFTResultActivity.this.teleApi.cftResult().getC2kCFTResult());
                    }
                    if (CFTResultActivity.this.teleApi.telephonyInfo().isSupportLte()) {
                        CFTResultActivity.access$184(CFTResultActivity.this, "\nLTE ");
                        CFTResultActivity.access$184(CFTResultActivity.this, CFTResultActivity.this.teleApi.cftResult().getLteCFTResult());
                    }
                    CFTResultActivity.this.mUiHandler.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.CFTResultActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            CFTResultActivity.this.txtViewlabel01.setText(CFTResultActivity.this.str);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean readFile() {
        try {
            File adcFile = new File(ADC_PATH);
            if (!adcFile.exists()) {
                Log.d(TAG, "adcFile do not exists");
                return false;
            }
            this.mInputStream = new DataInputStream(new FileInputStream(adcFile));
            int count = this.mInputStream.read(this.buffer, 0, 56);
            if (this.buffer != null && this.buffer.length > 0) {
                Log.d(TAG, "count = " + count + " size = " + this.buffer.length);
                int adcBit = this.buffer.length + (-4);
                int adcResult = this.buffer[adcBit] | (-4);
                Log.d(TAG, "adcBit = " + adcBit + " buffer[" + adcBit + "] = 0x" + Integer.toHexString(this.buffer[adcBit]) + " adcResult = 0x" + Integer.toHexString(adcResult));
                return adcResult == -1;
            }
            Log.d(TAG, "buffer == null or buffer.length <= 0");
            return false;
        } catch (Exception e) {
            Log.d(TAG, "Failed get outputStream: " + e);
            e.printStackTrace();
            return false;
        }
    }
}
