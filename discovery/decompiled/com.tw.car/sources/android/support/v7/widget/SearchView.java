package android.support.v7.widget;

import a.b.d.h.n;
import a.b.e.b.g;
import a.b.e.b.h;
import a.b.e.b.j;
import a.b.e.f.C0017h;
import a.b.e.f.E;
import a.b.e.f.O;
import a.b.e.f.P;
import a.b.e.f.Q;
import a.b.e.f.S;
import a.b.e.f.T;
import a.b.e.f.U;
import a.b.e.f.V;
import a.b.e.f.W;
import a.b.e.f.X;
import a.b.e.f.Y;
import a.b.e.f.Z;
import a.b.e.f.aa;
import a.b.e.f.ba;
import a.b.e.f.ha;
import a.b.e.f.ta;
import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class SearchView extends E implements a.b.e.e.a {
    public static final a p = new a();
    public Rect A;
    public int[] B;
    public int[] C;
    public final ImageView D;
    public final Drawable E;
    public final int F;
    public final int G;
    public final Intent H;
    public final Intent I;
    public final CharSequence J;
    public View.OnFocusChangeListener K;
    public View.OnClickListener L;
    public boolean M;
    public boolean N;
    public a.b.d.i.c O;
    public boolean P;
    public CharSequence Q;
    public boolean R;
    public boolean S;
    public int T;
    public boolean U;
    public CharSequence V;
    public CharSequence W;
    public boolean aa;
    public int ba;
    public SearchableInfo ca;
    public Bundle da;
    public final Runnable ea;
    public Runnable fa;
    public final WeakHashMap<String, Drawable.ConstantState> ga;
    public final View.OnClickListener ha;
    public View.OnKeyListener ia;
    public final TextView.OnEditorActionListener ja;
    public final AdapterView.OnItemClickListener ka;
    public final AdapterView.OnItemSelectedListener la;
    public f mTouchDelegate;
    public TextWatcher ma;
    public final SearchAutoComplete q;
    public final View r;
    public final View s;
    public final View t;
    public final ImageView u;
    public final ImageView v;
    public final ImageView w;
    public final ImageView x;
    public final View y;
    public Rect z;

    public static class SearchAutoComplete extends C0017h {
        public SearchView d;
        public boolean e;
        public final Runnable f;
        public int mThreshold;

        public SearchAutoComplete(Context context) {
            this(context, null, a.b.e.b.a.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, a.b.e.b.a.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.f = new aa(this);
            this.mThreshold = getThreshold();
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int i = configuration.screenWidthDp;
            int i2 = configuration.screenHeightDp;
            if (i >= 960 && i2 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (i < 600) {
                return (i < 640 || i2 < 480) ? 160 : 192;
            }
            return 192;
        }

        public boolean a() {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        public void b() {
            if (this.e) {
                ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 0);
                this.e = false;
            }
        }

        @Override // android.widget.AutoCompleteTextView
        public boolean enoughToFilter() {
            return this.mThreshold <= 0 || super.enoughToFilter();
        }

        @Override // a.b.e.f.C0017h, android.widget.TextView, android.view.View
        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.e) {
                removeCallbacks(this.f);
                post(this.f);
            }
            return inputConnectionOnCreateInputConnection;
        }

        @Override // android.view.View
        public void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            this.d.h();
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (i == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.d.clearFocus();
                        setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i, keyEvent);
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public void onWindowFocusChanged(boolean z) {
            Method method;
            super.onWindowFocusChanged(z);
            if (z && this.d.hasFocus() && getVisibility() == 0) {
                this.e = true;
                if (!SearchView.a(getContext()) || (method = SearchView.p.f424c) == null) {
                    return;
                }
                try {
                    method.invoke(this, true);
                } catch (Exception unused) {
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView
        public void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView
        public void replaceText(CharSequence charSequence) {
        }

        public void setImeVisibility(boolean z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            if (!z) {
                this.e = false;
                removeCallbacks(this.f);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else {
                if (!inputMethodManager.isActive(this)) {
                    this.e = true;
                    return;
                }
                this.e = false;
                removeCallbacks(this.f);
                inputMethodManager.showSoftInput(this, 0);
            }
        }

        public void setSearchView(SearchView searchView) {
            this.d = searchView;
        }

        @Override // android.widget.AutoCompleteTextView
        public void setThreshold(int i) {
            super.setThreshold(i);
            this.mThreshold = i;
        }
    }

    private static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public Method f422a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public Method f423b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public Method f424c;

        public a() {
            try {
                this.f422a = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.f422a.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            try {
                this.f423b = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.f423b.setAccessible(true);
            } catch (NoSuchMethodException unused2) {
            }
            try {
                this.f424c = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                this.f424c.setAccessible(true);
            } catch (NoSuchMethodException unused3) {
            }
        }
    }

    public interface b {
    }

    public interface c {
    }

    public interface d {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static class e extends a.b.d.h.c {
        public static final Parcelable.Creator<e> CREATOR = new Z();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public boolean f425a;

        public e(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f425a = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        public e(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder sbA = c.a.a.a.a.a("SearchView.SavedState{");
            sbA.append(Integer.toHexString(System.identityHashCode(this)));
            sbA.append(" isIconified=");
            sbA.append(this.f425a);
            sbA.append("}");
            return sbA.toString();
        }

        @Override // a.b.d.h.c, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.f157b, i);
            parcel.writeValue(Boolean.valueOf(this.f425a));
        }
    }

    private static class f extends TouchDelegate {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Rect f426a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final Rect f427b;
        public boolean mDelegateTargeted;
        public final View mDelegateView;
        public final int mSlop;
        public final Rect mSlopBounds;

        public f(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.mSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            this.f426a = new Rect();
            this.mSlopBounds = new Rect();
            this.f427b = new Rect();
            a(rect, rect2);
            this.mDelegateView = view;
        }

        public void a(Rect rect, Rect rect2) {
            this.f426a.set(rect);
            this.mSlopBounds.set(rect);
            Rect rect3 = this.mSlopBounds;
            int i = this.mSlop;
            rect3.inset(-i, -i);
            this.f427b.set(rect2);
        }

        /* JADX WARN: Code duplicated, block: B:18:0x003a  */
        @Override // android.view.TouchDelegate
        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z;
            float width;
            int height;
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int action = motionEvent.getAction();
            boolean z2 = true;
            if (action != 0) {
                if (action == 1 || action == 2) {
                    z = this.mDelegateTargeted;
                    if (z && !this.mSlopBounds.contains(x, y)) {
                        z2 = false;
                    }
                } else if (action != 3) {
                    z = false;
                } else {
                    z = this.mDelegateTargeted;
                    this.mDelegateTargeted = false;
                }
            } else if (this.f426a.contains(x, y)) {
                this.mDelegateTargeted = true;
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
            if (!z2 || this.f427b.contains(x, y)) {
                Rect rect = this.f427b;
                width = x - rect.left;
                height = y - rect.top;
            } else {
                width = this.mDelegateView.getWidth() / 2;
                height = this.mDelegateView.getHeight() / 2;
            }
            motionEvent.setLocation(width, height);
            return this.mDelegateView.dispatchTouchEvent(motionEvent);
        }
    }

    public SearchView(Context context) {
        this(context, null, a.b.e.b.a.searchViewStyle);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.b.e.b.a.searchViewStyle);
    }

    public static boolean a(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(a.b.e.b.d.abc_search_view_preferred_height);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(a.b.e.b.d.abc_search_view_preferred_width);
    }

    private void setQuery(CharSequence charSequence) {
        this.q.setText(charSequence);
        this.q.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    public final Intent a(Intent intent, SearchableInfo searchableInfo) {
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        Intent intent2 = new Intent("android.intent.action.SEARCH");
        intent2.setComponent(searchActivity);
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1073741824);
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.da;
        if (bundle2 != null) {
            bundle.putParcelable("app_data", bundle2);
        }
        Intent intent3 = new Intent(intent);
        Resources resources = getResources();
        String string = searchableInfo.getVoiceLanguageModeId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageModeId()) : "free_form";
        String string2 = searchableInfo.getVoicePromptTextId() != 0 ? resources.getString(searchableInfo.getVoicePromptTextId()) : null;
        String string3 = searchableInfo.getVoiceLanguageId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageId()) : null;
        int voiceMaxResults = searchableInfo.getVoiceMaxResults() != 0 ? searchableInfo.getVoiceMaxResults() : 1;
        intent3.putExtra("android.speech.extra.LANGUAGE_MODEL", string);
        intent3.putExtra("android.speech.extra.PROMPT", string2);
        intent3.putExtra("android.speech.extra.LANGUAGE", string3);
        intent3.putExtra("android.speech.extra.MAX_RESULTS", voiceMaxResults);
        intent3.putExtra("calling_package", searchActivity != null ? searchActivity.flattenToShortString() : null);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", activity);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent3;
    }

    public final Intent a(String str, Uri uri, String str2, String str3, int i, String str4) {
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra("user_query", this.W);
        if (str3 != null) {
            intent.putExtra("query", str3);
        }
        if (str2 != null) {
            intent.putExtra("intent_extra_data_key", str2);
        }
        Bundle bundle = this.da;
        if (bundle != null) {
            intent.putExtra("app_data", bundle);
        }
        if (i != 0) {
            intent.putExtra("action_key", i);
            intent.putExtra("action_msg", str4);
        }
        intent.setComponent(this.ca.getSearchActivity());
        return intent;
    }

    public void a() {
        int dimensionPixelSize;
        if (this.y.getWidth() > 1) {
            Resources resources = getContext().getResources();
            int paddingLeft = this.s.getPaddingLeft();
            Rect rect = new Rect();
            boolean zA = ta.a(this);
            if (this.M) {
                dimensionPixelSize = resources.getDimensionPixelSize(a.b.e.b.d.abc_dropdownitem_text_padding_left) + resources.getDimensionPixelSize(a.b.e.b.d.abc_dropdownitem_icon_width);
            } else {
                dimensionPixelSize = 0;
            }
            this.q.getDropDownBackground().getPadding(rect);
            this.q.setDropDownHorizontalOffset(zA ? -rect.left : paddingLeft - (rect.left + dimensionPixelSize));
            this.q.setDropDownWidth((((this.y.getWidth() + rect.left) + rect.right) + dimensionPixelSize) - paddingLeft);
        }
    }

    public void a(int i, String str, String str2) {
        getContext().startActivity(a("android.intent.action.SEARCH", (Uri) null, (String) null, str2, i, str));
    }

    public void a(CharSequence charSequence) {
        setQuery(charSequence);
    }

    public void a(CharSequence charSequence, boolean z) {
        this.q.setText(charSequence);
        if (charSequence != null) {
            SearchAutoComplete searchAutoComplete = this.q;
            searchAutoComplete.setSelection(searchAutoComplete.length());
            this.W = charSequence;
        }
        if (!z || TextUtils.isEmpty(charSequence)) {
            return;
        }
        g();
    }

    public final void a(boolean z) {
        this.v.setVisibility((this.P && d() && hasFocus() && (z || !this.U)) ? 0 : 8);
    }

    public boolean a(int i, int i2, String str) {
        int position;
        String strA;
        Cursor cursor = this.O.getCursor();
        if (cursor != null && cursor.moveToPosition(i)) {
            Intent intentA = null;
            try {
                String strA2 = ba.a(cursor, "suggest_intent_action");
                if (strA2 == null) {
                    strA2 = this.ca.getSuggestIntentAction();
                }
                if (strA2 == null) {
                    strA2 = "android.intent.action.SEARCH";
                }
                String str2 = strA2;
                String strA3 = ba.a(cursor, "suggest_intent_data");
                if (strA3 == null) {
                    strA3 = this.ca.getSuggestIntentData();
                }
                if (strA3 != null && (strA = ba.a(cursor, "suggest_intent_data_id")) != null) {
                    strA3 = strA3 + "/" + Uri.encode(strA);
                }
                intentA = a(str2, strA3 == null ? null : Uri.parse(strA3), ba.a(cursor, "suggest_intent_extra_data"), ba.a(cursor, "suggest_intent_query"), 0, (String) null);
            } catch (RuntimeException e2) {
                try {
                    position = cursor.getPosition();
                } catch (RuntimeException unused) {
                    position = -1;
                }
                Log.w("SearchView", "Search suggestions cursor at row " + position + " returned exception.", e2);
            }
            if (intentA != null) {
                try {
                    getContext().startActivity(intentA);
                } catch (RuntimeException e3) {
                    Log.e("SearchView", "Failed launch activity: " + intentA, e3);
                }
            }
        }
        this.q.setImeVisibility(false);
        this.q.dismissDropDown();
        return true;
    }

    public void b() {
        a aVar = p;
        SearchAutoComplete searchAutoComplete = this.q;
        Method method = aVar.f422a;
        if (method != null) {
            try {
                method.invoke(searchAutoComplete, new Object[0]);
            } catch (Exception unused) {
            }
        }
        a aVar2 = p;
        SearchAutoComplete searchAutoComplete2 = this.q;
        Method method2 = aVar2.f423b;
        if (method2 != null) {
            try {
                method2.invoke(searchAutoComplete2, new Object[0]);
            } catch (Exception unused2) {
            }
        }
    }

    public void b(CharSequence charSequence) {
        Editable text = this.q.getText();
        this.W = text;
        boolean z = !TextUtils.isEmpty(text);
        a(z);
        c(z ? false : true);
        j();
        m();
        this.V = charSequence.toString();
    }

    public final void b(boolean z) {
        this.N = z;
        int i = 8;
        int i2 = z ? 0 : 8;
        boolean z2 = !TextUtils.isEmpty(this.q.getText());
        this.u.setVisibility(i2);
        a(z2);
        this.r.setVisibility(z ? 8 : 0);
        if (this.D.getDrawable() != null && !this.M) {
            i = 0;
        }
        this.D.setVisibility(i);
        j();
        c(z2 ? false : true);
        m();
    }

    public final void c(boolean z) {
        int i;
        if (this.U && !c() && z) {
            i = 0;
            this.v.setVisibility(8);
        } else {
            i = 8;
        }
        this.x.setVisibility(i);
    }

    public boolean c() {
        return this.N;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void clearFocus() {
        this.S = true;
        super.clearFocus();
        this.q.clearFocus();
        this.q.setImeVisibility(false);
        this.S = false;
    }

    public final boolean d() {
        return (this.P || this.U) && !c();
    }

    public boolean d(int i) {
        CharSequence charSequenceConvertToString;
        Editable text = this.q.getText();
        Cursor cursor = this.O.getCursor();
        if (cursor == null) {
            return true;
        }
        if (!cursor.moveToPosition(i) || (charSequenceConvertToString = this.O.convertToString(cursor)) == null) {
            setQuery(text);
            return true;
        }
        setQuery(charSequenceConvertToString);
        return true;
    }

    public void e() {
        if (!TextUtils.isEmpty(this.q.getText())) {
            this.q.setText("");
            this.q.requestFocus();
            this.q.setImeVisibility(true);
        } else if (this.M) {
            clearFocus();
            b(true);
        }
    }

    public void f() {
        b(false);
        this.q.requestFocus();
        this.q.setImeVisibility(true);
        View.OnClickListener onClickListener = this.L;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    public void g() {
        Editable text = this.q.getText();
        if (text == null || TextUtils.getTrimmedLength(text) <= 0) {
            return;
        }
        if (this.ca != null) {
            a(0, (String) null, text.toString());
        }
        this.q.setImeVisibility(false);
        this.q.dismissDropDown();
    }

    public int getImeOptions() {
        return this.q.getImeOptions();
    }

    public int getInputType() {
        return this.q.getInputType();
    }

    public int getMaxWidth() {
        return this.T;
    }

    public CharSequence getQuery() {
        return this.q.getText();
    }

    public CharSequence getQueryHint() {
        CharSequence charSequence = this.Q;
        if (charSequence != null) {
            return charSequence;
        }
        SearchableInfo searchableInfo = this.ca;
        return (searchableInfo == null || searchableInfo.getHintId() == 0) ? this.J : getContext().getText(this.ca.getHintId());
    }

    public int getSuggestionCommitIconResId() {
        return this.G;
    }

    public int getSuggestionRowLayout() {
        return this.F;
    }

    public a.b.d.i.c getSuggestionsAdapter() {
        return this.O;
    }

    public void h() {
        b(c());
        post(this.ea);
        if (this.q.hasFocus()) {
            b();
        }
    }

    public void i() {
        SearchableInfo searchableInfo = this.ca;
        if (searchableInfo == null) {
            return;
        }
        try {
            if (!searchableInfo.getVoiceSearchLaunchWebSearch()) {
                if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                    getContext().startActivity(a(this.I, searchableInfo));
                }
            } else {
                Intent intent = new Intent(this.H);
                ComponentName searchActivity = searchableInfo.getSearchActivity();
                intent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
                getContext().startActivity(intent);
            }
        } catch (ActivityNotFoundException unused) {
            Log.w("SearchView", "Could not find voice search activity");
        }
    }

    public final void j() {
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(this.q.getText());
        if (!z2 && (!this.M || this.aa)) {
            z = false;
        }
        this.w.setVisibility(z ? 0 : 8);
        Drawable drawable = this.w.getDrawable();
        if (drawable != null) {
            drawable.setState(z2 ? ViewGroup.ENABLED_STATE_SET : ViewGroup.EMPTY_STATE_SET);
        }
    }

    public void k() {
        int[] iArr = this.q.hasFocus() ? ViewGroup.FOCUSED_STATE_SET : ViewGroup.EMPTY_STATE_SET;
        Drawable background = this.s.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.t.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void l() {
        CharSequence queryHint = getQueryHint();
        SearchAutoComplete searchAutoComplete = this.q;
        CharSequence charSequence = queryHint;
        if (queryHint == null) {
            charSequence = "";
        }
        CharSequence charSequence2 = charSequence;
        if (this.M) {
            charSequence2 = charSequence;
            if (this.E != null) {
                double textSize = this.q.getTextSize();
                Double.isNaN(textSize);
                Double.isNaN(textSize);
                int i = (int) (textSize * 1.25d);
                this.E.setBounds(0, 0, i, i);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
                spannableStringBuilder.setSpan(new ImageSpan(this.E), 1, 2, 33);
                spannableStringBuilder.append(charSequence);
                charSequence2 = spannableStringBuilder;
            }
        }
        searchAutoComplete.setHint(charSequence2);
    }

    public final void m() {
        this.t.setVisibility((d() && (this.v.getVisibility() == 0 || this.x.getVisibility() == 0)) ? 0 : 8);
    }

    @Override // a.b.e.e.a
    public void onActionViewCollapsed() {
        a("", false);
        clearFocus();
        b(true);
        this.q.setImeOptions(this.ba);
        this.aa = false;
    }

    @Override // a.b.e.e.a
    public void onActionViewExpanded() {
        if (this.aa) {
            return;
        }
        this.aa = true;
        this.ba = this.q.getImeOptions();
        this.q.setImeOptions(this.ba | 33554432);
        this.q.setText("");
        setIconified(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeCallbacks(this.ea);
        post(this.fa);
        super.onDetachedFromWindow();
    }

    @Override // a.b.e.f.E, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.d == 1) {
            b(i, i2, i3, i4);
        } else {
            a(i, i2, i3, i4);
        }
        if (z) {
            SearchAutoComplete searchAutoComplete = this.q;
            Rect rect = this.z;
            searchAutoComplete.getLocationInWindow(this.B);
            getLocationInWindow(this.C);
            int[] iArr = this.B;
            int i5 = iArr[1];
            int[] iArr2 = this.C;
            int i6 = i5 - iArr2[1];
            int i7 = iArr[0] - iArr2[0];
            rect.set(i7, i6, searchAutoComplete.getWidth() + i7, searchAutoComplete.getHeight() + i6);
            Rect rect2 = this.A;
            Rect rect3 = this.z;
            rect2.set(rect3.left, 0, rect3.right, i4 - i2);
            f fVar = this.mTouchDelegate;
            if (fVar != null) {
                fVar.a(this.A, this.z);
            } else {
                this.mTouchDelegate = new f(this.A, this.z, this.q);
                setTouchDelegate(this.mTouchDelegate);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x004c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:30:0x004f  */
    /* JADX WARN: Code duplicated, block: B:31:0x0054  */
    @Override // a.b.e.f.E, android.view.View
    public void onMeasure(int i, int i2) {
        int preferredWidth;
        int mode;
        int size;
        if (c()) {
            if (this.d == 1) {
                d(i, i2);
                return;
            } else {
                c(i, i2);
                return;
            }
        }
        int mode2 = View.MeasureSpec.getMode(i);
        int size2 = View.MeasureSpec.getSize(i);
        if (mode2 != Integer.MIN_VALUE) {
            if (mode2 == 0) {
                size2 = this.T;
                if (size2 <= 0) {
                    size2 = getPreferredWidth();
                }
            } else if (mode2 == 1073741824 && (preferredWidth = this.T) > 0) {
            }
            mode = View.MeasureSpec.getMode(i2);
            size = View.MeasureSpec.getSize(i2);
            if (mode != Integer.MIN_VALUE) {
                size = Math.min(getPreferredHeight(), size);
            } else if (mode == 0) {
                size = getPreferredHeight();
            }
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(size2, 1073741824), View.MeasureSpec.makeMeasureSpec(size, 1073741824));
        }
        preferredWidth = this.T;
        if (preferredWidth <= 0) {
            preferredWidth = getPreferredWidth();
        }
        size2 = Math.min(preferredWidth, size2);
        mode = View.MeasureSpec.getMode(i2);
        size = View.MeasureSpec.getSize(i2);
        if (mode != Integer.MIN_VALUE) {
            size = Math.min(getPreferredHeight(), size);
        } else if (mode == 0) {
            size = getPreferredHeight();
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size2, 1073741824), View.MeasureSpec.makeMeasureSpec(size, 1073741824));
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof e)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        e eVar = (e) parcelable;
        super.onRestoreInstanceState(eVar.f157b);
        b(eVar.f425a);
        requestLayout();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        e eVar = new e(super.onSaveInstanceState());
        eVar.f425a = c();
        return eVar;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        post(this.ea);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean requestFocus(int i, Rect rect) {
        if (this.S || !isFocusable()) {
            return false;
        }
        if (c()) {
            return super.requestFocus(i, rect);
        }
        boolean zRequestFocus = this.q.requestFocus(i, rect);
        if (zRequestFocus) {
            b(false);
        }
        return zRequestFocus;
    }

    public void setAppSearchData(Bundle bundle) {
        this.da = bundle;
    }

    public void setIconified(boolean z) {
        if (z) {
            e();
        } else {
            f();
        }
    }

    public void setIconifiedByDefault(boolean z) {
        if (this.M == z) {
            return;
        }
        this.M = z;
        b(z);
        l();
    }

    public void setImeOptions(int i) {
        this.q.setImeOptions(i);
    }

    public void setInputType(int i) {
        this.q.setInputType(i);
    }

    public void setMaxWidth(int i) {
        this.T = i;
        requestLayout();
    }

    public void setOnCloseListener(b bVar) {
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.K = onFocusChangeListener;
    }

    public void setOnQueryTextListener(c cVar) {
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.L = onClickListener;
    }

    public void setOnSuggestionListener(d dVar) {
    }

    public void setQueryHint(CharSequence charSequence) {
        this.Q = charSequence;
        l();
    }

    public void setQueryRefinementEnabled(boolean z) {
        this.R = z;
        a.b.d.i.c cVar = this.O;
        if (cVar instanceof ba) {
            ((ba) cVar).a(z ? 2 : 1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00a4  */
    public void setSearchableInfo(SearchableInfo searchableInfo) {
        boolean z;
        this.ca = searchableInfo;
        SearchableInfo searchableInfo2 = this.ca;
        Intent intent = null;
        if (searchableInfo2 != null) {
            this.q.setThreshold(searchableInfo2.getSuggestThreshold());
            this.q.setImeOptions(this.ca.getImeOptions());
            int inputType = this.ca.getInputType();
            if ((inputType & 15) == 1) {
                inputType &= -65537;
                if (this.ca.getSuggestAuthority() != null) {
                    inputType = inputType | 65536 | 524288;
                }
            }
            this.q.setInputType(inputType);
            a.b.d.i.c cVar = this.O;
            if (cVar != null) {
                cVar.changeCursor(null);
            }
            if (this.ca.getSuggestAuthority() != null) {
                this.O = new ba(getContext(), this, this.ca, this.ga);
                this.q.setAdapter(this.O);
                ((ba) this.O).a(this.R ? 2 : 1);
            }
            l();
        }
        SearchableInfo searchableInfo3 = this.ca;
        if (searchableInfo3 != null && searchableInfo3.getVoiceSearchEnabled()) {
            if (this.ca.getVoiceSearchLaunchWebSearch()) {
                intent = this.H;
            } else if (this.ca.getVoiceSearchLaunchRecognizer()) {
                intent = this.I;
            }
            z = (intent == null || getContext().getPackageManager().resolveActivity(intent, 65536) == null) ? false : true;
        }
        this.U = z;
        if (this.U) {
            this.q.setPrivateImeOptions("nm");
        }
        b(c());
    }

    public void setSubmitButtonEnabled(boolean z) {
        this.P = z;
        b(c());
    }

    public void setSuggestionsAdapter(a.b.d.i.c cVar) {
        this.O = cVar;
        this.q.setAdapter(this.O);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.z = new Rect();
        this.A = new Rect();
        this.B = new int[2];
        this.C = new int[2];
        this.ea = new P(this);
        this.fa = new Q(this);
        this.ga = new WeakHashMap<>();
        this.ha = new U(this);
        this.ia = new V(this);
        this.ja = new W(this);
        this.ka = new X(this);
        this.la = new Y(this);
        this.ma = new O(this);
        ha haVarA = ha.a(context, attributeSet, j.SearchView, i, 0);
        LayoutInflater.from(context).inflate(haVarA.f(j.SearchView_layout, g.abc_search_view), (ViewGroup) this, true);
        this.q = (SearchAutoComplete) findViewById(a.b.e.b.f.search_src_text);
        this.q.setSearchView(this);
        this.r = findViewById(a.b.e.b.f.search_edit_frame);
        this.s = findViewById(a.b.e.b.f.search_plate);
        this.t = findViewById(a.b.e.b.f.submit_area);
        this.u = (ImageView) findViewById(a.b.e.b.f.search_button);
        this.v = (ImageView) findViewById(a.b.e.b.f.search_go_btn);
        this.w = (ImageView) findViewById(a.b.e.b.f.search_close_btn);
        this.x = (ImageView) findViewById(a.b.e.b.f.search_voice_btn);
        this.D = (ImageView) findViewById(a.b.e.b.f.search_mag_icon);
        n.a(this.s, haVarA.b(j.SearchView_queryBackground));
        n.a(this.t, haVarA.b(j.SearchView_submitBackground));
        this.u.setImageDrawable(haVarA.b(j.SearchView_searchIcon));
        this.v.setImageDrawable(haVarA.b(j.SearchView_goIcon));
        this.w.setImageDrawable(haVarA.b(j.SearchView_closeIcon));
        this.x.setImageDrawable(haVarA.b(j.SearchView_voiceIcon));
        this.D.setImageDrawable(haVarA.b(j.SearchView_searchIcon));
        this.E = haVarA.b(j.SearchView_searchHintIcon);
        a.b.b.a.e.a(this.u, getResources().getString(h.abc_searchview_description_search));
        this.F = haVarA.f(j.SearchView_suggestionRowLayout, g.abc_search_dropdown_item_icons_2line);
        this.G = haVarA.f(j.SearchView_commitIcon, 0);
        this.u.setOnClickListener(this.ha);
        this.w.setOnClickListener(this.ha);
        this.v.setOnClickListener(this.ha);
        this.x.setOnClickListener(this.ha);
        this.q.setOnClickListener(this.ha);
        this.q.addTextChangedListener(this.ma);
        this.q.setOnEditorActionListener(this.ja);
        this.q.setOnItemClickListener(this.ka);
        this.q.setOnItemSelectedListener(this.la);
        this.q.setOnKeyListener(this.ia);
        this.q.setOnFocusChangeListener(new S(this));
        setIconifiedByDefault(haVarA.a(j.SearchView_iconifiedByDefault, true));
        int iC = haVarA.c(j.SearchView_android_maxWidth, -1);
        if (iC != -1) {
            setMaxWidth(iC);
        }
        this.J = haVarA.d(j.SearchView_defaultQueryHint);
        this.Q = haVarA.d(j.SearchView_queryHint);
        int iD = haVarA.d(j.SearchView_android_imeOptions, -1);
        if (iD != -1) {
            setImeOptions(iD);
        }
        int iD2 = haVarA.d(j.SearchView_android_inputType, -1);
        if (iD2 != -1) {
            setInputType(iD2);
        }
        setFocusable(haVarA.a(j.SearchView_android_focusable, true));
        haVarA.f337b.recycle();
        this.H = new Intent("android.speech.action.WEB_SEARCH");
        this.H.addFlags(268435456);
        this.H.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.I = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.I.addFlags(268435456);
        this.y = findViewById(this.q.getDropDownAnchor());
        View view = this.y;
        if (view != null) {
            view.addOnLayoutChangeListener(new T(this));
        }
        b(this.M);
        l();
    }

    public boolean a(View view, int i, KeyEvent keyEvent) {
        if (this.ca != null && this.O != null && keyEvent.getAction() == 0 && keyEvent.hasNoModifiers()) {
            if (i == 66 || i == 84 || i == 61) {
                return a(this.q.getListSelection(), 0, (String) null);
            }
            if (i == 21 || i == 22) {
                this.q.setSelection(i == 21 ? 0 : this.q.length());
                this.q.setListSelection(0);
                this.q.clearListSelection();
                a aVar = p;
                SearchAutoComplete searchAutoComplete = this.q;
                Method method = aVar.f424c;
                if (method != null) {
                    try {
                        method.invoke(searchAutoComplete, true);
                    } catch (Exception unused) {
                    }
                }
                return true;
            }
            if (i != 19 || this.q.getListSelection() == 0) {
                return false;
            }
        }
        return false;
    }
}
