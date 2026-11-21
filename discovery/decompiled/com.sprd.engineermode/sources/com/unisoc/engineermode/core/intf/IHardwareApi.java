package com.unisoc.engineermode.core.intf;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: IHardwareApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH&¨\u0006\u000e"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IHardwareApi;", "", "antennaApi", "Lcom/unisoc/engineermode/core/intf/IAntenna;", "asdivApi", "Lcom/unisoc/engineermode/core/intf/IAsdiv;", "chargeApi", "Lcom/unisoc/engineermode/core/intf/ICharge;", "coulometerPowerApi", "Lcom/unisoc/engineermode/core/intf/ICoulometerPower;", "sensePll", "Lcom/unisoc/engineermode/core/intf/ISensePll;", "thermalApi", "Lcom/unisoc/engineermode/core/intf/IThermal;", "name"}, k = 1, mv = {1, 1, 13})
public interface IHardwareApi {
    @NotNull
    IAntenna antennaApi();

    @NotNull
    IAsdiv asdivApi();

    @NotNull
    ICharge chargeApi();

    @NotNull
    ICoulometerPower coulometerPowerApi();

    @NotNull
    ISensePll sensePll();

    @NotNull
    IThermal thermalApi();
}
