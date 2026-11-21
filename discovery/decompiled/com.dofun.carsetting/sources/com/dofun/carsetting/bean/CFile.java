package com.dofun.carsetting.bean;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class CFile {
    public CharSequence cs;
    public File file;
    public File[] files;

    public CFile(CharSequence charSequence, File file) {
        this.cs = charSequence;
        this.file = file;
    }

    public CFile(CharSequence charSequence, File[] fileArr) {
        this.cs = charSequence;
        this.files = fileArr;
    }
}
