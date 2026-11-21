package com.unisoc.engineermode.core.impl.debuglog;

import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.IAiIp;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: AiIpImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0011\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R$\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00048V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\t¨\u0006\u0012"}, d2 = {"Lcom/unisoc/engineermode/core/impl/debuglog/AiIpImpl;", "Lcom/unisoc/engineermode/core/intf/IAiIp;", "()V", "AI_DEBUG_VLOG", "", "AI_IP_SWITCH", "vLogName", "aiDebugVLog", "getAiDebugVLog", "()Ljava/lang/String;", "setAiDebugVLog", "(Ljava/lang/String;)V", "aiIpStatus", "getAiIpStatus", "setAICambriconNPU", "", "setAIImaginationNNA", "setAINone", "name"}, k = 1, mv = {1, 1, 13})
public final class AiIpImpl implements IAiIp {
    private static final String AI_DEBUG_VLOG = "debug.nn.vlog";
    private static final String AI_IP_SWITCH = "persist.vendor.npu.version";
    public static final AiIpImpl INSTANCE = new AiIpImpl();

    private AiIpImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.IAiIp
    @NotNull
    public String getAiIpStatus() {
        return SystemPropertiesProxy.get(AI_IP_SWITCH, "");
    }

    @Override // com.unisoc.engineermode.core.intf.IAiIp
    @NotNull
    public String getAiDebugVLog() {
        return SystemPropertiesProxy.get(AI_DEBUG_VLOG, "");
    }

    @Override // com.unisoc.engineermode.core.intf.IAiIp
    public void setAiDebugVLog(@NotNull String vLogName) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(vLogName, "vLogName");
        SystemPropertiesProxy.set(AI_DEBUG_VLOG, vLogName);
    }

    @Override // com.unisoc.engineermode.core.intf.IAiIp
    public void setAICambriconNPU() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set(AI_IP_SWITCH, "2");
    }

    @Override // com.unisoc.engineermode.core.intf.IAiIp
    public void setAIImaginationNNA() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set(AI_IP_SWITCH, "1");
    }

    @Override // com.unisoc.engineermode.core.intf.IAiIp
    public void setAINone() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set(AI_IP_SWITCH, "0");
    }
}
