package com.sprd.engineermode.telephony.volte;

import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.util.function.Function;

/* JADX INFO: renamed from: com.sprd.engineermode.telephony.volte.-$$Lambda$IaTXTxr1U3etuDswdH-yhMQHw-Q, reason: invalid class name */
/* JADX INFO: compiled from: lambda */
/* JADX INFO: loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$IaTXTxr1U3etuDswdHyhMQHwQ implements Function {
    public static final /* synthetic */ $$Lambda$IaTXTxr1U3etuDswdHyhMQHwQ INSTANCE = new $$Lambda$IaTXTxr1U3etuDswdHyhMQHwQ();

    private /* synthetic */ $$Lambda$IaTXTxr1U3etuDswdHyhMQHwQ() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((ITelephonyApi.IVolteUeSettings.VoiceCodec) obj).name();
    }
}
