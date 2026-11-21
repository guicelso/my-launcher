package com.unisoc.engineermode.core.common;

import android.annotation.SuppressLint;
import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Common.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001e\u0010\u0000\u001a\u00020\u00018\u0006@\u0006X\u0087.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"appCtx", "Landroid/content/Context;", "getAppCtx", "()Landroid/content/Context;", "setAppCtx", "(Landroid/content/Context;)V", "name"}, k = 2, mv = {1, 1, 13})
public final class CommonKt {

    @SuppressLint({"StaticFieldLeak"})
    @NotNull
    public static Context appCtx;

    @NotNull
    public static final Context getAppCtx() {
        Context context = appCtx;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appCtx");
        }
        return context;
    }

    public static final void setAppCtx(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "<set-?>");
        appCtx = context;
    }
}
