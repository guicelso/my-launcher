package vendor.sprd.hardware.thermal.V1_0;

import android.hardware.thermal.V1_0.CoolingDevice;
import android.hardware.thermal.V1_0.CpuUsage;
import android.hardware.thermal.V1_0.Temperature;
import android.hardware.thermal.V1_0.ThermalStatus;
import android.hardware.thermal.V1_1.IThermal;
import android.hardware.thermal.V1_1.IThermalCallback;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes4.dex */
public interface IExtThermal extends IThermal {
    public static final String kInterfaceName = "vendor.sprd.hardware.thermal@1.0::IExtThermal";

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    IHwBinder asBinder();

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    boolean getExtThermal(int i) throws RemoteException;

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    void setExtThermal(int i) throws RemoteException;

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IExtThermal asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IExtThermal)) {
            return (IExtThermal) iface;
        }
        IExtThermal proxy = new Proxy(binder);
        try {
            for (String descriptor : proxy.interfaceChain()) {
                if (descriptor.equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException e) {
        }
        return null;
    }

    static IExtThermal castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IExtThermal getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IExtThermal getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    static IExtThermal getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    static IExtThermal getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IExtThermal {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(remote);
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of vendor.sprd.hardware.thermal@1.0::IExtThermal]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.thermal.V1_0.IThermal
        public void getTemperatures(android.hardware.thermal.V1_0.IThermal.getTemperaturesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<Temperature> _hidl_out_temperatures = Temperature.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_temperatures);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V1_0.IThermal
        public void getCpuUsages(android.hardware.thermal.V1_0.IThermal.getCpuUsagesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<CpuUsage> _hidl_out_cpuUsages = CpuUsage.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_cpuUsages);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V1_0.IThermal
        public void getCoolingDevices(android.hardware.thermal.V1_0.IThermal.getCoolingDevicesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<CoolingDevice> _hidl_out_devices = CoolingDevice.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_devices);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V1_1.IThermal
        public void registerThermalCallback(IThermalCallback callback) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IThermal.kInterfaceName);
            _hidl_request.writeStrongBinder(callback == null ? null : callback.asBinder());
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal
        public void setExtThermal(int cmd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IExtThermal.kInterfaceName);
            _hidl_request.writeInt32(cmd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal
        public boolean getExtThermal(int cmd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IExtThermal.kInterfaceName);
            _hidl_request.writeInt32(cmd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                boolean _hidl_out_en = _hidl_reply.readBool();
                return _hidl_out_en;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256067662, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ArrayList<String> _hidl_out_descriptors = _hidl_reply.readStringVector();
                return _hidl_out_descriptors;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            _hidl_request.writeNativeHandle(fd);
            _hidl_request.writeStringVector(options);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256131655, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public String interfaceDescriptor() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256136003, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                String _hidl_out_descriptor = _hidl_reply.readString();
                return _hidl_out_descriptor;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256398152, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ArrayList<byte[]> _hidl_out_hashchain = new ArrayList<>();
                HwBlob _hidl_blob = _hidl_reply.readBuffer(16L);
                int _hidl_vec_size = _hidl_blob.getInt32(8L);
                HwBlob childBlob = _hidl_reply.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
                _hidl_out_hashchain.clear();
                for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                    byte[] _hidl_vec_element = new byte[32];
                    long _hidl_array_offset_1 = _hidl_index_0 * 32;
                    childBlob.copyToInt8Array(_hidl_array_offset_1, _hidl_vec_element, 32);
                    _hidl_out_hashchain.add(_hidl_vec_element);
                }
                _hidl_reply.release();
                return _hidl_out_hashchain;
            } catch (Throwable th) {
                _hidl_reply.release();
                throw th;
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256462420, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public void ping() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256921159, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public DebugInfo getDebugInfo() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257049926, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                DebugInfo _hidl_out_info = new DebugInfo();
                _hidl_out_info.readFromParcel(_hidl_reply);
                return _hidl_out_info;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257120595, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IExtThermal {
        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public IHwBinder asBinder() {
            return this;
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IExtThermal.kInterfaceName, IThermal.kInterfaceName, android.hardware.thermal.V1_0.IThermal.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) {
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IExtThermal.kInterfaceName;
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{105, -16, -110, 79, -101, -100, -28, 55, -3, 26, 12, -63, -45, 107, 62, 32, -86, 0, 108, -8, -50, -84, 115, -91, 72, -32, 9, 65, -108, 41, -61, -122}, new byte[]{-12, -108, 94, 57, 123, 93, -22, 65, -69, 100, 81, -115, -3, -27, -101, -25, 18, 69, -40, -95, 37, -3, 30, 10, -49, -2, -75, 122, -57, -80, -113, -19}, new byte[]{-105, -15, -20, 68, 96, 67, -68, 90, 102, 69, -73, 69, 41, -90, 39, 100, -106, -67, -77, 94, 10, -18, 65, -19, -91, 92, -71, 45, 81, -21, 120, 2}, new byte[]{-20, ByteCompanionObject.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0L;
            info.arch = 0;
            return info;
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // vendor.sprd.hardware.thermal.V1_0.IExtThermal, android.hardware.thermal.V1_1.IThermal, android.hardware.thermal.V1_0.IThermal, android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        public IHwInterface queryLocalInterface(String descriptor) {
            if (IExtThermal.kInterfaceName.equals(descriptor)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String serviceName) throws RemoteException {
            registerService(serviceName);
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        public void onTransact(int _hidl_code, HwParcel _hidl_request, final HwParcel _hidl_reply, int _hidl_flags) throws RemoteException {
            switch (_hidl_code) {
                case 1:
                    boolean _hidl_is_oneway = (_hidl_flags & 1) != 0;
                    if (_hidl_is_oneway) {
                        _hidl_reply.writeStatus(Integer.MIN_VALUE);
                        _hidl_reply.send();
                        return;
                    } else {
                        _hidl_request.enforceInterface(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
                        getTemperatures(new android.hardware.thermal.V1_0.IThermal.getTemperaturesCallback() { // from class: vendor.sprd.hardware.thermal.V1_0.IExtThermal.Stub.1
                            @Override // android.hardware.thermal.V1_0.IThermal.getTemperaturesCallback
                            public void onValues(ThermalStatus status, ArrayList<Temperature> temperatures) {
                                _hidl_reply.writeStatus(0);
                                status.writeToParcel(_hidl_reply);
                                Temperature.writeVectorToParcel(_hidl_reply, temperatures);
                                _hidl_reply.send();
                            }
                        });
                        return;
                    }
                case 2:
                    boolean _hidl_is_oneway2 = (_hidl_flags & 1) != 0;
                    if (_hidl_is_oneway2) {
                        _hidl_reply.writeStatus(Integer.MIN_VALUE);
                        _hidl_reply.send();
                        return;
                    } else {
                        _hidl_request.enforceInterface(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
                        getCpuUsages(new android.hardware.thermal.V1_0.IThermal.getCpuUsagesCallback() { // from class: vendor.sprd.hardware.thermal.V1_0.IExtThermal.Stub.2
                            @Override // android.hardware.thermal.V1_0.IThermal.getCpuUsagesCallback
                            public void onValues(ThermalStatus status, ArrayList<CpuUsage> cpuUsages) {
                                _hidl_reply.writeStatus(0);
                                status.writeToParcel(_hidl_reply);
                                CpuUsage.writeVectorToParcel(_hidl_reply, cpuUsages);
                                _hidl_reply.send();
                            }
                        });
                        return;
                    }
                case 3:
                    boolean _hidl_is_oneway3 = (_hidl_flags & 1) != 0;
                    if (_hidl_is_oneway3) {
                        _hidl_reply.writeStatus(Integer.MIN_VALUE);
                        _hidl_reply.send();
                        return;
                    } else {
                        _hidl_request.enforceInterface(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
                        getCoolingDevices(new android.hardware.thermal.V1_0.IThermal.getCoolingDevicesCallback() { // from class: vendor.sprd.hardware.thermal.V1_0.IExtThermal.Stub.3
                            @Override // android.hardware.thermal.V1_0.IThermal.getCoolingDevicesCallback
                            public void onValues(ThermalStatus status, ArrayList<CoolingDevice> devices) {
                                _hidl_reply.writeStatus(0);
                                status.writeToParcel(_hidl_reply);
                                CoolingDevice.writeVectorToParcel(_hidl_reply, devices);
                                _hidl_reply.send();
                            }
                        });
                        return;
                    }
                case 4:
                    int cmd = _hidl_flags & 1;
                    boolean _hidl_is_oneway4 = cmd != 0;
                    if (_hidl_is_oneway4) {
                        _hidl_reply.writeStatus(Integer.MIN_VALUE);
                        _hidl_reply.send();
                        return;
                    }
                    _hidl_request.enforceInterface(IThermal.kInterfaceName);
                    IThermalCallback callback = IThermalCallback.asInterface(_hidl_request.readStrongBinder());
                    registerThermalCallback(callback);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 5:
                    boolean _hidl_is_oneway5 = (_hidl_flags & 1) != 0;
                    if (_hidl_is_oneway5) {
                        _hidl_reply.writeStatus(Integer.MIN_VALUE);
                        _hidl_reply.send();
                        return;
                    }
                    _hidl_request.enforceInterface(IExtThermal.kInterfaceName);
                    int cmd2 = _hidl_request.readInt32();
                    setExtThermal(cmd2);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 6:
                    boolean _hidl_is_oneway6 = (_hidl_flags & 1) != 0;
                    if (_hidl_is_oneway6) {
                        _hidl_reply.writeStatus(Integer.MIN_VALUE);
                        _hidl_reply.send();
                        return;
                    }
                    _hidl_request.enforceInterface(IExtThermal.kInterfaceName);
                    int cmd3 = _hidl_request.readInt32();
                    boolean _hidl_out_en = getExtThermal(cmd3);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeBool(_hidl_out_en);
                    _hidl_reply.send();
                    return;
                default:
                    switch (_hidl_code) {
                        case 256067662:
                            boolean _hidl_is_oneway7 = (_hidl_flags & 1) != 0;
                            if (_hidl_is_oneway7) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            }
                            _hidl_request.enforceInterface(IBase.kInterfaceName);
                            ArrayList<String> _hidl_out_descriptors = interfaceChain();
                            _hidl_reply.writeStatus(0);
                            _hidl_reply.writeStringVector(_hidl_out_descriptors);
                            _hidl_reply.send();
                            return;
                        case 256131655:
                            boolean _hidl_is_oneway8 = (_hidl_flags & 1) != 0;
                            if (_hidl_is_oneway8) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            }
                            _hidl_request.enforceInterface(IBase.kInterfaceName);
                            NativeHandle fd = _hidl_request.readNativeHandle();
                            ArrayList<String> options = _hidl_request.readStringVector();
                            debug(fd, options);
                            _hidl_reply.writeStatus(0);
                            _hidl_reply.send();
                            return;
                        case 256136003:
                            boolean _hidl_is_oneway9 = (_hidl_flags & 1) != 0;
                            if (_hidl_is_oneway9) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            }
                            _hidl_request.enforceInterface(IBase.kInterfaceName);
                            String _hidl_out_descriptor = interfaceDescriptor();
                            _hidl_reply.writeStatus(0);
                            _hidl_reply.writeString(_hidl_out_descriptor);
                            _hidl_reply.send();
                            return;
                        case 256398152:
                            boolean _hidl_is_oneway10 = (_hidl_flags & 1) != 0;
                            if (_hidl_is_oneway10) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            }
                            _hidl_request.enforceInterface(IBase.kInterfaceName);
                            ArrayList<byte[]> _hidl_out_hashchain = getHashChain();
                            _hidl_reply.writeStatus(0);
                            HwBlob _hidl_blob = new HwBlob(16);
                            int _hidl_vec_size = _hidl_out_hashchain.size();
                            _hidl_blob.putInt32(8L, _hidl_vec_size);
                            _hidl_blob.putBool(12L, false);
                            HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
                            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                                long _hidl_array_offset_1 = _hidl_index_0 * 32;
                                byte[] _hidl_array_item_1 = _hidl_out_hashchain.get(_hidl_index_0);
                                if (_hidl_array_item_1 == null || _hidl_array_item_1.length != 32) {
                                    throw new IllegalArgumentException("Array element is not of the expected length");
                                }
                                childBlob.putInt8Array(_hidl_array_offset_1, _hidl_array_item_1);
                            }
                            _hidl_blob.putBlob(0L, childBlob);
                            _hidl_reply.writeBuffer(_hidl_blob);
                            _hidl_reply.send();
                            return;
                        case 256462420:
                            boolean _hidl_is_oneway11 = (_hidl_flags & 1) != 0;
                            if (!_hidl_is_oneway11) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            } else {
                                _hidl_request.enforceInterface(IBase.kInterfaceName);
                                setHALInstrumentation();
                                return;
                            }
                        case 256660548:
                            boolean _hidl_is_oneway12 = (_hidl_flags & 1) != 0;
                            if (_hidl_is_oneway12) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            }
                            return;
                        case 256921159:
                            boolean _hidl_is_oneway13 = (_hidl_flags & 1) != 0;
                            if (_hidl_is_oneway13) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            } else {
                                _hidl_request.enforceInterface(IBase.kInterfaceName);
                                ping();
                                _hidl_reply.writeStatus(0);
                                _hidl_reply.send();
                                return;
                            }
                        case 257049926:
                            boolean _hidl_is_oneway14 = (_hidl_flags & 1) != 0;
                            if (_hidl_is_oneway14) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            }
                            _hidl_request.enforceInterface(IBase.kInterfaceName);
                            DebugInfo _hidl_out_info = getDebugInfo();
                            _hidl_reply.writeStatus(0);
                            _hidl_out_info.writeToParcel(_hidl_reply);
                            _hidl_reply.send();
                            return;
                        case 257120595:
                            boolean _hidl_is_oneway15 = (_hidl_flags & 1) != 0;
                            if (!_hidl_is_oneway15) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            } else {
                                _hidl_request.enforceInterface(IBase.kInterfaceName);
                                notifySyspropsChanged();
                                return;
                            }
                        case 257250372:
                            boolean _hidl_is_oneway16 = (_hidl_flags & 1) != 0;
                            if (_hidl_is_oneway16) {
                                _hidl_reply.writeStatus(Integer.MIN_VALUE);
                                _hidl_reply.send();
                                return;
                            }
                            return;
                        default:
                            return;
                    }
            }
        }
    }
}
