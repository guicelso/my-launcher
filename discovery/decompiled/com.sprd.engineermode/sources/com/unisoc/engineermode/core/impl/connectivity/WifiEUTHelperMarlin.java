package com.unisoc.engineermode.core.impl.connectivity;

import android.text.TextUtils;
import android.util.Log;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.IConnectivityApi;
import com.unisoc.engineermode.core.utils.HidlUtils;

/* JADX INFO: loaded from: classes4.dex */
public class WifiEUTHelperMarlin implements IConnectivityApi.IWifiEut {
    private static final String CMD_DISABLED_POWER_SAVE = "iwnpi wlan0 lna_off";
    private static final String CMD_ENABLED_POWER_SAVE = "iwnpi wlan0 lna_on";
    private static final String CMD_GET_POWER_SAVE_STATUS = "iwnpi wlan0 lna_status";
    private static final String CMD_SUCCESS = "OK";
    private static final String GET_ADAPTIVE_STATUS = "iwnpi wlan0 set_eng_mode 2";
    private static final String GET_BEAMFORMING_STATUS = "iwnpi wlan0 get_beamf_status";
    private static final String GET_SCAN_STATUS = "iwnpi wlan0 set_eng_mode 4";
    private static final String GET_STBC_RX_STATUS = "iwnpi wlan0 get_rxstbc_status";
    private static final String SET_ADAPTIVE_OFF = "iwnpi wlan0 set_eng_mode 1 0";
    private static final String SET_ADAPTIVE_ON = "iwnpi wlan0 set_eng_mode 1 1";
    private static final String SET_BEAMFORMING_OFF = "iwnpi wlan0 set_beamf_status 0";
    private static final String SET_BEAMFORMING_ON = "iwnpi wlan0 set_beamf_status 1";
    private static final String SET_EUT_CBW = "iwnpi wlan0 set_cbw ";
    private static final String SET_EUT_CW_START = "iwnpi wlan0 sin_wave";
    private static final String SET_EUT_GET_RXOK = "iwnpi wlan0 get_rx_ok";
    private static final String SET_EUT_GUARDINTERVAL = "iwnpi wlan0 set_gi ";
    private static final String SET_EUT_PATH = "iwnpi wlan0 set_chain ";
    private static final String SET_EUT_READ = "iwnpi wlan0 get_reg ";
    private static final String SET_EUT_RX_START = "iwnpi wlan0 rx_start";
    private static final String SET_EUT_RX_STOP = "iwnpi wlan0 rx_stop";
    private static final String SET_EUT_SBW = "iwnpi wlan0 set_sbw ";
    private static final String SET_EUT_SET_CHANNEL = "iwnpi wlan0 set_channel ";
    private static final String SET_EUT_SET_COUNT = "iwnpi wlan0 set_tx_count ";
    private static final String SET_EUT_SET_LENGTH = "iwnpi wlan0 set_pkt_len ";
    private static final String SET_EUT_SET_POWER = "iwnpi wlan0 set_tx_power ";
    private static final String SET_EUT_SET_PREAMBLE = "iwnpi wlan0 set_preamble ";
    private static final String SET_EUT_SET_RATE = "iwnpi wlan0 set_rate ";
    private static final String SET_EUT_START = "iwnpi wlan0 start";
    private static final String SET_EUT_STOP = "iwnpi wlan0 stop";
    private static final String SET_EUT_TX_START = "iwnpi wlan0 tx_start";
    private static final String SET_EUT_TX_STOP = "iwnpi wlan0 tx_stop";
    private static final String SET_EUT_UP = "iwnpi wlan0 ifaceup";
    private static final String SET_EUT_WRITE = "iwnpi wlan0 set_reg ";
    private static final String SET_SCAN_OFF = "iwnpi wlan0 set_eng_mode 3 0";
    private static final String SET_SCAN_ON = "iwnpi wlan0 set_eng_mode 3 1";
    private static final String SET_STBC_RX_OFF = "iwnpi wlan0 set_rxstbc_status 0";
    private static final String SET_STBC_RX_ON = "iwnpi wlan0 set_rxstbc_status 1";
    private static final String TAG = "WifiEUTHelperMarlin";
    private static final String WCND_ENG_SOCKET_NAME = "wcnd_eng ";
    private static final String WCND_SOCKET_NAME = "wcnd ";
    private static final String WIFI_DRIVER_INSMOD = "iwnpi wlan0 insmod";
    private static final String WIFI_DRIVER_RMMOD = "iwnpi wlan0 rmmod";
    private int tempPower = 0;
    private String chainVaule = "1";

