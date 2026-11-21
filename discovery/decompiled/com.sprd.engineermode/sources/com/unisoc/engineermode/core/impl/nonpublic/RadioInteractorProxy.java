package com.unisoc.engineermode.core.impl.nonpublic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.unisoc.engineermode.core.common.CommonKt;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RadioInteractorProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0007J\b\u0010\f\u001a\u00020\rH\u0007J\u0018\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0001X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/RadioInteractorProxy;", "", "()V", "CLASS_NAME", "", "TAG", "radioInteractorClass", "Ljava/lang/Class;", "radioInteractorObject", "getPreferredNetworkType", "", "slot", "init", "", "setPreferredNetworkType", "networkType", "name"}, k = 1, mv = {1, 1, 13})
public final class RadioInteractorProxy {
    private static final String CLASS_NAME = "com.android.sprd.telephony.RadioInteractor";
    public static final RadioInteractorProxy INSTANCE;
    private static final String TAG = "RADIOINTERACTORPROXY";
    private static Class<?> radioInteractorClass;
    private static Object radioInteractorObject;

    static {
        RadioInteractorProxy radioInteractorProxy = new RadioInteractorProxy();
        INSTANCE = radioInteractorProxy;
        radioInteractorProxy.init();
    }

    private RadioInteractorProxy() {
    }

    @SuppressLint({"PrivateApi"})
    public final void init() throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName(CLASS_NAME);
            Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(CLASS_NAME)");
            radioInteractorClass = cls;
            Class<?> cls2 = radioInteractorClass;
            if (cls2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("radioInteractorClass");
            }
            Object objNewInstance = cls2.getConstructor(Context.class).newInstance(CommonKt.getAppCtx());
            Intrinsics.checkExpressionValueIsNotNull(objNewInstance, "radioInteractorClass.get…     .newInstance(appCtx)");
            radioInteractorObject = objNewInstance;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "no such class, com.android.sprd.telephony.RadioInteractor");
            throw e;
        }
    }

    @JvmStatic
    public static final int setPreferredNetworkType(int slot, int networkType) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> cls = radioInteractorClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("radioInteractorClass");
        }
        Method method = cls.getMethod("setPreferredNetworkType", Integer.TYPE, Integer.TYPE);
        Object obj = radioInteractorObject;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("radioInteractorObject");
        }
        Object objInvoke = method.invoke(obj, Integer.valueOf(slot), Integer.valueOf(networkType));
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }

    @JvmStatic
    public static final int getPreferredNetworkType(int slot) {
        Class<?> cls = radioInteractorClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("radioInteractorClass");
        }
        Method method = cls.getMethod("getPreferredNetworkType", Integer.TYPE);
        Object obj = radioInteractorObject;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("radioInteractorObject");
        }
        Object objInvoke = method.invoke(obj, Integer.valueOf(slot));
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }
}
