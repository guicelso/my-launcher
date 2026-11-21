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
public class FMRegModeActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "FMRegModeActivity";
    private static final String hintText = "00000000~ffffffff";
    private Context mContext;
    private EditText mFmAddress;
    private EditText mFmVal;
    private Button mReadRegBtn;
    private Button mWriteRegBtn;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_reg_mode);
        this.mContext = this;
        this.mFmAddress = (EditText) findViewById(R.id.fm_address_edit);
        this.mFmAddress.setHint(hintText);
        this.mFmVal = (EditText) findViewById(R.id.fm_reg_val_edit);
        this.mFmVal.setHint(hintText);
        this.mReadRegBtn = (Button) findViewById(R.id.read_reg_btn);
        this.mReadRegBtn.setOnClickListener(this);
        this.mWriteRegBtn = (Button) findViewById(R.id.write_reg_btn);
        this.mWriteRegBtn.setOnClickListener(this);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.read_reg_btn) {
            FmNative.FmRegCtlParms read_para = new FmNative.FmRegCtlParms();
            String read_address = this.mFmAddress.getText().toString();
            if (read_address == null || "".equals(read_address)) {
                Toast.makeText(this.mContext, "empty address!", 0).show();
                return;
            }
            if (read_address.length() == 8 && Integer.parseInt(read_address.substring(0, 1), 16) > 7) {
                Toast.makeText(this.mContext, "please input address between 0~7fffffff!", 0).show();
                return;
            }
            read_para.err = (byte) 0;
            read_para.addr = Integer.parseInt(read_address, 16);
            read_para.val = 0;
            read_para.rw_flag = (byte) 1;
            Log.d(TAG, "set parameter:address=" + read_para.addr + ";err=" + ((int) read_para.err) + ";value=" + read_para.val + ";flag=" + ((int) read_para.rw_flag));
            if (-1 == FmNative.readRegParm(read_para)) {
                Toast.makeText(this.mContext, "read reg fail!", 0).show();
                Log.d(TAG, "read reg fail!");
                return;
            }
            Log.d(TAG, "read parameter:address=" + read_para.addr + ";err=" + ((int) read_para.err) + ";value=" + read_para.val + ";flag=" + ((int) read_para.rw_flag));
            int i = read_para.addr;
            this.mFmVal.setText(Integer.toHexString(read_para.val));
            return;
        }
        if (id == R.id.write_reg_btn) {
            String address = this.mFmAddress.getText().toString();
            if (address == null || "".equals(address)) {
                Toast.makeText(this.mContext, "empty address!", 0).show();
                return;
            }
            if (address.length() == 8 && Integer.parseInt(address.substring(0, 1), 16) > 7) {
                Toast.makeText(this.mContext, "please input address between 0~7fffffff!", 0).show();
                return;
            }
            String value = this.mFmVal.getText().toString();
            if (value == null || "".equals(value)) {
                Toast.makeText(this.mContext, "empty value!", 0).show();
                return;
            }
            if (value.length() == 8 && Integer.parseInt(value.substring(0, 1), 16) > 7) {
                Toast.makeText(this.mContext, "please input value between 0~7fffffff!", 0).show();
                return;
            }
            Log.d(TAG, "address is:" + address + ";value is:" + value);
            FmNative.FmRegCtlParms write_para = new FmNative.FmRegCtlParms();
            write_para.addr = Integer.parseInt(address, 16);
            write_para.err = Byte.parseByte("0");
            write_para.val = Integer.parseInt(value, 16);
            write_para.rw_flag = Byte.parseByte("0");
            Log.d(TAG, "write parameter:address=" + write_para.addr + ";err=" + ((int) write_para.err) + ";value=" + write_para.val + ";flag=" + ((int) write_para.rw_flag));
            FmNative.writeRegParm(write_para);
        }
    }
}
