package com.dofun.carsetting.bean;

/* JADX INFO: loaded from: classes.dex */
public class VoiceControlBean {
    private String FMSoftware;
    private String MapSoftware;
    private boolean awakeable;
    private boolean canQuickAwake;
    private String micIcon;
    private boolean micIconShow;
    private String musicSoftware;
    private boolean screenControl;
    private String sensitivity;
    private String speed;

    public String getMicIcon() {
        return this.micIcon;
    }

    public void setMicIcon(String str) {
        this.micIcon = str;
    }

    public boolean isMicIconShow() {
        return this.micIconShow;
    }

    public void setMicIconShow(boolean z) {
        this.micIconShow = z;
    }

    public boolean isAwakeable() {
        return this.awakeable;
    }

    public void setAwakeable(boolean z) {
        this.awakeable = z;
    }

    public String getSensitivity() {
        return this.sensitivity;
    }

    public void setSensitivity(String str) {
        this.sensitivity = str;
    }

    public String getSpeed() {
        return this.speed;
    }

    public void setSpeed(String str) {
        this.speed = str;
    }

    public String getMapSoftware() {
        return this.MapSoftware;
    }

    public void setMapSoftware(String str) {
        this.MapSoftware = str;
    }

    public String getMusicSoftware() {
        return this.musicSoftware;
    }

    public void setMusicSoftware(String str) {
        this.musicSoftware = str;
    }

    public String getFMSoftware() {
        return this.FMSoftware;
    }

    public void setFMSoftware(String str) {
        this.FMSoftware = str;
    }

    public boolean getScreenControl() {
        return this.screenControl;
    }

    public void setgetScreenControl(boolean z) {
        this.screenControl = z;
    }

    public String toString() {
        return "{\"micIcon\": \"" + this.micIcon + "\", \"micIconShow\": " + this.micIconShow + ", \"awakeable\": " + this.awakeable + ", \"sensitivity\": \"" + this.sensitivity + "\", \"speed\": \"" + this.speed + "\", \"MapSoftware\": \"" + this.MapSoftware + "\", \"musicSoftware\": \"" + this.musicSoftware + "\", \"FMSoftware\": \"" + this.FMSoftware + "\", \"canQuickAwake\": \"" + this.canQuickAwake + "\", \"screenControl\": \"" + this.screenControl + "\"}";
    }

    public boolean isCanQuickAwake() {
        return this.canQuickAwake;
    }

    public void setCanQuickAwake(boolean z) {
        this.canQuickAwake = z;
    }
}
