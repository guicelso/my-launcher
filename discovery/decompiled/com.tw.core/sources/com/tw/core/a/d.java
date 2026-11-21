package com.tw.core.a;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private String bQ;
    private String bR;
    private boolean bS;
    private boolean bT;
    private int bU;
    private int bV;

    public d(String str, int i, boolean z) {
        this.bR = str;
        this.bU = i;
        this.bT = z;
    }

    public JSONObject eD() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("iccid", this.bR);
            jSONObject.put("card_slot", this.bU);
            jSONObject.put("is_use", this.bT ? 0 : 1);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String eE() {
        return this.bR;
    }

    public boolean eF() {
        return this.bS;
    }

    public int eG() {
        return this.bU;
    }

    public boolean eH() {
        return this.bT;
    }

    public int eI() {
        return this.bV;
    }

    public void eJ(boolean z) {
        this.bS = z;
    }

    public void eK(int i) {
        this.bV = i;
    }

    public String toString() {
        return "SimInfo{iccId='" + this.bR + "', simSlotIndex='" + this.bU + "', isSimDefaultData='" + this.bT + "', cardType='" + this.bQ + "', isOpen='" + this.bS + "', subscriptionId='" + this.bV + "'}";
    }
}
