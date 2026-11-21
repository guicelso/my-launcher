package a.b.e.f;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

/* JADX INFO: renamed from: a.b.e.f.q, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0026q extends Spinner implements a.b.d.h.m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int[] f365a = {R.attr.spinnerMode};

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0018i f366b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final boolean f367c;
    public int mDropDownWidth;
    public D mForwardingListener;
    public b mPopup;
    public final Context mPopupContext;
    public SpinnerAdapter mTempAdapter;
    public final Rect mTempRect;

    /* JADX INFO: renamed from: a.b.e.f.q$a */
    private static class a implements ListAdapter, SpinnerAdapter {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public SpinnerAdapter f368a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public ListAdapter f369b;

        public a(SpinnerAdapter spinnerAdapter, Resources.Theme theme) {
            this.f368a = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.f369b = (ListAdapter) spinnerAdapter;
            }
            if (theme != null) {
                if (Build.VERSION.SDK_INT >= 23 && (spinnerAdapter instanceof ThemedSpinnerAdapter)) {
                    ThemedSpinnerAdapter themedSpinnerAdapter = (ThemedSpinnerAdapter) spinnerAdapter;
                    if (themedSpinnerAdapter.getDropDownViewTheme() != theme) {
                        themedSpinnerAdapter.setDropDownViewTheme(theme);
                        return;
                    }
                    return;
                }
                if (spinnerAdapter instanceof da) {
                    da daVar = (da) spinnerAdapter;
                    if (daVar.getDropDownViewTheme() == null) {
                        daVar.setDropDownViewTheme(theme);
                    }
                }
            }
        }

        @Override // android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.f369b;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            SpinnerAdapter spinnerAdapter = this.f368a;
            if (spinnerAdapter == null) {
                return 0;
            }
            return spinnerAdapter.getCount();
        }

        @Override // android.widget.SpinnerAdapter
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.f368a;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(i, view, viewGroup);
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            SpinnerAdapter spinnerAdapter = this.f368a;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getItem(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            SpinnerAdapter spinnerAdapter = this.f368a;
            if (spinnerAdapter == null) {
                return -1L;
            }
            return spinnerAdapter.getItemId(i);
        }

        @Override // android.widget.Adapter
        public int getItemViewType(int i) {
            return 0;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.f368a;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(i, view, viewGroup);
        }

        @Override // android.widget.Adapter
        public int getViewTypeCount() {
            return 1;
        }

        @Override // android.widget.Adapter
        public boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.f368a;
            return spinnerAdapter != null && spinnerAdapter.hasStableIds();
        }

        @Override // android.widget.Adapter
        public boolean isEmpty() {
            SpinnerAdapter spinnerAdapter = this.f368a;
            return (spinnerAdapter == null ? 0 : spinnerAdapter.getCount()) == 0;
        }

        @Override // android.widget.ListAdapter
        public boolean isEnabled(int i) {
            ListAdapter listAdapter = this.f369b;
            if (listAdapter != null) {
                return listAdapter.isEnabled(i);
            }
            return true;
        }

        @Override // android.widget.Adapter
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.f368a;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        @Override // android.widget.Adapter
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.f368a;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a.b.e.f.q$b */
    class b extends H {
        public CharSequence I;
        public ListAdapter J;
        public final Rect K;

        public b(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i, 0);
            this.K = new Rect();
            this.v = C0026q.this;
            a(true);
            this.t = 0;
            this.x = new r(this, C0026q.this);
        }

        public void a(CharSequence charSequence) {
            this.I = charSequence;
        }

        public boolean a(View view) {
            return a.b.d.h.n.f(view) && view.getGlobalVisibleRect(this.K);
        }

        /* JADX WARN: Code duplicated, block: B:23:0x008d  */
        /* JADX WARN: Code duplicated, block: B:24:0x0093  */
        public void c() {
            int iMax;
            int i;
            Drawable drawableB = b();
            int i2 = 0;
            if (drawableB != null) {
                drawableB.getPadding(C0026q.this.mTempRect);
                i2 = ta.a(C0026q.this) ? C0026q.this.mTempRect.right : -C0026q.this.mTempRect.left;
            } else {
                Rect rect = C0026q.this.mTempRect;
                rect.right = 0;
                rect.left = 0;
            }
            int paddingLeft = C0026q.this.getPaddingLeft();
            int paddingRight = C0026q.this.getPaddingRight();
            int width = C0026q.this.getWidth();
            C0026q c0026q = C0026q.this;
            int i3 = c0026q.mDropDownWidth;
            if (i3 != -2) {
                if (i3 == -1) {
                    iMax = (width - paddingLeft) - paddingRight;
                } else {
                    a(i3);
                }
                if (ta.a(C0026q.this)) {
                    i = ((width - paddingRight) - this.h) + i2;
                } else {
                    i = i2 + paddingLeft;
                }
                this.i = i;
            }
            int iA = c0026q.a((SpinnerAdapter) this.J, b());
            int i4 = C0026q.this.getContext().getResources().getDisplayMetrics().widthPixels;
            Rect rect2 = C0026q.this.mTempRect;
            int i5 = (i4 - rect2.left) - rect2.right;
            if (iA > i5) {
                iA = i5;
            }
            iMax = Math.max(iA, (width - paddingLeft) - paddingRight);
            a(iMax);
            if (ta.a(C0026q.this)) {
                i = ((width - paddingRight) - this.h) + i2;
            } else {
                i = i2 + paddingLeft;
            }
            this.i = i;
        }

        @Override // a.b.e.f.H, a.b.e.e.a.w
        public void show() {
            ViewTreeObserver viewTreeObserver;
            boolean zIsShowing = isShowing();
            c();
            this.H.setInputMethodMode(2);
            super.show();
            this.f.setChoiceMode(1);
            int selectedItemPosition = C0026q.this.getSelectedItemPosition();
            B b2 = this.f;
            if (isShowing() && b2 != null) {
                b2.setListSelectionHidden(false);
                b2.setSelection(selectedItemPosition);
                if (b2.getChoiceMode() != 0) {
                    b2.setItemChecked(selectedItemPosition, true);
                }
            }
            if (zIsShowing || (viewTreeObserver = C0026q.this.getViewTreeObserver()) == null) {
                return;
            }
            ViewTreeObserverOnGlobalLayoutListenerC0027s viewTreeObserverOnGlobalLayoutListenerC0027s = new ViewTreeObserverOnGlobalLayoutListenerC0027s(this);
            viewTreeObserver.addOnGlobalLayoutListener(viewTreeObserverOnGlobalLayoutListenerC0027s);
            this.H.setOnDismissListener(new C0028t(this, viewTreeObserverOnGlobalLayoutListenerC0027s));
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0063  */
    /* JADX WARN: Code duplicated, block: B:32:0x009e  */
    /* JADX WARN: Code duplicated, block: B:36:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:39:0x00c7  */
    public C0026q(Context context, AttributeSet attributeSet, int i) throws Throwable {
        CharSequence[] textArray;
        SpinnerAdapter spinnerAdapter;
        Exception e;
        TypedArray typedArrayObtainStyledAttributes;
        super(context, attributeSet, i);
        this.mTempRect = new Rect();
        ha haVarA = ha.a(context, attributeSet, a.b.e.b.j.Spinner, i, 0);
        this.f366b = new C0018i(this);
        TypedArray typedArray = null;
        int iF = haVarA.f(a.b.e.b.j.Spinner_popupTheme, 0);
        if (iF != 0) {
            this.mPopupContext = new a.b.e.e.b(context, iF);
        } else {
            this.mPopupContext = Build.VERSION.SDK_INT < 23 ? context : null;
        }
        if (this.mPopupContext != null) {
            int i2 = -1;
            try {
                typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f365a, i, 0);
                try {
                    try {
                        if (typedArrayObtainStyledAttributes.hasValue(0)) {
                            i2 = typedArrayObtainStyledAttributes.getInt(0, 0);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        Log.i("AppCompatSpinner", "Could not read android:spinnerMode", e);
                        if (typedArrayObtainStyledAttributes != null) {
                        }
                        if (i2 == 1) {
                            b bVar = new b(this.mPopupContext, attributeSet, i);
                            ha haVarA2 = ha.a(this.mPopupContext, attributeSet, a.b.e.b.j.Spinner, i, 0);
                            this.mDropDownWidth = haVarA2.e(a.b.e.b.j.Spinner_android_dropDownWidth, -2);
                            bVar.a(haVarA2.b(a.b.e.b.j.Spinner_android_popupBackground));
                            bVar.a(haVarA.c(a.b.e.b.j.Spinner_android_prompt));
                            haVarA2.f337b.recycle();
                            this.mPopup = bVar;
                            this.mForwardingListener = new C0025p(this, this, bVar);
                        }
                        textArray = haVarA.f337b.getTextArray(a.b.e.b.j.Spinner_android_entries);
                        if (textArray != null) {
                            ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.simple_spinner_item, textArray);
                            arrayAdapter.setDropDownViewResource(a.b.e.b.g.support_simple_spinner_dropdown_item);
                            setAdapter((SpinnerAdapter) arrayAdapter);
                        }
                        haVarA.f337b.recycle();
                        this.f367c = true;
                        spinnerAdapter = this.mTempAdapter;
                        if (spinnerAdapter != null) {
                            setAdapter(spinnerAdapter);
                            this.mTempAdapter = null;
                        }
                        this.f366b.a(attributeSet, i);
                    }
                } catch (Throwable th) {
                    th = th;
                    typedArray = typedArrayObtainStyledAttributes;
                    if (typedArray != null) {
                        typedArray.recycle();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                typedArrayObtainStyledAttributes = null;
            } catch (Throwable th2) {
                th = th2;
                if (typedArray != null) {
                    typedArray.recycle();
                }
                throw th;
            }
            typedArrayObtainStyledAttributes.recycle();
            if (i2 == 1) {
                b bVar2 = new b(this.mPopupContext, attributeSet, i);
                ha haVarA3 = ha.a(this.mPopupContext, attributeSet, a.b.e.b.j.Spinner, i, 0);
                this.mDropDownWidth = haVarA3.e(a.b.e.b.j.Spinner_android_dropDownWidth, -2);
                bVar2.a(haVarA3.b(a.b.e.b.j.Spinner_android_popupBackground));
                bVar2.a(haVarA.c(a.b.e.b.j.Spinner_android_prompt));
                haVarA3.f337b.recycle();
                this.mPopup = bVar2;
                this.mForwardingListener = new C0025p(this, this, bVar2);
            }
        }
        textArray = haVarA.f337b.getTextArray(a.b.e.b.j.Spinner_android_entries);
        if (textArray != null) {
            ArrayAdapter arrayAdapter2 = new ArrayAdapter(context, R.layout.simple_spinner_item, textArray);
            arrayAdapter2.setDropDownViewResource(a.b.e.b.g.support_simple_spinner_dropdown_item);
            setAdapter((SpinnerAdapter) arrayAdapter2);
        }
        haVarA.f337b.recycle();
        this.f367c = true;
        spinnerAdapter = this.mTempAdapter;
        if (spinnerAdapter != null) {
            setAdapter(spinnerAdapter);
            this.mTempAdapter = null;
        }
        this.f366b.a(attributeSet, i);
    }

    public int a(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        int i = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int iMax = Math.max(0, getSelectedItemPosition());
        int iMin = Math.min(spinnerAdapter.getCount(), iMax + 15);
        View view = null;
        int iMax2 = 0;
        for (int iMax3 = Math.max(0, iMax - (15 - (iMin - iMax))); iMax3 < iMin; iMax3++) {
            int itemViewType = spinnerAdapter.getItemViewType(iMax3);
            if (itemViewType != i) {
                view = null;
                i = itemViewType;
            }
            view = spinnerAdapter.getView(iMax3, view, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            iMax2 = Math.max(iMax2, view.getMeasuredWidth());
        }
        if (drawable == null) {
            return iMax2;
        }
        drawable.getPadding(this.mTempRect);
        Rect rect = this.mTempRect;
        return iMax2 + rect.left + rect.right;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0018i c0018i = this.f366b;
        if (c0018i != null) {
            c0018i.a();
        }
    }

    @Override // android.widget.Spinner
    public int getDropDownHorizontalOffset() {
        b bVar = this.mPopup;
        if (bVar != null) {
            return bVar.i;
        }
        int i = Build.VERSION.SDK_INT;
        return super.getDropDownHorizontalOffset();
    }

    @Override // android.widget.Spinner
    public int getDropDownVerticalOffset() {
        b bVar = this.mPopup;
        if (bVar == null) {
            int i = Build.VERSION.SDK_INT;
            return super.getDropDownVerticalOffset();
        }
        if (bVar.l) {
            return bVar.j;
        }
        return 0;
    }

    @Override // android.widget.Spinner
    public int getDropDownWidth() {
        if (this.mPopup != null) {
            return this.mDropDownWidth;
        }
        int i = Build.VERSION.SDK_INT;
        return super.getDropDownWidth();
    }

    @Override // android.widget.Spinner
    public Drawable getPopupBackground() {
        b bVar = this.mPopup;
        if (bVar != null) {
            return bVar.b();
        }
        int i = Build.VERSION.SDK_INT;
        return super.getPopupBackground();
    }

    @Override // android.widget.Spinner
    public Context getPopupContext() {
        if (this.mPopup != null) {
            return this.mPopupContext;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return super.getPopupContext();
        }
        return null;
    }

    @Override // android.widget.Spinner
    public CharSequence getPrompt() {
        b bVar = this.mPopup;
        return bVar != null ? bVar.I : super.getPrompt();
    }

    @Override // a.b.d.h.m
    public ColorStateList getSupportBackgroundTintList() {
        C0018i c0018i = this.f366b;
        if (c0018i != null) {
            return c0018i.b();
        }
        return null;
    }

    @Override // a.b.d.h.m
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0018i c0018i = this.f366b;
        if (c0018i != null) {
            return c0018i.c();
        }
        return null;
    }

    @Override // android.widget.Spinner, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b bVar = this.mPopup;
        if (bVar == null || !bVar.isShowing()) {
            return;
        }
        this.mPopup.dismiss();
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mPopup == null || View.MeasureSpec.getMode(i) != Integer.MIN_VALUE) {
            return;
        }
        setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), a(getAdapter(), getBackground())), View.MeasureSpec.getSize(i)), getMeasuredHeight());
    }

    @Override // android.widget.Spinner, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        D d = this.mForwardingListener;
        if (d == null || !d.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.widget.Spinner, android.view.View
    public boolean performClick() {
        b bVar = this.mPopup;
        if (bVar == null) {
            return super.performClick();
        }
        if (bVar.isShowing()) {
            return true;
        }
        this.mPopup.show();
        return true;
    }

    @Override // android.widget.AdapterView
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.f367c) {
            this.mTempAdapter = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        if (this.mPopup != null) {
            Context context = this.mPopupContext;
            if (context == null) {
                context = getContext();
            }
            b bVar = this.mPopup;
            a aVar = new a(spinnerAdapter, context.getTheme());
            DataSetObserver dataSetObserver = bVar.u;
            if (dataSetObserver == null) {
                bVar.u = new H.b();
            } else {
                ListAdapter listAdapter = bVar.e;
                if (listAdapter != null) {
                    listAdapter.unregisterDataSetObserver(dataSetObserver);
                }
            }
            bVar.e = aVar;
            aVar.registerDataSetObserver(bVar.u);
            B b2 = bVar.f;
            if (b2 != null) {
                b2.setAdapter(bVar.e);
            }
            bVar.J = aVar;
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0018i c0018i = this.f366b;
        if (c0018i != null) {
            c0018i.f341c = -1;
            c0018i.a((ColorStateList) null);
            c0018i.a();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0018i c0018i = this.f366b;
        if (c0018i != null) {
            c0018i.a(i);
        }
    }

    @Override // android.widget.Spinner
    public void setDropDownHorizontalOffset(int i) {
        b bVar = this.mPopup;
        if (bVar != null) {
            bVar.i = i;
        } else {
            int i2 = Build.VERSION.SDK_INT;
            super.setDropDownHorizontalOffset(i);
        }
    }

    @Override // android.widget.Spinner
    public void setDropDownVerticalOffset(int i) {
        b bVar = this.mPopup;
        if (bVar != null) {
            bVar.j = i;
            bVar.l = true;
        } else {
            int i2 = Build.VERSION.SDK_INT;
            super.setDropDownVerticalOffset(i);
        }
    }

    @Override // android.widget.Spinner
    public void setDropDownWidth(int i) {
        if (this.mPopup != null) {
            this.mDropDownWidth = i;
        } else {
            int i2 = Build.VERSION.SDK_INT;
            super.setDropDownWidth(i);
        }
    }

    @Override // android.widget.Spinner
    public void setPopupBackgroundDrawable(Drawable drawable) {
        b bVar = this.mPopup;
        if (bVar != null) {
            bVar.H.setBackgroundDrawable(drawable);
        } else {
            int i = Build.VERSION.SDK_INT;
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Spinner
    public void setPopupBackgroundResource(int i) {
        setPopupBackgroundDrawable(a.b.e.c.a.a.c(getPopupContext(), i));
    }

    @Override // android.widget.Spinner
    public void setPrompt(CharSequence charSequence) {
        b bVar = this.mPopup;
        if (bVar != null) {
            bVar.I = charSequence;
        } else {
            super.setPrompt(charSequence);
        }
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0018i c0018i = this.f366b;
        if (c0018i != null) {
            c0018i.b(colorStateList);
        }
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0018i c0018i = this.f366b;
        if (c0018i != null) {
            c0018i.a(mode);
        }
    }
}
