package android.support.v4.media;

import android.content.ComponentName;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
final class q implements o {
    private final String mPackageName;
    private final String mServiceName;
    private final String mSessionId;
    private final int mType;
    private final int mUid;
    private final c me;

    q(int i, int i2, String str, String str2, String str3, c cVar) {
        this.mUid = i;
        this.mType = i2;
        this.mPackageName = str;
        this.mServiceName = str2;
        if (this.mType != 0) {
            new ComponentName(str, str2);
        }
        this.mSessionId = str3;
        this.me = cVar;
    }

    public boolean equals(Object obj) {
        boolean zEquals;
        if (!(obj instanceof q)) {
            return false;
        }
        q qVar = (q) obj;
        if (this.mUid != qVar.mUid || !TextUtils.equals(this.mPackageName, qVar.mPackageName) || !TextUtils.equals(this.mServiceName, qVar.mServiceName) || !TextUtils.equals(this.mSessionId, qVar.mSessionId) || this.mType != qVar.mType) {
            return false;
        }
        c cVar = this.me;
        c cVar2 = qVar.me;
        if (cVar == null || cVar2 == null) {
            zEquals = cVar == cVar2;
        } else {
            zEquals = cVar.asBinder().equals(cVar2.asBinder());
        }
        return zEquals;
    }

    public int hashCode() {
        int i = this.mType;
        int i2 = this.mUid;
        int iHashCode = this.mPackageName.hashCode();
        int iHashCode2 = this.mSessionId.hashCode();
        String str = this.mServiceName;
        return ((((((((str != null ? str.hashCode() : 0) * 31) + iHashCode2) * 31) + iHashCode) * 31) + i2) * 31) + i;
    }

    public String toString() {
        StringBuilder sbE = b.a.a.a.a.e("SessionToken {pkg=");
        sbE.append(this.mPackageName);
        sbE.append(" id=");
        sbE.append(this.mSessionId);
        sbE.append(" type=");
        sbE.append(this.mType);
        sbE.append(" service=");
        sbE.append(this.mServiceName);
        sbE.append(" IMediaSession2=");
        sbE.append(this.me);
        sbE.append("}");
        return sbE.toString();
    }
}
