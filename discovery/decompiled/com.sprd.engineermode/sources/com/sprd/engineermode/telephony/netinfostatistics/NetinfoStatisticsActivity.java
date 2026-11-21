package com.sprd.engineermode.telephony.netinfostatistics;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.sprd.engineermode.R;
import com.sprd.engineermode.TabFragmentPagerAdapter;
import com.sprd.engineermode.telephony.SimSelectHelperActivity;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class NetinfoStatisticsActivity extends AppCompatActivity {
    public static String CURRENT_TAB = "android.app.engmode.currenttab";
    private static final String TAG = "NetinfoStatisticsActivity";
    private Context mContext;
    private ArrayList<Fragment> mFragmentsList;
    private ViewPager mViewPager;
    private List<String> mTitleList = new ArrayList();
    private int[] mTabTitle = {R.string.tab_reselect, R.string.tab_handover, R.string.tab_attachTime, R.string.tab_dropTimes};

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setToolbarTabLayout();
        initCoordinatorAndTabLayout();
    }

    private void setToolbarTabLayout() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    private void initCoordinatorAndTabLayout() {
        setContentView(R.layout.activity_netinfostatistics);
        this.mFragmentsList = new ArrayList<>();
        this.mViewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment reselectFragment = new ReselectFragment();
        Fragment handOverFragment = new HandOverFragment();
        Fragment attachTimeFragment = new AttachTimeFragment();
        Fragment dropTimesFragment = new DropTimesFragment();
        this.mFragmentsList.add(reselectFragment);
        this.mFragmentsList.add(handOverFragment);
        this.mFragmentsList.add(attachTimeFragment);
        this.mFragmentsList.add(dropTimesFragment);
        this.mViewPager.setAdapter(new TabFragmentPagerAdapter(fragmentManager, this.mFragmentsList, this.mTabTitle, this.mContext));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_reselect)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_handover)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_attachTime)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_dropTimes)));
        tabLayout.setupWithViewPager(this.mViewPager);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int simindex = intent.getIntExtra(SimSelectHelperActivity.INTENT_SIM_INDEX, -1);
        setTitle("SIM" + simindex);
        Log.d(TAG, "onResume simindex=" + simindex);
    }
}
