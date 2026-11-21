package com.sprd.engineermode.telephony.volte;

import android.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: loaded from: classes4.dex */
public class WhiteListActivity extends ListActivity {
    private static final String TAG = "WhiteListActivity";
    private ArrayList<String> fileNameList = new ArrayList<>();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showWhiteList();
    }

    private void showWhiteList() {
        String atRsp = IATUtils.sendATCmd(engconstents.ENG_AT_GET_WHITE_LIST, 0);
        Log.d(TAG, "sendATCmd mStrTmp= " + atRsp);
        try {
            if (atRsp.contains("OK")) {
                Toast.makeText(this, "Successful!", 0).show();
                String atRsp2 = atRsp.replaceAll("\"", "");
                if (atRsp2.contains(":")) {
                    String[] str = atRsp2.split(":");
                    Log.d(TAG, "ShowDeltanvFileList str.length: " + str.length);
                    if (str.length > 1) {
                        String[] str1 = str[1].split("\n")[0].split(",");
                        Collections.addAll(this.fileNameList, str1);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1, this.fileNameList);
                        setListAdapter(adapter);
                    }
                    return;
                }
                return;
            }
            Toast.makeText(this, atRsp, 0).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
