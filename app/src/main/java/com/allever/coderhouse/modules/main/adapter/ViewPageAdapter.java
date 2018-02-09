package com.allever.coderhouse.modules.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by allever on 17-5-27.
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {



    private List<Fragment> fragmentList;
    private List<String> titleList;


    public ViewPageAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> titleList){
        super(fragmentManager);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
