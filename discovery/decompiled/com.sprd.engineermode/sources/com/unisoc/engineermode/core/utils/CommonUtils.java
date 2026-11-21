package com.unisoc.engineermode.core.utils;

import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Process;
import android.os.UserManager;
import com.unisoc.engineermode.core.common.CommonKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CommonUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00020\u0006*\u00020\u00078FX\u0087\u0004¢\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\u0005\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/unisoc/engineermode/core/utils/CommonUtils;", "", "()V", "USBMANAGER_ACTION_USB_STATE", "", "isCurrentUserDeviceOwner", "", "Landroid/os/UserManager;", "isCurrentUserDeviceOwner$annotations", "(Landroid/os/UserManager;)V", "(Landroid/os/UserManager;)Z", "name"}, k = 1, mv = {1, 1, 13})
public final class CommonUtils {
    public static final CommonUtils INSTANCE = new CommonUtils();

    @JvmField
    @NotNull
    public static final String USBMANAGER_ACTION_USB_STATE;

    @JvmStatic
    public static /* synthetic */ void isCurrentUserDeviceOwner$annotations(UserManager userManager) {
    }

    static {
        Object obj = UsbManager.class.getField("ACTION_USB_STATE").get(null);
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        USBMANAGER_ACTION_USB_STATE = (String) obj;
    }

    private CommonUtils() {
    }

    public static final boolean isCurrentUserDeviceOwner(@NotNull UserManager receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (Build.VERSION.SDK_INT >= 23) {
            return receiver$0.isSystemUser();
        }
        return Build.VERSION.SDK_INT < 17 || receiver$0.getSerialNumberForUser(Process.myUserHandle()) == 0;
    }

    @JvmStatic
    public static final boolean isCurrentUserDeviceOwner() {
        Object systemService = CommonKt.getAppCtx().getSystemService("user");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.os.UserManager");
        }
        UserManager userManager = (UserManager) systemService;
        return userManager.getSerialNumberForUser(Process.myUserHandle()) == 0;
    }
}
