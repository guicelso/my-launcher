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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dofun.carsetting.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class InfoPreferenceV2 extends TwPreference {
    private TextView e;
    private TextView f;
    private TextView g;
    private ImageView h;
    private ImageView i;
    private View j;
    private String k;
    private String l;
    private String m;
    private boolean n;
    private Drawable o;
    private boolean p;
    private boolean q;
    private boolean r;
    private CharSequence[] s;
    private CharSequence[] t;
    private ArrayList<RadioButton> u;
    private AlertDialog v;
    private RadioGroup w;
    private com.dofun.carsetting.c.d x;

    public InfoPreferenceV2(Context context) {
        super(context);
        this.n = true;
        this.p = true;
        this.q = true;
        this.r = true;
    }

    public InfoPreferenceV2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.n = true;
        this.p = true;
        this.q = true;
        this.r = true;
    }

    public InfoPreferenceV2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.n = true;
        this.p = true;
        this.q = true;
        this.r = true;
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
        relativeLayout.addView(relativeLayout2, new RelativeLayout.LayoutParams(-1, -1));
        RelativeLayout relativeLayout3 = new RelativeLayout(this.f479a);
        LinearLayout linearLayout = new LinearLayout(this.f479a);
        linearLayout.setGravity(16);
        linearLayout.setOrientation(1);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams2.height = (int) getResources().getDimension(R.dimen.preference_height);
        relativeLayout3.addView(linearLayout, layoutParams2);
        this.e = new TextView(this.f479a);
        this.e.setId(View.generateViewId());
        this.e.setGravity(19);
        this.e.setMaxLines(1);
        this.e.setTextColor(getResources().getColor(R.color.text_color_normal));
        this.e.setTextSize(0, getResources().getDimension(R.dimen.preference_title_text_size));
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams3.weight = 1.0f;
        linearLayout.addView(this.e, layoutParams3);
        this.f = new TextView(this.f479a);
        this.f.setId(View.generateViewId());
        this.f.setGravity(19);
        this.f.setMaxLines(1);
        this.f.setTextColor(this.f479a.getColor(R.color.pref_text_summary_color));
        this.f.setTextSize(0, getResources().getDimension(R.dimen.preference_summary_text_size));
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams4.weight = 1.0f;
        linearLayout.addView(this.f, layoutParams4);
        LinearLayout linearLayout2 = new LinearLayout(this.f479a);
        linearLayout2.setId(View.generateViewId());
        linearLayout2.setGravity(16);
        linearLayout2.setOrientation(0);
        this.h = new ImageView(this.f479a);
        this.h.setId(View.generateViewId());
        this.h.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-2, -1);
        layoutParams5.width = (int) getResources().getDimension(R.dimen.preference_value_icon_width);
        layoutParams5.height = (int) getResources().getDimension(R.dimen.preference_value_icon_height);
        layoutParams5.setMargins((int) getResources().getDimension(R.dimen.tw_dp_w32), 0, (int) getResources().getDimension(R.dimen.tw_dp_w16), 0);
        linearLayout2.addView(this.h, layoutParams5);
        this.g = new TextView(this.f479a);
        this.g.setId(View.generateViewId());
        this.g.setGravity(19);
        this.g.setMaxWidth((int) getResources().getDimension(R.dimen.preference_value_min_width));
        this.g.setSingleLine(true);
        this.g.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        this.g.setMarqueeRepeatLimit(-1);
        this.g.setTextColor(this.f479a.getColor(R.color.pref_text_summary_color));
        this.g.setTextSize(0, getResources().getDimension(R.dimen.preference_value_text_size));
        this.g.setSelected(true);
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-2, -1);
        layoutParams6.setMargins((int) getResources().getDimension(R.dimen.tw_dp_w16), 0, (int) getResources().getDimension(R.dimen.tw_dp_w16), 0);
        linearLayout2.addView(this.g, layoutParams6);
        this.i = new ImageView(this.f479a);
        this.i.setId(View.generateViewId());
        this.i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.i.setImageResource(R.drawable.pref_select_more);
        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-2, -1);
        layoutParams7.width = (int) getResources().getDimension(R.dimen.preference_right_icon_width);
        layoutParams7.height = (int) getResources().getDimension(R.dimen.preference_right_icon_height);
        layoutParams7.setMargins((int) getResources().getDimension(R.dimen.tw_dp_w32), 0, (int) getResources().getDimension(R.dimen.tw_dp_w16), 0);
        linearLayout2.addView(this.i, layoutParams7);
        RelativeLayout.LayoutParams layoutParams8 = new RelativeLayout.LayoutParams(-2, -1);
        layoutParams8.setMargins((int) getResources().getDimension(R.dimen.tw_dp_w16), 0, 0, 0);
        layoutParams8.addRule(11);
        relativeLayout2.addView(linearLayout2, layoutParams8);
        RelativeLayout.LayoutParams layoutParams9 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams9.addRule(0, linearLayout2.getId());
        relativeLayout2.addView(relativeLayout3, layoutParams9);
        this.j = new View(this.f479a);
        this.j.setBackgroundResource(R.drawable.tl_list_divider2);
        RelativeLayout.LayoutParams layoutParams10 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams10.height = (int) getResources().getDimension(R.dimen.tw_dp_h2);
        layoutParams10.addRule(12);
        relativeLayout.addView(this.j, layoutParams10);
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.InfoPreferenceV2.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!InfoPreferenceV2.this.c) {
                    if (InfoPreferenceV2.this.s != null) {
                        InfoPreferenceV2.this.b(InfoPreferenceV2.this.k);
                        return;
                    } else {
                        if (InfoPreferenceV2.this.b != null) {
                            InfoPreferenceV2.this.b.a(InfoPreferenceV2.this, "");
                            return;
                        }
                        return;
                    }
                }
                InfoPreferenceV2.this.x = new com.dofun.carsetting.c.d(InfoPreferenceV2.this.f479a);
                InfoPreferenceV2.this.x.a(InfoPreferenceV2.this.k);
                InfoPreferenceV2.this.x.a(InfoPreferenceV2.this.f479a.getString(R.string.enter_your_pin), 2, true);
                InfoPreferenceV2.this.x.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.widget.InfoPreferenceV2.1.1
                    @Override // com.dofun.carsetting.g.g
                    public void a(EditTextPreference editTextPreference, Editable editable) {
                        if (TextUtils.equals(InfoPreferenceV2.this.d, editable.toString())) {
                            if (InfoPreferenceV2.this.s != null) {
                                InfoPreferenceV2.this.b(InfoPreferenceV2.this.k);
                            } else if (InfoPreferenceV2.this.b != null) {
                                InfoPreferenceV2.this.b.a(InfoPreferenceV2.this, "");
                            }
                        }
                    }
                });
                InfoPreferenceV2.this.x.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.widget.InfoPreferenceV2.1.2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        InputMethodManager inputMethodManager = (InputMethodManager) InfoPreferenceV2.this.getContext().getSystemService("input_method");
                        if (inputMethodManager.isActive()) {
                            return;
                        }
                        inputMethodManager.toggleSoftInput(1, 0);
                    }
                });
                InfoPreferenceV2.this.x.show();
            }
        });
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void a(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = this.f479a.obtainStyledAttributes(attributeSet, R.styleable.InfoPreference);
        setKey(typedArrayObtainStyledAttributes.getString(5));
        this.k = typedArrayObtainStyledAttributes.getString(15);
        this.l = typedArrayObtainStyledAttributes.getString(13);
        this.m = typedArrayObtainStyledAttributes.getString(7);
        this.d = typedArrayObtainStyledAttributes.getString(6);
        this.o = typedArrayObtainStyledAttributes.getDrawable(8);
        this.n = typedArrayObtainStyledAttributes.getBoolean(11, true);
        this.p = typedArrayObtainStyledAttributes.getBoolean(12, false);
        this.q = typedArrayObtainStyledAttributes.getBoolean(10, true);
        this.r = typedArrayObtainStyledAttributes.getBoolean(9, true);
        this.c = typedArrayObtainStyledAttributes.getBoolean(3, false);
        this.s = typedArrayObtainStyledAttributes.getTextArray(0);
        this.t = typedArrayObtainStyledAttributes.getTextArray(1);
        typedArrayObtainStyledAttributes.recycle();
        this.e.setText(this.k);
        this.f.setText(this.l);
        this.g.setText(this.m);
        if (this.o != null) {
            this.h.setVisibility(0);
            this.h.setImageDrawable(this.o);
        } else {
            this.h.setVisibility(8);
        }
        this.i.setVisibility(this.n ? 0 : 8);
        this.j.setVisibility(this.q ? 0 : 8);
        this.g.setVisibility(this.p ? 0 : 8);
        if (!TextUtils.isEmpty(this.l)) {
            this.f.setVisibility(0);
        } else {
            this.f.setVisibility(8);
        }
    }

    @Override // com.dofun.carsetting.widget.TwPreference
    protected void b() {
        if (this.s == null || this.t == null) {
            return;
        }
        if (this.u == null) {
            this.u = new ArrayList<>();
        }
        this.u.clear();
        for (int i = 0; i < this.s.length; i++) {
            RadioButton radioButton = new RadioButton(this.f479a);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(-1, (int) this.f479a.getResources().getDimension(R.dimen.preference_single_choose_height)));
            radioButton.setId(i);
            radioButton.setText(this.s[i]);
            radioButton.setButtonDrawable(R.drawable.select_on_off_circle);
            radioButton.setMaxLines(1);
            radioButton.setBackgroundResource(R.drawable.ripple_bg);
            radioButton.setTextSize(0, this.f479a.getResources().getDimensionPixelSize(R.dimen.normal_text_size));
            this.u.add(radioButton);
        }
    }

    public void setTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.e.setText(str);
    }

    public void setSummary(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f.setText(str);
    }

    public void setValue(String str) {
        int iA = a(str);
        if (iA == -1) {
            return;
        }
        a(Integer.valueOf(iA));
        setItemValue(iA);
        this.g.setVisibility(0);
        this.g.setText(a(iA));
    }

    public void setValueTip(String str) {
        this.g.setVisibility(0);
        this.g.setText(str);
    }

    public String getValue() {
        int iIntValue = ((Integer) b((Object) 0)).intValue();
        if (iIntValue >= 0 && iIntValue < this.t.length) {
            return a(iIntValue);
        }
        return a(0);
    }

    public void setValueIcon(Drawable drawable) {
        if (drawable == null) {
            Log.e("InfoPreferenceV2", "setValueIcon: drawable cannot be null");
        } else {
            this.h.setVisibility(0);
            this.h.setImageDrawable(drawable);
        }
    }

    public ImageView getValueIconView() {
        return this.h;
    }

    public int a(String str) {
        if (str == null || this.t == null) {
            return -1;
        }
        for (int length = this.t.length - 1; length >= 0; length--) {
            if (this.t[length].equals(str)) {
                return length;
            }
        }
        return -1;
    }

    private String a(int i) {
        return (i < 0 || i > this.s.length) ? "" : this.s[i].toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        if (this.u == null || this.u.size() == 0) {
            return;
        }
        View viewInflate = LayoutInflater.from(this.f479a).inflate(R.layout.dialog_single_choose, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.f479a, R.style.custom_dialog);
        builder.setView(viewInflate);
        ((TextView) viewInflate.findViewById(R.id.tv_dialog_title)).setText(str);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        this.w = (RadioGroup) viewInflate.findViewById(R.id.rg_list);
        int iIntValue = ((Integer) b((Object) 0)).intValue();
        if (this.r) {
            if (iIntValue >= 0 && iIntValue < this.u.size()) {
                this.u.get(iIntValue).setChecked(true);
            } else {
                this.u.get(0).setChecked(true);
            }
        }
        this.w.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.dofun.carsetting.widget.InfoPreferenceV2.2
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    RadioButton radioButton = (RadioButton) InfoPreferenceV2.this.u.get(i);
                    if (radioButton != null) {
                        if (radioButton.isPressed() || radioButton.isFocused()) {
                            InfoPreferenceV2.this.g.setText(InfoPreferenceV2.this.s[i]);
                            InfoPreferenceV2.this.a(Integer.valueOf(i));
                            if (InfoPreferenceV2.this.b != null) {
                                InfoPreferenceV2.this.b.a(InfoPreferenceV2.this, InfoPreferenceV2.this.t[i]);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("InfoPreferenceV2", "onCheckedChanged: " + e.getMessage());
                }
            }
        });
        try {
            ((ViewGroup) this.u.get(0).getParent()).removeAllViews();
        } catch (Exception e) {
            Log.e("InfoPreferenceV2", "onDismiss: " + e.getMessage());
        }
        for (int i = 0; i < this.u.size(); i++) {
            this.w.addView(this.u.get(i));
        }
        this.v = builder.show();
        Window window = this.v.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = 17;
            int size = this.u.size();
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
            Log.d("InfoPreferenceV2", "showChooseDialog:titleHeight:" + dimension + " dividHeight:" + dimension2 + " listHeight:" + dimension3 + " paddingSize:" + dimension4 + " height:" + attributes.height);
            this.v.getWindow().setAttributes(attributes);
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.widget.InfoPreferenceV2.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InfoPreferenceV2.this.v.dismiss();
                try {
                    InfoPreferenceV2.this.w.removeAllViews();
                } catch (Exception e2) {
                    Log.e("InfoPreferenceV2", "onDismiss: " + e2.getMessage());
                }
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.widget.InfoPreferenceV2.4
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    Log.d("InfoPreferenceV2", "onDismiss: ");
                    InfoPreferenceV2.this.w.removeAllViews();
                } catch (Exception e2) {
                    Log.e("InfoPreferenceV2", "onDismiss: " + e2.getMessage());
                }
            }
        });
    }

    public void setSaveValue(boolean z) {
        this.r = z;
        for (int i = 0; i < this.u.size(); i++) {
            this.u.get(i).setChecked(false);
        }
    }

    public void setItemValue(int i) {
        if (i < 0 || i >= this.u.size()) {
            return;
        }
        this.u.get(i).setChecked(true);
    }

    public void setAllSaveValue(boolean z) {
        this.r = z;
        for (int i = 0; i < this.u.size(); i++) {
            this.u.get(i).setChecked(false);
            if (this.u.get(i).isFocused()) {
                clearFocus();
            }
        }
    }
}
