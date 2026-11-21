package com.tw.core.track;

/* JADX INFO: loaded from: classes.dex */
public class h extends com.tw.core.track.model.a {
    private String dF;
    private com.tw.core.track.model.f dG;

    public h() {
    }

    public h(long j, String str, long j2, String str2, com.tw.core.track.model.f fVar) {
        super(j, str, j2);
        this.dF = str2;
        this.dG = fVar;
    }

    public String iZ() {
        return this.dF;
    }

    public com.tw.core.track.model.f ja() {
        return this.dG;
    }

    @Override // com.tw.core.track.model.a
    public String toString() {
        return "AddPointRequest [entityName=" + this.dF + ", trackPoint=" + this.dG + "]";
    }
}
