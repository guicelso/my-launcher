package com.unisoc.engineermode.core.sample;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class JavaImpl implements JavaInterface {
    private static String TAG = "SampleImpl2";

    private static class Holder {
        static final JavaImpl INSTANCE = new JavaImpl();

        private Holder() {
        }
    }

    @Override // com.unisoc.engineermode.core.sample.JavaInterface
    public String self() {
        Log.d(TAG, "this is for android10 sp7731e_1h10");
        return getClass().getSimpleName();
    }

    public static JavaImpl newInstance() {
        return Holder.INSTANCE;
    }
}
