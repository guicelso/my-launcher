package android.support.v4.app;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TabHost;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
    private final ArrayList jj;
    private TabHost.OnTabChangeListener kj;
    private U lj;
    private int mContainerId;
    private Context mContext;
    private AbstractC0023y mFragmentManager;
    private boolean mj;

    class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator CREATOR = new T();
        String Uf;

        SavedState(Parcel parcel) {
            super(parcel);
            this.Uf = parcel.readString();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder sbE = b.a.a.a.a.e("FragmentTabHost.SavedState{");
            sbE.append(Integer.toHexString(System.identityHashCode(this)));
            sbE.append(" curTab=");
            sbE.append(this.Uf);
            sbE.append("}");
            return sbE.toString();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.Uf);
        }
    }

    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.jj = new ArrayList();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{R.attr.inflatedId}, 0, 0);
        this.mContainerId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        super.setOnTabChangedListener(this);
    }

    private V a(String str, V v) {
        U u;
        ComponentCallbacksC0013n componentCallbacksC0013n;
        int size = this.jj.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                u = null;
                break;
            }
            u = (U) this.jj.get(i);
            if (u.tag.equals(str)) {
                break;
            }
            i++;
        }
        if (this.lj != u) {
            if (v == null) {
                v = this.mFragmentManager.beginTransaction();
            }
            U u2 = this.lj;
            if (u2 != null && (componentCallbacksC0013n = u2.fragment) != null) {
                ((C0004e) v).a(new C0003d(6, componentCallbacksC0013n));
            }
            if (u != null) {
                ComponentCallbacksC0013n componentCallbacksC0013n2 = u.fragment;
                if (componentCallbacksC0013n2 == null) {
                    u.fragment = ComponentCallbacksC0013n.instantiate(this.mContext, u.Ad.getName(), u.args);
                    int i2 = this.mContainerId;
                    ComponentCallbacksC0013n componentCallbacksC0013n3 = u.fragment;
                    String str2 = u.tag;
                    C0004e c0004e = (C0004e) v;
                    Class<?> cls = componentCallbacksC0013n3.getClass();
                    int modifiers = cls.getModifiers();
                    if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
                        StringBuilder sbE = b.a.a.a.a.e("Fragment ");
                        sbE.append(cls.getCanonicalName());
                        sbE.append(" must be a public static class to be  properly recreated from");
                        sbE.append(" instance state.");
                        throw new IllegalStateException(sbE.toString());
                    }
                    componentCallbacksC0013n3.mFragmentManager = c0004e.mManager;
                    if (str2 != null) {
                        String str3 = componentCallbacksC0013n3.mTag;
                        if (str3 != null && !str2.equals(str3)) {
                            throw new IllegalStateException("Can't change tag of fragment " + componentCallbacksC0013n3 + ": was " + componentCallbacksC0013n3.mTag + " now " + str2);
                        }
                        componentCallbacksC0013n3.mTag = str2;
                    }
                    if (i2 != 0) {
                        if (i2 == -1) {
                            throw new IllegalArgumentException("Can't add fragment " + componentCallbacksC0013n3 + " with tag " + str2 + " to container view with no id");
                        }
                        int i3 = componentCallbacksC0013n3.mFragmentId;
                        if (i3 != 0 && i3 != i2) {
                            throw new IllegalStateException("Can't change container ID of fragment " + componentCallbacksC0013n3 + ": was " + componentCallbacksC0013n3.mFragmentId + " now " + i2);
                        }
                        componentCallbacksC0013n3.mFragmentId = i2;
                        componentCallbacksC0013n3.mContainerId = i2;
                    }
                    c0004e.a(new C0003d(1, componentCallbacksC0013n3));
                } else {
                    ((C0004e) v).a(new C0003d(7, componentCallbacksC0013n2));
                }
            }
            this.lj = u;
        }
        return v;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTabTag = getCurrentTabTag();
        int size = this.jj.size();
        V vBeginTransaction = null;
        for (int i = 0; i < size; i++) {
            U u = (U) this.jj.get(i);
            u.fragment = this.mFragmentManager.findFragmentByTag(u.tag);
            ComponentCallbacksC0013n componentCallbacksC0013n = u.fragment;
            if (componentCallbacksC0013n != null && !componentCallbacksC0013n.mDetached) {
                if (u.tag.equals(currentTabTag)) {
                    this.lj = u;
                } else {
                    if (vBeginTransaction == null) {
                        vBeginTransaction = this.mFragmentManager.beginTransaction();
                    }
                    ((C0004e) vBeginTransaction).a(new C0003d(6, u.fragment));
                }
            }
        }
        this.mj = true;
        V vA = a(currentTabTag, vBeginTransaction);
        if (vA != null) {
            vA.commit();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mj = false;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentTabByTag(savedState.Uf);
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.Uf = getCurrentTabTag();
        return savedState;
    }

    @Override // android.widget.TabHost.OnTabChangeListener
    public void onTabChanged(String str) {
        V vA;
        if (this.mj && (vA = a(str, null)) != null) {
            vA.commit();
        }
        TabHost.OnTabChangeListener onTabChangeListener = this.kj;
        if (onTabChangeListener != null) {
            onTabChangeListener.onTabChanged(str);
        }
    }

    @Override // android.widget.TabHost
    public void setOnTabChangedListener(TabHost.OnTabChangeListener onTabChangeListener) {
        this.kj = onTabChangeListener;
    }

    @Override // android.widget.TabHost
    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }
}
