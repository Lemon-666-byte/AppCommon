package com.ui.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @date：2016/5/3 15:36
 */
public class PhotoViewAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    FragmentManager fm;

    public PhotoViewAdapter(FragmentManager fm) {
        super(fm);
    }

    public PhotoViewAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    
}
