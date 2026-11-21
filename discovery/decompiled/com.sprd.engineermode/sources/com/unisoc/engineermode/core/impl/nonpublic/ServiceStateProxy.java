package com.unisoc.engineermode.core.impl.nonpublic;

import android.telephony.ServiceState;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ServiceStateProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\b"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/ServiceStateProxy;", "", "()V", "getDataNetworkType", "", "ss", "Landroid/telephony/ServiceState;", "getVoiceRegState", "name"}, k = 1, mv = {1, 1, 13})
public final class ServiceStateProxy {
    public static final ServiceStateProxy INSTANCE = new ServiceStateProxy();

    private ServiceStateProxy() {
    }

    @JvmStatic
    public static final int getDataNetworkType(@NotNull ServiceState ss) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(ss, "ss");
        Object objInvoke = ServiceState.class.getMethod("getDataNetworkType", new Class[0]).invoke(ss, new Object[0]);
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }

    @JvmStatic
    public static final int getVoiceRegState(@NotNull ServiceState ss) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(ss, "ss");
        Object objInvoke = ServiceState.class.getMethod("getVoiceRegState", new Class[0]).invoke(ss, new Object[0]);
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }
}
