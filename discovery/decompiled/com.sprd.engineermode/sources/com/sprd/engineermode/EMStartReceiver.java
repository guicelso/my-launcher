package com.sprd.engineermode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import com.sprd.engineermode.debuglog.PhoneInfoActivity;
import com.sprd.engineermode.debuglog.SensorsIDActivity;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import io.reactivex.annotations.SchedulerSupport;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class EMStartReceiver extends BroadcastReceiver {
    private static final String SECURITY_CODE_ENGINEERMODE = "33284";
    private static final String TAG = "EMStartReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws IllegalAccessException, InvocationTargetException {
        Uri uri = intent.getData();
        if (uri == null) {
            Log.d(TAG, "uri is null");
            return;
        }
        String host = uri.getHost();
        Intent i = new Intent("android.intent.action.MAIN");
        i.setFlags(268435456);
        if ("83781".equals(host)) {
            i.setClass(context, EngineerModeActivity.class);
            context.startActivity(i);
            return;
        }
        if ("83782".equals(host)) {
            i.setClass(context, EngineerModeActivity_2.class);
            context.startActivity(i);
            return;
        }
        if ("1688".equals(host)) {
            i.setClass(context, SensorsIDActivity.class);
            context.startActivity(i);
            return;
        }
        if (Const.isBoardISharkL210c10() && SECURITY_CODE_ENGINEERMODE.equals(host)) {
            i.setClass(context, EngineerModeActivity.class);
            context.startActivity(i);
            return;
        }
        if ("0000".equals(host)) {
            String productName = SystemPropertiesProxy.get("ro.product.name", EnvironmentCompat.MEDIA_UNKNOWN);
            Log.d(TAG, "productName=:" + productName);
            if (!productName.contains("ctcc")) {
                Log.d(TAG, "This product is not support!");
                return;
            } else {
                i.setClass(context, PhoneInfoActivity.class);
                context.startActivity(i);
                return;
            }
        }
        if (SystemPropertiesProxy.get("ro.product.board.customer", SchedulerSupport.NONE).equalsIgnoreCase("cgmobile")) {
            if ("837868".equals(host)) {
                i.setClass(context, cgversioninfo.class);
                context.startActivity(i);
            } else if ("837866".equals(host)) {
                i.setClass(context, yulongversioninfo.class);
                context.startActivity(i);
            }
        }
    }
}
