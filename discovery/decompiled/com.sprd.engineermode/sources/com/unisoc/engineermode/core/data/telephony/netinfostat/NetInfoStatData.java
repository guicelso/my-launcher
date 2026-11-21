package com.unisoc.engineermode.core.data.telephony.netinfostat;

/* JADX INFO: loaded from: classes4.dex */
public class NetInfoStatData {
    private Item[] data;

    public static class Item {
        public String desc;
        public String success = "NA";
        public String fail = "NA";
        public String ratio = "NA";
        public String delay = "NA";

        public String getDesc() {
            return this.desc;
        }

        public String getSuccess() {
            return this.success;
        }

        public String getFail() {
            return this.fail;
        }

        public String getRatio() {
            return this.ratio;
        }

        public String getDelay() {
            return this.delay;
        }
    }

    public NetInfoStatData(Item[] data) {
        this.data = data;
    }

    public Item[] getData() {
        return this.data;
    }

    public void setData(Item[] data) {
        this.data = data;
    }
}
