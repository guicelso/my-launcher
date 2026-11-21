package a.b.d.i;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public abstract class f extends c {
    public int j;
    public int k;
    public LayoutInflater l;

    @Deprecated
    public f(Context context, int i, Cursor cursor, boolean z) {
        super(context, null, z);
        this.k = i;
        this.j = i;
        this.l = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    @Override // a.b.d.i.c
    public View d(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.l.inflate(this.j, viewGroup, false);
    }
}
