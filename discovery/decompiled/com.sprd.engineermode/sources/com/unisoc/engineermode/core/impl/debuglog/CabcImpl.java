package com.unisoc.engineermode.core.impl.debuglog;

import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.impl.hardware.PhaseCheckParse;
import com.unisoc.engineermode.core.intf.ICabc;
import kotlin.Metadata;

/* JADX INFO: compiled from: CabcImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/unisoc/engineermode/core/impl/debuglog/CabcImpl;", "Lcom/unisoc/engineermode/core/intf/ICabc;", "()V", "set", "", "isOn", "", "name"}, k = 1, mv = {1, 1, 13})
public final class CabcImpl implements ICabc {
    public static final CabcImpl INSTANCE = new CabcImpl();

    private CabcImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ICabc
    public void set(boolean isOn) throws Exception {
        boolean success;
        if (isOn) {
            success = new PhaseCheckParse().setCabc(0);
        } else {
            success = new PhaseCheckParse().setCabc(1);
        }
        if (!success) {
            throw new OperationFailedException("set cabc failed");
        }
    }
}
