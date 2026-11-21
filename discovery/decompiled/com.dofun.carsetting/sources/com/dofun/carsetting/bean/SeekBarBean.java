package com.dofun.carsetting.bean;

/* JADX INFO: loaded from: classes.dex */
public class SeekBarBean {
    int index;
    int mMax;
    int mMin;
    int mProgress;
    String mTitle;

    public SeekBarBean(String str, int i, int i2, int i3, int i4) {
        this.mMin = 0;
        this.index = -1;
        this.index = i4;
        this.mMax = i;
        this.mMin = i2;
        this.mTitle = str;
        this.mProgress = i3;
    }

    public int getmMax() {
        return this.mMax;
    }

    public int getmMin() {
        return this.mMin;
    }

    public int getmProgress() {
        return this.mProgress;
    }

    public String getmTitle() {
        return this.mTitle;
    }

    public void setmMax(int i) {
        this.mMax = i;
    }

    public void setmMin(int i) {
        this.mMin = i;
    }

    public void setmProgress(int i) {
        this.mProgress = i;
    }

    public void setmTitle(String str) {
        this.mTitle = str;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }
}
