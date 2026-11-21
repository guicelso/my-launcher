package com.sprd.engineermode.telephony;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.exception.EmException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes4.dex */
public class UeCapWcdmaActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String KEY_16QAM = "ul_16qam";
    private static final String KEY_DBHSDPA = "dbhsdpa";
    private static final String KEY_SNOW3G = "snow3g";
    private static final String KEY_UL_HSDPA = "hsdpa";
    private static final String KEY_W_DIVERSITY = "w_diversity";
    private static final String TAG = "UeCapWcdmaActivity";
    private Disposable disposable;
    private SwitchPreference mPre16qam;
    private SwitchPreference mPreDbhsdpa;
    private SwitchPreference mPreSnow3g;
    private SwitchPreference mPreUlhsdpa;
    private SwitchPreference mPreWdiversity;
    private ITelephonyApi.INetInfoWcdmaUeCap ueCapApi = CoreApi.getTelephonyApi().wcdmaUeCapApi();
    private int simIdx = UeNwCapActivity.mSimIndex;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_uecap_wcdma);
        this.mPre16qam = (SwitchPreference) findPreference(KEY_16QAM);
        this.mPre16qam.setOnPreferenceChangeListener(this);
        this.mPreDbhsdpa = (SwitchPreference) findPreference(KEY_DBHSDPA);
        this.mPreDbhsdpa.setOnPreferenceChangeListener(this);
        this.mPreSnow3g = (SwitchPreference) findPreference(KEY_SNOW3G);
        this.mPreSnow3g.setOnPreferenceChangeListener(this);
        this.mPreWdiversity = (SwitchPreference) findPreference(KEY_W_DIVERSITY);
        this.mPreWdiversity.setOnPreferenceChangeListener(this);
        this.mPreUlhsdpa = (SwitchPreference) findPreference(KEY_UL_HSDPA);
        this.mPreUlhsdpa.setOnPreferenceChangeListener(this);
    }

    @Override // android.app.Activity
    protected void onStart() {
        ITelephonyApi.INetInfoWcdmaUeCap.UeCap ueCap;
        Log.d(TAG, "onstart");
        super.onStart();
        try {
            ueCap = this.ueCapApi.getUeCap(this.simIdx);
        } catch (EmException e) {
            e.printStackTrace();
            ueCap = new ITelephonyApi.INetInfoWcdmaUeCap.UeCap();
        }
        this.mPre16qam.setChecked(ueCap.ul16Qam);
        this.mPreDbhsdpa.setChecked(ueCap.dbHsdpa);
        this.mPreSnow3g.setChecked(ueCap.snow3g);
        this.mPreWdiversity.setChecked(ueCap.wDiversity);
        this.mPreUlhsdpa.setChecked(ueCap.hsdpa);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        Log.d(TAG, "key = " + key);
        if (KEY_16QAM.equals(key)) {
            if (this.mPre16qam.isChecked()) {
                set(this.mPre16qam, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$wcAAwv6UAA55d58LexaXfKBoVDk
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return this.f$0.lambda$onPreferenceChange$0$UeCapWcdmaActivity();
                    }
                });
                return false;
            }
            set(this.mPre16qam, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$-8DWe2zivGiHXkGefLldBTvKQcg
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.lambda$onPreferenceChange$1$UeCapWcdmaActivity();
                }
            });
            return false;
        }
        if (KEY_DBHSDPA.equals(key)) {
            if (this.mPreDbhsdpa.isChecked()) {
                set(this.mPreDbhsdpa, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$vjypbOBYpBlS0rZozoCKlm2qDCw
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return this.f$0.lambda$onPreferenceChange$2$UeCapWcdmaActivity();
                    }
                });
                return false;
            }
            set(this.mPreDbhsdpa, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$9r33mqU17i11heCUeJW1-owK1SM
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.lambda$onPreferenceChange$3$UeCapWcdmaActivity();
                }
            });
            return false;
        }
        if (KEY_SNOW3G.equals(key)) {
            if (!this.mPreSnow3g.isChecked()) {
                set(this.mPreSnow3g, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$59GZUC7QbJb-NfYSophS-N7JRrw
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return this.f$0.lambda$onPreferenceChange$4$UeCapWcdmaActivity();
                    }
                });
                return false;
            }
            set(this.mPreSnow3g, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$BGWjyLithW1WXa9KbXHwsAYYzG8
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.lambda$onPreferenceChange$5$UeCapWcdmaActivity();
                }
            });
            return false;
        }
        if (KEY_W_DIVERSITY.equals(key)) {
            if (!this.mPreWdiversity.isChecked()) {
                set(this.mPreWdiversity, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$PmiVDEkWupnbND-eH6e6NWUci5g
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return this.f$0.lambda$onPreferenceChange$6$UeCapWcdmaActivity();
                    }
                });
                return false;
            }
            set(this.mPreWdiversity, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$WOBr4U_eT7pjsPH_R-2iUXuU0r8
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.lambda$onPreferenceChange$7$UeCapWcdmaActivity();
                }
            });
            return false;
        }
        if (KEY_UL_HSDPA.equals(key)) {
            if (!this.mPreUlhsdpa.isChecked()) {
                set(this.mPreUlhsdpa, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$96v3qVWydwZrmaYYVkzZ_JcJp84
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return this.f$0.lambda$onPreferenceChange$8$UeCapWcdmaActivity();
                    }
                });
                return false;
            }
            set(this.mPreUlhsdpa, new Callable() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$KpzEBwTtIQEtygfmPZFRwQNwROU
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.lambda$onPreferenceChange$9$UeCapWcdmaActivity();
                }
            });
            return false;
        }
        return false;
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$0$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.closeUl16Qam(this.simIdx));
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$1$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.openUl16Qam(this.simIdx));
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$2$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.closeDbHsdpa(this.simIdx));
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$3$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.openDbHsdpa(this.simIdx));
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$4$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.openSnow3G(this.simIdx));
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$5$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.closeSnow3G(this.simIdx));
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$6$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.openWDiversity(this.simIdx));
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$7$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.closeWDiversity(this.simIdx));
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$8$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.openUlHsdpa(this.simIdx));
    }

    public /* synthetic */ Boolean lambda$onPreferenceChange$9$UeCapWcdmaActivity() throws Exception {
        return Boolean.valueOf(this.ueCapApi.closeUlHsdpa(this.simIdx));
    }

    private void set(final SwitchPreference pref, Callable<Boolean> callable) {
        this.disposable = Single.fromCallable(callable).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.sprd.engineermode.telephony.-$$Lambda$UeCapWcdmaActivity$77kGx_xqt2j6pLsmpb-Uz7ANCbU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$set$10$UeCapWcdmaActivity(pref, (Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$set$10$UeCapWcdmaActivity(SwitchPreference pref, Boolean result) throws Exception {
        if (result.booleanValue()) {
            pref.setChecked(!pref.isChecked());
            Toast.makeText(this, "Success", 0).show();
        } else {
            Toast.makeText(this, "Change status is not supported", 0).show();
        }
    }
}
