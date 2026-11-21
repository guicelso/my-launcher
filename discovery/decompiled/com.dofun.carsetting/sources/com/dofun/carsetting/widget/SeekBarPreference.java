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
import com.dofun.carsetting.bean.SeekBarBean;
import com.dofun.carsetting.g.q;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SeekBarPreference extends TwPreference {
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
    private List<SeekBarBean> s;
    private com.dofun.carsetting.c.i t;
    private View u;
    private q v;

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void b() {
    }

    public SeekBarPreference(Context context) {
        super(context);
        this.p = true;
        this.q = true;
        this.r = true;
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.p = true;
        this.q = true;
        this.r = true;
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.p = true;
        this.q = true;
        this.r = true;
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a() {
        this.u = LayoutInflater.from(this.f479a).inflate(R.layout.preference_information, this);
        this.e = this.u.findViewById(R.id.item_container);
        this.f = (TextView) this.u.findViewById(R.id.tv_title);
        this.g = (TextView) this.u.findViewById(R.id.tv_summary);
        this.h = (TextView) this.u.findViewById(R.id.tv_value);
        this.i = (ImageView) this.u.findViewById(R.id.iv_value_icon);
        this.j = (ImageView) this.u.findViewById(R.id.iv_right_icon);
        this.k = this.u.findViewById(R.id.view_divider);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.SeekBarPreference.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SeekBarPreference.this.c) {
                    SeekBarPreference.this.a(SeekBarPreference.this.l, SeekBarPreference.this.d, new TwPreference.a() { // from class: com.dofun.carsetting.widget.SeekBarPreference.1.1
                        @Override // com.dofun.carsetting.widget.TwPreference.a
                        public void a(boolean z) {
                            if (z) {
                                if (SeekBarPreference.this.s != null) {
                                    SeekBarPreference.this.t.show();
                                } else if (SeekBarPreference.this.v != null) {
                                    SeekBarPreference.this.v.a(SeekBarPreference.this, 0, 0);
                                }
                            }
                        }
                    });
                } else if (SeekBarPreference.this.s != null) {
                    SeekBarPreference.this.t.show();
                } else if (SeekBarPreference.this.v != null) {
                    SeekBarPreference.this.v.a(SeekBarPreference.this, 0, 0);
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
    }

    public void setValue(List<SeekBarBean> list) {
        this.s = list;
        this.t = new com.dofun.carsetting.c.i(this.f479a);
        this.t.a(this.l);
        this.t.a(this.s);
        this.t.a(new q() { // from class: com.dofun.carsetting.widget.SeekBarPreference.2
            @Override // com.dofun.carsetting.g.q
            public void a(SeekBarPreference seekBarPreference, int i, int i2) {
                if (SeekBarPreference.this.v != null) {
                    SeekBarPreference.this.v.a(SeekBarPreference.this, i, i2);
                }
            }
        });
    }

    public com.dofun.carsetting.c.i getSeekBarDialog() {
        return this.t;
    }
}
