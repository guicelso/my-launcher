package a.b.d.e;

import android.util.Base64;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final String f85a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final String f86b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final String f87c;
    public final List<List<byte[]>> d;
    public final int e;
    public final String f;

    public a(String str, String str2, String str3, List<List<byte[]>> list) {
        if (str == null) {
            throw new NullPointerException();
        }
        this.f85a = str;
        if (str2 == null) {
            throw new NullPointerException();
        }
        this.f86b = str2;
        if (str3 == null) {
            throw new NullPointerException();
        }
        this.f87c = str3;
        if (list == null) {
            throw new NullPointerException();
        }
        this.d = list;
        this.e = 0;
        this.f = this.f85a + "-" + this.f86b + "-" + this.f87c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbA = c.a.a.a.a.a("FontRequest {mProviderAuthority: ");
        sbA.append(this.f85a);
        sbA.append(", mProviderPackage: ");
        sbA.append(this.f86b);
        sbA.append(", mQuery: ");
        sbA.append(this.f87c);
        sbA.append(", mCertificates:");
        sb.append(sbA.toString());
        for (int i = 0; i < this.d.size(); i++) {
            sb.append(" [");
            List<byte[]> list = this.d.get(i);
            for (int i2 = 0; i2 < list.size(); i2++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString(list.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
        }
        sb.append("}");
        sb.append("mCertificatesArray: " + this.e);
        return sb.toString();
    }
}
