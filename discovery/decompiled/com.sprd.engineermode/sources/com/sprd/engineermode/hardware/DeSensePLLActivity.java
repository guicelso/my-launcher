package com.sprd.engineermode.hardware;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ISensePll;

/* JADX INFO: loaded from: classes4.dex */
public class DeSensePLLActivity extends Activity {
    private static final String TAG = "DeSensePLLActivity/Activity";
    private ISensePll sensePllApi = CoreApi.getHardwareApi().sensePll();
    private EditText deSensePLLAddress = null;
    private EditText deSensePLLData = null;
    private EditText deSensePLLNumber = null;
    private TextView deSensePLLResult = null;
    private Button mRead = null;
    private Button mWrite = null;
    private int count = 0;
    private ButListener mButListener = new ButListener();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.de_sense_pll);
        this.deSensePLLAddress = (EditText) findViewById(R.id.de_sense_pll_address);
        this.deSensePLLData = (EditText) findViewById(R.id.de_sense_pll_data);
        this.deSensePLLNumber = (EditText) findViewById(R.id.de_sense_pll_number);
        this.deSensePLLResult = (TextView) findViewById(R.id.de_sense_pll_result);
        this.mRead = (Button) findViewById(R.id.de_sense_pll_read);
        this.mWrite = (Button) findViewById(R.id.de_sense_pll_write);
        this.mRead.setOnClickListener(this.mButListener);
        this.mWrite.setOnClickListener(this.mButListener);
        new AlertDialog.Builder(this).setTitle("Info").setMessage(getString(R.string.desense_pll_tips)).setPositiveButton(getString(R.string.alertdialog_ok), (DialogInterface.OnClickListener) null).show();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    private class ButListener implements View.OnClickListener {
        private ButListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.equals(DeSensePLLActivity.this.mRead)) {
                String address = DeSensePLLActivity.this.deSensePLLAddress.getText().toString();
                String number = DeSensePLLActivity.this.deSensePLLNumber.getText().toString();
                if (address.length() < 8) {
                    Toast.makeText(DeSensePLLActivity.this, "Please input 8 bit Address", 0).show();
                    return;
                }
                if ("00000000".equals(address)) {
                    Toast.makeText(DeSensePLLActivity.this, "Input Address Invalid", 0).show();
                    return;
                }
                if (number.length() != 0) {
                    try {
                        String result = DeSensePLLActivity.this.sensePllApi.read(address, Integer.parseInt(number));
                        DeSensePLLActivity.this.deSensePLLData.setText("");
                        DeSensePLLActivity.this.deSensePLLResult.setText(result);
                        Toast.makeText(DeSensePLLActivity.this, "Read Success", 0).show();
                        return;
                    } catch (OperationFailedException e) {
                        e.printStackTrace();
                        if (e.getCode() == ErrorCode.SOCKET_CONN_FAILED) {
                            Toast.makeText(DeSensePLLActivity.this, "connect socket failed", 0).show();
                            return;
                        } else {
                            if (e.getCode() == ErrorCode.CMD_EXEC_ERROR) {
                                DeSensePLLActivity.this.deSensePLLData.setText("");
                                DeSensePLLActivity.this.deSensePLLResult.setText("");
                                Toast.makeText(DeSensePLLActivity.this, "Read Fail", 0).show();
                                return;
                            }
                            return;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Toast.makeText(DeSensePLLActivity.this, "Read Fail", 0).show();
                        return;
                    }
                }
                Toast.makeText(DeSensePLLActivity.this, "Please input Number", 0).show();
                return;
            }
            if (view.equals(DeSensePLLActivity.this.mWrite)) {
                String address2 = DeSensePLLActivity.this.deSensePLLAddress.getText().toString();
                String data = DeSensePLLActivity.this.deSensePLLData.getText().toString();
                if (address2.length() < 8) {
                    Toast.makeText(DeSensePLLActivity.this, "Please input 8 bit Address", 0).show();
                    return;
                }
                if (data.length() != 0) {
                    try {
                        DeSensePLLActivity.this.sensePllApi.write(address2, data);
                        DeSensePLLActivity.this.deSensePLLResult.setText("");
                        Toast.makeText(DeSensePLLActivity.this, "Success", 0).show();
                        return;
                    } catch (OperationFailedException e3) {
                        e3.printStackTrace();
                        if (e3.getCode() == ErrorCode.SOCKET_CONN_FAILED) {
                            Toast.makeText(DeSensePLLActivity.this, "connect socket failed", 0).show();
                            return;
                        } else {
                            if (e3.getCode() == ErrorCode.CMD_EXEC_ERROR) {
                                DeSensePLLActivity.this.deSensePLLResult.setText("");
                                Toast.makeText(DeSensePLLActivity.this, "Write Fail", 0).show();
                                return;
                            }
                            return;
                        }
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        Toast.makeText(DeSensePLLActivity.this, "Write Fail", 0).show();
                        return;
                    }
                }
                Toast.makeText(DeSensePLLActivity.this, "Please input Data", 0).show();
            }
        }
    }
}
