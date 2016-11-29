package com.cu_dev.graffitimogul;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.cu_dev.graffitimogul.domain.Tag;

import java.util.List;

/**
 * Created by chris on 03-Nov-16.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment mCurrentFragment = null;
        if (position == 0) {
            mCurrentFragment = MapFragment.newInstance();
        } else if (position == 1) {
          mCurrentFragment = TagListFragment.newInstance();
        }
        return mCurrentFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
