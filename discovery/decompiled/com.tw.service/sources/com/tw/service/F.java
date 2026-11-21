package com.tw.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class F extends BroadcastReceiver {
    final /* synthetic */ TWService n;

    F(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            if ("android.intent.action.MEDIA_MOUNTED".equals(intent.getAction())) {
                String path = intent.getData().getPath();
                Log.d("TWService", "path:" + path);
                if (path.startsWith("/storage/usb")) {
                    this.n.yb.removeMessages(path.charAt(16) - '0');
                }
            }
        } catch (Exception e) {
            Log.d("TWService", Log.getStackTraceString(e));
        }
        Message messageObtainMessage = this.n.yb.obtainMessage();
        messageObtainMessage.what = -1;
        messageObtainMessage.obj = intent;
        this.n.yb.sendMessage(messageObtainMessage);
    }
}
