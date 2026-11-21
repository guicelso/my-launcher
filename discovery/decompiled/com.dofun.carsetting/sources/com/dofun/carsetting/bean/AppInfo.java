package com.dofun.carsetting.bean;

import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public class AppInfo {
    public Drawable appIcon;
    public String appName;
    public ComponentName componentName;
    private String currentName;
    private boolean isChoose;
    public ResolveInfo mInfo;
    private String packageName;
    public boolean installed = false;
    public boolean selected = false;

    public AppInfo() {
    }

    public AppInfo(ResolveInfo resolveInfo, String str, Drawable drawable) {
        this.mInfo = resolveInfo;
        this.appName = str;
        this.appIcon = drawable;
    }

    public AppInfo(ComponentName componentName, String str, Drawable drawable) {
        this.componentName = componentName;
        this.appName = str;
        this.appIcon = drawable;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public Drawable getAppIcon() {
        return this.appIcon;
    }

    public void setAppIcon(Drawable drawable) {
        this.appIcon = drawable;
    }

    public boolean isChoose() {
        return this.isChoose;
    }

    public void setChoose(boolean z) {
        this.isChoose = z;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public String getCurrentName() {
        return this.currentName;
    }

    public void setCurrentName(String str) {
        this.currentName = str;
    }
}
