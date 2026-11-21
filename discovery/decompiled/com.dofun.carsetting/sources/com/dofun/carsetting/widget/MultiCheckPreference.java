package com.dofun.carsetting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.g.l;

/* JADX INFO: loaded from: classes.dex */
public class MultiCheckPreference extends TwPreference {
    private View e;
    private TextView f;
    private TextView g;
    private TextView h;
    private ImageView i;
    private ImageView j;
    private View k;
    private String l;
    private String m;
    private String n;
    private Drawable o;
    private boolean p;
    private boolean q;
    private boolean r;
    private int s;
    private CharSequence[] t;
    private com.dofun.carsetting.c.g u;
    private View v;
    private l w;
    private int x;

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void b() {
    }

    public MultiCheckPreference(Context context) {
        super(context);
        this.p = true;
        this.q = true;
        this.r = true;
        this.s = 0;
    }

    public MultiCheckPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.p = true;
        this.q = true;
        this.r = true;
        this.s = 0;
    }

    public MultiCheckPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.p = true;
        this.q = true;
        this.r = true;
        this.s = 0;
    }

    public MultiCheckPreference a(l lVar) {
        this.w = lVar;
        return this;
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a() {
        this.v = LayoutInflater.from(this.f479a).inflate(R.layout.preference_information, this);
        this.e = this.v.findViewById(R.id.item_container);
        this.f = (TextView) this.v.findViewById(R.id.tv_title);
        this.g = (TextView) this.v.findViewById(R.id.tv_summary);
        this.h = (TextView) this.v.findViewById(R.id.tv_value);
        this.i = (ImageView) this.v.findViewById(R.id.iv_value_icon);
        this.j = (ImageView) this.v.findViewById(R.id.iv_right_icon);
        this.k = this.v.findViewById(R.id.view_divider);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.MultiCheckPreference.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MultiCheckPreference.this.c) {
                    MultiCheckPreference.this.a(MultiCheckPreference.this.l, MultiCheckPreference.this.d, new TwPreference.a() { // from class: com.dofun.carsetting.widget.MultiCheckPreference.1.1
                        @Override // com.dofun.carsetting.widget.TwPreference.a
                        public void a(boolean z) {
                            if (z) {
                                if (MultiCheckPreference.this.u != null) {
                                    MultiCheckPreference.this.u.show();
                                } else if (MultiCheckPreference.this.w != null) {
                                    MultiCheckPreference.this.w.a(MultiCheckPreference.this, 0, null);
                                }
                            }
                        }
                    });
                } else if (MultiCheckPreference.this.u != null) {
                    MultiCheckPreference.this.u.show();
                } else if (MultiCheckPreference.this.w != null) {
                    MultiCheckPreference.this.w.a(MultiCheckPreference.this, 0, null);
                }
            }
        });
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = this.f479a.obtainStyledAttributes(attributeSet, R.styleable.InfoPreference);
        setKey(typedArrayObtainStyledAttributes.getString(5));
        this.l = typedArrayObtainStyledAttributes.getString(15);
        this.m = typedArrayObtainStyledAttributes.getString(13);
        this.n = typedArrayObtainStyledAttributes.getString(7);
        this.o = typedArrayObtainStyledAttributes.getDrawable(8);
        this.p = typedArrayObtainStyledAttributes.getBoolean(11, true);
        this.q = typedArrayObtainStyledAttributes.getBoolean(12, false);
        this.r = typedArrayObtainStyledAttributes.getBoolean(10, true);
        this.t = typedArrayObtainStyledAttributes.getTextArray(0);
        typedArrayObtainStyledAttributes.recycle();
        this.f.setText(this.l);
        this.g.setText(this.m);
        this.h.setText(this.n);
        if (this.o != null) {
            this.i.setVisibility(0);
            this.i.setImageDrawable(this.o);
        } else {
            this.i.setVisibility(8);
        }
        this.j.setVisibility(this.p ? 0 : 8);
        this.k.setVisibility(this.r ? 0 : 8);
        this.h.setVisibility(this.q ? 0 : 8);
        if (!TextUtils.isEmpty(this.m)) {
            this.g.setVisibility(0);
        } else {
            this.g.setVisibility(8);
        }
        setValue(this.t);
    }

    public void setValue(CharSequence[] charSequenceArr) {
        this.u = new com.dofun.carsetting.c.g(this.f479a);
        this.u.a(this.l);
        this.u.b(getKey());
        this.u.a(charSequenceArr);
        this.u.a(this.s);
        this.u.a(new l() { // from class: com.dofun.carsetting.widget.MultiCheckPreference.2
            @Override // com.dofun.carsetting.g.l
            public void a(MultiCheckPreference multiCheckPreference, int i, boolean[] zArr) {
                if (MultiCheckPreference.this.w != null) {
                    MultiCheckPreference.this.w.a(MultiCheckPreference.this, i, zArr);
                }
            }
        });
    }

    public void setValue(int i) {
        this.s = i;
        this.u.a(this.s);
    }

    public void setTvValue(boolean[] zArr) {
        this.x = zArr.length;
        int iPow = 0;
        for (int i = 1; i <= this.x; i++) {
            int i2 = i - 1;
            if (zArr[i2]) {
                iPow |= (int) Math.pow(2.0d, i2);
            }
        }
        this.u.a(iPow);
    }

    public com.dofun.carsetting.c.g getMultifunctionDialog() {
        return this.u;
    }
}
