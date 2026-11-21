package android.support.v7.app;

import a.b.e.b.b;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public class AlertController$RecycleListView extends ListView {
    public AlertController$RecycleListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, b.s);
        typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, -1);
        typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, -1);
    }
}
