package com.tw.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

/* JADX INFO: renamed from: com.tw.service.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class HandlerC0037a extends Handler {
    private int HEIGHT;
    private WindowManager Hc;
    private float Mc;
    private float Nc;
    private int WIDTH;
    private Context mContext;
    private WindowManager.LayoutParams mLayoutParams;
    private View mView;
    private boolean Ic = false;
    private int Jc = -40;
    private int Kc = 0;
    private int Lc = 0;
    private int Oc = 0;
    private int Pc = 0;

    public HandlerC0037a(Context context) {
        int dimensionPixelOffset;
        int dimensionPixelOffset2;
        this.WIDTH = -1;
        this.HEIGHT = -1;
        this.Mc = 0.0f;
        this.Nc = 0.0f;
        this.mContext = context;
        Context context2 = this.mContext;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context2.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        this.Mc = displayMetrics.widthPixels;
        this.Nc = displayMetrics.heightPixels;
        StringBuilder sbE = b.a.a.a.a.e("getScreenSize: ");
        sbE.append(this.Mc);
        sbE.append("x");
        sbE.append(this.Nc);
        Log.d("AirPanel", sbE.toString());
        if (this.Mc > this.Nc) {
            if (this.Mc / this.Nc > 2.0f) {
                this.mView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.air_long, (ViewGroup) null);
                dimensionPixelOffset2 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tw_dp_w800);
            } else {
                this.mView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.air, (ViewGroup) null);
                dimensionPixelOffset2 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tw_dp_w1075);
            }
            this.WIDTH = dimensionPixelOffset2;
            dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tw_dp_h528);
        } else {
            this.mView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.air_portrait, (ViewGroup) null);
            this.WIDTH = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tw_dp_w1075);
            dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tw_dp_w528);
        }
        this.HEIGHT = dimensionPixelOffset;
        if (this.Hc == null) {
            this.Hc = (WindowManager) this.mContext.getSystemService("window");
        }
        this.mLayoutParams = new WindowManager.LayoutParams(this.WIDTH, this.HEIGHT, 0, 0, 2038, 40, 1);
        this.mLayoutParams.gravity = 17;
    }

    private void K(int i) {
        StringBuilder sb;
        Context context;
        int i2;
        String string;
        TextView textView = (TextView) this.mView.findViewById(R.id.air_rt);
        if (i == 127) {
            string = "";
        } else {
            if (this.Kc != 1) {
                if (this.Lc == 1) {
                    i = (int) (((double) (i - 32)) / 1.8d);
                }
                sb = new StringBuilder();
                sb.append(String.format(Locale.US, "%.1f", Integer.valueOf(i)));
                context = this.mContext;
                i2 = R.string.temperature;
            } else {
                if (this.Lc != 1) {
                    i = ((i * 9) / 5) + 32;
                }
                sb = new StringBuilder();
                sb.append(i);
                context = this.mContext;
                i2 = R.string.ftemperature;
            }
            sb.append(context.getString(i2));
            string = sb.toString();
        }
        textView.setText(string);
    }

    private void L(int i) {
        ((ImageView) this.mView.findViewById(R.id.air_wind_1)).getDrawable().setLevel(i > 0 ? 1 : 0);
        ((ImageView) this.mView.findViewById(R.id.air_wind_2)).getDrawable().setLevel(i > 1 ? 1 : 0);
        ((ImageView) this.mView.findViewById(R.id.air_wind_3)).getDrawable().setLevel(i > 2 ? 1 : 0);
        ((ImageView) this.mView.findViewById(R.id.air_wind_4)).getDrawable().setLevel(i > 3 ? 1 : 0);
        ((ImageView) this.mView.findViewById(R.id.air_wind_5)).getDrawable().setLevel(i > 4 ? 1 : 0);
        ((ImageView) this.mView.findViewById(R.id.air_wind_6)).getDrawable().setLevel(i > 5 ? 1 : 0);
        ((ImageView) this.mView.findViewById(R.id.air_wind_7)).getDrawable().setLevel(i > 6 ? 1 : 0);
        ((ImageView) this.mView.findViewById(R.id.air_wind_8)).getDrawable().setLevel(i > 7 ? 1 : 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002d, code lost:
    
        if (r1 != 5) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean Ua() {
        /*
            r4 = this;
            r0 = 65280(0xff00, float:9.1477E-41)
            r4.removeMessages(r0)
            android.content.Context r1 = r4.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            java.lang.String r2 = "AIR_POPUP_WINDOW"
            r3 = 1
            int r1 = android.provider.Settings.System.getInt(r1, r2, r3)
            if (r1 != 0) goto L17
            r4 = 0
            return r4
        L17:
            android.content.Context r1 = r4.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            int r1 = android.provider.Settings.System.getInt(r1, r2, r3)
            if (r1 == r3) goto L39
            r2 = 2
            if (r1 == r2) goto L36
            r2 = 3
            if (r1 == r2) goto L33
            r2 = 4
            if (r1 == r2) goto L30
            r2 = 5
            if (r1 == r2) goto L33
            goto L3e
        L30:
            r1 = 2000(0x7d0, double:9.88E-321)
            goto L3b
        L33:
            r1 = 4000(0xfa0, double:1.9763E-320)
            goto L3b
        L36:
            r1 = 6000(0x1770, double:2.9644E-320)
            goto L3b
        L39:
            r1 = 8000(0x1f40, double:3.9525E-320)
        L3b:
            r4.sendEmptyMessageDelayed(r0, r1)
        L3e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tw.service.HandlerC0037a.Ua():boolean");
    }

    private void a(int i, int i2, boolean z) {
        StringBuilder sb;
        Context context;
        int i3;
        String string;
        TextView textView = (TextView) this.mView.findViewById(i);
        if (i2 == 0) {
            string = "LO";
        } else if (i2 == 47) {
            string = "HI";
        } else {
            if (i2 < 1 || i2 > 46) {
                return;
            }
            float f = !z ? i2 + 60.0f : 14.0f + ((i2 - 1) * 0.5f);
            if (this.Kc != 1) {
                if (!z) {
                    f = (int) (((double) (f - 32.0f)) / 1.8d);
                }
                sb = new StringBuilder();
                sb.append(String.format(Locale.US, "%.1f", Float.valueOf(f)));
                context = this.mContext;
                i3 = R.string.temperature;
            } else {
                if (z) {
                    f = ((f * 9.0f) / 5.0f) + 32.0f;
                }
                sb = new StringBuilder();
                sb.append((int) f);
                context = this.mContext;
                i3 = R.string.ftemperature;
            }
            sb.append(context.getString(i3));
            string = sb.toString();
        }
        textView.setText(string);
    }

    private void b(int i, float f, boolean z) {
        StringBuilder sb;
        Context context;
        int i2;
        String string;
        TextView textView = (TextView) this.mView.findViewById(i);
        int i3 = (int) f;
        if (i3 == 0) {
            string = "LO";
        } else if (i3 == 255) {
            string = "HI";
        } else {
            if (z) {
                f -= 100.0f;
            }
            if (this.Kc != 1) {
                if (this.Lc == 1) {
                    f = (int) (((double) (f - 32.0f)) / 1.8d);
                }
                sb = new StringBuilder();
                sb.append(String.format(Locale.US, "%.1f", Float.valueOf(f)));
                context = this.mContext;
                i2 = R.string.temperature;
            } else {
                if (this.Lc != 1) {
                    f = ((f * 9.0f) / 5.0f) + 32.0f;
                }
                sb = new StringBuilder();
                sb.append((int) f);
                context = this.mContext;
                i2 = R.string.ftemperature;
            }
            sb.append(context.getString(i2));
            string = sb.toString();
        }
        textView.setText(string);
    }

    private void g(float f) {
        StringBuilder sb;
        Context context;
        int i;
        String string;
        TextView textView = (TextView) this.mView.findViewById(R.id.air_lt);
        int i2 = (int) f;
        if (i2 == 0) {
            string = "LO";
        } else if (i2 == 254) {
            string = "";
        } else if (i2 == 255) {
            string = "HI";
        } else {
            if (this.Kc != 1) {
                if (this.Lc == 1) {
                    f = (int) (((double) (f - 32.0f)) / 1.8d);
                }
                sb = new StringBuilder();
                sb.append(String.format(Locale.US, "%.1f", Float.valueOf(f)));
                context = this.mContext;
                i = R.string.temperature;
            } else {
                if (this.Lc != 1) {
                    f = ((f * 9.0f) / 5.0f) + 32.0f;
                }
                sb = new StringBuilder();
                sb.append((int) f);
                context = this.mContext;
                i = R.string.ftemperature;
            }
            sb.append(context.getString(i));
            string = sb.toString();
        }
        textView.setText(string);
    }

    private void hide() {
        if (this.Ic) {
            this.Hc.removeView(this.mView);
            this.Ic = false;
        }
    }

    private void l(int i, int i2) {
        if (this.Oc == i && this.Pc == i2) {
            return;
        }
        ((ImageView) this.mView.findViewById(R.id.air_heat_left_1)).getDrawable().setLevel(i > 0 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_left_1).setVisibility(i > 0 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_left_2)).getDrawable().setLevel(i > 1 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_left_2).setVisibility(i > 1 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_left_3)).getDrawable().setLevel(i > 2 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_left_3).setVisibility(i > 2 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_left_4)).getDrawable().setLevel(i > 3 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_left_4).setVisibility(i > 3 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_left_5)).getDrawable().setLevel(i > 4 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_left_5).setVisibility(i > 4 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_left_6)).getDrawable().setLevel(i > 5 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_left_6).setVisibility(i > 5 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_right_1)).getDrawable().setLevel(i2 > 0 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_right_1).setVisibility(i2 > 0 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_right_2)).getDrawable().setLevel(i2 > 1 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_right_2).setVisibility(i2 > 1 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_right_3)).getDrawable().setLevel(i2 > 2 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_right_3).setVisibility(i2 > 2 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_right_4)).getDrawable().setLevel(i2 > 3 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_right_4).setVisibility(i2 > 3 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_right_5)).getDrawable().setLevel(i2 > 4 ? 1 : 0);
        this.mView.findViewById(R.id.air_heat_right_5).setVisibility(i2 > 4 ? 0 : 4);
        ((ImageView) this.mView.findViewById(R.id.air_heat_right_6)).getDrawable().setLevel(i2 <= 5 ? 0 : 1);
        this.mView.findViewById(R.id.air_heat_right_6).setVisibility(i2 > 5 ? 0 : 4);
        removeMessages(65281);
        this.mView.findViewById(R.id.air_heat_seat).setVisibility(0);
        sendEmptyMessageDelayed(65281, 2000L);
        this.Oc = i;
        this.Pc = i2;
    }

    private void m(int i, int i2) {
        StringBuilder sb;
        Context context;
        int i3;
        String string;
        TextView textView = (TextView) this.mView.findViewById(i);
        if (i2 == 0) {
            string = "";
        } else {
            if (this.Kc != 1) {
                if (this.Lc == 1) {
                    i2 = (int) (((double) (i2 - 32)) / 1.8d);
                }
                sb = new StringBuilder();
                sb.append(String.format(Locale.US, "%.1f", Integer.valueOf(i2)));
                context = this.mContext;
                i3 = R.string.temperature;
            } else {
                if (this.Lc != 1) {
                    i2 = ((i2 * 9) / 5) + 32;
                }
                sb = new StringBuilder();
                sb.append(i2);
                context = this.mContext;
                i3 = R.string.ftemperature;
            }
            sb.append(context.getString(i3));
            string = sb.toString();
        }
        textView.setText(string);
    }

    private void n(int i, int i2) {
        float f;
        StringBuilder sb;
        Locale locale;
        Object[] objArr;
        StringBuilder sb2;
        Context context;
        int i3;
        String string;
        Context context2;
        int i4;
        TextView textView = (TextView) this.mView.findViewById(i);
        if (la.Tf == 2) {
            int i5 = la.mModel;
            if (i5 == 67) {
                if (i2 != 0) {
                    if (i2 != 63) {
                        if (i2 < 12 || i2 > 40) {
                            return;
                        }
                        f = ((i2 - 12) * 0.5f) + 15.5f;
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                f = (int) (((double) (f - 32.0f)) / 1.8d);
                            }
                            sb = new StringBuilder();
                            locale = Locale.US;
                            objArr = new Object[]{Float.valueOf(f)};
                            sb.append(String.format(locale, "%.1f", objArr));
                            context2 = this.mContext;
                            i4 = R.string.temperature;
                        } else {
                            if (this.Lc != 1) {
                                f = ((f * 9.0f) / 5.0f) + 32.0f;
                            }
                            sb = new StringBuilder();
                            sb.append((int) f);
                            context2 = this.mContext;
                            i4 = R.string.ftemperature;
                        }
                    }
                    textView.setText("HI");
                    return;
                }
                textView.setText("LO");
                return;
            }
            if (i5 == 147) {
                if (i2 != 0) {
                    if (i2 != 30) {
                        if (i2 < 1 || i2 > 29) {
                            return;
                        }
                        f = ((i2 - 1) * 0.5f) + 16.0f;
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                f = (int) (((double) (f - 32.0f)) / 1.8d);
                            }
                            sb = new StringBuilder();
                            locale = Locale.US;
                            objArr = new Object[]{Float.valueOf(f)};
                            sb.append(String.format(locale, "%.1f", objArr));
                            context2 = this.mContext;
                            i4 = R.string.temperature;
                        } else {
                            if (this.Lc != 1) {
                                f = ((f * 9.0f) / 5.0f) + 32.0f;
                            }
                            sb = new StringBuilder();
                            sb.append((int) f);
                            context2 = this.mContext;
                            i4 = R.string.ftemperature;
                        }
                    }
                    textView.setText("HI");
                    return;
                }
                textView.setText("LO");
                return;
            }
            if (i5 != -1) {
                if (i2 != 0) {
                    if (i2 != 30) {
                        if (i2 < 1 || i2 > 29) {
                            return;
                        }
                        f = ((i2 - 1) * 0.5f) + 14.0f;
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                f = (int) (((double) (f - 32.0f)) / 1.8d);
                            }
                            sb = new StringBuilder();
                            locale = Locale.US;
                            objArr = new Object[]{Float.valueOf(f)};
                            sb.append(String.format(locale, "%.1f", objArr));
                            context2 = this.mContext;
                            i4 = R.string.temperature;
                        } else {
                            if (this.Lc != 1) {
                                f = ((f * 9.0f) / 5.0f) + 32.0f;
                            }
                            sb = new StringBuilder();
                            sb.append((int) f);
                            context2 = this.mContext;
                            i4 = R.string.ftemperature;
                        }
                    }
                    textView.setText("HI");
                    return;
                }
                textView.setText("LO");
                return;
            }
            return;
        }
        int i6 = la.mModel;
        if (i6 == 17 || i6 == 22) {
            if (i2 != 0) {
                if (i2 != 31) {
                    if (i2 < 1 || i2 > 28) {
                        return;
                    }
                    f = ((i2 - 1) * 0.5f) + 16.0f;
                    if (this.Kc != 1) {
                        if (this.Lc == 1) {
                            f = (int) (((double) (f - 32.0f)) / 1.8d);
                        }
                        sb = new StringBuilder();
                        locale = Locale.US;
                        objArr = new Object[]{Float.valueOf(f)};
                        sb.append(String.format(locale, "%.1f", objArr));
                        context2 = this.mContext;
                        i4 = R.string.temperature;
                    } else {
                        if (this.Lc != 1) {
                            f = ((f * 9.0f) / 5.0f) + 32.0f;
                        }
                        sb = new StringBuilder();
                        sb.append((int) f);
                        context2 = this.mContext;
                        i4 = R.string.ftemperature;
                    }
                }
                textView.setText("HI");
                return;
            }
            textView.setText("LO");
            return;
        }
        if (i6 == 33 || i6 == 34 || i6 == 38) {
            if (i2 != 0) {
                if (i2 != 255) {
                    f = i2 * 0.5f;
                    if (this.Kc != 1) {
                        if (this.Lc == 1) {
                            f = (int) (((double) (f - 32.0f)) / 1.8d);
                        }
                        sb = new StringBuilder();
                        locale = Locale.US;
                        objArr = new Object[]{Float.valueOf(f)};
                        sb.append(String.format(locale, "%.1f", objArr));
                        context2 = this.mContext;
                        i4 = R.string.temperature;
                    } else {
                        if (this.Lc != 1) {
                            f = ((f * 9.0f) / 5.0f) + 32.0f;
                        }
                        sb = new StringBuilder();
                        sb.append((int) f);
                        context2 = this.mContext;
                        i4 = R.string.ftemperature;
                    }
                }
                textView.setText("HI");
                return;
            }
            textView.setText("LO");
            return;
        }
        if (i6 == 65) {
            if (i2 != 0) {
                if (i2 != 127) {
                    if (i2 < 31 || i2 > 59) {
                        return;
                    }
                    f = ((i2 - 31) * 0.5f) + 15.5f;
                    if (this.Kc != 1) {
                        if (this.Lc == 1) {
                            f = (int) (((double) (f - 32.0f)) / 1.8d);
                        }
                        sb = new StringBuilder();
                        locale = Locale.US;
                        objArr = new Object[]{Float.valueOf(f)};
                        sb.append(String.format(locale, "%.1f", objArr));
                        context2 = this.mContext;
                        i4 = R.string.temperature;
                    } else {
                        if (this.Lc != 1) {
                            f = ((f * 9.0f) / 5.0f) + 32.0f;
                        }
                        sb = new StringBuilder();
                        sb.append((int) f);
                        context2 = this.mContext;
                        i4 = R.string.ftemperature;
                    }
                }
                textView.setText("HI");
                return;
            }
            textView.setText("LO");
            return;
        }
        if (i6 == 128 || i6 == 134 || i6 == 137) {
            if (i2 != 0) {
                if (i2 != 30) {
                    if (i2 == 29) {
                        if (this.Kc != 1) {
                            sb2 = new StringBuilder();
                            sb2.append(String.format(Locale.US, "%.1f", Float.valueOf(16.0f)));
                            context = this.mContext;
                            i3 = R.string.temperature;
                        } else {
                            sb2 = new StringBuilder();
                            sb2.append(String.format(Locale.US, "%.1f", Float.valueOf(60.8f)));
                            context = this.mContext;
                            i3 = R.string.ftemperature;
                        }
                        sb2.append(context.getString(i3));
                        string = sb2.toString();
                    } else {
                        if (i2 < 1 || i2 > 28) {
                            return;
                        }
                        f = ((i2 - 1) * 0.5f) + 17.0f;
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                f = (int) (((double) (f - 32.0f)) / 1.8d);
                            }
                            sb = new StringBuilder();
                            locale = Locale.US;
                            objArr = new Object[]{Float.valueOf(f)};
                            sb.append(String.format(locale, "%.1f", objArr));
                            context2 = this.mContext;
                            i4 = R.string.temperature;
                        } else {
                            if (this.Lc != 1) {
                                f = ((f * 9.0f) / 5.0f) + 32.0f;
                            }
                            sb = new StringBuilder();
                            sb.append((int) f);
                            context2 = this.mContext;
                            i4 = R.string.ftemperature;
                        }
                    }
                    textView.setText(string);
                }
                textView.setText("HI");
                return;
            }
            textView.setText("LO");
            return;
        }
        if (i6 != -1) {
            if (i2 != 0) {
                if (i2 != 31) {
                    if (i2 >= 1 && i2 <= 29) {
                        f = ((i2 - 1) * 0.5f) + 18.0f;
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                f = (int) (((double) (f - 32.0f)) / 1.8d);
                            }
                            sb = new StringBuilder();
                            locale = Locale.US;
                            objArr = new Object[]{Float.valueOf(f)};
                            sb.append(String.format(locale, "%.1f", objArr));
                            context2 = this.mContext;
                            i4 = R.string.temperature;
                        } else {
                            if (this.Lc != 1) {
                                f = ((f * 9.0f) / 5.0f) + 32.0f;
                            }
                            sb = new StringBuilder();
                            sb.append((int) f);
                            context2 = this.mContext;
                            i4 = R.string.ftemperature;
                        }
                    } else {
                        if (i2 < 32 || i2 > 35) {
                            return;
                        }
                        f = ((i2 - 32) * 0.5f) + 16.0f;
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                f = (int) (((double) (f - 32.0f)) / 1.8d);
                            }
                            sb = new StringBuilder();
                            locale = Locale.US;
                            objArr = new Object[]{Float.valueOf(f)};
                            sb.append(String.format(locale, "%.1f", objArr));
                            context2 = this.mContext;
                            i4 = R.string.temperature;
                        } else {
                            if (this.Lc != 1) {
                                f = ((f * 9.0f) / 5.0f) + 32.0f;
                            }
                            sb = new StringBuilder();
                            sb.append((int) f);
                            context2 = this.mContext;
                            i4 = R.string.ftemperature;
                        }
                    }
                }
                textView.setText("HI");
                return;
            }
            textView.setText("LO");
            return;
        }
        return;
        sb.append(context2.getString(i4));
        string = sb.toString();
        textView.setText(string);
    }

    private void show() {
        if (!Ua() || this.Ic) {
            return;
        }
        this.Ic = true;
        this.Hc.addView(this.mView, this.mLayoutParams);
    }

    public void a(int i, int i2, int i3, byte[] bArr) {
        this.Kc = Settings.System.getInt(this.mContext.getContentResolver(), "TEMP_MODE", 0);
        this.Lc = Settings.System.getInt(this.mContext.getContentResolver(), "TEMP_CONVERT", 0);
        if (SystemProperties.getBoolean("persist.tw.air.show", true)) {
            obtainMessage(i, i2, i3, bArr).sendToTarget();
        }
    }

    public void h(int i) {
        this.Kc = Settings.System.getInt(this.mContext.getContentResolver(), "TEMP_MODE", 0);
        this.Lc = Settings.System.getInt(this.mContext.getContentResolver(), "TEMP_CONVERT", 0);
        this.Jc = i;
    }

    /* JADX WARN: Code duplicated, block: B:359:0x0c92 A[Catch: Exception -> 0x16a8, TryCatch #1 {Exception -> 0x16a8, blocks: (B:3:0x0004, B:7:0x0022, B:9:0x0027, B:10:0x0035, B:11:0x003a, B:13:0x0040, B:15:0x0045, B:17:0x004a, B:19:0x0063, B:21:0x0068, B:23:0x0081, B:25:0x0086, B:27:0x009f, B:29:0x00a4, B:31:0x00bd, B:33:0x00c2, B:35:0x00db, B:37:0x00e0, B:39:0x00f9, B:41:0x00fe, B:43:0x0117, B:45:0x011c, B:47:0x0147, B:49:0x014c, B:51:0x0177, B:53:0x017c, B:55:0x01ad, B:57:0x01b2, B:59:0x01b9, B:61:0x01c7, B:63:0x01ce, B:65:0x01e1, B:67:0x01e8, B:69:0x0201, B:71:0x0208, B:72:0x0211, B:73:0x0215, B:74:0x021e, B:68:0x01f5, B:64:0x01d8, B:75:0x0222, B:77:0x0229, B:79:0x0242, B:81:0x0249, B:83:0x025c, B:85:0x0263, B:86:0x026d, B:82:0x0253, B:78:0x0236, B:87:0x0277, B:89:0x028c, B:93:0x029c, B:97:0x02af, B:106:0x02d6, B:108:0x02db, B:110:0x02f4, B:112:0x02f9, B:114:0x0312, B:116:0x0317, B:117:0x032c, B:119:0x033a, B:121:0x033f, B:123:0x0358, B:125:0x035d, B:126:0x0372, B:128:0x0380, B:130:0x03a4, B:132:0x03aa, B:134:0x03b1, B:136:0x03b5, B:137:0x03c3, B:138:0x03ed, B:144:0x041c, B:146:0x0421, B:148:0x0428, B:150:0x045a, B:151:0x049f, B:154:0x050d, B:156:0x0514, B:158:0x0522, B:160:0x0529, B:162:0x0563, B:164:0x056c, B:165:0x0599, B:166:0x059e, B:167:0x05a7, B:161:0x0557, B:168:0x05ac, B:170:0x05b3, B:172:0x05f0, B:174:0x05f9, B:175:0x0625, B:171:0x05e4, B:152:0x04a3, B:153:0x04e9, B:176:0x0630, B:139:0x03f1, B:141:0x03f5, B:142:0x03fe, B:143:0x0419, B:127:0x0376, B:122:0x034c, B:118:0x0330, B:113:0x0306, B:109:0x02e8, B:98:0x02b3, B:102:0x02c3, B:54:0x0195, B:50:0x0162, B:46:0x0132, B:42:0x010b, B:38:0x00ed, B:34:0x00cf, B:30:0x00b1, B:26:0x0093, B:22:0x0075, B:18:0x0057, B:177:0x0635, B:178:0x063a, B:181:0x0644, B:183:0x0649, B:185:0x0662, B:187:0x0667, B:189:0x0680, B:191:0x0685, B:193:0x06b0, B:195:0x06b5, B:197:0x06e6, B:199:0x06eb, B:201:0x0704, B:203:0x070b, B:205:0x0724, B:207:0x0729, B:209:0x0742, B:211:0x0747, B:213:0x0760, B:218:0x0771, B:221:0x078f, B:222:0x0794, B:226:0x079b, B:228:0x07b8, B:233:0x07da, B:234:0x07df, B:239:0x0801, B:212:0x0754, B:208:0x0736, B:204:0x0718, B:200:0x06f8, B:196:0x06ce, B:192:0x069b, B:188:0x0674, B:184:0x0656, B:240:0x0806, B:242:0x080a, B:245:0x0812, B:247:0x0817, B:249:0x081e, B:251:0x0837, B:253:0x083c, B:255:0x0855, B:257:0x085a, B:259:0x0873, B:261:0x0878, B:262:0x088d, B:264:0x089b, B:266:0x08a0, B:268:0x08b9, B:270:0x08be, B:271:0x08d3, B:273:0x08e1, B:275:0x08e6, B:277:0x08ff, B:279:0x090d, B:281:0x093e, B:283:0x0943, B:285:0x0974, B:287:0x0979, B:289:0x09a4, B:291:0x09b2, B:292:0x09c2, B:294:0x09d9, B:296:0x0a22, B:298:0x0a26, B:299:0x0a32, B:300:0x0a5a, B:301:0x0a5f, B:303:0x0a63, B:304:0x0a6c, B:293:0x09c8, B:288:0x098f, B:284:0x095c, B:280:0x0926, B:276:0x08f3, B:272:0x08d7, B:267:0x08ad, B:263:0x0891, B:258:0x0867, B:254:0x0849, B:250:0x082b, B:306:0x0a88, B:308:0x0a8d, B:310:0x0a94, B:312:0x0aad, B:314:0x0ab2, B:316:0x0acb, B:318:0x0ad0, B:320:0x0ae9, B:322:0x0aee, B:324:0x0b07, B:325:0x0b50, B:335:0x0bda, B:357:0x0c71, B:359:0x0c92, B:360:0x0ca2, B:362:0x0cb9, B:364:0x0cca, B:366:0x0cd0, B:368:0x0cd7, B:370:0x0cdb, B:371:0x0ce8, B:372:0x0d12, B:373:0x0d17, B:375:0x0d1b, B:376:0x0d24, B:377:0x0d3f, B:361:0x0ca8, B:349:0x0bf8, B:350:0x0c16, B:351:0x0c1a, B:352:0x0c2d, B:353:0x0c43, B:354:0x0c56, B:355:0x0c60, B:356:0x0c67, B:327:0x0b55, B:328:0x0b76, B:329:0x0b7a, B:330:0x0b90, B:331:0x0ba6, B:332:0x0bbc, B:333:0x0bc6, B:334:0x0bd0, B:323:0x0afb, B:319:0x0add, B:315:0x0abf, B:311:0x0aa1, B:378:0x0d44, B:381:0x0d4c, B:383:0x0d53, B:385:0x0d6c, B:387:0x0d71, B:389:0x0d8a, B:391:0x0d8f, B:393:0x0da8, B:395:0x0dad, B:397:0x0dc6, B:412:0x0eb8, B:406:0x0e1d, B:407:0x0e4a, B:408:0x0e4e, B:409:0x0e64, B:410:0x0e8f, B:411:0x0ea5, B:396:0x0dba, B:392:0x0d9c, B:388:0x0d7e, B:384:0x0d60, B:413:0x0ed8, B:415:0x0edc, B:417:0x0ee2, B:419:0x0ee8, B:421:0x0eee, B:425:0x0ef8, B:427:0x0efd, B:429:0x0f04, B:431:0x0f1d, B:433:0x0f22, B:435:0x0f3b, B:437:0x0f40, B:439:0x0f59, B:441:0x0f5e, B:443:0x0f77, B:445:0x0f7c, B:447:0x0f95, B:449:0x0f9a, B:451:0x0fb3, B:453:0x0fb8, B:455:0x0fd1, B:457:0x0fd6, B:459:0x1007, B:461:0x100e, B:463:0x1039, B:465:0x103e, B:467:0x106f, B:469:0x1084, B:470:0x1094, B:472:0x10ab, B:474:0x10b1, B:477:0x10b9, B:479:0x10bf, B:481:0x10c5, B:484:0x10cd, B:486:0x10d3, B:488:0x10d8, B:490:0x10f1, B:492:0x10f8, B:494:0x1111, B:496:0x1116, B:497:0x112b, B:498:0x1130, B:493:0x1105, B:489:0x10e5, B:499:0x113a, B:501:0x1140, B:503:0x1145, B:504:0x115a, B:506:0x1168, B:510:0x117e, B:512:0x1185, B:514:0x1189, B:515:0x1193, B:516:0x11bb, B:517:0x11c0, B:519:0x11c4, B:520:0x11c9, B:521:0x11e2, B:505:0x115e, B:522:0x11e7, B:524:0x11ec, B:526:0x11f1, B:527:0x1206, B:529:0x1214, B:531:0x1219, B:533:0x1232, B:535:0x1237, B:536:0x124c, B:538:0x125a, B:537:0x1250, B:532:0x1226, B:528:0x120a, B:539:0x126d, B:541:0x1272, B:542:0x1280, B:543:0x128b, B:545:0x1294, B:546:0x12a9, B:548:0x12b7, B:550:0x12bc, B:552:0x12d5, B:554:0x12da, B:555:0x12ef, B:557:0x12fd, B:559:0x1321, B:561:0x1327, B:563:0x132e, B:565:0x1332, B:566:0x133f, B:567:0x136b, B:569:0x136f, B:570:0x1378, B:556:0x12f3, B:551:0x12c9, B:547:0x12ad, B:471:0x109a, B:466:0x1057, B:462:0x1024, B:458:0x0fef, B:454:0x0fc5, B:450:0x0fa7, B:446:0x0f89, B:442:0x0f6b, B:438:0x0f4d, B:434:0x0f2f, B:430:0x0f11, B:572:0x1396, B:574:0x139d, B:576:0x13b6, B:578:0x13bb, B:580:0x13d4, B:582:0x13d9, B:584:0x13f2, B:586:0x13f7, B:587:0x140c, B:589:0x141a, B:590:0x1476, B:602:0x15e3, B:604:0x15f1, B:605:0x1601, B:607:0x1618, B:611:0x163f, B:613:0x1646, B:615:0x164a, B:616:0x1654, B:617:0x167c, B:618:0x1681, B:620:0x1685, B:621:0x168a, B:622:0x16a3, B:606:0x1607, B:592:0x147b, B:593:0x14bd, B:594:0x14c2, B:595:0x14f0, B:596:0x1506, B:597:0x1531, B:598:0x1545, B:599:0x1571, B:600:0x1588, B:601:0x159f, B:588:0x1410, B:583:0x13e6, B:579:0x13c8, B:575:0x13aa, B:236:0x07e6, B:230:0x07bf), top: B:629:0x0004, inners: #0, #2 }] */
    /* JADX WARN: Code duplicated, block: B:361:0x0ca8 A[Catch: Exception -> 0x16a8, TryCatch #1 {Exception -> 0x16a8, blocks: (B:3:0x0004, B:7:0x0022, B:9:0x0027, B:10:0x0035, B:11:0x003a, B:13:0x0040, B:15:0x0045, B:17:0x004a, B:19:0x0063, B:21:0x0068, B:23:0x0081, B:25:0x0086, B:27:0x009f, B:29:0x00a4, B:31:0x00bd, B:33:0x00c2, B:35:0x00db, B:37:0x00e0, B:39:0x00f9, B:41:0x00fe, B:43:0x0117, B:45:0x011c, B:47:0x0147, B:49:0x014c, B:51:0x0177, B:53:0x017c, B:55:0x01ad, B:57:0x01b2, B:59:0x01b9, B:61:0x01c7, B:63:0x01ce, B:65:0x01e1, B:67:0x01e8, B:69:0x0201, B:71:0x0208, B:72:0x0211, B:73:0x0215, B:74:0x021e, B:68:0x01f5, B:64:0x01d8, B:75:0x0222, B:77:0x0229, B:79:0x0242, B:81:0x0249, B:83:0x025c, B:85:0x0263, B:86:0x026d, B:82:0x0253, B:78:0x0236, B:87:0x0277, B:89:0x028c, B:93:0x029c, B:97:0x02af, B:106:0x02d6, B:108:0x02db, B:110:0x02f4, B:112:0x02f9, B:114:0x0312, B:116:0x0317, B:117:0x032c, B:119:0x033a, B:121:0x033f, B:123:0x0358, B:125:0x035d, B:126:0x0372, B:128:0x0380, B:130:0x03a4, B:132:0x03aa, B:134:0x03b1, B:136:0x03b5, B:137:0x03c3, B:138:0x03ed, B:144:0x041c, B:146:0x0421, B:148:0x0428, B:150:0x045a, B:151:0x049f, B:154:0x050d, B:156:0x0514, B:158:0x0522, B:160:0x0529, B:162:0x0563, B:164:0x056c, B:165:0x0599, B:166:0x059e, B:167:0x05a7, B:161:0x0557, B:168:0x05ac, B:170:0x05b3, B:172:0x05f0, B:174:0x05f9, B:175:0x0625, B:171:0x05e4, B:152:0x04a3, B:153:0x04e9, B:176:0x0630, B:139:0x03f1, B:141:0x03f5, B:142:0x03fe, B:143:0x0419, B:127:0x0376, B:122:0x034c, B:118:0x0330, B:113:0x0306, B:109:0x02e8, B:98:0x02b3, B:102:0x02c3, B:54:0x0195, B:50:0x0162, B:46:0x0132, B:42:0x010b, B:38:0x00ed, B:34:0x00cf, B:30:0x00b1, B:26:0x0093, B:22:0x0075, B:18:0x0057, B:177:0x0635, B:178:0x063a, B:181:0x0644, B:183:0x0649, B:185:0x0662, B:187:0x0667, B:189:0x0680, B:191:0x0685, B:193:0x06b0, B:195:0x06b5, B:197:0x06e6, B:199:0x06eb, B:201:0x0704, B:203:0x070b, B:205:0x0724, B:207:0x0729, B:209:0x0742, B:211:0x0747, B:213:0x0760, B:218:0x0771, B:221:0x078f, B:222:0x0794, B:226:0x079b, B:228:0x07b8, B:233:0x07da, B:234:0x07df, B:239:0x0801, B:212:0x0754, B:208:0x0736, B:204:0x0718, B:200:0x06f8, B:196:0x06ce, B:192:0x069b, B:188:0x0674, B:184:0x0656, B:240:0x0806, B:242:0x080a, B:245:0x0812, B:247:0x0817, B:249:0x081e, B:251:0x0837, B:253:0x083c, B:255:0x0855, B:257:0x085a, B:259:0x0873, B:261:0x0878, B:262:0x088d, B:264:0x089b, B:266:0x08a0, B:268:0x08b9, B:270:0x08be, B:271:0x08d3, B:273:0x08e1, B:275:0x08e6, B:277:0x08ff, B:279:0x090d, B:281:0x093e, B:283:0x0943, B:285:0x0974, B:287:0x0979, B:289:0x09a4, B:291:0x09b2, B:292:0x09c2, B:294:0x09d9, B:296:0x0a22, B:298:0x0a26, B:299:0x0a32, B:300:0x0a5a, B:301:0x0a5f, B:303:0x0a63, B:304:0x0a6c, B:293:0x09c8, B:288:0x098f, B:284:0x095c, B:280:0x0926, B:276:0x08f3, B:272:0x08d7, B:267:0x08ad, B:263:0x0891, B:258:0x0867, B:254:0x0849, B:250:0x082b, B:306:0x0a88, B:308:0x0a8d, B:310:0x0a94, B:312:0x0aad, B:314:0x0ab2, B:316:0x0acb, B:318:0x0ad0, B:320:0x0ae9, B:322:0x0aee, B:324:0x0b07, B:325:0x0b50, B:335:0x0bda, B:357:0x0c71, B:359:0x0c92, B:360:0x0ca2, B:362:0x0cb9, B:364:0x0cca, B:366:0x0cd0, B:368:0x0cd7, B:370:0x0cdb, B:371:0x0ce8, B:372:0x0d12, B:373:0x0d17, B:375:0x0d1b, B:376:0x0d24, B:377:0x0d3f, B:361:0x0ca8, B:349:0x0bf8, B:350:0x0c16, B:351:0x0c1a, B:352:0x0c2d, B:353:0x0c43, B:354:0x0c56, B:355:0x0c60, B:356:0x0c67, B:327:0x0b55, B:328:0x0b76, B:329:0x0b7a, B:330:0x0b90, B:331:0x0ba6, B:332:0x0bbc, B:333:0x0bc6, B:334:0x0bd0, B:323:0x0afb, B:319:0x0add, B:315:0x0abf, B:311:0x0aa1, B:378:0x0d44, B:381:0x0d4c, B:383:0x0d53, B:385:0x0d6c, B:387:0x0d71, B:389:0x0d8a, B:391:0x0d8f, B:393:0x0da8, B:395:0x0dad, B:397:0x0dc6, B:412:0x0eb8, B:406:0x0e1d, B:407:0x0e4a, B:408:0x0e4e, B:409:0x0e64, B:410:0x0e8f, B:411:0x0ea5, B:396:0x0dba, B:392:0x0d9c, B:388:0x0d7e, B:384:0x0d60, B:413:0x0ed8, B:415:0x0edc, B:417:0x0ee2, B:419:0x0ee8, B:421:0x0eee, B:425:0x0ef8, B:427:0x0efd, B:429:0x0f04, B:431:0x0f1d, B:433:0x0f22, B:435:0x0f3b, B:437:0x0f40, B:439:0x0f59, B:441:0x0f5e, B:443:0x0f77, B:445:0x0f7c, B:447:0x0f95, B:449:0x0f9a, B:451:0x0fb3, B:453:0x0fb8, B:455:0x0fd1, B:457:0x0fd6, B:459:0x1007, B:461:0x100e, B:463:0x1039, B:465:0x103e, B:467:0x106f, B:469:0x1084, B:470:0x1094, B:472:0x10ab, B:474:0x10b1, B:477:0x10b9, B:479:0x10bf, B:481:0x10c5, B:484:0x10cd, B:486:0x10d3, B:488:0x10d8, B:490:0x10f1, B:492:0x10f8, B:494:0x1111, B:496:0x1116, B:497:0x112b, B:498:0x1130, B:493:0x1105, B:489:0x10e5, B:499:0x113a, B:501:0x1140, B:503:0x1145, B:504:0x115a, B:506:0x1168, B:510:0x117e, B:512:0x1185, B:514:0x1189, B:515:0x1193, B:516:0x11bb, B:517:0x11c0, B:519:0x11c4, B:520:0x11c9, B:521:0x11e2, B:505:0x115e, B:522:0x11e7, B:524:0x11ec, B:526:0x11f1, B:527:0x1206, B:529:0x1214, B:531:0x1219, B:533:0x1232, B:535:0x1237, B:536:0x124c, B:538:0x125a, B:537:0x1250, B:532:0x1226, B:528:0x120a, B:539:0x126d, B:541:0x1272, B:542:0x1280, B:543:0x128b, B:545:0x1294, B:546:0x12a9, B:548:0x12b7, B:550:0x12bc, B:552:0x12d5, B:554:0x12da, B:555:0x12ef, B:557:0x12fd, B:559:0x1321, B:561:0x1327, B:563:0x132e, B:565:0x1332, B:566:0x133f, B:567:0x136b, B:569:0x136f, B:570:0x1378, B:556:0x12f3, B:551:0x12c9, B:547:0x12ad, B:471:0x109a, B:466:0x1057, B:462:0x1024, B:458:0x0fef, B:454:0x0fc5, B:450:0x0fa7, B:446:0x0f89, B:442:0x0f6b, B:438:0x0f4d, B:434:0x0f2f, B:430:0x0f11, B:572:0x1396, B:574:0x139d, B:576:0x13b6, B:578:0x13bb, B:580:0x13d4, B:582:0x13d9, B:584:0x13f2, B:586:0x13f7, B:587:0x140c, B:589:0x141a, B:590:0x1476, B:602:0x15e3, B:604:0x15f1, B:605:0x1601, B:607:0x1618, B:611:0x163f, B:613:0x1646, B:615:0x164a, B:616:0x1654, B:617:0x167c, B:618:0x1681, B:620:0x1685, B:621:0x168a, B:622:0x16a3, B:606:0x1607, B:592:0x147b, B:593:0x14bd, B:594:0x14c2, B:595:0x14f0, B:596:0x1506, B:597:0x1531, B:598:0x1545, B:599:0x1571, B:600:0x1588, B:601:0x159f, B:588:0x1410, B:583:0x13e6, B:579:0x13c8, B:575:0x13aa, B:236:0x07e6, B:230:0x07bf), top: B:629:0x0004, inners: #0, #2 }] */
    /* JADX WARN: Code duplicated, block: B:377:0x0d3f A[Catch: Exception -> 0x16a8, TryCatch #1 {Exception -> 0x16a8, blocks: (B:3:0x0004, B:7:0x0022, B:9:0x0027, B:10:0x0035, B:11:0x003a, B:13:0x0040, B:15:0x0045, B:17:0x004a, B:19:0x0063, B:21:0x0068, B:23:0x0081, B:25:0x0086, B:27:0x009f, B:29:0x00a4, B:31:0x00bd, B:33:0x00c2, B:35:0x00db, B:37:0x00e0, B:39:0x00f9, B:41:0x00fe, B:43:0x0117, B:45:0x011c, B:47:0x0147, B:49:0x014c, B:51:0x0177, B:53:0x017c, B:55:0x01ad, B:57:0x01b2, B:59:0x01b9, B:61:0x01c7, B:63:0x01ce, B:65:0x01e1, B:67:0x01e8, B:69:0x0201, B:71:0x0208, B:72:0x0211, B:73:0x0215, B:74:0x021e, B:68:0x01f5, B:64:0x01d8, B:75:0x0222, B:77:0x0229, B:79:0x0242, B:81:0x0249, B:83:0x025c, B:85:0x0263, B:86:0x026d, B:82:0x0253, B:78:0x0236, B:87:0x0277, B:89:0x028c, B:93:0x029c, B:97:0x02af, B:106:0x02d6, B:108:0x02db, B:110:0x02f4, B:112:0x02f9, B:114:0x0312, B:116:0x0317, B:117:0x032c, B:119:0x033a, B:121:0x033f, B:123:0x0358, B:125:0x035d, B:126:0x0372, B:128:0x0380, B:130:0x03a4, B:132:0x03aa, B:134:0x03b1, B:136:0x03b5, B:137:0x03c3, B:138:0x03ed, B:144:0x041c, B:146:0x0421, B:148:0x0428, B:150:0x045a, B:151:0x049f, B:154:0x050d, B:156:0x0514, B:158:0x0522, B:160:0x0529, B:162:0x0563, B:164:0x056c, B:165:0x0599, B:166:0x059e, B:167:0x05a7, B:161:0x0557, B:168:0x05ac, B:170:0x05b3, B:172:0x05f0, B:174:0x05f9, B:175:0x0625, B:171:0x05e4, B:152:0x04a3, B:153:0x04e9, B:176:0x0630, B:139:0x03f1, B:141:0x03f5, B:142:0x03fe, B:143:0x0419, B:127:0x0376, B:122:0x034c, B:118:0x0330, B:113:0x0306, B:109:0x02e8, B:98:0x02b3, B:102:0x02c3, B:54:0x0195, B:50:0x0162, B:46:0x0132, B:42:0x010b, B:38:0x00ed, B:34:0x00cf, B:30:0x00b1, B:26:0x0093, B:22:0x0075, B:18:0x0057, B:177:0x0635, B:178:0x063a, B:181:0x0644, B:183:0x0649, B:185:0x0662, B:187:0x0667, B:189:0x0680, B:191:0x0685, B:193:0x06b0, B:195:0x06b5, B:197:0x06e6, B:199:0x06eb, B:201:0x0704, B:203:0x070b, B:205:0x0724, B:207:0x0729, B:209:0x0742, B:211:0x0747, B:213:0x0760, B:218:0x0771, B:221:0x078f, B:222:0x0794, B:226:0x079b, B:228:0x07b8, B:233:0x07da, B:234:0x07df, B:239:0x0801, B:212:0x0754, B:208:0x0736, B:204:0x0718, B:200:0x06f8, B:196:0x06ce, B:192:0x069b, B:188:0x0674, B:184:0x0656, B:240:0x0806, B:242:0x080a, B:245:0x0812, B:247:0x0817, B:249:0x081e, B:251:0x0837, B:253:0x083c, B:255:0x0855, B:257:0x085a, B:259:0x0873, B:261:0x0878, B:262:0x088d, B:264:0x089b, B:266:0x08a0, B:268:0x08b9, B:270:0x08be, B:271:0x08d3, B:273:0x08e1, B:275:0x08e6, B:277:0x08ff, B:279:0x090d, B:281:0x093e, B:283:0x0943, B:285:0x0974, B:287:0x0979, B:289:0x09a4, B:291:0x09b2, B:292:0x09c2, B:294:0x09d9, B:296:0x0a22, B:298:0x0a26, B:299:0x0a32, B:300:0x0a5a, B:301:0x0a5f, B:303:0x0a63, B:304:0x0a6c, B:293:0x09c8, B:288:0x098f, B:284:0x095c, B:280:0x0926, B:276:0x08f3, B:272:0x08d7, B:267:0x08ad, B:263:0x0891, B:258:0x0867, B:254:0x0849, B:250:0x082b, B:306:0x0a88, B:308:0x0a8d, B:310:0x0a94, B:312:0x0aad, B:314:0x0ab2, B:316:0x0acb, B:318:0x0ad0, B:320:0x0ae9, B:322:0x0aee, B:324:0x0b07, B:325:0x0b50, B:335:0x0bda, B:357:0x0c71, B:359:0x0c92, B:360:0x0ca2, B:362:0x0cb9, B:364:0x0cca, B:366:0x0cd0, B:368:0x0cd7, B:370:0x0cdb, B:371:0x0ce8, B:372:0x0d12, B:373:0x0d17, B:375:0x0d1b, B:376:0x0d24, B:377:0x0d3f, B:361:0x0ca8, B:349:0x0bf8, B:350:0x0c16, B:351:0x0c1a, B:352:0x0c2d, B:353:0x0c43, B:354:0x0c56, B:355:0x0c60, B:356:0x0c67, B:327:0x0b55, B:328:0x0b76, B:329:0x0b7a, B:330:0x0b90, B:331:0x0ba6, B:332:0x0bbc, B:333:0x0bc6, B:334:0x0bd0, B:323:0x0afb, B:319:0x0add, B:315:0x0abf, B:311:0x0aa1, B:378:0x0d44, B:381:0x0d4c, B:383:0x0d53, B:385:0x0d6c, B:387:0x0d71, B:389:0x0d8a, B:391:0x0d8f, B:393:0x0da8, B:395:0x0dad, B:397:0x0dc6, B:412:0x0eb8, B:406:0x0e1d, B:407:0x0e4a, B:408:0x0e4e, B:409:0x0e64, B:410:0x0e8f, B:411:0x0ea5, B:396:0x0dba, B:392:0x0d9c, B:388:0x0d7e, B:384:0x0d60, B:413:0x0ed8, B:415:0x0edc, B:417:0x0ee2, B:419:0x0ee8, B:421:0x0eee, B:425:0x0ef8, B:427:0x0efd, B:429:0x0f04, B:431:0x0f1d, B:433:0x0f22, B:435:0x0f3b, B:437:0x0f40, B:439:0x0f59, B:441:0x0f5e, B:443:0x0f77, B:445:0x0f7c, B:447:0x0f95, B:449:0x0f9a, B:451:0x0fb3, B:453:0x0fb8, B:455:0x0fd1, B:457:0x0fd6, B:459:0x1007, B:461:0x100e, B:463:0x1039, B:465:0x103e, B:467:0x106f, B:469:0x1084, B:470:0x1094, B:472:0x10ab, B:474:0x10b1, B:477:0x10b9, B:479:0x10bf, B:481:0x10c5, B:484:0x10cd, B:486:0x10d3, B:488:0x10d8, B:490:0x10f1, B:492:0x10f8, B:494:0x1111, B:496:0x1116, B:497:0x112b, B:498:0x1130, B:493:0x1105, B:489:0x10e5, B:499:0x113a, B:501:0x1140, B:503:0x1145, B:504:0x115a, B:506:0x1168, B:510:0x117e, B:512:0x1185, B:514:0x1189, B:515:0x1193, B:516:0x11bb, B:517:0x11c0, B:519:0x11c4, B:520:0x11c9, B:521:0x11e2, B:505:0x115e, B:522:0x11e7, B:524:0x11ec, B:526:0x11f1, B:527:0x1206, B:529:0x1214, B:531:0x1219, B:533:0x1232, B:535:0x1237, B:536:0x124c, B:538:0x125a, B:537:0x1250, B:532:0x1226, B:528:0x120a, B:539:0x126d, B:541:0x1272, B:542:0x1280, B:543:0x128b, B:545:0x1294, B:546:0x12a9, B:548:0x12b7, B:550:0x12bc, B:552:0x12d5, B:554:0x12da, B:555:0x12ef, B:557:0x12fd, B:559:0x1321, B:561:0x1327, B:563:0x132e, B:565:0x1332, B:566:0x133f, B:567:0x136b, B:569:0x136f, B:570:0x1378, B:556:0x12f3, B:551:0x12c9, B:547:0x12ad, B:471:0x109a, B:466:0x1057, B:462:0x1024, B:458:0x0fef, B:454:0x0fc5, B:450:0x0fa7, B:446:0x0f89, B:442:0x0f6b, B:438:0x0f4d, B:434:0x0f2f, B:430:0x0f11, B:572:0x1396, B:574:0x139d, B:576:0x13b6, B:578:0x13bb, B:580:0x13d4, B:582:0x13d9, B:584:0x13f2, B:586:0x13f7, B:587:0x140c, B:589:0x141a, B:590:0x1476, B:602:0x15e3, B:604:0x15f1, B:605:0x1601, B:607:0x1618, B:611:0x163f, B:613:0x1646, B:615:0x164a, B:616:0x1654, B:617:0x167c, B:618:0x1681, B:620:0x1685, B:621:0x168a, B:622:0x16a3, B:606:0x1607, B:592:0x147b, B:593:0x14bd, B:594:0x14c2, B:595:0x14f0, B:596:0x1506, B:597:0x1531, B:598:0x1545, B:599:0x1571, B:600:0x1588, B:601:0x159f, B:588:0x1410, B:583:0x13e6, B:579:0x13c8, B:575:0x13aa, B:236:0x07e6, B:230:0x07bf), top: B:629:0x0004, inners: #0, #2 }] */
    /* JADX WARN: Code duplicated, block: B:521:0x11e2 A[Catch: Exception -> 0x16a8, PHI: r2
      0x11e2: PHI (r2v225 android.widget.TextView) = 
      (r2v214 android.widget.TextView)
      (r2v214 android.widget.TextView)
      (r2v276 android.widget.TextView)
      (r2v276 android.widget.TextView)
     binds: [B:558:0x131f, B:560:0x1325, B:507:0x1178, B:509:0x117c] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {Exception -> 0x16a8, blocks: (B:3:0x0004, B:7:0x0022, B:9:0x0027, B:10:0x0035, B:11:0x003a, B:13:0x0040, B:15:0x0045, B:17:0x004a, B:19:0x0063, B:21:0x0068, B:23:0x0081, B:25:0x0086, B:27:0x009f, B:29:0x00a4, B:31:0x00bd, B:33:0x00c2, B:35:0x00db, B:37:0x00e0, B:39:0x00f9, B:41:0x00fe, B:43:0x0117, B:45:0x011c, B:47:0x0147, B:49:0x014c, B:51:0x0177, B:53:0x017c, B:55:0x01ad, B:57:0x01b2, B:59:0x01b9, B:61:0x01c7, B:63:0x01ce, B:65:0x01e1, B:67:0x01e8, B:69:0x0201, B:71:0x0208, B:72:0x0211, B:73:0x0215, B:74:0x021e, B:68:0x01f5, B:64:0x01d8, B:75:0x0222, B:77:0x0229, B:79:0x0242, B:81:0x0249, B:83:0x025c, B:85:0x0263, B:86:0x026d, B:82:0x0253, B:78:0x0236, B:87:0x0277, B:89:0x028c, B:93:0x029c, B:97:0x02af, B:106:0x02d6, B:108:0x02db, B:110:0x02f4, B:112:0x02f9, B:114:0x0312, B:116:0x0317, B:117:0x032c, B:119:0x033a, B:121:0x033f, B:123:0x0358, B:125:0x035d, B:126:0x0372, B:128:0x0380, B:130:0x03a4, B:132:0x03aa, B:134:0x03b1, B:136:0x03b5, B:137:0x03c3, B:138:0x03ed, B:144:0x041c, B:146:0x0421, B:148:0x0428, B:150:0x045a, B:151:0x049f, B:154:0x050d, B:156:0x0514, B:158:0x0522, B:160:0x0529, B:162:0x0563, B:164:0x056c, B:165:0x0599, B:166:0x059e, B:167:0x05a7, B:161:0x0557, B:168:0x05ac, B:170:0x05b3, B:172:0x05f0, B:174:0x05f9, B:175:0x0625, B:171:0x05e4, B:152:0x04a3, B:153:0x04e9, B:176:0x0630, B:139:0x03f1, B:141:0x03f5, B:142:0x03fe, B:143:0x0419, B:127:0x0376, B:122:0x034c, B:118:0x0330, B:113:0x0306, B:109:0x02e8, B:98:0x02b3, B:102:0x02c3, B:54:0x0195, B:50:0x0162, B:46:0x0132, B:42:0x010b, B:38:0x00ed, B:34:0x00cf, B:30:0x00b1, B:26:0x0093, B:22:0x0075, B:18:0x0057, B:177:0x0635, B:178:0x063a, B:181:0x0644, B:183:0x0649, B:185:0x0662, B:187:0x0667, B:189:0x0680, B:191:0x0685, B:193:0x06b0, B:195:0x06b5, B:197:0x06e6, B:199:0x06eb, B:201:0x0704, B:203:0x070b, B:205:0x0724, B:207:0x0729, B:209:0x0742, B:211:0x0747, B:213:0x0760, B:218:0x0771, B:221:0x078f, B:222:0x0794, B:226:0x079b, B:228:0x07b8, B:233:0x07da, B:234:0x07df, B:239:0x0801, B:212:0x0754, B:208:0x0736, B:204:0x0718, B:200:0x06f8, B:196:0x06ce, B:192:0x069b, B:188:0x0674, B:184:0x0656, B:240:0x0806, B:242:0x080a, B:245:0x0812, B:247:0x0817, B:249:0x081e, B:251:0x0837, B:253:0x083c, B:255:0x0855, B:257:0x085a, B:259:0x0873, B:261:0x0878, B:262:0x088d, B:264:0x089b, B:266:0x08a0, B:268:0x08b9, B:270:0x08be, B:271:0x08d3, B:273:0x08e1, B:275:0x08e6, B:277:0x08ff, B:279:0x090d, B:281:0x093e, B:283:0x0943, B:285:0x0974, B:287:0x0979, B:289:0x09a4, B:291:0x09b2, B:292:0x09c2, B:294:0x09d9, B:296:0x0a22, B:298:0x0a26, B:299:0x0a32, B:300:0x0a5a, B:301:0x0a5f, B:303:0x0a63, B:304:0x0a6c, B:293:0x09c8, B:288:0x098f, B:284:0x095c, B:280:0x0926, B:276:0x08f3, B:272:0x08d7, B:267:0x08ad, B:263:0x0891, B:258:0x0867, B:254:0x0849, B:250:0x082b, B:306:0x0a88, B:308:0x0a8d, B:310:0x0a94, B:312:0x0aad, B:314:0x0ab2, B:316:0x0acb, B:318:0x0ad0, B:320:0x0ae9, B:322:0x0aee, B:324:0x0b07, B:325:0x0b50, B:335:0x0bda, B:357:0x0c71, B:359:0x0c92, B:360:0x0ca2, B:362:0x0cb9, B:364:0x0cca, B:366:0x0cd0, B:368:0x0cd7, B:370:0x0cdb, B:371:0x0ce8, B:372:0x0d12, B:373:0x0d17, B:375:0x0d1b, B:376:0x0d24, B:377:0x0d3f, B:361:0x0ca8, B:349:0x0bf8, B:350:0x0c16, B:351:0x0c1a, B:352:0x0c2d, B:353:0x0c43, B:354:0x0c56, B:355:0x0c60, B:356:0x0c67, B:327:0x0b55, B:328:0x0b76, B:329:0x0b7a, B:330:0x0b90, B:331:0x0ba6, B:332:0x0bbc, B:333:0x0bc6, B:334:0x0bd0, B:323:0x0afb, B:319:0x0add, B:315:0x0abf, B:311:0x0aa1, B:378:0x0d44, B:381:0x0d4c, B:383:0x0d53, B:385:0x0d6c, B:387:0x0d71, B:389:0x0d8a, B:391:0x0d8f, B:393:0x0da8, B:395:0x0dad, B:397:0x0dc6, B:412:0x0eb8, B:406:0x0e1d, B:407:0x0e4a, B:408:0x0e4e, B:409:0x0e64, B:410:0x0e8f, B:411:0x0ea5, B:396:0x0dba, B:392:0x0d9c, B:388:0x0d7e, B:384:0x0d60, B:413:0x0ed8, B:415:0x0edc, B:417:0x0ee2, B:419:0x0ee8, B:421:0x0eee, B:425:0x0ef8, B:427:0x0efd, B:429:0x0f04, B:431:0x0f1d, B:433:0x0f22, B:435:0x0f3b, B:437:0x0f40, B:439:0x0f59, B:441:0x0f5e, B:443:0x0f77, B:445:0x0f7c, B:447:0x0f95, B:449:0x0f9a, B:451:0x0fb3, B:453:0x0fb8, B:455:0x0fd1, B:457:0x0fd6, B:459:0x1007, B:461:0x100e, B:463:0x1039, B:465:0x103e, B:467:0x106f, B:469:0x1084, B:470:0x1094, B:472:0x10ab, B:474:0x10b1, B:477:0x10b9, B:479:0x10bf, B:481:0x10c5, B:484:0x10cd, B:486:0x10d3, B:488:0x10d8, B:490:0x10f1, B:492:0x10f8, B:494:0x1111, B:496:0x1116, B:497:0x112b, B:498:0x1130, B:493:0x1105, B:489:0x10e5, B:499:0x113a, B:501:0x1140, B:503:0x1145, B:504:0x115a, B:506:0x1168, B:510:0x117e, B:512:0x1185, B:514:0x1189, B:515:0x1193, B:516:0x11bb, B:517:0x11c0, B:519:0x11c4, B:520:0x11c9, B:521:0x11e2, B:505:0x115e, B:522:0x11e7, B:524:0x11ec, B:526:0x11f1, B:527:0x1206, B:529:0x1214, B:531:0x1219, B:533:0x1232, B:535:0x1237, B:536:0x124c, B:538:0x125a, B:537:0x1250, B:532:0x1226, B:528:0x120a, B:539:0x126d, B:541:0x1272, B:542:0x1280, B:543:0x128b, B:545:0x1294, B:546:0x12a9, B:548:0x12b7, B:550:0x12bc, B:552:0x12d5, B:554:0x12da, B:555:0x12ef, B:557:0x12fd, B:559:0x1321, B:561:0x1327, B:563:0x132e, B:565:0x1332, B:566:0x133f, B:567:0x136b, B:569:0x136f, B:570:0x1378, B:556:0x12f3, B:551:0x12c9, B:547:0x12ad, B:471:0x109a, B:466:0x1057, B:462:0x1024, B:458:0x0fef, B:454:0x0fc5, B:450:0x0fa7, B:446:0x0f89, B:442:0x0f6b, B:438:0x0f4d, B:434:0x0f2f, B:430:0x0f11, B:572:0x1396, B:574:0x139d, B:576:0x13b6, B:578:0x13bb, B:580:0x13d4, B:582:0x13d9, B:584:0x13f2, B:586:0x13f7, B:587:0x140c, B:589:0x141a, B:590:0x1476, B:602:0x15e3, B:604:0x15f1, B:605:0x1601, B:607:0x1618, B:611:0x163f, B:613:0x1646, B:615:0x164a, B:616:0x1654, B:617:0x167c, B:618:0x1681, B:620:0x1685, B:621:0x168a, B:622:0x16a3, B:606:0x1607, B:592:0x147b, B:593:0x14bd, B:594:0x14c2, B:595:0x14f0, B:596:0x1506, B:597:0x1531, B:598:0x1545, B:599:0x1571, B:600:0x1588, B:601:0x159f, B:588:0x1410, B:583:0x13e6, B:579:0x13c8, B:575:0x13aa, B:236:0x07e6, B:230:0x07bf), top: B:629:0x0004, inners: #0, #2 }] */
    @Override // android.os.Handler
    public void handleMessage(Message message) {
        View viewFindViewById;
        View viewFindViewById2;
        int i;
        int i2;
        String str;
        int i3;
        int i4;
        View viewFindViewById3;
        View viewFindViewById4;
        TextView textView;
        String str2;
        View viewFindViewById5;
        View viewFindViewById6;
        View viewFindViewById7;
        View viewFindViewById8;
        View viewFindViewById9;
        View viewFindViewById10;
        View viewFindViewById11;
        int i5;
        int i6;
        TextView textView2;
        String str3;
        View viewFindViewById12;
        View viewFindViewById13;
        int i7;
        int i8;
        String str4;
        int i9;
        int i10;
        boolean z;
        View viewFindViewById14;
        View viewFindViewById15;
        View viewFindViewById16;
        View viewFindViewById17;
        TextView textView3;
        String str5;
        String str6;
        View viewFindViewById18;
        View viewFindViewById19;
        try {
            int i11 = message.what;
            boolean z2 = true;
            if (i11 != 1281) {
                if (i11 != 1289) {
                    switch (i11) {
                        case 65280:
                            hide();
                            break;
                        case 65281:
                            this.mView.findViewById(R.id.air_heat_seat).setVisibility(4);
                            break;
                        default:
                            break;
                    }
                }
                byte[] bArr = (byte[]) message.obj;
                if (bArr != null && (bArr[1] & 16) == 16) {
                    if ((bArr[0] & 64) == 64) {
                        this.mView.findViewById(R.id.air_ac).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_ac).setVisibility(8);
                    }
                    if ((bArr[0] & 32) == 32) {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(8);
                    }
                    if ((bArr[0] & 16) == 16) {
                        this.mView.findViewById(R.id.air_wind_large).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_large).setVisibility(8);
                    }
                    if ((bArr[0] & 8) == 8) {
                        this.mView.findViewById(R.id.air_wind_small).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_small).setVisibility(8);
                    }
                    if ((bArr[0] & 4) == 4) {
                        this.mView.findViewById(R.id.air_dual).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_dual).setVisibility(8);
                    }
                    if ((bArr[0] & 2) == 2) {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(8);
                    }
                    if ((bArr[0] & 1) == 1) {
                        this.mView.findViewById(R.id.air_rear).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_rear).setVisibility(8);
                    }
                    if ((bArr[1] & 128) == 128) {
                        this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                        this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_up1).setVisibility(4);
                        this.mView.findViewById(R.id.air_wind_up2).setVisibility(4);
                    }
                    if ((bArr[1] & 64) == 64) {
                        this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                        this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                        this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                    }
                    if ((bArr[1] & 32) == 32) {
                        this.mView.findViewById(R.id.air_wind_down1).setVisibility(0);
                        this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_down1).setVisibility(4);
                        this.mView.findViewById(R.id.air_wind_down2).setVisibility(4);
                    }
                    if (bArr.length > 11 && (bArr[11] & 2) == 2) {
                        if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                            if ((bArr[12] & 128) == 128) {
                                this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            } else {
                                this.mView.findViewById(R.id.air_wind_up2).setVisibility(4);
                            }
                            if ((bArr[12] & 64) == 64) {
                                this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                            } else {
                                this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                            }
                            if ((bArr[12] & 32) == 32) {
                                viewFindViewById19 = this.mView.findViewById(R.id.air_wind_down2);
                                viewFindViewById19.setVisibility(0);
                            } else {
                                viewFindViewById18 = this.mView.findViewById(R.id.air_wind_down2);
                                viewFindViewById18.setVisibility(4);
                            }
                        } else {
                            if ((bArr[12] & 128) == 128) {
                                this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                            } else {
                                this.mView.findViewById(R.id.air_wind_up1).setVisibility(4);
                            }
                            if ((bArr[12] & 64) == 64) {
                                this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                            } else {
                                this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                            }
                            if ((bArr[12] & 32) == 32) {
                                viewFindViewById19 = this.mView.findViewById(R.id.air_wind_down1);
                                viewFindViewById19.setVisibility(0);
                            } else {
                                viewFindViewById18 = this.mView.findViewById(R.id.air_wind_down1);
                                viewFindViewById18.setVisibility(4);
                            }
                        }
                    }
                    L(bArr[1] & 15);
                    if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                        a(R.id.air_lt, bArr[2] & 255, (bArr[4] & 1) == 0);
                        i9 = R.id.air_rt;
                        i10 = bArr[3] & 255;
                        z = (bArr[4] & 1) == 0;
                    } else {
                        a(R.id.air_lt, bArr[3] & 255, (bArr[4] & 1) == 0);
                        i9 = R.id.air_rt;
                        i10 = bArr[2] & 255;
                        z = (bArr[4] & 1) == 0;
                    }
                    a(i9, i10, z);
                    if ((bArr[4] & 128) == 128) {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(8);
                    }
                    if ((bArr[4] & 64) == 64) {
                        this.mView.findViewById(R.id.air_rear).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_rear).setVisibility(8);
                    }
                    if ((bArr[4] & 32) == 32) {
                        this.mView.findViewById(R.id.air_inner_loop_a).setVisibility(0);
                        viewFindViewById14 = this.mView.findViewById(R.id.air_inner_loop);
                    } else {
                        viewFindViewById14 = this.mView.findViewById(R.id.air_inner_loop_a);
                    }
                    viewFindViewById14.setVisibility(8);
                    if ((bArr[4] & 16) == 16) {
                        this.mView.findViewById(R.id.air_eco).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_eco).setVisibility(8);
                    }
                    if ((bArr[4] & 8) == 8) {
                        this.mView.findViewById(R.id.air_ac_max).setVisibility(0);
                        viewFindViewById15 = this.mView.findViewById(R.id.air_ac);
                    } else {
                        viewFindViewById15 = this.mView.findViewById(R.id.air_ac_max);
                    }
                    viewFindViewById15.setVisibility(8);
                    l(((bArr[5] & 255) >> 4) & 7, ((bArr[5] & 255) >> 0) & 7);
                    TextView textView4 = (TextView) this.mView.findViewById(R.id.air_bc);
                    if (this.Jc <= -40 || this.Jc >= 127) {
                        textView4.setVisibility(8);
                    } else {
                        textView4.setVisibility(0);
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                this.Jc = (int) (((double) (this.Jc - 32)) / 1.8d);
                            }
                            str6 = String.format(Locale.US, "Ext %d" + this.mContext.getString(R.string.temperature), Integer.valueOf(this.Jc));
                        } else {
                            if (this.Lc != 1) {
                                this.Jc = ((this.Jc * 9) / 5) + 32;
                            }
                            str6 = this.Jc + this.mContext.getString(R.string.ftemperature);
                        }
                        textView4.setText(str6);
                    }
                    if (bArr.length > 15) {
                        if ((bArr[15] & 16) == 16) {
                            this.mView.findViewById(R.id.air_wind_layout).setVisibility(4);
                            this.mView.findViewById(R.id.air_wind_left_value).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_right_value).setVisibility(0);
                            if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                                ((TextView) this.mView.findViewById(R.id.air_wind_left_value)).setText((bArr[1] & 15) + "");
                                textView3 = (TextView) this.mView.findViewById(R.id.air_wind_right_value);
                                str5 = (bArr[15] & 15) + "";
                            } else {
                                ((TextView) this.mView.findViewById(R.id.air_wind_left_value)).setText((bArr[15] & 15) + "");
                                textView3 = (TextView) this.mView.findViewById(R.id.air_wind_right_value);
                                str5 = (bArr[1] & 15) + "";
                            }
                            textView3.setText(str5);
                        } else {
                            this.mView.findViewById(R.id.air_wind_layout).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_left_value).setVisibility(8);
                            this.mView.findViewById(R.id.air_wind_right_value).setVisibility(8);
                        }
                        if ((bArr[11] & 2) == 2) {
                            if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                                if ((bArr[15] & 32) == 32) {
                                    this.mView.findViewById(R.id.air_auto_left).setVisibility(0);
                                    this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                                    this.mView.findViewById(R.id.air_wind_up1).setVisibility(4);
                                    this.mView.findViewById(R.id.air_wind_down1).setVisibility(4);
                                } else {
                                    this.mView.findViewById(R.id.air_auto_left).setVisibility(8);
                                }
                                if ((bArr[15] & 64) == 64) {
                                    this.mView.findViewById(R.id.air_auto_right).setVisibility(0);
                                    this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                                    this.mView.findViewById(R.id.air_wind_down2).setVisibility(4);
                                    viewFindViewById17 = this.mView.findViewById(R.id.air_wind_up2);
                                    viewFindViewById17.setVisibility(4);
                                } else {
                                    viewFindViewById16 = this.mView.findViewById(R.id.air_auto_right);
                                    viewFindViewById16.setVisibility(8);
                                }
                            } else {
                                if ((bArr[15] & 32) == 32) {
                                    this.mView.findViewById(R.id.air_auto_right).setVisibility(0);
                                    this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                                    this.mView.findViewById(R.id.air_wind_down2).setVisibility(4);
                                    this.mView.findViewById(R.id.air_wind_up2).setVisibility(4);
                                } else {
                                    this.mView.findViewById(R.id.air_auto_right).setVisibility(8);
                                }
                                if ((bArr[15] & 64) == 64) {
                                    this.mView.findViewById(R.id.air_auto_left).setVisibility(0);
                                    this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                                    this.mView.findViewById(R.id.air_wind_up1).setVisibility(4);
                                    viewFindViewById17 = this.mView.findViewById(R.id.air_wind_down1);
                                    viewFindViewById17.setVisibility(4);
                                } else {
                                    viewFindViewById16 = this.mView.findViewById(R.id.air_auto_left);
                                    viewFindViewById16.setVisibility(8);
                                }
                            }
                        }
                    }
                    show();
                }
                Ua();
                return;
            }
            byte[] bArr2 = (byte[]) message.obj;
            if (la.Tf == 1) {
                if (bArr2 != 0) {
                    if ((bArr2[1] & 1) == 1) {
                        this.mView.findViewById(R.id.air_ac).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_ac).setVisibility(8);
                    }
                    if ((bArr2[1] & 4) == 4) {
                        this.mView.findViewById(R.id.air_wind_small).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_small).setVisibility(8);
                    }
                    if ((bArr2[1] & '\b') == 8) {
                        this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                        this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                        this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                    }
                    if ((bArr2[1] & 16) == 16) {
                        this.mView.findViewById(R.id.air_wind_down1).setVisibility(0);
                        this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_down1).setVisibility(4);
                        this.mView.findViewById(R.id.air_wind_down2).setVisibility(4);
                    }
                    if ((bArr2[1] & ' ') == 32) {
                        this.mView.findViewById(R.id.air_rear).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_rear).setVisibility(8);
                    }
                    if ((bArr2[1] & '@') == 64) {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(8);
                    }
                    if ((bArr2[1] & 128) == 128) {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(8);
                    } else {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(0);
                    }
                    if ((bArr2[2] & ' ') == 32) {
                        this.mView.findViewById(R.id.air_dual).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_dual).setVisibility(8);
                    }
                    L(bArr2[2] & 15);
                    int i12 = message.arg2;
                    if (i12 == 16) {
                        if ((bArr2[2] & 128) != 128) {
                            z2 = false;
                        }
                        float f = ((bArr2[3] & 15) / 10.0f) + (bArr2[0] & 255);
                        if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                            b(R.id.air_lt, f, z2);
                            try {
                                b(R.id.air_rt, (((bArr2[3] & 240) >> 4) / 10.0f) + (bArr2[5] & 255), z2);
                            } catch (Exception unused) {
                                b(R.id.air_rt, f, z2);
                            }
                        } else {
                            b(R.id.air_rt, f, z2);
                            try {
                                b(R.id.air_lt, (((bArr2[3] & 240) >> 4) / 10.0f) + (bArr2[5] & 255), z2);
                            } catch (Exception unused2) {
                                b(R.id.air_lt, f, z2);
                            }
                        }
                    } else if (i12 == 19) {
                        g(((bArr2[3] & 15) / 10.0f) + (bArr2[0] & 255));
                        int i13 = bArr2[5] & 127;
                        if ((bArr2[5] & 128) == 128) {
                            i13 = -i13;
                        }
                        K(i13);
                    }
                    show();
                }
                hide();
                return;
            }
            if (la.Tf == 2) {
                if (la.mModel == 67) {
                    if (bArr2 != 0 && (bArr2[0] & 128) == 128) {
                        if ((bArr2[0] & '@') == 64) {
                            this.mView.findViewById(R.id.air_ac).setVisibility(0);
                        } else {
                            this.mView.findViewById(R.id.air_ac).setVisibility(8);
                        }
                        if ((bArr2[0] & 16) == 16) {
                            this.mView.findViewById(R.id.air_wind_small).setVisibility(0);
                        } else {
                            this.mView.findViewById(R.id.air_wind_small).setVisibility(8);
                        }
                        if ((bArr2[0] & 4) == 4) {
                            this.mView.findViewById(R.id.air_inner_loop).setVisibility(0);
                        } else {
                            this.mView.findViewById(R.id.air_inner_loop).setVisibility(8);
                        }
                        if ((bArr2[0] & '\b') == 8) {
                            this.mView.findViewById(R.id.air_inner_loop_a).setVisibility(0);
                            viewFindViewById12 = this.mView.findViewById(R.id.air_inner_loop);
                        } else {
                            viewFindViewById12 = this.mView.findViewById(R.id.air_inner_loop_a);
                        }
                        viewFindViewById12.setVisibility(8);
                        if ((bArr2[0] & 2) == 2) {
                            this.mView.findViewById(R.id.air_rear).setVisibility(0);
                        } else {
                            this.mView.findViewById(R.id.air_rear).setVisibility(8);
                        }
                        if ((bArr2[0] & 1) == 1) {
                            this.mView.findViewById(R.id.air_ac_max).setVisibility(0);
                            viewFindViewById13 = this.mView.findViewById(R.id.air_ac);
                        } else {
                            viewFindViewById13 = this.mView.findViewById(R.id.air_ac_max);
                        }
                        viewFindViewById13.setVisibility(8);
                        if ((bArr2[1] & ' ') == 32) {
                            this.mView.findViewById(R.id.air_max_fornt).setVisibility(0);
                        } else {
                            this.mView.findViewById(R.id.air_max_fornt).setVisibility(8);
                        }
                        L(bArr2[1] & 15);
                        if ((bArr2[2] & '@') == 64) {
                            this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                        } else {
                            this.mView.findViewById(R.id.air_wind_up1).setVisibility(4);
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(4);
                        }
                        if ((bArr2[2] & ' ') == 32) {
                            this.mView.findViewById(R.id.air_wind_down1).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                        } else {
                            this.mView.findViewById(R.id.air_wind_down1).setVisibility(4);
                            this.mView.findViewById(R.id.air_wind_down2).setVisibility(4);
                        }
                        if ((bArr2[2] & 16) == 16) {
                            this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                        } else {
                            this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                            this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                        }
                        if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                            n(R.id.air_lt, bArr2[3] & 255);
                            i7 = R.id.air_rt;
                            i8 = bArr2[4];
                        } else {
                            n(R.id.air_lt, bArr2[4] & 255);
                            i7 = R.id.air_rt;
                            i8 = bArr2[3];
                        }
                        n(i7, i8 & 255);
                        this.mView.findViewById(R.id.air_heat_left_3).setVisibility(4);
                        this.mView.findViewById(R.id.air_heat_right_3).setVisibility(4);
                        l(((bArr2[5] & 255) >> 4) & 3, ((bArr2[5] & 255) >> 0) & 3);
                        TextView textView5 = (TextView) this.mView.findViewById(R.id.air_bc);
                        textView5.setVisibility(0);
                        float f2 = ((bArr2[6] & 255) * 0.5f) - 40.0f;
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                f2 = (int) (((double) (f2 - 32.0f)) / 1.8d);
                            }
                            str4 = String.format(Locale.US, "Ext %.1f" + this.mContext.getString(R.string.temperature), Float.valueOf(f2));
                        } else {
                            if (this.Lc != 1) {
                                f2 = ((f2 * 9.0f) / 5.0f) + 32.0f;
                            }
                            str4 = ((int) f2) + this.mContext.getString(R.string.ftemperature);
                        }
                        textView5.setText(str4);
                    }
                    hide();
                    return;
                }
                if (bArr2 != 0 && (bArr2[0] & 128) == 128) {
                    if ((bArr2[0] & '@') == 64) {
                        this.mView.findViewById(R.id.air_ac).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_ac).setVisibility(8);
                    }
                    if ((bArr2[0] & ' ') == 32) {
                        this.mView.findViewById(R.id.air_wind_small).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_small).setVisibility(8);
                    }
                    if ((bArr2[0] & 16) == 16) {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(8);
                    } else {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(0);
                    }
                    if ((bArr2[0] & '\b') == 8) {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(8);
                    }
                    this.mView.findViewById(R.id.air_wind_up2).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_down2).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_up1).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_down1).setVisibility(4);
                    switch (bArr2[1] & 15) {
                        case 2:
                            viewFindViewById10 = this.mView.findViewById(R.id.air_wind_down2);
                            viewFindViewById10.setVisibility(0);
                            break;
                        case 3:
                            viewFindViewById10 = this.mView.findViewById(R.id.air_wind_right);
                            viewFindViewById10.setVisibility(0);
                            break;
                        case 4:
                            viewFindViewById10 = this.mView.findViewById(R.id.air_wind_up2);
                            viewFindViewById10.setVisibility(0);
                            break;
                        case 5:
                            this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                            viewFindViewById10 = this.mView.findViewById(R.id.air_wind_down2);
                            viewFindViewById10.setVisibility(0);
                            break;
                        case 6:
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            viewFindViewById10 = this.mView.findViewById(R.id.air_wind_down2);
                            viewFindViewById10.setVisibility(0);
                            break;
                        case 7:
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            viewFindViewById10 = this.mView.findViewById(R.id.air_wind_right);
                            viewFindViewById10.setVisibility(0);
                            break;
                        case 8:
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                            viewFindViewById10 = this.mView.findViewById(R.id.air_wind_down2);
                            viewFindViewById10.setVisibility(0);
                            break;
                    }
                    int i14 = bArr2[1] & 240;
                    if (i14 == 32) {
                        viewFindViewById11 = this.mView.findViewById(R.id.air_wind_down1);
                    } else if (i14 == 48) {
                        viewFindViewById11 = this.mView.findViewById(R.id.air_wind_left);
                    } else if (i14 == 64) {
                        viewFindViewById11 = this.mView.findViewById(R.id.air_wind_up1);
                    } else if (i14 == 80) {
                        this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                        viewFindViewById11 = this.mView.findViewById(R.id.air_wind_down1);
                    } else if (i14 == 96) {
                        this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                        viewFindViewById11 = this.mView.findViewById(R.id.air_wind_down1);
                    } else if (i14 != 112) {
                        if (i14 == 128) {
                            this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                            viewFindViewById11 = this.mView.findViewById(R.id.air_wind_down1);
                        }
                        this.mView.findViewById(R.id.air_wind_8).setVisibility(0);
                        L(bArr2[2] & 255);
                        if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                            n(R.id.air_lt, bArr2[3] & 255);
                            i5 = R.id.air_rt;
                            i6 = bArr2[4];
                        } else {
                            n(R.id.air_lt, bArr2[4] & 255);
                            i5 = R.id.air_rt;
                            i6 = bArr2[3];
                        }
                        n(i5, i6 & 255);
                        textView2 = (TextView) this.mView.findViewById(R.id.air_bc);
                        if (this.Jc > -40 || this.Jc >= 127) {
                            textView2.setVisibility(8);
                        } else {
                            textView2.setVisibility(0);
                            if (this.Kc != 1) {
                                if (this.Lc == 1) {
                                    this.Jc = (int) (((double) (this.Jc - 32)) / 1.8d);
                                }
                                str3 = String.format(Locale.US, "Ext %d" + this.mContext.getString(R.string.temperature), Integer.valueOf(this.Jc));
                            } else {
                                if (this.Lc != 1) {
                                    this.Jc = ((this.Jc * 9) / 5) + 32;
                                }
                                str3 = this.Jc + this.mContext.getString(R.string.ftemperature);
                            }
                            textView2.setText(str3);
                        }
                    } else {
                        this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                        viewFindViewById11 = this.mView.findViewById(R.id.air_wind_left);
                    }
                    viewFindViewById11.setVisibility(0);
                    this.mView.findViewById(R.id.air_wind_8).setVisibility(0);
                    L(bArr2[2] & 255);
                    if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                        n(R.id.air_lt, bArr2[3] & 255);
                        i5 = R.id.air_rt;
                        i6 = bArr2[4];
                    } else {
                        n(R.id.air_lt, bArr2[4] & 255);
                        i5 = R.id.air_rt;
                        i6 = bArr2[3];
                    }
                    n(i5, i6 & 255);
                    textView2 = (TextView) this.mView.findViewById(R.id.air_bc);
                    if (this.Jc > -40) {
                        textView2.setVisibility(8);
                    } else {
                        textView2.setVisibility(8);
                    }
                }
                Ua();
                return;
                show();
            }
            if (la.mModel == 49) {
                if (bArr2 != 0) {
                    if ((bArr2[0] & '@') == 64) {
                        this.mView.findViewById(R.id.air_ac).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_ac).setVisibility(8);
                    }
                    if ((bArr2[0] & '\b') == 8) {
                        this.mView.findViewById(R.id.air_rear).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_rear).setVisibility(8);
                    }
                    if ((bArr2[0] & 4) == 4) {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(8);
                    }
                    if ((bArr2[0] & 2) == 2) {
                        this.mView.findViewById(R.id.air_wind_small).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_wind_small).setVisibility(8);
                    }
                    this.mView.findViewById(R.id.air_wind_up2).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_down2).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_up1).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_down1).setVisibility(4);
                    int i15 = bArr2[1] & 255;
                    if (i15 == 1) {
                        this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                        viewFindViewById9 = this.mView.findViewById(R.id.air_wind_left);
                    } else if (i15 == 2) {
                        this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                        viewFindViewById9 = this.mView.findViewById(R.id.air_wind_down1);
                    } else if (i15 == 3) {
                        this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                        this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                        this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                        viewFindViewById9 = this.mView.findViewById(R.id.air_wind_down1);
                    } else {
                        if (i15 != 4) {
                            if (i15 == 5) {
                                this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                                this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                                this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                                viewFindViewById9 = this.mView.findViewById(R.id.air_wind_down1);
                            }
                            m(R.id.air_lt, bArr2[2] & 255);
                            this.mView.findViewById(R.id.air_wind_8).setVisibility(0);
                            L(bArr2[3] & 255);
                            show();
                        }
                        this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                        viewFindViewById9 = this.mView.findViewById(R.id.air_wind_up1);
                    }
                    viewFindViewById9.setVisibility(0);
                    m(R.id.air_lt, bArr2[2] & 255);
                    this.mView.findViewById(R.id.air_wind_8).setVisibility(0);
                    L(bArr2[3] & 255);
                    show();
                }
                hide();
                return;
            }
            if (la.mModel == 128 || la.mModel == 130 || la.mModel == 131 || la.mModel == 134 || la.mModel == 137) {
                if (bArr2 != 0) {
                    if ((bArr2[0] & '@') == 64) {
                        this.mView.findViewById(R.id.air_ac).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_ac).setVisibility(8);
                    }
                    if ((bArr2[0] & ' ') == 32) {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_inner_loop).setVisibility(8);
                    }
                    if ((bArr2[0] & 16) == 16) {
                        this.mView.findViewById(R.id.air_rear).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_rear).setVisibility(8);
                    }
                    if ((bArr2[0] & '\b') == 8) {
                        this.mView.findViewById(R.id.air_inner_loop_a).setVisibility(0);
                        viewFindViewById = this.mView.findViewById(R.id.air_inner_loop);
                    } else {
                        viewFindViewById = this.mView.findViewById(R.id.air_inner_loop_a);
                    }
                    viewFindViewById.setVisibility(8);
                    this.mView.findViewById(R.id.air_wind_7).setVisibility(8);
                    L(bArr2[0] & 7);
                    this.mView.findViewById(R.id.air_wind_up2).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_down2).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_up1).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_down1).setVisibility(4);
                    switch (bArr2[1] & 255) {
                        case 1:
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                            viewFindViewById2 = this.mView.findViewById(R.id.air_wind_down1);
                            viewFindViewById2.setVisibility(0);
                            break;
                        case 2:
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            viewFindViewById2 = this.mView.findViewById(R.id.air_wind_up1);
                            viewFindViewById2.setVisibility(0);
                            break;
                        case 3:
                            this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                            viewFindViewById2 = this.mView.findViewById(R.id.air_wind_down1);
                            viewFindViewById2.setVisibility(0);
                            break;
                        case 4:
                            this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_down1).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                            viewFindViewById2 = this.mView.findViewById(R.id.air_wind_left);
                            viewFindViewById2.setVisibility(0);
                            break;
                        case 5:
                            this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                            viewFindViewById2 = this.mView.findViewById(R.id.air_wind_left);
                            viewFindViewById2.setVisibility(0);
                            break;
                        case 6:
                            this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            viewFindViewById2 = this.mView.findViewById(R.id.air_wind_up1);
                            viewFindViewById2.setVisibility(0);
                            break;
                        case 7:
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            viewFindViewById2 = this.mView.findViewById(R.id.air_wind_up1);
                            viewFindViewById2.setVisibility(0);
                            break;
                        case 8:
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                            viewFindViewById2 = this.mView.findViewById(R.id.air_wind_down1);
                            viewFindViewById2.setVisibility(0);
                            break;
                        case 9:
                            this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                            this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                            viewFindViewById2 = this.mView.findViewById(R.id.air_wind_down1);
                            viewFindViewById2.setVisibility(0);
                            break;
                    }
                    if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                        n(R.id.air_lt, bArr2[2] & 255);
                        i = R.id.air_rt;
                        i2 = bArr2[3];
                    } else {
                        n(R.id.air_lt, bArr2[3] & 255);
                        i = R.id.air_rt;
                        i2 = bArr2[2];
                    }
                    n(i, i2 & 255);
                    l(((bArr2[4] & 255) >> 4) & 3, ((bArr2[4] & 255) >> 0) & 3);
                    TextView textView6 = (TextView) this.mView.findViewById(R.id.air_bc);
                    int i16 = bArr2[5];
                    if (i16 <= -40 || i16 >= 87) {
                        textView6.setVisibility(8);
                    } else {
                        textView6.setVisibility(0);
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                i16 = (int) (((double) (i16 - 32)) / 1.8d);
                            }
                            str = String.format(Locale.US, "Ext %d" + this.mContext.getString(R.string.temperature), Integer.valueOf(i16));
                        } else {
                            if (this.Lc != 1) {
                                i16 = ((i16 * 9) / 5) + 32;
                            }
                            str = i16 + this.mContext.getString(R.string.ftemperature);
                        }
                        textView6.setText(str);
                    }
                }
                hide();
                return;
            }
            if (bArr2 != 0 && (bArr2[1] & 16) == 16) {
                if ((bArr2[0] & '@') == 64) {
                    this.mView.findViewById(R.id.air_ac).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_ac).setVisibility(8);
                }
                if ((bArr2[0] & ' ') == 32) {
                    this.mView.findViewById(R.id.air_inner_loop).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_inner_loop).setVisibility(8);
                }
                if ((bArr2[0] & 16) == 16) {
                    this.mView.findViewById(R.id.air_wind_large).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_wind_large).setVisibility(8);
                }
                if ((bArr2[0] & '\b') == 8) {
                    this.mView.findViewById(R.id.air_wind_small).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_wind_small).setVisibility(8);
                }
                if ((bArr2[0] & 4) == 4) {
                    this.mView.findViewById(R.id.air_dual).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_dual).setVisibility(8);
                }
                if ((bArr2[0] & 2) == 2) {
                    this.mView.findViewById(R.id.air_max_fornt).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_max_fornt).setVisibility(8);
                }
                if ((bArr2[0] & 1) == 1) {
                    this.mView.findViewById(R.id.air_rear).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_rear).setVisibility(8);
                }
                if ((bArr2[1] & 128) == 128) {
                    this.mView.findViewById(R.id.air_wind_up1).setVisibility(0);
                    this.mView.findViewById(R.id.air_wind_up2).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_wind_up1).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_up2).setVisibility(4);
                }
                if ((bArr2[1] & '@') == 64) {
                    this.mView.findViewById(R.id.air_wind_left).setVisibility(0);
                    this.mView.findViewById(R.id.air_wind_right).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_wind_left).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_right).setVisibility(4);
                }
                if ((bArr2[1] & ' ') == 32) {
                    this.mView.findViewById(R.id.air_wind_down1).setVisibility(0);
                    this.mView.findViewById(R.id.air_wind_down2).setVisibility(0);
                } else {
                    this.mView.findViewById(R.id.air_wind_down1).setVisibility(4);
                    this.mView.findViewById(R.id.air_wind_down2).setVisibility(4);
                }
                L(bArr2[1] & 15);
                if (Settings.System.getInt(this.mContext.getContentResolver(), "LEFT_RIGHT_SEAT", 0) == 0) {
                    n(R.id.air_lt, bArr2[2] & 255);
                    i3 = R.id.air_rt;
                    i4 = bArr2[3];
                } else {
                    n(R.id.air_lt, bArr2[3] & 255);
                    i3 = R.id.air_rt;
                    i4 = bArr2[2];
                }
                n(i3, i4 & 255);
                if (la.mModel == 17 || la.mModel == 22) {
                    char c = bArr2[4];
                    char c2 = bArr2[4];
                    if ((bArr2[4] & ' ') == 32) {
                        this.mView.findViewById(R.id.air_inner_loop_a).setVisibility(0);
                        viewFindViewById3 = this.mView.findViewById(R.id.air_inner_loop);
                    } else {
                        viewFindViewById3 = this.mView.findViewById(R.id.air_inner_loop_a);
                    }
                    viewFindViewById3.setVisibility(8);
                    if ((bArr2[4] & 16) == 16) {
                        this.mView.findViewById(R.id.air_eco).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_eco).setVisibility(8);
                    }
                    if ((bArr2[4] & '\b') == 8) {
                        this.mView.findViewById(R.id.air_ac_max).setVisibility(0);
                        viewFindViewById4 = this.mView.findViewById(R.id.air_ac);
                    } else {
                        viewFindViewById4 = this.mView.findViewById(R.id.air_ac_max);
                    }
                    viewFindViewById4.setVisibility(8);
                    l(((bArr2[5] & 255) >> 4) & 7, ((bArr2[5] & 255) >> 0) & 7);
                    textView = (TextView) this.mView.findViewById(R.id.air_bc);
                    if (this.Jc <= -40 || this.Jc >= 127) {
                        textView.setVisibility(8);
                    } else {
                        textView.setVisibility(0);
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                this.Jc = (int) (((double) (this.Jc - 32)) / 1.8d);
                            }
                            str2 = String.format(Locale.US, "Ext %d" + this.mContext.getString(R.string.temperature), Integer.valueOf(this.Jc));
                        } else {
                            if (this.Lc != 1) {
                                this.Jc = ((this.Jc * 9) / 5) + 32;
                            }
                            str2 = this.Jc + this.mContext.getString(R.string.ftemperature);
                        }
                        textView.setText(str2);
                    }
                } else if (la.mModel == 33 || la.mModel == 34 || la.mModel == 38) {
                    if ((bArr2[4] & 128) == 128) {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(0);
                    } else {
                        viewFindViewById5 = this.mView.findViewById(R.id.air_max_fornt);
                        viewFindViewById5.setVisibility(8);
                    }
                } else if (la.mModel == 48) {
                    if ((bArr2[4] & 128) == 128) {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_max_fornt).setVisibility(8);
                    }
                    if ((bArr2[4] & '@') == 64) {
                        this.mView.findViewById(R.id.air_rear).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_rear).setVisibility(8);
                    }
                    if ((bArr2[4] & '\b') == 8) {
                        this.mView.findViewById(R.id.air_ac_max).setVisibility(0);
                        viewFindViewById5 = this.mView.findViewById(R.id.air_ac);
                    } else {
                        viewFindViewById5 = this.mView.findViewById(R.id.air_ac_max);
                    }
                    viewFindViewById5.setVisibility(8);
                } else if (la.mModel == 65) {
                    if ((bArr2[4] & 4) == 4) {
                        this.mView.findViewById(R.id.air_ac_max).setVisibility(0);
                        viewFindViewById8 = this.mView.findViewById(R.id.air_ac);
                    } else {
                        viewFindViewById8 = this.mView.findViewById(R.id.air_ac_max);
                    }
                    viewFindViewById8.setVisibility(8);
                    textView = (TextView) this.mView.findViewById(R.id.air_bc);
                    int i17 = bArr2[5];
                    if (i17 <= -40 || i17 >= 86) {
                        textView.setVisibility(8);
                    } else {
                        textView.setVisibility(0);
                        if (this.Kc != 1) {
                            if (this.Lc == 1) {
                                i17 = (int) (((double) (i17 - 32)) / 1.8d);
                            }
                            str2 = String.format(Locale.US, "Ext %d" + this.mContext.getString(R.string.temperature), Integer.valueOf(i17));
                        } else {
                            if (this.Lc != 1) {
                                i17 = ((i17 * 9) / 5) + 32;
                            }
                            str2 = i17 + this.mContext.getString(R.string.ftemperature);
                        }
                        textView.setText(str2);
                    }
                } else if (la.mModel != -1) {
                    if ((bArr2[4] & 128) == 128) {
                        this.mView.findViewById(R.id.air_inner_loop_a).setVisibility(0);
                        viewFindViewById6 = this.mView.findViewById(R.id.air_inner_loop);
                    } else {
                        viewFindViewById6 = this.mView.findViewById(R.id.air_inner_loop_a);
                    }
                    viewFindViewById6.setVisibility(8);
                    if ((bArr2[4] & '\b') == 8) {
                        this.mView.findViewById(R.id.air_rear_lock).setVisibility(0);
                    } else {
                        this.mView.findViewById(R.id.air_rear_lock).setVisibility(8);
                    }
                    if ((bArr2[4] & 4) == 4) {
                        this.mView.findViewById(R.id.air_ac_max).setVisibility(0);
                        viewFindViewById7 = this.mView.findViewById(R.id.air_ac);
                    } else {
                        viewFindViewById7 = this.mView.findViewById(R.id.air_ac_max);
                    }
                    viewFindViewById7.setVisibility(8);
                    l(((bArr2[4] & 255) >> 4) & 3, ((bArr2[4] & 255) >> 0) & 3);
                }
            }
            Ua();
            return;
            show();
        } catch (Exception e) {
            Log.e("AirPanel", Log.getStackTraceString(e));
        }
    }
}
