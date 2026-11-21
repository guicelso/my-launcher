package c.b.c;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public interface c extends IInterface {

    public static abstract class a extends Binder implements c {
        public a() {
            attachInterface(this, "com.tw.uart.IUartReceiver");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 1598968902) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    parcel2.writeString("com.tw.uart.IUartReceiver");
                    return true;
                }
                parcel.enforceInterface("com.tw.uart.IUartReceiver");
                if (parcel.readInt() != 0) {
                }
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface("com.tw.uart.IUartReceiver");
            byte[] bArrCreateByteArray = parcel.createByteArray();
            StringBuilder sbB = c.a.a.a.a.b("onUartDataUpdate:  ");
            StringBuilder sb = new StringBuilder("");
            for (byte b2 : bArrCreateByteArray) {
                String hexString = Integer.toHexString(b2 & 255);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
                sb.append(" ");
            }
            sbB.append(sb.toString().toUpperCase().trim());
            Log.d("LS123", sbB.toString());
            parcel2.writeNoException();
            return true;
        }
    }
}
