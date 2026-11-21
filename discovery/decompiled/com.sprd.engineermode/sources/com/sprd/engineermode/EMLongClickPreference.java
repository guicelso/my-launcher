package com.sprd.engineermode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceGroup;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/* JADX INFO: loaded from: classes4.dex */
public class EMLongClickPreference extends PreferenceGroup implements View.OnClickListener, View.OnLongClickListener {
    static final String TAG = "EMLongClickPreference";
    private Context mContext;
    private int mId;
    private ImageButton mImageButton;
    private RelativeLayout mRelativeLayout;

    public EMLongClickPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public EMLongClickPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    @Override // android.preference.Preference
    public View getView(View convertView, ViewGroup parent) {
        View view = super.getView(convertView, parent);
        View mView = view.findViewById(R.id.relativeLayout);
        Log.d(TAG, "getView");
        if (mView != null && (mView instanceof RelativeLayout)) {
            Log.d(TAG, "getView RelativeLayout");
            this.mRelativeLayout = (RelativeLayout) mView;
        }
        View widget = view.findViewById(R.id.help_button);
        Log.d(TAG, "getView");
        if (widget != null && (widget instanceof ImageButton)) {
            Log.d(TAG, "getView mImageButton");
            this.mImageButton = (ImageButton) widget;
            this.mImageButton.setOnClickListener(this);
        }
        View textLayout = view.findViewById(R.id.text_layout);
        if (textLayout != null && (textLayout instanceof TextView)) {
            ((TextView) textLayout).setText(getTitle());
        }
        View textSummary = view.findViewById(R.id.text_summary);
        if (textSummary != null && (textSummary instanceof TextView)) {
            getSummary();
        }
        view.setTag(Integer.valueOf(this.mId));
        return view;
    }

    private void init() {
        setLayoutResource(R.layout.long_click_preference_layout);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        if (v.getId() == R.id.help_button) {
            Log.d(TAG, "onClick help_button");
            if (getSummary() != null) {
                createDialog(getSummary().toString());
            }
        }
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View v) {
        Log.d(TAG, "onLongClick");
        createDialog(getSummary().toString());
        return true;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return this.mId;
    }

    private void createDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.mContext).setTitle(this.mContext.getString(R.string.function_introduction)).setMessage(message).setCancelable(false).setPositiveButton(this.mContext.getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.EMLongClickPreference.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        alertDialog.show();
    }
}
