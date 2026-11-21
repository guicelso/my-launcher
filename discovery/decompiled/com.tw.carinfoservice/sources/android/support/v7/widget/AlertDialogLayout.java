package android.support.v7.widget;

import a.b.d.h.j;
import a.b.e.f.t;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import com.tw.carinfoservice.R;

/* JADX INFO: loaded from: classes.dex */
public class AlertDialogLayout extends t {
    public AlertDialogLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static int o(View view) {
        int i = j.f151a;
        int minimumHeight = view.getMinimumHeight();
        if (minimumHeight > 0) {
            return minimumHeight;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() == 1) {
                return o(viewGroup.getChildAt(0));
            }
        }
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x009d  */
    @Override // a.b.e.f.t, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int paddingLeft = getPaddingLeft();
        int i7 = i3 - i;
        int paddingRight = i7 - getPaddingRight();
        int paddingRight2 = (i7 - paddingLeft) - getPaddingRight();
        int measuredHeight = getMeasuredHeight();
        int childCount = getChildCount();
        int gravity = getGravity();
        int i8 = gravity & 112;
        int i9 = gravity & 8388615;
        int paddingTop = i8 != 16 ? i8 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - measuredHeight : (((i4 - i2) - measuredHeight) / 2) + getPaddingTop();
        Drawable dividerDrawable = getDividerDrawable();
        int intrinsicHeight = dividerDrawable == null ? 0 : dividerDrawable.getIntrinsicHeight();
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (childAt != null && childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                t.a aVar = (t.a) childAt.getLayoutParams();
                int i11 = aVar.f324b;
                if (i11 < 0) {
                    i11 = i9;
                }
                int i12 = j.f151a;
                int absoluteGravity = Gravity.getAbsoluteGravity(i11, getLayoutDirection()) & 7;
                if (absoluteGravity != 1) {
                    if (absoluteGravity != 5) {
                        i6 = ((ViewGroup.MarginLayoutParams) aVar).leftMargin + paddingLeft;
                    } else {
                        i5 = paddingRight - measuredWidth;
                    }
                    if (l(i10)) {
                        paddingTop += intrinsicHeight;
                    }
                    int i13 = paddingTop + ((ViewGroup.MarginLayoutParams) aVar).topMargin;
                    childAt.layout(i6, i13, measuredWidth + i6, measuredHeight2 + i13);
                    paddingTop = measuredHeight2 + ((ViewGroup.MarginLayoutParams) aVar).bottomMargin + i13;
                } else {
                    i5 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft + ((ViewGroup.MarginLayoutParams) aVar).leftMargin;
                }
                i6 = i5 - ((ViewGroup.MarginLayoutParams) aVar).rightMargin;
                if (l(i10)) {
                    paddingTop += intrinsicHeight;
                }
                int i14 = paddingTop + ((ViewGroup.MarginLayoutParams) aVar).topMargin;
                childAt.layout(i6, i14, measuredWidth + i6, measuredHeight2 + i14);
                paddingTop = measuredHeight2 + ((ViewGroup.MarginLayoutParams) aVar).bottomMargin + i14;
            }
        }
    }

    @Override // a.b.e.f.t, android.view.View
    public void onMeasure(int i, int i2) {
        int iCombineMeasuredStates;
        int iO;
        int measuredHeight;
        int measuredHeight2;
        int childCount = getChildCount();
        View view = null;
        boolean z = false;
        View view2 = null;
        View view3 = null;
        int i3 = 0;
        while (true) {
            if (i3 >= childCount) {
                int mode = View.MeasureSpec.getMode(i2);
                int size = View.MeasureSpec.getSize(i2);
                int mode2 = View.MeasureSpec.getMode(i);
                int paddingBottom = getPaddingBottom() + getPaddingTop();
                if (view != null) {
                    view.measure(i, 0);
                    paddingBottom += view.getMeasuredHeight();
                    iCombineMeasuredStates = View.combineMeasuredStates(0, view.getMeasuredState());
                } else {
                    iCombineMeasuredStates = 0;
                }
                if (view2 != null) {
                    view2.measure(i, 0);
                    iO = o(view2);
                    measuredHeight = view2.getMeasuredHeight() - iO;
                    paddingBottom += iO;
                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
                } else {
                    iO = 0;
                    measuredHeight = 0;
                }
                if (view3 != null) {
                    view3.measure(i, mode == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingBottom), mode));
                    measuredHeight2 = view3.getMeasuredHeight();
                    paddingBottom += measuredHeight2;
                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view3.getMeasuredState());
                } else {
                    measuredHeight2 = 0;
                }
                int i4 = size - paddingBottom;
                if (view2 != null) {
                    int i5 = paddingBottom - iO;
                    int iMin = Math.min(i4, measuredHeight);
                    if (iMin > 0) {
                        i4 -= iMin;
                        iO += iMin;
                    }
                    view2.measure(i, View.MeasureSpec.makeMeasureSpec(iO, 1073741824));
                    paddingBottom = i5 + view2.getMeasuredHeight();
                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
                }
                if (view3 != null && i4 > 0) {
                    view3.measure(i, View.MeasureSpec.makeMeasureSpec(measuredHeight2 + i4, mode));
                    paddingBottom = (paddingBottom - measuredHeight2) + view3.getMeasuredHeight();
                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view3.getMeasuredState());
                }
                int iMax = 0;
                for (int i6 = 0; i6 < childCount; i6++) {
                    View childAt = getChildAt(i6);
                    if (childAt.getVisibility() != 8) {
                        iMax = Math.max(iMax, childAt.getMeasuredWidth());
                    }
                }
                setMeasuredDimension(View.resolveSizeAndState(getPaddingRight() + getPaddingLeft() + iMax, i, iCombineMeasuredStates), View.resolveSizeAndState(paddingBottom, i2, 0));
                if (mode2 != 1073741824) {
                    int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
                    for (int i7 = 0; i7 < childCount; i7++) {
                        View childAt2 = getChildAt(i7);
                        if (childAt2.getVisibility() != 8) {
                            t.a aVar = (t.a) childAt2.getLayoutParams();
                            if (((ViewGroup.MarginLayoutParams) aVar).width == -1) {
                                int i8 = ((ViewGroup.MarginLayoutParams) aVar).height;
                                ((ViewGroup.MarginLayoutParams) aVar).height = childAt2.getMeasuredHeight();
                                measureChildWithMargins(childAt2, iMakeMeasureSpec, 0, i2, 0);
                                ((ViewGroup.MarginLayoutParams) aVar).height = i8;
                            }
                        }
                    }
                }
                z = true;
                break;
            }
            View childAt3 = getChildAt(i3);
            if (childAt3.getVisibility() != 8) {
                int id = childAt3.getId();
                if (id == R.id.topPanel) {
                    view = childAt3;
                } else if (id == R.id.buttonPanel) {
                    view2 = childAt3;
                } else if ((id != R.id.contentPanel && id != R.id.customPanel) || view3 != null) {
                    break;
                } else {
                    view3 = childAt3;
                }
            }
            i3++;
        }
        if (z) {
            return;
        }
        super.onMeasure(i, i2);
    }
}
