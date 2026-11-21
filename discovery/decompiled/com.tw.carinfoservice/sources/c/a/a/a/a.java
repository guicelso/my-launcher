package c.a.a.a;

import android.content.Context;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: compiled from: outline */
/* JADX INFO: loaded from: classes.dex */
public class a {
    public static String a(XmlPullParser xmlPullParser, StringBuilder sb, String str) {
        sb.append(xmlPullParser.getPositionDescription());
        sb.append(str);
        return sb.toString();
    }

    public static StringBuilder b(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public static void c(Context context, int i, StringBuilder sb, String str, int i2) {
        sb.append(context.getString(i));
        sb.append(str);
        sb.append(i2);
    }

    public static void d(StringBuilder sb, String str, TextView textView) {
        sb.append(str);
        textView.setText(sb.toString());
    }
}
