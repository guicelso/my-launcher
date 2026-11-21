package com.unisoc.engineermode.core.intf;

import com.unisoc.engineermode.core.factory.BaseInterface;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: IHardwareApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003H&¨\u0006\n"}, d2 = {"Lcom/unisoc/engineermode/core/intf/ISensePll;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "read", "", "address", "count", "", "write", "", "data", "name"}, k = 1, mv = {1, 1, 13})
public interface ISensePll extends BaseInterface {
    @NotNull
    String read(@NotNull String address, int count) throws Exception;

    void write(@NotNull String address, @NotNull String data) throws Exception;
}
