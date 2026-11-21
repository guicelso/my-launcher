package com.dofun.carsetting.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.TwPreference;

/* JADX INFO: compiled from: ChannelBalanceFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class e extends ad<com.dofun.carsetting.presenter.f> implements View.OnClickListener, com.dofun.carsetting.g.j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.dofun.carsetting.f.u f263a;
    private SeekBar b;
    private SeekBar c;
    private SeekBar d;
    private SeekBar e;
    private SeekBar f;
    private SeekBar g;
    private TextView q;
    private TextView r;
    private TextView s;
    private TextView t;
    private TextView u;
    private TextView v;
    private LinearLayout w;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private int n = 0;
    private int o = 0;
    private int p = 0;
    private Handler x = new Handler() { // from class: com.dofun.carsetting.fragment.e.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                if (message.what != 260) {
                    return;
                }
                byte[] bArr = (byte[]) message.obj;
                if (bArr.length <= 9) {
                    e.this.k = 7;
                    e.this.b.setEnabled(false);
                    e.this.l = 7;
                    e.this.c.setEnabled(false);
                } else {
                    e.this.k = bArr[9] & 15;
                    e.this.b.setEnabled(true);
                    e.this.l = (bArr[9] & 240) >> 4;
                    e.this.c.setEnabled(true);
                }
                e.this.b.setProgress(e.this.k);
                e.this.q.setText(String.valueOf(e.this.k - 7));
                e.this.c.setProgress(e.this.l);
                e.this.r.setText(String.valueOf(e.this.l - 7));
                if (bArr.length <= 10) {
                    e.this.m = 7;
                    e.this.d.setEnabled(false);
                    e.this.n = 7;
                    e.this.e.setEnabled(false);
                } else {
                    e.this.m = (bArr[10] & 240) >> 4;
                    e.this.d.setEnabled(true);
                    e.this.n = bArr[10] & 15;
                    e.this.e.setEnabled(true);
                }
                e.this.d.setProgress(e.this.m);
                e.this.s.setText(String.valueOf(e.this.m - 7));
                e.this.e.setProgress(e.this.n);
                e.this.t.setText(String.valueOf(e.this.n - 7));
                if (bArr.length <= 11) {
                    e.this.o = 7;
                    e.this.f.setEnabled(false);
                    e.this.p = 7;
                    e.this.g.setEnabled(false);
                } else {
                    e.this.o = (bArr[11] & 240) >> 4;
                    e.this.f.setEnabled(true);
                    e.this.p = bArr[11] & 15;
                    e.this.g.setEnabled(true);
                }
                e.this.f.setProgress(e.this.o);
                e.this.u.setText(String.valueOf(e.this.o - 7));
                e.this.g.setProgress(e.this.p);
                e.this.v.setText(String.valueOf(e.this.p - 7));
                Log.d("ChannelBalanceFragment", "0x0104: " + com.dofun.carsetting.f.u.a((byte[]) message.obj));
            } catch (Exception unused) {
            }
        }
    };
    private SeekBar.OnSeekBarChangeListener y = new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.fragment.e.2
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                switch (seekBar.getId()) {
                    case R.id.aux_volume_seek_bar /* 2131230763 */:
                        e.this.f263a.write(260, 10, (e.this.m << 4) + i);
                        break;
                    case R.id.bt_call_volume_seek_bar /* 2131230774 */:
                        e.this.f263a.write(260, 9, (i << 4) + e.this.k);
                        break;
                    case R.id.bt_music_volume_seek_bar /* 2131230776 */:
                        e.this.f263a.write(260, 9, (e.this.l << 4) + i);
                        break;
                    case R.id.radio_volume_seek_bar /* 2131231111 */:
                        e.this.f263a.write(260, 10, (i << 4) + e.this.n);
                        break;
                    case R.id.system_audio_and_video_volume_seek_bar /* 2131231233 */:
                        e.this.f263a.write(260, 11, (e.this.o << 4) + i);
                        break;
                    case R.id.tv_volume_seek_bar /* 2131231337 */:
                        e.this.f263a.write(260, 11, (i << 4) + e.this.p);
                        break;
                }
            }
        }
    };

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.f d() {
        return null;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_channelbalance, (ViewGroup) null);
        this.f263a = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.b = (SeekBar) view.findViewById(R.id.bt_music_volume_seek_bar);
        this.q = (TextView) view.findViewById(R.id.bt_music_volume_value);
        this.b.setOnSeekBarChangeListener(this.y);
        this.c = (SeekBar) view.findViewById(R.id.bt_call_volume_seek_bar);
        this.r = (TextView) view.findViewById(R.id.bt_call_volume_value);
        this.c.setOnSeekBarChangeListener(this.y);
        this.d = (SeekBar) view.findViewById(R.id.radio_volume_seek_bar);
        this.s = (TextView) view.findViewById(R.id.radio_volume_value);
        this.d.setOnSeekBarChangeListener(this.y);
        this.e = (SeekBar) view.findViewById(R.id.aux_volume_seek_bar);
        this.t = (TextView) view.findViewById(R.id.aux_volume_value);
        this.e.setOnSeekBarChangeListener(this.y);
        this.f = (SeekBar) view.findViewById(R.id.tv_volume_seek_bar);
        this.u = (TextView) view.findViewById(R.id.tv_volume_value);
        this.f.setOnSeekBarChangeListener(this.y);
        this.g = (SeekBar) view.findViewById(R.id.system_audio_and_video_volume_seek_bar);
        this.v = (TextView) view.findViewById(R.id.system_audio_and_video_volume_value);
        this.g.setOnSeekBarChangeListener(this.y);
        this.w = (LinearLayout) view.findViewById(R.id.iv_back);
        this.w.setOnClickListener(this);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        this.b.setMax(14);
        this.c.setMax(14);
        this.d.setMax(14);
        this.e.setMax(14);
        this.f.setMax(14);
        this.g.setMax(14);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        if (this.f263a != null) {
            this.f263a.addHandler("ChannelBalanceFragment", this.x);
            this.f263a.write(260, 255);
        }
        super.onResume();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.f263a != null) {
            this.f263a.close();
            this.f263a.removeHandler("ChannelBalanceFragment");
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        this.f263a.removeHandler("ChannelBalanceFragment");
        super.onDestroy();
    }

    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        twPreference.getId();
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            ((MainActivity) getActivity()).c();
        }
    }
}
