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
    List<Tag> mTags;
    private UpdateTags mCurrentFragment;

    public HomePagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        mCurrentFragment = null;
        if (position == 0) {
            mCurrentFragment = new MapFragment(mTags);
        } else if (position == 1) {
          mCurrentFragment = new TagListFragment(mTags);
        }
        return (Fragment)mCurrentFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void setTags(List<Tag> tags) {
        this.mTags = tags;
        mCurrentFragment.setTags(tags);
    }
}
