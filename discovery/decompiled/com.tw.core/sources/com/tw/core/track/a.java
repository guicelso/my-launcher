package com.tw.core.track;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class a extends com.tw.core.track.model.a {
    private Map cZ;

    public a() {
    }

    public a(long j, String str, long j2, Map map) {
        super(j, str, j2);
        this.cZ = map;
    }

    public Map iq() {
        return this.cZ;
    }

    @Override // com.tw.core.track.model.a
    public String toString() {
        return "AddPointsRequest [trackPoints=" + this.cZ + "]";
    }
}
