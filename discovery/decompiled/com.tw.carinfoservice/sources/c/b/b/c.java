package c.b.b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.tw.carinfoservice.R;
import com.tw.qcarcamlib.CamParamConstant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public WindowManager f459a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public WindowManager.LayoutParams f460b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public View f461c;
    public TextView e;
    public Context f;
    public c.b.b.f.b g;
    public c.b.b.e h;
    public boolean d = false;
    public int i = 0;
    public Handler j = new a();
    public ArrayList<g> k = new ArrayList<>();

    public class a extends Handler {
        public a() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                if (message.what != 65281) {
                    return;
                }
                Intent intent = new Intent();
                intent.setAction("LRREVERSE_RESET");
                c.this.f.sendBroadcast(intent);
            } catch (Exception unused) {
            }
        }
    }

    public class b implements SeekBar.OnSeekBarChangeListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f463a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ c.b.b.e f464b;

        public b(Context context, c.b.b.e eVar) {
            this.f463a = context;
            this.f464b = eVar;
        }

        /* JADX WARN: Code duplicated, block: B:17:0x00d3 A[LOOP:0: B:15:0x00cd->B:17:0x00d3, LOOP_END] */
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            TextView textView;
            StringBuilder sb;
            Context context;
            int i2;
            Iterator<g> it;
            if (z) {
                int i3 = i - 50;
                int id = seekBar.getId();
                if (id == R.id.brightness_level) {
                    textView = (TextView) c.this.f461c.findViewById(R.id.brightness_title);
                    sb = new StringBuilder();
                    context = this.f463a;
                    i2 = R.string.brightness;
                } else {
                    if (id != R.id.contrast_level) {
                        if (id == R.id.saturation_level) {
                            textView = (TextView) c.this.f461c.findViewById(R.id.saturation_title);
                            sb = new StringBuilder();
                            context = this.f463a;
                            i2 = R.string.saturation;
                        } else if (id == R.id.offset_level) {
                            TextView textView2 = (TextView) c.this.f461c.findViewById(R.id.offset_title);
                            StringBuilder sb2 = new StringBuilder();
                            c.a.a.a.a.c(this.f463a, R.string.offset, sb2, "(", i3);
                            c.a.a.a.a.d(sb2, ")", textView2);
                            c.this.b(i3);
                        }
                        this.f464b.d(((SeekBar) c.this.f461c.findViewById(R.id.brightness_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.contrast_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.saturation_level)).getProgress() - 50);
                        it = c.this.k.iterator();
                        while (it.hasNext()) {
                            it.next().a();
                        }
                    }
                    textView = (TextView) c.this.f461c.findViewById(R.id.contrast_title);
                    sb = new StringBuilder();
                    context = this.f463a;
                    i2 = R.string.contrast;
                }
                c.a.a.a.a.c(context, i2, sb, "(", i3);
                c.a.a.a.a.d(sb, ")", textView);
                this.f464b.d(((SeekBar) c.this.f461c.findViewById(R.id.brightness_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.contrast_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.saturation_level)).getProgress() - 50);
                it = c.this.k.iterator();
                while (it.hasNext()) {
                    it.next().a();
                }
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    /* JADX INFO: renamed from: c.b.b.c$c, reason: collision with other inner class name */
    public class ViewOnClickListenerC0019c implements View.OnClickListener {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Context f466b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final /* synthetic */ c.b.b.e f467c;

        public ViewOnClickListenerC0019c(Context context, c.b.b.e eVar) {
            this.f466b = context;
            this.f467c = eVar;
        }

        /* JADX WARN: Code duplicated, block: B:58:0x0263 A[LOOP:0: B:56:0x025d->B:58:0x0263, LOOP_END] */
        @Override // android.view.View.OnClickListener
        @SuppressLint({"NonConstantResourceId"})
        public void onClick(View view) {
            int i;
            int progress;
            View viewFindViewById;
            View viewFindViewById2;
            Iterator<g> it;
            int id = view.getId();
            if (id != R.id.brightness_down) {
                if (id == R.id.brightness_up) {
                    progress = (((SeekBar) c.this.f461c.findViewById(R.id.brightness_level)).getProgress() - 50) + 1;
                    if (progress > 50) {
                        progress = 50;
                    }
                    TextView textView = (TextView) c.this.f461c.findViewById(R.id.brightness_title);
                    StringBuilder sb = new StringBuilder();
                    c.a.a.a.a.c(this.f466b, R.string.brightness, sb, "(", progress);
                    c.a.a.a.a.d(sb, ")", textView);
                    viewFindViewById = c.this.f461c.findViewById(R.id.brightness_level);
                } else if (id == R.id.contrast_down) {
                    int progress2 = (((SeekBar) c.this.f461c.findViewById(R.id.contrast_level)).getProgress() - 50) - 1;
                    i = progress2 >= -50 ? progress2 : -50;
                    TextView textView2 = (TextView) c.this.f461c.findViewById(R.id.contrast_title);
                    StringBuilder sb2 = new StringBuilder();
                    c.a.a.a.a.c(this.f466b, R.string.contrast, sb2, "(", i);
                    c.a.a.a.a.d(sb2, ")", textView2);
                    viewFindViewById2 = c.this.f461c.findViewById(R.id.contrast_level);
                } else {
                    if (id != R.id.contrast_up) {
                        if (id == R.id.saturation_down) {
                            int progress3 = (((SeekBar) c.this.f461c.findViewById(R.id.saturation_level)).getProgress() - 50) - 1;
                            i = progress3 >= -50 ? progress3 : -50;
                            TextView textView3 = (TextView) c.this.f461c.findViewById(R.id.saturation_title);
                            StringBuilder sb3 = new StringBuilder();
                            c.a.a.a.a.c(this.f466b, R.string.saturation, sb3, "(", i);
                            c.a.a.a.a.d(sb3, ")", textView3);
                            viewFindViewById2 = c.this.f461c.findViewById(R.id.saturation_level);
                        } else if (id == R.id.saturation_up) {
                            progress = (((SeekBar) c.this.f461c.findViewById(R.id.saturation_level)).getProgress() - 50) + 1;
                            if (progress > 50) {
                                progress = 50;
                            }
                            TextView textView4 = (TextView) c.this.f461c.findViewById(R.id.saturation_title);
                            StringBuilder sb4 = new StringBuilder();
                            c.a.a.a.a.c(this.f466b, R.string.saturation, sb4, "(", progress);
                            c.a.a.a.a.d(sb4, ")", textView4);
                            viewFindViewById = c.this.f461c.findViewById(R.id.saturation_level);
                        } else if (id == R.id.saturation_up) {
                            int progress4 = (((SeekBar) c.this.f461c.findViewById(R.id.offset_level)).getProgress() - 50) - 1;
                            i = progress4 >= -50 ? progress4 : -50;
                            TextView textView5 = (TextView) c.this.f461c.findViewById(R.id.offset_title);
                            StringBuilder sb5 = new StringBuilder();
                            c.a.a.a.a.c(this.f466b, R.string.offset, sb5, "(", i);
                            c.a.a.a.a.d(sb5, ")", textView5);
                            ((SeekBar) c.this.f461c.findViewById(R.id.offset_level)).setProgress(i + 50);
                            c.this.b(i);
                        } else if (id == R.id.saturation_up) {
                            int progress5 = (((SeekBar) c.this.f461c.findViewById(R.id.offset_level)).getProgress() - 50) + 1;
                            if (progress5 > 50) {
                                progress5 = 50;
                            }
                            TextView textView6 = (TextView) c.this.f461c.findViewById(R.id.offset_title);
                            StringBuilder sb6 = new StringBuilder();
                            c.a.a.a.a.c(this.f466b, R.string.offset, sb6, "(", progress5);
                            c.a.a.a.a.d(sb6, ")", textView6);
                            ((SeekBar) c.this.f461c.findViewById(R.id.offset_level)).setProgress(progress5 + 50);
                            c.this.b(progress5);
                        }
                        this.f467c.d(((SeekBar) c.this.f461c.findViewById(R.id.brightness_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.contrast_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.saturation_level)).getProgress() - 50);
                        it = c.this.k.iterator();
                        while (it.hasNext()) {
                            it.next().a();
                        }
                    }
                    progress = (((SeekBar) c.this.f461c.findViewById(R.id.contrast_level)).getProgress() - 50) + 1;
                    if (progress > 50) {
                        progress = 50;
                    }
                    TextView textView7 = (TextView) c.this.f461c.findViewById(R.id.contrast_title);
                    StringBuilder sb7 = new StringBuilder();
                    c.a.a.a.a.c(this.f466b, R.string.contrast, sb7, "(", progress);
                    c.a.a.a.a.d(sb7, ")", textView7);
                    viewFindViewById = c.this.f461c.findViewById(R.id.contrast_level);
                }
                ((SeekBar) viewFindViewById).setProgress(progress + 50);
                this.f467c.d(((SeekBar) c.this.f461c.findViewById(R.id.brightness_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.contrast_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.saturation_level)).getProgress() - 50);
                it = c.this.k.iterator();
                while (it.hasNext()) {
                    it.next().a();
                }
            }
            int progress6 = (((SeekBar) c.this.f461c.findViewById(R.id.brightness_level)).getProgress() - 50) - 1;
            i = progress6 >= -50 ? progress6 : -50;
            TextView textView8 = (TextView) c.this.f461c.findViewById(R.id.brightness_title);
            StringBuilder sb8 = new StringBuilder();
            c.a.a.a.a.c(this.f466b, R.string.brightness, sb8, "(", i);
            c.a.a.a.a.d(sb8, ")", textView8);
            viewFindViewById2 = c.this.f461c.findViewById(R.id.brightness_level);
            ((SeekBar) viewFindViewById2).setProgress(i + 50);
            this.f467c.d(((SeekBar) c.this.f461c.findViewById(R.id.brightness_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.contrast_level)).getProgress() - 50, ((SeekBar) c.this.f461c.findViewById(R.id.saturation_level)).getProgress() - 50);
            it = c.this.k.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        }
    }

    public class d implements View.OnClickListener {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Context f468b;

        public d(Context context) {
            this.f468b = context;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            View viewFindViewById;
            int i;
            String string;
            View viewFindViewById2;
            int i2;
            String string2;
            int id = view.getId();
            if (id == R.id.tv_camera_reset) {
                c cVar = c.this;
                if (cVar.d) {
                    int i3 = Settings.System.getInt(cVar.f.getContentResolver(), "TWCameraSaveType", 0);
                    if (i3 != 0) {
                        if (i3 == 1) {
                            c.b.b.f.a.E(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraBrightnessSave"), 50, cVar.f);
                            c.b.b.f.a.E(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraContrastSave"), 50, cVar.f);
                            c.b.b.f.a.E(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraSaturationSave"), 50, cVar.f);
                        } else if (i3 == 2) {
                            c.b.b.f.a.D(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraBrightnessSave"), 100);
                            c.b.b.f.a.D(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraContrastSave"), 100);
                            string2 = Settings.System.getString(cVar.f.getContentResolver(), "TWCameraSaturationSave");
                            c.b.b.f.a.D(string2, 100);
                        }
                    } else if (c.b.b.f.a.f()) {
                        c.b.b.f.a.E("brightness", 50, cVar.f);
                        c.b.b.f.a.E("contrast", 50, cVar.f);
                        c.b.b.f.a.E("saturation", 50, cVar.f);
                    } else if (c.b.b.f.a.i()) {
                        c.b.b.f.a.E("brightness", 100, cVar.f);
                        c.b.b.f.a.E("contrast", 100, cVar.f);
                        c.b.b.f.a.E("saturation", 100, cVar.f);
                    } else {
                        if (c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                            c.b.b.f.a.D("persist.camera.brightness2.aux", 100);
                            c.b.b.f.a.D("persist.camera.contrast2.aux", 100);
                            string2 = "persist.camera.saturation2.aux";
                        } else {
                            c.b.b.f.a.D(CamParamConstant.AUX.PERSIST_CAM_BRIGHTNESS, 100);
                            c.b.b.f.a.D(CamParamConstant.AUX.PERSIST_CAM_CONTRAST, 100);
                            string2 = CamParamConstant.AUX.PERSIST_CAM_SATURATION;
                        }
                        c.b.b.f.a.D(string2, 100);
                    }
                    TextView textView = (TextView) cVar.f461c.findViewById(R.id.brightness_title);
                    StringBuilder sb = new StringBuilder();
                    c.a.a.a.a.c(cVar.f, R.string.brightness, sb, "(", 0);
                    c.a.a.a.a.d(sb, ")", textView);
                    ((SeekBar) cVar.f461c.findViewById(R.id.brightness_level)).setMax(100);
                    ((SeekBar) cVar.f461c.findViewById(R.id.brightness_level)).setProgress(50);
                    TextView textView2 = (TextView) cVar.f461c.findViewById(R.id.contrast_title);
                    StringBuilder sb2 = new StringBuilder();
                    c.a.a.a.a.c(cVar.f, R.string.contrast, sb2, "(", 0);
                    c.a.a.a.a.d(sb2, ")", textView2);
                    ((SeekBar) cVar.f461c.findViewById(R.id.contrast_level)).setMax(100);
                    ((SeekBar) cVar.f461c.findViewById(R.id.contrast_level)).setProgress(50);
                    TextView textView3 = (TextView) cVar.f461c.findViewById(R.id.saturation_title);
                    StringBuilder sb3 = new StringBuilder();
                    c.a.a.a.a.c(cVar.f, R.string.saturation, sb3, "(", 0);
                    c.a.a.a.a.d(sb3, ")", textView3);
                    ((SeekBar) cVar.f461c.findViewById(R.id.saturation_level)).setMax(100);
                    ((SeekBar) cVar.f461c.findViewById(R.id.saturation_level)).setProgress(50);
                    TextView textView4 = (TextView) cVar.f461c.findViewById(R.id.offset_title);
                    StringBuilder sb4 = new StringBuilder();
                    c.a.a.a.a.c(cVar.f, R.string.offset, sb4, "(", 0);
                    c.a.a.a.a.d(sb4, ")", textView4);
                    ((SeekBar) cVar.f461c.findViewById(R.id.offset_level)).setMax(100);
                    ((SeekBar) cVar.f461c.findViewById(R.id.offset_level)).setProgress(50);
                    cVar.b(0);
                    cVar.h.d(0, 0, 0);
                }
            } else {
                if (id == R.id.tv_layout_frame) {
                    ((TextView) c.this.f461c.findViewById(R.id.tv_layout_frame)).setTextColor(-1);
                    ((TextView) c.this.f461c.findViewById(R.id.tv_layout_camera)).setTextColor(-7829368);
                    ((TextView) c.this.f461c.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(1);
                    ((TextView) c.this.f461c.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(0);
                    c.this.f461c.findViewById(R.id.scrollViewFrame).setVisibility(0);
                    viewFindViewById2 = c.this.f461c.findViewById(R.id.scrollViewCamera);
                    i2 = 8;
                } else if (id == R.id.tv_layout_camera) {
                    int progress = ((SeekBar) c.this.f461c.findViewById(R.id.brightness_level)).getProgress() - 50;
                    int progress2 = ((SeekBar) c.this.f461c.findViewById(R.id.contrast_level)).getProgress() - 50;
                    int progress3 = ((SeekBar) c.this.f461c.findViewById(R.id.saturation_level)).getProgress() - 50;
                    int i4 = (c.b.b.f.a.i() || c.b.b.f.a.f()) ? 0 : Settings.System.getInt(this.f468b.getContentResolver(), "TWCameraSaveSize", 128);
                    int i5 = progress + i4;
                    int i6 = progress2 + i4;
                    int i7 = progress3 + i4;
                    int i8 = Settings.System.getInt(this.f468b.getContentResolver(), "TWCameraSaveType", 0);
                    if (i8 != 0) {
                        if (i8 == 1) {
                            c.b.b.f.a.E(Settings.System.getString(this.f468b.getContentResolver(), "TWCameraBrightnessSave"), i5, this.f468b);
                            c.b.b.f.a.E(Settings.System.getString(this.f468b.getContentResolver(), "TWCameraContrastSave"), i6, this.f468b);
                            c.b.b.f.a.E(Settings.System.getString(this.f468b.getContentResolver(), "TWCameraSaturationSave"), i7, this.f468b);
                        } else if (i8 == 2) {
                            c.b.b.f.a.D(Settings.System.getString(this.f468b.getContentResolver(), "TWCameraBrightnessSave"), i5);
                            c.b.b.f.a.D(Settings.System.getString(this.f468b.getContentResolver(), "TWCameraContrastSave"), i6);
                            string = Settings.System.getString(this.f468b.getContentResolver(), "TWCameraSaturationSave");
                            c.b.b.f.a.D(string, i7);
                        }
                    } else if (c.b.b.f.a.i() || c.b.b.f.a.f()) {
                        c.b.b.f.a.E("brightness", i5, this.f468b);
                        c.b.b.f.a.E("contrast", i6, this.f468b);
                        c.b.b.f.a.E("saturation", i7, this.f468b);
                    } else if (c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                        c.b.b.f.a.D("persist.camera.brightness2.aux", i5);
                        c.b.b.f.a.D("persist.camera.contrast2.aux", i6);
                        c.b.b.f.a.D("persist.camera.saturation2.aux", i7);
                    } else {
                        c.b.b.f.a.D(CamParamConstant.AUX.PERSIST_CAM_BRIGHTNESS, i5);
                        c.b.b.f.a.D(CamParamConstant.AUX.PERSIST_CAM_CONTRAST, i6);
                        string = CamParamConstant.AUX.PERSIST_CAM_SATURATION;
                        c.b.b.f.a.D(string, i7);
                    }
                    ((TextView) c.this.f461c.findViewById(R.id.tv_layout_frame)).setTextColor(-7829368);
                    ((TextView) c.this.f461c.findViewById(R.id.tv_layout_camera)).setTextColor(-1);
                    ((TextView) c.this.f461c.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(0);
                    ((TextView) c.this.f461c.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(1);
                    c.this.f461c.findViewById(R.id.scrollViewFrame).setVisibility(8);
                    viewFindViewById2 = c.this.f461c.findViewById(R.id.scrollViewCamera);
                    i2 = 0;
                } else if (id == R.id.lev_arrow) {
                    c cVar2 = c.this;
                    if (cVar2.i == 0) {
                        cVar2.i = 1;
                        viewFindViewById = cVar2.f461c.findViewById(R.id.layout_camera);
                        i = 0;
                    } else {
                        cVar2.i = 0;
                        viewFindViewById = cVar2.f461c.findViewById(R.id.layout_camera);
                        i = 8;
                    }
                    viewFindViewById.setVisibility(i);
                    ((ImageView) c.this.f461c.findViewById(R.id.lev_arrow)).getDrawable().setLevel(c.this.i);
                }
                viewFindViewById2.setVisibility(i2);
            }
            Iterator<g> it = c.this.k.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        }
    }

    public class e implements CompoundButton.OnCheckedChangeListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f470a;

        public e(Context context) {
            this.f470a = context;
        }

        /* JADX WARN: Code duplicated, block: B:21:0x0099  */
        /* JADX WARN: Code duplicated, block: B:23:0x00ad A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:24:0x00af  */
        /* JADX WARN: Code duplicated, block: B:25:0x00bb A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:26:0x00bd  */
        /* JADX WARN: Code duplicated, block: B:27:0x00ca A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:28:0x00cc  */
        /* JADX WARN: Code duplicated, block: B:29:0x00d9 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:30:0x00db  */
        /* JADX WARN: Code duplicated, block: B:31:0x00e8 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:32:0x00ea  */
        /* JADX WARN: Code duplicated, block: B:33:0x00f7 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:34:0x00f9  */
        /* JADX WARN: Code duplicated, block: B:35:0x0106 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:36:0x0108  */
        /* JADX WARN: Code duplicated, block: B:37:0x0115 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:38:0x0117  */
        /* JADX WARN: Code duplicated, block: B:39:0x0126 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:40:0x0128  */
        /* JADX WARN: Code duplicated, block: B:41:0x0137 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:42:0x0139  */
        /* JADX WARN: Code duplicated, block: B:46:0x0183 A[LOOP:0: B:44:0x017d->B:46:0x0183, LOOP_END] */
        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            int id;
            TextView textView;
            Context context;
            int i;
            Iterator<g> it;
            View viewFindViewById;
            if (z) {
                switch (c.b.b.f.a.f478a) {
                    case 0:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_auto);
                        break;
                    case 1:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_ntsc);
                        break;
                    case 2:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_pal);
                        break;
                    case 3:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_25f);
                        break;
                    case 4:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_30f);
                        break;
                    case 5:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_1080p_25f);
                        break;
                    case 6:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_1080p_30f);
                        break;
                    case 7:
                        viewFindViewById = c.this.f461c.findViewById(R.id.tvi_25f);
                        break;
                    case 8:
                        viewFindViewById = c.this.f461c.findViewById(R.id.tvi_30f);
                        break;
                    case 9:
                        viewFindViewById = c.this.f461c.findViewById(R.id.tvi_1080p_25f);
                        break;
                    case 10:
                        viewFindViewById = c.this.f461c.findViewById(R.id.tvi_1080p_30f);
                        break;
                    default:
                        id = compoundButton.getId();
                        if (id == R.id.ahd_auto) {
                            c.b.b.f.a.f478a = 0;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_ahd_auto;
                        } else if (id == R.id.ahd_ntsc) {
                            c.b.b.f.a.f478a = 1;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_ahd_ntsc;
                        } else if (id == R.id.ahd_pal) {
                            c.b.b.f.a.f478a = 2;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_ahd_pal;
                        } else if (id == R.id.ahd_25f) {
                            c.b.b.f.a.f478a = 3;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_ahd_25f;
                        } else if (id == R.id.ahd_30f) {
                            c.b.b.f.a.f478a = 4;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_ahd_30f;
                        } else if (id == R.id.ahd_1080p_25f) {
                            c.b.b.f.a.f478a = 5;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_ahd_1080p_25f;
                        } else if (id == R.id.ahd_1080p_30f) {
                            c.b.b.f.a.f478a = 6;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_ahd_1080p_30f;
                        } else if (id == R.id.tvi_25f) {
                            c.b.b.f.a.f478a = 7;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_tvi_25f;
                        } else if (id == R.id.tvi_30f) {
                            c.b.b.f.a.f478a = 8;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_tvi_30f;
                        } else {
                            if (id == R.id.tvi_1080p_25f) {
                                if (id == R.id.tvi_1080p_30f) {
                                    c.b.b.f.a.f478a = 10;
                                    textView = c.this.e;
                                    context = this.f470a;
                                    i = R.string.reverse_tvi_1080p_30f;
                                }
                                ((RadioButton) c.this.f461c.findViewById(id)).setChecked(true);
                                Settings.System.putInt(this.f470a.getContentResolver(), "AUX_CAMERA_INDEX", c.b.b.f.a.f478a);
                                c.this.j.removeMessages(65281);
                                c.this.j.sendEmptyMessageDelayed(65281, 200L);
                                it = c.this.k.iterator();
                                while (it.hasNext()) {
                                    it.next().a();
                                }
                            }
                            c.b.b.f.a.f478a = 9;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_tvi_1080p_25f;
                        }
                        textView.setText(context.getString(i));
                        ((RadioButton) c.this.f461c.findViewById(id)).setChecked(true);
                        Settings.System.putInt(this.f470a.getContentResolver(), "AUX_CAMERA_INDEX", c.b.b.f.a.f478a);
                        c.this.j.removeMessages(65281);
                        c.this.j.sendEmptyMessageDelayed(65281, 200L);
                        it = c.this.k.iterator();
                        while (it.hasNext()) {
                            it.next().a();
                        }
                }
                ((RadioButton) viewFindViewById).setChecked(false);
                id = compoundButton.getId();
                if (id == R.id.ahd_auto) {
                    c.b.b.f.a.f478a = 0;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_ahd_auto;
                } else if (id == R.id.ahd_ntsc) {
                    c.b.b.f.a.f478a = 1;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_ahd_ntsc;
                } else if (id == R.id.ahd_pal) {
                    c.b.b.f.a.f478a = 2;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_ahd_pal;
                } else if (id == R.id.ahd_25f) {
                    c.b.b.f.a.f478a = 3;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_ahd_25f;
                } else if (id == R.id.ahd_30f) {
                    c.b.b.f.a.f478a = 4;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_ahd_30f;
                } else if (id == R.id.ahd_1080p_25f) {
                    c.b.b.f.a.f478a = 5;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_ahd_1080p_25f;
                } else if (id == R.id.ahd_1080p_30f) {
                    c.b.b.f.a.f478a = 6;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_ahd_1080p_30f;
                } else if (id == R.id.tvi_25f) {
                    c.b.b.f.a.f478a = 7;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_tvi_25f;
                } else if (id == R.id.tvi_30f) {
                    c.b.b.f.a.f478a = 8;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_tvi_30f;
                } else {
                    if (id == R.id.tvi_1080p_25f) {
                        if (id == R.id.tvi_1080p_30f) {
                            c.b.b.f.a.f478a = 10;
                            textView = c.this.e;
                            context = this.f470a;
                            i = R.string.reverse_tvi_1080p_30f;
                        }
                        ((RadioButton) c.this.f461c.findViewById(id)).setChecked(true);
                        Settings.System.putInt(this.f470a.getContentResolver(), "AUX_CAMERA_INDEX", c.b.b.f.a.f478a);
                        c.this.j.removeMessages(65281);
                        c.this.j.sendEmptyMessageDelayed(65281, 200L);
                        it = c.this.k.iterator();
                        while (it.hasNext()) {
                            it.next().a();
                        }
                    }
                    c.b.b.f.a.f478a = 9;
                    textView = c.this.e;
                    context = this.f470a;
                    i = R.string.reverse_tvi_1080p_25f;
                }
                textView.setText(context.getString(i));
                ((RadioButton) c.this.f461c.findViewById(id)).setChecked(true);
                Settings.System.putInt(this.f470a.getContentResolver(), "AUX_CAMERA_INDEX", c.b.b.f.a.f478a);
                c.this.j.removeMessages(65281);
                c.this.j.sendEmptyMessageDelayed(65281, 200L);
                it = c.this.k.iterator();
                while (it.hasNext()) {
                    it.next().a();
                }
            }
        }
    }

    public class f implements CompoundButton.OnCheckedChangeListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f472a;

        public f(Context context) {
            this.f472a = context;
        }

        /* JADX WARN: Code duplicated, block: B:20:0x008c  */
        /* JADX WARN: Code duplicated, block: B:22:0x00a0 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:23:0x00a2  */
        /* JADX WARN: Code duplicated, block: B:24:0x00af A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:25:0x00b1  */
        /* JADX WARN: Code duplicated, block: B:26:0x00be A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:27:0x00c0  */
        /* JADX WARN: Code duplicated, block: B:28:0x00cd A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:29:0x00cf  */
        /* JADX WARN: Code duplicated, block: B:30:0x00dc A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:31:0x00de  */
        /* JADX WARN: Code duplicated, block: B:32:0x00eb A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:33:0x00ed  */
        /* JADX WARN: Code duplicated, block: B:34:0x00fa A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:35:0x00fc  */
        /* JADX WARN: Code duplicated, block: B:36:0x0109 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:37:0x010b  */
        /* JADX WARN: Code duplicated, block: B:38:0x011a A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:39:0x011c  */
        /* JADX WARN: Code duplicated, block: B:43:0x0168 A[LOOP:0: B:41:0x0162->B:43:0x0168, LOOP_END] */
        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            int id;
            TextView textView;
            Context context;
            int i;
            Iterator<g> it;
            View viewFindViewById;
            if (z) {
                switch (c.b.b.f.a.f478a) {
                    case 0:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_ntsc);
                        break;
                    case 1:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_pal);
                        break;
                    case 2:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_25f);
                        break;
                    case 3:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_30f);
                        break;
                    case 4:
                        viewFindViewById = c.this.f461c.findViewById(R.id.tvi_25f);
                        break;
                    case 5:
                        viewFindViewById = c.this.f461c.findViewById(R.id.tvi_30f);
                        break;
                    case 6:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_1080p_25f);
                        break;
                    case 7:
                        viewFindViewById = c.this.f461c.findViewById(R.id.ahd_1080p_30f);
                        break;
                    case 8:
                        viewFindViewById = c.this.f461c.findViewById(R.id.tvi_1080p_25f);
                        break;
                    case 9:
                        viewFindViewById = c.this.f461c.findViewById(R.id.tvi_1080p_30f);
                        break;
                    default:
                        id = compoundButton.getId();
                        if (id == R.id.ahd_ntsc) {
                            c.b.b.f.a.f478a = 0;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_ahd_ntsc;
                        } else if (id == R.id.ahd_pal) {
                            c.b.b.f.a.f478a = 1;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_ahd_pal;
                        } else if (id == R.id.ahd_25f) {
                            c.b.b.f.a.f478a = 2;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_ahd_25f;
                        } else if (id == R.id.ahd_30f) {
                            c.b.b.f.a.f478a = 3;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_ahd_30f;
                        } else if (id == R.id.tvi_25f) {
                            c.b.b.f.a.f478a = 4;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_tvi_25f;
                        } else if (id == R.id.tvi_30f) {
                            c.b.b.f.a.f478a = 5;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_tvi_30f;
                        } else if (id == R.id.ahd_1080p_25f) {
                            c.b.b.f.a.f478a = 6;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_ahd_1080p_25f;
                        } else if (id == R.id.ahd_1080p_30f) {
                            c.b.b.f.a.f478a = 7;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_ahd_1080p_30f;
                        } else {
                            if (id == R.id.tvi_1080p_25f) {
                                if (id == R.id.tvi_1080p_30f) {
                                    c.b.b.f.a.f478a = 9;
                                    textView = c.this.e;
                                    context = this.f472a;
                                    i = R.string.reverse_tvi_1080p_30f;
                                }
                                SystemProperties.set(CamParamConstant.AUX.PERSIST_CAM_TYPE, String.valueOf(c.b.b.f.a.f478a));
                                Settings.System.putInt(this.f472a.getContentResolver(), CamParamConstant.AUX.PERSIST_CAM_TYPE, c.b.b.f.a.f478a);
                                c.b.b.f.a.f478a = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_TYPE, 0);
                                c.this.j.removeMessages(65281);
                                c.this.j.sendEmptyMessageDelayed(65281, 200L);
                                it = c.this.k.iterator();
                                while (it.hasNext()) {
                                    it.next().a();
                                }
                            }
                            c.b.b.f.a.f478a = 8;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_tvi_1080p_25f;
                        }
                        textView.setText(context.getString(i));
                        SystemProperties.set(CamParamConstant.AUX.PERSIST_CAM_TYPE, String.valueOf(c.b.b.f.a.f478a));
                        Settings.System.putInt(this.f472a.getContentResolver(), CamParamConstant.AUX.PERSIST_CAM_TYPE, c.b.b.f.a.f478a);
                        c.b.b.f.a.f478a = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_TYPE, 0);
                        c.this.j.removeMessages(65281);
                        c.this.j.sendEmptyMessageDelayed(65281, 200L);
                        it = c.this.k.iterator();
                        while (it.hasNext()) {
                            it.next().a();
                        }
                }
                ((RadioButton) viewFindViewById).setChecked(false);
                id = compoundButton.getId();
                if (id == R.id.ahd_ntsc) {
                    c.b.b.f.a.f478a = 0;
                    textView = c.this.e;
                    context = this.f472a;
                    i = R.string.reverse_ahd_ntsc;
                } else if (id == R.id.ahd_pal) {
                    c.b.b.f.a.f478a = 1;
                    textView = c.this.e;
                    context = this.f472a;
                    i = R.string.reverse_ahd_pal;
                } else if (id == R.id.ahd_25f) {
                    c.b.b.f.a.f478a = 2;
                    textView = c.this.e;
                    context = this.f472a;
                    i = R.string.reverse_ahd_25f;
                } else if (id == R.id.ahd_30f) {
                    c.b.b.f.a.f478a = 3;
                    textView = c.this.e;
                    context = this.f472a;
                    i = R.string.reverse_ahd_30f;
                } else if (id == R.id.tvi_25f) {
                    c.b.b.f.a.f478a = 4;
                    textView = c.this.e;
                    context = this.f472a;
                    i = R.string.reverse_tvi_25f;
                } else if (id == R.id.tvi_30f) {
                    c.b.b.f.a.f478a = 5;
                    textView = c.this.e;
                    context = this.f472a;
                    i = R.string.reverse_tvi_30f;
                } else if (id == R.id.ahd_1080p_25f) {
                    c.b.b.f.a.f478a = 6;
                    textView = c.this.e;
                    context = this.f472a;
                    i = R.string.reverse_ahd_1080p_25f;
                } else if (id == R.id.ahd_1080p_30f) {
                    c.b.b.f.a.f478a = 7;
                    textView = c.this.e;
                    context = this.f472a;
                    i = R.string.reverse_ahd_1080p_30f;
                } else {
                    if (id == R.id.tvi_1080p_25f) {
                        if (id == R.id.tvi_1080p_30f) {
                            c.b.b.f.a.f478a = 9;
                            textView = c.this.e;
                            context = this.f472a;
                            i = R.string.reverse_tvi_1080p_30f;
                        }
                        SystemProperties.set(CamParamConstant.AUX.PERSIST_CAM_TYPE, String.valueOf(c.b.b.f.a.f478a));
                        Settings.System.putInt(this.f472a.getContentResolver(), CamParamConstant.AUX.PERSIST_CAM_TYPE, c.b.b.f.a.f478a);
                        c.b.b.f.a.f478a = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_TYPE, 0);
                        c.this.j.removeMessages(65281);
                        c.this.j.sendEmptyMessageDelayed(65281, 200L);
                        it = c.this.k.iterator();
                        while (it.hasNext()) {
                            it.next().a();
                        }
                    }
                    c.b.b.f.a.f478a = 8;
                    textView = c.this.e;
                    context = this.f472a;
                    i = R.string.reverse_tvi_1080p_25f;
                }
                textView.setText(context.getString(i));
                SystemProperties.set(CamParamConstant.AUX.PERSIST_CAM_TYPE, String.valueOf(c.b.b.f.a.f478a));
                Settings.System.putInt(this.f472a.getContentResolver(), CamParamConstant.AUX.PERSIST_CAM_TYPE, c.b.b.f.a.f478a);
                c.b.b.f.a.f478a = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_TYPE, 0);
                c.this.j.removeMessages(65281);
                c.this.j.sendEmptyMessageDelayed(65281, 200L);
                it = c.this.k.iterator();
                while (it.hasNext()) {
                    it.next().a();
                }
            }
        }
    }

    public interface g {
        void a();
    }

    /* JADX WARN: Code duplicated, block: B:15:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:16:0x00d4  */
    /* JADX WARN: Failed to find 'out' block for switch in B:47:0x0163. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:64:0x0269. Please report as an issue. */
    public c(Context context, c.b.b.f.b bVar, c.b.b.e eVar) {
        boolean z;
        CompoundButton.OnCheckedChangeListener fVar;
        View view;
        int i;
        View viewFindViewById;
        View view2;
        int i2;
        View viewFindViewById2;
        int i3;
        this.f = context;
        this.g = bVar;
        this.h = eVar;
        View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.camera, (ViewGroup) null);
        this.f461c = viewInflate;
        b bVar2 = new b(context, eVar);
        ((SeekBar) viewInflate.findViewById(R.id.brightness_level)).setOnSeekBarChangeListener(bVar2);
        ((SeekBar) this.f461c.findViewById(R.id.contrast_level)).setOnSeekBarChangeListener(bVar2);
        ((SeekBar) this.f461c.findViewById(R.id.saturation_level)).setOnSeekBarChangeListener(bVar2);
        ((SeekBar) this.f461c.findViewById(R.id.offset_level)).setOnSeekBarChangeListener(bVar2);
        ViewOnClickListenerC0019c viewOnClickListenerC0019c = new ViewOnClickListenerC0019c(context, eVar);
        d dVar = new d(context);
        this.e = (TextView) this.f461c.findViewById(R.id.reverse_ahd);
        this.f461c.findViewById(R.id.tv_layout_camera).setVisibility(Settings.System.getInt(context.getContentResolver(), "TWCameraShowLayoutCamera", 0) == 0 ? 0 : 8);
        if (Settings.System.getInt(context.getContentResolver(), "TWCameraViewAuto", 0) == 0) {
            int i4 = Settings.System.getInt(context.getContentResolver(), "TWCameraCh", 0);
            if (i4 != 0) {
                if (i4 != 1) {
                    if (i4 == 2) {
                        c.b.b.f.a.H(bVar, context);
                    } else if (i4 == 3) {
                        c.b.b.f.a.x(bVar, context);
                    }
                    z = true;
                } else {
                    i3 = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_TYPE, 0);
                    c.b.b.f.a.f478a = i3;
                    this.f461c.findViewById(R.id.ahd_auto).setVisibility(8);
                    this.f461c.findViewById(R.id.ahd_auto_view).setVisibility(8);
                }
                z = false;
            } else if (c.b.b.f.a.i() || c.b.b.f.a.g() || c.b.b.f.a.k() || c.b.b.f.a.e() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                if (c.b.b.f.a.f480c) {
                    c.b.b.f.a.x(bVar, context);
                } else {
                    c.b.b.f.a.H(bVar, context);
                }
                z = true;
            } else {
                i3 = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_TYPE, 0);
                c.b.b.f.a.f478a = i3;
                this.f461c.findViewById(R.id.ahd_auto).setVisibility(8);
                this.f461c.findViewById(R.id.ahd_auto_view).setVisibility(8);
                z = false;
            }
        } else {
            z = Settings.System.getInt(context.getContentResolver(), "TWCameraViewAuto", 0) == 2;
            if (!z) {
                this.f461c.findViewById(R.id.ahd_auto).setVisibility(8);
                this.f461c.findViewById(R.id.ahd_auto_view).setVisibility(8);
            }
        }
        if (z) {
            switch (c.b.b.f.a.f478a) {
                case 0:
                    this.e.setText(context.getString(R.string.reverse_ahd_auto));
                    view2 = this.f461c;
                    i2 = R.id.ahd_auto;
                    viewFindViewById2 = view2.findViewById(i2);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 1:
                    this.e.setText(context.getString(R.string.reverse_ahd_ntsc));
                    view2 = this.f461c;
                    i2 = R.id.ahd_ntsc;
                    viewFindViewById2 = view2.findViewById(i2);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 2:
                    this.e.setText(context.getString(R.string.reverse_ahd_pal));
                    viewFindViewById2 = this.f461c.findViewById(R.id.ahd_pal);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 3:
                    this.e.setText(context.getString(R.string.reverse_ahd_25f));
                    view2 = this.f461c;
                    i2 = R.id.ahd_25f;
                    viewFindViewById2 = view2.findViewById(i2);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 4:
                    this.e.setText(context.getString(R.string.reverse_ahd_30f));
                    viewFindViewById2 = this.f461c.findViewById(R.id.ahd_30f);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 5:
                    this.e.setText(context.getString(R.string.reverse_ahd_1080p_25f));
                    viewFindViewById2 = this.f461c.findViewById(R.id.ahd_1080p_25f);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 6:
                    this.e.setText(context.getString(R.string.reverse_ahd_1080p_30f));
                    viewFindViewById2 = this.f461c.findViewById(R.id.ahd_1080p_30f);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 7:
                    this.e.setText(context.getString(R.string.reverse_tvi_25f));
                    viewFindViewById2 = this.f461c.findViewById(R.id.tvi_25f);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 8:
                    this.e.setText(context.getString(R.string.reverse_tvi_30f));
                    viewFindViewById2 = this.f461c.findViewById(R.id.tvi_30f);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 9:
                    this.e.setText(context.getString(R.string.reverse_tvi_1080p_25f));
                    viewFindViewById2 = this.f461c.findViewById(R.id.tvi_1080p_25f);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
                case 10:
                    this.e.setText(context.getString(R.string.reverse_tvi_1080p_30f));
                    viewFindViewById2 = this.f461c.findViewById(R.id.tvi_1080p_30f);
                    ((RadioButton) viewFindViewById2).setChecked(true);
                    break;
            }
            fVar = new e(context);
        } else {
            c.b.b.f.a.f478a = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_TYPE, 0);
            this.f461c.findViewById(R.id.ahd_auto).setVisibility(8);
            this.f461c.findViewById(R.id.ahd_auto_view).setVisibility(8);
            switch (c.b.b.f.a.f478a) {
                case 0:
                    this.e.setText(context.getString(R.string.reverse_ahd_ntsc));
                    view = this.f461c;
                    i = R.id.ahd_ntsc;
                    viewFindViewById = view.findViewById(i);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
                case 1:
                    this.e.setText(context.getString(R.string.reverse_ahd_pal));
                    view = this.f461c;
                    i = R.id.ahd_pal;
                    viewFindViewById = view.findViewById(i);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
                case 2:
                    this.e.setText(context.getString(R.string.reverse_ahd_25f));
                    view = this.f461c;
                    i = R.id.ahd_25f;
                    viewFindViewById = view.findViewById(i);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
                case 3:
                    this.e.setText(context.getString(R.string.reverse_ahd_30f));
                    viewFindViewById = this.f461c.findViewById(R.id.ahd_30f);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
                case 4:
                    this.e.setText(context.getString(R.string.reverse_tvi_25f));
                    viewFindViewById = this.f461c.findViewById(R.id.tvi_25f);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
                case 5:
                    this.e.setText(context.getString(R.string.reverse_tvi_30f));
                    viewFindViewById = this.f461c.findViewById(R.id.tvi_30f);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
                case 6:
                    this.e.setText(context.getString(R.string.reverse_ahd_1080p_25f));
                    viewFindViewById = this.f461c.findViewById(R.id.ahd_1080p_25f);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
                case 7:
                    this.e.setText(context.getString(R.string.reverse_ahd_1080p_30f));
                    viewFindViewById = this.f461c.findViewById(R.id.ahd_1080p_30f);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
                case 8:
                    this.e.setText(context.getString(R.string.reverse_tvi_1080p_25f));
                    viewFindViewById = this.f461c.findViewById(R.id.tvi_1080p_25f);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
                case 9:
                    this.e.setText(context.getString(R.string.reverse_tvi_1080p_30f));
                    viewFindViewById = this.f461c.findViewById(R.id.tvi_1080p_30f);
                    ((RadioButton) viewFindViewById).setChecked(true);
                    break;
            }
            fVar = new f(context);
        }
        this.f461c.findViewById(R.id.brightness_down).setOnClickListener(viewOnClickListenerC0019c);
        this.f461c.findViewById(R.id.brightness_up).setOnClickListener(viewOnClickListenerC0019c);
        this.f461c.findViewById(R.id.contrast_down).setOnClickListener(viewOnClickListenerC0019c);
        this.f461c.findViewById(R.id.contrast_up).setOnClickListener(viewOnClickListenerC0019c);
        this.f461c.findViewById(R.id.saturation_down).setOnClickListener(viewOnClickListenerC0019c);
        this.f461c.findViewById(R.id.saturation_up).setOnClickListener(viewOnClickListenerC0019c);
        this.f461c.findViewById(R.id.offset_down).setOnClickListener(viewOnClickListenerC0019c);
        this.f461c.findViewById(R.id.offset_up).setOnClickListener(viewOnClickListenerC0019c);
        this.f461c.findViewById(R.id.tv_camera_reset).setOnClickListener(dVar);
        this.f461c.findViewById(R.id.tv_layout_frame).setOnClickListener(dVar);
        this.f461c.findViewById(R.id.tv_layout_camera).setOnClickListener(dVar);
        this.f461c.findViewById(R.id.lev_arrow).setOnClickListener(dVar);
        ((RadioButton) this.f461c.findViewById(R.id.ahd_auto)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.ahd_pal)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.ahd_ntsc)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.ahd_pal)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.ahd_25f)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.ahd_30f)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.tvi_25f)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.tvi_30f)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.ahd_1080p_25f)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.ahd_1080p_30f)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.tvi_1080p_25f)).setOnCheckedChangeListener(fVar);
        ((RadioButton) this.f461c.findViewById(R.id.tvi_1080p_30f)).setOnCheckedChangeListener(fVar);
        if (Settings.System.getInt(context.getContentResolver(), "TWCameraOffsetType", 0) == 1) {
            this.f461c.findViewById(R.id.offset_title).setVisibility(0);
            this.f461c.findViewById(R.id.offset_layout).setVisibility(0);
        }
        this.f459a = (WindowManager) context.getSystemService("window");
        this.f460b = new WindowManager.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.camera_view_width), context.getResources().getDimensionPixelSize(R.dimen.camera_view_height), 0, 0, 2038, 40, 1);
    }

    public void a() {
        String string;
        if (this.d) {
            int progress = ((SeekBar) this.f461c.findViewById(R.id.brightness_level)).getProgress() - 50;
            int progress2 = ((SeekBar) this.f461c.findViewById(R.id.contrast_level)).getProgress() - 50;
            int progress3 = ((SeekBar) this.f461c.findViewById(R.id.saturation_level)).getProgress() - 50;
            int i = (c.b.b.f.a.f() || c.b.b.f.a.i()) ? 0 : Settings.System.getInt(this.f.getContentResolver(), "TWCameraSaveSize", 128);
            int i2 = progress + i;
            int i3 = progress2 + i;
            int i4 = progress3 + i;
            int i5 = Settings.System.getInt(this.f.getContentResolver(), "TWCameraSaveType", 0);
            if (i5 != 0) {
                if (i5 == 1) {
                    c.b.b.f.a.E(Settings.System.getString(this.f.getContentResolver(), "TWCameraBrightnessSave"), i2, this.f);
                    c.b.b.f.a.E(Settings.System.getString(this.f.getContentResolver(), "TWCameraContrastSave"), i3, this.f);
                    c.b.b.f.a.E(Settings.System.getString(this.f.getContentResolver(), "TWCameraSaturationSave"), i4, this.f);
                } else if (i5 == 2) {
                    c.b.b.f.a.D(Settings.System.getString(this.f.getContentResolver(), "TWCameraBrightnessSave"), i2);
                    c.b.b.f.a.D(Settings.System.getString(this.f.getContentResolver(), "TWCameraContrastSave"), i3);
                    string = Settings.System.getString(this.f.getContentResolver(), "TWCameraSaturationSave");
                    c.b.b.f.a.D(string, i4);
                }
            } else if (c.b.b.f.a.i() || c.b.b.f.a.f()) {
                c.b.b.f.a.E("brightness", i2, this.f);
                c.b.b.f.a.E("contrast", i3, this.f);
                c.b.b.f.a.E("saturation", i4, this.f);
            } else {
                if (c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                    c.b.b.f.a.D("persist.camera.brightness2.aux", i2);
                    c.b.b.f.a.D("persist.camera.contrast2.aux", i3);
                    string = "persist.camera.saturation2.aux";
                } else {
                    c.b.b.f.a.D(CamParamConstant.AUX.PERSIST_CAM_BRIGHTNESS, i2);
                    c.b.b.f.a.D(CamParamConstant.AUX.PERSIST_CAM_CONTRAST, i3);
                    string = CamParamConstant.AUX.PERSIST_CAM_SATURATION;
                }
                c.b.b.f.a.D(string, i4);
            }
            this.f459a.removeView(this.f461c);
            this.d = false;
        }
    }

    public final void b(int i) {
        float f2;
        float f3;
        try {
            int i2 = Settings.System.getInt(this.f.getContentResolver(), "TWCameraOffsetType", 0);
            if (i2 != 0) {
                if (i2 == 1) {
                    int i3 = Settings.System.getInt(this.f.getContentResolver(), "TWCameraOffsetSize", 138);
                    int i4 = Settings.System.getInt(this.f.getContentResolver(), "TWCameraOffsetSize", 138);
                    int i5 = Settings.System.getInt(this.f.getContentResolver(), "TWCameraOffsetMAX", 319);
                    int i6 = Settings.System.getInt(this.f.getContentResolver(), "TWCameraOffsetMIN", 64);
                    if (i > 0) {
                        double d2 = i5 - i4;
                        Double.isNaN(d2);
                        double d3 = i;
                        Double.isNaN(d3);
                        f2 = (float) (d2 * 0.02d * d3);
                        f3 = i4;
                    } else {
                        if (i < 0) {
                            double d4 = i4 - i6;
                            Double.isNaN(d4);
                            double d5 = 50 - (0 - i);
                            Double.isNaN(d5);
                            f2 = (float) (d4 * 0.02d * d5);
                            f3 = i6;
                        }
                        this.g.write(40730, 1, 0, Settings.System.getString(this.f.getContentResolver(), "TWCameraOffset") + i3);
                        SystemProperties.set(Settings.System.getString(this.f.getContentResolver(), "TWCameraOffsetSave"), String.format(Locale.US, "%d", Integer.valueOf(i3)));
                    }
                    i3 = (int) (f2 + f3);
                    this.g.write(40730, 1, 0, Settings.System.getString(this.f.getContentResolver(), "TWCameraOffset") + i3);
                    SystemProperties.set(Settings.System.getString(this.f.getContentResolver(), "TWCameraOffsetSave"), String.format(Locale.US, "%d", Integer.valueOf(i3)));
                }
            } else if (c.b.b.f.a.q()) {
                this.g.write(40730, 1, 0, "rwrite /sys/class/sensor_i2c/lrpos " + i + 128);
                SystemProperties.set("persist.camera.lrpos", String.format(Locale.US, "%d", Integer.valueOf(i + 128)));
            }
        } catch (Exception unused) {
        }
    }
}
