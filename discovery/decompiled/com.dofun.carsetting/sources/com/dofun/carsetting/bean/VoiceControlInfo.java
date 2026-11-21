package com.dofun.carsetting.bean;

/* JADX INFO: loaded from: classes.dex */
public class VoiceControlInfo {
    private static final String TAG = "VoiceControlInfo";
    public boolean awakeable;
    public String mapSoftware;
    public String micIcon;
    public boolean micIconShow;
    public String musicSoftware;
    public String radioSoftware;
    public String sensitivity;
    public String speed;
    public boolean canQuickAwake = true;
    public boolean screenControl = false;

    public String toString() {
        return "{\"micIcon\": \"" + this.micIcon + "\", \"micIconShow\": " + this.micIconShow + ", \"awakeable\": " + this.awakeable + ", \"sensitivity\": \"" + this.sensitivity + "\", \"speed\": \"" + this.speed + "\", \"MapSoftware\": \"" + this.mapSoftware + "\", \"musicSoftware\": \"" + this.musicSoftware + "\", \"FMSoftware\": \"" + this.radioSoftware + "\", \"canQuickAwake\": \"" + this.canQuickAwake + "\", \"screenControl\": \"" + this.screenControl + "\"}";
    }
}
