package com.unisoc.engineermode.core.impl.nonpublic;

import android.annotation.SuppressLint;
import android.content.Context;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ImsManagerProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0007J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\r\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/ImsManagerProxy;", "", "()V", "IMSMANAGER_CLASS_NAME", "", "imsManagerClass", "Ljava/lang/Class;", "init", "", "isWfcEnabledByPlatform", "", "ctx", "Landroid/content/Context;", "setEnhanced4gLteModeSetting", "enable", "name"}, k = 1, mv = {1, 1, 13})
public final class ImsManagerProxy {
    private static final String IMSMANAGER_CLASS_NAME = "com.android.ims.ImsManager";
    public static final ImsManagerProxy INSTANCE;
    private static Class<?> imsManagerClass;

    static {
        ImsManagerProxy imsManagerProxy = new ImsManagerProxy();
        INSTANCE = imsManagerProxy;
        imsManagerProxy.init();
    }

    private ImsManagerProxy() {
    }

    @SuppressLint({"PrivateApi"})
    public final void init() throws ClassNotFoundException {
        Class<?> cls = Class.forName(IMSMANAGER_CLASS_NAME);
        Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(IMSMANAGER_CLASS_NAME)");
        imsManagerClass = cls;
    }

    public final void setEnhanced4gLteModeSetting(@NotNull Context ctx, boolean enable) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(ctx, "ctx");
        Class<?> cls = imsManagerClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imsManagerClass");
        }
        cls.getMethod("setEnhanced4gLteModeSetting", Context.class, Boolean.TYPE).invoke(null, ctx, Boolean.valueOf(enable));
    }

    public final boolean isWfcEnabledByPlatform(@NotNull Context ctx) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(ctx, "ctx");
        Class<?> cls = imsManagerClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imsManagerClass");
        }
        Object objInvoke = cls.getMethod("isWfcEnabledByPlatform", Context.class).invoke(null, ctx);
        if (objInvoke != null) {
            return ((Boolean) objInvoke).booleanValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }
}
