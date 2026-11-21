package com.unisoc.engineermode.core.intf;

import com.unisoc.engineermode.core.factory.BaseInterface;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: IHardwareApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\rH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005¨\u0006\u000f"}, d2 = {"Lcom/unisoc/engineermode/core/intf/ICoulometerPower;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "ccResultNewKernel", "", "getCcResultNewKernel", "()Ljava/lang/String;", "ccTestResult", "getCcTestResult", "ccTestVoltages", "getCcTestVoltages", "ccVoltNewKernel", "getCcVoltNewKernel", "writeOneToCCTestCmd", "", "writeZeroToCCTestCmd", "name"}, k = 1, mv = {1, 1, 13})
public interface ICoulometerPower extends BaseInterface {
    @NotNull
    String getCcResultNewKernel();

    @NotNull
    String getCcTestResult();

    @NotNull
    String getCcTestVoltages();

    @NotNull
    String getCcVoltNewKernel();

    void writeOneToCCTestCmd() throws Exception;

    void writeZeroToCCTestCmd() throws Exception;
}
