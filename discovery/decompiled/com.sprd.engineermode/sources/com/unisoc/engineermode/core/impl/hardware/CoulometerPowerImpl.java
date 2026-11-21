package com.unisoc.engineermode.core.impl.hardware;

import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ICoulometerPower;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CoulometerPowerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007R\u0014\u0010\f\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007¨\u0006\u0011"}, d2 = {"Lcom/unisoc/engineermode/core/impl/hardware/CoulometerPowerImpl;", "Lcom/unisoc/engineermode/core/intf/ICoulometerPower;", "()V", "TAG", "", "ccResultNewKernel", "getCcResultNewKernel", "()Ljava/lang/String;", "ccTestResult", "getCcTestResult", "ccTestVoltages", "getCcTestVoltages", "ccVoltNewKernel", "getCcVoltNewKernel", "writeOneToCCTestCmd", "", "writeZeroToCCTestCmd", "name"}, k = 1, mv = {1, 1, 13})
public final class CoulometerPowerImpl implements ICoulometerPower {
    public static final CoulometerPowerImpl INSTANCE = new CoulometerPowerImpl();
    private static final String TAG = "CoulometerPowerImpl";

    private CoulometerPowerImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ICoulometerPower
    @NotNull
    public String getCcTestResult() throws Exception {
        try {
            String ccTestSwitch = new PhaseCheckParse().getCcTestSwitch();
            Intrinsics.checkExpressionValueIsNotNull(ccTestSwitch, "PhaseCheckParse().ccTestSwitch");
            return ccTestSwitch;
        } catch (Exception e) {
            throw new OperationFailedException("get cc result file failed");
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ICoulometerPower
    @NotNull
    public String getCcTestVoltages() throws Exception {
        try {
            String ccTestVoltage = new PhaseCheckParse().getCcTestVoltage();
            Intrinsics.checkExpressionValueIsNotNull(ccTestVoltage, "PhaseCheckParse().ccTestVoltage");
            return ccTestVoltage;
        } catch (Exception e) {
            throw new OperationFailedException("get cc Voltage file failed");
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ICoulometerPower
    @NotNull
    public String getCcResultNewKernel() throws Exception {
        try {
            String ccEnergyNewKernel = new PhaseCheckParse().getCcEnergyNewKernel();
            Intrinsics.checkExpressionValueIsNotNull(ccEnergyNewKernel, "PhaseCheckParse().ccEnergyNewKernel");
            return ccEnergyNewKernel;
        } catch (Exception e) {
            throw new OperationFailedException("get cc Voltage file failed");
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ICoulometerPower
    @NotNull
    public String getCcVoltNewKernel() throws Exception {
        try {
            String ccVoltagesNewKernel = new PhaseCheckParse().getCcVoltagesNewKernel();
            Intrinsics.checkExpressionValueIsNotNull(ccVoltagesNewKernel, "PhaseCheckParse().ccVoltagesNewKernel");
            return ccVoltagesNewKernel;
        } catch (Exception e) {
            throw new OperationFailedException("get cc Voltage file failed");
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ICoulometerPower
    public void writeOneToCCTestCmd() throws Exception {
        boolean success = new PhaseCheckParse().writeChargeSwitch(1);
        if (!success) {
            throw new OperationFailedException("write 1 To CCTestCmd failed");
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ICoulometerPower
    public void writeZeroToCCTestCmd() throws Exception {
        boolean success = new PhaseCheckParse().writeChargeSwitch(0);
        if (!success) {
            throw new OperationFailedException("write 0 To CCTestCmd failed");
        }
    }
}
