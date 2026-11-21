package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes4.dex */
public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.LinearLayoutCompat, defStyleAttr, 0);
        int index = a.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (index >= 0) {
            setOrientation(index);
        }
        int index2 = a.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (index2 >= 0) {
            setGravity(index2);
        }
        boolean baselineAligned = a.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!baselineAligned) {
            setBaselineAligned(baselineAligned);
        }
        this.mWeightSum = a.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = a.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = a.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = a.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = a.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        a.recycle();
    }

    public void setShowDividers(int showDividers) {
        if (showDividers != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = showDividers;
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable divider) {
        if (divider == this.mDivider) {
            return;
        }
        this.mDivider = divider;
        if (divider != null) {
            this.mDividerWidth = divider.getIntrinsicWidth();
            this.mDividerHeight = divider.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(divider == null);
        requestLayout();
    }

    public void setDividerPadding(int padding) {
        this.mDividerPadding = padding;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        int bottom;
        int count = getVirtualChildCount();
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int top = (child.getTop() - lp.topMargin) - this.mDividerHeight;
                drawHorizontalDivider(canvas, top);
            }
        }
        if (hasDividerBeforeChildAt(count)) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                bottom = child2.getBottom() + lp2.bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        int position;
        int position2;
        int count = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position2 = child.getRight() + lp.rightMargin;
                } else {
                    int position3 = child.getLeft();
                    position2 = (position3 - lp.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, position2);
            }
        }
        if (hasDividerBeforeChildAt(count)) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 == null) {
                if (isLayoutRtl) {
                    position = getPaddingLeft();
                } else {
                    int position4 = getWidth();
                    position = (position4 - getPaddingRight()) - this.mDividerWidth;
                }
            } else {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                if (isLayoutRtl) {
                    position = (child2.getLeft() - lp2.leftMargin) - this.mDividerWidth;
                } else {
                    int position5 = child2.getRight();
                    position = position5 + lp2.rightMargin;
                }
            }
            drawVerticalDivider(canvas, position);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int top) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int left) {
        this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + left, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public void setBaselineAligned(boolean baselineAligned) {
        this.mBaselineAligned = baselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    public void setMeasureWithLargestChildEnabled(boolean enabled) {
        this.mUseLargestChild = enabled;
    }

    @Override // android.view.View
    public int getBaseline() {
        int majorGravity;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i = this.mBaselineAlignedChildIndex;
        if (childCount <= i) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View child = getChildAt(i);
        int childBaseline = child.getBaseline();
        if (childBaseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int childTop = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (majorGravity = this.mGravity & 112) != 48) {
            if (majorGravity == 16) {
                childTop += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
            } else if (majorGravity == 80) {
                childTop = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        return lp.topMargin + childTop + childBaseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.mBaselineAlignedChildIndex = i;
    }

    View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public void setWeightSum(float weightSum) {
        this.mWeightSum = Math.max(0.0f, weightSum);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOrientation == 1) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    protected boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (childIndex == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) == 0) {
            return false;
        }
        for (int i = childIndex - 1; i >= 0; i--) {
            if (getChildAt(i).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int count;
        int heightMode;
        int maxWidth;
        int alternativeMaxWidth;
        float weightSum;
        int delta;
        int maxWidth2;
        int delta2;
        int maxWidth3;
        float totalWeight;
        int i;
        int oldHeight;
        LayoutParams lp;
        int childState;
        int maxWidth4;
        int heightMode2;
        int heightMode3;
        int weightedMaxWidth;
        int weightedMaxWidth2;
        int alternativeMaxWidth2;
        View child;
        int alternativeMaxWidth3;
        int weightedMaxWidth3;
        this.mTotalLength = 0;
        int childState2 = 0;
        float totalWeight2 = 0.0f;
        int count2 = getVirtualChildCount();
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode4 = View.MeasureSpec.getMode(heightMeasureSpec);
        int baselineChildIndex = this.mBaselineAlignedChildIndex;
        boolean useLargestChild = this.mUseLargestChild;
        boolean matchWidth = false;
        int alternativeMaxWidth4 = 0;
        int alternativeMaxWidth5 = 0;
        int maxWidth5 = 0;
        int weightedMaxWidth4 = 0;
        int largestChildHeight = 0;
        int i2 = 0;
        boolean allFillParent = true;
        while (true) {
            int weightedMaxWidth5 = maxWidth5;
            if (i2 < count2) {
                View child2 = getVirtualChildAt(i2);
                if (child2 == null) {
                    this.mTotalLength += measureNullChild(i2);
                    heightMode2 = heightMode4;
                    maxWidth5 = weightedMaxWidth5;
                    weightedMaxWidth2 = count2;
                } else {
                    int childState3 = childState2;
                    int childState4 = child2.getVisibility();
                    if (childState4 == 8) {
                        i2 += getChildrenSkipCount(child2, i2);
                        heightMode2 = heightMode4;
                        maxWidth5 = weightedMaxWidth5;
                        childState2 = childState3;
                        weightedMaxWidth2 = count2;
                    } else {
                        if (hasDividerBeforeChildAt(i2)) {
                            this.mTotalLength += this.mDividerHeight;
                        }
                        LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                        float totalWeight3 = totalWeight2 + lp2.weight;
                        if (heightMode4 != 1073741824 || lp2.height != 0 || lp2.weight <= 0.0f) {
                            int maxWidth6 = alternativeMaxWidth5;
                            if (lp2.height == 0 && lp2.weight > 0.0f) {
                                lp2.height = -2;
                                oldHeight = 0;
                            } else {
                                oldHeight = Integer.MIN_VALUE;
                            }
                            lp = lp2;
                            childState = childState3;
                            maxWidth4 = maxWidth6;
                            heightMode2 = heightMode4;
                            heightMode3 = weightedMaxWidth4;
                            weightedMaxWidth = weightedMaxWidth5;
                            weightedMaxWidth2 = count2;
                            int oldHeight2 = oldHeight;
                            alternativeMaxWidth2 = alternativeMaxWidth4;
                            int alternativeMaxWidth6 = totalWeight3 == 0.0f ? this.mTotalLength : 0;
                            measureChildBeforeLayout(child2, i2, widthMeasureSpec, 0, heightMeasureSpec, alternativeMaxWidth6);
                            if (oldHeight2 != Integer.MIN_VALUE) {
                                lp.height = oldHeight2;
                            }
                            int childHeight = child2.getMeasuredHeight();
                            int totalLength = this.mTotalLength;
                            child = child2;
                            this.mTotalLength = Math.max(totalLength, totalLength + childHeight + lp.topMargin + lp.bottomMargin + getNextLocationOffset(child));
                            if (useLargestChild) {
                                heightMode3 = Math.max(childHeight, heightMode3);
                            }
                        } else {
                            int totalLength2 = this.mTotalLength;
                            int i3 = lp2.topMargin + totalLength2;
                            int maxWidth7 = alternativeMaxWidth5;
                            int maxWidth8 = lp2.bottomMargin;
                            this.mTotalLength = Math.max(totalLength2, i3 + maxWidth8);
                            largestChildHeight = 1;
                            lp = lp2;
                            alternativeMaxWidth2 = alternativeMaxWidth4;
                            heightMode2 = heightMode4;
                            weightedMaxWidth = weightedMaxWidth5;
                            childState = childState3;
                            maxWidth4 = maxWidth7;
                            heightMode3 = weightedMaxWidth4;
                            child = child2;
                            weightedMaxWidth2 = count2;
                        }
                        if (baselineChildIndex >= 0 && baselineChildIndex == i2 + 1) {
                            this.mBaselineChildTop = this.mTotalLength;
                        }
                        if (i2 < baselineChildIndex && lp.weight > 0.0f) {
                            throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                        }
                        boolean matchWidthLocally = false;
                        if (widthMode != 1073741824 && lp.width == -1) {
                            matchWidth = true;
                            matchWidthLocally = true;
                        }
                        int margin = lp.leftMargin + lp.rightMargin;
                        int measuredWidth = child.getMeasuredWidth() + margin;
                        int maxWidth9 = Math.max(maxWidth4, measuredWidth);
                        int childState5 = View.combineMeasuredStates(childState, child.getMeasuredState());
                        boolean allFillParent2 = allFillParent && lp.width == -1;
                        if (lp.weight > 0.0f) {
                            weightedMaxWidth3 = Math.max(weightedMaxWidth, matchWidthLocally ? margin : measuredWidth);
                            alternativeMaxWidth3 = alternativeMaxWidth2;
                        } else {
                            int weightedMaxWidth6 = weightedMaxWidth;
                            alternativeMaxWidth3 = Math.max(alternativeMaxWidth2, matchWidthLocally ? margin : measuredWidth);
                            weightedMaxWidth3 = weightedMaxWidth6;
                        }
                        int weightedMaxWidth7 = getChildrenSkipCount(child, i2);
                        i2 += weightedMaxWidth7;
                        alternativeMaxWidth5 = maxWidth9;
                        childState2 = childState5;
                        allFillParent = allFillParent2;
                        maxWidth5 = weightedMaxWidth3;
                        weightedMaxWidth4 = heightMode3;
                        totalWeight2 = totalWeight3;
                        alternativeMaxWidth4 = alternativeMaxWidth3;
                    }
                }
                i2++;
                count2 = weightedMaxWidth2;
                heightMode4 = heightMode2;
            } else {
                int childState6 = childState2;
                int maxWidth10 = alternativeMaxWidth5;
                int heightMode5 = heightMode4;
                int maxWidth11 = 8;
                int largestChildHeight2 = weightedMaxWidth4;
                int alternativeMaxWidth7 = alternativeMaxWidth4;
                int weightedMaxWidth8 = count2;
                int weightedMaxWidth9 = weightedMaxWidth5;
                int largestChildHeight3 = this.mTotalLength;
                if (largestChildHeight3 > 0) {
                    count = weightedMaxWidth8;
                    if (hasDividerBeforeChildAt(count)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                } else {
                    count = weightedMaxWidth8;
                }
                if (useLargestChild) {
                    heightMode = heightMode5;
                    if (heightMode == Integer.MIN_VALUE || heightMode == 0) {
                        this.mTotalLength = 0;
                        int i4 = 0;
                        while (i4 < count) {
                            View child3 = getVirtualChildAt(i4);
                            if (child3 == null) {
                                this.mTotalLength += measureNullChild(i4);
                            } else {
                                if (child3.getVisibility() == maxWidth11) {
                                    i = i4 + getChildrenSkipCount(child3, i4);
                                } else {
                                    LayoutParams lp3 = (LayoutParams) child3.getLayoutParams();
                                    int totalLength3 = this.mTotalLength;
                                    int i5 = lp3.topMargin;
                                    this.mTotalLength = Math.max(totalLength3, totalLength3 + largestChildHeight2 + i5 + lp3.bottomMargin + getNextLocationOffset(child3));
                                }
                                i4 = i + 1;
                                maxWidth11 = 8;
                            }
                            i = i4;
                            i4 = i + 1;
                            maxWidth11 = 8;
                        }
                    }
                } else {
                    heightMode = heightMode5;
                }
                this.mTotalLength += getPaddingTop() + getPaddingBottom();
                int heightSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), heightMeasureSpec, 0);
                int heightSize = heightSizeAndState & ViewCompat.MEASURED_SIZE_MASK;
                int delta3 = heightSize - this.mTotalLength;
                if (largestChildHeight != 0 || (delta3 != 0 && totalWeight2 > 0.0f)) {
                    float totalWeight4 = this.mWeightSum;
                    if (totalWeight4 <= 0.0f) {
                        totalWeight4 = totalWeight2;
                    }
                    float weightSum2 = totalWeight4;
                    this.mTotalLength = 0;
                    int i6 = 0;
                    int alternativeMaxWidth8 = alternativeMaxWidth7;
                    int delta4 = delta3;
                    int alternativeMaxWidth9 = maxWidth10;
                    while (i6 < count) {
                        boolean useLargestChild2 = useLargestChild;
                        View child4 = getVirtualChildAt(i6);
                        int weightedMaxWidth10 = weightedMaxWidth9;
                        int weightedMaxWidth11 = child4.getVisibility();
                        int largestChildHeight4 = largestChildHeight2;
                        if (weightedMaxWidth11 == 8) {
                            heightMode = heightMode;
                            baselineChildIndex = baselineChildIndex;
                        } else {
                            LayoutParams lp4 = (LayoutParams) child4.getLayoutParams();
                            float childExtra = lp4.weight;
                            if (childExtra <= 0.0f) {
                                heightMode = heightMode;
                                weightSum = weightSum2;
                                delta = delta4;
                            } else {
                                int share = (int) ((delta4 * childExtra) / weightSum2);
                                weightSum = weightSum2 - childExtra;
                                delta = delta4 - share;
                                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp4.leftMargin + lp4.rightMargin, lp4.width);
                                if (lp4.height != 0 || heightMode != 1073741824) {
                                    int childHeight2 = child4.getMeasuredHeight() + share;
                                    if (childHeight2 < 0) {
                                        childHeight2 = 0;
                                    }
                                    child4.measure(childWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(childHeight2, 1073741824));
                                } else {
                                    heightMode = heightMode;
                                    child4.measure(childWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(share > 0 ? share : 0, 1073741824));
                                }
                                childState6 = View.combineMeasuredStates(childState6, child4.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                            }
                            int margin2 = lp4.leftMargin + lp4.rightMargin;
                            int measuredWidth2 = child4.getMeasuredWidth() + margin2;
                            int maxWidth12 = Math.max(alternativeMaxWidth9, measuredWidth2);
                            alternativeMaxWidth8 = Math.max(alternativeMaxWidth8, widthMode != 1073741824 && lp4.width == -1 ? margin2 : measuredWidth2);
                            if (allFillParent) {
                                maxWidth2 = maxWidth12;
                                boolean allFillParent3 = lp4.width == -1;
                                int totalLength4 = this.mTotalLength;
                                this.mTotalLength = Math.max(totalLength4, totalLength4 + child4.getMeasuredHeight() + lp4.topMargin + lp4.bottomMargin + getNextLocationOffset(child4));
                                allFillParent = allFillParent3;
                                delta4 = delta;
                                weightSum2 = weightSum;
                                alternativeMaxWidth9 = maxWidth2;
                            } else {
                                maxWidth2 = maxWidth12;
                            }
                            int totalLength5 = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength5, totalLength5 + child4.getMeasuredHeight() + lp4.topMargin + lp4.bottomMargin + getNextLocationOffset(child4));
                            allFillParent = allFillParent3;
                            delta4 = delta;
                            weightSum2 = weightSum;
                            alternativeMaxWidth9 = maxWidth2;
                        }
                        i6++;
                        useLargestChild = useLargestChild2;
                        weightedMaxWidth9 = weightedMaxWidth10;
                        largestChildHeight2 = largestChildHeight4;
                        baselineChildIndex = baselineChildIndex;
                        heightMode = heightMode;
                    }
                    maxWidth = alternativeMaxWidth9;
                    int maxWidth13 = this.mTotalLength;
                    this.mTotalLength = maxWidth13 + getPaddingTop() + getPaddingBottom();
                    alternativeMaxWidth = alternativeMaxWidth8;
                } else {
                    int alternativeMaxWidth10 = Math.max(alternativeMaxWidth7, weightedMaxWidth9);
                    if (useLargestChild) {
                        alternativeMaxWidth = alternativeMaxWidth10;
                        if (heightMode != 1073741824) {
                            int i7 = 0;
                            while (i7 < count) {
                                int heightSize2 = heightSize;
                                View child5 = getVirtualChildAt(i7);
                                if (child5 != null) {
                                    maxWidth3 = maxWidth10;
                                    int maxWidth14 = child5.getVisibility();
                                    totalWeight = totalWeight2;
                                    if (maxWidth14 != 8 && ((LayoutParams) child5.getLayoutParams()).weight > 0.0f) {
                                        child5.measure(View.MeasureSpec.makeMeasureSpec(child5.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(largestChildHeight2, 1073741824));
                                    }
                                } else {
                                    maxWidth3 = maxWidth10;
                                    totalWeight = totalWeight2;
                                }
                                i7++;
                                heightSize = heightSize2;
                                totalWeight2 = totalWeight;
                                delta3 = delta3;
                                maxWidth10 = maxWidth3;
                            }
                            maxWidth = maxWidth10;
                            delta2 = delta3;
                        } else {
                            maxWidth = maxWidth10;
                            delta2 = delta3;
                        }
                    } else {
                        alternativeMaxWidth = alternativeMaxWidth10;
                        maxWidth = maxWidth10;
                        delta2 = delta3;
                    }
                }
                if (!allFillParent && widthMode != 1073741824) {
                    maxWidth = alternativeMaxWidth;
                }
                setMeasuredDimension(View.resolveSizeAndState(Math.max(maxWidth + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), widthMeasureSpec, childState6), heightSizeAndState);
                if (matchWidth) {
                    forceUniformWidth(count, heightMeasureSpec);
                    return;
                }
                return;
            }
        }
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.width == -1) {
                    int oldHeight = lp.height;
                    lp.height = child.getMeasuredHeight();
                    measureChildWithMargins(child, uniformMeasureSpec, 0, heightMeasureSpec, 0);
                    lp.height = oldHeight;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:204:0x054a  */
    /* JADX WARN: Code duplicated, block: B:206:0x0553  */
    /* JADX WARN: Code duplicated, block: B:208:0x0557  */
    /* JADX WARN: Code duplicated, block: B:209:0x055a  */
    /* JADX WARN: Code duplicated, block: B:211:0x057d  */
    /* JADX WARN: Code duplicated, block: B:212:0x0582  */
    void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        int maxHeight;
        int widthMode;
        int childState;
        int widthSizeAndState;
        int alternativeMaxHeight;
        int maxHeight2;
        int childState2;
        int widthMode2;
        boolean useLargestChild;
        int alternativeMaxHeight2;
        boolean allFillParent;
        int childBaseline;
        int i;
        int alternativeMaxHeight3;
        int widthSize;
        int i2;
        int oldWidth;
        int weightedMaxHeight;
        int alternativeMaxHeight4;
        int maxHeight3;
        int i3;
        boolean baselineAligned;
        int largestChildWidth;
        int widthMode3;
        LayoutParams lp;
        int largestChildWidth2;
        int alternativeMaxHeight5;
        int weightedMaxHeight2;
        int childState3;
        int childBaseline2;
        this.mTotalLength = 0;
        int count = getVirtualChildCount();
        int widthMode4 = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        maxAscent[3] = -1;
        maxAscent[2] = -1;
        maxAscent[1] = -1;
        maxAscent[0] = -1;
        maxDescent[3] = -1;
        maxDescent[2] = -1;
        maxDescent[1] = -1;
        maxDescent[0] = -1;
        boolean baselineAligned2 = this.mBaselineAligned;
        boolean skippedMeasure = false;
        boolean useLargestChild2 = this.mUseLargestChild;
        boolean isExactly = widthMode4 == 1073741824;
        int childState4 = 0;
        int alternativeMaxHeight6 = 0;
        int largestChildWidth3 = 0;
        boolean matchHeight = true;
        int childHeight = 0;
        float totalWeight = 0.0f;
        int i4 = 0;
        int weightedMaxHeight3 = 0;
        int alternativeMaxHeight7 = 0;
        while (i4 < count) {
            View child = getVirtualChildAt(i4);
            if (child == null) {
                int largestChildWidth4 = alternativeMaxHeight6;
                int largestChildWidth5 = this.mTotalLength;
                this.mTotalLength = largestChildWidth5 + measureNullChild(i4);
                baselineAligned = baselineAligned2;
                childState3 = childState4;
                alternativeMaxHeight6 = largestChildWidth4;
                largestChildWidth = widthMode4;
            } else {
                int largestChildWidth6 = alternativeMaxHeight6;
                int largestChildWidth7 = child.getVisibility();
                int weightedMaxHeight4 = alternativeMaxHeight7;
                if (largestChildWidth7 == 8) {
                    i4 += getChildrenSkipCount(child, i4);
                    baselineAligned = baselineAligned2;
                    childState3 = childState4;
                    alternativeMaxHeight6 = largestChildWidth6;
                    alternativeMaxHeight7 = weightedMaxHeight4;
                    largestChildWidth = widthMode4;
                } else {
                    if (hasDividerBeforeChildAt(i4)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams lp2 = (LayoutParams) child.getLayoutParams();
                    float totalWeight2 = totalWeight + lp2.weight;
                    if (widthMode4 != 1073741824 || lp2.width != 0 || lp2.weight <= 0.0f) {
                        int alternativeMaxHeight8 = weightedMaxHeight3;
                        if (lp2.width == 0 && lp2.weight > 0.0f) {
                            lp2.width = -2;
                            oldWidth = 0;
                        } else {
                            oldWidth = Integer.MIN_VALUE;
                        }
                        weightedMaxHeight = weightedMaxHeight4;
                        int oldWidth2 = oldWidth;
                        alternativeMaxHeight4 = alternativeMaxHeight8;
                        maxHeight3 = childHeight;
                        i3 = i4;
                        baselineAligned = baselineAligned2;
                        largestChildWidth = widthMode4;
                        widthMode3 = -1;
                        measureChildBeforeLayout(child, i4, widthMeasureSpec, totalWeight2 == 0.0f ? this.mTotalLength : 0, heightMeasureSpec, 0);
                        if (oldWidth2 == Integer.MIN_VALUE) {
                            lp = lp2;
                        } else {
                            lp = lp2;
                            lp.width = oldWidth2;
                        }
                        int childWidth = child.getMeasuredWidth();
                        if (isExactly) {
                            this.mTotalLength += lp.leftMargin + childWidth + lp.rightMargin + getNextLocationOffset(child);
                        } else {
                            int totalLength = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength, totalLength + childWidth + lp.leftMargin + lp.rightMargin + getNextLocationOffset(child));
                        }
                        if (!useLargestChild2) {
                            largestChildWidth2 = largestChildWidth6;
                        } else {
                            largestChildWidth2 = Math.max(childWidth, largestChildWidth6);
                        }
                    } else {
                        if (!isExactly) {
                            int totalLength2 = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength2, lp2.leftMargin + totalLength2 + lp2.rightMargin);
                        } else {
                            int i5 = this.mTotalLength;
                            int i6 = lp2.leftMargin;
                            int alternativeMaxHeight9 = lp2.rightMargin;
                            this.mTotalLength = i5 + i6 + alternativeMaxHeight9;
                        }
                        if (baselineAligned2) {
                            int freeSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            child.measure(freeSpec, freeSpec);
                            lp = lp2;
                            maxHeight3 = childHeight;
                            i3 = i4;
                            baselineAligned = baselineAligned2;
                            largestChildWidth2 = largestChildWidth6;
                            weightedMaxHeight = weightedMaxHeight4;
                            alternativeMaxHeight4 = weightedMaxHeight3;
                            largestChildWidth = widthMode4;
                            widthMode3 = -1;
                        } else {
                            skippedMeasure = true;
                            lp = lp2;
                            maxHeight3 = childHeight;
                            i3 = i4;
                            baselineAligned = baselineAligned2;
                            largestChildWidth2 = largestChildWidth6;
                            weightedMaxHeight = weightedMaxHeight4;
                            alternativeMaxHeight4 = weightedMaxHeight3;
                            largestChildWidth = widthMode4;
                            widthMode3 = -1;
                        }
                    }
                    int oldWidth3 = 0;
                    if (heightMode != 1073741824 && lp.height == widthMode3) {
                        largestChildWidth3 = 1;
                        oldWidth3 = 1;
                    }
                    int margin = lp.topMargin + lp.bottomMargin;
                    int childHeight2 = child.getMeasuredHeight() + margin;
                    int childState5 = View.combineMeasuredStates(childState4, child.getMeasuredState());
                    if (baselineAligned && (childBaseline2 = child.getBaseline()) != widthMode3) {
                        int gravity = (lp.gravity < 0 ? this.mGravity : lp.gravity) & 112;
                        int index = ((gravity >> 4) & (-2)) >> 1;
                        maxAscent[index] = Math.max(maxAscent[index], childBaseline2);
                        int largestChildWidth8 = childHeight2 - childBaseline2;
                        maxDescent[index] = Math.max(maxDescent[index], largestChildWidth8);
                    }
                    int maxHeight4 = Math.max(maxHeight3, childHeight2);
                    boolean allFillParent2 = matchHeight && lp.height == -1;
                    if (lp.weight > 0.0f) {
                        weightedMaxHeight2 = Math.max(weightedMaxHeight, oldWidth3 != 0 ? margin : childHeight2);
                        alternativeMaxHeight5 = alternativeMaxHeight4;
                    } else {
                        int weightedMaxHeight5 = weightedMaxHeight;
                        alternativeMaxHeight5 = Math.max(alternativeMaxHeight4, oldWidth3 != 0 ? margin : childHeight2);
                        weightedMaxHeight2 = weightedMaxHeight5;
                    }
                    int weightedMaxHeight6 = getChildrenSkipCount(child, i3);
                    childHeight = maxHeight4;
                    matchHeight = allFillParent2;
                    alternativeMaxHeight7 = weightedMaxHeight2;
                    totalWeight = totalWeight2;
                    weightedMaxHeight3 = alternativeMaxHeight5;
                    childState3 = childState5;
                    i4 = i3 + weightedMaxHeight6;
                    alternativeMaxHeight6 = largestChildWidth2;
                }
            }
            i4++;
            childState4 = childState3;
            baselineAligned2 = baselineAligned;
            widthMode4 = largestChildWidth;
        }
        boolean baselineAligned3 = baselineAligned2;
        int widthMode5 = widthMode4;
        int i7 = childState4;
        int weightedMaxHeight7 = alternativeMaxHeight7;
        int maxHeight5 = childHeight;
        int alternativeMaxHeight10 = weightedMaxHeight3;
        int largestChildWidth9 = alternativeMaxHeight6;
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(count)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (maxAscent[1] != -1 || maxAscent[0] != -1 || maxAscent[2] != -1 || maxAscent[3] != -1) {
            int ascent = Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2])));
            int i8 = maxDescent[3];
            int i9 = maxDescent[0];
            int i10 = maxDescent[1];
            int childState6 = maxDescent[2];
            int descent = Math.max(i8, Math.max(i9, Math.max(i10, childState6)));
            maxHeight5 = Math.max(maxHeight5, ascent + descent);
        }
        if (useLargestChild2) {
            widthMode = widthMode5;
            if (widthMode == Integer.MIN_VALUE || widthMode == 0) {
                this.mTotalLength = 0;
                int i11 = 0;
                while (i11 < count) {
                    View child2 = getVirtualChildAt(i11);
                    if (child2 == null) {
                        this.mTotalLength += measureNullChild(i11);
                        i2 = i11;
                    } else if (child2.getVisibility() == 8) {
                        i2 = i11 + getChildrenSkipCount(child2, i11);
                    } else {
                        LayoutParams lp3 = (LayoutParams) child2.getLayoutParams();
                        if (!isExactly) {
                            i2 = i11;
                            int maxHeight6 = this.mTotalLength;
                            this.mTotalLength = Math.max(maxHeight6, maxHeight6 + largestChildWidth9 + lp3.leftMargin + lp3.rightMargin + getNextLocationOffset(child2));
                        } else {
                            int i12 = this.mTotalLength;
                            int i13 = lp3.leftMargin + largestChildWidth9;
                            i2 = i11;
                            int i14 = lp3.rightMargin;
                            this.mTotalLength = i12 + i13 + i14 + getNextLocationOffset(child2);
                        }
                    }
                    i11 = i2 + 1;
                    maxHeight5 = maxHeight5;
                }
                maxHeight = maxHeight5;
            } else {
                maxHeight = maxHeight5;
            }
        } else {
            maxHeight = maxHeight5;
            widthMode = widthMode5;
        }
        this.mTotalLength += getPaddingLeft() + getPaddingRight();
        int widthSizeAndState2 = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), widthMeasureSpec, 0);
        int widthSize2 = widthSizeAndState2 & ViewCompat.MEASURED_SIZE_MASK;
        int delta = widthSize2 - this.mTotalLength;
        if (skippedMeasure || (delta != 0 && totalWeight > 0.0f)) {
            float weightSum = this.mWeightSum;
            if (weightSum <= 0.0f) {
                weightSum = totalWeight;
            }
            maxAscent[3] = -1;
            maxAscent[2] = -1;
            maxAscent[1] = -1;
            maxAscent[0] = -1;
            maxDescent[3] = -1;
            maxDescent[2] = -1;
            maxDescent[1] = -1;
            maxDescent[0] = -1;
            maxHeight2 = -1;
            this.mTotalLength = 0;
            int i15 = 0;
            int alternativeMaxHeight11 = alternativeMaxHeight10;
            childState2 = i7;
            while (i15 < count) {
                int weightedMaxHeight8 = weightedMaxHeight7;
                View child3 = getVirtualChildAt(i15);
                if (child3 != null) {
                    useLargestChild = useLargestChild2;
                    if (child3.getVisibility() == 8) {
                        widthMode = widthMode;
                        widthMode2 = delta;
                        widthSizeAndState2 = widthSizeAndState2;
                        count = count;
                    } else {
                        LayoutParams lp4 = (LayoutParams) child3.getLayoutParams();
                        float childExtra = lp4.weight;
                        if (childExtra <= 0.0f) {
                            widthMode = widthMode;
                            widthMode2 = delta;
                        } else {
                            int share = (int) ((delta * childExtra) / weightSum);
                            float weightSum2 = weightSum - childExtra;
                            int delta2 = delta - share;
                            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp4.topMargin + lp4.bottomMargin, lp4.height);
                            if (lp4.width == 0 && widthMode == 1073741824) {
                                child3.measure(View.MeasureSpec.makeMeasureSpec(share > 0 ? share : 0, 1073741824), childHeightMeasureSpec);
                            } else {
                                int childWidth2 = child3.getMeasuredWidth() + share;
                                if (childWidth2 < 0) {
                                    childWidth2 = 0;
                                }
                                child3.measure(View.MeasureSpec.makeMeasureSpec(childWidth2, 1073741824), childHeightMeasureSpec);
                            }
                            childState2 = View.combineMeasuredStates(childState2, child3.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                            weightSum = weightSum2;
                            widthMode2 = delta2;
                        }
                        if (isExactly) {
                            this.mTotalLength += child3.getMeasuredWidth() + lp4.leftMargin + lp4.rightMargin + getNextLocationOffset(child3);
                        } else {
                            int totalLength3 = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength3, child3.getMeasuredWidth() + totalLength3 + lp4.leftMargin + lp4.rightMargin + getNextLocationOffset(child3));
                        }
                        boolean matchHeightLocally = heightMode != 1073741824 && lp4.height == -1;
                        int margin2 = lp4.topMargin + lp4.bottomMargin;
                        int childHeight3 = child3.getMeasuredHeight() + margin2;
                        maxHeight2 = Math.max(maxHeight2, childHeight3);
                        float weightSum3 = weightSum;
                        int alternativeMaxHeight12 = Math.max(alternativeMaxHeight11, matchHeightLocally ? margin2 : childHeight3);
                        if (matchHeight) {
                            alternativeMaxHeight2 = alternativeMaxHeight12;
                            allFillParent = lp4.height == -1;
                            if (baselineAligned3) {
                                matchHeight = allFillParent;
                            } else {
                                childBaseline = child3.getBaseline();
                                matchHeight = allFillParent;
                                if (childBaseline == -1) {
                                    if (lp4.gravity < 0) {
                                        i = this.mGravity;
                                    } else {
                                        i = lp4.gravity;
                                    }
                                    int gravity2 = i & 112;
                                    int index2 = ((gravity2 >> 4) & (-2)) >> 1;
                                    int gravity3 = maxAscent[index2];
                                    maxAscent[index2] = Math.max(gravity3, childBaseline);
                                    int childState7 = childHeight3 - childBaseline;
                                    maxDescent[index2] = Math.max(maxDescent[index2], childState7);
                                }
                            }
                            weightSum = weightSum3;
                            alternativeMaxHeight11 = alternativeMaxHeight2;
                            childState2 = childState2;
                        } else {
                            alternativeMaxHeight2 = alternativeMaxHeight12;
                        }
                        if (baselineAligned3) {
                            matchHeight = allFillParent;
                        } else {
                            childBaseline = child3.getBaseline();
                            matchHeight = allFillParent;
                            if (childBaseline == -1) {
                                if (lp4.gravity < 0) {
                                    i = this.mGravity;
                                } else {
                                    i = lp4.gravity;
                                }
                                int gravity4 = i & 112;
                                int index3 = ((gravity4 >> 4) & (-2)) >> 1;
                                int gravity5 = maxAscent[index3];
                                maxAscent[index3] = Math.max(gravity5, childBaseline);
                                int childState8 = childHeight3 - childBaseline;
                                maxDescent[index3] = Math.max(maxDescent[index3], childState8);
                            }
                        }
                        weightSum = weightSum3;
                        alternativeMaxHeight11 = alternativeMaxHeight2;
                        childState2 = childState2;
                    }
                } else {
                    widthMode = widthMode;
                    widthMode2 = delta;
                    widthSizeAndState2 = widthSizeAndState2;
                    count = count;
                    useLargestChild = useLargestChild2;
                }
                i15++;
                delta = widthMode2;
                widthSizeAndState2 = widthSizeAndState2;
                useLargestChild2 = useLargestChild;
                count = count;
                weightedMaxHeight7 = weightedMaxHeight8;
                widthMode = widthMode;
            }
            widthSizeAndState = widthSizeAndState2;
            childState = count;
            this.mTotalLength += getPaddingLeft() + getPaddingRight();
            if (maxAscent[1] != -1 || maxAscent[0] != -1 || maxAscent[2] != -1 || maxAscent[3] != -1) {
                int ascent2 = Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2])));
                int descent2 = Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2])));
                maxHeight2 = Math.max(maxHeight2, ascent2 + descent2);
            }
            alternativeMaxHeight = alternativeMaxHeight11;
        } else {
            int alternativeMaxHeight13 = Math.max(alternativeMaxHeight10, weightedMaxHeight7);
            if (!useLargestChild2 || widthMode == 1073741824) {
                alternativeMaxHeight = alternativeMaxHeight13;
            } else {
                int i16 = 0;
                while (i16 < count) {
                    float totalWeight3 = totalWeight;
                    View child4 = getVirtualChildAt(i16);
                    if (child4 != null) {
                        alternativeMaxHeight3 = alternativeMaxHeight13;
                        int alternativeMaxHeight14 = child4.getVisibility();
                        widthSize = widthSize2;
                        if (alternativeMaxHeight14 != 8 && ((LayoutParams) child4.getLayoutParams()).weight > 0.0f) {
                            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(largestChildWidth9, 1073741824);
                            int largestChildWidth10 = child4.getMeasuredHeight();
                            child4.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(largestChildWidth10, 1073741824));
                        }
                    } else {
                        alternativeMaxHeight3 = alternativeMaxHeight13;
                        widthSize = widthSize2;
                    }
                    i16++;
                    alternativeMaxHeight13 = alternativeMaxHeight3;
                    totalWeight = totalWeight3;
                    widthSize2 = widthSize;
                    largestChildWidth9 = largestChildWidth9;
                }
                alternativeMaxHeight = alternativeMaxHeight13;
            }
            widthSizeAndState = widthSizeAndState2;
            maxHeight2 = maxHeight;
            childState2 = i7;
            childState = count;
        }
        if (!matchHeight && heightMode != 1073741824) {
            maxHeight2 = alternativeMaxHeight;
        }
        setMeasuredDimension(widthSizeAndState | ((-16777216) & childState2), View.resolveSizeAndState(Math.max(maxHeight2 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), heightMeasureSpec, childState2 << 16));
        if (largestChildWidth3 != 0) {
            forceUniformHeight(childState, widthMeasureSpec);
        }
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.height == -1) {
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }

    int getChildrenSkipCount(View child, int index) {
        return 0;
    }

    int measureNullChild(int childIndex) {
        return 0;
    }

    void measureChildBeforeLayout(View child, int childIndex, int widthMeasureSpec, int totalWidth, int heightMeasureSpec, int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
    }

    int getLocationOffset(View child) {
        return 0;
    }

    int getNextLocationOffset(View child) {
        return 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mOrientation == 1) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }

    void layoutVertical(int left, int top, int right, int bottom) {
        int childTop;
        int gravity;
        int childLeft;
        int paddingLeft = getPaddingLeft();
        int width = right - left;
        int childRight = width - getPaddingRight();
        int childSpace = (width - paddingLeft) - getPaddingRight();
        int count = getVirtualChildCount();
        int i = this.mGravity;
        int majorGravity = i & 112;
        int minorGravity = i & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (majorGravity == 16) {
            int childTop2 = getPaddingTop();
            childTop = childTop2 + (((bottom - top) - this.mTotalLength) / 2);
        } else if (majorGravity == 80) {
            int childTop3 = getPaddingTop();
            childTop = ((childTop3 + bottom) - top) - this.mTotalLength;
        } else {
            childTop = getPaddingTop();
        }
        int i2 = 0;
        while (i2 < count) {
            View child = getVirtualChildAt(i2);
            if (child == null) {
                childTop += measureNullChild(i2);
            } else if (child.getVisibility() != 8) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int gravity2 = lp.gravity;
                if (gravity2 >= 0) {
                    gravity = gravity2;
                } else {
                    gravity = minorGravity;
                }
                int layoutDirection = ViewCompat.getLayoutDirection(this);
                int absoluteGravity = GravityCompat.getAbsoluteGravity(gravity, layoutDirection);
                int i3 = absoluteGravity & 7;
                if (i3 == 1) {
                    int childLeft2 = childSpace - childWidth;
                    childLeft = (((childLeft2 / 2) + paddingLeft) + lp.leftMargin) - lp.rightMargin;
                } else if (i3 == 5) {
                    int childLeft3 = childRight - childWidth;
                    childLeft = childLeft3 - lp.rightMargin;
                } else {
                    childLeft = lp.leftMargin + paddingLeft;
                }
                if (hasDividerBeforeChildAt(i2)) {
                    childTop += this.mDividerHeight;
                }
                int childTop4 = childTop + lp.topMargin;
                int childTop5 = getLocationOffset(child);
                int layoutDirection2 = childLeft;
                setChildFrame(child, layoutDirection2, childTop4 + childTop5, childWidth, childHeight);
                int childTop6 = childTop4 + childHeight + lp.bottomMargin + getNextLocationOffset(child);
                i2 += getChildrenSkipCount(child, i2);
                childTop = childTop6;
            }
            i2++;
            paddingLeft = paddingLeft;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:30:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:33:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:35:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:37:0x00db  */
    /* JADX WARN: Code duplicated, block: B:38:0x00de  */
    /* JADX WARN: Code duplicated, block: B:40:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:41:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:42:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:44:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:45:0x0104  */
    /* JADX WARN: Code duplicated, block: B:46:0x0107  */
    /* JADX WARN: Code duplicated, block: B:49:0x011a  */
    void layoutHorizontal(int left, int top, int right, int bottom) {
        int childLeft;
        int start;
        int dir;
        int layoutDirection;
        int height;
        int childBaseline;
        int gravity;
        int gravity2;
        int gravity3;
        int childTop;
        int childTop2;
        int childTop3;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int height2 = bottom - top;
        int childBottom = height2 - getPaddingBottom();
        int childSpace = (height2 - paddingTop) - getPaddingBottom();
        int count = getVirtualChildCount();
        int i = this.mGravity;
        int majorGravity = i & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int minorGravity = i & 112;
        boolean baselineAligned = this.mBaselineAligned;
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        int layoutDirection2 = ViewCompat.getLayoutDirection(this);
        int absoluteGravity = GravityCompat.getAbsoluteGravity(majorGravity, layoutDirection2);
        if (absoluteGravity == 1) {
            int childLeft2 = getPaddingLeft();
            childLeft = childLeft2 + (((right - left) - this.mTotalLength) / 2);
        } else if (absoluteGravity == 5) {
            int childLeft3 = getPaddingLeft();
            childLeft = ((childLeft3 + right) - left) - this.mTotalLength;
        } else {
            childLeft = getPaddingLeft();
        }
        if (!isLayoutRtl) {
            start = 0;
            dir = 1;
        } else {
            int start2 = count - 1;
            start = start2;
            dir = -1;
        }
        int i2 = 0;
        while (i2 < count) {
            int childIndex = start + (dir * i2);
            boolean isLayoutRtl2 = isLayoutRtl;
            View child = getVirtualChildAt(childIndex);
            if (child == null) {
                childLeft += measureNullChild(childIndex);
                layoutDirection = layoutDirection2;
                height = height2;
            } else {
                int i3 = i2;
                int i4 = child.getVisibility();
                layoutDirection = layoutDirection2;
                if (i4 != 8) {
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    if (!baselineAligned) {
                        height = height2;
                    } else {
                        height = height2;
                        if (lp.height != -1) {
                            childBaseline = child.getBaseline();
                        }
                        gravity = lp.gravity;
                        if (gravity < 0) {
                            gravity2 = gravity;
                        } else {
                            gravity2 = minorGravity;
                        }
                        gravity3 = gravity2 & 112;
                        if (gravity3 != 16) {
                            int childTop4 = ((((childSpace - childHeight) / 2) + paddingTop) + lp.topMargin) - lp.bottomMargin;
                            childTop = childTop4;
                        } else if (gravity3 != 48) {
                            int childTop5 = lp.topMargin;
                            childTop2 = childTop5 + paddingTop;
                            if (childBaseline != -1) {
                                childTop = childTop2 + (maxAscent[1] - childBaseline);
                            } else {
                                childTop = childTop2;
                            }
                        } else if (gravity3 != 80) {
                            int childTop6 = childBottom - childHeight;
                            childTop3 = childTop6 - lp.bottomMargin;
                            if (childBaseline != -1) {
                                childTop = childTop3;
                            } else {
                                int descent = child.getMeasuredHeight() - childBaseline;
                                childTop = childTop3 - (maxDescent[2] - descent);
                            }
                        } else {
                            childTop = paddingTop;
                        }
                        if (hasDividerBeforeChildAt(childIndex)) {
                            childLeft += this.mDividerWidth;
                        }
                        int childLeft4 = childLeft + lp.leftMargin;
                        int childLeft5 = getLocationOffset(child);
                        setChildFrame(child, childLeft4 + childLeft5, childTop, childWidth, childHeight);
                        int childLeft6 = childLeft4 + childWidth + lp.rightMargin + getNextLocationOffset(child);
                        i2 = i3 + getChildrenSkipCount(child, childIndex);
                        childLeft = childLeft6;
                    }
                    childBaseline = -1;
                    gravity = lp.gravity;
                    if (gravity < 0) {
                        gravity2 = gravity;
                    } else {
                        gravity2 = minorGravity;
                    }
                    gravity3 = gravity2 & 112;
                    if (gravity3 != 16) {
                        int childTop7 = ((((childSpace - childHeight) / 2) + paddingTop) + lp.topMargin) - lp.bottomMargin;
                        childTop = childTop7;
                    } else if (gravity3 != 48) {
                        int childTop8 = lp.topMargin;
                        childTop2 = childTop8 + paddingTop;
                        if (childBaseline != -1) {
                            childTop = childTop2 + (maxAscent[1] - childBaseline);
                        } else {
                            childTop = childTop2;
                        }
                    } else if (gravity3 != 80) {
                        int childTop9 = childBottom - childHeight;
                        childTop3 = childTop9 - lp.bottomMargin;
                        if (childBaseline != -1) {
                            childTop = childTop3;
                        } else {
                            int descent2 = child.getMeasuredHeight() - childBaseline;
                            childTop = childTop3 - (maxDescent[2] - descent2);
                        }
                    } else {
                        childTop = paddingTop;
                    }
                    if (hasDividerBeforeChildAt(childIndex)) {
                        childLeft += this.mDividerWidth;
                    }
                    int childLeft7 = childLeft + lp.leftMargin;
                    int childLeft8 = getLocationOffset(child);
                    setChildFrame(child, childLeft7 + childLeft8, childTop, childWidth, childHeight);
                    int childLeft9 = childLeft7 + childWidth + lp.rightMargin + getNextLocationOffset(child);
                    i2 = i3 + getChildrenSkipCount(child, childIndex);
                    childLeft = childLeft9;
                } else {
                    height = height2;
                    i2 = i3;
                }
            }
            i2++;
            isLayoutRtl = isLayoutRtl2;
            layoutDirection2 = layoutDirection;
            height2 = height;
            count = count;
            paddingTop = paddingTop;
            maxDescent = maxDescent;
            maxAscent = maxAscent;
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    public void setOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            if ((8388615 & gravity) == 0) {
                gravity |= GravityCompat.START;
            }
            if ((gravity & 112) == 0) {
                gravity |= 48;
            }
            this.mGravity = gravity;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setHorizontalGravity(int horizontalGravity) {
        int gravity = horizontalGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i = this.mGravity;
        if ((8388615 & i) != gravity) {
            this.mGravity = ((-8388616) & i) | gravity;
            requestLayout();
        }
    }

    public void setVerticalGravity(int verticalGravity) {
        int gravity = verticalGravity & 112;
        int i = this.mGravity;
        if ((i & 112) != gravity) {
            this.mGravity = (i & (-113)) | gravity;
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        int i = this.mOrientation;
        if (i == 0) {
            return new LayoutParams(-2, -2);
        }
        if (i == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(LinearLayoutCompat.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(LinearLayoutCompat.class.getName());
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.LinearLayoutCompat_Layout);
            this.weight = a.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = a.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height);
            this.gravity = -1;
            this.weight = weight;
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
            this.gravity = -1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
            this.gravity = -1;
        }

        public LayoutParams(LayoutParams source) {
            super((ViewGroup.MarginLayoutParams) source);
            this.gravity = -1;
            this.weight = source.weight;
            this.gravity = source.gravity;
        }
    }
}
