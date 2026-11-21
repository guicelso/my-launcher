package com.unisoc.engineermode.core.impl.debuglog;

import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.UsbManagerProxy;
import com.unisoc.engineermode.core.intf.IAtDiag;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;

/* JADX INFO: compiled from: AtDiagImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/unisoc/engineermode/core/impl/debuglog/AtDiagImpl;", "Lcom/unisoc/engineermode/core/intf/IAtDiag;", "()V", "SYS_MODEM_DIAG", "", "closeAtDiag", "", "isAtDiagEnabled", "", "openAtDiag", "name"}, k = 1, mv = {1, 1, 13})
public final class AtDiagImpl implements IAtDiag {
    public static final AtDiagImpl INSTANCE = new AtDiagImpl();
    private static final String SYS_MODEM_DIAG = "persist.vendor.sys.modem.diag";

    private AtDiagImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.IAtDiag
    public boolean isAtDiagEnabled() {
        return SystemPropertiesProxy.get(SYS_MODEM_DIAG, "disable").equals(",gser");
    }

    @Override // com.unisoc.engineermode.core.intf.IAtDiag
    public void openAtDiag() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set(SYS_MODEM_DIAG, ",gser");
        UsbManagerProxy.setCurrentFunctions(UsbManagerProxy.FUNCTION_NONE);
    }

    @Override // com.unisoc.engineermode.core.intf.IAtDiag
    public void closeAtDiag() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set(SYS_MODEM_DIAG, "disable");
        UsbManagerProxy.setCurrentFunctions(UsbManagerProxy.FUNCTION_NONE);
    }
}
