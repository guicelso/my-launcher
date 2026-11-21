package android.hardware.power.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class PowerStatePlatformSleepState {
    public long residencyInMsecSinceBoot;
    public boolean supportedOnlyInSuspend;
    public long totalTransitions;
    public String name = new String();
    public ArrayList<PowerStateVoter> voters = new ArrayList<>();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != PowerStatePlatformSleepState.class) {
            return false;
        }
        PowerStatePlatformSleepState other = (PowerStatePlatformSleepState) otherObject;
        if (HidlSupport.deepEquals(this.name, other.name) && this.residencyInMsecSinceBoot == other.residencyInMsecSinceBoot && this.totalTransitions == other.totalTransitions && this.supportedOnlyInSuspend == other.supportedOnlyInSuspend && HidlSupport.deepEquals(this.voters, other.voters)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.residencyInMsecSinceBoot))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.totalTransitions))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.supportedOnlyInSuspend))), Integer.valueOf(HidlSupport.deepHashCode(this.voters)));
    }

    public final String toString() {
        return "{.name = " + this.name + ", .residencyInMsecSinceBoot = " + this.residencyInMsecSinceBoot + ", .totalTransitions = " + this.totalTransitions + ", .supportedOnlyInSuspend = " + this.supportedOnlyInSuspend + ", .voters = " + this.voters + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(56L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<PowerStatePlatformSleepState> readVectorFromParcel(HwParcel parcel) {
        ArrayList<PowerStatePlatformSleepState> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 56, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            PowerStatePlatformSleepState _hidl_vec_element = new PowerStatePlatformSleepState();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 56);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.name = _hidl_blob.getString(_hidl_offset + 0);
        parcel.readEmbeddedBuffer(this.name.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 0 + 0, false);
        this.residencyInMsecSinceBoot = _hidl_blob.getInt64(_hidl_offset + 16);
        this.totalTransitions = _hidl_blob.getInt64(_hidl_offset + 24);
        this.supportedOnlyInSuspend = _hidl_blob.getBool(_hidl_offset + 32);
        int _hidl_vec_size = _hidl_blob.getInt32(_hidl_offset + 40 + 8);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), _hidl_offset + 40 + 0, true);
        this.voters.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            PowerStateVoter _hidl_vec_element = new PowerStateVoter();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 32);
            this.voters.add(_hidl_vec_element);
        }
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(56);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<PowerStatePlatformSleepState> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 56);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 56);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putString(_hidl_offset + 0, this.name);
        _hidl_blob.putInt64(16 + _hidl_offset, this.residencyInMsecSinceBoot);
        _hidl_blob.putInt64(24 + _hidl_offset, this.totalTransitions);
        _hidl_blob.putBool(32 + _hidl_offset, this.supportedOnlyInSuspend);
        int _hidl_vec_size = this.voters.size();
        _hidl_blob.putInt32(_hidl_offset + 40 + 8, _hidl_vec_size);
        _hidl_blob.putBool(_hidl_offset + 40 + 12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            this.voters.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 32);
        }
        _hidl_blob.putBlob(40 + _hidl_offset + 0, childBlob);
    }
}
