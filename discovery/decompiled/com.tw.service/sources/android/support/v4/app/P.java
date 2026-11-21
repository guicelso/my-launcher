package android.support.v4.app;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class P {
    private final List mFragments;
    private final List yd;
    private final List zd;

    P(List list, List list2, List list3) {
        this.mFragments = list;
        this.yd = list2;
        this.zd = list3;
    }

    List C() {
        return this.zd;
    }

    List getChildNonConfigs() {
        return this.yd;
    }

    List getFragments() {
        return this.mFragments;
    }
}
