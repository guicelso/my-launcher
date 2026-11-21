package com.unisoc.engineermode.core.sample;

import android.util.Log;
import com.unisoc.engineermode.core.factory.BaseInterface;
import com.unisoc.engineermode.core.factory.ImplementationFactory;

/* JADX INFO: loaded from: classes4.dex */
public class JavaTest {
    private static String TAG = "JAVATEST";

    public static JavaInterface javaApi() {
        return (JavaInterface) ImplementationFactory.create((Class<? extends BaseInterface>) JavaInterface.class);
    }

    public static void test() {
        Log.d(TAG, javaApi().self());
    }
}
