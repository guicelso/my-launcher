package com.dofun.carsetting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.dofun.carsetting.R;

/* JADX INFO: loaded from: classes.dex */
public class SwitchPreferenceV2 extends TwPreference {
    private TextView e;
    private TextView f;
    private ToggleButton g;
    private View h;
    private String i;
    private String j;
    private boolean k;

    public SwitchPreferenceV2(Context context) {
        super(context);
        this.k = true;
        this.f479a = context;
    }

    public SwitchPreferenceV2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.k = true;
        this.f479a = context;
    }

    public SwitchPreferenceV2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.k = true;
        this.f479a = context;
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a() {
        RelativeLayout relativeLayout = new RelativeLayout(this.f479a);
        relativeLayout.setBackgroundResource(R.drawable.ripple_bg);
        relativeLayout.setMinimumHeight((int) getResources().getDimension(R.dimen.preference_min_height));
        relativeLayout.setClickable(true);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, (int) getResources().getDimension(R.dimen.preference_height));
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.tw_dp_w16), 0, (int) getResources().getDimension(R.dimen.tw_dp_w16), 0);
        addView(relativeLayout, layoutParams);
        RelativeLayout relativeLayout2 = new RelativeLayout(this.f479a);
        relativeLayout2.setPadding(0, (int) getResources().getDimension(R.dimen.tw_dp_h8), 0, (int) getResources().getDimension(R.dimen.tw_dp_h8));
        LinearLayout linearLayout = new LinearLayout(this.f479a);
        linearLayout.setGravity(16);
        linearLayout.setOrientation(1);
        this.e = new TextView(this.f479a);
        this.e.setId(View.generateViewId());
        this.e.setGravity(19);
        this.e.setMaxLines(1);
        this.e.setTextColor(getResources().getColor(android.R.color.white));
        this.e.setTextSize(0, getResources().getDimension(R.dimen.preference_title_text_size));
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams2.weight = 1.0f;
        linearLayout.addView(this.e, layoutParams2);
        this.f = new TextView(this.f479a);
        this.f.setId(View.generateViewId());
        this.f.setGravity(19);
        this.f.setMaxLines(1);
        this.f.setTextColor(this.f479a.getColor(R.color.pref_text_summary_color));
        this.f.setTextSize(0, getResources().getDimension(R.dimen.preference_summary_text_size));
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams3.weight = 1.0f;
        linearLayout.addView(this.f, layoutParams3);
        this.g = new ToggleButton(this.f479a);
        this.g.setId(View.generateViewId());
        this.g.setBackgroundResource(R.drawable.select_pref_switch);
        this.g.setButtonDrawable((Drawable) null);
        this.g.setTextOn("");
        this.g.setTextOff("");
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams4.width = (int) getResources().getDimension(R.dimen.preference_toggle_icon_width);
        layoutParams4.height = (int) getResources().getDimension(R.dimen.preference_toggle_icon_height);
        layoutParams4.addRule(11);
        layoutParams4.addRule(15);
        relativeLayout2.addView(this.g, layoutParams4);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams5.addRule(0, this.g.getId());
        layoutParams5.height = (int) getResources().getDimension(R.dimen.preference_height);
        relativeLayout2.addView(linearLayout, layoutParams5);
        relativeLayout.addView(relativeLayout2, new RelativeLayout.LayoutParams(-1, -1));
        this.h = new View(this.f479a);
        this.h.setBackgroundResource(R.drawable.tl_list_divider2);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams6.height = (int) getResources().getDimension(R.dimen.tw_dp_h2);
        layoutParams6.addRule(12);
        relativeLayout.addView(this.h, layoutParams6);
        this.g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.dofun.carsetting.widget.SwitchPreferenceV2.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (compoundButton.isPressed() || compoundButton.isFocused()) {
                    SwitchPreferenceV2.this.a(Boolean.valueOf(z));
                    if (SwitchPreferenceV2.this.b != null) {
                        SwitchPreferenceV2.this.b.a(SwitchPreferenceV2.this, Boolean.valueOf(z));
                    }
                }
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.SwitchPreferenceV2.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SwitchPreferenceV2.this.c) {
                    SwitchPreferenceV2.this.a(SwitchPreferenceV2.this.i, SwitchPreferenceV2.this.d, new TwPreference.a() { // from class: com.dofun.carsetting.widget.SwitchPreferenceV2.2.1
                        @Override // com.dofun.carsetting.widget.TwPreference.a
                        public void a(boolean z) {
                            if (!z || SwitchPreferenceV2.this.b == null) {
                                return;
                            }
                            SwitchPreferenceV2.this.g.setChecked(!SwitchPreferenceV2.this.g.isChecked());
                            SwitchPreferenceV2.this.b.a(SwitchPreferenceV2.this, Boolean.valueOf(SwitchPreferenceV2.this.g.isChecked()));
                        }
                    });
                } else if (SwitchPreferenceV2.this.b != null) {
                    SwitchPreferenceV2.this.g.setChecked(!SwitchPreferenceV2.this.g.isChecked());
                    SwitchPreferenceV2.this.b.a(SwitchPreferenceV2.this, Boolean.valueOf(SwitchPreferenceV2.this.g.isChecked()));
                }
            }
        });
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = this.f479a.obtainStyledAttributes(attributeSet, R.styleable.SwitchPreference);
        setKey(typedArrayObtainStyledAttributes.getString(0));
        this.i = typedArrayObtainStyledAttributes.getString(5);
        this.j = typedArrayObtainStyledAttributes.getString(3);
        this.k = typedArrayObtainStyledAttributes.getBoolean(1, true);
        typedArrayObtainStyledAttributes.recycle();
        this.e.setText(this.i);
        this.f.setText(this.j);
        this.h.setVisibility(this.k ? 0 : 8);
        if (!TextUtils.isEmpty(this.j)) {
            this.f.setVisibility(0);
        } else {
            this.f.setVisibility(8);
        }
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void b() {
        this.g.setChecked(((Boolean) b(Boolean.TRUE)).booleanValue());
    }

    public void setValue(boolean z) {
        a(Boolean.valueOf(z));
        Log.d("SwitchPreferenceV2", "setValue: " + z);
        this.g.setChecked(z);
    }

    public boolean getValue() {
        return this.g.isChecked();
    }

    public void setTitle(String str) {
        this.e.setText(str);
    }
}
