package com.sprd.engineermode.debuglog;

import android.app.Activity;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.sprd.engineermode.utils.EMFileUtils;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.utils.IATUtils;
import com.unisoc.engineermode.core.utils.SocketUtils;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes4.dex */
public class VersionInfoActivity extends Activity {
    private static final int GET_CP2_VERSION = 3;
    private static final int GET_DSP_VERSION = 2;
    private static final int GET_MODEM_VERSION = 0;
    private static final int GET_PS_VERSION = 1;
    private static final int GET_TP_VERSION = 4;
    public static final String GPS_CSR_CG_CONFIG_FILE = "/data/cg/supl/supl.xml";
    public static final String GPS_CSR_CONFIG_FIL_FOR_GE2 = "/data/gnss/config/config.xml";
    public static final String GPS_CSR_GNSS_CONFIG_FILE = "/data/gnss/supl/supl.xml";
    private static final String KEY_AP_VERSION = "ap_version";
    private static final String KEY_CMD_TYPE = "cmd_type";
    private static final String KEY_CP2_VERSION = "cp2_version";
    private static final String KEY_DSP_VERSION = "dsp_version";
    private static final String KEY_GPS_VERSION = "gps_version";
    private static final String KEY_HARDWARE_VERSION = "hardware_version";
    private static final String KEY_MODEM_VERSION = "modem_version";
    private static final String KEY_PS_VERSION = "ps_version";
    private static final String KEY_TP_VERSION = "tp_version";
    private static final String PROCESS_NAME = "wcnd ";
    private static final String SOCKET_NAME = "hidl_common_socket";
    private static final String TAG = "VersionInfoActivity";
    public static final String TP_VERSION = "/sys/touchscreen/firmware_version";
    private static final String UNSUPPORT = "UNSOPPORT IN THIS VERSION";
    private String mCmdType;
    private TextView mTpVersionText;
    private Handler mUiThread = new Handler();
    private String mVersion;
    private VersionHandler mVersionHandler;
    private TextView mVersionText;
    private static final String DEFAULT_GPS_CHIP_TYPE = "ge";
    public static final String GPS_CHIP_TYPE = SystemPropertiesProxy.get("ro.wcn.gpschip", DEFAULT_GPS_CHIP_TYPE);

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version);
        this.mVersionText = (TextView) findViewById(R.id.version_id);
        this.mVersionText.setText("");
        this.mTpVersionText = (TextView) findViewById(R.id.tp_version_id);
        this.mTpVersionText.setText("");
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.d(TAG, "no requirment version");
            return;
        }
        this.mCmdType = extras.getString(KEY_CMD_TYPE, null);
        Log.d(TAG, "require version " + this.mCmdType);
        if (this.mCmdType == null) {
            return;
        }
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mVersionHandler = new VersionHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    protected void onStart() {
        String str = this.mCmdType;
        if (str != null) {
            if (str.equals(KEY_MODEM_VERSION)) {
                Message modemVersion = this.mVersionHandler.obtainMessage(0);
                this.mVersionHandler.sendMessage(modemVersion);
            } else if (this.mCmdType.equals(KEY_PS_VERSION)) {
                Message psVersion = this.mVersionHandler.obtainMessage(1);
                this.mVersionHandler.sendMessage(psVersion);
            } else if (this.mCmdType.equals(KEY_DSP_VERSION)) {
                Message dspVersion = this.mVersionHandler.obtainMessage(2);
                this.mVersionHandler.sendMessage(dspVersion);
            } else if (this.mCmdType.equals(KEY_CP2_VERSION)) {
                Message cp2Version = this.mVersionHandler.obtainMessage(3);
                this.mVersionHandler.sendMessage(cp2Version);
            } else if (this.mCmdType.equals(KEY_TP_VERSION)) {
                Message tPVersion = this.mVersionHandler.obtainMessage(4);
                this.mVersionHandler.sendMessage(tPVersion);
            } else if (this.mCmdType.equals(KEY_GPS_VERSION)) {
                this.mVersionText.setText(getGPSVersionInfo());
            }
        }
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        VersionHandler versionHandler = this.mVersionHandler;
        if (versionHandler != null) {
            versionHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    private String getGPSVersionInfo() {
        if (DEFAULT_GPS_CHIP_TYPE.equals(GPS_CHIP_TYPE)) {
            String gpsVersion = getGPSVersion(GPS_CSR_CG_CONFIG_FILE, "PROPERTY", "VERSION");
            return gpsVersion;
        }
        if ("ge2".equals(GPS_CHIP_TYPE)) {
            String gpsVersion2 = getGPSVersion(GPS_CSR_CONFIG_FIL_FOR_GE2, "PROPERTY", "GE2-VERSION");
            return gpsVersion2;
        }
        Log.d(TAG, "No match for version " + GPS_CHIP_TYPE);
        return "UNKNOWN";
    }

    private String getGPSVersion(String path, String elementName, String key) {
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
    public String recombinResult(int cmdType, String res) {
        String result = null;
        String[] str = res.split("\n");
        for (int i = 0; i < str.length - 1; i++) {
            if (cmdType == 0 && str[i].contains("Platform Version")) {
                result = "Modem Version:\n" + str[i].split("\\:")[1] + "\nRelease Date:\n" + str[str.length - 2] + "\n";
            } else if (cmdType == 1 && str[i].contains("BASE  Version")) {
                result = "PS Version:\n" + str[i].split("\\:")[1] + "\nRelease Date:\n" + str[str.length - 2] + "\n";
            }
        }
        if (cmdType == 3) {
            if (res.startsWith("marlin")) {
                String debug = "Debug";
                if (res.contains("Release")) {
                    debug = "Release";
                }
                String[] str1 = res.split("\\:");
                String cp2Version = str1[2].trim();
                int dateIndex = cp2Version.indexOf(debug) + debug.length();
                String version = cp2Version.substring(0, dateIndex);
                String time = cp2Version.substring(dateIndex) + ":" + str1[3].trim() + ":" + str1[4].trim();
                String result2 = "CP2 Version:\n" + version + "\nRelease Date:\n" + time;
                return result2;
            }
            String res2 = res.replaceAll("\\s+", "");
            if (!res2.startsWith("Platform")) {
                if (res2.startsWith("WCN_VER")) {
                    String[] str2 = str[0].split("\\~");
                    String[] strPlatformVersion = str2[0].split("\\:");
                    String[] strProjectVersion = str2[1].split("\\:");
                    String result3 = strPlatformVersion[2] + "|" + strProjectVersion[1] + "|" + str2[2];
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
            String subString2 = dateTime.substring(endIndex);
            String time2 = subString2.substring(10);
            String date = subString2.substring(0, 10);
            String result4 = m.group(1) + "|" + m.group(2) + "|" + subString1 + "|" + date + " " + time2;
            return result4;
        }
        return result;
    }

    class VersionHandler extends Handler {
        public VersionHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                String result = IATUtils.sendATCmd(engconstents.ENG_AT_CGMR, "atchannel1");
                Log.d(VersionInfoActivity.TAG, "Mode Version result is " + result);
                if (result.contains("OK")) {
                    VersionInfoActivity versionInfoActivity = VersionInfoActivity.this;
                    versionInfoActivity.mVersion = versionInfoActivity.recombinResult(0, result);
                } else {
                    Toast.makeText(VersionInfoActivity.this, "Get Modem Version Fail", 0).show();
                }
            } else if (i == 1) {
                String result2 = IATUtils.sendATCmd(engconstents.ENG_AT_CGMR, "atchannel1");
                Log.d(VersionInfoActivity.TAG, "PS Version result is " + result2);
                if (result2.contains("OK")) {
                    VersionInfoActivity versionInfoActivity2 = VersionInfoActivity.this;
                    versionInfoActivity2.mVersion = versionInfoActivity2.recombinResult(1, result2);
                } else {
                    Toast.makeText(VersionInfoActivity.this, "Get PS Version Fail", 0).show();
                }
            } else if (i == 2) {
                String result3 = IATUtils.sendATCmd("AT+SPDSPVERSION=0", "atchannel1");
                String[] str = result3.split("\n");
                if (result3.contains("OK")) {
                    String[] str1 = str[0].split("\\,");
                    VersionInfoActivity.this.mVersion = "GSM DSP Version Info:\n" + str1[1] + "\n\n";
                } else {
                    VersionInfoActivity.this.mVersion = "GSM DSP Version Info:\n" + VersionInfoActivity.this.getString(R.string.feature_not_support) + "\n\n";
                }
                Log.d(VersionInfoActivity.TAG, "DSPVersion GSM is " + result3);
                String result4 = IATUtils.sendATCmd("AT+SPDSPVERSION=1", "atchannel1");
                String[] str2 = result4.split("\n");
                if (result4.contains("OK")) {
                    String[] str3 = str2[0].split("\\,");
                    VersionInfoActivity.this.mVersion = VersionInfoActivity.this.mVersion + "TD DSP Version Info:\n" + str3[1] + "\n\n";
                } else {
                    VersionInfoActivity.this.mVersion = VersionInfoActivity.this.mVersion + "TD DSP Version Info:\n" + VersionInfoActivity.this.getString(R.string.feature_not_support) + "\n\n";
                }
                Log.d(VersionInfoActivity.TAG, "DSPVersion TD is " + result4);
                String result5 = IATUtils.sendATCmd("AT+SPDSPVERSION=2", "atchannel1");
                String[] str4 = result5.split("\n");
                if (result5.contains("OK")) {
                    String[] str5 = str4[0].split("\\,");
                    VersionInfoActivity.this.mVersion = VersionInfoActivity.this.mVersion + "WCDMA DSP Version Info:\n" + str5[1] + "\n\n";
                } else {
                    VersionInfoActivity.this.mVersion = VersionInfoActivity.this.mVersion + "WCDMA DSP Version Info:\n" + VersionInfoActivity.this.getString(R.string.feature_not_support) + "\n\n";
                }
                Log.d(VersionInfoActivity.TAG, "DSPVersion WCDMA is " + result5);
                String result6 = IATUtils.sendATCmd("AT+SPDSPVERSION=3", "atchannel1");
                String[] str6 = result6.split("\n");
                if (result6.contains("OK")) {
                    String[] str7 = str6[0].split("\\,");
                    VersionInfoActivity.this.mVersion = VersionInfoActivity.this.mVersion + "LTE DSP Version Info:\n" + str7[1] + "\n\n";
                } else {
                    VersionInfoActivity.this.mVersion = VersionInfoActivity.this.mVersion + "LTE DSP Version Info:\n" + VersionInfoActivity.this.getString(R.string.feature_not_support) + "\n\n";
                }
                Log.d(VersionInfoActivity.TAG, "DSPVersion LTE is " + result6);
            } else if (i == 3) {
                String powerResult = SocketUtils.sendCmdAndRecResult(VersionInfoActivity.SOCKET_NAME, LocalSocketAddress.Namespace.ABSTRACT, "wcnd wcn poweron");
                Log.d(VersionInfoActivity.TAG, "OPEN_CP2 Response is " + powerResult);
                String result7 = SocketUtils.sendCmdAndRecResult(VersionInfoActivity.SOCKET_NAME, LocalSocketAddress.Namespace.ABSTRACT, "wcnd wcn at+spatgetcp2info");
                Log.d(VersionInfoActivity.TAG, "CP2 Version is " + result7);
                if (result7 != null) {
                    VersionInfoActivity versionInfoActivity3 = VersionInfoActivity.this;
                    versionInfoActivity3.mVersion = versionInfoActivity3.recombinResult(3, result7);
                } else {
                    Toast.makeText(VersionInfoActivity.this, "Get CP2 Version Fail", 0).show();
                }
            } else if (i == 4) {
                VersionInfoActivity.this.mVersion = EMFileUtils.read(VersionInfoActivity.TP_VERSION);
                Log.d(VersionInfoActivity.TAG, "Tp Version is " + VersionInfoActivity.this.mVersion);
                if (VersionInfoActivity.this.mVersion == null) {
                    VersionInfoActivity.this.mVersion = VersionInfoActivity.UNSUPPORT;
                }
            }
            Log.d(VersionInfoActivity.TAG, "mVersion is " + VersionInfoActivity.this.mVersion);
            VersionInfoActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.VersionInfoActivity.VersionHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    VersionInfoActivity.this.mVersionText.setText(VersionInfoActivity.this.mVersion);
                }
            });
        }
    }
}
