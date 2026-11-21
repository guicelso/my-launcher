package android.support.v4.view;

import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
final class s implements Comparator {
    s() {
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        return ((x) obj).position - ((x) obj2).position;
    }
}
