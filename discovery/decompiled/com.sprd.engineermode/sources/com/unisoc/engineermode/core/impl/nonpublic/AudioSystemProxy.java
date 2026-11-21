package com.unisoc.engineermode.core.impl.nonpublic;

import android.annotation.SuppressLint;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AudioSystemProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0007J\u0018\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/AudioSystemProxy;", "", "()V", "AS_CLASS_NAME", "", "DEVICE_STATE_AVAILABLE", "", "DEVICE_STATE_UNAVAILABLE", "FORCE_NONE", "FORCE_SPEAKER", "FOR_FM", "asClass", "Ljava/lang/Class;", "init", "", "setForceUse", "usage", "config", "name"}, k = 1, mv = {1, 1, 13})
public final class AudioSystemProxy {
    private static final String AS_CLASS_NAME = "android.media.AudioSystem";

    @JvmField
    public static final int DEVICE_STATE_AVAILABLE;

    @JvmField
    public static final int DEVICE_STATE_UNAVAILABLE;

    @JvmField
    public static final int FORCE_NONE;

    @JvmField
    public static final int FORCE_SPEAKER;

    @JvmField
    public static final int FOR_FM;
    public static final AudioSystemProxy INSTANCE;
    private static Class<?> asClass;

    static {
        AudioSystemProxy audioSystemProxy = new AudioSystemProxy();
        INSTANCE = audioSystemProxy;
        audioSystemProxy.init();
        Class<?> cls = asClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("asClass");
        }
        Object obj = cls.getField("DEVICE_STATE_UNAVAILABLE").get(null);
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        DEVICE_STATE_UNAVAILABLE = ((Integer) obj).intValue();
        Class<?> cls2 = asClass;
        if (cls2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("asClass");
        }
        Object obj2 = cls2.getField("DEVICE_STATE_AVAILABLE").get(null);
        if (obj2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        DEVICE_STATE_AVAILABLE = ((Integer) obj2).intValue();
        Class<?> cls3 = asClass;
        if (cls3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("asClass");
        }
        Object obj3 = cls3.getField("FOR_FM").get(null);
        if (obj3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        FOR_FM = ((Integer) obj3).intValue();
        Class<?> cls4 = asClass;
        if (cls4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("asClass");
        }
        Object obj4 = cls4.getField("FORCE_SPEAKER").get(null);
        if (obj4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        FORCE_SPEAKER = ((Integer) obj4).intValue();
        Class<?> cls5 = asClass;
        if (cls5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("asClass");
        }
        Object obj5 = cls5.getField("FORCE_NONE").get(null);
        if (obj5 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        FORCE_NONE = ((Integer) obj5).intValue();
    }

    private AudioSystemProxy() {
    }

    @SuppressLint({"PrivateApi"})
    public final void init() throws ClassNotFoundException {
        Class<?> cls = Class.forName(AS_CLASS_NAME);
        Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(AS_CLASS_NAME)");
        asClass = cls;
    }

    @JvmStatic
    public static final int setForceUse(int usage, int config) throws IllegalAccessException, InvocationTargetException {
        Class<?> cls = asClass;
        if (cls == null) {
            Intrinsics.throwUninitializedPropertyAccessException("asClass");
        }
        Object objInvoke = cls.getMethod("setForceUse", Integer.TYPE, Integer.TYPE).invoke(null, Integer.valueOf(usage), Integer.valueOf(config));
        if (objInvoke != null) {
            return ((Integer) objInvoke).intValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }
}
