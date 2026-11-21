package com.unisoc.engineermode.core.impl.nonpublic;

import android.annotation.SuppressLint;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: SmsManagerExProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nJ\b\u0010\u000b\u001a\u00020\fH\u0007J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0001X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/SmsManagerExProxy;", "", "()V", "SMSMANAGEREX_CLASS_NAME", "", "smsManagerExClass", "Ljava/lang/Class;", "smsManagerExObject", "getSmscForSubscriber", "subId", "", "init", "", "setSmscForSubscriber", "", "smsc", "name"}, k = 1, mv = {1, 1, 13})
public final class SmsManagerExProxy {
    public static final SmsManagerExProxy INSTANCE;
    private static final String SMSMANAGEREX_CLASS_NAME = "android.telephony.SmsManagerEx";
    private static Class<?> smsManagerExClass;
    private static Object smsManagerExObject;

    static {
        SmsManagerExProxy smsManagerExProxy = new SmsManagerExProxy();
        INSTANCE = smsManagerExProxy;
        smsManagerExProxy.init();
    }

    private SmsManagerExProxy() {
    }

    @SuppressLint({"PrivateApi"})
    public final void init() throws IllegalAccessException, ClassNotFoundException, InvocationTargetException {
        Class<?> cls = Class.forName(SMSMANAGEREX_CLASS_NAME);
        Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(SMSMANAGEREX_CLASS_NAME)");
        smsManagerExClass = cls;
        Class<?> cls2 = smsManagerExClass;
        if (cls2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("smsManagerExClass");
        }
        Object objInvoke = cls2.getMethod("getDefault", new Class[0]).invoke(null, new Object[0]);
        if (objInvoke == null) {
            Intrinsics.throwNpe();
        }
        smsManagerExObject = objInvoke;
    }

    @NotNull
    public final String getSmscForSubscriber(int subId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> cls = smsManagerExClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("smsManagerExClass");
        }
        Method method = cls.getMethod("getSmscForSubscriber", Integer.TYPE);
        Object obj = smsManagerExObject;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("smsManagerExObject");
        }
        Object objInvoke = method.invoke(obj, Integer.valueOf(subId));
        if (objInvoke != null) {
            return (String) objInvoke;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    public final boolean setSmscForSubscriber(int subId, @NotNull String smsc) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(smsc, "smsc");
        Class<?> cls = smsManagerExClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("smsManagerExClass");
        }
        Method method = cls.getMethod("setSmscForSubscriber", Integer.TYPE, String.class);
        Object obj = smsManagerExObject;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("smsManagerExObject");
        }
        Object objInvoke = method.invoke(obj, Integer.valueOf(subId), smsc);
        if (objInvoke != null) {
            return ((Boolean) objInvoke).booleanValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }
}
