package com.unisoc.engineermode.core.impl.hardware;

import android.os.RemoteException;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.exception.UnsupportedFeatureException;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.IThermal;
import kotlin.Metadata;
import vendor.sprd.hardware.thermal.V1_0.IExtThermal;

/* JADX INFO: compiled from: ThermalImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0007H\u0016J\b\u0010\t\u001a\u00020\u0007H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u0007H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\b\u0010\u0010\u001a\u00020\u000eH\u0016J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\b\u0010\u0012\u001a\u00020\u000eH\u0016J\b\u0010\u0013\u001a\u00020\u000eH\u0016J\b\u0010\u0014\u001a\u00020\u000eH\u0016J\b\u0010\u0015\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/unisoc/engineermode/core/impl/hardware/ThermalImpl;", "Lcom/unisoc/engineermode/core/intf/IThermal;", "()V", "PROP_NAME", "", "TAG", "getThermalChargeState", "", "getThermalIpaState", "getThermalPaState", "getThermalService", "Lvendor/sprd/hardware/thermal/V1_0/IExtThermal;", "getThermalState", "switchThermalChargeOff", "", "switchThermalChargeOn", "switchThermalIpaOff", "switchThermalIpaOn", "switchThermalOff", "switchThermalOn", "switchThermalPaOff", "switchThermalPaOn", "name"}, k = 1, mv = {1, 1, 13})
public final class ThermalImpl implements IThermal {
    public static final ThermalImpl INSTANCE = new ThermalImpl();
    private static final String PROP_NAME = "persist.sys.thermal.ipa";
    private static final String TAG = "EM-THERMAL";

    private ThermalImpl() {
    }

    private final IExtThermal getThermalService() throws RemoteException {
        IExtThermal service = IExtThermal.getService();
        if (service != null) {
            return service;
        }
        throw new UnsupportedFeatureException("get thermal service failed");
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public void switchThermalOn() {
        try {
            getThermalService().setExtThermal(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public void switchThermalOff() {
        try {
            getThermalService().setExtThermal(1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public void switchThermalPaOn() {
        try {
            getThermalService().setExtThermal(5);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public void switchThermalPaOff() {
        try {
            getThermalService().setExtThermal(6);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public void switchThermalChargeOn() {
        try {
            getThermalService().setExtThermal(8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public void switchThermalChargeOff() {
        try {
            getThermalService().setExtThermal(9);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public void switchThermalIpaOn() {
        try {
            SystemPropertiesProxy.set(PROP_NAME, "1");
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public void switchThermalIpaOff() {
        try {
            SystemPropertiesProxy.set(PROP_NAME, "0");
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public boolean getThermalState() {
        try {
            return getThermalService().getExtThermal(4);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public boolean getThermalPaState() {
        try {
            return getThermalService().getExtThermal(7);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public boolean getThermalChargeState() {
        try {
            return getThermalService().getExtThermal(10);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IThermal
    public boolean getThermalIpaState() {
        try {
            return SystemPropertiesProxy.get(PROP_NAME, "1").equals("1");
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
    }
}
