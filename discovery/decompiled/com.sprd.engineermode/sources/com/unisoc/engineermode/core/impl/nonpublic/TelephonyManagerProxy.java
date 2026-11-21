package com.unisoc.engineermode.core.impl.nonpublic;

import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import com.unisoc.engineermode.core.common.CommonKt;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TelephonyManagerProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004H\u0007J\n\u0010\r\u001a\u0004\u0018\u00010\bH\u0007J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0004H\u0007J\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0004H\u0007J\b\u0010\u0014\u001a\u00020\u0004H\u0007J\b\u0010\u0015\u001a\u00020\u0006H\u0007J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0004H\u0007J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0007J\u0010\u0010\u001b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0006\u0010\u001c\u001a\u00020\u0006J\u0010\u0010\u001d\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\f\u001a\u00020\u0004H\u0007J\b\u0010 \u001a\u00020\u001fH\u0007J\u0010\u0010!\u001a\u00020\u001f2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u0004H\u0007J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001fH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/TelephonyManagerProxy;", "", "()V", "NETWORK_TYPE_LTE_CA", "", "telephonyManagerObject", "Landroid/telephony/TelephonyManager;", "getCdmaPrlVersion", "", "subId", "getCurrentPhoneType", "getCurrentPhoneTypeForSlot", "slotIndex", "getEsn", "getNetworkOperatorForPhone", "phoneId", "getNetworkOperatorName", "getNetworkType", "getNetworkTypeName", "type", "getPhoneCount", "getService", "getServiceStateForSubscriber", "Landroid/telephony/ServiceState;", "getSimOperatorNumericForPhone", "getSimState", "simIdx", "getSubscriberId", "getTelephonyManager", "getVoiceNetworkType", "hasIccCard", "", "isMultiSimEnabled", "isNetworkRoaming", "isSimExist", "simIndex", "setNetworkRoaming", "", "isRoaming", "name"}, k = 1, mv = {1, 1, 13})
public final class TelephonyManagerProxy {
    public static final TelephonyManagerProxy INSTANCE = new TelephonyManagerProxy();

    @JvmField
    public static final int NETWORK_TYPE_LTE_CA;
    private static TelephonyManager telephonyManagerObject;

    static {
        Object obj = TelephonyManager.class.getField("NETWORK_TYPE_LTE_CA").get(null);
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        NETWORK_TYPE_LTE_CA = ((Integer) obj).intValue();
        Object systemService = CommonKt.getAppCtx().getSystemService("phone");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.telephony.TelephonyManager");
        }
        telephonyManagerObject = (TelephonyManager) systemService;
    }

    private TelephonyManagerProxy() {
    }

    @NotNull
    public final TelephonyManager getTelephonyManager() {
        return telephonyManagerObject;
    }

    @JvmStatic
    @NotNull
    public static final TelephonyManager getService() {
        return telephonyManagerObject;
    }

    @JvmStatic
    public static final int getPhoneCount() {
        return telephonyManagerObject.getPhoneCount();
    }

    @JvmStatic
    public static final int getSimState(int simIdx) {
        return telephonyManagerObject.getSimState(simIdx);
    }

    @JvmStatic
    public static final void setNetworkRoaming(boolean isRoaming) throws IllegalAccessException, InvocationTargetException {
        TelephonyManager.class.getMethod("setNetworkRoaming", Boolean.TYPE).invoke(telephonyManagerObject, Boolean.valueOf(isRoaming));
    }

    @JvmStatic
    public static final boolean isSimExist(int simIndex) {
        return getSimState(simIndex) == 5;
    }

    @JvmStatic
    public static final int getNetworkType(int subId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getNetworkType", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(subId));
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }

    @JvmStatic
    @NotNull
    public static final String getNetworkTypeName(int type) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getNetworkTypeName", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(type));
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @JvmStatic
    public static final int getVoiceNetworkType(int subId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getVoiceNetworkType", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(subId));
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }

    @JvmStatic
    @NotNull
    public static final String getNetworkOperatorName(int subId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getNetworkOperatorName", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(subId));
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @JvmStatic
    @NotNull
    public static final String getNetworkOperatorForPhone(int phoneId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getNetworkOperatorForPhone", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(phoneId));
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @JvmStatic
    public static final boolean isNetworkRoaming(int subId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("isNetworkRoaming", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(subId));
        if (objInvoke != null) {
            return ((Boolean) objInvoke).booleanValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }

    @JvmStatic
    public static final boolean hasIccCard(int slotIndex) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("hasIccCard", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(slotIndex));
        if (objInvoke != null) {
            return ((Boolean) objInvoke).booleanValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }

    @JvmStatic
    public static final boolean isMultiSimEnabled() throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("isMultiSimEnabled", new Class[0]).invoke(telephonyManagerObject, new Object[0]);
        if (objInvoke != null) {
            return ((Boolean) objInvoke).booleanValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }

    @JvmStatic
    public static final int getCurrentPhoneTypeForSlot(int slotIndex) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getCurrentPhoneTypeForSlot", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(slotIndex));
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }

    @JvmStatic
    @NotNull
    public static final String getSubscriberId(int subId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getSubscriberId", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(subId));
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @JvmStatic
    @NotNull
    public static final String getSimOperatorNumericForPhone(int phoneId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getSimOperatorNumericForPhone", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(phoneId));
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @JvmStatic
    public static final int getCurrentPhoneType(int subId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getCurrentPhoneType", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(subId));
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }

    @JvmStatic
    @NotNull
    public static final ServiceState getServiceStateForSubscriber(int subId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getServiceStateForSubscriber", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(subId));
        if (objInvoke != null) {
            return (ServiceState) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.telephony.ServiceState");
    }

    @JvmStatic
    @NotNull
    public static final String getCdmaPrlVersion(int subId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = TelephonyManager.class.getMethod("getCdmaPrlVersion", Integer.TYPE).invoke(telephonyManagerObject, Integer.valueOf(subId));
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @JvmStatic
    @Nullable
    public static final String getEsn() {
        return (String) TelephonyManager.class.getMethod("getEsn", new Class[0]).invoke(telephonyManagerObject, new Object[0]);
    }
}
