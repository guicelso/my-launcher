package com.unisoc.engineermode.core.impl.nonpublic;

import android.hardware.usb.UsbManager;
import com.unisoc.engineermode.core.common.CommonKt;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;

/* JADX INFO: compiled from: UsbManagerProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/UsbManagerProxy;", "", "()V", "FUNCTION_NONE", "", "usbManagerObject", "Landroid/hardware/usb/UsbManager;", "setCurrentFunctions", "", "value", "name"}, k = 1, mv = {1, 1, 13})
public final class UsbManagerProxy {

    @JvmField
    public static final long FUNCTION_NONE;
    public static final UsbManagerProxy INSTANCE = new UsbManagerProxy();
    private static UsbManager usbManagerObject;

    static {
        Object systemService = CommonKt.getAppCtx().getSystemService("usb");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.hardware.usb.UsbManager");
        }
        usbManagerObject = (UsbManager) systemService;
        Object obj = UsbManager.class.getField("FUNCTION_NONE").get(null);
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
        }
        FUNCTION_NONE = ((Long) obj).longValue();
    }

    private UsbManagerProxy() {
    }

    @JvmStatic
    public static final void setCurrentFunctions(long value) throws IllegalAccessException, InvocationTargetException {
        UsbManager.class.getMethod("setCurrentFunctions", Long.TYPE).invoke(usbManagerObject, Long.valueOf(value));
    }
}
