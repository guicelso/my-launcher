package com.tw.qcarcamlib;

import android.view.SurfaceHolder;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class QcarcamJNI {
    private static final String TAG = "QcarcamJNI";
    private ArrayList<Object> mSurfaceList = new ArrayList<>();
    private OnCallback onCallback;

    public interface OnCallback {
        void onState(int i, int i2);
    }

    static {
        System.loadLibrary("qcarcam_wrapper");
    }

    private final native int native_setSurfaceList(ArrayList<Object> arrayList);

    public void nativeCameraDataCallback(int i, byte[] bArr, int i2, int i3, int i4, int i5) {
    }

    public void nativeCameraStateCallback(int i, int i2) {
        OnCallback onCallback = this.onCallback;
        if (onCallback != null) {
            onCallback.onState(i, i2);
        }
    }

    public final native int native_camera_close();

    public final native int native_camera_dump(int i);

    public final native int native_camera_open();

    public final native int native_init();

    public final native int native_set_camera_brightness(int i);

    public final native int native_set_camera_contrast(int i);

    public final native int native_set_camera_saturation(int i);

    public final native int native_surfaceChanged(int i, int i2, int i3);

    public final native int native_surfaceCreated(Object obj);

    public final native int native_surfaceDestroyed();

    public final native int native_test();

    public void setOnCallback(OnCallback onCallback) {
        this.onCallback = onCallback;
    }

    public void setSurfaceList(List<SurfaceHolder> list) {
        this.mSurfaceList.clear();
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                this.mSurfaceList.add(list.get(i).getSurface());
            }
            ArrayList<Object> arrayList = this.mSurfaceList;
            if (arrayList != null && arrayList.size() > 0) {
                native_setSurfaceList(this.mSurfaceList);
                return;
            }
        } else {
            this.mSurfaceList.clear();
        }
        native_setSurfaceList(null);
    }
}
