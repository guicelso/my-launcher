package com.tw.a.a.b;

import android.R;
import android.content.Context;
import android.text.BidiFormatter;
import android.text.format.Formatter;

/* JADX INFO: compiled from: DataUsageUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    public static CharSequence a(Context context, long j) {
        Formatter.BytesResult bytes = Formatter.formatBytes(context.getResources(), j, 8);
        return BidiFormatter.getInstance().unicodeWrap(context.getString(R.string.config_oem_enabled_satellite_s2cell_file, bytes.value, bytes.units));
    }
}
