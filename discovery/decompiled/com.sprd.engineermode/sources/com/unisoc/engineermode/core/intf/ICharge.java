package com.unisoc.engineermode.core.intf;

import com.unisoc.engineermode.core.factory.BaseInterface;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: IHardwareApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\u000bH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lcom/unisoc/engineermode/core/intf/ICharge;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "chargeLevel", "", "getChargeLevel", "()Ljava/lang/String;", "chargeState", "", "getChargeState", "()Z", "closeUsbCharge", "", "openUsbCharge", "name"}, k = 1, mv = {1, 1, 13})
public interface ICharge extends BaseInterface {
    void closeUsbCharge() throws Exception;

    @NotNull
    String getChargeLevel();

    boolean getChargeState();

    void openUsbCharge() throws Exception;
}
