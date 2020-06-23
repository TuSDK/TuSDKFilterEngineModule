package org.lasque.tusdkdemohelper.tusdk;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * 贴纸ViewPager滑动页
 * @author xujie
 * @Date 2018/9/21
 */

public class TabViewPagerAdapter extends FragmentStateAdapter {

    // 默认0 其他为贴纸ID
    public static long mStickerGroupId;

    private List<Fragment> mFragments;

    public TabViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, Lifecycle lifecycle) {
        super(fm,lifecycle);
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
