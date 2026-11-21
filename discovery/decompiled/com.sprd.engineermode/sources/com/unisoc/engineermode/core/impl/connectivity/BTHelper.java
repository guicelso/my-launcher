package com.unisoc.engineermode.core.impl.connectivity;

import android.util.Log;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.IConnectivityApi;
import com.unisoc.engineermode.core.utils.HidlUtils;
import com.unisoc.engineermode.core.utils.SocketUtils;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class BTHelper implements IConnectivityApi.IBtEut {
    private static final String CMD_BT_BLE_RX_READ = "eng bt set_nosig_rx_recv_data_le";
    private static final String CMD_BT_BLE_RX_START = "eng bt set_nosig_rx_testmode ";
    private static final String CMD_BT_BLE_RX_START_NEW = "eng bt le_enhanced_receiver_test ";
    private static final String CMD_BT_BLE_STOP_NEW = "eng bt le_test_end";
    private static final String CMD_BT_BLE_TX = "eng bt set_nosig_tx_testmode ";
    private static final String CMD_BT_BLE_TX_NEW = "eng bt le_enhanced_transmitter_test ";
    private static final String CMD_BT_OFF = "eng bt bt_off";
    private static final String CMD_BT_ON = "eng bt bt_on";
    private static final String CMD_BT_RX_READ = "eng bt set_nosig_rx_recv_data";
    private static final String CMD_BT_RX_START = "eng bt set_nosig_rx_testmode ";
    private static final String CMD_BT_SHARED_PATH = "eng bt set_rf_path 2";
    private static final String CMD_BT_SINGLE_PATH = "eng bt set_rf_path 1";
    private static final String CMD_BT_TX = "eng bt set_nosig_tx_testmode ";
    private static final String CMD_BT_TX_CW = "eng bt set_nosig_send_cw ";
    private static final String CMD_BT_TX_MODE_CW = "eng bt set_modulation_send_cw ";
    private static final String COMM_CONTROLLER_DISABLE = "\r\nAT+SPBQBTEST=0\r\n";
    private static final String COMM_CONTROLLER_ENABLE = "\r\nAT+SPBQBTEST=1\r\n";
    private static final String COMM_CONTROLLER_TRIGGER = "\r\nAT+SPBQBTEST=?\r\n";
    private static final String CONTROLLER_BQB_SOCKET = "/data/misc/.bqb_ctrl";
    private static final String MODE_CW = "1";
    private static final String NOTIFY_BQB_DISABLE = "\r\n+SPBQBTEST OK: DISABLE\r\n";
    private static final String NOTIFY_BQB_ENABLE = "\r\n+SPBQBTEST OK: ENABLED\r\n";
    private static final String PERSIST_BT_RF_PATH = "persist.sys.bt.rf.path";
    private static final String TAG = "BTHelper";
    private static final String TRIGGER_BQB_DISABLE = "\r\n+SPBQBTEST OK: AT\r\n";
    private static final String TRIGGER_BQB_ENABLE = "\r\n+SPBQBTEST OK: BQB\r\n";
    private static final String WCND_SOCKET_NAME = "wcnd_eng ";
    public static boolean isBTOn = false;
    private static String mCmd;

    public static class BtHelperHolder {
        static final IConnectivityApi.IBtEut INSTANCE = new BTHelper();
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean BTTxStart(IConnectivityApi.IBtEut.BTTX tx) throws IllegalAccessException, InvocationTargetException {
        String btOn = sendCmd(CMD_BT_ON);
        if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            String rfPathStatus = SystemPropertiesProxy.get(PERSIST_BT_RF_PATH, MODE_CW);
            Log.d(TAG, "method:BTTxStart rfPathStatus is: " + rfPathStatus);
            if (MODE_CW.equals(rfPathStatus)) {
                sendCmd(CMD_BT_SINGLE_PATH);
            } else if ("2".equals(rfPathStatus)) {
                sendCmd(CMD_BT_SHARED_PATH);
            }
        }
        if (btOn == null || !btOn.contains("bt_status=1")) {
            return false;
        }
        isBTOn = true;
        if (tx.txMode.equals(MODE_CW)) {
            mCmd = String.format("%s1 0 %s", CMD_BT_TX_CW, tx.channel);
        } else {
            mCmd = String.format("%s1 0 %s %s %s %s 1 %s %s", "eng bt set_nosig_tx_testmode ", tx.pattern, tx.channel, tx.pactype, tx.paclen, tx.powervalue, tx.paccnt);
        }
        Log.d(TAG, "BTTxStart   cmd is " + mCmd);
        String ret = sendCmd(mCmd);
        if (ret == null || !ret.contains("ok")) {
            return false;
        }
        return true;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean BTBLETxStart(IConnectivityApi.IBtEut.BTTX tx) throws IllegalAccessException, InvocationTargetException {
        String btOn = sendCmd(CMD_BT_ON);
        if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            String rfPathStatus = SystemPropertiesProxy.get(PERSIST_BT_RF_PATH, MODE_CW);
            Log.d(TAG, "method:BTBLETxStart rfPathStatus is: " + rfPathStatus);
            if (MODE_CW.equals(rfPathStatus)) {
                sendCmd(CMD_BT_SINGLE_PATH);
            } else if ("2".equals(rfPathStatus)) {
                sendCmd(CMD_BT_SHARED_PATH);
            }
        }
        if (btOn == null || !btOn.contains("bt_status=1")) {
            return false;
        }
        isBTOn = true;
        if (tx.txMode.equals(MODE_CW)) {
            mCmd = String.format("%s1 1 %s", CMD_BT_TX_CW, tx.channel);
        } else if (Const.isMarlin2()) {
            mCmd = String.format("%s1 1 %s %s 0 %s 1 %s %s", "eng bt set_nosig_tx_testmode ", tx.pattern, tx.channel, tx.datalength, tx.powervalue, tx.paccnt);
        } else if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            mCmd = String.format("%s%s %s %s %s %s %s", CMD_BT_BLE_TX_NEW, tx.channel, tx.datalength, tx.pattern, tx.lephy, tx.paccnt, tx.powervalue);
        } else {
            mCmd = String.format("%s1 1 %s %s 0 %s 1 %s %s", "eng bt set_nosig_tx_testmode ", tx.pattern, tx.channel, tx.datalength, tx.powervalue, tx.paccnt);
        }
        Log.d(TAG, "BTBLETxStart mCmd is " + mCmd);
        String ret = sendCmd(mCmd);
        Log.d(TAG, "BTBLETxStart bt_ble_tx is " + ret);
        if (ret == null || !ret.contains("ok")) {
            return false;
        }
        return true;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean BTTxStop(IConnectivityApi.IBtEut.BTTX tx) {
        if (tx.txMode.equals(MODE_CW)) {
            mCmd = String.format("%s0 0 %s", CMD_BT_TX_CW, tx.channel);
        } else {
            mCmd = String.format("%s0 0 %s %s %s %s 1 %s %s", "eng bt set_nosig_tx_testmode ", tx.pattern, tx.channel, tx.pactype, tx.paclen, tx.powervalue, tx.paccnt);
        }
        Log.d(TAG, "BTTxStop cmd is " + mCmd);
        String ret = sendCmd(mCmd);
        return ret != null && ret.contains("ok");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean BTBLETxStop(IConnectivityApi.IBtEut.BTTX tx) {
        if (tx.txMode.equals(MODE_CW)) {
            mCmd = String.format("%s0 1 %s", CMD_BT_TX_CW, tx.channel);
        } else if (Const.isMarlin2()) {
            mCmd = String.format("%s0 1 %s %s 0 %s 1 %s %s", "eng bt set_nosig_tx_testmode ", tx.pattern, tx.channel, tx.datalength, tx.powervalue, tx.paccnt);
        } else if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            mCmd = CMD_BT_BLE_STOP_NEW;
        } else {
            mCmd = String.format("%s0 1 %s %s 0 %s 1 %s %s", "eng bt set_nosig_tx_testmode ", tx.pattern, tx.channel, tx.datalength, tx.powervalue, tx.paccnt);
        }
        Log.d(TAG, "BTBLETxStop cmd is " + mCmd);
        String ret = sendCmd(mCmd);
        return ret != null && ret.contains("ok");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean BTOff() {
        String ret = sendCmd(CMD_BT_OFF);
        if (ret == null || !ret.contains("bt_status=0")) {
            return false;
        }
        isBTOn = false;
        return true;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean BTRxStart(IConnectivityApi.IBtEut.BTRX rx) throws IllegalAccessException, InvocationTargetException {
        String btOn = sendCmd(CMD_BT_ON);
        if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            String rfPathStatus = SystemPropertiesProxy.get(PERSIST_BT_RF_PATH, MODE_CW);
            Log.d(TAG, "method:BTRxStart rfPathStatus is: " + rfPathStatus);
            if (rfPathStatus.equals(MODE_CW)) {
                sendCmd(CMD_BT_SINGLE_PATH);
            } else if (rfPathStatus.equals("2")) {
                sendCmd(CMD_BT_SHARED_PATH);
            }
        }
        if (btOn == null || !btOn.contains("bt_status=1")) {
            return false;
        }
        isBTOn = true;
        mCmd = String.format("%s1 0 %s %s %s %s %s", "eng bt set_nosig_rx_testmode ", rx.pattern, rx.channel, rx.pactype, rx.gain, rx.addr);
        Log.d(TAG, "BTRxStart cmd is " + mCmd);
        String ret = sendCmd(mCmd);
        if (ret == null || !ret.contains("ok")) {
            return false;
        }
        return true;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean BTBLERxStart(IConnectivityApi.IBtEut.BTRX rx) throws IllegalAccessException, InvocationTargetException {
        String btOn = sendCmd(CMD_BT_ON);
        if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            String rfPathStatus = SystemPropertiesProxy.get(PERSIST_BT_RF_PATH, MODE_CW);
            Log.d(TAG, "method:BTBLERxStart rfPathStatus is: " + rfPathStatus);
            if (MODE_CW.equals(rfPathStatus)) {
                sendCmd(CMD_BT_SINGLE_PATH);
            } else if ("2".equals(rfPathStatus)) {
                sendCmd(CMD_BT_SHARED_PATH);
            }
        }
        Log.d(TAG, "BTBLERxStart bt_on: " + btOn);
        if (btOn == null || !btOn.contains("bt_status=1")) {
            return false;
        }
        isBTOn = true;
        if (Const.isMarlin2()) {
            mCmd = String.format("%s1 1 0 %s 0 %s 0", "eng bt set_nosig_rx_testmode ", rx.channel, rx.gain);
        } else if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            mCmd = String.format("%s%s %s %s 0 %s 0", CMD_BT_BLE_RX_START_NEW, rx.channel, rx.lephy, rx.modindex, rx.gain);
        } else {
            mCmd = String.format("%s1 1 %s %s %s %s 0", "eng bt set_nosig_rx_testmode ", rx.pattern, rx.channel, rx.pactype, rx.gain);
        }
        Log.d(TAG, "BTBLERxStart cmd is " + mCmd);
        String ret = sendCmd(mCmd);
        if (ret == null || !ret.contains("ok")) {
            return false;
        }
        return true;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public String BTRxRead() {
        return sendCmd(CMD_BT_RX_READ);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public String BTBLERxRead() {
        return sendCmd(CMD_BT_BLE_RX_READ);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean BTRxStop(IConnectivityApi.IBtEut.BTRX rx) {
        mCmd = String.format("%s0 0 %s %s %s %s %s", "eng bt set_nosig_rx_testmode ", rx.pattern, rx.channel, rx.pactype, rx.gain, rx.addr);
        Log.d(TAG, "BTRXStop cmd is " + mCmd);
        String ret = sendCmd(mCmd);
        return ret != null && ret.contains("ok");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean BTBLERxStop(IConnectivityApi.IBtEut.BTRX rx) {
        if (Const.isMarlin2()) {
            mCmd = String.format("%s0 1 %s %s %s %s %s", "eng bt set_nosig_rx_testmode ", rx.pattern, rx.channel, rx.pactype, rx.gain, rx.addr);
        } else if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            mCmd = CMD_BT_BLE_STOP_NEW;
        } else {
            mCmd = String.format("%s0 1 %s %s %s %s %s", "eng bt set_nosig_rx_testmode ", rx.pattern, rx.channel, rx.pactype, rx.gain, rx.addr);
        }
        Log.d(TAG, "BTBLERXStop cmd is " + mCmd);
        String btStop = sendCmd(mCmd);
        return btStop != null && btStop.contains("ok");
    }

    private String sendCmd(String command) {
        return HidlUtils.sendCmd(WCND_SOCKET_NAME + command);
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean btTxModeStart(IConnectivityApi.IBtEut.TxMode txMode) {
        String btOn = sendCmd(CMD_BT_ON);
        if (btOn == null || !btOn.contains("bt_status=1")) {
            return false;
        }
        isBTOn = true;
        mCmd = String.format("%s1 %s %s", CMD_BT_TX_MODE_CW, txMode.mode, txMode.channel);
        String ret = sendCmd(mCmd);
        if (ret == null || !ret.contains("ok")) {
            return false;
        }
        return true;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean btTxModeStop(IConnectivityApi.IBtEut.TxMode txMode) {
        mCmd = String.format("%s0 %s %s", CMD_BT_TX_MODE_CW, txMode.mode, txMode.channel);
        String ret = sendCmd(mCmd);
        return ret != null && ret.contains("ok");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean getControllerBqbState() throws IOException {
        String ret = SocketUtils.sendCmd(CONTROLLER_BQB_SOCKET, COMM_CONTROLLER_TRIGGER);
        if (ret != null) {
            if (ret.contains(TRIGGER_BQB_ENABLE)) {
                return true;
            }
            if (ret.contains(TRIGGER_BQB_DISABLE)) {
                return false;
            }
        }
        throw new OperationFailedException();
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IBtEut
    public boolean controllerBqbEnable(boolean enable) throws IOException {
        String cmd = enable ? COMM_CONTROLLER_ENABLE : COMM_CONTROLLER_DISABLE;
        String ret = SocketUtils.sendCmd(CONTROLLER_BQB_SOCKET, cmd);
        if (ret != null) {
            if (ret.contains(NOTIFY_BQB_ENABLE)) {
                return true;
            }
            if (ret.contains(NOTIFY_BQB_DISABLE)) {
                return false;
            }
        }
        throw new OperationFailedException();
    }
}
