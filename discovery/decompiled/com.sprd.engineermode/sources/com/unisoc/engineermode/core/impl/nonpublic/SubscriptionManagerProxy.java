package com.unisoc.engineermode.core.impl.nonpublic;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import com.unisoc.engineermode.core.common.CommonKt;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: SubscriptionManagerProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0006H\u0007J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0011\u001a\u00020\u0006H\u0007J\b\u0010\u0012\u001a\u00020\u0006H\u0007J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0014\u001a\u00020\u0006H\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0014\u001a\u00020\u0006H\u0007J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0006H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/SubscriptionManagerProxy;", "", "()V", "CONTENT_URI", "Landroid/net/Uri;", "INVALID_SIM_SLOT_INDEX", "", "SIM_SLOT_INDEX", "", "subscriptionManagerObject", "Landroid/telephony/SubscriptionManager;", "getActiveSubscriptionIdList", "", "getActiveSubscriptionInfo", "Landroid/telephony/SubscriptionInfo;", "subId", "getActiveSubscriptionInfoForSimSlotIndex", "slotIndex", "getDefaultDataPhoneId", "getSubId", "slotIdx", "isValidPhoneId", "", "isValidSubscriptionId", "name"}, k = 1, mv = {1, 1, 13})
public final class SubscriptionManagerProxy {

    @JvmField
    @NotNull
    public static final Uri CONTENT_URI;
    public static final SubscriptionManagerProxy INSTANCE = new SubscriptionManagerProxy();

    @JvmField
    public static final int INVALID_SIM_SLOT_INDEX;

    @JvmField
    @NotNull
    public static final String SIM_SLOT_INDEX;
    private static final SubscriptionManager subscriptionManagerObject;

    static {
        Object obj = SubscriptionManager.class.getField("SIM_SLOT_INDEX").get(null);
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        SIM_SLOT_INDEX = (String) obj;
        Object obj2 = SubscriptionManager.class.getField("INVALID_SIM_SLOT_INDEX").get(null);
        if (obj2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        INVALID_SIM_SLOT_INDEX = ((Integer) obj2).intValue();
        Object obj3 = SubscriptionManager.class.getField("CONTENT_URI").get(null);
        if (obj3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.net.Uri");
        }
        CONTENT_URI = (Uri) obj3;
        Object systemService = CommonKt.getAppCtx().getSystemService("telephony_subscription_service");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.telephony.SubscriptionManager");
        }
        subscriptionManagerObject = (SubscriptionManager) systemService;
    }

    private SubscriptionManagerProxy() {
    }

    @JvmStatic
    public static final int getDefaultDataPhoneId() {
        Object objInvoke = SubscriptionManager.class.getMethod("getDefaultDataPhoneId", new Class[0]).invoke(subscriptionManagerObject, new Object[0]);
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }

    @JvmStatic
    @Nullable
    public static final int[] getSubId(int slotIdx) {
        return (int[]) SubscriptionManager.class.getMethod("getSubId", Integer.TYPE).invoke(null, Integer.valueOf(slotIdx));
    }

    @JvmStatic
    public static final boolean isValidPhoneId(int slotIdx) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = SubscriptionManager.class.getMethod("isValidPhoneId", Integer.TYPE).invoke(null, Integer.valueOf(slotIdx));
        if (objInvoke != null) {
            return ((Boolean) objInvoke).booleanValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }

    @JvmStatic
    public static final boolean isValidSubscriptionId(int subId) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = SubscriptionManager.class.getMethod("isValidSubscriptionId", Integer.TYPE).invoke(null, Integer.valueOf(subId));
        if (objInvoke != null) {
            return ((Boolean) objInvoke).booleanValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }

    @JvmStatic
    @Nullable
    public static final SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int slotIndex) {
        return (SubscriptionInfo) SubscriptionManager.class.getMethod("getActiveSubscriptionInfoForSimSlotIndex", Integer.TYPE).invoke(subscriptionManagerObject, Integer.valueOf(slotIndex));
    }

    @JvmStatic
    @NotNull
    public static final int[] getActiveSubscriptionIdList() throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = SubscriptionManager.class.getMethod("getActiveSubscriptionIdList", new Class[0]).invoke(subscriptionManagerObject, new Object[0]);
        if (objInvoke != null) {
            return (int[]) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.IntArray");
    }

    @JvmStatic
    @SuppressLint({"MissingPermission"})
    @NotNull
    public static final SubscriptionInfo getActiveSubscriptionInfo(int subId) {
        SubscriptionInfo activeSubscriptionInfo = subscriptionManagerObject.getActiveSubscriptionInfo(subId);
        Intrinsics.checkExpressionValueIsNotNull(activeSubscriptionInfo, "subscriptionManagerObjec…veSubscriptionInfo(subId)");
        return activeSubscriptionInfo;
    }
}
