package a.a.a.a.a;

import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: compiled from: IConnmgr.java */
/* JADX INFO: loaded from: classes.dex */
public interface a extends android.a.a.a.a {
    String b(String str);

    ArrayList<String> b();

    static a a(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface("vendor.sprd.hardware.connmgr@1.0::IConnmgr");
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof a)) {
            return (a) iHwInterfaceQueryLocalInterface;
        }
        C0000a c0000a = new C0000a(iHwBinder);
        try {
            Iterator<String> it = c0000a.b().iterator();
            while (it.hasNext()) {
                if (it.next().equals("vendor.sprd.hardware.connmgr@1.0::IConnmgr")) {
                    return c0000a;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    @Deprecated
    static a a(String str) {
        return a(HwBinder.getService("vendor.sprd.hardware.connmgr@1.0::IConnmgr", str));
    }

    @Deprecated
    static a a() {
        return a("default");
    }

    /* JADX INFO: renamed from: a.a.a.a.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: IConnmgr.java */
    public static final class C0000a implements a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private IHwBinder f0a;

        public C0000a(IHwBinder iHwBinder) {
            this.f0a = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        public IHwBinder asBinder() {
            return this.f0a;
        }

        public String toString() {
            try {
                return c() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of vendor.sprd.hardware.connmgr@1.0::IConnmgr]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // a.a.a.a.a.a
        public String b(String str) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.sprd.hardware.connmgr@1.0::IConnmgr");
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.f0a.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // a.a.a.a.a.a
        public ArrayList<String> b() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.f0a.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        public String c() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.f0a.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }
    }
}
