package com.unisoc.engineermode.core.impl.nonpublic;

import android.app.ActivityManager;
import com.unisoc.engineermode.core.common.CommonKt;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: MiscProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"ActivityManager_forceStopPackage", "", "packageName", "", "name"}, k = 2, mv = {1, 1, 13})
public final class MiscProxyKt {
    public static final void ActivityManager_forceStopPackage(@NotNull String packageName) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        Object am = CommonKt.getAppCtx().getSystemService("activity");
        ActivityManager.class.getMethod("forceStopPackage", String.class).invoke(am, packageName);
    }
}
