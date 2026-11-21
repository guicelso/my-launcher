package com.android.ex.editstyledtext;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class EditStyledText$SavedStyledTextState extends View.BaseSavedState {
    public int kI;

    EditStyledText$SavedStyledTextState(Parcelable parcelable) {
        super(parcelable);
    }

    public String toString() {
        return "EditStyledText.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " bgcolor=" + this.kI + "}";
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.kI);
    }
}
