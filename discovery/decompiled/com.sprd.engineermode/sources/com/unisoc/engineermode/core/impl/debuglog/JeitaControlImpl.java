package com.unisoc.engineermode.core.impl.debuglog;

import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.IJeitaControl;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: JeitaControlImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u00020\u0004H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/unisoc/engineermode/core/impl/debuglog/JeitaControlImpl;", "Lcom/unisoc/engineermode/core/intf/IJeitaControl;", "()V", "JEITA_CONTROL_SWITCH", "", "TAG", "getJeita", "setJeita", "", "value", "name"}, k = 1, mv = {1, 1, 13})
public final class JeitaControlImpl implements IJeitaControl {
    public static final JeitaControlImpl INSTANCE = new JeitaControlImpl();
    private static final String JEITA_CONTROL_SWITCH = "persist.vendor.eng.jeita";
    private static final String TAG = "JeitaControlImpl";

    private JeitaControlImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.IJeitaControl
    @NotNull
    public String getJeita() {
        return SystemPropertiesProxy.get(JEITA_CONTROL_SWITCH, "1");
    }

    @Override // com.unisoc.engineermode.core.intf.IJeitaControl
    public void setJeita(@NotNull String value) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(value, "value");
        SystemPropertiesProxy.set(JEITA_CONTROL_SWITCH, value);
    }
}
