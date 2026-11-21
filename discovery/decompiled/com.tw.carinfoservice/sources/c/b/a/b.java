package c.b.a;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.util.Log;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class b extends Binder implements IInterface {
    public b() {
        attachInterface(this, "com.tw.carinfoservice.CarServiceAidl");
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 1598968902) {
            parcel2.writeString("com.tw.carinfoservice.CarServiceAidl");
            return true;
        }
        int i3 = -1;
        int i4 = 0;
        switch (i) {
            case 1:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                d dVarA = a.b.e.b.a.a(parcel.readStrongBinder());
                Log.e("LS123", "registerCarServiceCallBack: ");
                c.b.a.g.b bVar = c.b.a.g.b.C0015b.f410a;
                Context context = ((c.b.a.g.a) this).f406b;
                Objects.requireNonNull(bVar);
                Log.e("LS123", "registerCarServiceCallBack: ");
                RemoteCallbackList<d> remoteCallbackList = bVar.f408b;
                if (remoteCallbackList != null) {
                    bVar.f407a = context;
                    remoteCallbackList.register(dVarA);
                }
                parcel2.writeNoException();
                return true;
            case 2:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                d dVarA2 = a.b.e.b.a.a(parcel.readStrongBinder());
                ((c.b.a.g.a) this).f405a.u0(false);
                c.b.a.g.b bVar2 = c.b.a.g.b.C0015b.f410a;
                Objects.requireNonNull(bVar2);
                Log.e("LS123", "unRegisterCarServiceCallBack: ");
                RemoteCallbackList<d> remoteCallbackList2 = bVar2.f408b;
                if (remoteCallbackList2 != null) {
                    remoteCallbackList2.unregister(dVarA2);
                }
                parcel2.writeNoException();
                return true;
            case 3:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                try {
                    ((c.b.a.g.a) this).f405a.O.sendEmptyMessageDelayed(65283, 0L);
                    break;
                } catch (Exception e) {
                    StringBuilder sbB = c.a.a.a.a.b("requestData: ");
                    sbB.append(e.toString());
                    Log.e("LS123", sbB.toString());
                    e.printStackTrace();
                }
                parcel2.writeNoException();
                return true;
            case 4:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float f = ((c.b.a.g.a) this).f405a.y0("SendElectricVoltage")[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f);
                return true;
            case 5:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i5 = (int) ((c.b.a.g.a) this).f405a.y0("SendHeadlight")[0];
                parcel2.writeNoException();
                parcel2.writeInt(i5);
                return true;
            case 6:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                parcel2.writeNoException();
                parcel2.writeInt(-1);
                return true;
            case 7:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int[] iArrB = c.b.a.l.a.b(((c.b.a.g.a) this).f405a.x0(0));
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrB);
                return true;
            case 8:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int[] iArrB2 = c.b.a.l.a.b(((c.b.a.g.a) this).f405a.x0(1));
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrB2);
                return true;
            case 9:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float f2 = ((c.b.a.g.a) this).f405a.x0(2)[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f2);
                return true;
            case 10:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                c.b.a.g.a aVar = (c.b.a.g.a) this;
                StringBuilder sbB2 = c.a.a.a.a.b("getInstant_Fuel_Consumption: ");
                sbB2.append(aVar.f405a.x0(3)[0]);
                Log.e("LS123", sbB2.toString());
                float f3 = aVar.f405a.x0(3)[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f3);
                return true;
            case 11:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i6 = (int) ((c.b.a.g.a) this).f405a.x0(4)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i6);
                return true;
            case 12:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i7 = (int) ((c.b.a.g.a) this).f405a.x0(5)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i7);
                return true;
            case 13:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i8 = (int) ((c.b.a.g.a) this).f405a.x0(6)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i8);
                return true;
            case 14:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                c.b.a.g.a aVar2 = (c.b.a.g.a) this;
                if (aVar2.f405a.x0(7)[0] != -1.0f || aVar2.f405a.y0("SendHandbrake")[0] != -1.0f) {
                    float f4 = aVar2.f405a.x0(7)[0];
                    c.b.a.h.b bVar3 = aVar2.f405a;
                    i3 = (int) (f4 != -1.0f ? bVar3.x0(7)[0] : bVar3.y0("SendHandbrake")[0]);
                }
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            case 15:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i9 = (int) ((c.b.a.g.a) this).f405a.x0(8)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i9);
                return true;
            case 16:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i10 = (int) ((c.b.a.g.a) this).f405a.x0(9)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i10);
                return true;
            case 17:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i11 = (int) ((c.b.a.g.a) this).f405a.x0(15)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i11);
                return true;
            case 18:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                c.b.a.g.a aVar3 = (c.b.a.g.a) this;
                aVar3.f405a.u0(true);
                float[] fArrY0 = aVar3.f405a.y0("Speed");
                int[] iArr = new int[fArrY0.length + 1];
                for (int i12 = 0; i12 < fArrY0.length; i12++) {
                    iArr[i12] = (int) fArrY0[i12];
                }
                iArr[1] = 0;
                parcel2.writeNoException();
                parcel2.writeIntArray(iArr);
                return true;
            case 19:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                c.b.a.g.a aVar4 = (c.b.a.g.a) this;
                aVar4.f405a.u0(true);
                int i13 = (int) aVar4.f405a.x0(11)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i13);
                return true;
            case 20:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float[] fArrX0 = ((c.b.a.g.a) this).f405a.x0(12);
                parcel2.writeNoException();
                parcel2.writeFloatArray(fArrX0);
                return true;
            case 21:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float[] fArrX1 = ((c.b.a.g.a) this).f405a.x0(13);
                parcel2.writeNoException();
                parcel2.writeFloatArray(fArrX1);
                return true;
            case 22:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float[] fArrX2 = ((c.b.a.g.a) this).f405a.x0(14);
                parcel2.writeNoException();
                parcel2.writeFloatArray(fArrX2);
                return true;
            case 23:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float f5 = ((c.b.a.g.a) this).f405a.x0(18)[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f5);
                return true;
            case 24:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float f6 = ((c.b.a.g.a) this).f405a.x0(16)[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f6);
                return true;
            case 25:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i14 = (int) ((c.b.a.g.a) this).f405a.x0(17)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i14);
                return true;
            case 26:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i15 = (int) ((c.b.a.g.a) this).f405a.x0(20)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i15);
                return true;
            case 27:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                parcel2.writeNoException();
                parcel2.writeInt(-1);
                return true;
            case 28:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i16 = (int) ((c.b.a.g.a) this).f405a.x0(55)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i16);
                return true;
            case 29:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float f7 = ((c.b.a.g.a) this).f405a.x0(59)[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f7);
                return true;
            case 30:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float f8 = ((c.b.a.g.a) this).f405a.x0(60)[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f8);
                return true;
            case 31:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float f9 = ((c.b.a.g.a) this).f405a.x0(61)[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f9);
                return true;
            case 32:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float f10 = ((c.b.a.g.a) this).f405a.x0(62)[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f10);
                return true;
            case 33:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i17 = (int) ((c.b.a.g.a) this).f405a.x0(63)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i17);
                return true;
            case 34:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i18 = (int) ((c.b.a.g.a) this).f405a.x0(64)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i18);
                return true;
            case 35:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i19 = (int) ((c.b.a.g.a) this).f405a.x0(65)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i19);
                return true;
            case 36:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i20 = (int) ((c.b.a.g.a) this).f405a.x0(66)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i20);
                return true;
            case 37:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                c.b.a.h.b bVar4 = ((c.b.a.g.a) this).f405a;
                int[] iArr2 = {67, 68, 69, 70};
                Objects.requireNonNull(bVar4);
                float[] fArr = new float[5];
                for (int i21 = 0; i21 < 4; i21++) {
                    fArr[i21] = -1.0f;
                }
                while (i4 < 4) {
                    ArrayList<c.b.a.e.a> arrayList = bVar4.f413c;
                    if (arrayList != null) {
                        for (c.b.a.e.a aVar5 : arrayList) {
                            if (aVar5.f397b == iArr2[i4] && aVar5.U == 1) {
                                fArr[i4] = bVar4.w0(aVar5);
                                if (bVar4.v0(aVar5).equals("KM") || bVar4.v0(aVar5).equals("psi")) {
                                    fArr[4] = 0.0f;
                                } else if (bVar4.v0(aVar5).equals("MI") || bVar4.v0(aVar5).equals("kpa")) {
                                    fArr[4] = 1.0f;
                                } else if (bVar4.v0(aVar5).equals("bar")) {
                                    fArr[4] = 2.0f;
                                }
                                if (bVar4.v0(aVar5).equals("℃")) {
                                    fArr[4] = 0.0f;
                                } else if (bVar4.v0(aVar5).equals("℉")) {
                                    fArr[4] = 1.0f;
                                }
                            }
                        }
                    }
                    i4++;
                }
                parcel2.writeNoException();
                parcel2.writeFloatArray(fArr);
                return true;
            case 38:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i22 = (int) ((c.b.a.g.a) this).f405a.x0(71)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i22);
                return true;
            case 39:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i23 = (int) ((c.b.a.g.a) this).f405a.x0(72)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i23);
                return true;
            case 40:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i24 = (int) ((c.b.a.g.a) this).f405a.x0(73)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i24);
                return true;
            case 41:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i25 = (int) ((c.b.a.g.a) this).f405a.x0(74)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i25);
                return true;
            case 42:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i26 = (int) ((c.b.a.g.a) this).f405a.x0(75)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i26);
                return true;
            case 43:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int[] iArrB3 = c.b.a.l.a.b(((c.b.a.g.a) this).f405a.x0(76));
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrB3);
                return true;
            case 44:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i27 = (int) ((c.b.a.g.a) this).f405a.x0(77)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i27);
                return true;
            case 45:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i28 = (int) ((c.b.a.g.a) this).f405a.x0(78)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i28);
                return true;
            case 46:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                ((c.b.a.g.a) this).f405a.u0(false);
                parcel2.writeNoException();
                return true;
            case 47:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                ((c.b.a.g.a) this).f405a.u0(true);
                parcel2.writeNoException();
                return true;
            case 48:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int[] iArrB4 = c.b.a.l.a.b(((c.b.a.g.a) this).f405a.x0(79));
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrB4);
                return true;
            case 49:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int[] iArrB5 = c.b.a.l.a.b(((c.b.a.g.a) this).f405a.x0(80));
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrB5);
                return true;
            case 50:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int[] iArrB6 = c.b.a.l.a.b(((c.b.a.g.a) this).f405a.x0(81));
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrB6);
                return true;
            case 51:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int[] iArrB7 = c.b.a.l.a.b(((c.b.a.g.a) this).f405a.x0(82));
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrB7);
                return true;
            case 52:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int[] iArrB8 = c.b.a.l.a.b(((c.b.a.g.a) this).f405a.x0(83));
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrB8);
                return true;
            case 53:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float[] fArrX3 = ((c.b.a.g.a) this).f405a.x0(84);
                parcel2.writeNoException();
                parcel2.writeFloatArray(fArrX3);
                return true;
            case 54:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float[] fArrX4 = ((c.b.a.g.a) this).f405a.x0(85);
                parcel2.writeNoException();
                parcel2.writeFloatArray(fArrX4);
                return true;
            case 55:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float[] fArrX5 = ((c.b.a.g.a) this).f405a.x0(86);
                parcel2.writeNoException();
                parcel2.writeFloatArray(fArrX5);
                return true;
            case 56:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float[] fArrX6 = ((c.b.a.g.a) this).f405a.x0(87);
                parcel2.writeNoException();
                parcel2.writeFloatArray(fArrX6);
                return true;
            case 57:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i29 = (int) ((c.b.a.g.a) this).f405a.x0(88)[0];
                parcel2.writeNoException();
                parcel2.writeInt(i29);
                return true;
            case 58:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float[] fArrY1 = ((c.b.a.g.a) this).f405a.y0("Radar");
                int[] iArr3 = new int[fArrY1.length];
                while (i4 < fArrY1.length) {
                    iArr3[i4] = (int) fArrY1[i4];
                    i4++;
                }
                parcel2.writeNoException();
                parcel2.writeIntArray(iArr3);
                return true;
            case 59:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                c.b.a.g.a aVar6 = (c.b.a.g.a) this;
                aVar6.f405a.u0(true);
                int i30 = (int) aVar6.f405a.y0("Track")[0];
                parcel2.writeNoException();
                parcel2.writeInt(i30);
                return true;
            case 60:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                int i31 = (int) ((c.b.a.g.a) this).f405a.y0("Turn")[0];
                parcel2.writeNoException();
                parcel2.writeInt(i31);
                return true;
            case 61:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                float f11 = (int) ((c.b.a.g.a) this).f405a.x0(89)[0];
                parcel2.writeNoException();
                parcel2.writeFloat(f11);
                return true;
            case 62:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                c.b.a.g.a aVar7 = (c.b.a.g.a) this;
                StringBuilder sbB3 = c.a.a.a.a.b("extendedInterface: ");
                sbB3.append(aVar7.f405a != null);
                Log.e("LS123", sbB3.toString());
                try {
                    int i32 = ((byte) bundle.getInt("data0", 0)) & 255;
                    int i33 = ((byte) bundle.getInt("data1", 0)) & 255;
                    Log.e("LS123", "extendedInterface: " + i32);
                    c.b.a.h.b bVar5 = aVar7.f405a;
                    if (bVar5 != null) {
                        bVar5.B0(i32, i33);
                    }
                    break;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                parcel2.writeNoException();
                parcel2.writeInt(0);
                return true;
            case 63:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                a.b.e.b.a.a(parcel.readStrongBinder());
                parcel.readInt();
                parcel2.writeNoException();
                return true;
            case 64:
                parcel.enforceInterface("com.tw.carinfoservice.CarServiceAidl");
                parcel2.writeNoException();
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