    public static class WifiEutMarlinImplHolder {
        static final IConnectivityApi.IWifiEut INSTANCE = new WifiEUTHelperMarlin();
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean up() {
        return sendCmdEng(SET_EUT_UP);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean start() {
        return sendCmdEng(SET_EUT_START);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean down() {
        return sendCmdEng(SET_EUT_STOP);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean stop() {
        return sendCmdEng(SET_EUT_STOP);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean txGo(IConnectivityApi.IWifiEut.WifiTX tx) {
        getChainValue(tx);
        this.tempPower = Integer.parseInt(tx.powerlevel, 10);
        if (sendCmdEng(SET_EUT_SET_LENGTH + tx.pktlength)) {
            if (sendCmdEng(SET_EUT_SET_COUNT + tx.pktcnt)) {
                if (sendCmdEng(SET_EUT_SET_POWER + this.tempPower)) {
                    if (sendCmdEng(SET_EUT_SET_PREAMBLE + tx.preamble)) {
                        if (!Const.isMarlin2()) {
                            if (!sendCmdEng(SET_EUT_PATH + this.chainVaule)) {
                                return false;
                            }
                            if (!sendCmdEng(SET_EUT_CBW + tx.bandwidth)) {
                                return false;
                            }
                            if (!sendCmdEng(SET_EUT_SBW + tx.bandsbw)) {
                                return false;
                            }
                            if (!sendCmdEng(SET_EUT_SET_CHANNEL + tx.channel)) {
                                return false;
                            }
                            if (!sendCmdEng(SET_EUT_SET_RATE + tx.rate)) {
                                return false;
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(SET_EUT_GUARDINTERVAL);
                            sb.append(tx.guardinterval);
                            return sendCmdEng(sb.toString()) && sendCmdEng(SET_EUT_TX_START);
                        }
                        if (!sendCmdEng(SET_EUT_CBW + tx.bandwidth)) {
                            return false;
                        }
                        if (!sendCmdEng(SET_EUT_SET_CHANNEL + tx.channel)) {
                            return false;
                        }
                        if (!sendCmdEng(SET_EUT_SET_RATE + tx.rate)) {
                            return false;
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(SET_EUT_GUARDINTERVAL);
                        sb2.append(tx.guardinterval);
                        return sendCmdEng(sb2.toString()) && sendCmdEng(SET_EUT_TX_START);
                    }
                }
            }
        }
        sendCmdEng(SET_EUT_TX_START);
        return false;
    }

    private void getChainValue(IConnectivityApi.IWifiEut.WifiTX tx) {
        if (TextUtils.isEmpty(tx.powerlevel)) {
            tx.powerlevel = "0";
        }
        if (tx.path != null) {
            if (tx.path.equals("MIMO")) {
                this.chainVaule = "3";
            } else if (tx.path.equals("Diversity")) {
                this.chainVaule = "2";
            } else {
                this.chainVaule = "1";
            }
        }
        Log.d(TAG, "tx.path: " + tx.path);
        Log.d(TAG, "tx.rate: " + tx.rate);
        Log.d(TAG, "tx.pktlength: " + tx.pktlength);
        Log.d(TAG, "tx.pktcnt: " + tx.pktcnt);
        Log.d(TAG, "tx.preamble: " + tx.preamble);
        Log.d(TAG, "tx.bandwidth: " + tx.bandwidth);
        Log.d(TAG, "tx.bandsbw: " + tx.bandsbw);
        Log.d(TAG, "tx.channel: " + tx.channel);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean txStop() {
        return sendCmdEng(SET_EUT_TX_STOP);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean txCw(IConnectivityApi.IWifiEut.WifiTX tx) {
        getChainValue(tx);
        if (sendCmdEng(SET_EUT_SET_LENGTH + tx.pktlength)) {
            if (sendCmdEng(SET_EUT_SET_COUNT + tx.pktcnt)) {
                if (sendCmdEng(SET_EUT_SET_POWER + this.tempPower)) {
                    if (sendCmdEng(SET_EUT_SET_PREAMBLE + tx.preamble)) {
                        if (!Const.isMarlin2()) {
                            if (!sendCmdEng(SET_EUT_PATH + this.chainVaule)) {
                                return false;
                            }
                            if (!sendCmdEng(SET_EUT_CBW + tx.bandwidth)) {
                                return false;
                            }
                            if (!sendCmdEng(SET_EUT_SBW + tx.bandsbw)) {
                                return false;
                            }
                            if (!sendCmdEng(SET_EUT_SET_CHANNEL + tx.channel)) {
                                return false;
                            }
                            if (!sendCmdEng(SET_EUT_SET_RATE + tx.rate)) {
                                return false;
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(SET_EUT_GUARDINTERVAL);
                            sb.append(tx.guardinterval);
                            return sendCmdEng(sb.toString()) && sendCmdEng(SET_EUT_CW_START);
                        }
                        if (!sendCmdEng(SET_EUT_CBW + tx.bandwidth)) {
                            return false;
                        }
                        if (!sendCmdEng(SET_EUT_SET_CHANNEL + tx.channel)) {
                            return false;
                        }
                        if (!sendCmdEng(SET_EUT_SET_RATE + tx.rate)) {
                            return false;
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(SET_EUT_GUARDINTERVAL);
                        sb2.append(tx.guardinterval);
                        return sendCmdEng(sb2.toString()) && sendCmdEng(SET_EUT_CW_START);
                    }
                }
            }
        }
        sendCmdEng(SET_EUT_CW_START);
        return false;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean rxStart(IConnectivityApi.IWifiEut.WifiRX rx) {
        String chain = "1";
        if (rx.path != null) {
            if (rx.path.equals("MIMO")) {
                chain = "3";
            } else if (rx.path.equals("Diversity")) {
                chain = "2";
            } else {
                chain = "1";
            }
        }
        if (!sendCmdEng(SET_EUT_SET_CHANNEL + rx.channel)) {
            return false;
        }
        if (!Const.isMarlin2()) {
            if (!sendCmdEng(SET_EUT_PATH + chain)) {
                return false;
            }
            if (!sendCmdEng(SET_EUT_CBW + rx.bandcbw)) {
                return false;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(SET_EUT_SBW);
            sb.append(rx.bandsbw);
            return sendCmdEng(sb.toString()) && sendCmdEng(SET_EUT_RX_START);
        }
        return sendCmdEng(SET_EUT_RX_START);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public String rxResult() {
        return sendCmdEngWithResult(SET_EUT_GET_RXOK);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean rxStop() {
        return sendCmdEng(SET_EUT_RX_STOP);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public String regr(IConnectivityApi.IWifiEut.WifiREG reg) {
        return sendCmdEngWithResult(SET_EUT_READ + reg.type + " " + reg.addr + " " + reg.length);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public String regw(IConnectivityApi.IWifiEut.WifiREG reg) {
        return sendCmdEngWithResult(SET_EUT_WRITE + reg.type + " " + reg.addr + " " + reg.value);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean getPowerSaveState() {
        String ret = sendCmdEngWithResult(CMD_GET_POWER_SAVE_STATUS);
        checkResult(ret);
        String[] str = ret.split("\\:");
        String status = str[1].trim();
        return "1".equals(status);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void enablePowerSave() {
        if (!sendCmdEng(CMD_ENABLED_POWER_SAVE)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void disablePowerSave() {
        if (!sendCmdEng(CMD_DISABLED_POWER_SAVE)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean insMod() {
        return sendCmdEng(WIFI_DRIVER_INSMOD);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean removeMod() {
        return sendCmdEng(WIFI_DRIVER_RMMOD);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void setTxPower(int power) {
        if (!sendCmdEng(SET_EUT_SET_POWER + power)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void enableAdaptive() {
        if (!sendCmd(SET_ADAPTIVE_ON)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void disableAdaptive() {
        if (!sendCmd(SET_ADAPTIVE_OFF)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean getAdaptiveState() {
        String ret = sendCmdWithResult(GET_ADAPTIVE_STATUS);
        return ret.contains("status: 1");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void enableSleepless() {
        if (!sendCmd(CMD_DISABLED_POWER_SAVE)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void disableSleepless() {
        if (!sendCmd(CMD_ENABLED_POWER_SAVE)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean getSleeplessState() {
        String ret = sendCmdWithResult(CMD_GET_POWER_SAVE_STATUS);
        return ret.contains("status: 1");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void enableScan() {
        if (!sendCmd(SET_SCAN_ON)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void disableScan() {
        if (!sendCmd(SET_SCAN_OFF)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean getScanState() {
        String ret = sendCmdWithResult(GET_SCAN_STATUS);
        return ret.contains("status: 1");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void enableBeamforming() {
        if (!sendCmd(SET_BEAMFORMING_ON)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void disableBeamforming() {
        if (!sendCmd(SET_BEAMFORMING_OFF)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean getBeamformingStatus() {
        String ret = sendCmdWithResult(GET_BEAMFORMING_STATUS);
        return ret.contains("status: 1");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void enableStbcRx() {
        if (!sendCmd(SET_STBC_RX_ON)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public void disableStbcRx() {
        if (!sendCmd(SET_STBC_RX_OFF)) {
            fail();
        }
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWifiEut
    public boolean getStbcRxStatus() {
        String ret = sendCmdWithResult(GET_STBC_RX_STATUS);
        return ret.contains("status: 1");
    }

    private boolean sendCmd(String cmd) {
        String ret = sendCmdWithResult(cmd);
        return ret != null && ret.startsWith("OK");
    }

    private boolean sendCmdEng(String cmd) {
        String ret = sendCmdEngWithResult(cmd);
        return ret != null && ret.startsWith("OK");
    }

    private String sendCmdWithResult(String command) {
        return HidlUtils.sendCmd("wcnd eng " + command);
    }

    private String sendCmdEngWithResult(String command) {
        return HidlUtils.sendCmd("wcnd_eng eng " + command);
    }

    private void checkResult(String result) {
        if (result != null && result.startsWith("OK")) {
            return;
        }
        fail();
    }

    private void fail() {
        throw new OperationFailedException();
    }
}
