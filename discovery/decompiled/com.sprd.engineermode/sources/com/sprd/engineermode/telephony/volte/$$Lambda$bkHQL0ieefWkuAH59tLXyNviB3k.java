package com.sprd.engineermode.telephony.volte;

import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.util.function.Function;

/* JADX INFO: renamed from: com.sprd.engineermode.telephony.volte.-$$Lambda$bkHQL0ieefWkuAH59tLXyNviB3k, reason: invalid class name */
/* JADX INFO: compiled from: lambda */
/* JADX INFO: loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$bkHQL0ieefWkuAH59tLXyNviB3k implements Function {
    public static final /* synthetic */ $$Lambda$bkHQL0ieefWkuAH59tLXyNviB3k INSTANCE = new $$Lambda$bkHQL0ieefWkuAH59tLXyNviB3k();

    private /* synthetic */ $$Lambda$bkHQL0ieefWkuAH59tLXyNviB3k() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((ITelephonyApi.IVolteUeSettings.VideoCodec) obj).name();
    }
}
