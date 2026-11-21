package android.support.v4.media;

import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
class a implements c {
    private IBinder mRemote;

    a(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }
}
