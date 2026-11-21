package com.unisoc.engineermode.core.intf;

import com.unisoc.engineermode.core.data.telephony.VamosCpcData;
import com.unisoc.engineermode.core.data.telephony.netinfostat.NetInfoStatData;
import com.unisoc.engineermode.core.factory.BaseInterface;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface ITelephonyApi {

    public interface IC2kConfig {
        String getC2kConfig() throws Exception;

        void setC2kConfig(String str) throws Exception;
    }

    public interface ICallFunc {
        void closeAutoAnswer() throws Exception;

        String getCfu();

        void openAutoAnswer() throws Exception;

        void setCfu(String str) throws Exception;
    }

    public interface ICftResult extends BaseInterface {
        String getC2kCFTResult() throws Exception;

        String getGsmTdScdmaCFTResult() throws Exception;

        String getLteCFTResult() throws Exception;

        String getWcdmaCFTResult() throws Exception;
    }

    public interface IControlEvdo extends BaseInterface {
        void closeEvdo() throws Exception;

        String getEvdoStatus() throws Exception;

        void openEvdo() throws Exception;
    }

    public interface IControlMeid extends BaseInterface {
        void closeMeidSwitch() throws Exception;

        String getMeidStatus() throws Exception;

        void openMeidSwitch() throws Exception;
    }

    public interface ICsfb2GmsDelay {
        boolean closeGrrcRandomAccess(int i);

        boolean closeGrrcResident(int i);

        boolean getStatus(int i, int[] iArr);

        boolean openGrrcRandomAccess(int i);

        boolean openGrrcResident(int i);
    }

    public interface IDataServicePrefer {
        void close() throws Exception;

        void open() throws Exception;
    }

    public interface IDnsFilter extends BaseInterface {
        void set(int i);
    }

    public interface IEngTestStatus {
        boolean isEngTest();

        void set(int i);
    }

    public interface IFastDormancy {
        void close() throws Exception;

        void open(int i) throws Exception;
    }

    public interface IGprsSetting {
        boolean activateFirstPdp(int i, int i2, int i3);

        boolean activateSecondPdp(int i, int i2);

        void attach(int i) throws Exception;

        boolean deactivatePdp(int i, int i2);

        void detach(int i) throws Exception;

        boolean getAutoAttachState(int i) throws Exception;

        boolean[] getPdpContextState(int i) throws Exception;

        boolean sendData(int i, int i2, String str, String str2);

        void setAlwaysAttach(int i) throws Exception;

        void setNeededAttach(int i) throws Exception;
    }

    public interface IManualAssert extends BaseInterface {
        void assertModem();

        boolean canAssertCp2();

        boolean canResetCp2();

        void disableCp2Reset();

        void enableCp2Reset();

        boolean isCp2Reset();

        boolean isModemResetting();

        void powerOnCp2();
    }

    public interface INetInfo {
        String getPlmn() throws Exception;
    }

    public interface INetInfoC2k {
        void getAdjacentCell(int i, String[][] strArr);

        void getBetweenAdjacentCell2G(int i, String[][] strArr);

        void getBetweenAdjacentCell4G(int i, String[][] strArr);

        List<String> getOutfieldNetworkInfo(int i, String[] strArr);

        void getServingCell(int i, String[] strArr, String[] strArr2);
    }

    public interface INetInfoC2k1x extends BaseInterface {
        void getAdjacentCell(int i, String[][] strArr);

        List<String> getOutfieldNetworkInfo(int i, String[] strArr);

        void getServingCell(int i, String[] strArr, String[] strArr2);
    }

    public interface INetInfoC2kUeCap {
        String getUeCapInfo(int i);
    }

    public interface INetInfoGsm {
        void getAdjacentCell(int i, String[][] strArr);

        void getBetweenAdjacentCell3G(int i, String[][] strArr);

        void getBetweenAdjacentCell4G(int i, String[][] strArr);

        List<String> getOutfieldNetworkInfo(int i, String[] strArr);

        void getServingCell(int i, String[] strArr, String[] strArr2);
    }

    public interface INetInfoGsmUeCap {
        boolean closeDiversity(int i);

        boolean closeVamos(int i);

        boolean getDiversity(int i);

        boolean getVamos(int i);

        boolean openDiversity(int i);

        boolean openVamos(int i);
    }

    public interface INetInfoLte {
        void getAdjacentCell(int i, String[][] strArr);

        void getBetweenAdjacentCell2G(int i, String[][] strArr);

        void getBetweenAdjacentCell3G(int i, String[][] strArr);

        List<String> getOutfieldNetworkInfo(int i, String[] strArr);

        void getServingCell(int i, String[] strArr, String[] strArr2);
    }

    public interface INetInfoLteUeCap {
        boolean closeDlca(int i);

        boolean closeUl64Qam(int i);

        boolean closeUlca(int i);

        boolean getCap(int i, boolean[] zArr);

        boolean openDlca(int i);

        boolean openUl64Qam(int i);

        boolean openUlca(int i);
    }

    public interface INetInfoStat {

        public static class DataItem {
            public String data;
            public String desc;
        }

        DataItem[] getAttachTime(int i) throws Exception;

        DataItem[] getDropCount(int i) throws Exception;

        NetInfoStatData getHandOverInfo(int i) throws Exception;

        NetInfoStatData getReselectInfo(int i) throws Exception;
    }

    public interface INetInfoTdscdma {
        void getAdjacentCell(int i, String[][] strArr);

        void getBetweenAdjacentCell2G(int i, String[][] strArr);

        void getBetweenAdjacentCell4G(int i, String[][] strArr);

        List<String> getOutfieldNetworkInfo(int i, String[] strArr);

        void getServingCell(int i, String[] strArr, String[] strArr2);
    }

    public interface INetInfoWcdma {
        void getAdjacentCell(int i, String[][] strArr);

        void getBetweenAdjacentCell2G(int i, String[][] strArr);

        void getBetweenAdjacentCell4G(int i, String[][] strArr);

        List<String> getOutfieldNetworkInfo(int i, String[] strArr);

        void getServingCell(int i, String[] strArr, String[] strArr2);
    }

    public interface INetInfoWcdmaUeCap {

        public static class NwCap {
        }

        public static class UeCap {
            public boolean dbHsdpa;
            public boolean hsdpa;
            public boolean snow3g;
            public boolean ul16Qam;
            public boolean wDiversity;
        }

        boolean closeDbHsdpa(int i);

        boolean closeSnow3G(int i);

        boolean closeUl16Qam(int i);

        boolean closeUlHsdpa(int i);

        boolean closeWDiversity(int i);

        UeCap getUeCap(int i);

        boolean openDbHsdpa(int i);

        boolean openSnow3G(int i);

        boolean openUl16Qam(int i);

        boolean openUlHsdpa(int i);

        boolean openWDiversity(int i);
    }

    public interface INetWorkMode {
        String getNetWorkMode(int i);

        void setNetWorkMode(String str, int i) throws Exception;
    }

    public interface ISimFunc {
        String readPlmn(int i) throws Exception;
    }

    public interface ISimTrace {
        void close() throws Exception;

        boolean get() throws Exception;

        void open() throws Exception;
    }

    public interface ITelephonyInfo {
        boolean isSupportAPCFunc();

        boolean isSupportC2k();

        boolean isSupportCsfb();

        boolean isSupportGsm();

        boolean isSupportLplusG();

        boolean isSupportLplusL();

        boolean isSupportLplusW();

        boolean isSupportLte();

        boolean isSupportNetwork();

        boolean isSupportTdscdma();

        boolean isSupportWcdma();

        boolean isSupportWplusG();

        boolean isSupportWplusW();
    }

    public interface ITelephonyMgr {
        int getPhoneCount();

        int getPreferredNetworkType(int i);
    }

    public interface IVamos extends BaseInterface {

        public static class VamosState {
            public boolean isSupported;
            public boolean isWorking;
        }

        void closeCapability() throws Exception;

        VamosState getState() throws Exception;

        void openCapability() throws Exception;
    }

    public interface IVamosCpc {
        VamosCpcData getVamosCpc();
    }

    public interface IVideoType {
        int get() throws Exception;

        void set(int i) throws Exception;
    }

    public interface IVolteDevelopSettings extends BaseInterface {
        int getDevFlag1();

        int getDevFlag2();

        int getDevFlag3();

        void setDevFlag1(int i);

        void setDevFlag2(int i);

        void setDevFlag3(int i);
    }

    public interface IVolteOperatorSettings {
        String getActivateCfnl(int i) throws Exception;

        String getAlertingValue(int i) throws Exception;

        String getAudioDynamic(int i) throws Exception;

        String getAudioRRValue(int i) throws Exception;

        String getAudioRSValue(int i) throws Exception;

        String getAudioRTCPTime(int i) throws Exception;

        String getAudioRTPTimeout(int i) throws Exception;

        String getBsfPORTValue(int i) throws Exception;

        String getBsfURIValue(int i) throws Exception;

        String getChannelAwarkMode(int i) throws Exception;

        String getCipherAlgorithm(int i) throws Exception;

        String getConferenceURI(int i) throws Exception;

        String getDefaultBitRate(int i) throws Exception;

        String getDirectAlerting(int i) throws Exception;

        String getDnsSrvEnable(int i) throws Exception;

        String getEvsDefaultBitRate(int i) throws Exception;

        String getEvsRate(int i) throws Exception;

        String getHttpPutMedia(int i) throws Exception;

        String getHttpsEnable(int i) throws Exception;

        String getIPPriority(int i) throws Exception;

        String getInitialRegister(int i) throws Exception;

        String getIntegrityAlgorithm(int i) throws Exception;

        String getInternationalURL(int i) throws Exception;

        String getIpsecStatus(int i) throws Exception;

        String getLocalCall(int i) throws Exception;

        String getMTSessionTimer(int i) throws Exception;

        String getMTTimerRefresher(int i) throws Exception;

        String getMaxBandWidth(int i) throws Exception;

        String getMaxBitRate(int i) throws Exception;

        String getMidCallSwitch(int i) throws Exception;

        String getMinBandWidth(int i) throws Exception;

        String getMinBitRate(int i) throws Exception;

        String getMoAmrOa(int i) throws Exception;

        String getMtuValue(int i) throws Exception;

        String getMwiEnable(int i) throws Exception;

        String getMwiSubscrib(int i) throws Exception;

        String getNamedTelEvent(int i) throws Exception;

        String getNationalURL(int i) throws Exception;

        String getNbDefaultRate(int i) throws Exception;

        String getNbRate(int i) throws Exception;

        String getNoAnswerTimer(int i) throws Exception;

        String getPcscfPort(int i) throws Exception;

        String getPreAlerting(int i) throws Exception;

        String getPrecondition(int i) throws Exception;

        String getRSRVCCAlert(int i) throws Exception;

        String getRSRVCCMid(int i) throws Exception;

        String getRSRVCCSupport(int i) throws Exception;

        String getRegExpireValue(int i) throws Exception;

        String getRegSubscrib(int i) throws Exception;

        String getRegSubscribe(int i) throws Exception;

        String getReliable180(int i) throws Exception;

        String getResourceAlways(int i) throws Exception;

        String getRingbackTimer(int i) throws Exception;

        String getRingingTimerValue(int i) throws Exception;

        String getSIPHeaderCompact(int i) throws Exception;

        String getSMSoverIPEnable(int i) throws Exception;

        String getSessionTimer(int i) throws Exception;

        String getTcallTimerValue(int i) throws Exception;

        String getTemergregValue(int i) throws Exception;

        String getTimerRefresher(int i) throws Exception;

        String getTregTimerValue(int i) throws Exception;

        String getUssiEnable(int i) throws Exception;

        String getVideoAudioMerge(int i) throws Exception;

        String getVideoCodeType(int i) throws Exception;

        String getVideoDynamic(int i) throws Exception;

        String getVideoEarly(int i) throws Exception;

        String getVideoMaxResolution(int i) throws Exception;

        String getVideoRRValue(int i) throws Exception;

        String getVideoRsValue(int i) throws Exception;

        String getVideoUpgrade(int i) throws Exception;

        String getVoiceCodeType(int i) throws Exception;

        String getWbDefaultRate(int i) throws Exception;

        String getWbRate(int i) throws Exception;

        String getXcapAuidValue(int i) throws Exception;

        String getXcapPortValue(int i) throws Exception;

        String getXcapURIValue(int i) throws Exception;

        void setActivateCfnl(String str, int i) throws Exception;

        void setAlertingValue(String str, int i) throws Exception;

        void setAudioDynamic(String str, int i) throws Exception;

        void setAudioRRValue(String str, int i) throws Exception;

        void setAudioRSValue(String str, int i) throws Exception;

        void setAudioRTCPTime(String str, int i) throws Exception;

        void setAudioRTPTimeout(String str, int i) throws Exception;

        void setBsfPORTValue(String str, int i) throws Exception;

        void setBsfURIValue(String str, int i) throws Exception;

        void setChannelAwarkMode(String str, int i) throws Exception;

        void setCipherAlgorithm(String str, int i) throws Exception;

        void setConferenceURI(String str, int i) throws Exception;

        void setDefaultBitRate(String str, int i) throws Exception;

        void setDirectAlerting(String str, int i) throws Exception;

        void setDnsSrvEnable(String str, int i) throws Exception;

        void setEvsDefaultBitRate(String str, int i) throws Exception;

        void setEvsRate(String str, int i) throws Exception;

        void setHttpPutMedia(String str, int i) throws Exception;

        void setHttpsEnable(String str, int i) throws Exception;

        void setIPPriority(String str, int i) throws Exception;

        void setInitialRegister(String str, int i) throws Exception;

        void setIntegrityAlgorithm(String str, int i) throws Exception;

        void setInternationalURL(String str, int i) throws Exception;

        void setIpsecStatus(String str, int i) throws Exception;

        void setLocalCall(String str, int i) throws Exception;

        void setMTSessionTimer(String str, int i) throws Exception;

        void setMTTimerRefresher(String str, int i) throws Exception;

        void setMaxBandWidth(String str, int i) throws Exception;

        void setMaxBitRate(String str, int i) throws Exception;

        void setMidCallSwitch(String str, int i) throws Exception;

        void setMinBandWidth(String str, int i) throws Exception;

        void setMinBitRate(String str, int i) throws Exception;

        void setMoAmrOa(String str, int i) throws Exception;

        void setMtuValue(String str, int i) throws Exception;

        void setMwiEnable(String str, int i) throws Exception;

        void setMwiSubscrib(String str, int i) throws Exception;

        void setNamedTelEvent(String str, int i) throws Exception;

        void setNationalURL(String str, int i) throws Exception;

        void setNbDefaultRate(String str, int i) throws Exception;

        void setNbRate(String str, int i) throws Exception;

        void setNoAnswerTimer(String str, int i) throws Exception;

        void setPcscfPort(String str, int i) throws Exception;

        void setPreAlerting(String str, int i) throws Exception;

        void setPrecondition(String str, int i) throws Exception;

        void setRSRVCCAlert(String str, int i) throws Exception;

        void setRSRVCCMid(String str, int i) throws Exception;

        void setRSRVCCSupport(String str, int i) throws Exception;

        void setRegExpireValue(String str, int i) throws Exception;

        void setRegSubscrib(String str, int i) throws Exception;

        void setRegSubscribe(String str, int i) throws Exception;

        void setReliable180(String str, int i) throws Exception;

        void setResourceAlways(String str, int i) throws Exception;

        void setRingbackTimer(String str, int i) throws Exception;

        void setRingingTimerValue(String str, int i) throws Exception;

        void setSIPHeaderCompact(String str, int i) throws Exception;

        void setSMSoverIPEnable(String str, int i) throws Exception;

        void setSessionTimer(String str, int i) throws Exception;

        void setTcallTimerValue(String str, int i) throws Exception;

        void setTemergregValue(String str, int i) throws Exception;

        void setTimerRefresher(String str, int i) throws Exception;

        void setTregTimerValue(String str, int i) throws Exception;

        void setUssiEnable(String str, int i) throws Exception;

        void setVideoAudioMerge(String str, int i) throws Exception;

        void setVideoCodeType(String str, int i) throws Exception;

        void setVideoDynamic(String str, int i) throws Exception;

        void setVideoEarly(String str, int i) throws Exception;

        void setVideoMaxResolution(String str, int i) throws Exception;

        void setVideoRRValue(String str, int i) throws Exception;

        void setVideoRsValue(String str, int i) throws Exception;

        void setVideoUpgrade(String str, int i) throws Exception;

        void setVoiceCodeType(String str, int i) throws Exception;

        void setWbDefaultRate(String str, int i) throws Exception;

        void setWbRate(String str, int i) throws Exception;

        void setXcapAuidValue(String str, int i) throws Exception;

        void setXcapPortValue(String str, int i) throws Exception;

        void setXcapURIValue(String str, int i) throws Exception;
    }

    public interface IVoltePlmnSettings extends BaseInterface {
        String getPlmn1();

        String getPlmn2();

        void setPlmn1(String str);

        void setPlmn2(String str);
    }

    public interface IVolteTemporarySettings {
        void setDomain(String str, int i) throws Exception;

        void setImpi(String str, int i) throws Exception;

        void setImpu(String str, int i) throws Exception;

        void setPcscf(String str) throws Exception;
    }

    public interface IVolteUeSettings extends BaseInterface {

        public enum Bandwidth {
            NB,
            WB,
            SWB,
            FB
        }

        public enum Bitrate {
            KBPS_5_9,
            KBPS_7_2,
            KBPS_8_0,
            KBPS_9_6,
            KBPS_13_2,
            KBPS_16_4,
            KBPS_24_4,
            KBPS_32_0,
            KBPS_48_0,
            KBPS_64_0,
            KBPS_96_0,
            KBPS_128_0
        }

        public enum SmsPdu {
            TPDU,
            RPDU,
            TEXT
        }

        public enum VideoCodec {
            H264,
            H265
        }

        public enum VoiceCodec {
            EVS,
            AMR_WB,
            AMR_NB
        }

        Bandwidth getMaxBandWidth();

        Bitrate getMaxBitRate();

        SmsPdu getSmsPdu();

        String getSprdVolte();

        boolean getVideoCallState();

        VideoCodec[] getVideoCodecType();

        boolean getVideoConferenceState();

        VoiceCodec[] getVoiceCodecType();

        void setMaxBandWidth(Bandwidth bandwidth);

        void setMaxBitRate(Bitrate bitrate);

        void setSmsPdu(SmsPdu smsPdu);

        void setSprdVolte(String str);

        void setVideoCallState(boolean z);

        void setVideoCodecType(VideoCodec[] videoCodecArr);

        void setVideoConferenceState(boolean z);

        void setVoiceCodecType(VoiceCodec[] voiceCodecArr);
    }

    public interface IWcdmaPrefer extends BaseInterface {
        void closeWcdmaPrefer();

        String getWcdmaPrefer();

        void openWcdmaPrefer();
    }

    IEngTestStatus EngTestStatus();

    IBand band();

    IC2kConfig c2kConfig();

    INetInfoC2kUeCap c2kUeCapApi();

    ICallFunc callFunc();

    ICftResult cftResult();

    IControlEvdo controlEvdo();

    IControlMeid controlMeidSwitch();

    ICsfb2GmsDelay csfb2GsmDealyApi();

    IDataServicePrefer dataServicePrefer();

    IDnsFilter dnsFilter();

    IFastDormancy fastDormancy();

    IGprsSetting gprsOper();

    INetInfoGsmUeCap gsmUeCapApi();

    INetInfoLteUeCap lteUeCapApi();

    IManualAssert manualAssert();

    INetInfo netInfo();

    INetInfoC2k1x netInfoC2k1xApi();

    INetInfoC2k netInfoC2kApi();

    INetInfoGsm netInfoGsmApi();

    INetInfoLte netInfoLteApi();

    INetInfoStat netInfoStat();

    INetInfoTdscdma netInfoTdscdmaApi();

    INetInfoWcdma netInfoWcdmaApi();

    INetWorkMode netWorkMode();

    IRadioFunc radioFunc();

    ISimFunc simFunc();

    ISimTrace simTrace();

    ITelephonyInfo telephonyInfo();

    IVamos vamos();

    IVamosCpc vamosCpcApi();

    IVideoType videoType();

    IVolteDevelopSettings volteDevelopSettings();

    IVolteOperatorSettings volteOperatorSettings();

    IVoltePlmnSettings voltePlmnSettings();

    IVolteTemporarySettings volteTemporarySettings();

    IVolteUeSettings volteUeSettings();

    IWcdmaPrefer wcdmaPrefer();

    INetInfoWcdmaUeCap wcdmaUeCapApi();

    public interface IRadioFunc {

        public static class DualRfState {
            public boolean isLteDiversityRxOpened;
            public boolean isLtePrimaryRxOpened;
            public boolean isLtePrimaryTxOpened;
            public boolean isLteSccTxOpened;
            public WcdmaDualRfState wcdmaState;
        }

        public enum WcdmaDualRfState {
            PRIMARY_ONLY,
            PRIMARY_AND_DIVERSITY,
            DIVERSITY_ONLY
        }

        void clearMaxPower(Band band) throws Exception;

        DualRfState getDualRfState() throws Exception;

        int getUeCategory() throws Exception;

        void setDualRfState(DualRfState dualRfState) throws Exception;

        void setMaxPower(Band band, int i) throws Exception;

        public enum Band {
            GSM850(1),
            GSM900(2),
            DCS1800(3),
            DCS1900(4),
            TD19(5),
            TD21(6),
            WBAND1(7),
            WBAND2(8),
            WBAND5(9),
            WBAND8(10);

            private int value;

            Band(int value) {
                this.value = value;
            }

            public int getValue() {
                return this.value;
            }

            public static Band of(int value) {
                for (Band band : values()) {
                    if (band.getValue() == value) {
                        return band;
                    }
                }
                return null;
            }
        }
    }

    public interface IBand {

        public enum GsmBand {
            GSM900,
            DCS1800,
            PCS1900,
            GSM850
        }

        public static class LteBand {
            public int[] tddBands = new int[32];
            public int[] fddBands = new int[32];
        }

        public static class TdBand {
            public boolean band_a;
            public boolean band_f;
        }

        List<CDMA2000Band> getCDMA2000Band(int i) throws Exception;

        List<GsmBand> getGsmBand(int i) throws Exception;

        LteBand getLteBand(int i) throws Exception;

        TdBand getTdBand(int i) throws Exception;

        List<WcdmaBand> getWcdmaBand(int i) throws Exception;

        boolean isCDMA2000BandOpen(int i, CDMA2000Band cDMA2000Band) throws Exception;

        boolean isWcdmaBandOpen(int i, WcdmaBand wcdmaBand) throws Exception;

        void setCDMA2000Band(int i, List<CDMA2000Band> list, List<CDMA2000Band> list2) throws Exception;

        void setGsmBand(int i, List<GsmBand> list) throws Exception;

        void setLteBand(int i, LteBand lteBand) throws Exception;

        void setTdBand(int i, TdBand tdBand) throws Exception;

        void setWcdmaBand(int i, List<WcdmaBand> list, List<WcdmaBand> list2) throws Exception;

        public enum WcdmaBand {
            WCDMA_BAND1(1),
            WCDMA_BAND2(2),
            WCDMA_BAND5(5),
            WCDMA_BAND8(8);

            private int value;

            WcdmaBand(int value) {
                this.value = value;
            }

            public int getValue() {
                return this.value;
            }

            public static WcdmaBand of(int value) {
                for (WcdmaBand band : values()) {
                    if (band.getValue() == value) {
                        return band;
                    }
                }
                return null;
            }
        }

        public enum CDMA2000Band {
            CDMA2000_BAND1(1);

            private int valueC2K;

            CDMA2000Band(int value) {
                this.valueC2K = value;
            }

            public int getValue() {
                return this.valueC2K;
            }

            public static CDMA2000Band of(int value) {
                for (CDMA2000Band band : values()) {
                    if (band.getValue() == value) {
                        return band;
                    }
                }
                return null;
            }
        }
    }
}
