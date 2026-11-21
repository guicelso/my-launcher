package com.unisoc.engineermode.core.intf;

import com.unisoc.engineermode.core.factory.BaseInterface;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: IDebugLogApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\u000bH&J\b\u0010\r\u001a\u00020\u000bH&R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005¨\u0006\u000e"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IAiIp;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "aiDebugVLog", "", "getAiDebugVLog", "()Ljava/lang/String;", "setAiDebugVLog", "(Ljava/lang/String;)V", "aiIpStatus", "getAiIpStatus", "setAICambriconNPU", "", "setAIImaginationNNA", "setAINone", "name"}, k = 1, mv = {1, 1, 13})
public interface IAiIp extends BaseInterface {
    @NotNull
    String getAiDebugVLog();

    @NotNull
    String getAiIpStatus();

    void setAICambriconNPU();

    void setAIImaginationNNA();

    void setAINone();

    void setAiDebugVLog(@NotNull String str);
}
