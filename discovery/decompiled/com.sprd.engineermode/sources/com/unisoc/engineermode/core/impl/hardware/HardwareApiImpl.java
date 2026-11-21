package com.unisoc.engineermode.core.impl.hardware;

import com.unisoc.engineermode.core.factory.BaseInterface;
import com.unisoc.engineermode.core.factory.ImplementationFactory;
import com.unisoc.engineermode.core.intf.IAntenna;
import com.unisoc.engineermode.core.intf.IAsdiv;
import com.unisoc.engineermode.core.intf.ICharge;
import com.unisoc.engineermode.core.intf.ICoulometerPower;
import com.unisoc.engineermode.core.intf.IHardwareApi;
import com.unisoc.engineermode.core.intf.ISensePll;
import com.unisoc.engineermode.core.intf.IThermal;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: HardwareApiImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/unisoc/engineermode/core/impl/hardware/HardwareApiImpl;", "Lcom/unisoc/engineermode/core/intf/IHardwareApi;", "()V", "antennaApi", "Lcom/unisoc/engineermode/core/intf/IAntenna;", "asdivApi", "Lcom/unisoc/engineermode/core/intf/IAsdiv;", "chargeApi", "Lcom/unisoc/engineermode/core/intf/ICharge;", "coulometerPowerApi", "Lcom/unisoc/engineermode/core/intf/ICoulometerPower;", "sensePll", "Lcom/unisoc/engineermode/core/intf/ISensePll;", "thermalApi", "Lcom/unisoc/engineermode/core/intf/IThermal;", "name"}, k = 1, mv = {1, 1, 13})
public final class HardwareApiImpl implements IHardwareApi {
    public static final HardwareApiImpl INSTANCE = new HardwareApiImpl();

    private HardwareApiImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.IHardwareApi
    @NotNull
    public ICharge chargeApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(ICharge.class));
        if (baseInterfaceCreate != null) {
            return (ICharge) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.ICharge");
    }

    @Override // com.unisoc.engineermode.core.intf.IHardwareApi
    @NotNull
    public ISensePll sensePll() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(ISensePll.class));
        if (baseInterfaceCreate != null) {
            return (ISensePll) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.ISensePll");
    }

    @Override // com.unisoc.engineermode.core.intf.IHardwareApi
    @NotNull
    public ICoulometerPower coulometerPowerApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(ICoulometerPower.class));
        if (baseInterfaceCreate != null) {
            return (ICoulometerPower) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.ICoulometerPower");
    }

    @Override // com.unisoc.engineermode.core.intf.IHardwareApi
    @NotNull
    public IAntenna antennaApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(IAntenna.class));
        if (baseInterfaceCreate != null) {
            return (IAntenna) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.IAntenna");
    }

    @Override // com.unisoc.engineermode.core.intf.IHardwareApi
    @NotNull
    public IAsdiv asdivApi() {
        IAsdiv iAsdiv = AsdivImpl.AsdivImplHolder.INSTANCE;
        Intrinsics.checkExpressionValueIsNotNull(iAsdiv, "AsdivImpl.AsdivImplHolder.INSTANCE");
        return iAsdiv;
    }

    @Override // com.unisoc.engineermode.core.intf.IHardwareApi
    @NotNull
    public IThermal thermalApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(IThermal.class));
        if (baseInterfaceCreate != null) {
            return (IThermal) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.IThermal");
    }
}
