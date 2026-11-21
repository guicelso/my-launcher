package com.dofun.carsetting.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.dofun.carsetting.R;

/* JADX INFO: loaded from: classes.dex */
public abstract class TwPreference extends RelativeLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected Context f479a;
    protected com.dofun.carsetting.g.j b;
    protected boolean c;
    protected String d;
    private String e;
    private com.dofun.carsetting.c.d f;

    interface a {
        void a(boolean z);
    }

    protected abstract void a();

    protected abstract void a(AttributeSet attributeSet);

    protected abstract void b();

    public TwPreference(Context context) {
        super(context);
        this.c = false;
        this.f479a = context;
        a();
        b();
    }

    public TwPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = false;
        this.f479a = context;
        a();
        a(attributeSet);
        b();
    }

    public TwPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = false;
        this.f479a = context;
        a();
        a(attributeSet);
        b();
    }

    public String getKey() {
        return this.e;
    }

    protected void setKey(String str) {
        this.e = str;
    }

    public void setOnPreferenceChangeListener(com.dofun.carsetting.g.j jVar) {
        this.b = jVar;
    }

    protected void a(Object obj) {
        SharedPreferences.Editor editorEdit = this.f479a.getSharedPreferences("XTCarSetting", 0).edit();
        if (obj instanceof Integer) {
            editorEdit.putInt(this.e, ((Integer) obj).intValue());
        } else if (obj instanceof Float) {
            editorEdit.putFloat(this.e, ((Float) obj).floatValue());
        } else if (obj instanceof Boolean) {
            editorEdit.putBoolean(this.e, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Long) {
            editorEdit.putLong(this.e, ((Long) obj).longValue());
        } else if (obj instanceof String) {
            editorEdit.putString(this.e, (String) obj);
        }
        editorEdit.apply();
    }

    protected Object b(Object obj) {
        SharedPreferences sharedPreferences = this.f479a.getSharedPreferences("XTCarSetting", 0);
        if (obj instanceof Integer) {
            return Integer.valueOf(sharedPreferences.getInt(this.e, 0));
        }
        if (obj instanceof Float) {
            return Float.valueOf(sharedPreferences.getFloat(this.e, 0.0f));
        }
        if (obj instanceof Boolean) {
            return Boolean.valueOf(sharedPreferences.getBoolean(this.e, false));
        }
        if (obj instanceof Long) {
            return Long.valueOf(sharedPreferences.getLong(this.e, 0L));
        }
        if (obj instanceof String) {
            return sharedPreferences.getString(this.e, "");
        }
        return sharedPreferences.getString(this.e, "");
    }

    public void a(String str, String str2, final a aVar) {
        if (this.f == null) {
            this.f = new com.dofun.carsetting.c.d(this.f479a);
        }
        this.f.a(str);
        this.f.a(this.f479a.getString(R.string.enter_your_pin), 2, true);
        this.f.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.widget.TwPreference.1
            @Override // com.dofun.carsetting.g.g
            public void a(EditTextPreference editTextPreference, Editable editable) {
                if (aVar != null) {
                    if (TextUtils.equals(TwPreference.this.d, editable.toString())) {
                        aVar.a(true);
                    } else {
                        aVar.a(false);
                    }
                }
            }
        });
        this.f.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.widget.TwPreference.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });
        this.f.show();
    }

    public void setNeedPassword(boolean z) {
        this.c = z;
    }

    public void setPassword(String str) {
        this.d = str;
    }
}
