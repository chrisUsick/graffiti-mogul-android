package com.cu_dev.graffitimogul;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by chris on 03-Nov-16.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    public HomePagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MapFragment();
        } else if (position == 1) {
          return new TagListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
