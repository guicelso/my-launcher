package com.tw.reverse;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class LRReverseActivity extends Activity implements SurfaceHolder.Callback {
    public static final int ACTIVITY_PAUSE = 135;
    public static final int ACTIVITY_RUSEME = 7;
    private static final int CAMERA_HIDE = 65294;
    private static final int EXIT = 65281;
    private static final int GONE = 65280;
    public static final int REQUEST_BRAKE = 517;
    public static final int REQUEST_SERVICE = 40448;
    public static final int REQUEST_SOURCE = 40465;
    public static final int REQUEST_SOURCE2 = 769;
    public static final int RETURN_BRAKE = 517;
    private static final int SIGNAL = 65295;
    private static final int START_PREVIEW = 65296;
    private static final String TAG = "LRReverseActivity";
    private int heightPixels;
    private CameraView mCameraView;
    private RelativeLayout mHB;
    private TextView warning_driving;
    private View warning_layout;
    private TextView warning_no_signal;
    private int widthPixels;
    private TWUtil mTWUtil = null;
    private Camera mCamera = null;
    private SurfaceView mSurfaceView = null;
    private int mCount = 0;
    private int cameraId = 0;
    private boolean mBrake = false;
    private float density = 1.0f;
    private int w = 1024;
    private int h = 600;
    private boolean mPreview = false;
    private int mService = 0;
    private Handler mHandler = new Handler() { // from class: com.tw.reverse.LRReverseActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case 517:
                        break;
                    case LRReverseActivity.GONE /* 65280 */:
                        LRReverseActivity.this.mHB.setVisibility(8);
                        break;
                    case LRReverseActivity.EXIT /* 65281 */:
                        LRReverseActivity.this.finish();
                        break;
                    case LRReverseActivity.CAMERA_HIDE /* 65294 */:
                        LRReverseActivity.this.mCameraView.hide();
                        break;
                    case LRReverseActivity.SIGNAL /* 65295 */:
                        removeMessages(LRReverseActivity.SIGNAL);
                        LRReverseActivity.access$108(LRReverseActivity.this);
                        int signal = LRReverseActivity.this.getSignal();
                        if (signal == 0) {
                            LRReverseActivity.this.warning_layout.setVisibility(8);
                            if (LRReverseActivity.this.mCount > 20) {
                                LRReverseActivity.this.warning_layout.setVisibility(0);
                                LRReverseActivity.this.warning_no_signal.setVisibility(0);
                            }
                        } else {
                            LRReverseActivity.this.warning_layout.setVisibility(8);
                        }
                        sendEmptyMessageDelayed(LRReverseActivity.SIGNAL, 200L);
                        break;
                    case LRReverseActivity.START_PREVIEW /* 65296 */:
                        LRReverseActivity.this.startPreview();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
            }
        }
    };
    boolean isTV = false;

    static /* synthetic */ int access$108(LRReverseActivity x0) {
        int i = x0.mCount;
        x0.mCount = i + 1;
        return i;
    }

    public void requestBrake() {
        this.mTWUtil.write(517, 255);
    }

    public void setPreview(boolean on) {
        if (on && !this.mPreview) {
            this.mPreview = true;
            if (this.mCamera != null) {
                this.mSurfaceView.setVisibility(0);
                try {
                    Camera.Parameters parameters = this.mCamera.getParameters();
                    int mode = SystemProperties.getInt(this.isTV ? "persist.camera.mode1" : "persist.camera.mode2", 0);
                    int m_de = SystemProperties.getInt("persist.camera.deinterlace", 1);
                    switch (mode) {
                        case R.styleable.radar_adjust /* 0 */:
                            if (m_de == 0) {
                                parameters.setPreviewSize(960, 240);
                            } else {
                                parameters.setPreviewSize(960, 480);
                            }
                            break;
                        case R.styleable.radar_brush_width /* 1 */:
                            if (m_de == 0) {
                                parameters.setPreviewSize(960, 288);
                            } else {
                                parameters.setPreviewSize(960, 576);
                            }
                            break;
                        case R.styleable.radar_orientation /* 2 */:
                        case R.styleable.radar_src /* 3 */:
                        case 4:
                        case 5:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            parameters.setPreviewSize(1280, 720);
                            break;
                        case 6:
                        case ACTIVITY_RUSEME /* 7 */:
                        case 8:
                        case 9:
                            parameters.setPreviewSize(1920, 1080);
                            break;
                        default:
                            if (m_de == 0) {
                                parameters.setPreviewSize(960, 240);
                            } else {
                                parameters.setPreviewSize(960, 480);
                            }
                            break;
                    }
                    this.mCamera.setParameters(parameters);
                } catch (Exception e) {
                }
                int m_V = SystemProperties.getInt("persist.camera.lr.angle", 90);
                if (m_V == 270) {
                    this.mCamera.setDisplayOrientation(180);
                }
                this.mCamera.startPreview();
                return;
            }
            return;
        }
        if (!on && this.mPreview) {
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.stopPreview();
                this.mSurfaceView.setVisibility(8);
            }
            this.mPreview = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPreview() {
        if (this.mTWUtil.write(65521) == 544 && !getSystemVersion().contains("-360")) {
            this.mTWUtil.write(524, 4);
        }
        setPreview(true);
        this.warning_layout.setVisibility(8);
        this.mCount = 0;
        this.mHandler.removeMessages(SIGNAL);
        this.mHandler.sendEmptyMessageDelayed(SIGNAL, 300L);
    }

    public void requestService(int activity) {
        this.mService = activity;
        this.mTWUtil.write(REQUEST_SERVICE, activity);
    }

    public void touch(int x, int y) {
        this.mTWUtil.write(2050, (x * 255) / (this.w - 1), (y * 255) / (this.h - 1));
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.density = getResources().getDisplayMetrics().density;
        this.widthPixels = getResources().getDisplayMetrics().widthPixels;
        this.heightPixels = getResources().getDisplayMetrics().heightPixels;
        initCameraId();
        setContentView(R.layout.lrreverse);
        this.warning_layout = (RelativeLayout) findViewById(R.id.warning_layout);
        this.warning_driving = (TextView) findViewById(R.id.warning_driving);
        this.warning_no_signal = (TextView) findViewById(R.id.warning_no_signal);
        this.mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        SurfaceHolder holder = this.mSurfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(3);
        this.mHB = (RelativeLayout) findViewById(R.id.hb);
        this.mCameraView = new CameraView();
        this.mTWUtil = new TWUtil();
        if (this.mTWUtil.open(new short[]{517}) != 0) {
            finish();
        }
        this.mTWUtil.start();
        Log.d(TAG, "" + Camera.getNumberOfCameras());
        if (this.widthPixels / this.heightPixels >= 2.0d) {
            int mode = Settings.System.getInt(getContentResolver(), "REVERSING_MODE", 0);
            RelativeLayout.LayoutParams mlp_Radar = (RelativeLayout.LayoutParams) this.mSurfaceView.getLayoutParams();
            if (mode == 0) {
                mlp_Radar.width = (int) (this.widthPixels * 0.74f);
                this.mSurfaceView.setLayoutParams(mlp_Radar);
            } else if (mode == 1) {
                mlp_Radar.width = this.widthPixels;
                this.mSurfaceView.setLayoutParams(mlp_Radar);
            } else if (mode == 2) {
                mlp_Radar.width = (int) ((this.heightPixels / 9.0f) * 16.0f);
                this.mSurfaceView.setLayoutParams(mlp_Radar);
            }
        }
    }

    void initCameraId() {
        String version = getSystemVersion();
        if (TextUtils.isEmpty(version)) {
            this.cameraId = 0;
        }
        if (version.startsWith("TS10")) {
            this.cameraId = 0;
        } else if (version.startsWith("TS18")) {
            this.cameraId = 1;
        }
    }

    private class CameraView {
        private static final int MAX = 100;
        private boolean isShow = false;
        private WindowManager.LayoutParams mLayoutParams;
        private ImageView mLrReverseMirrorLeftRight;
        private ImageView mLrReverseMirrorTopBottom;
        private View mView;
        private WindowManager mWindowManager;

        public CameraView() {
            this.mView = ((LayoutInflater) LRReverseActivity.this.getSystemService("layout_inflater")).inflate(R.layout.lrreverse_camera, (ViewGroup) null);
            this.mLrReverseMirrorLeftRight = (ImageView) this.mView.findViewById(R.id.lrreverse_mirror_left_right);
            this.mLrReverseMirrorTopBottom = (ImageView) this.mView.findViewById(R.id.lrreverse_mirror_top_bottom);
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.tw.reverse.LRReverseActivity.CameraView.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int progress2 = progress - 50;
                        int id = seekBar.getId();
                        if (id == R.id.brightness_level) {
                            ((TextView) CameraView.this.mView.findViewById(R.id.brightness_title)).setText(LRReverseActivity.this.getString(R.string.brightness) + "(" + progress2 + ")");
                            if (LRReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters = LRReverseActivity.this.mCamera.getParameters();
                                    parameters.setBrightness(String.format(Locale.US, "%d", Integer.valueOf(progress2 + 128)));
                                    LRReverseActivity.this.mCamera.setParameters(parameters);
                                } catch (Exception e) {
                                }
                            }
                        } else if (id == R.id.contrast_level) {
                            ((TextView) CameraView.this.mView.findViewById(R.id.contrast_title)).setText(LRReverseActivity.this.getString(R.string.contrast) + "(" + progress2 + ")");
                            if (LRReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters2 = LRReverseActivity.this.mCamera.getParameters();
                                    parameters2.setContrast(String.format(Locale.US, "%d", Integer.valueOf(progress2 + 128)));
                                    LRReverseActivity.this.mCamera.setParameters(parameters2);
                                } catch (Exception e2) {
                                }
                            }
                        } else if (id == R.id.saturation_level) {
                            ((TextView) CameraView.this.mView.findViewById(R.id.saturation_title)).setText(LRReverseActivity.this.getString(R.string.saturation) + "(" + progress2 + ")");
                            if (LRReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters3 = LRReverseActivity.this.mCamera.getParameters();
                                    parameters3.setSaturation(String.format(Locale.US, "%d", Integer.valueOf(progress2 + 128)));
                                    LRReverseActivity.this.mCamera.setParameters(parameters3);
                                } catch (Exception e3) {
                                }
                            }
                        }
                        LRReverseActivity.this.mHandler.removeMessages(LRReverseActivity.CAMERA_HIDE);
                        LRReverseActivity.this.mHandler.sendEmptyMessageDelayed(LRReverseActivity.CAMERA_HIDE, 5000L);
                    }
                }
            };
            ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setOnSeekBarChangeListener(onSeekBarChangeListener);
            ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setOnSeekBarChangeListener(onSeekBarChangeListener);
            ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setOnSeekBarChangeListener(onSeekBarChangeListener);
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.tw.reverse.LRReverseActivity.CameraView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    int progress;
                    int progress2;
                    int progress3;
                    int progress4;
                    int progress5;
                    int progress6;
                    switch (view.getId()) {
                        case R.id.brightness_down /* 2131034126 */:
                            int progress7 = (((SeekBar) CameraView.this.mView.findViewById(R.id.brightness_level)).getProgress() - 50) - 1;
                            if (progress7 >= -50) {
                                progress = progress7;
                            } else {
                                progress = -50;
                            }
                            ((TextView) CameraView.this.mView.findViewById(R.id.brightness_title)).setText(LRReverseActivity.this.getString(R.string.brightness) + "(" + progress + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.brightness_level)).setProgress(progress + 50);
                            if (LRReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters = LRReverseActivity.this.mCamera.getParameters();
                                    parameters.setBrightness(String.format(Locale.US, "%d", Integer.valueOf(progress + 128)));
                                    LRReverseActivity.this.mCamera.setParameters(parameters);
                                } catch (Exception e) {
                                }
                            }
                            break;
                        case R.id.brightness_up /* 2131034129 */:
                            int progress8 = (((SeekBar) CameraView.this.mView.findViewById(R.id.brightness_level)).getProgress() - 50) + 1;
                            if (progress8 <= 50) {
                                progress2 = progress8;
                            } else {
                                progress2 = 50;
                            }
                            ((TextView) CameraView.this.mView.findViewById(R.id.brightness_title)).setText(LRReverseActivity.this.getString(R.string.brightness) + "(" + progress2 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.brightness_level)).setProgress(progress2 + 50);
                            if (LRReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters2 = LRReverseActivity.this.mCamera.getParameters();
                                    parameters2.setBrightness(String.format(Locale.US, "%d", Integer.valueOf(progress2 + 128)));
                                    LRReverseActivity.this.mCamera.setParameters(parameters2);
                                } catch (Exception e2) {
                                }
                            }
                            break;
                        case R.id.contrast_down /* 2131034149 */:
                            int progress9 = (((SeekBar) CameraView.this.mView.findViewById(R.id.contrast_level)).getProgress() - 50) - 1;
                            if (progress9 >= -50) {
                                progress3 = progress9;
                            } else {
                                progress3 = -50;
                            }
                            ((TextView) CameraView.this.mView.findViewById(R.id.contrast_title)).setText(LRReverseActivity.this.getString(R.string.contrast) + "(" + progress3 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.contrast_level)).setProgress(progress3 + 50);
                            if (LRReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters3 = LRReverseActivity.this.mCamera.getParameters();
                                    parameters3.setContrast(String.format(Locale.US, "%d", Integer.valueOf(progress3 + 128)));
                                    LRReverseActivity.this.mCamera.setParameters(parameters3);
                                } catch (Exception e3) {
                                }
                            }
                            break;
                        case R.id.contrast_up /* 2131034152 */:
                            int progress10 = (((SeekBar) CameraView.this.mView.findViewById(R.id.contrast_level)).getProgress() - 50) + 1;
                            if (progress10 <= 50) {
                                progress4 = progress10;
                            } else {
                                progress4 = 50;
                            }
                            ((TextView) CameraView.this.mView.findViewById(R.id.contrast_title)).setText(LRReverseActivity.this.getString(R.string.contrast) + "(" + progress4 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.contrast_level)).setProgress(progress4 + 50);
                            if (LRReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters4 = LRReverseActivity.this.mCamera.getParameters();
                                    parameters4.setContrast(String.format(Locale.US, "%d", Integer.valueOf(progress4 + 128)));
                                    LRReverseActivity.this.mCamera.setParameters(parameters4);
                                } catch (Exception e4) {
                                }
                            }
                            break;
                        case R.id.lrreverse_mirror_left_right /* 2131034175 */:
                            CameraView.this.sendLrReverseMirrorLeftRight();
                            break;
                        case R.id.lrreverse_mirror_top_bottom /* 2131034176 */:
                            CameraView.this.sendLrReverseMirrorTopBottom();
                            break;
                        case R.id.saturation_down /* 2131034216 */:
                            int progress11 = (((SeekBar) CameraView.this.mView.findViewById(R.id.saturation_level)).getProgress() - 50) - 1;
                            if (progress11 >= -50) {
                                progress5 = progress11;
                            } else {
                                progress5 = -50;
                            }
                            ((TextView) CameraView.this.mView.findViewById(R.id.saturation_title)).setText(LRReverseActivity.this.getString(R.string.saturation) + "(" + progress5 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.saturation_level)).setProgress(progress5 + 50);
                            if (LRReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters5 = LRReverseActivity.this.mCamera.getParameters();
                                    parameters5.setSaturation(String.format(Locale.US, "%d", Integer.valueOf(progress5 + 128)));
                                    LRReverseActivity.this.mCamera.setParameters(parameters5);
                                } catch (Exception e5) {
                                }
                            }
                            break;
                        case R.id.saturation_up /* 2131034219 */:
                            int progress12 = (((SeekBar) CameraView.this.mView.findViewById(R.id.saturation_level)).getProgress() - 50) + 1;
                            if (progress12 <= 50) {
                                progress6 = progress12;
                            } else {
                                progress6 = 50;
                            }
                            ((TextView) CameraView.this.mView.findViewById(R.id.saturation_title)).setText(LRReverseActivity.this.getString(R.string.saturation) + "(" + progress6 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.saturation_level)).setProgress(progress6 + 50);
                            if (LRReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters6 = LRReverseActivity.this.mCamera.getParameters();
                                    parameters6.setSaturation(String.format(Locale.US, "%d", Integer.valueOf(progress6 + 128)));
                                    LRReverseActivity.this.mCamera.setParameters(parameters6);
                                } catch (Exception e6) {
                                }
                            }
                            break;
                        case R.id.tv_camera_reset /* 2131034228 */:
                            CameraView.this.reset();
                            break;
                        case R.id.tv_layout_camera /* 2131034231 */:
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_frame)).setTextColor(-7829368);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_camera)).setTextColor(-1);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(0);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(1);
                            CameraView.this.mView.findViewById(R.id.layout_frame).setVisibility(8);
                            CameraView.this.mView.findViewById(R.id.layout_camera).setVisibility(0);
                            break;
                        case R.id.tv_layout_frame /* 2131034232 */:
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_frame)).setTextColor(-1);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_camera)).setTextColor(-7829368);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(1);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(0);
                            CameraView.this.mView.findViewById(R.id.layout_frame).setVisibility(0);
                            CameraView.this.mView.findViewById(R.id.layout_camera).setVisibility(8);
                            break;
                    }
                    LRReverseActivity.this.mHandler.removeMessages(LRReverseActivity.CAMERA_HIDE);
                    LRReverseActivity.this.mHandler.sendEmptyMessageDelayed(LRReverseActivity.CAMERA_HIDE, 5000L);
                }
            };
            this.mView.findViewById(R.id.brightness_down).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.brightness_up).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.contrast_down).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.contrast_up).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.saturation_down).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.saturation_up).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.tv_camera_reset).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.tv_layout_frame).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.tv_layout_camera).setOnClickListener(onClickListener);
            this.mLrReverseMirrorLeftRight.setOnClickListener(onClickListener);
            this.mLrReverseMirrorTopBottom.setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.ll_title).setVisibility(LRReverseActivity.this.isCameraViewTitleShow() ? 0 : 8);
            int viewH = LRReverseActivity.this.isCameraViewTitleShow() ? 400 : 350;
            this.mWindowManager = (WindowManager) LRReverseActivity.this.getSystemService("window");
            this.mLayoutParams = new WindowManager.LayoutParams((int) (LRReverseActivity.this.density * 550.0f), (int) (viewH * LRReverseActivity.this.density), 0, 0, 2038, 40, 1);
        }

        public void show() {
            if (!this.isShow) {
                int brightness = SystemProperties.getInt("persist.camera.brightness2", 128) - 128;
                ((TextView) this.mView.findViewById(R.id.brightness_title)).setText(LRReverseActivity.this.getString(R.string.brightness) + "(" + brightness + ")");
                ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setProgress(brightness + 50);
                int contrast = SystemProperties.getInt("persist.camera.contrast2", 128) - 128;
                ((TextView) this.mView.findViewById(R.id.contrast_title)).setText(LRReverseActivity.this.getString(R.string.contrast) + "(" + contrast + ")");
                ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setProgress(contrast + 50);
                int saturation = SystemProperties.getInt("persist.camera.saturation2", 128) - 128;
                ((TextView) this.mView.findViewById(R.id.saturation_title)).setText(LRReverseActivity.this.getString(R.string.saturation) + "(" + saturation + ")");
                ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setProgress(saturation + 50);
                ((TextView) this.mView.findViewById(R.id.tv_layout_frame)).setTextColor(-1);
                ((TextView) this.mView.findViewById(R.id.tv_layout_camera)).setTextColor(-7829368);
                ((TextView) this.mView.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(1);
                ((TextView) this.mView.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(0);
                this.mView.findViewById(R.id.layout_frame).setVisibility(0);
                this.mView.findViewById(R.id.layout_camera).setVisibility(8);
                showLrReverseMirrorLeftRight();
                showLrReverseMirrorTopBottom();
                this.isShow = true;
                this.mWindowManager.addView(this.mView, this.mLayoutParams);
                LRReverseActivity.this.mHandler.removeMessages(LRReverseActivity.CAMERA_HIDE);
                LRReverseActivity.this.mHandler.sendEmptyMessageDelayed(LRReverseActivity.CAMERA_HIDE, 5000L);
            }
        }

        public void hide() {
            if (this.isShow) {
                int brightness = (((SeekBar) this.mView.findViewById(R.id.brightness_level)).getProgress() - 50) + 128;
                int contrast = (((SeekBar) this.mView.findViewById(R.id.contrast_level)).getProgress() - 50) + 128;
                int saturation = (((SeekBar) this.mView.findViewById(R.id.saturation_level)).getProgress() - 50) + 128;
                SystemProperties.set("persist.camera.brightness2", String.format(Locale.US, "%d", Integer.valueOf(brightness)));
                SystemProperties.set("persist.camera.contrast2", String.format(Locale.US, "%d", Integer.valueOf(contrast)));
                SystemProperties.set("persist.camera.saturation2", String.format(Locale.US, "%d", Integer.valueOf(saturation)));
                this.mWindowManager.removeView(this.mView);
                this.isShow = false;
            }
        }

        public void reset() {
            if (this.isShow) {
                try {
                    Camera.Parameters parameters = LRReverseActivity.this.mCamera.getParameters();
                    parameters.setBrightness(String.format(Locale.US, "%d", 128));
                    LRReverseActivity.this.mCamera.setParameters(parameters);
                    parameters.setContrast(String.format(Locale.US, "%d", 128));
                    LRReverseActivity.this.mCamera.setParameters(parameters);
                    parameters.setSaturation(String.format(Locale.US, "%d", 128));
                    LRReverseActivity.this.mCamera.setParameters(parameters);
                } catch (Exception e) {
                }
                SystemProperties.set("persist.camera.brightness2", String.format(Locale.US, "%d", 128));
                SystemProperties.set("persist.camera.contrast2", String.format(Locale.US, "%d", 128));
                SystemProperties.set("persist.camera.saturation2", String.format(Locale.US, "%d", 128));
                ((TextView) this.mView.findViewById(R.id.brightness_title)).setText(LRReverseActivity.this.getString(R.string.brightness) + "(0)");
                ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setProgress(50);
                ((TextView) this.mView.findViewById(R.id.contrast_title)).setText(LRReverseActivity.this.getString(R.string.contrast) + "(0)");
                ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setProgress(50);
                ((TextView) this.mView.findViewById(R.id.saturation_title)).setText(LRReverseActivity.this.getString(R.string.saturation) + "(0)");
                ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setProgress(50);
            }
        }

        public void showLrReverseMirrorLeftRight() {
            int value;
            if (LRReverseActivity.this.isTV) {
                value = SystemProperties.getInt("persist.camera.mirror1", 0);
            } else {
                value = SystemProperties.getInt("persist.camera.mirror2", 0);
            }
            this.mLrReverseMirrorLeftRight.setImageLevel(value);
        }

        public void sendLrReverseMirrorLeftRight() {
            ImageView imageView = this.mLrReverseMirrorLeftRight;
            if (imageView == null) {
                return;
            }
            if (imageView.getDrawable().getLevel() == 0) {
                if (LRReverseActivity.this.isTV) {
                    SystemProperties.set("persist.camera.mirror1", "1");
                } else {
                    SystemProperties.set("persist.camera.mirror2", "1");
                }
                this.mLrReverseMirrorLeftRight.setImageLevel(1);
            } else {
                if (LRReverseActivity.this.isTV) {
                    SystemProperties.set("persist.camera.mirror1", "0");
                } else {
                    SystemProperties.set("persist.camera.mirror2", "0");
                }
                this.mLrReverseMirrorLeftRight.setImageLevel(0);
            }
            LRReverseActivity.this.mHandler.removeMessages(LRReverseActivity.SIGNAL);
            LRReverseActivity.this.setPreview(false);
            LRReverseActivity.this.warning_layout.setVisibility(8);
            LRReverseActivity.this.closeCamera();
            Log.d(LRReverseActivity.TAG, "sendLrReverseMirrorLeftRight->openCamera");
            LRReverseActivity.this.openCamera();
        }

        public void showLrReverseMirrorTopBottom() {
            int m_V = SystemProperties.getInt("persist.camera.lr.angle", 90);
            this.mLrReverseMirrorTopBottom.setImageLevel(m_V == 270 ? 1 : 0);
        }

        public void sendLrReverseMirrorTopBottom() {
            ImageView imageView = this.mLrReverseMirrorTopBottom;
            if (imageView == null) {
                return;
            }
            if (imageView.getDrawable().getLevel() == 0) {
                SystemProperties.set("persist.camera.lr.angle", "270");
                this.mLrReverseMirrorTopBottom.setImageLevel(1);
            } else {
                SystemProperties.set("persist.camera.lr.angle", "90");
                this.mLrReverseMirrorTopBottom.setImageLevel(0);
            }
            LRReverseActivity.this.mHandler.removeMessages(LRReverseActivity.SIGNAL);
            LRReverseActivity.this.setPreview(false);
            LRReverseActivity.this.warning_layout.setVisibility(8);
            LRReverseActivity.this.closeCamera();
            Log.d(LRReverseActivity.TAG, "sendLrReverseMirrorTopBottom->openCamera");
            LRReverseActivity.this.openCamera();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mTWUtil.stop();
        this.mTWUtil.close();
        this.mTWUtil = null;
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        if (this.mTWUtil.write(65521) == 544 && !getSystemVersion().contains("-360")) {
            this.mTWUtil.write(524, 0);
        }
        setPreview(false);
        Camera camera = this.mCamera;
        if (camera != null) {
            try {
                camera.stopPreview();
            } catch (Exception e) {
            }
            this.mCamera.release();
            this.mCamera = null;
        }
        requestSource(false);
        this.mHandler.removeMessages(EXIT);
        this.mTWUtil.removeHandler(TAG);
        this.mHandler.removeMessages(CAMERA_HIDE);
        this.mHandler.sendEmptyMessage(CAMERA_HIDE);
        this.mHandler.removeMessages(SIGNAL);
        this.warning_layout.setVisibility(8);
        requestService(ACTIVITY_PAUSE);
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        setStateBarNavigationHide();
        requestService(7);
        this.mTWUtil.addHandler(TAG, this.mHandler);
        requestBrake();
        int exitDelay = Settings.System.getInt(getContentResolver(), "SYSTEM_FOWARD_SWITCH", 0);
        if (exitDelay != 0) {
            if (exitDelay == 1) {
                this.mHandler.sendEmptyMessageDelayed(EXIT, 10000L);
            } else if (exitDelay == 2) {
                this.mHandler.sendEmptyMessageDelayed(EXIT, 15000L);
            } else if (exitDelay == 3) {
                this.mHandler.sendEmptyMessageDelayed(EXIT, 20000L);
            }
        }
        int channel = Settings.System.getInt(getContentResolver(), "SYSTEM_FOWARD_CHANNEL", 0);
        this.isTV = channel == 1;
        requestSource(true);
        openCamera();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openCamera() {
        try {
            SystemProperties.set("sys.camera.ch", this.isTV ? "1" : "2");
            this.mCamera = Camera.open(this.cameraId);
            int brightness = SystemProperties.getInt(this.isTV ? "persist.camera.brightness1" : "persist.camera.brightness2", 128);
            int contrast = SystemProperties.getInt(this.isTV ? "persist.camera.contrast1" : "persist.camera.contrast2", 128);
            int saturation = SystemProperties.getInt(this.isTV ? "persist.camera.saturation1" : "persist.camera.saturation2", 128);
            try {
                Camera.Parameters parameters = this.mCamera.getParameters();
                parameters.setBrightness(String.format(Locale.US, "%d", Integer.valueOf(brightness)));
                this.mCamera.setParameters(parameters);
                parameters.setContrast(String.format(Locale.US, "%d", Integer.valueOf(contrast)));
                this.mCamera.setParameters(parameters);
                parameters.setSaturation(String.format(Locale.US, "%d", Integer.valueOf(saturation)));
                this.mCamera.setParameters(parameters);
            } catch (Exception e) {
            }
        } catch (Exception e2) {
        }
        this.mHandler.sendEmptyMessageDelayed(START_PREVIEW, Settings.System.getInt(getContentResolver(), "REVERSE_DELAY", 0));
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1 || actionMasked == 2) {
            }
        } else if (this.mHB.getVisibility() != 0) {
            this.mHB.setVisibility(0);
            this.mHandler.removeMessages(GONE);
            this.mHandler.sendEmptyMessageDelayed(GONE, 2000L);
        }
        return super.onTouchEvent(event);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        } else if (id == R.id.camera_settings) {
            if (this.mCameraView.isShow) {
                this.mCameraView.hide();
            } else {
                this.mCameraView.show();
            }
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int _w, int _h) {
        this.w = _w;
        this.h = _h;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (this.mCamera != null) {
                this.mCamera.setPreviewDisplay(holder);
            }
        } catch (Exception e) {
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.stopPreview();
        }
    }

    private int getSysClassNode(String name) {
        int v = 0;
        BufferedReader br = null;
        try {
            try {
                br = new BufferedReader(new FileReader(name));
                String s = br.readLine();
                v = Integer.parseInt(s);
            } catch (Exception e) {
                if (br != null) {
                }
                return v;
            } catch (Throwable th) {
                if (br != null) {
                    br.close();
                }
                throw th;
            }
            br.close();
        } catch (Exception e2) {
        }
        return v;
    }

    private void setSysClassNode(String name, String data) {
        BufferedWriter bw = null;
        try {
            try {
                bw = new BufferedWriter(new FileWriter(name));
                bw.write(data);
                bw.flush();
            } catch (Exception e) {
            } finally {
                if (bw != null) {
                    bw.close();
                }
            }
        } catch (Exception e2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSignal() {
        TWUtil tWUtil = this.mTWUtil;
        if (tWUtil == null) {
            return 0;
        }
        int signal = tWUtil.write(40733);
        return signal;
    }

    public void requestSource(boolean is) {
        if (is) {
            this.mTWUtil.write(REQUEST_SOURCE2, 192, 6);
        } else {
            this.mTWUtil.write(REQUEST_SOURCE, 192, 6);
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0047 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:29:0x0048 A[RETURN] */
    public static String getSystemVersion() {
        String systemVersion = "";
        try {
            String v1 = SystemProperties.get("ro.tw.version");
            if (v1 != null && v1.length() > 0) {
                systemVersion = v1;
            }
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("/system/etc/version"));
                String v2 = br.readLine();
                if (v2 != null && v2.length() > 0) {
                    systemVersion = v2;
                }
            } catch (Exception e) {
                if (br != null) {
                }
                if (TextUtils.isEmpty(systemVersion)) {
                    return systemVersion;
                }
                return systemVersion;
            } catch (Throwable th) {
                if (br != null) {
                    br.close();
                }
                throw th;
            }
            br.close();
        } catch (Exception e2) {
        }
        if (TextUtils.isEmpty(systemVersion)) {
            return systemVersion;
        }
        return systemVersion;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeCamera() {
        if (this.mCamera != null) {
            try {
                this.mHandler.removeMessages(START_PREVIEW);
                this.mCamera.stopPreview();
            } catch (Exception e) {
            }
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCameraViewTitleShow() {
        if (getSystemVersion().contains("-XRC1")) {
            return true;
        }
        return false;
    }

    private void setStateBarNavigationHide() {
        getWindow().setFlags(1024, 1024);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(5126);
    }
}
