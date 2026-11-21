package com.sprd.engineermode;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private ArrayList<Fragment> mFragments;
    private int[] mTitleList;

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, int[] tabTitle, Context context) {
        super(fm);
        this.mFragments = fragments;
        this.mTitleList = tabTitle;
        this.mContext = context;
    }

    @Override // android.support.v13.app.FragmentPagerAdapter
    public Fragment getItem(int position) {
        ArrayList<Fragment> arrayList = this.mFragments;
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        return this.mFragments.get(position);
    }

    @Override // android.support.v4.view.PagerAdapter
    public CharSequence getPageTitle(int position) {
        int[] iArr = this.mTitleList;
        return iArr.length > position ? this.mContext.getString(iArr[position]) : "";
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        ArrayList<Fragment> arrayList = this.mFragments;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }
}
