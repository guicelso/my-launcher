package com.tw.core.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.logging.Logger;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private a() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    public static String fB(Context context) {
        int iFD = fD(context);
        if (1 == iFD) {
            return "NETWORK_WIFI";
        }
        if (4 == iFD) {
            return "NETWORK_4G";
        }
        if (3 == iFD) {
            return "NETWORK_3G";
        }
        if (2 == iFD) {
            return "NETWORK_2G";
        }
        return -1 == iFD ? "NETWORK_NO" : "NETWORK_UNKNOWN";
    }

    private static NetworkInfo fC(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    public static int fD(Context context) {
        NetworkInfo networkInfoFC = fC(context);
        if (networkInfoFC != null && networkInfoFC.isAvailable()) {
            if (networkInfoFC.getType() == 1) {
                return 1;
            }
            if (networkInfoFC.getType() != 0) {
                return 5;
            }
            switch (networkInfoFC.getSubtype()) {
                case Logger.SEVERE /* 1 */:
                case Logger.WARNING /* 2 */:
                case 4:
                case Logger.FINEST /* 7 */:
                case 11:
                case 16:
                    return 2;
                case 3:
                case Logger.FINE /* 5 */:
                case 6:
                case 8:
                case 9:
                case MqttConnectOptions.MAX_INFLIGHT_DEFAULT /* 10 */:
                case 12:
                case 14:
                case 15:
                case 17:
                    return 3;
                case 13:
                case 18:
                    return 4;
                default:
                    String subtypeName = networkInfoFC.getSubtypeName();
                    return (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) ? 3 : 5;
            }
        }
        return -1;
    }

    public static boolean fE(Context context) {
        NetworkInfo[] allNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null) {
                for (NetworkInfo networkInfo : allNetworkInfo) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean fF(Context context) {
        NetworkInfo networkInfoFC = fC(context);
        if (networkInfoFC != null) {
            return networkInfoFC.isConnected();
        }
        return false;
    }
}
