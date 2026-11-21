package com.tw.core.a;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class e extends g {
    private String bW;
    private List bX;
    private String bY;
    private List bZ;
    private List ca;
    private boolean cb;
    private List cc;
    private String cd;
    private List ce;
    private String cf;
    private List cg;
    private String ch;
    private int ci;

    public e(String str, int i, String str2, String str3) {
        super(str, i, str2, str3);
    }

    public String eL() {
        return this.bW;
    }

    public List eM() {
        return this.bX;
    }

    public String eN() {
        return this.bY;
    }

    public List eO() {
        return this.bZ;
    }

    public List eP() {
        return this.ca;
    }

    public List eQ() {
        return this.cc;
    }

    public String eR() {
        return this.cd;
    }

    public String eS() {
        return this.cf;
    }

    public String eT() {
        return this.ch;
    }

    public boolean eU() {
        return this.cb;
    }

    public e eV(String str) {
        this.bW = str;
        return this;
    }

    public e eW(List list) {
        this.bX = list;
        return this;
    }

    public e eX(String str) {
        this.bY = str;
        return this;
    }

    public e eY(List list) {
        this.bZ = list;
        return this;
    }

    public e eZ(List list) {
        this.ca = list;
        return this;
    }

    public e fa(boolean z) {
        this.cb = z;
        return this;
    }

    public e fb(List list) {
        this.cc = list;
        return this;
    }

    public e fc(String str) {
        this.cd = str;
        return this;
    }

    public e fd(List list) {
        this.ce = list;
        return this;
    }

    public e fe(String str) {
        this.cf = str;
        return this;
    }

    public e ff(List list) {
        this.cg = list;
        return this;
    }

    public e fg(String str) {
        this.ch = str;
        return this;
    }

    public e fh(int i) {
        this.ci = i;
        return this;
    }

    public int getType() {
        return this.ci;
    }

    @Override // com.tw.core.a.g
    public String toString() {
        return "VersionInfo{" + super.toString() + ", address='" + this.bW + "', title='" + this.ch + "', describe='" + this.bY + "', languageList=" + this.cc + ", md5='" + this.cd + "', type=" + this.ci + ", installNotExists=" + this.cb + ", channelList=" + this.bX + ", systemBlackList=" + this.ce + ", systemWhiteList=" + this.cg + ", fotaBlackList=" + this.bZ + ", fotaWhiteList=" + this.ca + ", systemRegular=" + this.cf + '}';
    }
}
