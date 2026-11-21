package com.unisoc.engineermode.core.intf;

import com.unisoc.engineermode.core.exception.EmException;
import com.unisoc.engineermode.core.factory.BaseInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: IHardwareApi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0002\u000b\fJ\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\r"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IAntenna;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "getAllStates", "Lcom/unisoc/engineermode/core/intf/IAntenna$AllState;", "setC2k", "", "state", "Lcom/unisoc/engineermode/core/intf/IAntenna$AntennaState;", "setGsm", "setLte", "setWcdma", "AllState", "AntennaState", "name"}, k = 1, mv = {1, 1, 13})
public interface IAntenna extends BaseInterface {

    /* JADX INFO: compiled from: IHardwareApi.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IAntenna$AntennaState;", "", "(Ljava/lang/String;I)V", "PRIMARY_DIVERSITY", "PRIMARY", "DIVERSITY", "PRIMARY_DIVERSITY_DYNAMIC", "INVALID", "name"}, k = 1, mv = {1, 1, 13})
    public enum AntennaState {
        PRIMARY_DIVERSITY,
        PRIMARY,
        DIVERSITY,
        PRIMARY_DIVERSITY_DYNAMIC,
        INVALID
    }

    @NotNull
    AllState getAllStates() throws EmException;

    void setC2k(@NotNull AntennaState state) throws EmException;

    void setGsm(@NotNull AntennaState state) throws EmException;

    void setLte(@NotNull AntennaState state) throws EmException;

    void setWcdma(@NotNull AntennaState state) throws EmException;

    /* JADX INFO: compiled from: IHardwareApi.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u001e"}, d2 = {"Lcom/unisoc/engineermode/core/intf/IAntenna$AllState;", "", "gsm", "Lcom/unisoc/engineermode/core/intf/IAntenna$AntennaState;", "wcdma", "lte", "c2k", "(Lcom/unisoc/engineermode/core/intf/IAntenna$AntennaState;Lcom/unisoc/engineermode/core/intf/IAntenna$AntennaState;Lcom/unisoc/engineermode/core/intf/IAntenna$AntennaState;Lcom/unisoc/engineermode/core/intf/IAntenna$AntennaState;)V", "getC2k", "()Lcom/unisoc/engineermode/core/intf/IAntenna$AntennaState;", "setC2k", "(Lcom/unisoc/engineermode/core/intf/IAntenna$AntennaState;)V", "getGsm", "setGsm", "getLte", "setLte", "getWcdma", "setWcdma", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "name"}, k = 1, mv = {1, 1, 13})
    public static final /* data */ class AllState {

        @NotNull
        private AntennaState c2k;

        @NotNull
        private AntennaState gsm;

        @NotNull
        private AntennaState lte;

        @NotNull
        private AntennaState wcdma;

        public AllState() {
            this(null, null, null, null, 15, null);
        }

        @NotNull
        public static /* synthetic */ AllState copy$default(AllState allState, AntennaState antennaState, AntennaState antennaState2, AntennaState antennaState3, AntennaState antennaState4, int i, Object obj) {
            if ((i & 1) != 0) {
                antennaState = allState.gsm;
            }
            if ((i & 2) != 0) {
                antennaState2 = allState.wcdma;
            }
            if ((i & 4) != 0) {
                antennaState3 = allState.lte;
            }
            if ((i & 8) != 0) {
                antennaState4 = allState.c2k;
            }
            return allState.copy(antennaState, antennaState2, antennaState3, antennaState4);
        }

        @NotNull
        /* JADX INFO: renamed from: component1, reason: from getter */
        public final AntennaState getGsm() {
            return this.gsm;
        }

        @NotNull
        /* JADX INFO: renamed from: component2, reason: from getter */
        public final AntennaState getWcdma() {
            return this.wcdma;
        }

        @NotNull
        /* JADX INFO: renamed from: component3, reason: from getter */
        public final AntennaState getLte() {
            return this.lte;
        }

        @NotNull
        /* JADX INFO: renamed from: component4, reason: from getter */
        public final AntennaState getC2k() {
            return this.c2k;
        }

        @NotNull
        public final AllState copy(@NotNull AntennaState gsm, @NotNull AntennaState wcdma, @NotNull AntennaState lte, @NotNull AntennaState c2k) {
            Intrinsics.checkParameterIsNotNull(gsm, "gsm");
            Intrinsics.checkParameterIsNotNull(wcdma, "wcdma");
            Intrinsics.checkParameterIsNotNull(lte, "lte");
            Intrinsics.checkParameterIsNotNull(c2k, "c2k");
            return new AllState(gsm, wcdma, lte, c2k);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AllState)) {
                return false;
            }
            AllState allState = (AllState) other;
            return Intrinsics.areEqual(this.gsm, allState.gsm) && Intrinsics.areEqual(this.wcdma, allState.wcdma) && Intrinsics.areEqual(this.lte, allState.lte) && Intrinsics.areEqual(this.c2k, allState.c2k);
        }

        public int hashCode() {
            AntennaState antennaState = this.gsm;
            int iHashCode = (antennaState != null ? antennaState.hashCode() : 0) * 31;
            AntennaState antennaState2 = this.wcdma;
            int iHashCode2 = (iHashCode + (antennaState2 != null ? antennaState2.hashCode() : 0)) * 31;
            AntennaState antennaState3 = this.lte;
            int iHashCode3 = (iHashCode2 + (antennaState3 != null ? antennaState3.hashCode() : 0)) * 31;
            AntennaState antennaState4 = this.c2k;
            return iHashCode3 + (antennaState4 != null ? antennaState4.hashCode() : 0);
        }

        @NotNull
        public String toString() {
            return "AllState(gsm=" + this.gsm + ", wcdma=" + this.wcdma + ", lte=" + this.lte + ", c2k=" + this.c2k + ")";
        }

        public AllState(@NotNull AntennaState gsm, @NotNull AntennaState wcdma, @NotNull AntennaState lte, @NotNull AntennaState c2k) {
            Intrinsics.checkParameterIsNotNull(gsm, "gsm");
            Intrinsics.checkParameterIsNotNull(wcdma, "wcdma");
            Intrinsics.checkParameterIsNotNull(lte, "lte");
            Intrinsics.checkParameterIsNotNull(c2k, "c2k");
            this.gsm = gsm;
            this.wcdma = wcdma;
            this.lte = lte;
            this.c2k = c2k;
        }

        public /* synthetic */ AllState(AntennaState antennaState, AntennaState antennaState2, AntennaState antennaState3, AntennaState antennaState4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? AntennaState.INVALID : antennaState, (i & 2) != 0 ? AntennaState.INVALID : antennaState2, (i & 4) != 0 ? AntennaState.INVALID : antennaState3, (i & 8) != 0 ? AntennaState.INVALID : antennaState4);
        }

        @NotNull
        public final AntennaState getGsm() {
            return this.gsm;
        }

        public final void setGsm(@NotNull AntennaState antennaState) {
            Intrinsics.checkParameterIsNotNull(antennaState, "<set-?>");
            this.gsm = antennaState;
        }

        @NotNull
        public final AntennaState getWcdma() {
            return this.wcdma;
        }

        public final void setWcdma(@NotNull AntennaState antennaState) {
            Intrinsics.checkParameterIsNotNull(antennaState, "<set-?>");
            this.wcdma = antennaState;
        }

        @NotNull
        public final AntennaState getLte() {
            return this.lte;
        }

        public final void setLte(@NotNull AntennaState antennaState) {
            Intrinsics.checkParameterIsNotNull(antennaState, "<set-?>");
            this.lte = antennaState;
        }

        @NotNull
        public final AntennaState getC2k() {
            return this.c2k;
        }

        public final void setC2k(@NotNull AntennaState antennaState) {
            Intrinsics.checkParameterIsNotNull(antennaState, "<set-?>");
            this.c2k = antennaState;
        }
    }
}
