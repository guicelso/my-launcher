package com.tw.core.messenger;

import android.os.Bundle;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static Message k(String str, int i) {
        Message messageObtain = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("data", str);
        messageObtain.what = i;
        messageObtain.obj = bundle;
        return messageObtain;
    }
}
