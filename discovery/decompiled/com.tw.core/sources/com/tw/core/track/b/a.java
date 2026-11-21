package com.tw.core.track.b;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static String ih(Exception exc) {
        StringWriter stringWriter = new StringWriter();
        exc.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static List ii(List list, int i) {
        if (list == null || list.size() == 0 || i < 1) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (list.size() <= i) {
            arrayList.add(list);
            return arrayList;
        }
        int size = list.size() / i;
        if (list.size() % i != 0) {
            size++;
        }
        int i2 = 0;
        while (i2 < size) {
            arrayList.add(list.subList(i2 * i, i2 < size + (-1) ? (i2 + 1) * i : list.size()));
            i2++;
        }
        return arrayList;
    }

    public static boolean ij(String str) {
        return str == null || "".equals(str);
    }
}
