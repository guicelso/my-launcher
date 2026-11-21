package com.unisoc.engineermode.core.intf;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: IDebugLogApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u000fH&¨\u0006\u0010"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IDebugLogApi;", "", "aiIpApi", "Lcom/unisoc/engineermode/core/intf/IAiIp;", "atDiagApi", "Lcom/unisoc/engineermode/core/intf/IAtDiag;", "cabcApi", "Lcom/unisoc/engineermode/core/intf/ICabc;", "gcfTestApi", "Lcom/unisoc/engineermode/core/intf/IGcfTest;", "jeitaControlApi", "Lcom/unisoc/engineermode/core/intf/IJeitaControl;", "phoneInfoApi", "Lcom/unisoc/engineermode/core/intf/IPhoneInfo;", "smsCenterNumberApi", "Lcom/unisoc/engineermode/core/intf/ISMSCenterNumber;", "name"}, k = 1, mv = {1, 1, 13})
public interface IDebugLogApi {
    @NotNull
    IAiIp aiIpApi();

    @NotNull
    IAtDiag atDiagApi();

    @NotNull
    ICabc cabcApi();

    @NotNull
    IGcfTest gcfTestApi();

    @NotNull
    IJeitaControl jeitaControlApi();

    @NotNull
    IPhoneInfo phoneInfoApi();

    @NotNull
    ISMSCenterNumber smsCenterNumberApi();
}
