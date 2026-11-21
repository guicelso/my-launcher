package com.dofun.carsetting.fragment;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.dofun.carsetting.R;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;

/* JADX INFO: compiled from: SoundFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class z extends ad<com.dofun.carsetting.presenter.d> implements View.OnClickListener, com.dofun.carsetting.g.d, com.dofun.carsetting.g.j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.dofun.carsetting.f.u f384a;
    private InfoPreference b;
    private InfoPreference c;
    private InfoPreference d;
    private InfoPreference e;
    private InfoPreference f;
    private String k;
    private SeekBar l;
    private SeekBar m;
    private LinearLayout n;
    private SwitchPreference o;
    private int g = 1;
    private String p = "";
    private Handler q = new Handler(Looper.myLooper()) { // from class: com.dofun.carsetting.fragment.z.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                int i = message.what;
                boolean z = true;
                if (i == 260) {
                    byte[] bArr = (byte[]) message.obj;
                    if (bArr[0] >= 0 && bArr[0] <= 1) {
                        z.this.e.setValue(String.valueOf((int) bArr[0]));
                    }
                    Settings.System.putInt(z.this.getActivity().getContentResolver(), "sound_effects_enabled", bArr[0] == 0 ? 0 : 1);
                    if (Integer.valueOf(bArr[1]).intValue() == 2) {
                        z.this.l.setEnabled(true);
                        z.this.m.setEnabled(true);
                        if (!z.this.j.Q || !z.this.j.R) {
                            z.this.n.setVisibility(0);
                        } else {
                            z.this.n.setVisibility(8);
                        }
                    } else {
                        z.this.l.setEnabled(false);
                        z.this.m.setEnabled(false);
                        z.this.n.setVisibility(8);
                    }
                    z.this.c.setValue(String.valueOf((int) bArr[1]));
                    z.this.b.setValue(String.valueOf((int) bArr[3]));
                    z.this.f.setValue(String.valueOf((int) bArr[4]));
                    return;
                }
                if (i != 262) {
                    if (i == 266 && message.arg1 == 0) {
                        z.this.p = ((String) message.obj).split("-")[3].substring(3, 4);
                        if (!z.this.p.equals("8") && !z.this.p.equals("6") && !z.this.p.equals("9")) {
                            z = false;
                        }
                        if ((com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.a().contains("TW2-HV") || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) && z) {
                            z.this.e.setVisibility(0);
                            return;
                        }
                        return;
                    }
                    return;
                }
                byte[] bArr2 = (byte[]) message.obj;
                z.this.l.setProgress(bArr2[0]);
                z.this.m.setProgress(bArr2[1]);
            } catch (Exception unused) {
            }
        }
    };
    private SeekBar.OnSeekBarChangeListener r = new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.fragment.z.2
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                int id = seekBar.getId();
                if (id == R.id.background_sound_seek_bar) {
                    z.this.f384a.write(262, 1, i);
                } else {
                    if (id != R.id.guide_sound_seek_bar) {
                        return;
                    }
                    z.this.f384a.write(262, 0, i);
                }
            }
        }
    };

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.f384a = com.dofun.carsetting.f.u.a();
        return layoutInflater.inflate(R.layout.fragment_sound, (ViewGroup) null);
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.d d() {
        Log.d("SoundFragment", "createPresenter: DevicesPresenter");
        return new com.dofun.carsetting.presenter.d(getActivity());
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.b = (InfoPreference) view.findViewById(R.id.ip_sound_priority);
        this.b.setOnPreferenceChangeListener(this);
        this.c = (InfoPreference) view.findViewById(R.id.ip_navi_channel);
        this.c.setOnPreferenceChangeListener(this);
        this.d = (InfoPreference) view.findViewById(R.id.ringtone_title);
        this.d.setOnPreferenceChangeListener(this);
        this.f = (InfoPreference) view.findViewById(R.id.speed_compensation);
        this.f.setOnPreferenceChangeListener(this);
        this.l = (SeekBar) view.findViewById(R.id.guide_sound_seek_bar);
        this.l.setOnSeekBarChangeListener(this.r);
        this.m = (SeekBar) view.findViewById(R.id.background_sound_seek_bar);
        this.m.setOnSeekBarChangeListener(this.r);
        this.e = (InfoPreference) view.findViewById(R.id.ip_key_tone);
        this.e.setOnPreferenceChangeListener(this);
        this.o = (SwitchPreference) view.findViewById(R.id.hdmi_raw);
        this.o.setOnPreferenceChangeListener(this);
        this.o.setValue(SystemProperties.get("mediasw.sft.rawdata", "").equals("HDMI_RAW") && SystemProperties.get("vendor.mediasw.sft.rawdata", "").equals("HDMI_RAW"));
        this.o.setVisibility((!com.dofun.carsetting.f.b.T() || com.dofun.carsetting.f.b.aE() <= 0) ? 8 : 0);
        this.n = (LinearLayout) view.findViewById(R.id.ll_sound_mix);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        int i = SystemProperties.getInt("persist.tw.dv.music", -1);
        int i2 = SystemProperties.getInt("persist.tw.dv.music.navi", -1);
        if (com.dofun.carsetting.f.b.H()) {
            if (i == -1) {
                i = 12;
            }
            if (i2 == -1) {
                i2 = 12;
            }
        } else {
            if (i == -1) {
                i = 15;
            }
            if (i2 == -1) {
                i2 = 14;
            }
        }
        this.l.setMax(i);
        this.m.setMax(i2);
        this.k = Settings.System.getString(this.i.getContentResolver(), "RINGTONRPATH");
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.f384a != null) {
            this.f384a.addHandler("SoundFragment", this.q);
            this.f384a.write(258, 255);
            this.f384a.write(260, 255);
            this.f384a.write(262, 255);
            this.f384a.write(516, 255);
            this.f384a.write(266, 255);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.f384a != null) {
            this.f384a.removeHandler("SoundFragment");
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.f384a != null) {
            this.f384a.close();
            this.f384a.removeHandler("SoundFragment");
        }
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
            if (uri != null) {
                this.k = uri.toString();
                RingtoneManager.setActualDefaultRingtoneUri(getContext(), 1, Uri.parse(this.k));
                RingtoneManager.setActualDefaultRingtoneUri(getContext(), 8, Uri.parse(this.k));
            } else {
                this.k = "";
                RingtoneManager.setActualDefaultRingtoneUri(getContext(), 1, null);
                RingtoneManager.setActualDefaultRingtoneUri(getContext(), 8, null);
            }
        } catch (Exception e) {
            Log.d("SoundFragment", "onActivityResult:" + e.getMessage());
        }
        super.onActivityResult(i, i2, intent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        switch (twPreference.getId()) {
            case R.id.hdmi_raw /* 2131230894 */:
                Boolean bool = (Boolean) obj;
                SystemProperties.set("mediasw.sft.rawdata", bool.booleanValue() ? "HDMI_RAW" : "");
                SystemProperties.set("vendor.mediasw.sft.rawdata", bool.booleanValue() ? "HDMI_RAW" : "");
                return false;
            case R.id.ip_key_tone /* 2131230925 */:
                try {
                    this.f384a.write(260, 0, Integer.valueOf((String) obj).intValue());
                    break;
                } catch (Exception e) {
                    Log.e("SoundFragment", "onPreferenceChange: " + e.getMessage());
                }
                return false;
            case R.id.ip_navi_channel /* 2131230933 */:
                try {
                    this.f384a.write(260, 1, Integer.valueOf((String) obj).intValue());
                    break;
                } catch (Exception e2) {
                    Log.e("SoundFragment", "onPreferenceChange: " + e2.getMessage());
                }
                return false;
            case R.id.ip_sound_priority /* 2131230938 */:
                try {
                    this.f384a.write(260, 3, Integer.valueOf((String) obj).intValue());
                    break;
                } catch (Exception e3) {
                    Log.e("SoundFragment", "onPreferenceChange: " + e3.getMessage());
                }
                return false;
            case R.id.ringtone_title /* 2131231161 */:
                Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", false);
                if (this.k != null) {
                    intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", Uri.parse(this.k));
                    Settings.System.putString(this.i.getContentResolver(), "RINGTONRPATH", this.k);
                }
                startActivityForResult(intent, this.g);
                return false;
            case R.id.speed_compensation /* 2131231220 */:
                try {
                    this.f384a.write(260, 4, Integer.valueOf((String) obj).intValue());
                    break;
                } catch (Exception e4) {
                    Log.e("SoundFragment", "onPreferenceChange: " + e4.getMessage());
                }
                return false;
            default:
                return false;
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        view.getId();
    }
}
