package com.unisoc.engineermode.core.impl.nonpublic;

import android.annotation.SuppressLint;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: SystemPropertiesProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004H\u0007J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u000bH\u0007J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\rH\u0007J\b\u0010\u000e\u001a\u00020\u000fH\u0007J\u0018\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/SystemPropertiesProxy;", "", "()V", "SYSPROP_CLASS_NAME", "", "sysPropClass", "Ljava/lang/Class;", "get", "key", "defaultValue", "getBoolean", "", "getInt", "", "init", "", "set", "value", "name"}, k = 1, mv = {1, 1, 13})
public final class SystemPropertiesProxy {
    public static final SystemPropertiesProxy INSTANCE;
    private static final String SYSPROP_CLASS_NAME = "android.os.SystemProperties";
    private static Class<?> sysPropClass;

    static {
        SystemPropertiesProxy systemPropertiesProxy = new SystemPropertiesProxy();
        INSTANCE = systemPropertiesProxy;
        systemPropertiesProxy.init();
    }

    private SystemPropertiesProxy() {
    }

    @SuppressLint({"PrivateApi"})
    public final void init() throws ClassNotFoundException {
        Class<?> cls = Class.forName(SYSPROP_CLASS_NAME);
        Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(SYSPROP_CLASS_NAME)");
        sysPropClass = cls;
    }

    @JvmStatic
    @NotNull
    public static final String get(@NotNull String key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Class<?> cls = sysPropClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sysPropClass");
        }
        Object objInvoke = cls.getMethod("get", String.class).invoke(null, key);
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @JvmStatic
    @NotNull
    public static final String get(@NotNull String key, @NotNull String defaultValue) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        Class<?> cls = sysPropClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sysPropClass");
        }
        Object objInvoke = cls.getMethod("get", String.class, String.class).invoke(null, key, defaultValue);
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @JvmStatic
    public static final boolean getBoolean(@NotNull String key, boolean defaultValue) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Class<?> cls = sysPropClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sysPropClass");
        }
        Object objInvoke = cls.getMethod("getBoolean", String.class, Boolean.TYPE).invoke(null, key, Boolean.valueOf(defaultValue));
        if (objInvoke != null) {
            return ((Boolean) objInvoke).booleanValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }

    @JvmStatic
    public static final int getInt(@NotNull String key, int defaultValue) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Class<?> cls = sysPropClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sysPropClass");
        }
        Object objInvoke = cls.getMethod("getInt", String.class, Integer.TYPE).invoke(null, key, Integer.valueOf(defaultValue));
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }

    @JvmStatic
    public static final void set(@NotNull String key, @NotNull String value) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(value, "value");
        Class<?> cls = sysPropClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sysPropClass");
        }
        cls.getMethod("set", String.class, String.class).invoke(null, key, value);
    }
}
