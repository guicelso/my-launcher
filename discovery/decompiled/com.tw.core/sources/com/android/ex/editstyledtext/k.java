package com.android.ex.editstyledtext;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.util.Log;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.logging.Logger;

/* JADX INFO: loaded from: classes.dex */
public class k extends CharacterStyle {
    private int lj;
    private int lk;

    private int qp(int i, int i2) {
        int i3;
        int i4;
        int iAlpha = Color.alpha(i2);
        int iRed = Color.red(i2);
        int iGreen = Color.green(i2);
        int iBlue = Color.blue(i2);
        if (iAlpha == 0) {
            iAlpha = 128;
        }
        switch (i) {
            case MqttConnectOptions.MQTT_VERSION_DEFAULT /* 0 */:
                i4 = iRed > 128 ? iRed / 2 : (255 - iRed) / 2;
                i3 = iGreen;
                break;
            case Logger.SEVERE /* 1 */:
                i3 = iGreen > 128 ? iGreen / 2 : (255 - iGreen) / 2;
                i4 = iRed;
                break;
            case Logger.WARNING /* 2 */:
                return 16777215;
            default:
                Log.e("EditStyledText", "--- getMarqueeColor: got illigal marquee ID.");
                return 16777215;
        }
        return Color.argb(iAlpha, i4, i3, iBlue);
    }

    public void qq(int i) {
        this.lj = qp(this.lk, i);
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.bgColor = this.lj;
    }
}
