package com.tw.core.b;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.L;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private b() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    public static String fG() {
        return TextUtils.isEmpty(Build.DEVICE) ? "0" : Build.DEVICE;
    }

    public static long fH(Context context) {
        long j = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            String line = bufferedReader.readLine();
            String[] strArrSplit = line.split("\\s+");
            for (String str : strArrSplit) {
                C0065s.bK(line, str + "\t");
            }
            j = Long.parseLong(strArrSplit[1]);
            bufferedReader.close();
        } catch (Exception e) {
            C0065s.bL("DeviceInfoTools", e);
        }
        return j / 1024;
    }

    public static String fI() {
        String str = Build.MODEL;
        return str != null ? str.trim().replaceAll("\\s*", "") : "0";
    }

    public static String fJ() {
        return TextUtils.isEmpty(Build.MANUFACTURER) ? "0" : Build.MANUFACTURER;
    }

    public static String fK() {
        return DoFunApplication.nt().ab();
    }

    public static String fL(Context context) {
        List<Sensor> sensorList = ((SensorManager) context.getSystemService("sensor")).getSensorList(-1);
        String str = "";
        for (int i = 0; i < sensorList.size(); i++) {
            str = str + sensorList.get(i).getName() + ",";
        }
        return str.length() > 0 ? str.substring(0, str.length() - 1) : "0";
    }

    public static String fM() {
        String line = "";
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address").getInputStream()));
            while (line != null) {
                line = lineNumberReader.readLine();
                if (line != null) {
                    return line.trim();
                }
            }
            return "0";
        } catch (IOException e) {
            C0065s.bL("DeviceInfoTools", e);
            return "0";
        }
    }

    public static String fN(Context context) {
        String strFR = fR(((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getIpAddress());
        return !TextUtils.isEmpty(strFR) ? strFR : "-1";
    }

    public static String fO() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            return "0";
        }
        String address = defaultAdapter.getAddress();
        return !TextUtils.isEmpty(address) ? address : "0";
    }

    public static String fP() {
        JSONObject jSONObject = new JSONObject();
        try {
            Context contextNu = DoFunApplication.nu();
            jSONObject.put("model", Build.MODEL);
            jSONObject.put("uuid", UUID.randomUUID().toString());
            jSONObject.put("screenSize", c.fV(contextNu));
            jSONObject.put("screenWH", c.fS(contextNu) + "x" + c.fT(contextNu));
            jSONObject.put("memInfo", String.valueOf(fH(contextNu)));
            jSONObject.put("cid", f.gr());
            jSONObject.put("systemVersion", e.gg());
            jSONObject.put("fingerPrint", SystemProperties.get("ro.build.fingerprint"));
            jSONObject.put("buildId", SystemProperties.get("ro.build.id"));
            List listDk = L.dk();
            if (listDk != null) {
                jSONObject.put("simInfo", listDk.toString());
            }
            return jSONObject.toString();
        } catch (Exception e) {
            C0065s.bK("DeviceInfoTools", "getDeviceInfo " + e.getMessage());
            return jSONObject.toString();
        }
    }

    public static String fQ() {
        Context contextNu = DoFunApplication.nu();
        String strBz = C0064r.bz(contextNu, "SP_RANDOM_UUID", null);
        if (!TextUtils.isEmpty(strBz)) {
            return strBz;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.valueOf(Build.VERSION.SDK_INT));
        arrayList.add(Build.MODEL);
        arrayList.add(Build.PRODUCT);
        arrayList.add(UUID.randomUUID().toString());
        arrayList.add(c.fV(contextNu));
        arrayList.add(c.fU(contextNu));
        arrayList.add(c.fS(contextNu) + "x" + c.fT(contextNu));
        arrayList.add(String.valueOf(fH(contextNu)));
        arrayList.add(f.gr());
        arrayList.add(e.gg());
        List listDk = L.dk();
        if (listDk != null) {
            arrayList.add(listDk.toString());
        }
        String string = UUID.nameUUIDFromBytes(arrayList.toString().getBytes()).toString();
        C0064r.bB(contextNu, "SP_RANDOM_UUID", string);
        return string;
    }

    public static String fR(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }
}
