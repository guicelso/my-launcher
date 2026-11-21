package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
class VideoTypeImpl implements ITelephonyApi.IVideoType {
    private static final String VALUEKEY_VIDEO_TYPE = "debug.videophone.videotype";

    VideoTypeImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVideoType
    public int get() throws Exception {
        return 1;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVideoType
    public void set(int type) throws Exception {
        SystemPropertiesProxy.set(VALUEKEY_VIDEO_TYPE, String.valueOf(type));
    }
}
