package android.support.v4.view;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

/* JADX INFO: loaded from: classes.dex */
public class f {
    private ViewParent Se;
    private ViewParent Te;
    private boolean Ue;
    private int[] Ve;
    private final View mView;

    public f(View view) {
        this.mView = view;
    }

    private ViewParent O(int i) {
        if (i == 0) {
            return this.Se;
        }
        if (i != 1) {
            return null;
        }
        return this.Te;
    }

    public boolean a(int i, int i2, int[] iArr, int[] iArr2, int i3) {
        ViewParent viewParentO;
        int i4;
        int i5;
        int[] iArr3;
        if (!this.Ue || (viewParentO = O(i3)) == null) {
            return false;
        }
        if (i == 0 && i2 == 0) {
            if (iArr2 != null) {
                iArr2[0] = 0;
                iArr2[1] = 0;
            }
            return false;
        }
        if (iArr2 != null) {
            this.mView.getLocationInWindow(iArr2);
            i4 = iArr2[0];
            i5 = iArr2[1];
        } else {
            i4 = 0;
            i5 = 0;
        }
        if (iArr == null) {
            if (this.Ve == null) {
                this.Ve = new int[2];
            }
            iArr3 = this.Ve;
        } else {
            iArr3 = iArr;
        }
        iArr3[0] = 0;
        iArr3[1] = 0;
        View view = this.mView;
        if (viewParentO instanceof g) {
            ((g) viewParentO).a(view, i, i2, iArr3, i3);
        } else if (i3 == 0) {
            int i6 = Build.VERSION.SDK_INT;
            try {
                viewParentO.onNestedPreScroll(view, i, i2, iArr3);
            } catch (AbstractMethodError e) {
                Log.e("ViewParentCompat", "ViewParent " + viewParentO + " does not implement interface method onNestedPreScroll", e);
            }
        }
        if (iArr2 != null) {
            this.mView.getLocationInWindow(iArr2);
            iArr2[0] = iArr2[0] - i4;
            iArr2[1] = iArr2[1] - i5;
        }
        return (iArr3[0] == 0 && iArr3[1] == 0) ? false : true;
    }

    public boolean b(int i, int i2) {
        boolean zOnStartNestedScroll;
        if (O(i2) != null) {
            return true;
        }
        if (this.Ue) {
            View view = this.mView;
            for (ViewParent parent = this.mView.getParent(); parent != null; parent = parent.getParent()) {
                View view2 = this.mView;
                boolean z = parent instanceof g;
                if (z) {
                    zOnStartNestedScroll = ((g) parent).b(view, view2, i, i2);
                } else if (i2 == 0) {
                    int i3 = Build.VERSION.SDK_INT;
                    try {
                        zOnStartNestedScroll = parent.onStartNestedScroll(view, view2, i);
                    } catch (AbstractMethodError e) {
                        Log.e("ViewParentCompat", "ViewParent " + parent + " does not implement interface method onStartNestedScroll", e);
                        zOnStartNestedScroll = false;
                    }
                } else {
                    zOnStartNestedScroll = false;
                }
                if (zOnStartNestedScroll) {
                    if (i2 == 0) {
                        this.Se = parent;
                    } else if (i2 == 1) {
                        this.Te = parent;
                    }
                    View view3 = this.mView;
                    if (z) {
                        ((g) parent).a(view, view3, i, i2);
                    } else if (i2 == 0) {
                        int i4 = Build.VERSION.SDK_INT;
                        try {
                            parent.onNestedScrollAccepted(view, view3, i);
                        } catch (AbstractMethodError e2) {
                            Log.e("ViewParentCompat", "ViewParent " + parent + " does not implement interface method onNestedScrollAccepted", e2);
                        }
                    }
                    return true;
                }
                if (parent instanceof View) {
                    view = (View) parent;
                }
            }
        }
        return false;
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        ViewParent viewParentO;
        if (!this.Ue || (viewParentO = O(0)) == null) {
            return false;
        }
        View view = this.mView;
        int i = Build.VERSION.SDK_INT;
        try {
            return viewParentO.onNestedFling(view, f, f2, z);
        } catch (AbstractMethodError e) {
            Log.e("ViewParentCompat", "ViewParent " + viewParentO + " does not implement interface method onNestedFling", e);
            return false;
        }
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        ViewParent viewParentO;
        if (!this.Ue || (viewParentO = O(0)) == null) {
            return false;
        }
        View view = this.mView;
        int i = Build.VERSION.SDK_INT;
        try {
            return viewParentO.onNestedPreFling(view, f, f2);
        } catch (AbstractMethodError e) {
            Log.e("ViewParentCompat", "ViewParent " + viewParentO + " does not implement interface method onNestedPreFling", e);
            return false;
        }
    }

    public boolean isNestedScrollingEnabled() {
        return this.Ue;
    }

    public boolean j(int i) {
        return O(i) != null;
    }

    public void k(int i) {
        ViewParent viewParentO = O(i);
        if (viewParentO != null) {
            View view = this.mView;
            if (viewParentO instanceof g) {
                ((g) viewParentO).a(view, i);
            } else if (i == 0) {
                int i2 = Build.VERSION.SDK_INT;
                try {
                    viewParentO.onStopNestedScroll(view);
                } catch (AbstractMethodError e) {
                    Log.e("ViewParentCompat", "ViewParent " + viewParentO + " does not implement interface method onStopNestedScroll", e);
                }
            }
            if (i == 0) {
                this.Se = null;
            } else {
                if (i != 1) {
                    return;
                }
                this.Te = null;
            }
        }
    }

    public void setNestedScrollingEnabled(boolean z) {
        if (this.Ue) {
            r.o(this.mView);
        }
        this.Ue = z;
    }

    public boolean a(int i, int i2, int i3, int i4, int[] iArr, int i5) {
        ViewParent viewParentO;
        int i6;
        int i7;
        if (!this.Ue || (viewParentO = O(i5)) == null) {
            return false;
        }
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
            }
            return false;
        }
        if (iArr != null) {
            this.mView.getLocationInWindow(iArr);
            i6 = iArr[0];
            i7 = iArr[1];
        } else {
            i6 = 0;
            i7 = 0;
        }
        View view = this.mView;
        if (viewParentO instanceof g) {
            ((g) viewParentO).a(view, i, i2, i3, i4, i5);
        } else if (i5 == 0) {
            int i8 = Build.VERSION.SDK_INT;
            try {
                viewParentO.onNestedScroll(view, i, i2, i3, i4);
            } catch (AbstractMethodError e) {
                Log.e("ViewParentCompat", "ViewParent " + viewParentO + " does not implement interface method onNestedScroll", e);
            }
        }
        if (iArr != null) {
            this.mView.getLocationInWindow(iArr);
            iArr[0] = iArr[0] - i6;
            iArr[1] = iArr[1] - i7;
        }
        return true;
    }
}
