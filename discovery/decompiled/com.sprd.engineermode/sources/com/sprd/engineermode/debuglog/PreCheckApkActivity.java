package com.sprd.engineermode.debuglog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.LocalSocketAddress;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.sprd.engineermode.utils.TeleManagerUtil;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import com.unisoc.engineermode.core.utils.SocketUtils;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes4.dex */
public class PreCheckApkActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final int ADCBYTES = 56;
    private static final String ADC_PATH = "/productinfo/adc.bin";
    private static final int AP_CONFIG_VERSION = 7;
    private static final int AP_VERSION = 0;
    private static final int BT_MAC = 9;
    private static final int CALL_FORWARDING = 12;
    private static final int CP_CONFIG_VERSION = 6;
    private static final int DSP_VERSION = 3;
    private static final String FILEPATH = "infolog.txt";
    private static final int GET_CP0_VERSION = 0;
    private static final int GET_WCN_VERSION = 1;
    private static final String GPS_CSR_CONFIG_FILE = "/data/cg/supl/supl.xml";
    private static final String GPS_CSR_CONFIG_FILE2 = "/data/gnss/config/config.xml";
    private static final int GPS_VERSION = -1;
    private static final int IMEI = 8;
    private static final int LTE_VERSION = 5;
    private static final int MODEM_VERSION = 2;
    private static final int MOS_MODE = 14;
    private static final String PROCESS_NAME = "wcnd ";
    private static final String SOCKET_NAME = "hidl_common_socket";
    private static final int SUPPORT_BAND = 11;
    private static final String TAG = "PreCheckActivity";
    private static final int USER_DEBUG_VERSION = 4;
    private static final String VIEW_ITEM_VERSION = "com.engineermode.debuglog.itemversion";
    private static final int VOLTE = 13;
    private static final int WCN_VERSION = 1;
    private static final int WIFI_MAC = 10;
    private static final int WRITE_FAIL = 21;
    private static final int WRITE_SUCC = 20;
    private static int mPhoneCount;
    private CheckItemAdapter mAdapter;
    private BandData mBandData;
    private ArrayList<String> mCheckItems;
    private String mFilePath;
    private ListView mListView;
    private Resources mResources;
    private TeleManagerUtil mTeleManagerUtil;
    private String mUnavailable;
    private WifiManager mWifiManager;
    private BluetoothAdapter mBluetoothAdapter = null;
    private PreHandler mPreHandler = new PreHandler();
    private DataInputStream mInputStream = null;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_check_activity);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(4, 4);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        setTitle(R.string.precheck_title);
        init();
    }

    private void init() {
        this.mResources = getResources();
        parseXml(R.array.CheckItems);
        this.mListView = (ListView) findViewById(R.id.itemlist);
        if (this.mAdapter == null) {
            this.mAdapter = new CheckItemAdapter(this.mCheckItems, this);
        }
        this.mListView.setAdapter((ListAdapter) this.mAdapter);
        this.mListView.setOnItemClickListener(this);
        this.mTeleManagerUtil = TeleManagerUtil.getInstance(this);
        mPhoneCount = this.mTeleManagerUtil.getPhoneCount();
        this.mWifiManager = (WifiManager) getSystemService("wifi");
        this.mUnavailable = getResources().getString(R.string.status_unavailable);
    }

    private void parseXml(int arrayId) {
        this.mCheckItems = new ArrayList<>();
        for (String s : this.mResources.getStringArray(arrayId)) {
            this.mCheckItems.add(s);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getApVersion() {
        if (Const.isUser()) {
            String ApVersion = SystemPropertiesProxy.get("ro.build.description");
            return ApVersion;
        }
        String ApVersion2 = Build.DISPLAY;
        return ApVersion2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getWcnVersion() {
        String result = SocketUtils.sendCmdAndRecResult(SOCKET_NAME, LocalSocketAddress.Namespace.ABSTRACT, "wcnd wcn at+spatgetcp2info");
        Log.d(TAG, "WCN Version is " + result);
        if (result != null) {
            return recombinResult(1, result);
        }
        return "UNKNOWN";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getModemVersion() {
        String result = IATUtils.sendAt(engconstents.ENG_AT_CGMR, "atchannel1");
        Log.d(TAG, "CP0 Version is " + result);
        if (result.contains("OK")) {
            return recombinResult(0, result);
        }
        return "UNKNOWN";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getImei() {
        String ImeiResult = IATUtils.sendAt("AT", "atchannel0");
        if (!ImeiResult.contains("OK")) {
            return "ERROR(modem asserted)";
        }
        StringBuilder strbuild = new StringBuilder();
        for (int i = 0; i < mPhoneCount; i++) {
            strbuild.append("IMEI" + (i + 1) + ":");
            StringBuilder sb = new StringBuilder();
            sb.append("atchannel");
            sb.append(i);
            String result = IATUtils.sendAt("AT+SPIMEI?", sb.toString());
            strbuild.append(result.substring(0, 15) + "\n");
        }
        return strbuild.toString();
    }

    private String getGpsVersion() {
        String gpsVersion = getGpsVersion("/data/cg/supl/supl.xml", "PROPERTY", "VERSION");
        if ("UNKNOWN".equals(gpsVersion)) {
            return getGpsVersion("/data/gnss/config/config.xml", "PROPERTY", "GE2-VERSION");
        }
        return gpsVersion;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Log.d(TAG, "onItemClick:arg2=" + arg2 + ", arg3=" + arg3);
        String version = null;
        switch (arg2) {
            case -1:
                version = getGpsVersion();
                break;
            case 0:
                version = getApVersion();
                break;
            case 1:
                version = getWcnVersion();
                break;
            case 2:
                version = getModemVersion();
                break;
            case 3:
                version = getDspVersion();
                break;
            case 4:
                version = getVersion();
                break;
            case 5:
                version = getLteVersion();
                break;
            case 6:
                version = getCpConfigurationVersion();
                break;
            case 7:
                version = getApConfigurationVersion();
                break;
            case 8:
                String ImeiResult = IATUtils.sendAt("AT", "atchannel0");
                if (!ImeiResult.contains("OK")) {
                    Toast.makeText(this, "sorry, the modem is not alive!", 1).show();
                    return;
                }
                version = getImei();
                break;
            case 9:
                version = getBtStatus();
                break;
            case 10:
                version = getWifiStatus();
                break;
            case 11:
                String supportband = IATUtils.sendAt("AT", "atchannel0");
                if (!supportband.contains("OK")) {
                    Toast.makeText(this, "sorry, the modem is not alive!", 1).show();
                    return;
                }
                version = getSupportBand();
                break;
            case 12:
                version = getCallforwarding();
                break;
            case 13:
                version = getVolte();
                break;
            case 14:
                version = getMosMode();
                break;
        }
        Intent intent = new Intent(VIEW_ITEM_VERSION);
        intent.putExtra(ViewItemVersionActivity.VERSION, version);
        intent.putExtra(ViewItemVersionActivity.TITLE, this.mCheckItems.get(arg2).split("\\.")[1]);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getDspVersion() {
        String mVersion;
        String mVersion2;
        String mVersion3;
        String mVersion4;
        String result = IATUtils.sendAt("AT+SPDSPVERSION=0", "atchannel1");
        String[] str = result.split("\n");
        if (result.contains("OK")) {
            String[] str1 = str[0].split("\\,");
            mVersion = "GSM DSP Version Info:\n" + str1[1] + "\n\n";
        } else {
            mVersion = "GSM DSP Version Info:\n" + str[0] + "\n\n";
        }
        Log.d(TAG, "DSPVersion GSM is " + result);
        String result2 = IATUtils.sendAt("AT+SPDSPVERSION=1", "atchannel1");
        String[] str2 = result2.split("\n");
        if (result2.contains("OK")) {
            String[] str3 = str2[0].split("\\,");
            mVersion2 = mVersion + "TD DSP Version Info:\n" + str3[1] + "\n\n";
        } else {
            mVersion2 = mVersion + "TD DSP Version Info:\n" + str2[0] + "\n\n";
        }
        Log.d(TAG, "DSPVersion TD is " + result2);
        String result3 = IATUtils.sendAt("AT+SPDSPVERSION=2", "atchannel1");
        String[] str4 = result3.split("\n");
        if (result3.contains("OK")) {
            String[] str5 = str4[0].split("\\,");
            mVersion3 = mVersion2 + "WCDMA DSP Version Info:\n" + str5[1] + "\n\n";
        } else {
            mVersion3 = mVersion2 + "WCDMA DSP Version Info:\n" + str4[0] + "\n\n";
        }
        Log.d(TAG, "DSPVersion WCDMA is " + result3);
        String result4 = IATUtils.sendAt("AT+SPDSPVERSION=3", "atchannel1");
        String[] str6 = result4.split("\n");
        if (result4.contains("OK")) {
            String[] str7 = str6[0].split("\\,");
            mVersion4 = mVersion3 + "LTE DSP Version Info:\n" + str7[1] + "\n\n";
        } else {
            mVersion4 = mVersion3 + "LTE DSP Version Info:\n" + getString(R.string.feature_not_support) + "\n\n";
        }
        Log.d(TAG, "DSPVersion LTE is " + result4);
        return mVersion4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getMosMode() {
        String result = IATUtils.sendAt(engconstents.ENG_AT_GET_MOS_STATUS, "atchannel0");
        Log.d(TAG, "result=" + result);
        if (!result.contains("OK")) {
            return "UNKNOWN";
        }
        String[] splits = result.split(",");
        int mode = Integer.valueOf(splits[2].substring(0, 1)).intValue();
        Log.d(TAG, "mode=" + mode);
        if (mode == 0) {
            return "not mos test mode";
        }
        if (mode != 1) {
            return "UNKNOWN";
        }
        return "mos test mode";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getApConfigurationVersion() {
        if (SystemPropertiesProxy.get("ro.operator").contains("cmcc")) {
            return "CMCC";
        }
        if (!SystemPropertiesProxy.get("ro.operator").contains("cucc")) {
            return "Open Market";
        }
        return "CUCC";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getVolte() {
        if (SystemPropertiesProxy.get("persist.vendor.sys.volte.enable").equals("true")) {
            return "Volte was launched";
        }
        return "Volte was not launched";
    }

    public boolean readFile() {
        try {
            try {
                File adcFile = new File(ADC_PATH);
                if (!adcFile.exists()) {
                    Log.d(TAG, "adcFile do not exists");
                    try {
                        this.mInputStream.close();
                    } catch (Exception e) {
                        Log.d(TAG, "Failed get outputStream: " + e);
                    }
                    return false;
                }
                this.mInputStream = new DataInputStream(new FileInputStream(adcFile));
                byte[] buffer = new byte[56];
                int count = this.mInputStream != null ? this.mInputStream.read(buffer, 0, 56) : 0;
                if (buffer.length <= 0) {
                    Log.d(TAG, "buffer == null or buffer.length <= 0");
                    try {
                        this.mInputStream.close();
                    } catch (Exception e2) {
                        Log.d(TAG, "Failed get outputStream: " + e2);
                    }
                    return false;
                }
                Log.d(TAG, "count = " + count + " size = " + buffer.length);
                int adcBit = buffer.length + (-4);
                int adcResult = buffer[adcBit] | (-4);
                Log.d(TAG, "adcBit = " + adcBit + " buffer[" + adcBit + "] = 0x" + Integer.toHexString(buffer[adcBit]) + " adcResult = 0x" + Integer.toHexString(adcResult));
                if (adcResult == -1) {
                    try {
                        this.mInputStream.close();
                    } catch (Exception e3) {
                        Log.d(TAG, "Failed get outputStream: " + e3);
                    }
                    return true;
                }
                try {
                    this.mInputStream.close();
                } catch (Exception e4) {
                    Log.d(TAG, "Failed get outputStream: " + e4);
                }
                return false;
            } catch (Exception e5) {
                Log.d(TAG, "Failed get outputStream: " + e5);
                e5.printStackTrace();
                try {
                    this.mInputStream.close();
                } catch (Exception e6) {
                    Log.d(TAG, "Failed get outputStream: " + e6);
                }
                return false;
            }
        } catch (Throwable th) {
            try {
                this.mInputStream.close();
            } catch (Exception e7) {
                Log.d(TAG, "Failed get outputStream: " + e7);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getLteVersion() {
        String nowModeName = SystemPropertiesProxy.get("persist.radio.ssda.mode");
        Log.d(TAG, "nowModeName=" + nowModeName);
        if ("tdd-csfb".equals(nowModeName)) {
            return "3 Mode";
        }
        if ("fdd-csfb".equals(nowModeName)) {
            return "4 Mode";
        }
        if (!"csfb".equals(nowModeName)) {
            return "Unknown";
        }
        return "5 Mode";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCpConfigurationVersion() {
        String result = IATUtils.sendAt("AT+SPCAPABILITY=32,0", "atchannel0");
        if (!result.contains("OK")) {
            return "UNKNOWN";
        }
        String[] splits = result.split(",");
        int mode = Integer.valueOf(splits[2].substring(0, 1)).intValue();
        Log.d(TAG, "mode=" + mode);
        if (mode == 0) {
            return "Open Market";
        }
        if (mode != 1) {
            if (mode != 2) {
                return "UNKNOWN";
            }
            return "CUCC";
        }
        return "CMCC";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getVersion() {
        if (Build.DISPLAY.contains("userdebug") || Build.DISPLAY.contains("test-keys")) {
            return "userdebug";
        }
        return "user";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCallforwarding() {
        String result = SystemPropertiesProxy.get("persist.sys.callforwarding");
        return TextUtils.isEmpty(result) ? "0" : result;
    }

    private String recombinResult(int cmdType, String res) {
        String subString2;
        String time;
        String result = null;
        String[] str = res.split("\n");
        for (int i = 0; i < str.length - 1; i++) {
            if (cmdType == 0 && str[i].contains("BASE  Version")) {
                result = "PS Version:\n" + str[i].split("\\:")[1] + "\nRelease Date:\n" + str[str.length - 2] + "\n";
            }
        }
        if (cmdType == 1) {
            if (res.startsWith("marlin")) {
                String result2 = res.trim();
                return result2;
            }
            String date = "";
            String res2 = res.replaceAll("\\s+", "");
            if (!res2.startsWith("Platform")) {
                if (res2.startsWith("WCN_VER")) {
                    String[] str1 = str[0].split("\\~");
                    String[] strPlatformVersion = str1[0].split("\\:");
                    String[] strProjectVersion = str1[1].split("\\:");
                    String result3 = strPlatformVersion[2] + "|" + strProjectVersion[1] + "|" + str1[2];
                    return result3;
                }
                Log.e(TAG, "cp2 version is error");
                return result;
            }
            Matcher m = Pattern.compile("PlatformVersion:(\\S+)ProjectVersion:(\\S+)HWVersion:(\\S+)").matcher(res2);
            if (!m.matches()) {
                Log.e(TAG, "Regex did not match on cp2 version: ");
                return result;
            }
            String dateTime = m.group(3);
            int endIndex = dateTime.indexOf("modem") + "modem".length();
            String subString1 = dateTime.substring(0, endIndex);
            String subString3 = dateTime.substring(endIndex);
            int index = subString3.lastIndexOf(":");
            if (index > 0) {
                subString2 = subString3.substring(0, index + 3);
            } else {
                if (subString1 != null && subString1.length() > 2) {
                    subString1 = subString1.substring(0, subString1.length() - 2);
                }
                subString2 = null;
            }
            if (subString2 == null) {
                time = "";
            } else {
                time = subString2.substring(10);
            }
            if (subString2 != null) {
                date = subString2.substring(0, 10);
            }
            String result4 = m.group(1) + "|" + m.group(2) + "|" + subString1 + "|" + date + " " + time;
            return result4;
        }
        return result;
    }

    private String getGpsVersion(String path, String elementName, String key) {
        String gpsVersion = "UNKNOWN";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File gpsConfig = new File(path);
            Document doc = db.parse(gpsConfig);
            NodeList nodeList = doc.getElementsByTagName(elementName);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                if (element.getAttribute("NAME").equals(key)) {
                    gpsVersion = element.getAttribute("VALUE");
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception->" + e);
            e.printStackTrace();
        }
        Log.d(TAG, "GPS Version is " + gpsVersion);
        return gpsVersion;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getBtStatus() {
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (bluetooth != null) {
            String address = bluetooth.isEnabled() ? bluetooth.getAddress() : null;
            if (!TextUtils.isEmpty(address)) {
                return address.toLowerCase();
            }
        }
        return this.mUnavailable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getWifiStatus() {
        WifiInfo wifiInfo = this.mWifiManager.getConnectionInfo();
        String macAddress = wifiInfo == null ? null : wifiInfo.getMacAddress();
        return !TextUtils.isEmpty(macAddress) ? macAddress : this.mUnavailable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSupportBand() {
        String supportband = IATUtils.sendAt("AT", "atchannel0");
        if (!supportband.contains("OK")) {
            Toast.makeText(this, "sorry, the modem is not alive!", 1).show();
            return "ERROR(modem asserted)";
        }
        StringBuilder bandResult = new StringBuilder();
        int i = 0;
        while (true) {
            int i2 = mPhoneCount;
            if (i < i2) {
                if (i2 > 1) {
                    bandResult.append("Sim Card ");
                    bandResult.append(i + 1);
                    bandResult.append(":\n");
                }
                bandResult.append(getLteBands(i));
                bandResult.append(getWcdmaBands(i));
                bandResult.append(getTdBands(i));
                i++;
            } else {
                return bandResult.toString();
            }
        }
    }

    private String getLteBands(int phoneId) {
        LTEBandData bandData = LTEBandData.getInstance(phoneId, this);
        ITelephonyApi.IBand.LteBand supportedBands = bandData.getSupportBands();
        if (supportedBands == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (supportedBands.tddBands[i] == 1) {
                sb.append(LTEBandData.KEY_TDD_LTE);
                sb.append(i + 33);
                sb.append("\n");
            }
        }
        sb.append("\n");
        for (int i2 = 0; i2 < 32; i2++) {
            if (supportedBands.fddBands[i2] == 1) {
                sb.append(LTEBandData.KEY_FDD_LTE);
                sb.append(i2 + 1);
                sb.append("\n");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    private String getWcdmaBands(int phoneId) {
        WCDMABandData bandData = new WCDMABandData(this, phoneId);
        List<ITelephonyApi.IBand.WcdmaBand> supportedBands = bandData.getSupportBands();
        if (supportedBands.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (ITelephonyApi.IBand.WcdmaBand band : supportedBands) {
            sb.append(band.name());
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    private String getTdBands(int phoneId) {
        TDBandData bandData = new TDBandData(this, phoneId);
        ITelephonyApi.IBand.TdBand supportedBands = bandData.getSupportBands();
        if (supportedBands == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (supportedBands.band_a) {
            sb.append("TD_SCDMA A Band");
            sb.append("\n");
        }
        if (supportedBands.band_f) {
            sb.append("TD_SCDMA F Band");
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pre_check, menu);
        MenuItem about = menu.findItem(R.id.action_about);
        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.sprd.engineermode.debuglog.PreCheckApkActivity.1
            @Override // android.view.MenuItem.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem arg0) {
                Dialog dialog = new AlertDialog.Builder(PreCheckApkActivity.this).setTitle("apk info").setMessage(PreCheckApkActivity.this.getApkVersion()).create();
                dialog.show();
                return true;
            }
        });
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == 16908332) {
            finish();
            return super.onOptionsItemSelected(item);
        }
        if (itemId == R.id.action_save) {
            saveAllInfoToFiles();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getApkVersion() {
        PackageManager pm = getPackageManager();
        String apkVersionName = EnvironmentCompat.MEDIA_UNKNOWN;
        try {
            String packagename = getPackageName();
            Log.d(TAG, "packagename=" + packagename);
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            apkVersionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String result = "Apk Version: " + apkVersionName + "\nFeature:  key parameters check\nCopyright: UNISOC";
        return result;
    }

    private void saveAllInfoToFiles() {
        this.mPreHandler.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.PreCheckApkActivity.2
            @Override // java.lang.Runnable
            public void run() {
                Log.d(PreCheckApkActivity.TAG, "run...");
                StringBuffer fileResult = new StringBuffer();
                fileResult.append("INFO LIST:\n");
                String[] items = PreCheckApkActivity.this.mResources.getStringArray(R.array.CheckItems);
                String number = items[0];
                String version = PreCheckApkActivity.this.getApVersion();
                fileResult.append(number + ":\n");
                fileResult.append(version + "\n\n");
                String number2 = items[1];
                String version2 = PreCheckApkActivity.this.getWcnVersion();
                fileResult.append(number2 + ":\n");
                fileResult.append(version2 + "\n\n");
                String number3 = items[2];
                String version3 = PreCheckApkActivity.this.getModemVersion();
                if (version3 != null && version3.equals("UNKNOWN")) {
                    Log.d(PreCheckApkActivity.TAG, "getModemVersion UNKNOWN");
                    Toast.makeText(PreCheckApkActivity.this, "sorry, the modem is not alive!", 1).show();
                    return;
                }
                fileResult.append(number3 + ":\n");
                fileResult.append(version3 + "\n\n");
                String number4 = items[3];
                String version4 = PreCheckApkActivity.this.getDspVersion();
                fileResult.append(number4 + ":\n");
                fileResult.append(version4 + "\n\n");
                String number5 = items[4];
                String version5 = PreCheckApkActivity.this.getVersion();
                fileResult.append(number5 + ":\n");
                fileResult.append(version5 + "\n\n");
                String number6 = items[5];
                String version6 = PreCheckApkActivity.this.getLteVersion();
                fileResult.append(number6 + ":\n");
                fileResult.append(version6 + "\n\n");
                String number7 = items[6];
                String version7 = PreCheckApkActivity.this.getCpConfigurationVersion();
                fileResult.append(number7 + ":\n");
                fileResult.append(version7 + "\n\n");
                String number8 = items[7];
                String version8 = PreCheckApkActivity.this.getApConfigurationVersion();
                fileResult.append(number8 + ":\n");
                fileResult.append(version8 + "\n\n");
                String number9 = items[8];
                String version9 = PreCheckApkActivity.this.getImei();
                fileResult.append(number9 + ":\n");
                fileResult.append(version9 + "\n\n");
                String number10 = items[9];
                String version10 = PreCheckApkActivity.this.getBtStatus();
                fileResult.append(number10 + ":\n");
                fileResult.append(version10 + "\n\n");
                String number11 = items[10];
                String version11 = PreCheckApkActivity.this.getWifiStatus();
                fileResult.append(number11 + ":\n");
                fileResult.append(version11 + "\n\n");
                String number12 = items[11];
                String version12 = PreCheckApkActivity.this.getSupportBand();
                fileResult.append(number12 + ":\n");
                fileResult.append(version12 + "\n\n");
                String number13 = items[12];
                String version13 = PreCheckApkActivity.this.getCallforwarding();
                fileResult.append(number13 + ":\n");
                fileResult.append(version13 + "\n\n");
                String number14 = items[13];
                String version14 = PreCheckApkActivity.this.getVolte();
                fileResult.append(number14 + ":\n");
                fileResult.append(version14 + "\n\n");
                String number15 = items[14];
                String version15 = PreCheckApkActivity.this.getMosMode();
                fileResult.append(number15 + ":\n");
                fileResult.append(version15 + "\n\n");
                boolean saveSucc = PreCheckApkActivity.this.saveInfo(PreCheckApkActivity.FILEPATH, fileResult.toString());
                Message msg = new Message();
                msg.arg1 = saveSucc ? 20 : 21;
                PreCheckApkActivity.this.mPreHandler.sendMessage(msg);
            }
        });
    }

    class PreHandler extends Handler {
        PreHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Log.d(PreCheckApkActivity.TAG, "handleMessage...");
            int i = msg.arg1;
            if (i != 20) {
                if (i == 21) {
                    Toast.makeText(PreCheckApkActivity.this, "save info fail", 0).show();
                }
            } else {
                Toast.makeText(PreCheckApkActivity.this, "file saved in " + PreCheckApkActivity.this.mFilePath, 0).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean saveInfo(String filename, String infos) {
        Log.d(TAG, "saveInfo infos:" + infos.length());
        File secondStorage = null;
        List<File> allStorages = new ArrayList<>();
        Collections.addAll(allStorages, getApplicationContext().getExternalFilesDirs(null));
        for (File file : allStorages) {
            if (file != null) {
                Log.d(TAG, "file = " + file.getAbsolutePath());
                if (file.getAbsolutePath().startsWith("/storage/emulated/0")) {
                    Log.d(TAG, "File is internal storage!");
                    secondStorage = file;
                    break;
                }
                secondStorage = file;
            }
        }
        if (secondStorage != null) {
            Log.d(TAG, "secondStorage = " + secondStorage.getAbsolutePath());
            this.mFilePath = secondStorage.getAbsolutePath() + "/" + filename;
            try {
                File saveFile = new File(this.mFilePath);
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(saveFile);
                out.write(infos.getBytes());
                out.close();
                return true;
            } catch (IOException e) {
                Log.d(TAG, "IOException:" + Log.getStackTraceString(e));
                return false;
            }
        }
        return false;
    }
}
