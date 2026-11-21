package com.unisoc.engineermode.core.sample;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KotlinImplDef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/unisoc/engineermode/core/sample/KotlinImplDef;", "Lcom/unisoc/engineermode/core/sample/KotlinInterface;", "()V", "name"}, k = 1, mv = {1, 1, 13})
public final class KotlinImplDef implements KotlinInterface {
    public static final KotlinImplDef INSTANCE = new KotlinImplDef();

    private KotlinImplDef() {
    }

    @Override // com.unisoc.engineermode.core.sample.KotlinInterface
    @NotNull
    public String self() {
        return KotlinInterface.DefaultImpls.self(this);
    }
}
