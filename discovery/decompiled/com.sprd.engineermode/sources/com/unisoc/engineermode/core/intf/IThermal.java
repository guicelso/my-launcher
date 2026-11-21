package com.unisoc.engineermode.core.intf;

import com.unisoc.engineermode.core.factory.BaseInterface;
import kotlin.Metadata;

/* JADX INFO: compiled from: IHardwareApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\bH&J\b\u0010\n\u001a\u00020\bH&J\b\u0010\u000b\u001a\u00020\bH&J\b\u0010\f\u001a\u00020\bH&J\b\u0010\r\u001a\u00020\bH&J\b\u0010\u000e\u001a\u00020\bH&J\b\u0010\u000f\u001a\u00020\bH&¨\u0006\u0010"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IThermal;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "getThermalChargeState", "", "getThermalIpaState", "getThermalPaState", "getThermalState", "switchThermalChargeOff", "", "switchThermalChargeOn", "switchThermalIpaOff", "switchThermalIpaOn", "switchThermalOff", "switchThermalOn", "switchThermalPaOff", "switchThermalPaOn", "name"}, k = 1, mv = {1, 1, 13})
public interface IThermal extends BaseInterface {
    boolean getThermalChargeState();

    boolean getThermalIpaState();

    boolean getThermalPaState();

    boolean getThermalState();

    void switchThermalChargeOff();

    void switchThermalChargeOn();

    void switchThermalIpaOff();

    void switchThermalIpaOn();

    void switchThermalOff();

    void switchThermalOn();

    void switchThermalPaOff();

    void switchThermalPaOn();
}
