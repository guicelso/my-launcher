package com.sprd.engineermode.gcf;

import android.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.hardware.PhaseCheckParse;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ManualDeltaNVActivity extends ListActivity {
    private static final String TAG = "ManualDeltaNVActivity";
    public static String[] keys = {"", ""};
    private int MCCStartPosition;
    private int combinentFileNameInt;
    private String combinentFileOffsetHex;
    private int combinentFileOffsetInt;
    private String combinentLengthHex;
    private int combinentLengthInt;
    private String combinentMCCHex;
    private int combinentMCCInt;
    private String combinentMNCHex;
    private int combinentMNCInt;
    private String combinentMNCStr;
    private int fileNameOffset1;
    private int fileNameOffset2;
    private int fileNameOffset3;
    private int fileNameOffset4;
    private String fileNameOffsetHex1;
    private String fileNameOffsetHex2;
    private String fileNameOffsetHex3;
    private String fileNameOffsetHex4;
    private boolean isRepeat;
    private String lengthHex1;
    private String lengthHex2;
    private String lengthHex3;
    private String lengthHex4;
    private int lengthInt1;
    private int lengthInt2;
    private int lengthInt3;
    private int lengthInt4;
    private String mATCmd;
    private String mAtCmd1;
    private Context mContext;
    private OperatorHandler mOperatorHandler;
    private String mStrTmp;
    private String mStrTmp1;
    private int mcc1;
    private int mcc2;
    private String mccHex1;
    private String mccHex2;
    private int mnc1;
    private int mnc2;
    private String mncHex1;
    private String mncHex2;
    private List<String> keyLists = new ArrayList();
    private RandomAccessFile file = null;
    private String s_FilePath = "/data/local/tmp/aaa";
    private char[] fileNameByte = new char[20];
    private String combinentFileNameHex = "";
    private ArrayList<String> FileNameList = new ArrayList<>();
    private ArrayList<String> MCCMNCList = new ArrayList<>();
    private ArrayList<String> MCCList = new ArrayList<>();
    private ArrayList<String> MNCList = new ArrayList<>();
    private int highestBit = 0;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mOperatorHandler = new OperatorHandler(ht.getLooper());
        int i = 0;
        while (true) {
            String[] strArr = keys;
            if (i < strArr.length) {
                this.keyLists.add(strArr[i]);
                i++;
            } else {
                Log.d(TAG, "keyLists.size = " + this.keyLists.size());
                showDeltanvFileList();
                return;
            }
        }
    }

    private void showDeltanvFileList() {
        String ret = IATUtils.sendATCmd(engconstents.ENG_AT_GET_DELTANV_NAME, "atchannel0");
        if (ret.contains("\"")) {
            String[] str = ret.split("\"");
            Log.d(TAG, "ShowDeltanvFileList str.length: " + str.length);
            if (str.length > 1) {
                String[] str1 = str[1].split(",");
                for (String str2 : str1) {
                    this.FileNameList.add(str2);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1, this.FileNameList);
                setListAdapter(adapter);
            }
        }
        Log.d(TAG, "ShowDeltanvFileList: " + ret);
    }

    private class LoadAsyncTask extends AsyncTask<Void, Integer, Integer> {
        private LoadAsyncTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Integer result) {
            Log.d(ManualDeltaNVActivity.TAG, "onPostExecute result =" + result);
            if (result.intValue() == 0) {
                Log.d(ManualDeltaNVActivity.TAG, "onPostExecute result = null");
                return;
            }
            ManualDeltaNVActivity manualDeltaNVActivity = ManualDeltaNVActivity.this;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(manualDeltaNVActivity, R.layout.simple_list_item_1, manualDeltaNVActivity.FileNameList);
            ManualDeltaNVActivity.this.setListAdapter(adapter);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Integer doInBackground(Void... params) {
            Log.d(ManualDeltaNVActivity.TAG, "doInBackground ");
            String result = new PhaseCheckParse().getDeltaNVInfo();
            int ret = ManualDeltaNVActivity.this.parseRelult(result);
            return ret <= 0 ? 0 : 1;
        }
    }

    @Override // android.app.ListActivity
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, "position = " + position);
        Message mOperator = this.mOperatorHandler.obtainMessage(position);
        this.mOperatorHandler.sendMessage(mOperator);
    }

    class OperatorHandler extends Handler {
        public OperatorHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int setPosition = msg.what;
            ManualDeltaNVActivity.this.mATCmd = "AT+SPOPRCFGNV=\"" + ((String) ManualDeltaNVActivity.this.FileNameList.get(setPosition)) + "\"";
            Log.d(ManualDeltaNVActivity.TAG, "setPosition = " + setPosition + " mATCmd: " + ManualDeltaNVActivity.this.mATCmd);
            ManualDeltaNVActivity manualDeltaNVActivity = ManualDeltaNVActivity.this;
            manualDeltaNVActivity.mStrTmp = IATUtils.sendATCmd(manualDeltaNVActivity.mATCmd, "atchannel0");
            Log.d(ManualDeltaNVActivity.TAG, "sendATCmd mStrTmp= " + ManualDeltaNVActivity.this.mStrTmp);
            if (ManualDeltaNVActivity.this.mStrTmp.contains("OK")) {
                Toast.makeText(ManualDeltaNVActivity.this, "Successful!", 0).show();
                ManualDeltaNVActivity.this.confirmToReboot();
            } else {
                ManualDeltaNVActivity manualDeltaNVActivity2 = ManualDeltaNVActivity.this;
                Toast.makeText(manualDeltaNVActivity2, manualDeltaNVActivity2.mStrTmp, 0).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmToReboot() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(com.sprd.engineermode.R.string.orange_remaining).setCancelable(false).setPositiveButton(com.sprd.engineermode.R.string.orange_remaining_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.gcf.ManualDeltaNVActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PowerManager pm = (PowerManager) ManualDeltaNVActivity.this.getSystemService("power");
                pm.reboot("test mode");
            }
        }).create();
        alertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int parseRelult(String results) {
        String[] operators = results.trim().split("\\|");
        Log.d(TAG, "parseRelult operators.length = " + operators.length);
        if (operators.length < 1) {
            return 0;
        }
        for (int i = 0; i < operators.length; i++) {
            if (!operators[i].trim().equals("")) {
                String[] strSplit = operators[i].split("\\:");
                if (strSplit.length > 1) {
                    this.FileNameList.add(strSplit[0].trim());
                    this.MCCMNCList.add(strSplit[1].trim());
                }
            }
        }
        return this.FileNameList.size();
    }

    private int readFromBinFile() {
        Log.d(TAG, "FileNameList.size() = " + this.FileNameList.size());
        try {
            if (!new File(this.s_FilePath).exists()) {
                Log.d(TAG, "init file not exist!");
            }
            this.file = new RandomAccessFile(this.s_FilePath, "r");
            this.file.seek(22L);
            this.lengthInt1 = this.file.readUnsignedByte();
            this.lengthHex1 = Integer.toHexString(this.lengthInt1);
            this.lengthInt2 = this.file.readUnsignedByte();
            this.lengthHex2 = Integer.toHexString(this.lengthInt2);
            this.lengthInt3 = this.file.readUnsignedByte();
            this.lengthHex3 = Integer.toHexString(this.lengthInt3);
            this.lengthInt4 = this.file.readUnsignedByte();
            this.lengthHex4 = Integer.toHexString(this.lengthInt4);
            this.combinentLengthHex = String.valueOf(this.lengthHex4) + String.valueOf(this.lengthHex3) + String.valueOf(this.lengthHex2) + String.valueOf(this.lengthHex1);
            this.combinentLengthInt = Integer.parseInt(this.combinentLengthHex, 16);
            Log.d(TAG, "readFromBin readByte combinentLengthHex:" + this.combinentLengthHex + " combinentLengthInt:" + this.combinentLengthInt);
            int n = 0;
            while (true) {
                this.MCCStartPosition = (n * 16) + 26;
                if (this.MCCStartPosition <= this.combinentLengthInt - 22) {
                    this.file.seek(this.MCCStartPosition);
                    this.mnc1 = this.file.readUnsignedByte();
                    this.mncHex1 = Integer.toHexString(this.mnc1);
                    this.mnc2 = this.file.readUnsignedByte();
                    this.mncHex2 = Integer.toHexString(this.mnc2);
                    Log.d(TAG, "readFromBin readByte mncHex1:" + this.mncHex1 + " mncHex2:" + this.mncHex2);
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(this.mncHex2));
                    sb.append(String.valueOf(this.mncHex1));
                    this.combinentMNCHex = sb.toString();
                    this.combinentMNCHex = this.combinentMNCHex.substring(1, this.combinentMNCHex.length());
                    this.combinentMNCInt = Integer.parseInt(this.combinentMNCHex, 16);
                    this.combinentMNCStr = String.valueOf(this.combinentMNCInt);
                    if (this.mncHex2.toLowerCase().startsWith("8") || this.mncHex2.toLowerCase().startsWith("c") || this.mncHex2.toLowerCase().startsWith("e") || this.mncHex2.toLowerCase().startsWith("f")) {
                        Log.d(TAG, " mncHex2:" + this.mncHex2 + " High bit is 1");
                        if (this.combinentMNCStr.length() == 1) {
                            this.combinentMNCStr = "00" + this.combinentMNCStr;
                        } else if (this.combinentMNCStr.length() == 2) {
                            this.combinentMNCStr = "0" + this.combinentMNCStr;
                        }
                    } else if (this.combinentMNCStr.length() == 1) {
                        this.combinentMNCStr = "0" + this.combinentMNCStr;
                    }
                    Log.d(TAG, "readFromBin readByte combinentMNCHex:" + this.combinentMNCHex + " MNC:" + this.combinentMNCStr);
                    this.mcc1 = this.file.readUnsignedByte();
                    this.mccHex1 = Integer.toHexString(this.mcc1);
                    this.mcc2 = this.file.readUnsignedByte();
                    this.mccHex2 = Integer.toHexString(this.mcc2);
                    this.combinentMCCHex = String.valueOf(this.mccHex2) + String.valueOf(this.mccHex1);
                    this.combinentMCCInt = Integer.parseInt(this.combinentMCCHex, 16);
                    Log.d(TAG, "readFromBin readByte combinentMCCHex:" + this.combinentMCCHex + " MCC:" + this.combinentMCCInt);
                    this.fileNameOffset1 = this.file.readUnsignedByte();
                    this.fileNameOffsetHex1 = Integer.toHexString(this.fileNameOffset1);
                    this.fileNameOffset2 = this.file.readUnsignedByte();
                    this.fileNameOffsetHex2 = Integer.toHexString(this.fileNameOffset2);
                    this.fileNameOffset3 = this.file.readUnsignedByte();
                    this.fileNameOffsetHex3 = Integer.toHexString(this.fileNameOffset3);
                    this.fileNameOffset4 = this.file.readUnsignedByte();
                    this.fileNameOffsetHex4 = Integer.toHexString(this.fileNameOffset4);
                    this.combinentFileOffsetHex = String.valueOf(this.fileNameOffsetHex4) + String.valueOf(this.fileNameOffsetHex3) + String.valueOf(this.fileNameOffsetHex2) + String.valueOf(this.fileNameOffsetHex1);
                    this.combinentFileOffsetInt = Integer.parseInt(this.combinentFileOffsetHex, 16);
                    this.file.seek((long) this.combinentFileOffsetInt);
                    for (int i = 0; i < 20; i++) {
                        int a = this.file.readUnsignedByte();
                        this.fileNameByte[i] = (char) a;
                    }
                    this.combinentFileNameHex = String.valueOf(this.fileNameByte).trim();
                    this.isRepeat = false;
                    for (int i2 = 0; i2 < this.FileNameList.size(); i2++) {
                        if (this.combinentFileNameHex.equals(this.FileNameList.get(i2))) {
                            this.isRepeat = true;
                            break;
                        }
                    }
                    if (!this.isRepeat) {
                        this.MCCList.add(String.valueOf(this.combinentMCCInt));
                        this.MNCList.add(this.combinentMNCStr);
                        this.FileNameList.add(this.combinentFileNameHex);
                    }
                    this.combinentFileNameHex = "";
                    n++;
                } else {
                    return this.FileNameList.size();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
