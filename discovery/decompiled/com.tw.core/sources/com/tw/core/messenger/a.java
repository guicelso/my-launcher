package com.tw.core.messenger;

import android.content.Context;
import android.os.Message;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static List m = new ArrayList();
    private static a n = new a();

    public static a get() {
        return n;
    }

    public void h(com.tw.core.messenger.server.a aVar) {
        m.add(aVar);
    }

    public void i(Context context, Message message) {
        for (com.tw.core.messenger.server.a aVar : m) {
            for (int i : aVar.getMessageId()) {
                if (i == message.what) {
                    aVar.a(message);
                }
            }
        }
    }

    public void j(com.tw.core.messenger.server.a aVar) {
        m.remove(aVar);
    }
}
