package com.unisoc.engineermode.core.intf;

/* JADX INFO: loaded from: classes4.dex */
public interface IModem {

    public interface Capability {
        boolean isG();

        boolean isW();

        boolean isWG();
    }

    Capability[] getCapabilities();

    Capability getCapability(int i);
}
