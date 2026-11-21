package com.dofun.carsetting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.dofun.carsetting.R;

/* JADX INFO: loaded from: classes.dex */
public class SwitchPreference extends TwPreference {
    private View e;
    private TextView f;
    private TextView g;
    private ToggleButton h;
    private View i;
    private String j;
    private String k;
    private int l;
    private boolean m;
    private View n;

    public SwitchPreference(Context context) {
        super(context);
        this.m = true;
        this.f479a = context;
    }

    public SwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.m = true;
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.m = true;
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a() {
        this.n = LayoutInflater.from(this.f479a).inflate(R.layout.preference_switch, this);
        this.e = this.n.findViewById(R.id.item_container);
        this.f = (TextView) this.n.findViewById(R.id.tv_title);
        this.g = (TextView) this.n.findViewById(R.id.tv_summary);
        this.h = (ToggleButton) this.n.findViewById(R.id.toggle_btn);
        this.h.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.dofun.carsetting.widget.SwitchPreference.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (compoundButton.isPressed() || compoundButton.isFocused()) {
                    SwitchPreference.this.a(Boolean.valueOf(z));
                    if (SwitchPreference.this.b != null) {
                        SwitchPreference.this.b.a(SwitchPreference.this, Boolean.valueOf(z));
                    }
                }
            }
        });
        this.i = this.n.findViewById(R.id.view_divider);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.SwitchPreference.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SwitchPreference.this.c) {
                    SwitchPreference.this.a(SwitchPreference.this.j, SwitchPreference.this.d, new TwPreference.a() { // from class: com.dofun.carsetting.widget.SwitchPreference.2.1
                        @Override // com.dofun.carsetting.widget.TwPreference.a
                        public void a(boolean z) {
                            if (!z || SwitchPreference.this.b == null) {
                                return;
                            }
                            SwitchPreference.this.h.setChecked(!SwitchPreference.this.h.isChecked());
                            SwitchPreference.this.b.a(SwitchPreference.this, Boolean.valueOf(SwitchPreference.this.h.isChecked()));
                        }
                    });
                } else if (SwitchPreference.this.b != null) {
                    SwitchPreference.this.h.setChecked(!SwitchPreference.this.h.isChecked());
                    SwitchPreference.this.b.a(SwitchPreference.this, Boolean.valueOf(SwitchPreference.this.h.isChecked()));
                }
            }
        });
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = this.f479a.obtainStyledAttributes(attributeSet, R.styleable.SwitchPreference);
        setKey(typedArrayObtainStyledAttributes.getString(0));
        this.j = typedArrayObtainStyledAttributes.getString(5);
        this.k = typedArrayObtainStyledAttributes.getString(3);
        this.l = typedArrayObtainStyledAttributes.getColor(4, this.f479a.getColor(R.color.pref_text_summary_color));
        this.m = typedArrayObtainStyledAttributes.getBoolean(1, true);
        typedArrayObtainStyledAttributes.recycle();
        this.f.setText(this.j);
        this.g.setText(this.k);
        if (this.l != 0) {
            this.g.setTextColor(this.l);
        } else {
            this.g.setTextColor(this.f479a.getColor(R.color.pref_text_summary_color));
        }
        this.i.setVisibility(this.m ? 0 : 8);
        if (!TextUtils.isEmpty(this.k)) {
            this.g.setVisibility(0);
        } else {
            this.g.setVisibility(8);
        }
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void b() {
        this.h.setChecked(((Boolean) b(Boolean.TRUE)).booleanValue());
    }

    public void setValue(boolean z) {
        a(Boolean.valueOf(z));
        Log.d("SwitchPreference", "setValue: " + z);
        this.h.setChecked(z);
    }

    public boolean getValue() {
        return this.h.isChecked();
    }

    public void c() {
        this.n.setVisibility(8);
    }

    public void setTitle(String str) {
        this.f.setText(str);
    }
}
