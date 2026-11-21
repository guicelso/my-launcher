package com.dofun.carsetting.f;

import com.dofun.carsetting.app.MyApp;
import java.util.Map;

/* JADX INFO: compiled from: StringUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class s {
    public static String a(Map<String, String> map) {
        Map<String, String> mapA = i.a(map);
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : mapA.entrySet()) {
            stringBuffer.append(entry.getKey() + entry.getValue());
        }
        return stringBuffer.toString();
    }

    public static String a(int i) {
        try {
            return MyApp.a().getString(i);
        } catch (Exception unused) {
            return "";
        }
    }
}
