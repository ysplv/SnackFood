package com.example.administrator.mysnack.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.mysnack.entity.DrawTitelEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class DrawlayoutFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment>fragments;
    private List<DrawTitelEntity>titles;
    public DrawlayoutFragmentPagerAdapter(FragmentManager fm,
                                          List<Fragment> fragments, List<DrawTitelEntity> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments!=null?fragments.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getTitle();
    }
}
