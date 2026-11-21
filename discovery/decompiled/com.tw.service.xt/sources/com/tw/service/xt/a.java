package com.tw.service.xt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.tw.service.xt.utils.DeviceUtils;

/* JADX INFO: loaded from: classes.dex */
class a implements Handler.Callback {
    final /* synthetic */ CommandService j;

    a(CommandService commandService) {
        this.j = commandService;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        byte[] bArr;
        Bundle bundle;
        int i = message.what;
        if (i != 513) {
            if (i == 516) {
                this.j.nightMode = message.arg1 == 1;
                int i2 = message.arg2;
                int i3 = i2 & 255;
                int i4 = (i2 >> 8) & 255;
                this.j.currentLightValue = 0;
                if (this.j.blmode == 0) {
                    CommandService commandService = this.j;
                    if (!commandService.nightMode) {
                        i4 = i3;
                    }
                    commandService.currentLightValue = i4;
                } else if (this.j.blmode == 1) {
                    this.j.currentLightValue = i3;
                } else if (this.j.blmode == 2) {
                    this.j.currentLightValue = i4;
                }
                if (DeviceUtils.isLightFromMCU()) {
                    this.j.sendLightValue();
                }
                if (this.j.easyConnBroadcastReceiver != null) {
                    this.j.easyConnBroadcastReceiver.sendNaviType(this.j.nightMode);
                }
            } else if (i != 769) {
                switch (i) {
                    case 2560:
                    case 2561:
                    case 2562:
                    case 2563:
                        bArr = (byte[]) message.obj;
                        bundle = new Bundle();
                        bundle.putInt("type", message.what);
                        if (bArr != null) {
                        }
                        Message messageObtain = Message.obtain();
                        messageObtain.what = 0;
                        messageObtain.obj = bundle;
                        this.j.mHandler.sendMessageDelayed(messageObtain, 50L);
                        break;
                    default:
                        if (this.j.sendTWCommandCallBack(message)) {
                            return true;
                        }
                        break;
                }
            } else {
                int i5 = this.j.mSource;
                int i6 = message.arg1;
                if (i5 != i6) {
                    this.j.mSource = i6;
                    CommandService commandService2 = this.j;
                    commandService2.sendSourceChange(commandService2.mSource);
                }
            }
            this.j.requestData = false;
            return false;
        }
        bArr = new byte[]{(byte) message.arg1, (byte) message.arg2};
        bundle = new Bundle();
        bundle.putInt("type", message.what);
        bundle.putByteArray("data", bArr);
        Message messageObtain2 = Message.obtain();
        messageObtain2.what = 0;
        messageObtain2.obj = bundle;
        this.j.mHandler.sendMessageDelayed(messageObtain2, 50L);
        this.j.requestData = false;
        return false;
    }
}
