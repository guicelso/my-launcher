package com.sprd.engineermode.debuglog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.sprd.engineermode.R;

/* JADX INFO: loaded from: classes4.dex */
public class ViewItemVersionActivity extends Activity {
    public static final String TITLE = "Show Title";
    public static final String VERSION = "Show Version";
    private TextView mVersionText;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(4, 4);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        setContentView(R.layout.version_pre);
        this.mVersionText = (TextView) findViewById(R.id.version_id);
        Intent intent = getIntent();
        if (intent != null) {
            this.mVersionText.setText(intent.getStringExtra(VERSION));
            setTitle(intent.getStringExtra(TITLE));
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
