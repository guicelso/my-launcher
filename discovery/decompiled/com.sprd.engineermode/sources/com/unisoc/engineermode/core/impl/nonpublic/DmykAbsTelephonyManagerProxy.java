package com.unisoc.engineermode.core.impl.nonpublic;

import android.content.Context;
import android.util.Log;
import com.unisoc.engineermode.core.common.CommonKt;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DmykAbsTelephonyManagerProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0001X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/DmykAbsTelephonyManagerProxy;", "", "()V", "DMYKABSTELMANAGER_CLASS_NAME", "", "TAG", "dmykAbsTelephonyManagerClass", "Ljava/lang/Class;", "dmykAbsTelephonyManagerObject", "getCdmaImsi", "subId", "", "getIccId", "init", "", "name"}, k = 1, mv = {1, 1, 13})
public final class DmykAbsTelephonyManagerProxy {
    private static final String DMYKABSTELMANAGER_CLASS_NAME = "com.dmyk.android.telephony.DmykAbsTelephonyManager";
    public static final DmykAbsTelephonyManagerProxy INSTANCE;
    private static final String TAG = "DmykAbsTelephonyManagerProxy";
    private static Class<?> dmykAbsTelephonyManagerClass;
    private static Object dmykAbsTelephonyManagerObject;

    static {
        DmykAbsTelephonyManagerProxy dmykAbsTelephonyManagerProxy = new DmykAbsTelephonyManagerProxy();
        INSTANCE = dmykAbsTelephonyManagerProxy;
        dmykAbsTelephonyManagerProxy.init();
    }

    private DmykAbsTelephonyManagerProxy() {
    }

    public final void init() throws IllegalAccessException, ClassNotFoundException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName(DMYKABSTELMANAGER_CLASS_NAME);
            Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(DMYKABSTELMANAGER_CLASS_NAME)");
            dmykAbsTelephonyManagerClass = cls;
            Class<?> cls2 = dmykAbsTelephonyManagerClass;
            if (cls2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dmykAbsTelephonyManagerClass");
            }
            Object objInvoke = cls2.getMethod("getDefault", Context.class).invoke(null, CommonKt.getAppCtx());
            if (objInvoke == null) {
                Intrinsics.throwNpe();
            }
            dmykAbsTelephonyManagerObject = objInvoke;
        } catch (ClassNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("no such class, ");
            Class<?> cls3 = dmykAbsTelephonyManagerClass;
            if (cls3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dmykAbsTelephonyManagerClass");
            }
            sb.append(cls3);
            Log.e(TAG, sb.toString());
            throw e;
        }
    }

    @Nullable
    public final String getCdmaImsi(int subId) throws NoSuchMethodException {
        Class<?> cls = dmykAbsTelephonyManagerClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dmykAbsTelephonyManagerClass");
        }
        Method method = cls.getMethod("getCdmaImsi", Integer.TYPE);
        Object obj = dmykAbsTelephonyManagerObject;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dmykAbsTelephonyManagerObject");
        }
        return (String) method.invoke(obj, Integer.valueOf(subId));
    }

    @Nullable
    public final String getIccId(int subId) throws NoSuchMethodException {
        Class<?> cls = dmykAbsTelephonyManagerClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dmykAbsTelephonyManagerClass");
        }
        Method method = cls.getMethod("getIccId", Integer.TYPE);
        Object obj = dmykAbsTelephonyManagerObject;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dmykAbsTelephonyManagerObject");
        }
        return (String) method.invoke(obj, Integer.valueOf(subId));
    }
}
