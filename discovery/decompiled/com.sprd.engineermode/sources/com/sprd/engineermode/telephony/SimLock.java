package com.sprd.engineermode.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SimLock {
    static final int LOCK_INFO_MAX = 2;
    private static final int SIMLOCK_IMSI = 1;
    private static final int SIMLOCK_STATUS = 0;
    private static final String[] SIMLOCK_TYPE_NAME = {"00", "PS", "PN", "PU", "PP", "PC"};
    static final String TAG = "SimLock";
    static final int TYPE_CORPORATE = 5;
    static final int TYPE_NETWORK = 2;
    static final int TYPE_NETWORK_SUBSET = 3;
    static final int TYPE_NULL = 0;
    static final int TYPE_SERVICE = 4;
    static final int TYPE_SIM = 1;
    private boolean mIsInit;
    private boolean mIsLocked;
    private List<String> mLockInfos;
    private int mType;

    private String parseRsp(String response, int type) {
        if (type == 0) {
            if (response.contains("OK")) {
                String[] str = response.split("\n");
                String[] str1 = str[0].split(":");
                return str1[1];
            }
            return IATUtils.AT_FAIL;
        }
        return null;
    }

    public SimLock() {
        this.mIsInit = false;
        this.mType = 0;
        this.mIsLocked = false;
        this.mLockInfos = new ArrayList();
    }

    public SimLock(int type) {
        this.mIsInit = false;
        this.mType = 0;
        this.mIsLocked = false;
        this.mLockInfos = new ArrayList();
        this.mType = type;
        String rsp = IATUtils.sendATCmd(engconstents.ENG_AT_CLCK + SIMLOCK_TYPE_NAME[this.mType] + ",2", "atchannel0");
        Log.d(TAG, rsp);
        String parseRe = parseRsp(rsp, 0);
        Log.d(TAG, parseRe);
        if (parseRe.contains("1")) {
            this.mIsLocked = true;
        } else if (!parseRe.contains("0")) {
            Log.d(TAG, "Get lock status ERROR");
        } else {
            this.mIsLocked = false;
        }
        this.mIsInit = true;
    }

    public boolean lock(String psw) {
        String rsp = IATUtils.sendATCmd(engconstents.ENG_AT_CLCK + SIMLOCK_TYPE_NAME[this.mType] + ",1," + psw, "atchannel0");
        if (rsp.contains("OK")) {
            this.mIsLocked = true;
            return true;
        }
        return false;
    }

    public boolean unlock(String psw) {
        String rsp = IATUtils.sendATCmd(engconstents.ENG_AT_CLCK + SIMLOCK_TYPE_NAME[this.mType] + ",1," + psw, "atchannel0");
        if (!rsp.contains("OK")) {
            return false;
        }
        this.mIsLocked = false;
        return true;
    }

    public boolean add(String imsi, String gid1, String gid2, String psw) {
        if (imsi == null) {
            String imsiRsp = IATUtils.sendATCmd(engconstents.ENG_AT_GETIMSI, "atchannel0");
            if (!imsiRsp.contains("OK")) {
                return false;
            }
            imsi = parseRsp(imsiRsp, 1);
        }
        String rsp = IATUtils.sendATCmd(engconstents.ENG_AT_ADDSIM + SIMLOCK_TYPE_NAME[this.mType] + "," + psw + ",2," + imsi, "atchannel0");
        return rsp.contains("OK");
    }

    public boolean remove() {
        return true;
    }

    public boolean unlockPermanently() {
        return false;
    }

    public int getLockRemainRetiresLeft() {
        return 5;
    }

    public int getUnlockRemainRetiresLeft() {
        return 5;
    }

    public boolean isInited() {
        return this.mIsInit;
    }

    public boolean isLocked() {
        return this.mIsLocked;
    }

    public boolean isSimLockInfoFull() {
        return this.mLockInfos.size() == 2;
    }

    public boolean isSimLockInfoEmpty() {
        return this.mLockInfos.size() == 0;
    }
}
