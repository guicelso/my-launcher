package com.android.ex.editstyledtext;

import android.R;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class d extends EditText {
    private static final NoCopySpan.Concrete kJ = new NoCopySpan.Concrete();
    private static CharSequence kK;
    private static CharSequence kL;
    private static CharSequence kM;
    private Drawable kN;
    private ArrayList kO;
    private InputConnection kP;
    private a kQ;

    /* JADX INFO: Access modifiers changed from: private */
    public void pI(int i, int i2) {
        if (this.kO != null) {
            Iterator it = this.kO.iterator();
            while (it.hasNext()) {
                ((c) it.next()).pz(i, i2);
            }
        }
    }

    private void pN() {
        this.kQ.pl();
    }

    private void pU(MotionEvent motionEvent) {
        if (this.kO != null) {
            Iterator it = this.kO.iterator();
            while (it.hasNext()) {
                ((c) it.next()).pA(motionEvent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void pV(View view, Spannable spannable) {
        spannable.removeSpan(kJ);
    }

    @Override // android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.kQ != null) {
            this.kQ.pl();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onCreateContextMenu(ContextMenu contextMenu) {
        super.onCreateContextMenu(contextMenu);
        g gVar = new g(this, null);
        if (kL != null) {
            contextMenu.add(0, 16776961, 0, kL).setOnMenuItemClickListener(gVar);
        }
        if (pH() && kK != null) {
            contextMenu.add(0, 16776962, 0, kK).setOnMenuItemClickListener(gVar);
        }
        if (this.kQ.oT()) {
            contextMenu.add(0, R.id.paste, 0, kM).setOnMenuItemClickListener(gVar).setAlphabeticShortcut('v');
        }
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        this.kP = new i(super.onCreateInputConnection(editorInfo), this);
        return this.kP;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z) {
            pQ();
        } else {
            if (pE()) {
                return;
            }
            pK();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof EditStyledText$SavedStyledTextState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        EditStyledText$SavedStyledTextState editStyledText$SavedStyledTextState = (EditStyledText$SavedStyledTextState) parcelable;
        super.onRestoreInstanceState(editStyledText$SavedStyledTextState.getSuperState());
        setBackgroundColor(editStyledText$SavedStyledTextState.kI);
    }

    @Override // android.widget.TextView, android.view.View
    public Parcelable onSaveInstanceState() {
        EditStyledText$SavedStyledTextState editStyledText$SavedStyledTextState = new EditStyledText$SavedStyledTextState(super.onSaveInstanceState());
        editStyledText$SavedStyledTextState.kI = this.kQ.oY();
        return editStyledText$SavedStyledTextState;
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (this.kQ != null) {
            this.kQ.pv(getText(), i, i2, i3);
            this.kQ.pw(getText(), i, i2, i3);
            if (i3 > i2) {
                this.kQ.pq(i, i + i3);
            } else if (i2 < i3) {
                this.kQ.pu();
            }
            if (this.kQ.pf()) {
                if (i3 > i2) {
                    this.kQ.pj();
                    pL();
                } else if (i3 < i2) {
                    this.kQ.pg(22);
                }
            }
        }
        super.onTextChanged(charSequence, i, i2, i3);
    }

    @Override // android.widget.EditText, android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        boolean z = getSelectionStart() != getSelectionEnd();
        switch (i) {
            case 16776961:
                pM();
                return true;
            case 16776962:
                pJ();
                return true;
            case 16776963:
                pQ();
                return true;
            case 16776964:
                pK();
                return true;
            case R.id.selectAll:
                pT();
                return true;
            case R.id.cut:
                if (z) {
                    pP();
                } else {
                    this.kQ.pn(false);
                    pP();
                }
                return true;
            case R.id.copy:
                if (z) {
                    pO();
                } else {
                    this.kQ.pn(false);
                    pO();
                }
                return true;
            case R.id.paste:
                pR();
                return true;
            case R.id.startSelectingText:
                pS();
                this.kQ.oS();
                break;
            case R.id.stopSelectingText:
                pL();
                break;
        }
        return super.onTextContextMenuItem(i);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent;
        if (motionEvent.getAction() == 1) {
            cancelLongPress();
            boolean zPF = pF();
            if (!zPF) {
                pQ();
            }
            int selectionStart = Selection.getSelectionStart(getText());
            int selectionEnd = Selection.getSelectionEnd(getText());
            zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (isFocused() && pD() == 0) {
                if (zPF) {
                    this.kQ.pr(Selection.getSelectionStart(getText()), Selection.getSelectionEnd(getText()));
                } else {
                    this.kQ.pr(selectionStart, selectionEnd);
                }
            }
            this.kQ.pj();
            this.kQ.pu();
        } else {
            zOnTouchEvent = super.onTouchEvent(motionEvent);
        }
        pU(motionEvent);
        return zOnTouchEvent;
    }

    public int pB() {
        return this.kQ.oY();
    }

    public int pC(int i) {
        if (i < 0 || i > getText().length()) {
            return -16777216;
        }
        ForegroundColorSpan[] foregroundColorSpanArr = (ForegroundColorSpan[]) getText().getSpans(i, i, ForegroundColorSpan.class);
        if (foregroundColorSpanArr.length > 0) {
            return foregroundColorSpanArr[0].getForegroundColor();
        }
        return -16777216;
    }

    public int pD() {
        return this.kQ.oZ();
    }

    public boolean pE() {
        boolean zPy = false;
        if (this.kO == null) {
            return false;
        }
        Iterator it = this.kO.iterator();
        while (true) {
            boolean z = zPy;
            if (!it.hasNext()) {
                return z;
            }
            zPy = ((c) it.next()).py() | z;
        }
    }

    public boolean pF() {
        return this.kQ.pc();
    }

    public boolean pG() {
        return this.kQ.pd();
    }

    public boolean pH() {
        return this.kQ.pe();
    }

    public void pJ() {
        this.kQ.pi();
    }

    public void pK() {
        this.kQ.pg(21);
    }

    public void pL() {
        this.kQ.pk();
    }

    public void pM() {
        this.kQ.pg(12);
    }

    public void pO() {
        this.kQ.pg(1);
    }

    public void pP() {
        this.kQ.pg(7);
    }

    public void pQ() {
        this.kQ.pg(20);
    }

    public void pR() {
        this.kQ.pg(2);
    }

    public void pS() {
        this.kQ.pm(true);
    }

    public void pT() {
        this.kQ.pn(true);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        if (i != 16777215) {
            super.setBackgroundColor(i);
        } else {
            setBackgroundDrawable(this.kN);
        }
        this.kQ.setBackgroundColor(i);
        pN();
    }
}
