package com.unisoc.engineermode.core.impl.nonpublic;

import android.annotation.SuppressLint;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: TelephonyPropertiesProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/TelephonyPropertiesProxy;", "", "()V", "PROPERTY_BASEBAND_VERSION", "", "PROPERTY_ICC_OPERATOR_NUMERIC", "TP_CLASS_NAME", "tpClass", "Ljava/lang/Class;", "init", "", "name"}, k = 1, mv = {1, 1, 13})
public final class TelephonyPropertiesProxy {
    public static final TelephonyPropertiesProxy INSTANCE;

    @JvmField
    @NotNull
    public static final String PROPERTY_BASEBAND_VERSION;

    @JvmField
    @NotNull
    public static final String PROPERTY_ICC_OPERATOR_NUMERIC;
    private static final String TP_CLASS_NAME = "com.android.internal.telephony.TelephonyProperties";
    private static Class<?> tpClass;

    static {
        TelephonyPropertiesProxy telephonyPropertiesProxy = new TelephonyPropertiesProxy();
        INSTANCE = telephonyPropertiesProxy;
        telephonyPropertiesProxy.init();
        Class<?> cls = tpClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tpClass");
        }
        Object obj = cls.getField("PROPERTY_BASEBAND_VERSION").get(null);
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        PROPERTY_BASEBAND_VERSION = (String) obj;
        Class<?> cls2 = tpClass;
        if (cls2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tpClass");
        }
        Object obj2 = cls2.getField("PROPERTY_ICC_OPERATOR_NUMERIC").get(null);
        if (obj2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        PROPERTY_ICC_OPERATOR_NUMERIC = (String) obj2;
    }

    private TelephonyPropertiesProxy() {
    }

    @SuppressLint({"PrivateApi"})
    public final void init() throws ClassNotFoundException {
        Class<?> cls = Class.forName(TP_CLASS_NAME);
        Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(TP_CLASS_NAME)");
        tpClass = cls;
    }
}
