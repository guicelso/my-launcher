package com.unisoc.engineermode.core.impl.debuglog;

import com.unisoc.engineermode.core.factory.BaseInterface;
import com.unisoc.engineermode.core.factory.ImplementationFactory;
import com.unisoc.engineermode.core.intf.IAiIp;
import com.unisoc.engineermode.core.intf.IAtDiag;
import com.unisoc.engineermode.core.intf.ICabc;
import com.unisoc.engineermode.core.intf.IDebugLogApi;
import com.unisoc.engineermode.core.intf.IGcfTest;
import com.unisoc.engineermode.core.intf.IJeitaControl;
import com.unisoc.engineermode.core.intf.IPhoneInfo;
import com.unisoc.engineermode.core.intf.ISMSCenterNumber;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: DebugLogApiImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lcom/unisoc/engineermode/core/impl/debuglog/DebugLogApiImpl;", "Lcom/unisoc/engineermode/core/intf/IDebugLogApi;", "()V", "aiIpApi", "Lcom/unisoc/engineermode/core/intf/IAiIp;", "atDiagApi", "Lcom/unisoc/engineermode/core/intf/IAtDiag;", "cabcApi", "Lcom/unisoc/engineermode/core/intf/ICabc;", "gcfTestApi", "Lcom/unisoc/engineermode/core/intf/IGcfTest;", "jeitaControlApi", "Lcom/unisoc/engineermode/core/intf/IJeitaControl;", "phoneInfoApi", "Lcom/unisoc/engineermode/core/intf/IPhoneInfo;", "smsCenterNumberApi", "Lcom/unisoc/engineermode/core/intf/ISMSCenterNumber;", "name"}, k = 1, mv = {1, 1, 13})
public final class DebugLogApiImpl implements IDebugLogApi {
    public static final DebugLogApiImpl INSTANCE = new DebugLogApiImpl();

    private DebugLogApiImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.IDebugLogApi
    @NotNull
    public IAiIp aiIpApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(IAiIp.class));
        if (baseInterfaceCreate != null) {
            return (IAiIp) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.IAiIp");
    }

    @Override // com.unisoc.engineermode.core.intf.IDebugLogApi
    @NotNull
    public ISMSCenterNumber smsCenterNumberApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(ISMSCenterNumber.class));
        if (baseInterfaceCreate != null) {
            return (ISMSCenterNumber) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.ISMSCenterNumber");
    }

    @Override // com.unisoc.engineermode.core.intf.IDebugLogApi
    @NotNull
    public IGcfTest gcfTestApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(IGcfTest.class));
        if (baseInterfaceCreate != null) {
            return (IGcfTest) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.IGcfTest");
    }

    @Override // com.unisoc.engineermode.core.intf.IDebugLogApi
    @NotNull
    public IPhoneInfo phoneInfoApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(IPhoneInfo.class));
        if (baseInterfaceCreate != null) {
            return (IPhoneInfo) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.IPhoneInfo");
    }

    @Override // com.unisoc.engineermode.core.intf.IDebugLogApi
    @NotNull
    public IJeitaControl jeitaControlApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(IJeitaControl.class));
        if (baseInterfaceCreate != null) {
            return (IJeitaControl) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.IJeitaControl");
    }

    @Override // com.unisoc.engineermode.core.intf.IDebugLogApi
    @NotNull
    public IAtDiag atDiagApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(IAtDiag.class));
        if (baseInterfaceCreate != null) {
            return (IAtDiag) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.IAtDiag");
    }

    @Override // com.unisoc.engineermode.core.intf.IDebugLogApi
    @NotNull
    public ICabc cabcApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(ICabc.class));
        if (baseInterfaceCreate != null) {
            return (ICabc) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.intf.ICabc");
    }
}
