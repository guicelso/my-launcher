package android.support.v7.view.menu;

import a.b.e.b.a;
import a.b.e.b.f;
import a.b.e.b.g;
import a.b.e.b.h;
import a.b.e.b.j;
import a.b.e.e.a.n;
import a.b.e.e.a.u;
import a.b.e.f.ha;
import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class ListMenuItemView extends LinearLayout implements u.a, AbsListView.SelectionBoundsAdjuster {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public n f404a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ImageView f405b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public RadioButton f406c;
    public TextView d;
    public CheckBox e;
    public TextView f;
    public ImageView g;
    public ImageView h;
    public LinearLayout i;
    public int j;
    public Context k;
    public boolean l;
    public Drawable m;
    public Drawable mBackground;
    public boolean n;
    public LayoutInflater o;
    public boolean p;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        ha haVarA = ha.a(getContext(), attributeSet, j.MenuView, i, 0);
        this.mBackground = haVarA.b(j.MenuView_android_itemBackground);
        this.j = haVarA.f(j.MenuView_android_itemTextAppearance, -1);
        this.l = haVarA.a(j.MenuView_preserveIconSpacing, false);
        this.k = context;
        this.m = haVarA.b(j.MenuView_subMenuArrow);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, new int[]{R.attr.divider}, a.dropDownListViewStyle, 0);
        this.n = typedArrayObtainStyledAttributes.hasValue(0);
        haVarA.f337b.recycle();
        typedArrayObtainStyledAttributes.recycle();
    }

    private LayoutInflater getInflater() {
        if (this.o == null) {
            this.o = LayoutInflater.from(getContext());
        }
        return this.o;
    }

    private void setSubMenuArrowVisible(boolean z) {
        ImageView imageView = this.g;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 8);
        }
    }

    @Override // a.b.e.e.a.u.a
    public void a(n nVar, int i) {
        this.f404a = nVar;
        setVisibility(nVar.isVisible() ? 0 : 8);
        setTitle(nVar.a(this));
        setCheckable((nVar.y & 1) == 1);
        a(nVar.f(), nVar.b());
        setIcon(nVar.getIcon());
        setEnabled(nVar.isEnabled());
        setSubMenuArrowVisible(nVar.hasSubMenu());
        setContentDescription(nVar.r);
    }

    public final void a(View view, int i) {
        LinearLayout linearLayout = this.i;
        if (linearLayout != null) {
            linearLayout.addView(view, i);
        } else {
            addView(view, i);
        }
    }

    @Override // android.widget.AbsListView.SelectionBoundsAdjuster
    public void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.h;
        if (imageView == null || imageView.getVisibility() != 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.h.getLayoutParams();
        rect.top = this.h.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin + rect.top;
    }

    public final void b() {
        this.f406c = (RadioButton) getInflater().inflate(g.abc_list_menu_item_radio, (ViewGroup) this, false);
        RadioButton radioButton = this.f406c;
        LinearLayout linearLayout = this.i;
        if (linearLayout != null) {
            linearLayout.addView(radioButton, -1);
        } else {
            addView(radioButton, -1);
        }
    }

    @Override // a.b.e.e.a.u.a
    public n getItemData() {
        return this.f404a;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        a.b.d.h.n.a(this, this.mBackground);
        this.d = (TextView) findViewById(f.title);
        int i = this.j;
        if (i != -1) {
            this.d.setTextAppearance(this.k, i);
        }
        this.f = (TextView) findViewById(f.shortcut);
        this.g = (ImageView) findViewById(f.submenuarrow);
        ImageView imageView = this.g;
        if (imageView != null) {
            imageView.setImageDrawable(this.m);
        }
        this.h = (ImageView) findViewById(f.group_divider);
        this.i = (LinearLayout) findViewById(f.content);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        if (this.f405b != null && this.l) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.f405b.getLayoutParams();
            if (layoutParams.height > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = layoutParams.height;
            }
        }
        super.onMeasure(i, i2);
    }

    @Override // a.b.e.e.a.u.a
    public boolean prefersCondensedTitle() {
        return false;
    }

    public void setCheckable(boolean z) {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        if (!z && this.f406c == null && this.e == null) {
            return;
        }
        if (this.f404a.e()) {
            if (this.f406c == null) {
                b();
            }
            compoundButton = this.f406c;
            compoundButton2 = this.e;
        } else {
            if (this.e == null) {
                a();
            }
            compoundButton = this.e;
            compoundButton2 = this.f406c;
        }
        if (z) {
            compoundButton.setChecked((this.f404a.y & 2) == 2);
            if (compoundButton.getVisibility() != 0) {
                compoundButton.setVisibility(0);
            }
            if (compoundButton2 == null || compoundButton2.getVisibility() == 8) {
                return;
            }
            compoundButton2.setVisibility(8);
            return;
        }
        CheckBox checkBox = this.e;
        if (checkBox != null) {
            checkBox.setVisibility(8);
        }
        RadioButton radioButton = this.f406c;
        if (radioButton != null) {
            radioButton.setVisibility(8);
        }
    }

    public void setChecked(boolean z) {
        CompoundButton compoundButton;
        if (this.f404a.e()) {
            if (this.f406c == null) {
                b();
            }
            compoundButton = this.f406c;
        } else {
            if (this.e == null) {
                a();
            }
            compoundButton = this.e;
        }
        compoundButton.setChecked(z);
    }

    public void setForceShowIcon(boolean z) {
        this.p = z;
        this.l = z;
    }

    public void setGroupDividerEnabled(boolean z) {
        ImageView imageView = this.h;
        if (imageView != null) {
            imageView.setVisibility((this.n || !z) ? 8 : 0);
        }
    }

    public void setIcon(Drawable drawable) {
        boolean z = this.f404a.n.s || this.p;
        if (z || this.l) {
            if (this.f405b == null && drawable == null && !this.l) {
                return;
            }
            if (this.f405b == null) {
                this.f405b = (ImageView) getInflater().inflate(g.abc_list_menu_item_icon, (ViewGroup) this, false);
                a(this.f405b, 0);
            }
            if (drawable == null && !this.l) {
                this.f405b.setVisibility(8);
                return;
            }
            ImageView imageView = this.f405b;
            if (!z) {
                drawable = null;
            }
            imageView.setImageDrawable(drawable);
            if (this.f405b.getVisibility() != 0) {
                this.f405b.setVisibility(0);
            }
        }
    }

    public void setTitle(CharSequence charSequence) {
        int i;
        TextView textView;
        if (charSequence != null) {
            this.d.setText(charSequence);
            if (this.d.getVisibility() == 0) {
                return;
            }
            textView = this.d;
            i = 0;
        } else {
            i = 8;
            if (this.d.getVisibility() == 8) {
                return;
            } else {
                textView = this.d;
            }
        }
        textView.setVisibility(i);
    }

    public final void a() {
        this.e = (CheckBox) getInflater().inflate(g.abc_list_menu_item_checkbox, (ViewGroup) this, false);
        CheckBox checkBox = this.e;
        LinearLayout linearLayout = this.i;
        if (linearLayout != null) {
            linearLayout.addView(checkBox, -1);
        } else {
            addView(checkBox, -1);
        }
    }

    public void a(boolean z, char c2) {
        int i;
        String string;
        int i2 = (z && this.f404a.f()) ? 0 : 8;
        if (i2 == 0) {
            TextView textView = this.f;
            n nVar = this.f404a;
            char cB = nVar.b();
            if (cB == 0) {
                string = "";
            } else {
                Resources resources = nVar.n.f229b.getResources();
                StringBuilder sb = new StringBuilder();
                if (ViewConfiguration.get(nVar.n.f229b).hasPermanentMenuKey()) {
                    sb.append(resources.getString(h.abc_prepend_shortcut_label));
                }
                int i3 = nVar.n.e() ? nVar.k : nVar.i;
                n.a(sb, i3, 65536, resources.getString(h.abc_menu_meta_shortcut_label));
                n.a(sb, i3, 4096, resources.getString(h.abc_menu_ctrl_shortcut_label));
                n.a(sb, i3, 2, resources.getString(h.abc_menu_alt_shortcut_label));
                n.a(sb, i3, 1, resources.getString(h.abc_menu_shift_shortcut_label));
                n.a(sb, i3, 4, resources.getString(h.abc_menu_sym_shortcut_label));
                n.a(sb, i3, 8, resources.getString(h.abc_menu_function_shortcut_label));
                if (cB == '\b') {
                    i = h.abc_menu_delete_shortcut_label;
                } else if (cB != '\n') {
                    if (cB != ' ') {
                        sb.append(cB);
                    } else {
                        i = h.abc_menu_space_shortcut_label;
                    }
                    string = sb.toString();
                } else {
                    i = h.abc_menu_enter_shortcut_label;
                }
                sb.append(resources.getString(i));
                string = sb.toString();
            }
            textView.setText(string);
        }
        if (this.f.getVisibility() != i2) {
            this.f.setVisibility(i2);
        }
    }
}
