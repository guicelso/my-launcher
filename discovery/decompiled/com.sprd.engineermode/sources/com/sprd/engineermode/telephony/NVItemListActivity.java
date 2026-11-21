package com.sprd.engineermode.telephony;

import android.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.SimpleAdapter;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.impl.nonpublic.ImsManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class NVItemListActivity extends ListActivity {
    private static final String ATError = "ERROR";
    private static final String ATFailRes = "AT cmd send fail";
    private static final String AT_AGPS_QOS_TIME = "AT+SPTEST=26";
    private static final String AT_CA = "AT+SPCAPABILITY=53,0";
    private static final String AT_COPS = "AT+COPS?";
    private static final String AT_LTE_BAND = "AT+SPBANDCTRL=0,5";
    private static final String AT_LTE_CATEGORY = "AT+SPUECAT=0";
    private static final String AT_SMS_BEARER = "AT+CGSMS?";
    private static final String AT_SPENGMD007 = "AT+SPENGMD=0,0,7";
    private static final String AT_SPENGMD008 = "AT+SPENGMD=0,0,8";
    private static final String AT_SYSCONFIG = "AT^SYSCONFIG?";
    private static final String AT_VOLTE_SUPPOERTED = "AT+CAVIMS?";
    static final Map COPS_MODE;
    private static final int LOAD_ITEMS = 1;
    private static final int REFRASH_SCREEN = 1;
    static final Map SMS_SERVICE_TYPE;
    static final Map SPENGMD007_CPC;
    static final Map SPENGMD007_VAMOS;
    static final Map SPENGMD007_WB_AMR;
    static final Map SPENGMD008_DC_HSDPA;
    static final Map SPENGMD008_DC_HSUPA;
    static final Map SPENGMD008_DTM;
    static final Map SPENGMD008_EDRX;
    static final Map SPENGMD008_EPCH;
    static final Map SPENGMD008_ERACH;
    static final Map SPENGMD008_E_FDPCH;
    static final Map SPENGMD008_HSUPA_CATEGORY;
    static final Map SPENGMD008_MAC_I;
    static final Map SPENGMD008_RECEIVER_TYPE;
    static final Map SPENGMD008_RX_DIVERSITY;
    static final Map SPENGMD008_SRBOHS;
    static final Map SPENGMD008_UE_FACH;
    static final Map SPENGMD008_UMTS_CIPHER;
    static final Map SPENGMD008_UMTS_INTEGRITY;
    static final Map SPENGMD008_WCDMA_VERSION;
    private static final String SPLIT = "\n|: |,";
    static final Map SYSCONFIG_ACQORDER;
    static final Map SYSCONFIG_SRVDOMAIM;
    private static final String TAG = "NVItemList";
    private BGHandler mBGHandler;
    private int mPhoneCount;
    private ProgressDialog mProgressDialog;
    private Handler mUiThread = new Handler();
    private List<Map<String, Object>> myData;
    private static final String[] mItemNamesList = {"Network mode", "GSM A5 algorithms", "Network preference", "Network select mode", "Network domain", "UMTS integrity", "UMTS ciphering", "GPRS/GEA algorithms", "DTM", "3G WB AMR", "E-FACH", "E-RACH", "eDRX", "E-PCH", "MAC_I", "CPC", "E-FDPCH", "SRBoHS", "Fast dormancy", "DC-HSDPA", "DC-HSUPA", "Rx diversity", "HSUPA category", "WCDMA version", "Receiver type 3i", "VAMOS", "LTE Category", "LTE Band", "VoLTE", "SMS Bearer", "SMS retry interval", "AGPS QoS Timeout"};
    static final Map SYSCONFIG_MODE = new HashMap();

    static {
        SYSCONFIG_MODE.put("2", "Auto");
        SYSCONFIG_MODE.put("12", "4G only");
        SYSCONFIG_MODE.put("13", "2G only");
        SYSCONFIG_MODE.put("14", "3G only");
        SYSCONFIG_MODE.put("15", "3G only");
        SYSCONFIG_MODE.put("17", "4G preferred");
        SYSCONFIG_ACQORDER = new HashMap();
        SYSCONFIG_ACQORDER.put("0", "Auto");
        SYSCONFIG_ACQORDER.put("1", "2G preferred");
        SYSCONFIG_ACQORDER.put("2", "3G preferred");
        SYSCONFIG_ACQORDER.put("3", "4G");
        SYSCONFIG_ACQORDER.put("4", "2G preferred");
        SYSCONFIG_ACQORDER.put("5", "4G preferred");
        SYSCONFIG_ACQORDER.put("6", "2G/3G preferred");
        SYSCONFIG_SRVDOMAIM = new HashMap();
        SYSCONFIG_SRVDOMAIM.put("0", "CS_only");
        SYSCONFIG_SRVDOMAIM.put("1", "PS_only");
        SYSCONFIG_SRVDOMAIM.put("2", "CS and PS");
        SYSCONFIG_SRVDOMAIM.put("3", "Any");
        COPS_MODE = new HashMap();
        COPS_MODE.put("0", "Auto");
        COPS_MODE.put("1", "Manual");
        SPENGMD007_WB_AMR = new HashMap();
        SPENGMD007_WB_AMR.put("0", "Not support");
        SPENGMD007_WB_AMR.put("1", "Support");
        SPENGMD007_VAMOS = new HashMap();
        SPENGMD007_VAMOS.put("0", "Not support");
        SPENGMD007_VAMOS.put("1", "Support");
        SPENGMD007_VAMOS.put("2", "Support");
        SPENGMD007_CPC = new HashMap();
        SPENGMD007_CPC.put("0", "Not support");
        SPENGMD007_CPC.put("1", "Support");
        SPENGMD008_UMTS_INTEGRITY = new HashMap();
        SPENGMD008_UMTS_INTEGRITY.put("0", "Not support");
        SPENGMD008_UMTS_INTEGRITY.put("1", "UIA0");
        SPENGMD008_UMTS_INTEGRITY.put("2", "UIA1");
        SPENGMD008_UMTS_INTEGRITY.put("3", "UIA0, UIA1");
        SPENGMD008_UMTS_INTEGRITY.put("6", "UIA1, UIA2");
        SPENGMD008_DTM = new HashMap();
        SPENGMD008_DTM.put("0", "Not support");
        SPENGMD008_DTM.put("1", "Support");
        SPENGMD008_ERACH = new HashMap();
        SPENGMD008_ERACH.put("0", "Not support");
        SPENGMD008_ERACH.put("1", "Support");
        SPENGMD008_EDRX = new HashMap();
        SPENGMD008_EDRX.put("0", "Not support");
        SPENGMD008_EDRX.put("1", "Support");
        SPENGMD008_EPCH = new HashMap();
        SPENGMD008_EPCH.put("0", "Not support");
        SPENGMD008_EPCH.put("1", "Support");
        SPENGMD008_MAC_I = new HashMap();
        SPENGMD008_MAC_I.put("0", "Not support");
        SPENGMD008_MAC_I.put("1", "Support");
        SPENGMD008_E_FDPCH = new HashMap();
        SPENGMD008_E_FDPCH.put("0", "Not support");
        SPENGMD008_E_FDPCH.put("1", "Support");
        SPENGMD008_SRBOHS = new HashMap();
        SPENGMD008_SRBOHS.put("0", "Not support");
        SPENGMD008_SRBOHS.put("1", "Support");
        SPENGMD008_RX_DIVERSITY = new HashMap();
        SPENGMD008_RX_DIVERSITY.put("0", "Not support");
        SPENGMD008_RX_DIVERSITY.put("1", "Support");
        SPENGMD008_HSUPA_CATEGORY = new HashMap();
        SPENGMD008_HSUPA_CATEGORY.put("5", "Cat 5");
        SPENGMD008_HSUPA_CATEGORY.put("6", "Cat 6");
        SPENGMD008_WCDMA_VERSION = new HashMap();
        SPENGMD008_WCDMA_VERSION.put("0", "R4");
        SPENGMD008_WCDMA_VERSION.put("1", "R5");
        SPENGMD008_WCDMA_VERSION.put("2", "R6");
        SPENGMD008_WCDMA_VERSION.put("3", "R7");
        SPENGMD008_WCDMA_VERSION.put("4", "R8");
        SPENGMD008_WCDMA_VERSION.put("5", "R9");
        SPENGMD008_RECEIVER_TYPE = new HashMap();
        SPENGMD008_RECEIVER_TYPE.put("0", "Not support");
        SPENGMD008_RECEIVER_TYPE.put("1", "Support");
        SPENGMD008_DC_HSDPA = new HashMap();
        SPENGMD008_DC_HSDPA.put("0", "Not support");
        SPENGMD008_DC_HSDPA.put("1", "Support");
        SPENGMD008_DC_HSUPA = new HashMap();
        SPENGMD008_DC_HSUPA.put("0", "Not support");
        SPENGMD008_DC_HSUPA.put("1", "Support");
        SPENGMD008_UMTS_CIPHER = new HashMap();
        SPENGMD008_UMTS_CIPHER.put("1", "UEA0");
        SPENGMD008_UMTS_CIPHER.put("2", "UEA1");
        SPENGMD008_UMTS_CIPHER.put("3", "UEA0,UEA1");
        SPENGMD008_UMTS_CIPHER.put("7", "UEA0,UEA1,UEA2");
        SPENGMD008_UE_FACH = new HashMap();
        SPENGMD008_UE_FACH.put("0", "Not support");
        SPENGMD008_UE_FACH.put("1", "Support");
        SMS_SERVICE_TYPE = new HashMap();
        SMS_SERVICE_TYPE.put("0", "PS");
        SMS_SERVICE_TYPE.put("1", "CS");
        SMS_SERVICE_TYPE.put("2", "PS preferred");
        SMS_SERVICE_TYPE.put("3", "CS preferred");
        SMS_SERVICE_TYPE.put("4", "No service");
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TelephonyManagerProxy telephonyManagerProxy = TelephonyManagerProxy.INSTANCE;
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.myData = new ArrayList();
        HandlerThread thread = new HandlerThread(TAG);
        thread.start();
        this.mBGHandler = new BGHandler(thread.getLooper());
        setListAdapter(new SimpleAdapter(this, this.myData, R.layout.simple_list_item_1, new String[]{"title"}, new int[]{R.id.text1}));
        getListView().setTextFilterEnabled(true);
        this.mBGHandler.sendEmptyMessage(1);
    }

    @Override // android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        BGHandler bGHandler = this.mBGHandler;
        if (bGHandler != null) {
            bGHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSysConfig() {
        String outstr;
        String outstr2;
        String outstr3;
        String atresult = AT_CMD("AT^SYSCONFIG?", 0);
        String[] sysconfig = atresult.split(SPLIT);
        String atresult1 = AT_CMD("AT^SYSCONFIG?", 1);
        String[] sysconfig1 = atresult1.split(SPLIT);
        if (sysconfig.length < 5 || sysconfig1.length < 5) {
            addItem("Parse error", "AT^SYSCONFIG?");
            return;
        }
        Log.d(TAG, "sysconfig:" + Arrays.toString(sysconfig));
        Log.d(TAG, "sysconfig1:" + Arrays.toString(sysconfig1));
        if (this.mPhoneCount <= 1) {
            outstr = getval(SYSCONFIG_MODE, sysconfig[1]);
        } else {
            outstr = "\nSIM1(" + getval(SYSCONFIG_MODE, sysconfig[1]) + "), \nSIM2(" + getval(SYSCONFIG_MODE, sysconfig1[1]) + ")";
        }
        addItem(mItemNamesList[0], outstr);
        if (this.mPhoneCount > 1) {
            outstr2 = "\nSIM1(" + getval(SYSCONFIG_ACQORDER, sysconfig[2]) + "), \nSIM2(" + getval(SYSCONFIG_ACQORDER, sysconfig1[2]) + ")";
        } else {
            outstr2 = getval(SYSCONFIG_ACQORDER, sysconfig[2]);
        }
        addItem(mItemNamesList[2], outstr2);
        if (this.mPhoneCount > 1) {
            outstr3 = "\nSIM1(" + getval(SYSCONFIG_SRVDOMAIM, sysconfig[4]) + "), \nSIM2(" + getval(SYSCONFIG_SRVDOMAIM, sysconfig1[4]) + ")";
        } else {
            outstr3 = getval(SYSCONFIG_SRVDOMAIM, sysconfig[4]);
        }
        addItem(mItemNamesList[4], outstr3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processCops() {
        String outstr;
        String atresult = AT_CMD("AT+COPS?", 0);
        String[] cops = atresult.split(SPLIT);
        String atresult1 = AT_CMD("AT+COPS?", 1);
        String[] cops1 = atresult1.split(SPLIT);
        if (cops.length < 2 || cops1.length < 2) {
            addItem("Parse error", "AT+COPS?");
            return;
        }
        Log.d(TAG, "cops:" + Arrays.toString(cops));
        Log.d(TAG, "cops1:" + Arrays.toString(cops1));
        if (this.mPhoneCount <= 1) {
            outstr = getval(COPS_MODE, cops[1]);
        } else {
            outstr = "\nSIM1(" + getval(COPS_MODE, cops[1]) + "), \nSIM2(" + getval(COPS_MODE, cops1[1]) + ")";
        }
        addItem(mItemNamesList[3], outstr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSpengmd007() {
        String atresult = AT_CMD("AT+SPENGMD=0,0,7", 0);
        String[] spengmd = atresult.split("-|\n");
        if (spengmd.length < 15) {
            addItem("Parse error", "AT+SPENGMD=0,0,7");
            return;
        }
        Log.d(TAG, "spengmd:" + Arrays.toString(spengmd));
        addItem(mItemNamesList[9], getval(SPENGMD007_WB_AMR, spengmd[0].substring(0, 1)));
        addItem(mItemNamesList[25], getval(SPENGMD007_VAMOS, spengmd[7].substring(0, 1)));
        addItem(mItemNamesList[15], getval(SPENGMD007_CPC, spengmd[14].substring(0, 1)));
        StringBuffer sb = new StringBuffer();
        int n = Integer.parseInt(spengmd[3].substring(0, 1));
        for (int i = 0; i < 7; i++) {
            if (((n >> i) & 1) == 1) {
                if (!sb.toString().isEmpty()) {
                    sb.append(", ");
                }
                sb.append("a5/" + (i + 1));
            }
        }
        addItem(mItemNamesList[1], sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSpengmd008() {
        String atresult = AT_CMD("AT+SPENGMD=0,0,8", 0);
        String[] temp = atresult.split("\n");
        String[] spengmd = temp[0].split("-");
        Log.d(TAG, "spengmd:" + Arrays.toString(spengmd));
        if (spengmd.length < 18) {
            addItem("Parse error", "AT+SPENGMD=0,0,8");
            return;
        }
        addItem(mItemNamesList[5], getval(SPENGMD008_UMTS_INTEGRITY, spengmd[0].substring(0, 1)));
        addItem(mItemNamesList[8], getval(SPENGMD008_DTM, spengmd[1].substring(0, 1)));
        addItem(mItemNamesList[11], getval(SPENGMD008_ERACH, spengmd[2].substring(0, 1)));
        addItem(mItemNamesList[12], getval(SPENGMD008_EDRX, spengmd[3].substring(0, 1)));
        addItem(mItemNamesList[13], getval(SPENGMD008_EPCH, spengmd[4].substring(0, 1)));
        addItem(mItemNamesList[14], getval(SPENGMD008_MAC_I, spengmd[5].substring(0, 1)));
        addItem(mItemNamesList[16], getval(SPENGMD008_E_FDPCH, spengmd[6].substring(0, 1)));
        addItem(mItemNamesList[17], getval(SPENGMD008_SRBOHS, spengmd[7].substring(0, 1)));
        if (spengmd[8].substring(0, 1).equals("255")) {
            addItem(mItemNamesList[18], "Not support");
        } else {
            addItem(mItemNamesList[18], "Support");
        }
        addItem(mItemNamesList[21], getval(SPENGMD008_RX_DIVERSITY, spengmd[9].substring(0, 1)));
        addItem(mItemNamesList[22], getval(SPENGMD008_HSUPA_CATEGORY, spengmd[10].substring(0, 1)));
        addItem(mItemNamesList[23], getval(SPENGMD008_WCDMA_VERSION, spengmd[11].substring(0, 1)));
        addItem(mItemNamesList[24], getval(SPENGMD008_RECEIVER_TYPE, spengmd[12].substring(0, 1)));
        addItem(mItemNamesList[19], getval(SPENGMD008_DC_HSDPA, spengmd[13].substring(spengmd[13].length() - 1)));
        addItem(mItemNamesList[20], getval(SPENGMD008_DC_HSUPA, spengmd[14].substring(spengmd[14].length() - 1)));
        StringBuffer sb = new StringBuffer();
        String[] geaList = spengmd[15].split(",");
        for (int i = 0; i < geaList.length; i++) {
            if (geaList[i].equals("1")) {
                if (!sb.toString().isEmpty()) {
                    sb.append(", ");
                }
                sb.append("GEA" + (i + 1));
            }
        }
        addItem(mItemNamesList[7], sb.toString());
        addItem(mItemNamesList[6], getval(SPENGMD008_UMTS_CIPHER, spengmd[16].substring(0, 1)));
        addItem(mItemNamesList[10], getval(SPENGMD008_UE_FACH, spengmd[17].substring(0, 1)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSpengmdltecategory() {
        if (Const.isWPlusG()) {
            addItem(mItemNamesList[26], "Not support");
            return;
        }
        String atresult = AT_CMD("AT+SPUECAT=0", 0);
        Log.d(TAG, "atresult check AT_LTE_CATEGORY: " + atresult);
        if (atresult.isEmpty()) {
            addItem("Parse error", "AT+SPUECAT=0");
            return;
        }
        if (atresult.contains(ATFailRes)) {
            addItem(mItemNamesList[26], "UNKNOWN");
            return;
        }
        if (atresult.contains("ERROR")) {
            addItem(mItemNamesList[26], "Not support");
            return;
        }
        String[] temp = atresult.split("\n");
        String[] volte_category = temp[0].split(":");
        if (volte_category.length < 2) {
            if ("OK".equals(volte_category[0].trim())) {
                addItem(mItemNamesList[26], "Not support");
                return;
            } else {
                addItem("Parse error", "AT+SPUECAT=0");
                return;
            }
        }
        Log.d(TAG, "volte_category:" + Arrays.toString(volte_category));
        addItem(mItemNamesList[26], volte_category[1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSpengmdlteband() {
        if (Const.isWPlusG()) {
            addItem(mItemNamesList[27], "Not support");
            return;
        }
        String atresult = AT_CMD("AT+SPBANDCTRL=0,5", 0);
        Log.d(TAG, "atresult check AT_LTE_BAND: " + atresult);
        if (atresult == null) {
            addItem("Parse error", "AT+SPBANDCTRL=0,5");
            return;
        }
        if (atresult.contains(ATFailRes)) {
            addItem(mItemNamesList[27], "UNKNOWN");
            return;
        }
        if (atresult.contains("ERROR")) {
            addItem(mItemNamesList[27], "Not support");
            return;
        }
        String[] temp = atresult.split("\n");
        String[] volte_band = temp[0].split(":");
        if (volte_band.length < 2) {
            return;
        }
        String[] volte_band1 = volte_band[1].split(",");
        int n = volte_band1.length;
        Log.d(TAG, "volte_band1" + volte_band1[0] + "volte_band1.length" + n);
        StringBuffer sb = new StringBuffer();
        if (!"5".equals(volte_band1[0].trim()) || n < 1) {
            addItem("Parse error", "AT+SPBANDCTRL=0,5");
            return;
        }
        if (n == 1) {
            addItem(mItemNamesList[27], "Not support");
            return;
        }
        for (int i = 1; i < n; i++) {
            if (!volte_band1[i].isEmpty()) {
                if (!sb.toString().isEmpty()) {
                    sb.append(", ");
                }
                sb.append(volte_band1[i]);
            }
        }
        addItem(mItemNamesList[27], sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSpengmdvolte() {
        String atresult = AT_CMD("AT+CAVIMS?", 0);
        Log.d(TAG, "atresult check AT_VOLTE_SUPPOERTED: " + atresult);
        if (atresult.isEmpty()) {
            addItem("Parse error", "AT+CAVIMS?");
            return;
        }
        if (atresult.contains(ATFailRes)) {
            addItem(mItemNamesList[28], "UNKNOWN");
            return;
        }
        String[] temp = atresult.split("\n");
        String[] volte_supported = temp[0].split(":");
        String atresult1 = AT_CMD("AT+CAVIMS?", 1);
        Log.d(TAG, "atresult1 check AT_VOLTE_SUPPOERTED: " + atresult1);
        if (atresult1.isEmpty()) {
            addItem("Parse error", "AT+CAVIMS?");
            return;
        }
        String[] temp1 = atresult1.split("\n");
        String[] volte_supported1 = temp1[0].split(":");
        if (volte_supported.length < 2 || volte_supported1.length < 2) {
            if ("OK".equals(volte_supported[0].trim()) && "OK".equals(volte_supported1[0].trim())) {
                addItem(mItemNamesList[28], "Not support");
                return;
            } else {
                addItem("Parse error", "AT+CAVIMS?");
                return;
            }
        }
        Log.d(TAG, "volte_supported:" + Arrays.toString(volte_supported));
        Log.d(TAG, "volte_supported1:" + Arrays.toString(volte_supported1));
        if (volte_supported[1].contains("1") || volte_supported1[1].contains("1")) {
            addItem(mItemNamesList[28], "Support");
        } else {
            addItem(mItemNamesList[28], "Not support");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSMSBearer() {
        String atresult = AT_CMD("AT+CGSMS?", 0);
        Log.d(TAG, "atresult check AT_SMS_BEARER: " + atresult);
        if (atresult.isEmpty()) {
            addItem("Parse error", "AT+CGSMS?");
            return;
        }
        if (atresult.contains(ATFailRes)) {
            addItem(mItemNamesList[29], "UNKNOWN");
            return;
        }
        String[] temp = atresult.split("\n");
        String[] temp1 = temp[0].split(":");
        String sms_bearer = temp1[1].trim();
        Log.d(TAG, "sms_bearer: " + sms_bearer);
        Log.d(TAG, "getval:" + getval(SMS_SERVICE_TYPE, sms_bearer));
        addItem(mItemNamesList[29], getval(SMS_SERVICE_TYPE, sms_bearer));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSMSBRetryInterval() {
        addItem(mItemNamesList[30], "0 ms");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processAGPSQosTime() {
        Log.d(TAG, " processAGPSQosTime()");
        String atresult = AT_CMD("AT+SPTEST=26", 0);
        Log.d(TAG, "atresult check AT_AGPS_QOS_TIME: " + atresult);
        if (atresult.isEmpty()) {
            addItem("Parse error", "AT+SPTEST=26");
            return;
        }
        if (atresult.contains(ATFailRes)) {
            addItem(mItemNamesList[31], "UNKNOWN");
            return;
        }
        String[] temp = atresult.split("\n");
        String[] temp1 = temp[0].split(":");
        String[] agps_qostime = temp1[1].split(",");
        Log.d(TAG, "agps_qostime[1]: " + agps_qostime[1]);
        addItem(mItemNamesList[31], agps_qostime[1] + " s");
    }

    private String getval(Map map, String key) {
        if (map == SPENGMD008_HSUPA_CATEGORY) {
            return "Cat " + key;
        }
        String val = (String) map.get(key.trim());
        if (val == null) {
            return "Unknow";
        }
        return val;
    }

    protected void addItem(String item, String value) {
        Map<String, Object> temp = new HashMap<>();
        temp.put("title", item + ": " + value);
        this.myData.add(temp);
    }

    private String AT_CMD(String cmd, int channelid) {
        String res = IATUtils.sendATCmd(cmd, "atchannel" + channelid);
        Log.d(TAG, "AT_CMD[" + channelid + "] >> " + cmd);
        Log.d(TAG, "AT_CMD[" + channelid + "] << " + res);
        return res;
    }

    private void showProgressDialog() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.NVItemListActivity.1
            @Override // java.lang.Runnable
            public void run() {
                NVItemListActivity nVItemListActivity = NVItemListActivity.this;
                nVItemListActivity.mProgressDialog = ProgressDialog.show(nVItemListActivity, "Loading...", "Please wait...", true, false);
            }
        });
    }

    private void dismissProgressDialog() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.NVItemListActivity.2
            @Override // java.lang.Runnable
            public void run() {
                if (NVItemListActivity.this.mProgressDialog != null && NVItemListActivity.this.mProgressDialog.isShowing()) {
                    NVItemListActivity.this.mProgressDialog.dismiss();
                }
            }
        });
    }

    private void showErrorDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(TAG).setMessage(message).setPositiveButton(com.sprd.engineermode.R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.NVItemListActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        alertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processVoWifi() throws IllegalAccessException, InvocationTargetException {
        boolean voWifiSupport = ImsManagerProxy.INSTANCE.isWfcEnabledByPlatform(this);
        Log.d(TAG, "voWifiSupport = " + voWifiSupport);
        addItem("VoWifi", voWifiSupport ? "Support" : "Not Support");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processCA() {
        String atResult;
        char c;
        int i;
        Log.d(TAG, " processCA");
        String atResult2 = AT_CMD(AT_CA, 0);
        Log.d(TAG, "atresult : " + atResult2);
        String[] results = atResult2.split("\n");
        CAData[] CADatas = new CAData[4];
        CADatas[0] = null;
        int i2 = 1;
        CADatas[1] = null;
        int i3 = 2;
        CADatas[2] = null;
        char c2 = 3;
        CADatas[3] = null;
        List<CAData> CADataList = new ArrayList<>();
        for (int i4 = 2; i4 <= 5; i4++) {
            CADatas[i4 - 2] = new CAData(i4);
            CADataList.add(CADatas[i4 - 2]);
        }
        if (atResult2.contains("OK")) {
            int i5 = 0;
            while (i5 < results.length - i2) {
                String[] temp = results[i5].split(":");
                String cc = temp[i2].trim();
                String[] data = cc.split(",");
                String type = data[i2];
                int numOfBandComb = Integer.parseInt(data[i3]);
                int numOfBand = Integer.parseInt(data[c2]);
                if (numOfBandComb < i3) {
                    atResult = atResult2;
                    c = 4;
                    i = 1;
                } else if (numOfBandComb > 5) {
                    atResult = atResult2;
                    c = 4;
                    i = 1;
                } else {
                    CAData caData = CADataList.get(numOfBandComb - 2);
                    StringBuilder bands = new StringBuilder();
                    int j = 0;
                    while (j < numOfBand) {
                        int numOfBandComb2 = numOfBandComb;
                        String atResult3 = atResult2;
                        StringBuilder bands2 = bands;
                        bands2.append(data[(j * 2) + 4]);
                        bands2.append((char) (Integer.parseInt(data[(j * 2) + 4 + 1]) + 65));
                        if (j < numOfBand - 1) {
                            bands2.append("-");
                        }
                        j++;
                        bands = bands2;
                        atResult2 = atResult3;
                        numOfBandComb = numOfBandComb2;
                    }
                    atResult = atResult2;
                    c = 4;
                    i = 1;
                    int j2 = Integer.parseInt(type);
                    caData.add(j2, bands.toString());
                }
                i5++;
                i2 = i;
                atResult2 = atResult;
                results = results;
                i3 = 2;
                c2 = 3;
            }
        }
        for (int k = 0; k < CADataList.size(); k++) {
            addItem("[" + CADataList.get(k).getId() + "CC]", CADataList.get(k).toString());
        }
    }

    private static class CAData {
        int id;
        StringBuilder up = null;
        StringBuilder down = null;

        CAData(int id) {
            this.id = 0;
            this.id = id;
        }

        void add(int type, String value) {
            if (type == 0) {
                StringBuilder sb = this.down;
                if (sb == null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("         Downlink: ");
                    this.down = sb2;
                } else {
                    sb.append(", ");
                }
                this.down.append(value);
                return;
            }
            if (type == 1) {
                StringBuilder sb3 = this.up;
                if (sb3 == null) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("         Uplink: ");
                    this.up = sb4;
                } else {
                    sb3.append(", ");
                }
                this.up.append(value);
                return;
            }
            Log.d(NVItemListActivity.TAG, "invalid type");
        }

        public int getId() {
            return this.id;
        }

        public String toString() {
            if (this.up == null && this.down == null) {
                return "N/A";
            }
            if (this.up == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("         Uplink: N/A");
                this.up = sb;
            }
            if (this.down == null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("         Downlink: N/A");
                this.down = sb2;
            }
            return "\n" + ((CharSequence) this.up) + "\n" + ((CharSequence) this.down);
        }
    }

    class BGHandler extends Handler {
        public BGHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Log.d(NVItemListActivity.TAG, "handleMessage:" + msg.what);
            if (msg.what == 1) {
                NVItemListActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.NVItemListActivity.BGHandler.1
                    @Override // java.lang.Runnable
                    public void run() throws IllegalAccessException, InvocationTargetException {
                        NVItemListActivity.this.processSysConfig();
                        NVItemListActivity.this.processCops();
                        NVItemListActivity.this.processSpengmd007();
                        NVItemListActivity.this.processSpengmd008();
                        NVItemListActivity.this.processSpengmdltecategory();
                        NVItemListActivity.this.processSpengmdlteband();
                        NVItemListActivity.this.processSpengmdvolte();
                        NVItemListActivity.this.processSMSBearer();
                        NVItemListActivity.this.processSMSBRetryInterval();
                        NVItemListActivity.this.processAGPSQosTime();
                        NVItemListActivity.this.processVoWifi();
                        NVItemListActivity.this.processCA();
                        NVItemListActivity.this.setListAdapter(new SimpleAdapter(NVItemListActivity.this, NVItemListActivity.this.myData, R.layout.simple_list_item_1, new String[]{"title"}, new int[]{R.id.text1}));
                        NVItemListActivity.this.getListView().setTextFilterEnabled(true);
                    }
                });
            }
        }
    }
}
