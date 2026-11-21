package com.unisoc.engineermode.core.impl.nonpublic;

import android.media.AudioManager;
import com.unisoc.engineermode.core.common.CommonKt;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: AudioManagerProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u0006H\u0002J(\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0006H\u0007J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0006H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/AudioManagerProxy;", "", "()V", "DEVICE_OUT_FM_HEADSET", "", "EXTRA_VOLUME_STREAM_TYPE", "", "EXTRA_VOLUME_STREAM_VALUE", "VOLUME_CHANGED_ACTION", "audioManager", "Landroid/media/AudioManager;", "getField", "fieldName", "setDeviceConnectionStateForFM", "", "device", "state", "device_address", "device_name", "setParameters", "keyValuePairs", "name"}, k = 1, mv = {1, 1, 13})
public final class AudioManagerProxy {

    @JvmField
    public static final int DEVICE_OUT_FM_HEADSET;

    @JvmField
    @NotNull
    public static final String EXTRA_VOLUME_STREAM_TYPE;

    @JvmField
    @NotNull
    public static final String EXTRA_VOLUME_STREAM_VALUE;
    public static final AudioManagerProxy INSTANCE;

    @JvmField
    @NotNull
    public static final String VOLUME_CHANGED_ACTION;
    private static AudioManager audioManager;

    static {
        AudioManagerProxy audioManagerProxy = new AudioManagerProxy();
        INSTANCE = audioManagerProxy;
        Object field = audioManagerProxy.getField("EXTRA_VOLUME_STREAM_TYPE");
        if (field == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        EXTRA_VOLUME_STREAM_TYPE = (String) field;
        Object field2 = audioManagerProxy.getField("EXTRA_VOLUME_STREAM_VALUE");
        if (field2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        EXTRA_VOLUME_STREAM_VALUE = (String) field2;
        Object field3 = audioManagerProxy.getField("DEVICE_OUT_FM_HEADSET");
        if (field3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        DEVICE_OUT_FM_HEADSET = ((Integer) field3).intValue();
        Object field4 = audioManagerProxy.getField("VOLUME_CHANGED_ACTION");
        if (field4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        VOLUME_CHANGED_ACTION = (String) field4;
        Object systemService = CommonKt.getAppCtx().getSystemService("audio");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.media.AudioManager");
        }
        audioManager = (AudioManager) systemService;
    }

    private AudioManagerProxy() {
    }

    private final Object getField(String fieldName) throws IllegalAccessException {
        Object obj = AudioManager.class.getField(fieldName).get(null);
        if (obj == null) {
            Intrinsics.throwNpe();
        }
        return obj;
    }

    @JvmStatic
    public static final void setParameters(@NotNull String keyValuePairs) {
        Intrinsics.checkParameterIsNotNull(keyValuePairs, "keyValuePairs");
        AudioManager.class.getMethod("setParameters", String.class).invoke(audioManager, keyValuePairs);
    }

    @JvmStatic
    public static final void setDeviceConnectionStateForFM(int device, int state, @NotNull String device_address, @NotNull String device_name) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(device_address, "device_address");
        Intrinsics.checkParameterIsNotNull(device_name, "device_name");
        AudioManager.class.getMethod("setDeviceConnectionStateForFM", Integer.TYPE, Integer.TYPE, String.class, String.class).invoke(audioManager, Integer.valueOf(device), Integer.valueOf(state), device_address, device_name);
    }
}
