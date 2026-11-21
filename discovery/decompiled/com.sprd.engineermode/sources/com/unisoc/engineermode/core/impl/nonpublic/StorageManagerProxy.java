package com.unisoc.engineermode.core.impl.nonpublic;

import android.os.storage.StorageManager;
import com.unisoc.engineermode.core.common.CommonKt;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;

/* JADX INFO: compiled from: StorageManagerProxy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/unisoc/engineermode/core/impl/nonpublic/StorageManagerProxy;", "", "()V", "storageManagerObject", "Landroid/os/storage/StorageManager;", "getPrimaryStorageSize", "", "name"}, k = 1, mv = {1, 1, 13})
public final class StorageManagerProxy {
    public static final StorageManagerProxy INSTANCE = new StorageManagerProxy();
    private static StorageManager storageManagerObject;

    static {
        Object systemService = CommonKt.getAppCtx().getSystemService("storage");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.os.storage.StorageManager");
        }
        storageManagerObject = (StorageManager) systemService;
    }

    private StorageManagerProxy() {
    }

    @JvmStatic
    public static final long getPrimaryStorageSize() throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = StorageManager.class.getMethod("getPrimaryStorageSize", new Class[0]).invoke(storageManagerObject, new Object[0]);
        if (objInvoke != null) {
            return ((Long) objInvoke).longValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
    }
}
