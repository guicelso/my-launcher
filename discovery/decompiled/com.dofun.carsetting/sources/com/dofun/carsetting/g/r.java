package com.dofun.carsetting.g;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;

/* JADX INFO: compiled from: SimInfoView.java */
/* JADX INFO: loaded from: classes.dex */
public interface r extends c {
    void a(int i, int i2, String str);

    void a(int i, ServiceState serviceState);

    void a(int i, SignalStrength signalStrength);

    void a(SubscriptionInfo subscriptionInfo, SubscriptionInfo subscriptionInfo2);

    void a(boolean z);
}
