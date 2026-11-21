package com.unisoc.engineermode.core.intf;

/* JADX INFO: loaded from: classes4.dex */
public interface IConnectivityApi {

    public interface IBtEut {

        public static class BTRX {
            public String addr;
            public String channel;
            public String gain;
            public String lephy;
            public String modindex;
            public String pactype;
            public String pattern;
        }

        public static class BTTX {
            public String channel;
            public String datalength;
            public String lephy;
            public String paccnt;
            public String paclen;
            public String pactype;
            public String pattern;
            public String powervalue;
            public String txMode;
        }

        public static class TxMode {
            public String channel;
            public String mode;
        }

        String BTBLERxRead();

        boolean BTBLERxStart(BTRX btrx);

        boolean BTBLERxStop(BTRX btrx);

        boolean BTBLETxStart(BTTX bttx);

        boolean BTBLETxStop(BTTX bttx);

        boolean BTOff();

        String BTRxRead();

        boolean BTRxStart(BTRX btrx);

        boolean BTRxStop(BTRX btrx);

        boolean BTTxStart(BTTX bttx);

        boolean BTTxStop(BTTX bttx);

        boolean btTxModeStart(TxMode txMode);

        boolean btTxModeStop(TxMode txMode);

        boolean controllerBqbEnable(boolean z) throws Exception;

        boolean getControllerBqbState() throws Exception;
    }

    public interface IWcndEngControl {
        boolean isWcndEngRunning() throws Exception;

        void startWcndEng() throws Exception;

        void stopWcndEng() throws Exception;
    }

    public interface IWifi {
    }

    public interface IWifiEut {

        public static class WifiREG {
            public String addr;
            public String length;
            public String type;
            public String value;
        }

        public static class WifiRX {
            public String band;
            public String bandcbw;
            public String bandsbw;
            public String bandwidth;
            public String channel;
            public String path;
            public String rxtestnum;
        }

        public static class WifiTX {
            public String band;
            public String bandsbw;
            public String bandwidth;
            public String channel;
            public String guardinterval;
            public String mode;
            public String path;
            public String pktcnt;
            public String pktlength;
            public String powerlevel;
            public String preamble;
            public String rate;
        }

        void disableAdaptive();

        void disableBeamforming();

        void disablePowerSave();

        void disableScan();

        void disableSleepless();

        void disableStbcRx();

        boolean down();

        void enableAdaptive();

        void enableBeamforming();

        void enablePowerSave();

        void enableScan();

        void enableSleepless();

        void enableStbcRx();

        boolean getAdaptiveState();

        boolean getBeamformingStatus();

        boolean getPowerSaveState();

        boolean getScanState();

        boolean getSleeplessState();

        boolean getStbcRxStatus();

        boolean insMod();

        String regr(WifiREG wifiREG);

        String regw(WifiREG wifiREG);

        boolean removeMod();

        String rxResult();

        boolean rxStart(WifiRX wifiRX);

        boolean rxStop();

        void setTxPower(int i);

        boolean start();

        boolean stop();

        boolean txCw(WifiTX wifiTX);

        boolean txGo(WifiTX wifiTX);

        boolean txStop();

        boolean up();
    }

    IBtEut btEut();

    IWcndEngControl wcndEngControl();

    IWifiEut wifiEut();
}
