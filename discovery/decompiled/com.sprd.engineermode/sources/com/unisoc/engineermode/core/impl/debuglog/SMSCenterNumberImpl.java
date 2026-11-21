package com.unisoc.engineermode.core.impl.debuglog;

import com.unisoc.engineermode.core.impl.nonpublic.SmsManagerExProxy;
import com.unisoc.engineermode.core.intf.ISMSCenterNumber;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: SMSCenterNumberImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0004H\u0016¨\u0006\n"}, d2 = {"Lcom/unisoc/engineermode/core/impl/debuglog/SMSCenterNumberImpl;", "Lcom/unisoc/engineermode/core/intf/ISMSCenterNumber;", "()V", "getSmsCenterNumber", "", "subId", "", "setSmsCenterNumber", "", "smsAddress", "name"}, k = 1, mv = {1, 1, 13})
public final class SMSCenterNumberImpl implements ISMSCenterNumber {
    public static final SMSCenterNumberImpl INSTANCE = new SMSCenterNumberImpl();

    private SMSCenterNumberImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ISMSCenterNumber
    @NotNull
    public String getSmsCenterNumber(int subId) {
        return SmsManagerExProxy.INSTANCE.getSmscForSubscriber(subId);
    }

    @Override // com.unisoc.engineermode.core.intf.ISMSCenterNumber
    public boolean setSmsCenterNumber(int subId, @NotNull String smsAddress) {
        Intrinsics.checkParameterIsNotNull(smsAddress, "smsAddress");
        try {
            boolean result = SmsManagerExProxy.INSTANCE.setSmscForSubscriber(subId, smsAddress);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
    }
}
