package com.unisoc.engineermode.core.intf;

import com.unisoc.engineermode.core.factory.BaseInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: IDebugLogApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001:\u0002\u0015\u0016J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H&J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003H&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003H&J\b\u0010\r\u001a\u00020\u0004H&J\b\u0010\u000e\u001a\u00020\u0004H&J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\b\u0010\u0010\u001a\u00020\u0004H&J\b\u0010\u0011\u001a\u00020\u0004H&J\b\u0010\u0012\u001a\u00020\u0004H&J\b\u0010\u0013\u001a\u00020\u0004H&J\b\u0010\u0014\u001a\u00020\u0004H&¨\u0006\u0017"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IPhoneInfo;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "getAllImei", "", "", "getAllImsi", "getAllOpers", "Lcom/unisoc/engineermode/core/intf/IPhoneInfo$Operator;", "getCdmaImsi", "getCdmaInfo", "Lcom/unisoc/engineermode/core/intf/IPhoneInfo$CdmaInfo;", "getCellId", "", "getEsn", "getHardwareVersion", "getIccId", "getInternalStorageSize", "getMeid", "getMemorySize", "getOsVersion", "getSoftwareVersion", "CdmaInfo", "Operator", "name"}, k = 1, mv = {1, 1, 13})
public interface IPhoneInfo extends BaseInterface {
    @NotNull
    List<String> getAllImei();

    @NotNull
    List<String> getAllImsi();

    @NotNull
    List<Operator> getAllOpers();

    @NotNull
    List<String> getCdmaImsi();

    @NotNull
    List<CdmaInfo> getCdmaInfo();

    @NotNull
    List<Integer> getCellId();

    @NotNull
    String getEsn();

    @NotNull
    String getHardwareVersion();

    @NotNull
    List<String> getIccId();

    @NotNull
    String getInternalStorageSize();

    @NotNull
    String getMeid();

    @NotNull
    String getMemorySize();

    @NotNull
    String getOsVersion();

    @NotNull
    String getSoftwareVersion();

    /* JADX INFO: compiled from: IDebugLogApi.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\u0015"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IPhoneInfo$Operator;", "", "mcc", "", "mnc", "(Ljava/lang/String;Ljava/lang/String;)V", "getMcc", "()Ljava/lang/String;", "setMcc", "(Ljava/lang/String;)V", "getMnc", "setMnc", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "name"}, k = 1, mv = {1, 1, 13})
    public static final /* data */ class Operator {

        @NotNull
        private String mcc;

        @NotNull
        private String mnc;

        @NotNull
        public static /* synthetic */ Operator copy$default(Operator operator, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = operator.mcc;
            }
            if ((i & 2) != 0) {
                str2 = operator.mnc;
            }
            return operator.copy(str, str2);
        }

        @NotNull
        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getMcc() {
            return this.mcc;
        }

        @NotNull
        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getMnc() {
            return this.mnc;
        }

