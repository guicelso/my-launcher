package com.unisoc.engineermode.core.impl.nonpublic;

import android.annotation.SuppressLint;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: EnvironmentExProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/EnvironmentExProxy;", "", "()V", "ENVEX_CLASS_NAME", "", "environmentExClass", "Ljava/lang/Class;", "getInternalStoragePath", "Ljava/io/File;", "init", "", "name"}, k = 1, mv = {1, 1, 13})
public final class EnvironmentExProxy {
    private static final String ENVEX_CLASS_NAME = "android.os.EnvironmentEx";
    public static final EnvironmentExProxy INSTANCE;
    private static Class<?> environmentExClass;

    static {
        EnvironmentExProxy environmentExProxy = new EnvironmentExProxy();
        INSTANCE = environmentExProxy;
        environmentExProxy.init();
    }

    private EnvironmentExProxy() {
    }

    @SuppressLint({"PrivateApi"})
    public final void init() throws ClassNotFoundException {
        Class<?> cls = Class.forName(ENVEX_CLASS_NAME);
        Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(ENVEX_CLASS_NAME)");
        environmentExClass = cls;
    }

    @JvmStatic
    @NotNull
    public static final File getInternalStoragePath() throws IllegalAccessException, InvocationTargetException {
        Class<?> cls = environmentExClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("environmentExClass");
        }
        Object objInvoke = cls.getMethod("getInternalStoragePath", new Class[0]).invoke(null, new Object[0]);
        if (objInvoke != null) {
            return (File) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.io.File");
    }
}
