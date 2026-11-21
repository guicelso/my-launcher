package com.tw.a.a.b;

import android.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.INetworkStatsService;
import android.net.INetworkStatsSession;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkStatsHistory;
import android.net.NetworkTemplate;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.TelephonyManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.Pair;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/* JADX INFO: compiled from: DataUsageController.java */
/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final boolean f562a = Log.isLoggable("DataUsageController", 3);
    private static final StringBuilder b = new StringBuilder(50);
    private static final Formatter c = new Formatter(b, Locale.getDefault());
    private final Context d;
    private final TelephonyManager e;
    private final ConnectivityManager f;
    private final INetworkStatsService g = INetworkStatsService.Stub.asInterface(ServiceManager.getService("netstats"));
    private final NetworkPolicyManager h;
    private INetworkStatsSession i;
    private b j;

    /* JADX INFO: renamed from: com.tw.a.a.b.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: DataUsageController.java */
    public static class C0025a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public String f563a;
        public String b;
        public long c;
        public long d;
        public long e;
        public long f;
    }

    /* JADX INFO: compiled from: DataUsageController.java */
    public interface b {
        String a();
    }

    public a(Context context) {
        this.d = context;
        this.e = TelephonyManager.from(context);
        this.f = ConnectivityManager.from(context);
        this.h = NetworkPolicyManager.from(this.d);
    }

    public long a() {
        return ((long) this.d.getResources().getInteger(R.integer.config_pauseRotationWhenUnfolding_displaySwitchTimeout)) * 1048576;
    }

    private INetworkStatsSession b() {
        if (this.i == null) {
            try {
                this.i = this.g.openSession();
            } catch (RemoteException e) {
                Log.w("DataUsageController", "Failed to open stats session", e);
            } catch (RuntimeException e2) {
                Log.w("DataUsageController", "Failed to open stats session", e2);
            }
        }
        return this.i;
    }

    private C0025a a(String str) {
        Log.w("DataUsageController", "Failed to get data usage, " + str);
        return null;
    }

    public C0025a a(NetworkTemplate networkTemplate) {
        long epochMilli;
        long epochMilli2;
        INetworkStatsSession iNetworkStatsSessionB = b();
        if (iNetworkStatsSessionB == null) {
            return a("no stats session");
        }
        NetworkPolicy networkPolicyB = b(networkTemplate);
        try {
            NetworkStatsHistory historyForNetwork = iNetworkStatsSessionB.getHistoryForNetwork(networkTemplate, 10);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (networkPolicyB != null) {
                Pair pair = (Pair) NetworkPolicyManager.cycleIterator(networkPolicyB).next();
                epochMilli = ((ZonedDateTime) pair.first).toInstant().toEpochMilli();
                epochMilli2 = ((ZonedDateTime) pair.second).toInstant().toEpochMilli();
            } else {
                epochMilli = jCurrentTimeMillis - 2419200000L;
                epochMilli2 = jCurrentTimeMillis;
            }
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            long j = epochMilli2;
            NetworkStatsHistory.Entry values = historyForNetwork.getValues(epochMilli, epochMilli2, jCurrentTimeMillis, (NetworkStatsHistory.Entry) null);
            long jCurrentTimeMillis3 = System.currentTimeMillis();
            if (f562a) {
                Log.d("DataUsageController", String.format("history call from %s to %s now=%s took %sms: %s", new Date(epochMilli), new Date(j), new Date(jCurrentTimeMillis), Long.valueOf(jCurrentTimeMillis3 - jCurrentTimeMillis2), a(values)));
            }
            if (values == null) {
                return a("no entry data");
            }
            long j2 = values.rxBytes + values.txBytes;
            C0025a c0025a = new C0025a();
            c0025a.c = epochMilli;
            c0025a.f = j2;
            c0025a.b = a(epochMilli, j);
            if (networkPolicyB != null) {
                c0025a.d = networkPolicyB.limitBytes > 0 ? networkPolicyB.limitBytes : 0L;
                c0025a.e = networkPolicyB.warningBytes > 0 ? networkPolicyB.warningBytes : 0L;
            } else {
                c0025a.e = a();
            }
            if (this.j != null) {
                c0025a.f563a = this.j.a();
            }
            return c0025a;
        } catch (RemoteException unused) {
            return a("remote call failed");
        }
    }

    private NetworkPolicy b(NetworkTemplate networkTemplate) {
        NetworkPolicy[] networkPolicies;
        if (this.h == null || networkTemplate == null || (networkPolicies = this.h.getNetworkPolicies()) == null) {
            return null;
        }
        for (NetworkPolicy networkPolicy : networkPolicies) {
            if (networkPolicy != null && networkTemplate.equals(networkPolicy.template)) {
                return networkPolicy;
            }
        }
        return null;
    }

    private static String a(NetworkStatsHistory.Entry entry) {
        if (entry == null) {
            return null;
        }
        return "Entry[bucketDuration=" + entry.bucketDuration + ",bucketStart=" + entry.bucketStart + ",activeTime=" + entry.activeTime + ",rxBytes=" + entry.rxBytes + ",rxPackets=" + entry.rxPackets + ",txBytes=" + entry.txBytes + ",txPackets=" + entry.txPackets + ",operations=" + entry.operations + ']';
    }

    private String a(long j, long j2) {
        String string;
        synchronized (b) {
            b.setLength(0);
            string = DateUtils.formatDateRange(this.d, c, j, j2, 65552, null).toString();
        }
        return string;
    }
}