        @NotNull
        public final Operator copy(@NotNull String mcc, @NotNull String mnc) {
            Intrinsics.checkParameterIsNotNull(mcc, "mcc");
            Intrinsics.checkParameterIsNotNull(mnc, "mnc");
            return new Operator(mcc, mnc);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Operator)) {
                return false;
            }
            Operator operator = (Operator) other;
            return Intrinsics.areEqual(this.mcc, operator.mcc) && Intrinsics.areEqual(this.mnc, operator.mnc);
        }

        public int hashCode() {
            String str = this.mcc;
            int iHashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.mnc;
            return iHashCode + (str2 != null ? str2.hashCode() : 0);
        }

        @NotNull
        public String toString() {
            return "Operator(mcc=" + this.mcc + ", mnc=" + this.mnc + ")";
        }

        public Operator(@NotNull String mcc, @NotNull String mnc) {
            Intrinsics.checkParameterIsNotNull(mcc, "mcc");
            Intrinsics.checkParameterIsNotNull(mnc, "mnc");
            this.mcc = mcc;
            this.mnc = mnc;
        }

        @NotNull
        public final String getMcc() {
            return this.mcc;
        }

        @NotNull
        public final String getMnc() {
            return this.mnc;
        }

        public final void setMcc(@NotNull String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.mcc = str;
        }

        public final void setMnc(@NotNull String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.mnc = str;
        }
    }

    /* JADX INFO: compiled from: IDebugLogApi.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÖ\u0001J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\n\"\u0004\b\u0014\u0010\f¨\u0006\u001f"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IPhoneInfo$CdmaInfo;", "", "prl", "", "systemId", "", "networkId", "baseStationId", "(Ljava/lang/String;III)V", "getBaseStationId", "()I", "setBaseStationId", "(I)V", "getNetworkId", "setNetworkId", "getPrl", "()Ljava/lang/String;", "setPrl", "(Ljava/lang/String;)V", "getSystemId", "setSystemId", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "name"}, k = 1, mv = {1, 1, 13})
    public static final /* data */ class CdmaInfo {
        private int baseStationId;
        private int networkId;

        @NotNull
        private String prl;
        private int systemId;

        @NotNull
        public static /* synthetic */ CdmaInfo copy$default(CdmaInfo cdmaInfo, String str, int i, int i2, int i3, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                str = cdmaInfo.prl;
            }
            if ((i4 & 2) != 0) {
                i = cdmaInfo.systemId;
            }
            if ((i4 & 4) != 0) {
                i2 = cdmaInfo.networkId;
            }
            if ((i4 & 8) != 0) {
                i3 = cdmaInfo.baseStationId;
            }
            return cdmaInfo.copy(str, i, i2, i3);
        }

        @NotNull
        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getPrl() {
            return this.prl;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getSystemId() {
            return this.systemId;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getNetworkId() {
            return this.networkId;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final int getBaseStationId() {
            return this.baseStationId;
        }

        @NotNull
        public final CdmaInfo copy(@NotNull String prl, int systemId, int networkId, int baseStationId) {
            Intrinsics.checkParameterIsNotNull(prl, "prl");
            return new CdmaInfo(prl, systemId, networkId, baseStationId);
        }

        public boolean equals(@Nullable Object other) {
            if (this != other) {
                if (other instanceof CdmaInfo) {
                    CdmaInfo cdmaInfo = (CdmaInfo) other;
                    if (Intrinsics.areEqual(this.prl, cdmaInfo.prl)) {
                        if (this.systemId == cdmaInfo.systemId) {
                            if (this.networkId == cdmaInfo.networkId) {
                                if (this.baseStationId == cdmaInfo.baseStationId) {
                                }
                            }
                        }
                    }
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            String str = this.prl;
            return ((((((str != null ? str.hashCode() : 0) * 31) + Integer.hashCode(this.systemId)) * 31) + Integer.hashCode(this.networkId)) * 31) + Integer.hashCode(this.baseStationId);
        }

        @NotNull
        public String toString() {
            return "CdmaInfo(prl=" + this.prl + ", systemId=" + this.systemId + ", networkId=" + this.networkId + ", baseStationId=" + this.baseStationId + ")";
        }

        public CdmaInfo(@NotNull String prl, int systemId, int networkId, int baseStationId) {
            Intrinsics.checkParameterIsNotNull(prl, "prl");
            this.prl = prl;
            this.systemId = systemId;
            this.networkId = networkId;
            this.baseStationId = baseStationId;
        }

        public final int getBaseStationId() {
            return this.baseStationId;
        }

        public final int getNetworkId() {
            return this.networkId;
        }

        @NotNull
        public final String getPrl() {
            return this.prl;
        }

        public final int getSystemId() {
            return this.systemId;
        }

        public final void setBaseStationId(int i) {
            this.baseStationId = i;
        }

        public final void setNetworkId(int i) {
            this.networkId = i;
        }

        public final void setPrl(@NotNull String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.prl = str;
        }

        public final void setSystemId(int i) {
            this.systemId = i;
        }
    }
}
