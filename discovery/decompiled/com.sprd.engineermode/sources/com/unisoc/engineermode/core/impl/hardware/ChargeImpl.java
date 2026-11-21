package com.unisoc.engineermode.core.impl.hardware;

import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ICharge;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ChargeImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\tH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0011"}, d2 = {"Lcom/unisoc/engineermode/core/impl/hardware/ChargeImpl;", "Lcom/unisoc/engineermode/core/intf/ICharge;", "()V", "TAG", "", "chargeLevel", "getChargeLevel", "()Ljava/lang/String;", "chargeState", "", "getChargeState", "()Z", "closeUsbCharge", "", "openUsbCharge", "switchCharge", "isOn", "name"}, k = 1, mv = {1, 1, 13})
public final class ChargeImpl implements ICharge {
    public static final ChargeImpl INSTANCE = new ChargeImpl();
    private static final String TAG = "EM-CHARGE";

    private ChargeImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ICharge
    public boolean getChargeState() throws Exception {
        try {
            String chargeStatus = new PhaseCheckParse().readChargeState();
            if (chargeStatus == null) {
                Intrinsics.throwNpe();
            }
            return StringsKt.contains$default((CharSequence) chargeStatus, (CharSequence) "1", false, 2, (Object) null);
        } catch (Exception e) {
            throw new OperationFailedException("read charge state file failed");
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ICharge
    @NotNull
    public String getChargeLevel() throws Exception {
        try {
            String chargeLevel = new PhaseCheckParse().readChargeLevel();
            Intrinsics.checkExpressionValueIsNotNull(chargeLevel, "chargeLevel");
            return chargeLevel;
        } catch (Exception e) {
            throw new OperationFailedException("read charge level file failed");
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ICharge
    public void openUsbCharge() throws Exception {
        switchCharge(true);
    }

    @Override // com.unisoc.engineermode.core.intf.ICharge
    public void closeUsbCharge() throws Exception {
        switchCharge(false);
    }

    private final void switchCharge(boolean isOn) throws Exception {
        boolean success;
        if (isOn) {
            success = new PhaseCheckParse().writeChargeSwitch(0);
        } else {
            success = new PhaseCheckParse().writeChargeSwitch(1);
        }
        if (!success) {
            throw new OperationFailedException("switch charge failed");
        }
    }
}
