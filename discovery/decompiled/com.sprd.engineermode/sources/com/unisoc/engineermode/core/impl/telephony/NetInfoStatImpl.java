package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.data.telephony.netinfostat.NetInfoStatData;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.text.NumberFormat;

/* JADX INFO: loaded from: classes4.dex */
class NetInfoStatImpl implements ITelephonyApi.INetInfoStat {
    private static final String TAG = "NetInfoStat";
    private static final String[] reselect_lte_desc = {"3G->3G", "3G->2G", "2G->2G", "2G->3G", "3G->4G", "2G->4G", "4G->4G", "4G->2G", "4G->3G"};
    private static final String[] reselect_desc = {"3G->3G", "3G->2G", "2G->2G", "2G->3G"};
    private static final String[] handover_desc = {"3G->3G HO", "3G->2G HO", "2G->2G HO", "2G->3G HO", "3G->2G CCO", "2G->2G CCO", "2G->3G CCO"};
    private static final String[] handover_lte_desc = {"3G->3G HO", "3G->2G HO", "2G->2G HO", "2G->3G HO", "3G->2G CCO", "2G->2G CCO", "2G->3G CCO", "2G->4G CCO", "4G->2G CCO", "2G->4G HO", "3G->4G HO", "4G->4G HO", "4G->3G HO", "4G->2G HO", "SRVCC 2G", "SRVCC 3G"};
    private static final String[] attachtime_desc = {"Time on 2G", "Time on 3G", "Time on Unknown", "All the Time"};
    private static final String[] attachtime_lte_desc = {"Time on 2g", "Time on 3g", "Time on unknown", "Time all", "Time on lte", "Time on volte", "Time on lte ca", "Count of lte ca", "Count of non-lte ca"};
    private static final String[] dropcount_desc = {"Drop times on 2G", "Drop times on 3G"};
    private static final String[] dropcount_lte_desc = {"Drop times on 2G", "Drop times on 3G", "Drop times on 4G"};

    NetInfoStatImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoStat
    public NetInfoStatData getReselectInfo(int simIdx) throws Exception {
        String[] desc;
        int itemCount;
        String atRsp = IATUtils.sendATCmd("AT+SPENGMD=0,7,1", simIdx);
        if (!atRsp.contains("OK")) {
            throw new Exception("AT reponse error");
        }
        if (TelephonyApiImpl.getInstance().telephonyInfo().isSupportLte()) {
            desc = reselect_lte_desc;
            itemCount = reselect_lte_desc.length;
        } else {
            desc = reselect_desc;
            itemCount = reselect_desc.length;
        }
        String[] str = atRsp.replaceAll("--", "-").split("-");
        int array_len = str.length;
        Log.d(TAG, "atRSP.length = " + array_len);
        for (int i = 0; i < array_len; i++) {
            str[i] = str[i].replaceAll("\r|\n", "");
        }
        int i2 = array_len - 1;
        str[i2] = str[array_len - 1].substring(0, str[array_len - 1].length() - 2);
        Log.d(TAG, "split str len: " + str.length);
        int temp = 0;
        NetInfoStatData.Item[] items = new NetInfoStatData.Item[itemCount];
        for (int i3 = 0; i3 < itemCount; i3++) {
            Log.d(TAG, "current item : " + i3);
            items[i3] = new NetInfoStatData.Item();
            items[i3].desc = desc[i3];
            int temp2 = temp + 1;
            items[i3].success = str[temp];
            int temp3 = temp2 + 1;
            items[i3].fail = str[temp2];
            int temp4 = temp3 + 1;
            items[i3].ratio = convertPercent(str[temp3], items[i3].success);
            if (temp4 == 12) {
                temp4 += 2;
            }
            temp = temp4;
        }
        if (TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WG || TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WW) {
            items[0].delay = str[32];
            items[1].delay = str[33];
            items[2].delay = str[29];
            items[3].delay = str[30];
        } else {
            items[0].delay = str[32];
            items[1].delay = str[33];
            items[2].delay = str[29];
            items[3].delay = str[30];
            items[4].delay = str[34];
            items[5].delay = str[31];
            items[6].delay = str[35];
            items[7].delay = str[36];
            items[8].delay = str[37];
        }
        return new NetInfoStatData(items);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoStat
    public NetInfoStatData getHandOverInfo(int simIdx) throws Exception {
        String[] desc;
        int itemCount;
        String atRsp = IATUtils.sendATCmd("AT+SPENGMD=0,7,2", simIdx);
        if (!atRsp.contains("OK")) {
            throw new Exception("AT reponse error");
        }
        if (TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WG || TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WW) {
            desc = handover_desc;
            itemCount = handover_desc.length;
        } else {
            desc = handover_lte_desc;
            itemCount = handover_lte_desc.length;
        }
        String[] str = atRsp.split("-");
        int array_len = str.length;
        for (int i = 0; i < str.length; i++) {
            str[i] = str[i].replaceAll("\r|\n", "");
        }
        int i2 = array_len - 1;
        str[i2] = str[array_len - 1].substring(0, str[array_len - 1].length() - 2);
        int temp = 0;
        NetInfoStatData.Item[] items = new NetInfoStatData.Item[itemCount];
        int i3 = 0;
        while (i3 < itemCount - 2) {
            Log.d(TAG, "current item : " + i3);
            items[i3] = new NetInfoStatData.Item();
            items[i3].desc = desc[i3];
            int temp2 = temp + 1;
            items[i3].success = str[temp];
            int temp3 = temp2 + 1;
            items[i3].fail = str[temp2];
            items[i3].ratio = convertPercent(str[temp3], items[i3].success);
            Log.d(TAG, items[i3].desc + ": " + items[i3].success + "," + items[i3].fail);
            i3++;
            temp = temp3 + 1;
        }
        int i4 = str.length;
        if (i4 >= 68) {
            int temp4 = temp + 18;
            int i5 = itemCount - 2;
            while (i5 < itemCount) {
                items[i5] = new NetInfoStatData.Item();
                items[i5].desc = desc[i5];
                int temp5 = temp4 + 1;
                items[i5].success = str[temp4];
                int temp6 = temp5 + 1;
                items[i5].fail = str[temp5];
                items[i5].ratio = convertPercent(str[temp6], items[i5].success);
                i5++;
                temp4 = temp6 + 1;
            }
        } else {
            for (int i6 = itemCount - 2; i6 < itemCount; i6++) {
                items[i6] = new NetInfoStatData.Item();
                items[i6].desc = desc[i6];
                items[i6].success = "NA";
                items[i6].fail = "NA";
                items[i6].ratio = "NA";
            }
        }
        if (TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WG || TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WW) {
            items[0].delay = str[48];
            items[1].delay = str[49];
            items[2].delay = str[42];
            items[3].delay = str[44];
            items[4].delay = str[50];
            items[5].delay = str[43];
            items[6].delay = str[45];
        } else {
            items[0].delay = str[48];
            items[1].delay = str[49];
            items[2].delay = str[42];
            items[3].delay = str[44];
            items[4].delay = str[50];
            items[5].delay = str[43];
            items[6].delay = str[45];
            items[7].delay = str[47];
            items[8].delay = str[54];
            items[9].delay = str[46];
            items[10].delay = str[51];
            items[11].delay = str[52];
            items[12].delay = str[55];
            items[13].delay = str[53];
            if (str.length >= 68) {
                items[14].delay = str[66];
                items[15].delay = str[67];
            } else {
                items[14].delay = "NA";
                items[15].delay = "NA";
            }
        }
        return new NetInfoStatData(items);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoStat
    public ITelephonyApi.INetInfoStat.DataItem[] getAttachTime(int simIdx) throws Exception {
        String[] desc;
        int itemCount;
        String atRsp = IATUtils.sendATCmd("AT+SPENGMD=0,7,7", simIdx);
        if (!atRsp.contains("OK")) {
            throw new Exception("AT reponse error");
        }
        Log.d(TAG, "parseNetStatistic atRSP =" + atRsp);
        String[] str = atRsp.split("-");
        int array_len = str.length;
        for (int i = 0; i < str.length; i++) {
            str[i] = str[i].replaceAll("\r|\n", "");
            Log.d(TAG, "parseNetStatistic str =" + str[i]);
        }
        int i2 = array_len - 1;
        str[i2] = str[array_len - 1].substring(0, str[array_len - 1].length() - 2);
        if (TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WG || TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WW) {
            desc = attachtime_desc;
            itemCount = attachtime_desc.length;
        } else {
            desc = attachtime_lte_desc;
            itemCount = attachtime_lte_desc.length;
        }
        ITelephonyApi.INetInfoStat.DataItem[] items = new ITelephonyApi.INetInfoStat.DataItem[itemCount];
        for (int i3 = 0; i3 < itemCount; i3++) {
            items[i3] = new ITelephonyApi.INetInfoStat.DataItem();
            items[i3].desc = desc[i3];
            items[i3].data = str[i3];
            Log.d(TAG, i3 + " => " + items[i3].desc + ": " + items[i3].data);
        }
        return items;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoStat
    public ITelephonyApi.INetInfoStat.DataItem[] getDropCount(int simIdx) throws Exception {
        String[] desc;
        int itemCount;
        String atRsp = IATUtils.sendATCmd("AT+SPENGMD=0,7,0", simIdx);
        if (!atRsp.contains("OK")) {
            throw new Exception("AT reponse error");
        }
        String[] str = atRsp.split("-");
        int array_len = str.length;
        for (int i = 0; i < str.length; i++) {
            str[i] = str[i].replaceAll("\r|\n", "");
        }
        int i2 = array_len - 1;
        str[i2] = str[array_len - 1].substring(0, str[array_len - 1].length() - 2);
        if (TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WG || TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WW) {
            desc = dropcount_desc;
            itemCount = dropcount_desc.length;
        } else {
            desc = dropcount_lte_desc;
            itemCount = dropcount_lte_desc.length;
        }
        ITelephonyApi.INetInfoStat.DataItem[] items = new ITelephonyApi.INetInfoStat.DataItem[itemCount];
        for (int i3 = 0; i3 < itemCount; i3++) {
            items[i3] = new ITelephonyApi.INetInfoStat.DataItem();
            items[i3].desc = desc[i3];
            items[i3].data = str[i3];
            Log.d(TAG, i3 + " => " + items[i3].desc + ": " + items[i3].data);
        }
        return items;
    }

    private static String convertPercent(String total, String pass) {
        NumberFormat format = NumberFormat.getPercentInstance();
        try {
            if (Integer.parseInt(total) == 0) {
                format.setMinimumFractionDigits(2);
                return format.format(1.0d);
            }
            format.setMinimumFractionDigits(2);
            return format.format(((double) Integer.parseInt(pass)) / ((double) Integer.parseInt(total)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "NA";
        }
    }
}
