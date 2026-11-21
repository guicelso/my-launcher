package com.unisoc.engineermode.core.factory;

import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ImplementationInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J!\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00062\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0016¢\u0006\u0002\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0019\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR9\u0010\f\u001a*\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u000e0\rj\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u000e`\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0018"}, d2 = {"Lcom/unisoc/engineermode/core/factory/ImplementationInfo;", "", "interfaceClass", "Ljava/lang/Class;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "implementClassName", "", "(Ljava/lang/Class;Ljava/lang/String;)V", "getImplementClassName", "()Ljava/lang/String;", "getInterfaceClass", "()Ljava/lang/Class;", "properties", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "getProperties", "()Ljava/util/HashMap;", "addProperty", "", "key", "values", "", "(Ljava/lang/String;[Ljava/lang/String;)V", "name"}, k = 1, mv = {1, 1, 13})
public final class ImplementationInfo {

    @NotNull
    private final String implementClassName;

    @NotNull
    private final Class<? extends BaseInterface> interfaceClass;

    @NotNull
    private final HashMap<String, List<String>> properties;

    public ImplementationInfo(@NotNull Class<? extends BaseInterface> interfaceClass, @NotNull String implementClassName) {
        Intrinsics.checkParameterIsNotNull(interfaceClass, "interfaceClass");
        Intrinsics.checkParameterIsNotNull(implementClassName, "implementClassName");
        this.interfaceClass = interfaceClass;
        this.implementClassName = implementClassName;
        this.properties = new HashMap<>();
    }

    @NotNull
    public final Class<? extends BaseInterface> getInterfaceClass() {
        return this.interfaceClass;
    }

    @NotNull
    public final String getImplementClassName() {
        return this.implementClassName;
    }

    @NotNull
    public final HashMap<String, List<String>> getProperties() {
        return this.properties;
    }

    public final void addProperty(@NotNull String key, @NotNull String[] values) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(values, "values");
        this.properties.put(key, ArraysKt.toList(values));
    }
}
