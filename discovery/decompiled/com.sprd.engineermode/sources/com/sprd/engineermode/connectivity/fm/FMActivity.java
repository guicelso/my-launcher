package com.sprd.engineermode.connectivity.fm;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.sprd.engineermode.R;

/* JADX INFO: loaded from: classes4.dex */
public class FMActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_activity_main);
        initFragment();
    }

    public void initFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        FMFragment fmFragment = new FMFragment();
        fragmentTransaction.add(R.id.fm_framelayout, fmFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
