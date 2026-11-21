package com.unisoc.engineermode.core;

import android.content.Context;
import com.unisoc.engineermode.core.common.CommonKt;
import com.unisoc.engineermode.core.factory.ImplementationFactory;
import com.unisoc.engineermode.core.impl.connectivity.ConnectivityApiImpl;
import com.unisoc.engineermode.core.impl.debuglog.DebugLogApiImpl;
import com.unisoc.engineermode.core.impl.hardware.HardwareApiImpl;
import com.unisoc.engineermode.core.impl.telephony.TelephonyApiImpl;
import com.unisoc.engineermode.core.intf.IConnectivityApi;
import com.unisoc.engineermode.core.intf.IDebugLogApi;
import com.unisoc.engineermode.core.intf.IHardwareApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CoreApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0007R\u001a\u0010\u0003\u001a\u00020\u00048FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\t8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u0002\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000e8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u000f\u0010\u0002\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u00138FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u0002\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001b"}, d2 = {"Lcom/unisoc/engineermode/core/CoreApi;", "", "()V", "connectivityApi", "Lcom/unisoc/engineermode/core/intf/IConnectivityApi;", "connectivityApi$annotations", "getConnectivityApi", "()Lcom/unisoc/engineermode/core/intf/IConnectivityApi;", "debugLogApi", "Lcom/unisoc/engineermode/core/intf/IDebugLogApi;", "debugLogApi$annotations", "getDebugLogApi", "()Lcom/unisoc/engineermode/core/intf/IDebugLogApi;", "hardwareApi", "Lcom/unisoc/engineermode/core/intf/IHardwareApi;", "hardwareApi$annotations", "getHardwareApi", "()Lcom/unisoc/engineermode/core/intf/IHardwareApi;", "telephonyApi", "Lcom/unisoc/engineermode/core/intf/ITelephonyApi;", "telephonyApi$annotations", "getTelephonyApi", "()Lcom/unisoc/engineermode/core/intf/ITelephonyApi;", "init", "", "ctx", "Landroid/content/Context;", "name"}, k = 1, mv = {1, 1, 13})
public final class CoreApi {
    public static final CoreApi INSTANCE = new CoreApi();

    @JvmStatic
    public static /* synthetic */ void connectivityApi$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void debugLogApi$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void hardwareApi$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void telephonyApi$annotations() {
    }

    private CoreApi() {
    }

    @NotNull
    public static final ITelephonyApi getTelephonyApi() {
        ITelephonyApi telephonyApiImpl = TelephonyApiImpl.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(telephonyApiImpl, "TelephonyApiImpl.getInstance()");
        return telephonyApiImpl;
    }

    @NotNull
    public static final IDebugLogApi getDebugLogApi() {
        return DebugLogApiImpl.INSTANCE;
    }

    @NotNull
    public static final IConnectivityApi getConnectivityApi() {
        IConnectivityApi connectivityApiImpl = ConnectivityApiImpl.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(connectivityApiImpl, "ConnectivityApiImpl.getInstance()");
        return connectivityApiImpl;
    }

    @NotNull
    public static final IHardwareApi getHardwareApi() {
        return HardwareApiImpl.INSTANCE;
    }

    @JvmStatic
    public static final void init(@NotNull Context ctx) {
        Intrinsics.checkParameterIsNotNull(ctx, "ctx");
        CommonKt.setAppCtx(ctx);
        ImplementationFactory.INSTANCE.init(ctx);
    }
}
