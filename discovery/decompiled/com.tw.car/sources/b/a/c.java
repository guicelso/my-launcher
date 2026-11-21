package b.a;

import android.os.Parcel;
import android.util.SparseIntArray;

/* JADX INFO: loaded from: classes.dex */
public class c extends b {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Parcel f442b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f443c;
    public final int d;
    public final String e;
    public int g;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final SparseIntArray f441a = new SparseIntArray();
    public int f = -1;

    public c(Parcel parcel, int i, int i2, String str) {
        this.g = 0;
        this.f442b = parcel;
        this.f443c = i;
        this.d = i2;
        this.g = this.f443c;
        this.e = str;
    }

    @Override // b.a.b
    public void a() {
        int i = this.f;
        if (i >= 0) {
            int i2 = this.f441a.get(i);
            int iDataPosition = this.f442b.dataPosition();
            this.f442b.setDataPosition(i2);
            this.f442b.writeInt(iDataPosition - i2);
            this.f442b.setDataPosition(iDataPosition);
        }
    }

    @Override // b.a.b
    public boolean a(int i) {
        int iDataPosition;
        while (true) {
            int i2 = this.g;
            if (i2 >= this.d) {
                iDataPosition = -1;
                break;
            }
            this.f442b.setDataPosition(i2);
            int i3 = this.f442b.readInt();
            int i4 = this.f442b.readInt();
            this.g += i3;
            if (i4 == i) {
                iDataPosition = this.f442b.dataPosition();
                break;
            }
        }
        if (iDataPosition == -1) {
            return false;
        }
        this.f442b.setDataPosition(iDataPosition);
        return true;
    }

    @Override // b.a.b
    public b b() {
        Parcel parcel = this.f442b;
        int iDataPosition = parcel.dataPosition();
        int i = this.g;
        if (i == this.f443c) {
            i = this.d;
        }
        return new c(parcel, iDataPosition, i, this.e + "  ");
    }

    @Override // b.a.b
    public void b(int i) {
        a();
        this.f = i;
        this.f441a.put(i, this.f442b.dataPosition());
        this.f442b.writeInt(0);
        this.f442b.writeInt(i);
    }

    @Override // b.a.b
    public String c() {
        return this.f442b.readString();
    }
}
