package com.tw.core.track.model;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import com.tw.core.c.C0031k;
import com.tw.core.utils.C0065s;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GpsModel implements Serializable {
    private static DecimalFormat cG = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.CHINA));
    private static DecimalFormat cH = new DecimalFormat("#0.000000", new DecimalFormatSymbols(Locale.CHINA));
    private String cid;
    private String direction;
    private String height;
    private String latitude;
    private String longitude;
    private String radius;
    private String speed;
    private String time;
    private String uid;
    private String upid;

    public static GpsModel gG(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("latitude");
            String string2 = jSONObject.getString("longitude");
            String string3 = jSONObject.getString("loc_time");
            String string4 = jSONObject.getString("speed");
            String string5 = jSONObject.getString("direction");
            String string6 = jSONObject.getString("height");
            String string7 = jSONObject.getString("radius");
            String strOptString = jSONObject.has("uid") ? jSONObject.optString("uid") : null;
            GpsModel gpsModel = new GpsModel();
            gpsModel.gX(string);
            gpsModel.gY(string2);
            gpsModel.hb(string3);
            gpsModel.ha(string4);
            gpsModel.gV(string5);
            gpsModel.gW(string6);
            gpsModel.gZ(string7);
            gpsModel.gU(com.tw.core.b.f.gr());
            gpsModel.hc(strOptString);
            return gpsModel;
        } catch (JSONException e) {
            e.printStackTrace();
            C0065s.bK("convertGpsModel", "JSONException=" + str);
            return null;
        }
    }

    public static GpsModel gI(Context context, Location location) {
        if (location == null) {
            return null;
        }
        GpsModel gpsModel = new GpsModel();
        gpsModel.gX(String.valueOf(location.getLatitude()));
        gpsModel.gY(String.valueOf(location.getLongitude()));
        gpsModel.hb(String.valueOf(location.getTime() / 1000));
        gpsModel.ha(String.valueOf(((double) location.getSpeed()) * 3.6d));
        gpsModel.gV(String.valueOf(location.getBearing()));
        gpsModel.gW(String.valueOf(location.getAltitude()));
        gpsModel.gZ(String.valueOf(location.getAccuracy()));
        gpsModel.gU(com.tw.core.b.f.gr());
        gpsModel.hc(C0031k.kt().ku());
        return gpsModel;
    }

    public static GpsModel gT(Location location, long j) {
        if (location == null) {
            return null;
        }
        GpsModel gpsModel = new GpsModel();
        gpsModel.gX(cH.format(location.getLatitude()));
        gpsModel.gY(cH.format(location.getLongitude()));
        gpsModel.hb(String.valueOf(location.getTime() / 1000));
        gpsModel.ha(cG.format(location.getSpeed()));
        gpsModel.gV(String.valueOf((int) location.getBearing()));
        gpsModel.gW(cG.format(location.getAltitude()));
        gpsModel.gZ(cG.format(location.getAccuracy()));
        gpsModel.gU(com.tw.core.b.f.gr());
        gpsModel.hc(C0031k.kt().ku());
        gpsModel.hd(String.valueOf(j));
        return gpsModel;
    }

    public static GpsModel he(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String strOptString = jSONObject.optString("latitude");
            String strOptString2 = jSONObject.optString("longitude");
            String strOptString3 = jSONObject.optString("timestamp");
            String strOptString4 = jSONObject.optString("speed");
            String strOptString5 = jSONObject.optString("direction");
            String strOptString6 = jSONObject.optString("altitude");
            String strOptString7 = jSONObject.optString("precision");
            String strOptString8 = jSONObject.optString("upid");
            String strOptString9 = jSONObject.has("uid") ? jSONObject.optString("uid") : null;
            GpsModel gpsModel = new GpsModel();
            gpsModel.gX(strOptString);
            gpsModel.gY(strOptString2);
            gpsModel.hb(strOptString3);
            gpsModel.ha(strOptString4);
            gpsModel.gV(strOptString5);
            gpsModel.gW(strOptString6);
            gpsModel.gZ(strOptString7);
            gpsModel.gU(com.tw.core.b.f.gr());
            gpsModel.hc(strOptString9);
            gpsModel.hd(strOptString8);
            return gpsModel;
        } catch (JSONException e) {
            e.printStackTrace();
            C0065s.bK("convertGpsModel", "JSONException=" + str);
            return null;
        }
    }

    public String gF() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("latitude", gN());
            jSONObject.put("longitude", gO());
            jSONObject.put("loc_time", gH());
            jSONObject.put("speed", gQ());
            jSONObject.put("height", gM());
            jSONObject.put("direction", gL());
            jSONObject.put("radius", gP());
            jSONObject.put("uid", gR());
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String gH() {
        return this.time;
    }

    public String gJ() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("latitude", gN());
            jSONObject.put("longitude", gO());
            jSONObject.put("altitude", gM());
            jSONObject.put("timestamp", gH());
            jSONObject.put("speed", gQ());
            jSONObject.put("direction", gL());
            jSONObject.put("precision", gP());
            jSONObject.put("cid", gK());
            jSONObject.put("uid", gR());
            jSONObject.put("upid", gS());
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String gK() {
        return this.cid;
    }

    public String gL() {
        return this.direction;
    }

    public String gM() {
        return this.height;
    }

    public String gN() {
        return this.latitude;
    }

    public String gO() {
        return this.longitude;
    }

    public String gP() {
        return this.radius;
    }

    public String gQ() {
        return this.speed;
    }

    public String gR() {
        return this.uid;
    }

    public String gS() {
        return this.upid;
    }

    public void gU(String str) {
        this.cid = str;
    }

    public void gV(String str) {
        this.direction = str;
    }

    public void gW(String str) {
        this.height = str;
    }

    public void gX(String str) {
        this.latitude = str;
    }

    public void gY(String str) {
        this.longitude = str;
    }

    public void gZ(String str) {
        this.radius = str;
    }

    public void ha(String str) {
        this.speed = str;
    }

    public void hb(String str) {
        this.time = str;
    }

    public void hc(String str) {
        this.uid = str;
    }

    public void hd(String str) {
        this.upid = str;
    }

    public String toString() {
        return "GpsModel{cid='" + this.cid + "', longitude='" + this.longitude + "', latitude='" + this.latitude + "', direction='" + this.direction + "', height='" + this.height + "', radius='" + this.radius + "', speed='" + this.speed + "', time='" + this.time + "', uid='" + this.uid + "', upid='" + this.upid + "'}";
    }
}
