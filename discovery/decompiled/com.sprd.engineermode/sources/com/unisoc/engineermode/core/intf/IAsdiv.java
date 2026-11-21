package com.unisoc.engineermode.core.intf;

import com.unisoc.engineermode.core.factory.BaseInterface;
import kotlin.Metadata;

/* JADX INFO: compiled from: IHardwareApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\u0003H&J\b\u0010\n\u001a\u00020\u0003H&J\b\u0010\u000b\u001a\u00020\u0003H&¨\u0006\f"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IAsdiv;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "closeAllAntennas", "", "setC2kToAntenna1", "setC2kToAntenna2", "setGsmToAntenna1", "setGsmToAntenna2", "setLteToAntenna1", "setLteToAntenna2", "setWcdmaToAntenna1", "setWcdmaToAntenna2", "name"}, k = 1, mv = {1, 1, 13})
public interface IAsdiv extends BaseInterface {
    void closeAllAntennas() throws Exception;

    void setC2kToAntenna1() throws Exception;

    void setC2kToAntenna2() throws Exception;

    void setGsmToAntenna1() throws Exception;

    void setGsmToAntenna2() throws Exception;

    void setLteToAntenna1() throws Exception;

    void setLteToAntenna2() throws Exception;

    void setWcdmaToAntenna1() throws Exception;

    void setWcdmaToAntenna2() throws Exception;
}
