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
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BacklightPreference extends TwPreference {
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
    private int t;
    private int u;
    private com.dofun.carsetting.c.b v;
    private com.dofun.carsetting.g.b w;

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void b() {
    }

    public BacklightPreference(Context context) {
        super(context);
        this.p = true;
        this.q = true;
        this.r = true;
    }

    public BacklightPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.p = true;
        this.q = true;
        this.r = true;
    }

    public BacklightPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.p = true;
        this.q = true;
        this.r = true;
    }

    public BacklightPreference a(com.dofun.carsetting.g.b bVar) {
        this.w = bVar;
        return this;
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a() {
        View viewInflate = LayoutInflater.from(this.f479a).inflate(R.layout.preference_information, this);
        this.e = viewInflate.findViewById(R.id.item_container);
        this.f = (TextView) viewInflate.findViewById(R.id.tv_title);
        this.g = (TextView) viewInflate.findViewById(R.id.tv_summary);
        this.h = (TextView) viewInflate.findViewById(R.id.tv_value);
        this.i = (ImageView) viewInflate.findViewById(R.id.iv_value_icon);
        this.j = (ImageView) viewInflate.findViewById(R.id.iv_right_icon);
        this.k = viewInflate.findViewById(R.id.view_divider);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.BacklightPreference.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!BacklightPreference.this.c) {
                    BacklightPreference.this.d = "7890";
                }
                BacklightPreference.this.a(BacklightPreference.this.l, BacklightPreference.this.d, new TwPreference.a() { // from class: com.dofun.carsetting.widget.BacklightPreference.1.1
                    @Override // com.dofun.carsetting.widget.TwPreference.a
                    public void a(boolean z) {
                        if (!z || BacklightPreference.this.v == null) {
                            return;
                        }
                        BacklightPreference.this.v.a(false, 0, BacklightPreference.this.t, BacklightPreference.this.u);
                        BacklightPreference.this.v.show();
                    }
                });
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
        this.v = new com.dofun.carsetting.c.b(this.f479a);
        this.v.a(this.l);
        this.v.a(new com.dofun.carsetting.g.b() { // from class: com.dofun.carsetting.widget.BacklightPreference.2
            @Override // com.dofun.carsetting.g.b
            public void a(BacklightPreference backlightPreference, int i, int i2, boolean z) {
                if (BacklightPreference.this.w != null) {
                    BacklightPreference.this.w.a(BacklightPreference.this, i, i2, z);
                }
            }
        });
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
    }

    public void a(boolean z, int i, int i2, int i3) {
        this.t = i2;
        this.u = i3;
        if (this.v != null) {
            this.v.a(z, i, i2, i3);
        }
    }

    public com.dofun.carsetting.c.b getBacklightEleAjustDialog() {
        return this.v;
    }
}
