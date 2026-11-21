package com.dofun.carsetting.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.dofun.carsetting.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class InfoPreference extends TwPreference {
    private com.dofun.carsetting.c.d A;
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
    private int o;
    private boolean p;
    private Drawable q;
    private boolean r;
    private boolean s;
    private boolean t;
    private CharSequence[] u;
    private CharSequence[] v;
    private ArrayList<RadioButton> w;
    private AlertDialog x;
    private RadioGroup y;
    private View z;

    public InfoPreference(Context context) {
        super(context);
        this.p = true;
        this.r = true;
        this.s = true;
        this.t = true;
    }

    public InfoPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.p = true;
        this.r = true;
        this.s = true;
        this.t = true;
    }

    public InfoPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.p = true;
        this.r = true;
        this.s = true;
        this.t = true;
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a() {
        this.z = LayoutInflater.from(this.f479a).inflate(R.layout.preference_information, this);
        this.e = this.z.findViewById(R.id.item_container);
        this.f = (TextView) this.z.findViewById(R.id.tv_title);
        this.g = (TextView) this.z.findViewById(R.id.tv_summary);
        this.h = (TextView) this.z.findViewById(R.id.tv_value);
        this.h.setSelected(true);
        this.i = (ImageView) this.z.findViewById(R.id.iv_value_icon);
        this.j = (ImageView) this.z.findViewById(R.id.iv_right_icon);
        this.k = this.z.findViewById(R.id.view_divider);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.InfoPreference.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!InfoPreference.this.c) {
                    if (InfoPreference.this.u != null) {
                        InfoPreference.this.b(InfoPreference.this.l);
                        return;
                    } else {
                        if (InfoPreference.this.b != null) {
                            InfoPreference.this.b.a(InfoPreference.this, "");
                            return;
                        }
                        return;
                    }
                }
                InfoPreference.this.A = new com.dofun.carsetting.c.d(InfoPreference.this.f479a);
                InfoPreference.this.A.a(InfoPreference.this.l);
                InfoPreference.this.A.a(InfoPreference.this.f479a.getString(R.string.enter_your_pin), 2, true);
                InfoPreference.this.A.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.widget.InfoPreference.1.1
                    @Override // com.dofun.carsetting.g.g
                    public void a(EditTextPreference editTextPreference, Editable editable) {
                        if (TextUtils.equals(InfoPreference.this.d, editable.toString())) {
                            if (InfoPreference.this.u != null) {
                                InfoPreference.this.b(InfoPreference.this.l);
                            } else if (InfoPreference.this.b != null) {
                                InfoPreference.this.b.a(InfoPreference.this, "");
                            }
                        }
                    }
                });
                InfoPreference.this.A.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.widget.InfoPreference.1.2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        InputMethodManager inputMethodManager = (InputMethodManager) InfoPreference.this.getContext().getSystemService("input_method");
                        if (inputMethodManager.isActive()) {
                            return;
                        }
                        inputMethodManager.toggleSoftInput(1, 0);
                    }
                });
                InfoPreference.this.A.show();
            }
        });
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = this.f479a.obtainStyledAttributes(attributeSet, R.styleable.InfoPreference);
        setKey(typedArrayObtainStyledAttributes.getString(5));
        this.l = typedArrayObtainStyledAttributes.getString(15);
        this.m = typedArrayObtainStyledAttributes.getString(13);
        this.o = typedArrayObtainStyledAttributes.getColor(14, this.f479a.getColor(R.color.pref_text_summary_color));
        this.n = typedArrayObtainStyledAttributes.getString(7);
        this.d = typedArrayObtainStyledAttributes.getString(6);
        this.q = typedArrayObtainStyledAttributes.getDrawable(8);
        this.p = typedArrayObtainStyledAttributes.getBoolean(11, true);
        this.r = typedArrayObtainStyledAttributes.getBoolean(12, false);
        this.s = typedArrayObtainStyledAttributes.getBoolean(10, true);
        this.t = typedArrayObtainStyledAttributes.getBoolean(9, true);
        this.c = typedArrayObtainStyledAttributes.getBoolean(3, false);
        this.u = typedArrayObtainStyledAttributes.getTextArray(0);
        this.v = typedArrayObtainStyledAttributes.getTextArray(1);
        typedArrayObtainStyledAttributes.recycle();
        this.f.setText(this.l);
        this.g.setText(this.m);
        this.h.setText(this.n);
        if (this.o != 0) {
            this.g.setTextColor(this.o);
        } else {
            this.g.setTextColor(this.f479a.getColor(R.color.pref_text_summary_color));
        }
        if (this.q != null) {
            this.i.setVisibility(0);
            this.i.setImageDrawable(this.q);
        } else {
            this.i.setVisibility(8);
        }
        this.j.setVisibility(this.p ? 0 : 8);
        this.k.setVisibility(this.s ? 0 : 8);
        this.h.setVisibility(this.r ? 0 : 8);
        if (!TextUtils.isEmpty(this.m)) {
            this.g.setVisibility(0);
        } else {
            this.g.setVisibility(8);
        }
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void b() {
        if (this.u == null || this.v == null) {
            return;
        }
        if (this.w == null) {
            this.w = new ArrayList<>();
        }
        this.w.clear();
        for (int i = 0; i < this.u.length; i++) {
            RadioButton radioButton = new RadioButton(this.f479a);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(-1, (int) this.f479a.getResources().getDimension(R.dimen.preference_single_choose_height)));
            radioButton.setId(i);
            radioButton.setText(this.u[i]);
            radioButton.setButtonDrawable(R.drawable.select_on_off_circle);
            radioButton.setMaxLines(1);
            radioButton.setBackgroundResource(R.drawable.ripple_bg);
            radioButton.setTextColor(this.f479a.getColor(R.color.text_color_normal));
            radioButton.setTextSize(0, this.f479a.getResources().getDimensionPixelSize(R.dimen.normal_text_size));
            this.w.add(radioButton);
        }
    }

    public void setTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f.setText(str);
    }

    public void setSummary(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.g.setText(str);
    }

    public void a(CharSequence[] charSequenceArr, CharSequence[] charSequenceArr2) {
        this.u = charSequenceArr;
        this.v = charSequenceArr2;
        b();
    }

    public void setValue(String str) {
        int iA = a(str);
        if (iA == -1) {
            return;
        }
        a(Integer.valueOf(iA));
        setItemValue(iA);
        this.h.setVisibility(0);
        this.h.setText(a(iA));
    }

    public void setItemValue(int i) {
        if (i < 0 || i >= this.w.size()) {
            return;
        }
        this.w.get(i).setChecked(true);
    }

    public void setValueTip(String str) {
        this.h.setVisibility(0);
        this.h.setText(str);
    }

    public String getValue() {
        int iIntValue = ((Integer) b((Object) 0)).intValue();
        if (iIntValue >= 0 && iIntValue < this.v.length) {
            return a(iIntValue);
        }
        return a(0);
    }

    public void a(boolean z) {
        this.i.setVisibility(z ? 0 : 8);
    }

    public void setValueIcon(Drawable drawable) {
        if (drawable == null) {
            Log.e("InfoPreference", "setValueIcon: drawable cannot be null");
        } else {
            this.i.setVisibility(0);
            this.i.setImageDrawable(drawable);
        }
    }

    public ImageView getValueIconView() {
        return this.i;
    }

    public int a(String str) {
        if (str == null || this.v == null) {
            return -1;
        }
        for (int length = this.v.length - 1; length >= 0; length--) {
            if (this.v[length].equals(str)) {
                return length;
            }
        }
        return -1;
    }

    private String a(int i) {
        return (i < 0 || i > this.u.length) ? "" : this.u[i].toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        if (this.w == null || this.w.size() == 0) {
            return;
        }
        View viewInflate = LayoutInflater.from(this.f479a).inflate(R.layout.dialog_single_choose, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.f479a, R.style.custom_dialog);
        builder.setView(viewInflate);
        ((TextView) viewInflate.findViewById(R.id.tv_dialog_title)).setText(str);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        this.y = (RadioGroup) viewInflate.findViewById(R.id.rg_list);
        int iIntValue = ((Integer) b((Object) 0)).intValue();
        if (this.t) {
            if (iIntValue >= 0 && iIntValue < this.w.size()) {
                this.w.get(iIntValue).setChecked(true);
            } else {
                this.w.get(0).setChecked(true);
            }
        }
        this.y.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.dofun.carsetting.widget.InfoPreference.2
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    RadioButton radioButton = (RadioButton) InfoPreference.this.w.get(i);
                    if (radioButton != null) {
                        if (radioButton.isPressed() || radioButton.isFocused()) {
                            InfoPreference.this.h.setText(InfoPreference.this.u[i]);
                            InfoPreference.this.a(Integer.valueOf(i));
                            if (InfoPreference.this.b != null) {
                                InfoPreference.this.b.a(InfoPreference.this, InfoPreference.this.v[i]);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("InfoPreference", "onCheckedChanged: " + e.getMessage());
                }
            }
        });
        try {
            ((ViewGroup) this.w.get(0).getParent()).removeAllViews();
        } catch (Exception e) {
            Log.e("InfoPreference", "onDismiss: " + e.getMessage());
        }
        for (int i = 0; i < this.w.size(); i++) {
            this.y.addView(this.w.get(i));
        }
        this.x = builder.show();
        Window window = this.x.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = 17;
            int size = this.w.size();
            attributes.width = (int) this.f479a.getResources().getDimension(R.dimen.dialog_single_choose_width);
            if (size == 0) {
                size = 1;
            } else if (size > 4) {
                size = 4;
            }
            float dimension = this.f479a.getResources().getDimension(R.dimen.dialog_title_height);
            float dimension2 = this.f479a.getResources().getDimension(R.dimen.tw_dp_h3);
            float dimension3 = this.f479a.getResources().getDimension(R.dimen.preference_single_choose_height) * size;
            float dimension4 = this.f479a.getResources().getDimension(R.dimen.dialog_single_choose_padding);
            attributes.height = (int) (dimension + dimension2 + dimension3 + dimension4);
            Log.d("InfoPreference", "showChooseDialog:titleHeight:" + dimension + " dividHeight:" + dimension2 + " listHeight:" + dimension3 + " paddingSize:" + dimension4 + " height:" + attributes.height);
            this.x.getWindow().setAttributes(attributes);
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.InfoPreference.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InfoPreference.this.x.dismiss();
                try {
                    InfoPreference.this.y.removeAllViews();
                } catch (Exception e2) {
                    Log.e("InfoPreference", "onDismiss: " + e2.getMessage());
                }
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.widget.InfoPreference.4
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    Log.d("InfoPreference", "onDismiss: ");
                    InfoPreference.this.y.removeAllViews();
                } catch (Exception e2) {
                    Log.e("InfoPreference", "onDismiss: " + e2.getMessage());
                }
            }
        });
    }

    public void setSaveValue(boolean z) {
        this.t = z;
        for (int i = 0; i < this.w.size(); i++) {
            this.w.get(i).setChecked(false);
        }
    }

    public void setAllSaveValue(boolean z) {
        this.t = z;
        for (int i = 0; i < this.w.size(); i++) {
            this.w.get(i).setChecked(false);
            if (this.w.get(i).isFocused()) {
                clearFocus();
            }
        }
    }

    public void c() {
        this.x.dismiss();
        try {
            this.y.removeAllViews();
            for (int i = 0; i < this.w.size(); i++) {
                this.w.get(i).setChecked(false);
            }
        } catch (Exception e) {
            Log.e("InfoPreference", "onDismiss: " + e.getMessage());
        }
    }

    public void d() {
        this.w = new ArrayList<>();
    }
}
