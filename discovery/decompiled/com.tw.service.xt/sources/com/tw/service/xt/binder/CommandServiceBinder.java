package com.tw.service.xt.binder;

import android.os.Binder;
import com.tw.service.xt.BaseService;

/* JADX INFO: loaded from: classes.dex */
public class CommandServiceBinder {
    public static Binder getSpeechCommandServiceBinder(BaseService baseService) {
        return new a(baseService);
    }
}
