package com.tw.reverse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class ReverseActivity extends Activity implements SurfaceHolder.Callback, View.OnTouchListener {
    private static final int CAMERA_HIDE = 65294;
    private static final int CAR_BRIGHTNESS = 65281;
    private static final int CAR_CAMERA_HIDE = 65284;
    private static final int CAR_CONTRAST = 65282;
    private static final int CAR_SATURATION = 65283;
    private static final int CLOSE = 65292;
    private static final int OPEN = 65293;
    private static final int REOPEN_CAMERA = 65297;
    public static final int REQUEST_REVERSE = 772;
    public static final int REQUEST_REVERSE2 = 40468;
    private static final int REVERSE_CHECK = 65280;
    private static final int SHOWTIP = 65291;
    private static final int SIGNAL = 65295;
    private static final int START_PREVIEW = 65296;
    private static final String TAG = "ReverseActivity";
    private Button btnAdjust;
    private Button btnReset;
    private LinearLayout context_reverse_view_mode4;
    private ImageView dynamic_radar_centerleft;
    private ImageView dynamic_radar_centerright;
    private ImageView dynamic_radar_left;
    private ImageView dynamic_radar_right;
    private RelativeLayout dynamic_view;
    private int heightPixels;
    private int imageDebounce;
    private int iskeypad;
    private LinearLayout mBReverse;
    private LinearLayout mBReverse2;
    private LinearLayout mBReverse3;
    private LinearLayout mBReverse4;
    private LinearLayout mBReverse5;
    private CameraView mCameraView;
    private ImageView mCamera_settings;
    private CarCameraView mCarCameraView;
    private View mCentre;
    private TextView mFReverseD;
    private RelativeLayout mInnerLayer;
    private int mLeftMargin;
    private RelativeLayout mOuterLayer;
    private RelativeLayout mRReverse;
    private TextView mRReverseD;
    private RadarView mRV;
    private RelativeLayout mReverse;
    private ShowAHDTip mShowAHDTip;
    private int mUpperSpacing;
    private ImageView mWarningImage;
    private TextView mWarningText;
    private LinearLayout mlayout_1;
    private RelativeLayout.LayoutParams mlp_radar_view;
    private RelativeLayout.LayoutParams mlp_revese_view;
    private ReverseLineRuler reverseLineRuler;
    private ImageView reverse_view_mode1;
    private ImageView reverse_view_mode2;
    private ImageView reverse_view_mode3;
    private ImageView reverse_view_mode4;
    private int reversingRulerState;
    private RelativeLayout rl_reverse;
    private RelativeLayout rl_reverse2;
    private TextView tv_reverse_view_mode;
    private int widthPixels;
    private static boolean isSignal = false;
    private static Drawable mDrawable = null;
    public static boolean isFRadar = false;
    private TWTW mTW = null;
    private Camera mCamera = null;
    private SurfaceView mSurfaceView = null;
    private int mCount = 0;
    private int w = 1024;
    private int h = 600;
    private boolean mPreview = false;
    private int cameraId = 0;
    private boolean mMCURadar = false;
    private boolean mMCUTrack = false;
    private int mCarCameraBringtness = 20;
    private int mCarCameraContrast = 20;
    private int mCarCameraSaturation = 20;
    private int mRadarVoice = -1;
    private int mRadar = -1;
    private int mRadarSet = -1;
    private int mSW = 24;
    private boolean isInvalid = false;
    private boolean noRMsg = false;
    private float density = 1.0f;
    private boolean noAHD = false;
    private Handler mHandler = new Handler() { // from class: com.tw.reverse.ReverseActivity.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                int i = message.what;
                if (i != 266) {
                    int i2 = 1;
                    boolean z = true;
                    if (i != 523) {
                        if (i == 1281) {
                            byte[] bArr = (byte[]) message.obj;
                            if (TWTW.mCType == 1) {
                                if (TWTW.mModel != 33 && TWTW.mModel != 34 && TWTW.mModel != 35 && TWTW.mModel != 38 && TWTW.mModel != 39) {
                                    if (TWTW.mModel == 244) {
                                        if (message.arg2 == 48) {
                                            ReverseActivity.this.setSW1((bArr[1] << 8) | (bArr[0] & 255));
                                        }
                                        return;
                                    }
                                    if (TWTW.mModel == 22) {
                                        int i3 = message.arg1 & 255;
                                        if (i3 == 64) {
                                            ReverseActivity.this.mMode = bArr[4] & 255;
                                            ReverseActivity.this.reverse4modeRefresh(ReverseActivity.this.mMode);
                                        } else if (i3 == 176) {
                                            ReverseActivity.this.mCarCameraBringtness = (bArr[2] & 255) - 30;
                                            ReverseActivity.this.mCarCameraContrast = (bArr[3] & 255) - 30;
                                            ReverseActivity.this.mCarCameraSaturation = (bArr[4] & 255) - 30;
                                        }
                                        return;
                                    }
                                    if (TWTW.mModel != -1) {
                                        if (message.arg2 == 50 && bArr[0] == 0) {
                                            if (ReverseActivity.this.mRadar == -1) {
                                                ReverseActivity.this.mRadar = 1;
                                                ReverseActivity.this.mReverse.setVisibility(0);
                                                if (!ReverseActivity.isFRadar) {
                                                    ReverseActivity.this.mRReverse.setVisibility(0);
                                                }
                                            }
                                            ReverseActivity.this.mRV.setRearRadar(ReverseActivity.this.toV1(bArr[2], bArr[1]), ReverseActivity.this.toV1(bArr[3], bArr[1]), ReverseActivity.this.toV1(bArr[4], bArr[1]), ReverseActivity.this.toV1(bArr[5], bArr[1]));
                                            ReverseActivity.this.showDanamicRadar(ReverseActivity.this.toV1(bArr[2], bArr[1]), ReverseActivity.this.toV1(bArr[3], bArr[1]), ReverseActivity.this.toV1(bArr[4], bArr[1]), ReverseActivity.this.toV1(bArr[5], bArr[1]));
                                            ReverseActivity.this.mRV.invalidate();
                                        }
                                        return;
                                    }
                                    return;
                                }
                                int i4 = message.arg1;
                                if (i4 == 34 || i4 == 35) {
                                    if (ReverseActivity.this.mRadar == -1) {
                                        ReverseActivity.this.mRadar = 1;
                                        ReverseActivity.this.mReverse.setVisibility(0);
                                        if (!ReverseActivity.isFRadar) {
                                            ReverseActivity.this.mRReverse.setVisibility(0);
                                        }
                                    }
                                    int i5 = message.arg1;
                                    if (i5 == 34) {
                                        ReverseActivity.this.mRV.setRearRadar(ReverseActivity.this.toV22(bArr[0]), ReverseActivity.this.toV22(bArr[1]), ReverseActivity.this.toV22(bArr[2]), ReverseActivity.this.toV22(bArr[3]));
                                        ReverseActivity.this.showDanamicRadar(ReverseActivity.this.toV22(bArr[0]), ReverseActivity.this.toV22(bArr[1]), ReverseActivity.this.toV22(bArr[2]), ReverseActivity.this.toV22(bArr[3]));
                                    } else if (i5 == 35) {
                                        ReverseActivity.this.mRV.setFrontRadar(ReverseActivity.this.toV22(bArr[0]), ReverseActivity.this.toV22(bArr[1]), ReverseActivity.this.toV22(bArr[2]), ReverseActivity.this.toV22(bArr[3]));
                                    }
                                    ReverseActivity.this.mRV.invalidate();
                                } else if (i4 == 41) {
                                    ReverseActivity.this.setSW5((bArr[1] << 8) | (bArr[0] & 255));
                                }
                                return;
                            }
                            if (TWTW.mCType == 2) {
                                if (TWTW.mModel == 181) {
                                    int i6 = message.arg1;
                                    if (i6 == 28) {
                                        if (ReverseActivity.this.mRadar == -1) {
                                            ReverseActivity.this.mRadar = 1;
                                            ReverseActivity.this.mReverse.setVisibility(0);
                                            if (!ReverseActivity.isFRadar) {
                                                ReverseActivity.this.mRReverse.setVisibility(0);
                                            }
                                        }
                                        ReverseActivity.this.mRV.setRearRadar(bArr[0], bArr[1], bArr[2], bArr[3]);
                                        ReverseActivity.this.showDanamicRadar(bArr[0], bArr[1], bArr[2], bArr[3]);
                                        ReverseActivity.this.mRV.invalidate();
                                    } else if (i6 == 36) {
                                        int i7 = bArr[0] & 127;
                                        if ((bArr[0] & 128) == 128) {
                                            i7 = -i7;
                                        }
                                        ReverseActivity.this.setSW6(i7);
                                    }
                                    return;
                                }
                                if (TWTW.mModel != -1) {
                                    int i8 = message.arg1;
                                    if ((i8 == 50 || i8 == 66) && bArr[0] == 2) {
                                        if (ReverseActivity.this.mRadar == -1) {
                                            ReverseActivity.this.mRadar = 1;
                                            ReverseActivity.this.mReverse.setVisibility(0);
                                            if (!ReverseActivity.isFRadar) {
                                                ReverseActivity.this.mRReverse.setVisibility(0);
                                            }
                                        }
                                        int i9 = message.arg1;
                                        if (i9 == 50) {
                                            ReverseActivity.this.mRV.setRearRadar(ReverseActivity.this.toV2(bArr[1]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[3]));
                                            ReverseActivity.this.showDanamicRadar(ReverseActivity.this.toV2(bArr[1]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[3]));
                                        } else if (i9 == 66) {
                                            ReverseActivity.this.mRV.setFrontRadar(ReverseActivity.this.toV2(bArr[1]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[3]));
                                        }
                                        ReverseActivity.this.mRV.invalidate();
                                    }
                                    return;
                                }
                                return;
                            }
                            if (TWTW.mModel != 33 && TWTW.mModel != 34 && TWTW.mModel != 35 && TWTW.mModel != 38 && TWTW.mModel != 39) {
                                if (TWTW.mModel == 48) {
                                    int i10 = message.arg1;
                                    if (i10 != 30) {
                                        if (i10 == 41) {
                                            if ((bArr[1] & 8) == 8) {
                                                bArr[1] = (byte) (bArr[1] | 240);
                                            }
                                            ReverseActivity.this.setSW7((bArr[1] << 8) | (bArr[0] & 255));
                                        }
                                    } else if ((bArr[4] & 128) == 128) {
                                        if (ReverseActivity.this.mRadar == -1) {
                                            ReverseActivity.this.mRadar = 1;
                                            ReverseActivity.this.mReverse.setVisibility(0);
                                            if (!ReverseActivity.isFRadar) {
                                                ReverseActivity.this.mRReverse.setVisibility(0);
                                            }
                                        }
                                        ReverseActivity.this.mRV.setRearRadar(bArr[0], bArr[1], bArr[2], bArr[3]);
                                        ReverseActivity.this.showDanamicRadar(bArr[0], bArr[1], bArr[2], bArr[3]);
                                        ReverseActivity.this.mRV.invalidate();
                                    }
                                    return;
                                }
                                if (TWTW.mModel != 150 && TWTW.mModel != 153) {
                                    if (TWTW.mModel != -1) {
                                        int i11 = message.arg1;
                                        if (i11 == 34 || i11 == 35) {
                                            if (ReverseActivity.this.mRadar == -1) {
                                                ReverseActivity.this.mRadar = 1;
                                                ReverseActivity.this.mReverse.setVisibility(0);
                                                if (!ReverseActivity.isFRadar) {
                                                    ReverseActivity.this.mRReverse.setVisibility(0);
                                                }
                                                if (TWTW.mModel == 16 || TWTW.mModel == 17 || TWTW.mModel == 18 || TWTW.mModel == 19) {
                                                    ReverseActivity.this.mBReverse.setVisibility(0);
                                                    if (ReverseActivity.this.mRadarVoice == -1) {
                                                        ReverseActivity.this.mTW.ssWrite(144, 37);
                                                    } else {
                                                        ((ImageView) ReverseActivity.this.findViewById(R.id.reverse_voice)).getDrawable().setLevel(ReverseActivity.this.mRadarVoice);
                                                    }
                                                } else if (TWTW.mModel == 22) {
                                                    ReverseActivity.this.mBReverse4.setVisibility(4);
                                                    if (ReverseActivity.this.mRadarVoice == -1) {
                                                        ReverseActivity.this.mTW.ssWrite(144, 37);
                                                    } else {
                                                        ((ImageView) ReverseActivity.this.findViewById(R.id.reverse_voice)).getDrawable().setLevel(ReverseActivity.this.mRadarVoice);
                                                    }
                                                }
                                                ReverseActivity.this.mTW.write(33281, 1, 43);
                                            }
                                            int i12 = message.arg1;
                                            if (i12 != 34) {
                                                if (i12 == 35) {
                                                    if (TWTW.mModel == 17 || TWTW.mModel == 22) {
                                                        ReverseActivity.this.mRV.setFrontRadar(ReverseActivity.this.toV11(bArr[0] & 255), ReverseActivity.this.toV11(bArr[1] & 255), ReverseActivity.this.toV11(bArr[2] & 255), ReverseActivity.this.toV11(bArr[3] & 255));
                                                    } else {
                                                        ReverseActivity.this.mRV.setFrontRadar(bArr[0], bArr[1], bArr[2], bArr[3]);
                                                    }
                                                }
                                            } else if (TWTW.mModel == 17 || TWTW.mModel == 22) {
                                                ReverseActivity.this.mRV.setRearRadar(ReverseActivity.this.toV11(bArr[0] & 255), ReverseActivity.this.toV11(bArr[1] & 255), ReverseActivity.this.toV11(bArr[2] & 255), ReverseActivity.this.toV11(bArr[3] & 255));
                                                ReverseActivity.this.showDanamicRadar(ReverseActivity.this.toV11(bArr[0] & 255), ReverseActivity.this.toV11(bArr[1] & 255), ReverseActivity.this.toV11(bArr[2] & 255), ReverseActivity.this.toV11(bArr[3] & 255));
                                            } else {
                                                ReverseActivity.this.mRV.setRearRadar(bArr[0], bArr[1], bArr[2], bArr[3]);
                                                ReverseActivity.this.showDanamicRadar(bArr[0], bArr[1], bArr[2], bArr[3]);
                                            }
                                            ReverseActivity.this.mRV.invalidate();
                                        } else if (i11 != 37) {
                                            if (i11 != 38) {
                                                if (i11 != 41) {
                                                    if (i11 == 64) {
                                                        if (TWTW.mModel == 22 && (bArr[0] & 255) == 208) {
                                                            ReverseActivity.this.mMode = bArr[1] & 255;
                                                            ReverseActivity.this.mCarCameraBringtness = (bArr[2] & 255) - 30;
                                                            ReverseActivity.this.mCarCameraContrast = (bArr[3] & 255) - 30;
                                                            ReverseActivity.this.mCarCameraSaturation = (bArr[4] & 255) - 30;
                                                            ReverseActivity.this.reverse4modeRefresh(ReverseActivity.this.mMode);
                                                        }
                                                    } else if (i11 == 232 && TWTW.mCType == 4 && (TWTW.mModel == 22 || TWTW.mModel == 17)) {
                                                        ReverseActivity.this.reverse5modeRefresh(bArr[1] & 255);
                                                        ReverseActivity.this.mCarCameraBringtness = (bArr[2] & 255) - 30;
                                                        ReverseActivity.this.mCarCameraContrast = (bArr[3] & 255) - 30;
                                                        ReverseActivity.this.mCarCameraSaturation = (bArr[4] & 255) - 30;
                                                    }
                                                } else if (TWTW.mModel == 17 || TWTW.mModel == 22) {
                                                    ReverseActivity.this.setSW2((bArr[1] << 8) | (bArr[0] & 255));
                                                }
                                            } else if (TWTW.mModel == 16 || TWTW.mModel == 18) {
                                                ReverseActivity.this.setSW3((bArr[1] << 8) | (bArr[0] & 255));
                                            } else if (TWTW.mModel == 19) {
                                                ReverseActivity.this.setSW4((bArr[1] << 8) | (bArr[0] & 255));
                                            } else if (TWTW.mModel == 128 || TWTW.mModel == 134) {
                                                ReverseActivity.this.setSW8((bArr[1] << 8) | (bArr[0] & 255));
                                            }
                                        } else if ((TWTW.mModel & 240) == 16) {
                                            ReverseActivity reverseActivity = ReverseActivity.this;
                                            if ((bArr[0] & 1) != 1) {
                                                i2 = 0;
                                            }
                                            reverseActivity.mRadarVoice = i2;
                                            ((ImageView) ReverseActivity.this.findViewById(R.id.reverse_voice)).getDrawable().setLevel(ReverseActivity.this.mRadarVoice);
                                            if ((bArr[0] & 2) == 0) {
                                                ReverseActivity.this.mTW.write(ReverseActivity.REQUEST_REVERSE2, 0);
                                            }
                                        }
                                        return;
                                    }
                                    return;
                                }
                                if (message.arg1 == 50 && bArr[0] == 2) {
                                    if (ReverseActivity.this.mRadar == -1) {
                                        ReverseActivity.this.mRadar = 1;
                                        ReverseActivity.this.mReverse.setVisibility(0);
                                        if (!ReverseActivity.isFRadar) {
                                            ReverseActivity.this.mRReverse.setVisibility(0);
                                        }
                                    }
                                    ReverseActivity.this.mRV.setRearRadar(ReverseActivity.this.toV2(bArr[1]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[3]));
                                    ReverseActivity.this.showDanamicRadar(ReverseActivity.this.toV2(bArr[1]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[2]), ReverseActivity.this.toV2(bArr[3]));
                                    ReverseActivity.this.mRV.setFrontRadar(ReverseActivity.this.toV2(bArr[4]), ReverseActivity.this.toV2(bArr[5]), ReverseActivity.this.toV2(bArr[5]), ReverseActivity.this.toV2(bArr[6]));
                                    ReverseActivity.this.mRV.invalidate();
                                }
                                return;
                            }
                            int i13 = message.arg1;
                            if (i13 == 34 || i13 == 35) {
                                if (ReverseActivity.this.mRadar == -1) {
                                    ReverseActivity.this.mRadar = 1;
                                    ReverseActivity.this.mReverse.setVisibility(0);
                                    if (!ReverseActivity.isFRadar) {
                                        ReverseActivity.this.mRReverse.setVisibility(0);
                                    }
                                }
                                int i14 = message.arg1;
                                if (i14 == 34) {
                                    ReverseActivity.this.mRV.setRearRadar(ReverseActivity.this.toV22(bArr[0]), ReverseActivity.this.toV22(bArr[1]), ReverseActivity.this.toV22(bArr[2]), ReverseActivity.this.toV22(bArr[3]));
                                    ReverseActivity.this.showDanamicRadar(ReverseActivity.this.toV22(bArr[0]), ReverseActivity.this.toV22(bArr[1]), ReverseActivity.this.toV22(bArr[2]), ReverseActivity.this.toV22(bArr[3]));
                                } else if (i14 == 35) {
                                    ReverseActivity.this.mRV.setFrontRadar(ReverseActivity.this.toV22(bArr[0]), ReverseActivity.this.toV22(bArr[1]), ReverseActivity.this.toV22(bArr[2]), ReverseActivity.this.toV22(bArr[3]));
                                }
                                ReverseActivity.this.mRV.invalidate();
                            } else if (i13 == 41) {
                                ReverseActivity.this.setSW5((bArr[1] << 8) | (bArr[0] & 255));
                            }
                            return;
                        }
                        if (i == 1294) {
                            byte[] bArr2 = (byte[]) message.obj;
                            if (ReverseActivity.this.mRadar == -1) {
                                ReverseActivity.this.mRadar = 1;
                                ReverseActivity.this.mReverse.setVisibility(0);
                                if (!ReverseActivity.isFRadar) {
                                    ReverseActivity.this.mRReverse.setVisibility(0);
                                }
                                ReverseActivity.this.mTW.write(33281, 1, 43);
                                if (TWTW.mModel == 17 || TWTW.mModel == 18 || TWTW.mModel == 19) {
                                    ReverseActivity.this.mBReverse.setVisibility(0);
                                }
                            }
                            if (!ReverseActivity.this.isEffective(new int[]{bArr2[0] & 255, bArr2[1] & 255, bArr2[2] & 255, bArr2[3] & 255}, new int[]{bArr2[5] & 255, bArr2[6] & 255, bArr2[7] & 255, bArr2[8] & 255}, bArr2[4] & 255, bArr2[9] & 255)) {
                                if (ReverseActivity.this.isInvalid) {
                                    ReverseActivity.this.isInvalid = false;
                                } else {
                                    return;
                                }
                            } else {
                                ReverseActivity.this.isInvalid = true;
                            }
                            ReverseActivity.this.mRV.setLSRadar(ReverseActivity.this.toV(bArr2[0], bArr2[4]), ReverseActivity.this.toV(bArr2[1], bArr2[4]), ReverseActivity.this.toV(bArr2[2], bArr2[4]), ReverseActivity.this.toV(bArr2[3], bArr2[4]));
                            ReverseActivity.this.mRV.setRSRadar(ReverseActivity.this.toV(bArr2[5], bArr2[9]), ReverseActivity.this.toV(bArr2[6], bArr2[9]), ReverseActivity.this.toV(bArr2[7], bArr2[9]), ReverseActivity.this.toV(bArr2[8], bArr2[9]));
                            int i15 = 0;
                            for (int i16 = 10; i16 < 14; i16++) {
                                try {
                                    int i17 = bArr2[i16] & 255;
                                    if (i17 != 0 && (i15 == 0 || i15 > i17)) {
                                        i15 = i17;
                                    }
                                } catch (Exception e) {
                                    Log.e(ReverseActivity.TAG, "0x050e:error:" + e.getMessage());
                                }
                            }
                            if (i15 == 0) {
                                ReverseActivity.this.mRReverseD.setVisibility(8);
                            } else {
                                if (i15 > (bArr2[14] & 255)) {
                                    ReverseActivity.this.mRReverseD.setText(i15 + ReverseActivity.this.getString(R.string.cm));
                                    ReverseActivity.this.mRReverseD.setTextColor(-1);
                                } else {
                                    ReverseActivity.this.mRReverseD.setText(R.string.stop);
                                    ReverseActivity.this.mRReverseD.setTextColor(-65536);
                                }
                                ReverseActivity.this.mRReverseD.setVisibility(0);
                            }
                            int i18 = 0;
                            for (int i19 = 15; i19 < 19; i19++) {
                                int i20 = bArr2[i19] & 255;
                                if (i20 != 0 && (i18 == 0 || i18 > i20)) {
                                    i18 = i20;
                                }
                            }
                            if (i18 == 0) {
                                ReverseActivity.this.mFReverseD.setVisibility(8);
                            } else {
                                if (i18 > (bArr2[19] & 255)) {
                                    ReverseActivity.this.mFReverseD.setText(i18 + ReverseActivity.this.getString(R.string.cm));
                                    ReverseActivity.this.mFReverseD.setTextColor(-1);
                                } else {
                                    ReverseActivity.this.mFReverseD.setText(R.string.stop);
                                    ReverseActivity.this.mFReverseD.setTextColor(-65536);
                                }
                                ReverseActivity.this.mFReverseD.setVisibility(0);
                            }
                            ReverseActivity.this.mRV.invalidate();
                            return;
                        }
                        if (i != 40732) {
                            if (i != 1286) {
                                if (i != 1287) {
                                    switch (i) {
                                        case 34054:
                                            break;
                                        case 34055:
                                            break;
                                        default:
                                            switch (i) {
                                                case ReverseActivity.REVERSE_CHECK /* 65280 */:
                                                    ReverseActivity.this.mTW.write(40464, 1);
                                                    ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.REVERSE_CHECK, 700L);
                                                    break;
                                                case ReverseActivity.CAR_BRIGHTNESS /* 65281 */:
                                                    if (TWTW.mCType == 4 && (TWTW.mModel == 22 || TWTW.mModel == 17)) {
                                                        ReverseActivity.this.mTW.ssWrite(242, 10, ReverseActivity.this.mCarCameraBringtness + 30);
                                                    } else if (TWTW.mModel == 22) {
                                                        ReverseActivity.this.mTW.ssWrite(198, 71, ReverseActivity.this.mCarCameraBringtness + 30);
                                                    }
                                                    break;
                                                case ReverseActivity.CAR_CONTRAST /* 65282 */:
                                                    if (TWTW.mCType == 4 && (TWTW.mModel == 22 || TWTW.mModel == 17)) {
                                                        ReverseActivity.this.mTW.ssWrite(242, 11, ReverseActivity.this.mCarCameraContrast + 30);
                                                    } else if (TWTW.mModel == 22) {
                                                        ReverseActivity.this.mTW.ssWrite(198, 72, ReverseActivity.this.mCarCameraContrast + 30);
                                                    }
                                                    break;
                                                case ReverseActivity.CAR_SATURATION /* 65283 */:
                                                    if (TWTW.mCType == 4 && (TWTW.mModel == 22 || TWTW.mModel == 17)) {
                                                        ReverseActivity.this.mTW.ssWrite(242, 12, ReverseActivity.this.mCarCameraSaturation + 30);
                                                    } else if (TWTW.mModel == 22) {
                                                        ReverseActivity.this.mTW.ssWrite(198, 73, ReverseActivity.this.mCarCameraSaturation + 30);
                                                    }
                                                    break;
                                                case ReverseActivity.CAR_CAMERA_HIDE /* 65284 */:
                                                    ReverseActivity.this.mCarCameraView.hide();
                                                    break;
                                                default:
                                                    switch (i) {
                                                        case ReverseActivity.SHOWTIP /* 65291 */:
                                                            ReverseActivity.this.showLayoutDetecting(false);
                                                            if (ReverseActivity.this.getSignal() < 3) {
                                                                ReverseActivity.this.mShowAHDTip.show(false);
                                                            }
                                                            break;
                                                        case ReverseActivity.CLOSE /* 65292 */:
                                                            Log.d(ReverseActivity.TAG, "closeCamera2");
                                                            ReverseActivity.this.mHandler.removeMessages(ReverseActivity.SIGNAL);
                                                            ReverseActivity.this.setPreview(false);
                                                            ReverseActivity.this.mWarningImage.setVisibility(8);
                                                            ReverseActivity.this.mWarningText.setVisibility(8);
                                                            ReverseActivity.this.closeCamera();
                                                            ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.OPEN, 300L);
                                                            break;
                                                        case ReverseActivity.OPEN /* 65293 */:
                                                            Log.d(ReverseActivity.TAG, "openCamera2");
                                                            ReverseActivity.this.openCamera();
                                                            break;
                                                        case ReverseActivity.CAMERA_HIDE /* 65294 */:
                                                            ReverseActivity.this.mCameraView.hide();
                                                            break;
                                                        case ReverseActivity.SIGNAL /* 65295 */:
                                                            removeMessages(ReverseActivity.SIGNAL);
                                                            ReverseActivity.access$1508(ReverseActivity.this);
                                                            int signal = ReverseActivity.this.getSignal();
                                                            if (signal == 0) {
                                                                if (!ReverseActivity.this.noRMsg && ReverseActivity.this.mCount > 10) {
                                                                    ReverseActivity reverseActivity2 = ReverseActivity.this;
                                                                    if (signal >= 2) {
                                                                        z = false;
                                                                    }
                                                                    reverseActivity2.showRMsg(z);
                                                                }
                                                                if (!ReverseActivity.isSignal && ReverseActivity.this.mCount > 20) {
                                                                    ReverseActivity.this.mWarningImage.setImageResource(R.drawable.warning_novideosignal);
                                                                    ReverseActivity.this.mWarningImage.setVisibility(0);
                                                                    ReverseActivity.this.mWarningText.setVisibility(0);
                                                                }
                                                            } else {
                                                                if (!ReverseActivity.this.noRMsg) {
                                                                    ReverseActivity.this.showRMsg(signal < 2);
                                                                }
                                                                ReverseActivity.this.mWarningImage.setImageDrawable(null);
                                                                ReverseActivity.this.mWarningImage.setVisibility(8);
                                                                ReverseActivity.this.mWarningText.setVisibility(8);
                                                                boolean unused = ReverseActivity.isSignal = true;
                                                            }
                                                            sendEmptyMessageDelayed(ReverseActivity.SIGNAL, 200L);
                                                            break;
                                                        case ReverseActivity.START_PREVIEW /* 65296 */:
                                                            ReverseActivity.this.startPreview();
                                                            break;
                                                        case ReverseActivity.REOPEN_CAMERA /* 65297 */:
                                                            ReverseActivity.this.openCamera();
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                    break;
                                            }
                                    }
                                }
                                if (message.what == 1287) {
                                    ReverseActivity.this.mMCURadar = true;
                                }
                                if (!ReverseActivity.this.mMCURadar || message.what != 34055) {
                                    byte[] bArr3 = (byte[]) message.obj;
                                    if (ReverseActivity.this.mRadar == -1) {
                                        ReverseActivity.this.mRadar = 1;
                                        ReverseActivity.this.mReverse.setVisibility(0);
                                        if (!ReverseActivity.isFRadar) {
                                            ReverseActivity.this.mRReverse.setVisibility(0);
                                        }
                                        ReverseActivity.this.mTW.write(33281, 1, 43);
                                        if (TWTW.mModel == 17 || TWTW.mModel == 18 || TWTW.mModel == 19) {
                                            ReverseActivity.this.mBReverse.setVisibility(0);
                                        }
                                    }
                                    if (!ReverseActivity.this.isEffective(new int[]{bArr3[0] & 255, bArr3[1] & 255, bArr3[2] & 255, bArr3[3] & 255}, new int[]{bArr3[5] & 255, bArr3[6] & 255, bArr3[7] & 255, bArr3[8] & 255}, bArr3[4] & 255, bArr3[9] & 255)) {
                                        if (ReverseActivity.this.isInvalid) {
                                            ReverseActivity.this.isInvalid = false;
                                        } else {
                                            return;
                                        }
                                    } else {
                                        ReverseActivity.this.isInvalid = true;
                                    }
                                    ReverseActivity.this.mRV.setRearRadar(ReverseActivity.this.toV(bArr3[0], bArr3[4]), ReverseActivity.this.toV(bArr3[1], bArr3[4]), ReverseActivity.this.toV(bArr3[2], bArr3[4]), ReverseActivity.this.toV(bArr3[3], bArr3[4]));
                                    ReverseActivity.this.showDanamicRadar(ReverseActivity.this.toV(bArr3[0], bArr3[4]), ReverseActivity.this.toV(bArr3[1], bArr3[4]), ReverseActivity.this.toV(bArr3[2], bArr3[4]), ReverseActivity.this.toV(bArr3[3], bArr3[4]));
                                    ReverseActivity.this.mRV.setFrontRadar(ReverseActivity.this.toV(bArr3[5], bArr3[9]), ReverseActivity.this.toV(bArr3[6], bArr3[9]), ReverseActivity.this.toV(bArr3[7], bArr3[9]), ReverseActivity.this.toV(bArr3[8], bArr3[9]));
                                    int i21 = 0;
                                    for (int i22 = 10; i22 < 14; i22++) {
                                        try {
                                            int i23 = bArr3[i22] & 255;
                                            if (i23 != 0 && (i21 == 0 || i21 > i23)) {
                                                i21 = i23;
                                            }
                                        } catch (Exception e2) {
                                            Log.e(ReverseActivity.TAG, "0x0507:error:" + e2.getMessage());
                                        }
                                    }
                                    if (i21 == 0) {
                                        ReverseActivity.this.mRReverseD.setVisibility(8);
                                    } else {
                                        if (i21 > (bArr3[14] & 255)) {
                                            ReverseActivity.this.mRReverseD.setText(i21 + ReverseActivity.this.getString(R.string.cm));
                                            ReverseActivity.this.mRReverseD.setTextColor(-1);
                                        } else {
                                            ReverseActivity.this.mRReverseD.setText(R.string.stop);
                                            ReverseActivity.this.mRReverseD.setTextColor(-65536);
                                        }
                                        ReverseActivity.this.mRReverseD.setVisibility(0);
                                    }
                                    int i24 = 0;
                                    for (int i25 = 15; i25 < 19; i25++) {
                                        int i26 = bArr3[i25] & 255;
                                        if (i26 != 0 && (i24 == 0 || i24 > i26)) {
                                            i24 = i26;
                                        }
                                    }
                                    if (i24 == 0) {
                                        ReverseActivity.this.mFReverseD.setVisibility(8);
                                    } else {
                                        if (i24 > (bArr3[19] & 255)) {
                                            ReverseActivity.this.mFReverseD.setText(i24 + ReverseActivity.this.getString(R.string.cm));
                                            ReverseActivity.this.mFReverseD.setTextColor(-1);
                                        } else {
                                            ReverseActivity.this.mFReverseD.setText(R.string.stop);
                                            ReverseActivity.this.mFReverseD.setTextColor(-65536);
                                        }
                                        ReverseActivity.this.mFReverseD.setVisibility(0);
                                    }
                                    ReverseActivity.this.mRV.invalidate();
                                    return;
                                }
                                return;
                            }
                            if (message.what == 1286) {
                                ReverseActivity.this.mMCUTrack = true;
                            }
                            if ((!ReverseActivity.this.mMCUTrack || message.what != 34054) && message.obj != null) {
                                byte[] bArr4 = (byte[]) message.obj;
                                ReverseActivity.this.setSW((bArr4[1] << 8) | (bArr4[0] & 255), (bArr4[3] << 8) | (bArr4[2] & 255));
                                return;
                            }
                            return;
                        }
                    }
                    if (ReverseActivity.this.iskeypad == 1 && message.arg1 == 1) {
                        ReverseActivity.this.iskeypad = 0;
                    }
                    if (message.arg1 == 0) {
                        if ((Settings.System.getInt(ReverseActivity.this.getContentResolver(), "SYSTEM_FOWARD_SWITCH", 0) != 0) && ReverseActivity.this.iskeypad == 0) {
                            Intent intent = new Intent();
                            intent.setClass(ReverseActivity.this, LRReverseActivity.class);
                            intent.setFlags(268435456);
                            ReverseActivity.this.startActivity(intent);
                        }
                        if (ReverseActivity.this.iskeypad == 0) {
                            ReverseActivity.this.finish();
                        }
                    } else if (message.arg1 == 1) {
                        ReverseActivity.this.setTrackLineState();
                        return;
                    }
                    return;
                }
                if (message.arg1 == 0) {
                    String[] strArrSplit = ((String) message.obj).split("-");
                    if (TWTW.mModel == -1) {
                        try {
                            String str = strArrSplit[2];
                            TWTW.mModel = Integer.parseInt(str.substring(0, 2), 16);
                            try {
                                TWTW.mCType = Integer.parseInt(str.substring(2, 3), 16);
                            } catch (Exception e3) {
                                Log.e(ReverseActivity.TAG, "0x010a:error 1:" + e3.getMessage());
                            }
                            try {
                                try {
                                    Integer.parseInt(strArrSplit[3].substring(4, 6), 16);
                                } catch (Exception e4) {
                                }
                            } catch (Exception e5) {
                            }
                        } catch (Exception e6) {
                            Log.e(ReverseActivity.TAG, "0x010a:error 2:" + e6.getMessage());
                        }
                        if (TWTW.mModel == -1) {
                            TWTW.mModel = 0;
                        }
                    }
                }
                if (TWTW.mModel == 113) {
                    ReverseActivity.this.mReverse.setVisibility(0);
                    ReverseActivity.this.mBReverse2.setVisibility(0);
                    return;
                }
                return;
            } catch (Exception e7) {
                Log.e(ReverseActivity.TAG, Log.getStackTraceString(e7));
            }
            Log.e(ReverseActivity.TAG, Log.getStackTraceString(e7));
        }
    };
    private int mMode = 0;
    private boolean showDetecting = false;
    private int btnState = 0;
    private int btnStateCamera = 0;
    private int reverseMode = 0;

    static /* synthetic */ int access$1508(ReverseActivity x0) {
        int i = x0.mCount;
        x0.mCount = i + 1;
        return i;
    }

    public void setPreview(boolean on) {
        if (on && !this.mPreview) {
            this.mPreview = true;
            if (this.mCamera != null) {
                this.mSurfaceView.setVisibility(0);
                try {
                    Camera.Parameters parameters = this.mCamera.getParameters();
                    int m_de = SystemProperties.getInt("persist.camera.deinterlace", 1);
                    int mode = SystemProperties.getInt("persist.camera.mode0", 2);
                    if (mode != 0) {
                        if (mode == 1) {
                            if (m_de == 0) {
                                parameters.setPreviewSize(960, 288);
                            } else {
                                parameters.setPreviewSize(960, 576);
                            }
                        } else {
                            switch (mode) {
                                case 6:
                                case LRReverseActivity.ACTIVITY_RUSEME /* 7 */:
                                case 8:
                                case 9:
                                    parameters.setPreviewSize(1920, 1080);
                                    break;
                                default:
                                    parameters.setPreviewSize(1280, 720);
                                    break;
                            }
                        }
                    } else if (m_de == 0) {
                        parameters.setPreviewSize(960, 240);
                    } else {
                        parameters.setPreviewSize(960, 480);
                    }
                    this.mCamera.setParameters(parameters);
                } catch (Exception e) {
                    Log.e(TAG, "setPreview:error:" + e.getMessage());
                }
                int m_V = SystemProperties.getInt("persist.camera.angle", 90);
                if (m_V == 270) {
                    this.mCamera.setDisplayOrientation(180);
                }
                this.mCamera.startPreview();
                return;
            }
            return;
        }
        if (!on && this.mPreview) {
            if (this.mCamera != null) {
                this.mHandler.removeMessages(START_PREVIEW);
                this.mCamera.stopPreview();
                this.mSurfaceView.setVisibility(8);
            }
            this.mPreview = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPreview() {
        setPreview(true);
        this.mWarningImage.setVisibility(8);
        this.mWarningText.setVisibility(8);
        this.mCount = 0;
        this.mHandler.removeMessages(SIGNAL);
        this.mHandler.sendEmptyMessageDelayed(SIGNAL, 300L);
        showRMsg(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDanamicRadar(int Left, int CenterLeft, int CenterRight, int Right) {
        this.dynamic_radar_left.getDrawable().setLevel(rebuildLev(Left));
        this.dynamic_radar_centerleft.getDrawable().setLevel(rebuildLev(CenterLeft));
        this.dynamic_radar_centerright.getDrawable().setLevel(rebuildLev(CenterRight));
        this.dynamic_radar_right.getDrawable().setLevel(rebuildLev(Right));
    }

    private int rebuildLev(int lev) {
        if (lev == 4 || lev == 5) {
            return 4;
        }
        if (lev == 6 || lev == 7) {
            return 5;
        }
        if (lev > 7) {
            return 6;
        }
        return lev;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toV(int v, int m) {
        if (v < m) {
            return ((v * 11) / m) + 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toV1(int v, int m) {
        if (v < m) {
            return v + 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toV2(int v) {
        if (v >= 5) {
            return 0;
        }
        return (v + 1) << 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toV11(int v) {
        if (v == 0) {
            return 1;
        }
        if (v <= 165) {
            return ((v - 1) / 15) + 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toV22(int v) {
        return v << 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRMsg(boolean no) {
        if (this.noAHD != no) {
            this.noAHD = no;
            if (no) {
                ((TextView) findViewById(R.id.tv_common)).setTextColor(-16777216);
                ((TextView) findViewById(R.id.tv_ahd)).setTextColor(-1);
                findViewById(R.id.flayout_common).getBackground().setLevel(1);
                findViewById(R.id.flayout_ahd).getBackground().setLevel(0);
                ((TextView) findViewById(R.id.tv_camera_alarm_message)).setText(R.string.camera_alarm_AHD);
                return;
            }
            ((TextView) findViewById(R.id.tv_common)).setTextColor(-1);
            ((TextView) findViewById(R.id.tv_ahd)).setTextColor(-16777216);
            findViewById(R.id.flayout_common).getBackground().setLevel(0);
            findViewById(R.id.flayout_ahd).getBackground().setLevel(1);
            ((TextView) findViewById(R.id.tv_camera_alarm_message)).setText(R.string.camera_alarm);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEffective(int[] arg1, int[] arg2, int arg1max, int arg2max) {
        boolean flag1 = false;
        for (int arg : arg1) {
            if (arg > arg1max) {
                flag1 = false;
            } else {
                flag1 = true;
                break;
            }
        }
        boolean flag2 = false;
        for (int arg3 : arg2) {
            if (arg3 > arg2max) {
                flag2 = false;
            } else {
                flag2 = true;
                break;
            }
        }
        return flag2 | flag1;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCameraId();
        this.density = getResources().getDisplayMetrics().density;
        this.widthPixels = getResources().getDisplayMetrics().widthPixels;
        this.heightPixels = getResources().getDisplayMetrics().heightPixels;
        setContentView(R.layout.reverse);
        this.mWarningImage = (ImageView) findViewById(R.id.warning_image);
        this.mWarningText = (TextView) findViewById(R.id.warning_text_signal);
        this.mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        SurfaceHolder holder = this.mSurfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(3);
        this.mReverse = (RelativeLayout) findViewById(R.id.reverse);
        this.mRReverse = (RelativeLayout) findViewById(R.id.rreverse);
        this.rl_reverse = (RelativeLayout) findViewById(R.id.rl_reverse);
        this.dynamic_view = (RelativeLayout) findViewById(R.id.dynamic_view);
        this.dynamic_radar_left = (ImageView) findViewById(R.id.dynamic_radar_left);
        this.dynamic_radar_centerleft = (ImageView) findViewById(R.id.dynamic_radar_centerleft);
        this.dynamic_radar_centerright = (ImageView) findViewById(R.id.dynamic_radar_centerright);
        this.dynamic_radar_right = (ImageView) findViewById(R.id.dynamic_radar_right);
        this.mBReverse = (LinearLayout) findViewById(R.id.breverse);
        this.mBReverse2 = (LinearLayout) findViewById(R.id.breverse2);
        this.mBReverse3 = (LinearLayout) findViewById(R.id.breverse3);
        this.mBReverse4 = (LinearLayout) findViewById(R.id.breverse4);
        this.mBReverse5 = (LinearLayout) findViewById(R.id.breverse5);
        this.mRV = (RadarView) findViewById(R.id.reverse_rv);
        this.mFReverseD = (TextView) findViewById(R.id.freverse_d);
        this.mRReverseD = (TextView) findViewById(R.id.rreverse_d);
        if (TWTW.getSystemVersion().contains("BMW")) {
            isFRadar = Settings.System.getInt(getContentResolver(), "SYSTEM_FRADAR", 1) != 0;
        } else {
            isFRadar = Settings.System.getInt(getContentResolver(), "SYSTEM_FRADAR", 0) != 0;
        }
        if (isFRadar) {
            this.mRReverse.setVisibility(8);
        }
        this.noRMsg = Settings.System.getInt(getContentResolver(), "SYSTEM_RMSG", 0) != 0;
        ((TextView) findViewById(R.id.tv_common)).setTextColor(-1);
        ((TextView) findViewById(R.id.tv_ahd)).setTextColor(-16777216);
        findViewById(R.id.flayout_common).getBackground().setLevel(0);
        findViewById(R.id.flayout_ahd).getBackground().setLevel(1);
        this.mCamera_settings = (ImageView) findViewById(R.id.camera_settings);
        this.mShowAHDTip = new ShowAHDTip();
        this.rl_reverse2 = (RelativeLayout) findViewById(R.id.rl_reverse2);
        if (Settings.System.getInt(getContentResolver(), "SYSTEM_LINE2", 0) != 0) {
            this.rl_reverse2.setVisibility(0);
            this.reversingRulerState = 1;
        }
        this.reverseLineRuler = (ReverseLineRuler) findViewById(R.id.lreverse2);
        this.btnAdjust = (Button) findViewById(R.id.btn_adjust);
        this.btnReset = (Button) findViewById(R.id.btn_reset);
        this.mCameraView = new CameraView();
        Log.d(TAG, "" + Camera.getNumberOfCameras());
        this.mlp_radar_view = (RelativeLayout.LayoutParams) this.mRReverse.getLayoutParams();
        this.mlp_revese_view = (RelativeLayout.LayoutParams) this.rl_reverse.getLayoutParams();
        if (this.widthPixels / this.heightPixels >= 2.0d) {
            int mode = Settings.System.getInt(getContentResolver(), "REVERSING_MODE", 0);
            changeReverseViewMode(mode);
        }
        initView();
        this.mRV.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.tw.reverse.ReverseActivity.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View arg0) {
                ReverseActivity.this.visible();
                ReverseActivity.this.setview();
                return false;
            }
        });
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
        private View mView;
        private WindowManager mWindowManager;
        private String mode0Index;
        private TextView reverse_ahd;

        public CameraView() {
            byte b = 0;
            this.mode0Index = "0";
            this.mView = ((LayoutInflater) ReverseActivity.this.getSystemService("layout_inflater")).inflate(R.layout.camera, (ViewGroup) null);
            ReverseActivity.this.tv_reverse_view_mode = (TextView) this.mView.findViewById(R.id.tv_reverse_view_mode);
            if (ReverseActivity.this.widthPixels / ReverseActivity.this.heightPixels >= 2.0d) {
                ReverseActivity.this.tv_reverse_view_mode.setVisibility(0);
            }
            ReverseActivity.this.reverse_view_mode1 = (ImageView) this.mView.findViewById(R.id.reverse_view_mode1);
            ReverseActivity.this.reverse_view_mode2 = (ImageView) this.mView.findViewById(R.id.reverse_view_mode2);
            ReverseActivity.this.reverse_view_mode3 = (ImageView) this.mView.findViewById(R.id.reverse_view_mode3);
            ReverseActivity.this.reverse_view_mode4 = (ImageView) this.mView.findViewById(R.id.reverse_view_mode4);
            ReverseActivity.this.context_reverse_view_mode4 = (LinearLayout) this.mView.findViewById(R.id.context_reverse_view_mode4);
            if (!TWTW.getSystemVersion().contains("BBA")) {
                ReverseActivity.this.context_reverse_view_mode4.setVisibility(8);
            } else {
                ReverseActivity.this.context_reverse_view_mode4.setVisibility(0);
            }
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.tw.reverse.ReverseActivity.CameraView.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.d(ReverseActivity.TAG, "onProgressChanged fromUser = " + fromUser + " progress = " + progress);
                    if (fromUser) {
                        int progress2 = progress - 50;
                        int id = seekBar.getId();
                        if (id == R.id.brightness_level) {
                            ((TextView) CameraView.this.mView.findViewById(R.id.brightness_title)).setText(ReverseActivity.this.getString(R.string.brightness) + "(" + progress2 + ")");
                            if (ReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters = ReverseActivity.this.mCamera.getParameters();
                                    parameters.setBrightness(String.format(Locale.US, "%d", Integer.valueOf(progress2 + 128)));
                                    ReverseActivity.this.mCamera.setParameters(parameters);
                                } catch (Exception e) {
                                }
                            }
                        } else if (id == R.id.contrast_level) {
                            ((TextView) CameraView.this.mView.findViewById(R.id.contrast_title)).setText(ReverseActivity.this.getString(R.string.contrast) + "(" + progress2 + ")");
                            if (ReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters2 = ReverseActivity.this.mCamera.getParameters();
                                    parameters2.setContrast(String.format(Locale.US, "%d", Integer.valueOf(progress2 + 128)));
                                    ReverseActivity.this.mCamera.setParameters(parameters2);
                                } catch (Exception e2) {
                                }
                            }
                        } else if (id == R.id.saturation_level) {
                            ((TextView) CameraView.this.mView.findViewById(R.id.saturation_title)).setText(ReverseActivity.this.getString(R.string.saturation) + "(" + progress2 + ")");
                            if (ReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters3 = ReverseActivity.this.mCamera.getParameters();
                                    parameters3.setSaturation(String.format(Locale.US, "%d", Integer.valueOf(progress2 + 128)));
                                    ReverseActivity.this.mCamera.setParameters(parameters3);
                                } catch (Exception e3) {
                                }
                            }
                        }
                        ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAMERA_HIDE);
                        ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAMERA_HIDE, 5000L);
                    }
                }
            };
            ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setOnSeekBarChangeListener(onSeekBarChangeListener);
            ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setOnSeekBarChangeListener(onSeekBarChangeListener);
            ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setOnSeekBarChangeListener(onSeekBarChangeListener);
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.tw.reverse.ReverseActivity.CameraView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    int progress;
                    int progress2;
                    int progress3;
                    int progress4;
                    int progress5;
                    int progress6;
                    Log.d(ReverseActivity.TAG, "onClick");
                    switch (view.getId()) {
                        case R.id.brightness_down /* 2131034126 */:
                            int progress7 = (((SeekBar) CameraView.this.mView.findViewById(R.id.brightness_level)).getProgress() - 50) - 1;
                            if (progress7 >= -50) {
                                progress = progress7;
                            } else {
                                progress = -50;
                            }
                            ((TextView) CameraView.this.mView.findViewById(R.id.brightness_title)).setText(ReverseActivity.this.getString(R.string.brightness) + "(" + progress + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.brightness_level)).setProgress(progress + 50);
                            if (ReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters = ReverseActivity.this.mCamera.getParameters();
                                    parameters.setBrightness(String.format(Locale.US, "%d", Integer.valueOf(progress + 128)));
                                    ReverseActivity.this.mCamera.setParameters(parameters);
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
                            ((TextView) CameraView.this.mView.findViewById(R.id.brightness_title)).setText(ReverseActivity.this.getString(R.string.brightness) + "(" + progress2 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.brightness_level)).setProgress(progress2 + 50);
                            if (ReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters2 = ReverseActivity.this.mCamera.getParameters();
                                    parameters2.setBrightness(String.format(Locale.US, "%d", Integer.valueOf(progress2 + 128)));
                                    ReverseActivity.this.mCamera.setParameters(parameters2);
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
                            ((TextView) CameraView.this.mView.findViewById(R.id.contrast_title)).setText(ReverseActivity.this.getString(R.string.contrast) + "(" + progress3 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.contrast_level)).setProgress(progress3 + 50);
                            if (ReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters3 = ReverseActivity.this.mCamera.getParameters();
                                    parameters3.setContrast(String.format(Locale.US, "%d", Integer.valueOf(progress3 + 128)));
                                    ReverseActivity.this.mCamera.setParameters(parameters3);
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
                            ((TextView) CameraView.this.mView.findViewById(R.id.contrast_title)).setText(ReverseActivity.this.getString(R.string.contrast) + "(" + progress4 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.contrast_level)).setProgress(progress4 + 50);
                            if (ReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters4 = ReverseActivity.this.mCamera.getParameters();
                                    parameters4.setContrast(String.format(Locale.US, "%d", Integer.valueOf(progress4 + 128)));
                                    ReverseActivity.this.mCamera.setParameters(parameters4);
                                } catch (Exception e4) {
                                }
                            }
                            break;
                        case R.id.image_debounce /* 2131034163 */:
                            if (ReverseActivity.this.imageDebounce == 0) {
                                ReverseActivity.this.imageDebounce = 1;
                                SystemProperties.set("persist.camera.deinterlace", "1");
                            } else {
                                ReverseActivity.this.imageDebounce = 0;
                                SystemProperties.set("persist.camera.deinterlace", "0");
                            }
                            ((ImageView) CameraView.this.mView.findViewById(R.id.image_debounce)).getDrawable().setLevel(ReverseActivity.this.imageDebounce);
                            break;
                        case R.id.lev_arrow /* 2131034171 */:
                            if (ReverseActivity.this.btnStateCamera == 0) {
                                ReverseActivity.this.btnStateCamera = 1;
                                CameraView.this.mView.findViewById(R.id.layout_camera).setVisibility(0);
                                ((ImageView) CameraView.this.mView.findViewById(R.id.lev_arrow)).getDrawable().setLevel(ReverseActivity.this.btnStateCamera);
                            } else {
                                ReverseActivity.this.btnStateCamera = 0;
                                CameraView.this.mView.findViewById(R.id.layout_camera).setVisibility(8);
                                ((ImageView) CameraView.this.mView.findViewById(R.id.lev_arrow)).getDrawable().setLevel(ReverseActivity.this.btnStateCamera);
                            }
                            break;
                        case R.id.reverse_view_mode1 /* 2131034203 */:
                            ReverseActivity.this.changeReverseViewMode(0);
                            ReverseActivity.this.reverseLineRuler.resetLineRuler();
                            ReverseActivity.this.reverseMode = 0;
                            break;
                        case R.id.reverse_view_mode2 /* 2131034204 */:
                            ReverseActivity.this.changeReverseViewMode(1);
                            ReverseActivity.this.reverseLineRuler.resetLineRuler();
                            ReverseActivity.this.reverseMode = 1;
                            break;
                        case R.id.reverse_view_mode3 /* 2131034205 */:
                            ReverseActivity.this.changeReverseViewMode(2);
                            ReverseActivity.this.reverseLineRuler.resetLineRuler();
                            ReverseActivity.this.reverseMode = 2;
                            break;
                        case R.id.reverse_view_mode4 /* 2131034206 */:
                            if (ReverseActivity.this.reverse_view_mode4.getDrawable().getLevel() == 0) {
                                ReverseActivity.this.reverse_view_mode4.getDrawable().setLevel(1);
                                ReverseActivity.this.dynamic_view.setVisibility(0);
                                if (ReverseActivity.this.reverseMode != 0) {
                                    ReverseActivity.this.mRReverse.setVisibility(8);
                                }
                                Settings.System.putInt(ReverseActivity.this.getContentResolver(), "RADAR_DYNAMIC", 1);
                            } else {
                                ReverseActivity.this.reverse_view_mode4.getDrawable().setLevel(0);
                                ReverseActivity.this.dynamic_view.setVisibility(8);
                                ReverseActivity.this.mRReverse.setVisibility(0);
                                Settings.System.putInt(ReverseActivity.this.getContentResolver(), "RADAR_DYNAMIC", 0);
                            }
                            break;
                        case R.id.reversing_ruler_line /* 2131034209 */:
                            if (ReverseActivity.this.reversingRulerState == 0) {
                                ReverseActivity.this.reversingRulerState = 1;
                                ReverseActivity.this.rl_reverse2.setVisibility(0);
                                CameraView.this.mView.findViewById(R.id.reversing_ruler_line_settings).setVisibility(0);
                            } else {
                                ReverseActivity.this.reversingRulerState = 0;
                                ReverseActivity.this.rl_reverse2.setVisibility(8);
                                CameraView.this.mView.findViewById(R.id.reversing_ruler_line_settings).setVisibility(8);
                            }
                            ((ImageView) CameraView.this.mView.findViewById(R.id.reversing_ruler_line)).getDrawable().setLevel(ReverseActivity.this.reversingRulerState);
                            Settings.System.putInt(ReverseActivity.this.getContentResolver(), "SYSTEM_LINE2", ReverseActivity.this.reversingRulerState);
                            break;
                        case R.id.reversing_ruler_line_settings /* 2131034210 */:
                            ReverseActivity.this.reverseLineRuler.adjustLineRuler();
                            ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAMERA_HIDE);
                            ReverseActivity.this.btnReset.setVisibility(0);
                            ReverseActivity.this.btnAdjust.setVisibility(0);
                            ReverseActivity.this.btnState = 0;
                            ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAMERA_HIDE, 10L);
                            return;
                        case R.id.saturation_down /* 2131034216 */:
                            int progress11 = (((SeekBar) CameraView.this.mView.findViewById(R.id.saturation_level)).getProgress() - 50) - 1;
                            if (progress11 >= -50) {
                                progress5 = progress11;
                            } else {
                                progress5 = -50;
                            }
                            ((TextView) CameraView.this.mView.findViewById(R.id.saturation_title)).setText(ReverseActivity.this.getString(R.string.saturation) + "(" + progress5 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.saturation_level)).setProgress(progress5 + 50);
                            if (ReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters5 = ReverseActivity.this.mCamera.getParameters();
                                    parameters5.setSaturation(String.format(Locale.US, "%d", Integer.valueOf(progress5 + 128)));
                                    ReverseActivity.this.mCamera.setParameters(parameters5);
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
                            ((TextView) CameraView.this.mView.findViewById(R.id.saturation_title)).setText(ReverseActivity.this.getString(R.string.saturation) + "(" + progress6 + ")");
                            ((SeekBar) CameraView.this.mView.findViewById(R.id.saturation_level)).setProgress(progress6 + 50);
                            if (ReverseActivity.this.mCamera != null) {
                                try {
                                    Camera.Parameters parameters6 = ReverseActivity.this.mCamera.getParameters();
                                    parameters6.setSaturation(String.format(Locale.US, "%d", Integer.valueOf(progress6 + 128)));
                                    ReverseActivity.this.mCamera.setParameters(parameters6);
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
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_reverse_view_mode)).setTextColor(-7829368);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(0);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(1);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_reverse_view_mode)).getCompoundDrawables()[3].setLevel(0);
                            CameraView.this.mView.findViewById(R.id.layout_frame).setVisibility(8);
                            CameraView.this.mView.findViewById(R.id.scrollViewCamera).setVisibility(0);
                            CameraView.this.mView.findViewById(R.id.layout_reverse_view_mode).setVisibility(8);
                            break;
                        case R.id.tv_layout_frame /* 2131034232 */:
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_frame)).setTextColor(-1);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_camera)).setTextColor(-7829368);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_reverse_view_mode)).setTextColor(-7829368);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(1);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(0);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_reverse_view_mode)).getCompoundDrawables()[3].setLevel(0);
                            CameraView.this.mView.findViewById(R.id.layout_frame).setVisibility(0);
                            CameraView.this.mView.findViewById(R.id.scrollViewCamera).setVisibility(8);
                            CameraView.this.mView.findViewById(R.id.layout_reverse_view_mode).setVisibility(8);
                            break;
                        case R.id.tv_reverse_view_mode /* 2131034233 */:
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_frame)).setTextColor(-7829368);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_camera)).setTextColor(-7829368);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_reverse_view_mode)).setTextColor(-1);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(0);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(0);
                            ((TextView) CameraView.this.mView.findViewById(R.id.tv_reverse_view_mode)).getCompoundDrawables()[3].setLevel(1);
                            CameraView.this.mView.findViewById(R.id.layout_frame).setVisibility(8);
                            CameraView.this.mView.findViewById(R.id.scrollViewCamera).setVisibility(8);
                            CameraView.this.mView.findViewById(R.id.layout_reverse_view_mode).setVisibility(0);
                            break;
                    }
                    ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAMERA_HIDE);
                    ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAMERA_HIDE, 5000L);
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
            this.mView.findViewById(R.id.lev_arrow).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.reversing_ruler_line).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.reversing_ruler_line_settings).setOnClickListener(onClickListener);
            ReverseActivity.this.tv_reverse_view_mode.setOnClickListener(onClickListener);
            ReverseActivity.this.reverse_view_mode1.setOnClickListener(onClickListener);
            ReverseActivity.this.reverse_view_mode2.setOnClickListener(onClickListener);
            ReverseActivity.this.reverse_view_mode3.setOnClickListener(onClickListener);
            ReverseActivity.this.reverse_view_mode4.setOnClickListener(onClickListener);
            if (Settings.System.getInt(ReverseActivity.this.getContentResolver(), "SYSTEM_LINE2", 0) != 0) {
                rulerLineCloseOpen(true);
            } else {
                rulerLineCloseOpen(false);
            }
            if (Settings.System.getInt(ReverseActivity.this.getContentResolver(), "RADAR_DYNAMIC", 0) != 0) {
                ReverseActivity.this.reverse_view_mode4.getDrawable().setLevel(1);
                ReverseActivity.this.dynamic_view.setVisibility(0);
            } else {
                ReverseActivity.this.reverse_view_mode4.getDrawable().setLevel(0);
                ReverseActivity.this.dynamic_view.setVisibility(8);
            }
            ReverseActivity.this.reversingRulerState = Settings.System.getInt(ReverseActivity.this.getContentResolver(), "SYSTEM_LINE2", 0);
            if (ReverseActivity.this.reversingRulerState == 0) {
                this.mView.findViewById(R.id.reversing_ruler_line_settings).setVisibility(8);
            }
            ((ImageView) this.mView.findViewById(R.id.reversing_ruler_line)).getDrawable().setLevel(ReverseActivity.this.reversingRulerState);
            this.mView.findViewById(R.id.image_debounce).setOnClickListener(onClickListener);
            this.reverse_ahd = (TextView) this.mView.findViewById(R.id.reverse_ahd);
            this.mode0Index = SystemProperties.get("persist.camera.mode0", "2");
            String str = this.mode0Index;
            switch (str.hashCode()) {
                case 48:
                    if (!str.equals("0")) {
                        b = -1;
                    }
                    break;
                case 49:
                    b = !str.equals("1") ? (byte) -1 : (byte) 1;
                    break;
                case 50:
                    b = !str.equals("2") ? (byte) -1 : (byte) 2;
                    break;
                case 51:
                    b = !str.equals("3") ? (byte) -1 : (byte) 3;
                    break;
                case 52:
                    b = !str.equals("4") ? (byte) -1 : (byte) 4;
                    break;
                case 53:
                    b = !str.equals("5") ? (byte) -1 : (byte) 5;
                    break;
                case 54:
                    b = !str.equals("6") ? (byte) -1 : (byte) 6;
                    break;
                case 55:
                    b = !str.equals("7") ? (byte) -1 : (byte) 7;
                    break;
                case 56:
                    b = !str.equals("8") ? (byte) -1 : (byte) 8;
                    break;
                case 57:
                    b = !str.equals("9") ? (byte) -1 : (byte) 9;
                    break;
                default:
                    b = -1;
                    break;
            }
            switch (b) {
                case R.styleable.radar_adjust /* 0 */:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_ntsc));
                    ((RadioButton) this.mView.findViewById(R.id.ahd_ntsc)).setChecked(true);
                    break;
                case R.styleable.radar_brush_width /* 1 */:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_pal));
                    ((RadioButton) this.mView.findViewById(R.id.ahd_pal)).setChecked(true);
                    break;
                case R.styleable.radar_orientation /* 2 */:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_25f));
                    ((RadioButton) this.mView.findViewById(R.id.ahd_25f)).setChecked(true);
                    break;
                case R.styleable.radar_src /* 3 */:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_30f));
                    ((RadioButton) this.mView.findViewById(R.id.ahd_30f)).setChecked(true);
                    break;
                case 4:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_tvi_25f));
                    ((RadioButton) this.mView.findViewById(R.id.tvi_25f)).setChecked(true);
                    break;
                case 5:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_tvi_30f));
                    ((RadioButton) this.mView.findViewById(R.id.tvi_30f)).setChecked(true);
                    break;
                case 6:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_1080p_25f));
                    ((RadioButton) this.mView.findViewById(R.id.ahd_1080p_25f)).setChecked(true);
                    break;
                case LRReverseActivity.ACTIVITY_RUSEME /* 7 */:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_1080p_30f));
                    ((RadioButton) this.mView.findViewById(R.id.ahd_1080p_30f)).setChecked(true);
                    break;
                case 8:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_tvi_1080p_25f));
                    ((RadioButton) this.mView.findViewById(R.id.tvi_1080p_25f)).setChecked(true);
                    break;
                case 9:
                    this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_tvi_1080p_30f));
                    ((RadioButton) this.mView.findViewById(R.id.tvi_1080p_30f)).setChecked(true);
                    break;
            }
            CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.tw.reverse.ReverseActivity.CameraView.3
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton checkedId, boolean checked) {
                    if (checked) {
                        switch (CameraView.this.mode0Index) {
                            case "0":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.ahd_ntsc)).setChecked(false);
                                break;
                            case "1":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.ahd_pal)).setChecked(false);
                                break;
                            case "2":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.ahd_25f)).setChecked(false);
                                break;
                            case "3":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.ahd_30f)).setChecked(false);
                                break;
                            case "4":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.tvi_25f)).setChecked(false);
                                break;
                            case "5":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.tvi_30f)).setChecked(false);
                                break;
                            case "6":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.ahd_1080p_25f)).setChecked(false);
                                break;
                            case "7":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.ahd_1080p_30f)).setChecked(false);
                                break;
                            case "8":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.tvi_1080p_25f)).setChecked(false);
                                break;
                            case "9":
                                ((RadioButton) CameraView.this.mView.findViewById(R.id.tvi_1080p_30f)).setChecked(false);
                                break;
                        }
                        String ahd = "0";
                        int id = checkedId.getId();
                        switch (id) {
                            case R.id.ahd_1080p_25f /* 2131034112 */:
                                ahd = "6";
                                CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_1080p_25f));
                                break;
                            case R.id.ahd_1080p_30f /* 2131034113 */:
                                ahd = "7";
                                CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_1080p_30f));
                                break;
                            case R.id.ahd_25f /* 2131034114 */:
                                ahd = "2";
                                CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_25f));
                                break;
                            case R.id.ahd_30f /* 2131034115 */:
                                ahd = "3";
                                CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_30f));
                                break;
                            default:
                                switch (id) {
                                    case R.id.ahd_ntsc /* 2131034118 */:
                                        ahd = "0";
                                        CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_ntsc));
                                        break;
                                    case R.id.ahd_pal /* 2131034119 */:
                                        ahd = "1";
                                        CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_ahd_pal));
                                        break;
                                    default:
                                        switch (id) {
                                            case R.id.tvi_1080p_25f /* 2131034234 */:
                                                ahd = "8";
                                                CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_tvi_1080p_25f));
                                                break;
                                            case R.id.tvi_1080p_30f /* 2131034235 */:
                                                ahd = "9";
                                                CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_tvi_1080p_30f));
                                                break;
                                            case R.id.tvi_25f /* 2131034236 */:
                                                ahd = "4";
                                                CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_tvi_25f));
                                                break;
                                            case R.id.tvi_30f /* 2131034237 */:
                                                ahd = "5";
                                                CameraView.this.reverse_ahd.setText(ReverseActivity.this.getString(R.string.reverse_tvi_30f));
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                        CameraView.this.mode0Index = ahd;
                        if (CameraView.this.mode0Index.equals("0") || CameraView.this.mode0Index.equals("1")) {
                            ((LinearLayout) CameraView.this.mView.findViewById(R.id.image_debounce_switch)).setVisibility(0);
                        } else {
                            ((LinearLayout) CameraView.this.mView.findViewById(R.id.image_debounce_switch)).setVisibility(8);
                        }
                        int mode0 = Integer.parseInt(CameraView.this.mode0Index);
                        if (mode0 >= 0) {
                            SystemProperties.set("persist.camera.ahd360", String.valueOf(mode0 + 1));
                            SystemProperties.set("persist.camera.mode0", String.valueOf(mode0));
                        }
                        if (mode0 >= 2) {
                            SystemProperties.set("persist.camera.id", "0");
                        } else {
                            SystemProperties.set("persist.camera.id", "1");
                        }
                        ReverseActivity.this.mHandler.removeMessages(ReverseActivity.SIGNAL);
                        ReverseActivity.this.setPreview(false);
                        ReverseActivity.this.mWarningImage.setVisibility(8);
                        ReverseActivity.this.closeCamera();
                        Log.d(ReverseActivity.TAG, "openCamera1");
                        ReverseActivity.this.openCamera();
                        ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAMERA_HIDE);
                        ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAMERA_HIDE, 5000L);
                    }
                }
            };
            ((RadioButton) this.mView.findViewById(R.id.ahd_auto)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.ahd_pal)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.ahd_ntsc)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.ahd_pal)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.ahd_25f)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.ahd_30f)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.tvi_25f)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.tvi_30f)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.ahd_1080p_25f)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.ahd_1080p_30f)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.tvi_1080p_25f)).setOnCheckedChangeListener(checkedChangeListener);
            ((RadioButton) this.mView.findViewById(R.id.tvi_1080p_30f)).setOnCheckedChangeListener(checkedChangeListener);
            this.mWindowManager = (WindowManager) ReverseActivity.this.getSystemService("window");
            this.mLayoutParams = new WindowManager.LayoutParams((int) (ReverseActivity.this.density * 550.0f), (int) (ReverseActivity.this.density * 400.0f), 0, 0, 2038, 40, 1);
        }

        private void rulerLineCloseOpen(boolean isOpen) {
            if (isOpen) {
                ReverseActivity.this.reversingRulerState = 1;
                ReverseActivity.this.rl_reverse2.setVisibility(0);
                this.mView.findViewById(R.id.reversing_ruler_line_settings).setVisibility(0);
                Settings.System.putInt(ReverseActivity.this.getContentResolver(), "SYSTEM_LINE2", ReverseActivity.this.reversingRulerState);
                return;
            }
            ReverseActivity.this.reversingRulerState = 0;
            ReverseActivity.this.rl_reverse2.setVisibility(8);
            this.mView.findViewById(R.id.reversing_ruler_line_settings).setVisibility(8);
            Settings.System.putInt(ReverseActivity.this.getContentResolver(), "SYSTEM_LINE2", ReverseActivity.this.reversingRulerState);
        }

        public void close() {
            ReverseActivity.this.noRMsg = true;
            Settings.System.putInt(ReverseActivity.this.getContentResolver(), "SYSTEM_RMSG", 1);
        }

        public void show() {
            if (TWTW.mModel == 22 && ReverseActivity.this.mCarCameraView != null && ReverseActivity.this.mCarCameraView.isShow) {
                ReverseActivity.this.mCarCameraView.hide();
            }
            if (!this.isShow) {
                int brightness = SystemProperties.getInt("persist.camera.brightness0", 128) - 128;
                ((TextView) this.mView.findViewById(R.id.brightness_title)).setText(ReverseActivity.this.getString(R.string.brightness) + "(" + brightness + ")");
                ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setProgress(brightness + 50);
                int contrast = SystemProperties.getInt("persist.camera.contrast0", 128) - 128;
                ((TextView) this.mView.findViewById(R.id.contrast_title)).setText(ReverseActivity.this.getString(R.string.contrast) + "(" + contrast + ")");
                ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setProgress(contrast + 50);
                int saturation = SystemProperties.getInt("persist.camera.saturation0", 128) - 128;
                ((TextView) this.mView.findViewById(R.id.saturation_title)).setText(ReverseActivity.this.getString(R.string.saturation) + "(" + saturation + ")");
                ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setProgress(saturation + 50);
                ((TextView) this.mView.findViewById(R.id.tv_layout_frame)).setTextColor(-1);
                ((TextView) this.mView.findViewById(R.id.tv_layout_camera)).setTextColor(-7829368);
                ((TextView) this.mView.findViewById(R.id.tv_reverse_view_mode)).setTextColor(-7829368);
                ((TextView) this.mView.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(1);
                ((TextView) this.mView.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(0);
                ((TextView) this.mView.findViewById(R.id.tv_reverse_view_mode)).getCompoundDrawables()[3].setLevel(0);
                this.mView.findViewById(R.id.layout_frame).setVisibility(0);
                this.mView.findViewById(R.id.scrollViewCamera).setVisibility(8);
                this.mView.findViewById(R.id.layout_reverse_view_mode).setVisibility(8);
                this.isShow = true;
                this.mWindowManager.addView(this.mView, this.mLayoutParams);
                ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAMERA_HIDE);
                ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAMERA_HIDE, 5000L);
                if (SystemProperties.get("persist.camera.deinterlace").equals("0")) {
                    ReverseActivity.this.imageDebounce = 0;
                    SystemProperties.set("persist.camera.deinterlace", "0");
                } else {
                    ReverseActivity.this.imageDebounce = 1;
                    SystemProperties.set("persist.camera.deinterlace", "1");
                }
                ((ImageView) this.mView.findViewById(R.id.image_debounce)).getDrawable().setLevel(ReverseActivity.this.imageDebounce);
            }
        }

        public void hide() {
            if (this.isShow) {
                int brightness = (((SeekBar) this.mView.findViewById(R.id.brightness_level)).getProgress() - 50) + 128;
                int contrast = (((SeekBar) this.mView.findViewById(R.id.contrast_level)).getProgress() - 50) + 128;
                int saturation = (((SeekBar) this.mView.findViewById(R.id.saturation_level)).getProgress() - 50) + 128;
                SystemProperties.set("persist.camera.brightness0", String.format(Locale.US, "%d", Integer.valueOf(brightness)));
                SystemProperties.set("persist.camera.contrast0", String.format(Locale.US, "%d", Integer.valueOf(contrast)));
                SystemProperties.set("persist.camera.saturation0", String.format(Locale.US, "%d", Integer.valueOf(saturation)));
                this.mWindowManager.removeView(this.mView);
                this.isShow = false;
            }
        }

        public void reset() {
            if (this.isShow) {
                try {
                    Camera.Parameters parameters = ReverseActivity.this.mCamera.getParameters();
                    parameters.setBrightness(String.format(Locale.US, "%d", 128));
                    ReverseActivity.this.mCamera.setParameters(parameters);
                    parameters.setContrast(String.format(Locale.US, "%d", 128));
                    ReverseActivity.this.mCamera.setParameters(parameters);
                    parameters.setSaturation(String.format(Locale.US, "%d", 128));
                    ReverseActivity.this.mCamera.setParameters(parameters);
                } catch (Exception e) {
                }
                SystemProperties.set("persist.camera.brightness0", String.format(Locale.US, "%d", 128));
                SystemProperties.set("persist.camera.contrast0", String.format(Locale.US, "%d", 128));
                SystemProperties.set("persist.camera.saturation0", String.format(Locale.US, "%d", 128));
                ((TextView) this.mView.findViewById(R.id.brightness_title)).setText(ReverseActivity.this.getString(R.string.brightness) + "(0)");
                ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.brightness_level)).setProgress(50);
                ((TextView) this.mView.findViewById(R.id.contrast_title)).setText(ReverseActivity.this.getString(R.string.contrast) + "(0)");
                ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.contrast_level)).setProgress(50);
                ((TextView) this.mView.findViewById(R.id.saturation_title)).setText(ReverseActivity.this.getString(R.string.saturation) + "(0)");
                ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.saturation_level)).setProgress(50);
            }
        }
    }

    private class CarCameraView {
        private static final int MAX = 40;
        private boolean isShow = false;
        private WindowManager.LayoutParams mLayoutParams;
        private View mView;
        private WindowManager mWindowManager;

        public CarCameraView() {
            this.mView = ((LayoutInflater) ReverseActivity.this.getSystemService("layout_inflater")).inflate(R.layout.car_camera, (ViewGroup) null);
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.tw.reverse.ReverseActivity.CarCameraView.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.d(ReverseActivity.TAG, "onProgressChanged xx");
                    if (fromUser) {
                        int id = seekBar.getId();
                        if (id == R.id.car_camera_brightness_level) {
                            ((TextView) CarCameraView.this.mView.findViewById(R.id.car_camera_brightness_title)).setText(ReverseActivity.this.getString(R.string.brightness) + "(" + progress + ")");
                            ReverseActivity.this.mCarCameraBringtness = progress;
                            ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAR_BRIGHTNESS);
                            ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAR_BRIGHTNESS, 200L);
                        } else if (id == R.id.car_camera_contrast_level) {
                            ((TextView) CarCameraView.this.mView.findViewById(R.id.car_camera_contrast_title)).setText(ReverseActivity.this.getString(R.string.contrast) + "(" + progress + ")");
                            ReverseActivity.this.mCarCameraContrast = progress;
                            ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAR_CONTRAST);
                            ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAR_CONTRAST, 200L);
                        } else if (id == R.id.car_camera_saturation_level) {
                            ((TextView) CarCameraView.this.mView.findViewById(R.id.car_camera_saturation_title)).setText(ReverseActivity.this.getString(R.string.saturation) + "(" + progress + ")");
                            ReverseActivity.this.mCarCameraSaturation = progress;
                            ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAR_SATURATION);
                            ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAR_SATURATION, 200L);
                        }
                        ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAR_CAMERA_HIDE);
                        ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAR_CAMERA_HIDE, 5000L);
                    }
                }
            };
            ((SeekBar) this.mView.findViewById(R.id.car_camera_brightness_level)).setOnSeekBarChangeListener(onSeekBarChangeListener);
            ((SeekBar) this.mView.findViewById(R.id.car_camera_contrast_level)).setOnSeekBarChangeListener(onSeekBarChangeListener);
            ((SeekBar) this.mView.findViewById(R.id.car_camera_saturation_level)).setOnSeekBarChangeListener(onSeekBarChangeListener);
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.tw.reverse.ReverseActivity.CarCameraView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Log.d(ReverseActivity.TAG, "onClick1");
                    switch (view.getId()) {
                        case R.id.car_camera_brightness_down /* 2131034134 */:
                            int progress = ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_brightness_level)).getProgress() - 1;
                            if (progress <= 0) {
                                progress = 0;
                            }
                            ((TextView) CarCameraView.this.mView.findViewById(R.id.car_camera_brightness_title)).setText(ReverseActivity.this.getString(R.string.brightness) + "(" + progress + ")");
                            ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_brightness_level)).setProgress(progress);
                            ReverseActivity.this.mCarCameraBringtness = progress;
                            if (TWTW.mCType != 4 || (TWTW.mModel != 22 && TWTW.mModel != 17)) {
                                ReverseActivity.this.mTW.ssWrite(198, 71, ReverseActivity.this.mCarCameraBringtness + 30);
                            } else {
                                ReverseActivity.this.mTW.ssWrite(242, 10, (ReverseActivity.this.mCarCameraBringtness + 30) - 1);
                            }
                            break;
                        case R.id.car_camera_brightness_up /* 2131034137 */:
                            int progress2 = ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_brightness_level)).getProgress() + 1;
                            if (progress2 > CarCameraView.MAX) {
                                progress2 = CarCameraView.MAX;
                            }
                            ((TextView) CarCameraView.this.mView.findViewById(R.id.car_camera_brightness_title)).setText(ReverseActivity.this.getString(R.string.brightness) + "(" + progress2 + ")");
                            ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_brightness_level)).setProgress(progress2);
                            ReverseActivity.this.mCarCameraBringtness = progress2;
                            if (TWTW.mCType != 4 || (TWTW.mModel != 22 && TWTW.mModel != 17)) {
                                ReverseActivity.this.mTW.ssWrite(198, 71, ReverseActivity.this.mCarCameraBringtness + 30);
                            } else {
                                ReverseActivity.this.mTW.ssWrite(242, 10, ReverseActivity.this.mCarCameraBringtness + 30 + 1);
                            }
                            break;
                        case R.id.car_camera_contrast_down /* 2131034138 */:
                            int progress3 = ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_contrast_level)).getProgress() - 1;
                            if (progress3 < 0) {
                                progress3 = 0;
                            }
                            ((TextView) CarCameraView.this.mView.findViewById(R.id.car_camera_contrast_title)).setText(ReverseActivity.this.getString(R.string.contrast) + "(" + progress3 + ")");
                            ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_contrast_level)).setProgress(progress3);
                            ReverseActivity.this.mCarCameraContrast = progress3;
                            if (TWTW.mCType != 4 || (TWTW.mModel != 22 && TWTW.mModel != 17)) {
                                ReverseActivity.this.mTW.ssWrite(198, 72, ReverseActivity.this.mCarCameraContrast + 30);
                            } else {
                                ReverseActivity.this.mTW.ssWrite(242, 11, (ReverseActivity.this.mCarCameraContrast + 30) - 1);
                            }
                            break;
                        case R.id.car_camera_contrast_up /* 2131034141 */:
                            int progress4 = ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_contrast_level)).getProgress() + 1;
                            if (progress4 > CarCameraView.MAX) {
                                progress4 = CarCameraView.MAX;
                            }
                            ((TextView) CarCameraView.this.mView.findViewById(R.id.car_camera_contrast_title)).setText(ReverseActivity.this.getString(R.string.contrast) + "(" + progress4 + ")");
                            ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_contrast_level)).setProgress(progress4);
                            ReverseActivity.this.mCarCameraContrast = progress4;
                            if (TWTW.mCType != 4 || (TWTW.mModel != 22 && TWTW.mModel != 17)) {
                                ReverseActivity.this.mTW.ssWrite(198, 72, ReverseActivity.this.mCarCameraContrast + 30);
                            } else {
                                ReverseActivity.this.mTW.ssWrite(242, 11, ReverseActivity.this.mCarCameraContrast + 30 + 1);
                            }
                            break;
                        case R.id.car_camera_saturation_down /* 2131034142 */:
                            int progress5 = ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_saturation_level)).getProgress() - 1;
                            if (progress5 < 0) {
                                progress5 = 0;
                            }
                            ((TextView) CarCameraView.this.mView.findViewById(R.id.car_camera_saturation_title)).setText(ReverseActivity.this.getString(R.string.saturation) + "(" + progress5 + ")");
                            ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_saturation_level)).setProgress(progress5);
                            ReverseActivity.this.mCarCameraSaturation = progress5;
                            if (TWTW.mCType != 4 || (TWTW.mModel != 22 && TWTW.mModel != 17)) {
                                ReverseActivity.this.mTW.ssWrite(198, 73, ReverseActivity.this.mCarCameraSaturation + 30);
                            } else {
                                ReverseActivity.this.mTW.ssWrite(242, 12, (ReverseActivity.this.mCarCameraSaturation + 30) - 1);
                            }
                            break;
                        case R.id.car_camera_saturation_up /* 2131034145 */:
                            int progress6 = ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_saturation_level)).getProgress() + 1;
                            if (progress6 > CarCameraView.MAX) {
                                progress6 = CarCameraView.MAX;
                            }
                            ((TextView) CarCameraView.this.mView.findViewById(R.id.car_camera_saturation_title)).setText(ReverseActivity.this.getString(R.string.saturation) + "(" + progress6 + ")");
                            ((SeekBar) CarCameraView.this.mView.findViewById(R.id.car_camera_saturation_level)).setProgress(progress6);
                            ReverseActivity.this.mCarCameraSaturation = progress6;
                            if (TWTW.mCType != 4 || (TWTW.mModel != 22 && TWTW.mModel != 17)) {
                                ReverseActivity.this.mTW.ssWrite(198, 73, ReverseActivity.this.mCarCameraSaturation + 30);
                            } else {
                                ReverseActivity.this.mTW.ssWrite(242, 12, ReverseActivity.this.mCarCameraSaturation + 30 + 1);
                            }
                            break;
                    }
                    ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAR_CAMERA_HIDE);
                    ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAR_CAMERA_HIDE, 5000L);
                }
            };
            this.mView.findViewById(R.id.car_camera_brightness_down).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.car_camera_brightness_up).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.car_camera_contrast_down).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.car_camera_contrast_up).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.car_camera_saturation_down).setOnClickListener(onClickListener);
            this.mView.findViewById(R.id.car_camera_saturation_up).setOnClickListener(onClickListener);
            this.mWindowManager = (WindowManager) ReverseActivity.this.getSystemService("window");
            this.mLayoutParams = new WindowManager.LayoutParams((int) (ReverseActivity.this.density * 450.0f), (int) (ReverseActivity.this.density * 290.0f), 0, 0, 2038, MAX, 1);
        }

        public void show() {
            ReverseActivity.this.mCameraView.hide();
            if (!this.isShow) {
                int brightness = ReverseActivity.this.mCarCameraBringtness;
                ((TextView) this.mView.findViewById(R.id.car_camera_brightness_title)).setText(ReverseActivity.this.getString(R.string.brightness) + "(" + brightness + ")");
                ((SeekBar) this.mView.findViewById(R.id.car_camera_brightness_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.car_camera_brightness_level)).setProgress(brightness);
                int contrast = ReverseActivity.this.mCarCameraContrast;
                ((TextView) this.mView.findViewById(R.id.car_camera_contrast_title)).setText(ReverseActivity.this.getString(R.string.contrast) + "(" + contrast + ")");
                ((SeekBar) this.mView.findViewById(R.id.car_camera_contrast_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.car_camera_contrast_level)).setProgress(contrast);
                int saturation = ReverseActivity.this.mCarCameraSaturation;
                ((TextView) this.mView.findViewById(R.id.car_camera_saturation_title)).setText(ReverseActivity.this.getString(R.string.saturation) + "(" + saturation + ")");
                ((SeekBar) this.mView.findViewById(R.id.car_camera_saturation_level)).setMax(MAX);
                ((SeekBar) this.mView.findViewById(R.id.car_camera_saturation_level)).setProgress(saturation);
                this.isShow = true;
                this.mWindowManager.addView(this.mView, this.mLayoutParams);
                ReverseActivity.this.mHandler.removeMessages(ReverseActivity.CAR_CAMERA_HIDE);
                ReverseActivity.this.mHandler.sendEmptyMessageDelayed(ReverseActivity.CAR_CAMERA_HIDE, 8000L);
            }
        }

        public void hide() {
            if (this.isShow) {
                this.mWindowManager.removeView(this.mView);
                this.isShow = false;
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mTW.close();
        this.mTW = null;
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openCamera() {
        try {
            SystemProperties.set("sys.camera.ch", "0");
            this.mCamera = Camera.open(this.cameraId);
            int brightness = SystemProperties.getInt("persist.camera.brightness0", 128);
            int contrast = SystemProperties.getInt("persist.camera.contrast0", 128);
            int saturation = SystemProperties.getInt("persist.camera.saturation0", 128);
            try {
                Camera.Parameters parameters = this.mCamera.getParameters();
                parameters.setBrightness(String.format(Locale.US, "%d", Integer.valueOf(brightness)));
                this.mCamera.setParameters(parameters);
                parameters.setContrast(String.format(Locale.US, "%d", Integer.valueOf(contrast)));
                this.mCamera.setParameters(parameters);
                parameters.setSaturation(String.format(Locale.US, "%d", Integer.valueOf(saturation)));
                this.mCamera.setParameters(parameters);
                Log.d(TAG, "openCamera OK");
            } catch (Exception e) {
                Log.d(TAG, "openCamera Exception 1:" + e.getMessage());
            }
            this.mHandler.sendEmptyMessageDelayed(START_PREVIEW, Settings.System.getInt(getContentResolver(), "REVERSE_DELAY", 0));
        } catch (Exception e2) {
            Log.e(TAG, "openCamera Exception 2:" + e2.getMessage());
            this.mHandler.removeMessages(REOPEN_CAMERA);
            this.mHandler.sendEmptyMessageDelayed(REOPEN_CAMERA, 200L);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        setStateBarNavigationHide();
        this.mTW = TWTW.open();
        TWTW twtw = this.mTW;
        if (twtw != null) {
            twtw.addHandler(TAG, this.mHandler);
            this.mTW.write(266, 255);
        }
        this.mTW.write(REQUEST_REVERSE, 1);
        openCamera();
        this.mHandler.removeMessages(REVERSE_CHECK);
        this.mHandler.sendEmptyMessageDelayed(REVERSE_CHECK, 700L);
        if (Settings.System.getInt(getContentResolver(), "SYSTEM_LINE", 0) != 0) {
            if (mDrawable == null) {
                try {
                    mDrawable = getResources().getDrawable(R.drawable.reverse_l_bg);
                } catch (Exception e) {
                }
            }
            if (mDrawable != null) {
                if (Settings.System.getInt(getContentResolver(), "SYSTEM_LINE_DIRECTION", 0) == 0) {
                    Drawable drawable = mDrawable;
                    int i = this.mSW;
                    drawable.setLevel(i > 24 ? (int) Math.ceil(((double) i) / 4.0d) : i / 4);
                } else {
                    Drawable drawable2 = mDrawable;
                    int i2 = this.mSW;
                    drawable2.setLevel(i2 < 24 ? (int) Math.ceil(((double) (48 - i2)) / 4.0d) : (48 - i2) / 4);
                }
                findViewById(R.id.lreverse).setBackground(mDrawable);
            }
        }
        if (Settings.System.getInt(getContentResolver(), "SYSTEM_LINE2", 0) != 0) {
            this.rl_reverse2.setVisibility(0);
        }
        if (getIntent() != null) {
            this.iskeypad = getIntent().getIntExtra("iskeypad", 0);
        }
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeCamera() {
        if (this.mCamera != null) {
            try {
                this.mHandler.removeMessages(START_PREVIEW);
                this.mCamera.stopPreview();
            } catch (Exception e) {
                Log.e(TAG, "closeCamera Exception:" + e.getMessage());
            }
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        this.iskeypad = 0;
        this.mTW.removeHandler(TAG);
        this.mHandler.removeMessages(CAMERA_HIDE);
        this.mCameraView.hide();
        this.mShowAHDTip.hide();
        this.mHandler.removeMessages(REVERSE_CHECK);
        this.mHandler.removeMessages(CLOSE);
        this.mHandler.removeMessages(OPEN);
        this.mHandler.removeMessages(SIGNAL);
        this.mHandler.removeMessages(REOPEN_CAMERA);
        setPreview(false);
        this.mWarningImage.setVisibility(8);
        this.mWarningText.setVisibility(8);
        closeCamera();
        this.mTW.write(REQUEST_REVERSE, 0);
        super.onPause();
    }

    public void touch(int x, int y) {
        int x2 = (x * 255) / (this.w - 1);
        int x3 = y * 255;
        int y2 = x3 / (this.h - 1);
        this.mTW.write(522, 0, (y2 << 8) | x2);
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int actionMasked = event.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                touch(x, y);
            } else if (actionMasked != 2) {
            }
        } else if (x > 170) {
            try {
                if (x < this.w - 130) {
                    if (!this.mCameraView.isShow) {
                        if (this.mCamera_settings.getVisibility() == 0) {
                            this.mCamera_settings.setVisibility(8);
                        } else {
                            this.mCamera_settings.setVisibility(0);
                        }
                    }
                    if (TWTW.mCType == 4 && (TWTW.mModel == 22 || TWTW.mModel == 17)) {
                        if (this.mBReverse5.getVisibility() == 0) {
                            this.mReverse.setVisibility(8);
                            this.mBReverse5.setVisibility(8);
                        } else {
                            this.mBReverse5.setVisibility(0);
                            this.mBReverse4.setVisibility(8);
                            this.mReverse.setVisibility(0);
                        }
                    } else if (TWTW.mModel == 22) {
                        if (this.mBReverse4.getVisibility() == 0) {
                            this.mReverse.setVisibility(8);
                            this.mBReverse4.setVisibility(8);
                        } else {
                            this.mBReverse4.setVisibility(0);
                            this.mReverse.setVisibility(0);
                            if (TWTW.mCType == 1) {
                                this.mTW.ssWrite(144, 64, 64);
                            } else {
                                this.mTW.ssWrite(144, 64, 208);
                            }
                        }
                    } else if (TWTW.mModel == 18 && TWTW.mCType == 4) {
                        if (this.mBReverse.getVisibility() == 0) {
                            this.mBReverse.setVisibility(8);
                        } else {
                            this.mReverse.setVisibility(0);
                            this.mBReverse.setVisibility(0);
                        }
                    } else if (TWTW.mModel == 34 || TWTW.mModel == 35 || TWTW.mModel == 38 || TWTW.mModel == 39) {
                        if (this.mBReverse3.getVisibility() == 0) {
                            this.mReverse.setVisibility(8);
                            this.mBReverse3.setVisibility(8);
                        } else {
                            this.mReverse.setVisibility(0);
                            this.mBReverse3.setVisibility(0);
                        }
                    } else if (this.mRadar >= 0) {
                        int i = this.mRadar + 1;
                        this.mRadar = i;
                        if (i > 1) {
                            this.mRadar = 0;
                        }
                        this.mReverse.setVisibility(this.mRadar == 1 ? 0 : 8);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return super.onTouchEvent(event);
    }

    public void onClick(View v) {
        Log.d(TAG, "onClick2");
        int id = v.getId();
        switch (id) {
            case R.id.btn_adjust /* 2131034130 */:
                if (this.btnState == 1) {
                    this.btnAdjust.setText(getString(R.string.save));
                    this.btnReset.setVisibility(0);
                    this.reverseLineRuler.adjustLineRuler();
                    this.btnState = 0;
                } else {
                    this.btnAdjust.setText(getString(R.string.adjust));
                    this.reverseLineRuler.saveLineRuler();
                    this.btnReset.setVisibility(8);
                    this.btnState = 1;
                }
                break;
            case R.id.btn_reset /* 2131034131 */:
                this.reverseLineRuler.resetLineRuler();
                break;
            case R.id.camera_settings /* 2131034132 */:
                if (this.mCameraView.isShow) {
                    this.mCameraView.hide();
                } else {
                    this.mCameraView.show();
                }
                break;
            default:
                switch (id) {
                    case R.id.flayout_ahd /* 2131034158 */:
                    case R.id.flayout_common /* 2131034159 */:
                        if (this.showDetecting) {
                            Log.d(TAG, "showDetecting");
                        } else {
                            Log.d(TAG, "closeCamera1");
                            this.mHandler.removeMessages(CLOSE);
                            this.mHandler.removeMessages(OPEN);
                            this.mHandler.removeMessages(SIGNAL);
                            this.mHandler.removeMessages(SHOWTIP);
                            setPreview(false);
                            this.mWarningImage.setVisibility(8);
                            this.mWarningText.setVisibility(8);
                            closeCamera();
                            Log.d(TAG, "openCamera1");
                            openCamera();
                            this.mHandler.sendEmptyMessageDelayed(CLOSE, 2000L);
                            this.mHandler.sendEmptyMessageDelayed(SHOWTIP, 4000L);
                            showLayoutDetecting(true);
                        }
                        break;
                    default:
                        switch (id) {
                            case R.id.reverse3_close /* 2131034181 */:
                                this.mBReverse3.setVisibility(8);
                                break;
                            case R.id.reverse4_close /* 2131034182 */:
                                this.mBReverse4.setVisibility(4);
                                break;
                            case R.id.reverse4_display_set /* 2131034183 */:
                            case R.id.reverse5_display_set /* 2131034190 */:
                                if (this.mCarCameraView == null) {
                                    this.mCarCameraView = new CarCameraView();
                                }
                                if (TWTW.mCType == 1) {
                                    this.mTW.ssWrite(144, 64, 176);
                                } else {
                                    this.mTW.ssWrite(144, 64, 208);
                                }
                                this.mTW.ssWrite(144, 64, 176);
                                if (this.mCarCameraView.isShow) {
                                    this.mCarCameraView.hide();
                                } else {
                                    this.mCarCameraView.show();
                                }
                                break;
                            case R.id.reverse4_mode1 /* 2131034184 */:
                                this.mTW.ssWrite(198, 70, 0);
                                break;
                            case R.id.reverse4_mode2 /* 2131034185 */:
                                this.mTW.ssWrite(198, 70, 1);
                                break;
                            case R.id.reverse4_mode3 /* 2131034186 */:
                                this.mTW.ssWrite(198, 70, 2);
                                break;
                            case R.id.reverse4_mode4 /* 2131034187 */:
                                this.mTW.ssWrite(198, 70, 3);
                                break;
                            case R.id.reverse4_voice /* 2131034188 */:
                                this.mTW.ssWrite(198, 171, this.mRadarVoice != 1 ? 1 : 0);
                                break;
                            case R.id.reverse5_close /* 2131034189 */:
                                this.mBReverse5.setVisibility(4);
                                break;
                            case R.id.reverse5_mode1 /* 2131034191 */:
                                this.mTW.ssWrite(242, 1, 255);
                                break;
                            case R.id.reverse5_mode2 /* 2131034192 */:
                                this.mTW.ssWrite(242, 2, 255);
                                break;
                            case R.id.reverse5_mode3 /* 2131034193 */:
                                this.mTW.ssWrite(242, 3, 255);
                                break;
                            case R.id.reverse5_mode4 /* 2131034194 */:
                                this.mTW.ssWrite(242, 9, 255);
                                break;
                            default:
                                switch (id) {
                                    case R.id.reverse_close /* 2131034196 */:
                                        if (TWTW.mModel == 16 || TWTW.mModel == 18 || TWTW.mModel == 19) {
                                            this.mTW.ssWrite(198, 1, 0);
                                        } else {
                                            this.mTW.write(1287, 0);
                                        }
                                        break;
                                    case R.id.reverse_fv /* 2131034197 */:
                                        if (TWTW.mModel == 113) {
                                            this.mTW.ssWrite(198, 2, 1);
                                            this.mTW.ssWrite(199, 1);
                                        }
                                        break;
                                    case R.id.reverse_mode /* 2131034198 */:
                                        int i = this.mMode + 1;
                                        this.mMode = i;
                                        if (i > 1) {
                                            this.mMode = 0;
                                        }
                                        if (TWTW.mModel == 16 || TWTW.mModel == 18 || TWTW.mModel == 19) {
                                            this.mTW.ssWrite(198, 2, this.mMode);
                                        }
                                        break;
                                    case R.id.reverse_ol /* 2131034199 */:
                                        if (TWTW.mModel == 33 || TWTW.mModel == 34 || TWTW.mModel == 35 || TWTW.mModel == 38 || TWTW.mModel == 39) {
                                            if (TWTW.mCType == 4) {
                                                this.mTW.ssWrite(242, 3, 255);
                                            } else if (TWTW.mCType == 8) {
                                                if (TWTW.mModel == 38) {
                                                    this.mTW.write(1281, 132, 1, new byte[]{2});
                                                } else if (TWTW.mModel == 35) {
                                                    this.mTW.write(1281, 138, 1, new byte[]{2});
                                                }
                                            } else {
                                                this.mTW.ssWrite(198, 64, 2);
                                            }
                                        }
                                        break;
                                    default:
                                        switch (id) {
                                            case R.id.reverse_std /* 2131034201 */:
                                                if (TWTW.mModel == 33 || TWTW.mModel == 34 || TWTW.mModel == 35 || TWTW.mModel == 38 || TWTW.mModel == 39) {
                                                    if (TWTW.mCType == 4) {
                                                        this.mTW.ssWrite(242, 1, 255);
                                                    } else if (TWTW.mCType == 8) {
                                                        if (TWTW.mModel == 38) {
                                                            this.mTW.write(1281, 132, 1, new byte[]{0});
                                                        } else if (TWTW.mModel == 35) {
                                                            this.mTW.write(1281, 138, 1, new byte[]{0});
                                                        }
                                                    } else {
                                                        this.mTW.ssWrite(198, 64, 0);
                                                    }
                                                }
                                                break;
                                            case R.id.reverse_switch /* 2131034202 */:
                                                break;
                                            default:
                                                switch (id) {
                                                    case R.id.reverse_voice /* 2131034207 */:
                                                        if (TWTW.mModel == 16 || TWTW.mModel == 18 || TWTW.mModel == 19) {
                                                            this.mTW.ssWrite(198, 0, this.mRadarVoice == 1 ? 0 : 1);
                                                        } else if (TWTW.mModel == 17 || TWTW.mModel == 22) {
                                                            this.mTW.ssWrite(198, 171, this.mRadarVoice != 1 ? 1 : 0);
                                                        }
                                                        break;
                                                    case R.id.reverse_wa /* 2131034208 */:
                                                        if (TWTW.mModel == 33 || TWTW.mModel == 34 || TWTW.mModel == 35 || TWTW.mModel == 38 || TWTW.mModel == 39) {
                                                            if (TWTW.mCType == 4) {
                                                                this.mTW.ssWrite(242, 2, 255);
                                                            } else if (TWTW.mCType == 8) {
                                                                if (TWTW.mModel == 38) {
                                                                    this.mTW.write(1281, 132, 1, new byte[]{1});
                                                                } else if (TWTW.mModel == 35) {
                                                                    this.mTW.write(1281, 138, 1, new byte[]{1});
                                                                }
                                                            } else {
                                                                this.mTW.ssWrite(198, 64, 1);
                                                            }
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLayoutDetecting(boolean show) {
        this.showDetecting = show;
        findViewById(R.id.layout_detecting).setVisibility(show ? 0 : 8);
    }

    private class ShowAHDTip {
        private WindowManager.LayoutParams mLayoutParams;
        private View mView;
        private WindowManager mWindowManager;
        private boolean isShow = false;
        private boolean cancel = false;

        public ShowAHDTip() {
            this.mView = ((LayoutInflater) ReverseActivity.this.getSystemService("layout_inflater")).inflate(R.layout.camera_alarm_user, (ViewGroup) null);
            ((TextView) this.mView.findViewById(R.id.tv_camera_alarm_user)).setText(Html.fromHtml(ReverseActivity.this.getResources().getString(R.string.camera_alarm_user)));
            this.mView.findViewById(R.id.tv_camera_alarm_user_ok).setOnClickListener(new View.OnClickListener() { // from class: com.tw.reverse.ReverseActivity.ShowAHDTip.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    Log.d(ReverseActivity.TAG, "onClick3");
                    ShowAHDTip.this.hide();
                    if (ShowAHDTip.this.cancel) {
                        ReverseActivity.this.mCameraView.close();
                        ReverseActivity.this.mCameraView.show();
                    }
                }
            });
            this.mView.findViewById(R.id.tv_camera_alarm_user_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tw.reverse.ReverseActivity.ShowAHDTip.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    Log.d(ReverseActivity.TAG, "onClick4");
                    ShowAHDTip.this.hide();
                    if (ShowAHDTip.this.cancel) {
                        ReverseActivity.this.mCameraView.show();
                    }
                }
            });
            this.mWindowManager = (WindowManager) ReverseActivity.this.getSystemService("window");
            this.mLayoutParams = new WindowManager.LayoutParams((int) (ReverseActivity.this.density * 450.0f), (int) (ReverseActivity.this.density * 300.0f), 0, 0, 2005, 40, 1);
        }

        public void show(boolean c) {
            if (this.cancel != c) {
                this.cancel = c;
                if (this.isShow) {
                    hide();
                }
                this.mView.findViewById(R.id.tv_camera_alarm_user_cancel).setVisibility(c ? 0 : 8);
            }
            if (!this.isShow) {
                this.isShow = true;
                this.mWindowManager.addView(this.mView, this.mLayoutParams);
            }
        }

        public void hide() {
            if (this.isShow) {
                this.mWindowManager.removeView(this.mView);
                this.isShow = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSW1(int sw) {
        int sw2 = (sw / (-405)) + 24;
        if (sw2 > 48) {
            sw2 = 48;
        } else if (sw2 < 0) {
            sw2 = 0;
        }
        setSW(sw2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSW2(int sw) {
        int sw2 = (sw / (-826)) + 24;
        if (sw2 > 48) {
            sw2 = 48;
        } else if (sw2 < 0) {
            sw2 = 0;
        }
        setSW(sw2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSW3(int sw) {
        int sw2 = (sw / 522) + 24;
        if (sw2 > 48) {
            sw2 = 48;
        } else if (sw2 < 0) {
            sw2 = 0;
        }
        setSW(sw2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSW4(int sw) {
        int sw2 = (sw / 22) + 24;
        if (sw2 > 48) {
            sw2 = 48;
        } else if (sw2 < 0) {
            sw2 = 0;
        }
        setSW(sw2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSW5(int sw) {
        int sw2 = (sw / (-192)) + 24;
        if (sw2 > 48) {
            sw2 = 48;
        } else if (sw2 < 0) {
            sw2 = 0;
        }
        setSW(sw2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSW6(int sw) {
        int sw2 = (sw / 2) + 24;
        if (sw2 > 48) {
            sw2 = 48;
        } else if (sw2 < 0) {
            sw2 = 0;
        }
        setSW(sw2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSW7(int sw) {
        int sw2 = (sw / 15) + 24;
        if (sw2 > 48) {
            sw2 = 48;
        } else if (sw2 < 0) {
            sw2 = 0;
        }
        setSW(sw2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSW8(int sw) {
        int sw2 = (sw / 325) + 24;
        if (sw2 > 48) {
            sw2 = 48;
        } else if (sw2 < 0) {
            sw2 = 0;
        }
        setSW(sw2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSW(int sw, int max) {
        int sw2 = (((-sw) * 24) / max) + 24;
        if (sw2 > 48) {
            sw2 = 48;
        } else if (sw2 < 0) {
            sw2 = 0;
        }
        setSW(sw2);
    }

    private void setSW(int sw) {
        if (this.mSW != sw) {
            this.mSW = sw;
            if (mDrawable != null) {
                if (Settings.System.getInt(getContentResolver(), "SYSTEM_LINE_DIRECTION", 0) == 0) {
                    Drawable drawable = mDrawable;
                    int i = this.mSW;
                    drawable.setLevel(i > 24 ? (int) Math.ceil(((double) i) / 4.0d) : i / 4);
                } else {
                    Drawable drawable2 = mDrawable;
                    int i2 = this.mSW;
                    drawable2.setLevel(i2 < 24 ? (int) Math.ceil(((double) (48 - i2)) / 4.0d) : (48 - i2) / 4);
                }
            }
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
        TWTW twtw = this.mTW;
        if (twtw == null) {
            return 0;
        }
        int signal = twtw.write(40733);
        return signal;
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
            Log.e(TAG, "surfaceCreated Exception:" + e.getMessage());
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.mCamera != null) {
            this.mHandler.removeMessages(START_PREVIEW);
            this.mCamera.stopPreview();
        }
    }

    public void reverse4modeRefresh(int mode) {
        ((ImageView) findViewById(R.id.reverse4_mode1)).getDrawable().setLevel(0);
        ((ImageView) findViewById(R.id.reverse4_mode2)).getDrawable().setLevel(0);
        ((ImageView) findViewById(R.id.reverse4_mode3)).getDrawable().setLevel(0);
        ((ImageView) findViewById(R.id.reverse4_mode4)).getDrawable().setLevel(0);
        if (mode != 0) {
            if (mode == 1) {
                ((ImageView) findViewById(R.id.reverse4_mode2)).getDrawable().setLevel(1);
                return;
            } else if (mode == 2) {
                ((ImageView) findViewById(R.id.reverse4_mode3)).getDrawable().setLevel(1);
                return;
            } else {
                if (mode == 3) {
                    ((ImageView) findViewById(R.id.reverse4_mode4)).getDrawable().setLevel(1);
                    return;
                }
                return;
            }
        }
        ((ImageView) findViewById(R.id.reverse4_mode1)).getDrawable().setLevel(1);
    }

    public void reverse5modeRefresh(int mode) {
        ((ImageView) findViewById(R.id.reverse5_mode1)).getDrawable().setLevel(0);
        ((ImageView) findViewById(R.id.reverse5_mode2)).getDrawable().setLevel(0);
        ((ImageView) findViewById(R.id.reverse5_mode3)).getDrawable().setLevel(0);
        ((ImageView) findViewById(R.id.reverse5_mode4)).getDrawable().setLevel(0);
        if (mode == 1) {
            ((ImageView) findViewById(R.id.reverse5_mode1)).getDrawable().setLevel(1);
            return;
        }
        if (mode == 2) {
            ((ImageView) findViewById(R.id.reverse5_mode2)).getDrawable().setLevel(1);
        } else if (mode == 3) {
            ((ImageView) findViewById(R.id.reverse5_mode3)).getDrawable().setLevel(1);
        } else if (mode == 9) {
            ((ImageView) findViewById(R.id.reverse5_mode4)).getDrawable().setLevel(1);
        }
    }

    private String getSystemVersion() {
        String s = "";
        try {
            s = SystemProperties.get("ro.tw.version");
            BufferedReader br = null;
            try {
                try {
                    br = new BufferedReader(new FileReader("/system/etc/version"));
                    String v2 = br.readLine();
                    if (v2 != null && v2.length() > 0) {
                        s = v2;
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    if (br != null) {
                    }
                    return s;
                }
                br.close();
            } catch (Throwable th) {
                if (br != null) {
                    br.close();
                }
                throw th;
            }
        } catch (Exception e2) {
            Log.e(TAG, e2.getMessage());
        }
        return s;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTrackLineState() {
        int trackLineDiaplay = Settings.System.getInt(getContentResolver(), "TRACK_LINE_CONTROL_DISPLAY", -1);
        if (Settings.System.getInt(getContentResolver(), "SYSTEM_LINE", 0) != 0) {
            if (trackLineDiaplay == -1) {
                findViewById(R.id.lreverse).setVisibility(0);
                return;
            } else if (trackLineDiaplay == 0) {
                findViewById(R.id.lreverse).setVisibility(8);
                return;
            } else {
                if (trackLineDiaplay == 1) {
                    findViewById(R.id.lreverse).setVisibility(0);
                    return;
                }
                return;
            }
        }
        findViewById(R.id.lreverse).setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeReverseViewMode(int mode) {
        if (this.reverse_view_mode1 == null) {
            return;
        }
        isFRadar = Settings.System.getInt(getContentResolver(), "SYSTEM_FRADAR", 0) != 0;
        if (mode != 0 && (isFRadar || this.mRadar != 1)) {
            this.mRReverse.setVisibility(8);
        } else {
            this.mRReverse.setVisibility(0);
        }
        Settings.System.putInt(getContentResolver(), "REVERSING_MODE", mode);
        int i = this.widthPixels;
        if (mode == 0) {
            int radarViewWidth = (int) (i * 0.26f);
            this.mlp_radar_view = new RelativeLayout.LayoutParams(-2, -1);
            RelativeLayout.LayoutParams layoutParams = this.mlp_radar_view;
            layoutParams.width = radarViewWidth;
            layoutParams.addRule(11, -1);
            this.mlp_radar_view.setMargins(0, 0, 0, 0);
            this.mRReverse.setLayoutParams(this.mlp_radar_view);
            this.mlp_revese_view = new RelativeLayout.LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams2 = this.mlp_revese_view;
            layoutParams2.width = -1;
            layoutParams2.addRule(0, R.id.rreverse);
            this.rl_reverse.setLayoutParams(this.mlp_revese_view);
            this.reverse_view_mode1.getDrawable().setLevel(1);
            this.reverse_view_mode2.getDrawable().setLevel(0);
            this.reverse_view_mode3.getDrawable().setLevel(0);
            return;
        }
        if (mode == 1) {
            int reverseViewWidth = this.widthPixels;
            this.mlp_radar_view = new RelativeLayout.LayoutParams(-2, -1);
            this.mlp_radar_view.addRule(9);
            this.mlp_radar_view.setMargins(0, 0, 0, 0);
            this.mRReverse.setLayoutParams(this.mlp_radar_view);
            if (Settings.System.getInt(getContentResolver(), "RADAR_DYNAMIC", 0) != 0) {
                this.mRReverse.setVisibility(8);
            } else {
                this.mRReverse.setVisibility(0);
            }
            this.mlp_revese_view = new RelativeLayout.LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams3 = this.mlp_revese_view;
            layoutParams3.width = reverseViewWidth;
            this.rl_reverse.setLayoutParams(layoutParams3);
            this.reverse_view_mode1.getDrawable().setLevel(0);
            this.reverse_view_mode2.getDrawable().setLevel(1);
            this.reverse_view_mode3.getDrawable().setLevel(0);
            return;
        }
        if (mode == 2) {
            this.mlp_radar_view = new RelativeLayout.LayoutParams(-2, -1);
            this.mlp_radar_view.addRule(5, R.id.rl_reverse);
            this.mRReverse.setLayoutParams(this.mlp_radar_view);
            if (Settings.System.getInt(getContentResolver(), "RADAR_DYNAMIC", 0) != 0) {
                this.mRReverse.setVisibility(8);
            } else {
                this.mRReverse.setVisibility(0);
            }
            this.mlp_revese_view = new RelativeLayout.LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams4 = this.mlp_revese_view;
            layoutParams4.width = (int) ((this.heightPixels / 9.0f) * 16.0f);
            layoutParams4.addRule(13);
            this.rl_reverse.setLayoutParams(this.mlp_revese_view);
            this.reverse_view_mode1.getDrawable().setLevel(0);
            this.reverse_view_mode2.getDrawable().setLevel(0);
            this.reverse_view_mode3.getDrawable().setLevel(1);
        }
    }

    private void setStateBarNavigationHide() {
        getWindow().setFlags(1024, 1024);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(5126);
    }

    private void initView() {
        this.mCentre = findViewById(R.id.centre);
        this.mOuterLayer = (RelativeLayout) findViewById(R.id.outer_layer);
        this.mInnerLayer = (RelativeLayout) findViewById(R.id.inner_layer);
        this.mlayout_1 = (LinearLayout) findViewById(R.id.layout_1);
        this.mCentre.setOnTouchListener(this);
        this.mLeftMargin = Settings.System.getInt(getContentResolver(), "ReverseCarLeftMargin", 0);
        this.mUpperSpacing = Settings.System.getInt(getContentResolver(), "ReverseCarTopMargin", 96);
        RelativeLayout.LayoutParams mRReverseParams = (RelativeLayout.LayoutParams) this.mRReverse.getLayoutParams();
        mRReverseParams.setMargins(this.mLeftMargin, this.mUpperSpacing, 0, 0);
        mRReverseParams.addRule(10);
        this.mRReverse.setLayoutParams(mRReverseParams);
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() { // from class: com.tw.reverse.ReverseActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Settings.System.putInt(ReverseActivity.this.getContentResolver(), "ReverseCarLeftMargin", 0);
                Settings.System.putInt(ReverseActivity.this.getContentResolver(), "ReverseCarTopMargin", 96);
                ReverseActivity reverseActivity = ReverseActivity.this;
                reverseActivity.mLeftMargin = Settings.System.getInt(reverseActivity.getContentResolver(), "ReverseCarLeftMargin", 0);
                ReverseActivity reverseActivity2 = ReverseActivity.this;
                reverseActivity2.mUpperSpacing = Settings.System.getInt(reverseActivity2.getContentResolver(), "ReverseCarTopMargin", 0);
                ReverseActivity.this.setview();
            }
        });
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() { // from class: com.tw.reverse.ReverseActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Settings.System.putInt(ReverseActivity.this.getContentResolver(), "ReverseCarLeftMargin", ReverseActivity.this.mLeftMargin);
                Settings.System.putInt(ReverseActivity.this.getContentResolver(), "ReverseCarTopMargin", ReverseActivity.this.mUpperSpacing);
                RelativeLayout.LayoutParams mRReverseParams2 = (RelativeLayout.LayoutParams) ReverseActivity.this.mRReverse.getLayoutParams();
                mRReverseParams2.setMargins(ReverseActivity.this.mLeftMargin, ReverseActivity.this.mUpperSpacing, 0, 0);
                mRReverseParams2.addRule(10);
                ReverseActivity.this.mRReverse.setLayoutParams(mRReverseParams2);
                ReverseActivity.this.gone();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setview() {
        this.mOuterLayer.setVisibility(0);
        RelativeLayout.LayoutParams mInnerLayerParams = (RelativeLayout.LayoutParams) this.mInnerLayer.getLayoutParams();
        mInnerLayerParams.setMargins(this.mLeftMargin, this.mUpperSpacing, 0, 0);
        this.mInnerLayer.setLayoutParams(mInnerLayerParams);
        RelativeLayout.LayoutParams mRReverseParams = (RelativeLayout.LayoutParams) this.mRReverse.getLayoutParams();
        mRReverseParams.setMargins(this.mLeftMargin, this.mUpperSpacing, 0, 0);
        mRReverseParams.addRule(10);
        this.mRReverse.setLayoutParams(mRReverseParams);
    }

    public void visible() {
        this.mOuterLayer.setVisibility(0);
        this.mlayout_1.setVisibility(0);
        this.mCentre.setVisibility(0);
        this.mRReverse.setVisibility(0);
        setview();
    }

    public void gone() {
        this.mOuterLayer.setVisibility(8);
        this.mlayout_1.setVisibility(8);
        this.mCentre.setVisibility(8);
        this.mRReverse.setVisibility(0);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action != 0 && action != 1 && action == 2) {
            if (v.getId() == R.id.centre) {
                RelativeLayout.LayoutParams mInnerLayerParams = (RelativeLayout.LayoutParams) this.mInnerLayer.getLayoutParams();
                if (this.mLeftMargin + event.getX() + mInnerLayerParams.width < this.widthPixels - 1 && this.mLeftMargin + event.getX() > 0.0f) {
                    setmLeftMargin((int) (this.mLeftMargin + event.getX()));
                }
                if (this.mUpperSpacing + event.getY() + mInnerLayerParams.height < this.heightPixels - 1 && 0.0f < this.mUpperSpacing + event.getY()) {
                    setmUpperSpacing((int) (this.mUpperSpacing + event.getY()));
                }
            }
            setview();
            return false;
        }
        return false;
    }

    public void setmLeftMargin(int mLeftMargin) {
        this.mLeftMargin = mLeftMargin;
    }

    public void setmUpperSpacing(int mUpperSpacing) {
        this.mUpperSpacing = mUpperSpacing;
    }
}
