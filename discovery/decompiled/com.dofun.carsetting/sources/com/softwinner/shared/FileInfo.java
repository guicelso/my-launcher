package com.softwinner.shared;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class FileInfo {

    public static class FileUnit implements Serializable {
        private static final long serialVersionUID = 1;
        private String path;
        private int type;

        public FileUnit(String str, int i) {
            this.path = "";
            this.type = 0;
            this.path = str;
            this.type = i;
        }

        public void setPath(String str) {
            this.path = str;
        }

        public void setType(int i) {
            this.type = i;
        }

        public int getType() {
            return this.type;
        }

        public String getPath() {
            return this.path;
        }
    }
}
