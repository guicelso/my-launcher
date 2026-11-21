package android.hardware.power.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class PowerStateVoter {
    public String name = new String();
    public long totalNumberOfTimesVotedSinceBoot;
    public long totalTimeInMsecVotedForSinceBoot;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != PowerStateVoter.class) {
            return false;
        }
        PowerStateVoter other = (PowerStateVoter) otherObject;
        if (HidlSupport.deepEquals(this.name, other.name) && this.totalTimeInMsecVotedForSinceBoot == other.totalTimeInMsecVotedForSinceBoot && this.totalNumberOfTimesVotedSinceBoot == other.totalNumberOfTimesVotedSinceBoot) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.totalTimeInMsecVotedForSinceBoot))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.totalNumberOfTimesVotedSinceBoot))));
    }

    public final String toString() {
        return "{.name = " + this.name + ", .totalTimeInMsecVotedForSinceBoot = " + this.totalTimeInMsecVotedForSinceBoot + ", .totalNumberOfTimesVotedSinceBoot = " + this.totalNumberOfTimesVotedSinceBoot + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(32L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<PowerStateVoter> readVectorFromParcel(HwParcel parcel) {
        ArrayList<PowerStateVoter> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            PowerStateVoter _hidl_vec_element = new PowerStateVoter();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 32);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.name = _hidl_blob.getString(_hidl_offset + 0);
        parcel.readEmbeddedBuffer(this.name.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 0 + 0, false);
        this.totalTimeInMsecVotedForSinceBoot = _hidl_blob.getInt64(16 + _hidl_offset);
        this.totalNumberOfTimesVotedSinceBoot = _hidl_blob.getInt64(24 + _hidl_offset);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(32);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<PowerStateVoter> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 32);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putString(0 + _hidl_offset, this.name);
        _hidl_blob.putInt64(16 + _hidl_offset, this.totalTimeInMsecVotedForSinceBoot);
        _hidl_blob.putInt64(24 + _hidl_offset, this.totalNumberOfTimesVotedSinceBoot);
    }
}
