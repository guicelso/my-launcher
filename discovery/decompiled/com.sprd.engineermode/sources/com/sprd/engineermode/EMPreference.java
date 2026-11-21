package com.sprd.engineermode;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

/* JADX INFO: loaded from: classes4.dex */
public class EMPreference extends Preference implements CompoundButton.OnCheckedChangeListener {
    static final String TAG = "EMPreference";
    private int mId;
    private boolean mIsSelected;
    private boolean mProtectFromCheckedChange;
    private RadioButton mRadioButton;

    public EMPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIsSelected = false;
        this.mProtectFromCheckedChange = false;
        init();
    }

    public EMPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsSelected = false;
        this.mProtectFromCheckedChange = false;
        init();
    }

    public EMPreference(Context context) {
        super(context);
        this.mIsSelected = false;
        this.mProtectFromCheckedChange = false;
        init();
    }

    @Override // android.preference.Preference
    public View getView(View convertView, ViewGroup parent) {
        View view = super.getView(convertView, parent);
        View widget = view.findViewById(R.id.audio_profile_radiobutton);
        if (widget != null && (widget instanceof RadioButton)) {
            this.mRadioButton = (RadioButton) widget;
            this.mRadioButton.setOnCheckedChangeListener(this);
            this.mProtectFromCheckedChange = true;
            this.mRadioButton.setChecked(this.mIsSelected);
            this.mProtectFromCheckedChange = false;
        }
        View textLayout = view.findViewById(R.id.text_layout);
        if (textLayout != null && (textLayout instanceof TextView)) {
            ((TextView) textLayout).setText(getTitle());
        }
        View textSummary = view.findViewById(R.id.text_summary);
        if (textSummary != null && (textSummary instanceof TextView) && getSummary() != null) {
            ((TextView) textSummary).setText(getSummary());
            ((TextView) textSummary).setVisibility(0);
        }
        view.setTag(Integer.valueOf(this.mId));
        return view;
    }

    private void init() {
        setLayoutResource(R.layout.audio_profile_preference_layout);
    }

    public void setChecked(boolean checked) {
        if (checked != this.mIsSelected) {
            this.mIsSelected = checked;
        }
        RadioButton radioButton = this.mRadioButton;
        if (radioButton != null) {
            radioButton.setChecked(this.mIsSelected);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.i(TAG, "ID: " + this.mId + " :" + isChecked);
        if (this.mProtectFromCheckedChange) {
            return;
        }
        if (isChecked) {
            this.mIsSelected = true;
            callChangeListener(Integer.valueOf(this.mId));
        } else {
            this.mIsSelected = false;
        }
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return this.mId;
    }
}
