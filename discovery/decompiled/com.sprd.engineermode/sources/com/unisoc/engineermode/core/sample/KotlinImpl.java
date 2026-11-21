package com.unisoc.engineermode.core.sample;

import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KotlinImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/unisoc/engineermode/core/sample/KotlinImpl;", "Lcom/unisoc/engineermode/core/sample/KotlinInterface;", "()V", "TAG", "", "self", "name"}, k = 1, mv = {1, 1, 13})
public final class KotlinImpl implements KotlinInterface {
    public static final KotlinImpl INSTANCE = new KotlinImpl();

    @NotNull
    public static final String TAG = "SampleImpl";

    private KotlinImpl() {
    }

    @Override // com.unisoc.engineermode.core.sample.KotlinInterface
    @NotNull
    public String self() {
        Log.d(TAG, "this is SmapleImple for android9");
        String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
        if (simpleName == null) {
            Intrinsics.throwNpe();
        }
        return simpleName;
    }
}
