package com.tw.core.track.model;

import android.location.Location;
import android.text.TextUtils;
import com.tw.core.utils.C0065s;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ThirdPartnerGpsModel implements Serializable {
    private int direction;
    private double height;
    private double latitude;
    private double longitude;
    private double radius;
    private double speed;
    private long time;
    private static DecimalFormat cR = new DecimalFormat("#0.000000", new DecimalFormatSymbols(Locale.CHINA));
    private static DecimalFormat cQ = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.CHINA));

    public static ThirdPartnerGpsModel hp(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            double dOptDouble = jSONObject.optDouble("latitude");
            double dOptDouble2 = jSONObject.optDouble("longitude");
            long jOptLong = jSONObject.optLong("loc_time");
            double dOptDouble3 = jSONObject.optDouble("speed");
            int iOptInt = jSONObject.optInt("direction");
            double dOptDouble4 = jSONObject.optDouble("height");
            double dOptDouble5 = jSONObject.optDouble("radius");
            ThirdPartnerGpsModel thirdPartnerGpsModel = new ThirdPartnerGpsModel();
            thirdPartnerGpsModel.hA(dOptDouble);
            thirdPartnerGpsModel.hB(dOptDouble2);
            thirdPartnerGpsModel.hE(jOptLong);
            thirdPartnerGpsModel.hD(dOptDouble3);
            thirdPartnerGpsModel.hy(iOptInt);
            thirdPartnerGpsModel.hz(dOptDouble4);
            thirdPartnerGpsModel.hC(dOptDouble5);
            return thirdPartnerGpsModel;
        } catch (JSONException e) {
            e.printStackTrace();
            C0065s.bK("convertGpsModel", "JSONException=" + str);
            return null;
        }
    }

    public static ThirdPartnerGpsModel hx(Location location) {
        if (location == null) {
            return null;
        }
        ThirdPartnerGpsModel thirdPartnerGpsModel = new ThirdPartnerGpsModel();
        thirdPartnerGpsModel.hA(Double.parseDouble(cR.format(location.getLatitude())));
        thirdPartnerGpsModel.hB(Double.parseDouble(cR.format(location.getLongitude())));
        thirdPartnerGpsModel.hE(location.getTime());
        thirdPartnerGpsModel.hD(Double.parseDouble(cQ.format(location.getSpeed())));
        thirdPartnerGpsModel.hy((int) location.getBearing());
        thirdPartnerGpsModel.hz(Double.parseDouble(cQ.format(location.getAltitude())));
        thirdPartnerGpsModel.hC(Double.parseDouble(cQ.format(location.getAccuracy())));
        return thirdPartnerGpsModel;
    }

    public void hA(double d) {
        this.latitude = d;
    }

    public void hB(double d) {
        this.longitude = d;
    }

    public void hC(double d) {
        this.radius = d;
    }

    public void hD(double d) {
        this.speed = d;
    }

    public void hE(long j) {
        this.time = j;
    }

    public JSONObject ho() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("latitude", ht());
            jSONObject.put("longitude", hu());
            jSONObject.put("loc_time", hq());
            jSONObject.put("speed", hw());
            jSONObject.put("height", hs());
            jSONObject.put("direction", hr());
            jSONObject.put("radius", hv());
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long hq() {
        return this.time;
    }

    public int hr() {
        return this.direction;
    }

    public double hs() {
        return this.height;
    }

    public double ht() {
        return this.latitude;
    }

    public double hu() {
        return this.longitude;
    }

    public double hv() {
        return this.radius;
    }

    public double hw() {
        return this.speed;
    }

    public void hy(int i) {
        this.direction = i;
    }

    public void hz(double d) {
        this.height = d;
    }

    public String toString() {
        return "ThirdPartnerGpsModel{longitude=" + this.longitude + ", latitude=" + this.latitude + ", direction=" + this.direction + ", height=" + this.height + ", radius=" + this.radius + ", speed=" + this.speed + ", time=" + this.time + '}';
    }
}
