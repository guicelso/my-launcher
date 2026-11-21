package com.dofun.carsetting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.dofun.carsetting.R;

/* JADX INFO: loaded from: classes.dex */
public class EditTextPreference extends TwPreference {
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
    private String o;
    private Drawable p;
    private boolean q;
    private boolean r;
    private boolean s;
    private boolean t;
    private int u;
    private CharSequence[] v;
    private CharSequence[] w;
    private com.dofun.carsetting.c.d x;
    private View y;
    private com.dofun.carsetting.g.g z;

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void b() {
    }

    public EditTextPreference(Context context) {
        super(context);
        this.q = true;
        this.r = true;
        this.s = true;
        this.t = false;
        this.u = 0;
    }

    public EditTextPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.q = true;
        this.r = true;
        this.s = true;
        this.t = false;
        this.u = 0;
    }

    public EditTextPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.q = true;
        this.r = true;
        this.s = true;
        this.t = false;
        this.u = 0;
    }

    public EditTextPreference a(com.dofun.carsetting.g.g gVar) {
        this.z = gVar;
        return this;
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a() {
        this.y = LayoutInflater.from(this.f479a).inflate(R.layout.preference_information, this);
        this.e = this.y.findViewById(R.id.item_container);
        this.f = (TextView) this.y.findViewById(R.id.tv_title);
        this.g = (TextView) this.y.findViewById(R.id.tv_summary);
        this.h = (TextView) this.y.findViewById(R.id.tv_value);
        this.i = (ImageView) this.y.findViewById(R.id.iv_value_icon);
        this.j = (ImageView) this.y.findViewById(R.id.iv_right_icon);
        this.k = this.y.findViewById(R.id.view_divider);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.EditTextPreference.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (EditTextPreference.this.x != null) {
                    EditTextPreference.this.x.show();
                } else if (EditTextPreference.this.z != null) {
                    EditTextPreference.this.z.a(EditTextPreference.this, null);
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
        this.o = typedArrayObtainStyledAttributes.getString(2);
        this.p = typedArrayObtainStyledAttributes.getDrawable(8);
        this.q = typedArrayObtainStyledAttributes.getBoolean(11, true);
        this.r = typedArrayObtainStyledAttributes.getBoolean(12, false);
        this.s = typedArrayObtainStyledAttributes.getBoolean(10, true);
        this.t = typedArrayObtainStyledAttributes.getBoolean(3, false);
        this.u = typedArrayObtainStyledAttributes.getInteger(4, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.f.setText(this.l);
        this.g.setText(this.m);
        this.h.setText(this.n);
        if (this.p != null) {
            this.i.setVisibility(0);
            this.i.setImageDrawable(this.p);
        } else {
            this.i.setVisibility(8);
        }
        this.j.setVisibility(this.q ? 0 : 8);
        this.k.setVisibility(this.s ? 0 : 8);
        this.h.setVisibility(this.r ? 0 : 8);
        if (!TextUtils.isEmpty(this.m)) {
            this.g.setVisibility(0);
        } else {
            this.g.setVisibility(8);
        }
        a(this.o, this.u, this.t);
    }

    public int a(String str) {
        if (str == null || this.w == null) {
            return -1;
        }
        for (int length = this.w.length - 1; length >= 0; length--) {
            if (this.w[length].equals(str)) {
                return length;
            }
        }
        return -1;
    }

    private String a(int i) {
        return (i < 0 || i > this.v.length) ? "" : this.v[i].toString();
    }

    public void setValue(String str) {
        int iA = a(str);
        if (iA == -1) {
            return;
        }
        a(Integer.valueOf(iA));
        this.h.setVisibility(0);
        this.h.setText(a(iA));
    }

    public void a(String str, int i, boolean z) {
        this.x = new com.dofun.carsetting.c.d(this.f479a);
        this.x.a(this.l);
        this.x.a(str, i, z);
        this.x.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.widget.EditTextPreference.2
            @Override // com.dofun.carsetting.g.g
            public void a(EditTextPreference editTextPreference, Editable editable) {
                if (EditTextPreference.this.z != null) {
                    EditTextPreference.this.z.a(EditTextPreference.this, editable);
                }
            }
        });
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    public void setNeedPassword(boolean z) {
        this.t = z;
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    public void setPassword(String str) {
        this.d = str;
    }

    public boolean getNeedPassword() {
        return this.t;
    }

    public String getPassword() {
        return this.d;
    }
}
