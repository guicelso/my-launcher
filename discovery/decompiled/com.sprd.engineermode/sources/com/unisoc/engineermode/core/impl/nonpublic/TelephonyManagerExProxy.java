package com.unisoc.engineermode.core.impl.nonpublic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.CellLocation;
import android.util.Log;
import com.unisoc.engineermode.core.common.CommonKt;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TelephonyManagerExProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\b\u0010\r\u001a\u00020\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0001X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/TelephonyManagerExProxy;", "", "()V", "CLASS_NAME", "", "TAG", "tmExClass", "Ljava/lang/Class;", "tmExObject", "getCellLocationForPhone", "Landroid/telephony/CellLocation;", "phoneId", "", "init", "", "name"}, k = 1, mv = {1, 1, 13})
public final class TelephonyManagerExProxy {
    private static final String CLASS_NAME = "android.telephony.TelephonyManagerEx";
    public static final TelephonyManagerExProxy INSTANCE;
    private static final String TAG = "TELEPHONYMANAGEREXPROXY";
    private static Class<?> tmExClass;
    private static Object tmExObject;

    static {
        TelephonyManagerExProxy telephonyManagerExProxy = new TelephonyManagerExProxy();
        INSTANCE = telephonyManagerExProxy;
        telephonyManagerExProxy.init();
    }

    private TelephonyManagerExProxy() {
    }

    @SuppressLint({"PrivateApi"})
    public final void init() throws IllegalAccessException, ClassNotFoundException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName(CLASS_NAME);
            Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(CLASS_NAME)");
            tmExClass = cls;
            Class<?> cls2 = tmExClass;
            if (cls2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tmExClass");
            }
            Object objInvoke = cls2.getMethod("from", Context.class).invoke(null, CommonKt.getAppCtx());
            if (objInvoke == null) {
                Intrinsics.throwNpe();
            }
            tmExObject = objInvoke;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "no such class, android.telephony.TelephonyManagerEx");
            throw e;
        }
    }

    @JvmStatic
    @Nullable
    public static final CellLocation getCellLocationForPhone(int phoneId) throws NoSuchMethodException {
        Class<?> cls = tmExClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tmExClass");
        }
        Method method = cls.getMethod("getCellLocationForPhone", Integer.TYPE);
        Object obj = tmExObject;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tmExObject");
        }
        return (CellLocation) method.invoke(obj, Integer.valueOf(phoneId));
    }
}
