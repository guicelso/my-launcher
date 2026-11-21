package a.b.d.e;

import android.util.Base64;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final String f82a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final String f83b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final String f84c;
    public final List<List<byte[]>> d;
    public final String e;

    public a(String str, String str2, String str3, List<List<byte[]>> list) {
        this.f82a = str;
        this.f83b = str2;
        this.f84c = str3;
        Objects.requireNonNull(list);
        this.d = list;
        this.e = str + "-" + str2 + "-" + str3;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbB = c.a.a.a.a.b("FontRequest {mProviderAuthority: ");
        sbB.append(this.f82a);
        sbB.append(", mProviderPackage: ");
        sbB.append(this.f83b);
        sbB.append(", mQuery: ");
        sbB.append(this.f84c);
        sbB.append(", mCertificates:");
        sb.append(sbB.toString());
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
        sb.append("mCertificatesArray: 0");
        return sb.toString();
    }
}
